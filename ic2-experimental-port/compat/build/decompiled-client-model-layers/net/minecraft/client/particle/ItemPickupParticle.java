/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;

public class ItemPickupParticle
extends Particle {
    private static final int f_172257_ = 3;
    private final RenderBuffers f_107020_;
    private final Entity f_107021_;
    private final Entity f_107017_;
    private int f_107018_;
    private final EntityRenderDispatcher f_107019_;

    public ItemPickupParticle(EntityRenderDispatcher p_107023_, RenderBuffers p_107024_, ClientLevel p_107025_, Entity p_107026_, Entity p_107027_) {
        this(p_107023_, p_107024_, p_107025_, p_107026_, p_107027_, p_107026_.m_20184_());
    }

    private ItemPickupParticle(EntityRenderDispatcher p_107029_, RenderBuffers p_107030_, ClientLevel p_107031_, Entity p_107032_, Entity p_107033_, Vec3 p_107034_) {
        super(p_107031_, p_107032_.m_20185_(), p_107032_.m_20186_(), p_107032_.m_20189_(), p_107034_.f_82479_, p_107034_.f_82480_, p_107034_.f_82481_);
        this.f_107020_ = p_107030_;
        this.f_107021_ = this.m_107036_(p_107032_);
        this.f_107017_ = p_107033_;
        this.f_107019_ = p_107029_;
    }

    private Entity m_107036_(Entity p_107037_) {
        if (!(p_107037_ instanceof ItemEntity)) {
            return p_107037_;
        }
        return ((ItemEntity)p_107037_).m_32066_();
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107433_;
    }

    @Override
    public void m_5744_(VertexConsumer p_107039_, Camera p_107040_, float p_107041_) {
        float $$3 = ((float)this.f_107018_ + p_107041_) / 3.0f;
        $$3 *= $$3;
        double $$4 = Mth.m_14139_(p_107041_, this.f_107017_.f_19790_, this.f_107017_.m_20185_());
        double $$5 = Mth.m_14139_(p_107041_, this.f_107017_.f_19791_, (this.f_107017_.m_20186_() + this.f_107017_.m_20188_()) / 2.0);
        double $$6 = Mth.m_14139_(p_107041_, this.f_107017_.f_19792_, this.f_107017_.m_20189_());
        double $$7 = Mth.m_14139_($$3, this.f_107021_.m_20185_(), $$4);
        double $$8 = Mth.m_14139_($$3, this.f_107021_.m_20186_(), $$5);
        double $$9 = Mth.m_14139_($$3, this.f_107021_.m_20189_(), $$6);
        MultiBufferSource.BufferSource $$10 = this.f_107020_.m_110104_();
        Vec3 $$11 = p_107040_.m_90583_();
        this.f_107019_.m_114384_(this.f_107021_, $$7 - $$11.m_7096_(), $$8 - $$11.m_7098_(), $$9 - $$11.m_7094_(), this.f_107021_.m_146908_(), p_107041_, new PoseStack(), $$10, this.f_107019_.m_114394_(this.f_107021_, p_107041_));
        $$10.m_109911_();
    }

    @Override
    public void m_5989_() {
        ++this.f_107018_;
        if (this.f_107018_ == 3) {
            this.m_107274_();
        }
    }
}

