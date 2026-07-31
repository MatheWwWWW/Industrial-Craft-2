/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.inventory;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ItemCombinerMenu
extends AbstractContainerMenu {
    public static final int f_150595_ = 0;
    public static final int f_150596_ = 1;
    public static final int f_150597_ = 2;
    private static final int f_150594_ = 3;
    private static final int f_150598_ = 30;
    private static final int f_150599_ = 30;
    private static final int f_150600_ = 39;
    protected final ResultContainer f_39768_ = new ResultContainer();
    protected final Container f_39769_ = new SimpleContainer(2){

        @Override
        public void m_6596_() {
            super.m_6596_();
            ItemCombinerMenu.this.m_6199_(this);
        }
    };
    protected final ContainerLevelAccess f_39770_;
    protected final Player f_39771_;

    protected abstract boolean m_6560_(Player var1, boolean var2);

    protected abstract void m_142365_(Player var1, ItemStack var2);

    protected abstract boolean m_8039_(BlockState var1);

    public ItemCombinerMenu(@Nullable MenuType<?> p_39773_, int p_39774_, Inventory p_39775_, ContainerLevelAccess p_39776_) {
        super(p_39773_, p_39774_);
        this.f_39770_ = p_39776_;
        this.f_39771_ = p_39775_.f_35978_;
        this.m_38897_(new Slot(this.f_39769_, 0, 27, 47));
        this.m_38897_(new Slot(this.f_39769_, 1, 76, 47));
        this.m_38897_(new Slot(this.f_39768_, 2, 134, 47){

            @Override
            public boolean m_5857_(ItemStack p_39818_) {
                return false;
            }

            @Override
            public boolean m_8010_(Player p_39813_) {
                return ItemCombinerMenu.this.m_6560_(p_39813_, this.m_6657_());
            }

            @Override
            public void m_142406_(Player p_150604_, ItemStack p_150605_) {
                ItemCombinerMenu.this.m_142365_(p_150604_, p_150605_);
            }
        });
        for (int $$4 = 0; $$4 < 3; ++$$4) {
            for (int $$5 = 0; $$5 < 9; ++$$5) {
                this.m_38897_(new Slot(p_39775_, $$5 + $$4 * 9 + 9, 8 + $$5 * 18, 84 + $$4 * 18));
            }
        }
        for (int $$6 = 0; $$6 < 9; ++$$6) {
            this.m_38897_(new Slot(p_39775_, $$6, 8 + $$6 * 18, 142));
        }
    }

    public abstract void m_6640_();

    @Override
    public void m_6199_(Container p_39778_) {
        super.m_6199_(p_39778_);
        if (p_39778_ == this.f_39769_) {
            this.m_6640_();
        }
    }

    @Override
    public void m_6877_(Player p_39790_) {
        super.m_6877_(p_39790_);
        this.f_39770_.m_39292_((p_39796_, p_39797_) -> this.m_150411_(p_39790_, this.f_39769_));
    }

    @Override
    public boolean m_6875_(Player p_39780_) {
        return this.f_39770_.m_39299_((p_39785_, p_39786_) -> {
            if (!this.m_8039_(p_39785_.m_8055_((BlockPos)p_39786_))) {
                return false;
            }
            return p_39780_.m_20275_((double)p_39786_.m_123341_() + 0.5, (double)p_39786_.m_123342_() + 0.5, (double)p_39786_.m_123343_() + 0.5) <= 64.0;
        }, true);
    }

    protected boolean m_5861_(ItemStack p_39787_) {
        return false;
    }

    @Override
    public ItemStack m_7648_(Player p_39792_, int p_39793_) {
        ItemStack $$2 = ItemStack.f_41583_;
        Slot $$3 = (Slot)this.f_38839_.get(p_39793_);
        if ($$3 != null && $$3.m_6657_()) {
            ItemStack $$4 = $$3.m_7993_();
            $$2 = $$4.m_41777_();
            if (p_39793_ == 2) {
                if (!this.m_38903_($$4, 3, 39, true)) {
                    return ItemStack.f_41583_;
                }
                $$3.m_40234_($$4, $$2);
            } else if (p_39793_ == 0 || p_39793_ == 1) {
                if (!this.m_38903_($$4, 3, 39, false)) {
                    return ItemStack.f_41583_;
                }
            } else if (p_39793_ >= 3 && p_39793_ < 39) {
                int $$5;
                int n = $$5 = this.m_5861_($$2) ? 1 : 0;
                if (!this.m_38903_($$4, $$5, 2, false)) {
                    return ItemStack.f_41583_;
                }
            }
            if ($$4.m_41619_()) {
                $$3.m_5852_(ItemStack.f_41583_);
            } else {
                $$3.m_6654_();
            }
            if ($$4.m_41613_() == $$2.m_41613_()) {
                return ItemStack.f_41583_;
            }
            $$3.m_142406_(p_39792_, $$4);
        }
        return $$2;
    }
}

