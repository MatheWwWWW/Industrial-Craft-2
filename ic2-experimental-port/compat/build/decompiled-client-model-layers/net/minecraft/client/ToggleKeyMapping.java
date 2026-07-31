/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.function.BooleanSupplier;
import net.minecraft.client.KeyMapping;

public class ToggleKeyMapping
extends KeyMapping {
    private final BooleanSupplier f_92527_;

    public ToggleKeyMapping(String p_92529_, int p_92530_, String p_92531_, BooleanSupplier p_92532_) {
        super(p_92529_, InputConstants.Type.KEYSYM, p_92530_, p_92531_);
        this.f_92527_ = p_92532_;
    }

    @Override
    public void m_7249_(boolean p_92534_) {
        if (this.f_92527_.getAsBoolean()) {
            if (p_92534_) {
                super.m_7249_(!this.m_90857_());
            }
        } else {
            super.m_7249_(p_92534_);
        }
    }
}

