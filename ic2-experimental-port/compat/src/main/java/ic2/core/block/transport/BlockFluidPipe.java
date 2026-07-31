package ic2.core.block.transport;

import ic2.core.block.transport.items.PipeSize;
import ic2.core.block.transport.items.PipeType;
import ic2.core.item.block.ItemFluidPipe;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Modelled, non-opaque host block for all eight legacy fluid-pipe variants. */
public final class BlockFluidPipe extends BaseEntityBlock {
    public static final BooleanProperty DOWN = BooleanProperty.m_61465_("down");
    public static final BooleanProperty UP = BooleanProperty.m_61465_("up");
    public static final BooleanProperty NORTH = BooleanProperty.m_61465_("north");
    public static final BooleanProperty SOUTH = BooleanProperty.m_61465_("south");
    public static final BooleanProperty WEST = BooleanProperty.m_61465_("west");
    public static final BooleanProperty EAST = BooleanProperty.m_61465_("east");
    public static final EnumProperty<PipeType> TYPE = EnumProperty.m_61587_("type", PipeType.class);
    public static final EnumProperty<PipeSize> SIZE = EnumProperty.m_61587_("size", PipeSize.class);

    private static final Map<Direction, BooleanProperty> CONNECTION_PROPERTIES =
            new EnumMap<>(Direction.class);

    static {
        CONNECTION_PROPERTIES.put(Direction.DOWN, DOWN);
        CONNECTION_PROPERTIES.put(Direction.UP, UP);
        CONNECTION_PROPERTIES.put(Direction.NORTH, NORTH);
        CONNECTION_PROPERTIES.put(Direction.SOUTH, SOUTH);
        CONNECTION_PROPERTIES.put(Direction.WEST, WEST);
        CONNECTION_PROPERTIES.put(Direction.EAST, EAST);
    }

    public BlockFluidPipe() {
        super(BlockBehaviour.Properties.m_60939_(Material.f_76279_)
                .m_60999_()
                .m_60913_(1.0F, 10.0F));
        BlockState state = m_49966_()
                .m_61124_(DOWN, Boolean.FALSE)
                .m_61124_(UP, Boolean.FALSE)
                .m_61124_(NORTH, Boolean.FALSE)
                .m_61124_(SOUTH, Boolean.FALSE)
                .m_61124_(WEST, Boolean.FALSE)
                .m_61124_(EAST, Boolean.FALSE)
                .m_61124_(TYPE, PipeType.bronze)
                .m_61124_(SIZE, PipeSize.small);
        m_49959_(state);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(DOWN, UP, NORTH, SOUTH, WEST, EAST, TYPE, SIZE);
    }

    @Override
    public RenderShape m_7514_(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return new TileEntityFluidPipe(pos, state);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(
            Level level, BlockState state, BlockEntityType<T> type) {
        if (type != RestoredLegacyContent.FLUID_PIPE_BLOCK_ENTITY.get()) {
            return null;
        }
        return (tickLevel, tickPos, tickState, tile) ->
                ((TileEntityFluidPipe) tile).tickPipe();
    }

    @Override
    public void m_6861_(
            BlockState state,
            Level level,
            BlockPos pos,
            Block neighbor,
            BlockPos neighborPos,
            boolean moving) {
        BlockEntity tile = level.m_7702_(pos);
        if (tile instanceof TileEntityFluidPipe pipe) {
            pipe.updateConnectivity();
        }
    }

    @Override
    public VoxelShape m_5940_(
            BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getPipeShape(state);
    }

    @Override
    public VoxelShape m_5939_(
            BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getPipeShape(state);
    }

    @Override
    public ItemStack m_7397_(BlockGetter level, BlockPos pos, BlockState state) {
        return ItemFluidPipe.getPipe(state.m_61143_(TYPE), state.m_61143_(SIZE));
    }

    @Override
    public void m_6810_(
            BlockState oldState, Level level, BlockPos pos, BlockState newState, boolean moving) {
        if (!oldState.m_60713_(newState.m_60734_())) {
            BlockEntity tile = level.m_7702_(pos);
            if (tile instanceof TileEntityFluidPipe pipe && !level.f_46443_) {
                pipe.dropContents(newState.m_60795_());
            }
            super.m_6810_(oldState, level, pos, newState, moving);
        }
    }

    public static BooleanProperty property(Direction direction) {
        return CONNECTION_PROPERTIES.get(direction);
    }

    private static VoxelShape getPipeShape(BlockState state) {
        PipeSize size = state.m_61143_(SIZE);
        double half = size.thickness * 8.0D;
        double min = 8.0D - half;
        double max = 8.0D + half;
        VoxelShape shape = Block.m_49796_(min, min, min, max, max, max);
        for (Direction direction : Direction.values()) {
            if (!state.m_61143_(property(direction))) {
                continue;
            }
            VoxelShape arm = switch (direction) {
                case DOWN -> Block.m_49796_(min, 0.0D, min, max, min, max);
                case UP -> Block.m_49796_(min, max, min, max, 16.0D, max);
                case NORTH -> Block.m_49796_(min, min, 0.0D, max, max, min);
                case SOUTH -> Block.m_49796_(min, min, max, max, max, 16.0D);
                case WEST -> Block.m_49796_(0.0D, min, min, min, max, max);
                case EAST -> Block.m_49796_(max, min, min, 16.0D, max, max);
            };
            shape = Shapes.m_83110_(shape, arm);
        }
        return shape;
    }
}
