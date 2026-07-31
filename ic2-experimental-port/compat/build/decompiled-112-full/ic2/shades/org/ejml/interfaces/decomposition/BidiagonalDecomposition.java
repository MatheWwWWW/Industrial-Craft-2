/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.interfaces.decomposition;

import ic2.shades.org.ejml.data.ReshapeMatrix64F;
import ic2.shades.org.ejml.interfaces.decomposition.DecompositionInterface;

public interface BidiagonalDecomposition<T extends ReshapeMatrix64F>
extends DecompositionInterface<T> {
    public T getB(T var1, boolean var2);

    public T getU(T var1, boolean var2, boolean var3);

    public T getV(T var1, boolean var2, boolean var3);

    public void getDiagonal(double[] var1, double[] var2);
}

