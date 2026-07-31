/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DispenserBlockEntity
extends RandomizableContainerBlockEntity {
    public static final int f_155487_ = 9;
    private NonNullList<ItemStack> f_59228_ = NonNullList.m_122780_(9, ItemStack.f_41583_);

    protected DispenserBlockEntity(BlockEntityType<?> p_155489_, BlockPos p_155490_, BlockState p_155491_) {
        super(p_155489_, p_155490_, p_155491_);
    }

    public DispenserBlockEntity(BlockPos p_155493_, BlockState p_155494_) {
        this(BlockEntityType.f_58922_, p_155493_, p_155494_);
    }

    @Override
    public int m_6643_() {
        return 9;
    }

    public int m_222761_(RandomSource p_222762_) {
        this.m_59640_(null);
        int $$1 = -1;
        int $$2 = 1;
        for (int $$3 = 0; $$3 < this.f_59228_.size(); ++$$3) {
            if (this.f_59228_.get($$3).m_41619_() || p_222762_.m_188503_($$2++) != 0) continue;
            $$1 = $$3;
        }
        return $$1;
    }

    public int m_59237_(ItemStack p_59238_) {
        for (int $$1 = 0; $$1 < this.f_59228_.size(); ++$$1) {
            if (!this.f_59228_.get($$1).m_41619_()) continue;
            this.m_6836_($$1, p_59238_);
            return $$1;
        }
        return -1;
    }

    @Override
    protected Component m_6820_() {
        return Component.m_237115_("container.dispenser");
    }

    @Override
    public void m_142466_(CompoundTag p_155496_) {
        super.m_142466_(p_155496_);
        this.f_59228_ = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
        if (!this.m_59631_(p_155496_)) {
            ContainerHelper.m_18980_(p_155496_, this.f_59228_);
        }
    }

    @Override
    protected void m_183515_(CompoundTag p_187498_) {
        super.m_183515_(p_187498_);
        if (!this.m_59634_(p_187498_)) {
            ContainerHelper.m_18973_(p_187498_, this.f_59228_);
        }
    }

    @Override
    protected NonNullList<ItemStack> m_7086_() {
        return this.f_59228_;
    }

    @Override
    protected void m_6520_(NonNullList<ItemStack> p_59243_) {
        this.f_59228_ = p_59243_;
    }

    @Override
    protected AbstractContainerMenu m_6555_(int p_59235_, Inventory p_59236_) {
        return new DispenserMenu(p_59235_, p_59236_, this);
    }
}

