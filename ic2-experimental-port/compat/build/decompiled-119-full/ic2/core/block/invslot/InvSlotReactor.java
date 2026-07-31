/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.core.block.invslot.InvSlot;
import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import net.minecraft.world.item.ItemStack;

public class InvSlotReactor
extends InvSlot {
    private final int rows = 6;
    private final int maxCols = 9;

    public InvSlotReactor(TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric, String string, int n) {
        super(tileEntityNuclearReactorElectric, string, InvSlot.Access.IO, n);
        this.setStackSizeLimit(1);
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        return ((TileEntityNuclearReactorElectric)this.base).isUsefulItem(itemStack, true);
    }

    @Override
    public int size() {
        return ((TileEntityNuclearReactorElectric)this.base).getReactorSize() * 6;
    }

    public int rawSize() {
        return super.size();
    }

    @Override
    public ItemStack get(int n) {
        return super.get(this.mapIndex(n));
    }

    public ItemStack get(int n, int n2) {
        return super.get(n2 * 9 + n);
    }

    @Override
    protected void putFromNBT(int n, ItemStack itemStack) {
        super.putFromNBT(this.mapIndex(n), itemStack);
    }

    @Override
    public void put(int n, ItemStack itemStack) {
        super.put(this.mapIndex(n), itemStack);
    }

    public void put(int n, int n2, ItemStack itemStack) {
        super.put(n2 * 9 + n, itemStack);
    }

    private int mapIndex(int n) {
        int n2 = this.size();
        int n3 = n2 / 6;
        if (n < n2) {
            int n4 = n / n3;
            int n5 = n % n3;
            return n4 * 9 + n5;
        }
        int n6 = 9 - n3;
        int n7 = (n -= n2) / n6;
        int n8 = n3 + n % n6;
        return n7 * 9 + n8;
    }
}

