/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.font.glyphs;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.RenderType;

public class BakedGlyph {
    private final RenderType f_95199_;
    private final RenderType f_95200_;
    private final RenderType f_181374_;
    private final float f_95201_;
    private final float f_95202_;
    private final float f_95203_;
    private final float f_95204_;
    private final float f_95205_;
    private final float f_95206_;
    private final float f_95207_;
    private final float f_95208_;

    public BakedGlyph(RenderType p_181376_, RenderType p_181377_, RenderType p_181378_, float p_181379_, float p_181380_, float p_181381_, float p_181382_, float p_181383_, float p_181384_, float p_181385_, float p_181386_) {
        this.f_95199_ = p_181376_;
        this.f_95200_ = p_181377_;
        this.f_181374_ = p_181378_;
        this.f_95201_ = p_181379_;
        this.f_95202_ = p_181380_;
        this.f_95203_ = p_181381_;
        this.f_95204_ = p_181382_;
        this.f_95205_ = p_181383_;
        this.f_95206_ = p_181384_;
        this.f_95207_ = p_181385_;
        this.f_95208_ = p_181386_;
    }

    public void m_5626_(boolean p_95227_, float p_95228_, float p_95229_, Matrix4f p_95230_, VertexConsumer p_95231_, float p_95232_, float p_95233_, float p_95234_, float p_95235_, int p_95236_) {
        int $$10 = 3;
        float $$11 = p_95228_ + this.f_95205_;
        float $$12 = p_95228_ + this.f_95206_;
        float $$13 = this.f_95207_ - 3.0f;
        float $$14 = this.f_95208_ - 3.0f;
        float $$15 = p_95229_ + $$13;
        float $$16 = p_95229_ + $$14;
        float $$17 = p_95227_ ? 1.0f - 0.25f * $$13 : 0.0f;
        float $$18 = p_95227_ ? 1.0f - 0.25f * $$14 : 0.0f;
        p_95231_.m_85982_(p_95230_, $$11 + $$17, $$15, 0.0f).m_85950_(p_95232_, p_95233_, p_95234_, p_95235_).m_7421_(this.f_95201_, this.f_95203_).m_85969_(p_95236_).m_5752_();
        p_95231_.m_85982_(p_95230_, $$11 + $$18, $$16, 0.0f).m_85950_(p_95232_, p_95233_, p_95234_, p_95235_).m_7421_(this.f_95201_, this.f_95204_).m_85969_(p_95236_).m_5752_();
        p_95231_.m_85982_(p_95230_, $$12 + $$18, $$16, 0.0f).m_85950_(p_95232_, p_95233_, p_95234_, p_95235_).m_7421_(this.f_95202_, this.f_95204_).m_85969_(p_95236_).m_5752_();
        p_95231_.m_85982_(p_95230_, $$12 + $$17, $$15, 0.0f).m_85950_(p_95232_, p_95233_, p_95234_, p_95235_).m_7421_(this.f_95202_, this.f_95203_).m_85969_(p_95236_).m_5752_();
    }

    public void m_95220_(Effect p_95221_, Matrix4f p_95222_, VertexConsumer p_95223_, int p_95224_) {
        p_95223_.m_85982_(p_95222_, p_95221_.f_95237_, p_95221_.f_95238_, p_95221_.f_95241_).m_85950_(p_95221_.f_95242_, p_95221_.f_95243_, p_95221_.f_95244_, p_95221_.f_95245_).m_7421_(this.f_95201_, this.f_95203_).m_85969_(p_95224_).m_5752_();
        p_95223_.m_85982_(p_95222_, p_95221_.f_95239_, p_95221_.f_95238_, p_95221_.f_95241_).m_85950_(p_95221_.f_95242_, p_95221_.f_95243_, p_95221_.f_95244_, p_95221_.f_95245_).m_7421_(this.f_95201_, this.f_95204_).m_85969_(p_95224_).m_5752_();
        p_95223_.m_85982_(p_95222_, p_95221_.f_95239_, p_95221_.f_95240_, p_95221_.f_95241_).m_85950_(p_95221_.f_95242_, p_95221_.f_95243_, p_95221_.f_95244_, p_95221_.f_95245_).m_7421_(this.f_95202_, this.f_95204_).m_85969_(p_95224_).m_5752_();
        p_95223_.m_85982_(p_95222_, p_95221_.f_95237_, p_95221_.f_95240_, p_95221_.f_95241_).m_85950_(p_95221_.f_95242_, p_95221_.f_95243_, p_95221_.f_95244_, p_95221_.f_95245_).m_7421_(this.f_95202_, this.f_95203_).m_85969_(p_95224_).m_5752_();
    }

    public RenderType m_181387_(Font.DisplayMode p_181388_) {
        return switch (p_181388_) {
            default -> throw new IncompatibleClassChangeError();
            case Font.DisplayMode.NORMAL -> this.f_95199_;
            case Font.DisplayMode.SEE_THROUGH -> this.f_95200_;
            case Font.DisplayMode.POLYGON_OFFSET -> this.f_181374_;
        };
    }

    public static class Effect {
        protected final float f_95237_;
        protected final float f_95238_;
        protected final float f_95239_;
        protected final float f_95240_;
        protected final float f_95241_;
        protected final float f_95242_;
        protected final float f_95243_;
        protected final float f_95244_;
        protected final float f_95245_;

        public Effect(float p_95247_, float p_95248_, float p_95249_, float p_95250_, float p_95251_, float p_95252_, float p_95253_, float p_95254_, float p_95255_) {
            this.f_95237_ = p_95247_;
            this.f_95238_ = p_95248_;
            this.f_95239_ = p_95249_;
            this.f_95240_ = p_95250_;
            this.f_95241_ = p_95251_;
            this.f_95242_ = p_95252_;
            this.f_95243_ = p_95253_;
            this.f_95244_ = p_95254_;
            this.f_95245_ = p_95255_;
        }
    }
}

