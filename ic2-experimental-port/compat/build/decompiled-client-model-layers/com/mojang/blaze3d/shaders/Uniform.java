/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.lwjgl.system.MemoryUtil
 *  org.slf4j.Logger
 */
package com.mojang.blaze3d.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.AbstractUniform;
import com.mojang.blaze3d.shaders.Shader;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;

public class Uniform
extends AbstractUniform
implements AutoCloseable {
    private static final Logger f_85584_ = LogUtils.getLogger();
    public static final int f_166625_ = 0;
    public static final int f_166626_ = 1;
    public static final int f_166627_ = 2;
    public static final int f_166628_ = 3;
    public static final int f_166629_ = 4;
    public static final int f_166630_ = 5;
    public static final int f_166631_ = 6;
    public static final int f_166632_ = 7;
    public static final int f_166633_ = 8;
    public static final int f_166634_ = 9;
    public static final int f_166635_ = 10;
    private static final boolean f_166636_ = false;
    private int f_85585_;
    private final int f_85586_;
    private final int f_85587_;
    private final IntBuffer f_85588_;
    private final FloatBuffer f_85589_;
    private final String f_85590_;
    private boolean f_85591_;
    private final Shader f_85592_;

    public Uniform(String p_166638_, int p_166639_, int p_166640_, Shader p_166641_) {
        this.f_85590_ = p_166638_;
        this.f_85586_ = p_166640_;
        this.f_85587_ = p_166639_;
        this.f_85592_ = p_166641_;
        if (p_166639_ <= 3) {
            this.f_85588_ = MemoryUtil.memAllocInt((int)p_166640_);
            this.f_85589_ = null;
        } else {
            this.f_85588_ = null;
            this.f_85589_ = MemoryUtil.memAllocFloat((int)p_166640_);
        }
        this.f_85585_ = -1;
        this.m_85642_();
    }

    public static int m_85624_(int p_85625_, CharSequence p_85626_) {
        return GlStateManager.m_84345_(p_85625_, p_85626_);
    }

    public static void m_85616_(int p_85617_, int p_85618_) {
        RenderSystem.m_69543_(p_85617_, p_85618_);
    }

    public static int m_85639_(int p_85640_, CharSequence p_85641_) {
        return GlStateManager.m_84398_(p_85640_, p_85641_);
    }

    public static void m_166710_(int p_166711_, int p_166712_, CharSequence p_166713_) {
        GlStateManager.m_157061_(p_166711_, p_166712_, p_166713_);
    }

    @Override
    public void close() {
        if (this.f_85588_ != null) {
            MemoryUtil.memFree((Buffer)this.f_85588_);
        }
        if (this.f_85589_ != null) {
            MemoryUtil.memFree((Buffer)this.f_85589_);
        }
    }

    private void m_85642_() {
        this.f_85591_ = true;
        if (this.f_85592_ != null) {
            this.f_85592_.m_108957_();
        }
    }

    public static int m_85629_(String p_85630_) {
        int $$1 = -1;
        if ("int".equals(p_85630_)) {
            $$1 = 0;
        } else if ("float".equals(p_85630_)) {
            $$1 = 4;
        } else if (p_85630_.startsWith("matrix")) {
            if (p_85630_.endsWith("2x2")) {
                $$1 = 8;
            } else if (p_85630_.endsWith("3x3")) {
                $$1 = 9;
            } else if (p_85630_.endsWith("4x4")) {
                $$1 = 10;
            }
        }
        return $$1;
    }

    public void m_85614_(int p_85615_) {
        this.f_85585_ = p_85615_;
    }

    public String m_85599_() {
        return this.f_85590_;
    }

    @Override
    public final void m_5985_(float p_85601_) {
        this.f_85589_.position(0);
        this.f_85589_.put(0, p_85601_);
        this.m_85642_();
    }

    @Override
    public final void m_7971_(float p_85603_, float p_85604_) {
        this.f_85589_.position(0);
        this.f_85589_.put(0, p_85603_);
        this.f_85589_.put(1, p_85604_);
        this.m_85642_();
    }

    public final void m_166700_(int p_166701_, float p_166702_) {
        this.f_85589_.position(0);
        this.f_85589_.put(p_166701_, p_166702_);
        this.m_85642_();
    }

    @Override
    public final void m_5889_(float p_85606_, float p_85607_, float p_85608_) {
        this.f_85589_.position(0);
        this.f_85589_.put(0, p_85606_);
        this.f_85589_.put(1, p_85607_);
        this.f_85589_.put(2, p_85608_);
        this.m_85642_();
    }

    @Override
    public final void m_142276_(Vector3f p_166715_) {
        this.f_85589_.position(0);
        this.f_85589_.put(0, p_166715_.m_122239_());
        this.f_85589_.put(1, p_166715_.m_122260_());
        this.f_85589_.put(2, p_166715_.m_122269_());
        this.m_85642_();
    }

    @Override
    public final void m_5805_(float p_85610_, float p_85611_, float p_85612_, float p_85613_) {
        this.f_85589_.position(0);
        this.f_85589_.put(p_85610_);
        this.f_85589_.put(p_85611_);
        this.f_85589_.put(p_85612_);
        this.f_85589_.put(p_85613_);
        this.f_85589_.flip();
        this.m_85642_();
    }

    @Override
    public final void m_142558_(Vector4f p_166717_) {
        this.f_85589_.position(0);
        this.f_85589_.put(0, p_166717_.m_123601_());
        this.f_85589_.put(1, p_166717_.m_123615_());
        this.f_85589_.put(2, p_166717_.m_123616_());
        this.f_85589_.put(3, p_166717_.m_123617_());
        this.m_85642_();
    }

    @Override
    public final void m_5808_(float p_85635_, float p_85636_, float p_85637_, float p_85638_) {
        this.f_85589_.position(0);
        if (this.f_85587_ >= 4) {
            this.f_85589_.put(0, p_85635_);
        }
        if (this.f_85587_ >= 5) {
            this.f_85589_.put(1, p_85636_);
        }
        if (this.f_85587_ >= 6) {
            this.f_85589_.put(2, p_85637_);
        }
        if (this.f_85587_ >= 7) {
            this.f_85589_.put(3, p_85638_);
        }
        this.m_85642_();
    }

    @Override
    public final void m_7401_(int p_85620_, int p_85621_, int p_85622_, int p_85623_) {
        this.f_85588_.position(0);
        if (this.f_85587_ >= 0) {
            this.f_85588_.put(0, p_85620_);
        }
        if (this.f_85587_ >= 1) {
            this.f_85588_.put(1, p_85621_);
        }
        if (this.f_85587_ >= 2) {
            this.f_85588_.put(2, p_85622_);
        }
        if (this.f_85587_ >= 3) {
            this.f_85588_.put(3, p_85623_);
        }
        this.m_85642_();
    }

    @Override
    public final void m_142617_(int p_166699_) {
        this.f_85588_.position(0);
        this.f_85588_.put(0, p_166699_);
        this.m_85642_();
    }

    @Override
    public final void m_142326_(int p_166704_, int p_166705_) {
        this.f_85588_.position(0);
        this.f_85588_.put(0, p_166704_);
        this.f_85588_.put(1, p_166705_);
        this.m_85642_();
    }

    @Override
    public final void m_142693_(int p_166707_, int p_166708_, int p_166709_) {
        this.f_85588_.position(0);
        this.f_85588_.put(0, p_166707_);
        this.f_85588_.put(1, p_166708_);
        this.f_85588_.put(2, p_166709_);
        this.m_85642_();
    }

    @Override
    public final void m_142492_(int p_166748_, int p_166749_, int p_166750_, int p_166751_) {
        this.f_85588_.position(0);
        this.f_85588_.put(0, p_166748_);
        this.f_85588_.put(1, p_166749_);
        this.f_85588_.put(2, p_166750_);
        this.f_85588_.put(3, p_166751_);
        this.m_85642_();
    }

    @Override
    public final void m_5941_(float[] p_85632_) {
        if (p_85632_.length < this.f_85586_) {
            f_85584_.warn("Uniform.set called with a too-small value array (expected {}, got {}). Ignoring.", (Object)this.f_85586_, (Object)p_85632_.length);
            return;
        }
        this.f_85589_.position(0);
        this.f_85589_.put(p_85632_);
        this.f_85589_.position(0);
        this.m_85642_();
    }

    @Override
    public final void m_142588_(float p_166754_, float p_166755_, float p_166756_, float p_166757_) {
        this.f_85589_.position(0);
        this.f_85589_.put(0, p_166754_);
        this.f_85589_.put(1, p_166755_);
        this.f_85589_.put(2, p_166756_);
        this.f_85589_.put(3, p_166757_);
        this.m_85642_();
    }

    @Override
    public final void m_141964_(float p_166643_, float p_166644_, float p_166645_, float p_166646_, float p_166647_, float p_166648_) {
        this.f_85589_.position(0);
        this.f_85589_.put(0, p_166643_);
        this.f_85589_.put(1, p_166644_);
        this.f_85589_.put(2, p_166645_);
        this.f_85589_.put(3, p_166646_);
        this.f_85589_.put(4, p_166647_);
        this.f_85589_.put(5, p_166648_);
        this.m_85642_();
    }

    @Override
    public final void m_142005_(float p_166650_, float p_166651_, float p_166652_, float p_166653_, float p_166654_, float p_166655_, float p_166656_, float p_166657_) {
        this.f_85589_.position(0);
        this.f_85589_.put(0, p_166650_);
        this.f_85589_.put(1, p_166651_);
        this.f_85589_.put(2, p_166652_);
        this.f_85589_.put(3, p_166653_);
        this.f_85589_.put(4, p_166654_);
        this.f_85589_.put(5, p_166655_);
        this.f_85589_.put(6, p_166656_);
        this.f_85589_.put(7, p_166657_);
        this.m_85642_();
    }

    @Override
    public final void m_141963_(float p_166719_, float p_166720_, float p_166721_, float p_166722_, float p_166723_, float p_166724_) {
        this.f_85589_.position(0);
        this.f_85589_.put(0, p_166719_);
        this.f_85589_.put(1, p_166720_);
        this.f_85589_.put(2, p_166721_);
        this.f_85589_.put(3, p_166722_);
        this.f_85589_.put(4, p_166723_);
        this.f_85589_.put(5, p_166724_);
        this.m_85642_();
    }

    @Override
    public final void m_142217_(float p_166659_, float p_166660_, float p_166661_, float p_166662_, float p_166663_, float p_166664_, float p_166665_, float p_166666_, float p_166667_) {
        this.f_85589_.position(0);
        this.f_85589_.put(0, p_166659_);
        this.f_85589_.put(1, p_166660_);
        this.f_85589_.put(2, p_166661_);
        this.f_85589_.put(3, p_166662_);
        this.f_85589_.put(4, p_166663_);
        this.f_85589_.put(5, p_166664_);
        this.f_85589_.put(6, p_166665_);
        this.f_85589_.put(7, p_166666_);
        this.f_85589_.put(8, p_166667_);
        this.m_85642_();
    }

    @Override
    public final void m_142604_(float p_166669_, float p_166670_, float p_166671_, float p_166672_, float p_166673_, float p_166674_, float p_166675_, float p_166676_, float p_166677_, float p_166678_, float p_166679_, float p_166680_) {
        this.f_85589_.position(0);
        this.f_85589_.put(0, p_166669_);
        this.f_85589_.put(1, p_166670_);
        this.f_85589_.put(2, p_166671_);
        this.f_85589_.put(3, p_166672_);
        this.f_85589_.put(4, p_166673_);
        this.f_85589_.put(5, p_166674_);
        this.f_85589_.put(6, p_166675_);
        this.f_85589_.put(7, p_166676_);
        this.f_85589_.put(8, p_166677_);
        this.f_85589_.put(9, p_166678_);
        this.f_85589_.put(10, p_166679_);
        this.f_85589_.put(11, p_166680_);
        this.m_85642_();
    }

    @Override
    public final void m_142004_(float p_166726_, float p_166727_, float p_166728_, float p_166729_, float p_166730_, float p_166731_, float p_166732_, float p_166733_) {
        this.f_85589_.position(0);
        this.f_85589_.put(0, p_166726_);
        this.f_85589_.put(1, p_166727_);
        this.f_85589_.put(2, p_166728_);
        this.f_85589_.put(3, p_166729_);
        this.f_85589_.put(4, p_166730_);
        this.f_85589_.put(5, p_166731_);
        this.f_85589_.put(6, p_166732_);
        this.f_85589_.put(7, p_166733_);
        this.m_85642_();
    }

    @Override
    public final void m_142605_(float p_166735_, float p_166736_, float p_166737_, float p_166738_, float p_166739_, float p_166740_, float p_166741_, float p_166742_, float p_166743_, float p_166744_, float p_166745_, float p_166746_) {
        this.f_85589_.position(0);
        this.f_85589_.put(0, p_166735_);
        this.f_85589_.put(1, p_166736_);
        this.f_85589_.put(2, p_166737_);
        this.f_85589_.put(3, p_166738_);
        this.f_85589_.put(4, p_166739_);
        this.f_85589_.put(5, p_166740_);
        this.f_85589_.put(6, p_166741_);
        this.f_85589_.put(7, p_166742_);
        this.f_85589_.put(8, p_166743_);
        this.f_85589_.put(9, p_166744_);
        this.f_85589_.put(10, p_166745_);
        this.f_85589_.put(11, p_166746_);
        this.m_85642_();
    }

    @Override
    public final void m_141978_(float p_166682_, float p_166683_, float p_166684_, float p_166685_, float p_166686_, float p_166687_, float p_166688_, float p_166689_, float p_166690_, float p_166691_, float p_166692_, float p_166693_, float p_166694_, float p_166695_, float p_166696_, float p_166697_) {
        this.f_85589_.position(0);
        this.f_85589_.put(0, p_166682_);
        this.f_85589_.put(1, p_166683_);
        this.f_85589_.put(2, p_166684_);
        this.f_85589_.put(3, p_166685_);
        this.f_85589_.put(4, p_166686_);
        this.f_85589_.put(5, p_166687_);
        this.f_85589_.put(6, p_166688_);
        this.f_85589_.put(7, p_166689_);
        this.f_85589_.put(8, p_166690_);
        this.f_85589_.put(9, p_166691_);
        this.f_85589_.put(10, p_166692_);
        this.f_85589_.put(11, p_166693_);
        this.f_85589_.put(12, p_166694_);
        this.f_85589_.put(13, p_166695_);
        this.f_85589_.put(14, p_166696_);
        this.f_85589_.put(15, p_166697_);
        this.m_85642_();
    }

    @Override
    public final void m_5679_(Matrix4f p_85628_) {
        this.f_85589_.position(0);
        p_85628_.m_27650_(this.f_85589_);
        this.m_85642_();
    }

    @Override
    public final void m_200759_(Matrix3f p_200935_) {
        this.f_85589_.position(0);
        p_200935_.m_152780_(this.f_85589_);
        this.m_85642_();
    }

    public void m_85633_() {
        if (!this.f_85591_) {
            // empty if block
        }
        this.f_85591_ = false;
        if (this.f_85587_ <= 3) {
            this.m_85644_();
        } else if (this.f_85587_ <= 7) {
            this.m_85645_();
        } else if (this.f_85587_ <= 10) {
            this.m_85646_();
        } else {
            f_85584_.warn("Uniform.upload called, but type value ({}) is not a valid type. Ignoring.", (Object)this.f_85587_);
            return;
        }
    }

    private void m_85644_() {
        this.f_85588_.rewind();
        switch (this.f_85587_) {
            case 0: {
                RenderSystem.m_69540_(this.f_85585_, this.f_85588_);
                break;
            }
            case 1: {
                RenderSystem.m_69549_(this.f_85585_, this.f_85588_);
                break;
            }
            case 2: {
                RenderSystem.m_69555_(this.f_85585_, this.f_85588_);
                break;
            }
            case 3: {
                RenderSystem.m_69561_(this.f_85585_, this.f_85588_);
                break;
            }
            default: {
                f_85584_.warn("Uniform.upload called, but count value ({}) is  not in the range of 1 to 4. Ignoring.", (Object)this.f_85586_);
            }
        }
    }

    private void m_85645_() {
        this.f_85589_.rewind();
        switch (this.f_85587_) {
            case 4: {
                RenderSystem.m_69537_(this.f_85585_, this.f_85589_);
                break;
            }
            case 5: {
                RenderSystem.m_69546_(this.f_85585_, this.f_85589_);
                break;
            }
            case 6: {
                RenderSystem.m_69552_(this.f_85585_, this.f_85589_);
                break;
            }
            case 7: {
                RenderSystem.m_69558_(this.f_85585_, this.f_85589_);
                break;
            }
            default: {
                f_85584_.warn("Uniform.upload called, but count value ({}) is not in the range of 1 to 4. Ignoring.", (Object)this.f_85586_);
            }
        }
    }

    private void m_85646_() {
        this.f_85589_.clear();
        switch (this.f_85587_) {
            case 8: {
                RenderSystem.m_69564_(this.f_85585_, false, this.f_85589_);
                break;
            }
            case 9: {
                RenderSystem.m_69568_(this.f_85585_, false, this.f_85589_);
                break;
            }
            case 10: {
                RenderSystem.m_69572_(this.f_85585_, false, this.f_85589_);
            }
        }
    }

    public int m_166752_() {
        return this.f_85585_;
    }

    public int m_166758_() {
        return this.f_85586_;
    }

    public int m_166759_() {
        return this.f_85587_;
    }

    public IntBuffer m_166760_() {
        return this.f_85588_;
    }

    public FloatBuffer m_166761_() {
        return this.f_85589_;
    }
}

