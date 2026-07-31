/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.level.storage.LevelSummary
 */
package ic2.core.utils.config.gui.api;

import ic2.core.utils.config.api.ConfigType;
import ic2.core.utils.config.api.IConfigProxy;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.impl.PerWorldProxy;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.storage.LevelSummary;

public interface IModConfig {
    public String getFileName();

    public String getConfigName();

    public String getModId();

    public boolean isDynamicConfig();

    public ConfigType getConfigType();

    public IConfigNode getRootNode();

    public boolean isDefault();

    public void restoreDefault();

    public List<IConfigTarget> getPotentialFiles();

    public IModConfig loadFromFile(Path var1);

    public IModConfig loadFromNetworking(UUID var1, Consumer<Predicate<FriendlyByteBuf>> var2);

    public void save();

    public static class WorldConfigTarget
    implements IConfigTarget {
        LevelSummary summary;
        Path folder;
        Path file;
        String name;

        public WorldConfigTarget(PerWorldProxy.WorldTarget target, Path file) {
            this(target.getSummary(), target.getFolder(), file, target.getName());
        }

        public WorldConfigTarget(LevelSummary summary, Path folder, Path file, String name) {
            this.summary = summary;
            this.folder = folder;
            this.file = file;
            this.name = name;
        }

        @Override
        public Path getFolder() {
            return this.folder;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public Path getConfigFile() {
            return this.file;
        }

        public LevelSummary getSummary() {
            return this.summary;
        }
    }

    public static class SimpleConfigTarget
    implements IConfigTarget {
        Path folder;
        Path file;
        String name;

        public SimpleConfigTarget(IConfigProxy.IPotentialTarget target, Path file) {
            this(target.getFolder(), file, target.getName());
        }

        public SimpleConfigTarget(Path folder, Path file, String name) {
            this.folder = folder;
            this.file = file;
            this.name = name;
        }

        @Override
        public Path getFolder() {
            return this.folder;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public Path getConfigFile() {
            return this.file;
        }
    }

    public static interface IConfigTarget
    extends IConfigProxy.IPotentialTarget {
        public Path getConfigFile();
    }
}

