/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public interface SimpleWaterloggedBlock
extends BucketPickup,
LiquidBlockContainer {
    @Override
    default public boolean m_6044_(BlockGetter p_56301_, BlockPos p_56302_, BlockState p_56303_, Fluid p_56304_) {
        return p_56303_.m_61143_(BlockStateProperties.f_61362_) == false && p_56304_ == Fluids.f_76193_;
    }

    @Override
    default public boolean m_7361_(LevelAccessor p_56306_, BlockPos p_56307_, BlockState p_56308_, FluidState p_56309_) {
        if (!p_56308_.m_61143_(BlockStateProperties.f_61362_).booleanValue() && p_56309_.m_76152_() == Fluids.f_76193_) {
            if (!p_56306_.m_5776_()) {
                p_56306_.m_7731_(p_56307_, (BlockState)p_56308_.m_61124_(BlockStateProperties.f_61362_, true), 3);
                p_56306_.m_186469_(p_56307_, p_56309_.m_76152_(), p_56309_.m_76152_().m_6718_(p_56306_));
            }
            return true;
        }
        return false;
    }

    @Override
    default public ItemStack m_142598_(LevelAccessor p_154560_, BlockPos p_154561_, BlockState p_154562_) {
        if (p_154562_.m_61143_(BlockStateProperties.f_61362_).booleanValue()) {
            p_154560_.m_7731_(p_154561_, (BlockState)p_154562_.m_61124_(BlockStateProperties.f_61362_, false), 3);
            if (!p_154562_.m_60710_(p_154560_, p_154561_)) {
                p_154560_.m_46961_(p_154561_, true);
            }
            return new ItemStack(Items.f_42447_);
        }
        return ItemStack.f_41583_;
    }

    @Override
    default public Optional<SoundEvent> m_142298_() {
        return Fluids.f_76193_.m_142520_();
    }
}

