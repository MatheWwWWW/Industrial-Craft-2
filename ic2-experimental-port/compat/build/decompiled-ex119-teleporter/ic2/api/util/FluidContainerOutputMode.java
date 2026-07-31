/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.util;

public enum FluidContainerOutputMode {
    EmptyFullToOutput(true),
    AnyToOutput(true),
    InPlacePreferred(false),
    InPlace(false);

    private final boolean outputEmptyFull;

    private FluidContainerOutputMode(boolean bl) {
        this.outputEmptyFull = bl;
    }

    public boolean isOutputEmptyFull() {
        return this.outputEmptyFull;
    }
}

