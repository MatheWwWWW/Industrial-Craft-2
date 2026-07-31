/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$AxisDirection
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.cables.mointor;

import ic2.api.util.DirectionList;
import ic2.core.block.base.blocks.BaseActivityBlock;
import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.base.misc.color.IBlockColorListener;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.cables.mointor.MonitorTileEntity;
import ic2.core.block.machines.BaseMachineBlock;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.platform.rendering.features.block.IColoredBlockModel;
import ic2.core.utils.math.ConnectionState;
import ic2.core.utils.tooltips.helper.ITooltipProvider;
import java.util.Map;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MonitorBlock
extends BaseActivityBlock<BaseTileEntity>
implements IColoredBlockModel,
IBlockColorListener {
    public static final BooleanProperty MASTER = BooleanProperty.m_61465_((String)"master");
    public static final BooleanProperty LOCKED = BooleanProperty.m_61465_((String)"locked");
    public static final IntegerProperty CONNECTION = IntegerProperty.m_61631_((String)"connections", (int)0, (int)15);

    public MonitorBlock() {
        super("monitor", BaseMachineBlock.BASE_MACHINE, ITextureProvider.triToggleIC2("electric/monitor"), IC2Tiles.MONITOR);
        this.setDropProvider(IBlockDropProvider.SELF_OR_MACHINE);
        this.addTooltip(ITooltipProvider.IV_MACHINE);
    }

    @Override
    protected void setDefaultState() {
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(false))).m_61124_((Property)MASTER, (Comparable)Boolean.valueOf(false))).m_61124_((Property)LOCKED, (Comparable)Boolean.valueOf(false))).m_61124_((Property)CONNECTION, (Comparable)Integer.valueOf(15)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{MASTER}).m_61104_(new Property[]{LOCKED}).m_61104_(new Property[]{CONNECTION});
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public int getItemColor(ItemStack stack, int tintIndex) {
        return DyeColor.LIME.m_41071_() | 0xFF000000;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public int getBlockColor(BlockState state, BlockAndTintGetter world, BlockPos pos, int tintIndex) {
        if (world == null || pos == null) {
            return DyeColor.LIME.m_41071_() | 0xFF000000;
        }
        BlockEntity tile = world.m_7702_(pos);
        return (tile instanceof MonitorTileEntity ? ((MonitorTileEntity)tile).getScreenColor() : DyeColor.LIME).m_41071_() | 0xFF000000;
    }

    @Override
    protected Direction getFacing(BlockPlaceContext context) {
        if (context.m_43723_() == null) {
            return Direction.NORTH;
        }
        return context.m_8125_().m_122424_();
    }

    @Override
    protected void setPlaceData(BaseTileEntity tile, BlockState state, LivingEntity placer, ItemStack stack) {
        DirectionList connections = MonitorBlock.getConnections(tile.m_58904_(), tile.m_58899_(), tile.getFacing(), -1);
        tile.setState((BlockState)((BlockState)state.m_61124_((Property)MASTER, (Comparable)Boolean.valueOf(MonitorBlock.isMaster(connections, tile.getFacing())))).m_61124_((Property)CONNECTION, (Comparable)Integer.valueOf(ConnectionState.fromList(connections, tile.getFacing()).getIndex())));
    }

    public static DirectionList getConnections(Level world, BlockPos pos, Direction axis, int id) {
        DirectionList connections = DirectionList.EMPTY;
        for (Direction dir : DirectionList.ofAxis(axis.m_122434_()).invert()) {
            BlockEntity tile;
            BlockPos other = pos.m_121945_(dir);
            BlockState state = world.m_8055_(other);
            if (!MonitorBlock.isBlock(state, axis) || !((tile = world.m_7702_(other)) instanceof MonitorTileEntity) || ((MonitorTileEntity)tile).gridId != id) continue;
            connections = connections.add(dir);
        }
        return connections;
    }

    public static boolean isMaster(DirectionList list, Direction dir) {
        boolean positive;
        boolean bl = positive = dir.m_122421_() == Direction.AxisDirection.POSITIVE;
        if (dir.m_122434_().m_122478_()) {
            return list.notContains(positive ? Direction.SOUTH : Direction.NORTH) && list.notContains(positive ? Direction.EAST : Direction.WEST);
        }
        return list.notContains(dir.m_122427_()) && list.notContains(Direction.UP);
    }

    public static boolean isMaster(Level world, BlockPos pos, Direction dir) {
        boolean positive;
        boolean bl = positive = dir.m_122421_() == Direction.AxisDirection.POSITIVE;
        if (dir.m_122434_().m_122478_()) {
            return !MonitorBlock.isBlock(world.m_8055_(pos.m_121945_(positive ? Direction.SOUTH : Direction.NORTH)), dir) && !MonitorBlock.isBlock(world.m_8055_(pos.m_121945_(positive ? Direction.EAST : Direction.WEST)), dir);
        }
        return !MonitorBlock.isBlock(world.m_8055_(pos.m_121945_(dir.m_122427_())), dir) && !MonitorBlock.isBlock(world.m_8055_(pos.m_121945_(Direction.UP)), dir);
    }

    public static Direction getSearchDir(Direction baseDir, boolean height) {
        boolean positive;
        boolean bl = positive = baseDir.m_122421_() == Direction.AxisDirection.POSITIVE;
        if (baseDir.m_122434_().m_122478_()) {
            return height ? (positive ? Direction.WEST : Direction.EAST) : (positive ? Direction.NORTH : Direction.SOUTH);
        }
        return height ? Direction.DOWN : baseDir.m_122428_();
    }

    public static boolean isBlock(BlockState state, Direction dir) {
        return state.m_60734_() instanceof MonitorBlock && state.m_61143_((Property)FACING) == dir;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        if (side != Direction.SOUTH) {
            return super.getSpriteForState(state, side);
        }
        Map<String, TextureAtlasSprite> texture = IC2Textures.getMappedEntriesBlockIC2("electric/monitor/" + ((Boolean)state.m_61143_((Property)ACTIVE) != false ? "active_front" : "inactive_front"));
        return texture.get(ConnectionState.fromNumber((Integer)state.m_61143_((Property)CONNECTION)).getTypeName());
    }

    @Override
    public int getTintedIndexFor(BlockState state, Direction dir, int layer) {
        return dir == Direction.SOUTH ? 0 : -1;
    }
}

