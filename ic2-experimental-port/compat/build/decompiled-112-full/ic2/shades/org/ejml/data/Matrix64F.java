/*
 * Decompiled with CFR 0.152.
 */
package ic2.shades.org.ejml.data;

import java.io.Serializable;

public interface Matrix64F
extends Serializable {
    public double get(int var1, int var2);

    public double unsafe_get(int var1, int var2);

    public void set(int var1, int var2, double var3);

    public void unsafe_set(int var1, int var2, double var3);

    public int getNumRows();

    public int getNumCols();

    public int getNumElements();

    public <T extends Matrix64F> T copy();

    public void print();
}

