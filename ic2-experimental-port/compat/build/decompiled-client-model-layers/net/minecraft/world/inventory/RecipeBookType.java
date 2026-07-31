/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

public final class RecipeBookType
extends Enum<RecipeBookType> {
    public static final /* enum */ RecipeBookType CRAFTING = new RecipeBookType();
    public static final /* enum */ RecipeBookType FURNACE = new RecipeBookType();
    public static final /* enum */ RecipeBookType BLAST_FURNACE = new RecipeBookType();
    public static final /* enum */ RecipeBookType SMOKER = new RecipeBookType();
    private static final /* synthetic */ RecipeBookType[] $VALUES;

    public static RecipeBookType[] values() {
        return (RecipeBookType[])$VALUES.clone();
    }

    public static RecipeBookType valueOf(String p_40132_) {
        return Enum.valueOf(RecipeBookType.class, p_40132_);
    }

    private static /* synthetic */ RecipeBookType[] m_150636_() {
        return new RecipeBookType[]{CRAFTING, FURNACE, BLAST_FURNACE, SMOKER};
    }

    static {
        $VALUES = RecipeBookType.m_150636_();
    }
}

