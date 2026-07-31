/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.client.resources.metadata.animation;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.resources.metadata.animation.AnimationFrame;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSectionSerializer;

public class AnimationMetadataSection {
    public static final AnimationMetadataSectionSerializer f_119011_ = new AnimationMetadataSectionSerializer();
    public static final String f_174858_ = "animation";
    public static final int f_174859_ = 1;
    public static final int f_174860_ = -1;
    public static final AnimationMetadataSection f_119012_ = new AnimationMetadataSection((List)Lists.newArrayList(), -1, -1, 1, false){

        @Override
        public Pair<Integer, Integer> m_7117_(int p_119054_, int p_119055_) {
            return Pair.of((Object)p_119054_, (Object)p_119055_);
        }
    };
    private final List<AnimationFrame> f_119013_;
    private final int f_119014_;
    private final int f_119015_;
    private final int f_119016_;
    private final boolean f_119017_;

    public AnimationMetadataSection(List<AnimationFrame> p_119020_, int p_119021_, int p_119022_, int p_119023_, boolean p_119024_) {
        this.f_119013_ = p_119020_;
        this.f_119014_ = p_119021_;
        this.f_119015_ = p_119022_;
        this.f_119016_ = p_119023_;
        this.f_119017_ = p_119024_;
    }

    private static boolean m_119033_(int p_119034_, int p_119035_) {
        return p_119034_ / p_119035_ * p_119035_ == p_119034_;
    }

    public Pair<Integer, Integer> m_7117_(int p_119028_, int p_119029_) {
        Pair<Integer, Integer> $$2 = this.m_119039_(p_119028_, p_119029_);
        int $$3 = (Integer)$$2.getFirst();
        int $$4 = (Integer)$$2.getSecond();
        if (!AnimationMetadataSection.m_119033_(p_119028_, $$3) || !AnimationMetadataSection.m_119033_(p_119029_, $$4)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "Image size %s,%s is not multiply of frame size %s,%s", p_119028_, p_119029_, $$3, $$4));
        }
        return $$2;
    }

    private Pair<Integer, Integer> m_119039_(int p_119040_, int p_119041_) {
        if (this.f_119014_ != -1) {
            if (this.f_119015_ != -1) {
                return Pair.of((Object)this.f_119014_, (Object)this.f_119015_);
            }
            return Pair.of((Object)this.f_119014_, (Object)p_119041_);
        }
        if (this.f_119015_ != -1) {
            return Pair.of((Object)p_119040_, (Object)this.f_119015_);
        }
        int $$2 = Math.min(p_119040_, p_119041_);
        return Pair.of((Object)$$2, (Object)$$2);
    }

    public int m_119026_(int p_119027_) {
        return this.f_119015_ == -1 ? p_119027_ : this.f_119015_;
    }

    public int m_119031_(int p_119032_) {
        return this.f_119014_ == -1 ? p_119032_ : this.f_119014_;
    }

    public int m_119030_() {
        return this.f_119016_;
    }

    public boolean m_119036_() {
        return this.f_119017_;
    }

    public void m_174861_(FrameOutput p_174862_) {
        for (AnimationFrame $$1 : this.f_119013_) {
            p_174862_.m_174863_($$1.m_119010_(), $$1.m_174856_(this.f_119016_));
        }
    }

    @FunctionalInterface
    public static interface FrameOutput {
        public void m_174863_(int var1, int var2);
    }
}

