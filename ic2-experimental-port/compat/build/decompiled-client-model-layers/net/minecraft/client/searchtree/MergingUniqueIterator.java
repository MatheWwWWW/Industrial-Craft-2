/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.AbstractIterator
 *  com.google.common.collect.Iterators
 *  com.google.common.collect.PeekingIterator
 */
package net.minecraft.client.searchtree;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import java.util.Comparator;
import java.util.Iterator;

public class MergingUniqueIterator<T>
extends AbstractIterator<T> {
    private final PeekingIterator<T> f_235182_;
    private final PeekingIterator<T> f_235183_;
    private final Comparator<T> f_235184_;

    public MergingUniqueIterator(Iterator<T> p_235186_, Iterator<T> p_235187_, Comparator<T> p_235188_) {
        this.f_235182_ = Iterators.peekingIterator(p_235186_);
        this.f_235183_ = Iterators.peekingIterator(p_235187_);
        this.f_235184_ = p_235188_;
    }

    protected T computeNext() {
        boolean $$1;
        boolean $$0 = !this.f_235182_.hasNext();
        boolean bl = $$1 = !this.f_235183_.hasNext();
        if ($$0 && $$1) {
            return (T)this.endOfData();
        }
        if ($$0) {
            return (T)this.f_235183_.next();
        }
        if ($$1) {
            return (T)this.f_235182_.next();
        }
        int $$2 = this.f_235184_.compare(this.f_235182_.peek(), this.f_235183_.peek());
        if ($$2 == 0) {
            this.f_235183_.next();
        }
        return (T)($$2 <= 0 ? this.f_235182_.next() : this.f_235183_.next());
    }
}

