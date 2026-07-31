/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.block.base.blocks;

import ic2.core.block.base.blocks.BaseFacingBlock;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.features.ITextureProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class BaseActivityBlock<T extends BaseTileEntity>
extends BaseFacingBlock<T> {
    public static final BooleanProperty ACTIVE = IC2Properties.ACTIVE;

    public BaseActivityBlock(String blockName, BlockBehaviour.Properties properties, ITextureProvider provider, BlockEntityType<? extends BlockEntity> tile) {
        super(blockName, properties, provider, tile);
    }

    @Override
    protected void setDefaultState() {
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(false)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{ACTIVE});
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, T tile) {
        ((BaseTileEntity)tile).setState((BlockState)((BlockState)state.m_61124_((Property)FACING, (Comparable)((BaseTileEntity)tile).getFacing())).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(((BaseTileEntity)tile).isActive())));
    }
}

