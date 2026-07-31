/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.wiring;

import ic2.core.block.wiring.AbstractDetectorCableBlock;
import ic2.core.block.wiring.CableType;
import ic2.core.block.wiring.DetectorFoamCableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class DetectorCableBlock
extends AbstractDetectorCableBlock {
    private final DetectorFoamCableBlock foamCableBlock;

    public static DetectorCableBlock create(BlockBehaviour.Properties properties, DetectorFoamCableBlock detectorFoamCableBlock) {
        DetectorCableBlock.prepareCreate(CableType.detector, 0);
        return new DetectorCableBlock(properties, detectorFoamCableBlock);
    }

    protected DetectorCableBlock(BlockBehaviour.Properties properties, DetectorFoamCableBlock detectorFoamCableBlock) {
        super(properties);
        this.foamCableBlock = detectorFoamCableBlock;
    }

    @Override
    public boolean isFoam() {
        return false;
    }

    @Override
    public boolean isHardFoam(BlockState blockState) {
        return false;
    }

    public DetectorFoamCableBlock getFoamCableBlock() {
        return this.foamCableBlock;
    }
}

