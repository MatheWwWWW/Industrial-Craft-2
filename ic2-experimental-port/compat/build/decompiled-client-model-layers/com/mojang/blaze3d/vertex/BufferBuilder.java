/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.primitives.Floats
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.IntArrays
 *  it.unimi.dsi.fastutil.ints.IntConsumer
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableInt
 *  org.lwjgl.system.MemoryUtil
 *  org.slf4j.Logger
 */
package com.mojang.blaze3d.vertex;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Floats;
import com.mojang.blaze3d.platform.MemoryTracker;
import com.mojang.blaze3d.vertex.BufferVertexConsumer;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.DefaultedVertexConsumer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import com.mojang.logging.LogUtils;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntConsumer;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.mutable.MutableInt;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;

public class BufferBuilder
extends DefaultedVertexConsumer
implements BufferVertexConsumer {
    private static final int f_166763_ = 0x200000;
    private static final Logger f_85647_ = LogUtils.getLogger();
    private ByteBuffer f_85648_;
    private int f_231156_;
    private int f_231157_;
    private int f_85652_;
    private int f_85654_;
    @Nullable
    private VertexFormatElement f_85655_;
    private int f_85656_;
    private VertexFormat f_85658_;
    private VertexFormat.Mode f_85657_;
    private boolean f_85659_;
    private boolean f_85660_;
    private boolean f_85661_;
    @Nullable
    private Vector3f[] f_166766_;
    private float f_166767_ = Float.NaN;
    private float f_166768_ = Float.NaN;
    private float f_166769_ = Float.NaN;
    private boolean f_166762_;

    public BufferBuilder(int p_85664_) {
        this.f_85648_ = MemoryTracker.m_182527_(p_85664_ * 6);
    }

    private void m_85665_() {
        this.m_85722_(this.f_85658_.m_86020_());
    }

    private void m_85722_(int p_85723_) {
        if (this.f_85652_ + p_85723_ <= this.f_85648_.capacity()) {
            return;
        }
        int $$1 = this.f_85648_.capacity();
        int $$2 = $$1 + BufferBuilder.m_85725_(p_85723_);
        f_85647_.debug("Needed to grow BufferBuilder buffer: Old size {} bytes, new size {} bytes.", (Object)$$1, (Object)$$2);
        ByteBuffer $$3 = MemoryTracker.m_182529_(this.f_85648_, $$2);
        $$3.rewind();
        this.f_85648_ = $$3;
    }

    private static int m_85725_(int p_85726_) {
        int $$2;
        int $$1 = 0x200000;
        if (p_85726_ == 0) {
            return $$1;
        }
        if (p_85726_ < 0) {
            $$1 *= -1;
        }
        if (($$2 = p_85726_ % $$1) == 0) {
            return p_85726_;
        }
        return p_85726_ + $$1 - $$2;
    }

    public void m_166771_(float p_166772_, float p_166773_, float p_166774_) {
        if (this.f_85657_ != VertexFormat.Mode.QUADS) {
            return;
        }
        if (this.f_166767_ != p_166772_ || this.f_166768_ != p_166773_ || this.f_166769_ != p_166774_) {
            this.f_166767_ = p_166772_;
            this.f_166768_ = p_166773_;
            this.f_166769_ = p_166774_;
            if (this.f_166766_ == null) {
                this.f_166766_ = this.m_166794_();
            }
        }
    }

    public SortState m_166770_() {
        return new SortState(this.f_85657_, this.f_85654_, this.f_166766_, this.f_166767_, this.f_166768_, this.f_166769_);
    }

    public void m_166775_(SortState p_166776_) {
        this.f_85648_.rewind();
        this.f_85657_ = p_166776_.f_166817_;
        this.f_85654_ = p_166776_.f_166818_;
        this.f_85652_ = this.f_231157_;
        this.f_166766_ = p_166776_.f_166819_;
        this.f_166767_ = p_166776_.f_166820_;
        this.f_166768_ = p_166776_.f_166821_;
        this.f_166769_ = p_166776_.f_166822_;
        this.f_166762_ = true;
    }

    public void m_166779_(VertexFormat.Mode p_166780_, VertexFormat p_166781_) {
        if (this.f_85661_) {
            throw new IllegalStateException("Already building!");
        }
        this.f_85661_ = true;
        this.f_85657_ = p_166780_;
        this.m_85704_(p_166781_);
        this.f_85655_ = (VertexFormatElement)p_166781_.m_86023_().get(0);
        this.f_85656_ = 0;
        this.f_85648_.rewind();
    }

    private void m_85704_(VertexFormat p_85705_) {
        if (this.f_85658_ == p_85705_) {
            return;
        }
        this.f_85658_ = p_85705_;
        boolean $$1 = p_85705_ == DefaultVertexFormat.f_85812_;
        boolean $$2 = p_85705_ == DefaultVertexFormat.f_85811_;
        this.f_85659_ = $$1 || $$2;
        this.f_85660_ = $$1;
    }

    private IntConsumer m_231158_(int p_231159_, VertexFormat.IndexType p_231160_) {
        MutableInt $$2 = new MutableInt(p_231159_);
        return switch (p_231160_) {
            default -> throw new IncompatibleClassChangeError();
            case VertexFormat.IndexType.BYTE -> p_231174_ -> this.f_85648_.put($$2.getAndIncrement(), (byte)p_231174_);
            case VertexFormat.IndexType.SHORT -> p_231167_ -> this.f_85648_.putShort($$2.getAndAdd(2), (short)p_231167_);
            case VertexFormat.IndexType.INT -> p_231163_ -> this.f_85648_.putInt($$2.getAndAdd(4), p_231163_);
        };
    }

    private Vector3f[] m_166794_() {
        FloatBuffer $$0 = this.f_85648_.asFloatBuffer();
        int $$1 = this.f_231157_ / 4;
        int $$2 = this.f_85658_.m_86017_();
        int $$3 = $$2 * this.f_85657_.f_166948_;
        int $$4 = this.f_85654_ / this.f_85657_.f_166948_;
        Vector3f[] $$5 = new Vector3f[$$4];
        for (int $$6 = 0; $$6 < $$4; ++$$6) {
            float $$7 = $$0.get($$1 + $$6 * $$3 + 0);
            float $$8 = $$0.get($$1 + $$6 * $$3 + 1);
            float $$9 = $$0.get($$1 + $$6 * $$3 + 2);
            float $$10 = $$0.get($$1 + $$6 * $$3 + $$2 * 2 + 0);
            float $$11 = $$0.get($$1 + $$6 * $$3 + $$2 * 2 + 1);
            float $$12 = $$0.get($$1 + $$6 * $$3 + $$2 * 2 + 2);
            float $$13 = ($$7 + $$10) / 2.0f;
            float $$14 = ($$8 + $$11) / 2.0f;
            float $$15 = ($$9 + $$12) / 2.0f;
            $$5[$$6] = new Vector3f($$13, $$14, $$15);
        }
        return $$5;
    }

    private void m_166786_(VertexFormat.IndexType p_166787_) {
        float[] $$1 = new float[this.f_166766_.length];
        int[] $$2 = new int[this.f_166766_.length];
        for (int $$3 = 0; $$3 < this.f_166766_.length; ++$$3) {
            float $$4 = this.f_166766_[$$3].m_122239_() - this.f_166767_;
            float $$5 = this.f_166766_[$$3].m_122260_() - this.f_166768_;
            float $$6 = this.f_166766_[$$3].m_122269_() - this.f_166769_;
            $$1[$$3] = $$4 * $$4 + $$5 * $$5 + $$6 * $$6;
            $$2[$$3] = $$3;
        }
        IntArrays.mergeSort((int[])$$2, (p_166784_, p_166785_) -> Floats.compare((float)$$1[p_166785_], (float)$$1[p_166784_]));
        IntConsumer $$7 = this.m_231158_(this.f_85652_, p_166787_);
        for (int $$8 : $$2) {
            $$7.accept($$8 * this.f_85657_.f_166948_ + 0);
            $$7.accept($$8 * this.f_85657_.f_166948_ + 1);
            $$7.accept($$8 * this.f_85657_.f_166948_ + 2);
            $$7.accept($$8 * this.f_85657_.f_166948_ + 2);
            $$7.accept($$8 * this.f_85657_.f_166948_ + 3);
            $$7.accept($$8 * this.f_85657_.f_166948_ + 0);
        }
    }

    public boolean m_231164_() {
        return this.f_85654_ == 0;
    }

    @Nullable
    public RenderedBuffer m_231168_() {
        this.m_231176_();
        if (this.m_231164_()) {
            this.m_231178_();
            return null;
        }
        RenderedBuffer $$0 = this.m_231177_();
        this.m_231178_();
        return $$0;
    }

    public RenderedBuffer m_231175_() {
        this.m_231176_();
        RenderedBuffer $$0 = this.m_231177_();
        this.m_231178_();
        return $$0;
    }

    private void m_231176_() {
        if (!this.f_85661_) {
            throw new IllegalStateException("Not building!");
        }
    }

    private RenderedBuffer m_231177_() {
        int $$7;
        boolean $$6;
        int $$0 = this.f_85657_.m_166958_(this.f_85654_);
        int $$1 = !this.f_166762_ ? this.f_85654_ * this.f_85658_.m_86020_() : 0;
        VertexFormat.IndexType $$2 = VertexFormat.IndexType.m_166933_($$0);
        if (this.f_166766_ != null) {
            int $$3 = Mth.m_144941_($$0 * $$2.f_166924_, 4);
            this.m_85722_($$3);
            this.m_166786_($$2);
            boolean $$4 = false;
            this.f_85652_ += $$3;
            int $$5 = $$1 + $$3;
        } else {
            $$6 = true;
            $$7 = $$1;
        }
        int $$8 = this.f_231157_;
        this.f_231157_ += $$7;
        ++this.f_231156_;
        DrawState $$9 = new DrawState(this.f_85658_, this.f_85654_, $$0, this.f_85657_, $$2, this.f_166762_, $$6);
        return new RenderedBuffer($$8, $$9);
    }

    private void m_231178_() {
        this.f_85661_ = false;
        this.f_85654_ = 0;
        this.f_85655_ = null;
        this.f_85656_ = 0;
        this.f_166766_ = null;
        this.f_166767_ = Float.NaN;
        this.f_166768_ = Float.NaN;
        this.f_166769_ = Float.NaN;
        this.f_166762_ = false;
    }

    @Override
    public void m_5672_(int p_85686_, byte p_85687_) {
        this.f_85648_.put(this.f_85652_ + p_85686_, p_85687_);
    }

    @Override
    public void m_5586_(int p_85700_, short p_85701_) {
        this.f_85648_.putShort(this.f_85652_ + p_85700_, p_85701_);
    }

    @Override
    public void m_5832_(int p_85689_, float p_85690_) {
        this.f_85648_.putFloat(this.f_85652_ + p_85689_, p_85690_);
    }

    @Override
    public void m_5752_() {
        if (this.f_85656_ != 0) {
            throw new IllegalStateException("Not filled all elements of the vertex");
        }
        ++this.f_85654_;
        this.m_85665_();
        if (this.f_85657_ == VertexFormat.Mode.LINES || this.f_85657_ == VertexFormat.Mode.LINE_STRIP) {
            int $$0 = this.f_85658_.m_86020_();
            this.f_85648_.put(this.f_85652_, this.f_85648_, this.f_85652_ - $$0, $$0);
            this.f_85652_ += $$0;
            ++this.f_85654_;
            this.m_85665_();
        }
    }

    @Override
    public void m_5751_() {
        VertexFormatElement $$1;
        ImmutableList<VertexFormatElement> $$0 = this.f_85658_.m_86023_();
        this.f_85656_ = (this.f_85656_ + 1) % $$0.size();
        this.f_85652_ += this.f_85655_.m_86050_();
        this.f_85655_ = $$1 = (VertexFormatElement)$$0.get(this.f_85656_);
        if ($$1.m_86048_() == VertexFormatElement.Usage.PADDING) {
            this.m_5751_();
        }
        if (this.f_85824_ && this.f_85655_.m_86048_() == VertexFormatElement.Usage.COLOR) {
            BufferVertexConsumer.super.m_6122_(this.f_85825_, this.f_85826_, this.f_85827_, this.f_85828_);
        }
    }

    @Override
    public VertexConsumer m_6122_(int p_85692_, int p_85693_, int p_85694_, int p_85695_) {
        if (this.f_85824_) {
            throw new IllegalStateException();
        }
        return BufferVertexConsumer.super.m_6122_(p_85692_, p_85693_, p_85694_, p_85695_);
    }

    @Override
    public void m_5954_(float p_85671_, float p_85672_, float p_85673_, float p_85674_, float p_85675_, float p_85676_, float p_85677_, float p_85678_, float p_85679_, int p_85680_, int p_85681_, float p_85682_, float p_85683_, float p_85684_) {
        if (this.f_85824_) {
            throw new IllegalStateException();
        }
        if (this.f_85659_) {
            int $$15;
            this.m_5832_(0, p_85671_);
            this.m_5832_(4, p_85672_);
            this.m_5832_(8, p_85673_);
            this.m_5672_(12, (byte)(p_85674_ * 255.0f));
            this.m_5672_(13, (byte)(p_85675_ * 255.0f));
            this.m_5672_(14, (byte)(p_85676_ * 255.0f));
            this.m_5672_(15, (byte)(p_85677_ * 255.0f));
            this.m_5832_(16, p_85678_);
            this.m_5832_(20, p_85679_);
            if (this.f_85660_) {
                this.m_5586_(24, (short)(p_85680_ & 0xFFFF));
                this.m_5586_(26, (short)(p_85680_ >> 16 & 0xFFFF));
                int $$14 = 28;
            } else {
                $$15 = 24;
            }
            this.m_5586_($$15 + 0, (short)(p_85681_ & 0xFFFF));
            this.m_5586_($$15 + 2, (short)(p_85681_ >> 16 & 0xFFFF));
            this.m_5672_($$15 + 4, BufferVertexConsumer.m_85774_(p_85682_));
            this.m_5672_($$15 + 5, BufferVertexConsumer.m_85774_(p_85683_));
            this.m_5672_($$15 + 6, BufferVertexConsumer.m_85774_(p_85684_));
            this.f_85652_ += $$15 + 8;
            this.m_5752_();
            return;
        }
        super.m_5954_(p_85671_, p_85672_, p_85673_, p_85674_, p_85675_, p_85676_, p_85677_, p_85678_, p_85679_, p_85680_, p_85681_, p_85682_, p_85683_, p_85684_);
    }

    void m_231179_() {
        if (this.f_231156_ > 0 && --this.f_231156_ == 0) {
            this.m_85729_();
        }
    }

    public void m_85729_() {
        if (this.f_231156_ > 0) {
            f_85647_.warn("Clearing BufferBuilder with unused batches");
        }
        this.m_85730_();
    }

    public void m_85730_() {
        this.f_231156_ = 0;
        this.f_231157_ = 0;
        this.f_85652_ = 0;
    }

    @Override
    public VertexFormatElement m_6297_() {
        if (this.f_85655_ == null) {
            throw new IllegalStateException("BufferBuilder not started");
        }
        return this.f_85655_;
    }

    public boolean m_85732_() {
        return this.f_85661_;
    }

    ByteBuffer m_231169_(int p_231170_, int p_231171_) {
        return MemoryUtil.memSlice((ByteBuffer)this.f_85648_, (int)p_231170_, (int)(p_231171_ - p_231170_));
    }

    public static class SortState {
        final VertexFormat.Mode f_166817_;
        final int f_166818_;
        @Nullable
        final Vector3f[] f_166819_;
        final float f_166820_;
        final float f_166821_;
        final float f_166822_;

        SortState(VertexFormat.Mode p_166824_, int p_166825_, @Nullable Vector3f[] p_166826_, float p_166827_, float p_166828_, float p_166829_) {
            this.f_166817_ = p_166824_;
            this.f_166818_ = p_166825_;
            this.f_166819_ = p_166826_;
            this.f_166820_ = p_166827_;
            this.f_166821_ = p_166828_;
            this.f_166822_ = p_166829_;
        }
    }

    public class RenderedBuffer {
        private final int f_231189_;
        private final DrawState f_231190_;
        private boolean f_231191_;

        RenderedBuffer(int p_231194_, DrawState p_231195_) {
            this.f_231189_ = p_231194_;
            this.f_231190_ = p_231195_;
        }

        public ByteBuffer m_231196_() {
            int $$0 = this.f_231189_ + this.f_231190_.m_231180_();
            int $$1 = this.f_231189_ + this.f_231190_.m_231181_();
            return BufferBuilder.this.m_231169_($$0, $$1);
        }

        public ByteBuffer m_231197_() {
            int $$0 = this.f_231189_ + this.f_231190_.m_231182_();
            int $$1 = this.f_231189_ + this.f_231190_.m_231183_();
            return BufferBuilder.this.m_231169_($$0, $$1);
        }

        public DrawState m_231198_() {
            return this.f_231190_;
        }

        public boolean m_231199_() {
            return this.f_231190_.f_85734_ == 0;
        }

        public void m_231200_() {
            if (this.f_231191_) {
                throw new IllegalStateException("Buffer has already been released!");
            }
            BufferBuilder.this.m_231179_();
            this.f_231191_ = true;
        }
    }

    public record DrawState(VertexFormat f_85733_, int f_85734_, int f_166797_, VertexFormat.Mode f_85735_, VertexFormat.IndexType f_166798_, boolean f_166799_, boolean f_166800_) {
        public int m_166812_() {
            return this.f_85734_ * this.f_85733_.m_86020_();
        }

        public int m_231180_() {
            return 0;
        }

        public int m_231181_() {
            return this.m_166812_();
        }

        public int m_231182_() {
            return this.f_166799_ ? 0 : this.m_231181_();
        }

        public int m_231183_() {
            return this.m_231182_() + this.m_166816_();
        }

        private int m_166816_() {
            return this.f_166800_ ? 0 : this.f_166797_ * this.f_166798_.f_166924_;
        }

        public int m_166813_() {
            return this.m_231183_();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{DrawState.class, "format;vertexCount;indexCount;mode;indexType;indexOnly;sequentialIndex", "f_85733_", "f_85734_", "f_166797_", "f_85735_", "f_166798_", "f_166799_", "f_166800_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{DrawState.class, "format;vertexCount;indexCount;mode;indexType;indexOnly;sequentialIndex", "f_85733_", "f_85734_", "f_166797_", "f_85735_", "f_166798_", "f_166799_", "f_166800_"}, this);
        }

        @Override
        public final boolean equals(Object p_231185_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{DrawState.class, "format;vertexCount;indexCount;mode;indexType;indexOnly;sequentialIndex", "f_85733_", "f_85734_", "f_166797_", "f_85735_", "f_166798_", "f_166799_", "f_166800_"}, this, p_231185_);
        }
    }
}

