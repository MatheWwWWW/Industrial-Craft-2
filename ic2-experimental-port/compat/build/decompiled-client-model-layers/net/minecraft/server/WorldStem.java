/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.packs.resources.CloseableResourceManager;
import net.minecraft.world.level.storage.WorldData;

public record WorldStem(CloseableResourceManager f_206892_, ReloadableServerResources f_206893_, RegistryAccess.Frozen f_206894_, WorldData f_206895_) implements AutoCloseable
{
    public static CompletableFuture<WorldStem> m_214415_(WorldLoader.InitConfig p_214416_, WorldLoader.WorldDataSupplier<WorldData> p_214417_, Executor p_214418_, Executor p_214419_) {
        return WorldLoader.m_214362_(p_214416_, p_214417_, WorldStem::new, p_214418_, p_214419_);
    }

    @Override
    public void close() {
        this.f_206892_.close();
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{WorldStem.class, "resourceManager;dataPackResources;registryAccess;worldData", "f_206892_", "f_206893_", "f_206894_", "f_206895_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{WorldStem.class, "resourceManager;dataPackResources;registryAccess;worldData", "f_206892_", "f_206893_", "f_206894_", "f_206895_"}, this);
    }

    @Override
    public final boolean equals(Object p_206923_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{WorldStem.class, "resourceManager;dataPackResources;registryAccess;worldData", "f_206892_", "f_206893_", "f_206894_", "f_206895_"}, this, p_206923_);
    }
}

