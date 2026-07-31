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

import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IC2ArmorMaterial
implements ArmorMaterial {
    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int[] durabilityAmounts;
    private final int[] damageReductionAmounts;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final LazyLoadedValue<Ingredient> repairMaterial;

    public IC2ArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmounts, int enchantability, SoundEvent soundEvent, Supplier<Ingredient> repairMaterial, float toughness) {
        this(name, IC2ArmorMaterial.buildDurabilities(maxDamageFactor), damageReductionAmounts, enchantability, soundEvent, repairMaterial, toughness);
    }

    public IC2ArmorMaterial(String name, int[] durabilityAmounts, int[] damageReductionAmounts, int enchantability, SoundEvent soundEvent, Supplier<Ingredient> repairMaterial, float toughness) {
        this.name = name;
        this.durabilityAmounts = durabilityAmounts;
        this.damageReductionAmounts = damageReductionAmounts;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.repairMaterial = new LazyLoadedValue(repairMaterial);
    }

    private static int[] buildDurabilities(int maxDamageFactor) {
        int[] durability = new int[MAX_DAMAGE_ARRAY.length];
        for (int i = 0; i < MAX_DAMAGE_ARRAY.length; ++i) {
            durability[i] = MAX_DAMAGE_ARRAY[i] * maxDamageFactor;
        }
        return durability;
    }

    public int m_7366_(EquipmentSlot slotIn) {
        return this.durabilityAmounts[slotIn.m_20749_()];
    }

    public int m_7365_(EquipmentSlot slotIn) {
        return this.damageReductionAmounts[slotIn.m_20749_()];
    }

    public int m_6646_() {
        return this.enchantability;
    }

    public SoundEvent m_7344_() {
        return this.soundEvent;
    }

    public Ingredient m_6230_() {
        return (Ingredient)this.repairMaterial.m_13971_();
    }

    @OnlyIn(value=Dist.CLIENT)
    public String m_6082_() {
        return this.name;
    }

    public float m_6649_() {
        return 0.0f;
    }

    public float m_6651_() {
        return this.toughness;
    }
}

