/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Nameable
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.WallBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.block.state.properties.WallSide
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.block.misc.base;

import com.google.common.collect.ImmutableMap;
import ic2.api.blocks.DyeableMap;
import ic2.api.blocks.PainterHelper;
import ic2.core.block.base.IAutoCreator;
import ic2.core.block.base.IToolProvider;
import ic2.core.block.base.features.IDropProvider;
import ic2.core.block.base.misc.IDualLogged;
import ic2.core.block.misc.base.DyeableBlock;
import ic2.core.block.rendering.block.base.WallModel;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.plugins.IRegistryProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Nameable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.GameData;

public class IC2WallBlock
extends WallBlock
implements IAutoCreator,
ICustomBlockModel,
PainterHelper.IPaintable,
IRegistryProvider {
    private String textureName;
    private String textureFolder;
    private Block material;
    private DyeableMap colorMap;
    private DyeColor color;
    private ResourceLocation id;

    public IC2WallBlock(String name, Block material, String textureFolder, String textureName) {
        super(BlockBehaviour.Properties.m_60926_((BlockBehaviour)material));
        this.id = GameData.checkPrefix((String)name, (boolean)false);
        this.textureName = textureName;
        this.textureFolder = textureFolder;
        this.material = material;
    }

    public IC2WallBlock(String name, Block material, BlockBehaviour.Properties props, String textureFolder, String textureName) {
        super(props);
        this.id = GameData.checkPrefix((String)name, (boolean)false);
        this.textureName = textureName;
        this.textureFolder = textureFolder;
        this.material = material;
    }

    public IC2WallBlock(DyeableBlock material, String name, String texture, DyeableMap colorMap) {
        this(name + material.getColor().m_41065_(), material, texture, material.getColor().m_41065_());
        this.color = material.getColor();
        this.colorMap = colorMap;
        colorMap.addBlock((Block)this, this.color);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    public static IC2WallBlock createCFoamWall(DyeableBlock block, DyeableMap colorMap) {
        return new IC2LavaWallBlock(block, "cfoam_wall_", "cfoam/normal", colorMap);
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this, new Item.Properties().m_41491_(Objects.requireNonNull(this.material.m_5456_().m_41471_())));
    }

    public List<ItemStack> m_7381_(BlockState state, LootContext.Builder builder) {
        LootTable loottable = builder.m_78962_().m_7654_().m_129898_().m_79217_(this.m_60589_());
        if (loottable != LootTable.f_79105_) {
            return loottable.m_230922_(builder.m_78972_(LootContextParams.f_81461_, (Object)state).m_78975_(LootContextParamSets.f_81421_));
        }
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        BlockEntity tile = (BlockEntity)builder.m_78982_(LootContextParams.f_81462_);
        ItemStack stack = new ItemStack((ItemLike)this);
        if (!stack.m_41619_()) {
            if (tile instanceof Nameable && ((Nameable)tile).m_8077_()) {
                stack.m_41714_(((Nameable)tile).m_7770_());
            }
            drops.add(stack);
        }
        if (tile instanceof IDropProvider) {
            ((IDropProvider)tile).addDrops(drops);
        }
        return drops;
    }

    public DyeableMap getColorMap() {
        return this.colorMap;
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

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return new WallModel(state, this.textureFolder, this.textureName);
    }

    public static class IC2LavaWallBlock
    extends IC2WallBlock
    implements IDualLogged,
    IToolProvider {
        Tool tool = Tool.PICKAXE.withLevel(1);
        public static final BooleanProperty LAVA = IC2Properties.LAVA_LOGGED;
        private final Map<BlockState, VoxelShape> shapeByIndex;
        private final Map<BlockState, VoxelShape> collisionShapeByIndex;

        public IC2LavaWallBlock(String name, Block material, String textureFolder, String textureName) {
            super(name, material, textureFolder, textureName);
            this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(false)));
            this.shapeByIndex = this.makeShapes(4.0f, 3.0f, 16.0f, 0.0f, 14.0f, 16.0f);
            this.collisionShapeByIndex = this.makeShapes(4.0f, 3.0f, 24.0f, 0.0f, 24.0f, 24.0f);
        }

        public IC2LavaWallBlock(String name, Block material, BlockBehaviour.Properties props, String textureFolder, String textureName) {
            super(name, material, props, textureFolder, textureName);
            this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(false)));
            this.shapeByIndex = this.makeShapes(4.0f, 3.0f, 16.0f, 0.0f, 14.0f, 16.0f);
            this.collisionShapeByIndex = this.makeShapes(4.0f, 3.0f, 24.0f, 0.0f, 24.0f, 24.0f);
        }

        public IC2LavaWallBlock(DyeableBlock material, String name, String texture, DyeableMap colorMap) {
            super(material, name, texture, colorMap);
            this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(false)));
            this.shapeByIndex = this.makeShapes(4.0f, 3.0f, 16.0f, 0.0f, 14.0f, 16.0f);
            this.collisionShapeByIndex = this.makeShapes(4.0f, 3.0f, 24.0f, 0.0f, 24.0f, 24.0f);
        }

        public IC2LavaWallBlock withTool(Tool tool) {
            this.tool = tool;
            return this;
        }

        @Override
        public void registerTools() {
            this.tool.register((Block)this);
        }

        protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
            builder.m_61104_(new Property[]{LAVA});
            super.m_7926_(builder);
        }

        public FluidState m_5888_(BlockState state) {
            return IDualLogged.getFluidState(state);
        }

        public BlockState m_5573_(BlockPlaceContext context) {
            BlockState state = super.m_5573_(context);
            FluidState fluid = context.m_43725_().m_6425_(context.m_8083_());
            return (BlockState)state.m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(fluid.m_76152_() == Fluids.f_76195_));
        }

        public VoxelShape m_5940_(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
            return this.shapeByIndex.get(state);
        }

        public VoxelShape m_5939_(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
            return this.collisionShapeByIndex.get(state);
        }

        public void m_6861_(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
            super.m_6861_(state, world, pos, block, fromPos, isMoving);
            if (((Boolean)state.m_61143_((Property)LAVA)).booleanValue()) {
                world.m_186469_(pos, (Fluid)Fluids.f_76195_, Fluids.f_76195_.m_6718_((LevelReader)world));
            }
        }

        private static VoxelShape applyWallShape(VoxelShape center, WallSide wallHeight, VoxelShape side, VoxelShape top) {
            if (wallHeight == WallSide.TALL) {
                return Shapes.m_83110_((VoxelShape)center, (VoxelShape)top);
            }
            return wallHeight == WallSide.LOW ? Shapes.m_83110_((VoxelShape)center, (VoxelShape)side) : center;
        }

        private Map<BlockState, VoxelShape> makeShapes(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
            float f = 8.0f - minX;
            float f1 = 8.0f + minX;
            float f2 = 8.0f - minY;
            float f3 = 8.0f + minY;
            VoxelShape voxelShape_0 = Block.m_49796_((double)f, (double)0.0, (double)f, (double)f1, (double)minZ, (double)f1);
            VoxelShape voxelShape_1 = Block.m_49796_((double)f2, (double)maxX, (double)0.0, (double)f3, (double)maxY, (double)f3);
            VoxelShape voxelShape_2 = Block.m_49796_((double)f2, (double)maxX, (double)f2, (double)f3, (double)maxY, (double)16.0);
            VoxelShape voxelShape_3 = Block.m_49796_((double)0.0, (double)maxX, (double)f2, (double)f3, (double)maxY, (double)f3);
            VoxelShape voxelShape_4 = Block.m_49796_((double)f2, (double)maxX, (double)f2, (double)16.0, (double)maxY, (double)f3);
            VoxelShape voxelShape_5 = Block.m_49796_((double)f2, (double)maxX, (double)0.0, (double)f3, (double)maxZ, (double)f3);
            VoxelShape voxelShape_6 = Block.m_49796_((double)f2, (double)maxX, (double)f2, (double)f3, (double)maxZ, (double)16.0);
            VoxelShape voxelShape_7 = Block.m_49796_((double)0.0, (double)maxX, (double)f2, (double)f3, (double)maxZ, (double)f3);
            VoxelShape voxelShape_8 = Block.m_49796_((double)f2, (double)maxX, (double)f2, (double)16.0, (double)maxZ, (double)f3);
            ImmutableMap.Builder builder = ImmutableMap.builder();
            for (Boolean up : f_57949_.m_6908_()) {
                for (WallSide wallHeight_0 : f_57950_.m_6908_()) {
                    for (WallSide wallHeight_1 : f_57951_.m_6908_()) {
                        for (WallSide wallHeight_2 : f_57953_.m_6908_()) {
                            for (WallSide wallHeight_3 : f_57952_.m_6908_()) {
                                VoxelShape voxelShape_9 = Shapes.m_83040_();
                                voxelShape_9 = IC2LavaWallBlock.applyWallShape(voxelShape_9, wallHeight_0, voxelShape_4, voxelShape_8);
                                voxelShape_9 = IC2LavaWallBlock.applyWallShape(voxelShape_9, wallHeight_2, voxelShape_3, voxelShape_7);
                                voxelShape_9 = IC2LavaWallBlock.applyWallShape(voxelShape_9, wallHeight_1, voxelShape_1, voxelShape_5);
                                voxelShape_9 = IC2LavaWallBlock.applyWallShape(voxelShape_9, wallHeight_3, voxelShape_2, voxelShape_6);
                                if (up.booleanValue()) {
                                    voxelShape_9 = Shapes.m_83110_((VoxelShape)voxelShape_9, (VoxelShape)voxelShape_0);
                                }
                                BlockState blockstate = (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_((Property)f_57949_, (Comparable)up)).m_61124_((Property)f_57950_, (Comparable)wallHeight_0)).m_61124_((Property)f_57953_, (Comparable)wallHeight_2)).m_61124_((Property)f_57951_, (Comparable)wallHeight_1)).m_61124_((Property)f_57952_, (Comparable)wallHeight_3);
                                for (Boolean lava : LAVA.m_6908_()) {
                                    builder.put((Object)((BlockState)((BlockState)blockstate.m_61124_((Property)LAVA, (Comparable)lava)).m_61124_((Property)f_57954_, (Comparable)Boolean.FALSE)), (Object)voxelShape_9);
                                    builder.put((Object)((BlockState)((BlockState)blockstate.m_61124_((Property)LAVA, (Comparable)lava)).m_61124_((Property)f_57954_, (Comparable)Boolean.TRUE)), (Object)voxelShape_9);
                                }
                            }
                        }
                    }
                }
            }
            return builder.build();
        }
    }
}

