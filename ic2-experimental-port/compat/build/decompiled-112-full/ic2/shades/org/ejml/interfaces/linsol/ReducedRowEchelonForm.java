/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.interfaces.linsol;

import ic2.shades.org.ejml.data.Matrix64F;

public interface ReducedRowEchelonForm<T extends Matrix64F> {
    public void reduce(T var1, int var2);

    public void setTolerance(double var1);
}

