/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Sets
 */
package net.minecraft.world.level.block.state.properties;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import net.minecraft.world.level.block.state.properties.Property;

public class IntegerProperty
extends Property<Integer> {
    private final ImmutableSet<Integer> f_61621_;
    private final int f_223000_;
    private final int f_223001_;

    protected IntegerProperty(String p_61623_, int p_61624_, int p_61625_) {
        super(p_61623_, Integer.class);
        if (p_61624_ < 0) {
            throw new IllegalArgumentException("Min value of " + p_61623_ + " must be 0 or greater");
        }
        if (p_61625_ <= p_61624_) {
            throw new IllegalArgumentException("Max value of " + p_61623_ + " must be greater than min (" + p_61624_ + ")");
        }
        this.f_223000_ = p_61624_;
        this.f_223001_ = p_61625_;
        HashSet $$3 = Sets.newHashSet();
        for (int $$4 = p_61624_; $$4 <= p_61625_; ++$$4) {
            $$3.add($$4);
        }
        this.f_61621_ = ImmutableSet.copyOf((Collection)$$3);
    }

    @Override
    public Collection<Integer> m_6908_() {
        return this.f_61621_;
    }

    @Override
    public boolean equals(Object p_61639_) {
        if (this == p_61639_) {
            return true;
        }
        if (p_61639_ instanceof IntegerProperty && super.equals(p_61639_)) {
            IntegerProperty $$1 = (IntegerProperty)p_61639_;
            return this.f_61621_.equals($$1.f_61621_);
        }
        return false;
    }

    @Override
    public int m_6310_() {
        return 31 * super.m_6310_() + this.f_61621_.hashCode();
    }

    public static IntegerProperty m_61631_(String p_61632_, int p_61633_, int p_61634_) {
        return new IntegerProperty(p_61632_, p_61633_, p_61634_);
    }

    @Override
    public Optional<Integer> m_6215_(String p_61637_) {
        try {
            Integer $$1 = Integer.valueOf(p_61637_);
            return $$1 >= this.f_223000_ && $$1 <= this.f_223001_ ? Optional.of($$1) : Optional.empty();
        }
        catch (NumberFormatException $$2) {
            return Optional.empty();
        }
    }

    @Override
    public String m_6940_(Integer p_61630_) {
        return p_61630_.toString();
    }
}

