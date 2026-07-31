/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ModelBlockRenderer {
    private static final int f_173403_ = 0;
    private static final int f_173404_ = 1;
    static final Direction[] f_173405_ = Direction.values();
    private final BlockColors f_110995_;
    private static final int f_173406_ = 100;
    static final ThreadLocal<Cache> f_110996_ = ThreadLocal.withInitial(Cache::new);

    public ModelBlockRenderer(BlockColors p_110999_) {
        this.f_110995_ = p_110999_;
    }

    public void m_234379_(BlockAndTintGetter p_234380_, BakedModel p_234381_, BlockState p_234382_, BlockPos p_234383_, PoseStack p_234384_, VertexConsumer p_234385_, boolean p_234386_, RandomSource p_234387_, long p_234388_, int p_234389_) {
        boolean $$10 = Minecraft.m_91086_() && p_234382_.m_60791_() == 0 && p_234381_.m_7541_();
        Vec3 $$11 = p_234382_.m_60824_(p_234380_, p_234383_);
        p_234384_.m_85837_($$11.f_82479_, $$11.f_82480_, $$11.f_82481_);
        try {
            if ($$10) {
                this.m_234390_(p_234380_, p_234381_, p_234382_, p_234383_, p_234384_, p_234385_, p_234386_, p_234387_, p_234388_, p_234389_);
            } else {
                this.m_234401_(p_234380_, p_234381_, p_234382_, p_234383_, p_234384_, p_234385_, p_234386_, p_234387_, p_234388_, p_234389_);
            }
        }
        catch (Throwable $$12) {
            CrashReport $$13 = CrashReport.m_127521_($$12, "Tesselating block model");
            CrashReportCategory $$14 = $$13.m_127514_("Block model being tesselated");
            CrashReportCategory.m_178950_($$14, p_234380_, p_234383_, p_234382_);
            $$14.m_128159_("Using AO", $$10);
            throw new ReportedException($$13);
        }
    }

    public void m_234390_(BlockAndTintGetter p_234391_, BakedModel p_234392_, BlockState p_234393_, BlockPos p_234394_, PoseStack p_234395_, VertexConsumer p_234396_, boolean p_234397_, RandomSource p_234398_, long p_234399_, int p_234400_) {
        float[] $$10 = new float[f_173405_.length * 2];
        BitSet $$11 = new BitSet(3);
        AmbientOcclusionFace $$12 = new AmbientOcclusionFace();
        BlockPos.MutableBlockPos $$13 = p_234394_.m_122032_();
        for (Direction $$14 : f_173405_) {
            p_234398_.m_188584_(p_234399_);
            List<BakedQuad> $$15 = p_234392_.m_213637_(p_234393_, $$14, p_234398_);
            if ($$15.isEmpty()) continue;
            $$13.m_122159_(p_234394_, $$14);
            if (p_234397_ && !Block.m_152444_(p_234393_, p_234391_, p_234394_, $$14, $$13)) continue;
            this.m_111012_(p_234391_, p_234393_, p_234394_, p_234395_, p_234396_, $$15, $$10, $$11, $$12, p_234400_);
        }
        p_234398_.m_188584_(p_234399_);
        List<BakedQuad> $$16 = p_234392_.m_213637_(p_234393_, null, p_234398_);
        if (!$$16.isEmpty()) {
            this.m_111012_(p_234391_, p_234393_, p_234394_, p_234395_, p_234396_, $$16, $$10, $$11, $$12, p_234400_);
        }
    }

    public void m_234401_(BlockAndTintGetter p_234402_, BakedModel p_234403_, BlockState p_234404_, BlockPos p_234405_, PoseStack p_234406_, VertexConsumer p_234407_, boolean p_234408_, RandomSource p_234409_, long p_234410_, int p_234411_) {
        BitSet $$10 = new BitSet(3);
        BlockPos.MutableBlockPos $$11 = p_234405_.m_122032_();
        for (Direction $$12 : f_173405_) {
            p_234409_.m_188584_(p_234410_);
            List<BakedQuad> $$13 = p_234403_.m_213637_(p_234404_, $$12, p_234409_);
            if ($$13.isEmpty()) continue;
            $$11.m_122159_(p_234405_, $$12);
            if (p_234408_ && !Block.m_152444_(p_234404_, p_234402_, p_234405_, $$12, $$11)) continue;
            int $$14 = LevelRenderer.m_109537_(p_234402_, p_234404_, $$11);
            this.m_111001_(p_234402_, p_234404_, p_234405_, $$14, p_234411_, false, p_234406_, p_234407_, $$13, $$10);
        }
        p_234409_.m_188584_(p_234410_);
        List<BakedQuad> $$15 = p_234403_.m_213637_(p_234404_, null, p_234409_);
        if (!$$15.isEmpty()) {
            this.m_111001_(p_234402_, p_234404_, p_234405_, -1, p_234411_, true, p_234406_, p_234407_, $$15, $$10);
        }
    }

    private void m_111012_(BlockAndTintGetter p_111013_, BlockState p_111014_, BlockPos p_111015_, PoseStack p_111016_, VertexConsumer p_111017_, List<BakedQuad> p_111018_, float[] p_111019_, BitSet p_111020_, AmbientOcclusionFace p_111021_, int p_111022_) {
        for (BakedQuad $$10 : p_111018_) {
            this.m_111039_(p_111013_, p_111014_, p_111015_, $$10.m_111303_(), $$10.m_111306_(), p_111019_, p_111020_);
            p_111021_.m_111167_(p_111013_, p_111014_, p_111015_, $$10.m_111306_(), p_111019_, p_111020_, $$10.m_111307_());
            this.m_111023_(p_111013_, p_111014_, p_111015_, p_111017_, p_111016_.m_85850_(), $$10, p_111021_.f_111149_[0], p_111021_.f_111149_[1], p_111021_.f_111149_[2], p_111021_.f_111149_[3], p_111021_.f_111150_[0], p_111021_.f_111150_[1], p_111021_.f_111150_[2], p_111021_.f_111150_[3], p_111022_);
        }
    }

    private void m_111023_(BlockAndTintGetter p_111024_, BlockState p_111025_, BlockPos p_111026_, VertexConsumer p_111027_, PoseStack.Pose p_111028_, BakedQuad p_111029_, float p_111030_, float p_111031_, float p_111032_, float p_111033_, int p_111034_, int p_111035_, int p_111036_, int p_111037_, int p_111038_) {
        float $$21;
        float $$20;
        float $$19;
        if (p_111029_.m_111304_()) {
            int $$15 = this.f_110995_.m_92577_(p_111025_, p_111024_, p_111026_, p_111029_.m_111305_());
            float $$16 = (float)($$15 >> 16 & 0xFF) / 255.0f;
            float $$17 = (float)($$15 >> 8 & 0xFF) / 255.0f;
            float $$18 = (float)($$15 & 0xFF) / 255.0f;
        } else {
            $$19 = 1.0f;
            $$20 = 1.0f;
            $$21 = 1.0f;
        }
        p_111027_.m_85995_(p_111028_, p_111029_, new float[]{p_111030_, p_111031_, p_111032_, p_111033_}, $$19, $$20, $$21, new int[]{p_111034_, p_111035_, p_111036_, p_111037_}, p_111038_, true);
    }

    private void m_111039_(BlockAndTintGetter p_111040_, BlockState p_111041_, BlockPos p_111042_, int[] p_111043_, Direction p_111044_, @Nullable float[] p_111045_, BitSet p_111046_) {
        float $$7 = 32.0f;
        float $$8 = 32.0f;
        float $$9 = 32.0f;
        float $$10 = -32.0f;
        float $$11 = -32.0f;
        float $$12 = -32.0f;
        for (int $$13 = 0; $$13 < 4; ++$$13) {
            float $$14 = Float.intBitsToFloat(p_111043_[$$13 * 8]);
            float $$15 = Float.intBitsToFloat(p_111043_[$$13 * 8 + 1]);
            float $$16 = Float.intBitsToFloat(p_111043_[$$13 * 8 + 2]);
            $$7 = Math.min($$7, $$14);
            $$8 = Math.min($$8, $$15);
            $$9 = Math.min($$9, $$16);
            $$10 = Math.max($$10, $$14);
            $$11 = Math.max($$11, $$15);
            $$12 = Math.max($$12, $$16);
        }
        if (p_111045_ != null) {
            p_111045_[Direction.WEST.m_122411_()] = $$7;
            p_111045_[Direction.EAST.m_122411_()] = $$10;
            p_111045_[Direction.DOWN.m_122411_()] = $$8;
            p_111045_[Direction.UP.m_122411_()] = $$11;
            p_111045_[Direction.NORTH.m_122411_()] = $$9;
            p_111045_[Direction.SOUTH.m_122411_()] = $$12;
            int $$17 = f_173405_.length;
            p_111045_[Direction.WEST.m_122411_() + $$17] = 1.0f - $$7;
            p_111045_[Direction.EAST.m_122411_() + $$17] = 1.0f - $$10;
            p_111045_[Direction.DOWN.m_122411_() + $$17] = 1.0f - $$8;
            p_111045_[Direction.UP.m_122411_() + $$17] = 1.0f - $$11;
            p_111045_[Direction.NORTH.m_122411_() + $$17] = 1.0f - $$9;
            p_111045_[Direction.SOUTH.m_122411_() + $$17] = 1.0f - $$12;
        }
        float $$18 = 1.0E-4f;
        float $$19 = 0.9999f;
        switch (p_111044_) {
            case DOWN: {
                p_111046_.set(1, $$7 >= 1.0E-4f || $$9 >= 1.0E-4f || $$10 <= 0.9999f || $$12 <= 0.9999f);
                p_111046_.set(0, $$8 == $$11 && ($$8 < 1.0E-4f || p_111041_.m_60838_(p_111040_, p_111042_)));
                break;
            }
            case UP: {
                p_111046_.set(1, $$7 >= 1.0E-4f || $$9 >= 1.0E-4f || $$10 <= 0.9999f || $$12 <= 0.9999f);
                p_111046_.set(0, $$8 == $$11 && ($$11 > 0.9999f || p_111041_.m_60838_(p_111040_, p_111042_)));
                break;
            }
            case NORTH: {
                p_111046_.set(1, $$7 >= 1.0E-4f || $$8 >= 1.0E-4f || $$10 <= 0.9999f || $$11 <= 0.9999f);
                p_111046_.set(0, $$9 == $$12 && ($$9 < 1.0E-4f || p_111041_.m_60838_(p_111040_, p_111042_)));
                break;
            }
            case SOUTH: {
                p_111046_.set(1, $$7 >= 1.0E-4f || $$8 >= 1.0E-4f || $$10 <= 0.9999f || $$11 <= 0.9999f);
                p_111046_.set(0, $$9 == $$12 && ($$12 > 0.9999f || p_111041_.m_60838_(p_111040_, p_111042_)));
                break;
            }
            case WEST: {
                p_111046_.set(1, $$8 >= 1.0E-4f || $$9 >= 1.0E-4f || $$11 <= 0.9999f || $$12 <= 0.9999f);
                p_111046_.set(0, $$7 == $$10 && ($$7 < 1.0E-4f || p_111041_.m_60838_(p_111040_, p_111042_)));
                break;
            }
            case EAST: {
                p_111046_.set(1, $$8 >= 1.0E-4f || $$9 >= 1.0E-4f || $$11 <= 0.9999f || $$12 <= 0.9999f);
                p_111046_.set(0, $$7 == $$10 && ($$10 > 0.9999f || p_111041_.m_60838_(p_111040_, p_111042_)));
            }
        }
    }

    private void m_111001_(BlockAndTintGetter p_111002_, BlockState p_111003_, BlockPos p_111004_, int p_111005_, int p_111006_, boolean p_111007_, PoseStack p_111008_, VertexConsumer p_111009_, List<BakedQuad> p_111010_, BitSet p_111011_) {
        for (BakedQuad $$10 : p_111010_) {
            if (p_111007_) {
                this.m_111039_(p_111002_, p_111003_, p_111004_, $$10.m_111303_(), $$10.m_111306_(), null, p_111011_);
                BlockPos $$11 = p_111011_.get(0) ? p_111004_.m_121945_($$10.m_111306_()) : p_111004_;
                p_111005_ = LevelRenderer.m_109537_(p_111002_, p_111003_, $$11);
            }
            float $$12 = p_111002_.m_7717_($$10.m_111306_(), $$10.m_111307_());
            this.m_111023_(p_111002_, p_111003_, p_111004_, p_111009_, p_111008_.m_85850_(), $$10, $$12, $$12, $$12, $$12, p_111005_, p_111005_, p_111005_, p_111005_, p_111006_);
        }
    }

    public void m_111067_(PoseStack.Pose p_111068_, VertexConsumer p_111069_, @Nullable BlockState p_111070_, BakedModel p_111071_, float p_111072_, float p_111073_, float p_111074_, int p_111075_, int p_111076_) {
        RandomSource $$9 = RandomSource.m_216327_();
        long $$10 = 42L;
        for (Direction $$11 : f_173405_) {
            $$9.m_188584_(42L);
            ModelBlockRenderer.m_111058_(p_111068_, p_111069_, p_111072_, p_111073_, p_111074_, p_111071_.m_213637_(p_111070_, $$11, $$9), p_111075_, p_111076_);
        }
        $$9.m_188584_(42L);
        ModelBlockRenderer.m_111058_(p_111068_, p_111069_, p_111072_, p_111073_, p_111074_, p_111071_.m_213637_(p_111070_, null, $$9), p_111075_, p_111076_);
    }

    private static void m_111058_(PoseStack.Pose p_111059_, VertexConsumer p_111060_, float p_111061_, float p_111062_, float p_111063_, List<BakedQuad> p_111064_, int p_111065_, int p_111066_) {
        for (BakedQuad $$8 : p_111064_) {
            float $$14;
            float $$13;
            float $$12;
            if ($$8.m_111304_()) {
                float $$9 = Mth.m_14036_(p_111061_, 0.0f, 1.0f);
                float $$10 = Mth.m_14036_(p_111062_, 0.0f, 1.0f);
                float $$11 = Mth.m_14036_(p_111063_, 0.0f, 1.0f);
            } else {
                $$12 = 1.0f;
                $$13 = 1.0f;
                $$14 = 1.0f;
            }
            p_111060_.m_85987_(p_111059_, $$8, $$12, $$13, $$14, p_111065_, p_111066_);
        }
    }

    public static void m_111000_() {
        f_110996_.get().m_111220_();
    }

    public static void m_111077_() {
        f_110996_.get().m_111225_();
    }

    static class AmbientOcclusionFace {
        final float[] f_111149_ = new float[4];
        final int[] f_111150_ = new int[4];

        public void m_111167_(BlockAndTintGetter p_111168_, BlockState p_111169_, BlockPos p_111170_, Direction p_111171_, float[] p_111172_, BitSet p_111173_, boolean p_111174_) {
            int $$50;
            float $$49;
            int $$45;
            float $$44;
            int $$40;
            float $$39;
            int $$35;
            float $$34;
            boolean $$30;
            BlockPos $$7 = p_111173_.get(0) ? p_111170_.m_121945_(p_111171_) : p_111170_;
            AdjacencyInfo $$8 = AdjacencyInfo.m_111131_(p_111171_);
            BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
            Cache $$10 = f_110996_.get();
            $$9.m_122159_($$7, $$8.f_111110_[0]);
            BlockState $$11 = p_111168_.m_8055_($$9);
            int $$12 = $$10.m_111221_($$11, p_111168_, $$9);
            float $$13 = $$10.m_111226_($$11, p_111168_, $$9);
            $$9.m_122159_($$7, $$8.f_111110_[1]);
            BlockState $$14 = p_111168_.m_8055_($$9);
            int $$15 = $$10.m_111221_($$14, p_111168_, $$9);
            float $$16 = $$10.m_111226_($$14, p_111168_, $$9);
            $$9.m_122159_($$7, $$8.f_111110_[2]);
            BlockState $$17 = p_111168_.m_8055_($$9);
            int $$18 = $$10.m_111221_($$17, p_111168_, $$9);
            float $$19 = $$10.m_111226_($$17, p_111168_, $$9);
            $$9.m_122159_($$7, $$8.f_111110_[3]);
            BlockState $$20 = p_111168_.m_8055_($$9);
            int $$21 = $$10.m_111221_($$20, p_111168_, $$9);
            float $$22 = $$10.m_111226_($$20, p_111168_, $$9);
            BlockState $$23 = p_111168_.m_8055_($$9.m_122159_($$7, $$8.f_111110_[0]).m_122173_(p_111171_));
            boolean $$24 = !$$23.m_60831_(p_111168_, $$9) || $$23.m_60739_(p_111168_, $$9) == 0;
            BlockState $$25 = p_111168_.m_8055_($$9.m_122159_($$7, $$8.f_111110_[1]).m_122173_(p_111171_));
            boolean $$26 = !$$25.m_60831_(p_111168_, $$9) || $$25.m_60739_(p_111168_, $$9) == 0;
            BlockState $$27 = p_111168_.m_8055_($$9.m_122159_($$7, $$8.f_111110_[2]).m_122173_(p_111171_));
            boolean $$28 = !$$27.m_60831_(p_111168_, $$9) || $$27.m_60739_(p_111168_, $$9) == 0;
            BlockState $$29 = p_111168_.m_8055_($$9.m_122159_($$7, $$8.f_111110_[3]).m_122173_(p_111171_));
            boolean bl = $$30 = !$$29.m_60831_(p_111168_, $$9) || $$29.m_60739_(p_111168_, $$9) == 0;
            if ($$28 || $$24) {
                $$9.m_122159_($$7, $$8.f_111110_[0]).m_122173_($$8.f_111110_[2]);
                BlockState $$31 = p_111168_.m_8055_($$9);
                float $$32 = $$10.m_111226_($$31, p_111168_, $$9);
                int $$33 = $$10.m_111221_($$31, p_111168_, $$9);
            } else {
                $$34 = $$13;
                $$35 = $$12;
            }
            if ($$30 || $$24) {
                $$9.m_122159_($$7, $$8.f_111110_[0]).m_122173_($$8.f_111110_[3]);
                BlockState $$36 = p_111168_.m_8055_($$9);
                float $$37 = $$10.m_111226_($$36, p_111168_, $$9);
                int $$38 = $$10.m_111221_($$36, p_111168_, $$9);
            } else {
                $$39 = $$13;
                $$40 = $$12;
            }
            if ($$28 || $$26) {
                $$9.m_122159_($$7, $$8.f_111110_[1]).m_122173_($$8.f_111110_[2]);
                BlockState $$41 = p_111168_.m_8055_($$9);
                float $$42 = $$10.m_111226_($$41, p_111168_, $$9);
                int $$43 = $$10.m_111221_($$41, p_111168_, $$9);
            } else {
                $$44 = $$13;
                $$45 = $$12;
            }
            if ($$30 || $$26) {
                $$9.m_122159_($$7, $$8.f_111110_[1]).m_122173_($$8.f_111110_[3]);
                BlockState $$46 = p_111168_.m_8055_($$9);
                float $$47 = $$10.m_111226_($$46, p_111168_, $$9);
                int $$48 = $$10.m_111221_($$46, p_111168_, $$9);
            } else {
                $$49 = $$13;
                $$50 = $$12;
            }
            int $$51 = $$10.m_111221_(p_111169_, p_111168_, p_111170_);
            $$9.m_122159_(p_111170_, p_111171_);
            BlockState $$52 = p_111168_.m_8055_($$9);
            if (p_111173_.get(0) || !$$52.m_60804_(p_111168_, $$9)) {
                $$51 = $$10.m_111221_($$52, p_111168_, $$9);
            }
            float $$53 = p_111173_.get(0) ? $$10.m_111226_(p_111168_.m_8055_($$7), p_111168_, $$7) : $$10.m_111226_(p_111168_.m_8055_(p_111170_), p_111168_, p_111170_);
            AmbientVertexRemap $$54 = AmbientVertexRemap.m_111201_(p_111171_);
            if (!p_111173_.get(1) || !$$8.f_111111_) {
                float $$55 = ($$22 + $$13 + $$39 + $$53) * 0.25f;
                float $$56 = ($$19 + $$13 + $$34 + $$53) * 0.25f;
                float $$57 = ($$19 + $$16 + $$44 + $$53) * 0.25f;
                float $$58 = ($$22 + $$16 + $$49 + $$53) * 0.25f;
                this.f_111150_[$$54.f_111185_] = this.m_111153_($$21, $$12, $$40, $$51);
                this.f_111150_[$$54.f_111186_] = this.m_111153_($$18, $$12, $$35, $$51);
                this.f_111150_[$$54.f_111187_] = this.m_111153_($$18, $$15, $$45, $$51);
                this.f_111150_[$$54.f_111188_] = this.m_111153_($$21, $$15, $$50, $$51);
                this.f_111149_[$$54.f_111185_] = $$55;
                this.f_111149_[$$54.f_111186_] = $$56;
                this.f_111149_[$$54.f_111187_] = $$57;
                this.f_111149_[$$54.f_111188_] = $$58;
            } else {
                float $$59 = ($$22 + $$13 + $$39 + $$53) * 0.25f;
                float $$60 = ($$19 + $$13 + $$34 + $$53) * 0.25f;
                float $$61 = ($$19 + $$16 + $$44 + $$53) * 0.25f;
                float $$62 = ($$22 + $$16 + $$49 + $$53) * 0.25f;
                float $$63 = p_111172_[$$8.f_111112_[0].f_111258_] * p_111172_[$$8.f_111112_[1].f_111258_];
                float $$64 = p_111172_[$$8.f_111112_[2].f_111258_] * p_111172_[$$8.f_111112_[3].f_111258_];
                float $$65 = p_111172_[$$8.f_111112_[4].f_111258_] * p_111172_[$$8.f_111112_[5].f_111258_];
                float $$66 = p_111172_[$$8.f_111112_[6].f_111258_] * p_111172_[$$8.f_111112_[7].f_111258_];
                float $$67 = p_111172_[$$8.f_111113_[0].f_111258_] * p_111172_[$$8.f_111113_[1].f_111258_];
                float $$68 = p_111172_[$$8.f_111113_[2].f_111258_] * p_111172_[$$8.f_111113_[3].f_111258_];
                float $$69 = p_111172_[$$8.f_111113_[4].f_111258_] * p_111172_[$$8.f_111113_[5].f_111258_];
                float $$70 = p_111172_[$$8.f_111113_[6].f_111258_] * p_111172_[$$8.f_111113_[7].f_111258_];
                float $$71 = p_111172_[$$8.f_111114_[0].f_111258_] * p_111172_[$$8.f_111114_[1].f_111258_];
                float $$72 = p_111172_[$$8.f_111114_[2].f_111258_] * p_111172_[$$8.f_111114_[3].f_111258_];
                float $$73 = p_111172_[$$8.f_111114_[4].f_111258_] * p_111172_[$$8.f_111114_[5].f_111258_];
                float $$74 = p_111172_[$$8.f_111114_[6].f_111258_] * p_111172_[$$8.f_111114_[7].f_111258_];
                float $$75 = p_111172_[$$8.f_111115_[0].f_111258_] * p_111172_[$$8.f_111115_[1].f_111258_];
                float $$76 = p_111172_[$$8.f_111115_[2].f_111258_] * p_111172_[$$8.f_111115_[3].f_111258_];
                float $$77 = p_111172_[$$8.f_111115_[4].f_111258_] * p_111172_[$$8.f_111115_[5].f_111258_];
                float $$78 = p_111172_[$$8.f_111115_[6].f_111258_] * p_111172_[$$8.f_111115_[7].f_111258_];
                this.f_111149_[$$54.f_111185_] = $$59 * $$63 + $$60 * $$64 + $$61 * $$65 + $$62 * $$66;
                this.f_111149_[$$54.f_111186_] = $$59 * $$67 + $$60 * $$68 + $$61 * $$69 + $$62 * $$70;
                this.f_111149_[$$54.f_111187_] = $$59 * $$71 + $$60 * $$72 + $$61 * $$73 + $$62 * $$74;
                this.f_111149_[$$54.f_111188_] = $$59 * $$75 + $$60 * $$76 + $$61 * $$77 + $$62 * $$78;
                int $$79 = this.m_111153_($$21, $$12, $$40, $$51);
                int $$80 = this.m_111153_($$18, $$12, $$35, $$51);
                int $$81 = this.m_111153_($$18, $$15, $$45, $$51);
                int $$82 = this.m_111153_($$21, $$15, $$50, $$51);
                this.f_111150_[$$54.f_111185_] = this.m_111158_($$79, $$80, $$81, $$82, $$63, $$64, $$65, $$66);
                this.f_111150_[$$54.f_111186_] = this.m_111158_($$79, $$80, $$81, $$82, $$67, $$68, $$69, $$70);
                this.f_111150_[$$54.f_111187_] = this.m_111158_($$79, $$80, $$81, $$82, $$71, $$72, $$73, $$74);
                this.f_111150_[$$54.f_111188_] = this.m_111158_($$79, $$80, $$81, $$82, $$75, $$76, $$77, $$78);
            }
            float $$83 = p_111168_.m_7717_(p_111171_, p_111174_);
            int $$84 = 0;
            while ($$84 < this.f_111149_.length) {
                int n = $$84++;
                this.f_111149_[n] = this.f_111149_[n] * $$83;
            }
        }

        private int m_111153_(int p_111154_, int p_111155_, int p_111156_, int p_111157_) {
            if (p_111154_ == 0) {
                p_111154_ = p_111157_;
            }
            if (p_111155_ == 0) {
                p_111155_ = p_111157_;
            }
            if (p_111156_ == 0) {
                p_111156_ = p_111157_;
            }
            return p_111154_ + p_111155_ + p_111156_ + p_111157_ >> 2 & 0xFF00FF;
        }

        private int m_111158_(int p_111159_, int p_111160_, int p_111161_, int p_111162_, float p_111163_, float p_111164_, float p_111165_, float p_111166_) {
            int $$8 = (int)((float)(p_111159_ >> 16 & 0xFF) * p_111163_ + (float)(p_111160_ >> 16 & 0xFF) * p_111164_ + (float)(p_111161_ >> 16 & 0xFF) * p_111165_ + (float)(p_111162_ >> 16 & 0xFF) * p_111166_) & 0xFF;
            int $$9 = (int)((float)(p_111159_ & 0xFF) * p_111163_ + (float)(p_111160_ & 0xFF) * p_111164_ + (float)(p_111161_ & 0xFF) * p_111165_ + (float)(p_111162_ & 0xFF) * p_111166_) & 0xFF;
            return $$8 << 16 | $$9;
        }
    }

    static class Cache {
        private boolean f_111214_;
        private final Long2IntLinkedOpenHashMap f_111215_ = Util.m_137537_(() -> {
            Long2IntLinkedOpenHashMap $$0 = new Long2IntLinkedOpenHashMap(100, 0.25f){

                protected void rehash(int p_111238_) {
                }
            };
            $$0.defaultReturnValue(Integer.MAX_VALUE);
            return $$0;
        });
        private final Long2FloatLinkedOpenHashMap f_111216_ = Util.m_137537_(() -> {
            Long2FloatLinkedOpenHashMap $$0 = new Long2FloatLinkedOpenHashMap(100, 0.25f){

                protected void rehash(int p_111245_) {
                }
            };
            $$0.defaultReturnValue(Float.NaN);
            return $$0;
        });

        private Cache() {
        }

        public void m_111220_() {
            this.f_111214_ = true;
        }

        public void m_111225_() {
            this.f_111214_ = false;
            this.f_111215_.clear();
            this.f_111216_.clear();
        }

        public int m_111221_(BlockState p_111222_, BlockAndTintGetter p_111223_, BlockPos p_111224_) {
            int $$4;
            long $$3 = p_111224_.m_121878_();
            if (this.f_111214_ && ($$4 = this.f_111215_.get($$3)) != Integer.MAX_VALUE) {
                return $$4;
            }
            int $$5 = LevelRenderer.m_109537_(p_111223_, p_111222_, p_111224_);
            if (this.f_111214_) {
                if (this.f_111215_.size() == 100) {
                    this.f_111215_.removeFirstInt();
                }
                this.f_111215_.put($$3, $$5);
            }
            return $$5;
        }

        public float m_111226_(BlockState p_111227_, BlockAndTintGetter p_111228_, BlockPos p_111229_) {
            float $$4;
            long $$3 = p_111229_.m_121878_();
            if (this.f_111214_ && !Float.isNaN($$4 = this.f_111216_.get($$3))) {
                return $$4;
            }
            float $$5 = p_111227_.m_60792_(p_111228_, p_111229_);
            if (this.f_111214_) {
                if (this.f_111216_.size() == 100) {
                    this.f_111216_.removeFirstFloat();
                }
                this.f_111216_.put($$3, $$5);
            }
            return $$5;
        }
    }

    protected static final class AdjacencyInfo
    extends Enum<AdjacencyInfo> {
        public static final /* enum */ AdjacencyInfo DOWN = new AdjacencyInfo(new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH}, 0.5f, true, new SizeInfo[]{SizeInfo.FLIP_WEST, SizeInfo.SOUTH, SizeInfo.FLIP_WEST, SizeInfo.FLIP_SOUTH, SizeInfo.WEST, SizeInfo.FLIP_SOUTH, SizeInfo.WEST, SizeInfo.SOUTH}, new SizeInfo[]{SizeInfo.FLIP_WEST, SizeInfo.NORTH, SizeInfo.FLIP_WEST, SizeInfo.FLIP_NORTH, SizeInfo.WEST, SizeInfo.FLIP_NORTH, SizeInfo.WEST, SizeInfo.NORTH}, new SizeInfo[]{SizeInfo.FLIP_EAST, SizeInfo.NORTH, SizeInfo.FLIP_EAST, SizeInfo.FLIP_NORTH, SizeInfo.EAST, SizeInfo.FLIP_NORTH, SizeInfo.EAST, SizeInfo.NORTH}, new SizeInfo[]{SizeInfo.FLIP_EAST, SizeInfo.SOUTH, SizeInfo.FLIP_EAST, SizeInfo.FLIP_SOUTH, SizeInfo.EAST, SizeInfo.FLIP_SOUTH, SizeInfo.EAST, SizeInfo.SOUTH});
        public static final /* enum */ AdjacencyInfo UP = new AdjacencyInfo(new Direction[]{Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH}, 1.0f, true, new SizeInfo[]{SizeInfo.EAST, SizeInfo.SOUTH, SizeInfo.EAST, SizeInfo.FLIP_SOUTH, SizeInfo.FLIP_EAST, SizeInfo.FLIP_SOUTH, SizeInfo.FLIP_EAST, SizeInfo.SOUTH}, new SizeInfo[]{SizeInfo.EAST, SizeInfo.NORTH, SizeInfo.EAST, SizeInfo.FLIP_NORTH, SizeInfo.FLIP_EAST, SizeInfo.FLIP_NORTH, SizeInfo.FLIP_EAST, SizeInfo.NORTH}, new SizeInfo[]{SizeInfo.WEST, SizeInfo.NORTH, SizeInfo.WEST, SizeInfo.FLIP_NORTH, SizeInfo.FLIP_WEST, SizeInfo.FLIP_NORTH, SizeInfo.FLIP_WEST, SizeInfo.NORTH}, new SizeInfo[]{SizeInfo.WEST, SizeInfo.SOUTH, SizeInfo.WEST, SizeInfo.FLIP_SOUTH, SizeInfo.FLIP_WEST, SizeInfo.FLIP_SOUTH, SizeInfo.FLIP_WEST, SizeInfo.SOUTH});
        public static final /* enum */ AdjacencyInfo NORTH = new AdjacencyInfo(new Direction[]{Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST}, 0.8f, true, new SizeInfo[]{SizeInfo.UP, SizeInfo.FLIP_WEST, SizeInfo.UP, SizeInfo.WEST, SizeInfo.FLIP_UP, SizeInfo.WEST, SizeInfo.FLIP_UP, SizeInfo.FLIP_WEST}, new SizeInfo[]{SizeInfo.UP, SizeInfo.FLIP_EAST, SizeInfo.UP, SizeInfo.EAST, SizeInfo.FLIP_UP, SizeInfo.EAST, SizeInfo.FLIP_UP, SizeInfo.FLIP_EAST}, new SizeInfo[]{SizeInfo.DOWN, SizeInfo.FLIP_EAST, SizeInfo.DOWN, SizeInfo.EAST, SizeInfo.FLIP_DOWN, SizeInfo.EAST, SizeInfo.FLIP_DOWN, SizeInfo.FLIP_EAST}, new SizeInfo[]{SizeInfo.DOWN, SizeInfo.FLIP_WEST, SizeInfo.DOWN, SizeInfo.WEST, SizeInfo.FLIP_DOWN, SizeInfo.WEST, SizeInfo.FLIP_DOWN, SizeInfo.FLIP_WEST});
        public static final /* enum */ AdjacencyInfo SOUTH = new AdjacencyInfo(new Direction[]{Direction.WEST, Direction.EAST, Direction.DOWN, Direction.UP}, 0.8f, true, new SizeInfo[]{SizeInfo.UP, SizeInfo.FLIP_WEST, SizeInfo.FLIP_UP, SizeInfo.FLIP_WEST, SizeInfo.FLIP_UP, SizeInfo.WEST, SizeInfo.UP, SizeInfo.WEST}, new SizeInfo[]{SizeInfo.DOWN, SizeInfo.FLIP_WEST, SizeInfo.FLIP_DOWN, SizeInfo.FLIP_WEST, SizeInfo.FLIP_DOWN, SizeInfo.WEST, SizeInfo.DOWN, SizeInfo.WEST}, new SizeInfo[]{SizeInfo.DOWN, SizeInfo.FLIP_EAST, SizeInfo.FLIP_DOWN, SizeInfo.FLIP_EAST, SizeInfo.FLIP_DOWN, SizeInfo.EAST, SizeInfo.DOWN, SizeInfo.EAST}, new SizeInfo[]{SizeInfo.UP, SizeInfo.FLIP_EAST, SizeInfo.FLIP_UP, SizeInfo.FLIP_EAST, SizeInfo.FLIP_UP, SizeInfo.EAST, SizeInfo.UP, SizeInfo.EAST});
        public static final /* enum */ AdjacencyInfo WEST = new AdjacencyInfo(new Direction[]{Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH}, 0.6f, true, new SizeInfo[]{SizeInfo.UP, SizeInfo.SOUTH, SizeInfo.UP, SizeInfo.FLIP_SOUTH, SizeInfo.FLIP_UP, SizeInfo.FLIP_SOUTH, SizeInfo.FLIP_UP, SizeInfo.SOUTH}, new SizeInfo[]{SizeInfo.UP, SizeInfo.NORTH, SizeInfo.UP, SizeInfo.FLIP_NORTH, SizeInfo.FLIP_UP, SizeInfo.FLIP_NORTH, SizeInfo.FLIP_UP, SizeInfo.NORTH}, new SizeInfo[]{SizeInfo.DOWN, SizeInfo.NORTH, SizeInfo.DOWN, SizeInfo.FLIP_NORTH, SizeInfo.FLIP_DOWN, SizeInfo.FLIP_NORTH, SizeInfo.FLIP_DOWN, SizeInfo.NORTH}, new SizeInfo[]{SizeInfo.DOWN, SizeInfo.SOUTH, SizeInfo.DOWN, SizeInfo.FLIP_SOUTH, SizeInfo.FLIP_DOWN, SizeInfo.FLIP_SOUTH, SizeInfo.FLIP_DOWN, SizeInfo.SOUTH});
        public static final /* enum */ AdjacencyInfo EAST = new AdjacencyInfo(new Direction[]{Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH}, 0.6f, true, new SizeInfo[]{SizeInfo.FLIP_DOWN, SizeInfo.SOUTH, SizeInfo.FLIP_DOWN, SizeInfo.FLIP_SOUTH, SizeInfo.DOWN, SizeInfo.FLIP_SOUTH, SizeInfo.DOWN, SizeInfo.SOUTH}, new SizeInfo[]{SizeInfo.FLIP_DOWN, SizeInfo.NORTH, SizeInfo.FLIP_DOWN, SizeInfo.FLIP_NORTH, SizeInfo.DOWN, SizeInfo.FLIP_NORTH, SizeInfo.DOWN, SizeInfo.NORTH}, new SizeInfo[]{SizeInfo.FLIP_UP, SizeInfo.NORTH, SizeInfo.FLIP_UP, SizeInfo.FLIP_NORTH, SizeInfo.UP, SizeInfo.FLIP_NORTH, SizeInfo.UP, SizeInfo.NORTH}, new SizeInfo[]{SizeInfo.FLIP_UP, SizeInfo.SOUTH, SizeInfo.FLIP_UP, SizeInfo.FLIP_SOUTH, SizeInfo.UP, SizeInfo.FLIP_SOUTH, SizeInfo.UP, SizeInfo.SOUTH});
        final Direction[] f_111110_;
        final boolean f_111111_;
        final SizeInfo[] f_111112_;
        final SizeInfo[] f_111113_;
        final SizeInfo[] f_111114_;
        final SizeInfo[] f_111115_;
        private static final AdjacencyInfo[] f_111116_;
        private static final /* synthetic */ AdjacencyInfo[] $VALUES;

        public static AdjacencyInfo[] values() {
            return (AdjacencyInfo[])$VALUES.clone();
        }

        public static AdjacencyInfo valueOf(String p_111146_) {
            return Enum.valueOf(AdjacencyInfo.class, p_111146_);
        }

        private AdjacencyInfo(Direction[] p_111122_, float p_111123_, boolean p_111124_, SizeInfo[] p_111125_, SizeInfo[] p_111126_, SizeInfo[] p_111127_, SizeInfo[] p_111128_) {
            this.f_111110_ = p_111122_;
            this.f_111111_ = p_111124_;
            this.f_111112_ = p_111125_;
            this.f_111113_ = p_111126_;
            this.f_111114_ = p_111127_;
            this.f_111115_ = p_111128_;
        }

        public static AdjacencyInfo m_111131_(Direction p_111132_) {
            return f_111116_[p_111132_.m_122411_()];
        }

        private static /* synthetic */ AdjacencyInfo[] m_173407_() {
            return new AdjacencyInfo[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
        }

        static {
            $VALUES = AdjacencyInfo.m_173407_();
            f_111116_ = Util.m_137469_(new AdjacencyInfo[6], p_111134_ -> {
                p_111134_[Direction.DOWN.m_122411_()] = DOWN;
                p_111134_[Direction.UP.m_122411_()] = UP;
                p_111134_[Direction.NORTH.m_122411_()] = NORTH;
                p_111134_[Direction.SOUTH.m_122411_()] = SOUTH;
                p_111134_[Direction.WEST.m_122411_()] = WEST;
                p_111134_[Direction.EAST.m_122411_()] = EAST;
            });
        }
    }

    protected static final class SizeInfo
    extends Enum<SizeInfo> {
        public static final /* enum */ SizeInfo DOWN = new SizeInfo(Direction.DOWN, false);
        public static final /* enum */ SizeInfo UP = new SizeInfo(Direction.UP, false);
        public static final /* enum */ SizeInfo NORTH = new SizeInfo(Direction.NORTH, false);
        public static final /* enum */ SizeInfo SOUTH = new SizeInfo(Direction.SOUTH, false);
        public static final /* enum */ SizeInfo WEST = new SizeInfo(Direction.WEST, false);
        public static final /* enum */ SizeInfo EAST = new SizeInfo(Direction.EAST, false);
        public static final /* enum */ SizeInfo FLIP_DOWN = new SizeInfo(Direction.DOWN, true);
        public static final /* enum */ SizeInfo FLIP_UP = new SizeInfo(Direction.UP, true);
        public static final /* enum */ SizeInfo FLIP_NORTH = new SizeInfo(Direction.NORTH, true);
        public static final /* enum */ SizeInfo FLIP_SOUTH = new SizeInfo(Direction.SOUTH, true);
        public static final /* enum */ SizeInfo FLIP_WEST = new SizeInfo(Direction.WEST, true);
        public static final /* enum */ SizeInfo FLIP_EAST = new SizeInfo(Direction.EAST, true);
        final int f_111258_;
        private static final /* synthetic */ SizeInfo[] $VALUES;

        public static SizeInfo[] values() {
            return (SizeInfo[])$VALUES.clone();
        }

        public static SizeInfo valueOf(String p_111269_) {
            return Enum.valueOf(SizeInfo.class, p_111269_);
        }

        private SizeInfo(Direction p_111264_, boolean p_111265_) {
            this.f_111258_ = p_111264_.m_122411_() + (p_111265_ ? f_173405_.length : 0);
        }

        private static /* synthetic */ SizeInfo[] m_173409_() {
            return new SizeInfo[]{DOWN, UP, NORTH, SOUTH, WEST, EAST, FLIP_DOWN, FLIP_UP, FLIP_NORTH, FLIP_SOUTH, FLIP_WEST, FLIP_EAST};
        }

        static {
            $VALUES = SizeInfo.m_173409_();
        }
    }

    static final class AmbientVertexRemap
    extends Enum<AmbientVertexRemap> {
        public static final /* enum */ AmbientVertexRemap DOWN = new AmbientVertexRemap(0, 1, 2, 3);
        public static final /* enum */ AmbientVertexRemap UP = new AmbientVertexRemap(2, 3, 0, 1);
        public static final /* enum */ AmbientVertexRemap NORTH = new AmbientVertexRemap(3, 0, 1, 2);
        public static final /* enum */ AmbientVertexRemap SOUTH = new AmbientVertexRemap(0, 1, 2, 3);
        public static final /* enum */ AmbientVertexRemap WEST = new AmbientVertexRemap(3, 0, 1, 2);
        public static final /* enum */ AmbientVertexRemap EAST = new AmbientVertexRemap(1, 2, 3, 0);
        final int f_111185_;
        final int f_111186_;
        final int f_111187_;
        final int f_111188_;
        private static final AmbientVertexRemap[] f_111189_;
        private static final /* synthetic */ AmbientVertexRemap[] $VALUES;

        public static AmbientVertexRemap[] values() {
            return (AmbientVertexRemap[])$VALUES.clone();
        }

        public static AmbientVertexRemap valueOf(String p_111212_) {
            return Enum.valueOf(AmbientVertexRemap.class, p_111212_);
        }

        private AmbientVertexRemap(int p_111195_, int p_111196_, int p_111197_, int p_111198_) {
            this.f_111185_ = p_111195_;
            this.f_111186_ = p_111196_;
            this.f_111187_ = p_111197_;
            this.f_111188_ = p_111198_;
        }

        public static AmbientVertexRemap m_111201_(Direction p_111202_) {
            return f_111189_[p_111202_.m_122411_()];
        }

        private static /* synthetic */ AmbientVertexRemap[] m_173408_() {
            return new AmbientVertexRemap[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
        }

        static {
            $VALUES = AmbientVertexRemap.m_173408_();
            f_111189_ = Util.m_137469_(new AmbientVertexRemap[6], p_111204_ -> {
                p_111204_[Direction.DOWN.m_122411_()] = DOWN;
                p_111204_[Direction.UP.m_122411_()] = UP;
                p_111204_[Direction.NORTH.m_122411_()] = NORTH;
                p_111204_[Direction.SOUTH.m_122411_()] = SOUTH;
                p_111204_[Direction.WEST.m_122411_()] = WEST;
                p_111204_[Direction.EAST.m_122411_()] = EAST;
            });
        }
    }
}

