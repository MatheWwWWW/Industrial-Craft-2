/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ElectricItemManager
implements IElectricItemManager {
    @Override
    public double charge(ItemStack itemStack, double d, int n, boolean bl, boolean bl2) {
        IElectricItem iElectricItem = (IElectricItem)itemStack.m_41720_();
        assert (iElectricItem.getMaxCharge(itemStack) > 0.0);
        if (d < 0.0 || StackUtil.getSize(itemStack) > 1 || iElectricItem.getTier(itemStack) > n) {
            return 0.0;
        }
        if (!bl && d > iElectricItem.getTransferLimit(itemStack)) {
            d = iElectricItem.getTransferLimit(itemStack);
        }
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        double d2 = compoundTag.m_128459_("charge");
        d = Math.min(d, iElectricItem.getMaxCharge(itemStack) - d2);
        if (!bl2) {
            if ((d2 += d) > 0.0) {
                compoundTag.m_128347_("charge", d2);
            } else {
                compoundTag.m_128473_("charge");
                if (compoundTag.m_128456_()) {
                    itemStack.m_41751_(null);
                }
            }
            if (itemStack.m_41720_() instanceof IElectricItem) {
                iElectricItem = (IElectricItem)itemStack.m_41720_();
                int n2 = DamageHandler.getMaxDamage(itemStack);
                DamageHandler.setDamage(itemStack, ElectricItemManager.mapChargeLevelToDamage(d2, iElectricItem.getMaxCharge(itemStack), n2), true);
            } else {
                DamageHandler.setDamage(itemStack, 0, true);
            }
        }
        return d;
    }

    private static int mapChargeLevelToDamage(double d, double d2, int n) {
        if (n < 2) {
            return 0;
        }
        return --n - (int)Util.map(d, d2, n);
    }

    @Override
    public double discharge(ItemStack itemStack, double d, int n, boolean bl, boolean bl2, boolean bl3) {
        IElectricItem iElectricItem = (IElectricItem)itemStack.m_41720_();
        assert (iElectricItem.getMaxCharge(itemStack) > 0.0);
        if (d < 0.0 || StackUtil.getSize(itemStack) > 1 || iElectricItem.getTier(itemStack) > n) {
            return 0.0;
        }
        if (bl2 && !iElectricItem.canProvideEnergy(itemStack)) {
            return 0.0;
        }
        if (!bl && d > iElectricItem.getTransferLimit(itemStack)) {
            d = iElectricItem.getTransferLimit(itemStack);
        }
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        double d2 = compoundTag.m_128459_("charge");
        d = Math.min(d, d2);
        if (!bl3) {
            if ((d2 -= d) > 0.0) {
                compoundTag.m_128347_("charge", d2);
            } else {
                compoundTag.m_128473_("charge");
                if (compoundTag.m_128456_()) {
                    itemStack.m_41751_(null);
                }
            }
            if (itemStack.m_41720_() instanceof IElectricItem) {
                iElectricItem = (IElectricItem)itemStack.m_41720_();
                int n2 = DamageHandler.getMaxDamage(itemStack);
                DamageHandler.setDamage(itemStack, ElectricItemManager.mapChargeLevelToDamage(d2, iElectricItem.getMaxCharge(itemStack), n2), true);
            } else {
                DamageHandler.setDamage(itemStack, 0, true);
            }
        }
        return d;
    }

    @Override
    public double getCharge(ItemStack itemStack) {
        return ElectricItem.manager.discharge(itemStack, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, false, true);
    }

    @Override
    public double getStackCharge(ItemStack itemStack) {
        IElectricItem iElectricItem = (IElectricItem)itemStack.m_41720_();
        assert (iElectricItem.getMaxCharge(itemStack) > 0.0);
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        return compoundTag.m_128459_("charge");
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return ElectricItem.manager.getCharge(itemStack) + ElectricItem.manager.charge(itemStack, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, true);
    }

    @Override
    public boolean canUse(ItemStack itemStack, double d) {
        return ElectricItem.manager.getCharge(itemStack) >= d;
    }

    @Override
    public boolean use(ItemStack itemStack, double d, LivingEntity livingEntity) {
        double d2;
        if (livingEntity != null) {
            ElectricItem.manager.chargeFromArmor(itemStack, livingEntity);
        }
        if (Util.isSimilar(d2 = ElectricItem.manager.discharge(itemStack, d, Integer.MAX_VALUE, true, false, true), d)) {
            ElectricItem.manager.discharge(itemStack, d, Integer.MAX_VALUE, true, false, false);
            if (livingEntity != null) {
                ElectricItem.manager.chargeFromArmor(itemStack, livingEntity);
            }
            return true;
        }
        return false;
    }

    @Override
    public void chargeFromArmor(ItemStack itemStack, LivingEntity livingEntity) {
        boolean bl = false;
        for (EquipmentSlot equipmentSlot : ArmorSlot.getAll()) {
            ItemStack itemStack2 = livingEntity.m_6844_(equipmentSlot);
            if (itemStack2 == null) continue;
            int n = itemStack2.m_41720_() instanceof IElectricItem ? ((IElectricItem)itemStack2.m_41720_()).getTier(itemStack) : Integer.MAX_VALUE;
            double d = ElectricItem.manager.discharge(itemStack2, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, true, true);
            if (d <= 0.0 || (d = ElectricItem.manager.charge(itemStack, d, n, true, false)) <= 0.0) continue;
            ElectricItem.manager.discharge(itemStack2, d, Integer.MAX_VALUE, true, true, false);
            bl = true;
        }
        if (bl && livingEntity instanceof Player && IC2.sideProxy.isSimulating()) {
            ((Player)livingEntity).f_36096_.m_38946_();
        }
    }

    @Override
    public String getToolTip(ItemStack itemStack) {
        double d = ElectricItem.manager.getCharge(itemStack);
        double d2 = ElectricItem.manager.charge(itemStack, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, true);
        return Util.toSiString(d, 3) + "/" + Util.toSiString(d + d2, 3) + " EU";
    }

    public static ItemStack getCharged(Item item, double d) {
        if (!(item instanceof IElectricItem)) {
            throw new IllegalArgumentException("no electric item");
        }
        ItemStack itemStack = new ItemStack((ItemLike)item);
        ElectricItem.manager.charge(itemStack, d, Integer.MAX_VALUE, true, false);
        return itemStack;
    }

    public static void addChargeVariants(Item item, List<ItemStack> list) {
        list.add(ElectricItemManager.getCharged(item, 0.0));
        list.add(ElectricItemManager.getCharged(item, Double.POSITIVE_INFINITY));
    }

    @Override
    public int getTier(ItemStack itemStack) {
        if (itemStack == null || !(itemStack.m_41720_() instanceof IElectricItem)) {
            return 0;
        }
        return ((IElectricItem)itemStack.m_41720_()).getTier(itemStack);
    }
}

