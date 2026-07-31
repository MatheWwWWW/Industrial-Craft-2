/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.client.Minecraft
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.level.storage.LevelResource
 *  net.minecraft.world.level.storage.LevelStorageSource
 *  net.minecraft.world.level.storage.LevelStorageSource$LevelStorageAccess
 *  net.minecraft.world.level.storage.LevelSummary
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.fml.loading.FMLEnvironment
 *  net.minecraftforge.fml.loading.FMLPaths
 *  net.minecraftforge.server.ServerLifecycleHooks
 */
package ic2.core.utils.config.impl;

import ic2.core.utils.config.api.ConfigType;
import ic2.core.utils.config.api.IConfigProxy;
import ic2.core.utils.config.api.SimpleConfigProxy;
import ic2.core.utils.config.config.ConfigSettings;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.server.ServerLifecycleHooks;

public final class PerWorldProxy
implements IConfigProxy {
    public static final LevelResource SERVERCONFIG = new LevelResource("serverconfig");
    public static final IConfigProxy INSTANCE = new PerWorldProxy(FMLPaths.GAMEDIR.get().resolve("multiplayerconfigs"), FMLPaths.GAMEDIR.get().resolve("defaultconfigs"), FMLPaths.GAMEDIR.get().resolve("saves"));
    Path baseClientPath;
    Path baseServerPath;
    Path saveFolders;

    private PerWorldProxy(Path baseClientPath, Path baseServerPath, Path saveFolders) {
        this.baseClientPath = baseClientPath;
        this.baseServerPath = baseServerPath;
        this.saveFolders = saveFolders;
    }

    public static boolean isProxy(IConfigProxy proxy) {
        return proxy instanceof PerWorldProxy;
    }

    public static ConfigSettings perWorld() {
        return ConfigSettings.withFolderProxy(INSTANCE).withType(ConfigType.SERVER);
    }

    @Override
    public List<Path> getBasePaths() {
        ObjectArrayList paths = new ObjectArrayList();
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            paths.add(server.m_129843_(SERVERCONFIG));
        } else if (FMLEnvironment.dist.isClient()) {
            paths.add(this.baseClientPath);
        }
        paths.add(this.baseServerPath);
        return paths;
    }

    @Override
    public List<? extends IConfigProxy.IPotentialTarget> getPotentialConfigs() {
        if (FMLEnvironment.dist.isClient()) {
            return this.getLevels();
        }
        return Collections.singletonList(new SimpleConfigProxy.SimpleTarget(ServerLifecycleHooks.getCurrentServer().m_129843_(SERVERCONFIG), "server"));
    }

    @OnlyIn(value=Dist.CLIENT)
    private List<WorldTarget> getLevels() {
        LevelStorageSource storage = Minecraft.m_91087_().m_91392_();
        ObjectArrayList folders = new ObjectArrayList();
        for (LevelSummary sum : (List)storage.m_230813_(storage.m_230833_()).join()) {
            try {
                LevelStorageSource.LevelStorageAccess access = Minecraft.m_91087_().m_91392_().m_78260_(sum.m_78358_());
                try {
                    Path path = access.m_78283_(SERVERCONFIG);
                    if (!Files.exists(path, new LinkOption[0])) continue;
                    folders.add(new WorldTarget(sum, access.m_78283_(LevelResource.f_78182_), path));
                }
                finally {
                    if (access == null) continue;
                    access.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return folders;
    }

    @Override
    public boolean isDynamicProxy() {
        return true;
    }

    public static class WorldTarget
    implements IConfigProxy.IPotentialTarget {
        LevelSummary summary;
        Path worldFile;
        Path folder;

        public WorldTarget(LevelSummary summary, Path worldFile, Path folder) {
            this.summary = summary;
            this.worldFile = worldFile;
            this.folder = folder;
        }

        @Override
        public Path getFolder() {
            return this.folder;
        }

        @Override
        public String getName() {
            return this.summary.m_78361_();
        }

        public Path getWorldFile() {
            return this.worldFile;
        }

        public LevelSummary getSummary() {
            return this.summary;
        }
    }
}

