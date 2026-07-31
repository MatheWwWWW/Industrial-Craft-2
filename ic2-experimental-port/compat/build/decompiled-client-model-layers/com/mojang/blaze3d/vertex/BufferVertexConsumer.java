/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import net.minecraft.util.Mth;

public interface BufferVertexConsumer
extends VertexConsumer {
    public VertexFormatElement m_6297_();

    public void m_5751_();

    public void m_5672_(int var1, byte var2);

    public void m_5586_(int var1, short var2);

    public void m_5832_(int var1, float var2);

    @Override
    default public VertexConsumer m_5483_(double p_85771_, double p_85772_, double p_85773_) {
        if (this.m_6297_().m_86048_() != VertexFormatElement.Usage.POSITION) {
            return this;
        }
        if (this.m_6297_().m_86041_() != VertexFormatElement.Type.FLOAT || this.m_6297_().m_166969_() != 3) {
            throw new IllegalStateException();
        }
        this.m_5832_(0, (float)p_85771_);
        this.m_5832_(4, (float)p_85772_);
        this.m_5832_(8, (float)p_85773_);
        this.m_5751_();
        return this;
    }

    @Override
    default public VertexConsumer m_6122_(int p_85787_, int p_85788_, int p_85789_, int p_85790_) {
        VertexFormatElement $$4 = this.m_6297_();
        if ($$4.m_86048_() != VertexFormatElement.Usage.COLOR) {
            return this;
        }
        if ($$4.m_86041_() != VertexFormatElement.Type.UBYTE || $$4.m_166969_() != 4) {
            throw new IllegalStateException();
        }
        this.m_5672_(0, (byte)p_85787_);
        this.m_5672_(1, (byte)p_85788_);
        this.m_5672_(2, (byte)p_85789_);
        this.m_5672_(3, (byte)p_85790_);
        this.m_5751_();
        return this;
    }

    @Override
    default public VertexConsumer m_7421_(float p_85777_, float p_85778_) {
        VertexFormatElement $$2 = this.m_6297_();
        if ($$2.m_86048_() != VertexFormatElement.Usage.UV || $$2.m_86049_() != 0) {
            return this;
        }
        if ($$2.m_86041_() != VertexFormatElement.Type.FLOAT || $$2.m_166969_() != 2) {
            throw new IllegalStateException();
        }
        this.m_5832_(0, p_85777_);
        this.m_5832_(4, p_85778_);
        this.m_5751_();
        return this;
    }

    @Override
    default public VertexConsumer m_7122_(int p_85784_, int p_85785_) {
        return this.m_85793_((short)p_85784_, (short)p_85785_, 1);
    }

    @Override
    default public VertexConsumer m_7120_(int p_85802_, int p_85803_) {
        return this.m_85793_((short)p_85802_, (short)p_85803_, 2);
    }

    default public VertexConsumer m_85793_(short p_85794_, short p_85795_, int p_85796_) {
        VertexFormatElement $$3 = this.m_6297_();
        if ($$3.m_86048_() != VertexFormatElement.Usage.UV || $$3.m_86049_() != p_85796_) {
            return this;
        }
        if ($$3.m_86041_() != VertexFormatElement.Type.SHORT || $$3.m_166969_() != 2) {
            throw new IllegalStateException();
        }
        this.m_5586_(0, p_85794_);
        this.m_5586_(2, p_85795_);
        this.m_5751_();
        return this;
    }

    @Override
    default public VertexConsumer m_5601_(float p_85798_, float p_85799_, float p_85800_) {
        VertexFormatElement $$3 = this.m_6297_();
        if ($$3.m_86048_() != VertexFormatElement.Usage.NORMAL) {
            return this;
        }
        if ($$3.m_86041_() != VertexFormatElement.Type.BYTE || $$3.m_166969_() != 3) {
            throw new IllegalStateException();
        }
        this.m_5672_(0, BufferVertexConsumer.m_85774_(p_85798_));
        this.m_5672_(1, BufferVertexConsumer.m_85774_(p_85799_));
        this.m_5672_(2, BufferVertexConsumer.m_85774_(p_85800_));
        this.m_5751_();
        return this;
    }

    public static byte m_85774_(float p_85775_) {
        return (byte)((int)(Mth.m_14036_(p_85775_, -1.0f, 1.0f) * 127.0f) & 0xFF);
    }
}

