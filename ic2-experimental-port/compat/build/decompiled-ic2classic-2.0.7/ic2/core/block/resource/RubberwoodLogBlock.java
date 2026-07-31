/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.RotatedPillarBlock
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.level.material.PushReaction
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.ToolAction
 *  net.minecraftforge.common.ToolActions
 */
package ic2.core.block.resource;

import ic2.api.blocks.PainterHelper;
import ic2.core.block.base.IC2Block;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IBlockModifiers;
import ic2.core.utils.helpers.Tool;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class RubberwoodLogBlock
extends IC2Block
implements IBlockModifiers,
IBlockModel {
    public static final DirectionProperty RESIN_FACING = IC2Properties.HORIZONTAL_FACINGS;
    public static final BooleanProperty RESIN = BooleanProperty.m_61465_((String)"resin");
    public static final BooleanProperty COLLECTABLE = BooleanProperty.m_61465_((String)"collectable");
    public static final EnumProperty<Direction.Axis> AXIS = RotatedPillarBlock.f_55923_;
    private boolean isStripped;

    public RubberwoodLogBlock(boolean isStripped) {
        super(isStripped ? "rubber_wood_stripped" : "rubber_wood", BlockBehaviour.Properties.m_60939_((Material)Material.f_76320_).m_60918_(SoundType.f_56736_).m_60913_(1.0f, 5.0f).m_60977_());
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(AXIS, (Comparable)Direction.Axis.Y)).m_61124_((Property)RESIN, (Comparable)Boolean.valueOf(false))).m_61124_((Property)COLLECTABLE, (Comparable)Boolean.valueOf(false))).m_61124_((Property)RESIN_FACING, (Comparable)Direction.NORTH));
        this.setHarvestTool(Tool.AXE);
        this.isStripped = isStripped;
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem(this);
    }

    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (toolAction == ToolActions.AXE_STRIP && !this.isStripped && context.m_43722_().canPerformAction(toolAction)) {
            return PainterHelper.copyProperties(state, IC2Blocks.RUBBER_LOG_STRIPPED.m_49966_());
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{RESIN_FACING}).m_61104_(new Property[]{RESIN}).m_61104_(new Property[]{COLLECTABLE}).m_61104_(new Property[]{AXIS});
    }

    public void m_6786_(LevelAccessor worldIn, BlockPos pos, BlockState state) {
        if (worldIn.isAreaLoaded(pos, 5)) {
            int i = 4;
            for (BlockPos blockpos : BlockPos.m_121940_((BlockPos)pos.m_7918_(-i, -i, -i), (BlockPos)pos.m_7918_(i, i, i))) {
                BlockState posState = worldIn.m_8055_(blockpos);
                if (!posState.m_204336_(BlockTags.f_13035_)) continue;
                Blocks.f_50016_.m_49966_().m_60701_(worldIn, blockpos, 2);
            }
        }
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        return (BlockState)this.m_49966_().m_61124_(AXIS, (Comparable)context.m_43719_().m_122434_());
    }

    @Override
    public void addExtraDrops(List<ItemStack> list, BlockState state, ItemStack tool, RandomSource rand) {
        int fortune = EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44987_, (ItemStack)tool);
        for (int i = 0; i < 1 + fortune; ++i) {
            if (rand.m_188503_(12) != 0) continue;
            list.add(new ItemStack((ItemLike)IC2Items.STICKY_RESIN));
        }
    }

    public void m_213897_(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        this.update(state, worldIn, pos, random);
    }

    public void m_213898_(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        this.update(state, worldIn, pos, random);
    }

    public void update(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (((Boolean)state.m_61143_((Property)RESIN)).booleanValue() && !((Boolean)state.m_61143_((Property)COLLECTABLE)).booleanValue()) {
            int range;
            int n = range = worldIn.m_8055_(pos.m_121945_((Direction)state.m_61143_((Property)RESIN_FACING))).m_60734_() == IC2Blocks.TREETAP_AND_BUCKET ? 150 : 200;
            if (random.m_188503_(range) == 0) {
                worldIn.m_46597_(pos, (BlockState)state.m_61124_((Property)COLLECTABLE, (Comparable)Boolean.valueOf(true)));
            } else {
                worldIn.m_186460_(pos, (Block)this, range - 100);
            }
        }
    }

    @Override
    public boolean hasTextureRotation(BlockState state, Direction side) {
        Direction.Axis axis = (Direction.Axis)state.m_61143_(AXIS);
        if (axis == Direction.Axis.Y) {
            return false;
        }
        return axis == Direction.Axis.X || side == Direction.EAST || side == Direction.WEST;
    }

    @Override
    public int getTextureRotation(BlockState state, Direction side) {
        Direction.Axis axis = (Direction.Axis)state.m_61143_(AXIS);
        if (axis == Direction.Axis.Y) {
            return 0;
        }
        return axis == Direction.Axis.X ? 90 : (side == Direction.EAST || side == Direction.WEST ? 90 : 0);
    }

    @Override
    public boolean hasCustomTextureUVs(BlockState state, Direction side) {
        return false;
    }

    @Override
    public float[] getCustomTextureUVs(BlockState state, Direction side) {
        return null;
    }

    @Override
    public List<BlockState> getModelStates() {
        return this.m_49965_().m_61056_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        String stripped;
        Direction.Axis blockAxis = (Direction.Axis)state.m_61143_(AXIS);
        String string = stripped = this.isStripped ? "_stripped" : "";
        if (blockAxis != Direction.Axis.Y) {
            if (side.m_122434_() == blockAxis) {
                return IC2Textures.getMappedEntriesBlockIC2("resources/rubberwood").get("log_top" + stripped);
            }
        } else {
            if (side.m_122434_().m_122478_()) {
                return IC2Textures.getMappedEntriesBlockIC2("resources/rubberwood").get("log_top" + stripped);
            }
            if (((Boolean)state.m_61143_((Property)RESIN)).booleanValue() && state.m_61143_((Property)RESIN_FACING) == side) {
                return IC2Textures.getMappedEntriesBlockIC2("resources/rubberwood").get((Boolean)state.m_61143_((Property)COLLECTABLE) != false ? "log_resin_full" + stripped : "log_resin_empty" + stripped);
            }
        }
        return IC2Textures.getMappedEntriesBlockIC2("resources/rubberwood").get("log_sides" + stripped);
    }

    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 20;
    }

    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 4;
    }

    public PushReaction m_5537_(BlockState state) {
        return (Boolean)state.m_61143_((Property)RESIN) != false ? PushReaction.DESTROY : this.f_60442_.m_76338_();
    }
}

