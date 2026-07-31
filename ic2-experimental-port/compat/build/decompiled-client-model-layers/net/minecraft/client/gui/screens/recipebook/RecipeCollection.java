/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 */
package net.minecraft.client.gui.screens.recipebook;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeCollection {
    private final List<Recipe<?>> f_100491_;
    private final boolean f_100492_;
    private final Set<Recipe<?>> f_100493_ = Sets.newHashSet();
    private final Set<Recipe<?>> f_100494_ = Sets.newHashSet();
    private final Set<Recipe<?>> f_100495_ = Sets.newHashSet();

    public RecipeCollection(List<Recipe<?>> p_100497_) {
        this.f_100491_ = ImmutableList.copyOf(p_100497_);
        this.f_100492_ = p_100497_.size() <= 1 ? true : RecipeCollection.m_100508_(p_100497_);
    }

    private static boolean m_100508_(List<Recipe<?>> p_100509_) {
        int $$1 = p_100509_.size();
        ItemStack $$2 = p_100509_.get(0).m_8043_();
        for (int $$3 = 1; $$3 < $$1; ++$$3) {
            ItemStack $$4 = p_100509_.get($$3).m_8043_();
            if (ItemStack.m_41746_($$2, $$4) && ItemStack.m_41658_($$2, $$4)) continue;
            return false;
        }
        return true;
    }

    public boolean m_100498_() {
        return !this.f_100495_.isEmpty();
    }

    public void m_100499_(RecipeBook p_100500_) {
        for (Recipe<?> $$1 : this.f_100491_) {
            if (!p_100500_.m_12709_($$1)) continue;
            this.f_100495_.add($$1);
        }
    }

    public void m_100501_(StackedContents p_100502_, int p_100503_, int p_100504_, RecipeBook p_100505_) {
        for (Recipe<?> $$4 : this.f_100491_) {
            boolean $$5;
            boolean bl = $$5 = $$4.m_8004_(p_100503_, p_100504_) && p_100505_.m_12709_($$4);
            if ($$5) {
                this.f_100494_.add($$4);
            } else {
                this.f_100494_.remove($$4);
            }
            if ($$5 && p_100502_.m_36475_($$4, null)) {
                this.f_100493_.add($$4);
                continue;
            }
            this.f_100493_.remove($$4);
        }
    }

    public boolean m_100506_(Recipe<?> p_100507_) {
        return this.f_100493_.contains(p_100507_);
    }

    public boolean m_100512_() {
        return !this.f_100493_.isEmpty();
    }

    public boolean m_100515_() {
        return !this.f_100494_.isEmpty();
    }

    public List<Recipe<?>> m_100516_() {
        return this.f_100491_;
    }

    public List<Recipe<?>> m_100510_(boolean p_100511_) {
        ArrayList $$1 = Lists.newArrayList();
        Set<Recipe<?>> $$2 = p_100511_ ? this.f_100493_ : this.f_100494_;
        for (Recipe<?> $$3 : this.f_100491_) {
            if (!$$2.contains($$3)) continue;
            $$1.add($$3);
        }
        return $$1;
    }

    public List<Recipe<?>> m_100513_(boolean p_100514_) {
        ArrayList $$1 = Lists.newArrayList();
        for (Recipe<?> $$2 : this.f_100491_) {
            if (!this.f_100494_.contains($$2) || this.f_100493_.contains($$2) != p_100514_) continue;
            $$1.add($$2);
        }
        return $$1;
    }

    public boolean m_100517_() {
        return this.f_100492_;
    }
}

