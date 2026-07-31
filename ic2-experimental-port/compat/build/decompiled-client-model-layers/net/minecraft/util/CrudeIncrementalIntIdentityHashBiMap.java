/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  com.google.common.base.Predicates
 *  com.google.common.collect.Iterators
 *  javax.annotation.Nullable
 */
package net.minecraft.util;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.core.IdMap;
import net.minecraft.util.Mth;

public class CrudeIncrementalIntIdentityHashBiMap<K>
implements IdMap<K> {
    private static final int f_144605_ = -1;
    private static final Object f_13545_ = null;
    private static final float f_144606_ = 0.8f;
    private K[] f_13546_;
    private int[] f_13547_;
    private K[] f_13548_;
    private int f_13549_;
    private int f_13550_;

    private CrudeIncrementalIntIdentityHashBiMap(int p_13553_) {
        this.f_13546_ = new Object[p_13553_];
        this.f_13547_ = new int[p_13553_];
        this.f_13548_ = new Object[p_13553_];
    }

    private CrudeIncrementalIntIdentityHashBiMap(K[] p_199841_, int[] p_199842_, K[] p_199843_, int p_199844_, int p_199845_) {
        this.f_13546_ = p_199841_;
        this.f_13547_ = p_199842_;
        this.f_13548_ = p_199843_;
        this.f_13549_ = p_199844_;
        this.f_13550_ = p_199845_;
    }

    public static <A> CrudeIncrementalIntIdentityHashBiMap<A> m_184237_(int p_184238_) {
        return new CrudeIncrementalIntIdentityHashBiMap((int)((float)p_184238_ / 0.8f));
    }

    @Override
    public int m_7447_(@Nullable K p_13558_) {
        return this.m_13567_(this.m_13563_(p_13558_, this.m_13573_(p_13558_)));
    }

    @Override
    @Nullable
    public K m_7942_(int p_13556_) {
        if (p_13556_ < 0 || p_13556_ >= this.f_13548_.length) {
            return null;
        }
        return this.f_13548_[p_13556_];
    }

    private int m_13567_(int p_13568_) {
        if (p_13568_ == -1) {
            return -1;
        }
        return this.f_13547_[p_13568_];
    }

    public boolean m_144609_(K p_144610_) {
        return this.m_7447_(p_144610_) != -1;
    }

    public boolean m_144607_(int p_144608_) {
        return this.m_7942_(p_144608_) != null;
    }

    public int m_13569_(K p_13570_) {
        int $$1 = this.m_13566_();
        this.m_13559_(p_13570_, $$1);
        return $$1;
    }

    private int m_13566_() {
        while (this.f_13549_ < this.f_13548_.length && this.f_13548_[this.f_13549_] != null) {
            ++this.f_13549_;
        }
        return this.f_13549_;
    }

    private void m_13571_(int p_13572_) {
        K[] $$1 = this.f_13546_;
        int[] $$2 = this.f_13547_;
        CrudeIncrementalIntIdentityHashBiMap<K> $$3 = new CrudeIncrementalIntIdentityHashBiMap<K>(p_13572_);
        for (int $$4 = 0; $$4 < $$1.length; ++$$4) {
            if ($$1[$$4] == null) continue;
            $$3.m_13559_($$1[$$4], $$2[$$4]);
        }
        this.f_13546_ = $$3.f_13546_;
        this.f_13547_ = $$3.f_13547_;
        this.f_13548_ = $$3.f_13548_;
        this.f_13549_ = $$3.f_13549_;
        this.f_13550_ = $$3.f_13550_;
    }

    public void m_13559_(K p_13560_, int p_13561_) {
        int $$2 = Math.max(p_13561_, this.f_13550_ + 1);
        if ((float)$$2 >= (float)this.f_13546_.length * 0.8f) {
            int $$3;
            for ($$3 = this.f_13546_.length << 1; $$3 < p_13561_; $$3 <<= 1) {
            }
            this.m_13571_($$3);
        }
        int $$4 = this.m_13575_(this.m_13573_(p_13560_));
        this.f_13546_[$$4] = p_13560_;
        this.f_13547_[$$4] = p_13561_;
        this.f_13548_[p_13561_] = p_13560_;
        ++this.f_13550_;
        if (p_13561_ == this.f_13549_) {
            ++this.f_13549_;
        }
    }

    private int m_13573_(@Nullable K p_13574_) {
        return (Mth.m_14183_(System.identityHashCode(p_13574_)) & Integer.MAX_VALUE) % this.f_13546_.length;
    }

    private int m_13563_(@Nullable K p_13564_, int p_13565_) {
        for (int $$2 = p_13565_; $$2 < this.f_13546_.length; ++$$2) {
            if (this.f_13546_[$$2] == p_13564_) {
                return $$2;
            }
            if (this.f_13546_[$$2] != f_13545_) continue;
            return -1;
        }
        for (int $$3 = 0; $$3 < p_13565_; ++$$3) {
            if (this.f_13546_[$$3] == p_13564_) {
                return $$3;
            }
            if (this.f_13546_[$$3] != f_13545_) continue;
            return -1;
        }
        return -1;
    }

    private int m_13575_(int p_13576_) {
        for (int $$1 = p_13576_; $$1 < this.f_13546_.length; ++$$1) {
            if (this.f_13546_[$$1] != f_13545_) continue;
            return $$1;
        }
        for (int $$2 = 0; $$2 < p_13576_; ++$$2) {
            if (this.f_13546_[$$2] != f_13545_) continue;
            return $$2;
        }
        throw new RuntimeException("Overflowed :(");
    }

    @Override
    public Iterator<K> iterator() {
        return Iterators.filter((Iterator)Iterators.forArray((Object[])this.f_13548_), (Predicate)Predicates.notNull());
    }

    public void m_13554_() {
        Arrays.fill(this.f_13546_, null);
        Arrays.fill(this.f_13548_, null);
        this.f_13549_ = 0;
        this.f_13550_ = 0;
    }

    @Override
    public int m_13562_() {
        return this.f_13550_;
    }

    public CrudeIncrementalIntIdentityHashBiMap<K> m_199846_() {
        return new CrudeIncrementalIntIdentityHashBiMap<Object>((Object[])this.f_13546_.clone(), (int[])this.f_13547_.clone(), (Object[])this.f_13548_.clone(), this.f_13549_, this.f_13550_);
    }
}

