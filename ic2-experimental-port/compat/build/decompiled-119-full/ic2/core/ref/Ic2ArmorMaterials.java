/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Suppliers
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.item.ArmorMaterial
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.ref;

import com.google.common.base.Suppliers;
import ic2.core.ref.Ic2Items;
import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public enum Ic2ArmorMaterials implements ArmorMaterial
{
    BRONZE("ic2_bronze", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.f_11677_, 0.0f, 0.0f, () -> Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.BRONZE_INGOT})),
    ALLOY("ic2_alloy", 50, new int[]{4, 7, 9, 4}, 12, SoundEvents.f_11677_, 2.0f, 0.0f, () -> Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.ALLOY})),
    NANO_SUIT("ic2_nano", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.f_11677_, 2.0f, 0.0f, Ingredient::m_151265_),
    QUANTUM_SUIT("ic2_quantum", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.f_11677_, 2.0f, 0.0f, Ingredient::m_151265_),
    NIGHT_VISION_GOGGLES("ic2_night_vision", 0, new int[]{3, 0, 0, 0}, 0, SoundEvents.f_11677_, 2.0f, 0.0f, Ingredient::m_151265_),
    HAZMAT("ic2_hazmat", SoundEvents.f_11678_),
    CF_PACK("ic2_cf_pack", SoundEvents.f_11677_),
    JET_PACK("ic2_jet_pack", SoundEvents.f_11677_);

    private static final int[] BASE_DURABILITY;
    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredientSupplier;

    private Ic2ArmorMaterials(String string2, int n2, int[] nArray, int n3, SoundEvent soundEvent, float f, float f2, Supplier<Ingredient> supplier) {
        this.name = string2;
        this.durabilityMultiplier = n2;
        this.protectionAmounts = nArray;
        this.enchantability = n3;
        this.equipSound = soundEvent;
        this.toughness = f;
        this.knockbackResistance = f2;
        this.repairIngredientSupplier = supplier;
    }

    private Ic2ArmorMaterials(String string2, SoundEvent soundEvent) {
        this.name = string2;
        this.durabilityMultiplier = 0;
        this.enchantability = 0;
        this.equipSound = soundEvent;
        this.knockbackResistance = 0.0f;
        this.protectionAmounts = new int[]{0, 0, 0, 0};
        this.toughness = 0.0f;
        this.repairIngredientSupplier = Suppliers.memoize(Ingredient::m_151265_);
    }

    public int m_7366_(EquipmentSlot equipmentSlot) {
        return BASE_DURABILITY[equipmentSlot.m_20749_()] * this.durabilityMultiplier;
    }

    public int m_7365_(EquipmentSlot equipmentSlot) {
        return this.protectionAmounts[equipmentSlot.m_20749_()];
    }

    public int m_6646_() {
        return this.enchantability;
    }

    public SoundEvent m_7344_() {
        return this.equipSound;
    }

    public Ingredient m_6230_() {
        return this.repairIngredientSupplier.get();
    }

    public String m_6082_() {
        return this.name;
    }

    public float m_6651_() {
        return this.toughness;
    }

    public float m_6649_() {
        return this.knockbackResistance;
    }

    static {
        BASE_DURABILITY = new int[]{13, 15, 16, 11};
    }
}

