/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.chat;

import net.minecraft.network.chat.Component;

public class ThrowingComponent
extends Exception {
    private final Component f_237302_;

    public ThrowingComponent(Component p_237304_) {
        super(p_237304_.getString());
        this.f_237302_ = p_237304_;
    }

    public ThrowingComponent(Component p_237306_, Throwable p_237307_) {
        super(p_237306_.getString(), p_237307_);
        this.f_237302_ = p_237306_;
    }

    public Component m_237308_() {
        return this.f_237302_;
    }
}

