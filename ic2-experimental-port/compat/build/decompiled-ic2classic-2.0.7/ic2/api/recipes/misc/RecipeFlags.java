/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package ic2.api.recipes.misc;

import net.minecraft.nbt.CompoundTag;

public enum RecipeFlags {
    CONSUME_CONTAINERS("consume_containers"),
    KEEP_IN_INPUT("keep_in_input"),
    HIDE_RECIPE("hide_recipe");

    String flag_name;

    private RecipeFlags(String flag_name) {
        this.flag_name = flag_name;
    }

    public CompoundTag apply() {
        return this.applyFlag(new CompoundTag(), true);
    }

    public CompoundTag applyFlag(CompoundTag nbt, boolean flag) {
        nbt.m_128379_(this.flag_name, flag);
        return nbt;
    }

    public boolean getFlag(CompoundTag nbt, boolean defaultValue) {
        return nbt.m_128441_(this.flag_name) ? nbt.m_128471_(this.flag_name) : defaultValue;
    }
}

