/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 */
package ic2.core.inventory.slot;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class ArmorSlot
extends Slot {
    public static final String[] ARMOR_SLOT_TEXTURES = new String[]{"item/empty_armor_slot_boots", "item/empty_armor_slot_leggings", "item/empty_armor_slot_chestplate", "item/empty_armor_slot_helmet"};
    Player player;
    EquipmentSlot f_40217_;

    public ArmorSlot(Player inventoryIn, EquipmentSlot slot, int index, int xPosition, int yPosition) {
        super((Container)inventoryIn.m_150109_(), index, xPosition, yPosition);
        this.player = inventoryIn;
        this.f_40217_ = slot;
        this.setBackground(InventoryMenu.f_39692_, new ResourceLocation(ARMOR_SLOT_TEXTURES[slot.m_20749_()]));
    }

    public int m_6641_() {
        return 1;
    }

    public boolean m_5857_(ItemStack stack) {
        return stack.canEquip(this.f_40217_, (Entity)this.player);
    }

    public boolean m_8010_(Player playerIn) {
        ItemStack itemstack = this.m_7993_();
        return (itemstack.m_41619_() || playerIn.m_7500_() || !EnchantmentHelper.m_44920_((ItemStack)itemstack)) && super.m_8010_(playerIn);
    }
}

