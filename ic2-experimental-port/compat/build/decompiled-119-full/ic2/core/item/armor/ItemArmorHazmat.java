/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.EquipmentSlot$Type
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.armor;

import ic2.api.item.IHazmatLike;
import ic2.core.Ic2DamageSource;
import ic2.core.item.armor.ItemArmorUtility;
import ic2.core.ref.Ic2ArmorMaterials;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemArmorHazmat
extends ItemArmorUtility
implements IHazmatLike {
    public ItemArmorHazmat(EquipmentSlot equipmentSlot, Item.Properties properties) {
        super(Ic2ArmorMaterials.HAZMAT, properties, equipmentSlot);
    }

    @Override
    public boolean addsProtection(LivingEntity livingEntity, EquipmentSlot equipmentSlot, ItemStack itemStack) {
        return true;
    }

    public static boolean hasCompleteHazmat(LivingEntity livingEntity) {
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (equipmentSlot.m_20743_() != EquipmentSlot.Type.ARMOR) continue;
            ItemStack itemStack = livingEntity.m_6844_(equipmentSlot);
            if (itemStack == null || !(itemStack.m_41720_() instanceof IHazmatLike)) {
                return false;
            }
            IHazmatLike iHazmatLike = (IHazmatLike)itemStack.m_41720_();
            if (!iHazmatLike.addsProtection(livingEntity, equipmentSlot, itemStack)) {
                return false;
            }
            if (!iHazmatLike.fullyProtects(livingEntity, equipmentSlot, itemStack)) continue;
            return true;
        }
        return true;
    }

    public static boolean hazmatAbsorbs(DamageSource damageSource) {
        return damageSource == DamageSource.f_19305_ || damageSource == DamageSource.f_19310_ || damageSource == DamageSource.f_19308_ || damageSource == DamageSource.f_19309_ || damageSource == DamageSource.f_19307_ || damageSource == Ic2DamageSource.electricity || damageSource == Ic2DamageSource.radiation;
    }

    public boolean absorbFall(ItemStack itemStack, LivingEntity livingEntity2, float f) {
        int n = Math.max((int)f - 3, 0);
        if (n >= 8) {
            return false;
        }
        int n2 = (n + 1) / 2;
        if (n2 <= 0 || n2 > itemStack.m_41776_() - itemStack.m_41773_()) {
            return false;
        }
        itemStack.m_41622_(n2, livingEntity2, livingEntity -> livingEntity.m_21166_(this.f_40377_));
        return true;
    }
}

