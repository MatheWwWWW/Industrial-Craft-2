/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.mojang.realmsclient.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.annotation.Nullable;

public class SkinProcessor {
    private int[] f_90232_;
    private int f_90233_;
    private int f_90234_;

    @Nullable
    public BufferedImage m_90241_(@Nullable BufferedImage p_90242_) {
        boolean $$3;
        if (p_90242_ == null) {
            return null;
        }
        this.f_90233_ = 64;
        this.f_90234_ = 64;
        BufferedImage $$1 = new BufferedImage(this.f_90233_, this.f_90234_, 2);
        Graphics $$2 = $$1.getGraphics();
        $$2.drawImage(p_90242_, 0, 0, null);
        boolean bl = $$3 = p_90242_.getHeight() == 32;
        if ($$3) {
            $$2.setColor(new Color(0, 0, 0, 0));
            $$2.fillRect(0, 32, 64, 32);
            $$2.drawImage($$1, 24, 48, 20, 52, 4, 16, 8, 20, null);
            $$2.drawImage($$1, 28, 48, 24, 52, 8, 16, 12, 20, null);
            $$2.drawImage($$1, 20, 52, 16, 64, 8, 20, 12, 32, null);
            $$2.drawImage($$1, 24, 52, 20, 64, 4, 20, 8, 32, null);
            $$2.drawImage($$1, 28, 52, 24, 64, 0, 20, 4, 32, null);
            $$2.drawImage($$1, 32, 52, 28, 64, 12, 20, 16, 32, null);
            $$2.drawImage($$1, 40, 48, 36, 52, 44, 16, 48, 20, null);
            $$2.drawImage($$1, 44, 48, 40, 52, 48, 16, 52, 20, null);
            $$2.drawImage($$1, 36, 52, 32, 64, 48, 20, 52, 32, null);
            $$2.drawImage($$1, 40, 52, 36, 64, 44, 20, 48, 32, null);
            $$2.drawImage($$1, 44, 52, 40, 64, 40, 20, 44, 32, null);
            $$2.drawImage($$1, 48, 52, 44, 64, 52, 20, 56, 32, null);
        }
        $$2.dispose();
        this.f_90232_ = ((DataBufferInt)$$1.getRaster().getDataBuffer()).getData();
        this.m_90243_(0, 0, 32, 16);
        if ($$3) {
            this.m_90236_(32, 0, 64, 32);
        }
        this.m_90243_(0, 16, 64, 32);
        this.m_90243_(16, 48, 48, 64);
        return $$1;
    }

    private void m_90236_(int p_90237_, int p_90238_, int p_90239_, int p_90240_) {
        for (int $$4 = p_90237_; $$4 < p_90239_; ++$$4) {
            for (int $$5 = p_90238_; $$5 < p_90240_; ++$$5) {
                int $$6 = this.f_90232_[$$4 + $$5 * this.f_90233_];
                if (($$6 >> 24 & 0xFF) >= 128) continue;
                return;
            }
        }
        for (int $$7 = p_90237_; $$7 < p_90239_; ++$$7) {
            for (int $$8 = p_90238_; $$8 < p_90240_; ++$$8) {
                int n = $$7 + $$8 * this.f_90233_;
                this.f_90232_[n] = this.f_90232_[n] & 0xFFFFFF;
            }
        }
    }

    private void m_90243_(int p_90244_, int p_90245_, int p_90246_, int p_90247_) {
        for (int $$4 = p_90244_; $$4 < p_90246_; ++$$4) {
            for (int $$5 = p_90245_; $$5 < p_90247_; ++$$5) {
                int n = $$4 + $$5 * this.f_90233_;
                this.f_90232_[n] = this.f_90232_[n] | 0xFF000000;
            }
        }
    }
}

