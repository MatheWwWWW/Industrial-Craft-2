/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
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
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SlabBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.block.state.properties.SlabType
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.block.misc.base;

import ic2.api.blocks.DyeableMap;
import ic2.api.blocks.PainterHelper;
import ic2.core.block.base.IAutoCreator;
import ic2.core.block.base.IToolProvider;
import ic2.core.block.base.features.IDropProvider;
import ic2.core.block.base.misc.IDualLogged;
import ic2.core.block.misc.base.DyeableBlock;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IBlockModifiers;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.plugins.IRegistryProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.GameData;

public class IC2SlabBlock
extends SlabBlock
implements IBlockModel,
IAutoCreator,
IBlockModifiers,
PainterHelper.IPaintable,
IRegistryProvider {
    private static final AABB BOTTOM = new AABB(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    private static final AABB TOP = new AABB(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);
    private String textureName;
    private String textureFolder;
    private Block material;
    private DyeableMap colorMap;
    private DyeColor color;
    private ResourceLocation id;

    public IC2SlabBlock(String name, Block material, String textureFolder, String textureName) {
        super(BlockBehaviour.Properties.m_60926_((BlockBehaviour)material));
        this.id = GameData.checkPrefix((String)name, (boolean)false);
        this.textureName = textureName;
        this.textureFolder = textureFolder;
        this.material = material;
        this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)SlabBlock.f_56353_, (Comparable)SlabType.BOTTOM));
    }

    public IC2SlabBlock(String name, Block material, BlockBehaviour.Properties props, String textureFolder, String textureName) {
        super(props);
        this.id = GameData.checkPrefix((String)name, (boolean)false);
        this.textureName = textureName;
        this.textureFolder = textureFolder;
        this.material = material;
        this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)SlabBlock.f_56353_, (Comparable)SlabType.BOTTOM));
    }

    public IC2SlabBlock(DyeableBlock material, String name, String texture, DyeableMap colorMap) {
        this(name + material.getColor().m_41065_(), material, texture, material.getColor().m_41065_());
        this.color = material.getColor();
        this.colorMap = colorMap;
        colorMap.addBlock((Block)this, this.color);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    public static IC2SlabBlock createCFoamSlab(DyeableBlock block, DyeableMap colorMap) {
        return new IC2LavaSlabBlock(block, "cfoam_slab_", "cfoam/normal", colorMap);
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this, new Item.Properties().m_41491_(Objects.requireNonNull(this.material.m_5456_().m_41471_())));
    }

    @Override
    public List<BlockState> getModelStates() {
        return this.m_49965_().m_61056_();
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
    public AABB getModelBounds(BlockState state) {
        switch ((SlabType)state.m_61143_((Property)SlabBlock.f_56353_)) {
            case BOTTOM: {
                return BOTTOM;
            }
            case TOP: {
                return TOP;
            }
            case DOUBLE: {
                return DEFAULT_BOUNDS;
            }
        }
        return DEFAULT_BOUNDS;
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return state.m_61143_((Property)SlabBlock.f_56353_) == SlabType.DOUBLE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2(this.textureFolder).get(this.textureName);
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
        return side.m_122434_().m_122479_() && state.m_61143_((Property)SlabBlock.f_56353_) != SlabType.DOUBLE;
    }

    @Override
    public float[] getCustomTextureUVs(BlockState state, Direction side) {
        float[] fArray;
        if (state.m_61143_((Property)SlabBlock.f_56353_) == SlabType.TOP) {
            float[] fArray2 = new float[4];
            fArray2[0] = 0.0f;
            fArray2[1] = 8.0f;
            fArray2[2] = 16.0f;
            fArray = fArray2;
            fArray2[3] = 16.0f;
        } else {
            float[] fArray3 = new float[4];
            fArray3[0] = 0.0f;
            fArray3[1] = 0.0f;
            fArray3[2] = 16.0f;
            fArray = fArray3;
            fArray3[3] = 8.0f;
        }
        return fArray;
    }

    public List<ItemStack> m_7381_(BlockState state, LootContext.Builder builder) {
        LootTable loottable = builder.m_78962_().m_7654_().m_129898_().m_79217_(this.m_60589_());
        if (loottable != LootTable.f_79105_) {
            return loottable.m_230922_(builder.m_78972_(LootContextParams.f_81461_, (Object)state).m_78975_(LootContextParamSets.f_81421_));
        }
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        BlockEntity tile = (BlockEntity)builder.m_78982_(LootContextParams.f_81462_);
        ItemStack stack = new ItemStack((ItemLike)this, state.m_61143_((Property)SlabBlock.f_56353_) == SlabType.DOUBLE ? 2 : 1);
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

    public static class IC2LavaSlabBlock
    extends IC2SlabBlock
    implements IDualLogged,
    IToolProvider {
        Tool tool = Tool.PICKAXE.withLevel(1);
        public static final BooleanProperty LAVA = IC2Properties.LAVA_LOGGED;

        public IC2LavaSlabBlock(String name, Block material, String textureFolder, String textureName) {
            super(name, material, textureFolder, textureName);
        }

        public IC2LavaSlabBlock(String name, Block material, BlockBehaviour.Properties props, String textureFolder, String textureName) {
            super(name, material, props, textureFolder, textureName);
        }

        public IC2LavaSlabBlock(DyeableBlock material, String name, String texture, DyeableMap colorMap) {
            super(material, name, texture, colorMap);
        }

        public IC2LavaSlabBlock withTool(Tool tool) {
            this.tool = tool;
            return this;
        }

        @Override
        public void registerTools() {
            this.tool.register((Block)this);
        }

        protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
            super.m_7926_(builder);
            builder.m_61104_(new Property[]{LAVA});
        }

        @Override
        public boolean m_6044_(BlockGetter p_204510_1_, BlockPos p_204510_2_, BlockState p_204510_3_, Fluid p_204510_4_) {
            return p_204510_3_.m_61143_((Property)f_56353_) != SlabType.DOUBLE && IDualLogged.super.m_6044_(p_204510_1_, p_204510_2_, p_204510_3_, p_204510_4_);
        }

        @Override
        public boolean m_7361_(LevelAccessor p_204509_1_, BlockPos p_204509_2_, BlockState p_204509_3_, FluidState p_204509_4_) {
            return IDualLogged.super.m_7361_(p_204509_1_, p_204509_2_, p_204509_3_, p_204509_4_);
        }

        public FluidState m_5888_(BlockState state) {
            return IDualLogged.getFluidState(state);
        }

        public BlockState m_5573_(BlockPlaceContext context) {
            BlockState state = super.m_5573_(context);
            if (state.m_61143_((Property)SlabBlock.f_56353_) == SlabType.DOUBLE) {
                return state;
            }
            FluidState fluid = context.m_43725_().m_6425_(context.m_8083_());
            return (BlockState)state.m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(fluid.m_76152_() == Fluids.f_76195_));
        }

        public void m_6861_(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
            super.m_6861_(state, world, pos, block, fromPos, isMoving);
            if (((Boolean)state.m_61143_((Property)LAVA)).booleanValue()) {
                world.m_186469_(pos, (Fluid)Fluids.f_76195_, Fluids.f_76195_.m_6718_((LevelReader)world));
            }
        }
    }
}

