/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.alg.dense.linsol.lu;

import ic2.shades.org.ejml.alg.dense.decomposition.lu.LUDecompositionBase_D64;
import ic2.shades.org.ejml.alg.dense.linsol.lu.LinearSolverLuBase;
import ic2.shades.org.ejml.data.DenseMatrix64F;
import ic2.shades.org.ejml.ops.SpecializedOps;

public class LinearSolverLuKJI
extends LinearSolverLuBase {
    private double[] dataLU;
    private int[] pivot;

    public LinearSolverLuKJI(LUDecompositionBase_D64 decomp) {
        super(decomp);
    }

    @Override
    public boolean setA(DenseMatrix64F A) {
        boolean ret = super.setA(A);
        this.pivot = this.decomp.getPivot();
        this.dataLU = this.decomp.getLU().data;
        return ret;
    }

    @Override
    public void solve(DenseMatrix64F b, DenseMatrix64F x) {
        int j;
        int i;
        int k;
        if (b.numCols != x.numCols || b.numRows != this.numRows || x.numRows != this.numCols) {
            throw new IllegalArgumentException("Unexpected matrix size");
        }
        if (b == x) {
            throw new IllegalArgumentException("Current doesn't support using the same matrix instance");
        }
        SpecializedOps.copyChangeRow(this.pivot, b, x);
        int nx = b.numCols;
        double[] dataX = x.data;
        for (k = 0; k < this.numCols; ++k) {
            for (i = k + 1; i < this.numCols; ++i) {
                for (j = 0; j < nx; ++j) {
                    int n = i * nx + j;
                    dataX[n] = dataX[n] - dataX[k * nx + j] * this.dataLU[i * this.numCols + k];
                }
            }
        }
        for (k = this.numCols - 1; k >= 0; --k) {
            for (int j2 = 0; j2 < nx; ++j2) {
                int n = k * nx + j2;
                dataX[n] = dataX[n] / this.dataLU[k * this.numCols + k];
            }
            for (i = 0; i < k; ++i) {
                for (j = 0; j < nx; ++j) {
                    int n = i * nx + j;
                    dataX[n] = dataX[n] - dataX[k * nx + j] * this.dataLU[i * this.numCols + k];
                }
            }
        }
    }
}

