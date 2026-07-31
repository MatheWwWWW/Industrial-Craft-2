/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.data;

import ic2.shades.org.ejml.data.DenseMatrix64F;

public class Eigenpair {
    public double value;
    public DenseMatrix64F vector;

    public Eigenpair(double value, DenseMatrix64F vector) {
        this.value = value;
        this.vector = vector;
    }
}

