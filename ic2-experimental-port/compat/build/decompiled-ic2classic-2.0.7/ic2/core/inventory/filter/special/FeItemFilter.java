/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.energy.IEnergyStorage
 */
package ic2.core.inventory.filter.special;

import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.InvertedFilter;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;

public class FeItemFilter
implements IFilter {
    public static final IFilter CHARGE_FILTER = new FeItemFilter();
    public static final IFilter NOT_CHARGE_FILTER = new InvertedFilter(CHARGE_FILTER);

    @Override
    public boolean matches(ItemStack input) {
        if (input.m_41619_()) {
            return false;
        }
        IEnergyStorage cap = (IEnergyStorage)input.getCapability(ForgeCapabilities.ENERGY).orElse(null);
        return cap != null && cap.canReceive() && cap.getEnergyStored() < cap.getMaxEnergyStored();
    }
}

