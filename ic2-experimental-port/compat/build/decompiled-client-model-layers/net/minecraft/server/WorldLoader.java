/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.server;

import com.mojang.datafixers.util.Pair;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.resources.CloseableResourceManager;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.DataPackConfig;

public class WorldLoader {
    public static <D, R> CompletableFuture<R> m_214362_(InitConfig p_214363_, WorldDataSupplier<D> p_214364_, ResultFactory<D, R> p_214365_, Executor p_214366_, Executor p_214367_) {
        try {
            Pair<DataPackConfig, CloseableResourceManager> $$5 = p_214363_.f_214378_.m_214399_();
            CloseableResourceManager $$6 = (CloseableResourceManager)$$5.getSecond();
            Pair<D, RegistryAccess.Frozen> $$7 = p_214364_.m_214412_($$6, (DataPackConfig)$$5.getFirst());
            Object $$8 = $$7.getFirst();
            RegistryAccess.Frozen $$9 = (RegistryAccess.Frozen)$$7.getSecond();
            return ((CompletableFuture)ReloadableServerResources.m_206861_($$6, $$9, p_214363_.f_214379_(), p_214363_.f_214380_(), p_214366_, p_214367_).whenComplete((p_214370_, p_214371_) -> {
                if (p_214371_ != null) {
                    $$6.close();
                }
            })).thenApplyAsync(p_214377_ -> {
                p_214377_.m_206868_($$9);
                return p_214365_.m_214407_($$6, (ReloadableServerResources)p_214377_, $$9, (Object)$$8);
            }, p_214367_);
        }
        catch (Exception $$10) {
            return CompletableFuture.failedFuture($$10);
        }
    }

    public record InitConfig(PackConfig f_214378_, Commands.CommandSelection f_214379_, int f_214380_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{InitConfig.class, "packConfig;commandSelection;functionCompilationLevel", "f_214378_", "f_214379_", "f_214380_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{InitConfig.class, "packConfig;commandSelection;functionCompilationLevel", "f_214378_", "f_214379_", "f_214380_"}, this);
        }

        @Override
        public final boolean equals(Object p_214389_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{InitConfig.class, "packConfig;commandSelection;functionCompilationLevel", "f_214378_", "f_214379_", "f_214380_"}, this, p_214389_);
        }
    }

    public record PackConfig(PackRepository f_214392_, DataPackConfig f_214393_, boolean f_214394_) {
        public Pair<DataPackConfig, CloseableResourceManager> m_214399_() {
            DataPackConfig $$0 = MinecraftServer.m_129819_(this.f_214392_, this.f_214393_, this.f_214394_);
            List<PackResources> $$1 = this.f_214392_.m_10525_();
            MultiPackResourceManager $$2 = new MultiPackResourceManager(PackType.SERVER_DATA, $$1);
            return Pair.of((Object)$$0, (Object)$$2);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{PackConfig.class, "packRepository;initialDataPacks;safeMode", "f_214392_", "f_214393_", "f_214394_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{PackConfig.class, "packRepository;initialDataPacks;safeMode", "f_214392_", "f_214393_", "f_214394_"}, this);
        }

        @Override
        public final boolean equals(Object p_214404_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{PackConfig.class, "packRepository;initialDataPacks;safeMode", "f_214392_", "f_214393_", "f_214394_"}, this, p_214404_);
        }
    }

    @FunctionalInterface
    public static interface WorldDataSupplier<D> {
        public Pair<D, RegistryAccess.Frozen> m_214412_(ResourceManager var1, DataPackConfig var2);
    }

    @FunctionalInterface
    public static interface ResultFactory<D, R> {
        public R m_214407_(CloseableResourceManager var1, ReloadableServerResources var2, RegistryAccess.Frozen var3, D var4);
    }
}

