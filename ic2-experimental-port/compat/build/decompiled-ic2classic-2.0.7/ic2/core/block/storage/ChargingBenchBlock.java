/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.BlockItem
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
package ic2.core.block.storage;

import ic2.core.block.base.IC2ContainerBlock;
import ic2.core.block.base.IStateController;
import ic2.core.block.base.tiles.impls.BaseChargingBenchTileEntity;
import ic2.core.block.machines.BaseMachineBlock;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
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

public class ChargingBenchBlock
extends IC2ContainerBlock
implements IStateController<BaseChargingBenchTileEntity>,
IBlockModel {
    public static final IntegerProperty STATE = IC2Properties.ACTIVE_0_3;
    BlockEntityType<?> type;
    String textureName;

    public ChargingBenchBlock(String blockName, String textureName, BlockEntityType<?> type) {
        super(blockName, BaseMachineBlock.BASE_MACHINE);
        this.type = type;
        this.textureName = textureName;
        this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)STATE, (Comparable)Integer.valueOf(0)));
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this);
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{STATE});
    }

    @Override
    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return this.type.m_155264_(pos, state);
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, BaseChargingBenchTileEntity tile) {
        tile.setState((BlockState)state.m_61124_((Property)STATE, (Comparable)Integer.valueOf(tile.getState())));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2(this.textureName).get(side.m_122434_().m_122478_() ? side.m_122433_() : ChargingBenchBlock.calculateState((Integer)state.m_61143_((Property)STATE)) + "_" + side.m_122433_());
    }

    public static String calculateState(int state) {
        switch (state) {
            case 0: {
                return "empty";
            }
            case 1: {
                return "missing";
            }
            case 2: {
                return "charging";
            }
        }
        return "charged";
    }

    public static int calculateMode(int state) {
        switch (state) {
            case 0: {
                return 3;
            }
            case 1: {
                return 4;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 0;
            }
        }
        return 0;
    }
}

