/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.slot;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SlotArmor
extends Slot {
    private final EntityEquipmentSlot armorType;

    public SlotArmor(InventoryPlayer inventory, EntityEquipmentSlot armorType, int x, int y) {
        super((IInventory)inventory, 36 + armorType.func_188454_b(), x, y);
        this.armorType = armorType;
    }

    public boolean func_75214_a(ItemStack stack) {
        Item item = stack.func_77973_b();
        if (item == null) {
            return false;
        }
        return item.isValidArmor(stack, this.armorType, (Entity)((InventoryPlayer)this.field_75224_c).field_70458_d);
    }

    @SideOnly(value=Side.CLIENT)
    public String func_178171_c() {
        return ItemArmor.field_94603_a[this.armorType.func_188454_b()];
    }
}

