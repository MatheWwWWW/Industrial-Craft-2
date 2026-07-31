/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.ArmorMaterial
 *  net.minecraft.world.item.Item$Properties
 */
package ic2.core.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class ItemArmorUtility
extends ArmorItem {
    public ItemArmorUtility(ArmorMaterial armorMaterial, Item.Properties properties, EquipmentSlot equipmentSlot) {
        super(armorMaterial, equipmentSlot, properties);
    }
}

