/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.block.machines;

import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.base.tiles.BaseMultiBlockTileEntity;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.base.tiles.impls.machine.multi.BaseColossalMachineTileEntity;
import ic2.core.block.machines.BaseMachineBlock;
import ic2.core.block.multi.ColossalMultiBlock;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.platform.rendering.features.block.IBlockModifiers;
import ic2.core.utils.math.ConnectionState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class ColossalMachineBlock
extends BaseMachineBlock
implements IBlockModifiers {
    public static final BooleanProperty FORMED = IC2Properties.STRUCTURE_FORMED;
    public static final IntegerProperty SIZE = IC2Properties.DYNAMIC_STATE;

    public ColossalMachineBlock(String blockName, IBlockDropProvider drop, ITextureProvider provider, BlockEntityType<? extends BaseTileEntity> type) {
        super(blockName, drop, provider, type);
    }

    @Override
    protected void setDefaultState() {
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(false))).m_61124_((Property)FORMED, (Comparable)Boolean.valueOf(false))).m_61124_((Property)SIZE, (Comparable)Integer.valueOf(0)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{FORMED}).m_61104_(new Property[]{SIZE});
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, BaseTileEntity tile) {
        tile.setState((BlockState)((BlockState)((BlockState)((BlockState)state.m_61124_((Property)FACING, (Comparable)tile.getFacing())).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(tile.isActive()))).m_61124_((Property)FORMED, (Comparable)Boolean.valueOf(((BaseMultiBlockTileEntity)tile).isValid))).m_61124_((Property)SIZE, (Comparable)Integer.valueOf(((BaseColossalMachineTileEntity)tile).getStructureSize())));
    }

    @Override
    public boolean hasTextureRotation(BlockState state, Direction side) {
        return false;
    }

    @Override
    public int getTextureRotation(BlockState state, Direction side) {
        return 0;
    }

    @Override
    public boolean hasCustomTextureUVs(BlockState state, Direction side) {
        return (Boolean)state.m_61143_((Property)FORMED);
    }

    @Override
    public float[] getCustomTextureUVs(BlockState state, Direction side) {
        int size = (Integer)state.m_61143_((Property)SIZE);
        return ConnectionState.createQuads(ColossalMultiBlock.UVS[size], side, 1, size + 2, size + 2, size + 2);
    }
}

