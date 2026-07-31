/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.core.item.reactor.ItemReactorHeatStorage;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemReactorVent
extends ItemReactorHeatStorage {
    public final int selfVent;
    public final int reactorVent;

    public ItemReactorVent(Item.Properties properties, int n, int n2, int n3) {
        super(properties, n);
        this.selfVent = n2;
        this.reactorVent = n3;
    }

    @Override
    public void processChamber(ItemStack itemStack, IReactor iReactor, int n, int n2, boolean bl) {
        if (bl) {
            int n3;
            if (this.reactorVent > 0) {
                n3 = iReactor.getHeat();
                int n4 = n3;
                if (n4 > this.reactorVent) {
                    n4 = this.reactorVent;
                }
                n3 -= n4;
                if ((n4 = this.alterHeat(itemStack, iReactor, n, n2, n4)) > 0) {
                    return;
                }
                iReactor.setHeat(n3);
            }
            if ((n3 = this.alterHeat(itemStack, iReactor, n, n2, -this.selfVent)) <= 0) {
                iReactor.addEmitHeat(n3 + this.selfVent);
            }
        }
    }
}

