/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;
import net.minecraft.world.level.chunk.MissingPaletteEntryException;
import net.minecraft.world.level.chunk.Palette;
import net.minecraft.world.level.chunk.PaletteResize;

public class HashMapPalette<T>
implements Palette<T> {
    private final IdMap<T> f_62657_;
    private final CrudeIncrementalIntIdentityHashBiMap<T> f_62658_;
    private final PaletteResize<T> f_62659_;
    private final int f_62662_;

    public HashMapPalette(IdMap<T> p_187908_, int p_187909_, PaletteResize<T> p_187910_, List<T> p_187911_) {
        this(p_187908_, p_187909_, p_187910_);
        p_187911_.forEach(this.f_62658_::m_13569_);
    }

    public HashMapPalette(IdMap<T> p_187904_, int p_187905_, PaletteResize<T> p_187906_) {
        this(p_187904_, p_187905_, p_187906_, CrudeIncrementalIntIdentityHashBiMap.m_184237_(1 << p_187905_));
    }

    private HashMapPalette(IdMap<T> p_199915_, int p_199916_, PaletteResize<T> p_199917_, CrudeIncrementalIntIdentityHashBiMap<T> p_199918_) {
        this.f_62657_ = p_199915_;
        this.f_62662_ = p_199916_;
        this.f_62659_ = p_199917_;
        this.f_62658_ = p_199918_;
    }

    public static <A> Palette<A> m_187912_(int p_187913_, IdMap<A> p_187914_, PaletteResize<A> p_187915_, List<A> p_187916_) {
        return new HashMapPalette<A>(p_187914_, p_187913_, p_187915_, p_187916_);
    }

    @Override
    public int m_6796_(T p_62673_) {
        int $$1 = this.f_62658_.m_7447_(p_62673_);
        if ($$1 == -1 && ($$1 = this.f_62658_.m_13569_(p_62673_)) >= 1 << this.f_62662_) {
            $$1 = this.f_62659_.m_7248_(this.f_62662_ + 1, p_62673_);
        }
        return $$1;
    }

    @Override
    public boolean m_6419_(Predicate<T> p_62675_) {
        for (int $$1 = 0; $$1 < this.m_62680_(); ++$$1) {
            if (!p_62675_.test(this.f_62658_.m_7942_($$1))) continue;
            return true;
        }
        return false;
    }

    @Override
    public T m_5795_(int p_62671_) {
        T $$1 = this.f_62658_.m_7942_(p_62671_);
        if ($$1 == null) {
            throw new MissingPaletteEntryException(p_62671_);
        }
        return $$1;
    }

    @Override
    public void m_5680_(FriendlyByteBuf p_62679_) {
        this.f_62658_.m_13554_();
        int $$1 = p_62679_.m_130242_();
        for (int $$2 = 0; $$2 < $$1; ++$$2) {
            this.f_62658_.m_13569_(this.f_62657_.m_200957_(p_62679_.m_130242_()));
        }
    }

    @Override
    public void m_5678_(FriendlyByteBuf p_62684_) {
        int $$1 = this.m_62680_();
        p_62684_.m_130130_($$1);
        for (int $$2 = 0; $$2 < $$1; ++$$2) {
            p_62684_.m_130130_(this.f_62657_.m_7447_(this.f_62658_.m_7942_($$2)));
        }
    }

    @Override
    public int m_6429_() {
        int $$0 = FriendlyByteBuf.m_130053_(this.m_62680_());
        for (int $$1 = 0; $$1 < this.m_62680_(); ++$$1) {
            $$0 += FriendlyByteBuf.m_130053_(this.f_62657_.m_7447_(this.f_62658_.m_7942_($$1)));
        }
        return $$0;
    }

    public List<T> m_187917_() {
        ArrayList $$0 = new ArrayList();
        this.f_62658_.iterator().forEachRemaining($$0::add);
        return $$0;
    }

    @Override
    public int m_62680_() {
        return this.f_62658_.m_13562_();
    }

    @Override
    public Palette<T> m_199814_() {
        return new HashMapPalette<T>(this.f_62657_, this.f_62662_, this.f_62659_, this.f_62658_.m_199846_());
    }
}

