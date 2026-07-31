/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.HitResult
 */
package ic2.core.block.multi;

import ic2.core.block.base.blocks.BaseActivityBlock;
import ic2.core.block.base.tiles.BaseLinkingTileEntity;
import ic2.core.block.machines.BaseMachineBlock;
import ic2.core.platform.rendering.features.ITextureProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public class BaseMultiBlock
extends BaseActivityBlock<BaseLinkingTileEntity> {
    public BaseMultiBlock(String name, ITextureProvider provider, BlockEntityType<? extends BlockEntity> tile) {
        this(name, provider, BaseMachineBlock.BASE_MACHINE, tile);
    }

    public BaseMultiBlock(String blockName, ITextureProvider provider, BlockBehaviour.Properties properties, BlockEntityType<? extends BlockEntity> tile) {
        super(blockName, properties, provider, tile);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        BlockEntity tile;
        if (!player.m_6144_() && (tile = world.m_7702_(pos)) instanceof BaseLinkingTileEntity && (tile = ((BaseLinkingTileEntity)tile).getMaster()) != null) {
            return tile.m_58900_().getCloneItemStack(target, world, tile.m_58899_(), player);
        }
        return super.getCloneItemStack(state, target, world, pos, player);
    }
}

