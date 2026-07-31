/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.gui;

import ic2.core.gui.IOverlaySupplier;

public class OverlaySupplier
implements IOverlaySupplier {
    private final int uS;
    private final int vS;
    private final int uE;
    private final int vE;

    public OverlaySupplier(int n, int n2, int n3, int n4) {
        this.uS = n;
        this.vS = n2;
        this.uE = n3;
        this.vE = n4;
    }

    @Override
    public int getUS() {
        return this.uS;
    }

    @Override
    public int getVS() {
        return this.vS;
    }

    @Override
    public int getUE() {
        return this.uE;
    }

    @Override
    public int getVE() {
        return this.vE;
    }
}

