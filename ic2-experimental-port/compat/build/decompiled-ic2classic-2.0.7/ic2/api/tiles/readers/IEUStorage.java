/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.tiles.readers;

public interface IEUStorage {
    public int getStoredEU();

    public int getMaxEU();

    public int getTier();

    default public double getChargeLevel() {
        return (double)this.getStoredEU() / (double)this.getMaxEU();
    }
}

