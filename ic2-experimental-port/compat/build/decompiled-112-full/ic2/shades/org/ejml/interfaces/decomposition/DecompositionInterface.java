/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.interfaces.decomposition;

import ic2.shades.org.ejml.data.Matrix64F;

public interface DecompositionInterface<T extends Matrix64F> {
    public boolean decompose(T var1);

    public boolean inputModified();
}

