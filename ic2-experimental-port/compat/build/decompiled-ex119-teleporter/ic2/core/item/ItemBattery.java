/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.item;

import ic2.api.item.ElectricItem;
import ic2.api.network.INetworkItemEventListener;
import ic2.core.IC2;
import ic2.core.item.BaseElectricItem;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.StackUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemBattery
extends BaseElectricItem
implements INetworkItemEventListener {
    public ItemBattery(Item.Properties properties, double d, double d2, int n) {
        super(properties, d, d2, n);
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return true;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = StackUtil.get(player, interactionHand);
        if (level.f_46443_ || StackUtil.getSize(itemStack) != 1) {
            return new InteractionResultHolder(InteractionResult.PASS, (Object)itemStack);
        }
        if (ElectricItem.manager.getCharge(itemStack) > 0.0) {
            boolean bl = false;
            for (int i = 0; i < 9; ++i) {
                double d;
                ItemStack itemStack2 = (ItemStack)player.m_150109_().f_35974_.get(i);
                if (itemStack2 == null || itemStack2 == itemStack || ElectricItem.manager.discharge(itemStack2, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, true, true) > 0.0 || (d = ElectricItem.manager.discharge(itemStack, 2.0 * this.transferLimit, Integer.MAX_VALUE, true, true, true)) <= 0.0 || (d = ElectricItem.manager.charge(itemStack2, d, this.tier, true, false)) <= 0.0) continue;
                ElectricItem.manager.discharge(itemStack, d, Integer.MAX_VALUE, true, true, false);
                bl = true;
            }
            if (bl && !level.f_46443_) {
                player.f_36096_.m_38946_();
                IC2.network.get(true).initiateItemEvent(player, itemStack, 0, true);
            }
        }
        return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)itemStack);
    }

    @Override
    public void onNetworkEvent(ItemStack itemStack, Player player, int n) {
        player.m_5496_(Ic2SoundEvents.ITEM_BATTERY_USE, 1.0f, 1.0f);
    }
}

