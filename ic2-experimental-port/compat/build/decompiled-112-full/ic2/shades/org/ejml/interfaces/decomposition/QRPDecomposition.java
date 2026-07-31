/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.interfaces.decomposition;

import ic2.shades.org.ejml.data.DenseMatrix64F;
import ic2.shades.org.ejml.data.Matrix64F;
import ic2.shades.org.ejml.interfaces.decomposition.QRDecomposition;

public interface QRPDecomposition<T extends Matrix64F>
extends QRDecomposition<T> {
    public void setSingularThreshold(double var1);

    public int getRank();

    public int[] getPivots();

    public DenseMatrix64F getPivotMatrix(DenseMatrix64F var1);
}

