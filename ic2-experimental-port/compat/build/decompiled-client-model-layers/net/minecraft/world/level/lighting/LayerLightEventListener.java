/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.lighting;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.lighting.LightEventListener;

public interface LayerLightEventListener
extends LightEventListener {
    @Nullable
    public DataLayer m_8079_(SectionPos var1);

    public int m_7768_(BlockPos var1);

    public static final class DummyLightLayerEventListener
    extends Enum<DummyLightLayerEventListener>
    implements LayerLightEventListener {
        public static final /* enum */ DummyLightLayerEventListener INSTANCE = new DummyLightLayerEventListener();
        private static final /* synthetic */ DummyLightLayerEventListener[] $VALUES;

        public static DummyLightLayerEventListener[] values() {
            return (DummyLightLayerEventListener[])$VALUES.clone();
        }

        public static DummyLightLayerEventListener valueOf(String p_75725_) {
            return Enum.valueOf(DummyLightLayerEventListener.class, p_75725_);
        }

        @Override
        @Nullable
        public DataLayer m_8079_(SectionPos p_75718_) {
            return null;
        }

        @Override
        public int m_7768_(BlockPos p_75723_) {
            return 0;
        }

        @Override
        public void m_7174_(BlockPos p_164434_) {
        }

        @Override
        public void m_8116_(BlockPos p_164436_, int p_164437_) {
        }

        @Override
        public boolean m_75643_() {
            return false;
        }

        @Override
        public int m_5738_(int p_164427_, boolean p_164428_, boolean p_164429_) {
            return p_164427_;
        }

        @Override
        public void m_6191_(SectionPos p_75720_, boolean p_75721_) {
        }

        @Override
        public void m_6460_(ChunkPos p_164431_, boolean p_164432_) {
        }

        private static /* synthetic */ DummyLightLayerEventListener[] m_164438_() {
            return new DummyLightLayerEventListener[]{INSTANCE};
        }

        static {
            $VALUES = DummyLightLayerEventListener.m_164438_();
        }
    }
}

