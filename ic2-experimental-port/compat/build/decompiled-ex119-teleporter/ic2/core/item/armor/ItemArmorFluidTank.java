/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.item.ArmorMaterial
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core.item.armor;

import ic2.api.item.IItemHudInfo;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.StandardFluidItem;
import ic2.core.init.Localization;
import ic2.core.item.armor.ItemArmorUtility;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

public abstract class ItemArmorFluidTank
extends ItemArmorUtility
implements StandardFluidItem,
IItemHudInfo {
    protected final int capacity;
    protected final Fluid allowfluid;

    public ItemArmorFluidTank(ArmorMaterial armorMaterial, Item.Properties properties, Fluid fluid, int n) {
        super(armorMaterial, properties, EquipmentSlot.CHEST);
        this.capacity = n;
        this.allowfluid = fluid;
    }

    @Override
    public int getCapacityMb(ItemStack itemStack) {
        return this.capacity;
    }

    @Override
    public boolean canFill(ItemStack itemStack, Ic2FluidStack ic2FluidStack) {
        return ic2FluidStack.getFluid() == this.allowfluid;
    }

    public void filltank(ItemStack itemStack) {
        this.fillMb(itemStack, Ic2FluidStack.create(this.allowfluid, Integer.MAX_VALUE), false, null);
    }

    public double getCharge(ItemStack itemStack) {
        return Ic2FluidStack.get(itemStack).getAmountMb();
    }

    private double getChargeLevel(ItemStack itemStack) {
        return this.getCharge(itemStack) / (double)this.capacity;
    }

    public double getMaxCharge(ItemStack itemStack) {
        return this.capacity;
    }

    public boolean isEmpty(ItemStack itemStack) {
        return Ic2FluidStack.get(itemStack).isEmpty();
    }

    public void m_7373_(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.m_7373_(itemStack, level, list, tooltipFlag);
        list.add((Component)Component.m_237113_((String)ItemArmorFluidTank.getContentDescription(itemStack)));
    }

    public boolean m_142522_(ItemStack itemStack) {
        return true;
    }

    public int m_142158_(ItemStack itemStack) {
        return (int)Math.round(this.getChargeLevel(itemStack) * 13.0);
    }

    public int m_142159_(ItemStack itemStack) {
        return Mth.m_14169_((float)((float)(this.getChargeLevel(itemStack) / 3.0)), (float)1.0f, (float)1.0f);
    }

    @Override
    public List<String> getHudInfo(ItemStack itemStack, boolean bl) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(ItemArmorFluidTank.getContentDescription(itemStack));
        return arrayList;
    }

    private static String getContentDescription(ItemStack itemStack) {
        Ic2FluidStack ic2FluidStack = Ic2FluidStack.get(itemStack);
        if (!ic2FluidStack.isEmpty()) {
            return String.format("< %s, %d mB >", Registry.f_122822_.m_7981_((Object)ic2FluidStack.getFluid()), ic2FluidStack.getAmountMb());
        }
        return Localization.translate("ic2.item.FluidContainer.Empty");
    }

    public boolean m_6832_(ItemStack itemStack, ItemStack itemStack2) {
        return false;
    }
}

