/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.ArmorMaterial
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.armor;

import ic2.api.item.IMetalArmor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemArmorIC2
extends ArmorItem
implements IMetalArmor {
    public ItemArmorIC2(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Item.Properties properties) {
        super(armorMaterial, equipmentSlot, properties);
    }

    @Override
    public boolean isMetalArmor(ItemStack itemStack, Player player) {
        return true;
    }
}

