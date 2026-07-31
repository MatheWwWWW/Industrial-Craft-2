/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 */
package ic2.core.item;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.core.IC2;
import ic2.core.item.DamageHandler;
import ic2.core.slot.ArmorSlot;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ElectricItemManager
implements IElectricItemManager {
    @Override
    public double charge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
        IElectricItem item = (IElectricItem)stack.func_77973_b();
        assert (item.getMaxCharge(stack) > 0.0);
        if (amount < 0.0 || StackUtil.getSize(stack) > 1 || item.getTier(stack) > tier) {
            return 0.0;
        }
        if (!ignoreTransferLimit && amount > item.getTransferLimit(stack)) {
            amount = item.getTransferLimit(stack);
        }
        NBTTagCompound tNBT = StackUtil.getOrCreateNbtData(stack);
        double newCharge = tNBT.func_74769_h("charge");
        amount = Math.min(amount, item.getMaxCharge(stack) - newCharge);
        if (!simulate) {
            if ((newCharge += amount) > 0.0) {
                tNBT.func_74780_a("charge", newCharge);
            } else {
                tNBT.func_82580_o("charge");
                if (tNBT.func_82582_d()) {
                    stack.func_77982_d(null);
                }
            }
            if (stack.func_77973_b() instanceof IElectricItem) {
                item = (IElectricItem)stack.func_77973_b();
                int maxDamage = DamageHandler.getMaxDamage(stack);
                DamageHandler.setDamage(stack, ElectricItemManager.mapChargeLevelToDamage(newCharge, item.getMaxCharge(stack), maxDamage), true);
            } else {
                DamageHandler.setDamage(stack, 0, true);
            }
        }
        return amount;
    }

    private static int mapChargeLevelToDamage(double charge, double maxCharge, int maxDamage) {
        if (maxDamage < 2) {
            return 0;
        }
        return --maxDamage - (int)Util.map(charge, maxCharge, maxDamage);
    }

    @Override
    public double discharge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
        IElectricItem item = (IElectricItem)stack.func_77973_b();
        assert (item.getMaxCharge(stack) > 0.0);
        if (amount < 0.0 || StackUtil.getSize(stack) > 1 || item.getTier(stack) > tier) {
            return 0.0;
        }
        if (externally && !item.canProvideEnergy(stack)) {
            return 0.0;
        }
        if (!ignoreTransferLimit && amount > item.getTransferLimit(stack)) {
            amount = item.getTransferLimit(stack);
        }
        NBTTagCompound tNBT = StackUtil.getOrCreateNbtData(stack);
        double newCharge = tNBT.func_74769_h("charge");
        amount = Math.min(amount, newCharge);
        if (!simulate) {
            if ((newCharge -= amount) > 0.0) {
                tNBT.func_74780_a("charge", newCharge);
            } else {
                tNBT.func_82580_o("charge");
                if (tNBT.func_82582_d()) {
                    stack.func_77982_d(null);
                }
            }
            if (stack.func_77973_b() instanceof IElectricItem) {
                item = (IElectricItem)stack.func_77973_b();
                int maxDamage = DamageHandler.getMaxDamage(stack);
                DamageHandler.setDamage(stack, ElectricItemManager.mapChargeLevelToDamage(newCharge, item.getMaxCharge(stack), maxDamage), true);
            } else {
                DamageHandler.setDamage(stack, 0, true);
            }
        }
        return amount;
    }

    @Override
    public double getCharge(ItemStack stack) {
        return ElectricItem.manager.discharge(stack, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, false, true);
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return ElectricItem.manager.getCharge(stack) + ElectricItem.manager.charge(stack, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, true);
    }

    @Override
    public boolean canUse(ItemStack stack, double amount) {
        return ElectricItem.manager.getCharge(stack) >= amount;
    }

    @Override
    public boolean use(ItemStack stack, double amount, EntityLivingBase entity) {
        double transfer;
        if (entity != null) {
            ElectricItem.manager.chargeFromArmor(stack, entity);
        }
        if (Util.isSimilar(transfer = ElectricItem.manager.discharge(stack, amount, Integer.MAX_VALUE, true, false, true), amount)) {
            ElectricItem.manager.discharge(stack, amount, Integer.MAX_VALUE, true, false, false);
            if (entity != null) {
                ElectricItem.manager.chargeFromArmor(stack, entity);
            }
            return true;
        }
        return false;
    }

    @Override
    public void chargeFromArmor(ItemStack target, EntityLivingBase entity) {
        boolean transferred = false;
        for (EntityEquipmentSlot slot : ArmorSlot.getAll()) {
            ItemStack source = entity.func_184582_a(slot);
            if (source == null) continue;
            int tier = source.func_77973_b() instanceof IElectricItem ? ((IElectricItem)source.func_77973_b()).getTier(target) : Integer.MAX_VALUE;
            double transfer = ElectricItem.manager.discharge(source, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, true, true);
            if (transfer <= 0.0 || (transfer = ElectricItem.manager.charge(target, transfer, tier, true, false)) <= 0.0) continue;
            ElectricItem.manager.discharge(source, transfer, Integer.MAX_VALUE, true, true, false);
            transferred = true;
        }
        if (transferred && entity instanceof EntityPlayer && IC2.platform.isSimulating()) {
            ((EntityPlayer)entity).field_71070_bA.func_75142_b();
        }
    }

    @Override
    public String getToolTip(ItemStack stack) {
        double charge = ElectricItem.manager.getCharge(stack);
        double space = ElectricItem.manager.charge(stack, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, true);
        return Util.toSiString(charge, 3) + "/" + Util.toSiString(charge + space, 3) + " EU";
    }

    public static ItemStack getCharged(Item item, double charge) {
        if (!(item instanceof IElectricItem)) {
            throw new IllegalArgumentException("no electric item");
        }
        ItemStack ret = new ItemStack(item);
        ElectricItem.manager.charge(ret, charge, Integer.MAX_VALUE, true, false);
        return ret;
    }

    public static void addChargeVariants(Item item, List<ItemStack> list) {
        list.add(ElectricItemManager.getCharged(item, 0.0));
        list.add(ElectricItemManager.getCharged(item, Double.POSITIVE_INFINITY));
    }

    @Override
    public int getTier(ItemStack stack) {
        if (stack == null || !(stack.func_77973_b() instanceof IElectricItem)) {
            return 0;
        }
        return ((IElectricItem)stack.func_77973_b()).getTier(stack);
    }
}

