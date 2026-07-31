/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BarrelBlockEntity
extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> f_58591_ = NonNullList.m_122780_(27, ItemStack.f_41583_);
    private ContainerOpenersCounter f_155050_ = new ContainerOpenersCounter(){

        @Override
        protected void m_142292_(Level p_155062_, BlockPos p_155063_, BlockState p_155064_) {
            BarrelBlockEntity.this.m_58600_(p_155064_, SoundEvents.f_11725_);
            BarrelBlockEntity.this.m_58606_(p_155064_, true);
        }

        @Override
        protected void m_142289_(Level p_155072_, BlockPos p_155073_, BlockState p_155074_) {
            BarrelBlockEntity.this.m_58600_(p_155074_, SoundEvents.f_11724_);
            BarrelBlockEntity.this.m_58606_(p_155074_, false);
        }

        @Override
        protected void m_142148_(Level p_155066_, BlockPos p_155067_, BlockState p_155068_, int p_155069_, int p_155070_) {
        }

        @Override
        protected boolean m_142718_(Player p_155060_) {
            if (p_155060_.f_36096_ instanceof ChestMenu) {
                Container $$1 = ((ChestMenu)p_155060_.f_36096_).m_39261_();
                return $$1 == BarrelBlockEntity.this;
            }
            return false;
        }
    };

    public BarrelBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(BlockEntityType.f_58942_, p_155052_, p_155053_);
    }

    @Override
    protected void m_183515_(CompoundTag p_187459_) {
        super.m_183515_(p_187459_);
        if (!this.m_59634_(p_187459_)) {
            ContainerHelper.m_18973_(p_187459_, this.f_58591_);
        }
    }

    @Override
    public void m_142466_(CompoundTag p_155055_) {
        super.m_142466_(p_155055_);
        this.f_58591_ = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
        if (!this.m_59631_(p_155055_)) {
            ContainerHelper.m_18980_(p_155055_, this.f_58591_);
        }
    }

    @Override
    public int m_6643_() {
        return 27;
    }

    @Override
    protected NonNullList<ItemStack> m_7086_() {
        return this.f_58591_;
    }

    @Override
    protected void m_6520_(NonNullList<ItemStack> p_58610_) {
        this.f_58591_ = p_58610_;
    }

    @Override
    protected Component m_6820_() {
        return Component.m_237115_("container.barrel");
    }

    @Override
    protected AbstractContainerMenu m_6555_(int p_58598_, Inventory p_58599_) {
        return ChestMenu.m_39237_(p_58598_, p_58599_, this);
    }

    @Override
    public void m_5856_(Player p_58616_) {
        if (!this.f_58859_ && !p_58616_.m_5833_()) {
            this.f_155050_.m_155452_(p_58616_, this.m_58904_(), this.m_58899_(), this.m_58900_());
        }
    }

    @Override
    public void m_5785_(Player p_58614_) {
        if (!this.f_58859_ && !p_58614_.m_5833_()) {
            this.f_155050_.m_155468_(p_58614_, this.m_58904_(), this.m_58899_(), this.m_58900_());
        }
    }

    public void m_58619_() {
        if (!this.f_58859_) {
            this.f_155050_.m_155476_(this.m_58904_(), this.m_58899_(), this.m_58900_());
        }
    }

    void m_58606_(BlockState p_58607_, boolean p_58608_) {
        this.f_58857_.m_7731_(this.m_58899_(), (BlockState)p_58607_.m_61124_(BarrelBlock.f_49043_, p_58608_), 3);
    }

    void m_58600_(BlockState p_58601_, SoundEvent p_58602_) {
        Vec3i $$2 = p_58601_.m_61143_(BarrelBlock.f_49042_).m_122436_();
        double $$3 = (double)this.f_58858_.m_123341_() + 0.5 + (double)$$2.m_123341_() / 2.0;
        double $$4 = (double)this.f_58858_.m_123342_() + 0.5 + (double)$$2.m_123342_() / 2.0;
        double $$5 = (double)this.f_58858_.m_123343_() + 0.5 + (double)$$2.m_123343_() / 2.0;
        this.f_58857_.m_6263_(null, $$3, $$4, $$5, p_58602_, SoundSource.BLOCKS, 0.5f, this.f_58857_.f_46441_.m_188501_() * 0.1f + 0.9f);
    }
}

