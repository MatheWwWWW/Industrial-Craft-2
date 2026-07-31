/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;

public class FoliagePlacerType<P extends FoliagePlacer> {
    public static final FoliagePlacerType<BlobFoliagePlacer> f_68591_ = FoliagePlacerType.m_68605_("blob_foliage_placer", BlobFoliagePlacer.f_68392_);
    public static final FoliagePlacerType<SpruceFoliagePlacer> f_68592_ = FoliagePlacerType.m_68605_("spruce_foliage_placer", SpruceFoliagePlacer.f_68713_);
    public static final FoliagePlacerType<PineFoliagePlacer> f_68593_ = FoliagePlacerType.m_68605_("pine_foliage_placer", PineFoliagePlacer.f_68676_);
    public static final FoliagePlacerType<AcaciaFoliagePlacer> f_68594_ = FoliagePlacerType.m_68605_("acacia_foliage_placer", AcaciaFoliagePlacer.f_68362_);
    public static final FoliagePlacerType<BushFoliagePlacer> f_68595_ = FoliagePlacerType.m_68605_("bush_foliage_placer", BushFoliagePlacer.f_68428_);
    public static final FoliagePlacerType<FancyFoliagePlacer> f_68596_ = FoliagePlacerType.m_68605_("fancy_foliage_placer", FancyFoliagePlacer.f_68492_);
    public static final FoliagePlacerType<MegaJungleFoliagePlacer> f_68597_ = FoliagePlacerType.m_68605_("jungle_foliage_placer", MegaJungleFoliagePlacer.f_68608_);
    public static final FoliagePlacerType<MegaPineFoliagePlacer> f_68598_ = FoliagePlacerType.m_68605_("mega_pine_foliage_placer", MegaPineFoliagePlacer.f_68642_);
    public static final FoliagePlacerType<DarkOakFoliagePlacer> f_68599_ = FoliagePlacerType.m_68605_("dark_oak_foliage_placer", DarkOakFoliagePlacer.f_68455_);
    public static final FoliagePlacerType<RandomSpreadFoliagePlacer> f_161452_ = FoliagePlacerType.m_68605_("random_spread_foliage_placer", RandomSpreadFoliagePlacer.f_161501_);
    private final Codec<P> f_68600_;

    private static <P extends FoliagePlacer> FoliagePlacerType<P> m_68605_(String p_68606_, Codec<P> p_68607_) {
        return Registry.m_122961_(Registry.f_122858_, p_68606_, new FoliagePlacerType<P>(p_68607_));
    }

    private FoliagePlacerType(Codec<P> p_68603_) {
        this.f_68600_ = p_68603_;
    }

    public Codec<P> m_68604_() {
        return this.f_68600_;
    }
}

