/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.block;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LiquidBlockRenderer {
    private static final float f_173402_ = 0.8888889f;
    private final TextureAtlasSprite[] f_110940_ = new TextureAtlasSprite[2];
    private final TextureAtlasSprite[] f_110941_ = new TextureAtlasSprite[2];
    private TextureAtlasSprite f_110942_;

    protected void m_110944_() {
        this.f_110940_[0] = Minecraft.m_91087_().m_91304_().m_119430_().m_110893_(Blocks.f_49991_.m_49966_()).m_6160_();
        this.f_110940_[1] = ModelBakery.f_119221_.m_119204_();
        this.f_110941_[0] = Minecraft.m_91087_().m_91304_().m_119430_().m_110893_(Blocks.f_49990_.m_49966_()).m_6160_();
        this.f_110941_[1] = ModelBakery.f_119222_.m_119204_();
        this.f_110942_ = ModelBakery.f_119223_.m_119204_();
    }

    private static boolean m_203185_(FluidState p_203186_, FluidState p_203187_) {
        return p_203187_.m_76152_().m_6212_(p_203186_.m_76152_());
    }

    private static boolean m_110978_(BlockGetter p_110979_, Direction p_110980_, float p_110981_, BlockPos p_110982_, BlockState p_110983_) {
        if (p_110983_.m_60815_()) {
            VoxelShape $$5 = Shapes.m_83048_(0.0, 0.0, 0.0, 1.0, p_110981_, 1.0);
            VoxelShape $$6 = p_110983_.m_60768_(p_110979_, p_110982_);
            return Shapes.m_83117_($$5, $$6, p_110980_);
        }
        return false;
    }

    private static boolean m_203179_(BlockGetter p_203180_, BlockPos p_203181_, Direction p_203182_, float p_203183_, BlockState p_203184_) {
        return LiquidBlockRenderer.m_110978_(p_203180_, p_203182_, p_203183_, p_203181_.m_121945_(p_203182_), p_203184_);
    }

    private static boolean m_110959_(BlockGetter p_110960_, BlockPos p_110961_, BlockState p_110962_, Direction p_110963_) {
        return LiquidBlockRenderer.m_110978_(p_110960_, p_110963_.m_122424_(), 1.0f, p_110961_, p_110962_);
    }

    public static boolean m_203166_(BlockAndTintGetter p_203167_, BlockPos p_203168_, FluidState p_203169_, BlockState p_203170_, Direction p_203171_, FluidState p_203172_) {
        return !LiquidBlockRenderer.m_110959_(p_203167_, p_203168_, p_203170_, p_203171_) && !LiquidBlockRenderer.m_203185_(p_203169_, p_203172_);
    }

    /*
     * WARNING - void declaration
     */
    public void m_234369_(BlockAndTintGetter p_234370_, BlockPos p_234371_, VertexConsumer p_234372_, BlockState p_234373_, FluidState p_234374_) {
        float $$51;
        float $$46;
        float $$45;
        float $$44;
        float $$43;
        boolean $$5 = p_234374_.m_205070_(FluidTags.f_13132_);
        TextureAtlasSprite[] $$6 = $$5 ? this.f_110940_ : this.f_110941_;
        int $$7 = $$5 ? 0xFFFFFF : BiomeColors.m_108811_(p_234370_, p_234371_);
        float $$8 = (float)($$7 >> 16 & 0xFF) / 255.0f;
        float $$9 = (float)($$7 >> 8 & 0xFF) / 255.0f;
        float $$10 = (float)($$7 & 0xFF) / 255.0f;
        BlockState $$11 = p_234370_.m_8055_(p_234371_.m_121945_(Direction.DOWN));
        FluidState $$12 = $$11.m_60819_();
        BlockState $$13 = p_234370_.m_8055_(p_234371_.m_121945_(Direction.UP));
        FluidState $$14 = $$13.m_60819_();
        BlockState $$15 = p_234370_.m_8055_(p_234371_.m_121945_(Direction.NORTH));
        FluidState $$16 = $$15.m_60819_();
        BlockState $$17 = p_234370_.m_8055_(p_234371_.m_121945_(Direction.SOUTH));
        FluidState $$18 = $$17.m_60819_();
        BlockState $$19 = p_234370_.m_8055_(p_234371_.m_121945_(Direction.WEST));
        FluidState $$20 = $$19.m_60819_();
        BlockState $$21 = p_234370_.m_8055_(p_234371_.m_121945_(Direction.EAST));
        FluidState $$22 = $$21.m_60819_();
        boolean $$23 = !LiquidBlockRenderer.m_203185_(p_234374_, $$14);
        boolean $$24 = LiquidBlockRenderer.m_203166_(p_234370_, p_234371_, p_234374_, p_234373_, Direction.DOWN, $$12) && !LiquidBlockRenderer.m_203179_(p_234370_, p_234371_, Direction.DOWN, 0.8888889f, $$11);
        boolean $$25 = LiquidBlockRenderer.m_203166_(p_234370_, p_234371_, p_234374_, p_234373_, Direction.NORTH, $$16);
        boolean $$26 = LiquidBlockRenderer.m_203166_(p_234370_, p_234371_, p_234374_, p_234373_, Direction.SOUTH, $$18);
        boolean $$27 = LiquidBlockRenderer.m_203166_(p_234370_, p_234371_, p_234374_, p_234373_, Direction.WEST, $$20);
        boolean $$28 = LiquidBlockRenderer.m_203166_(p_234370_, p_234371_, p_234374_, p_234373_, Direction.EAST, $$22);
        if (!($$23 || $$24 || $$28 || $$27 || $$25 || $$26)) {
            return;
        }
        float $$29 = p_234370_.m_7717_(Direction.DOWN, true);
        float $$30 = p_234370_.m_7717_(Direction.UP, true);
        float $$31 = p_234370_.m_7717_(Direction.NORTH, true);
        float $$32 = p_234370_.m_7717_(Direction.WEST, true);
        Fluid $$33 = p_234374_.m_76152_();
        float $$34 = this.m_203160_(p_234370_, $$33, p_234371_, p_234373_, p_234374_);
        if ($$34 >= 1.0f) {
            float $$35 = 1.0f;
            float $$36 = 1.0f;
            float $$37 = 1.0f;
            float $$38 = 1.0f;
        } else {
            float $$39 = this.m_203160_(p_234370_, $$33, p_234371_.m_122012_(), $$15, $$16);
            float $$40 = this.m_203160_(p_234370_, $$33, p_234371_.m_122019_(), $$17, $$18);
            float $$41 = this.m_203160_(p_234370_, $$33, p_234371_.m_122029_(), $$21, $$22);
            float $$42 = this.m_203160_(p_234370_, $$33, p_234371_.m_122024_(), $$19, $$20);
            $$43 = this.m_203149_(p_234370_, $$33, $$34, $$39, $$41, p_234371_.m_121945_(Direction.NORTH).m_121945_(Direction.EAST));
            $$44 = this.m_203149_(p_234370_, $$33, $$34, $$39, $$42, p_234371_.m_121945_(Direction.NORTH).m_121945_(Direction.WEST));
            $$45 = this.m_203149_(p_234370_, $$33, $$34, $$40, $$41, p_234371_.m_121945_(Direction.SOUTH).m_121945_(Direction.EAST));
            $$46 = this.m_203149_(p_234370_, $$33, $$34, $$40, $$42, p_234371_.m_121945_(Direction.SOUTH).m_121945_(Direction.WEST));
        }
        double $$47 = p_234371_.m_123341_() & 0xF;
        double $$48 = p_234371_.m_123342_() & 0xF;
        double $$49 = p_234371_.m_123343_() & 0xF;
        float $$50 = 0.001f;
        float f = $$51 = $$24 ? 0.001f : 0.0f;
        if ($$23 && !LiquidBlockRenderer.m_203179_(p_234370_, p_234371_, Direction.UP, Math.min(Math.min($$44, $$46), Math.min($$45, $$43)), $$13)) {
            float $$74;
            float $$73;
            float $$72;
            float $$71;
            float $$70;
            float $$69;
            float $$68;
            float $$67;
            $$44 -= 0.001f;
            $$46 -= 0.001f;
            $$45 -= 0.001f;
            $$43 -= 0.001f;
            Vec3 $$52 = p_234374_.m_76179_(p_234370_, p_234371_);
            if ($$52.f_82479_ == 0.0 && $$52.f_82481_ == 0.0) {
                TextureAtlasSprite $$53 = $$6[0];
                float $$54 = $$53.m_118367_(0.0);
                float $$55 = $$53.m_118393_(0.0);
                float $$56 = $$54;
                float $$57 = $$53.m_118393_(16.0);
                float $$58 = $$53.m_118367_(16.0);
                float $$59 = $$57;
                float $$60 = $$58;
                float $$61 = $$55;
            } else {
                TextureAtlasSprite $$62 = $$6[1];
                float $$63 = (float)Mth.m_14136_($$52.f_82481_, $$52.f_82479_) - 1.5707964f;
                float $$64 = Mth.m_14031_($$63) * 0.25f;
                float $$65 = Mth.m_14089_($$63) * 0.25f;
                float $$66 = 8.0f;
                $$67 = $$62.m_118367_(8.0f + (-$$65 - $$64) * 16.0f);
                $$68 = $$62.m_118393_(8.0f + (-$$65 + $$64) * 16.0f);
                $$69 = $$62.m_118367_(8.0f + (-$$65 + $$64) * 16.0f);
                $$70 = $$62.m_118393_(8.0f + ($$65 + $$64) * 16.0f);
                $$71 = $$62.m_118367_(8.0f + ($$65 + $$64) * 16.0f);
                $$72 = $$62.m_118393_(8.0f + ($$65 - $$64) * 16.0f);
                $$73 = $$62.m_118367_(8.0f + ($$65 - $$64) * 16.0f);
                $$74 = $$62.m_118393_(8.0f + (-$$65 - $$64) * 16.0f);
            }
            void $$75 = ($$67 + $$69 + $$71 + $$73) / 4.0f;
            void $$76 = ($$68 + $$70 + $$72 + $$74) / 4.0f;
            float $$77 = (float)$$6[0].m_118405_() / ($$6[0].m_118410_() - $$6[0].m_118409_());
            float $$78 = (float)$$6[0].m_118408_() / ($$6[0].m_118412_() - $$6[0].m_118411_());
            float $$79 = 4.0f / Math.max($$78, $$77);
            $$67 = Mth.m_14179_($$79, $$67, (float)$$75);
            $$69 = Mth.m_14179_($$79, $$69, (float)$$75);
            $$71 = Mth.m_14179_($$79, $$71, (float)$$75);
            $$73 = Mth.m_14179_($$79, $$73, (float)$$75);
            $$68 = Mth.m_14179_($$79, $$68, (float)$$76);
            $$70 = Mth.m_14179_($$79, $$70, (float)$$76);
            $$72 = Mth.m_14179_($$79, $$72, (float)$$76);
            $$74 = Mth.m_14179_($$79, $$74, (float)$$76);
            int $$80 = this.m_110945_(p_234370_, p_234371_);
            float $$81 = $$30 * $$8;
            float $$82 = $$30 * $$9;
            float $$83 = $$30 * $$10;
            this.m_110984_(p_234372_, $$47 + 0.0, $$48 + (double)$$44, $$49 + 0.0, $$81, $$82, $$83, $$67, $$68, $$80);
            this.m_110984_(p_234372_, $$47 + 0.0, $$48 + (double)$$46, $$49 + 1.0, $$81, $$82, $$83, $$69, $$70, $$80);
            this.m_110984_(p_234372_, $$47 + 1.0, $$48 + (double)$$45, $$49 + 1.0, $$81, $$82, $$83, $$71, $$72, $$80);
            this.m_110984_(p_234372_, $$47 + 1.0, $$48 + (double)$$43, $$49 + 0.0, $$81, $$82, $$83, $$73, $$74, $$80);
            if (p_234374_.m_76171_(p_234370_, p_234371_.m_7494_())) {
                this.m_110984_(p_234372_, $$47 + 0.0, $$48 + (double)$$44, $$49 + 0.0, $$81, $$82, $$83, $$67, $$68, $$80);
                this.m_110984_(p_234372_, $$47 + 1.0, $$48 + (double)$$43, $$49 + 0.0, $$81, $$82, $$83, $$73, $$74, $$80);
                this.m_110984_(p_234372_, $$47 + 1.0, $$48 + (double)$$45, $$49 + 1.0, $$81, $$82, $$83, $$71, $$72, $$80);
                this.m_110984_(p_234372_, $$47 + 0.0, $$48 + (double)$$46, $$49 + 1.0, $$81, $$82, $$83, $$69, $$70, $$80);
            }
        }
        if ($$24) {
            float $$84 = $$6[0].m_118409_();
            float $$85 = $$6[0].m_118410_();
            float $$86 = $$6[0].m_118411_();
            float $$87 = $$6[0].m_118412_();
            int $$88 = this.m_110945_(p_234370_, p_234371_.m_7495_());
            float $$89 = $$29 * $$8;
            float $$90 = $$29 * $$9;
            float $$91 = $$29 * $$10;
            this.m_110984_(p_234372_, $$47, $$48 + (double)$$51, $$49 + 1.0, $$89, $$90, $$91, $$84, $$87, $$88);
            this.m_110984_(p_234372_, $$47, $$48 + (double)$$51, $$49, $$89, $$90, $$91, $$84, $$86, $$88);
            this.m_110984_(p_234372_, $$47 + 1.0, $$48 + (double)$$51, $$49, $$89, $$90, $$91, $$85, $$86, $$88);
            this.m_110984_(p_234372_, $$47 + 1.0, $$48 + (double)$$51, $$49 + 1.0, $$89, $$90, $$91, $$85, $$87, $$88);
        }
        int $$92 = this.m_110945_(p_234370_, p_234371_);
        for (Direction $$93 : Direction.Plane.HORIZONTAL) {
            Block $$124;
            boolean $$121;
            double $$120;
            double $$119;
            double $$118;
            double $$117;
            void $$116;
            float $$115;
            switch ($$93) {
                case NORTH: {
                    void $$94 = $$44;
                    float $$95 = $$43;
                    double $$96 = $$47;
                    double $$97 = $$47 + 1.0;
                    double $$98 = $$49 + (double)0.001f;
                    double $$99 = $$49 + (double)0.001f;
                    boolean $$100 = $$25;
                    break;
                }
                case SOUTH: {
                    void $$101 = $$45;
                    void $$102 = $$46;
                    double $$103 = $$47 + 1.0;
                    double $$104 = $$47;
                    double $$105 = $$49 + 1.0 - (double)0.001f;
                    double $$106 = $$49 + 1.0 - (double)0.001f;
                    boolean $$107 = $$26;
                    break;
                }
                case WEST: {
                    void $$108 = $$46;
                    void $$109 = $$44;
                    double $$110 = $$47 + (double)0.001f;
                    double $$111 = $$47 + (double)0.001f;
                    double $$112 = $$49 + 1.0;
                    double $$113 = $$49;
                    boolean $$114 = $$27;
                    break;
                }
                default: {
                    $$115 = $$43;
                    $$116 = $$45;
                    $$117 = $$47 + 1.0 - (double)0.001f;
                    $$118 = $$47 + 1.0 - (double)0.001f;
                    $$119 = $$49;
                    $$120 = $$49 + 1.0;
                    $$121 = $$28;
                }
            }
            if (!$$121 || LiquidBlockRenderer.m_203179_(p_234370_, p_234371_, $$93, Math.max($$115, (float)$$116), p_234370_.m_8055_(p_234371_.m_121945_($$93)))) continue;
            BlockPos $$122 = p_234371_.m_121945_($$93);
            TextureAtlasSprite $$123 = $$6[1];
            if (!$$5 && (($$124 = p_234370_.m_8055_($$122).m_60734_()) instanceof HalfTransparentBlock || $$124 instanceof LeavesBlock)) {
                $$123 = this.f_110942_;
            }
            float $$125 = $$123.m_118367_(0.0);
            float $$126 = $$123.m_118367_(8.0);
            float $$127 = $$123.m_118393_((1.0f - $$115) * 16.0f * 0.5f);
            float $$128 = $$123.m_118393_((1.0f - $$116) * 16.0f * 0.5f);
            float $$129 = $$123.m_118393_(8.0);
            float $$130 = $$93.m_122434_() == Direction.Axis.Z ? $$31 : $$32;
            float $$131 = $$30 * $$130 * $$8;
            float $$132 = $$30 * $$130 * $$9;
            float $$133 = $$30 * $$130 * $$10;
            this.m_110984_(p_234372_, $$117, $$48 + (double)$$115, $$119, $$131, $$132, $$133, $$125, $$127, $$92);
            this.m_110984_(p_234372_, $$118, $$48 + (double)$$116, $$120, $$131, $$132, $$133, $$126, $$128, $$92);
            this.m_110984_(p_234372_, $$118, $$48 + (double)$$51, $$120, $$131, $$132, $$133, $$126, $$129, $$92);
            this.m_110984_(p_234372_, $$117, $$48 + (double)$$51, $$119, $$131, $$132, $$133, $$125, $$129, $$92);
            if ($$123 == this.f_110942_) continue;
            this.m_110984_(p_234372_, $$117, $$48 + (double)$$51, $$119, $$131, $$132, $$133, $$125, $$129, $$92);
            this.m_110984_(p_234372_, $$118, $$48 + (double)$$51, $$120, $$131, $$132, $$133, $$126, $$129, $$92);
            this.m_110984_(p_234372_, $$118, $$48 + (double)$$116, $$120, $$131, $$132, $$133, $$126, $$128, $$92);
            this.m_110984_(p_234372_, $$117, $$48 + (double)$$115, $$119, $$131, $$132, $$133, $$125, $$127, $$92);
        }
    }

    private float m_203149_(BlockAndTintGetter p_203150_, Fluid p_203151_, float p_203152_, float p_203153_, float p_203154_, BlockPos p_203155_) {
        if (p_203154_ >= 1.0f || p_203153_ >= 1.0f) {
            return 1.0f;
        }
        float[] $$6 = new float[2];
        if (p_203154_ > 0.0f || p_203153_ > 0.0f) {
            float $$7 = this.m_203156_(p_203150_, p_203151_, p_203155_);
            if ($$7 >= 1.0f) {
                return 1.0f;
            }
            this.m_203188_($$6, $$7);
        }
        this.m_203188_($$6, p_203152_);
        this.m_203188_($$6, p_203154_);
        this.m_203188_($$6, p_203153_);
        return $$6[0] / $$6[1];
    }

    private void m_203188_(float[] p_203189_, float p_203190_) {
        if (p_203190_ >= 0.8f) {
            p_203189_[0] = p_203189_[0] + p_203190_ * 10.0f;
            p_203189_[1] = p_203189_[1] + 10.0f;
        } else if (p_203190_ >= 0.0f) {
            p_203189_[0] = p_203189_[0] + p_203190_;
            p_203189_[1] = p_203189_[1] + 1.0f;
        }
    }

    private float m_203156_(BlockAndTintGetter p_203157_, Fluid p_203158_, BlockPos p_203159_) {
        BlockState $$3 = p_203157_.m_8055_(p_203159_);
        return this.m_203160_(p_203157_, p_203158_, p_203159_, $$3, $$3.m_60819_());
    }

    private float m_203160_(BlockAndTintGetter p_203161_, Fluid p_203162_, BlockPos p_203163_, BlockState p_203164_, FluidState p_203165_) {
        if (p_203162_.m_6212_(p_203165_.m_76152_())) {
            BlockState $$5 = p_203161_.m_8055_(p_203163_.m_7494_());
            if (p_203162_.m_6212_($$5.m_60819_().m_76152_())) {
                return 1.0f;
            }
            return p_203165_.m_76182_();
        }
        if (!p_203164_.m_60767_().m_76333_()) {
            return 0.0f;
        }
        return -1.0f;
    }

    private void m_110984_(VertexConsumer p_110985_, double p_110986_, double p_110987_, double p_110988_, float p_110989_, float p_110990_, float p_110991_, float p_110992_, float p_110993_, int p_110994_) {
        p_110985_.m_5483_(p_110986_, p_110987_, p_110988_).m_85950_(p_110989_, p_110990_, p_110991_, 1.0f).m_7421_(p_110992_, p_110993_).m_85969_(p_110994_).m_5601_(0.0f, 1.0f, 0.0f).m_5752_();
    }

    private int m_110945_(BlockAndTintGetter p_110946_, BlockPos p_110947_) {
        int $$2 = LevelRenderer.m_109541_(p_110946_, p_110947_);
        int $$3 = LevelRenderer.m_109541_(p_110946_, p_110947_.m_7494_());
        int $$4 = $$2 & 0xFF;
        int $$5 = $$3 & 0xFF;
        int $$6 = $$2 >> 16 & 0xFF;
        int $$7 = $$3 >> 16 & 0xFF;
        return ($$4 > $$5 ? $$4 : $$5) | ($$6 > $$7 ? $$6 : $$7) << 16;
    }
}

