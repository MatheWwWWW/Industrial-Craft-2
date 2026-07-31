/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.phys.shapes;

import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;

public final class SubShape
extends DiscreteVoxelShape {
    private final DiscreteVoxelShape f_83182_;
    private final int f_83183_;
    private final int f_83184_;
    private final int f_83185_;
    private final int f_83186_;
    private final int f_83187_;
    private final int f_83188_;

    protected SubShape(DiscreteVoxelShape p_83190_, int p_83191_, int p_83192_, int p_83193_, int p_83194_, int p_83195_, int p_83196_) {
        super(p_83194_ - p_83191_, p_83195_ - p_83192_, p_83196_ - p_83193_);
        this.f_83182_ = p_83190_;
        this.f_83183_ = p_83191_;
        this.f_83184_ = p_83192_;
        this.f_83185_ = p_83193_;
        this.f_83186_ = p_83194_;
        this.f_83187_ = p_83195_;
        this.f_83188_ = p_83196_;
    }

    @Override
    public boolean m_6696_(int p_83206_, int p_83207_, int p_83208_) {
        return this.f_83182_.m_6696_(this.f_83183_ + p_83206_, this.f_83184_ + p_83207_, this.f_83185_ + p_83208_);
    }

    @Override
    public void m_142703_(int p_166060_, int p_166061_, int p_166062_) {
        this.f_83182_.m_142703_(this.f_83183_ + p_166060_, this.f_83184_ + p_166061_, this.f_83185_ + p_166062_);
    }

    @Override
    public int m_6538_(Direction.Axis p_83204_) {
        return this.m_166056_(p_83204_, this.f_83182_.m_6538_(p_83204_));
    }

    @Override
    public int m_6536_(Direction.Axis p_83210_) {
        return this.m_166056_(p_83210_, this.f_83182_.m_6536_(p_83210_));
    }

    private int m_166056_(Direction.Axis p_166057_, int p_166058_) {
        int $$2 = p_166057_.m_7863_(this.f_83183_, this.f_83184_, this.f_83185_);
        int $$3 = p_166057_.m_7863_(this.f_83186_, this.f_83187_, this.f_83188_);
        return Mth.m_14045_(p_166058_, $$2, $$3) - $$2;
    }
}

