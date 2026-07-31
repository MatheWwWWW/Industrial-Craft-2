/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Nameable
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.StairBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
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
import ic2.core.block.rendering.block.base.StairsModel;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.plugins.IRegistryProvider;
import java.util.ArrayList;
import java.util.List;
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
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.GameData;

public class IC2StairsBlock
extends StairBlock
implements ICustomBlockModel,
IAutoCreator,
PainterHelper.IPaintable,
IRegistryProvider {
    private String textureName;
    private String textureFolder;
    private Block material;
    private DyeableMap colorMap;
    private DyeColor color;
    private ResourceLocation id;

    public IC2StairsBlock(String name, Block material, String textureFolder, String textureName) {
        super(material.m_49966_(), BlockBehaviour.Properties.m_60926_((BlockBehaviour)material));
        this.id = GameData.checkPrefix((String)name, (boolean)false);
        this.textureName = textureName;
        this.textureFolder = textureFolder;
        this.material = material;
    }

    public IC2StairsBlock(String name, Block material, BlockBehaviour.Properties props, String textureFolder, String textureName) {
        super(material.m_49966_(), props);
        this.id = GameData.checkPrefix((String)name, (boolean)false);
        this.textureName = textureName;
        this.textureFolder = textureFolder;
        this.material = material;
    }

    public IC2StairsBlock(DyeableBlock material, String name, String texture, DyeableMap colorMap) {
        this(name + material.getColor().m_41065_(), material, texture, material.getColor().m_41065_());
        this.color = material.getColor();
        this.colorMap = colorMap;
        colorMap.addBlock((Block)this, this.color);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    public static IC2StairsBlock createCFoamStairs(DyeableBlock material, DyeableMap colorMap) {
        return new IC2LavaStairBlock(material, "cfoam_stairs_", "cfoam/normal", colorMap);
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

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return new StairsModel(state, this.textureFolder, this.textureName);
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this, new Item.Properties().m_41491_(Objects.requireNonNull(this.material.m_5456_().m_41471_())));
    }

    public static class IC2LavaStairBlock
    extends IC2StairsBlock
    implements IDualLogged,
    IToolProvider {
        public static final BooleanProperty LAVA = IC2Properties.LAVA_LOGGED;
        Tool tool = Tool.PICKAXE.withLevel(1);

        public IC2LavaStairBlock(String name, Block material, String textureFolder, String textureName) {
            super(name, material, textureFolder, textureName);
            this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(false)));
        }

        public IC2LavaStairBlock(String name, Block material, BlockBehaviour.Properties props, String textureFolder, String textureName) {
            super(name, material, props, textureFolder, textureName);
            this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(false)));
        }

        public IC2LavaStairBlock(DyeableBlock material, String name, String texture, DyeableMap colorMap) {
            super(material, name, texture, colorMap);
            this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(false)));
        }

        public IC2LavaStairBlock withTool(Tool tool) {
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

        public FluidState m_5888_(BlockState state) {
            return IDualLogged.getFluidState(state);
        }

        public BlockState m_5573_(BlockPlaceContext context) {
            BlockState state = super.m_5573_(context);
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

