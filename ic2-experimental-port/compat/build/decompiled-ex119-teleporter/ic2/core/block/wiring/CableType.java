/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.block.wiring;

public enum CableType {
    copper(1, 1, 0.25f, 0.2, 128),
    glass(0, 0, 0.25f, 0.025, 8192),
    gold(2, 1, 0.1875f, 0.4, 512),
    iron(3, 1, 0.375f, 0.8, 2048),
    tin(1, 1, 0.25f, 0.2, 32),
    detector(0, Integer.MAX_VALUE, 0.5f, 0.5, 8192),
    splitter(0, Integer.MAX_VALUE, 0.5f, 0.5, 8192);

    public static final float insulationThickness = 0.0625f;
    public final int maxInsulation;
    public final int minColoredInsulation;
    public final float thickness;
    public final double loss;
    public final int capacity;
    public static final CableType[] values;

    private CableType(int n2, int n3, float f, double d, int n4) {
        this.maxInsulation = n2;
        this.minColoredInsulation = n3;
        this.thickness = f;
        this.loss = d;
        this.capacity = n4;
    }

    public float getThickness(int n) {
        return 0.125f * (float)n + this.thickness;
    }

    static {
        values = CableType.values();
    }
}

