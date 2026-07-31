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

public class HopperMenu
extends AbstractContainerMenu {
    public static final int f_150576_ = 5;
    private final Container f_39638_;

    public HopperMenu(int p_39640_, Inventory p_39641_) {
        this(p_39640_, p_39641_, new SimpleContainer(5));
    }

    public HopperMenu(int p_39643_, Inventory p_39644_, Container p_39645_) {
        super(MenuType.f_39972_, p_39643_);
        this.f_39638_ = p_39645_;
        HopperMenu.m_38869_(p_39645_, 5);
        p_39645_.m_5856_(p_39644_.f_35978_);
        int $$3 = 51;
        for (int $$4 = 0; $$4 < 5; ++$$4) {
            this.m_38897_(new Slot(p_39645_, $$4, 44 + $$4 * 18, 20));
        }
        for (int $$5 = 0; $$5 < 3; ++$$5) {
            for (int $$6 = 0; $$6 < 9; ++$$6) {
                this.m_38897_(new Slot(p_39644_, $$6 + $$5 * 9 + 9, 8 + $$6 * 18, $$5 * 18 + 51));
            }
        }
        for (int $$7 = 0; $$7 < 9; ++$$7) {
            this.m_38897_(new Slot(p_39644_, $$7, 8 + $$7 * 18, 109));
        }
    }

    @Override
    public boolean m_6875_(Player p_39647_) {
        return this.f_39638_.m_6542_(p_39647_);
    }

    @Override
    public ItemStack m_7648_(Player p_39651_, int p_39652_) {
        ItemStack $$2 = ItemStack.f_41583_;
        Slot $$3 = (Slot)this.f_38839_.get(p_39652_);
        if ($$3 != null && $$3.m_6657_()) {
            ItemStack $$4 = $$3.m_7993_();
            $$2 = $$4.m_41777_();
            if (p_39652_ < this.f_39638_.m_6643_() ? !this.m_38903_($$4, this.f_39638_.m_6643_(), this.f_38839_.size(), true) : !this.m_38903_($$4, 0, this.f_39638_.m_6643_(), false)) {
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
    public void m_6877_(Player p_39649_) {
        super.m_6877_(p_39649_);
        this.f_39638_.m_5785_(p_39649_);
    }
}

