/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.block.wiring;

import ic2.core.block.wiring.AbstractCableBlock;
import ic2.core.block.wiring.CableFoam;
import ic2.core.block.wiring.CableType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class FoamCableBlock
extends AbstractCableBlock {
    public static final CableFoam DEFAULT_FOAM = CableFoam.SOFT;

    public static FoamCableBlock create(BlockBehaviour.Properties properties, CableType cableType, int n) {
        FoamCableBlock.prepareCreate(cableType, n);
        return new FoamCableBlock(properties, cableType, n);
    }

    protected FoamCableBlock(BlockBehaviour.Properties properties, CableType cableType, int n) {
        super(properties, cableType, n);
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

