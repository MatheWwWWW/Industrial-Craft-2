/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Suppliers
 */
package net.minecraft.util;

import com.google.common.base.Suppliers;
import java.util.function.Supplier;

@Deprecated
public class LazyLoadedValue<T> {
    private final Supplier<T> f_13967_ = Suppliers.memoize(p_13970_::get);

    public LazyLoadedValue(Supplier<T> p_13970_) {
    }

    public T m_13971_() {
        return this.f_13967_.get();
    }
}

