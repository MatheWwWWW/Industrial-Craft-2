/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.block.wiring;

import ic2.core.block.wiring.AbstractCableBlock;
import ic2.core.block.wiring.CableType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public abstract class AbstractSplitterCableBlock
extends AbstractCableBlock {
    public static final BooleanProperty active = BooleanProperty.m_61465_((String)"active");

    protected AbstractSplitterCableBlock(BlockBehaviour.Properties properties, CableType cableType, int n) {
        super(properties, cableType, n);
        this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)active, (Comparable)Boolean.valueOf(false)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{active});
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext blockPlaceContext) {
        return (BlockState)super.m_5573_(blockPlaceContext).m_61124_((Property)active, (Comparable)Boolean.valueOf(blockPlaceContext.m_43725_().m_46753_(blockPlaceContext.m_8083_())));
    }

    public void m_6861_(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (level.f_46443_) {
            return;
        }
        boolean bl2 = level.m_46753_(blockPos);
        if ((Boolean)blockState.m_61143_((Property)active) == bl2) {
            return;
        }
        blockState = (BlockState)blockState.m_61124_((Property)active, (Comparable)Boolean.valueOf(bl2));
        level.m_46597_(blockPos, blockState);
        if (bl2) {
            this.addToEnet(blockState, level, blockPos, true);
        } else {
            this.removeFromEnet(blockState, level, blockPos);
        }
    }

    @Override
    protected void addToEnet(BlockState blockState, Level level, BlockPos blockPos, boolean bl) {
        if (((Boolean)blockState.m_61143_((Property)active)).booleanValue()) {
            super.addToEnet(blockState, level, blockPos, bl);
        }
    }
}

