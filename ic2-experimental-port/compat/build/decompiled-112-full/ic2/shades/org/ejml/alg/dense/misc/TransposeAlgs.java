/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.alg.dense.misc;

import ic2.shades.org.ejml.data.RowD1Matrix64F;

public class TransposeAlgs {
    public static void square(RowD1Matrix64F mat) {
        int index = 1;
        int indexEnd = mat.numCols;
        int i = 0;
        while (i < mat.numRows) {
            int indexOther = (i + 1) * mat.numCols + i;
            while (index < indexEnd) {
                double val = mat.data[index];
                mat.data[index] = mat.data[indexOther];
                mat.data[indexOther] = val;
                ++index;
                indexOther += mat.numCols;
            }
            index += ++i + 1;
            indexEnd += mat.numCols;
        }
    }

    public static void block(RowD1Matrix64F A, RowD1Matrix64F A_tran, int blockLength) {
        for (int i = 0; i < A.numRows; i += blockLength) {
            int blockHeight = Math.min(blockLength, A.numRows - i);
            int indexSrc = i * A.numCols;
            int indexDst = i;
            for (int j = 0; j < A.numCols; j += blockLength) {
                int blockWidth = Math.min(blockLength, A.numCols - j);
                int indexSrcEnd = indexSrc + blockWidth;
                while (indexSrc < indexSrcEnd) {
                    int rowSrc = indexSrc;
                    int rowDst = indexDst;
                    int end = rowDst + blockHeight;
                    while (rowDst < end) {
                        A_tran.data[rowDst++] = A.data[rowSrc];
                        rowSrc += A.numCols;
                    }
                    indexDst += A_tran.numCols;
                    ++indexSrc;
                }
            }
        }
    }

    public static void standard(RowD1Matrix64F A, RowD1Matrix64F A_tran) {
        int index = 0;
        for (int i = 0; i < A_tran.numRows; ++i) {
            int index2 = i;
            int end = index + A_tran.numCols;
            while (index < end) {
                A_tran.data[index++] = A.data[index2];
                index2 += A.numCols;
            }
        }
    }
}

