/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.world.level.biome;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.SharedConstants;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.CubicSpline;
import net.minecraft.util.ToFloatFunction;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseRouterData;

public final class OverworldBiomeBuilder {
    private static final float f_187134_ = 0.05f;
    private static final float f_187135_ = 0.26666668f;
    public static final float f_187127_ = 0.4f;
    private static final float f_187136_ = 0.93333334f;
    private static final float f_187137_ = 0.1f;
    public static final float f_187128_ = 0.56666666f;
    private static final float f_187138_ = 0.7666667f;
    public static final float f_187129_ = -0.11f;
    public static final float f_187130_ = 0.03f;
    public static final float f_187131_ = 0.3f;
    public static final float f_187132_ = -0.78f;
    public static final float f_187133_ = -0.375f;
    private static final float f_220663_ = -0.225f;
    private static final float f_220664_ = 0.9f;
    private final Climate.Parameter f_187139_ = Climate.Parameter.m_186822_(-1.0f, 1.0f);
    private final Climate.Parameter[] f_187140_ = new Climate.Parameter[]{Climate.Parameter.m_186822_(-1.0f, -0.45f), Climate.Parameter.m_186822_(-0.45f, -0.15f), Climate.Parameter.m_186822_(-0.15f, 0.2f), Climate.Parameter.m_186822_(0.2f, 0.55f), Climate.Parameter.m_186822_(0.55f, 1.0f)};
    private final Climate.Parameter[] f_187141_ = new Climate.Parameter[]{Climate.Parameter.m_186822_(-1.0f, -0.35f), Climate.Parameter.m_186822_(-0.35f, -0.1f), Climate.Parameter.m_186822_(-0.1f, 0.1f), Climate.Parameter.m_186822_(0.1f, 0.3f), Climate.Parameter.m_186822_(0.3f, 1.0f)};
    private final Climate.Parameter[] f_187142_ = new Climate.Parameter[]{Climate.Parameter.m_186822_(-1.0f, -0.78f), Climate.Parameter.m_186822_(-0.78f, -0.375f), Climate.Parameter.m_186822_(-0.375f, -0.2225f), Climate.Parameter.m_186822_(-0.2225f, 0.05f), Climate.Parameter.m_186822_(0.05f, 0.45f), Climate.Parameter.m_186822_(0.45f, 0.55f), Climate.Parameter.m_186822_(0.55f, 1.0f)};
    private final Climate.Parameter f_187143_ = this.f_187140_[0];
    private final Climate.Parameter f_187144_ = Climate.Parameter.m_186829_(this.f_187140_[1], this.f_187140_[4]);
    private final Climate.Parameter f_187145_ = Climate.Parameter.m_186822_(-1.2f, -1.05f);
    private final Climate.Parameter f_187146_ = Climate.Parameter.m_186822_(-1.05f, -0.455f);
    private final Climate.Parameter f_187147_ = Climate.Parameter.m_186822_(-0.455f, -0.19f);
    private final Climate.Parameter f_187148_ = Climate.Parameter.m_186822_(-0.19f, -0.11f);
    private final Climate.Parameter f_187149_ = Climate.Parameter.m_186822_(-0.11f, 0.55f);
    private final Climate.Parameter f_187150_ = Climate.Parameter.m_186822_(-0.11f, 0.03f);
    private final Climate.Parameter f_187151_ = Climate.Parameter.m_186822_(0.03f, 0.3f);
    private final Climate.Parameter f_187152_ = Climate.Parameter.m_186822_(0.3f, 1.0f);
    private final ResourceKey<Biome>[][] f_187121_ = new ResourceKey[][]{{Biomes.f_48172_, Biomes.f_48171_, Biomes.f_48225_, Biomes.f_48170_, Biomes.f_48166_}, {Biomes.f_48211_, Biomes.f_48168_, Biomes.f_48174_, Biomes.f_48167_, Biomes.f_48166_}};
    private final ResourceKey<Biome>[][] f_187122_ = new ResourceKey[][]{{Biomes.f_186761_, Biomes.f_186761_, Biomes.f_186761_, Biomes.f_48152_, Biomes.f_48206_}, {Biomes.f_48202_, Biomes.f_48202_, Biomes.f_48205_, Biomes.f_48206_, Biomes.f_186764_}, {Biomes.f_48179_, Biomes.f_48202_, Biomes.f_48205_, Biomes.f_48149_, Biomes.f_48151_}, {Biomes.f_48157_, Biomes.f_48157_, Biomes.f_48205_, Biomes.f_48222_, Biomes.f_48222_}, {Biomes.f_48203_, Biomes.f_48203_, Biomes.f_48203_, Biomes.f_48203_, Biomes.f_48203_}};
    private final ResourceKey<Biome>[][] f_187123_ = new ResourceKey[][]{{Biomes.f_48182_, null, Biomes.f_48152_, null, null}, {null, null, null, null, Biomes.f_186763_}, {Biomes.f_48176_, null, null, Biomes.f_186762_, null}, {null, null, Biomes.f_48202_, Biomes.f_186769_, Biomes.f_48197_}, {null, null, null, null, null}};
    private final ResourceKey<Biome>[][] f_187124_ = new ResourceKey[][]{{Biomes.f_186761_, Biomes.f_186761_, Biomes.f_186761_, Biomes.f_48152_, Biomes.f_48152_}, {Biomes.f_186754_, Biomes.f_186754_, Biomes.f_48205_, Biomes.f_48206_, Biomes.f_186764_}, {Biomes.f_186754_, Biomes.f_186754_, Biomes.f_186754_, Biomes.f_186754_, Biomes.f_48151_}, {Biomes.f_48158_, Biomes.f_48158_, Biomes.f_48205_, Biomes.f_48205_, Biomes.f_48222_}, {Biomes.f_48159_, Biomes.f_48159_, Biomes.f_48159_, Biomes.f_186753_, Biomes.f_186753_}};
    private final ResourceKey<Biome>[][] f_187125_ = new ResourceKey[][]{{Biomes.f_48182_, null, null, null, null}, {null, null, Biomes.f_186754_, Biomes.f_186754_, Biomes.f_186763_}, {null, null, Biomes.f_48205_, Biomes.f_48149_, null}, {null, null, null, null, null}, {Biomes.f_48194_, Biomes.f_48194_, null, null, null}};
    private final ResourceKey<Biome>[][] f_201989_ = new ResourceKey[][]{{Biomes.f_186766_, Biomes.f_186766_, Biomes.f_186765_, Biomes.f_186767_, Biomes.f_186767_}, {Biomes.f_186766_, Biomes.f_186766_, Biomes.f_186765_, Biomes.f_186767_, Biomes.f_186767_}, {Biomes.f_186765_, Biomes.f_186765_, Biomes.f_186765_, Biomes.f_186767_, Biomes.f_186767_}, {null, null, null, null, null}, {null, null, null, null, null}};

    public List<Climate.ParameterPoint> m_187154_() {
        Climate.Parameter $$0 = Climate.Parameter.m_186820_(0.0f);
        float $$1 = 0.16f;
        return List.of(new Climate.ParameterPoint(this.f_187139_, this.f_187139_, Climate.Parameter.m_186829_(this.f_187149_, this.f_187139_), this.f_187139_, $$0, Climate.Parameter.m_186822_(-1.0f, -0.16f), 0L), new Climate.ParameterPoint(this.f_187139_, this.f_187139_, Climate.Parameter.m_186829_(this.f_187149_, this.f_187139_), this.f_187139_, $$0, Climate.Parameter.m_186822_(0.16f, 1.0f), 0L));
    }

    protected void m_187175_(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187176_) {
        if (SharedConstants.f_183698_) {
            CubicSpline $$8;
            DensityFunctions.Spline.Coordinate $$1 = new DensityFunctions.Spline.Coordinate(BuiltinRegistries.f_211085_.m_206081_(NoiseRouterData.f_209451_));
            DensityFunctions.Spline.Coordinate $$2 = new DensityFunctions.Spline.Coordinate(BuiltinRegistries.f_211085_.m_206081_(NoiseRouterData.f_209452_));
            DensityFunctions.Spline.Coordinate $$3 = new DensityFunctions.Spline.Coordinate(BuiltinRegistries.f_211085_.m_206081_(NoiseRouterData.f_224429_));
            p_187176_.accept((Pair<Climate.ParameterPoint, ResourceKey<Biome>>)Pair.of((Object)Climate.m_186798_(this.f_187139_, this.f_187139_, this.f_187139_, this.f_187139_, Climate.Parameter.m_186820_(0.0f), this.f_187139_, 0.01f), Biomes.f_48202_));
            CubicSpline $$4 = TerrainProvider.m_236595_($$2, $$3, -0.15f, 0.0f, 0.0f, 0.1f, 0.0f, -0.03f, false, false, ToFloatFunction.f_216471_);
            if ($$4 instanceof CubicSpline.Multipoint) {
                CubicSpline.Multipoint $$5 = (CubicSpline.Multipoint)$$4;
                ResourceKey<Biome> $$6 = Biomes.f_48203_;
                for (float $$7 : $$5.f_184320_()) {
                    p_187176_.accept((Pair<Climate.ParameterPoint, ResourceKey<Biome>>)Pair.of((Object)Climate.m_186798_(this.f_187139_, this.f_187139_, this.f_187139_, Climate.Parameter.m_186820_($$7), Climate.Parameter.m_186820_(0.0f), this.f_187139_, 0.0f), $$6));
                    $$6 = $$6 == Biomes.f_48203_ ? Biomes.f_48159_ : Biomes.f_48203_;
                }
            }
            if (($$8 = TerrainProvider.m_236635_($$1, $$2, $$3, false)) instanceof CubicSpline.Multipoint) {
                CubicSpline.Multipoint $$9 = (CubicSpline.Multipoint)$$8;
                for (float $$10 : $$9.f_184320_()) {
                    p_187176_.accept((Pair<Climate.ParameterPoint, ResourceKey<Biome>>)Pair.of((Object)Climate.m_186798_(this.f_187139_, this.f_187139_, Climate.Parameter.m_186820_($$10), this.f_187139_, Climate.Parameter.m_186820_(0.0f), this.f_187139_, 0.0f), Biomes.f_48152_));
                }
            }
            return;
        }
        this.m_187195_(p_187176_);
        this.m_187215_(p_187176_);
        this.m_187226_(p_187176_);
    }

    private void m_187195_(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187196_) {
        this.m_187180_(p_187196_, this.f_187139_, this.f_187139_, this.f_187145_, this.f_187139_, this.f_187139_, 0.0f, Biomes.f_48215_);
        for (int $$1 = 0; $$1 < this.f_187140_.length; ++$$1) {
            Climate.Parameter $$2 = this.f_187140_[$$1];
            this.m_187180_(p_187196_, $$2, this.f_187139_, this.f_187146_, this.f_187139_, this.f_187139_, 0.0f, this.f_187121_[0][$$1]);
            this.m_187180_(p_187196_, $$2, this.f_187139_, this.f_187147_, this.f_187139_, this.f_187139_, 0.0f, this.f_187121_[1][$$1]);
        }
    }

    private void m_187215_(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187216_) {
        this.m_187217_(p_187216_, Climate.Parameter.m_186822_(-1.0f, -0.93333334f));
        this.m_187197_(p_187216_, Climate.Parameter.m_186822_(-0.93333334f, -0.7666667f));
        this.m_187177_(p_187216_, Climate.Parameter.m_186822_(-0.7666667f, -0.56666666f));
        this.m_187197_(p_187216_, Climate.Parameter.m_186822_(-0.56666666f, -0.4f));
        this.m_187217_(p_187216_, Climate.Parameter.m_186822_(-0.4f, -0.26666668f));
        this.m_187228_(p_187216_, Climate.Parameter.m_186822_(-0.26666668f, -0.05f));
        this.m_187237_(p_187216_, Climate.Parameter.m_186822_(-0.05f, 0.05f));
        this.m_187228_(p_187216_, Climate.Parameter.m_186822_(0.05f, 0.26666668f));
        this.m_187217_(p_187216_, Climate.Parameter.m_186822_(0.26666668f, 0.4f));
        this.m_187197_(p_187216_, Climate.Parameter.m_186822_(0.4f, 0.56666666f));
        this.m_187177_(p_187216_, Climate.Parameter.m_186822_(0.56666666f, 0.7666667f));
        this.m_187197_(p_187216_, Climate.Parameter.m_186822_(0.7666667f, 0.93333334f));
        this.m_187217_(p_187216_, Climate.Parameter.m_186822_(0.93333334f, 1.0f));
    }

    private void m_187177_(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187178_, Climate.Parameter p_187179_) {
        for (int $$2 = 0; $$2 < this.f_187140_.length; ++$$2) {
            Climate.Parameter $$3 = this.f_187140_[$$2];
            for (int $$4 = 0; $$4 < this.f_187141_.length; ++$$4) {
                Climate.Parameter $$5 = this.f_187141_[$$4];
                ResourceKey<Biome> $$6 = this.m_187163_($$2, $$4, p_187179_);
                ResourceKey<Biome> $$7 = this.m_187191_($$2, $$4, p_187179_);
                ResourceKey<Biome> $$8 = this.m_187211_($$2, $$4, p_187179_);
                ResourceKey<Biome> $$9 = this.m_187233_($$2, $$4, p_187179_);
                ResourceKey<Biome> $$10 = this.m_202001_($$2, $$4, p_187179_);
                ResourceKey<Biome> $$11 = this.m_201990_($$2, $$4, p_187179_, $$10);
                ResourceKey<Biome> $$12 = this.m_187240_($$2, $$4, p_187179_);
                this.m_187180_(p_187178_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187148_, this.f_187152_), this.f_187142_[0], p_187179_, 0.0f, $$12);
                this.m_187180_(p_187178_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187148_, this.f_187150_), this.f_187142_[1], p_187179_, 0.0f, $$8);
                this.m_187180_(p_187178_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187151_, this.f_187152_), this.f_187142_[1], p_187179_, 0.0f, $$12);
                this.m_187180_(p_187178_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187148_, this.f_187150_), Climate.Parameter.m_186829_(this.f_187142_[2], this.f_187142_[3]), p_187179_, 0.0f, $$6);
                this.m_187180_(p_187178_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187151_, this.f_187152_), this.f_187142_[2], p_187179_, 0.0f, $$9);
                this.m_187180_(p_187178_, $$3, $$5, this.f_187151_, this.f_187142_[3], p_187179_, 0.0f, $$7);
                this.m_187180_(p_187178_, $$3, $$5, this.f_187152_, this.f_187142_[3], p_187179_, 0.0f, $$9);
                this.m_187180_(p_187178_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187148_, this.f_187152_), this.f_187142_[4], p_187179_, 0.0f, $$6);
                this.m_187180_(p_187178_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187148_, this.f_187150_), this.f_187142_[5], p_187179_, 0.0f, $$11);
                this.m_187180_(p_187178_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187151_, this.f_187152_), this.f_187142_[5], p_187179_, 0.0f, $$10);
                this.m_187180_(p_187178_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187148_, this.f_187152_), this.f_187142_[6], p_187179_, 0.0f, $$6);
            }
        }
    }

    private void m_187197_(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187198_, Climate.Parameter p_187199_) {
        for (int $$2 = 0; $$2 < this.f_187140_.length; ++$$2) {
            Climate.Parameter $$3 = this.f_187140_[$$2];
            for (int $$4 = 0; $$4 < this.f_187141_.length; ++$$4) {
                Climate.Parameter $$5 = this.f_187141_[$$4];
                ResourceKey<Biome> $$6 = this.m_187163_($$2, $$4, p_187199_);
                ResourceKey<Biome> $$7 = this.m_187191_($$2, $$4, p_187199_);
                ResourceKey<Biome> $$8 = this.m_187211_($$2, $$4, p_187199_);
                ResourceKey<Biome> $$9 = this.m_187233_($$2, $$4, p_187199_);
                ResourceKey<Biome> $$10 = this.m_202001_($$2, $$4, p_187199_);
                ResourceKey<Biome> $$11 = this.m_201990_($$2, $$4, p_187199_, $$6);
                ResourceKey<Biome> $$12 = this.m_187244_($$2, $$4, p_187199_);
                ResourceKey<Biome> $$13 = this.m_187240_($$2, $$4, p_187199_);
                this.m_187180_(p_187198_, $$3, $$5, this.f_187148_, Climate.Parameter.m_186829_(this.f_187142_[0], this.f_187142_[1]), p_187199_, 0.0f, $$6);
                this.m_187180_(p_187198_, $$3, $$5, this.f_187150_, this.f_187142_[0], p_187199_, 0.0f, $$12);
                this.m_187180_(p_187198_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187151_, this.f_187152_), this.f_187142_[0], p_187199_, 0.0f, $$13);
                this.m_187180_(p_187198_, $$3, $$5, this.f_187150_, this.f_187142_[1], p_187199_, 0.0f, $$8);
                this.m_187180_(p_187198_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187151_, this.f_187152_), this.f_187142_[1], p_187199_, 0.0f, $$12);
                this.m_187180_(p_187198_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187148_, this.f_187150_), Climate.Parameter.m_186829_(this.f_187142_[2], this.f_187142_[3]), p_187199_, 0.0f, $$6);
                this.m_187180_(p_187198_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187151_, this.f_187152_), this.f_187142_[2], p_187199_, 0.0f, $$9);
                this.m_187180_(p_187198_, $$3, $$5, this.f_187151_, this.f_187142_[3], p_187199_, 0.0f, $$7);
                this.m_187180_(p_187198_, $$3, $$5, this.f_187152_, this.f_187142_[3], p_187199_, 0.0f, $$9);
                this.m_187180_(p_187198_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187148_, this.f_187152_), this.f_187142_[4], p_187199_, 0.0f, $$6);
                this.m_187180_(p_187198_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187148_, this.f_187150_), this.f_187142_[5], p_187199_, 0.0f, $$11);
                this.m_187180_(p_187198_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187151_, this.f_187152_), this.f_187142_[5], p_187199_, 0.0f, $$10);
                this.m_187180_(p_187198_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187148_, this.f_187152_), this.f_187142_[6], p_187199_, 0.0f, $$6);
            }
        }
    }

    private void m_187217_(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187218_, Climate.Parameter p_187219_) {
        this.m_187180_(p_187218_, this.f_187139_, this.f_187139_, this.f_187148_, Climate.Parameter.m_186829_(this.f_187142_[0], this.f_187142_[2]), p_187219_, 0.0f, Biomes.f_186760_);
        this.m_187180_(p_187218_, Climate.Parameter.m_186829_(this.f_187140_[1], this.f_187140_[2]), this.f_187139_, Climate.Parameter.m_186829_(this.f_187150_, this.f_187152_), this.f_187142_[6], p_187219_, 0.0f, Biomes.f_48207_);
        this.m_187180_(p_187218_, Climate.Parameter.m_186829_(this.f_187140_[3], this.f_187140_[4]), this.f_187139_, Climate.Parameter.m_186829_(this.f_187150_, this.f_187152_), this.f_187142_[6], p_187219_, 0.0f, Biomes.f_220595_);
        for (int $$2 = 0; $$2 < this.f_187140_.length; ++$$2) {
            Climate.Parameter $$3 = this.f_187140_[$$2];
            for (int $$4 = 0; $$4 < this.f_187141_.length; ++$$4) {
                Climate.Parameter $$5 = this.f_187141_[$$4];
                ResourceKey<Biome> $$6 = this.m_187163_($$2, $$4, p_187219_);
                ResourceKey<Biome> $$7 = this.m_187191_($$2, $$4, p_187219_);
                ResourceKey<Biome> $$8 = this.m_187211_($$2, $$4, p_187219_);
                ResourceKey<Biome> $$9 = this.m_202001_($$2, $$4, p_187219_);
                ResourceKey<Biome> $$10 = this.m_187233_($$2, $$4, p_187219_);
                ResourceKey<Biome> $$11 = this.m_187160_($$2, $$4);
                ResourceKey<Biome> $$12 = this.m_201990_($$2, $$4, p_187219_, $$6);
                ResourceKey<Biome> $$13 = this.m_187222_($$2, $$4, p_187219_);
                ResourceKey<Biome> $$14 = this.m_187244_($$2, $$4, p_187219_);
                this.m_187180_(p_187218_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187150_, this.f_187152_), this.f_187142_[0], p_187219_, 0.0f, $$14);
                this.m_187180_(p_187218_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187150_, this.f_187151_), this.f_187142_[1], p_187219_, 0.0f, $$8);
                this.m_187180_(p_187218_, $$3, $$5, this.f_187152_, this.f_187142_[1], p_187219_, 0.0f, $$2 == 0 ? $$14 : $$10);
                this.m_187180_(p_187218_, $$3, $$5, this.f_187150_, this.f_187142_[2], p_187219_, 0.0f, $$6);
                this.m_187180_(p_187218_, $$3, $$5, this.f_187151_, this.f_187142_[2], p_187219_, 0.0f, $$7);
                this.m_187180_(p_187218_, $$3, $$5, this.f_187152_, this.f_187142_[2], p_187219_, 0.0f, $$10);
                this.m_187180_(p_187218_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187148_, this.f_187150_), this.f_187142_[3], p_187219_, 0.0f, $$6);
                this.m_187180_(p_187218_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187151_, this.f_187152_), this.f_187142_[3], p_187219_, 0.0f, $$7);
                if (p_187219_.f_186814_() < 0L) {
                    this.m_187180_(p_187218_, $$3, $$5, this.f_187148_, this.f_187142_[4], p_187219_, 0.0f, $$11);
                    this.m_187180_(p_187218_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187150_, this.f_187152_), this.f_187142_[4], p_187219_, 0.0f, $$6);
                } else {
                    this.m_187180_(p_187218_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187148_, this.f_187152_), this.f_187142_[4], p_187219_, 0.0f, $$6);
                }
                this.m_187180_(p_187218_, $$3, $$5, this.f_187148_, this.f_187142_[5], p_187219_, 0.0f, $$13);
                this.m_187180_(p_187218_, $$3, $$5, this.f_187150_, this.f_187142_[5], p_187219_, 0.0f, $$12);
                this.m_187180_(p_187218_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187151_, this.f_187152_), this.f_187142_[5], p_187219_, 0.0f, $$9);
                if (p_187219_.f_186814_() < 0L) {
                    this.m_187180_(p_187218_, $$3, $$5, this.f_187148_, this.f_187142_[6], p_187219_, 0.0f, $$11);
                } else {
                    this.m_187180_(p_187218_, $$3, $$5, this.f_187148_, this.f_187142_[6], p_187219_, 0.0f, $$6);
                }
                if ($$2 != 0) continue;
                this.m_187180_(p_187218_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187150_, this.f_187152_), this.f_187142_[6], p_187219_, 0.0f, $$6);
            }
        }
    }

    private void m_187228_(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187229_, Climate.Parameter p_187230_) {
        this.m_187180_(p_187229_, this.f_187139_, this.f_187139_, this.f_187148_, Climate.Parameter.m_186829_(this.f_187142_[0], this.f_187142_[2]), p_187230_, 0.0f, Biomes.f_186760_);
        this.m_187180_(p_187229_, Climate.Parameter.m_186829_(this.f_187140_[1], this.f_187140_[2]), this.f_187139_, Climate.Parameter.m_186829_(this.f_187150_, this.f_187152_), this.f_187142_[6], p_187230_, 0.0f, Biomes.f_48207_);
        this.m_187180_(p_187229_, Climate.Parameter.m_186829_(this.f_187140_[3], this.f_187140_[4]), this.f_187139_, Climate.Parameter.m_186829_(this.f_187150_, this.f_187152_), this.f_187142_[6], p_187230_, 0.0f, Biomes.f_220595_);
        for (int $$2 = 0; $$2 < this.f_187140_.length; ++$$2) {
            Climate.Parameter $$3 = this.f_187140_[$$2];
            for (int $$4 = 0; $$4 < this.f_187141_.length; ++$$4) {
                Climate.Parameter $$5 = this.f_187141_[$$4];
                ResourceKey<Biome> $$6 = this.m_187163_($$2, $$4, p_187230_);
                ResourceKey<Biome> $$7 = this.m_187191_($$2, $$4, p_187230_);
                ResourceKey<Biome> $$8 = this.m_187211_($$2, $$4, p_187230_);
                ResourceKey<Biome> $$9 = this.m_187160_($$2, $$4);
                ResourceKey<Biome> $$10 = this.m_201990_($$2, $$4, p_187230_, $$6);
                ResourceKey<Biome> $$11 = this.m_187222_($$2, $$4, p_187230_);
                this.m_187180_(p_187229_, $$3, $$5, this.f_187150_, Climate.Parameter.m_186829_(this.f_187142_[0], this.f_187142_[1]), p_187230_, 0.0f, $$7);
                this.m_187180_(p_187229_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187151_, this.f_187152_), Climate.Parameter.m_186829_(this.f_187142_[0], this.f_187142_[1]), p_187230_, 0.0f, $$8);
                this.m_187180_(p_187229_, $$3, $$5, this.f_187150_, Climate.Parameter.m_186829_(this.f_187142_[2], this.f_187142_[3]), p_187230_, 0.0f, $$6);
                this.m_187180_(p_187229_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187151_, this.f_187152_), Climate.Parameter.m_186829_(this.f_187142_[2], this.f_187142_[3]), p_187230_, 0.0f, $$7);
                this.m_187180_(p_187229_, $$3, $$5, this.f_187148_, Climate.Parameter.m_186829_(this.f_187142_[3], this.f_187142_[4]), p_187230_, 0.0f, $$9);
                this.m_187180_(p_187229_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187150_, this.f_187152_), this.f_187142_[4], p_187230_, 0.0f, $$6);
                this.m_187180_(p_187229_, $$3, $$5, this.f_187148_, this.f_187142_[5], p_187230_, 0.0f, $$11);
                this.m_187180_(p_187229_, $$3, $$5, this.f_187150_, this.f_187142_[5], p_187230_, 0.0f, $$10);
                this.m_187180_(p_187229_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187151_, this.f_187152_), this.f_187142_[5], p_187230_, 0.0f, $$6);
                this.m_187180_(p_187229_, $$3, $$5, this.f_187148_, this.f_187142_[6], p_187230_, 0.0f, $$9);
                if ($$2 != 0) continue;
                this.m_187180_(p_187229_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187150_, this.f_187152_), this.f_187142_[6], p_187230_, 0.0f, $$6);
            }
        }
    }

    private void m_187237_(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187238_, Climate.Parameter p_187239_) {
        this.m_187180_(p_187238_, this.f_187143_, this.f_187139_, this.f_187148_, Climate.Parameter.m_186829_(this.f_187142_[0], this.f_187142_[1]), p_187239_, 0.0f, p_187239_.f_186814_() < 0L ? Biomes.f_186760_ : Biomes.f_48212_);
        this.m_187180_(p_187238_, this.f_187144_, this.f_187139_, this.f_187148_, Climate.Parameter.m_186829_(this.f_187142_[0], this.f_187142_[1]), p_187239_, 0.0f, p_187239_.f_186814_() < 0L ? Biomes.f_186760_ : Biomes.f_48208_);
        this.m_187180_(p_187238_, this.f_187143_, this.f_187139_, this.f_187150_, Climate.Parameter.m_186829_(this.f_187142_[0], this.f_187142_[1]), p_187239_, 0.0f, Biomes.f_48212_);
        this.m_187180_(p_187238_, this.f_187144_, this.f_187139_, this.f_187150_, Climate.Parameter.m_186829_(this.f_187142_[0], this.f_187142_[1]), p_187239_, 0.0f, Biomes.f_48208_);
        this.m_187180_(p_187238_, this.f_187143_, this.f_187139_, Climate.Parameter.m_186829_(this.f_187148_, this.f_187152_), Climate.Parameter.m_186829_(this.f_187142_[2], this.f_187142_[5]), p_187239_, 0.0f, Biomes.f_48212_);
        this.m_187180_(p_187238_, this.f_187144_, this.f_187139_, Climate.Parameter.m_186829_(this.f_187148_, this.f_187152_), Climate.Parameter.m_186829_(this.f_187142_[2], this.f_187142_[5]), p_187239_, 0.0f, Biomes.f_48208_);
        this.m_187180_(p_187238_, this.f_187143_, this.f_187139_, this.f_187148_, this.f_187142_[6], p_187239_, 0.0f, Biomes.f_48212_);
        this.m_187180_(p_187238_, this.f_187144_, this.f_187139_, this.f_187148_, this.f_187142_[6], p_187239_, 0.0f, Biomes.f_48208_);
        this.m_187180_(p_187238_, Climate.Parameter.m_186829_(this.f_187140_[1], this.f_187140_[2]), this.f_187139_, Climate.Parameter.m_186829_(this.f_187149_, this.f_187152_), this.f_187142_[6], p_187239_, 0.0f, Biomes.f_48207_);
        this.m_187180_(p_187238_, Climate.Parameter.m_186829_(this.f_187140_[3], this.f_187140_[4]), this.f_187139_, Climate.Parameter.m_186829_(this.f_187149_, this.f_187152_), this.f_187142_[6], p_187239_, 0.0f, Biomes.f_220595_);
        this.m_187180_(p_187238_, this.f_187143_, this.f_187139_, Climate.Parameter.m_186829_(this.f_187149_, this.f_187152_), this.f_187142_[6], p_187239_, 0.0f, Biomes.f_48212_);
        for (int $$2 = 0; $$2 < this.f_187140_.length; ++$$2) {
            Climate.Parameter $$3 = this.f_187140_[$$2];
            for (int $$4 = 0; $$4 < this.f_187141_.length; ++$$4) {
                Climate.Parameter $$5 = this.f_187141_[$$4];
                ResourceKey<Biome> $$6 = this.m_187191_($$2, $$4, p_187239_);
                this.m_187180_(p_187238_, $$3, $$5, Climate.Parameter.m_186829_(this.f_187151_, this.f_187152_), Climate.Parameter.m_186829_(this.f_187142_[0], this.f_187142_[1]), p_187239_, 0.0f, $$6);
            }
        }
    }

    private void m_187226_(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187227_) {
        this.m_187200_(p_187227_, this.f_187139_, this.f_187139_, Climate.Parameter.m_186822_(0.8f, 1.0f), this.f_187139_, this.f_187139_, 0.0f, Biomes.f_151784_);
        this.m_187200_(p_187227_, this.f_187139_, Climate.Parameter.m_186822_(0.7f, 1.0f), this.f_187139_, this.f_187139_, this.f_187139_, 0.0f, Biomes.f_151785_);
        this.m_220668_(p_187227_, this.f_187139_, this.f_187139_, this.f_187139_, Climate.Parameter.m_186829_(this.f_187142_[0], this.f_187142_[1]), this.f_187139_, 0.0f, Biomes.f_220594_);
    }

    private ResourceKey<Biome> m_187163_(int p_187164_, int p_187165_, Climate.Parameter p_187166_) {
        if (p_187166_.f_186814_() < 0L) {
            return this.f_187122_[p_187164_][p_187165_];
        }
        ResourceKey<Biome> $$3 = this.f_187123_[p_187164_][p_187165_];
        return $$3 == null ? this.f_187122_[p_187164_][p_187165_] : $$3;
    }

    private ResourceKey<Biome> m_187191_(int p_187192_, int p_187193_, Climate.Parameter p_187194_) {
        return p_187192_ == 4 ? this.m_187172_(p_187193_, p_187194_) : this.m_187163_(p_187192_, p_187193_, p_187194_);
    }

    private ResourceKey<Biome> m_187211_(int p_187212_, int p_187213_, Climate.Parameter p_187214_) {
        return p_187212_ == 0 ? this.m_187244_(p_187212_, p_187213_, p_187214_) : this.m_187191_(p_187212_, p_187213_, p_187214_);
    }

    private ResourceKey<Biome> m_201990_(int p_201991_, int p_201992_, Climate.Parameter p_201993_, ResourceKey<Biome> p_201994_) {
        if (p_201991_ > 1 && p_201992_ < 4 && p_201993_.f_186814_() >= 0L) {
            return Biomes.f_186768_;
        }
        return p_201994_;
    }

    private ResourceKey<Biome> m_187222_(int p_187223_, int p_187224_, Climate.Parameter p_187225_) {
        ResourceKey<Biome> $$3 = p_187225_.f_186814_() >= 0L ? this.m_187163_(p_187223_, p_187224_, p_187225_) : this.m_187160_(p_187223_, p_187224_);
        return this.m_201990_(p_187223_, p_187224_, p_187225_, $$3);
    }

    private ResourceKey<Biome> m_187160_(int p_187161_, int p_187162_) {
        if (p_187161_ == 0) {
            return Biomes.f_48148_;
        }
        if (p_187161_ == 4) {
            return Biomes.f_48203_;
        }
        return Biomes.f_48217_;
    }

    private ResourceKey<Biome> m_187172_(int p_187173_, Climate.Parameter p_187174_) {
        if (p_187173_ < 2) {
            return p_187174_.f_186814_() < 0L ? Biomes.f_48159_ : Biomes.f_48194_;
        }
        if (p_187173_ < 3) {
            return Biomes.f_48159_;
        }
        return Biomes.f_186753_;
    }

    private ResourceKey<Biome> m_187233_(int p_187234_, int p_187235_, Climate.Parameter p_187236_) {
        if (p_187236_.f_186814_() < 0L) {
            return this.f_187124_[p_187234_][p_187235_];
        }
        ResourceKey<Biome> $$3 = this.f_187125_[p_187234_][p_187235_];
        return $$3 == null ? this.f_187124_[p_187234_][p_187235_] : $$3;
    }

    private ResourceKey<Biome> m_187240_(int p_187241_, int p_187242_, Climate.Parameter p_187243_) {
        if (p_187241_ <= 2) {
            return p_187243_.f_186814_() < 0L ? Biomes.f_186758_ : Biomes.f_186757_;
        }
        if (p_187241_ == 3) {
            return Biomes.f_186759_;
        }
        return this.m_187172_(p_187242_, p_187243_);
    }

    private ResourceKey<Biome> m_187244_(int p_187245_, int p_187246_, Climate.Parameter p_187247_) {
        if (p_187245_ >= 3) {
            return this.m_187233_(p_187245_, p_187246_, p_187247_);
        }
        if (p_187246_ <= 1) {
            return Biomes.f_186756_;
        }
        return Biomes.f_186755_;
    }

    private ResourceKey<Biome> m_202001_(int p_202002_, int p_202003_, Climate.Parameter p_202004_) {
        ResourceKey<Biome> $$3 = this.f_201989_[p_202002_][p_202003_];
        return $$3 == null ? this.m_187163_(p_202002_, p_202003_, p_202004_) : $$3;
    }

    private void m_187180_(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187181_, Climate.Parameter p_187182_, Climate.Parameter p_187183_, Climate.Parameter p_187184_, Climate.Parameter p_187185_, Climate.Parameter p_187186_, float p_187187_, ResourceKey<Biome> p_187188_) {
        p_187181_.accept((Pair<Climate.ParameterPoint, ResourceKey<Biome>>)Pair.of((Object)Climate.m_186798_(p_187182_, p_187183_, p_187184_, p_187185_, Climate.Parameter.m_186820_(0.0f), p_187186_, p_187187_), p_187188_));
        p_187181_.accept((Pair<Climate.ParameterPoint, ResourceKey<Biome>>)Pair.of((Object)Climate.m_186798_(p_187182_, p_187183_, p_187184_, p_187185_, Climate.Parameter.m_186820_(1.0f), p_187186_, p_187187_), p_187188_));
    }

    private void m_187200_(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187201_, Climate.Parameter p_187202_, Climate.Parameter p_187203_, Climate.Parameter p_187204_, Climate.Parameter p_187205_, Climate.Parameter p_187206_, float p_187207_, ResourceKey<Biome> p_187208_) {
        p_187201_.accept((Pair<Climate.ParameterPoint, ResourceKey<Biome>>)Pair.of((Object)Climate.m_186798_(p_187202_, p_187203_, p_187204_, p_187205_, Climate.Parameter.m_186822_(0.2f, 0.9f), p_187206_, p_187207_), p_187208_));
    }

    private void m_220668_(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_220669_, Climate.Parameter p_220670_, Climate.Parameter p_220671_, Climate.Parameter p_220672_, Climate.Parameter p_220673_, Climate.Parameter p_220674_, float p_220675_, ResourceKey<Biome> p_220676_) {
        p_220669_.accept((Pair<Climate.ParameterPoint, ResourceKey<Biome>>)Pair.of((Object)Climate.m_186798_(p_220670_, p_220671_, p_220672_, p_220673_, Climate.Parameter.m_186820_(1.1f), p_220674_, p_220675_), p_220676_));
    }

    public static boolean m_220665_(double p_220666_, double p_220667_) {
        return p_220666_ < (double)-0.225f && p_220667_ > (double)0.9f;
    }

    public static String m_187155_(double p_187156_) {
        if (p_187156_ < (double)NoiseRouterData.m_224435_(0.05f)) {
            return "Valley";
        }
        if (p_187156_ < (double)NoiseRouterData.m_224435_(0.26666668f)) {
            return "Low";
        }
        if (p_187156_ < (double)NoiseRouterData.m_224435_(0.4f)) {
            return "Mid";
        }
        if (p_187156_ < (double)NoiseRouterData.m_224435_(0.56666666f)) {
            return "High";
        }
        return "Peak";
    }

    public String m_187189_(double p_187190_) {
        double $$1 = Climate.m_186779_((float)p_187190_);
        if ($$1 < (double)this.f_187145_.f_186814_()) {
            return "Mushroom fields";
        }
        if ($$1 < (double)this.f_187146_.f_186814_()) {
            return "Deep ocean";
        }
        if ($$1 < (double)this.f_187147_.f_186814_()) {
            return "Ocean";
        }
        if ($$1 < (double)this.f_187148_.f_186814_()) {
            return "Coast";
        }
        if ($$1 < (double)this.f_187150_.f_186814_()) {
            return "Near inland";
        }
        if ($$1 < (double)this.f_187151_.f_186814_()) {
            return "Mid inland";
        }
        return "Far inland";
    }

    public String m_187209_(double p_187210_) {
        return OverworldBiomeBuilder.m_187157_(p_187210_, this.f_187142_);
    }

    public String m_187220_(double p_187221_) {
        return OverworldBiomeBuilder.m_187157_(p_187221_, this.f_187140_);
    }

    public String m_187231_(double p_187232_) {
        return OverworldBiomeBuilder.m_187157_(p_187232_, this.f_187141_);
    }

    private static String m_187157_(double p_187158_, Climate.Parameter[] p_187159_) {
        double $$2 = Climate.m_186779_((float)p_187158_);
        for (int $$3 = 0; $$3 < p_187159_.length; ++$$3) {
            if (!($$2 < (double)p_187159_[$$3].f_186814_())) continue;
            return "" + $$3;
        }
        return "?";
    }

    @VisibleForDebug
    public Climate.Parameter[] m_201995_() {
        return this.f_187140_;
    }

    @VisibleForDebug
    public Climate.Parameter[] m_201996_() {
        return this.f_187141_;
    }

    @VisibleForDebug
    public Climate.Parameter[] m_201997_() {
        return this.f_187142_;
    }

    @VisibleForDebug
    public Climate.Parameter[] m_201998_() {
        return new Climate.Parameter[]{this.f_187145_, this.f_187146_, this.f_187147_, this.f_187148_, this.f_187150_, this.f_187151_, this.f_187152_};
    }

    @VisibleForDebug
    public Climate.Parameter[] m_201999_() {
        return new Climate.Parameter[]{Climate.Parameter.m_186822_(-2.0f, NoiseRouterData.m_224435_(0.05f)), Climate.Parameter.m_186822_(NoiseRouterData.m_224435_(0.05f), NoiseRouterData.m_224435_(0.26666668f)), Climate.Parameter.m_186822_(NoiseRouterData.m_224435_(0.26666668f), NoiseRouterData.m_224435_(0.4f)), Climate.Parameter.m_186822_(NoiseRouterData.m_224435_(0.4f), NoiseRouterData.m_224435_(0.56666666f)), Climate.Parameter.m_186822_(NoiseRouterData.m_224435_(0.56666666f), 2.0f)};
    }

    @VisibleForDebug
    public Climate.Parameter[] m_202000_() {
        return new Climate.Parameter[]{Climate.Parameter.m_186822_(-2.0f, 0.0f), Climate.Parameter.m_186822_(0.0f, 2.0f)};
    }
}

