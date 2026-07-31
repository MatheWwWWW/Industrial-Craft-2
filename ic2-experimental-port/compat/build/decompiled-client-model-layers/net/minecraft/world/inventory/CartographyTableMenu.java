/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class CartographyTableMenu
extends AbstractContainerMenu {
    public static final int f_150501_ = 0;
    public static final int f_150502_ = 1;
    public static final int f_150503_ = 2;
    private static final int f_150504_ = 3;
    private static final int f_150505_ = 30;
    private static final int f_150506_ = 30;
    private static final int f_150507_ = 39;
    private final ContainerLevelAccess f_39136_;
    long f_39137_;
    public final Container f_39135_ = new SimpleContainer(2){

        @Override
        public void m_6596_() {
            CartographyTableMenu.this.m_6199_(this);
            super.m_6596_();
        }
    };
    private final ResultContainer f_39138_ = new ResultContainer(){

        @Override
        public void m_6596_() {
            CartographyTableMenu.this.m_6199_(this);
            super.m_6596_();
        }
    };

    public CartographyTableMenu(int p_39140_, Inventory p_39141_) {
        this(p_39140_, p_39141_, ContainerLevelAccess.f_39287_);
    }

    public CartographyTableMenu(int p_39143_, Inventory p_39144_, final ContainerLevelAccess p_39145_) {
        super(MenuType.f_39979_, p_39143_);
        this.f_39136_ = p_39145_;
        this.m_38897_(new Slot(this.f_39135_, 0, 15, 15){

            @Override
            public boolean m_5857_(ItemStack p_39194_) {
                return p_39194_.m_150930_(Items.f_42573_);
            }
        });
        this.m_38897_(new Slot(this.f_39135_, 1, 15, 52){

            @Override
            public boolean m_5857_(ItemStack p_39203_) {
                return p_39203_.m_150930_(Items.f_42516_) || p_39203_.m_150930_(Items.f_42676_) || p_39203_.m_150930_(Items.f_42027_);
            }
        });
        this.m_38897_(new Slot(this.f_39138_, 2, 145, 39){

            @Override
            public boolean m_5857_(ItemStack p_39217_) {
                return false;
            }

            @Override
            public void m_142406_(Player p_150509_, ItemStack p_150510_) {
                ((Slot)CartographyTableMenu.this.f_38839_.get(0)).m_6201_(1);
                ((Slot)CartographyTableMenu.this.f_38839_.get(1)).m_6201_(1);
                p_150510_.m_41720_().m_7836_(p_150510_, p_150509_.f_19853_, p_150509_);
                p_39145_.m_39292_((p_39219_, p_39220_) -> {
                    long $$2 = p_39219_.m_46467_();
                    if (CartographyTableMenu.this.f_39137_ != $$2) {
                        p_39219_.m_5594_(null, (BlockPos)p_39220_, SoundEvents.f_12493_, SoundSource.BLOCKS, 1.0f, 1.0f);
                        CartographyTableMenu.this.f_39137_ = $$2;
                    }
                });
                super.m_142406_(p_150509_, p_150510_);
            }
        });
        for (int $$3 = 0; $$3 < 3; ++$$3) {
            for (int $$4 = 0; $$4 < 9; ++$$4) {
                this.m_38897_(new Slot(p_39144_, $$4 + $$3 * 9 + 9, 8 + $$4 * 18, 84 + $$3 * 18));
            }
        }
        for (int $$5 = 0; $$5 < 9; ++$$5) {
            this.m_38897_(new Slot(p_39144_, $$5, 8 + $$5 * 18, 142));
        }
    }

    @Override
    public boolean m_6875_(Player p_39149_) {
        return CartographyTableMenu.m_38889_(this.f_39136_, p_39149_, Blocks.f_50621_);
    }

    @Override
    public void m_6199_(Container p_39147_) {
        ItemStack $$1 = this.f_39135_.m_8020_(0);
        ItemStack $$2 = this.f_39135_.m_8020_(1);
        ItemStack $$3 = this.f_39138_.m_8020_(2);
        if (!$$3.m_41619_() && ($$1.m_41619_() || $$2.m_41619_())) {
            this.f_39138_.m_8016_(2);
        } else if (!$$1.m_41619_() && !$$2.m_41619_()) {
            this.m_39162_($$1, $$2, $$3);
        }
    }

    private void m_39162_(ItemStack p_39163_, ItemStack p_39164_, ItemStack p_39165_) {
        this.f_39136_.m_39292_((p_39170_, p_39171_) -> {
            void $$9;
            MapItemSavedData $$5 = MapItem.m_42853_(p_39163_, p_39170_);
            if ($$5 == null) {
                return;
            }
            if (p_39164_.m_150930_(Items.f_42516_) && !$$5.f_77892_ && $$5.f_77890_ < 4) {
                ItemStack $$6 = p_39163_.m_41777_();
                $$6.m_41764_(1);
                $$6.m_41784_().m_128405_("map_scale_direction", 1);
                this.m_38946_();
            } else if (p_39164_.m_150930_(Items.f_42027_) && !$$5.f_77892_) {
                ItemStack $$7 = p_39163_.m_41777_();
                $$7.m_41764_(1);
                $$7.m_41784_().m_128379_("map_to_lock", true);
                this.m_38946_();
            } else if (p_39164_.m_150930_(Items.f_42676_)) {
                ItemStack $$8 = p_39163_.m_41777_();
                $$8.m_41764_(2);
                this.m_38946_();
            } else {
                this.f_39138_.m_8016_(2);
                this.m_38946_();
                return;
            }
            if (!ItemStack.m_41728_((ItemStack)$$9, p_39165_)) {
                this.f_39138_.m_6836_(2, (ItemStack)$$9);
                this.m_38946_();
            }
        });
    }

    @Override
    public boolean m_5882_(ItemStack p_39160_, Slot p_39161_) {
        return p_39161_.f_40218_ != this.f_39138_ && super.m_5882_(p_39160_, p_39161_);
    }

    @Override
    public ItemStack m_7648_(Player p_39175_, int p_39176_) {
        ItemStack $$2 = ItemStack.f_41583_;
        Slot $$3 = (Slot)this.f_38839_.get(p_39176_);
        if ($$3 != null && $$3.m_6657_()) {
            ItemStack $$4 = $$3.m_7993_();
            $$2 = $$4.m_41777_();
            if (p_39176_ == 2) {
                $$4.m_41720_().m_7836_($$4, p_39175_.f_19853_, p_39175_);
                if (!this.m_38903_($$4, 3, 39, true)) {
                    return ItemStack.f_41583_;
                }
                $$3.m_40234_($$4, $$2);
            } else if (p_39176_ == 1 || p_39176_ == 0 ? !this.m_38903_($$4, 3, 39, false) : ($$4.m_150930_(Items.f_42573_) ? !this.m_38903_($$4, 0, 1, false) : ($$4.m_150930_(Items.f_42516_) || $$4.m_150930_(Items.f_42676_) || $$4.m_150930_(Items.f_42027_) ? !this.m_38903_($$4, 1, 2, false) : (p_39176_ >= 3 && p_39176_ < 30 ? !this.m_38903_($$4, 30, 39, false) : p_39176_ >= 30 && p_39176_ < 39 && !this.m_38903_($$4, 3, 30, false))))) {
                return ItemStack.f_41583_;
            }
            if ($$4.m_41619_()) {
                $$3.m_5852_(ItemStack.f_41583_);
            }
            $$3.m_6654_();
            if ($$4.m_41613_() == $$2.m_41613_()) {
                return ItemStack.f_41583_;
            }
            $$3.m_142406_(p_39175_, $$4);
            this.m_38946_();
        }
        return $$2;
    }

    @Override
    public void m_6877_(Player p_39173_) {
        super.m_6877_(p_39173_);
        this.f_39138_.m_8016_(2);
        this.f_39136_.m_39292_((p_39152_, p_39153_) -> this.m_150411_(p_39173_, this.f_39135_));
    }
}

