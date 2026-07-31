/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.reactor;

import ic2.api.util.ILocation;
import net.minecraft.world.item.ItemStack;

public interface IReactor
extends ILocation {
    public int getHeat();

    public void setHeat(int var1);

    public void addHeat(int var1);

    public int getMaxHeat();

    public void setMaxHeat(int var1);

    public float getHeatEffectModifier();

    public void setHeatEffectModifier(float var1);

    public double getEnergyOutput();

    public void addOutput(float var1);

    public ItemStack getStackInReactor(int var1, int var2);

    public void setStackInReactor(int var1, int var2, ItemStack var3);

    public void explode();

    public int getTickRate();

    public boolean isProducingEnergy();
}

