/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.control;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.Control;

public class JumpControl
implements Control {
    private final Mob f_24898_;
    protected boolean f_24897_;

    public JumpControl(Mob p_24900_) {
        this.f_24898_ = p_24900_;
    }

    public void m_24901_() {
        this.f_24897_ = true;
    }

    public void m_8124_() {
        this.f_24898_.m_6862_(this.f_24897_);
        this.f_24897_ = false;
    }
}

