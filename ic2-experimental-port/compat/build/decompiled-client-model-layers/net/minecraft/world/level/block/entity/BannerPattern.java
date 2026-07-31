/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class BannerPattern {
    final String f_58532_;

    public BannerPattern(String p_222696_) {
        this.f_58532_ = p_222696_;
    }

    public static ResourceLocation m_222697_(ResourceKey<BannerPattern> p_222698_, boolean p_222699_) {
        String $$2 = p_222699_ ? "banner" : "shield";
        ResourceLocation $$3 = p_222698_.m_135782_();
        return new ResourceLocation($$3.m_135827_(), "entity/" + $$2 + "/" + $$3.m_135815_());
    }

    public String m_58579_() {
        return this.f_58532_;
    }

    @Nullable
    public static Holder<BannerPattern> m_222700_(String p_222701_) {
        return Registry.f_235736_.m_203611_().filter(p_222704_ -> ((BannerPattern)p_222704_.m_203334_()).f_58532_.equals(p_222701_)).findAny().orElse(null);
    }

    public static class Builder {
        private final List<Pair<Holder<BannerPattern>, DyeColor>> f_58585_ = Lists.newArrayList();

        public Builder m_222705_(ResourceKey<BannerPattern> p_222706_, DyeColor p_222707_) {
            return this.m_222708_(Registry.f_235736_.m_206081_(p_222706_), p_222707_);
        }

        public Builder m_222708_(Holder<BannerPattern> p_222709_, DyeColor p_222710_) {
            return this.m_155048_((Pair<Holder<BannerPattern>, DyeColor>)Pair.of(p_222709_, (Object)p_222710_));
        }

        public Builder m_155048_(Pair<Holder<BannerPattern>, DyeColor> p_155049_) {
            this.f_58585_.add(p_155049_);
            return this;
        }

        public ListTag m_58587_() {
            ListTag $$0 = new ListTag();
            for (Pair<Holder<BannerPattern>, DyeColor> $$1 : this.f_58585_) {
                CompoundTag $$2 = new CompoundTag();
                $$2.m_128359_("Pattern", ((BannerPattern)((Holder)$$1.getFirst()).m_203334_()).f_58532_);
                $$2.m_128405_("Color", ((DyeColor)$$1.getSecond()).m_41060_());
                $$0.add($$2);
            }
            return $$0;
        }
    }
}

