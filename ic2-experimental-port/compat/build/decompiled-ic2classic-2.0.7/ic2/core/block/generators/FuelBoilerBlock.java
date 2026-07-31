/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.block.generators;

import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.base.tiles.BaseMultiBlockTileEntity;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.generators.BaseGeneratorBlock;
import ic2.core.block.multi.SimpleMultiBlock;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.platform.rendering.features.block.IBlockModifiers;
import ic2.core.utils.math.ConnectionState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class FuelBoilerBlock
extends BaseGeneratorBlock
implements IBlockModifiers {
    public static final BooleanProperty FORMED = IC2Properties.STRUCTURE_FORMED;

    public FuelBoilerBlock() {
        super("fuel_boiler", IBlockDropProvider.SELF_OR_GENERATOR, ITextureProvider.toggleIC2("electric/generator/fuel_boiler"), IC2Tiles.GENERATOR_FUEL_BOILER);
        this.setOverrideRarity(Rarity.UNCOMMON);
    }

    @Override
    protected void setDefaultState() {
        this.m_49959_((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(false))).m_61124_((Property)FORMED, (Comparable)Boolean.valueOf(false)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{FORMED});
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, BaseTileEntity tile) {
        tile.setState((BlockState)((BlockState)((BlockState)state.m_61124_((Property)FACING, (Comparable)tile.getFacing())).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(tile.isActive()))).m_61124_((Property)FORMED, (Comparable)Boolean.valueOf(((BaseMultiBlockTileEntity)tile).isValid)));
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
        return (Boolean)state.m_61143_((Property)FORMED);
    }

    @Override
    public float[] getCustomTextureUVs(BlockState state, Direction side) {
        return ConnectionState.createQuads(SimpleMultiBlock.UVS, side, 1, 2, 2, 2);
    }
}

