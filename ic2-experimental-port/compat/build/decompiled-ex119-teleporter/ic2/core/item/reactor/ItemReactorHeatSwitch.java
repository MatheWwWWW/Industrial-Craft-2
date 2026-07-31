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
import ic2.core.item.reactor.ItemReactorHeatStorage;
import java.util.ArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemReactorHeatSwitch
extends ItemReactorHeatStorage {
    public final int switchSide;
    public final int switchReactor;

    public ItemReactorHeatSwitch(Item.Properties properties, int n, int n2, int n3) {
        super(properties, n);
        this.switchSide = n2;
        this.switchReactor = n3;
    }

    @Override
    public void processChamber(ItemStack itemStack, IReactor iReactor, int n, int n2, boolean bl) {
        if (!bl) {
            return;
        }
        int n3 = 0;
        ArrayList<ItemStackCoord> arrayList = new ArrayList<ItemStackCoord>();
        if (this.switchSide > 0) {
            this.checkHeatAcceptor(iReactor, n - 1, n2, arrayList);
            this.checkHeatAcceptor(iReactor, n + 1, n2, arrayList);
            this.checkHeatAcceptor(iReactor, n, n2 - 1, arrayList);
            this.checkHeatAcceptor(iReactor, n, n2 + 1, arrayList);
        }
        if (this.switchSide > 0) {
            for (ItemStackCoord itemStackCoord : arrayList) {
                IReactorComponent iReactorComponent = (IReactorComponent)itemStackCoord.stack.m_41720_();
                double d = (double)this.getCurrentHeat(itemStack, iReactor, n, n2) * 100.0 / (double)this.getMaxHeat(itemStack, iReactor, n, n2);
                double d2 = (double)iReactorComponent.getCurrentHeat(itemStackCoord.stack, iReactor, itemStackCoord.x, itemStackCoord.y) * 100.0 / (double)iReactorComponent.getMaxHeat(itemStackCoord.stack, iReactor, itemStackCoord.x, itemStackCoord.y);
                int n4 = (int)((double)iReactorComponent.getMaxHeat(itemStackCoord.stack, iReactor, itemStackCoord.x, itemStackCoord.y) / 100.0 * (d2 + d / 2.0));
                if (n4 > this.switchSide) {
                    n4 = this.switchSide;
                }
                if (d2 + d / 2.0 < 1.0) {
                    n4 = this.switchSide / 2;
                }
                if (d2 + d / 2.0 < 0.75) {
                    n4 = this.switchSide / 4;
                }
                if (d2 + d / 2.0 < 0.5) {
                    n4 = this.switchSide / 8;
                }
                if (d2 + d / 2.0 < 0.25) {
                    n4 = 1;
                }
                if ((double)Math.round(d2 * 10.0) / 10.0 > (double)Math.round(d * 10.0) / 10.0) {
                    n4 -= 2 * n4;
                } else if ((double)Math.round(d2 * 10.0) / 10.0 == (double)Math.round(d * 10.0) / 10.0) {
                    n4 = 0;
                }
                n3 -= n4;
                n4 = iReactorComponent.alterHeat(itemStackCoord.stack, iReactor, itemStackCoord.x, itemStackCoord.y, n4);
                n3 += n4;
            }
        }
        if (this.switchReactor > 0) {
            double d = (double)this.getCurrentHeat(itemStack, iReactor, n, n2) * 100.0 / (double)this.getMaxHeat(itemStack, iReactor, n, n2);
            double d3 = (double)iReactor.getHeat() * 100.0 / (double)iReactor.getMaxHeat();
            int n5 = (int)Math.round((double)iReactor.getMaxHeat() / 100.0 * (d3 + d / 2.0));
            if (n5 > this.switchReactor) {
                n5 = this.switchReactor;
            }
            if (d3 + d / 2.0 < 1.0) {
                n5 = this.switchSide / 2;
            }
            if (d3 + d / 2.0 < 0.75) {
                n5 = this.switchSide / 4;
            }
            if (d3 + d / 2.0 < 0.5) {
                n5 = this.switchSide / 8;
            }
            if (d3 + d / 2.0 < 0.25) {
                n5 = 1;
            }
            if ((double)Math.round(d3 * 10.0) / 10.0 > (double)Math.round(d * 10.0) / 10.0) {
                n5 -= 2 * n5;
            } else if ((double)Math.round(d3 * 10.0) / 10.0 == (double)Math.round(d * 10.0) / 10.0) {
                n5 = 0;
            }
            n3 -= n5;
            iReactor.setHeat(iReactor.getHeat() + n5);
        }
        this.alterHeat(itemStack, iReactor, n, n2, n3);
    }

    private void checkHeatAcceptor(IReactor iReactor, int n, int n2, ArrayList<ItemStackCoord> arrayList) {
        IReactorComponent iReactorComponent;
        ItemStack itemStack = iReactor.getItemAt(n, n2);
        if (itemStack != null && itemStack.m_41720_() instanceof IReactorComponent && (iReactorComponent = (IReactorComponent)itemStack.m_41720_()).canStoreHeat(itemStack, iReactor, n, n2)) {
            arrayList.add(new ItemStackCoord(itemStack, n, n2));
        }
    }

    private class ItemStackCoord {
        public ItemStack stack;
        public int x;
        public int y;

        public ItemStackCoord(ItemStack itemStack, int n, int n2) {
            this.stack = itemStack;
            this.x = n;
            this.y = n2;
        }
    }
}

