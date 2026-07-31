/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.block.base.features.personal;

import ic2.core.block.base.features.IWrenchableTile;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;

public interface IPersonalWrenchable
extends IWrenchableTile {
    public boolean canSetFacing(Direction var1, Player var2);

    @Override
    default public boolean canSetFacing(Direction dir) {
        return false;
    }
}

