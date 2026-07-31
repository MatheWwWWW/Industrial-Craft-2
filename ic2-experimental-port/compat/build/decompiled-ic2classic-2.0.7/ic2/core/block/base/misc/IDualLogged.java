/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.SimpleWaterloggedBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.block.base.misc;

import ic2.core.block.base.misc.ILavaLoggable;
import ic2.core.platform.registries.IC2Properties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public interface IDualLogged
extends ILavaLoggable,
SimpleWaterloggedBlock {
    @Override
    default public boolean m_6044_(BlockGetter world, BlockPos pos, BlockState state, Fluid fluid) {
        if (fluid == Fluids.f_76195_ && !this.canBeLavaLogged(state)) {
            return false;
        }
        return (Boolean)state.m_61143_((Property)IC2Properties.LAVA_LOGGED) == false && (Boolean)state.m_61143_((Property)BlockStateProperties.f_61362_) == false && (fluid == Fluids.f_76195_ || fluid == Fluids.f_76193_);
    }

    default public boolean canBeLavaLogged(BlockState state) {
        return true;
    }

    @Override
    default public boolean m_7361_(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluid) {
        if (this.m_6044_((BlockGetter)world, pos, state, fluid.m_76152_())) {
            if (!world.m_5776_()) {
                world.m_7731_(pos, (BlockState)state.m_61124_((Property)(fluid.m_76152_() == Fluids.f_76195_ ? IC2Properties.LAVA_LOGGED : BlockStateProperties.f_61362_), (Comparable)Boolean.valueOf(true)), 3);
                world.m_186469_(pos, fluid.m_76152_(), fluid.m_76152_().m_6718_((LevelReader)world));
            }
            return true;
        }
        return false;
    }

    @Override
    default public Fluid takeLiquid(LevelAccessor world, BlockPos pos, BlockState state) {
        if (this.canBeLavaLogged(state) && ((Boolean)state.m_61143_((Property)IC2Properties.LAVA_LOGGED)).booleanValue()) {
            world.m_7731_(pos, (BlockState)state.m_61124_((Property)IC2Properties.LAVA_LOGGED, (Comparable)Boolean.valueOf(false)), 3);
            return Fluids.f_76195_;
        }
        if (((Boolean)state.m_61143_((Property)BlockStateProperties.f_61362_)).booleanValue()) {
            world.m_7731_(pos, (BlockState)state.m_61124_((Property)BlockStateProperties.f_61362_, (Comparable)Boolean.valueOf(false)), 3);
            return Fluids.f_76193_;
        }
        return Fluids.f_76191_;
    }

    default public ItemStack m_142598_(LevelAccessor level, BlockPos pos, BlockState state) {
        if (((Boolean)state.m_61143_((Property)BlockStateProperties.f_61362_)).booleanValue()) {
            level.m_7731_(pos, (BlockState)state.m_61124_((Property)BlockStateProperties.f_61362_, (Comparable)Boolean.valueOf(false)), 3);
            if (!state.m_60710_((LevelReader)level, pos)) {
                level.m_46961_(pos, true);
            }
            return new ItemStack((ItemLike)Items.f_42447_);
        }
        if (this.canBeLavaLogged(state) && ((Boolean)state.m_61143_((Property)IC2Properties.LAVA_LOGGED)).booleanValue()) {
            level.m_7731_(pos, (BlockState)state.m_61124_((Property)IC2Properties.LAVA_LOGGED, (Comparable)Boolean.valueOf(false)), 3);
            if (!state.m_60710_((LevelReader)level, pos)) {
                level.m_46961_(pos, true);
            }
            return new ItemStack((ItemLike)Items.f_42448_);
        }
        return ItemStack.f_41583_;
    }

    public static FluidState getFluidState(BlockState state) {
        return (Boolean)state.m_61143_((Property)BlockStateProperties.f_61362_) != false ? Fluids.f_76193_.m_76145_() : ((Boolean)state.m_61143_((Property)IC2Properties.LAVA_LOGGED) != false ? Fluids.f_76195_.m_76145_() : Fluids.f_76191_.m_76145_());
    }
}

