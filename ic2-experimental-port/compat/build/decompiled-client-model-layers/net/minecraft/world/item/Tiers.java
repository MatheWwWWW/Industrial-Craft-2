/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import java.util.function.Supplier;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public final class Tiers
extends Enum<Tiers>
implements Tier {
    public static final /* enum */ Tiers WOOD = new Tiers(0, 59, 2.0f, 0.0f, 15, () -> Ingredient.m_204132_(ItemTags.f_13168_));
    public static final /* enum */ Tiers STONE = new Tiers(1, 131, 4.0f, 1.0f, 5, () -> Ingredient.m_204132_(ItemTags.f_13165_));
    public static final /* enum */ Tiers IRON = new Tiers(2, 250, 6.0f, 2.0f, 14, () -> Ingredient.m_43929_(Items.f_42416_));
    public static final /* enum */ Tiers DIAMOND = new Tiers(3, 1561, 8.0f, 3.0f, 10, () -> Ingredient.m_43929_(Items.f_42415_));
    public static final /* enum */ Tiers GOLD = new Tiers(0, 32, 12.0f, 0.0f, 22, () -> Ingredient.m_43929_(Items.f_42417_));
    public static final /* enum */ Tiers NETHERITE = new Tiers(4, 2031, 9.0f, 4.0f, 15, () -> Ingredient.m_43929_(Items.f_42418_));
    private final int f_43321_;
    private final int f_43322_;
    private final float f_43323_;
    private final float f_43324_;
    private final int f_43325_;
    private final LazyLoadedValue<Ingredient> f_43326_;
    private static final /* synthetic */ Tiers[] $VALUES;

    public static Tiers[] values() {
        return (Tiers[])$VALUES.clone();
    }

    public static Tiers valueOf(String p_43351_) {
        return Enum.valueOf(Tiers.class, p_43351_);
    }

    private Tiers(int p_43332_, int p_43333_, float p_43334_, float p_43335_, int p_43336_, Supplier<Ingredient> p_43337_) {
        this.f_43321_ = p_43332_;
        this.f_43322_ = p_43333_;
        this.f_43323_ = p_43334_;
        this.f_43324_ = p_43335_;
        this.f_43325_ = p_43336_;
        this.f_43326_ = new LazyLoadedValue<Ingredient>(p_43337_);
    }

    @Override
    public int m_6609_() {
        return this.f_43322_;
    }

    @Override
    public float m_6624_() {
        return this.f_43323_;
    }

    @Override
    public float m_6631_() {
        return this.f_43324_;
    }

    @Override
    public int m_6604_() {
        return this.f_43321_;
    }

    @Override
    public int m_6601_() {
        return this.f_43325_;
    }

    @Override
    public Ingredient m_6282_() {
        return this.f_43326_.m_13971_();
    }

    private static /* synthetic */ Tiers[] m_151228_() {
        return new Tiers[]{WOOD, STONE, IRON, DIAMOND, GOLD, NETHERITE};
    }

    static {
        $VALUES = Tiers.m_151228_();
    }
}

