/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.block.crops.soils;

import ic2.api.crops.IFarmland;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;

public class Farmland
implements IFarmland {
    @Override
    public int getHumidity(BlockState state) {
        return (Integer)state.m_61143_((Property)BlockStateProperties.f_61423_) == 7 ? 4 : 0;
    }

    @Override
    public int getNutrients(BlockState state) {
        return 0;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public BlockState getSpecialState(Block block) {
        return (BlockState)block.m_49966_().m_61124_((Property)BlockStateProperties.f_61423_, (Comparable)Integer.valueOf(7));
    }
}

