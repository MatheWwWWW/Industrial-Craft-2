/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.crops;

public class CropProperties {
    private final int tier;
    private final int chemistry;
    private final int consumable;
    private final int defensive;
    private final int colorful;
    private final int weed;

    public CropProperties(int n, int n2, int n3, int n4, int n5, int n6) {
        this.tier = n;
        this.chemistry = n2;
        this.consumable = n3;
        this.defensive = n4;
        this.colorful = n5;
        this.weed = n6;
    }

    public int getTier() {
        return this.tier;
    }

    public int getChemistry() {
        return this.chemistry;
    }

    public int getConsumable() {
        return this.consumable;
    }

    public int getDefensive() {
        return this.defensive;
    }

    public int getColorful() {
        return this.colorful;
    }

    public int getWeed() {
        return this.weed;
    }

    public int[] getAllProperties() {
        return new int[]{this.getChemistry(), this.getConsumable(), this.getDefensive(), this.getColorful(), this.getWeed()};
    }
}

