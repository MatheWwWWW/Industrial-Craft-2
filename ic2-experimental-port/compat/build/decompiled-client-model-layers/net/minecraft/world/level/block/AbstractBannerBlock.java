/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractBannerBlock
extends BaseEntityBlock {
    private final DyeColor f_48657_;

    protected AbstractBannerBlock(DyeColor p_48659_, BlockBehaviour.Properties p_48660_) {
        super(p_48660_);
        this.f_48657_ = p_48659_;
    }

    @Override
    public boolean m_5568_() {
        return true;
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_151892_, BlockState p_151893_) {
        return new BannerBlockEntity(p_151892_, p_151893_, this.f_48657_);
    }

    @Override
    public void m_6402_(Level p_48668_, BlockPos p_48669_, BlockState p_48670_, @Nullable LivingEntity p_48671_, ItemStack p_48672_) {
        if (p_48668_.f_46443_) {
            p_48668_.m_141902_(p_48669_, BlockEntityType.f_58935_).ifPresent(p_187404_ -> p_187404_.m_187453_(p_48672_));
        } else if (p_48672_.m_41788_()) {
            p_48668_.m_141902_(p_48669_, BlockEntityType.f_58935_).ifPresent(p_187401_ -> p_187401_.m_58501_(p_48672_.m_41786_()));
        }
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_48664_, BlockPos p_48665_, BlockState p_48666_) {
        BlockEntity $$3 = p_48664_.m_7702_(p_48665_);
        if ($$3 instanceof BannerBlockEntity) {
            return ((BannerBlockEntity)$$3).m_155043_();
        }
        return super.m_7397_(p_48664_, p_48665_, p_48666_);
    }

    public DyeColor m_48674_() {
        return this.f_48657_;
    }
}

