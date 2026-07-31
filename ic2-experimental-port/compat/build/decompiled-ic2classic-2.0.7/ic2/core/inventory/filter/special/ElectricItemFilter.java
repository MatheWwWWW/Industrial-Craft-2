/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 */
package ic2.core.inventory.filter.special;

import ic2.api.items.electric.ElectricItem;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.InvertedFilter;
import ic2.core.item.manager.GatewayElectricItemManager;
import ic2.core.platform.registries.IC2Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ElectricItemFilter
implements IFilter {
    public static final IFilter CHARGE_FILTER = new ElectricItemFilter(true, false);
    public static final IFilter NOT_CHARGE_FILTER = new InvertedFilter(CHARGE_FILTER);
    public static final IFilter DISCHARGE_INTERNAL_FILTER = new ElectricItemFilter(false, true);
    public static final IFilter DISCHARGE_EXTERNAL_FILTER = new ElectricItemFilter(false, false);
    public static final IFilter NOT_DISCHARGE_FILTER = new InvertedFilter(DISCHARGE_EXTERNAL_FILTER);
    public static final IFilter ELECTRIC_ITEM = T -> ((GatewayElectricItemManager)ElectricItem.MANAGER).getManager(T) != null;
    boolean charge;
    boolean external;
    int tier;

    public ElectricItemFilter(boolean charge, boolean external) {
        this(charge, external, Integer.MAX_VALUE);
    }

    public ElectricItemFilter(boolean charge, boolean external, int tier) {
        this.charge = charge;
        this.external = external;
        this.tier = tier;
    }

    @Override
    public boolean matches(ItemStack stack) {
        if (this.charge) {
            return ElectricItem.MANAGER.charge(stack, Integer.MAX_VALUE, this.tier, true, true) > 0;
        }
        return stack.m_150930_(Items.f_42451_) || stack.m_150930_(IC2Items.SU_BATTERY) || ElectricItem.MANAGER.discharge(stack, Integer.MAX_VALUE, this.tier, true, this.external, true) > 0;
    }
}

