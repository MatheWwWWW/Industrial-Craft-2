/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.item.armor;

import ic2.core.fluid.Ic2FluidStack;
import ic2.core.item.armor.ItemArmorFluidTank;
import ic2.core.item.armor.jetpack.IJetpack;
import ic2.core.ref.Ic2ArmorMaterials;
import ic2.core.ref.Ic2Fluids;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ItemArmorJetpack
extends ItemArmorFluidTank
implements IJetpack {
    public ItemArmorJetpack(Item.Properties properties) {
        super(Ic2ArmorMaterials.JET_PACK, properties, Ic2Fluids.BIOGAS.still, 30000);
    }

    public void m_6787_(CreativeModeTab creativeModeTab, NonNullList<ItemStack> nonNullList) {
        if (!this.m_220152_(creativeModeTab)) {
            return;
        }
        ItemStack itemStack = new ItemStack((ItemLike)this);
        this.filltank(itemStack);
        nonNullList.add((Object)itemStack);
        nonNullList.add((Object)new ItemStack((ItemLike)this));
    }

    @Override
    public boolean drainEnergy(ItemStack itemStack, int n) {
        if (this.isEmpty(itemStack)) {
            return false;
        }
        Ic2FluidStack ic2FluidStack = this.drainMb(itemStack, n, true, null);
        if (ic2FluidStack.getAmountMb() < n) {
            return false;
        }
        this.drainMb(itemStack, n, false, null);
        return true;
    }

    @Override
    public float getPower(ItemStack itemStack) {
        return 1.0f;
    }

    @Override
    public float getDropPercentage(ItemStack itemStack) {
        return 0.2f;
    }

    @Override
    public boolean isJetpackActive(ItemStack itemStack) {
        return true;
    }

    @Override
    public double getChargeLevel(ItemStack itemStack) {
        return this.getCharge(itemStack) / this.getMaxCharge(itemStack);
    }

    @Override
    public float getHoverMultiplier(ItemStack itemStack, boolean bl) {
        return 0.2f;
    }

    @Override
    public float getWorldHeightDivisor(ItemStack itemStack) {
        return 1.0f;
    }
}

