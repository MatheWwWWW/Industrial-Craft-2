/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.alg.dense.linsol;

import ic2.shades.org.ejml.data.DenseMatrix64F;
import ic2.shades.org.ejml.interfaces.linsol.LinearSolver;

public interface AdjustableLinearSolver
extends LinearSolver<DenseMatrix64F> {
    public boolean addRowToA(double[] var1, int var2);

    public boolean removeRowFromA(int var1);
}

