/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Lifecycle
 */
package net.minecraft.client.gui.screens.worldselection;

import com.mojang.serialization.Lifecycle;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.world.level.levelgen.WorldGenSettings;

public record WorldCreationContext(WorldGenSettings f_232987_, Lifecycle f_232988_, RegistryAccess.Frozen f_232989_, ReloadableServerResources f_232990_) {
    public WorldCreationContext m_232997_(WorldGenSettings p_232998_) {
        return new WorldCreationContext(p_232998_, this.f_232988_, this.f_232989_, this.f_232990_);
    }

    public WorldCreationContext m_232999_(SimpleUpdater p_233000_) {
        WorldGenSettings $$1 = (WorldGenSettings)p_233000_.apply(this.f_232987_);
        return this.m_232997_($$1);
    }

    public WorldCreationContext m_233001_(Updater p_233002_) {
        WorldGenSettings $$1 = (WorldGenSettings)p_233002_.apply(this.f_232989_, this.f_232987_);
        return this.m_232997_($$1);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{WorldCreationContext.class, "worldGenSettings;worldSettingsStability;registryAccess;dataPackResources", "f_232987_", "f_232988_", "f_232989_", "f_232990_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{WorldCreationContext.class, "worldGenSettings;worldSettingsStability;registryAccess;dataPackResources", "f_232987_", "f_232988_", "f_232989_", "f_232990_"}, this);
    }

    @Override
    public final boolean equals(Object p_233007_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{WorldCreationContext.class, "worldGenSettings;worldSettingsStability;registryAccess;dataPackResources", "f_232987_", "f_232988_", "f_232989_", "f_232990_"}, this, p_233007_);
    }

    @FunctionalInterface
    public static interface SimpleUpdater
    extends UnaryOperator<WorldGenSettings> {
    }

    @FunctionalInterface
    public static interface Updater
    extends BiFunction<RegistryAccess.Frozen, WorldGenSettings, WorldGenSettings> {
    }
}

