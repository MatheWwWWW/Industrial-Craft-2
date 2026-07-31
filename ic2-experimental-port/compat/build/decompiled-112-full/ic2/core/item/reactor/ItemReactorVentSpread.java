/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.item.reactor.AbstractReactorComponent;
import ic2.core.ref.ItemName;
import net.minecraft.item.ItemStack;

public class ItemReactorVentSpread
extends AbstractReactorComponent {
    public final int sideVent;

    public ItemReactorVentSpread(ItemName name, int sidevent) {
        super(name);
        this.sideVent = sidevent;
    }

    @Override
    public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatrun) {
        if (heatrun) {
            this.cool(reactor, x - 1, y);
            this.cool(reactor, x + 1, y);
            this.cool(reactor, x, y - 1);
            this.cool(reactor, x, y + 1);
        }
    }

    private void cool(IReactor reactor, int x, int y) {
        int self;
        IReactorComponent comp;
        ItemStack stack = reactor.getItemAt(x, y);
        if (stack != null && stack.func_77973_b() instanceof IReactorComponent && (comp = (IReactorComponent)stack.func_77973_b()).canStoreHeat(stack, reactor, x, y) && (self = comp.alterHeat(stack, reactor, x, y, -this.sideVent)) <= 0) {
            reactor.addEmitHeat(self + this.sideVent);
        }
    }
}

