/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.alg.dense.decomposition.chol;

import ic2.shades.org.ejml.alg.dense.decomposition.chol.CholeskyDecompositionCommon_D64;

public class CholeskyDecompositionInner_D64
extends CholeskyDecompositionCommon_D64 {
    public CholeskyDecompositionInner_D64() {
        super(true);
    }

    public CholeskyDecompositionInner_D64(boolean lower) {
        super(lower);
    }

    @Override
    protected boolean decomposeLower() {
        int j;
        int i;
        double div_el_ii = 0.0;
        for (i = 0; i < this.n; ++i) {
            for (j = i; j < this.n; ++j) {
                double sum = this.t[i * this.n + j];
                int iEl = i * this.n;
                int jEl = j * this.n;
                int end = iEl + i;
                while (iEl < end) {
                    sum -= this.t[iEl] * this.t[jEl];
                    ++iEl;
                    ++jEl;
                }
                if (i == j) {
                    double el_ii;
                    if (sum <= 0.0) {
                        return false;
                    }
                    this.t[i * this.n + i] = el_ii = Math.sqrt(sum);
                    div_el_ii = 1.0 / el_ii;
                    continue;
                }
                this.t[j * this.n + i] = sum * div_el_ii;
            }
        }
        for (i = 0; i < this.n; ++i) {
            for (j = i + 1; j < this.n; ++j) {
                this.t[i * this.n + j] = 0.0;
            }
        }
        return true;
    }

    @Override
    protected boolean decomposeUpper() {
        int j;
        int i;
        double div_el_ii = 0.0;
        for (i = 0; i < this.n; ++i) {
            for (j = i; j < this.n; ++j) {
                double sum = this.t[i * this.n + j];
                for (int k = 0; k < i; ++k) {
                    sum -= this.t[k * this.n + i] * this.t[k * this.n + j];
                }
                if (i == j) {
                    double el_ii;
                    if (sum <= 0.0) {
                        return false;
                    }
                    this.t[i * this.n + i] = el_ii = Math.sqrt(sum);
                    div_el_ii = 1.0 / el_ii;
                    continue;
                }
                this.t[i * this.n + j] = sum * div_el_ii;
            }
        }
        for (i = 0; i < this.n; ++i) {
            for (j = 0; j < i; ++j) {
                this.t[i * this.n + j] = 0.0;
            }
        }
        return true;
    }
}

