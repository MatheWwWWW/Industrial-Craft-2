/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.BeeDebugRenderer;
import net.minecraft.client.renderer.debug.BrainDebugRenderer;
import net.minecraft.client.renderer.debug.ChunkBorderRenderer;
import net.minecraft.client.renderer.debug.ChunkDebugRenderer;
import net.minecraft.client.renderer.debug.CollisionBoxRenderer;
import net.minecraft.client.renderer.debug.GameEventListenerRenderer;
import net.minecraft.client.renderer.debug.GameTestDebugRenderer;
import net.minecraft.client.renderer.debug.GoalSelectorDebugRenderer;
import net.minecraft.client.renderer.debug.HeightMapRenderer;
import net.minecraft.client.renderer.debug.LightDebugRenderer;
import net.minecraft.client.renderer.debug.NeighborsUpdateRenderer;
import net.minecraft.client.renderer.debug.PathfindingRenderer;
import net.minecraft.client.renderer.debug.RaidDebugRenderer;
import net.minecraft.client.renderer.debug.SolidFaceRenderer;
import net.minecraft.client.renderer.debug.StructureRenderer;
import net.minecraft.client.renderer.debug.VillageSectionsDebugRenderer;
import net.minecraft.client.renderer.debug.WaterDebugRenderer;
import net.minecraft.client.renderer.debug.WorldGenAttemptRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class DebugRenderer {
    public final PathfindingRenderer f_113413_ = new PathfindingRenderer();
    public final SimpleDebugRenderer f_113414_;
    public final SimpleDebugRenderer f_113415_;
    public final SimpleDebugRenderer f_113416_;
    public final SimpleDebugRenderer f_113417_;
    public final SimpleDebugRenderer f_113418_;
    public final StructureRenderer f_113420_;
    public final SimpleDebugRenderer f_113421_;
    public final SimpleDebugRenderer f_113422_;
    public final SimpleDebugRenderer f_113423_;
    public final SimpleDebugRenderer f_113424_;
    public final BrainDebugRenderer f_113425_;
    public final VillageSectionsDebugRenderer f_113426_;
    public final BeeDebugRenderer f_113427_;
    public final RaidDebugRenderer f_113428_;
    public final GoalSelectorDebugRenderer f_113429_;
    public final GameTestDebugRenderer f_113430_;
    public final GameEventListenerRenderer f_173815_;
    private boolean f_113431_;

    public DebugRenderer(Minecraft p_113433_) {
        this.f_113414_ = new WaterDebugRenderer(p_113433_);
        this.f_113415_ = new ChunkBorderRenderer(p_113433_);
        this.f_113416_ = new HeightMapRenderer(p_113433_);
        this.f_113417_ = new CollisionBoxRenderer(p_113433_);
        this.f_113418_ = new NeighborsUpdateRenderer(p_113433_);
        this.f_113420_ = new StructureRenderer(p_113433_);
        this.f_113421_ = new LightDebugRenderer(p_113433_);
        this.f_113422_ = new WorldGenAttemptRenderer();
        this.f_113423_ = new SolidFaceRenderer(p_113433_);
        this.f_113424_ = new ChunkDebugRenderer(p_113433_);
        this.f_113425_ = new BrainDebugRenderer(p_113433_);
        this.f_113426_ = new VillageSectionsDebugRenderer();
        this.f_113427_ = new BeeDebugRenderer(p_113433_);
        this.f_113428_ = new RaidDebugRenderer(p_113433_);
        this.f_113429_ = new GoalSelectorDebugRenderer(p_113433_);
        this.f_113430_ = new GameTestDebugRenderer();
        this.f_173815_ = new GameEventListenerRenderer(p_113433_);
    }

    public void m_113434_() {
        this.f_113413_.m_5630_();
        this.f_113414_.m_5630_();
        this.f_113415_.m_5630_();
        this.f_113416_.m_5630_();
        this.f_113417_.m_5630_();
        this.f_113418_.m_5630_();
        this.f_113420_.m_5630_();
        this.f_113421_.m_5630_();
        this.f_113422_.m_5630_();
        this.f_113423_.m_5630_();
        this.f_113424_.m_5630_();
        this.f_113425_.m_5630_();
        this.f_113426_.m_5630_();
        this.f_113427_.m_5630_();
        this.f_113428_.m_5630_();
        this.f_113429_.m_5630_();
        this.f_113430_.m_5630_();
        this.f_173815_.m_5630_();
    }

    public boolean m_113506_() {
        this.f_113431_ = !this.f_113431_;
        return this.f_113431_;
    }

    public void m_113457_(PoseStack p_113458_, MultiBufferSource.BufferSource p_113459_, double p_113460_, double p_113461_, double p_113462_) {
        if (this.f_113431_ && !Minecraft.m_91087_().m_91299_()) {
            this.f_113415_.m_7790_(p_113458_, p_113459_, p_113460_, p_113461_, p_113462_);
        }
        this.f_113430_.m_7790_(p_113458_, p_113459_, p_113460_, p_113461_, p_113462_);
    }

    public static Optional<Entity> m_113448_(@Nullable Entity p_113449_, int p_113450_) {
        int $$6;
        Predicate<Entity> $$7;
        AABB $$5;
        Vec3 $$3;
        Vec3 $$4;
        if (p_113449_ == null) {
            return Optional.empty();
        }
        Vec3 $$2 = p_113449_.m_146892_();
        EntityHitResult $$8 = ProjectileUtil.m_37287_(p_113449_, $$2, $$4 = $$2.m_82549_($$3 = p_113449_.m_20252_(1.0f).m_82490_(p_113450_)), $$5 = p_113449_.m_20191_().m_82369_($$3).m_82400_(1.0), $$7 = p_113447_ -> !p_113447_.m_5833_() && p_113447_.m_6087_(), $$6 = p_113450_ * p_113450_);
        if ($$8 == null) {
            return Optional.empty();
        }
        if ($$2.m_82557_($$8.m_82450_()) > (double)$$6) {
            return Optional.empty();
        }
        return Optional.of($$8.m_82443_());
    }

    public static void m_113470_(BlockPos p_113471_, BlockPos p_113472_, float p_113473_, float p_113474_, float p_113475_, float p_113476_) {
        Camera $$6 = Minecraft.m_91087_().f_91063_.m_109153_();
        if (!$$6.m_90593_()) {
            return;
        }
        Vec3 $$7 = $$6.m_90583_().m_82548_();
        AABB $$8 = new AABB(p_113471_, p_113472_).m_82383_($$7);
        DebugRenderer.m_113451_($$8, p_113473_, p_113474_, p_113475_, p_113476_);
    }

    public static void m_113463_(BlockPos p_113464_, float p_113465_, float p_113466_, float p_113467_, float p_113468_, float p_113469_) {
        Camera $$6 = Minecraft.m_91087_().f_91063_.m_109153_();
        if (!$$6.m_90593_()) {
            return;
        }
        Vec3 $$7 = $$6.m_90583_().m_82548_();
        AABB $$8 = new AABB(p_113464_).m_82383_($$7).m_82400_(p_113465_);
        DebugRenderer.m_113451_($$8, p_113466_, p_113467_, p_113468_, p_113469_);
    }

    public static void m_113451_(AABB p_113452_, float p_113453_, float p_113454_, float p_113455_, float p_113456_) {
        DebugRenderer.m_113435_(p_113452_.f_82288_, p_113452_.f_82289_, p_113452_.f_82290_, p_113452_.f_82291_, p_113452_.f_82292_, p_113452_.f_82293_, p_113453_, p_113454_, p_113455_, p_113456_);
    }

    public static void m_113435_(double p_113436_, double p_113437_, double p_113438_, double p_113439_, double p_113440_, double p_113441_, float p_113442_, float p_113443_, float p_113444_, float p_113445_) {
        Tesselator $$10 = Tesselator.m_85913_();
        BufferBuilder $$11 = $$10.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        $$11.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);
        LevelRenderer.m_109556_($$11, p_113436_, p_113437_, p_113438_, p_113439_, p_113440_, p_113441_, p_113442_, p_113443_, p_113444_, p_113445_);
        $$10.m_85914_();
    }

    public static void m_113500_(String p_113501_, int p_113502_, int p_113503_, int p_113504_, int p_113505_) {
        DebugRenderer.m_113477_(p_113501_, (double)p_113502_ + 0.5, (double)p_113503_ + 0.5, (double)p_113504_ + 0.5, p_113505_);
    }

    public static void m_113477_(String p_113478_, double p_113479_, double p_113480_, double p_113481_, int p_113482_) {
        DebugRenderer.m_113483_(p_113478_, p_113479_, p_113480_, p_113481_, p_113482_, 0.02f);
    }

    public static void m_113483_(String p_113484_, double p_113485_, double p_113486_, double p_113487_, int p_113488_, float p_113489_) {
        DebugRenderer.m_113490_(p_113484_, p_113485_, p_113486_, p_113487_, p_113488_, p_113489_, true, 0.0f, false);
    }

    public static void m_113490_(String p_113491_, double p_113492_, double p_113493_, double p_113494_, int p_113495_, float p_113496_, boolean p_113497_, float p_113498_, boolean p_113499_) {
        Minecraft $$9 = Minecraft.m_91087_();
        Camera $$10 = $$9.f_91063_.m_109153_();
        if (!$$10.m_90593_() || $$9.m_91290_().f_114360_ == null) {
            return;
        }
        Font $$11 = $$9.f_91062_;
        double $$12 = $$10.m_90583_().f_82479_;
        double $$13 = $$10.m_90583_().f_82480_;
        double $$14 = $$10.m_90583_().f_82481_;
        PoseStack $$15 = RenderSystem.m_157191_();
        $$15.m_85836_();
        $$15.m_85837_((float)(p_113492_ - $$12), (float)(p_113493_ - $$13) + 0.07f, (float)(p_113494_ - $$14));
        $$15.m_166854_(new Matrix4f($$10.m_90591_()));
        $$15.m_85841_(p_113496_, -p_113496_, p_113496_);
        RenderSystem.m_69493_();
        if (p_113499_) {
            RenderSystem.m_69465_();
        } else {
            RenderSystem.m_69482_();
        }
        RenderSystem.m_69458_(true);
        $$15.m_85841_(-1.0f, 1.0f, 1.0f);
        RenderSystem.m_157182_();
        float $$16 = p_113497_ ? (float)(-$$11.m_92895_(p_113491_)) / 2.0f : 0.0f;
        MultiBufferSource.BufferSource $$17 = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
        $$11.m_92811_(p_113491_, $$16 -= p_113498_ / p_113496_, 0.0f, p_113495_, false, Transformation.m_121093_().m_121104_(), $$17, p_113499_, 0, 0xF000F0);
        $$17.m_109911_();
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_69482_();
        $$15.m_85849_();
        RenderSystem.m_157182_();
    }

    public static interface SimpleDebugRenderer {
        public void m_7790_(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7);

        default public void m_5630_() {
        }
    }
}

