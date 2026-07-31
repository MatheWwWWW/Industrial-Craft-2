/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public final class ArmorMaterials
extends Enum<ArmorMaterials>
implements ArmorMaterial {
    public static final /* enum */ ArmorMaterials LEATHER = new ArmorMaterials("leather", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.f_11678_, 0.0f, 0.0f, () -> Ingredient.m_43929_(Items.f_42454_));
    public static final /* enum */ ArmorMaterials CHAIN = new ArmorMaterials("chainmail", 15, new int[]{1, 4, 5, 2}, 12, SoundEvents.f_11672_, 0.0f, 0.0f, () -> Ingredient.m_43929_(Items.f_42416_));
    public static final /* enum */ ArmorMaterials IRON = new ArmorMaterials("iron", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.f_11677_, 0.0f, 0.0f, () -> Ingredient.m_43929_(Items.f_42416_));
    public static final /* enum */ ArmorMaterials GOLD = new ArmorMaterials("gold", 7, new int[]{1, 3, 5, 2}, 25, SoundEvents.f_11676_, 0.0f, 0.0f, () -> Ingredient.m_43929_(Items.f_42417_));
    public static final /* enum */ ArmorMaterials DIAMOND = new ArmorMaterials("diamond", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.f_11673_, 2.0f, 0.0f, () -> Ingredient.m_43929_(Items.f_42415_));
    public static final /* enum */ ArmorMaterials TURTLE = new ArmorMaterials("turtle", 25, new int[]{2, 5, 6, 2}, 9, SoundEvents.f_11680_, 0.0f, 0.0f, () -> Ingredient.m_43929_(Items.f_42355_));
    public static final /* enum */ ArmorMaterials NETHERITE = new ArmorMaterials("netherite", 37, new int[]{3, 6, 8, 3}, 15, SoundEvents.f_11679_, 3.0f, 0.1f, () -> Ingredient.m_43929_(Items.f_42418_));
    private static final int[] f_40460_;
    private final String f_40461_;
    private final int f_40462_;
    private final int[] f_40463_;
    private final int f_40464_;
    private final SoundEvent f_40465_;
    private final float f_40466_;
    private final float f_40467_;
    private final LazyLoadedValue<Ingredient> f_40468_;
    private static final /* synthetic */ ArmorMaterials[] $VALUES;

    public static ArmorMaterials[] values() {
        return (ArmorMaterials[])$VALUES.clone();
    }

    public static ArmorMaterials valueOf(String p_40500_) {
        return Enum.valueOf(ArmorMaterials.class, p_40500_);
    }

    private ArmorMaterials(String p_40474_, int p_40475_, int[] p_40476_, int p_40477_, SoundEvent p_40478_, float p_40479_, float p_40480_, Supplier<Ingredient> p_40481_) {
        this.f_40461_ = p_40474_;
        this.f_40462_ = p_40475_;
        this.f_40463_ = p_40476_;
        this.f_40464_ = p_40477_;
        this.f_40465_ = p_40478_;
        this.f_40466_ = p_40479_;
        this.f_40467_ = p_40480_;
        this.f_40468_ = new LazyLoadedValue<Ingredient>(p_40481_);
    }

    @Override
    public int m_7366_(EquipmentSlot p_40484_) {
        return f_40460_[p_40484_.m_20749_()] * this.f_40462_;
    }

    @Override
    public int m_7365_(EquipmentSlot p_40487_) {
        return this.f_40463_[p_40487_.m_20749_()];
    }

    @Override
    public int m_6646_() {
        return this.f_40464_;
    }

    @Override
    public SoundEvent m_7344_() {
        return this.f_40465_;
    }

    @Override
    public Ingredient m_6230_() {
        return this.f_40468_.m_13971_();
    }

    @Override
    public String m_6082_() {
        return this.f_40461_;
    }

    @Override
    public float m_6651_() {
        return this.f_40466_;
    }

    @Override
    public float m_6649_() {
        return this.f_40467_;
    }

    private static /* synthetic */ ArmorMaterials[] m_150682_() {
        return new ArmorMaterials[]{LEATHER, CHAIN, IRON, GOLD, DIAMOND, TURTLE, NETHERITE};
    }

    static {
        $VALUES = ArmorMaterials.m_150682_();
        f_40460_ = new int[]{13, 15, 16, 11};
    }
}

