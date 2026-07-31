/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.gui;

import ic2.core.gui.IOverlaySupplier;

public abstract class FixedSizeOverlaySupplier
implements IOverlaySupplier {
    private final int width;
    private final int height;

    public FixedSizeOverlaySupplier(int size) {
        this(size, size);
    }

    public FixedSizeOverlaySupplier(int width, int height) {
        this.width = width;
        this.height = height;
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

