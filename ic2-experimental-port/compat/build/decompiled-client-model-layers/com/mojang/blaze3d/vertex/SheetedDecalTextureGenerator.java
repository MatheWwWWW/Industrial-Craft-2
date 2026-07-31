/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.vertex.DefaultedVertexConsumer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.core.Direction;

public class SheetedDecalTextureGenerator
extends DefaultedVertexConsumer {
    private final VertexConsumer f_85867_;
    private final Matrix4f f_85868_;
    private final Matrix3f f_85869_;
    private float f_85870_;
    private float f_85871_;
    private float f_85872_;
    private int f_85873_;
    private int f_85874_;
    private int f_85875_;
    private float f_85876_;
    private float f_85877_;
    private float f_85878_;

    public SheetedDecalTextureGenerator(VertexConsumer p_85880_, Matrix4f p_85881_, Matrix3f p_85882_) {
        this.f_85867_ = p_85880_;
        this.f_85868_ = p_85881_.m_27658_();
        this.f_85868_.m_27657_();
        this.f_85869_ = p_85882_.m_8183_();
        this.f_85869_.m_8187_();
        this.m_85883_();
    }

    private void m_85883_() {
        this.f_85870_ = 0.0f;
        this.f_85871_ = 0.0f;
        this.f_85872_ = 0.0f;
        this.f_85873_ = 0;
        this.f_85874_ = 10;
        this.f_85875_ = 0xF000F0;
        this.f_85876_ = 0.0f;
        this.f_85877_ = 1.0f;
        this.f_85878_ = 0.0f;
    }

    @Override
    public void m_5752_() {
        Vector3f $$0 = new Vector3f(this.f_85876_, this.f_85877_, this.f_85878_);
        $$0.m_122249_(this.f_85869_);
        Direction $$1 = Direction.m_122372_($$0.m_122239_(), $$0.m_122260_(), $$0.m_122269_());
        Vector4f $$2 = new Vector4f(this.f_85870_, this.f_85871_, this.f_85872_, 1.0f);
        $$2.m_123607_(this.f_85868_);
        $$2.m_123609_(Vector3f.f_122225_.m_122240_(180.0f));
        $$2.m_123609_(Vector3f.f_122223_.m_122240_(-90.0f));
        $$2.m_123609_($$1.m_122406_());
        float $$3 = -$$2.m_123601_();
        float $$4 = -$$2.m_123615_();
        this.f_85867_.m_5483_(this.f_85870_, this.f_85871_, this.f_85872_).m_85950_(1.0f, 1.0f, 1.0f, 1.0f).m_7421_($$3, $$4).m_7122_(this.f_85873_, this.f_85874_).m_85969_(this.f_85875_).m_5601_(this.f_85876_, this.f_85877_, this.f_85878_).m_5752_();
        this.m_85883_();
    }

    @Override
    public VertexConsumer m_5483_(double p_85885_, double p_85886_, double p_85887_) {
        this.f_85870_ = (float)p_85885_;
        this.f_85871_ = (float)p_85886_;
        this.f_85872_ = (float)p_85887_;
        return this;
    }

    @Override
    public VertexConsumer m_6122_(int p_85895_, int p_85896_, int p_85897_, int p_85898_) {
        return this;
    }

    @Override
    public VertexConsumer m_7421_(float p_85889_, float p_85890_) {
        return this;
    }

    @Override
    public VertexConsumer m_7122_(int p_85892_, int p_85893_) {
        this.f_85873_ = p_85892_;
        this.f_85874_ = p_85893_;
        return this;
    }

    @Override
    public VertexConsumer m_7120_(int p_85904_, int p_85905_) {
        this.f_85875_ = p_85904_ | p_85905_ << 16;
        return this;
    }

    @Override
    public VertexConsumer m_5601_(float p_85900_, float p_85901_, float p_85902_) {
        this.f_85876_ = p_85900_;
        this.f_85877_ = p_85901_;
        this.f_85878_ = p_85902_;
        return this;
    }
}

