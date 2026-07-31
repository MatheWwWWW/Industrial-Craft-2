/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class HorseInventoryMenu
extends AbstractContainerMenu {
    private final Container f_39653_;
    private final AbstractHorse f_39654_;

    public HorseInventoryMenu(int p_39656_, Inventory p_39657_, Container p_39658_, final AbstractHorse p_39659_) {
        super(null, p_39656_);
        this.f_39653_ = p_39658_;
        this.f_39654_ = p_39659_;
        int $$4 = 3;
        p_39658_.m_5856_(p_39657_.f_35978_);
        int $$5 = -18;
        this.m_38897_(new Slot(p_39658_, 0, 8, 18){

            @Override
            public boolean m_5857_(ItemStack p_39677_) {
                return p_39677_.m_150930_(Items.f_42450_) && !this.m_6657_() && p_39659_.m_6741_();
            }

            @Override
            public boolean m_6659_() {
                return p_39659_.m_6741_();
            }
        });
        this.m_38897_(new Slot(p_39658_, 1, 8, 36){

            @Override
            public boolean m_5857_(ItemStack p_39690_) {
                return p_39659_.m_6010_(p_39690_);
            }

            @Override
            public boolean m_6659_() {
                return p_39659_.m_7482_();
            }

            @Override
            public int m_6641_() {
                return 1;
            }
        });
        if (this.m_150577_(p_39659_)) {
            for (int $$6 = 0; $$6 < 3; ++$$6) {
                for (int $$7 = 0; $$7 < ((AbstractChestedHorse)p_39659_).m_7488_(); ++$$7) {
                    this.m_38897_(new Slot(p_39658_, 2 + $$7 + $$6 * ((AbstractChestedHorse)p_39659_).m_7488_(), 80 + $$7 * 18, 18 + $$6 * 18));
                }
            }
        }
        for (int $$8 = 0; $$8 < 3; ++$$8) {
            for (int $$9 = 0; $$9 < 9; ++$$9) {
                this.m_38897_(new Slot(p_39657_, $$9 + $$8 * 9 + 9, 8 + $$9 * 18, 102 + $$8 * 18 + -18));
            }
        }
        for (int $$10 = 0; $$10 < 9; ++$$10) {
            this.m_38897_(new Slot(p_39657_, $$10, 8 + $$10 * 18, 142));
        }
    }

    @Override
    public boolean m_6875_(Player p_39661_) {
        return !this.f_39654_.m_149511_(this.f_39653_) && this.f_39653_.m_6542_(p_39661_) && this.f_39654_.m_6084_() && this.f_39654_.m_20270_(p_39661_) < 8.0f;
    }

    private boolean m_150577_(AbstractHorse p_150578_) {
        return p_150578_ instanceof AbstractChestedHorse && ((AbstractChestedHorse)p_150578_).m_30502_();
    }

    @Override
    public ItemStack m_7648_(Player p_39665_, int p_39666_) {
        ItemStack $$2 = ItemStack.f_41583_;
        Slot $$3 = (Slot)this.f_38839_.get(p_39666_);
        if ($$3 != null && $$3.m_6657_()) {
            ItemStack $$4 = $$3.m_7993_();
            $$2 = $$4.m_41777_();
            int $$5 = this.f_39653_.m_6643_();
            if (p_39666_ < $$5) {
                if (!this.m_38903_($$4, $$5, this.f_38839_.size(), true)) {
                    return ItemStack.f_41583_;
                }
            } else if (this.m_38853_(1).m_5857_($$4) && !this.m_38853_(1).m_6657_()) {
                if (!this.m_38903_($$4, 1, 2, false)) {
                    return ItemStack.f_41583_;
                }
            } else if (this.m_38853_(0).m_5857_($$4)) {
                if (!this.m_38903_($$4, 0, 1, false)) {
                    return ItemStack.f_41583_;
                }
            } else if ($$5 <= 2 || !this.m_38903_($$4, 2, $$5, false)) {
                int $$7;
                int $$6 = $$5;
                int $$8 = $$7 = $$6 + 27;
                int $$9 = $$8 + 9;
                if (p_39666_ >= $$8 && p_39666_ < $$9 ? !this.m_38903_($$4, $$6, $$7, false) : (p_39666_ >= $$6 && p_39666_ < $$7 ? !this.m_38903_($$4, $$8, $$9, false) : !this.m_38903_($$4, $$8, $$7, false))) {
                    return ItemStack.f_41583_;
                }
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
    public void m_6877_(Player p_39663_) {
        super.m_6877_(p_39663_);
        this.f_39653_.m_5785_(p_39663_);
    }
}

