/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.item.wearable.armor.electric.ElectricPackArmor
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 */
package trinsdar.gravisuit.items.armor;

import ic2.core.item.wearable.armor.electric.ElectricPackArmor;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import trinsdar.gravisuit.util.Registry;

public class ItemAdvancedLappack
extends ElectricPackArmor {
    final Supplier<Integer> capacity;
    final Supplier<Integer> transferLimit;

    public ItemAdvancedLappack(String itemName, Rarity rarity, Supplier<Integer> capacity, int tier, Supplier<Integer> transferLimit) {
        super(itemName, "lappack", itemName, "gravisuit:textures/models/" + itemName, rarity, 0, tier, 0);
        Registry.REGISTRY.put(new ResourceLocation("gravisuit", itemName), (Item)this);
        this.capacity = capacity;
        this.transferLimit = transferLimit;
    }

    public int getCapacity(ItemStack stack) {
        return this.capacity.get();
    }

    public int getTransferLimit(ItemStack stack) {
        return this.transferLimit.get();
    }
}

