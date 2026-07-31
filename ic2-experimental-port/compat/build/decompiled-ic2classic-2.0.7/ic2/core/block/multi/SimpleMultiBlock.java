/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.multi;

import ic2.core.block.base.tiles.BaseLinkingTileEntity;
import ic2.core.block.multi.BaseMultiBlock;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.platform.rendering.features.block.IBlockModifiers;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.math.ConnectionState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SimpleMultiBlock
extends BaseMultiBlock
implements IBlockModifiers {
    public static final IntegerProperty STATE = IC2Properties.FORMED_STATE2X2;
    public static final float[][][] UVS = IC2Properties.UVS_2_4[0];
    ITextureProvider provider;

    public SimpleMultiBlock(String name, ITextureProvider unformedTexture, ITextureProvider formedTexture, BlockEntityType<? extends BlockEntity> tile) {
        super(name, unformedTexture, tile);
        this.provider = formedTexture;
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
    }

    public static BlockState buildState(Block block, Direction facing, int index) {
        return (BlockState)((BlockState)((BlockState)block.m_49966_().m_61124_((Property)FACING, (Comparable)facing)).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(false))).m_61124_((Property)STATE, (Comparable)Integer.valueOf(index));
    }

    public static BlockPos buildPos(int index, Direction facing) {
        return new BlockPos.MutableBlockPos().m_122175_(facing.m_122427_(), 1 - index % 2).m_122175_(facing, 1 - index / 2 % 2).m_122175_(Direction.DOWN, index / 4).m_7949_();
    }

    @Override
    protected void setDefaultState() {
        this.m_49959_((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(false))).m_61124_((Property)STATE, (Comparable)Integer.valueOf(0)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{STATE});
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, BaseLinkingTileEntity tile) {
        tile.setState((BlockState)((BlockState)((BlockState)state.m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(tile.isActive()))).m_61124_((Property)FACING, (Comparable)tile.getFacing())).m_61124_((Property)STATE, (Comparable)Integer.valueOf(tile.getState())));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return (Integer)state.m_61143_((Property)STATE) > 0 ? this.provider.getTexture(state, side) : super.getSpriteForState(state, side);
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
        return (Integer)state.m_61143_((Property)STATE) > 0;
    }

    @Override
    public float[] getCustomTextureUVs(BlockState state, Direction side) {
        return ConnectionState.createQuads(UVS, side, (Integer)state.m_61143_((Property)STATE) + 1, 2, 2, 2);
    }
}

