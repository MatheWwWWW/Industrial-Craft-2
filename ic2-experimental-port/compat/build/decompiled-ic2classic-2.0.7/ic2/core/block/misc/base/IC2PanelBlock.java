/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Direction$AxisDirection
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.block.state.properties.StairsShape
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc.base;

import ic2.api.blocks.DyeableMap;
import ic2.api.blocks.PainterHelper;
import ic2.core.block.base.IAutoCreator;
import ic2.core.block.base.IC2Block;
import ic2.core.block.base.misc.IDualLogged;
import ic2.core.block.misc.base.DyeableBlock;
import ic2.core.block.misc.base.IC2CornerRailingBlock;
import ic2.core.block.misc.base.IC2DoubleRailingBlock;
import ic2.core.block.misc.base.IC2EdgeRailingBlock;
import ic2.core.block.rendering.block.base.RailingModel;
import ic2.core.item.block.RailingItem;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.helpers.Tool;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IC2PanelBlock
extends IC2Block
implements ICustomBlockModel,
IAutoCreator,
IDualLogged,
PainterHelper.IPaintable {
    public static final BooleanProperty WATER = BlockStateProperties.f_61362_;
    public static final BooleanProperty LAVA = IC2Properties.LAVA_LOGGED;
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.f_61398_;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.f_54117_;
    private static final VoxelShape[][] SHAPES = new VoxelShape[][]{{Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)2.0), Block.m_49796_((double)14.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)16.0), Block.m_49796_((double)0.0, (double)0.0, (double)14.0, (double)16.0, (double)16.0, (double)16.0), Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)2.0, (double)16.0, (double)16.0)}, {Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)2.0, (double)16.0, (double)2.0), Block.m_49796_((double)14.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)2.0), Block.m_49796_((double)14.0, (double)0.0, (double)14.0, (double)16.0, (double)16.0, (double)16.0), Block.m_49796_((double)0.0, (double)0.0, (double)14.0, (double)2.0, (double)16.0, (double)16.0)}, {Block.m_49796_((double)14.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)2.0), Block.m_49796_((double)14.0, (double)0.0, (double)14.0, (double)16.0, (double)16.0, (double)16.0), Block.m_49796_((double)0.0, (double)0.0, (double)14.0, (double)2.0, (double)16.0, (double)16.0), Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)2.0, (double)16.0, (double)2.0)}, {Shapes.m_83110_((VoxelShape)Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)2.0), (VoxelShape)Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)2.0, (double)16.0, (double)16.0)), Shapes.m_83110_((VoxelShape)Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)2.0), (VoxelShape)Block.m_49796_((double)14.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)16.0)), Shapes.m_83110_((VoxelShape)Block.m_49796_((double)0.0, (double)0.0, (double)14.0, (double)16.0, (double)16.0, (double)16.0), (VoxelShape)Block.m_49796_((double)14.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)16.0)), Shapes.m_83110_((VoxelShape)Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)2.0, (double)16.0, (double)16.0), (VoxelShape)Block.m_49796_((double)0.0, (double)0.0, (double)14.0, (double)16.0, (double)16.0, (double)16.0))}, {Shapes.m_83110_((VoxelShape)Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)2.0), (VoxelShape)Block.m_49796_((double)14.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)16.0)), Shapes.m_83110_((VoxelShape)Block.m_49796_((double)0.0, (double)0.0, (double)14.0, (double)16.0, (double)16.0, (double)16.0), (VoxelShape)Block.m_49796_((double)14.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)16.0)), Shapes.m_83110_((VoxelShape)Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)2.0, (double)16.0, (double)16.0), (VoxelShape)Block.m_49796_((double)0.0, (double)0.0, (double)14.0, (double)16.0, (double)16.0, (double)16.0)), Shapes.m_83110_((VoxelShape)Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)2.0), (VoxelShape)Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)2.0, (double)16.0, (double)16.0))}};
    private String textureName;
    private String textureFolder;
    private Block material;
    protected DyeableMap colorMap;
    private DyeColor color;
    private Block straight;
    private Block edge;
    private Block corner;

    public IC2PanelBlock(String name, Block material, String textureFolder, String textureName) {
        super(name, BlockBehaviour.Properties.m_60926_((BlockBehaviour)material));
        this.textureName = textureName;
        this.textureFolder = textureFolder;
        this.material = material;
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_((Property)FACING, (Comparable)Direction.SOUTH)).m_61124_((Property)WATER, (Comparable)Boolean.valueOf(false))).m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(false)));
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
    }

    public IC2PanelBlock(DyeableBlock material, String name, String texture, DyeableMap colorMap) {
        this(name + material.getColor().m_41065_(), material, texture, material.getColor().m_41065_());
        this.color = material.getColor();
        this.colorMap = colorMap;
        colorMap.addBlock(this, this.color);
    }

    public static IC2PanelBlock createFoamPanel(DyeableBlock material, DyeableMap colorMap) {
        return new IC2PanelBlock(material, "cfoam_panel_", "cfoam/normal", colorMap);
    }

    public void setEdge(Block edge) {
        this.edge = edge;
    }

    public void setCorner(Block corner) {
        this.corner = corner;
    }

    public void setStraight(Block straight) {
        this.straight = straight;
    }

    public Block getCorner() {
        return this.corner;
    }

    public DyeColor getColor() {
        return this.color;
    }

    @Override
    public boolean recolor(BlockState state, Level world, BlockPos pos, Vec3 exactClick, Direction dir, DyeColor color) {
        if (this.colorMap == null) {
            return false;
        }
        Block block = this.colorMap.getBlock(color);
        return block != null && world.m_46597_(pos, PainterHelper.copyProperties(state, block.m_49966_()));
    }

    @Override
    public DyeColor getColor(BlockState state) {
        return this.color;
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES[((StairsShape)state.m_61143_(SHAPE)).ordinal()][((Direction)state.m_61143_((Property)FACING)).m_122416_()];
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return new RailingModel(state, this.textureFolder, this.textureName);
    }

    @Override
    public BlockItem createItem() {
        return new RailingItem((Block)this, new Item.Properties().m_41491_(Objects.requireNonNull(this.material.m_5456_().m_41471_())));
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{FACING}).m_61104_(new Property[]{SHAPE}).m_61104_(new Property[]{WATER}).m_61104_(new Property[]{LAVA});
    }

    public boolean m_6864_(BlockState state, BlockPlaceContext context) {
        if (Block.m_49814_((Item)context.m_43722_().m_41720_()) instanceof IC2PanelBlock && context.m_43723_() != null & context.m_43723_().m_6144_()) {
            StairsShape shape = (StairsShape)state.m_61143_(SHAPE);
            if (this.straight != null && shape == StairsShape.STRAIGHT) {
                return true;
            }
            if (this.edge != null && (shape == StairsShape.OUTER_LEFT || shape == StairsShape.OUTER_RIGHT)) {
                return true;
            }
            return this.corner != null && (shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT);
        }
        return false;
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        BlockState original;
        StairsShape shape;
        BlockState state = (BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)context.m_8125_().m_122424_());
        StairsShape stairsShape = shape = context.m_43723_() != null && context.m_43723_().m_6144_() ? StairsShape.STRAIGHT : this.getShapeProperty(state, (BlockGetter)context.m_43725_(), context.m_8083_(), context.m_43720_());
        if (shape == StairsShape.STRAIGHT && (original = context.m_43725_().m_8055_(context.m_8083_())).m_60734_() instanceof IC2PanelBlock) {
            StairsShape otherShape = (StairsShape)original.m_61143_(SHAPE);
            Direction facing = (Direction)original.m_61143_((Property)FACING);
            if (this.straight != null && otherShape == StairsShape.STRAIGHT) {
                DyeColor orColor = ((IC2PanelBlock)original.m_60734_()).color;
                boolean negative = facing.m_122421_() == Direction.AxisDirection.NEGATIVE;
                return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.straight.m_49966_().m_61124_((Property)IC2DoubleRailingBlock.AXIS, (Comparable)Boolean.valueOf(facing.m_122434_() == Direction.Axis.Z))).m_61124_(IC2DoubleRailingBlock.MAIN_COLOR, (Comparable)(negative ? this.color : orColor))).m_61124_(IC2DoubleRailingBlock.SECOND_COLOR, (Comparable)(negative ? orColor : this.color))).m_61124_((Property)WATER, (Comparable)((Boolean)original.m_61143_((Property)WATER)))).m_61124_((Property)LAVA, (Comparable)((Boolean)original.m_61143_((Property)LAVA)));
            }
            if (this.edge != null && (otherShape == StairsShape.OUTER_LEFT || otherShape == StairsShape.OUTER_RIGHT)) {
                DyeColor orColor = ((IC2PanelBlock)original.m_60734_()).color;
                return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.edge.m_49966_().m_61124_((Property)IC2EdgeRailingBlock.FACING, (Comparable)facing)).m_61124_((Property)IC2EdgeRailingBlock.RIGHT, (Comparable)Boolean.valueOf(otherShape == StairsShape.OUTER_RIGHT))).m_61124_(IC2EdgeRailingBlock.MAIN_COLOR, (Comparable)orColor)).m_61124_(IC2EdgeRailingBlock.SECOND_COLOR, (Comparable)this.color)).m_61124_((Property)WATER, (Comparable)((Boolean)original.m_61143_((Property)WATER)))).m_61124_((Property)LAVA, (Comparable)((Boolean)original.m_61143_((Property)LAVA)));
            }
            if (this.corner != null && (otherShape == StairsShape.INNER_LEFT || otherShape == StairsShape.INNER_RIGHT)) {
                return (BlockState)((BlockState)this.corner.m_49966_().m_61124_((Property)WATER, (Comparable)((Boolean)original.m_61143_((Property)WATER)))).m_61124_((Property)LAVA, (Comparable)((Boolean)original.m_61143_((Property)LAVA)));
            }
            return original;
        }
        FluidState fluid = context.m_43725_().m_6425_(context.m_8083_());
        return (BlockState)((BlockState)((BlockState)state.m_61124_(SHAPE, (Comparable)shape)).m_61124_((Property)WATER, (Comparable)Boolean.valueOf(fluid.m_76152_() == Fluids.f_76193_))).m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(fluid.m_76152_() == Fluids.f_76195_));
    }

    public FluidState m_5888_(BlockState state) {
        return IDualLogged.getFluidState(state);
    }

    public void m_6861_(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (((Boolean)state.m_61143_((Property)WATER)).booleanValue()) {
            world.m_186469_(pos, (Fluid)Fluids.f_76193_, Fluids.f_76193_.m_6718_((LevelReader)world));
        } else if (((Boolean)state.m_61143_((Property)LAVA)).booleanValue()) {
            world.m_186469_(pos, (Fluid)Fluids.f_76195_, Fluids.f_76195_.m_6718_((LevelReader)world));
        }
    }

    public BlockState m_6843_(BlockState state, Rotation rotation) {
        return (BlockState)state.m_61124_((Property)FACING, (Comparable)rotation.m_55954_((Direction)state.m_61143_((Property)FACING)));
    }

    public BlockState m_6943_(BlockState state, Mirror mirror) {
        return (BlockState)state.m_61124_((Property)FACING, (Comparable)mirror.m_54848_((Direction)state.m_61143_((Property)FACING)));
    }

    public StairsShape getShapeProperty(BlockState state, BlockGetter blockReader, BlockPos pos, Vec3 exactClick) {
        Direction otherFacing;
        Direction direction = (Direction)state.m_61143_((Property)FACING);
        Direction place = direction.m_122427_();
        double exact = exactClick.m_82492_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_()).m_82542_((double)Math.abs(place.m_122429_()), 0.0, (double)Math.abs(place.m_122431_())).m_82553_();
        boolean right = direction.m_122421_() == (direction.m_122434_() == Direction.Axis.X ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE) ? exact > 0.5 : exact < 0.5;
        BlockState otherState = blockReader.m_8055_(pos.m_121945_(direction));
        if (this.isRailing(otherState)) {
            otherFacing = (Direction)otherState.m_61143_((Property)FACING);
            if (otherFacing.m_122434_() != ((Direction)state.m_61143_((Property)FACING)).m_122434_() && this.isDifferentRailing(state, blockReader, pos, otherFacing.m_122424_())) {
                return otherFacing == direction.m_122428_() ? StairsShape.OUTER_LEFT : StairsShape.OUTER_RIGHT;
            }
        } else if (this.isDoubleRailing(otherState, direction) || this.isEdgeRailing(otherState, direction) || this.isCornerRailing(otherState, direction, right)) {
            return right ? StairsShape.OUTER_RIGHT : StairsShape.OUTER_LEFT;
        }
        if (this.isRailing(otherState = blockReader.m_8055_(pos.m_121945_(direction.m_122424_())))) {
            otherFacing = (Direction)otherState.m_61143_((Property)FACING);
            if (otherFacing.m_122434_() != ((Direction)state.m_61143_((Property)FACING)).m_122434_() && this.isDifferentRailing(state, blockReader, pos, otherFacing)) {
                return otherFacing == direction.m_122428_() ? StairsShape.INNER_LEFT : StairsShape.INNER_RIGHT;
            }
        } else if (this.isDoubleRailing(otherState, direction) || this.isEdgeRailing(otherState, direction) || this.isCornerRailing(otherState, direction, right)) {
            return right ? StairsShape.INNER_LEFT : StairsShape.INNER_RIGHT;
        }
        return StairsShape.STRAIGHT;
    }

    public boolean isDifferentRailing(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        BlockState blockstate = world.m_8055_(pos.m_121945_(face));
        return !this.isRailing(blockstate) || blockstate.m_61143_((Property)FACING) != state.m_61143_((Property)FACING);
    }

    public boolean isCornerRailing(BlockState state, Direction direction, boolean right) {
        return state.m_60734_() instanceof IC2CornerRailingBlock && ((Integer)state.m_61143_((Property)IC2CornerRailingBlock.COLLISION) & 1 << this.calcIndex(direction, right)) != 0;
    }

    public boolean isEdgeRailing(BlockState state, Direction direction) {
        if (state.m_60734_() instanceof IC2EdgeRailingBlock) {
            Direction facing = (Direction)state.m_61143_((Property)IC2EdgeRailingBlock.FACING);
            return facing.m_122424_() == direction || ((Boolean)state.m_61143_((Property)IC2EdgeRailingBlock.RIGHT) != false ? facing.m_122428_() : facing.m_122427_()) == direction;
        }
        return false;
    }

    public boolean isDoubleRailing(BlockState state, Direction direction) {
        return state.m_60734_() instanceof IC2DoubleRailingBlock && direction.m_122434_() == Direction.Axis.Z != (Boolean)state.m_61143_((Property)IC2DoubleRailingBlock.AXIS);
    }

    public int calcIndex(Direction dir, boolean right) {
        return (dir.m_122416_() + (right ? 1 : 0)) % 4;
    }

    public boolean isRailing(BlockState state) {
        return state.m_60734_() instanceof IC2PanelBlock;
    }
}

