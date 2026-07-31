/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.wiring;

import ic2.core.block.wiring.AbstractCableBlock;
import ic2.core.block.wiring.CableType;
import ic2.core.block.wiring.FoamCableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CableBlock
extends AbstractCableBlock {
    private final FoamCableBlock foamCableBlock;

    public static CableBlock create(BlockBehaviour.Properties properties, CableType cableType, int n, FoamCableBlock foamCableBlock) {
        CableBlock.prepareCreate(cableType, n);
        return new CableBlock(properties, cableType, n, foamCableBlock);
    }

    protected CableBlock(BlockBehaviour.Properties properties, CableType cableType, int n, FoamCableBlock foamCableBlock) {
        super(properties, cableType, n);
        this.foamCableBlock = foamCableBlock;
    }

    @Override
    public boolean isFoam() {
        return false;
    }

    @Override
    public boolean isHardFoam(BlockState blockState) {
        return false;
    }

    public FoamCableBlock getFoamCableBlock() {
        return this.foamCableBlock;
    }
}

