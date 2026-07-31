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

public class IntersectionIterator<T>
extends AbstractIterator<T> {
    private final PeekingIterator<T> f_235174_;
    private final PeekingIterator<T> f_235175_;
    private final Comparator<T> f_235176_;

    public IntersectionIterator(Iterator<T> p_235178_, Iterator<T> p_235179_, Comparator<T> p_235180_) {
        this.f_235174_ = Iterators.peekingIterator(p_235178_);
        this.f_235175_ = Iterators.peekingIterator(p_235179_);
        this.f_235176_ = p_235180_;
    }

    protected T computeNext() {
        while (this.f_235174_.hasNext() && this.f_235175_.hasNext()) {
            int $$0 = this.f_235176_.compare(this.f_235174_.peek(), this.f_235175_.peek());
            if ($$0 == 0) {
                this.f_235175_.next();
                return (T)this.f_235174_.next();
            }
            if ($$0 < 0) {
                this.f_235174_.next();
                continue;
            }
            this.f_235175_.next();
        }
        return (T)this.endOfData();
    }
}

