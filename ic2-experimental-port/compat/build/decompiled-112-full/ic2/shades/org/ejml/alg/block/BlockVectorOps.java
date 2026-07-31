/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.alg.block;

import ic2.shades.org.ejml.data.D1Submatrix64F;

public class BlockVectorOps {
    public static void scale_row(int blockLength, D1Submatrix64F A, int rowA, double alpha, D1Submatrix64F B, int rowB, int offset, int end) {
        double[] dataA = A.original.data;
        double[] dataB = B.original.data;
        int startI = offset - offset % blockLength;
        offset %= blockLength;
        int rowBlockA = A.row0 + rowA - rowA % blockLength;
        rowA %= blockLength;
        int rowBlockB = B.row0 + rowB - rowB % blockLength;
        rowB %= blockLength;
        int heightA = Math.min(blockLength, A.row1 - rowBlockA);
        int heightB = Math.min(blockLength, B.row1 - rowBlockB);
        for (int i = startI; i < end; i += blockLength) {
            int j;
            int segment = Math.min(blockLength, end - i);
            int widthA = Math.min(blockLength, A.col1 - A.col0 - i);
            int widthB = Math.min(blockLength, B.col1 - B.col0 - i);
            int indexA = rowBlockA * A.original.numCols + (A.col0 + i) * heightA + rowA * widthA;
            int indexB = rowBlockB * B.original.numCols + (B.col0 + i) * heightB + rowB * widthB;
            if (i == startI) {
                indexA += offset;
                indexB += offset;
                for (j = offset; j < segment; ++j) {
                    dataB[indexB++] = alpha * dataA[indexA++];
                }
                continue;
            }
            for (j = 0; j < segment; ++j) {
                dataB[indexB++] = alpha * dataA[indexA++];
            }
        }
    }

    public static void div_row(int blockLength, D1Submatrix64F A, int rowA, double alpha, D1Submatrix64F B, int rowB, int offset, int end) {
        double[] dataA = A.original.data;
        double[] dataB = B.original.data;
        int startI = offset - offset % blockLength;
        offset %= blockLength;
        int rowBlockA = A.row0 + rowA - rowA % blockLength;
        rowA %= blockLength;
        int rowBlockB = B.row0 + rowB - rowB % blockLength;
        rowB %= blockLength;
        int heightA = Math.min(blockLength, A.row1 - rowBlockA);
        int heightB = Math.min(blockLength, B.row1 - rowBlockB);
        for (int i = startI; i < end; i += blockLength) {
            int j;
            int segment = Math.min(blockLength, end - i);
            int widthA = Math.min(blockLength, A.col1 - A.col0 - i);
            int widthB = Math.min(blockLength, B.col1 - B.col0 - i);
            int indexA = rowBlockA * A.original.numCols + (A.col0 + i) * heightA + rowA * widthA;
            int indexB = rowBlockB * B.original.numCols + (B.col0 + i) * heightB + rowB * widthB;
            if (i == startI) {
                indexA += offset;
                indexB += offset;
                for (j = offset; j < segment; ++j) {
                    dataB[indexB++] = dataA[indexA++] / alpha;
                }
                continue;
            }
            for (j = 0; j < segment; ++j) {
                dataB[indexB++] = dataA[indexA++] / alpha;
            }
        }
    }

    public static void add_row(int blockLength, D1Submatrix64F A, int rowA, double alpha, D1Submatrix64F B, int rowB, double beta, D1Submatrix64F C, int rowC, int offset, int end) {
        int heightA = Math.min(blockLength, A.row1 - A.row0);
        int heightB = Math.min(blockLength, B.row1 - B.row0);
        int heightC = Math.min(blockLength, C.row1 - C.row0);
        int startI = offset - offset % blockLength;
        offset %= blockLength;
        double[] dataA = A.original.data;
        double[] dataB = B.original.data;
        double[] dataC = C.original.data;
        for (int i = startI; i < end; i += blockLength) {
            int j;
            int segment = Math.min(blockLength, end - i);
            int widthA = Math.min(blockLength, A.col1 - A.col0 - i);
            int widthB = Math.min(blockLength, B.col1 - B.col0 - i);
            int widthC = Math.min(blockLength, C.col1 - C.col0 - i);
            int indexA = A.row0 * A.original.numCols + (A.col0 + i) * heightA + rowA * widthA;
            int indexB = B.row0 * B.original.numCols + (B.col0 + i) * heightB + rowB * widthB;
            int indexC = C.row0 * C.original.numCols + (C.col0 + i) * heightC + rowC * widthC;
            if (i == startI) {
                indexA += offset;
                indexB += offset;
                indexC += offset;
                for (j = offset; j < segment; ++j) {
                    dataC[indexC++] = alpha * dataA[indexA++] + beta * dataB[indexB++];
                }
                continue;
            }
            for (j = 0; j < segment; ++j) {
                dataC[indexC++] = alpha * dataA[indexA++] + beta * dataB[indexB++];
            }
        }
    }

    public static double dot_row(int blockLength, D1Submatrix64F A, int rowA, D1Submatrix64F B, int rowB, int offset, int end) {
        int startI = offset - offset % blockLength;
        offset %= blockLength;
        double[] dataA = A.original.data;
        double[] dataB = B.original.data;
        double total = 0.0;
        int rowBlockA = A.row0 + rowA - rowA % blockLength;
        rowA %= blockLength;
        int rowBlockB = B.row0 + rowB - rowB % blockLength;
        rowB %= blockLength;
        int heightA = Math.min(blockLength, A.row1 - rowBlockA);
        int heightB = Math.min(blockLength, B.row1 - rowBlockB);
        if (A.col1 - A.col0 != B.col1 - B.col0) {
            throw new RuntimeException();
        }
        for (int i = startI; i < end; i += blockLength) {
            int j;
            int segment = Math.min(blockLength, end - i);
            int widthA = Math.min(blockLength, A.col1 - A.col0 - i);
            int widthB = Math.min(blockLength, B.col1 - B.col0 - i);
            int indexA = rowBlockA * A.original.numCols + (A.col0 + i) * heightA + rowA * widthA;
            int indexB = rowBlockB * B.original.numCols + (B.col0 + i) * heightB + rowB * widthB;
            if (i == startI) {
                indexA += offset;
                indexB += offset;
                for (j = offset; j < segment; ++j) {
                    total += dataB[indexB++] * dataA[indexA++];
                }
                continue;
            }
            for (j = 0; j < segment; ++j) {
                total += dataB[indexB++] * dataA[indexA++];
            }
        }
        return total;
    }

    public static double dot_row_col(int blockLength, D1Submatrix64F A, int rowA, D1Submatrix64F B, int colB, int offset, int end) {
        int startI = offset - offset % blockLength;
        offset %= blockLength;
        double[] dataA = A.original.data;
        double[] dataB = B.original.data;
        double total = 0.0;
        int rowBlockA = A.row0 + rowA - rowA % blockLength;
        rowA %= blockLength;
        int colBlockB = B.col0 + colB - colB % blockLength;
        colB %= blockLength;
        int heightA = Math.min(blockLength, A.row1 - rowBlockA);
        int widthB = Math.min(blockLength, B.col1 - colBlockB);
        if (A.col1 - A.col0 != B.col1 - B.col0) {
            throw new RuntimeException();
        }
        for (int i = startI; i < end; i += blockLength) {
            int j;
            int segment = Math.min(blockLength, end - i);
            int widthA = Math.min(blockLength, A.col1 - A.col0 - i);
            int heightB = Math.min(blockLength, B.row1 - B.row0 - i);
            int indexA = rowBlockA * A.original.numCols + (A.col0 + i) * heightA + rowA * widthA;
            int indexB = (B.row0 + i) * B.original.numCols + colBlockB * heightB + colB;
            if (i == startI) {
                indexA += offset;
                indexB += offset * widthB;
                j = offset;
                while (j < segment) {
                    total += dataB[indexB] * dataA[indexA++];
                    ++j;
                    indexB += widthB;
                }
                continue;
            }
            j = 0;
            while (j < segment) {
                total += dataB[indexB] * dataA[indexA++];
                ++j;
                indexB += widthB;
            }
        }
        return total;
    }
}

