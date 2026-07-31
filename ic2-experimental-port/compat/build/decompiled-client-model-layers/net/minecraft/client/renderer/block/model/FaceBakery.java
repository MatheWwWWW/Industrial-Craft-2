/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.block.model;

import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.FaceInfo;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockElementRotation;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.BlockMath;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FaceBakery {
    public static final int f_173433_ = 8;
    private static final float f_111569_ = 1.0f / (float)Math.cos(0.3926991f) - 1.0f;
    private static final float f_111570_ = 1.0f / (float)Math.cos(0.7853981852531433) - 1.0f;
    public static final int f_173434_ = 4;
    private static final int f_173436_ = 3;
    public static final int f_173435_ = 4;

    public BakedQuad m_111600_(Vector3f p_111601_, Vector3f p_111602_, BlockElementFace p_111603_, TextureAtlasSprite p_111604_, Direction p_111605_, ModelState p_111606_, @Nullable BlockElementRotation p_111607_, boolean p_111608_, ResourceLocation p_111609_) {
        BlockFaceUV $$9 = p_111603_.f_111357_;
        if (p_111606_.m_7538_()) {
            $$9 = FaceBakery.m_111581_(p_111603_.f_111357_, p_111605_, p_111606_.m_6189_(), p_111609_);
        }
        float[] $$10 = new float[$$9.f_111387_.length];
        System.arraycopy($$9.f_111387_, 0, $$10, 0, $$10.length);
        float $$11 = p_111604_.m_118417_();
        float $$12 = ($$9.f_111387_[0] + $$9.f_111387_[0] + $$9.f_111387_[2] + $$9.f_111387_[2]) / 4.0f;
        float $$13 = ($$9.f_111387_[1] + $$9.f_111387_[1] + $$9.f_111387_[3] + $$9.f_111387_[3]) / 4.0f;
        $$9.f_111387_[0] = Mth.m_14179_($$11, $$9.f_111387_[0], $$12);
        $$9.f_111387_[2] = Mth.m_14179_($$11, $$9.f_111387_[2], $$12);
        $$9.f_111387_[1] = Mth.m_14179_($$11, $$9.f_111387_[1], $$13);
        $$9.f_111387_[3] = Mth.m_14179_($$11, $$9.f_111387_[3], $$13);
        int[] $$14 = this.m_111573_($$9, p_111604_, p_111605_, this.m_111592_(p_111601_, p_111602_), p_111606_.m_6189_(), p_111607_, p_111608_);
        Direction $$15 = FaceBakery.m_111612_($$14);
        System.arraycopy($$10, 0, $$9.f_111387_, 0, $$10.length);
        if (p_111607_ == null) {
            this.m_111630_($$14, $$15);
        }
        return new BakedQuad($$14, p_111603_.f_111355_, $$15, p_111604_, p_111608_);
    }

    public static BlockFaceUV m_111581_(BlockFaceUV p_111582_, Direction p_111583_, Transformation p_111584_, ResourceLocation p_111585_) {
        float $$22;
        float $$21;
        float $$18;
        float $$17;
        Matrix4f $$4 = BlockMath.m_121844_(p_111584_, p_111583_, () -> "Unable to resolve UVLock for model: " + p_111585_).m_121104_();
        float $$5 = p_111582_.m_111392_(p_111582_.m_111398_(0));
        float $$6 = p_111582_.m_111396_(p_111582_.m_111398_(0));
        Vector4f $$7 = new Vector4f($$5 / 16.0f, $$6 / 16.0f, 0.0f, 1.0f);
        $$7.m_123607_($$4);
        float $$8 = 16.0f * $$7.m_123601_();
        float $$9 = 16.0f * $$7.m_123615_();
        float $$10 = p_111582_.m_111392_(p_111582_.m_111398_(2));
        float $$11 = p_111582_.m_111396_(p_111582_.m_111398_(2));
        Vector4f $$12 = new Vector4f($$10 / 16.0f, $$11 / 16.0f, 0.0f, 1.0f);
        $$12.m_123607_($$4);
        float $$13 = 16.0f * $$12.m_123601_();
        float $$14 = 16.0f * $$12.m_123615_();
        if (Math.signum($$10 - $$5) == Math.signum($$13 - $$8)) {
            float $$15 = $$8;
            float $$16 = $$13;
        } else {
            $$17 = $$13;
            $$18 = $$8;
        }
        if (Math.signum($$11 - $$6) == Math.signum($$14 - $$9)) {
            float $$19 = $$9;
            float $$20 = $$14;
        } else {
            $$21 = $$14;
            $$22 = $$9;
        }
        float $$23 = (float)Math.toRadians(p_111582_.f_111388_);
        Vector3f $$24 = new Vector3f(Mth.m_14089_($$23), Mth.m_14031_($$23), 0.0f);
        Matrix3f $$25 = new Matrix3f($$4);
        $$24.m_122249_($$25);
        int $$26 = Math.floorMod(-((int)Math.round(Math.toDegrees(Math.atan2($$24.m_122260_(), $$24.m_122239_())) / 90.0)) * 90, 360);
        return new BlockFaceUV(new float[]{$$17, $$21, $$18, $$22}, $$26);
    }

    private int[] m_111573_(BlockFaceUV p_111574_, TextureAtlasSprite p_111575_, Direction p_111576_, float[] p_111577_, Transformation p_111578_, @Nullable BlockElementRotation p_111579_, boolean p_111580_) {
        int[] $$7 = new int[32];
        for (int $$8 = 0; $$8 < 4; ++$$8) {
            this.m_111620_($$7, $$8, p_111576_, p_111574_, p_111577_, p_111575_, p_111578_, p_111579_, p_111580_);
        }
        return $$7;
    }

    private float[] m_111592_(Vector3f p_111593_, Vector3f p_111594_) {
        float[] $$2 = new float[Direction.values().length];
        $$2[FaceInfo.Constants.f_108996_] = p_111593_.m_122239_() / 16.0f;
        $$2[FaceInfo.Constants.f_108995_] = p_111593_.m_122260_() / 16.0f;
        $$2[FaceInfo.Constants.f_108994_] = p_111593_.m_122269_() / 16.0f;
        $$2[FaceInfo.Constants.f_108993_] = p_111594_.m_122239_() / 16.0f;
        $$2[FaceInfo.Constants.f_108992_] = p_111594_.m_122260_() / 16.0f;
        $$2[FaceInfo.Constants.f_108991_] = p_111594_.m_122269_() / 16.0f;
        return $$2;
    }

    private void m_111620_(int[] p_111621_, int p_111622_, Direction p_111623_, BlockFaceUV p_111624_, float[] p_111625_, TextureAtlasSprite p_111626_, Transformation p_111627_, @Nullable BlockElementRotation p_111628_, boolean p_111629_) {
        FaceInfo.VertexInfo $$9 = FaceInfo.m_108984_(p_111623_).m_108982_(p_111622_);
        Vector3f $$10 = new Vector3f(p_111625_[$$9.f_108998_], p_111625_[$$9.f_108999_], p_111625_[$$9.f_109000_]);
        this.m_111586_($$10, p_111628_);
        this.m_111589_($$10, p_111627_);
        this.m_111614_(p_111621_, p_111622_, $$10, p_111626_, p_111624_);
    }

    private void m_111614_(int[] p_111615_, int p_111616_, Vector3f p_111617_, TextureAtlasSprite p_111618_, BlockFaceUV p_111619_) {
        int $$5 = p_111616_ * 8;
        p_111615_[$$5] = Float.floatToRawIntBits(p_111617_.m_122239_());
        p_111615_[$$5 + 1] = Float.floatToRawIntBits(p_111617_.m_122260_());
        p_111615_[$$5 + 2] = Float.floatToRawIntBits(p_111617_.m_122269_());
        p_111615_[$$5 + 3] = -1;
        p_111615_[$$5 + 4] = Float.floatToRawIntBits(p_111618_.m_118367_(p_111619_.m_111392_(p_111616_)));
        p_111615_[$$5 + 4 + 1] = Float.floatToRawIntBits(p_111618_.m_118393_(p_111619_.m_111396_(p_111616_)));
    }

    /*
     * WARNING - void declaration
     */
    private void m_111586_(Vector3f p_111587_, @Nullable BlockElementRotation p_111588_) {
        void $$9;
        void $$8;
        if (p_111588_ == null) {
            return;
        }
        switch (p_111588_.f_111379_) {
            case X: {
                Vector3f $$2 = Vector3f.f_122223_;
                Vector3f $$3 = new Vector3f(0.0f, 1.0f, 1.0f);
                break;
            }
            case Y: {
                Vector3f $$4 = Vector3f.f_122225_;
                Vector3f $$5 = new Vector3f(1.0f, 0.0f, 1.0f);
                break;
            }
            case Z: {
                Vector3f $$6 = Vector3f.f_122227_;
                Vector3f $$7 = new Vector3f(1.0f, 1.0f, 0.0f);
                break;
            }
            default: {
                throw new IllegalArgumentException("There are only 3 axes");
            }
        }
        Quaternion $$10 = $$8.m_122240_(p_111588_.f_111380_);
        if (p_111588_.f_111381_) {
            if (Math.abs(p_111588_.f_111380_) == 22.5f) {
                $$9.m_122261_(f_111569_);
            } else {
                $$9.m_122261_(f_111570_);
            }
            $$9.m_122272_(1.0f, 1.0f, 1.0f);
        } else {
            $$9.m_122245_(1.0f, 1.0f, 1.0f);
        }
        this.m_111595_(p_111587_, p_111588_.f_111378_.m_122281_(), new Matrix4f($$10), (Vector3f)$$9);
    }

    public void m_111589_(Vector3f p_111590_, Transformation p_111591_) {
        if (p_111591_ == Transformation.m_121093_()) {
            return;
        }
        this.m_111595_(p_111590_, new Vector3f(0.5f, 0.5f, 0.5f), p_111591_.m_121104_(), new Vector3f(1.0f, 1.0f, 1.0f));
    }

    private void m_111595_(Vector3f p_111596_, Vector3f p_111597_, Matrix4f p_111598_, Vector3f p_111599_) {
        Vector4f $$4 = new Vector4f(p_111596_.m_122239_() - p_111597_.m_122239_(), p_111596_.m_122260_() - p_111597_.m_122260_(), p_111596_.m_122269_() - p_111597_.m_122269_(), 1.0f);
        $$4.m_123607_(p_111598_);
        $$4.m_123611_(p_111599_);
        p_111596_.m_122245_($$4.m_123601_() + p_111597_.m_122239_(), $$4.m_123615_() + p_111597_.m_122260_(), $$4.m_123616_() + p_111597_.m_122269_());
    }

    public static Direction m_111612_(int[] p_111613_) {
        Vector3f $$1 = new Vector3f(Float.intBitsToFloat(p_111613_[0]), Float.intBitsToFloat(p_111613_[1]), Float.intBitsToFloat(p_111613_[2]));
        Vector3f $$2 = new Vector3f(Float.intBitsToFloat(p_111613_[8]), Float.intBitsToFloat(p_111613_[9]), Float.intBitsToFloat(p_111613_[10]));
        Vector3f $$3 = new Vector3f(Float.intBitsToFloat(p_111613_[16]), Float.intBitsToFloat(p_111613_[17]), Float.intBitsToFloat(p_111613_[18]));
        Vector3f $$4 = $$1.m_122281_();
        $$4.m_122267_($$2);
        Vector3f $$5 = $$3.m_122281_();
        $$5.m_122267_($$2);
        Vector3f $$6 = $$5.m_122281_();
        $$6.m_122279_($$4);
        $$6.m_122278_();
        Direction $$7 = null;
        float $$8 = 0.0f;
        for (Direction $$9 : Direction.values()) {
            Vec3i $$10 = $$9.m_122436_();
            Vector3f $$11 = new Vector3f($$10.m_123341_(), $$10.m_123342_(), $$10.m_123343_());
            float $$12 = $$6.m_122276_($$11);
            if (!($$12 >= 0.0f) || !($$12 > $$8)) continue;
            $$8 = $$12;
            $$7 = $$9;
        }
        if ($$7 == null) {
            return Direction.UP;
        }
        return $$7;
    }

    private void m_111630_(int[] p_111631_, Direction p_111632_) {
        int[] $$2 = new int[p_111631_.length];
        System.arraycopy(p_111631_, 0, $$2, 0, p_111631_.length);
        float[] $$3 = new float[Direction.values().length];
        $$3[FaceInfo.Constants.f_108996_] = 999.0f;
        $$3[FaceInfo.Constants.f_108995_] = 999.0f;
        $$3[FaceInfo.Constants.f_108994_] = 999.0f;
        $$3[FaceInfo.Constants.f_108993_] = -999.0f;
        $$3[FaceInfo.Constants.f_108992_] = -999.0f;
        $$3[FaceInfo.Constants.f_108991_] = -999.0f;
        for (int $$4 = 0; $$4 < 4; ++$$4) {
            int $$5 = 8 * $$4;
            float $$6 = Float.intBitsToFloat($$2[$$5]);
            float $$7 = Float.intBitsToFloat($$2[$$5 + 1]);
            float $$8 = Float.intBitsToFloat($$2[$$5 + 2]);
            if ($$6 < $$3[FaceInfo.Constants.f_108996_]) {
                $$3[FaceInfo.Constants.f_108996_] = $$6;
            }
            if ($$7 < $$3[FaceInfo.Constants.f_108995_]) {
                $$3[FaceInfo.Constants.f_108995_] = $$7;
            }
            if ($$8 < $$3[FaceInfo.Constants.f_108994_]) {
                $$3[FaceInfo.Constants.f_108994_] = $$8;
            }
            if ($$6 > $$3[FaceInfo.Constants.f_108993_]) {
                $$3[FaceInfo.Constants.f_108993_] = $$6;
            }
            if ($$7 > $$3[FaceInfo.Constants.f_108992_]) {
                $$3[FaceInfo.Constants.f_108992_] = $$7;
            }
            if (!($$8 > $$3[FaceInfo.Constants.f_108991_])) continue;
            $$3[FaceInfo.Constants.f_108991_] = $$8;
        }
        FaceInfo $$9 = FaceInfo.m_108984_(p_111632_);
        for (int $$10 = 0; $$10 < 4; ++$$10) {
            int $$11 = 8 * $$10;
            FaceInfo.VertexInfo $$12 = $$9.m_108982_($$10);
            float $$13 = $$3[$$12.f_108998_];
            float $$14 = $$3[$$12.f_108999_];
            float $$15 = $$3[$$12.f_109000_];
            p_111631_[$$11] = Float.floatToRawIntBits($$13);
            p_111631_[$$11 + 1] = Float.floatToRawIntBits($$14);
            p_111631_[$$11 + 2] = Float.floatToRawIntBits($$15);
            for (int $$16 = 0; $$16 < 4; ++$$16) {
                int $$17 = 8 * $$16;
                float $$18 = Float.intBitsToFloat($$2[$$17]);
                float $$19 = Float.intBitsToFloat($$2[$$17 + 1]);
                float $$20 = Float.intBitsToFloat($$2[$$17 + 2]);
                if (!Mth.m_14033_($$13, $$18) || !Mth.m_14033_($$14, $$19) || !Mth.m_14033_($$15, $$20)) continue;
                p_111631_[$$11 + 4] = $$2[$$17 + 4];
                p_111631_[$$11 + 4 + 1] = $$2[$$17 + 4 + 1];
            }
        }
    }
}

