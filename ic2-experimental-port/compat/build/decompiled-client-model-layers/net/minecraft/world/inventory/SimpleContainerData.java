/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.world.inventory.ContainerData;

public class SimpleContainerData
implements ContainerData {
    private final int[] f_40208_;

    public SimpleContainerData(int p_40210_) {
        this.f_40208_ = new int[p_40210_];
    }

    @Override
    public int m_6413_(int p_40213_) {
        return this.f_40208_[p_40213_];
    }

    @Override
    public void m_8050_(int p_40215_, int p_40216_) {
        this.f_40208_[p_40215_] = p_40216_;
    }

    @Override
    public int m_6499_() {
        return this.f_40208_.length;
    }
}

