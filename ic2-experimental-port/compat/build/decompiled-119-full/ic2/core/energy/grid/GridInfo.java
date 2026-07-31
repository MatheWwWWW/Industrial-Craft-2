/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.energy.grid;

public class GridInfo {
    public final int id;
    public final int nodeCount;
    public final int complexNodeCount;
    public final int minX;
    public final int minY;
    public final int minZ;
    public final int maxX;
    public final int maxY;
    public final int maxZ;

    public GridInfo(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        this.id = n;
        this.nodeCount = n2;
        this.complexNodeCount = n3;
        this.minX = n4;
        this.minY = n5;
        this.minZ = n6;
        this.maxX = n7;
        this.maxY = n8;
        this.maxZ = n9;
    }
}

