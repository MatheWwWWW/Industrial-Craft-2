/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.attributes;

public class Attribute {
    public static final int f_147357_ = 64;
    private final double f_22076_;
    private boolean f_22077_;
    private final String f_22078_;

    protected Attribute(String p_22080_, double p_22081_) {
        this.f_22076_ = p_22081_;
        this.f_22078_ = p_22080_;
    }

    public double m_22082_() {
        return this.f_22076_;
    }

    public boolean m_22086_() {
        return this.f_22077_;
    }

    public Attribute m_22084_(boolean p_22085_) {
        this.f_22077_ = p_22085_;
        return this;
    }

    public double m_6740_(double p_22083_) {
        return p_22083_;
    }

    public String m_22087_() {
        return this.f_22078_;
    }
}

