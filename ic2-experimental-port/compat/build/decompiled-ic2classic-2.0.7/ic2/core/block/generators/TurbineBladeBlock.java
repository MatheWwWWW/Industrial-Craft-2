/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.HitResult
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.generators;

import ic2.core.block.base.IC2Block;
import ic2.core.block.machines.BaseMachineBlock;
import ic2.core.block.rendering.block.TurbineBladeModel;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.IRenderType;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.helpers.Tool;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TurbineBladeBlock
extends IC2Block
implements IBlockModel,
ICustomBlockModel,
IRenderType {
    public static final BooleanProperty ACTIVE = IC2Properties.ACTIVE;
    public static final DirectionProperty FACINGS = IC2Properties.ALL_FACINGS;
    public static final IntegerProperty FORMED = IC2Properties.FORMED_STATE3X3_FLAT;

    public TurbineBladeBlock() {
        super("turbine_block", BaseMachineBlock.BASE_MACHINE);
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
    }

    public static BlockState createState(BlockState state, boolean active, Direction facing, int index) {
        return (BlockState)((BlockState)((BlockState)state.m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(active))).m_61124_((Property)FACINGS, (Comparable)facing)).m_61124_((Property)FORMED, (Comparable)Integer.valueOf(index));
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{ACTIVE}).m_61104_(new Property[]{FACINGS}).m_61104_(new Property[]{FORMED});
    }

    public void m_49811_(CreativeModeTab p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem(this);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public RenderType getType() {
        return RenderType.m_110463_();
    }

    @Override
    public ItemStack createDrop(BlockState state, ItemStack tool, RandomSource rand, BlockEntity tile) {
        return new ItemStack((ItemLike)IC2Blocks.MACHINE_BLOCK_PLATED);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return new ItemStack((ItemLike)IC2Blocks.MACHINE_BLOCK_PLATED);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return new TurbineBladeModel(state, this, IC2Textures.getMappedEntriesBlockIC2("electric/generator/steam_tunnel").get((Boolean)state.m_61143_((Property)ACTIVE) != false ? "active_overlay" : "inactive_overlay"));
    }

    @Override
    public List<BlockState> getModelStates() {
        return Collections.emptyList();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2("misc/machine_block_plated").get(side.m_122433_());
    }

    public BlockState m_6843_(BlockState state, Rotation rotation) {
        return (BlockState)state.m_61124_((Property)FACINGS, (Comparable)rotation.m_55954_((Direction)state.m_61143_((Property)FACINGS)));
    }

    public BlockState m_6943_(BlockState state, Mirror mirror) {
        return (BlockState)state.m_61124_((Property)FACINGS, (Comparable)mirror.m_54848_((Direction)state.m_61143_((Property)FACINGS)));
    }
}

