/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.attributes;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class RangedAttribute
extends Attribute {
    private final double f_22307_;
    private final double f_22308_;

    public RangedAttribute(String p_22310_, double p_22311_, double p_22312_, double p_22313_) {
        super(p_22310_, p_22311_);
        this.f_22307_ = p_22312_;
        this.f_22308_ = p_22313_;
        if (p_22312_ > p_22313_) {
            throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
        }
        if (p_22311_ < p_22312_) {
            throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
        }
        if (p_22311_ > p_22313_) {
            throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
        }
    }

    public double m_147361_() {
        return this.f_22307_;
    }

    public double m_147362_() {
        return this.f_22308_;
    }

    @Override
    public double m_6740_(double p_22315_) {
        if (Double.isNaN(p_22315_)) {
            return this.f_22307_;
        }
        return Mth.m_14008_(p_22315_, this.f_22307_, this.f_22308_);
    }
}

