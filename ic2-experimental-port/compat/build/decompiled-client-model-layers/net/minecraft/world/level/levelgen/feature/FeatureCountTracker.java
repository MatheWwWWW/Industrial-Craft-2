/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.CacheBuilder
 *  com.google.common.cache.CacheLoader
 *  com.google.common.cache.LoadingCache
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  org.apache.commons.lang3.mutable.MutableInt
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen.feature;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;

public class FeatureCountTracker {
    private static final Logger f_190876_ = LogUtils.getLogger();
    private static final LoadingCache<ServerLevel, LevelData> f_190877_ = CacheBuilder.newBuilder().weakKeys().expireAfterAccess(5L, TimeUnit.MINUTES).build((CacheLoader)new CacheLoader<ServerLevel, LevelData>(){

        public LevelData load(ServerLevel p_190902_) {
            return new LevelData((Object2IntMap<FeatureData>)Object2IntMaps.synchronize((Object2IntMap)new Object2IntOpenHashMap()), new MutableInt(0));
        }

        public /* synthetic */ Object load(Object object) throws Exception {
            return this.load((ServerLevel)object);
        }
    });

    public static void m_190881_(ServerLevel p_190882_) {
        try {
            ((LevelData)f_190877_.get((Object)p_190882_)).f_190917_().increment();
        }
        catch (Exception $$1) {
            f_190876_.error("Failed to increment chunk count", (Throwable)$$1);
        }
    }

    public static void m_190883_(ServerLevel p_190884_, ConfiguredFeature<?, ?> p_190885_, Optional<PlacedFeature> p_190886_) {
        try {
            ((LevelData)f_190877_.get((Object)p_190884_)).f_190916_().computeInt((Object)new FeatureData(p_190885_, p_190886_), (p_190891_, p_190892_) -> p_190892_ == null ? 1 : p_190892_ + 1);
        }
        catch (Exception $$3) {
            f_190876_.error("Failed to increment feature count", (Throwable)$$3);
        }
    }

    public static void m_190880_() {
        f_190877_.invalidateAll();
        f_190876_.debug("Cleared feature counts");
    }

    public static void m_190899_() {
        f_190876_.debug("Logging feature counts:");
        f_190877_.asMap().forEach((p_190888_, p_190889_) -> {
            String $$2 = p_190888_.m_46472_().m_135782_().toString();
            boolean $$3 = p_190888_.m_7654_().m_130010_();
            Registry<PlacedFeature> $$4 = p_190888_.m_5962_().m_175515_(Registry.f_194567_);
            String $$5 = ($$3 ? "running" : "dead") + " " + $$2;
            Integer $$6 = p_190889_.f_190917_().getValue();
            f_190876_.debug($$5 + " total_chunks: " + $$6);
            p_190889_.f_190916_().forEach((p_190897_, p_190898_) -> f_190876_.debug($$5 + " " + String.format(Locale.ROOT, "%10d ", p_190898_) + String.format(Locale.ROOT, "%10f ", (double)p_190898_.intValue() / (double)$$6.intValue()) + p_190897_.f_190906_().flatMap($$4::m_7854_).map(ResourceKey::m_135782_) + " " + p_190897_.f_190905_().f_65377_() + " " + p_190897_.f_190905_()));
        });
    }

    record LevelData(Object2IntMap<FeatureData> f_190916_, MutableInt f_190917_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{LevelData.class, "featureData;chunksWithFeatures", "f_190916_", "f_190917_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{LevelData.class, "featureData;chunksWithFeatures", "f_190916_", "f_190917_"}, this);
        }

        @Override
        public final boolean equals(Object p_190924_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{LevelData.class, "featureData;chunksWithFeatures", "f_190916_", "f_190917_"}, this, p_190924_);
        }
    }

    record FeatureData(ConfiguredFeature<?, ?> f_190905_, Optional<PlacedFeature> f_190906_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{FeatureData.class, "feature;topFeature", "f_190905_", "f_190906_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{FeatureData.class, "feature;topFeature", "f_190905_", "f_190906_"}, this);
        }

        @Override
        public final boolean equals(Object p_190913_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{FeatureData.class, "feature;topFeature", "f_190905_", "f_190906_"}, this, p_190913_);
        }
    }
}

