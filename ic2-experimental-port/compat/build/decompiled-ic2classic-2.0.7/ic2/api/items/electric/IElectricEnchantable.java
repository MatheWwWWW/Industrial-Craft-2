/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentCategory
 *  net.minecraftforge.common.extensions.IForgeItem
 */
package ic2.api.items.electric;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.extensions.IForgeItem;

public interface IElectricEnchantable
extends IForgeItem {
    public InteractionResult getEnchantmentCompatibility(ItemStack var1, Enchantment var2);

    public EnchantmentCategory getEnchantmentType(ItemStack var1);

    default public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
}

