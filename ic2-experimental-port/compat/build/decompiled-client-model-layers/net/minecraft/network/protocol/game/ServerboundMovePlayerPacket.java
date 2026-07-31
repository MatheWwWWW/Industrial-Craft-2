/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public abstract class ServerboundMovePlayerPacket
implements Packet<ServerGamePacketListener> {
    protected final double f_134118_;
    protected final double f_134119_;
    protected final double f_134120_;
    protected final float f_134121_;
    protected final float f_134122_;
    protected final boolean f_134123_;
    protected final boolean f_134124_;
    protected final boolean f_134125_;

    protected ServerboundMovePlayerPacket(double p_179675_, double p_179676_, double p_179677_, float p_179678_, float p_179679_, boolean p_179680_, boolean p_179681_, boolean p_179682_) {
        this.f_134118_ = p_179675_;
        this.f_134119_ = p_179676_;
        this.f_134120_ = p_179677_;
        this.f_134121_ = p_179678_;
        this.f_134122_ = p_179679_;
        this.f_134123_ = p_179680_;
        this.f_134124_ = p_179681_;
        this.f_134125_ = p_179682_;
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134138_) {
        p_134138_.m_7185_(this);
    }

    public double m_134129_(double p_134130_) {
        return this.f_134124_ ? this.f_134118_ : p_134130_;
    }

    public double m_134140_(double p_134141_) {
        return this.f_134124_ ? this.f_134119_ : p_134141_;
    }

    public double m_134146_(double p_134147_) {
        return this.f_134124_ ? this.f_134120_ : p_134147_;
    }

    public float m_134131_(float p_134132_) {
        return this.f_134125_ ? this.f_134121_ : p_134132_;
    }

    public float m_134142_(float p_134143_) {
        return this.f_134125_ ? this.f_134122_ : p_134143_;
    }

    public boolean m_134139_() {
        return this.f_134123_;
    }

    public boolean m_179683_() {
        return this.f_134124_;
    }

    public boolean m_179684_() {
        return this.f_134125_;
    }

    public static class StatusOnly
    extends ServerboundMovePlayerPacket {
        public StatusOnly(boolean p_179692_) {
            super(0.0, 0.0, 0.0, 0.0f, 0.0f, p_179692_, false, false);
        }

        public static StatusOnly m_179697_(FriendlyByteBuf p_179698_) {
            boolean $$1 = p_179698_.readUnsignedByte() != 0;
            return new StatusOnly($$1);
        }

        @Override
        public void m_5779_(FriendlyByteBuf p_179694_) {
            p_179694_.writeByte(this.f_134123_ ? 1 : 0);
        }
    }

    public static class Rot
    extends ServerboundMovePlayerPacket {
        public Rot(float p_134176_, float p_134177_, boolean p_134178_) {
            super(0.0, 0.0, 0.0, p_134176_, p_134177_, p_134178_, false, true);
        }

        public static Rot m_179689_(FriendlyByteBuf p_179690_) {
            float $$1 = p_179690_.readFloat();
            float $$2 = p_179690_.readFloat();
            boolean $$3 = p_179690_.readUnsignedByte() != 0;
            return new Rot($$1, $$2, $$3);
        }

        @Override
        public void m_5779_(FriendlyByteBuf p_134184_) {
            p_134184_.writeFloat(this.f_134121_);
            p_134184_.writeFloat(this.f_134122_);
            p_134184_.writeByte(this.f_134123_ ? 1 : 0);
        }
    }

    public static class Pos
    extends ServerboundMovePlayerPacket {
        public Pos(double p_134150_, double p_134151_, double p_134152_, boolean p_134153_) {
            super(p_134150_, p_134151_, p_134152_, 0.0f, 0.0f, p_134153_, true, false);
        }

        public static Pos m_179685_(FriendlyByteBuf p_179686_) {
            double $$1 = p_179686_.readDouble();
            double $$2 = p_179686_.readDouble();
            double $$3 = p_179686_.readDouble();
            boolean $$4 = p_179686_.readUnsignedByte() != 0;
            return new Pos($$1, $$2, $$3, $$4);
        }

        @Override
        public void m_5779_(FriendlyByteBuf p_134159_) {
            p_134159_.writeDouble(this.f_134118_);
            p_134159_.writeDouble(this.f_134119_);
            p_134159_.writeDouble(this.f_134120_);
            p_134159_.writeByte(this.f_134123_ ? 1 : 0);
        }
    }

    public static class PosRot
    extends ServerboundMovePlayerPacket {
        public PosRot(double p_134162_, double p_134163_, double p_134164_, float p_134165_, float p_134166_, boolean p_134167_) {
            super(p_134162_, p_134163_, p_134164_, p_134165_, p_134166_, p_134167_, true, true);
        }

        public static PosRot m_179687_(FriendlyByteBuf p_179688_) {
            double $$1 = p_179688_.readDouble();
            double $$2 = p_179688_.readDouble();
            double $$3 = p_179688_.readDouble();
            float $$4 = p_179688_.readFloat();
            float $$5 = p_179688_.readFloat();
            boolean $$6 = p_179688_.readUnsignedByte() != 0;
            return new PosRot($$1, $$2, $$3, $$4, $$5, $$6);
        }

        @Override
        public void m_5779_(FriendlyByteBuf p_134173_) {
            p_134173_.writeDouble(this.f_134118_);
            p_134173_.writeDouble(this.f_134119_);
            p_134173_.writeDouble(this.f_134120_);
            p_134173_.writeFloat(this.f_134121_);
            p_134173_.writeFloat(this.f_134122_);
            p_134173_.writeByte(this.f_134123_ ? 1 : 0);
        }
    }
}

