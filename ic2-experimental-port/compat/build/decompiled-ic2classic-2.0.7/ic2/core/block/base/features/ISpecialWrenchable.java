/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.base.features;

import ic2.core.block.base.features.IWrenchableTile;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public interface ISpecialWrenchable
extends IWrenchableTile {
    @Override
    default public boolean canSetFacing(Direction dir) {
        return false;
    }

    @Override
    default public void setFacing(Direction dir) {
    }

    @Override
    public boolean doSpecialAction(Direction var1, Vec3 var2, Player var3);

    @Override
    public AABB hasSpecialAction(Direction var1, Vec3 var2, Player var3);

    @Override
    default public boolean canRemoveBlock(Player player) {
        return false;
    }

    @Override
    default public double getDropRate(Player player) {
        return 0.0;
    }
}

