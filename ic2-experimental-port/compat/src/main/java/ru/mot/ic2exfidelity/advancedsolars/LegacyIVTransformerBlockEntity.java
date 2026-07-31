package ru.mot.ic2exfidelity.advancedsolars;

import ic2.core.block.wiring.tileentity.TileEntityTransformer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** IC2 Classic IV transformer: 8,192 EU/t low side, 32,768 EU/t high side. */
public final class LegacyIVTransformerBlockEntity extends TileEntityTransformer {
    public LegacyIVTransformerBlockEntity(BlockPos position, BlockState state) {
        super(LegacyAdvancedSolarsPrerequisites.IV_TRANSFORMER_BLOCK_ENTITY.get(),
                position, state, 5);
    }
}
