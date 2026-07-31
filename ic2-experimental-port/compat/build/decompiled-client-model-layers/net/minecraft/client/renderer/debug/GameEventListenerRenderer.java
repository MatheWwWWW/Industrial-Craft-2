/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Vector3f;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;

public class GameEventListenerRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private final Minecraft f_173816_;
    private static final int f_173817_ = 32;
    private static final float f_173818_ = 1.0f;
    private final List<TrackedGameEvent> f_173819_ = Lists.newArrayList();
    private final List<TrackedListener> f_173820_ = Lists.newArrayList();

    public GameEventListenerRenderer(Minecraft p_173822_) {
        this.f_173816_ = p_173822_;
    }

    @Override
    public void m_7790_(PoseStack p_173846_, MultiBufferSource p_173847_, double p_173848_, double p_173849_, double p_173850_) {
        ClientLevel $$5 = this.f_173816_.f_91073_;
        if ($$5 == null) {
            this.f_173819_.clear();
            this.f_173820_.clear();
            return;
        }
        Vec3 $$6 = new Vec3(p_173848_, 0.0, p_173850_);
        this.f_173819_.removeIf(TrackedGameEvent::m_173868_);
        this.f_173820_.removeIf(p_234512_ -> p_234512_.m_234542_($$5, $$6));
        RenderSystem.m_69472_();
        RenderSystem.m_69482_();
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        VertexConsumer $$7 = p_173847_.m_6299_(RenderType.m_110504_());
        for (TrackedListener $$8 : this.f_173820_) {
            $$8.m_173875_($$5).ifPresent(p_234531_ -> {
                double $$7 = p_234531_.m_7096_() - (double)$$8.m_142078_();
                double $$8 = p_234531_.m_7098_() - (double)$$8.m_142078_();
                double $$9 = p_234531_.m_7094_() - (double)$$8.m_142078_();
                double $$10 = p_234531_.m_7096_() + (double)$$8.m_142078_();
                double $$11 = p_234531_.m_7098_() + (double)$$8.m_142078_();
                double $$12 = p_234531_.m_7094_() + (double)$$8.m_142078_();
                Vector3f $$13 = new Vector3f(1.0f, 1.0f, 0.0f);
                LevelRenderer.m_109654_(p_173846_, $$7, Shapes.m_83064_(new AABB($$7, $$8, $$9, $$10, $$11, $$12)), -p_173848_, -p_173849_, -p_173850_, $$13.m_122239_(), $$13.m_122260_(), $$13.m_122269_(), 0.35f);
            });
        }
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        Tesselator $$9 = Tesselator.m_85913_();
        BufferBuilder $$10 = $$9.m_85915_();
        $$10.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);
        for (TrackedListener $$11 : this.f_173820_) {
            $$11.m_173875_($$5).ifPresent(p_234523_ -> {
                Vector3f $$5 = new Vector3f(1.0f, 1.0f, 0.0f);
                LevelRenderer.m_109556_($$10, p_234523_.m_7096_() - 0.25 - p_173848_, p_234523_.m_7098_() - p_173849_, p_234523_.m_7094_() - 0.25 - p_173850_, p_234523_.m_7096_() + 0.25 - p_173848_, p_234523_.m_7098_() - p_173849_ + 1.0, p_234523_.m_7094_() + 0.25 - p_173850_, $$5.m_122239_(), $$5.m_122260_(), $$5.m_122269_(), 0.35f);
            });
        }
        $$9.m_85914_();
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_69832_(2.0f);
        RenderSystem.m_69458_(false);
        for (TrackedListener $$12 : this.f_173820_) {
            $$12.m_173875_($$5).ifPresent(p_234517_ -> {
                DebugRenderer.m_113483_("Listener Origin", p_234517_.m_7096_(), p_234517_.m_7098_() + (double)1.8f, p_234517_.m_7094_(), -1, 0.025f);
                DebugRenderer.m_113483_(new BlockPos((Vec3)p_234517_).toString(), p_234517_.m_7096_(), p_234517_.m_7098_() + 1.5, p_234517_.m_7094_(), -6959665, 0.025f);
            });
        }
        for (TrackedGameEvent $$13 : this.f_173819_) {
            Vec3 $$14 = $$13.f_173863_;
            double $$15 = 0.2f;
            double $$16 = $$14.f_82479_ - (double)0.2f;
            double $$17 = $$14.f_82480_ - (double)0.2f;
            double $$18 = $$14.f_82481_ - (double)0.2f;
            double $$19 = $$14.f_82479_ + (double)0.2f;
            double $$20 = $$14.f_82480_ + (double)0.2f + 0.5;
            double $$21 = $$14.f_82481_ + (double)0.2f;
            GameEventListenerRenderer.m_173833_(new AABB($$16, $$17, $$18, $$19, $$20, $$21), 1.0f, 1.0f, 1.0f, 0.2f);
            DebugRenderer.m_113483_($$13.f_173862_.m_157821_(), $$14.f_82479_, $$14.f_82480_ + (double)0.85f, $$14.f_82481_, -7564911, 0.0075f);
        }
        RenderSystem.m_69458_(true);
        RenderSystem.m_69493_();
        RenderSystem.m_69461_();
    }

    private static void m_173833_(AABB p_173834_, float p_173835_, float p_173836_, float p_173837_, float p_173838_) {
        Camera $$5 = Minecraft.m_91087_().f_91063_.m_109153_();
        if (!$$5.m_90593_()) {
            return;
        }
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        Vec3 $$6 = $$5.m_90583_().m_82548_();
        DebugRenderer.m_113451_(p_173834_.m_82383_($$6), p_173835_, p_173836_, p_173837_, p_173838_);
    }

    public void m_234513_(GameEvent p_234514_, Vec3 p_234515_) {
        this.f_173819_.add(new TrackedGameEvent(Util.m_137550_(), p_234514_, p_234515_));
    }

    public void m_173830_(PositionSource p_173831_, int p_173832_) {
        this.f_173820_.add(new TrackedListener(p_173831_, p_173832_));
    }

    static class TrackedListener
    implements GameEventListener {
        public final PositionSource f_173869_;
        public final int f_173870_;

        public TrackedListener(PositionSource p_173872_, int p_173873_) {
            this.f_173869_ = p_173872_;
            this.f_173870_ = p_173873_;
        }

        public boolean m_234542_(Level p_234543_, Vec3 p_234544_) {
            return this.f_173869_.m_142502_(p_234543_).filter(p_234547_ -> p_234547_.m_82557_(p_234544_) <= 1024.0).isPresent();
        }

        public Optional<Vec3> m_173875_(Level p_173876_) {
            return this.f_173869_.m_142502_(p_173876_);
        }

        @Override
        public PositionSource m_142460_() {
            return this.f_173869_;
        }

        @Override
        public int m_142078_() {
            return this.f_173870_;
        }

        @Override
        public boolean m_214068_(ServerLevel p_234540_, GameEvent.Message p_234541_) {
            return false;
        }
    }

    record TrackedGameEvent(long f_173861_, GameEvent f_173862_, Vec3 f_173863_) {
        public boolean m_173868_() {
            return Util.m_137550_() - this.f_173861_ > 3000L;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{TrackedGameEvent.class, "timeStamp;gameEvent;position", "f_173861_", "f_173862_", "f_173863_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{TrackedGameEvent.class, "timeStamp;gameEvent;position", "f_173861_", "f_173862_", "f_173863_"}, this);
        }

        @Override
        public final boolean equals(Object p_234536_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{TrackedGameEvent.class, "timeStamp;gameEvent;position", "f_173861_", "f_173862_", "f_173863_"}, this, p_234536_);
        }
    }
}

