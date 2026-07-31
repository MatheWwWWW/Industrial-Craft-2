/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.electronwill.nightconfig.core.UnmodifiableConfig
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraftforge.fml.ModContainer
 *  net.minecraftforge.fml.ModLoadingContext
 */
package ic2.core.utils.config.impl.internal;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import ic2.core.utils.config.api.ConfigType;
import ic2.core.utils.config.config.ConfigHandler;
import ic2.core.utils.config.gui.api.BackgroundTexture;
import ic2.core.utils.config.gui.api.IModConfig;
import ic2.core.utils.config.gui.api.IModConfigs;
import ic2.core.utils.config.gui.impl.carbon.ModConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;

public class ModConfigs
implements IModConfigs {
    ModContainer container;
    List<ConfigHandler> knownConfigs = new ObjectArrayList();

    public ModConfigs(ModContainer container) {
        this.container = container;
    }

    public static ModConfigs of() {
        return new ModConfigs(ModLoadingContext.get().getActiveContainer());
    }

    public static ModConfigs of(ConfigHandler handler) {
        ModConfigs configs = new ModConfigs(ModLoadingContext.get().getActiveContainer());
        configs.addConfig(handler);
        return configs;
    }

    @Override
    public String getModName() {
        return this.container.getModInfo().getDisplayName();
    }

    public void addConfig(ConfigHandler config) {
        this.knownConfigs.add(config);
    }

    @Override
    public List<IModConfig> getConfigInstances(ConfigType type) {
        ObjectArrayList instance = new ObjectArrayList();
        for (ConfigHandler handler : this.knownConfigs) {
            if (!handler.isRegistered() || handler.getConfigType() != type) continue;
            instance.add(new ModConfig(this.container.getModId(), handler));
        }
        instance.sort(Comparator.comparing(IModConfig::getConfigName, String.CASE_INSENSITIVE_ORDER));
        return instance;
    }

    @Override
    public BackgroundTexture getBackground() {
        UnmodifiableConfig config;
        Optional texture = this.container.getCustomExtension(IModConfigs.Background.class);
        if (texture.isPresent()) {
            return ((IModConfigs.Background)texture.get()).texture();
        }
        Object obj = this.container.getModInfo().getModProperties().get("guiconfig");
        if (obj instanceof UnmodifiableConfig && (config = (UnmodifiableConfig)obj) != null) {
            if (config.contains("texture")) {
                BackgroundTexture.Builder builder = BackgroundTexture.of((String)config.get("texture"));
                if (config.contains("brightness")) {
                    builder.withBrightness(Integer.parseInt((String)config.get("brightness")));
                }
                return builder.build();
            }
            if (config.contains("background")) {
                BackgroundTexture.Builder builder = BackgroundTexture.of((String)config.get("background"));
                if (config.contains("foreground")) {
                    builder.withForeground((String)config.get("foreground"));
                }
                if (config.contains("brightness")) {
                    builder.withBrightness(Integer.parseInt((String)config.get("brightness")));
                }
                if (config.contains("background_brightness")) {
                    builder.withBackground(Integer.parseInt((String)config.get("background_brightness")));
                }
                if (config.contains("foreground_brightness")) {
                    builder.withForeground(Integer.parseInt((String)config.get("foreground_brightness")));
                }
                return builder.build();
            }
        }
        return BackgroundTexture.DEFAULT;
    }
}

