/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.wearable.armor;

import ic2.api.items.armor.IEnergyShieldArmor;
import ic2.api.items.armor.IMetalArmor;
import ic2.core.item.wearable.base.IC2ArmorBase;
import ic2.core.platform.registries.IC2Materials;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CompositeArmor
extends IC2ArmorBase
implements IMetalArmor,
IEnergyShieldArmor {
    public CompositeArmor(String itemName, EquipmentSlot slot) {
        super(itemName, IC2Materials.COMPOSITE_ARMOR, slot);
    }

    @Override
    public String getTextureFolder() {
        return "armor/composite";
    }

    @Override
    public String getTextureName() {
        switch (this.f_40377_) {
            case HEAD: {
                return "helmet";
            }
            case CHEST: {
                return "vest";
            }
            case LEGS: {
                return "pants";
            }
            case FEET: {
                return "boots";
            }
        }
        return "";
    }

    @Override
    public String getArmorTexture() {
        return "ic2:textures/models/armor/alloy";
    }

    @Override
    public boolean isMetalArmor(ItemStack stack, Player player, EquipmentSlot targetSlot) {
        return true;
    }

    @Override
    public boolean addsShieldEffect(EquipmentSlot type, LivingEntity entity, ItemStack stack) {
        return true;
    }

    @Override
    public boolean isEffectAlwaysOn(EquipmentSlot type, LivingEntity entity, ItemStack stack) {
        return false;
    }
}

