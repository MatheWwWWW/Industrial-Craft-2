/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.interfaces.decomposition;

import ic2.shades.org.ejml.data.Matrix64F;
import ic2.shades.org.ejml.interfaces.decomposition.DecompositionInterface;

public interface CholeskyDecomposition<MatrixType extends Matrix64F>
extends DecompositionInterface<MatrixType> {
    public boolean isLower();

    public MatrixType getT(MatrixType var1);
}

