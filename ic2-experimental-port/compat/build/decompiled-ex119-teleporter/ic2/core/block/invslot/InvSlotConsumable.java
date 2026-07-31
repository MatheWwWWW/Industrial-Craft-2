/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.core.IC2;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.item.DamageHandler;
import ic2.core.util.StackUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class InvSlotConsumable
extends InvSlot {
    public InvSlotConsumable(IInventorySlotHolder<?> iInventorySlotHolder, String string, int n) {
        super(iInventorySlotHolder, string, InvSlot.Access.I, n, InvSlot.InvSide.TOP);
    }

    public InvSlotConsumable(IInventorySlotHolder<?> iInventorySlotHolder, String string, InvSlot.Access access, int n, InvSlot.InvSide invSide) {
        super(iInventorySlotHolder, string, access, n, invSide);
    }

    @Override
    public abstract boolean accepts(ItemStack var1);

    @Override
    public boolean canOutput() {
        return super.canOutput() || this.access != InvSlot.Access.NONE && !this.isEmpty() && !this.accepts(this.get());
    }

    public ItemStack consume(int n) {
        return this.consume(n, false, false);
    }

    public ItemStack consume(int n, boolean bl, boolean bl2) {
        ItemStack itemStack = null;
        for (int i = 0; i < this.size(); ++i) {
            ItemStack itemStack2 = this.get(i);
            if (StackUtil.getSize(itemStack2) < 1 || !this.accepts(itemStack2) || itemStack != null && !StackUtil.checkItemEqualityStrict(itemStack2, itemStack) || StackUtil.getSize(itemStack2) != 1 && !bl2 && IC2.envProxy.hasRecipeRemainder(itemStack2)) continue;
            int n2 = Math.min(n, StackUtil.getSize(itemStack2));
            n -= n2;
            if (!bl) {
                if (StackUtil.getSize(itemStack2) == n2) {
                    if (!bl2 && IC2.envProxy.hasRecipeRemainder(itemStack2)) {
                        ItemStack itemStack3 = IC2.envProxy.getRecipeRemainder(itemStack2);
                        if (itemStack3 != null && itemStack3.m_41763_() && DamageHandler.getDamage(itemStack3) > DamageHandler.getMaxDamage(itemStack3)) {
                            itemStack3 = null;
                        }
                        this.put(i, itemStack3);
                    } else {
                        this.clear(i);
                    }
                } else {
                    this.put(i, StackUtil.decSize(itemStack2, n2));
                }
            }
            itemStack = itemStack == null ? StackUtil.copyWithSize(itemStack2, n2) : StackUtil.incSize(itemStack, n2);
            if (n == 0) break;
        }
        return itemStack;
    }

    public int damage(int n, boolean bl) {
        return this.damage(n, bl, null);
    }

    public int damage(int n, boolean bl, LivingEntity livingEntity) {
        int n2 = 0;
        ItemStack itemStack = null;
        for (int i = 0; i < this.size() && n > 0; ++i) {
            ItemStack itemStack2 = this.get(i);
            if (StackUtil.isEmpty(itemStack2)) continue;
            Item item = itemStack2.m_41720_();
            if (!this.accepts(itemStack2) || !item.m_41465_() || itemStack != null && (item != itemStack.m_41720_() || !ItemStack.m_41658_((ItemStack)itemStack2, (ItemStack)itemStack))) continue;
            if (itemStack == null) {
                itemStack = itemStack2.m_41777_();
            }
            if (bl) {
                itemStack2 = itemStack2.m_41777_();
            }
            int n3 = DamageHandler.getMaxDamage(itemStack2);
            do {
                int n4 = Math.min(n, n3 - DamageHandler.getDamage(itemStack2));
                DamageHandler.damage(itemStack2, n4, livingEntity, null);
                n2 += n4;
                n -= n4;
                if (DamageHandler.getDamage(itemStack2) < n3) continue;
                if (StackUtil.isEmpty(itemStack2 = StackUtil.decSize(itemStack2))) break;
                DamageHandler.setDamage(itemStack2, 0, false);
            } while (n > 0 && !StackUtil.isEmpty(itemStack2));
            if (bl) continue;
            this.put(i, itemStack2);
        }
        return n2;
    }
}

