/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.interfaces.decomposition;

import ic2.shades.org.ejml.data.ReshapeMatrix64F;
import ic2.shades.org.ejml.interfaces.decomposition.DecompositionInterface;

public interface TridiagonalSimilarDecomposition<MatrixType extends ReshapeMatrix64F>
extends DecompositionInterface<MatrixType> {
    public MatrixType getT(MatrixType var1);

    public MatrixType getQ(MatrixType var1, boolean var2);

    public void getDiagonal(double[] var1, double[] var2);
}

