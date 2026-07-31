/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.item.tool.material;

import ic2.core.platform.registries.IC2Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class ToolMaterial
implements Tier {
    public static final ToolMaterial BRONZE = new ToolMaterial(350, 6.0f, 2.0f, 2, 13, Ingredient.m_43929_((ItemLike[])new ItemLike[]{IC2Items.INGOT_BRONZE}));
    public static final ToolMaterial ALUMINIUM = new ToolMaterial(700, 10.0f, 2.0f, 2, 13, Ingredient.m_43929_((ItemLike[])new ItemLike[]{IC2Items.INGOT_ALUMINIUM}));
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int harvestLevel;
    private final int enchantability;
    private final Ingredient repairMaterial;

    public ToolMaterial(int maxUses, float efficiency, float attackDamage, int harvestLevel, int enchantability, Ingredient repairMaterial) {
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.harvestLevel = harvestLevel;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    public int m_6609_() {
        return this.maxUses;
    }

    public float m_6624_() {
        return this.efficiency;
    }

    public float m_6631_() {
        return this.attackDamage;
    }

    public int m_6604_() {
        return this.harvestLevel;
    }

    public int m_6601_() {
        return this.enchantability;
    }

    public Ingredient m_6282_() {
        return this.repairMaterial;
    }
}

