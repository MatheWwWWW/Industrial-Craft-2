/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.alg.dense.decomposition.bidiagonal;

import ic2.shades.org.ejml.data.DenseMatrix64F;
import ic2.shades.org.ejml.ops.SpecializedOps;
import ic2.shades.org.ejml.simple.SimpleMatrix;

public class BidiagonalDecompositionNaive_D64 {
    private SimpleMatrix U;
    private SimpleMatrix B;
    private SimpleMatrix V;
    private int m;
    private int n;
    private int min;
    DenseMatrix64F u;

    public SimpleMatrix getU() {
        return this.U;
    }

    public SimpleMatrix getB() {
        return this.B;
    }

    public SimpleMatrix getV() {
        return this.V;
    }

    public boolean decompose(DenseMatrix64F A) {
        this.init(A);
        return this._decompose();
    }

    protected void init(DenseMatrix64F A) {
        this.m = A.numRows;
        this.n = A.numCols;
        this.min = Math.min(this.m, this.n);
        this.U = SimpleMatrix.identity(this.m);
        this.B = new SimpleMatrix(A);
        this.V = SimpleMatrix.identity(this.n);
        int max = Math.max(this.m, this.n);
        this.u = new DenseMatrix64F(max, 1);
    }

    private boolean _decompose() {
        for (int k = 0; k < this.min; ++k) {
            this.computeU(k);
            this.computeV(k);
        }
        return true;
    }

    protected void computeU(int k) {
        this.u.reshape(this.m, 1, false);
        double[] u = this.u.data;
        double max = 0.0;
        for (int i = k; i < this.m; ++i) {
            double val = u[i] = this.B.get(i, k);
            if (!((val = Math.abs(val)) > max)) continue;
            max = val;
        }
        if (max > 0.0) {
            double tau = 0.0;
            int i = k;
            while (i < this.m) {
                int n = i++;
                double d = u[n] / max;
                u[n] = d;
                double val = d;
                tau += val * val;
            }
            tau = Math.sqrt(tau);
            if (u[k] < 0.0) {
                tau = -tau;
            }
            double nu = u[k] + tau;
            u[k] = 1.0;
            int i2 = k + 1;
            while (i2 < this.m) {
                int n = i2++;
                u[n] = u[n] / nu;
            }
            SimpleMatrix Q_k = SimpleMatrix.wrap(SpecializedOps.createReflector(this.u, nu / tau));
            this.U = this.U.mult(Q_k);
            this.B = Q_k.mult(this.B);
        }
    }

    protected void computeV(int k) {
        this.u.reshape(this.n, 1, false);
        this.u.zero();
        double[] u = this.u.data;
        double max = 0.0;
        for (int i = k + 1; i < this.n; ++i) {
            double val = u[i] = this.B.get(k, i);
            if (!((val = Math.abs(val)) > max)) continue;
            max = val;
        }
        if (max > 0.0) {
            double tau = 0.0;
            int i = k + 1;
            while (i < this.n) {
                int n = i++;
                double d = u[n] / max;
                u[n] = d;
                double val = d;
                tau += val * val;
            }
            tau = Math.sqrt(tau);
            if (u[k + 1] < 0.0) {
                tau = -tau;
            }
            double nu = u[k + 1] + tau;
            u[k + 1] = 1.0;
            int i2 = k + 2;
            while (i2 < this.n) {
                int n = i2++;
                u[n] = u[n] / nu;
            }
            SimpleMatrix Q_k = SimpleMatrix.wrap(SpecializedOps.createReflector(this.u, nu / tau));
            this.V = this.V.mult(Q_k);
            this.B = this.B.mult(Q_k);
        }
    }
}

