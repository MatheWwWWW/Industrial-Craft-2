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

public class ChestMenu
extends AbstractContainerMenu {
    private static final int f_150511_ = 9;
    private final Container f_39221_;
    private final int f_39222_;

    private ChestMenu(MenuType<?> p_39224_, int p_39225_, Inventory p_39226_, int p_39227_) {
        this(p_39224_, p_39225_, p_39226_, new SimpleContainer(9 * p_39227_), p_39227_);
    }

    public static ChestMenu m_39234_(int p_39235_, Inventory p_39236_) {
        return new ChestMenu(MenuType.f_39957_, p_39235_, p_39236_, 1);
    }

    public static ChestMenu m_39243_(int p_39244_, Inventory p_39245_) {
        return new ChestMenu(MenuType.f_39958_, p_39244_, p_39245_, 2);
    }

    public static ChestMenu m_39255_(int p_39256_, Inventory p_39257_) {
        return new ChestMenu(MenuType.f_39959_, p_39256_, p_39257_, 3);
    }

    public static ChestMenu m_39258_(int p_39259_, Inventory p_39260_) {
        return new ChestMenu(MenuType.f_39960_, p_39259_, p_39260_, 4);
    }

    public static ChestMenu m_39262_(int p_39263_, Inventory p_39264_) {
        return new ChestMenu(MenuType.f_39961_, p_39263_, p_39264_, 5);
    }

    public static ChestMenu m_39266_(int p_39267_, Inventory p_39268_) {
        return new ChestMenu(MenuType.f_39962_, p_39267_, p_39268_, 6);
    }

    public static ChestMenu m_39237_(int p_39238_, Inventory p_39239_, Container p_39240_) {
        return new ChestMenu(MenuType.f_39959_, p_39238_, p_39239_, p_39240_, 3);
    }

    public static ChestMenu m_39246_(int p_39247_, Inventory p_39248_, Container p_39249_) {
        return new ChestMenu(MenuType.f_39962_, p_39247_, p_39248_, p_39249_, 6);
    }

    public ChestMenu(MenuType<?> p_39229_, int p_39230_, Inventory p_39231_, Container p_39232_, int p_39233_) {
        super(p_39229_, p_39230_);
        ChestMenu.m_38869_(p_39232_, p_39233_ * 9);
        this.f_39221_ = p_39232_;
        this.f_39222_ = p_39233_;
        p_39232_.m_5856_(p_39231_.f_35978_);
        int $$5 = (this.f_39222_ - 4) * 18;
        for (int $$6 = 0; $$6 < this.f_39222_; ++$$6) {
            for (int $$7 = 0; $$7 < 9; ++$$7) {
                this.m_38897_(new Slot(p_39232_, $$7 + $$6 * 9, 8 + $$7 * 18, 18 + $$6 * 18));
            }
        }
        for (int $$8 = 0; $$8 < 3; ++$$8) {
            for (int $$9 = 0; $$9 < 9; ++$$9) {
                this.m_38897_(new Slot(p_39231_, $$9 + $$8 * 9 + 9, 8 + $$9 * 18, 103 + $$8 * 18 + $$5));
            }
        }
        for (int $$10 = 0; $$10 < 9; ++$$10) {
            this.m_38897_(new Slot(p_39231_, $$10, 8 + $$10 * 18, 161 + $$5));
        }
    }

    @Override
    public boolean m_6875_(Player p_39242_) {
        return this.f_39221_.m_6542_(p_39242_);
    }

    @Override
    public ItemStack m_7648_(Player p_39253_, int p_39254_) {
        ItemStack $$2 = ItemStack.f_41583_;
        Slot $$3 = (Slot)this.f_38839_.get(p_39254_);
        if ($$3 != null && $$3.m_6657_()) {
            ItemStack $$4 = $$3.m_7993_();
            $$2 = $$4.m_41777_();
            if (p_39254_ < this.f_39222_ * 9 ? !this.m_38903_($$4, this.f_39222_ * 9, this.f_38839_.size(), true) : !this.m_38903_($$4, 0, this.f_39222_ * 9, false)) {
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
    public void m_6877_(Player p_39251_) {
        super.m_6877_(p_39251_);
        this.f_39221_.m_5785_(p_39251_);
    }

    public Container m_39261_() {
        return this.f_39221_;
    }

    public int m_39265_() {
        return this.f_39222_;
    }
}

