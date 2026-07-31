/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.world.level.chunk;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.chunk.MissingPaletteEntryException;
import net.minecraft.world.level.chunk.Palette;
import net.minecraft.world.level.chunk.PaletteResize;
import org.apache.commons.lang3.Validate;

public class LinearPalette<T>
implements Palette<T> {
    private final IdMap<T> f_63025_;
    private final T[] f_63026_;
    private final PaletteResize<T> f_63027_;
    private final int f_63029_;
    private int f_63030_;

    private LinearPalette(IdMap<T> p_188015_, int p_188016_, PaletteResize<T> p_188017_, List<T> p_188018_) {
        this.f_63025_ = p_188015_;
        this.f_63026_ = new Object[1 << p_188016_];
        this.f_63029_ = p_188016_;
        this.f_63027_ = p_188017_;
        Validate.isTrue((p_188018_.size() <= this.f_63026_.length ? 1 : 0) != 0, (String)"Can't initialize LinearPalette of size %d with %d entries", (Object[])new Object[]{this.f_63026_.length, p_188018_.size()});
        for (int $$4 = 0; $$4 < p_188018_.size(); ++$$4) {
            this.f_63026_[$$4] = p_188018_.get($$4);
        }
        this.f_63030_ = p_188018_.size();
    }

    private LinearPalette(IdMap<T> p_199921_, T[] p_199922_, PaletteResize<T> p_199923_, int p_199924_, int p_199925_) {
        this.f_63025_ = p_199921_;
        this.f_63026_ = p_199922_;
        this.f_63027_ = p_199923_;
        this.f_63029_ = p_199924_;
        this.f_63030_ = p_199925_;
    }

    public static <A> Palette<A> m_188019_(int p_188020_, IdMap<A> p_188021_, PaletteResize<A> p_188022_, List<A> p_188023_) {
        return new LinearPalette<A>(p_188021_, p_188020_, p_188022_, p_188023_);
    }

    @Override
    public int m_6796_(T p_63040_) {
        int $$2;
        for (int $$1 = 0; $$1 < this.f_63030_; ++$$1) {
            if (this.f_63026_[$$1] != p_63040_) continue;
            return $$1;
        }
        if (($$2 = this.f_63030_++) < this.f_63026_.length) {
            this.f_63026_[$$2] = p_63040_;
            return $$2;
        }
        return this.f_63027_.m_7248_(this.f_63029_ + 1, p_63040_);
    }

    @Override
    public boolean m_6419_(Predicate<T> p_63042_) {
        for (int $$1 = 0; $$1 < this.f_63030_; ++$$1) {
            if (!p_63042_.test(this.f_63026_[$$1])) continue;
            return true;
        }
        return false;
    }

    @Override
    public T m_5795_(int p_63038_) {
        if (p_63038_ >= 0 && p_63038_ < this.f_63030_) {
            return this.f_63026_[p_63038_];
        }
        throw new MissingPaletteEntryException(p_63038_);
    }

    @Override
    public void m_5680_(FriendlyByteBuf p_63046_) {
        this.f_63030_ = p_63046_.m_130242_();
        for (int $$1 = 0; $$1 < this.f_63030_; ++$$1) {
            this.f_63026_[$$1] = this.f_63025_.m_200957_(p_63046_.m_130242_());
        }
    }

    @Override
    public void m_5678_(FriendlyByteBuf p_63049_) {
        p_63049_.m_130130_(this.f_63030_);
        for (int $$1 = 0; $$1 < this.f_63030_; ++$$1) {
            p_63049_.m_130130_(this.f_63025_.m_7447_(this.f_63026_[$$1]));
        }
    }

    @Override
    public int m_6429_() {
        int $$0 = FriendlyByteBuf.m_130053_(this.m_62680_());
        for (int $$1 = 0; $$1 < this.m_62680_(); ++$$1) {
            $$0 += FriendlyByteBuf.m_130053_(this.f_63025_.m_7447_(this.f_63026_[$$1]));
        }
        return $$0;
    }

    @Override
    public int m_62680_() {
        return this.f_63030_;
    }

    @Override
    public Palette<T> m_199814_() {
        return new LinearPalette<Object>(this.f_63025_, (Object[])this.f_63026_.clone(), this.f_63027_, this.f_63029_, this.f_63030_);
    }
}

