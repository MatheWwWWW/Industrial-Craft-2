/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Material
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.ToolAction
 *  net.minecraftforge.common.ToolActions
 */
package ic2.core.block.resource;

import ic2.api.blocks.PainterHelper;
import ic2.core.block.base.IC2Block;
import ic2.core.block.resource.RubberwoodLogBlock;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IBlockModifiers;
import ic2.core.utils.helpers.Tool;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class RubberwoodBarkBlock
extends IC2Block
implements IBlockModel,
IBlockModifiers {
    private boolean isStripped;

    public RubberwoodBarkBlock(boolean isStripped) {
        super(isStripped ? "rubber_log_barked_stripped" : "rubber_log_barked", BlockBehaviour.Properties.m_60939_((Material)Material.f_76320_).m_60918_(SoundType.f_56736_).m_60913_(1.0f, 5.0f).m_60977_());
        this.setHarvestTool(Tool.AXE);
        this.m_49959_((BlockState)this.m_49966_().m_61124_(RubberwoodLogBlock.AXIS, (Comparable)Direction.Axis.Y));
        this.isStripped = isStripped;
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem(this);
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{RubberwoodLogBlock.AXIS});
    }

    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (toolAction == ToolActions.AXE_STRIP && !this.isStripped && context.m_43722_().canPerformAction(toolAction)) {
            return PainterHelper.copyProperties(state, IC2Blocks.RUBBER_LOG_BARKED_STRIPPED.m_49966_());
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        return (BlockState)this.m_49966_().m_61124_(RubberwoodLogBlock.AXIS, (Comparable)context.m_43719_().m_122434_());
    }

    @Override
    public boolean hasTextureRotation(BlockState state, Direction side) {
        Direction.Axis axis = (Direction.Axis)state.m_61143_(RubberwoodLogBlock.AXIS);
        if (axis == Direction.Axis.Y) {
            return false;
        }
        return axis == Direction.Axis.X || side == Direction.EAST || side == Direction.WEST;
    }

    @Override
    public int getTextureRotation(BlockState state, Direction side) {
        Direction.Axis axis = (Direction.Axis)state.m_61143_(RubberwoodLogBlock.AXIS);
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
        return IC2Textures.getMappedEntriesBlockIC2("resources/rubberwood").get("log_sides" + (this.isStripped ? "_stripped" : ""));
    }

    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 20;
    }

    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 4;
    }
}

