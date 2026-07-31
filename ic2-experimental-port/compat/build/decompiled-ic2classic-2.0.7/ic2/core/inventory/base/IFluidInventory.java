/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core.inventory.base;

import net.minecraft.world.level.material.Fluid;

public interface IFluidInventory {
    public int getFluidSlots();

    public void setFluidInSlot(int var1, Fluid var2);

    public boolean canInsert(int var1, Fluid var2);

    public Fluid getFluidInSlot(int var1);
}

