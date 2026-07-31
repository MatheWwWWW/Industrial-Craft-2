/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.LockCode;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseContainerBlockEntity
extends BlockEntity
implements Container,
MenuProvider,
Nameable {
    private LockCode f_58621_ = LockCode.f_19102_;
    private Component f_58622_;

    protected BaseContainerBlockEntity(BlockEntityType<?> p_155076_, BlockPos p_155077_, BlockState p_155078_) {
        super(p_155076_, p_155077_, p_155078_);
    }

    @Override
    public void m_142466_(CompoundTag p_155080_) {
        super.m_142466_(p_155080_);
        this.f_58621_ = LockCode.m_19111_(p_155080_);
        if (p_155080_.m_128425_("CustomName", 8)) {
            this.f_58622_ = Component.Serializer.m_130701_(p_155080_.m_128461_("CustomName"));
        }
    }

    @Override
    protected void m_183515_(CompoundTag p_187461_) {
        super.m_183515_(p_187461_);
        this.f_58621_.m_19109_(p_187461_);
        if (this.f_58622_ != null) {
            p_187461_.m_128359_("CustomName", Component.Serializer.m_130703_(this.f_58622_));
        }
    }

    public void m_58638_(Component p_58639_) {
        this.f_58622_ = p_58639_;
    }

    @Override
    public Component m_7755_() {
        if (this.f_58622_ != null) {
            return this.f_58622_;
        }
        return this.m_6820_();
    }

    @Override
    public Component m_5446_() {
        return this.m_7755_();
    }

    @Override
    @Nullable
    public Component m_7770_() {
        return this.f_58622_;
    }

    protected abstract Component m_6820_();

    public boolean m_7525_(Player p_58645_) {
        return BaseContainerBlockEntity.m_58629_(p_58645_, this.f_58621_, this.m_5446_());
    }

    public static boolean m_58629_(Player p_58630_, LockCode p_58631_, Component p_58632_) {
        if (p_58630_.m_5833_() || p_58631_.m_19107_(p_58630_.m_21205_())) {
            return true;
        }
        p_58630_.m_5661_(Component.m_237110_("container.isLocked", p_58632_), true);
        p_58630_.m_6330_(SoundEvents.f_11748_, SoundSource.BLOCKS, 1.0f, 1.0f);
        return false;
    }

    @Override
    @Nullable
    public AbstractContainerMenu m_7208_(int p_58641_, Inventory p_58642_, Player p_58643_) {
        if (this.m_7525_(p_58643_)) {
            return this.m_6555_(p_58641_, p_58642_);
        }
        return null;
    }

    protected abstract AbstractContainerMenu m_6555_(int var1, Inventory var2);
}

