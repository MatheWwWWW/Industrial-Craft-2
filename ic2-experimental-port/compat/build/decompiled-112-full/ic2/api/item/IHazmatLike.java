/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.inventory.EntityEquipmentSlot$Type
 *  net.minecraft.item.ItemStack
 */
package ic2.api.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public interface IHazmatLike {
    public boolean addsProtection(EntityLivingBase var1, EntityEquipmentSlot var2, ItemStack var3);

    default public boolean fullyProtects(EntityLivingBase entity, EntityEquipmentSlot slot, ItemStack stack) {
        return false;
    }

    public static boolean hasCompleteHazmat(EntityLivingBase living) {
        for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
            if (slot.func_188453_a() != EntityEquipmentSlot.Type.ARMOR) continue;
            ItemStack stack = living.func_184582_a(slot);
            if (stack == null || !(stack.func_77973_b() instanceof IHazmatLike)) {
                return false;
            }
            IHazmatLike hazmat = (IHazmatLike)stack.func_77973_b();
            if (!hazmat.addsProtection(living, slot, stack)) {
                return false;
            }
            if (!hazmat.fullyProtects(living, slot, stack)) continue;
            return true;
        }
        return true;
    }
}

