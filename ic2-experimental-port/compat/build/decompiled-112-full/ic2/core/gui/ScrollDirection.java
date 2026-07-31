/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.gui;

public enum ScrollDirection {
    stopped(0),
    up(-1),
    down(1);

    public final byte multiplier;

    private ScrollDirection(int multiplier) {
        this.multiplier = (byte)multiplier;
    }
}

