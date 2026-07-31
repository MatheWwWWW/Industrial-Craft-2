/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor.base;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import net.minecraft.world.item.ItemStack;

public class ItemStackCoord {
    public ItemStack stack;
    public int x;
    public int y;

    public ItemStackCoord(ItemStack stack, int x, int y) {
        this.stack = stack;
        this.x = x;
        this.y = y;
    }

    public int storeHeat(IReactorComponent component, IReactor reactor, int toAdd) {
        return component.storeHeat(this.stack, reactor, this.x, this.y, toAdd);
    }

    public int getTransferRate(IReactorComponent component, IReactor reactor, double medium) {
        return (int)(medium * (double)component.getMaxStoredHeat(this.stack, reactor, this.x, this.y) - (double)component.getStoredHeat(this.stack, reactor, this.x, this.y));
    }

    public static class ItemStackDirCoord
    extends ItemStackCoord {
        boolean dir;

        public ItemStackDirCoord(ItemStack stack, int x, int y, boolean dir) {
            super(stack, x, y);
            this.dir = dir;
        }

        @Override
        public int getTransferRate(IReactorComponent component, IReactor reactor, double medium) {
            int prev = super.getTransferRate(component, reactor, medium);
            return prev > 0 != this.dir ? prev : 0;
        }
    }
}

