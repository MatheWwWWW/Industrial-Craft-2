/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.misc.explosive;

import ic2.core.block.misc.base.IC2ExplosiveBlock;
import ic2.core.entity.explosion.IC2ExplosiveEntity;
import ic2.core.entity.explosion.ITNTEntity;
import ic2.core.platform.registries.IC2Entities;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ITNTBlock
extends IC2ExplosiveBlock {
    public ITNTBlock() {
        super("itnt", "itnt", "industrial_tnt_", true);
    }

    @Override
    public IC2ExplosiveEntity getExplosiveEntity(Level world, double x, double y, double z, BlockState state, boolean explosion) {
        return new ITNTEntity(IC2Entities.ITNT, world, x, y, z);
    }
}

