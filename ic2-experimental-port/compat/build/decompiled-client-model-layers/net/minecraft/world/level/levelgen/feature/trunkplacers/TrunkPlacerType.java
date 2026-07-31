/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer;

public class TrunkPlacerType<P extends TrunkPlacer> {
    public static final TrunkPlacerType<StraightTrunkPlacer> f_70315_ = TrunkPlacerType.m_70326_("straight_trunk_placer", StraightTrunkPlacer.f_70245_);
    public static final TrunkPlacerType<ForkingTrunkPlacer> f_70316_ = TrunkPlacerType.m_70326_("forking_trunk_placer", ForkingTrunkPlacer.f_70145_);
    public static final TrunkPlacerType<GiantTrunkPlacer> f_70317_ = TrunkPlacerType.m_70326_("giant_trunk_placer", GiantTrunkPlacer.f_70162_);
    public static final TrunkPlacerType<MegaJungleTrunkPlacer> f_70318_ = TrunkPlacerType.m_70326_("mega_jungle_trunk_placer", MegaJungleTrunkPlacer.f_70190_);
    public static final TrunkPlacerType<DarkOakTrunkPlacer> f_70319_ = TrunkPlacerType.m_70326_("dark_oak_trunk_placer", DarkOakTrunkPlacer.f_70074_);
    public static final TrunkPlacerType<FancyTrunkPlacer> f_70320_ = TrunkPlacerType.m_70326_("fancy_trunk_placer", FancyTrunkPlacer.f_70091_);
    public static final TrunkPlacerType<BendingTrunkPlacer> f_161899_ = TrunkPlacerType.m_70326_("bending_trunk_placer", BendingTrunkPlacer.f_161765_);
    public static final TrunkPlacerType<UpwardsBranchingTrunkPlacer> f_226193_ = TrunkPlacerType.m_70326_("upwards_branching_trunk_placer", UpwardsBranchingTrunkPlacer.f_226194_);
    private final Codec<P> f_70321_;

    private static <P extends TrunkPlacer> TrunkPlacerType<P> m_70326_(String p_70327_, Codec<P> p_70328_) {
        return Registry.m_122961_(Registry.f_122859_, p_70327_, new TrunkPlacerType<P>(p_70328_));
    }

    private TrunkPlacerType(Codec<P> p_70324_) {
        this.f_70321_ = p_70324_;
    }

    public Codec<P> m_70325_() {
        return this.f_70321_;
    }
}

