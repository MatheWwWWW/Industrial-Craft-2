/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ShulkerBoxSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ShulkerBoxMenu
extends AbstractContainerMenu {
    private static final int f_150640_ = 27;
    private final Container f_40186_;

    public ShulkerBoxMenu(int p_40188_, Inventory p_40189_) {
        this(p_40188_, p_40189_, new SimpleContainer(27));
    }

    public ShulkerBoxMenu(int p_40191_, Inventory p_40192_, Container p_40193_) {
        super(MenuType.f_39976_, p_40191_);
        ShulkerBoxMenu.m_38869_(p_40193_, 27);
        this.f_40186_ = p_40193_;
        p_40193_.m_5856_(p_40192_.f_35978_);
        int $$3 = 3;
        int $$4 = 9;
        for (int $$5 = 0; $$5 < 3; ++$$5) {
            for (int $$6 = 0; $$6 < 9; ++$$6) {
                this.m_38897_(new ShulkerBoxSlot(p_40193_, $$6 + $$5 * 9, 8 + $$6 * 18, 18 + $$5 * 18));
            }
        }
        for (int $$7 = 0; $$7 < 3; ++$$7) {
            for (int $$8 = 0; $$8 < 9; ++$$8) {
                this.m_38897_(new Slot(p_40192_, $$8 + $$7 * 9 + 9, 8 + $$8 * 18, 84 + $$7 * 18));
            }
        }
        for (int $$9 = 0; $$9 < 9; ++$$9) {
            this.m_38897_(new Slot(p_40192_, $$9, 8 + $$9 * 18, 142));
        }
    }

    @Override
    public boolean m_6875_(Player p_40195_) {
        return this.f_40186_.m_6542_(p_40195_);
    }

    @Override
    public ItemStack m_7648_(Player p_40199_, int p_40200_) {
        ItemStack $$2 = ItemStack.f_41583_;
        Slot $$3 = (Slot)this.f_38839_.get(p_40200_);
        if ($$3 != null && $$3.m_6657_()) {
            ItemStack $$4 = $$3.m_7993_();
            $$2 = $$4.m_41777_();
            if (p_40200_ < this.f_40186_.m_6643_() ? !this.m_38903_($$4, this.f_40186_.m_6643_(), this.f_38839_.size(), true) : !this.m_38903_($$4, 0, this.f_40186_.m_6643_(), false)) {
                return ItemStack.f_41583_;
            }
            if ($$4.m_41619_()) {
                $$3.m_5852_(ItemStack.f_41583_);
            } else {
                $$3.m_6654_();
            }
        }
        return $$2;
    }

    @Override
    public void m_6877_(Player p_40197_) {
        super.m_6877_(p_40197_);
        this.f_40186_.m_5785_(p_40197_);
    }
}

