/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.core.dispenser;

import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;

public abstract class OptionalDispenseItemBehavior
extends DefaultDispenseItemBehavior {
    private boolean f_123568_ = true;

    public boolean m_123570_() {
        return this.f_123568_;
    }

    public void m_123573_(boolean p_123574_) {
        this.f_123568_ = p_123574_;
    }

    @Override
    protected void m_6823_(BlockSource p_123572_) {
        p_123572_.m_7727_().m_46796_(this.m_123570_() ? 1000 : 1001, p_123572_.m_7961_(), 0);
    }
}

