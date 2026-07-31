/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.entity;

import javax.annotation.Nullable;

public interface EntityTypeTest<B, T extends B> {
    public static <B, T extends B> EntityTypeTest<B, T> m_156916_(final Class<T> p_156917_) {
        return new EntityTypeTest<B, T>(){

            @Override
            @Nullable
            public T m_141992_(B p_156924_) {
                return p_156917_.isInstance(p_156924_) ? p_156924_ : null;
            }

            @Override
            public Class<? extends B> m_142225_() {
                return p_156917_;
            }
        };
    }

    @Nullable
    public T m_141992_(B var1);

    public Class<? extends B> m_142225_();
}

