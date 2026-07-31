/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.extensions.common.IClientBlockExtensions
 */
package ic2.core.block.misc.textured;

import ic2.api.blocks.PainterHelper;
import ic2.core.block.base.ICamouflageBlock;
import ic2.core.block.base.features.redstone.IRedstoneProvider;
import ic2.core.block.misc.base.IC2SlabBlock;
import ic2.core.block.misc.tiles.TexturedBlockTileEntity;
import ic2.core.block.rendering.block.CamouflageModel;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;

public class TexturedSlabBlock
extends IC2SlabBlock.IC2LavaSlabBlock
implements ICustomBlockModel,
ICamouflageBlock,
PainterHelper.IPaintable,
EntityBlock {
    public TexturedSlabBlock() {
        super("textured_slab", Blocks.f_50069_, BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50069_).m_60955_().m_60924_((T, V, X) -> false), "", "");
    }

    @Override
    public List<BlockState> getModelStates() {
        return Collections.emptyList();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return CamouflageModel.getInstance();
    }

    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return IC2Tiles.TEXTURED_SLAB.m_155264_(pos, state);
    }

    public void initializeClient(Consumer<IClientBlockExtensions> consumer) {
        consumer.accept(new ICamouflageBlock.CamuflageWrapper(this));
    }

    @Override
    public boolean recolor(BlockState state, Level world, BlockPos pos, Vec3 exactPos, Direction dir, DyeColor color) {
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof TexturedBlockTileEntity) {
            return ((TexturedBlockTileEntity)tile).setColor(dir, color, false);
        }
        return false;
    }

    @Override
    public DyeColor getColor(BlockState state) {
        return null;
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this).disableCreative();
    }

    @Override
    public List<ItemStack> m_7381_(BlockState state, LootContext.Builder builder) {
        return Collections.emptyList();
    }

    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        BlockState resultState;
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof TexturedBlockTileEntity && target instanceof BlockHitResult && (resultState = ((TexturedBlockTileEntity)tile).getStorage().getCache(((BlockHitResult)target).m_82434_()).getDisplayBlock()) != null) {
            try {
                return resultState.getCloneItemStack(target, world, pos, player);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return super.getCloneItemStack(state, target, world, pos, player);
    }

    public boolean m_7420_(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    public int m_6376_(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        BlockEntity tile = blockAccess.m_7702_(pos);
        if (tile instanceof IRedstoneProvider) {
            return ((IRedstoneProvider)tile).getStrongSignalStrength(side);
        }
        return 0;
    }

    public int m_6378_(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        BlockEntity tile = blockAccess.m_7702_(pos);
        if (tile instanceof IRedstoneProvider) {
            return ((IRedstoneProvider)tile).getWeakSignalStrength(side);
        }
        return 0;
    }
}

