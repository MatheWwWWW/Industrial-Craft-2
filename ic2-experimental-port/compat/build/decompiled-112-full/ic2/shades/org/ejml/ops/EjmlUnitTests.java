/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.ops;

import ic2.shades.org.ejml.data.ReshapeMatrix64F;

public class EjmlUnitTests {
    public static void assertCountable(ReshapeMatrix64F A) {
        for (int i = 0; i < A.numRows; ++i) {
            for (int j = 0; j < A.numCols; ++j) {
                EjmlUnitTests.assertTrue(!Double.isNaN(A.get(i, j)), "NaN found at " + i + " " + j);
                EjmlUnitTests.assertTrue(!Double.isInfinite(A.get(i, j)), "Infinite found at " + i + " " + j);
            }
        }
    }

    public static void assertShape(ReshapeMatrix64F A, ReshapeMatrix64F B) {
        EjmlUnitTests.assertTrue(A.numRows == B.numRows, "Number of rows do not match");
        EjmlUnitTests.assertTrue(A.numCols == B.numCols, "Number of columns do not match");
    }

    public static void assertShape(ReshapeMatrix64F A, int numRows, int numCols) {
        EjmlUnitTests.assertTrue(A.numRows == numRows, "Unexpected number of rows.");
        EjmlUnitTests.assertTrue(A.numCols == numCols, "Unexpected number of columns.");
    }

    public static void assertEqualsUncountable(ReshapeMatrix64F A, ReshapeMatrix64F B, double tol) {
        EjmlUnitTests.assertShape(A, B);
        for (int i = 0; i < A.numRows; ++i) {
            for (int j = 0; j < A.numCols; ++j) {
                double valA = A.get(i, j);
                double valB = B.get(i, j);
                if (Double.isNaN(valA)) {
                    EjmlUnitTests.assertTrue(Double.isNaN(valB), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
                    continue;
                }
                if (Double.isInfinite(valA)) {
                    EjmlUnitTests.assertTrue(Double.isInfinite(valB), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
                    continue;
                }
                double diff = Math.abs(valA - valB);
                EjmlUnitTests.assertTrue(diff <= tol, "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
            }
        }
    }

    public static void assertEquals(ReshapeMatrix64F A, ReshapeMatrix64F B, double tol) {
        EjmlUnitTests.assertShape(A, B);
        for (int i = 0; i < A.numRows; ++i) {
            for (int j = 0; j < A.numCols; ++j) {
                double valA = A.get(i, j);
                double valB = B.get(i, j);
                EjmlUnitTests.assertTrue(!Double.isNaN(valA) && !Double.isNaN(valB), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
                EjmlUnitTests.assertTrue(!Double.isInfinite(valA) && !Double.isInfinite(valB), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
                EjmlUnitTests.assertTrue(Math.abs(valA - valB) <= tol, "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
            }
        }
    }

    public static void assertEqualsTrans(ReshapeMatrix64F A, ReshapeMatrix64F B, double tol) {
        EjmlUnitTests.assertShape(A, B.numCols, B.numRows);
        for (int i = 0; i < A.numRows; ++i) {
            for (int j = 0; j < A.numCols; ++j) {
                double valA = A.get(i, j);
                double valB = B.get(j, i);
                EjmlUnitTests.assertTrue(!Double.isNaN(valA) && !Double.isNaN(valB), "A(" + i + "," + j + ") = " + valA + ") B(" + j + "," + i + ") = " + valB);
                EjmlUnitTests.assertTrue(!Double.isInfinite(valA) && !Double.isInfinite(valB), "A(" + i + "," + j + ") = " + valA + ") B(" + j + "," + i + ") = " + valB);
                EjmlUnitTests.assertTrue(Math.abs(valA - valB) <= tol, "A(" + i + "," + j + ") = " + valA + ") B(" + j + "," + i + ") = " + valB);
            }
        }
    }

    private static void assertTrue(boolean result, String message) {
        assert (result) : message;
        if (!result) {
            throw new TestException(message);
        }
    }

    public static class TestException
    extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }
}

