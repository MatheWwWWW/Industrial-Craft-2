/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  org.slf4j.Logger
 */
package net.minecraft.util.random;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import org.slf4j.Logger;

public class Weight {
    public static final Codec<Weight> f_146274_ = Codec.INT.xmap(Weight::m_146282_, Weight::m_146281_);
    private static final Weight f_146275_ = new Weight(1);
    private static final Logger f_146276_ = LogUtils.getLogger();
    private final int f_146277_;

    private Weight(int p_146280_) {
        this.f_146277_ = p_146280_;
    }

    public static Weight m_146282_(int p_146283_) {
        if (p_146283_ == 1) {
            return f_146275_;
        }
        Weight.m_146284_(p_146283_);
        return new Weight(p_146283_);
    }

    public int m_146281_() {
        return this.f_146277_;
    }

    private static void m_146284_(int p_146285_) {
        if (p_146285_ < 0) {
            throw Util.m_137570_(new IllegalArgumentException("Weight should be >= 0"));
        }
        if (p_146285_ == 0 && SharedConstants.f_136183_) {
            f_146276_.warn("Found 0 weight, make sure this is intentional!");
        }
    }

    public String toString() {
        return Integer.toString(this.f_146277_);
    }

    public int hashCode() {
        return Integer.hashCode(this.f_146277_);
    }

    public boolean equals(Object p_146287_) {
        if (this == p_146287_) {
            return true;
        }
        return p_146287_ instanceof Weight && this.f_146277_ == ((Weight)p_146287_).f_146277_;
    }
}

