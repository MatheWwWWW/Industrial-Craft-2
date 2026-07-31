/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import ic2.core.item.ElectricItemManager;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class BaseElectricItem
extends Item
implements IElectricItem,
IItemHudInfo {
    protected final double maxCharge;
    protected final double transferLimit;
    protected final int tier;

    public BaseElectricItem(Item.Properties properties, double d, double d2, int n) {
        super(properties);
        this.maxCharge = d;
        this.transferLimit = d2;
        this.tier = n;
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return false;
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return this.maxCharge;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return this.tier;
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return this.transferLimit;
    }

    @Override
    public List<String> getHudInfo(ItemStack itemStack, boolean bl) {
        LinkedList<String> linkedList = new LinkedList<String>();
        linkedList.add(ElectricItem.manager.getToolTip(itemStack));
        return linkedList;
    }

    public void m_6787_(CreativeModeTab creativeModeTab, NonNullList<ItemStack> nonNullList) {
        if (!this.m_220152_(creativeModeTab)) {
            return;
        }
        ElectricItemManager.addChargeVariants(this, nonNullList);
    }

    public boolean m_142522_(ItemStack itemStack) {
        return ElectricItem.manager.getCharge(itemStack) <= ElectricItem.manager.getMaxCharge(itemStack);
    }

    public int m_142158_(ItemStack itemStack) {
        return (int)Math.round(ElectricItem.manager.getStackChargeLevel(itemStack) * 13.0);
    }

    public int m_142159_(ItemStack itemStack) {
        return Mth.m_14169_((float)((float)(ElectricItem.manager.getStackChargeLevel(itemStack) / 3.0)), (float)1.0f, (float)1.0f);
    }
}

