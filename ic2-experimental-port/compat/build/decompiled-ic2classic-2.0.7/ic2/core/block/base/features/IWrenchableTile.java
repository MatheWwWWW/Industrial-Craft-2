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

import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public interface IWrenchableTile {
    public boolean canSetFacing(Direction var1);

    public void setFacing(Direction var1);

    default public boolean doSpecialAction(Direction side, Vec3 hit, Player player) {
        return false;
    }

    default public AABB hasSpecialAction(Direction side, Vec3 hit, Player player) {
        return null;
    }

    public boolean canRemoveBlock(Player var1);

    public double getDropRate(Player var1);

    default public boolean isHarvestWrenchRequired(Player player) {
        return true;
    }
}

