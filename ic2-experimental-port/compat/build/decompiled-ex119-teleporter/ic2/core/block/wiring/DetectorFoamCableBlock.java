/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.block.wiring;

import ic2.core.block.wiring.AbstractDetectorCableBlock;
import ic2.core.block.wiring.CableFoam;
import ic2.core.block.wiring.CableType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class DetectorFoamCableBlock
extends AbstractDetectorCableBlock {
    public static DetectorFoamCableBlock create(BlockBehaviour.Properties properties) {
        DetectorFoamCableBlock.prepareCreate(CableType.detector, 0);
        return new DetectorFoamCableBlock(properties);
    }

    protected DetectorFoamCableBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoam() {
        return true;
    }

    @Override
    public boolean isHardFoam(BlockState blockState) {
        return ((CableFoam)((Object)blockState.m_61143_((Property)foamProperty))).isHard();
    }
}

