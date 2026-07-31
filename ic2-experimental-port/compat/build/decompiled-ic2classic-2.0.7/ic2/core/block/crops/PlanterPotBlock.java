/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.crops;

import ic2.core.block.base.IC2ContainerBlock;
import ic2.core.block.base.misc.IDualLogged;
import ic2.core.block.crops.CropRegistry;
import ic2.core.block.crops.PlanterTileEntity;
import ic2.core.block.rendering.block.PlanterPotModel;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.helpers.StackUtil;
import java.util.Map;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class PlanterPotBlock
extends IC2ContainerBlock
implements ICustomBlockModel,
IDualLogged {
    static final VoxelShape SHAPE = Shapes.m_83124_((VoxelShape)Block.m_49796_((double)2.0, (double)0.0, (double)2.0, (double)14.0, (double)4.0, (double)14.0), (VoxelShape[])new VoxelShape[]{Block.m_49796_((double)1.0, (double)4.0, (double)1.0, (double)15.0, (double)9.0, (double)15.0), Block.m_49796_((double)0.0, (double)9.0, (double)0.0, (double)16.0, (double)15.0, (double)16.0), Block.m_49796_((double)0.0, (double)15.0, (double)0.0, (double)16.0, (double)16.0, (double)1.0), Block.m_49796_((double)0.0, (double)15.0, (double)15.0, (double)16.0, (double)16.0, (double)16.0), Block.m_49796_((double)0.0, (double)15.0, (double)1.0, (double)1.0, (double)16.0, (double)15.0), Block.m_49796_((double)15.0, (double)15.0, (double)1.0, (double)16.0, (double)16.0, (double)15.0)});
    public static final BooleanProperty WATER = BlockStateProperties.f_61362_;
    public static final BooleanProperty LAVA = IC2Properties.LAVA_LOGGED;

    public PlanterPotBlock() {
        super("planter_pot", BlockBehaviour.Properties.m_60939_((Material)Material.f_76278_));
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)WATER, (Comparable)Boolean.valueOf(false))).m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(false)));
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this);
    }

    @Override
    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return IC2Tiles.PLANTER_POT.m_155264_(pos, state);
    }

    public void m_6402_(Level world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
        BlockEntity tile;
        Block block = PlanterPotBlock.getId(stack);
        if (block != null && block != Blocks.f_50016_ && (tile = world.m_7702_(pos)) instanceof PlanterTileEntity) {
            ((PlanterTileEntity)tile).setBlock(block);
        }
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        super.m_7926_(p_206840_1_);
        p_206840_1_.m_61104_(new Property[]{WATER}).m_61104_(new Property[]{LAVA});
    }

    public FluidState m_5888_(BlockState state) {
        return IDualLogged.getFluidState(state);
    }

    @Override
    public void m_6861_(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (((Boolean)state.m_61143_((Property)WATER)).booleanValue()) {
            world.m_186469_(pos, (Fluid)Fluids.f_76193_, Fluids.f_76193_.m_6718_((LevelReader)world));
        } else if (((Boolean)state.m_61143_((Property)LAVA)).booleanValue()) {
            world.m_186469_(pos, (Fluid)Fluids.f_76195_, Fluids.f_76195_.m_6718_((LevelReader)world));
        }
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        FluidState state = context.m_43725_().m_6425_(context.m_8083_());
        return (BlockState)((BlockState)super.m_5573_(context).m_61124_((Property)WATER, (Comparable)Boolean.valueOf(state.m_76152_() == Fluids.f_76193_))).m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(state.m_76152_() == Fluids.f_76195_));
    }

    @Override
    public ItemStack createDrop(BlockState state, ItemStack stack, RandomSource rand, BlockEntity tile, boolean wrench) {
        ItemStack result = new ItemStack((ItemLike)this);
        if (tile instanceof PlanterTileEntity) {
            PlanterPotBlock.setId(result, ((PlanterTileEntity)tile).block);
        }
        return result;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof PlanterTileEntity) {
            ItemStack result = new ItemStack((ItemLike)this);
            PlanterPotBlock.setId(result, ((PlanterTileEntity)tile).block);
            return result;
        }
        return super.getCloneItemStack(state, target, world, pos, player);
    }

    public void m_49811_(CreativeModeTab p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
        for (Block block : CropRegistry.REGISTRY.getAllFarmlands()) {
            ItemStack stack = new ItemStack((ItemLike)this);
            PlanterPotBlock.setId(stack, block);
            p_149666_2_.add((Object)stack);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        TextureAtlasSprite[] sprite = new TextureAtlasSprite[6];
        Map<String, TextureAtlasSprite> sprites = IC2Textures.getMappedEntriesBlockIC2("crops/planter_pot");
        for (Direction dir : Direction.values()) {
            sprite[dir.m_122411_()] = sprites.get(dir.m_122433_());
        }
        return new PlanterPotModel(sprite);
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public static Block getId(ItemStack stack) {
        CompoundTag data = StackUtil.getNbtData(stack);
        return data.m_128441_("id") ? (Block)ForgeRegistries.BLOCKS.getValue(ResourceLocation.m_135820_((String)data.m_128461_("id"))) : Blocks.f_50016_;
    }

    public static void setId(ItemStack stack, Block block) {
        stack.m_41784_().m_128359_("id", ForgeRegistries.BLOCKS.getKey((Object)block).toString());
    }
}

