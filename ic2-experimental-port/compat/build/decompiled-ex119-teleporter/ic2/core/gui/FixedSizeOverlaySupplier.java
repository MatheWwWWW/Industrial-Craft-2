/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.gui;

import ic2.core.gui.IOverlaySupplier;

public abstract class FixedSizeOverlaySupplier
implements IOverlaySupplier {
    private final int width;
    private final int height;

    public FixedSizeOverlaySupplier(int n) {
        this(n, n);
    }

    public FixedSizeOverlaySupplier(int n, int n2) {
        this.width = n;
        this.height = n2;
    }

    @Override
    public int getUE() {
        return this.getUS() + this.width;
    }

    @Override
    public int getVE() {
        return this.getVS() + this.height;
    }
}

