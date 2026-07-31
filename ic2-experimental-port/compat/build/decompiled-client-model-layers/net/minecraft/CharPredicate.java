/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft;

import java.util.Objects;

@FunctionalInterface
public interface CharPredicate {
    public boolean m_125854_(char var1);

    default public CharPredicate m_178286_(CharPredicate p_178287_) {
        Objects.requireNonNull(p_178287_);
        return p_178295_ -> this.m_125854_(p_178295_) && p_178287_.m_125854_(p_178295_);
    }

    default public CharPredicate m_178283_() {
        return p_178285_ -> !this.m_125854_(p_178285_);
    }

    default public CharPredicate m_178291_(CharPredicate p_178292_) {
        Objects.requireNonNull(p_178292_);
        return p_178290_ -> this.m_125854_(p_178290_) || p_178292_.m_125854_(p_178290_);
    }
}

