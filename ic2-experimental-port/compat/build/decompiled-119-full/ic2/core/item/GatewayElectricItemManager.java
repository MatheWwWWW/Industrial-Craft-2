/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;
import ic2.core.util.StackUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GatewayElectricItemManager
implements IElectricItemManager {
    @Override
    public double charge(ItemStack itemStack, double d, int n, boolean bl, boolean bl2) {
        if (StackUtil.isEmpty(itemStack)) {
            return 0.0;
        }
        IElectricItemManager iElectricItemManager = this.getManager(itemStack);
        if (iElectricItemManager == null) {
            return 0.0;
        }
        return iElectricItemManager.charge(itemStack, d, n, bl, bl2);
    }

    @Override
    public double discharge(ItemStack itemStack, double d, int n, boolean bl, boolean bl2, boolean bl3) {
        if (StackUtil.isEmpty(itemStack)) {
            return 0.0;
        }
        IElectricItemManager iElectricItemManager = this.getManager(itemStack);
        if (iElectricItemManager == null) {
            return 0.0;
        }
        return iElectricItemManager.discharge(itemStack, d, n, bl, bl2, bl3);
    }

    @Override
    public double getCharge(ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            return 0.0;
        }
        IElectricItemManager iElectricItemManager = this.getManager(itemStack);
        if (iElectricItemManager == null) {
            return 0.0;
        }
        return iElectricItemManager.getCharge(itemStack);
    }

    @Override
    public double getStackCharge(ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            return 0.0;
        }
        IElectricItemManager iElectricItemManager = this.getManager(itemStack);
        if (iElectricItemManager == null) {
            return 0.0;
        }
        return iElectricItemManager.getStackCharge(itemStack);
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            return 0.0;
        }
        IElectricItemManager iElectricItemManager = this.getManager(itemStack);
        if (iElectricItemManager == null) {
            return 0.0;
        }
        return iElectricItemManager.getMaxCharge(itemStack);
    }

    @Override
    public boolean canUse(ItemStack itemStack, double d) {
        if (StackUtil.isEmpty(itemStack)) {
            return false;
        }
        IElectricItemManager iElectricItemManager = this.getManager(itemStack);
        if (iElectricItemManager == null) {
            return false;
        }
        return iElectricItemManager.canUse(itemStack, d);
    }

    @Override
    public boolean use(ItemStack itemStack, double d, LivingEntity livingEntity) {
        if (StackUtil.isEmpty(itemStack)) {
            return false;
        }
        if (livingEntity instanceof Player && ((Player)livingEntity).m_150110_().f_35937_) {
            return this.canUse(itemStack, d);
        }
        IElectricItemManager iElectricItemManager = this.getManager(itemStack);
        if (iElectricItemManager == null) {
            return false;
        }
        return iElectricItemManager.use(itemStack, d, livingEntity);
    }

    @Override
    public void chargeFromArmor(ItemStack itemStack, LivingEntity livingEntity) {
        if (StackUtil.isEmpty(itemStack)) {
            return;
        }
        if (livingEntity == null) {
            return;
        }
        IElectricItemManager iElectricItemManager = this.getManager(itemStack);
        if (iElectricItemManager == null) {
            return;
        }
        iElectricItemManager.chargeFromArmor(itemStack, livingEntity);
    }

    @Override
    public String getToolTip(ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            return null;
        }
        IElectricItemManager iElectricItemManager = this.getManager(itemStack);
        if (iElectricItemManager == null) {
            return null;
        }
        return iElectricItemManager.getToolTip(itemStack);
    }

    private IElectricItemManager getManager(ItemStack itemStack) {
        Item item = itemStack.m_41720_();
        if (item == null) {
            return null;
        }
        if (item instanceof ISpecialElectricItem) {
            return ((ISpecialElectricItem)item).getManager(itemStack);
        }
        if (item instanceof IElectricItem) {
            return ElectricItem.rawManager;
        }
        return ElectricItem.getBackupManager(itemStack);
    }

    @Override
    public int getTier(ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            return 0;
        }
        IElectricItemManager iElectricItemManager = this.getManager(itemStack);
        if (iElectricItemManager == null) {
            return 0;
        }
        return iElectricItemManager.getTier(itemStack);
    }
}

