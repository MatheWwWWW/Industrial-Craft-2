/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.alg.dense.linsol.qr;

import ic2.shades.org.ejml.alg.dense.decomposition.TriangularSolver;
import ic2.shades.org.ejml.alg.dense.decomposition.qr.QRDecompositionHouseholder_D64;
import ic2.shades.org.ejml.alg.dense.linsol.LinearSolverAbstract;
import ic2.shades.org.ejml.data.DenseMatrix64F;
import ic2.shades.org.ejml.ops.SpecializedOps;

public class LinearSolverQrHouse
extends LinearSolverAbstract {
    private QRDecompositionHouseholder_D64 decomposer = new QRDecompositionHouseholder_D64();
    private double[] a;
    private double[] u;
    private int maxRows = -1;
    private DenseMatrix64F QR;
    private double[] gammas;

    public void setMaxSize(int maxRows) {
        this.maxRows = maxRows;
        this.a = new double[maxRows];
        this.u = new double[maxRows];
    }

    @Override
    public boolean setA(DenseMatrix64F A) {
        if (A.numRows > this.maxRows) {
            this.setMaxSize(A.numRows);
        }
        this._setA(A);
        if (!this.decomposer.decompose(A)) {
            return false;
        }
        this.gammas = this.decomposer.getGammas();
        this.QR = this.decomposer.getQR();
        return true;
    }

    @Override
    public double quality() {
        return SpecializedOps.qualityTriangular(true, this.QR);
    }

    @Override
    public void solve(DenseMatrix64F B, DenseMatrix64F X) {
        if (X.numRows != this.numCols) {
            throw new IllegalArgumentException("Unexpected dimensions for X");
        }
        if (B.numRows != this.numRows || B.numCols != X.numCols) {
            throw new IllegalArgumentException("Unexpected dimensions for B");
        }
        int BnumCols = B.numCols;
        for (int colB = 0; colB < BnumCols; ++colB) {
            int i;
            for (i = 0; i < this.numRows; ++i) {
                this.a[i] = B.data[i * BnumCols + colB];
            }
            for (int n = 0; n < this.numCols; ++n) {
                int i2;
                this.u[n] = 1.0;
                double ub = this.a[n];
                for (i2 = n + 1; i2 < this.numRows; ++i2) {
                    this.u[i2] = this.QR.unsafe_get(i2, n);
                    ub += this.u[i2] * this.a[i2];
                }
                ub *= this.gammas[n];
                for (i2 = n; i2 < this.numRows; ++i2) {
                    int n2 = i2;
                    this.a[n2] = this.a[n2] - this.u[i2] * ub;
                }
            }
            TriangularSolver.solveU(this.QR.data, this.a, this.numCols);
            for (i = 0; i < this.numCols; ++i) {
                X.data[i * X.numCols + colB] = this.a[i];
            }
        }
    }

    @Override
    public boolean modifiesA() {
        return false;
    }

    @Override
    public boolean modifiesB() {
        return false;
    }
}

