/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen;

import net.minecraft.util.CubicSpline;
import net.minecraft.util.Mth;
import net.minecraft.util.ToFloatFunction;
import net.minecraft.world.level.levelgen.NoiseRouterData;

public class TerrainProvider {
    private static final float f_236557_ = -0.51f;
    private static final float f_236558_ = -0.4f;
    private static final float f_236559_ = 0.1f;
    private static final float f_236560_ = -0.15f;
    private static final ToFloatFunction<Float> f_236561_ = ToFloatFunction.f_216471_;
    private static final ToFloatFunction<Float> f_236562_ = ToFloatFunction.m_216475_(p_236651_ -> p_236651_ < 0.0f ? p_236651_ : p_236651_ * 2.0f);
    private static final ToFloatFunction<Float> f_236563_ = ToFloatFunction.m_216475_(p_236649_ -> 1.25f - 6.25f / (p_236649_ + 5.0f));
    private static final ToFloatFunction<Float> f_236564_ = ToFloatFunction.m_216475_(p_236641_ -> p_236641_ * 2.0f);

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> m_236635_(I p_236636_, I p_236637_, I p_236638_, boolean p_236639_) {
        ToFloatFunction<Float> $$4 = p_236639_ ? f_236562_ : f_236561_;
        CubicSpline<C, I> $$5 = TerrainProvider.m_236595_(p_236637_, p_236638_, -0.15f, 0.0f, 0.0f, 0.1f, 0.0f, -0.03f, false, false, $$4);
        CubicSpline<C, I> $$6 = TerrainProvider.m_236595_(p_236637_, p_236638_, -0.1f, 0.03f, 0.1f, 0.1f, 0.01f, -0.03f, false, false, $$4);
        CubicSpline<C, I> $$7 = TerrainProvider.m_236595_(p_236637_, p_236638_, -0.1f, 0.03f, 0.1f, 0.7f, 0.01f, -0.03f, true, true, $$4);
        CubicSpline<C, I> $$8 = TerrainProvider.m_236595_(p_236637_, p_236638_, -0.05f, 0.03f, 0.1f, 1.0f, 0.01f, 0.01f, true, true, $$4);
        return CubicSpline.m_184254_(p_236636_, $$4).m_216114_(-1.1f, 0.044f).m_216114_(-1.02f, -0.2222f).m_216114_(-0.51f, -0.2222f).m_216114_(-0.44f, -0.12f).m_216114_(-0.18f, -0.12f).m_216117_(-0.16f, $$5).m_216117_(-0.15f, $$5).m_216117_(-0.1f, $$6).m_216117_(0.25f, $$7).m_216117_(1.0f, $$8).m_184297_();
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> m_236629_(I p_236630_, I p_236631_, I p_236632_, I p_236633_, boolean p_236634_) {
        ToFloatFunction<Float> $$5 = p_236634_ ? f_236563_ : f_236561_;
        return CubicSpline.m_184254_(p_236630_, f_236561_).m_216114_(-0.19f, 3.95f).m_216117_(-0.15f, TerrainProvider.m_236622_(p_236631_, p_236632_, p_236633_, 6.25f, true, f_236561_)).m_216117_(-0.1f, TerrainProvider.m_236622_(p_236631_, p_236632_, p_236633_, 5.47f, true, $$5)).m_216117_(0.03f, TerrainProvider.m_236622_(p_236631_, p_236632_, p_236633_, 5.08f, true, $$5)).m_216117_(0.06f, TerrainProvider.m_236622_(p_236631_, p_236632_, p_236633_, 4.69f, false, $$5)).m_184297_();
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> m_236642_(I p_236643_, I p_236644_, I p_236645_, I p_236646_, boolean p_236647_) {
        ToFloatFunction<Float> $$5 = p_236647_ ? f_236564_ : f_236561_;
        float $$6 = 0.65f;
        return CubicSpline.m_184254_(p_236643_, $$5).m_216114_(-0.11f, 0.0f).m_216117_(0.03f, TerrainProvider.m_236613_(p_236644_, p_236645_, p_236646_, 1.0f, 0.5f, 0.0f, 0.0f, $$5)).m_216117_(0.65f, TerrainProvider.m_236613_(p_236644_, p_236645_, p_236646_, 1.0f, 1.0f, 1.0f, 0.0f, $$5)).m_184297_();
    }

    private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> m_236613_(I p_236614_, I p_236615_, I p_236616_, float p_236617_, float p_236618_, float p_236619_, float p_236620_, ToFloatFunction<Float> p_236621_) {
        float $$8 = -0.5775f;
        CubicSpline<C, I> $$9 = TerrainProvider.m_236607_(p_236615_, p_236616_, p_236617_, p_236619_, p_236621_);
        CubicSpline<C, I> $$10 = TerrainProvider.m_236607_(p_236615_, p_236616_, p_236618_, p_236620_, p_236621_);
        return CubicSpline.m_184254_(p_236614_, p_236621_).m_216117_(-1.0f, $$9).m_216117_(-0.78f, $$10).m_216117_(-0.5775f, $$10).m_216114_(-0.375f, 0.0f).m_184297_();
    }

    private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> m_236607_(I p_236608_, I p_236609_, float p_236610_, float p_236611_, ToFloatFunction<Float> p_236612_) {
        float $$5 = NoiseRouterData.m_224435_(0.4f);
        float $$6 = NoiseRouterData.m_224435_(0.56666666f);
        float $$7 = ($$5 + $$6) / 2.0f;
        CubicSpline.Builder<C, I> $$8 = CubicSpline.m_184254_(p_236609_, p_236612_);
        $$8.m_216114_($$5, 0.0f);
        if (p_236611_ > 0.0f) {
            $$8.m_216117_($$7, TerrainProvider.m_236586_(p_236608_, p_236611_, p_236612_));
        } else {
            $$8.m_216114_($$7, 0.0f);
        }
        if (p_236610_ > 0.0f) {
            $$8.m_216117_(1.0f, TerrainProvider.m_236586_(p_236608_, p_236610_, p_236612_));
        } else {
            $$8.m_216114_(1.0f, 0.0f);
        }
        return $$8.m_184297_();
    }

    private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> m_236586_(I p_236587_, float p_236588_, ToFloatFunction<Float> p_236589_) {
        float $$3 = 0.63f * p_236588_;
        float $$4 = 0.3f * p_236588_;
        return CubicSpline.m_184254_(p_236587_, p_236589_).m_216114_(-0.01f, $$3).m_216114_(0.01f, $$4).m_184297_();
    }

    private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> m_236622_(I p_236623_, I p_236624_, I p_236625_, float p_236626_, boolean p_236627_, ToFloatFunction<Float> p_236628_) {
        CubicSpline $$6 = CubicSpline.m_184254_(p_236624_, p_236628_).m_216114_(-0.2f, 6.3f).m_216114_(0.2f, p_236626_).m_184297_();
        CubicSpline.Builder $$7 = CubicSpline.m_184254_(p_236623_, p_236628_).m_216117_(-0.6f, $$6).m_216117_(-0.5f, CubicSpline.m_184254_(p_236624_, p_236628_).m_216114_(-0.05f, 6.3f).m_216114_(0.05f, 2.67f).m_184297_()).m_216117_(-0.35f, $$6).m_216117_(-0.25f, $$6).m_216117_(-0.1f, CubicSpline.m_184254_(p_236624_, p_236628_).m_216114_(-0.05f, 2.67f).m_216114_(0.05f, 6.3f).m_184297_()).m_216117_(0.03f, $$6);
        if (p_236627_) {
            CubicSpline $$8 = CubicSpline.m_184254_(p_236624_, p_236628_).m_216114_(0.0f, p_236626_).m_216114_(0.1f, 0.625f).m_184297_();
            CubicSpline $$9 = CubicSpline.m_184254_(p_236625_, p_236628_).m_216114_(-0.9f, p_236626_).m_216117_(-0.69f, $$8).m_184297_();
            $$7.m_216114_(0.35f, p_236626_).m_216117_(0.45f, $$9).m_216117_(0.55f, $$9).m_216114_(0.62f, p_236626_);
        } else {
            CubicSpline $$10 = CubicSpline.m_184254_(p_236625_, p_236628_).m_216117_(-0.7f, $$6).m_216114_(-0.15f, 1.37f).m_184297_();
            CubicSpline $$11 = CubicSpline.m_184254_(p_236625_, p_236628_).m_216117_(0.45f, $$6).m_216114_(0.7f, 1.56f).m_184297_();
            $$7.m_216117_(0.05f, $$11).m_216117_(0.4f, $$11).m_216117_(0.45f, $$10).m_216117_(0.55f, $$10).m_216114_(0.58f, p_236626_);
        }
        return $$7.m_184297_();
    }

    private static float m_236572_(float p_236573_, float p_236574_, float p_236575_, float p_236576_) {
        return (p_236574_ - p_236573_) / (p_236576_ - p_236575_);
    }

    private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> m_236590_(I p_236591_, float p_236592_, boolean p_236593_, ToFloatFunction<Float> p_236594_) {
        CubicSpline.Builder $$4 = CubicSpline.m_184254_(p_236591_, p_236594_);
        float $$5 = -0.7f;
        float $$6 = -1.0f;
        float $$7 = TerrainProvider.m_236568_(-1.0f, p_236592_, -0.7f);
        float $$8 = 1.0f;
        float $$9 = TerrainProvider.m_236568_(1.0f, p_236592_, -0.7f);
        float $$10 = TerrainProvider.m_236566_(p_236592_);
        float $$11 = -0.65f;
        if (-0.65f < $$10 && $$10 < 1.0f) {
            float $$12 = TerrainProvider.m_236568_(-0.65f, p_236592_, -0.7f);
            float $$13 = -0.75f;
            float $$14 = TerrainProvider.m_236568_(-0.75f, p_236592_, -0.7f);
            float $$15 = TerrainProvider.m_236572_($$7, $$14, -1.0f, -0.75f);
            $$4.m_184298_(-1.0f, $$7, $$15);
            $$4.m_216114_(-0.75f, $$14);
            $$4.m_216114_(-0.65f, $$12);
            float $$16 = TerrainProvider.m_236568_($$10, p_236592_, -0.7f);
            float $$17 = TerrainProvider.m_236572_($$16, $$9, $$10, 1.0f);
            float $$18 = 0.01f;
            $$4.m_216114_($$10 - 0.01f, $$16);
            $$4.m_184298_($$10, $$16, $$17);
            $$4.m_184298_(1.0f, $$9, $$17);
        } else {
            float $$19 = TerrainProvider.m_236572_($$7, $$9, -1.0f, 1.0f);
            if (p_236593_) {
                $$4.m_216114_(-1.0f, Math.max(0.2f, $$7));
                $$4.m_184298_(0.0f, Mth.m_14179_(0.5f, $$7, $$9), $$19);
            } else {
                $$4.m_184298_(-1.0f, $$7, $$19);
            }
            $$4.m_184298_(1.0f, $$9, $$19);
        }
        return $$4.m_184297_();
    }

    private static float m_236568_(float p_236569_, float p_236570_, float p_236571_) {
        float $$3 = 1.17f;
        float $$4 = 0.46082947f;
        float $$5 = 1.0f - (1.0f - p_236570_) * 0.5f;
        float $$6 = 0.5f * (1.0f - p_236570_);
        float $$7 = (p_236569_ + 1.17f) * 0.46082947f;
        float $$8 = $$7 * $$5 - $$6;
        if (p_236569_ < p_236571_) {
            return Math.max($$8, -0.2222f);
        }
        return Math.max($$8, 0.0f);
    }

    private static float m_236566_(float p_236567_) {
        float $$1 = 1.17f;
        float $$2 = 0.46082947f;
        float $$3 = 1.0f - (1.0f - p_236567_) * 0.5f;
        float $$4 = 0.5f * (1.0f - p_236567_);
        return $$4 / (0.46082947f * $$3) - 1.17f;
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> m_236595_(I p_236596_, I p_236597_, float p_236598_, float p_236599_, float p_236600_, float p_236601_, float p_236602_, float p_236603_, boolean p_236604_, boolean p_236605_, ToFloatFunction<Float> p_236606_) {
        float $$11 = 0.6f;
        float $$12 = 0.5f;
        float $$13 = 0.5f;
        CubicSpline<C, I> $$14 = TerrainProvider.m_236590_(p_236597_, Mth.m_14179_(p_236601_, 0.6f, 1.5f), p_236605_, p_236606_);
        CubicSpline<C, I> $$15 = TerrainProvider.m_236590_(p_236597_, Mth.m_14179_(p_236601_, 0.6f, 1.0f), p_236605_, p_236606_);
        CubicSpline<C, I> $$16 = TerrainProvider.m_236590_(p_236597_, p_236601_, p_236605_, p_236606_);
        CubicSpline<C, I> $$17 = TerrainProvider.m_236577_(p_236597_, p_236598_ - 0.15f, 0.5f * p_236601_, Mth.m_14179_(0.5f, 0.5f, 0.5f) * p_236601_, 0.5f * p_236601_, 0.6f * p_236601_, 0.5f, p_236606_);
        CubicSpline<C, I> $$18 = TerrainProvider.m_236577_(p_236597_, p_236598_, p_236602_ * p_236601_, p_236599_ * p_236601_, 0.5f * p_236601_, 0.6f * p_236601_, 0.5f, p_236606_);
        CubicSpline<C, I> $$19 = TerrainProvider.m_236577_(p_236597_, p_236598_, p_236602_, p_236602_, p_236599_, p_236600_, 0.5f, p_236606_);
        CubicSpline<C, I> $$20 = TerrainProvider.m_236577_(p_236597_, p_236598_, p_236602_, p_236602_, p_236599_, p_236600_, 0.5f, p_236606_);
        CubicSpline $$21 = CubicSpline.m_184254_(p_236597_, p_236606_).m_216114_(-1.0f, p_236598_).m_216117_(-0.4f, $$19).m_216114_(0.0f, p_236600_ + 0.07f).m_184297_();
        CubicSpline<C, I> $$22 = TerrainProvider.m_236577_(p_236597_, -0.02f, p_236603_, p_236603_, p_236599_, p_236600_, 0.0f, p_236606_);
        CubicSpline.Builder<C, I> $$23 = CubicSpline.m_184254_(p_236596_, p_236606_).m_216117_(-0.85f, $$14).m_216117_(-0.7f, $$15).m_216117_(-0.4f, $$16).m_216117_(-0.35f, $$17).m_216117_(-0.1f, $$18).m_216117_(0.2f, $$19);
        if (p_236604_) {
            $$23.m_216117_(0.4f, $$20).m_216117_(0.45f, $$21).m_216117_(0.55f, $$21).m_216117_(0.58f, $$20);
        }
        $$23.m_216117_(0.7f, $$22);
        return $$23.m_184297_();
    }

    private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> m_236577_(I p_236578_, float p_236579_, float p_236580_, float p_236581_, float p_236582_, float p_236583_, float p_236584_, ToFloatFunction<Float> p_236585_) {
        float $$8 = Math.max(0.5f * (p_236580_ - p_236579_), p_236584_);
        float $$9 = 5.0f * (p_236581_ - p_236580_);
        return CubicSpline.m_184254_(p_236578_, p_236585_).m_184298_(-1.0f, p_236579_, $$8).m_184298_(-0.4f, p_236580_, Math.min($$8, $$9)).m_184298_(0.0f, p_236581_, $$9).m_184298_(0.4f, p_236582_, 2.0f * (p_236582_ - p_236581_)).m_184298_(1.0f, p_236583_, 0.7f * (p_236583_ - p_236582_)).m_184297_();
    }
}

