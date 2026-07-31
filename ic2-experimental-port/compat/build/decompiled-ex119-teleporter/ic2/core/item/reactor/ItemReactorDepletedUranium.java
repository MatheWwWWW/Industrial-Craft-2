/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.core.item.reactor.AbstractDamageableReactorComponent;
import ic2.core.profile.NotExperimental;
import ic2.core.ref.Ic2Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

@NotExperimental
public class ItemReactorDepletedUranium
extends AbstractDamageableReactorComponent {
    public ItemReactorDepletedUranium(Item.Properties properties) {
        super(properties, 10000);
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack itemStack, IReactor iReactor, ItemStack itemStack2, int n, int n2, int n3, int n4, boolean bl) {
        if (bl) {
            int n5 = this.getUse(itemStack) + 1 + iReactor.getHeat() / 3000;
            if (n5 >= this.getMaxUse()) {
                iReactor.setItemAt(n, n2, new ItemStack((ItemLike)Ic2Items.RE_ENRICHED_URANIUM));
            } else {
                this.setUse(itemStack, n5);
            }
        }
        return true;
    }

    @Override
    public double getUseFraction(ItemStack itemStack) {
        return 1.0 - super.getUseFraction(itemStack);
    }
}

