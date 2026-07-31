/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.alg.dense.decomposition.eig;

import ic2.shades.org.ejml.data.Complex64F;
import ic2.shades.org.ejml.data.DenseMatrix64F;

public interface EigenvalueExtractor {
    public boolean process(DenseMatrix64F var1);

    public int getNumberOfEigenvalues();

    public Complex64F[] getEigenvalues();
}

