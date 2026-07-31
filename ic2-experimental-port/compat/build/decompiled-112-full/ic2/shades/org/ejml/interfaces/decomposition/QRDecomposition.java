/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.interfaces.decomposition;

import ic2.shades.org.ejml.data.Matrix64F;
import ic2.shades.org.ejml.interfaces.decomposition.DecompositionInterface;

public interface QRDecomposition<T extends Matrix64F>
extends DecompositionInterface<T> {
    public T getQ(T var1, boolean var2);

    public T getR(T var1, boolean var2);
}

