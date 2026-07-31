/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.slot;

import com.mojang.datafixers.util.Pair;
import ic2.core.util.ReflectionUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlotArmor
extends Slot {
    private static final ResourceLocation[] EMPTY_ARMOR_SLOT_TEXTURES = (ResourceLocation[])ReflectionUtil.getFieldValue(ReflectionUtil.getField(InventoryMenu.class, ResourceLocation[].class), null);
    private final EquipmentSlot armorType;

    public SlotArmor(Inventory inventory, EquipmentSlot equipmentSlot, int n, int n2) {
        super((Container)inventory, 36 + equipmentSlot.m_20749_(), n, n2);
        this.armorType = equipmentSlot;
    }

    public boolean m_5857_(ItemStack itemStack) {
        Item item = itemStack.m_41720_();
        if (item == null) {
            return false;
        }
        return Mob.m_147233_((ItemStack)itemStack) == this.armorType;
    }

    public Pair<ResourceLocation, ResourceLocation> m_7543_() {
        return Pair.of((Object)InventoryMenu.f_39692_, (Object)EMPTY_ARMOR_SLOT_TEXTURES[this.armorType.m_20749_()]);
    }
}

