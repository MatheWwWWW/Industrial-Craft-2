/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.alg.block.decomposition.chol;

import ic2.shades.org.ejml.data.D1Submatrix64F;

public class InnerCholesky_B64 {
    public static boolean upper(D1Submatrix64F T) {
        int n = T.row1 - T.row0;
        int indexT = T.row0 * T.original.numCols + T.col0 * n;
        return InnerCholesky_B64.upper(T.original.data, indexT, n);
    }

    public static boolean lower(D1Submatrix64F T) {
        int n = T.row1 - T.row0;
        int indexT = T.row0 * T.original.numCols + T.col0 * n;
        return InnerCholesky_B64.lower(T.original.data, indexT, n);
    }

    public static boolean upper(double[] T, int indexT, int n) {
        double div_el_ii = 0.0;
        for (int i = 0; i < n; ++i) {
            for (int j = i; j < n; ++j) {
                double sum = T[indexT + i * n + j];
                for (int k = 0; k < i; ++k) {
                    sum -= T[indexT + k * n + i] * T[indexT + k * n + j];
                }
                if (i == j) {
                    double el_ii;
                    if (sum <= 0.0) {
                        return false;
                    }
                    T[indexT + i * n + i] = el_ii = Math.sqrt(sum);
                    div_el_ii = 1.0 / el_ii;
                    continue;
                }
                T[indexT + i * n + j] = sum * div_el_ii;
            }
        }
        return true;
    }

    public static boolean lower(double[] T, int indexT, int n) {
        double div_el_ii = 0.0;
        for (int i = 0; i < n; ++i) {
            for (int j = i; j < n; ++j) {
                double sum = T[indexT + j * n + i];
                for (int k = 0; k < i; ++k) {
                    sum -= T[indexT + i * n + k] * T[indexT + j * n + k];
                }
                if (i == j) {
                    double el_ii;
                    if (sum <= 0.0) {
                        return false;
                    }
                    T[indexT + i * n + i] = el_ii = Math.sqrt(sum);
                    div_el_ii = 1.0 / el_ii;
                    continue;
                }
                T[indexT + j * n + i] = sum * div_el_ii;
            }
        }
        return true;
    }
}

