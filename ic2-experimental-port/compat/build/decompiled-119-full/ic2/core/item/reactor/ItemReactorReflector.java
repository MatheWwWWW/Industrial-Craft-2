/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.item.reactor.AbstractDamageableReactorComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemReactorReflector
extends AbstractDamageableReactorComponent {
    public ItemReactorReflector(Item.Properties properties, int n) {
        super(properties, n);
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack itemStack, IReactor iReactor, ItemStack itemStack2, int n, int n2, int n3, int n4, boolean bl) {
        if (!bl) {
            IReactorComponent iReactorComponent = (IReactorComponent)itemStack2.m_41720_();
            iReactorComponent.acceptUraniumPulse(itemStack2, iReactor, itemStack, n3, n4, n, n2, bl);
        } else if (this.getUse(itemStack) + 1 >= this.getMaxUse()) {
            iReactor.setItemAt(n, n2, null);
        } else {
            this.incrementUse(itemStack);
        }
        return true;
    }

    @Override
    public float influenceExplosion(ItemStack itemStack, IReactor iReactor) {
        return -1.0f;
    }
}

