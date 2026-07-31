/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 */
package net.minecraft.world.level.block.state.properties;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.Property;

public class EnumProperty<T extends Enum<T>>
extends Property<T> {
    private final ImmutableSet<T> f_61576_;
    private final Map<String, T> f_61577_ = Maps.newHashMap();

    protected EnumProperty(String p_61579_, Class<T> p_61580_, Collection<T> p_61581_) {
        super(p_61579_, p_61580_);
        this.f_61576_ = ImmutableSet.copyOf(p_61581_);
        for (Enum $$3 : p_61581_) {
            String $$4 = ((StringRepresentable)((Object)$$3)).m_7912_();
            if (this.f_61577_.containsKey($$4)) {
                throw new IllegalArgumentException("Multiple values have the same name '" + $$4 + "'");
            }
            this.f_61577_.put($$4, $$3);
        }
    }

    @Override
    public Collection<T> m_6908_() {
        return this.f_61576_;
    }

    @Override
    public Optional<T> m_6215_(String p_61604_) {
        return Optional.ofNullable((Enum)this.f_61577_.get(p_61604_));
    }

    @Override
    public String m_6940_(T p_61586_) {
        return ((StringRepresentable)p_61586_).m_7912_();
    }

    @Override
    public boolean equals(Object p_61606_) {
        if (this == p_61606_) {
            return true;
        }
        if (p_61606_ instanceof EnumProperty && super.equals(p_61606_)) {
            EnumProperty $$1 = (EnumProperty)p_61606_;
            return this.f_61576_.equals($$1.f_61576_) && this.f_61577_.equals($$1.f_61577_);
        }
        return false;
    }

    @Override
    public int m_6310_() {
        int $$0 = super.m_6310_();
        $$0 = 31 * $$0 + this.f_61576_.hashCode();
        $$0 = 31 * $$0 + this.f_61577_.hashCode();
        return $$0;
    }

    public static <T extends Enum<T>> EnumProperty<T> m_61587_(String p_61588_, Class<T> p_61589_) {
        return EnumProperty.m_61594_(p_61588_, p_61589_, p_187560_ -> true);
    }

    public static <T extends Enum<T>> EnumProperty<T> m_61594_(String p_61595_, Class<T> p_61596_, Predicate<T> p_61597_) {
        return EnumProperty.m_61590_(p_61595_, p_61596_, Arrays.stream((Enum[])p_61596_.getEnumConstants()).filter(p_61597_).collect(Collectors.toList()));
    }

    public static <T extends Enum<T>> EnumProperty<T> m_61598_(String p_61599_, Class<T> p_61600_, T ... p_61601_) {
        return EnumProperty.m_61590_(p_61599_, p_61600_, Lists.newArrayList((Object[])p_61601_));
    }

    public static <T extends Enum<T>> EnumProperty<T> m_61590_(String p_61591_, Class<T> p_61592_, Collection<T> p_61593_) {
        return new EnumProperty<T>(p_61591_, p_61592_, p_61593_);
    }
}

