/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.chunk;

import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.util.VisibleForDebug;

public final class DataLayer {
    public static final int f_182480_ = 16;
    public static final int f_156339_ = 128;
    public static final int f_156338_ = 2048;
    private static final int f_156340_ = 4;
    @Nullable
    protected byte[] f_62551_;

    public DataLayer() {
    }

    public DataLayer(byte[] p_62556_) {
        this.f_62551_ = p_62556_;
        if (p_62556_.length != 2048) {
            throw Util.m_137570_(new IllegalArgumentException("DataLayer should be 2048 bytes not: " + p_62556_.length));
        }
    }

    protected DataLayer(int p_62554_) {
        this.f_62551_ = new byte[p_62554_];
    }

    public int m_62560_(int p_62561_, int p_62562_, int p_62563_) {
        return this.m_62570_(DataLayer.m_6406_(p_62561_, p_62562_, p_62563_));
    }

    public void m_62564_(int p_62565_, int p_62566_, int p_62567_, int p_62568_) {
        this.m_62557_(DataLayer.m_6406_(p_62565_, p_62566_, p_62567_), p_62568_);
    }

    private static int m_6406_(int p_62572_, int p_62573_, int p_62574_) {
        return p_62573_ << 8 | p_62574_ << 4 | p_62572_;
    }

    private int m_62570_(int p_62571_) {
        if (this.f_62551_ == null) {
            return 0;
        }
        int $$1 = DataLayer.m_62578_(p_62571_);
        int $$2 = DataLayer.m_182481_(p_62571_);
        return this.f_62551_[$$1] >> 4 * $$2 & 0xF;
    }

    private void m_62557_(int p_62558_, int p_62559_) {
        if (this.f_62551_ == null) {
            this.f_62551_ = new byte[2048];
        }
        int $$2 = DataLayer.m_62578_(p_62558_);
        int $$3 = DataLayer.m_182481_(p_62558_);
        int $$4 = ~(15 << 4 * $$3);
        int $$5 = (p_62559_ & 0xF) << 4 * $$3;
        this.f_62551_[$$2] = (byte)(this.f_62551_[$$2] & $$4 | $$5);
    }

    private static int m_182481_(int p_182482_) {
        return p_182482_ & 1;
    }

    private static int m_62578_(int p_62579_) {
        return p_62579_ >> 1;
    }

    public byte[] m_7877_() {
        if (this.f_62551_ == null) {
            this.f_62551_ = new byte[2048];
        }
        return this.f_62551_;
    }

    public DataLayer m_62569_() {
        if (this.f_62551_ == null) {
            return new DataLayer();
        }
        return new DataLayer((byte[])this.f_62551_.clone());
    }

    public String toString() {
        StringBuilder $$0 = new StringBuilder();
        for (int $$1 = 0; $$1 < 4096; ++$$1) {
            $$0.append(Integer.toHexString(this.m_62570_($$1)));
            if (($$1 & 0xF) == 15) {
                $$0.append("\n");
            }
            if (($$1 & 0xFF) != 255) continue;
            $$0.append("\n");
        }
        return $$0.toString();
    }

    @VisibleForDebug
    public String m_156341_(int p_156342_) {
        StringBuilder $$1 = new StringBuilder();
        for (int $$2 = 0; $$2 < 256; ++$$2) {
            $$1.append(Integer.toHexString(this.m_62570_($$2)));
            if (($$2 & 0xF) != 15) continue;
            $$1.append("\n");
        }
        return $$1.toString();
    }

    public boolean m_62575_() {
        return this.f_62551_ == null;
    }
}

