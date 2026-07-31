/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.network.FriendlyByteBuf
 */
package ic2.core.utils.config.gui.impl.carbon;

import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.config.ConfigRequestPacket;
import ic2.core.networking.packets.config.SaveConfigPacket;
import ic2.core.utils.config.api.ConfigType;
import ic2.core.utils.config.api.IConfigProxy;
import ic2.core.utils.config.config.Config;
import ic2.core.utils.config.config.ConfigHandler;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.api.IModConfig;
import ic2.core.utils.config.gui.impl.carbon.ConfigRoot;
import ic2.core.utils.config.impl.PerWorldProxy;
import ic2.core.utils.config.utils.MultilinePolicy;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.network.FriendlyByteBuf;

public class ModConfig
implements IModConfig {
    String modId;
    ConfigHandler handler;
    Config config;
    Path path;

    public ModConfig(String modId, ConfigHandler handler) {
        this(modId, handler, handler.getConfig(), handler.getConfigFile());
    }

    public ModConfig(String modId, ConfigHandler handler, Config config, Path path) {
        this.modId = modId;
        this.handler = handler;
        this.config = config;
        this.path = path;
    }

    @Override
    public IModConfig loadFromFile(Path path) {
        if (Files.notExists(path, new LinkOption[0])) {
            return null;
        }
        Config copy = this.config.copy();
        try {
            ConfigHandler.load(this.handler, copy, Files.readAllLines(path), false);
            return new ModConfig(this.modId, this.handler, this.config, path);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public IModConfig loadFromNetworking(UUID requestId, Consumer<Predicate<FriendlyByteBuf>> network) {
        NetworkModConfig config = new NetworkModConfig(this.modId, this.handler, this.config.copy());
        PacketManager.INSTANCE.sendToServer(new ConfigRequestPacket(requestId, this.handler.getConfigIdentifer()));
        network.accept(config);
        return config;
    }

    @Override
    public String getFileName() {
        return this.handler.getConfig().getName().concat(".cfg");
    }

    @Override
    public String getConfigName() {
        return this.handler.getSubFolder().isEmpty() ? this.handler.getConfig().getName() : this.handler.getConfigIdentifer();
    }

    @Override
    public String getModId() {
        return this.modId;
    }

    @Override
    public boolean isDynamicConfig() {
        return this.handler.getProxy().isDynamicProxy();
    }

    @Override
    public List<IModConfig.IConfigTarget> getPotentialFiles() {
        ObjectArrayList result = new ObjectArrayList();
        for (IConfigProxy.IPotentialTarget iPotentialTarget : this.handler.getProxy().getPotentialConfigs()) {
            Path file = this.handler.createConfigFile(iPotentialTarget.getFolder());
            if (Files.notExists(file, new LinkOption[0])) continue;
            if (iPotentialTarget instanceof PerWorldProxy.WorldTarget) {
                result.add(new IModConfig.WorldConfigTarget((PerWorldProxy.WorldTarget)iPotentialTarget, file));
                continue;
            }
            result.add(new IModConfig.SimpleConfigTarget(iPotentialTarget, file));
        }
        return result;
    }

    @Override
    public ConfigType getConfigType() {
        return this.handler.getConfigType();
    }

    @Override
    public IConfigNode getRootNode() {
        return new ConfigRoot(this.config);
    }

    @Override
    public boolean isDefault() {
        return this.config.isDefault();
    }

    @Override
    public void restoreDefault() {
        this.config.resetDefault();
    }

    @Override
    public void save() {
        if (this.config == this.handler.getConfig()) {
            this.handler.save();
            this.handler.onSynced();
        } else {
            try (BufferedWriter writer = Files.newBufferedWriter(this.path, new OpenOption[0]);){
                writer.write(this.config.serialize(this.handler.getMultilinePolicy()));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class NetworkModConfig
    extends ModConfig
    implements Predicate<FriendlyByteBuf> {
        public NetworkModConfig(String modId, ConfigHandler handler, Config config) {
            super(modId, handler, config, null);
        }

        @Override
        public boolean test(FriendlyByteBuf t) {
            ObjectArrayList lines = ObjectArrayList.wrap((Object[])t.m_130136_(262144).split("\n"));
            try {
                ConfigHandler.load(this.handler, this.config, (List<String>)lines, false);
                return true;
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public void save() {
            PacketManager.INSTANCE.sendToServer(new SaveConfigPacket(this.handler.getConfigIdentifer(), this.config.serialize(MultilinePolicy.DISABLED)));
        }
    }
}

