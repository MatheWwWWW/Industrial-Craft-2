/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.util;

import ic2.api.info.IInfoProvider;
import ic2.core.IC2;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.init.MainConfig;
import ic2.core.ref.Ic2Items;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;

public class ItemInfo
implements IInfoProvider {
    @Override
    public double getEnergyValue(ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            return 0.0;
        }
        if (StackUtil.checkItemEquality(itemStack, Items.f_42451_)) {
            return 800.0;
        }
        if (StackUtil.checkItemEquality(itemStack, Ic2Items.SINGLE_USE_BATTERY)) {
            return 1200.0;
        }
        if (StackUtil.checkItemEquality(itemStack, Ic2Items.ENERGIUM_DUST)) {
            return 16000.0;
        }
        return 0.0;
    }

    @Override
    public int getFuelValue(ItemStack itemStack, boolean bl) {
        boolean bl2;
        if (StackUtil.isEmpty(itemStack)) {
            return 0;
        }
        if ((StackUtil.checkItemEquality(itemStack, Ic2Items.SCRAP) || StackUtil.checkItemEquality(itemStack, Ic2Items.SCRAP_BOX)) && !ConfigUtil.getBool(MainConfig.get(), "misc/allowBurningScrap")) {
            return 0;
        }
        Ic2FluidStack ic2FluidStack = Ic2FluidStack.get(itemStack);
        boolean bl3 = bl2 = ic2FluidStack != null && !ic2FluidStack.isEmpty() && ic2FluidStack.getFluid() == Fluids.f_76195_;
        if (bl2 && !bl) {
            return 0;
        }
        int n = IC2.envProxy.getBurnTime(itemStack);
        return bl2 ? n / 10 : n;
    }
}

