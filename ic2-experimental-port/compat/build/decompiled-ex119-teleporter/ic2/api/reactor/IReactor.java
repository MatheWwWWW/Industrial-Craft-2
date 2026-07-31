/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.api.reactor;

import ic2.api.info.ILocatable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IReactor
extends ILocatable {
    public BlockEntity getCoreTe();

    public int getHeat();

    public void setHeat(int var1);

    public int addHeat(int var1);

    public int getMaxHeat();

    public void setMaxHeat(int var1);

    public void addEmitHeat(int var1);

    public float getHeatEffectModifier();

    public void setHeatEffectModifier(float var1);

    public float getReactorEnergyOutput();

    public double getReactorEUEnergyOutput();

    public float addOutput(float var1);

    public ItemStack getItemAt(int var1, int var2);

    public void setItemAt(int var1, int var2, ItemStack var3);

    public void explode();

    public int getTickRate();

    public boolean produceEnergy();

    public boolean isFluidCooled();
}

