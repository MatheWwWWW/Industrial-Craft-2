/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.gui;

import ic2.core.gui.IClickHandler;
import ic2.core.gui.INumericValueHandler;
import ic2.core.gui.IOverlaySupplier;
import ic2.core.gui.MouseButton;

public class CycleHandler
implements IClickHandler,
IOverlaySupplier {
    private final int uS;
    private final int vS;
    private final int uE;
    private final int vE;
    private final int overlayStep;
    private final boolean vertical;
    private final int options;
    private final INumericValueHandler handler;

    public CycleHandler(int n, int n2, int n3, int n4, int n5, boolean bl, int n6, INumericValueHandler iNumericValueHandler) {
        this.uS = n;
        this.vS = n2;
        this.uE = n3;
        this.vE = n4;
        this.overlayStep = n5;
        this.vertical = bl;
        this.options = n6;
        this.handler = iNumericValueHandler;
    }

    @Override
    public void onClick(MouseButton mouseButton) {
        int n = this.getValue();
        if (mouseButton == MouseButton.left) {
            n = (n + 1) % this.options;
        } else if (mouseButton == MouseButton.right) {
            n = (n + this.options - 1) % this.options;
        } else {
            return;
        }
        this.handler.onChange(n);
    }

    @Override
    public int getUS() {
        if (this.vertical) {
            return this.uS;
        }
        return this.uS + this.overlayStep * this.getValue();
    }

    @Override
    public int getVS() {
        if (!this.vertical) {
            return this.vS;
        }
        return this.vS + this.overlayStep * this.getValue();
    }

    @Override
    public int getUE() {
        if (this.vertical) {
            return this.uE;
        }
        return this.uS + this.overlayStep * (this.getValue() + 1);
    }

    @Override
    public int getVE() {
        if (!this.vertical) {
            return this.vE;
        }
        return this.vS + this.overlayStep * (this.getValue() + 1);
    }

    protected int getValue() {
        int n = this.handler.getValue();
        if (n < 0 || n >= this.options) {
            throw new RuntimeException("invalid value: " + n);
        }
        return n;
    }
}

