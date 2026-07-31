/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.extensions.common.IClientBlockExtensions
 */
package ic2.core.block.misc.textured;

import ic2.api.blocks.PainterHelper;
import ic2.core.IC2;
import ic2.core.block.base.IC2ContainerBlock;
import ic2.core.block.base.ICamouflageBlock;
import ic2.core.block.misc.tiles.TexturedBlockTileEntity;
import ic2.core.block.rendering.block.CamouflageModel;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.helpers.Tool;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;

public class TexturedBlockBlock
extends IC2ContainerBlock
implements ICustomBlockModel,
ICamouflageBlock,
PainterHelper.IPaintable {
    public static final BooleanProperty LIGHT = IC2Properties.LIGHT;
    public static final BooleanProperty REDSTONE = IC2Properties.REDSTONE;

    public TexturedBlockBlock() {
        super("textured_block", BlockBehaviour.Properties.m_60939_((Material)Material.f_76278_).m_60955_().m_60924_((T, V, X) -> false).m_60953_(T -> (Boolean)T.m_61143_((Property)LIGHT) != false ? 15 : 0).m_60913_(3.0f, 30.0f).m_60918_(SoundType.f_56742_));
        this.setHarvestTool(Tool.PICKAXE);
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)LIGHT, (Comparable)Boolean.valueOf(false))).m_61124_((Property)REDSTONE, (Comparable)Boolean.valueOf(false)));
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{LIGHT, REDSTONE});
    }

    public void initializeClient(Consumer<IClientBlockExtensions> consumer) {
        consumer.accept(new ICamouflageBlock.CamuflageWrapper(this));
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this).disableCreative();
    }

    @Override
    public ItemStack createDrop(BlockState state, ItemStack tool, RandomSource rand, BlockEntity tile, boolean wrench) {
        return ItemStack.f_41583_;
    }

    @Override
    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return IC2Tiles.TEXTURED_BLOCK.m_155264_(pos, state);
    }

    @Override
    public boolean recolor(BlockState state, Level world, BlockPos pos, Vec3 exactClick, Direction dir, DyeColor color) {
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

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return CamouflageModel.getInstance();
    }

    public boolean m_7420_(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack stack = player.m_21120_(handIn);
        if (IC2.KEYBOARD.isAltKeyDown(player)) {
            if (stack.m_41720_() == Items.f_42451_) {
                worldIn.m_46597_(pos, (BlockState)state.m_61124_((Property)REDSTONE, (Comparable)Boolean.valueOf((Boolean)state.m_61143_((Property)REDSTONE) == false)));
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                if (IC2.PLATFORM.isSimulating()) {
                    player.m_5661_((Component)this.translate((Boolean)state.m_61143_((Property)REDSTONE) == false ? "tooltip.block.ic2.cfoam.redstone.enable" : "tooltip.block.ic2.cfoam.redstone.disable"), true);
                }
                return InteractionResult.SUCCESS;
            }
            if (stack.m_41720_() == Items.f_42525_) {
                worldIn.m_46597_(pos, (BlockState)state.m_61124_((Property)LIGHT, (Comparable)Boolean.valueOf((Boolean)state.m_61143_((Property)LIGHT) == false)));
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                if (IC2.PLATFORM.isSimulating()) {
                    player.m_5661_((Component)this.translate((Boolean)state.m_61143_((Property)LIGHT) == false ? "tooltip.block.ic2.cfoam.light.enable" : "tooltip.block.ic2.cfoam.light.disable"), true);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.m_6227_(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public int m_6376_(BlockState state, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return (Boolean)state.m_61143_((Property)REDSTONE) != false ? 15 : 0;
    }

    @Override
    public int m_6378_(BlockState state, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return (Boolean)state.m_61143_((Property)REDSTONE) != false ? 15 : 0;
    }

    @Override
    public boolean shouldCheckWeakPower(BlockState state, LevelReader world, BlockPos pos, Direction side) {
        return (Boolean)state.m_61143_((Property)REDSTONE);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        return (Boolean)state.m_61143_((Property)REDSTONE);
    }
}

