/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Material
 */
package ic2.core.block.storage;

import ic2.core.block.base.blocks.BaseFacingBlock;
import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.base.tiles.impls.BaseEnergyStorageTileEntity;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.utils.helpers.Tool;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;

public class EnergyStorageBlock
extends BaseFacingBlock<BaseEnergyStorageTileEntity> {
    public static final BlockBehaviour.Properties BASE_ENERGY_STORAGE = BlockBehaviour.Properties.m_60939_((Material)Material.f_76279_).m_60918_(SoundType.f_56743_).m_60913_(1.5f, 7.5f).m_60999_();
    public static final IntegerProperty STATE = IC2Properties.ACTIVE_0_3;

    public EnergyStorageBlock(String blockName, IBlockDropProvider drop, ITextureProvider provider, BlockEntityType<? extends BlockEntity> type) {
        super(blockName, BASE_ENERGY_STORAGE, provider, type);
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
        this.setDropProvider(drop);
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)Direction.NORTH)).m_61124_((Property)STATE, (Comparable)Integer.valueOf(0)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{STATE});
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, BaseEnergyStorageTileEntity tile) {
        tile.setState((BlockState)((BlockState)state.m_61124_((Property)FACING, (Comparable)tile.getFacing())).m_61124_((Property)STATE, (Comparable)Integer.valueOf(tile.state)));
    }

    @Override
    protected Direction getFacing(BlockPlaceContext context) {
        if (context.m_43723_() == null) {
            return Direction.NORTH;
        }
        int pitch = Math.round(context.m_43723_().m_146909_());
        return pitch >= 65 ? Direction.UP : (pitch <= -65 ? Direction.DOWN : context.m_8125_().m_122424_());
    }
}

