/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.Util;

public class MipmapGenerator {
    private static final int f_174686_ = 96;
    private static final float[] f_118038_ = Util.m_137469_(new float[256], p_118058_ -> {
        for (int $$1 = 0; $$1 < ((float[])p_118058_).length; ++$$1) {
            p_118058_[$$1] = (float)Math.pow((float)$$1 / 255.0f, 2.2);
        }
    });

    private MipmapGenerator() {
    }

    public static NativeImage[] m_118054_(NativeImage p_118055_, int p_118056_) {
        NativeImage[] $$2 = new NativeImage[p_118056_ + 1];
        $$2[0] = p_118055_;
        if (p_118056_ > 0) {
            boolean $$3 = false;
            block0: for (int $$4 = 0; $$4 < p_118055_.m_84982_(); ++$$4) {
                for (int $$5 = 0; $$5 < p_118055_.m_85084_(); ++$$5) {
                    if (p_118055_.m_84985_($$4, $$5) >> 24 != 0) continue;
                    $$3 = true;
                    break block0;
                }
            }
            for (int $$6 = 1; $$6 <= p_118056_; ++$$6) {
                NativeImage $$7 = $$2[$$6 - 1];
                NativeImage $$8 = new NativeImage($$7.m_84982_() >> 1, $$7.m_85084_() >> 1, false);
                int $$9 = $$8.m_84982_();
                int $$10 = $$8.m_85084_();
                for (int $$11 = 0; $$11 < $$9; ++$$11) {
                    for (int $$12 = 0; $$12 < $$10; ++$$12) {
                        $$8.m_84988_($$11, $$12, MipmapGenerator.m_118048_($$7.m_84985_($$11 * 2 + 0, $$12 * 2 + 0), $$7.m_84985_($$11 * 2 + 1, $$12 * 2 + 0), $$7.m_84985_($$11 * 2 + 0, $$12 * 2 + 1), $$7.m_84985_($$11 * 2 + 1, $$12 * 2 + 1), $$3));
                    }
                }
                $$2[$$6] = $$8;
            }
        }
        return $$2;
    }

    private static int m_118048_(int p_118049_, int p_118050_, int p_118051_, int p_118052_, boolean p_118053_) {
        if (p_118053_) {
            float $$5 = 0.0f;
            float $$6 = 0.0f;
            float $$7 = 0.0f;
            float $$8 = 0.0f;
            if (p_118049_ >> 24 != 0) {
                $$5 += MipmapGenerator.m_118040_(p_118049_ >> 24);
                $$6 += MipmapGenerator.m_118040_(p_118049_ >> 16);
                $$7 += MipmapGenerator.m_118040_(p_118049_ >> 8);
                $$8 += MipmapGenerator.m_118040_(p_118049_ >> 0);
            }
            if (p_118050_ >> 24 != 0) {
                $$5 += MipmapGenerator.m_118040_(p_118050_ >> 24);
                $$6 += MipmapGenerator.m_118040_(p_118050_ >> 16);
                $$7 += MipmapGenerator.m_118040_(p_118050_ >> 8);
                $$8 += MipmapGenerator.m_118040_(p_118050_ >> 0);
            }
            if (p_118051_ >> 24 != 0) {
                $$5 += MipmapGenerator.m_118040_(p_118051_ >> 24);
                $$6 += MipmapGenerator.m_118040_(p_118051_ >> 16);
                $$7 += MipmapGenerator.m_118040_(p_118051_ >> 8);
                $$8 += MipmapGenerator.m_118040_(p_118051_ >> 0);
            }
            if (p_118052_ >> 24 != 0) {
                $$5 += MipmapGenerator.m_118040_(p_118052_ >> 24);
                $$6 += MipmapGenerator.m_118040_(p_118052_ >> 16);
                $$7 += MipmapGenerator.m_118040_(p_118052_ >> 8);
                $$8 += MipmapGenerator.m_118040_(p_118052_ >> 0);
            }
            int $$9 = (int)(Math.pow($$5 /= 4.0f, 0.45454545454545453) * 255.0);
            int $$10 = (int)(Math.pow($$6 /= 4.0f, 0.45454545454545453) * 255.0);
            int $$11 = (int)(Math.pow($$7 /= 4.0f, 0.45454545454545453) * 255.0);
            int $$12 = (int)(Math.pow($$8 /= 4.0f, 0.45454545454545453) * 255.0);
            if ($$9 < 96) {
                $$9 = 0;
            }
            return $$9 << 24 | $$10 << 16 | $$11 << 8 | $$12;
        }
        int $$13 = MipmapGenerator.m_118042_(p_118049_, p_118050_, p_118051_, p_118052_, 24);
        int $$14 = MipmapGenerator.m_118042_(p_118049_, p_118050_, p_118051_, p_118052_, 16);
        int $$15 = MipmapGenerator.m_118042_(p_118049_, p_118050_, p_118051_, p_118052_, 8);
        int $$16 = MipmapGenerator.m_118042_(p_118049_, p_118050_, p_118051_, p_118052_, 0);
        return $$13 << 24 | $$14 << 16 | $$15 << 8 | $$16;
    }

    private static int m_118042_(int p_118043_, int p_118044_, int p_118045_, int p_118046_, int p_118047_) {
        float $$5 = MipmapGenerator.m_118040_(p_118043_ >> p_118047_);
        float $$6 = MipmapGenerator.m_118040_(p_118044_ >> p_118047_);
        float $$7 = MipmapGenerator.m_118040_(p_118045_ >> p_118047_);
        float $$8 = MipmapGenerator.m_118040_(p_118046_ >> p_118047_);
        float $$9 = (float)((double)((float)Math.pow((double)($$5 + $$6 + $$7 + $$8) * 0.25, 0.45454545454545453)));
        return (int)((double)$$9 * 255.0);
    }

    private static float m_118040_(int p_118041_) {
        return f_118038_[p_118041_ & 0xFF];
    }
}

