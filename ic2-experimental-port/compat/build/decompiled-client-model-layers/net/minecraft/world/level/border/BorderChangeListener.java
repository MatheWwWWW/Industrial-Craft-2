/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.border;

import net.minecraft.world.level.border.WorldBorder;

public interface BorderChangeListener {
    public void m_6312_(WorldBorder var1, double var2);

    public void m_6689_(WorldBorder var1, double var2, double var4, long var6);

    public void m_7721_(WorldBorder var1, double var2, double var4);

    public void m_5904_(WorldBorder var1, int var2);

    public void m_5903_(WorldBorder var1, int var2);

    public void m_6315_(WorldBorder var1, double var2);

    public void m_6313_(WorldBorder var1, double var2);

    public static class DelegateBorderChangeListener
    implements BorderChangeListener {
        private final WorldBorder f_61864_;

        public DelegateBorderChangeListener(WorldBorder p_61866_) {
            this.f_61864_ = p_61866_;
        }

        @Override
        public void m_6312_(WorldBorder p_61868_, double p_61869_) {
            this.f_61864_.m_61917_(p_61869_);
        }

        @Override
        public void m_6689_(WorldBorder p_61875_, double p_61876_, double p_61877_, long p_61878_) {
            this.f_61864_.m_61919_(p_61876_, p_61877_, p_61878_);
        }

        @Override
        public void m_7721_(WorldBorder p_61871_, double p_61872_, double p_61873_) {
            this.f_61864_.m_61949_(p_61872_, p_61873_);
        }

        @Override
        public void m_5904_(WorldBorder p_61880_, int p_61881_) {
            this.f_61864_.m_61944_(p_61881_);
        }

        @Override
        public void m_5903_(WorldBorder p_61886_, int p_61887_) {
            this.f_61864_.m_61952_(p_61887_);
        }

        @Override
        public void m_6315_(WorldBorder p_61883_, double p_61884_) {
            this.f_61864_.m_61947_(p_61884_);
        }

        @Override
        public void m_6313_(WorldBorder p_61889_, double p_61890_) {
            this.f_61864_.m_61939_(p_61890_);
        }
    }
}

