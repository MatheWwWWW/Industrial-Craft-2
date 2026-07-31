/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nonnull
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.core;

import com.google.common.collect.Lists;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;

public class NonNullList<E>
extends AbstractList<E> {
    private final List<E> f_122773_;
    @Nullable
    private final E f_122774_;

    public static <E> NonNullList<E> m_122779_() {
        return new NonNullList<Object>(Lists.newArrayList(), null);
    }

    public static <E> NonNullList<E> m_182647_(int p_182648_) {
        return new NonNullList<Object>(Lists.newArrayListWithCapacity((int)p_182648_), null);
    }

    public static <E> NonNullList<E> m_122780_(int p_122781_, E p_122782_) {
        Validate.notNull(p_122782_);
        Object[] $$2 = new Object[p_122781_];
        Arrays.fill($$2, p_122782_);
        return new NonNullList<Object>(Arrays.asList($$2), p_122782_);
    }

    @SafeVarargs
    public static <E> NonNullList<E> m_122783_(E p_122784_, E ... p_122785_) {
        return new NonNullList<E>(Arrays.asList(p_122785_), p_122784_);
    }

    protected NonNullList(List<E> p_122777_, @Nullable E p_122778_) {
        this.f_122773_ = p_122777_;
        this.f_122774_ = p_122778_;
    }

    @Override
    @Nonnull
    public E get(int p_122791_) {
        return this.f_122773_.get(p_122791_);
    }

    @Override
    public E set(int p_122795_, E p_122796_) {
        Validate.notNull(p_122796_);
        return this.f_122773_.set(p_122795_, p_122796_);
    }

    @Override
    public void add(int p_122787_, E p_122788_) {
        Validate.notNull(p_122788_);
        this.f_122773_.add(p_122787_, p_122788_);
    }

    @Override
    public E remove(int p_122793_) {
        return this.f_122773_.remove(p_122793_);
    }

    @Override
    public int size() {
        return this.f_122773_.size();
    }

    @Override
    public void clear() {
        if (this.f_122774_ == null) {
            super.clear();
        } else {
            for (int $$0 = 0; $$0 < this.size(); ++$$0) {
                this.set($$0, this.f_122774_);
            }
        }
    }
}

