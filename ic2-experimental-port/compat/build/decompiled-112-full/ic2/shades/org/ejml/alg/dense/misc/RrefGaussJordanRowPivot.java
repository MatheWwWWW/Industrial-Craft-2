/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.alg.dense.misc;

import ic2.shades.org.ejml.data.DenseMatrix64F;
import ic2.shades.org.ejml.interfaces.linsol.ReducedRowEchelonForm;

public class RrefGaussJordanRowPivot
implements ReducedRowEchelonForm<DenseMatrix64F> {
    double tol;

    @Override
    public void setTolerance(double tol) {
        this.tol = tol;
    }

    @Override
    public void reduce(DenseMatrix64F A, int coefficientColumns) {
        if (A.numCols < coefficientColumns) {
            throw new IllegalArgumentException("The system must be at least as wide as A");
        }
        int leadIndex = 0;
        for (int i = 0; i < coefficientColumns; ++i) {
            int row;
            int pivotRow = -1;
            double maxValue = this.tol;
            for (row = leadIndex; row < A.numRows; ++row) {
                double v = Math.abs(A.data[row * A.numCols + i]);
                if (!(v > maxValue)) continue;
                maxValue = v;
                pivotRow = row;
            }
            if (pivotRow == -1) continue;
            if (leadIndex != pivotRow) {
                RrefGaussJordanRowPivot.swapRows(A, leadIndex, pivotRow);
            }
            for (row = 0; row < A.numRows; ++row) {
                if (row == leadIndex) continue;
                int indexPivot = leadIndex * A.numCols + i;
                int indexTarget = row * A.numCols + i;
                double alpha = A.data[indexTarget] / A.data[indexPivot++];
                A.data[indexTarget++] = 0.0;
                for (int col = i + 1; col < A.numCols; ++col) {
                    int n = indexTarget++;
                    A.data[n] = A.data[n] - A.data[indexPivot++] * alpha;
                }
            }
            int indexPivot = leadIndex * A.numCols + i;
            double alpha = 1.0 / A.data[indexPivot];
            A.data[indexPivot++] = 1.0;
            for (int col = i + 1; col < A.numCols; ++col) {
                int n = indexPivot++;
                A.data[n] = A.data[n] * alpha;
            }
            ++leadIndex;
        }
    }

    protected static void swapRows(DenseMatrix64F A, int rowA, int rowB) {
        int indexA = rowA * A.numCols;
        int indexB = rowB * A.numCols;
        int i = 0;
        while (i < A.numCols) {
            double temp = A.data[indexA];
            A.data[indexA] = A.data[indexB];
            A.data[indexB] = temp;
            ++i;
            ++indexA;
            ++indexB;
        }
    }
}

