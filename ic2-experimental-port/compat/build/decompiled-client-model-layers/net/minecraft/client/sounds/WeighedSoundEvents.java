/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.sounds;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.Weighted;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

public class WeighedSoundEvents
implements Weighted<Sound> {
    private final List<Weighted<Sound>> f_120441_ = Lists.newArrayList();
    private final RandomSource f_120442_ = RandomSource.m_216327_();
    private final ResourceLocation f_120443_;
    @Nullable
    private final Component f_120444_;

    public WeighedSoundEvents(ResourceLocation p_120446_, @Nullable String p_120447_) {
        this.f_120443_ = p_120446_;
        this.f_120444_ = p_120447_ == null ? null : Component.m_237115_(p_120447_);
    }

    @Override
    public int m_7789_() {
        int $$0 = 0;
        for (Weighted<Sound> $$1 : this.f_120441_) {
            $$0 += $$1.m_7789_();
        }
        return $$0;
    }

    @Override
    public Sound m_213718_(RandomSource p_235265_) {
        int $$1 = this.m_7789_();
        if (this.f_120441_.isEmpty() || $$1 == 0) {
            return SoundManager.f_120344_;
        }
        int $$2 = p_235265_.m_188503_($$1);
        for (Weighted<Sound> $$3 : this.f_120441_) {
            if (($$2 -= $$3.m_7789_()) >= 0) continue;
            return $$3.m_213718_(p_235265_);
        }
        return SoundManager.f_120344_;
    }

    public void m_120451_(Weighted<Sound> p_120452_) {
        this.f_120441_.add(p_120452_);
    }

    public ResourceLocation m_174998_() {
        return this.f_120443_;
    }

    @Nullable
    public Component m_120453_() {
        return this.f_120444_;
    }

    @Override
    public void m_8054_(SoundEngine p_120450_) {
        for (Weighted<Sound> $$1 : this.f_120441_) {
            $$1.m_8054_(p_120450_);
        }
    }

    @Override
    public /* synthetic */ Object m_213718_(RandomSource randomSource) {
        return this.m_213718_(randomSource);
    }
}

