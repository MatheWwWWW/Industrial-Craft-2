/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.LazyLoadedValue
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.item.ArmorMaterial
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.wearable.material;

import java.util.Arrays;
import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IC2SpecialArmorMaterial
implements ArmorMaterial {
    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private ArmorMaterial baseMaterial;
    private String nameOverride = null;
    private int[] durabilityOverride = null;
    private int[] damageReductionOverride = null;
    private Integer enchantabilityOverride = null;
    private SoundEvent soundEventOverride = null;
    private Float knockBackOverride = null;
    private Float toughnessOverride = null;
    private LazyLoadedValue<Ingredient> repairMaterialOverride = null;

    public IC2SpecialArmorMaterial(ArmorMaterial baseMaterial) {
        this.baseMaterial = baseMaterial;
    }

    public IC2SpecialArmorMaterial setMaxDamageFactor(int maxDamageFactor) {
        this.durabilityOverride = new int[MAX_DAMAGE_ARRAY.length];
        for (int i = 0; i < MAX_DAMAGE_ARRAY.length; ++i) {
            this.durabilityOverride[i] = MAX_DAMAGE_ARRAY[i] * maxDamageFactor;
        }
        return this;
    }

    public IC2SpecialArmorMaterial setDurability(int durability) {
        this.durabilityOverride = new int[MAX_DAMAGE_ARRAY.length];
        Arrays.fill(this.durabilityOverride, durability);
        return this;
    }

    public IC2SpecialArmorMaterial setDurability(int[] durability) {
        this.durabilityOverride = durability;
        return this;
    }

    public IC2SpecialArmorMaterial setDamageReduction(int[] damageReduction) {
        this.damageReductionOverride = damageReduction;
        return this;
    }

    public IC2SpecialArmorMaterial setEnchantability(Integer enchantability) {
        this.enchantabilityOverride = enchantability;
        return this;
    }

    public IC2SpecialArmorMaterial setSoundEvent(SoundEvent soundEvent) {
        this.soundEventOverride = soundEvent;
        return this;
    }

    public IC2SpecialArmorMaterial setRepairMaterial(Supplier<Ingredient> repairMaterial) {
        this.repairMaterialOverride = new LazyLoadedValue(repairMaterial);
        return this;
    }

    public IC2SpecialArmorMaterial setName(String name) {
        this.nameOverride = name;
        return this;
    }

    public IC2SpecialArmorMaterial setToughness(Float toughness) {
        this.toughnessOverride = toughness;
        return this;
    }

    public IC2SpecialArmorMaterial setKnockbackResistance(Float kockback) {
        this.knockBackOverride = kockback;
        return this;
    }

    public int m_7366_(EquipmentSlot slot) {
        return this.durabilityOverride == null ? this.baseMaterial.m_7366_(slot) : this.durabilityOverride[slot.m_20749_()];
    }

    public int m_7365_(EquipmentSlot slot) {
        return this.damageReductionOverride == null ? this.baseMaterial.m_7365_(slot) : this.damageReductionOverride[slot.m_20749_()];
    }

    public int m_6646_() {
        return this.enchantabilityOverride == null ? this.baseMaterial.m_6646_() : this.enchantabilityOverride.intValue();
    }

    public SoundEvent m_7344_() {
        return this.soundEventOverride == null ? this.baseMaterial.m_7344_() : this.soundEventOverride;
    }

    public Ingredient m_6230_() {
        return this.repairMaterialOverride == null ? this.baseMaterial.m_6230_() : (Ingredient)this.repairMaterialOverride.m_13971_();
    }

    @OnlyIn(value=Dist.CLIENT)
    public String m_6082_() {
        return this.nameOverride == null ? this.baseMaterial.m_6082_() : this.nameOverride;
    }

    public float m_6651_() {
        return this.toughnessOverride == null ? this.baseMaterial.m_6651_() : this.toughnessOverride.floatValue();
    }

    public float m_6649_() {
        return this.knockBackOverride == null ? this.baseMaterial.m_6649_() : this.knockBackOverride.floatValue();
    }
}

