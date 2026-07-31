/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.interfaces.linsol;

import ic2.shades.org.ejml.data.Matrix64F;

public interface LinearSolver<T extends Matrix64F> {
    public boolean setA(T var1);

    public double quality();

    public void solve(T var1, T var2);

    public void invert(T var1);

    public boolean modifiesA();

    public boolean modifiesB();
}

