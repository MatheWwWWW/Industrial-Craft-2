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
import ic2.core.item.reactor.AbstractReactorComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemReactorVentSpread
extends AbstractReactorComponent {
    public final int sideVent;

    public ItemReactorVentSpread(Item.Properties properties, int n) {
        super(properties);
        this.sideVent = n;
    }

    @Override
    public void processChamber(ItemStack itemStack, IReactor iReactor, int n, int n2, boolean bl) {
        if (bl) {
            this.cool(iReactor, n - 1, n2);
            this.cool(iReactor, n + 1, n2);
            this.cool(iReactor, n, n2 - 1);
            this.cool(iReactor, n, n2 + 1);
        }
    }

    private void cool(IReactor iReactor, int n, int n2) {
        int n3;
        IReactorComponent iReactorComponent;
        ItemStack itemStack = iReactor.getItemAt(n, n2);
        if (itemStack != null && itemStack.m_41720_() instanceof IReactorComponent && (iReactorComponent = (IReactorComponent)itemStack.m_41720_()).canStoreHeat(itemStack, iReactor, n, n2) && (n3 = iReactorComponent.alterHeat(itemStack, iReactor, n, n2, -this.sideVent)) <= 0) {
            iReactor.addEmitHeat(n3 + this.sideVent);
        }
    }
}

