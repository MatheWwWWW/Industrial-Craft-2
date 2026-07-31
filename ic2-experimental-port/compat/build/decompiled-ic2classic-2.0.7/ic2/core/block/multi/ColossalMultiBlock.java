/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
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
import ic2.core.block.rendering.block.ColossalModel;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.platform.rendering.features.block.IBlockModifiers;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.blocks.SimpleBlockModel;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.math.ConnectionState;
import java.util.Collections;
import java.util.List;
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

public class ColossalMultiBlock
extends BaseMultiBlock
implements IBlockModifiers,
ICustomBlockModel {
    public static final IntegerProperty RANGE = IC2Properties.DYNAMIC_STATE;
    public static final IntegerProperty STATE = IC2Properties.FORMED_STATE4X4;
    public static final float[][][][] UVS = IC2Properties.UVS_2_4;

    public ColossalMultiBlock(String name, ITextureProvider unformedTexture, BlockEntityType<? extends BlockEntity> tile) {
        super(name, unformedTexture, tile);
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
    }

    @Override
    protected void setDefaultState() {
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(false))).m_61124_((Property)STATE, (Comparable)Integer.valueOf(0))).m_61124_((Property)RANGE, (Comparable)Integer.valueOf(0)));
    }

    public static BlockState buildState(Block block, int range, Direction facing, int index) {
        return (BlockState)((BlockState)((BlockState)((BlockState)block.m_49966_().m_61124_((Property)FACING, (Comparable)facing)).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(false))).m_61124_((Property)STATE, (Comparable)Integer.valueOf(index))).m_61124_((Property)RANGE, (Comparable)Integer.valueOf(range));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{STATE}).m_61104_(new Property[]{RANGE});
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, BaseLinkingTileEntity tile) {
        tile.setState((BlockState)((BlockState)((BlockState)((BlockState)state.m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(tile.isActive()))).m_61124_((Property)FACING, (Comparable)tile.getFacing())).m_61124_((Property)STATE, (Comparable)Integer.valueOf(tile.getState() >> 2))).m_61124_((Property)RANGE, (Comparable)Integer.valueOf(tile.getState() & 3)));
    }

    @Override
    public List<BlockState> getModelStates() {
        return Collections.emptyList();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return (Integer)state.m_61143_((Property)STATE) > 0 ? new ColossalModel(state, this) : new SimpleBlockModel(state, this);
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
        int range = (Integer)state.m_61143_((Property)RANGE);
        return ConnectionState.createQuads(UVS[range], side, (Integer)state.m_61143_((Property)STATE) + 1, range + 2, range + 2, range + 2);
    }
}

