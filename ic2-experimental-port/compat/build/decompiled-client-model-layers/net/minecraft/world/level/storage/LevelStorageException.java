/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage;

import net.minecraft.network.chat.Component;

public class LevelStorageException
extends RuntimeException {
    private final Component f_230803_;

    public LevelStorageException(Component p_230805_) {
        super(p_230805_.getString());
        this.f_230803_ = p_230805_;
    }

    public Component m_230806_() {
        return this.f_230803_;
    }
}

