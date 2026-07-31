/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.BooleanOp
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.extensions.common.IClientBlockExtensions
 */
package ic2.core.block.transport.item;

import com.google.common.base.Objects;
import ic2.api.blocks.PainterHelper;
import ic2.api.util.DirectionList;
import ic2.core.block.base.IC2ContainerBlock;
import ic2.core.block.base.ICamouflageBlock;
import ic2.core.block.base.IStateController;
import ic2.core.block.base.misc.IDualLogged;
import ic2.core.block.base.misc.ITubeBlock;
import ic2.core.block.cables.CableBlock;
import ic2.core.block.misc.CFoamBlock;
import ic2.core.block.rendering.block.tubes.TubeModel;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.item.tool.SprayerTool;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.blocks.NonTranslucentBlockModel;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.math.ColorUtils;
import java.util.function.Consumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;

public class TubeBlock
extends IC2ContainerBlock
implements ITubeBlock,
ICustomBlockModel,
ICamouflageBlock,
IDualLogged,
IStateController<TubeTileEntity>,
PainterHelper.IPaintable {
    public static final BooleanProperty WATER = BlockStateProperties.f_61362_;
    public static final BooleanProperty LAVA = IC2Properties.LAVA_LOGGED;
    public static final IntegerProperty FOAMED = CableBlock.FOAMED;
    BlockEntityType<? extends BlockEntity> type;
    VoxelShape[] shapes = this.createShapes();
    String texture;

    public TubeBlock(String name, BlockEntityType<? extends BlockEntity> type, String texture) {
        super(name, BlockBehaviour.Properties.m_60939_((Material)Material.f_76283_).m_60955_().m_60924_((T, V, X) -> false).m_60978_(1.0f).m_60918_(SoundType.f_56742_));
        this.setHarvestTool(Tool.PICKAXE);
        this.type = type;
        this.texture = texture;
        this.m_49959_((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_((Property)WATER, (Comparable)Boolean.valueOf(false))).m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(false))).m_61124_((Property)FOAMED, (Comparable)Integer.valueOf(0)));
    }

    @Override
    public int getHighlightColor(BlockState state) {
        return ColorUtils.RED;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean canRenderInLayer(BlockState state, RenderType type) {
        return (Integer)state.m_61143_((Property)FOAMED) > 0 && Objects.equal((Object)RenderType.m_110466_(), (Object)type) || Objects.equal((Object)RenderType.m_110463_(), (Object)type);
    }

    public void initializeClient(Consumer<IClientBlockExtensions> consumer) {
        consumer.accept(new ICamouflageBlock.CamuflageWrapper(this));
    }

    public boolean m_6864_(BlockState state, BlockPlaceContext useContext) {
        return useContext.m_43722_().m_41720_() instanceof SprayerTool && (Integer)state.m_61143_((Property)FOAMED) <= 0;
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this);
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{WATER}).m_61104_(new Property[]{LAVA}).m_61104_(new Property[]{FOAMED});
    }

    @Override
    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return this.type.m_155264_(pos, state);
    }

    public FluidState m_5888_(BlockState state) {
        return IDualLogged.getFluidState(state);
    }

    @Override
    public boolean m_6044_(BlockGetter worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return IDualLogged.super.m_6044_(worldIn, pos, state, fluidIn) && (Integer)state.m_61143_((Property)FOAMED) <= 0;
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, TubeTileEntity tile) {
        tile.setState((BlockState)((BlockState)((BlockState)state.m_61124_((Property)FOAMED, (Comparable)Integer.valueOf(tile.foamed))).m_61124_((Property)WATER, (Comparable)Boolean.valueOf((Boolean)state.m_61143_((Property)WATER) != false && tile.foamed == 0))).m_61124_((Property)LAVA, (Comparable)Boolean.valueOf((Boolean)state.m_61143_((Property)LAVA) != false && tile.foamed == 0)));
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if ((Integer)state.m_61143_((Property)FOAMED) > 0) {
            return Shapes.m_83144_();
        }
        BlockEntity tile = worldIn.m_7702_(pos);
        if (tile instanceof TubeTileEntity) {
            TubeTileEntity tube = (TubeTileEntity)tile;
            return this.shapes[tube.getConnectivity()];
        }
        return this.shapes[0];
    }

    @Override
    public VoxelShape[] getRealShapes(BlockState state, BlockGetter reader, BlockPos pos) {
        BlockEntity tile = reader.m_7702_(pos);
        if (tile instanceof TubeTileEntity) {
            TubeTileEntity tube = (TubeTileEntity)tile;
            return new VoxelShape[]{this.shapes[tube.getValidDirections()], Shapes.m_83113_((VoxelShape)this.shapes[0], (VoxelShape)this.shapes[tube.getAnchors()], (BooleanOp)BooleanOp.f_82683_)};
        }
        return new VoxelShape[]{this.shapes[0], this.shapes[0]};
    }

    @Override
    public boolean recolor(BlockState state, Level world, BlockPos pos, Vec3 exactClick, Direction dir, DyeColor color) {
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof TubeTileEntity) {
            return ((TubeTileEntity)tile).setColor(dir, color);
        }
        return false;
    }

    @Override
    public DyeColor getColor(BlockState state) {
        return null;
    }

    @Override
    public DyeColor getBlockColor(BlockState state) {
        return null;
    }

    @Override
    public float getRadius(BlockState state) {
        return 3.0f;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(BlockState state, boolean center, Direction dir, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2("transport/tubes").get(this.texture);
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        FluidState state = context.m_43725_().m_6425_(context.m_8083_());
        return (BlockState)super.m_5573_(context).m_61124_((Property)WATER, (Comparable)Boolean.valueOf(state.m_76152_() == Fluids.f_76193_));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return (Integer)state.m_61143_((Property)FOAMED) == 1 ? new NonTranslucentBlockModel((BlockState)IC2Blocks.CFOAM_WET.m_49966_().m_61124_(CFoamBlock.FOAM_TYPE, (Comparable)((Object)CFoamBlock.FoamType.CABLE)), (IBlockModel)IC2Blocks.CFOAM_WET) : new TubeModel(this, state);
    }

    VoxelShape[] createShapes() {
        VoxelShape[] shapes = this.createSubShapes();
        VoxelShape[] results = new VoxelShape[64];
        for (int j = 0; j < 64; ++j) {
            DirectionList list = DirectionList.ofNumber(j);
            VoxelShape[] entry = new VoxelShape[list.size()];
            int index = 0;
            for (Direction dir : list) {
                entry[index++] = shapes[dir.m_122411_()];
            }
            results[j] = Shapes.m_83124_((VoxelShape)shapes[6], (VoxelShape[])entry);
        }
        return results;
    }

    VoxelShape[] createSubShapes() {
        VoxelShape[] result = new VoxelShape[7];
        float minValue = 8.0f - this.getRadius(this.m_49966_());
        float maxValue = 8.0f + this.getRadius(this.m_49966_());
        result[0] = Block.m_49796_((double)minValue, (double)0.0, (double)minValue, (double)maxValue, (double)minValue, (double)maxValue);
        result[1] = Block.m_49796_((double)minValue, (double)maxValue, (double)minValue, (double)maxValue, (double)16.0, (double)maxValue);
        result[2] = Block.m_49796_((double)minValue, (double)minValue, (double)0.0, (double)maxValue, (double)maxValue, (double)minValue);
        result[3] = Block.m_49796_((double)minValue, (double)minValue, (double)maxValue, (double)maxValue, (double)maxValue, (double)16.0);
        result[4] = Block.m_49796_((double)0.0, (double)minValue, (double)minValue, (double)minValue, (double)maxValue, (double)maxValue);
        result[5] = Block.m_49796_((double)maxValue, (double)minValue, (double)minValue, (double)16.0, (double)maxValue, (double)maxValue);
        result[6] = Block.m_49796_((double)minValue, (double)minValue, (double)minValue, (double)maxValue, (double)maxValue, (double)maxValue);
        return result;
    }
}

