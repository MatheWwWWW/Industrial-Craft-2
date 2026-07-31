/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.NonNullList
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.Nameable
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
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
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc.base;

import ic2.api.blocks.DyeableMap;
import ic2.core.block.base.IC2Block;
import ic2.core.block.base.IStateController;
import ic2.core.block.base.misc.IDualLogged;
import ic2.core.block.misc.base.IC2PanelBlock;
import ic2.core.block.misc.tiles.PanelCornerTileEntity;
import ic2.core.block.rendering.block.base.CornerRailingModel;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.helpers.Tool;
import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IC2CornerRailingBlock
extends IC2Block
implements ICustomBlockModel,
IStateController<PanelCornerTileEntity>,
IDualLogged {
    public static final VoxelShape[] SHAPES = IC2CornerRailingBlock.createShapes();
    public static final BooleanProperty WATER = BlockStateProperties.f_61362_;
    public static final BooleanProperty LAVA = IC2Properties.LAVA_LOGGED;
    public static final IntegerProperty COLLISION = IntegerProperty.m_61631_((String)"collision", (int)0, (int)15);
    protected BlockEntityType<? extends PanelCornerTileEntity> type;
    protected DyeableMap colorMap;
    protected String path;

    public IC2CornerRailingBlock(String blockName, String path, BlockEntityType<? extends PanelCornerTileEntity> type, BlockBehaviour.Properties properties, DyeableMap colorMap) {
        super(blockName, properties);
        this.colorMap = colorMap;
        this.path = path;
        this.type = type;
        for (Block block : colorMap.getBlocks()) {
            if (!(block instanceof IC2PanelBlock)) continue;
            ((IC2PanelBlock)block).setCorner(this);
        }
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)WATER, (Comparable)Boolean.valueOf(false))).m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(false)));
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
    }

    public boolean m_6864_(BlockState state, BlockPlaceContext context) {
        if (Block.m_49814_((Item)context.m_43722_().m_41720_()) instanceof IC2PanelBlock && context.m_43723_() != null & context.m_43723_().m_6144_()) {
            return (Integer)state.m_61143_((Property)COLLISION) != 15;
        }
        return false;
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> state) {
        state.m_61104_(new Property[]{COLLISION}).m_61104_(new Property[]{WATER}).m_61104_(new Property[]{LAVA});
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

    public DyeableMap getColorMap() {
        return this.colorMap;
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES[(Integer)state.m_61143_((Property)COLLISION)];
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return new CornerRailingModel(this.path);
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, PanelCornerTileEntity tile) {
        tile.withState(COLLISION, tile.getBoundId());
    }

    @Override
    public ItemStack createDrop(BlockState state, ItemStack tool, RandomSource rand, BlockEntity tile) {
        return ItemStack.f_41583_;
    }

    @Override
    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return this.type.m_155264_(pos, state);
    }

    public void m_49811_(CreativeModeTab p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        DyeColor color;
        BlockEntity tile;
        int index;
        if (target.m_6662_() != HitResult.Type.BLOCK) {
            return ItemStack.f_41583_;
        }
        BlockHitResult block = (BlockHitResult)target;
        Vec3 place = block.m_82450_().m_82492_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
        int n = place.m_7096_() < 0.5 ? (place.m_7094_() < 0.5 ? 0 : 3) : (index = place.m_7094_() < 0.5 ? 1 : 2);
        if (((Integer)state.m_61143_((Property)COLLISION) & 1 << index) != 0 && (tile = world.m_7702_(pos)) instanceof PanelCornerTileEntity && (color = ((PanelCornerTileEntity)tile).getColor(index)) != null) {
            ItemStack stack = new ItemStack((ItemLike)this.colorMap.getBlock(color));
            if (((Nameable)tile).m_8077_()) {
                stack.m_41714_(((Nameable)tile).m_7770_());
            }
            return stack;
        }
        return ItemStack.f_41583_;
    }

    @Override
    public BlockItem createItem() {
        return null;
    }

    private static VoxelShape[] createShapes() {
        VoxelShape[] baseShape = new VoxelShape[]{Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)2.0, (double)16.0, (double)2.0), Block.m_49796_((double)14.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)2.0), Block.m_49796_((double)14.0, (double)0.0, (double)14.0, (double)16.0, (double)16.0, (double)16.0), Block.m_49796_((double)0.0, (double)0.0, (double)14.0, (double)2.0, (double)16.0, (double)16.0)};
        Object[] result = new VoxelShape[16];
        Arrays.fill(result, Shapes.m_83040_());
        for (int i = 0; i < 16; ++i) {
            if ((i & 1) != 0) {
                result[i] = Shapes.m_83110_((VoxelShape)result[i], (VoxelShape)baseShape[0]);
            }
            if ((i & 2) != 0) {
                result[i] = Shapes.m_83110_((VoxelShape)result[i], (VoxelShape)baseShape[1]);
            }
            if ((i & 4) != 0) {
                result[i] = Shapes.m_83110_((VoxelShape)result[i], (VoxelShape)baseShape[2]);
            }
            if ((i & 8) != 0) {
                result[i] = Shapes.m_83110_((VoxelShape)result[i], (VoxelShape)baseShape[3]);
            }
            if (i != 0) continue;
            result[i] = Shapes.m_83144_();
        }
        return result;
    }
}

