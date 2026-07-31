/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public interface Nameable {
    public Component m_7755_();

    default public boolean m_8077_() {
        return this.m_7770_() != null;
    }

    default public Component m_5446_() {
        return this.m_7755_();
    }

    @Nullable
    default public Component m_7770_() {
        return null;
    }
}

