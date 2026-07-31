/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.EquipmentSlot$Type
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 */
package ic2.core.item.manager;

import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IElectricItem;
import ic2.api.items.electric.IElectricItemManager;
import ic2.core.IC2;
import ic2.core.utils.helpers.Formatters;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class ElectricItemManager
implements IElectricItemManager {
    @Override
    public int charge(ItemStack stack, int amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
        IElectricItem item = (IElectricItem)stack.m_41720_();
        if (amount < 0 || stack.m_41613_() > 1 || item.getTier(stack) > tier) {
            return 0;
        }
        if (!ignoreTransferLimit) {
            amount = Math.min(amount, item.getTransferLimit(stack));
        }
        CompoundTag nbt = stack.m_41784_();
        int charge = nbt.m_128451_("charge");
        amount = Math.min(amount, item.getCapacity(stack) - charge);
        if (!simulate) {
            if ((charge += amount) > 0) {
                nbt.m_128405_("charge", charge);
            } else {
                nbt.m_128473_("charge");
            }
            stack.m_41721_(this.calculateDamage(stack, charge));
        }
        return amount;
    }

    @Override
    public int discharge(ItemStack stack, int amount, int tier, boolean ignoreTransferLimit, boolean externalUse, boolean simulate) {
        CompoundTag nbt;
        int charge;
        if (!stack.m_41782_() || amount <= 0 || stack.m_41613_() > 1) {
            return 0;
        }
        IElectricItem item = (IElectricItem)stack.m_41720_();
        if (item.getTier(stack) > tier || externalUse && !item.canProvideEnergy(stack)) {
            return 0;
        }
        if (!ignoreTransferLimit) {
            amount = Math.min(amount, item.getTransferLimit(stack));
        }
        if (amount > (charge = (nbt = stack.m_41784_()).m_128451_("charge"))) {
            amount = charge;
        }
        if (!simulate) {
            if ((charge -= amount) > 0) {
                nbt.m_128405_("charge", charge);
            } else {
                nbt.m_128473_("charge");
            }
            stack.m_41721_(this.calculateDamage(stack, charge));
        }
        return amount;
    }

    @Override
    public int getCharge(ItemStack stack) {
        return this.discharge(stack, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false, true);
    }

    @Override
    public boolean canUse(ItemStack stack, int amount) {
        return this.getCharge(stack) >= this.applyEnchantmentEffect(stack, amount);
    }

    @Override
    public boolean use(ItemStack stack, int amount, LivingEntity entity) {
        amount = this.applyEnchantmentEffect(stack, amount);
        this.chargeFromArmor(stack, entity);
        int transfer = this.discharge(stack, amount, Integer.MAX_VALUE, true, false, true);
        if (transfer == amount) {
            this.discharge(stack, amount, Integer.MAX_VALUE, true, false, false);
            this.chargeFromArmor(stack, entity);
            return true;
        }
        return false;
    }

    @Override
    public void chargeFromArmor(ItemStack stack, LivingEntity entity) {
        if (entity == null) {
            return;
        }
        boolean inventoryChanged = false;
        int needed = this.charge(stack, Integer.MAX_VALUE, Integer.MAX_VALUE, true, true);
        if (needed <= 0) {
            return;
        }
        if (entity instanceof Player && IC2.CURIO_PLUGIN != null) {
            int added = IC2.CURIO_PLUGIN.chargeFromArmor((Player)entity, stack, needed);
            needed -= added;
            boolean bl = inventoryChanged = added > 0;
        }
        if (needed > 0) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                int provided;
                if (needed <= 0) break;
                if (slot.m_20743_() == EquipmentSlot.Type.HAND || (provided = ElectricItem.MANAGER.discharge(entity.m_6844_(slot), needed, Integer.MAX_VALUE, true, true, false)) <= 0) continue;
                this.charge(stack, provided, Integer.MAX_VALUE, true, false);
                inventoryChanged = true;
                needed -= provided;
            }
        }
        if (inventoryChanged && entity instanceof Player) {
            ((Player)entity).f_36096_.m_38946_();
        }
    }

    public int applyEnchantmentEffect(ItemStack item, int amount) {
        amount = (int)((double)amount * (1.0 + 0.15 * (double)EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44984_, (ItemStack)item)));
        amount = (int)((double)amount * (1.0 - 0.1 * (double)EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44986_, (ItemStack)item)));
        return amount;
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return ((IElectricItem)stack.m_41720_()).getCapacity(stack);
    }

    @Override
    public int getTier(ItemStack stack) {
        return ((IElectricItem)stack.m_41720_()).getTier(stack);
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return ((IElectricItem)stack.m_41720_()).getTransferLimit(stack);
    }

    private int calculateDamage(ItemStack stack, int newCharge) {
        if (stack.m_41619_() || stack.m_41776_() < 2) {
            return 0;
        }
        return (int)((double)newCharge / (double)this.getCapacity(stack) * (double)(stack.m_41776_() - 1));
    }

    @Override
    public Component getToolTip(ItemStack stack) {
        return Component.m_237110_((String)"misc.ic2.eu_data", (Object[])new Object[]{Formatters.EU_FORMAT.format(this.getCharge(stack)), Formatters.EU_FORMAT.format(this.getCapacity(stack))}).m_130940_(ChatFormatting.AQUA);
    }
}

