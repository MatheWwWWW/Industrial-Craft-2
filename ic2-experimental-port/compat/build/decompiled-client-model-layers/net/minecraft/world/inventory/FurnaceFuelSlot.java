/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FurnaceFuelSlot
extends Slot {
    private final AbstractFurnaceMenu f_39518_;

    public FurnaceFuelSlot(AbstractFurnaceMenu p_39520_, Container p_39521_, int p_39522_, int p_39523_, int p_39524_) {
        super(p_39521_, p_39522_, p_39523_, p_39524_);
        this.f_39518_ = p_39520_;
    }

    @Override
    public boolean m_5857_(ItemStack p_39526_) {
        return this.f_39518_.m_38988_(p_39526_) || FurnaceFuelSlot.m_39529_(p_39526_);
    }

    @Override
    public int m_5866_(ItemStack p_39528_) {
        return FurnaceFuelSlot.m_39529_(p_39528_) ? 1 : super.m_5866_(p_39528_);
    }

    public static boolean m_39529_(ItemStack p_39530_) {
        return p_39530_.m_150930_(Items.f_42446_);
    }
}

