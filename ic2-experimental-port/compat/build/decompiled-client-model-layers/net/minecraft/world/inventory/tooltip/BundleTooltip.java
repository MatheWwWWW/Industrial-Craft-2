/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory.tooltip;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public class BundleTooltip
implements TooltipComponent {
    private final NonNullList<ItemStack> f_150674_;
    private final int f_150675_;

    public BundleTooltip(NonNullList<ItemStack> p_150677_, int p_150678_) {
        this.f_150674_ = p_150677_;
        this.f_150675_ = p_150678_;
    }

    public NonNullList<ItemStack> m_150679_() {
        return this.f_150674_;
    }

    public int m_150680_() {
        return this.f_150675_;
    }
}

