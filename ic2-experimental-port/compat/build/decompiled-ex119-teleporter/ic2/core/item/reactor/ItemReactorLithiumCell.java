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
import ic2.core.profile.NotClassic;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@NotClassic
public class ItemReactorLithiumCell
extends AbstractDamageableReactorComponent {
    public ItemReactorLithiumCell(Item.Properties properties) {
        super(properties, 10000);
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack itemStack, IReactor iReactor, ItemStack itemStack2, int n, int n2, int n3, int n4, boolean bl) {
        int n5;
        if (bl && (n5 = this.getUse(itemStack) + iReactor.getHeat() / 3000) < this.getMaxUse()) {
            this.setUse(itemStack, n5);
        }
        return true;
    }

    @Override
    public double getUseFraction(ItemStack itemStack) {
        return 1.0 - super.getUseFraction(itemStack);
    }
}

