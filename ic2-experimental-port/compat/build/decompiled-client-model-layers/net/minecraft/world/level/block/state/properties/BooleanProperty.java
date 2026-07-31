/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 */
package net.minecraft.world.level.block.state.properties;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Optional;
import net.minecraft.world.level.block.state.properties.Property;

public class BooleanProperty
extends Property<Boolean> {
    private final ImmutableSet<Boolean> f_61457_ = ImmutableSet.of((Object)true, (Object)false);

    protected BooleanProperty(String p_61459_) {
        super(p_61459_, Boolean.class);
    }

    @Override
    public Collection<Boolean> m_6908_() {
        return this.f_61457_;
    }

    public static BooleanProperty m_61465_(String p_61466_) {
        return new BooleanProperty(p_61466_);
    }

    @Override
    public Optional<Boolean> m_6215_(String p_61469_) {
        if ("true".equals(p_61469_) || "false".equals(p_61469_)) {
            return Optional.of(Boolean.valueOf(p_61469_));
        }
        return Optional.empty();
    }

    @Override
    public String m_6940_(Boolean p_61462_) {
        return p_61462_.toString();
    }

    @Override
    public boolean equals(Object p_61471_) {
        if (this == p_61471_) {
            return true;
        }
        if (p_61471_ instanceof BooleanProperty && super.equals(p_61471_)) {
            BooleanProperty $$1 = (BooleanProperty)p_61471_;
            return this.f_61457_.equals($$1.f_61457_);
        }
        return false;
    }

    @Override
    public int m_6310_() {
        return 31 * super.m_6310_() + this.f_61457_.hashCode();
    }
}

