/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.ref;

import ic2.core.ref.Ic2Items;
import java.util.function.Supplier;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public enum Ic2ToolMaterials implements Tier
{
    BRONZE(2, 350, 6.0f, 2.0f, 14, () -> Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.BRONZE_INGOT})),
    CHAINSAW(3, 250, 12.0f, 9.0f, 14, Ingredient::m_151265_);

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    private Ic2ToolMaterials(int n2, int n3, float f, float f2, int n4, Supplier<Ingredient> supplier) {
        this.miningLevel = n2;
        this.itemDurability = n3;
        this.miningSpeed = f;
        this.attackDamage = f2;
        this.enchantability = n4;
        this.repairIngredient = supplier;
    }

    public int m_6609_() {
        return this.itemDurability;
    }

    public float m_6624_() {
        return this.miningSpeed;
    }

    public float m_6631_() {
        return this.attackDamage;
    }

    public int m_6604_() {
        return this.miningLevel;
    }

    public int m_6601_() {
        return this.enchantability;
    }

    public Ingredient m_6282_() {
        return this.repairIngredient.get();
    }
}

