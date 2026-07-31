/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.chunk;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.chunk.MissingPaletteEntryException;
import net.minecraft.world.level.chunk.Palette;
import net.minecraft.world.level.chunk.PaletteResize;

public class GlobalPalette<T>
implements Palette<T> {
    private final IdMap<T> f_62639_;

    public GlobalPalette(IdMap<T> p_187897_) {
        this.f_62639_ = p_187897_;
    }

    public static <A> Palette<A> m_187898_(int p_187899_, IdMap<A> p_187900_, PaletteResize<A> p_187901_, List<A> p_187902_) {
        return new GlobalPalette<A>(p_187900_);
    }

    @Override
    public int m_6796_(T p_62648_) {
        int $$1 = this.f_62639_.m_7447_(p_62648_);
        return $$1 == -1 ? 0 : $$1;
    }

    @Override
    public boolean m_6419_(Predicate<T> p_62650_) {
        return true;
    }

    @Override
    public T m_5795_(int p_62646_) {
        T $$1 = this.f_62639_.m_7942_(p_62646_);
        if ($$1 == null) {
            throw new MissingPaletteEntryException(p_62646_);
        }
        return $$1;
    }

    @Override
    public void m_5680_(FriendlyByteBuf p_62654_) {
    }

    @Override
    public void m_5678_(FriendlyByteBuf p_62656_) {
    }

    @Override
    public int m_6429_() {
        return FriendlyByteBuf.m_130053_(0);
    }

    @Override
    public int m_62680_() {
        return this.f_62639_.m_13562_();
    }

    @Override
    public Palette<T> m_199814_() {
        return this;
    }
}

