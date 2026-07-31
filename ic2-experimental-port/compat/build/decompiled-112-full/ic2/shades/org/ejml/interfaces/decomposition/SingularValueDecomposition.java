/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.interfaces.decomposition;

import ic2.shades.org.ejml.data.Matrix64F;
import ic2.shades.org.ejml.interfaces.decomposition.DecompositionInterface;

public interface SingularValueDecomposition<T extends Matrix64F>
extends DecompositionInterface<T> {
    public double[] getSingularValues();

    public int numberOfSingularValues();

    public boolean isCompact();

    public T getU(T var1, boolean var2);

    public T getV(T var1, boolean var2);

    public T getW(T var1);

    public int numRows();

    public int numCols();
}

