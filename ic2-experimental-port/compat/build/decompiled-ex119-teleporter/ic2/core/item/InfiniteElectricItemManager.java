/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item;

import ic2.api.item.IElectricItemManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class InfiniteElectricItemManager
implements IElectricItemManager {
    @Override
    public double charge(ItemStack itemStack, double d, int n, boolean bl, boolean bl2) {
        return d;
    }

    @Override
    public double discharge(ItemStack itemStack, double d, int n, boolean bl, boolean bl2, boolean bl3) {
        return d;
    }

    @Override
    public double getCharge(ItemStack itemStack) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getStackCharge(ItemStack itemStack) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public boolean canUse(ItemStack itemStack, double d) {
        return true;
    }

    @Override
    public boolean use(ItemStack itemStack, double d, LivingEntity livingEntity) {
        return true;
    }

    @Override
    public void chargeFromArmor(ItemStack itemStack, LivingEntity livingEntity) {
    }

    @Override
    public String getToolTip(ItemStack itemStack) {
        return "infinite EU";
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return Integer.MAX_VALUE;
    }
}

