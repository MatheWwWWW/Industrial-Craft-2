/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.BucketPickup
 *  net.minecraft.world.level.block.LiquidBlockContainer
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.block.base.misc;

import ic2.core.platform.registries.IC2Properties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public interface ILavaLoggable
extends BucketPickup,
LiquidBlockContainer {
    default public boolean m_6044_(BlockGetter world, BlockPos pos, BlockState state, Fluid fluid) {
        return (Boolean)state.m_61143_((Property)IC2Properties.LAVA_LOGGED) == false && fluid == Fluids.f_76195_;
    }

    default public boolean m_7361_(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluid) {
        if (!((Boolean)state.m_61143_((Property)IC2Properties.LAVA_LOGGED)).booleanValue() && fluid.m_76152_() == Fluids.f_76195_) {
            if (!world.m_5776_()) {
                world.m_7731_(pos, (BlockState)state.m_61124_((Property)IC2Properties.LAVA_LOGGED, (Comparable)Boolean.TRUE), 3);
                world.m_186469_(pos, fluid.m_76152_(), fluid.m_76152_().m_6718_((LevelReader)world));
            }
            return true;
        }
        return false;
    }

    default public Fluid takeLiquid(LevelAccessor world, BlockPos pos, BlockState state) {
        if (((Boolean)state.m_61143_((Property)IC2Properties.LAVA_LOGGED)).booleanValue()) {
            world.m_7731_(pos, (BlockState)state.m_61124_((Property)IC2Properties.LAVA_LOGGED, (Comparable)Boolean.FALSE), 3);
            return Fluids.f_76195_;
        }
        return Fluids.f_76191_;
    }
}

