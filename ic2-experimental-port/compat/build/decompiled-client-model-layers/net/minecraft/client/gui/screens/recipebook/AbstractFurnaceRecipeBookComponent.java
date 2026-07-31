/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.recipebook;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public abstract class AbstractFurnaceRecipeBookComponent
extends RecipeBookComponent {
    @Nullable
    private Ingredient f_100113_;

    @Override
    protected void m_5674_() {
        this.f_100270_.m_94624_(152, 182, 28, 18, f_100268_);
    }

    @Override
    public void m_6904_(@Nullable Slot p_100120_) {
        super.m_6904_(p_100120_);
        if (p_100120_ != null && p_100120_.f_40219_ < this.f_100271_.m_6653_()) {
            this.f_100269_.m_100140_();
        }
    }

    @Override
    public void m_7173_(Recipe<?> p_100122_, List<Slot> p_100123_) {
        ItemStack $$2 = p_100122_.m_8043_();
        this.f_100269_.m_100147_(p_100122_);
        this.f_100269_.m_100143_(Ingredient.m_43927_($$2), p_100123_.get((int)2).f_40220_, p_100123_.get((int)2).f_40221_);
        NonNullList<Ingredient> $$3 = p_100122_.m_7527_();
        Slot $$4 = p_100123_.get(1);
        if ($$4.m_7993_().m_41619_()) {
            if (this.f_100113_ == null) {
                this.f_100113_ = Ingredient.m_43921_(this.m_7690_().stream().map(ItemStack::new));
            }
            this.f_100269_.m_100143_(this.f_100113_, $$4.f_40220_, $$4.f_40221_);
        }
        Iterator $$5 = $$3.iterator();
        for (int $$6 = 0; $$6 < 2; ++$$6) {
            if (!$$5.hasNext()) {
                return;
            }
            Ingredient $$7 = (Ingredient)$$5.next();
            if ($$7.m_43947_()) continue;
            Slot $$8 = p_100123_.get($$6);
            this.f_100269_.m_100143_($$7, $$8.f_40220_, $$8.f_40221_);
        }
    }

    protected abstract Set<Item> m_7690_();
}

