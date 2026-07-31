/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
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
package ic2.core.block.generators;

import ic2.core.block.base.blocks.BaseFacingBlock;
import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.generators.tiles.ThermalGeneratorTileEntity;
import ic2.core.block.machines.BaseMachineBlock;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.providers.EmptyProvider;
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

public class ThermalGeneratorBlock
extends BaseFacingBlock<ThermalGeneratorTileEntity> {
    public static final IntegerProperty ACTIVE = IC2Properties.ACTIVE_0_2;

    public ThermalGeneratorBlock(BlockEntityType<? extends BlockEntity> tile) {
        super("thermal_generator", BaseMachineBlock.BASE_MACHINE, EmptyProvider.INSTANCE, tile);
        this.setDropProvider(IBlockDropProvider.SELF_OR_GENERATOR);
    }

    @Override
    protected void setDefaultState() {
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ACTIVE, (Comparable)Integer.valueOf(0)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{ACTIVE});
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2("electric/generator/thermal").get(this.getState((Integer)state.m_61143_((Property)ACTIVE)) + "_" + side.m_122433_());
    }

    private String getState(int state) {
        switch (state) {
            case 0: {
                return "passive";
            }
            case 1: {
                return "inactive";
            }
        }
        return "active";
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, ThermalGeneratorTileEntity tile) {
        tile.setState((BlockState)((BlockState)state.m_61124_((Property)FACING, (Comparable)tile.getFacing())).m_61124_((Property)ACTIVE, (Comparable)Integer.valueOf(tile.isThermalEnergy() ? 0 : (tile.isActive() ? 2 : 1))));
    }
}

