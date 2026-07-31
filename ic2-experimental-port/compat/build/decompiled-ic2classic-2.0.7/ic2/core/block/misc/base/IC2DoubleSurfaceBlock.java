/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.NonNullList
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc.base;

import ic2.api.blocks.DyeableMap;
import ic2.core.block.base.IC2Block;
import ic2.core.block.base.misc.IDualLogged;
import ic2.core.block.misc.base.IC2SurfaceBlock;
import ic2.core.block.rendering.block.base.DoubleSurfaceModel;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.helpers.Tool;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IC2DoubleSurfaceBlock
extends IC2Block
implements ICustomBlockModel,
IDualLogged {
    public static final EnumProperty<DyeColor> MAIN_COLOR = IC2Properties.MAIN_COLOR;
    public static final EnumProperty<DyeColor> SECOND_COLOR = IC2Properties.SECOND_COLOR;
    public static final BooleanProperty WATER = BlockStateProperties.f_61362_;
    public static final BooleanProperty LAVA = IC2Properties.LAVA_LOGGED;
    private static final VoxelShape SHAPE = Shapes.m_83110_((VoxelShape)Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)2.0, (double)16.0), (VoxelShape)Block.m_49796_((double)0.0, (double)14.0, (double)0.0, (double)16.0, (double)16.0, (double)16.0));
    protected DyeableMap colorMap;
    protected String path;

    public IC2DoubleSurfaceBlock(String blockName, String path, BlockBehaviour.Properties properties, DyeableMap colorMap) {
        super(blockName, properties);
        this.colorMap = colorMap;
        this.path = path;
        for (Block block : colorMap.getBlocks()) {
            if (!(block instanceof IC2SurfaceBlock)) continue;
            ((IC2SurfaceBlock)block).setDouble(this);
        }
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)WATER, (Comparable)Boolean.valueOf(false))).m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(false)));
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{MAIN_COLOR}).m_61104_(new Property[]{SECOND_COLOR}).m_61104_(new Property[]{WATER}).m_61104_(new Property[]{LAVA});
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

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        Map<String, TextureAtlasSprite> sprite = IC2Textures.getMappedEntriesBlockIC2(this.path);
        return new DoubleSurfaceModel(sprite.get(((DyeColor)state.m_61143_(MAIN_COLOR)).m_41065_()), sprite.get(((DyeColor)state.m_61143_(SECOND_COLOR)).m_41065_()));
    }

    @Override
    public ItemStack createDrop(BlockState state, ItemStack tool, RandomSource rand, BlockEntity tile) {
        return ItemStack.f_41583_;
    }

    @Override
    public void addExtraDrops(List<ItemStack> list, BlockState state, ItemStack tool, RandomSource rand) {
        list.add(new ItemStack((ItemLike)this.colorMap.getBlock((DyeColor)state.m_61143_(MAIN_COLOR))));
        list.add(new ItemStack((ItemLike)this.colorMap.getBlock((DyeColor)state.m_61143_(SECOND_COLOR))));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        if (target.m_6662_() != HitResult.Type.BLOCK) {
            return ItemStack.f_41583_;
        }
        BlockHitResult block = (BlockHitResult)target;
        return new ItemStack((ItemLike)this.colorMap.getBlock((DyeColor)state.m_61143_(block.m_82450_().m_7098_() - (double)pos.m_123342_() > 0.5 ? MAIN_COLOR : SECOND_COLOR)));
    }

    public VoxelShape m_5940_(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
        return SHAPE;
    }

    public void m_49811_(CreativeModeTab p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
    }

    @Override
    public BlockItem createItem() {
        return null;
    }
}

