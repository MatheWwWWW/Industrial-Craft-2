/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package ic2.api.recipes.misc;

import net.minecraft.nbt.CompoundTag;

public enum RecipeMods {
    ENERGY_USAGE("energy_mod", "energy_add"),
    RECIPE_TIME("time_mod", "time_add");

    String mod_tag;
    String add_tag;

    private RecipeMods(String mod_tag, String add_tag) {
        this.mod_tag = mod_tag;
        this.add_tag = add_tag;
    }

    public int apply(CompoundTag compound, int base) {
        double mod = compound.m_128441_(this.mod_tag) ? compound.m_128459_(this.mod_tag) : 1.0;
        long ret = Math.round((double)(base + compound.m_128451_(this.add_tag)) * mod);
        return Math.max(1, (int)(ret > Integer.MAX_VALUE ? Integer.MAX_VALUE : ret));
    }

    public CompoundTag create(CompoundTag compound, double mod, int add) {
        compound.m_128405_(this.add_tag, add);
        compound.m_128347_(this.mod_tag, mod);
        return compound;
    }

    public CompoundTag create(double mod, int add) {
        return this.create(new CompoundTag(), mod, add);
    }

    public CompoundTag create(CompoundTag compound, double mod) {
        compound.m_128347_(this.mod_tag, mod);
        return compound;
    }

    public CompoundTag create(double mod) {
        return this.create(new CompoundTag(), mod);
    }

    public CompoundTag create(CompoundTag compound, int add) {
        compound.m_128405_(this.add_tag, add);
        return compound;
    }

    public CompoundTag create(int add) {
        return this.create(new CompoundTag(), add);
    }

    public static int apply(int min, int base, int extra, double multiplier) {
        long ret = Math.round((double)(base + extra) * multiplier);
        return Math.max(min, (int)(ret > Integer.MAX_VALUE ? Integer.MAX_VALUE : ret));
    }

    public static int apply(int base, int extra, double multiplier) {
        long ret = Math.round((double)(base + extra) * multiplier);
        return Math.max(1, (int)(ret > Integer.MAX_VALUE ? Integer.MAX_VALUE : ret));
    }

    public static float apply(float min, float base, float extra, double multiplier) {
        double ret = (double)(base + extra) * multiplier;
        return Math.max(min, (float)(ret > 2.147483647E9 ? 2.147483647E9 : ret));
    }

    public static float apply(float base, float extra, double multiplier) {
        double ret = (double)(base + extra) * multiplier;
        return Math.max(1.0f, (float)(ret > 2.147483647E9 ? 2.147483647E9 : ret));
    }
}

