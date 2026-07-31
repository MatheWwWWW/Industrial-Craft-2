/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public abstract class AbstractCookingRecipe
implements Recipe<Container> {
    protected final RecipeType<?> f_43726_;
    protected final ResourceLocation f_43727_;
    protected final String f_43728_;
    protected final Ingredient f_43729_;
    protected final ItemStack f_43730_;
    protected final float f_43731_;
    protected final int f_43732_;

    public AbstractCookingRecipe(RecipeType<?> p_43734_, ResourceLocation p_43735_, String p_43736_, Ingredient p_43737_, ItemStack p_43738_, float p_43739_, int p_43740_) {
        this.f_43726_ = p_43734_;
        this.f_43727_ = p_43735_;
        this.f_43728_ = p_43736_;
        this.f_43729_ = p_43737_;
        this.f_43730_ = p_43738_;
        this.f_43731_ = p_43739_;
        this.f_43732_ = p_43740_;
    }

    @Override
    public boolean m_5818_(Container p_43748_, Level p_43749_) {
        return this.f_43729_.test(p_43748_.m_8020_(0));
    }

    @Override
    public ItemStack m_5874_(Container p_43746_) {
        return this.f_43730_.m_41777_();
    }

    @Override
    public boolean m_8004_(int p_43743_, int p_43744_) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> m_7527_() {
        NonNullList<Ingredient> $$0 = NonNullList.m_122779_();
        $$0.add(this.f_43729_);
        return $$0;
    }

    public float m_43750_() {
        return this.f_43731_;
    }

    @Override
    public ItemStack m_8043_() {
        return this.f_43730_;
    }

    @Override
    public String m_6076_() {
        return this.f_43728_;
    }

    public int m_43753_() {
        return this.f_43732_;
    }

    @Override
    public ResourceLocation m_6423_() {
        return this.f_43727_;
    }

    @Override
    public RecipeType<?> m_6671_() {
        return this.f_43726_;
    }
}

