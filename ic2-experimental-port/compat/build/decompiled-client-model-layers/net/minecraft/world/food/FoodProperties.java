/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.world.food;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.world.effect.MobEffectInstance;

public class FoodProperties {
    private final int f_38723_;
    private final float f_38724_;
    private final boolean f_38725_;
    private final boolean f_38726_;
    private final boolean f_38727_;
    private final List<Pair<MobEffectInstance, Float>> f_38728_;

    FoodProperties(int p_38730_, float p_38731_, boolean p_38732_, boolean p_38733_, boolean p_38734_, List<Pair<MobEffectInstance, Float>> p_38735_) {
        this.f_38723_ = p_38730_;
        this.f_38724_ = p_38731_;
        this.f_38725_ = p_38732_;
        this.f_38726_ = p_38733_;
        this.f_38727_ = p_38734_;
        this.f_38728_ = p_38735_;
    }

    public int m_38744_() {
        return this.f_38723_;
    }

    public float m_38745_() {
        return this.f_38724_;
    }

    public boolean m_38746_() {
        return this.f_38725_;
    }

    public boolean m_38747_() {
        return this.f_38726_;
    }

    public boolean m_38748_() {
        return this.f_38727_;
    }

    public List<Pair<MobEffectInstance, Float>> m_38749_() {
        return this.f_38728_;
    }

    public static class Builder {
        private int f_38750_;
        private float f_38751_;
        private boolean f_38752_;
        private boolean f_38753_;
        private boolean f_38754_;
        private final List<Pair<MobEffectInstance, Float>> f_38755_ = Lists.newArrayList();

        public Builder m_38760_(int p_38761_) {
            this.f_38750_ = p_38761_;
            return this;
        }

        public Builder m_38758_(float p_38759_) {
            this.f_38751_ = p_38759_;
            return this;
        }

        public Builder m_38757_() {
            this.f_38752_ = true;
            return this;
        }

        public Builder m_38765_() {
            this.f_38753_ = true;
            return this;
        }

        public Builder m_38766_() {
            this.f_38754_ = true;
            return this;
        }

        public Builder m_38762_(MobEffectInstance p_38763_, float p_38764_) {
            this.f_38755_.add((Pair<MobEffectInstance, Float>)Pair.of((Object)p_38763_, (Object)Float.valueOf(p_38764_)));
            return this;
        }

        public FoodProperties m_38767_() {
            return new FoodProperties(this.f_38750_, this.f_38751_, this.f_38752_, this.f_38753_, this.f_38754_, this.f_38755_);
        }
    }
}

