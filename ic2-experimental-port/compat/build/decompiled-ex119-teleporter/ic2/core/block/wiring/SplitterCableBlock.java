/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.wiring;

import ic2.core.block.wiring.AbstractSplitterCableBlock;
import ic2.core.block.wiring.CableType;
import ic2.core.block.wiring.SplitterFoamCableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class SplitterCableBlock
extends AbstractSplitterCableBlock {
    private final SplitterFoamCableBlock foamCableBlock;

    public static SplitterCableBlock create(BlockBehaviour.Properties properties, SplitterFoamCableBlock splitterFoamCableBlock) {
        SplitterCableBlock.prepareCreate(CableType.splitter, 0);
        return new SplitterCableBlock(properties, splitterFoamCableBlock);
    }

    protected SplitterCableBlock(BlockBehaviour.Properties properties, SplitterFoamCableBlock splitterFoamCableBlock) {
        super(properties, CableType.splitter, 0);
        this.foamCableBlock = splitterFoamCableBlock;
    }

    @Override
    public boolean isFoam() {
        return false;
    }

    @Override
    public boolean isHardFoam(BlockState blockState) {
        return false;
    }

    public SplitterFoamCableBlock getFoamCableBlock() {
        return this.foamCableBlock;
    }
}

