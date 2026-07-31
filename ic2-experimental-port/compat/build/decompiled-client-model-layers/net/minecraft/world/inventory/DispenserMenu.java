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
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class DispenserMenu
extends AbstractContainerMenu {
    private static final int f_150557_ = 9;
    private static final int f_150558_ = 9;
    private static final int f_150559_ = 36;
    private static final int f_150560_ = 36;
    private static final int f_150561_ = 45;
    private final Container f_39431_;

    public DispenserMenu(int p_39433_, Inventory p_39434_) {
        this(p_39433_, p_39434_, new SimpleContainer(9));
    }

    public DispenserMenu(int p_39436_, Inventory p_39437_, Container p_39438_) {
        super(MenuType.f_39963_, p_39436_);
        DispenserMenu.m_38869_(p_39438_, 9);
        this.f_39431_ = p_39438_;
        p_39438_.m_5856_(p_39437_.f_35978_);
        for (int $$3 = 0; $$3 < 3; ++$$3) {
            for (int $$4 = 0; $$4 < 3; ++$$4) {
                this.m_38897_(new Slot(p_39438_, $$4 + $$3 * 3, 62 + $$4 * 18, 17 + $$3 * 18));
            }
        }
        for (int $$5 = 0; $$5 < 3; ++$$5) {
            for (int $$6 = 0; $$6 < 9; ++$$6) {
                this.m_38897_(new Slot(p_39437_, $$6 + $$5 * 9 + 9, 8 + $$6 * 18, 84 + $$5 * 18));
            }
        }
        for (int $$7 = 0; $$7 < 9; ++$$7) {
            this.m_38897_(new Slot(p_39437_, $$7, 8 + $$7 * 18, 142));
        }
    }

    @Override
    public boolean m_6875_(Player p_39440_) {
        return this.f_39431_.m_6542_(p_39440_);
    }

    @Override
    public ItemStack m_7648_(Player p_39444_, int p_39445_) {
        ItemStack $$2 = ItemStack.f_41583_;
        Slot $$3 = (Slot)this.f_38839_.get(p_39445_);
        if ($$3 != null && $$3.m_6657_()) {
            ItemStack $$4 = $$3.m_7993_();
            $$2 = $$4.m_41777_();
            if (p_39445_ < 9 ? !this.m_38903_($$4, 9, 45, true) : !this.m_38903_($$4, 0, 9, false)) {
                return ItemStack.f_41583_;
            }
            if ($$4.m_41619_()) {
                $$3.m_5852_(ItemStack.f_41583_);
            } else {
                $$3.m_6654_();
            }
            if ($$4.m_41613_() == $$2.m_41613_()) {
                return ItemStack.f_41583_;
            }
            $$3.m_142406_(p_39444_, $$4);
        }
        return $$2;
    }

    @Override
    public void m_6877_(Player p_39442_) {
        super.m_6877_(p_39442_);
        this.f_39431_.m_5785_(p_39442_);
    }
}

