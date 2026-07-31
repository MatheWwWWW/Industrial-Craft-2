/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.alg.block;

public class BlockInnerMultiplication {
    public static void blockMultPlus(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int a = indexA;
        int rowC = indexC;
        int i = 0;
        while (i < heightA) {
            int b = indexB;
            int endC = rowC + widthC;
            int endA = a + widthA;
            while (a != endA) {
                double valA = dataA[a++];
                int c = rowC;
                while (c != endC) {
                    int n = c++;
                    dataC[n] = dataC[n] + valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultPlusTransA(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int rowC = indexC;
        int i = 0;
        while (i < widthA) {
            int colA;
            int endA = colA + widthA * heightA;
            int b = indexB;
            for (colA = i + indexA; colA != endA; colA += widthA) {
                double valA = dataA[colA];
                int c = rowC;
                int endB = b + widthC;
                while (b != endB) {
                    int n = c++;
                    dataC[n] = dataC[n] + valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultPlusTransB(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        for (int i = 0; i < heightA; ++i) {
            for (int j = 0; j < widthC; ++j) {
                double val = 0.0;
                for (int k = 0; k < widthA; ++k) {
                    val += dataA[i * widthA + k + indexA] * dataB[j * widthA + k + indexB];
                }
                int n = i * widthC + j + indexC;
                dataC[n] = dataC[n] + val;
            }
        }
    }

    public static void blockMultMinus(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int a = indexA;
        int rowC = indexC;
        int i = 0;
        while (i < heightA) {
            int b = indexB;
            int endC = rowC + widthC;
            int endA = a + widthA;
            while (a != endA) {
                double valA = dataA[a++];
                int c = rowC;
                while (c != endC) {
                    int n = c++;
                    dataC[n] = dataC[n] - valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultMinusTransA(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int rowC = indexC;
        int i = 0;
        while (i < widthA) {
            int colA;
            int endA = colA + widthA * heightA;
            int b = indexB;
            for (colA = i + indexA; colA != endA; colA += widthA) {
                double valA = dataA[colA];
                int c = rowC;
                int endB = b + widthC;
                while (b != endB) {
                    int n = c++;
                    dataC[n] = dataC[n] - valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultMinusTransB(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        for (int i = 0; i < heightA; ++i) {
            for (int j = 0; j < widthC; ++j) {
                double val = 0.0;
                for (int k = 0; k < widthA; ++k) {
                    val += dataA[i * widthA + k + indexA] * dataB[j * widthA + k + indexB];
                }
                int n = i * widthC + j + indexC;
                dataC[n] = dataC[n] - val;
            }
        }
    }

    public static void blockMultSet(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int a = indexA;
        int rowC = indexC;
        int i = 0;
        while (i < heightA) {
            int b = indexB;
            int endC = rowC + widthC;
            int endA = a + widthA;
            while (a != endA) {
                double valA = dataA[a++];
                int c = rowC;
                if (b == indexB) {
                    while (c != endC) {
                        dataC[c++] = valA * dataB[b++];
                    }
                    continue;
                }
                while (c != endC) {
                    int n = c++;
                    dataC[n] = dataC[n] + valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultSetTransA(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int rowC = indexC;
        int i = 0;
        while (i < widthA) {
            int colA;
            int endA = colA + widthA * heightA;
            int b = indexB;
            for (colA = i + indexA; colA != endA; colA += widthA) {
                double valA = dataA[colA];
                int c = rowC;
                int endB = b + widthC;
                if (b == indexB) {
                    while (b != endB) {
                        dataC[c++] = valA * dataB[b++];
                    }
                    continue;
                }
                while (b != endB) {
                    int n = c++;
                    dataC[n] = dataC[n] + valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultSetTransB(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        for (int i = 0; i < heightA; ++i) {
            for (int j = 0; j < widthC; ++j) {
                double val = 0.0;
                for (int k = 0; k < widthA; ++k) {
                    val += dataA[i * widthA + k + indexA] * dataB[j * widthA + k + indexB];
                }
                dataC[i * widthC + j + indexC] = val;
            }
        }
    }

    public static void blockMultPlus(double alpha, double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int a = indexA;
        int rowC = indexC;
        int i = 0;
        while (i < heightA) {
            int b = indexB;
            int endC = rowC + widthC;
            int endA = a + widthA;
            while (a != endA) {
                double valA = alpha * dataA[a++];
                int c = rowC;
                while (c != endC) {
                    int n = c++;
                    dataC[n] = dataC[n] + valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultPlusTransA(double alpha, double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int rowC = indexC;
        int i = 0;
        while (i < widthA) {
            int colA;
            int endA = colA + widthA * heightA;
            int b = indexB;
            for (colA = i + indexA; colA != endA; colA += widthA) {
                double valA = alpha * dataA[colA];
                int c = rowC;
                int endB = b + widthC;
                while (b != endB) {
                    int n = c++;
                    dataC[n] = dataC[n] + valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultPlusTransB(double alpha, double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        for (int i = 0; i < heightA; ++i) {
            for (int j = 0; j < widthC; ++j) {
                double val = 0.0;
                for (int k = 0; k < widthA; ++k) {
                    val += dataA[i * widthA + k + indexA] * dataB[j * widthA + k + indexB];
                }
                int n = i * widthC + j + indexC;
                dataC[n] = dataC[n] + alpha * val;
            }
        }
    }

    public static void blockMultSet(double alpha, double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int a = indexA;
        int rowC = indexC;
        int i = 0;
        while (i < heightA) {
            int b = indexB;
            int endC = rowC + widthC;
            int endA = a + widthA;
            while (a != endA) {
                double valA = alpha * dataA[a++];
                int c = rowC;
                if (b == indexB) {
                    while (c != endC) {
                        dataC[c++] = valA * dataB[b++];
                    }
                    continue;
                }
                while (c != endC) {
                    int n = c++;
                    dataC[n] = dataC[n] + valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultSetTransA(double alpha, double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int rowC = indexC;
        int i = 0;
        while (i < widthA) {
            int colA;
            int endA = colA + widthA * heightA;
            int b = indexB;
            for (colA = i + indexA; colA != endA; colA += widthA) {
                double valA = alpha * dataA[colA];
                int c = rowC;
                int endB = b + widthC;
                if (b == indexB) {
                    while (b != endB) {
                        dataC[c++] = valA * dataB[b++];
                    }
                    continue;
                }
                while (b != endB) {
                    int n = c++;
                    dataC[n] = dataC[n] + valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultSetTransB(double alpha, double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        for (int i = 0; i < heightA; ++i) {
            for (int j = 0; j < widthC; ++j) {
                double val = 0.0;
                for (int k = 0; k < widthA; ++k) {
                    val += dataA[i * widthA + k + indexA] * dataB[j * widthA + k + indexB];
                }
                dataC[i * widthC + j + indexC] = alpha * val;
            }
        }
    }
}

