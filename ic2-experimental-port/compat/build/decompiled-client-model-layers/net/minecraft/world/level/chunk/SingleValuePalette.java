/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.world.level.chunk;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.chunk.Palette;
import net.minecraft.world.level.chunk.PaletteResize;
import org.apache.commons.lang3.Validate;

public class SingleValuePalette<T>
implements Palette<T> {
    private final IdMap<T> f_188203_;
    @Nullable
    private T f_188204_;
    private final PaletteResize<T> f_188205_;

    public SingleValuePalette(IdMap<T> p_188207_, PaletteResize<T> p_188208_, List<T> p_188209_) {
        this.f_188203_ = p_188207_;
        this.f_188205_ = p_188208_;
        if (p_188209_.size() > 0) {
            Validate.isTrue((p_188209_.size() <= 1 ? 1 : 0) != 0, (String)"Can't initialize SingleValuePalette with %d values.", (long)p_188209_.size());
            this.f_188204_ = p_188209_.get(0);
        }
    }

    public static <A> Palette<A> m_188213_(int p_188214_, IdMap<A> p_188215_, PaletteResize<A> p_188216_, List<A> p_188217_) {
        return new SingleValuePalette<A>(p_188215_, p_188216_, p_188217_);
    }

    @Override
    public int m_6796_(T p_188219_) {
        if (this.f_188204_ == null || this.f_188204_ == p_188219_) {
            this.f_188204_ = p_188219_;
            return 0;
        }
        return this.f_188205_.m_7248_(1, p_188219_);
    }

    @Override
    public boolean m_6419_(Predicate<T> p_188221_) {
        if (this.f_188204_ == null) {
            throw new IllegalStateException("Use of an uninitialized palette");
        }
        return p_188221_.test(this.f_188204_);
    }

    @Override
    public T m_5795_(int p_188212_) {
        if (this.f_188204_ == null || p_188212_ != 0) {
            throw new IllegalStateException("Missing Palette entry for id " + p_188212_ + ".");
        }
        return this.f_188204_;
    }

    @Override
    public void m_5680_(FriendlyByteBuf p_188223_) {
        this.f_188204_ = this.f_188203_.m_200957_(p_188223_.m_130242_());
    }

    @Override
    public void m_5678_(FriendlyByteBuf p_188226_) {
        if (this.f_188204_ == null) {
            throw new IllegalStateException("Use of an uninitialized palette");
        }
        p_188226_.m_130130_(this.f_188203_.m_7447_(this.f_188204_));
    }

    @Override
    public int m_6429_() {
        if (this.f_188204_ == null) {
            throw new IllegalStateException("Use of an uninitialized palette");
        }
        return FriendlyByteBuf.m_130053_(this.f_188203_.m_7447_(this.f_188204_));
    }

    @Override
    public int m_62680_() {
        return 1;
    }

    @Override
    public Palette<T> m_199814_() {
        if (this.f_188204_ == null) {
            throw new IllegalStateException("Use of an uninitialized palette");
        }
        return this;
    }
}

