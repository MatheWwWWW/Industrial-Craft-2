/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import java.util.List;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnchantmentTableBlock;

public class EnchantmentMenu
extends AbstractContainerMenu {
    private final Container f_39449_ = new SimpleContainer(2){

        @Override
        public void m_6596_() {
            super.m_6596_();
            EnchantmentMenu.this.m_6199_(this);
        }
    };
    private final ContainerLevelAccess f_39450_;
    private final RandomSource f_39451_ = RandomSource.m_216327_();
    private final DataSlot f_39452_ = DataSlot.m_39401_();
    public final int[] f_39446_ = new int[3];
    public final int[] f_39447_ = new int[]{-1, -1, -1};
    public final int[] f_39448_ = new int[]{-1, -1, -1};

    public EnchantmentMenu(int p_39454_, Inventory p_39455_) {
        this(p_39454_, p_39455_, ContainerLevelAccess.f_39287_);
    }

    public EnchantmentMenu(int p_39457_, Inventory p_39458_, ContainerLevelAccess p_39459_) {
        super(MenuType.f_39969_, p_39457_);
        this.f_39450_ = p_39459_;
        this.m_38897_(new Slot(this.f_39449_, 0, 15, 47){

            @Override
            public boolean m_5857_(ItemStack p_39508_) {
                return true;
            }

            @Override
            public int m_6641_() {
                return 1;
            }
        });
        this.m_38897_(new Slot(this.f_39449_, 1, 35, 47){

            @Override
            public boolean m_5857_(ItemStack p_39517_) {
                return p_39517_.m_150930_(Items.f_42534_);
            }
        });
        for (int $$3 = 0; $$3 < 3; ++$$3) {
            for (int $$4 = 0; $$4 < 9; ++$$4) {
                this.m_38897_(new Slot(p_39458_, $$4 + $$3 * 9 + 9, 8 + $$4 * 18, 84 + $$3 * 18));
            }
        }
        for (int $$5 = 0; $$5 < 9; ++$$5) {
            this.m_38897_(new Slot(p_39458_, $$5, 8 + $$5 * 18, 142));
        }
        this.m_38895_(DataSlot.m_39406_(this.f_39446_, 0));
        this.m_38895_(DataSlot.m_39406_(this.f_39446_, 1));
        this.m_38895_(DataSlot.m_39406_(this.f_39446_, 2));
        this.m_38895_(this.f_39452_).m_6422_(p_39458_.f_35978_.m_36322_());
        this.m_38895_(DataSlot.m_39406_(this.f_39447_, 0));
        this.m_38895_(DataSlot.m_39406_(this.f_39447_, 1));
        this.m_38895_(DataSlot.m_39406_(this.f_39447_, 2));
        this.m_38895_(DataSlot.m_39406_(this.f_39448_, 0));
        this.m_38895_(DataSlot.m_39406_(this.f_39448_, 1));
        this.m_38895_(DataSlot.m_39406_(this.f_39448_, 2));
    }

    @Override
    public void m_6199_(Container p_39461_) {
        if (p_39461_ == this.f_39449_) {
            ItemStack $$1 = p_39461_.m_8020_(0);
            if ($$1.m_41619_() || !$$1.m_41792_()) {
                for (int $$2 = 0; $$2 < 3; ++$$2) {
                    this.f_39446_[$$2] = 0;
                    this.f_39447_[$$2] = -1;
                    this.f_39448_[$$2] = -1;
                }
            } else {
                this.f_39450_.m_39292_((p_39485_, p_39486_) -> {
                    int $$3 = 0;
                    for (BlockPos $$4 : EnchantmentTableBlock.f_207902_) {
                        if (!EnchantmentTableBlock.m_207909_(p_39485_, p_39486_, $$4)) continue;
                        ++$$3;
                    }
                    this.f_39451_.m_188584_(this.f_39452_.m_6501_());
                    for (int $$5 = 0; $$5 < 3; ++$$5) {
                        this.f_39446_[$$5] = EnchantmentHelper.m_220287_(this.f_39451_, $$5, $$3, $$1);
                        this.f_39447_[$$5] = -1;
                        this.f_39448_[$$5] = -1;
                        if (this.f_39446_[$$5] >= $$5 + 1) continue;
                        this.f_39446_[$$5] = 0;
                    }
                    for (int $$6 = 0; $$6 < 3; ++$$6) {
                        List<EnchantmentInstance> $$7;
                        if (this.f_39446_[$$6] <= 0 || ($$7 = this.m_39471_($$1, $$6, this.f_39446_[$$6])) == null || $$7.isEmpty()) continue;
                        EnchantmentInstance $$8 = $$7.get(this.f_39451_.m_188503_($$7.size()));
                        this.f_39447_[$$6] = Registry.f_122825_.m_7447_($$8.f_44947_);
                        this.f_39448_[$$6] = $$8.f_44948_;
                    }
                    this.m_38946_();
                });
            }
        }
    }

    @Override
    public boolean m_6366_(Player p_39465_, int p_39466_) {
        if (p_39466_ < 0 || p_39466_ >= this.f_39446_.length) {
            Util.m_143785_(p_39465_.m_7755_() + " pressed invalid button id: " + p_39466_);
            return false;
        }
        ItemStack $$2 = this.f_39449_.m_8020_(0);
        ItemStack $$3 = this.f_39449_.m_8020_(1);
        int $$4 = p_39466_ + 1;
        if (($$3.m_41619_() || $$3.m_41613_() < $$4) && !p_39465_.m_150110_().f_35937_) {
            return false;
        }
        if (this.f_39446_[p_39466_] > 0 && !$$2.m_41619_() && (p_39465_.f_36078_ >= $$4 && p_39465_.f_36078_ >= this.f_39446_[p_39466_] || p_39465_.m_150110_().f_35937_)) {
            this.f_39450_.m_39292_((p_39481_, p_39482_) -> {
                ItemStack $$7 = $$2;
                List<EnchantmentInstance> $$8 = this.m_39471_($$7, p_39466_, this.f_39446_[p_39466_]);
                if (!$$8.isEmpty()) {
                    p_39465_.m_7408_($$7, $$4);
                    boolean $$9 = $$7.m_150930_(Items.f_42517_);
                    if ($$9) {
                        $$7 = new ItemStack(Items.f_42690_);
                        CompoundTag $$10 = $$2.m_41783_();
                        if ($$10 != null) {
                            $$7.m_41751_($$10.m_6426_());
                        }
                        this.f_39449_.m_6836_(0, $$7);
                    }
                    for (int $$11 = 0; $$11 < $$8.size(); ++$$11) {
                        EnchantmentInstance $$12 = $$8.get($$11);
                        if ($$9) {
                            EnchantedBookItem.m_41153_($$7, $$12);
                            continue;
                        }
                        $$7.m_41663_($$12.f_44947_, $$12.f_44948_);
                    }
                    if (!p_39478_.m_150110_().f_35937_) {
                        $$3.m_41774_($$4);
                        if ($$3.m_41619_()) {
                            this.f_39449_.m_6836_(1, ItemStack.f_41583_);
                        }
                    }
                    p_39465_.m_36220_(Stats.f_12964_);
                    if (p_39465_ instanceof ServerPlayer) {
                        CriteriaTriggers.f_10575_.m_27668_((ServerPlayer)p_39465_, $$7, $$4);
                    }
                    this.f_39449_.m_6596_();
                    this.f_39452_.m_6422_(p_39465_.m_36322_());
                    this.m_6199_(this.f_39449_);
                    p_39481_.m_5594_(null, (BlockPos)p_39482_, SoundEvents.f_11887_, SoundSource.BLOCKS, 1.0f, p_39481_.f_46441_.m_188501_() * 0.1f + 0.9f);
                }
            });
            return true;
        }
        return false;
    }

    private List<EnchantmentInstance> m_39471_(ItemStack p_39472_, int p_39473_, int p_39474_) {
        this.f_39451_.m_188584_(this.f_39452_.m_6501_() + p_39473_);
        List<EnchantmentInstance> $$3 = EnchantmentHelper.m_220297_(this.f_39451_, p_39472_, p_39474_, false);
        if (p_39472_.m_150930_(Items.f_42517_) && $$3.size() > 1) {
            $$3.remove(this.f_39451_.m_188503_($$3.size()));
        }
        return $$3;
    }

    public int m_39492_() {
        ItemStack $$0 = this.f_39449_.m_8020_(1);
        if ($$0.m_41619_()) {
            return 0;
        }
        return $$0.m_41613_();
    }

    public int m_39493_() {
        return this.f_39452_.m_6501_();
    }

    @Override
    public void m_6877_(Player p_39488_) {
        super.m_6877_(p_39488_);
        this.f_39450_.m_39292_((p_39469_, p_39470_) -> this.m_150411_(p_39488_, this.f_39449_));
    }

    @Override
    public boolean m_6875_(Player p_39463_) {
        return EnchantmentMenu.m_38889_(this.f_39450_, p_39463_, Blocks.f_50201_);
    }

    @Override
    public ItemStack m_7648_(Player p_39490_, int p_39491_) {
        ItemStack $$2 = ItemStack.f_41583_;
        Slot $$3 = (Slot)this.f_38839_.get(p_39491_);
        if ($$3 != null && $$3.m_6657_()) {
            ItemStack $$4 = $$3.m_7993_();
            $$2 = $$4.m_41777_();
            if (p_39491_ == 0) {
                if (!this.m_38903_($$4, 2, 38, true)) {
                    return ItemStack.f_41583_;
                }
            } else if (p_39491_ == 1) {
                if (!this.m_38903_($$4, 2, 38, true)) {
                    return ItemStack.f_41583_;
                }
            } else if ($$4.m_150930_(Items.f_42534_)) {
                if (!this.m_38903_($$4, 1, 2, true)) {
                    return ItemStack.f_41583_;
                }
            } else if (!((Slot)this.f_38839_.get(0)).m_6657_() && ((Slot)this.f_38839_.get(0)).m_5857_($$4)) {
                ItemStack $$5 = $$4.m_41777_();
                $$5.m_41764_(1);
                $$4.m_41774_(1);
                ((Slot)this.f_38839_.get(0)).m_5852_($$5);
            } else {
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
            $$3.m_142406_(p_39490_, $$4);
        }
        return $$2;
    }
}

