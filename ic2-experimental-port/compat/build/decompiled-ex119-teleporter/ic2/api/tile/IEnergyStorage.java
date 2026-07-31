/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 */
package ic2.api.tile;

import net.minecraft.core.Direction;

public interface IEnergyStorage {
    public int getStored();

    public void setStored(int var1);

    public int addEnergy(int var1);

    public int getCapacity();

    public int getOutput();

    public double getOutputEnergyUnitsPerTick();

    public boolean isTeleporterCompatible(Direction var1);
}

