/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.util.EnumSet;
import java.util.Set;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundPlayerPositionPacket
implements Packet<ClientGamePacketListener> {
    private final double f_132796_;
    private final double f_132797_;
    private final double f_132798_;
    private final float f_132799_;
    private final float f_132800_;
    private final Set<RelativeArgument> f_132801_;
    private final int f_132802_;
    private final boolean f_179147_;

    public ClientboundPlayerPositionPacket(double p_179149_, double p_179150_, double p_179151_, float p_179152_, float p_179153_, Set<RelativeArgument> p_179154_, int p_179155_, boolean p_179156_) {
        this.f_132796_ = p_179149_;
        this.f_132797_ = p_179150_;
        this.f_132798_ = p_179151_;
        this.f_132799_ = p_179152_;
        this.f_132800_ = p_179153_;
        this.f_132801_ = p_179154_;
        this.f_132802_ = p_179155_;
        this.f_179147_ = p_179156_;
    }

    public ClientboundPlayerPositionPacket(FriendlyByteBuf p_179158_) {
        this.f_132796_ = p_179158_.readDouble();
        this.f_132797_ = p_179158_.readDouble();
        this.f_132798_ = p_179158_.readDouble();
        this.f_132799_ = p_179158_.readFloat();
        this.f_132800_ = p_179158_.readFloat();
        this.f_132801_ = RelativeArgument.m_132840_(p_179158_.readUnsignedByte());
        this.f_132802_ = p_179158_.m_130242_();
        this.f_179147_ = p_179158_.readBoolean();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132820_) {
        p_132820_.writeDouble(this.f_132796_);
        p_132820_.writeDouble(this.f_132797_);
        p_132820_.writeDouble(this.f_132798_);
        p_132820_.writeFloat(this.f_132799_);
        p_132820_.writeFloat(this.f_132800_);
        p_132820_.writeByte(RelativeArgument.m_132842_(this.f_132801_));
        p_132820_.m_130130_(this.f_132802_);
        p_132820_.writeBoolean(this.f_179147_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132817_) {
        p_132817_.m_5682_(this);
    }

    public double m_132818_() {
        return this.f_132796_;
    }

    public double m_132821_() {
        return this.f_132797_;
    }

    public double m_132822_() {
        return this.f_132798_;
    }

    public float m_132823_() {
        return this.f_132799_;
    }

    public float m_132824_() {
        return this.f_132800_;
    }

    public int m_132825_() {
        return this.f_132802_;
    }

    public boolean m_179159_() {
        return this.f_179147_;
    }

    public Set<RelativeArgument> m_132826_() {
        return this.f_132801_;
    }

    public static final class RelativeArgument
    extends Enum<RelativeArgument> {
        public static final /* enum */ RelativeArgument X = new RelativeArgument(0);
        public static final /* enum */ RelativeArgument Y = new RelativeArgument(1);
        public static final /* enum */ RelativeArgument Z = new RelativeArgument(2);
        public static final /* enum */ RelativeArgument Y_ROT = new RelativeArgument(3);
        public static final /* enum */ RelativeArgument X_ROT = new RelativeArgument(4);
        private final int f_132832_;
        private static final /* synthetic */ RelativeArgument[] $VALUES;

        public static RelativeArgument[] values() {
            return (RelativeArgument[])$VALUES.clone();
        }

        public static RelativeArgument valueOf(String p_132847_) {
            return Enum.valueOf(RelativeArgument.class, p_132847_);
        }

        private RelativeArgument(int p_132838_) {
            this.f_132832_ = p_132838_;
        }

        private int m_132839_() {
            return 1 << this.f_132832_;
        }

        private boolean m_132844_(int p_132845_) {
            return (p_132845_ & this.m_132839_()) == this.m_132839_();
        }

        public static Set<RelativeArgument> m_132840_(int p_132841_) {
            EnumSet<RelativeArgument> $$1 = EnumSet.noneOf(RelativeArgument.class);
            for (RelativeArgument $$2 : RelativeArgument.values()) {
                if (!$$2.m_132844_(p_132841_)) continue;
                $$1.add($$2);
            }
            return $$1;
        }

        public static int m_132842_(Set<RelativeArgument> p_132843_) {
            int $$1 = 0;
            for (RelativeArgument $$2 : p_132843_) {
                $$1 |= $$2.m_132839_();
            }
            return $$1;
        }

        private static /* synthetic */ RelativeArgument[] m_179160_() {
            return new RelativeArgument[]{X, Y, Z, Y_ROT, X_ROT};
        }

        static {
            $VALUES = RelativeArgument.m_179160_();
        }
    }
}

