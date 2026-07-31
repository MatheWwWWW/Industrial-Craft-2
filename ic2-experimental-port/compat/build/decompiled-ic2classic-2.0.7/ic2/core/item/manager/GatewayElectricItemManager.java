/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 */
package ic2.core.item.manager;

import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.ICustomElectricItem;
import ic2.api.items.electric.IDamagelessElectricItem;
import ic2.api.items.electric.IElectricItem;
import ic2.api.items.electric.IElectricItemManager;
import ic2.core.item.manager.DamagelessElectricItemManager;
import ic2.core.item.manager.ElectricItemManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GatewayElectricItemManager
implements IElectricItemManager {
    public static IElectricItemManager DIRECT_DAMAGELESS;

    public static void init() {
        ElectricItem.MANAGER = new GatewayElectricItemManager();
        ElectricItem.DIRECT_MANAGER = new ElectricItemManager();
        DIRECT_DAMAGELESS = new DamagelessElectricItemManager();
    }

    @Override
    public int charge(ItemStack itemStack, int amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
        IElectricItemManager manager = this.getManager(itemStack);
        return manager == null ? 0 : manager.charge(itemStack, amount, tier, ignoreTransferLimit, simulate);
    }

    @Override
    public int discharge(ItemStack itemStack, int amount, int tier, boolean ignoreTransferLimit, boolean externalUse, boolean simulate) {
        IElectricItemManager manager = this.getManager(itemStack);
        return manager == null ? 0 : manager.discharge(itemStack, amount, tier, ignoreTransferLimit, externalUse, simulate);
    }

    @Override
    public int getCharge(ItemStack itemStack) {
        IElectricItemManager manager = this.getManager(itemStack);
        return manager == null ? 0 : manager.getCharge(itemStack);
    }

    @Override
    public boolean canUse(ItemStack itemStack, int amount) {
        IElectricItemManager manager = this.getManager(itemStack);
        return manager != null && manager.canUse(itemStack, amount);
    }

    @Override
    public boolean use(ItemStack itemStack, int amount, LivingEntity entity) {
        if (entity instanceof Player && ((Player)entity).m_7500_()) {
            return true;
        }
        IElectricItemManager manager = this.getManager(itemStack);
        return manager != null && manager.use(itemStack, amount, entity);
    }

    @Override
    public void chargeFromArmor(ItemStack itemStack, LivingEntity entity) {
        IElectricItemManager manager = this.getManager(itemStack);
        if (manager == null) {
            return;
        }
        manager.chargeFromArmor(itemStack, entity);
    }

    @Override
    public int getCapacity(ItemStack stack) {
        IElectricItemManager manager = this.getManager(stack);
        return manager == null ? 0 : manager.getCapacity(stack);
    }

    @Override
    public int getTier(ItemStack stack) {
        IElectricItemManager manager = this.getManager(stack);
        return manager == null ? 0 : manager.getTier(stack);
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        IElectricItemManager manager = this.getManager(stack);
        return manager == null ? 0 : manager.getTransferLimit(stack);
    }

    public IElectricItemManager getManager(ItemStack stack) {
        Item item = stack.m_41720_();
        if (item == Items.f_41852_) {
            return null;
        }
        if (item instanceof IDamagelessElectricItem) {
            return DIRECT_DAMAGELESS;
        }
        if (item instanceof IElectricItem) {
            return ElectricItem.DIRECT_MANAGER;
        }
        if (item instanceof ICustomElectricItem) {
            return ((ICustomElectricItem)item).getManager(stack);
        }
        return ElectricItem.getBackupManager(item);
    }

    @Override
    public Component getToolTip(ItemStack stack) {
        IElectricItemManager manager = this.getManager(stack);
        return manager == null ? null : manager.getToolTip(stack);
    }
}

