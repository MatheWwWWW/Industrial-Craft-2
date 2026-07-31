/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor.base;

import ic2.api.reactor.IReactor;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.reactor.base.ReactorComponentBase;
import net.minecraft.world.item.ItemStack;

public abstract class ReactorHeatStorageBase
extends ReactorComponentBase {
    public ReactorHeatStorageBase(String name, String textureFolder, String textureName, PropertiesBuilder properties) {
        super(name, textureFolder, textureName, properties);
    }

    @Override
    public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatCalculation, boolean damageTick) {
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack source, int myX, int myY, int sourceX, int sourceY, boolean heatRun, boolean damageTick) {
        return false;
    }

    @Override
    public boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return true;
    }

    @Override
    public int getStoredHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return this.getDamage(stack);
    }

    @Override
    public int getMaxStoredHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return this.getMaxDamage(stack);
    }

    @Override
    public int storeHeat(ItemStack stack, IReactor reactor, int x, int y, int heatChange) {
        int myHeat = this.getDamage(stack);
        if ((myHeat += heatChange) > this.getMaxDamage(stack)) {
            reactor.setStackInReactor(x, y, ItemStack.f_41583_);
            heatChange = this.getMaxDamage(stack) - myHeat + 1;
        } else {
            if (myHeat < 0) {
                heatChange = myHeat;
                myHeat = 0;
            } else {
                heatChange = 0;
            }
            this.setDamage(stack, myHeat);
        }
        return heatChange;
    }

    @Override
    public float getExplosionInfluence(ItemStack stack, IReactor reactor) {
        return 0.0f;
    }

    @Override
    public boolean isValidForReactor(ItemStack stack, IReactor reactor) {
        return true;
    }
}

