/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world;

import javax.annotation.Nullable;

public interface Clearable {
    public void m_6211_();

    public static void m_18908_(@Nullable Object p_18909_) {
        if (p_18909_ instanceof Clearable) {
            ((Clearable)p_18909_).m_6211_();
        }
    }
}

