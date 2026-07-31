/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentCategory
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.item.wearable.base;

import ic2.api.items.armor.ICustomArmor;
import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IDamagelessElectricItem;
import ic2.api.items.electric.IElectricEnchantable;
import ic2.api.items.electric.IElectricItem;
import ic2.api.util.IC2DamageSource;
import ic2.core.IC2;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IC2ArmorBase;
import ic2.core.platform.registries.IC2Materials;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;

public abstract class IC2ElectricArmorBase
extends IC2ArmorBase
implements IDamagelessElectricItem,
ICustomArmor,
IElectricEnchantable {
    public int capacity;
    public int transferLimit;
    public int tier;

    public IC2ElectricArmorBase(String itemName, EquipmentSlot slot, @Nullable PropertiesBuilder props) {
        super(itemName, IC2Materials.ELECTRIC_ARMOR, slot, (props == null ? new PropertiesBuilder() : props).setNoRepair().maxDamage(0).maxStackSize(1));
    }

    public IC2ElectricArmorBase(String itemName, EquipmentSlot slot, int capacity, int transferLimit, int tier, @Nullable PropertiesBuilder props) {
        super(itemName, IC2Materials.ELECTRIC_ARMOR, slot, (props == null ? new PropertiesBuilder() : props).setNoRepair().maxDamage(0).maxStackSize(1));
        this.capacity = capacity;
        this.transferLimit = transferLimit;
        this.tier = tier;
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return this.capacity;
    }

    public int getTier() {
        return this.tier;
    }

    public int getTransferLimit() {
        return this.transferLimit;
    }

    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    public boolean m_8120_(ItemStack stack) {
        return false;
    }

    public boolean m_6832_(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public InteractionResult getEnchantmentCompatibility(ItemStack stack, Enchantment ench) {
        return ench == Enchantments.f_44962_ ? InteractionResult.FAIL : InteractionResult.PASS;
    }

    @Override
    public EnchantmentCategory getEnchantmentType(ItemStack stack) {
        if (!(stack.m_41720_() instanceof IC2ArmorBase)) {
            return null;
        }
        IC2ArmorBase item = (IC2ArmorBase)stack.m_41720_();
        switch (item.m_40402_()) {
            case CHEST: {
                return EnchantmentCategory.ARMOR_CHEST;
            }
            case FEET: {
                return EnchantmentCategory.ARMOR_FEET;
            }
            case HEAD: {
                return EnchantmentCategory.ARMOR_HEAD;
            }
            case LEGS: {
                return EnchantmentCategory.ARMOR_LEGS;
            }
        }
        return EnchantmentCategory.ARMOR;
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> subItems) {
        if (!this.m_220152_(group)) {
            return;
        }
        IC2ElectricItem.addEmptyAndFullToGroup((ItemLike)this, subItems);
    }

    public void damageAbsorbed(Player player, int damage) {
    }

    public void useEnergy(ItemStack stack, int amount, LivingEntity player) {
        ElectricItem.MANAGER.discharge(stack, ElectricItem.applyEnchantmentEffect(stack, amount), Integer.MAX_VALUE, true, false, false);
    }

    public boolean canUseEnergy(ItemStack stack, int amount) {
        return ElectricItem.MANAGER.discharge(stack, ElectricItem.applyEnchantmentEffect(stack, amount), Integer.MAX_VALUE, true, false, true) >= amount;
    }

    public boolean isBlockingEverything(ItemStack stack, DamageSource source, LivingEntity living) {
        return false;
    }

    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        ElectricItem.MANAGER.discharge(stack, ElectricItem.applyEnchantmentEffect(stack, this.getEnergyPerDamage(stack) * amount), Integer.MAX_VALUE, true, false, false);
        return 0;
    }

    public abstract double getDamageAbsorptionRatio(ItemStack var1);

    public abstract int getEnergyPerDamage(ItemStack var1);

    private double getBaseAbsorptionRatio(ItemStack stack) {
        switch (this.f_40377_) {
            case HEAD: {
                return 0.15;
            }
            case CHEST: {
                return 0.4;
            }
            case FEET: 
            case LEGS: {
                return 0.3;
            }
        }
        return 0.0;
    }

    public static boolean hasElectricBoots(LivingEntity base) {
        ItemStack stack = base.m_6844_(EquipmentSlot.FEET);
        if (stack.m_41619_() || !(stack.m_41720_() instanceof IElectricItem)) {
            return false;
        }
        return !base.f_19853_.m_46859_(base.m_20183_().m_7495_());
    }

    public int m_142158_(ItemStack stack) {
        return IC2ElectricItem.getElectricWidth(stack);
    }

    public int m_142159_(ItemStack stack) {
        return IC2ElectricItem.getRGBDurability(stack);
    }

    public boolean m_142522_(ItemStack stack) {
        return true;
    }

    @Override
    public ICustomArmor.AbsorptionProperties getProperties(LivingEntity entity, ItemStack armor, DamageSource source, double damage, EquipmentSlot slot) {
        if (source.m_19376_() && !this.isBlockingEverything(armor, source, entity)) {
            return new ICustomArmor.AbsorptionProperties(0, 0.0, 0);
        }
        if (source.m_19385_().equals(IC2DamageSource.ELECTRICITY.m_19385_()) && IC2.CONFIG.enableSpecialElectricArmor.get()) {
            return new ICustomArmor.AbsorptionProperties(0, 1.0, Integer.MAX_VALUE);
        }
        double absorptionRatio = this.getBaseAbsorptionRatio(armor) * this.getDamageAbsorptionRatio(armor);
        double energyPerDamage = this.getEnergyPerDamage(armor);
        int damageLimit = (int)(energyPerDamage > 0.0 ? (double)ElectricItem.MANAGER.discharge(armor, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false, true) / (energyPerDamage *= IC2.CONFIG.electricSuitEnergyCostModifier.get()) : 0.0);
        return new ICustomArmor.AbsorptionProperties(0, absorptionRatio *= IC2.CONFIG.electricSuitAbsorptionScale.get(), damageLimit);
    }

    @Override
    public void damageArmor(LivingEntity player, ItemStack stack, DamageSource source, int damage, EquipmentSlot slot, ICustomArmor.DamageType type) {
        if (source.m_19385_().equals(IC2DamageSource.ELECTRICITY.m_19385_()) && IC2.CONFIG.enableSpecialElectricArmor.get()) {
            int max = this.getCapacity(stack) - ElectricItem.MANAGER.getCharge(stack);
            if (max > 0) {
                ElectricItem.MANAGER.charge(stack, damage, Integer.MAX_VALUE, true, false);
                return;
            }
            if (IC2ElectricArmorBase.hasElectricBoots(player)) {
                return;
            }
        }
        this.useEnergy(stack, (int)((double)damage * ((double)this.getEnergyPerDamage(stack) * IC2.CONFIG.electricSuitAbsorptionScale.get())), player);
        if (player instanceof Player) {
            this.damageAbsorbed((Player)player, damage);
        }
    }

    @Override
    public boolean canBlockDamageSource(LivingEntity player, ItemStack stack, DamageSource source, EquipmentSlot slot) {
        return source.m_19385_().equals(IC2DamageSource.ELECTRICITY.m_19385_()) && IC2.CONFIG.enableSpecialElectricArmor.get() || !source.m_19376_() || this.isBlockingEverything(stack, source, player);
    }
}

