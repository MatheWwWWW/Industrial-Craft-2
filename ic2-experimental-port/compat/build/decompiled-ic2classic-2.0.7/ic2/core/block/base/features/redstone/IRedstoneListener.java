/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.block.base.features.redstone;

import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IRedstoneListener {
    default public boolean allowWeakSignal(Direction dir) {
        BlockEntity tile = (BlockEntity)this;
        return tile.m_58900_().m_60796_((BlockGetter)tile.m_58904_(), tile.m_58899_());
    }

    public boolean canConnectToRedstone(Direction var1);
}

