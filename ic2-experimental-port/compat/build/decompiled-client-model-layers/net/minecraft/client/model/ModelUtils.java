/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

public class ModelUtils {
    public static float m_103125_(float p_103126_, float p_103127_, float p_103128_) {
        float $$3;
        for ($$3 = p_103127_ - p_103126_; $$3 < (float)(-Math.PI); $$3 += (float)Math.PI * 2) {
        }
        while ($$3 >= (float)Math.PI) {
            $$3 -= (float)Math.PI * 2;
        }
        return p_103126_ + p_103128_ * $$3;
    }
}

