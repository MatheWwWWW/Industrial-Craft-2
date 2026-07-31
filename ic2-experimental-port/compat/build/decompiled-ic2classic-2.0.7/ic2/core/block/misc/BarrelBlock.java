/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Material
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc;

import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.IC2ContainerBlock;
import ic2.core.block.base.IStateController;
import ic2.core.block.misc.tiles.BarrelTileEntity;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.item.base.IC2TexturedBlockItem;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IRotatableBlock;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.tooltips.helper.ITooltipProvider;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BarrelBlock
extends IC2ContainerBlock
implements IBlockModel,
IStateController<BarrelTileEntity>,
IRotatableBlock {
    public static final DirectionProperty FACING = DirectionProperty.m_61543_((String)"facing", DirectionList.HORIZONTAL.toFacings());
    public static final BooleanProperty TREETAB = BooleanProperty.m_61465_((String)"treetab");

    public BarrelBlock() {
        super("barrel", BlockBehaviour.Properties.m_60939_((Material)Material.f_76320_).m_60913_(1.0f, 1.0f).m_60918_(SoundType.f_56736_));
        this.setHarvestTool(Tool.AXE);
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)Direction.NORTH)).m_61124_((Property)TREETAB, (Comparable)Boolean.valueOf(false)));
        this.addTooltip(ITooltipProvider.tooltip("tooltip.block.ic2.barrel.info"));
    }

    @Override
    public List<BlockState> getModelStates() {
        return this.m_49965_().m_61056_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        if (((Boolean)state.m_61143_((Property)TREETAB)).booleanValue() && side == Direction.SOUTH) {
            return IC2Textures.getMappedEntriesBlockIC2("misc/barrel").get("treetab");
        }
        return IC2Textures.getMappedEntriesBlockIC2("misc/barrel").get(side.m_122433_());
    }

    public void m_6402_(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (IC2.PLATFORM.isRendering()) {
            return;
        }
        BlockEntity tile = worldIn.m_7702_(pos);
        if (tile instanceof BarrelTileEntity) {
            ((BarrelTileEntity)tile).loadItemData(stack);
        }
    }

    @Override
    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return IC2Tiles.BARREL.m_155264_(pos, state);
    }

    @Override
    public IC2BlockItem createItem() {
        return new IC2TexturedBlockItem((Block)this, "food_and_drink", "barrel");
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{FACING}).m_61104_(new Property[]{TREETAB});
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, BarrelTileEntity tile) {
        tile.setState((BlockState)((BlockState)state.m_61124_((Property)FACING, (Comparable)tile.getFacing())).m_61124_((Property)TREETAB, (Comparable)Boolean.valueOf(tile.hasTreeTap())));
    }

    public boolean m_7898_(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.m_8055_(pos).m_60734_() == IC2Blocks.SCAFFOLD_WOOD;
    }

    @Override
    public boolean hasRotation(BlockState state) {
        return true;
    }

    @Override
    public Direction getRotation(BlockState state) {
        return (Direction)state.m_61143_((Property)FACING);
    }
}

