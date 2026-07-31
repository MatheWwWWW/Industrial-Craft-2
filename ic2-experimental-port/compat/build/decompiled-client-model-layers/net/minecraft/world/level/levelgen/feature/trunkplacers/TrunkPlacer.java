/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.Products$P3
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Mu
 */
package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public abstract class TrunkPlacer {
    public static final Codec<TrunkPlacer> f_70262_ = Registry.f_122859_.m_194605_().dispatch(TrunkPlacer::m_7362_, TrunkPlacerType::m_70325_);
    private static final int f_161865_ = 32;
    private static final int f_161866_ = 24;
    public static final int f_161867_ = 80;
    protected final int f_70263_;
    protected final int f_70264_;
    protected final int f_70265_;

    protected static <P extends TrunkPlacer> Products.P3<RecordCodecBuilder.Mu<P>, Integer, Integer, Integer> m_70305_(RecordCodecBuilder.Instance<P> p_70306_) {
        return p_70306_.group((App)Codec.intRange((int)0, (int)32).fieldOf("base_height").forGetter(p_70314_ -> p_70314_.f_70263_), (App)Codec.intRange((int)0, (int)24).fieldOf("height_rand_a").forGetter(p_70312_ -> p_70312_.f_70264_), (App)Codec.intRange((int)0, (int)24).fieldOf("height_rand_b").forGetter(p_70308_ -> p_70308_.f_70265_));
    }

    public TrunkPlacer(int p_70268_, int p_70269_, int p_70270_) {
        this.f_70263_ = p_70268_;
        this.f_70264_ = p_70269_;
        this.f_70265_ = p_70270_;
    }

    protected abstract TrunkPlacerType<?> m_7362_();

    public abstract List<FoliagePlacer.FoliageAttachment> m_213934_(LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, RandomSource var3, int var4, BlockPos var5, TreeConfiguration var6);

    public int m_226153_(RandomSource p_226154_) {
        return this.f_70263_ + p_226154_.m_188503_(this.f_70264_ + 1) + p_226154_.m_188503_(this.f_70265_ + 1);
    }

    private static boolean m_70295_(LevelSimulatedReader p_70296_, BlockPos p_70297_) {
        return p_70296_.m_7433_(p_70297_, p_70304_ -> Feature.m_159759_(p_70304_) && !p_70304_.m_60713_(Blocks.f_50440_) && !p_70304_.m_60713_(Blocks.f_50195_));
    }

    protected static void m_226169_(LevelSimulatedReader p_226170_, BiConsumer<BlockPos, BlockState> p_226171_, RandomSource p_226172_, BlockPos p_226173_, TreeConfiguration p_226174_) {
        if (p_226174_.f_161215_ || !TrunkPlacer.m_70295_(p_226170_, p_226173_)) {
            p_226171_.accept(p_226173_, p_226174_.f_161212_.m_213972_(p_226172_, p_226173_));
        }
    }

    protected boolean m_226187_(LevelSimulatedReader p_226188_, BiConsumer<BlockPos, BlockState> p_226189_, RandomSource p_226190_, BlockPos p_226191_, TreeConfiguration p_226192_) {
        return this.m_226175_(p_226188_, p_226189_, p_226190_, p_226191_, p_226192_, Function.identity());
    }

    protected boolean m_226175_(LevelSimulatedReader p_226176_, BiConsumer<BlockPos, BlockState> p_226177_, RandomSource p_226178_, BlockPos p_226179_, TreeConfiguration p_226180_, Function<BlockState, BlockState> p_226181_) {
        if (this.m_213554_(p_226176_, p_226179_)) {
            p_226177_.accept(p_226179_, p_226181_.apply(p_226180_.f_68185_.m_213972_(p_226178_, p_226179_)));
            return true;
        }
        return false;
    }

    protected void m_226163_(LevelSimulatedReader p_226164_, BiConsumer<BlockPos, BlockState> p_226165_, RandomSource p_226166_, BlockPos.MutableBlockPos p_226167_, TreeConfiguration p_226168_) {
        if (this.m_226184_(p_226164_, p_226167_)) {
            this.m_226187_(p_226164_, p_226165_, p_226166_, p_226167_, p_226168_);
        }
    }

    protected boolean m_213554_(LevelSimulatedReader p_226155_, BlockPos p_226156_) {
        return TreeFeature.m_67272_(p_226155_, p_226156_);
    }

    public boolean m_226184_(LevelSimulatedReader p_226185_, BlockPos p_226186_) {
        return this.m_213554_(p_226185_, p_226186_) || p_226185_.m_7433_(p_226186_, p_226183_ -> p_226183_.m_204336_(BlockTags.f_13106_));
    }
}

