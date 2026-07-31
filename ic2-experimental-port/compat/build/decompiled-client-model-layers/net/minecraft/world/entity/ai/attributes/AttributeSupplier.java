/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.attributes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class AttributeSupplier {
    private final Map<Attribute, AttributeInstance> f_22241_;

    public AttributeSupplier(Map<Attribute, AttributeInstance> p_22243_) {
        this.f_22241_ = ImmutableMap.copyOf(p_22243_);
    }

    private AttributeInstance m_22260_(Attribute p_22261_) {
        AttributeInstance $$1 = this.f_22241_.get(p_22261_);
        if ($$1 == null) {
            throw new IllegalArgumentException("Can't find attribute " + Registry.f_122866_.m_7981_(p_22261_));
        }
        return $$1;
    }

    public double m_22245_(Attribute p_22246_) {
        return this.m_22260_(p_22246_).m_22135_();
    }

    public double m_22253_(Attribute p_22254_) {
        return this.m_22260_(p_22254_).m_22115_();
    }

    public double m_22247_(Attribute p_22248_, UUID p_22249_) {
        AttributeModifier $$2 = this.m_22260_(p_22248_).m_22111_(p_22249_);
        if ($$2 == null) {
            throw new IllegalArgumentException("Can't find modifier " + p_22249_ + " on attribute " + Registry.f_122866_.m_7981_(p_22248_));
        }
        return $$2.m_22218_();
    }

    @Nullable
    public AttributeInstance m_22250_(Consumer<AttributeInstance> p_22251_, Attribute p_22252_) {
        AttributeInstance $$2 = this.f_22241_.get(p_22252_);
        if ($$2 == null) {
            return null;
        }
        AttributeInstance $$3 = new AttributeInstance(p_22252_, p_22251_);
        $$3.m_22102_($$2);
        return $$3;
    }

    public static Builder m_22244_() {
        return new Builder();
    }

    public boolean m_22258_(Attribute p_22259_) {
        return this.f_22241_.containsKey(p_22259_);
    }

    public boolean m_22255_(Attribute p_22256_, UUID p_22257_) {
        AttributeInstance $$2 = this.f_22241_.get(p_22256_);
        return $$2 != null && $$2.m_22111_(p_22257_) != null;
    }

    public static class Builder {
        private final Map<Attribute, AttributeInstance> f_22262_ = Maps.newHashMap();
        private boolean f_22263_;

        private AttributeInstance m_22274_(Attribute p_22275_) {
            AttributeInstance $$1 = new AttributeInstance(p_22275_, p_22273_ -> {
                if (this.f_22263_) {
                    throw new UnsupportedOperationException("Tried to change value for default attribute instance: " + Registry.f_122866_.m_7981_(p_22275_));
                }
            });
            this.f_22262_.put(p_22275_, $$1);
            return $$1;
        }

        public Builder m_22266_(Attribute p_22267_) {
            this.m_22274_(p_22267_);
            return this;
        }

        public Builder m_22268_(Attribute p_22269_, double p_22270_) {
            AttributeInstance $$2 = this.m_22274_(p_22269_);
            $$2.m_22100_(p_22270_);
            return this;
        }

        public AttributeSupplier m_22265_() {
            this.f_22263_ = true;
            return new AttributeSupplier(this.f_22262_);
        }
    }
}

