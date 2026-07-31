/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item;

import ic2.core.IC2;
import ic2.core.item.IPseudoDamageItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DamageHandler {
    public static int getDamage(ItemStack itemStack) {
        Item item = itemStack.m_41720_();
        if (item == null) {
            return 0;
        }
        return itemStack.m_41773_();
    }

    public static void setDamage(ItemStack itemStack, int n, boolean bl) {
        Item item = itemStack.m_41720_();
        if (item == null) {
            return;
        }
        if (item instanceof IPseudoDamageItem) {
            if (!bl) {
                throw new IllegalStateException("can't damage " + itemStack + " physically");
            }
            ((IPseudoDamageItem)item).setStackDamage(itemStack, n);
        } else if (itemStack.m_41763_()) {
            itemStack.m_41721_(n);
        }
    }

    public static int getMaxDamage(ItemStack itemStack) {
        Item item = itemStack.m_41720_();
        if (item == null) {
            return 0;
        }
        return itemStack.m_41776_();
    }

    public static boolean damage(ItemStack itemStack, int n, LivingEntity livingEntity2, InteractionHand interactionHand) {
        Item item = itemStack.m_41720_();
        if (item == null) {
            return false;
        }
        if (livingEntity2 != null) {
            itemStack.m_41622_(n, livingEntity2, livingEntity -> {
                if (interactionHand != null) {
                    livingEntity.m_21190_(interactionHand);
                }
            });
            return true;
        }
        return itemStack.m_220157_(n, IC2.random, livingEntity2 instanceof ServerPlayer ? (ServerPlayer)livingEntity2 : null);
    }
}

