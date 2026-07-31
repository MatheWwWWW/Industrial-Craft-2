/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.core.item.reactor.AbstractReactorComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemReactorPlating
extends AbstractReactorComponent {
    private final int maxHeatAdd;
    private final float effectModifier;

    public ItemReactorPlating(Item.Properties properties, int n, float f) {
        super(properties);
        this.maxHeatAdd = n;
        this.effectModifier = f;
    }

    @Override
    public void processChamber(ItemStack itemStack, IReactor iReactor, int n, int n2, boolean bl) {
        if (bl) {
            iReactor.setMaxHeat(iReactor.getMaxHeat() + this.maxHeatAdd);
            iReactor.setHeatEffectModifier(iReactor.getHeatEffectModifier() * this.effectModifier);
        }
    }

    @Override
    public float influenceExplosion(ItemStack itemStack, IReactor iReactor) {
        if (this.effectModifier >= 1.0f) {
            return 0.0f;
        }
        return this.effectModifier;
    }
}

