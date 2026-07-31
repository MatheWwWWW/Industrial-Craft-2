/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.core.item.reactor.AbstractDamageableReactorComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemReactorCondensator
extends AbstractDamageableReactorComponent {
    public ItemReactorCondensator(Item.Properties properties, int n) {
        super(properties, n);
    }

    @Override
    public boolean canStoreHeat(ItemStack itemStack, IReactor iReactor, int n, int n2) {
        return this.getCurrentHeat(itemStack) < this.getMaxHeat(itemStack, iReactor, n, n2);
    }

    @Override
    public int getMaxHeat(ItemStack itemStack, IReactor iReactor, int n, int n2) {
        return this.getMaxUse();
    }

    private int getCurrentHeat(ItemStack itemStack) {
        return this.getUse(itemStack);
    }

    @Override
    public int alterHeat(ItemStack itemStack, IReactor iReactor, int n, int n2, int n3) {
        if (n3 < 0) {
            return n3;
        }
        int n4 = this.getCurrentHeat(itemStack);
        int n5 = Math.min(n3, this.getMaxHeat(itemStack, iReactor, n, n2) - n4);
        this.setUse(itemStack, n4 + n5);
        return n3 -= n5;
    }
}

