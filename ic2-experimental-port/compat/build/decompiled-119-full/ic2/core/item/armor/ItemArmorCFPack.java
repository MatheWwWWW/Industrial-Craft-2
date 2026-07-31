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

import ic2.core.item.armor.ItemArmorFluidTank;
import ic2.core.ref.Ic2ArmorMaterials;
import ic2.core.ref.Ic2Fluids;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ItemArmorCFPack
extends ItemArmorFluidTank {
    public ItemArmorCFPack(Item.Properties properties) {
        super(Ic2ArmorMaterials.CF_PACK, properties, Ic2Fluids.CONSTRUCTION_FOAM.still, 80000);
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
}

