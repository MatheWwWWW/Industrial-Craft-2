/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.interfaces.decomposition;

import ic2.shades.org.ejml.data.Matrix64F;
import ic2.shades.org.ejml.interfaces.decomposition.DecompositionInterface;

public interface LUDecomposition<T extends Matrix64F>
extends DecompositionInterface<T> {
    public T getLower(T var1);

    public T getUpper(T var1);

    public T getPivot(T var1);

    public boolean isSingular();

    public double computeDeterminant();
}

