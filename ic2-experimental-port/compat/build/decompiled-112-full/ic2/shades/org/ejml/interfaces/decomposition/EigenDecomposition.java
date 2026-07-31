/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.interfaces.decomposition;

import ic2.shades.org.ejml.data.Complex64F;
import ic2.shades.org.ejml.data.Matrix64F;
import ic2.shades.org.ejml.interfaces.decomposition.DecompositionInterface;

public interface EigenDecomposition<MatrixType extends Matrix64F>
extends DecompositionInterface<MatrixType> {
    public int getNumberOfEigenvalues();

    public Complex64F getEigenvalue(int var1);

    public MatrixType getEigenVector(int var1);
}

