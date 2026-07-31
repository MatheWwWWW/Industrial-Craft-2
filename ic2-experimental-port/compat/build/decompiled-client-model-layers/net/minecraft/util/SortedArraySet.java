/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrays
 *  javax.annotation.Nullable
 */
package net.minecraft.util;

import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

public class SortedArraySet<T>
extends AbstractSet<T> {
    private static final int f_144974_ = 10;
    private final Comparator<T> f_14240_;
    T[] f_14241_;
    int f_14242_;

    private SortedArraySet(int p_14244_, Comparator<T> p_14245_) {
        this.f_14240_ = p_14245_;
        if (p_14244_ < 0) {
            throw new IllegalArgumentException("Initial capacity (" + p_14244_ + ") is negative");
        }
        this.f_14241_ = SortedArraySet.m_14258_(new Object[p_14244_]);
    }

    public static <T extends Comparable<T>> SortedArraySet<T> m_144975_() {
        return SortedArraySet.m_14246_(10);
    }

    public static <T extends Comparable<T>> SortedArraySet<T> m_14246_(int p_14247_) {
        return new SortedArraySet(p_14247_, Comparator.naturalOrder());
    }

    public static <T> SortedArraySet<T> m_144976_(Comparator<T> p_144977_) {
        return SortedArraySet.m_144978_(p_144977_, 10);
    }

    public static <T> SortedArraySet<T> m_144978_(Comparator<T> p_144979_, int p_144980_) {
        return new SortedArraySet<T>(p_144980_, p_144979_);
    }

    private static <T> T[] m_14258_(Object[] p_14259_) {
        return p_14259_;
    }

    private int m_14269_(T p_14270_) {
        return Arrays.binarySearch(this.f_14241_, 0, this.f_14242_, p_14270_, this.f_14240_);
    }

    private static int m_14263_(int p_14264_) {
        return -p_14264_ - 1;
    }

    @Override
    public boolean add(T p_14261_) {
        int $$1 = this.m_14269_(p_14261_);
        if ($$1 >= 0) {
            return false;
        }
        int $$2 = SortedArraySet.m_14263_($$1);
        this.m_14255_(p_14261_, $$2);
        return true;
    }

    private void m_14267_(int p_14268_) {
        if (p_14268_ <= this.f_14241_.length) {
            return;
        }
        if (this.f_14241_ != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
            p_14268_ = (int)Math.max(Math.min((long)this.f_14241_.length + (long)(this.f_14241_.length >> 1), 0x7FFFFFF7L), (long)p_14268_);
        } else if (p_14268_ < 10) {
            p_14268_ = 10;
        }
        Object[] $$1 = new Object[p_14268_];
        System.arraycopy(this.f_14241_, 0, $$1, 0, this.f_14242_);
        this.f_14241_ = SortedArraySet.m_14258_($$1);
    }

    private void m_14255_(T p_14256_, int p_14257_) {
        this.m_14267_(this.f_14242_ + 1);
        if (p_14257_ != this.f_14242_) {
            System.arraycopy(this.f_14241_, p_14257_, this.f_14241_, p_14257_ + 1, this.f_14242_ - p_14257_);
        }
        this.f_14241_[p_14257_] = p_14256_;
        ++this.f_14242_;
    }

    void m_14274_(int p_14275_) {
        --this.f_14242_;
        if (p_14275_ != this.f_14242_) {
            System.arraycopy(this.f_14241_, p_14275_ + 1, this.f_14241_, p_14275_, this.f_14242_ - p_14275_);
        }
        this.f_14241_[this.f_14242_] = null;
    }

    private T m_14276_(int p_14277_) {
        return this.f_14241_[p_14277_];
    }

    public T m_14253_(T p_14254_) {
        int $$1 = this.m_14269_(p_14254_);
        if ($$1 >= 0) {
            return this.m_14276_($$1);
        }
        this.m_14255_(p_14254_, SortedArraySet.m_14263_($$1));
        return p_14254_;
    }

    @Override
    public boolean remove(Object p_14282_) {
        int $$1 = this.m_14269_(p_14282_);
        if ($$1 >= 0) {
            this.m_14274_($$1);
            return true;
        }
        return false;
    }

    @Nullable
    public T m_144981_(T p_144982_) {
        int $$1 = this.m_14269_(p_144982_);
        if ($$1 >= 0) {
            return this.m_14276_($$1);
        }
        return null;
    }

    public T m_14262_() {
        return this.m_14276_(0);
    }

    public T m_144983_() {
        return this.m_14276_(this.f_14242_ - 1);
    }

    @Override
    public boolean contains(Object p_14273_) {
        int $$1 = this.m_14269_(p_14273_);
        return $$1 >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    @Override
    public int size() {
        return this.f_14242_;
    }

    @Override
    public Object[] toArray() {
        return (Object[])this.f_14241_.clone();
    }

    @Override
    public <U> U[] toArray(U[] p_14286_) {
        if (p_14286_.length < this.f_14242_) {
            return Arrays.copyOf(this.f_14241_, this.f_14242_, p_14286_.getClass());
        }
        System.arraycopy(this.f_14241_, 0, p_14286_, 0, this.f_14242_);
        if (p_14286_.length > this.f_14242_) {
            p_14286_[this.f_14242_] = null;
        }
        return p_14286_;
    }

    @Override
    public void clear() {
        Arrays.fill(this.f_14241_, 0, this.f_14242_, null);
        this.f_14242_ = 0;
    }

    @Override
    public boolean equals(Object p_14279_) {
        if (this == p_14279_) {
            return true;
        }
        if (p_14279_ instanceof SortedArraySet) {
            SortedArraySet $$1 = (SortedArraySet)p_14279_;
            if (this.f_14240_.equals($$1.f_14240_)) {
                return this.f_14242_ == $$1.f_14242_ && Arrays.equals(this.f_14241_, $$1.f_14241_);
            }
        }
        return super.equals(p_14279_);
    }

    class ArrayIterator
    implements Iterator<T> {
        private int f_14288_;
        private int f_14289_ = -1;

        ArrayIterator() {
        }

        @Override
        public boolean hasNext() {
            return this.f_14288_ < SortedArraySet.this.f_14242_;
        }

        @Override
        public T next() {
            if (this.f_14288_ >= SortedArraySet.this.f_14242_) {
                throw new NoSuchElementException();
            }
            this.f_14289_ = this.f_14288_++;
            return SortedArraySet.this.f_14241_[this.f_14289_];
        }

        @Override
        public void remove() {
            if (this.f_14289_ == -1) {
                throw new IllegalStateException();
            }
            SortedArraySet.this.m_14274_(this.f_14289_);
            --this.f_14288_;
            this.f_14289_ = -1;
        }
    }
}

