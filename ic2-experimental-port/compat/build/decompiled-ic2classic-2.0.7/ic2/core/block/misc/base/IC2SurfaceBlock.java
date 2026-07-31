/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
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
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Half
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
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
import ic2.core.block.misc.base.IC2DoubleSurfaceBlock;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IBlockModifiers;
import ic2.core.utils.helpers.Tool;
import java.util.Objects;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IC2SurfaceBlock
extends IC2Block
implements IBlockModel,
IAutoCreator,
IBlockModifiers,
PainterHelper.IPaintable {
    public static final EnumProperty<Half> HALF = BlockStateProperties.f_61402_;
    public static final BooleanProperty WATER = BlockStateProperties.f_61362_;
    private static final AABB BOTTOM = new AABB(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
    private static final AABB TOP = new AABB(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape BOTTOM_SHAPE = Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)2.0, (double)16.0);
    private static final VoxelShape TOP_SHAPE = Block.m_49796_((double)0.0, (double)14.0, (double)0.0, (double)16.0, (double)16.0, (double)16.0);
    private String textureName;
    private String textureFolder;
    private Block material;
    private Block doubleBlock;
    protected DyeableMap colorMap;
    private DyeColor color;

    public IC2SurfaceBlock(String name, Block material, String textureFolder, String textureName) {
        super(name, BlockBehaviour.Properties.m_60926_((BlockBehaviour)material));
        this.textureName = textureName;
        this.textureFolder = textureFolder;
        this.material = material;
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(HALF, (Comparable)Half.BOTTOM));
    }

    public IC2SurfaceBlock(DyeableBlock material, String name, String texture, DyeableMap colorMap) {
        this(name + material.getColor().m_41065_(), material, texture, material.getColor().m_41065_());
        this.color = material.getColor();
        this.colorMap = colorMap;
        colorMap.addBlock(this, this.color);
    }

    public static IC2SurfaceBlock createCFoamTile(DyeableBlock block, DyeableMap colorMap) {
        return new IC2LavaSurfaceBlock(block, "cfoam_surface_", "cfoam/normal", colorMap);
    }

    public void setDouble(Block doubleBlock) {
        this.doubleBlock = doubleBlock;
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.m_61143_(HALF) == Half.TOP ? TOP_SHAPE : BOTTOM_SHAPE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2(this.textureFolder).get(this.textureName);
    }

    @Override
    public AABB getModelBounds(BlockState state) {
        return state.m_61143_(HALF) == Half.TOP ? TOP : BOTTOM;
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

    public boolean m_6864_(BlockState state, BlockPlaceContext context) {
        return this.doubleBlock != null && Block.m_49814_((Item)context.m_43722_().m_41720_()) instanceof IC2SurfaceBlock && context.m_43723_() != null && context.m_43723_().m_6144_();
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this, new Item.Properties().m_41491_(Objects.requireNonNull(this.material.m_5456_().m_41471_())));
    }

    @Override
    public boolean hasTextureRotation(BlockState state, Direction side) {
        return false;
    }

    @Override
    public int getTextureRotation(BlockState state, Direction side) {
        return 0;
    }

    @Override
    public boolean hasCustomTextureUVs(BlockState state, Direction side) {
        return true;
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public float[] getCustomTextureUVs(BlockState state, Direction side) {
        float[] output;
        if (Direction.UP == side | Direction.DOWN == side) {
            output = new float[]{1.0f, 1.0f, 14.0f, 14.0f};
        } else {
            float[] fArray;
            if (side.m_122421_() == Direction.AxisDirection.POSITIVE) {
                float[] fArray2 = new float[4];
                fArray2[0] = 1.0f;
                fArray2[1] = 3.0f;
                fArray2[2] = 14.0f;
                fArray = fArray2;
                fArray2[3] = 14.0f;
            } else {
                float[] fArray3 = new float[4];
                fArray3[0] = 1.0f;
                fArray3[1] = 1.0f;
                fArray3[2] = 14.0f;
                fArray = fArray3;
                fArray3[3] = 3.0f;
            }
            output = fArray;
        }
        return output;
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{HALF}).m_61104_(new Property[]{WATER});
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        BlockState original;
        Direction direction = context.m_43719_();
        BlockPos blockpos = context.m_8083_();
        FluidState fluid = context.m_43725_().m_6425_(blockpos);
        if (this.doubleBlock != null && context.m_7078_() && (original = context.m_43725_().m_8055_(blockpos)).m_60734_() instanceof IC2SurfaceBlock) {
            DyeColor orColor = ((IC2SurfaceBlock)original.m_60734_()).color;
            boolean top = original.m_61143_(HALF) == Half.TOP;
            return (BlockState)((BlockState)((BlockState)this.doubleBlock.m_49966_().m_61124_(IC2DoubleSurfaceBlock.MAIN_COLOR, (Comparable)(top ? this.color : orColor))).m_61124_(IC2DoubleSurfaceBlock.SECOND_COLOR, (Comparable)(top ? orColor : this.color))).m_61124_((Property)WATER, (Comparable)Boolean.valueOf(fluid.m_76152_() == Fluids.f_76193_));
        }
        return (BlockState)((BlockState)this.m_49966_().m_61124_(HALF, (Comparable)(!(direction == Direction.DOWN || direction != Direction.UP && context.m_43720_().f_82480_ - (double)blockpos.m_123342_() > 0.5) ? Half.BOTTOM : Half.TOP))).m_61124_((Property)WATER, (Comparable)Boolean.valueOf(fluid.m_76152_() == Fluids.f_76193_));
    }

    public void m_6861_(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (((Boolean)state.m_61143_((Property)WATER)).booleanValue()) {
            world.m_186469_(pos, (Fluid)Fluids.f_76193_, Fluids.f_76193_.m_6718_((LevelReader)world));
        }
    }

    public FluidState m_5888_(BlockState state) {
        return (Boolean)state.m_61143_((Property)WATER) != false ? Fluids.f_76193_.m_76145_() : Fluids.f_76191_.m_76145_();
    }

    public static class IC2LavaSurfaceBlock
    extends IC2SurfaceBlock
    implements IDualLogged {
        public static final BooleanProperty LAVA = IC2Properties.LAVA_LOGGED;

        public IC2LavaSurfaceBlock(String name, Block material, String textureFolder, String textureName) {
            super(name, material, textureFolder, textureName);
            this.setHarvestTool(Tool.PICKAXE.withLevel(1));
        }

        public IC2LavaSurfaceBlock(DyeableBlock material, String name, String texture, DyeableMap colorMap) {
            super(material, name, texture, colorMap);
            this.setHarvestTool(Tool.PICKAXE.withLevel(1));
        }

        @Override
        protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
            super.m_7926_(builder);
            builder.m_61104_(new Property[]{LAVA});
        }

        @Override
        public FluidState m_5888_(BlockState state) {
            return IDualLogged.getFluidState(state);
        }

        @Override
        public BlockState m_5573_(BlockPlaceContext context) {
            BlockState state = super.m_5573_(context);
            FluidState fluid = context.m_43725_().m_6425_(context.m_8083_());
            return (BlockState)state.m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(fluid.m_76152_() == Fluids.f_76195_));
        }

        @Override
        public void m_6861_(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
            super.m_6861_(state, world, pos, block, fromPos, isMoving);
            if (((Boolean)state.m_61143_((Property)LAVA)).booleanValue()) {
                world.m_186469_(pos, (Fluid)Fluids.f_76195_, Fluids.f_76195_.m_6718_((LevelReader)world));
            }
        }
    }
}

