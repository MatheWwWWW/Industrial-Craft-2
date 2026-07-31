/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package ic2.core.item;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;
import ic2.core.util.StackUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GatewayElectricItemManager
implements IElectricItemManager {
    @Override
    public double charge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
        if (StackUtil.isEmpty(stack)) {
            return 0.0;
        }
        IElectricItemManager manager = this.getManager(stack);
        if (manager == null) {
            return 0.0;
        }
        return manager.charge(stack, amount, tier, ignoreTransferLimit, simulate);
    }

    @Override
    public double discharge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
        if (StackUtil.isEmpty(stack)) {
            return 0.0;
        }
        IElectricItemManager manager = this.getManager(stack);
        if (manager == null) {
            return 0.0;
        }
        return manager.discharge(stack, amount, tier, ignoreTransferLimit, externally, simulate);
    }

    @Override
    public double getCharge(ItemStack stack) {
        if (StackUtil.isEmpty(stack)) {
            return 0.0;
        }
        IElectricItemManager manager = this.getManager(stack);
        if (manager == null) {
            return 0.0;
        }
        return manager.getCharge(stack);
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        if (StackUtil.isEmpty(stack)) {
            return 0.0;
        }
        IElectricItemManager manager = this.getManager(stack);
        if (manager == null) {
            return 0.0;
        }
        return manager.getMaxCharge(stack);
    }

    @Override
    public boolean canUse(ItemStack stack, double amount) {
        if (StackUtil.isEmpty(stack)) {
            return false;
        }
        IElectricItemManager manager = this.getManager(stack);
        if (manager == null) {
            return false;
        }
        return manager.canUse(stack, amount);
    }

    @Override
    public boolean use(ItemStack stack, double amount, EntityLivingBase entity) {
        if (StackUtil.isEmpty(stack)) {
            return false;
        }
        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75098_d) {
            return this.canUse(stack, amount);
        }
        IElectricItemManager manager = this.getManager(stack);
        if (manager == null) {
            return false;
        }
        return manager.use(stack, amount, entity);
    }

    @Override
    public void chargeFromArmor(ItemStack stack, EntityLivingBase entity) {
        if (StackUtil.isEmpty(stack)) {
            return;
        }
        if (entity == null) {
            return;
        }
        IElectricItemManager manager = this.getManager(stack);
        if (manager == null) {
            return;
        }
        manager.chargeFromArmor(stack, entity);
    }

    @Override
    public String getToolTip(ItemStack stack) {
        if (StackUtil.isEmpty(stack)) {
            return null;
        }
        IElectricItemManager manager = this.getManager(stack);
        if (manager == null) {
            return null;
        }
        return manager.getToolTip(stack);
    }

    private IElectricItemManager getManager(ItemStack stack) {
        Item item = stack.func_77973_b();
        if (item == null) {
            return null;
        }
        if (item instanceof ISpecialElectricItem) {
            return ((ISpecialElectricItem)item).getManager(stack);
        }
        if (item instanceof IElectricItem) {
            return ElectricItem.rawManager;
        }
        return ElectricItem.getBackupManager(stack);
    }

    @Override
    public int getTier(ItemStack stack) {
        if (StackUtil.isEmpty(stack)) {
            return 0;
        }
        IElectricItemManager manager = this.getManager(stack);
        if (manager == null) {
            return 0;
        }
        return manager.getTier(stack);
    }
}

