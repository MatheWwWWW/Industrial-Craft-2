/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.stats;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.RecipeBookSettings;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeBook {
    protected final Set<ResourceLocation> f_12680_ = Sets.newHashSet();
    protected final Set<ResourceLocation> f_12681_ = Sets.newHashSet();
    private final RecipeBookSettings f_12682_ = new RecipeBookSettings();

    public void m_12685_(RecipeBook p_12686_) {
        this.f_12680_.clear();
        this.f_12681_.clear();
        this.f_12682_.m_12732_(p_12686_.f_12682_);
        this.f_12680_.addAll(p_12686_.f_12680_);
        this.f_12681_.addAll(p_12686_.f_12681_);
    }

    public void m_12700_(Recipe<?> p_12701_) {
        if (!p_12701_.m_5598_()) {
            this.m_12702_(p_12701_.m_6423_());
        }
    }

    protected void m_12702_(ResourceLocation p_12703_) {
        this.f_12680_.add(p_12703_);
    }

    public boolean m_12709_(@Nullable Recipe<?> p_12710_) {
        if (p_12710_ == null) {
            return false;
        }
        return this.f_12680_.contains(p_12710_.m_6423_());
    }

    public boolean m_12711_(ResourceLocation p_12712_) {
        return this.f_12680_.contains(p_12712_);
    }

    public void m_12713_(Recipe<?> p_12714_) {
        this.m_12715_(p_12714_.m_6423_());
    }

    protected void m_12715_(ResourceLocation p_12716_) {
        this.f_12680_.remove(p_12716_);
        this.f_12681_.remove(p_12716_);
    }

    public boolean m_12717_(Recipe<?> p_12718_) {
        return this.f_12681_.contains(p_12718_.m_6423_());
    }

    public void m_12721_(Recipe<?> p_12722_) {
        this.f_12681_.remove(p_12722_.m_6423_());
    }

    public void m_12723_(Recipe<?> p_12724_) {
        this.m_12719_(p_12724_.m_6423_());
    }

    protected void m_12719_(ResourceLocation p_12720_) {
        this.f_12681_.add(p_12720_);
    }

    public boolean m_12691_(RecipeBookType p_12692_) {
        return this.f_12682_.m_12734_(p_12692_);
    }

    public void m_12693_(RecipeBookType p_12694_, boolean p_12695_) {
        this.f_12682_.m_12736_(p_12694_, p_12695_);
    }

    public boolean m_12689_(RecipeBookMenu<?> p_12690_) {
        return this.m_12704_(p_12690_.m_5867_());
    }

    public boolean m_12704_(RecipeBookType p_12705_) {
        return this.f_12682_.m_12754_(p_12705_);
    }

    public void m_12706_(RecipeBookType p_12707_, boolean p_12708_) {
        this.f_12682_.m_12756_(p_12707_, p_12708_);
    }

    public void m_12687_(RecipeBookSettings p_12688_) {
        this.f_12682_.m_12732_(p_12688_);
    }

    public RecipeBookSettings m_12684_() {
        return this.f_12682_.m_12731_();
    }

    public void m_12696_(RecipeBookType p_12697_, boolean p_12698_, boolean p_12699_) {
        this.f_12682_.m_12736_(p_12697_, p_12698_);
        this.f_12682_.m_12756_(p_12697_, p_12699_);
    }
}

