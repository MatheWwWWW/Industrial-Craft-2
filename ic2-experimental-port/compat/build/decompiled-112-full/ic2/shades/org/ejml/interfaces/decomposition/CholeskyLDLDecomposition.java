/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.interfaces.decomposition;

import ic2.shades.org.ejml.data.Matrix64F;
import ic2.shades.org.ejml.interfaces.decomposition.DecompositionInterface;

public interface CholeskyLDLDecomposition<MatrixType extends Matrix64F>
extends DecompositionInterface<MatrixType> {
    public MatrixType getL(MatrixType var1);

    public double[] getDiagonal();

    public MatrixType getD(MatrixType var1);
}

