/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.block.comp;

import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.tileentity.Ic2TileEntity;
import java.util.function.IntSupplier;

public abstract class BasicRedstoneComponent
extends TileEntityComponent {
    private int level;
    private IntSupplier update;

    public BasicRedstoneComponent(Ic2TileEntity ic2TileEntity) {
        super(ic2TileEntity);
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int n) {
        if (n == this.level) {
            return;
        }
        this.level = n;
        this.onChange();
    }

    public abstract void onChange();

    @Override
    public boolean enableWorldTick() {
        return this.update != null;
    }

    @Override
    public void onWorldTick() {
        assert (this.update != null);
        this.setLevel(this.update.getAsInt());
    }

    public void setUpdate(IntSupplier intSupplier) {
        this.update = intSupplier;
    }
}

