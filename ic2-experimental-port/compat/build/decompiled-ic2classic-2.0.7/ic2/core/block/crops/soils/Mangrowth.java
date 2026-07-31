/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.MangroveRootsBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.block.crops.soils;

import ic2.api.crops.ISubSoil;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MangroveRootsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluids;

public class Mangrowth
implements ISubSoil {
    @Override
    public int getHumidity(BlockState state) {
        return state.m_60819_().m_76152_() == Fluids.f_76193_ ? 3 : 0;
    }

    @Override
    public int getNutrients(BlockState state) {
        return state.m_60819_().m_76152_() == Fluids.f_76193_ ? -1 : 0;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public BlockState getSpecialState(Block block) {
        return (BlockState)block.m_49966_().m_61124_((Property)MangroveRootsBlock.f_221503_, (Comparable)Boolean.valueOf(true));
    }
}

