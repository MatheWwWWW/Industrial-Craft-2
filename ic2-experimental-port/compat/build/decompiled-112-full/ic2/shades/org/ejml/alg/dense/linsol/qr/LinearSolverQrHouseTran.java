/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.alg.dense.linsol.qr;

import ic2.shades.org.ejml.alg.dense.decomposition.TriangularSolver;
import ic2.shades.org.ejml.alg.dense.decomposition.qr.QRDecompositionHouseholderTran_D64;
import ic2.shades.org.ejml.alg.dense.linsol.LinearSolverAbstract;
import ic2.shades.org.ejml.data.DenseMatrix64F;
import ic2.shades.org.ejml.ops.SpecializedOps;

public class LinearSolverQrHouseTran
extends LinearSolverAbstract {
    private QRDecompositionHouseholderTran_D64 decomposer = new QRDecompositionHouseholderTran_D64();
    private double[] a;
    protected int maxRows = -1;
    protected int maxCols = -1;
    private DenseMatrix64F QR;
    private DenseMatrix64F U;

    public void setMaxSize(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
        this.a = new double[maxRows];
    }

    @Override
    public boolean setA(DenseMatrix64F A) {
        if (A.numRows > this.maxRows || A.numCols > this.maxCols) {
            this.setMaxSize(A.numRows, A.numCols);
        }
        this._setA(A);
        if (!this.decomposer.decompose(A)) {
            return false;
        }
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
            throw new IllegalArgumentException("Unexpected dimensions for X: X rows = " + X.numRows + " expected = " + this.numCols);
        }
        if (B.numRows != this.numRows || B.numCols != X.numCols) {
            throw new IllegalArgumentException("Unexpected dimensions for B");
        }
        this.U = this.decomposer.getR(this.U, true);
        double[] gammas = this.decomposer.getGammas();
        double[] dataQR = this.QR.data;
        int BnumCols = B.numCols;
        for (int colB = 0; colB < BnumCols; ++colB) {
            int i;
            for (i = 0; i < this.numRows; ++i) {
                this.a[i] = B.data[i * BnumCols + colB];
            }
            for (int n = 0; n < this.numCols; ++n) {
                int indexU = n * this.numRows + n + 1;
                double ub = this.a[n];
                int i2 = n + 1;
                while (i2 < this.numRows) {
                    ub += dataQR[indexU] * this.a[i2];
                    ++i2;
                    ++indexU;
                }
                int n2 = n;
                this.a[n2] = this.a[n2] - (ub *= gammas[n]);
                indexU = n * this.numRows + n + 1;
                i2 = n + 1;
                while (i2 < this.numRows) {
                    int n3 = i2++;
                    this.a[n3] = this.a[n3] - dataQR[indexU] * ub;
                    ++indexU;
                }
            }
            TriangularSolver.solveU(this.U.data, this.a, this.numCols);
            for (i = 0; i < this.numCols; ++i) {
                X.data[i * X.numCols + colB] = this.a[i];
            }
        }
    }

    @Override
    public boolean modifiesA() {
        return this.decomposer.inputModified();
    }

    @Override
    public boolean modifiesB() {
        return false;
    }
}

