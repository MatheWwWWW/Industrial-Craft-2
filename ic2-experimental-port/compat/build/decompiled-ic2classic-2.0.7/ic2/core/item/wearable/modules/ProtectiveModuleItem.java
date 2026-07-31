/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.wearable.modules;

import ic2.api.items.armor.IArmorModule;
import ic2.core.item.wearable.modules.BaseModuleItem;
import ic2.core.platform.registries.IC2Potions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ProtectiveModuleItem
extends BaseModuleItem {
    public ProtectiveModuleItem(String textureFolder, String textureName) {
        super("protective_module", null, textureFolder, textureName, IArmorModule.ModuleType.ANY);
    }

    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {
        return armorType == EquipmentSlot.HEAD;
    }

    @Override
    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        MobEffectInstance effect = player.m_21124_(MobEffects.f_19614_);
        if (effect != null && this.canUseEnergy(armor, 10000 * (effect.m_19564_() + 1))) {
            this.useEnergy(armor, 10000 * (effect.m_19564_() + 1), (LivingEntity)player);
            player.m_21195_(MobEffects.f_19614_);
        }
        if ((effect = player.m_21124_(IC2Potions.RADIATION)) != null && this.canUseEnergy(armor, 20000 * (effect.m_19564_() + 1))) {
            this.useEnergy(armor, 20000 * (effect.m_19564_() + 1), (LivingEntity)player);
            player.m_21195_(IC2Potions.RADIATION);
        }
        if ((effect = player.m_21124_(MobEffects.f_19615_)) != null && this.canUseEnergy(armor, 25000 * (effect.m_19564_() + 1))) {
            this.useEnergy(armor, 25000 * (effect.m_19564_() + 1), (LivingEntity)player);
            player.m_21195_(MobEffects.f_19615_);
        }
    }
}

