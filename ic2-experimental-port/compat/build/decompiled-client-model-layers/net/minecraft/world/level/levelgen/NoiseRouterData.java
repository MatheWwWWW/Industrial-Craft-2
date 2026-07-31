/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen;

import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.OreVeinifier;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class NoiseRouterData {
    public static final float f_224426_ = -0.50375f;
    private static final float f_209440_ = 0.08f;
    private static final double f_209441_ = 1.5;
    private static final double f_209442_ = 1.5;
    private static final double f_209443_ = 1.5625;
    private static final double f_224432_ = -0.703125;
    public static final int f_224427_ = 64;
    public static final long f_224428_ = 4096L;
    private static final DensityFunction f_209444_ = DensityFunctions.m_208264_(10.0);
    private static final DensityFunction f_209445_ = DensityFunctions.m_208263_();
    private static final ResourceKey<DensityFunction> f_209446_ = NoiseRouterData.m_209536_("zero");
    private static final ResourceKey<DensityFunction> f_209447_ = NoiseRouterData.m_209536_("y");
    private static final ResourceKey<DensityFunction> f_209448_ = NoiseRouterData.m_209536_("shift_x");
    private static final ResourceKey<DensityFunction> f_209449_ = NoiseRouterData.m_209536_("shift_z");
    private static final ResourceKey<DensityFunction> f_224433_ = NoiseRouterData.m_209536_("overworld/base_3d_noise");
    private static final ResourceKey<DensityFunction> f_224434_ = NoiseRouterData.m_209536_("nether/base_3d_noise");
    private static final ResourceKey<DensityFunction> f_224418_ = NoiseRouterData.m_209536_("end/base_3d_noise");
    public static final ResourceKey<DensityFunction> f_209451_ = NoiseRouterData.m_209536_("overworld/continents");
    public static final ResourceKey<DensityFunction> f_209452_ = NoiseRouterData.m_209536_("overworld/erosion");
    public static final ResourceKey<DensityFunction> f_209453_ = NoiseRouterData.m_209536_("overworld/ridges");
    public static final ResourceKey<DensityFunction> f_224429_ = NoiseRouterData.m_209536_("overworld/ridges_folded");
    public static final ResourceKey<DensityFunction> f_224430_ = NoiseRouterData.m_209536_("overworld/offset");
    public static final ResourceKey<DensityFunction> f_209454_ = NoiseRouterData.m_209536_("overworld/factor");
    public static final ResourceKey<DensityFunction> f_224431_ = NoiseRouterData.m_209536_("overworld/jaggedness");
    public static final ResourceKey<DensityFunction> f_209455_ = NoiseRouterData.m_209536_("overworld/depth");
    private static final ResourceKey<DensityFunction> f_209456_ = NoiseRouterData.m_209536_("overworld/sloped_cheese");
    public static final ResourceKey<DensityFunction> f_209457_ = NoiseRouterData.m_209536_("overworld_large_biomes/continents");
    public static final ResourceKey<DensityFunction> f_209458_ = NoiseRouterData.m_209536_("overworld_large_biomes/erosion");
    private static final ResourceKey<DensityFunction> f_224419_ = NoiseRouterData.m_209536_("overworld_large_biomes/offset");
    private static final ResourceKey<DensityFunction> f_209459_ = NoiseRouterData.m_209536_("overworld_large_biomes/factor");
    private static final ResourceKey<DensityFunction> f_224420_ = NoiseRouterData.m_209536_("overworld_large_biomes/jaggedness");
    private static final ResourceKey<DensityFunction> f_209460_ = NoiseRouterData.m_209536_("overworld_large_biomes/depth");
    private static final ResourceKey<DensityFunction> f_209461_ = NoiseRouterData.m_209536_("overworld_large_biomes/sloped_cheese");
    private static final ResourceKey<DensityFunction> f_224421_ = NoiseRouterData.m_209536_("overworld_amplified/offset");
    private static final ResourceKey<DensityFunction> f_224422_ = NoiseRouterData.m_209536_("overworld_amplified/factor");
    private static final ResourceKey<DensityFunction> f_224423_ = NoiseRouterData.m_209536_("overworld_amplified/jaggedness");
    private static final ResourceKey<DensityFunction> f_224424_ = NoiseRouterData.m_209536_("overworld_amplified/depth");
    private static final ResourceKey<DensityFunction> f_224425_ = NoiseRouterData.m_209536_("overworld_amplified/sloped_cheese");
    private static final ResourceKey<DensityFunction> f_209462_ = NoiseRouterData.m_209536_("end/sloped_cheese");
    private static final ResourceKey<DensityFunction> f_209463_ = NoiseRouterData.m_209536_("overworld/caves/spaghetti_roughness_function");
    private static final ResourceKey<DensityFunction> f_209464_ = NoiseRouterData.m_209536_("overworld/caves/entrances");
    private static final ResourceKey<DensityFunction> f_209465_ = NoiseRouterData.m_209536_("overworld/caves/noodle");
    private static final ResourceKey<DensityFunction> f_209437_ = NoiseRouterData.m_209536_("overworld/caves/pillars");
    private static final ResourceKey<DensityFunction> f_209438_ = NoiseRouterData.m_209536_("overworld/caves/spaghetti_2d_thickness_modulator");
    private static final ResourceKey<DensityFunction> f_209439_ = NoiseRouterData.m_209536_("overworld/caves/spaghetti_2d");

    private static ResourceKey<DensityFunction> m_209536_(String p_209537_) {
        return ResourceKey.m_135785_(Registry.f_211074_, new ResourceLocation(p_209537_));
    }

    public static Holder<? extends DensityFunction> m_224458_(Registry<DensityFunction> p_224459_) {
        NoiseRouterData.m_224498_(p_224459_, f_209446_, DensityFunctions.m_208263_());
        int $$1 = DimensionType.f_156653_ * 2;
        int $$2 = DimensionType.f_156652_ * 2;
        NoiseRouterData.m_224498_(p_224459_, f_209447_, DensityFunctions.m_208266_($$1, $$2, $$1, $$2));
        DensityFunction $$3 = NoiseRouterData.m_224467_(p_224459_, f_209448_, DensityFunctions.m_208361_(DensityFunctions.m_208373_(DensityFunctions.m_208366_(NoiseRouterData.m_209542_(Noises.f_189286_)))));
        DensityFunction $$4 = NoiseRouterData.m_224467_(p_224459_, f_209449_, DensityFunctions.m_208361_(DensityFunctions.m_208373_(DensityFunctions.m_208378_(NoiseRouterData.m_209542_(Noises.f_189286_)))));
        NoiseRouterData.m_224498_(p_224459_, f_224433_, BlendedNoise.m_230477_(0.25, 0.125, 80.0, 160.0, 8.0));
        NoiseRouterData.m_224498_(p_224459_, f_224434_, BlendedNoise.m_230477_(0.25, 0.375, 80.0, 60.0, 8.0));
        NoiseRouterData.m_224498_(p_224459_, f_224418_, BlendedNoise.m_230477_(0.25, 0.25, 80.0, 160.0, 4.0));
        Holder<DensityFunction> $$5 = NoiseRouterData.m_224498_(p_224459_, f_209451_, DensityFunctions.m_208361_(DensityFunctions.m_208296_($$3, $$4, 0.25, NoiseRouterData.m_209542_(Noises.f_189279_))));
        Holder<DensityFunction> $$6 = NoiseRouterData.m_224498_(p_224459_, f_209452_, DensityFunctions.m_208361_(DensityFunctions.m_208296_($$3, $$4, 0.25, NoiseRouterData.m_209542_(Noises.f_189280_))));
        DensityFunction $$7 = NoiseRouterData.m_224467_(p_224459_, f_209453_, DensityFunctions.m_208361_(DensityFunctions.m_208296_($$3, $$4, 0.25, NoiseRouterData.m_209542_(Noises.f_189285_))));
        NoiseRouterData.m_224498_(p_224459_, f_224429_, NoiseRouterData.m_224437_($$7));
        DensityFunction $$8 = DensityFunctions.m_208368_(NoiseRouterData.m_209542_(Noises.f_189255_), 1500.0, 0.0);
        NoiseRouterData.m_224474_(p_224459_, $$8, $$5, $$6, f_224430_, f_209454_, f_224431_, f_209455_, f_209456_, false);
        Holder<DensityFunction> $$9 = NoiseRouterData.m_224498_(p_224459_, f_209457_, DensityFunctions.m_208361_(DensityFunctions.m_208296_($$3, $$4, 0.25, NoiseRouterData.m_209542_(Noises.f_189283_))));
        Holder<DensityFunction> $$10 = NoiseRouterData.m_224498_(p_224459_, f_209458_, DensityFunctions.m_208361_(DensityFunctions.m_208296_($$3, $$4, 0.25, NoiseRouterData.m_209542_(Noises.f_189284_))));
        NoiseRouterData.m_224474_(p_224459_, $$8, $$9, $$10, f_224419_, f_209459_, f_224420_, f_209460_, f_209461_, false);
        NoiseRouterData.m_224474_(p_224459_, $$8, $$5, $$6, f_224421_, f_224422_, f_224423_, f_224424_, f_224425_, true);
        NoiseRouterData.m_224498_(p_224459_, f_209462_, DensityFunctions.m_208293_(DensityFunctions.m_208271_(0L), NoiseRouterData.m_224464_(p_224459_, f_224418_)));
        NoiseRouterData.m_224498_(p_224459_, f_209463_, NoiseRouterData.m_209547_());
        NoiseRouterData.m_224498_(p_224459_, f_209438_, DensityFunctions.m_208380_(DensityFunctions.m_208336_(NoiseRouterData.m_209542_(Noises.f_189297_), 2.0, 1.0, -0.6, -1.3)));
        NoiseRouterData.m_224498_(p_224459_, f_209439_, NoiseRouterData.m_224517_(p_224459_));
        NoiseRouterData.m_224498_(p_224459_, f_209464_, NoiseRouterData.m_224513_(p_224459_));
        NoiseRouterData.m_224498_(p_224459_, f_209465_, NoiseRouterData.m_224515_(p_224459_));
        return NoiseRouterData.m_224498_(p_224459_, f_209437_, NoiseRouterData.m_209560_());
    }

    private static void m_224474_(Registry<DensityFunction> p_224475_, DensityFunction p_224476_, Holder<DensityFunction> p_224477_, Holder<DensityFunction> p_224478_, ResourceKey<DensityFunction> p_224479_, ResourceKey<DensityFunction> p_224480_, ResourceKey<DensityFunction> p_224481_, ResourceKey<DensityFunction> p_224482_, ResourceKey<DensityFunction> p_224483_, boolean p_224484_) {
        DensityFunctions.Spline.Coordinate $$10 = new DensityFunctions.Spline.Coordinate(p_224477_);
        DensityFunctions.Spline.Coordinate $$11 = new DensityFunctions.Spline.Coordinate(p_224478_);
        DensityFunctions.Spline.Coordinate $$12 = new DensityFunctions.Spline.Coordinate(p_224475_.m_206081_(f_209453_));
        DensityFunctions.Spline.Coordinate $$13 = new DensityFunctions.Spline.Coordinate(p_224475_.m_206081_(f_224429_));
        DensityFunction $$14 = NoiseRouterData.m_224467_(p_224475_, p_224479_, NoiseRouterData.m_224453_(DensityFunctions.m_208293_(DensityFunctions.m_208264_(-0.50375f), DensityFunctions.m_224020_(TerrainProvider.m_236635_($$10, $$11, $$13, p_224484_))), DensityFunctions.m_208372_()));
        DensityFunction $$15 = NoiseRouterData.m_224467_(p_224475_, p_224480_, NoiseRouterData.m_224453_(DensityFunctions.m_224020_(TerrainProvider.m_236629_($$10, $$11, $$12, $$13, p_224484_)), f_209444_));
        DensityFunction $$16 = NoiseRouterData.m_224467_(p_224475_, p_224482_, DensityFunctions.m_208293_(DensityFunctions.m_208266_(-64, 320, 1.5, -1.5), $$14));
        DensityFunction $$17 = NoiseRouterData.m_224467_(p_224475_, p_224481_, NoiseRouterData.m_224453_(DensityFunctions.m_224020_(TerrainProvider.m_236642_($$10, $$11, $$12, $$13, p_224484_)), f_209445_));
        DensityFunction $$18 = DensityFunctions.m_208363_($$17, p_224476_.m_208232_());
        DensityFunction $$19 = NoiseRouterData.m_212271_($$15, DensityFunctions.m_208293_($$16, $$18));
        NoiseRouterData.m_224498_(p_224475_, p_224483_, DensityFunctions.m_208293_($$19, NoiseRouterData.m_224464_(p_224475_, f_224433_)));
    }

    private static DensityFunction m_224467_(Registry<DensityFunction> p_224468_, ResourceKey<DensityFunction> p_224469_, DensityFunction p_224470_) {
        return new DensityFunctions.HolderHolder(BuiltinRegistries.m_206384_(p_224468_, p_224469_, p_224470_));
    }

    private static Holder<DensityFunction> m_224498_(Registry<DensityFunction> p_224499_, ResourceKey<DensityFunction> p_224500_, DensityFunction p_224501_) {
        return BuiltinRegistries.m_206384_(p_224499_, p_224500_, p_224501_);
    }

    private static Holder<NormalNoise.NoiseParameters> m_209542_(ResourceKey<NormalNoise.NoiseParameters> p_209543_) {
        return BuiltinRegistries.f_194654_.m_206081_(p_209543_);
    }

    private static DensityFunction m_224464_(Registry<DensityFunction> p_224465_, ResourceKey<DensityFunction> p_224466_) {
        return new DensityFunctions.HolderHolder(p_224465_.m_206081_(p_224466_));
    }

    private static DensityFunction m_224437_(DensityFunction p_224438_) {
        return DensityFunctions.m_208363_(DensityFunctions.m_208293_(DensityFunctions.m_208293_(p_224438_.m_208229_(), DensityFunctions.m_208264_(-0.6666666666666666)).m_208229_(), DensityFunctions.m_208264_(-0.3333333333333333)), DensityFunctions.m_208264_(-3.0));
    }

    public static float m_224435_(float p_224436_) {
        return -(Math.abs(Math.abs(p_224436_) - 0.6666667f) - 0.33333334f) * 3.0f;
    }

    private static DensityFunction m_209547_() {
        DensityFunction $$0 = DensityFunctions.m_208322_(NoiseRouterData.m_209542_(Noises.f_189302_));
        DensityFunction $$1 = DensityFunctions.m_208327_(NoiseRouterData.m_209542_(Noises.f_189243_), 0.0, -0.1);
        return DensityFunctions.m_208380_(DensityFunctions.m_208363_($$1, DensityFunctions.m_208293_($$0.m_208229_(), DensityFunctions.m_208264_(-0.4))));
    }

    private static DensityFunction m_224513_(Registry<DensityFunction> p_224514_) {
        DensityFunction $$1 = DensityFunctions.m_208380_(DensityFunctions.m_208368_(NoiseRouterData.m_209542_(Noises.f_189300_), 2.0, 1.0));
        DensityFunction $$2 = DensityFunctions.m_208327_(NoiseRouterData.m_209542_(Noises.f_189301_), -0.065, -0.088);
        DensityFunction $$3 = DensityFunctions.m_208315_($$1, NoiseRouterData.m_209542_(Noises.f_189298_), DensityFunctions.WeirdScaledSampler.RarityValueMapper.TYPE1);
        DensityFunction $$4 = DensityFunctions.m_208315_($$1, NoiseRouterData.m_209542_(Noises.f_189299_), DensityFunctions.WeirdScaledSampler.RarityValueMapper.TYPE1);
        DensityFunction $$5 = DensityFunctions.m_208293_(DensityFunctions.m_208382_($$3, $$4), $$2).m_208220_(-1.0, 1.0);
        DensityFunction $$6 = NoiseRouterData.m_224464_(p_224514_, f_209463_);
        DensityFunction $$7 = DensityFunctions.m_208368_(NoiseRouterData.m_209542_(Noises.f_189244_), 0.75, 0.5);
        DensityFunction $$8 = DensityFunctions.m_208293_(DensityFunctions.m_208293_($$7, DensityFunctions.m_208264_(0.37)), DensityFunctions.m_208266_(-10, 30, 0.3, 0.0));
        return DensityFunctions.m_208380_(DensityFunctions.m_208375_($$8, DensityFunctions.m_208293_($$6, $$5)));
    }

    private static DensityFunction m_224515_(Registry<DensityFunction> p_224516_) {
        DensityFunction $$1 = NoiseRouterData.m_224464_(p_224516_, f_209447_);
        int $$2 = -64;
        int $$3 = -60;
        int $$4 = 320;
        DensityFunction $$5 = NoiseRouterData.m_209471_($$1, DensityFunctions.m_208368_(NoiseRouterData.m_209542_(Noises.f_189251_), 1.0, 1.0), -60, 320, -1);
        DensityFunction $$6 = NoiseRouterData.m_209471_($$1, DensityFunctions.m_208336_(NoiseRouterData.m_209542_(Noises.f_189252_), 1.0, 1.0, -0.05, -0.1), -60, 320, 0);
        double $$7 = 2.6666666666666665;
        DensityFunction $$8 = NoiseRouterData.m_209471_($$1, DensityFunctions.m_208368_(NoiseRouterData.m_209542_(Noises.f_189253_), 2.6666666666666665, 2.6666666666666665), -60, 320, 0);
        DensityFunction $$9 = NoiseRouterData.m_209471_($$1, DensityFunctions.m_208368_(NoiseRouterData.m_209542_(Noises.f_189254_), 2.6666666666666665, 2.6666666666666665), -60, 320, 0);
        DensityFunction $$10 = DensityFunctions.m_208363_(DensityFunctions.m_208264_(1.5), DensityFunctions.m_208382_($$8.m_208229_(), $$9.m_208229_()));
        return DensityFunctions.m_208287_($$5, -1000000.0, 0.0, DensityFunctions.m_208264_(64.0), DensityFunctions.m_208293_($$6, $$10));
    }

    private static DensityFunction m_209560_() {
        double $$0 = 25.0;
        double $$1 = 0.3;
        DensityFunction $$2 = DensityFunctions.m_208368_(NoiseRouterData.m_209542_(Noises.f_189291_), 25.0, 0.3);
        DensityFunction $$3 = DensityFunctions.m_208327_(NoiseRouterData.m_209542_(Noises.f_189292_), 0.0, -2.0);
        DensityFunction $$4 = DensityFunctions.m_208327_(NoiseRouterData.m_209542_(Noises.f_189293_), 0.0, 1.1);
        DensityFunction $$5 = DensityFunctions.m_208293_(DensityFunctions.m_208363_($$2, DensityFunctions.m_208264_(2.0)), $$3);
        return DensityFunctions.m_208380_(DensityFunctions.m_208363_($$5, $$4.m_208231_()));
    }

    private static DensityFunction m_224517_(Registry<DensityFunction> p_224518_) {
        DensityFunction $$1 = DensityFunctions.m_208368_(NoiseRouterData.m_209542_(Noises.f_189296_), 2.0, 1.0);
        DensityFunction $$2 = DensityFunctions.m_208315_($$1, NoiseRouterData.m_209542_(Noises.f_189294_), DensityFunctions.WeirdScaledSampler.RarityValueMapper.TYPE2);
        DensityFunction $$3 = DensityFunctions.m_208331_(NoiseRouterData.m_209542_(Noises.f_189295_), 0.0, Math.floorDiv(-64, 8), 8.0);
        DensityFunction $$4 = NoiseRouterData.m_224464_(p_224518_, f_209438_);
        DensityFunction $$5 = DensityFunctions.m_208293_($$3, DensityFunctions.m_208266_(-64, 320, 8.0, -40.0)).m_208229_();
        DensityFunction $$6 = DensityFunctions.m_208293_($$5, $$4).m_208231_();
        double $$7 = 0.083;
        DensityFunction $$8 = DensityFunctions.m_208293_($$2, DensityFunctions.m_208363_(DensityFunctions.m_208264_(0.083), $$4));
        return DensityFunctions.m_208382_($$8, $$6).m_208220_(-1.0, 1.0);
    }

    private static DensityFunction m_224471_(Registry<DensityFunction> p_224472_, DensityFunction p_224473_) {
        DensityFunction $$2 = NoiseRouterData.m_224464_(p_224472_, f_209439_);
        DensityFunction $$3 = NoiseRouterData.m_224464_(p_224472_, f_209463_);
        DensityFunction $$4 = DensityFunctions.m_208324_(NoiseRouterData.m_209542_(Noises.f_189245_), 8.0);
        DensityFunction $$5 = DensityFunctions.m_208363_(DensityFunctions.m_208264_(4.0), $$4.m_208230_());
        DensityFunction $$6 = DensityFunctions.m_208324_(NoiseRouterData.m_209542_(Noises.f_189246_), 0.6666666666666666);
        DensityFunction $$7 = DensityFunctions.m_208293_(DensityFunctions.m_208293_(DensityFunctions.m_208264_(0.27), $$6).m_208220_(-1.0, 1.0), DensityFunctions.m_208293_(DensityFunctions.m_208264_(1.5), DensityFunctions.m_208363_(DensityFunctions.m_208264_(-0.64), p_224473_)).m_208220_(0.0, 0.5));
        DensityFunction $$8 = DensityFunctions.m_208293_($$5, $$7);
        DensityFunction $$9 = DensityFunctions.m_208375_(DensityFunctions.m_208375_($$8, NoiseRouterData.m_224464_(p_224472_, f_209464_)), DensityFunctions.m_208293_($$2, $$3));
        DensityFunction $$10 = NoiseRouterData.m_224464_(p_224472_, f_209437_);
        DensityFunction $$11 = DensityFunctions.m_208287_($$10, -1000000.0, 0.03, DensityFunctions.m_208264_(-1000000.0), $$10);
        return DensityFunctions.m_208382_($$9, $$11);
    }

    private static DensityFunction m_224492_(DensityFunction p_224493_) {
        DensityFunction $$1 = DensityFunctions.m_208389_(p_224493_);
        return DensityFunctions.m_208363_(DensityFunctions.m_208281_($$1), DensityFunctions.m_208264_(0.64)).m_208234_();
    }

    protected static NoiseRouter m_224485_(Registry<DensityFunction> p_224486_, boolean p_224487_, boolean p_224488_) {
        DensityFunction $$3 = DensityFunctions.m_208324_(NoiseRouterData.m_209542_(Noises.f_189287_), 0.5);
        DensityFunction $$4 = DensityFunctions.m_208324_(NoiseRouterData.m_209542_(Noises.f_189288_), 0.67);
        DensityFunction $$5 = DensityFunctions.m_208324_(NoiseRouterData.m_209542_(Noises.f_189290_), 0.7142857142857143);
        DensityFunction $$6 = DensityFunctions.m_208322_(NoiseRouterData.m_209542_(Noises.f_189289_));
        DensityFunction $$7 = NoiseRouterData.m_224464_(p_224486_, f_209448_);
        DensityFunction $$8 = NoiseRouterData.m_224464_(p_224486_, f_209449_);
        DensityFunction $$9 = DensityFunctions.m_208296_($$7, $$8, 0.25, NoiseRouterData.m_209542_(p_224487_ ? Noises.f_189281_ : Noises.f_189269_));
        DensityFunction $$10 = DensityFunctions.m_208296_($$7, $$8, 0.25, NoiseRouterData.m_209542_(p_224487_ ? Noises.f_189282_ : Noises.f_189278_));
        DensityFunction $$11 = NoiseRouterData.m_224464_(p_224486_, p_224487_ ? f_209459_ : (p_224488_ ? f_224422_ : f_209454_));
        DensityFunction $$12 = NoiseRouterData.m_224464_(p_224486_, p_224487_ ? f_209460_ : (p_224488_ ? f_224424_ : f_209455_));
        DensityFunction $$13 = NoiseRouterData.m_212271_(DensityFunctions.m_208373_($$11), $$12);
        DensityFunction $$14 = NoiseRouterData.m_224464_(p_224486_, p_224487_ ? f_209461_ : (p_224488_ ? f_224425_ : f_209456_));
        DensityFunction $$15 = DensityFunctions.m_208375_($$14, DensityFunctions.m_208363_(DensityFunctions.m_208264_(5.0), NoiseRouterData.m_224464_(p_224486_, f_209464_)));
        DensityFunction $$16 = DensityFunctions.m_208287_($$14, -1000000.0, 1.5625, $$15, NoiseRouterData.m_224471_(p_224486_, $$14));
        DensityFunction $$17 = DensityFunctions.m_208375_(NoiseRouterData.m_224492_(NoiseRouterData.m_224489_(p_224488_, $$16)), NoiseRouterData.m_224464_(p_224486_, f_209465_));
        DensityFunction $$18 = NoiseRouterData.m_224464_(p_224486_, f_209447_);
        int $$19 = Stream.of(OreVeinifier.VeinType.values()).mapToInt(p_224495_ -> p_224495_.f_209674_).min().orElse(-DimensionType.f_156653_ * 2);
        int $$20 = Stream.of(OreVeinifier.VeinType.values()).mapToInt(p_224457_ -> p_224457_.f_209675_).max().orElse(-DimensionType.f_156653_ * 2);
        DensityFunction $$21 = NoiseRouterData.m_209471_($$18, DensityFunctions.m_208368_(NoiseRouterData.m_209542_(Noises.f_189247_), 1.5, 1.5), $$19, $$20, 0);
        float $$22 = 4.0f;
        DensityFunction $$23 = NoiseRouterData.m_209471_($$18, DensityFunctions.m_208368_(NoiseRouterData.m_209542_(Noises.f_189248_), 4.0, 4.0), $$19, $$20, 0).m_208229_();
        DensityFunction $$24 = NoiseRouterData.m_209471_($$18, DensityFunctions.m_208368_(NoiseRouterData.m_209542_(Noises.f_189249_), 4.0, 4.0), $$19, $$20, 0).m_208229_();
        DensityFunction $$25 = DensityFunctions.m_208293_(DensityFunctions.m_208264_(-0.08f), DensityFunctions.m_208382_($$23, $$24));
        DensityFunction $$26 = DensityFunctions.m_208322_(NoiseRouterData.m_209542_(Noises.f_189250_));
        return new NoiseRouter($$3, $$4, $$5, $$6, $$9, $$10, NoiseRouterData.m_224464_(p_224486_, p_224487_ ? f_209457_ : f_209451_), NoiseRouterData.m_224464_(p_224486_, p_224487_ ? f_209458_ : f_209452_), $$12, NoiseRouterData.m_224464_(p_224486_, f_209453_), NoiseRouterData.m_224489_(p_224488_, DensityFunctions.m_208293_($$13, DensityFunctions.m_208264_(-0.703125)).m_208220_(-64.0, 64.0)), $$17, $$21, $$25, $$26);
    }

    private static NoiseRouter m_224502_(Registry<DensityFunction> p_224503_, DensityFunction p_224504_) {
        DensityFunction $$2 = NoiseRouterData.m_224464_(p_224503_, f_209448_);
        DensityFunction $$3 = NoiseRouterData.m_224464_(p_224503_, f_209449_);
        DensityFunction $$4 = DensityFunctions.m_208296_($$2, $$3, 0.25, NoiseRouterData.m_209542_(Noises.f_189269_));
        DensityFunction $$5 = DensityFunctions.m_208296_($$2, $$3, 0.25, NoiseRouterData.m_209542_(Noises.f_189278_));
        DensityFunction $$6 = NoiseRouterData.m_224492_(p_224504_);
        return new NoiseRouter(DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), $$4, $$5, DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), $$6, DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_());
    }

    private static DensityFunction m_224489_(boolean p_224490_, DensityFunction p_224491_) {
        return NoiseRouterData.m_224443_(p_224491_, -64, 384, p_224490_ ? 16 : 80, p_224490_ ? 0 : 64, -0.078125, 0, 24, p_224490_ ? 0.4 : 0.1171875);
    }

    private static DensityFunction m_224460_(Registry<DensityFunction> p_224461_, int p_224462_, int p_224463_) {
        return NoiseRouterData.m_224443_(NoiseRouterData.m_224464_(p_224461_, f_224434_), p_224462_, p_224463_, 24, 0, 0.9375, -8, 24, 2.5);
    }

    private static DensityFunction m_224439_(DensityFunction p_224440_, int p_224441_, int p_224442_) {
        return NoiseRouterData.m_224443_(p_224440_, p_224441_, p_224442_, 72, -184, -23.4375, 4, 32, -0.234375);
    }

    protected static NoiseRouter m_224496_(Registry<DensityFunction> p_224497_) {
        return NoiseRouterData.m_224502_(p_224497_, NoiseRouterData.m_224460_(p_224497_, 0, 128));
    }

    protected static NoiseRouter m_224507_(Registry<DensityFunction> p_224508_) {
        return NoiseRouterData.m_224502_(p_224508_, NoiseRouterData.m_224460_(p_224508_, -64, 192));
    }

    protected static NoiseRouter m_224509_(Registry<DensityFunction> p_224510_) {
        return NoiseRouterData.m_224502_(p_224510_, NoiseRouterData.m_224439_(NoiseRouterData.m_224464_(p_224510_, f_224418_), 0, 256));
    }

    private static DensityFunction m_224505_(DensityFunction p_224506_) {
        return NoiseRouterData.m_224439_(p_224506_, 0, 128);
    }

    protected static NoiseRouter m_224511_(Registry<DensityFunction> p_224512_) {
        DensityFunction $$1 = DensityFunctions.m_208373_(DensityFunctions.m_208271_(0L));
        DensityFunction $$2 = NoiseRouterData.m_224492_(NoiseRouterData.m_224505_(NoiseRouterData.m_224464_(p_224512_, f_209462_)));
        return new NoiseRouter(DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), $$1, DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), NoiseRouterData.m_224505_(DensityFunctions.m_208293_($$1, DensityFunctions.m_208264_(-0.703125))), $$2, DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_());
    }

    protected static NoiseRouter m_238384_() {
        return new NoiseRouter(DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_(), DensityFunctions.m_208263_());
    }

    private static DensityFunction m_224453_(DensityFunction p_224454_, DensityFunction p_224455_) {
        DensityFunction $$2 = DensityFunctions.m_208301_(DensityFunctions.m_208360_(), p_224455_, p_224454_);
        return DensityFunctions.m_208361_(DensityFunctions.m_208373_($$2));
    }

    private static DensityFunction m_212271_(DensityFunction p_212272_, DensityFunction p_212273_) {
        DensityFunction $$2 = DensityFunctions.m_208363_(p_212273_, p_212272_);
        return DensityFunctions.m_208363_(DensityFunctions.m_208264_(4.0), $$2.m_208233_());
    }

    private static DensityFunction m_209471_(DensityFunction p_209472_, DensityFunction p_209473_, int p_209474_, int p_209475_, int p_209476_) {
        return DensityFunctions.m_208281_(DensityFunctions.m_208287_(p_209472_, p_209474_, p_209475_ + 1, p_209473_, DensityFunctions.m_208264_(p_209476_)));
    }

    private static DensityFunction m_224443_(DensityFunction p_224444_, int p_224445_, int p_224446_, int p_224447_, int p_224448_, double p_224449_, int p_224450_, int p_224451_, double p_224452_) {
        DensityFunction $$9 = p_224444_;
        DensityFunction $$10 = DensityFunctions.m_208266_(p_224445_ + p_224446_ - p_224447_, p_224445_ + p_224446_ - p_224448_, 1.0, 0.0);
        $$9 = DensityFunctions.m_224030_($$10, p_224449_, $$9);
        DensityFunction $$11 = DensityFunctions.m_208266_(p_224445_ + p_224450_, p_224445_ + p_224451_, 0.0, 1.0);
        $$9 = DensityFunctions.m_224030_($$11, p_224452_, $$9);
        return $$9;
    }

    protected static final class QuantizedSpaghettiRarity {
        protected QuantizedSpaghettiRarity() {
        }

        protected static double m_209563_(double p_209564_) {
            if (p_209564_ < -0.75) {
                return 0.5;
            }
            if (p_209564_ < -0.5) {
                return 0.75;
            }
            if (p_209564_ < 0.5) {
                return 1.0;
            }
            if (p_209564_ < 0.75) {
                return 2.0;
            }
            return 3.0;
        }

        protected static double m_209565_(double p_209566_) {
            if (p_209566_ < -0.5) {
                return 0.75;
            }
            if (p_209566_ < 0.0) {
                return 1.0;
            }
            if (p_209566_ < 0.5) {
                return 1.5;
            }
            return 2.0;
        }
    }
}

