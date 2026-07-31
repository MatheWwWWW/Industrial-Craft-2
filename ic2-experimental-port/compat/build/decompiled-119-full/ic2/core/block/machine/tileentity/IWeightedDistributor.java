/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.Container
 */
package ic2.core.block.machine.tileentity;

import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;

public interface IWeightedDistributor
extends Container {
    public Direction getFacing();

    public List<Direction> getPriority();

    public void updatePriority(boolean var1);
}

