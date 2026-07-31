/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.EquipmentSlot$Type
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.material.Material
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.wearable.armor;

import ic2.api.items.armor.ICustomArmor;
import ic2.api.util.IC2DamageSource;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.item.base.IC2Item;
import ic2.core.item.wearable.base.IC2ArmorBase;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Materials;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HazmatArmor
extends IC2ArmorBase
implements ICustomArmor {
    private static IFilter AIR_CELL_FILTER = i -> i.m_41720_() == IC2Items.CELL_AIR;

    public HazmatArmor(String itemName, EquipmentSlot slot) {
        super(itemName, IC2Materials.HAZMAT_ARMOR, slot);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addDataTooltip("tooltip.item.ic2.armor_hazmat.prevent_burn", new Object[0]);
        if (this.f_40377_ == EquipmentSlot.HEAD) {
            helper.addDataTooltip("tooltip.item.ic2.armor_hazmat_helmet.provide_air", new Object[0]);
        }
    }

    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (this.f_40377_ == EquipmentSlot.HEAD) {
            IItemTransporter trans;
            ItemStack tmp;
            if (HazmatArmor.isFullHazmatSuit((LivingEntity)player)) {
                if (player.m_20077_() || world.m_45556_(player.m_20191_().m_82363_((double)-0.1f, (double)-0.4f, (double)-0.1f)).anyMatch(T -> T.m_60767_() == Material.f_76309_)) {
                    player.m_7292_(new MobEffectInstance(MobEffects.f_19607_, 60, 1));
                }
                player.m_20095_();
            }
            if (player.m_20146_() <= 100 && player.m_150109_().m_36063_(new ItemStack((ItemLike)IC2Items.CELL_AIR)) && !(tmp = (trans = TransporterManager.getTransporter(player)).removeItem(AIR_CELL_FILTER, Direction.DOWN, 1, false)).m_41619_()) {
                trans.addItem(new ItemStack((ItemLike)IC2Items.CELL_EMPTY), Direction.DOWN, false);
                player.m_36220_(IC2Stats.AIR_CELLS_USED);
                player.m_20301_(player.m_20146_() + 150);
                player.f_36096_.m_38946_();
            }
        }
    }

    public static boolean isFullHazmatSuit(LivingEntity base) {
        if (!(base instanceof Player)) {
            return false;
        }
        Player player = (Player)base;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack;
            if (slot.m_20743_() == EquipmentSlot.Type.HAND || !(stack = player.m_6844_(slot)).m_41619_() && stack.m_41720_() instanceof HazmatArmor) continue;
            return false;
        }
        return true;
    }

    public boolean hazmatAbsorbs(DamageSource source) {
        return source == DamageSource.f_19305_ || source == DamageSource.f_19310_ || source == DamageSource.f_19308_ || source == DamageSource.f_19307_ || source.m_19385_().equals(IC2DamageSource.ELECTRICITY.m_19385_()) || source == IC2DamageSource.RADIATION;
    }

    @Override
    public String getTextureFolder() {
        return "armor/hazmat";
    }

    @Override
    public String getTextureName() {
        switch (this.f_40377_) {
            case HEAD: {
                return "scuba_helmet";
            }
            case CHEST: {
                return "suit";
            }
            case LEGS: {
                return "leggings";
            }
            case FEET: {
                return "rubber_boots";
            }
        }
        return "";
    }

    @Override
    public String getArmorTexture() {
        return "ic2:textures/models/armor/hazmat";
    }

    @Override
    public ICustomArmor.AbsorptionProperties getProperties(LivingEntity entity, ItemStack armor, DamageSource source, double damage, EquipmentSlot slot) {
        if (slot == EquipmentSlot.HEAD && this.hazmatAbsorbs(source) && HazmatArmor.isFullHazmatSuit(entity)) {
            if (source.m_19384_()) {
                entity.m_7292_(new MobEffectInstance(MobEffects.f_19607_, 60, 1));
            }
            return new ICustomArmor.AbsorptionProperties(10, 1.0, Integer.MAX_VALUE);
        }
        if (slot == EquipmentSlot.FEET && source == DamageSource.f_19315_) {
            return new ICustomArmor.AbsorptionProperties(10, damage < 8.0 ? 1.0 : 0.875, (armor.m_41776_() - armor.m_41773_() + 1) * 2);
        }
        return new ICustomArmor.AbsorptionProperties(0, 1.0, (armor.m_41776_() - armor.m_41773_() + 1) / 2);
    }

    @Override
    public void damageArmor(LivingEntity player, ItemStack stack, DamageSource source, int damage, EquipmentSlot slot, ICustomArmor.DamageType type) {
        if (this.hazmatAbsorbs(source) && HazmatArmor.isFullHazmatSuit(player)) {
            return;
        }
        if (source == DamageSource.f_19313_ || slot != EquipmentSlot.FEET && source == DamageSource.f_19315_) {
            return;
        }
        int damageTotal = damage * 2;
        if (slot == EquipmentSlot.FEET && source == DamageSource.f_19315_) {
            damageTotal = (damage + 1) / 2;
        }
        stack.m_41622_(damageTotal, player, IC2Item.get(slot));
    }

    @Override
    public boolean canBlockDamageSource(LivingEntity player, ItemStack stack, DamageSource source, EquipmentSlot slot) {
        if (slot == EquipmentSlot.FEET && source == DamageSource.f_19315_) {
            return true;
        }
        if (slot == EquipmentSlot.HEAD && this.hazmatAbsorbs(source)) {
            return true;
        }
        return !source.m_19376_() || source == DamageSource.f_19313_;
    }
}

