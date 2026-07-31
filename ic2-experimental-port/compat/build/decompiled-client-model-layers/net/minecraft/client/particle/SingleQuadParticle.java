/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public abstract class SingleQuadParticle
extends Particle {
    protected float f_107663_;

    protected SingleQuadParticle(ClientLevel p_107665_, double p_107666_, double p_107667_, double p_107668_) {
        super(p_107665_, p_107666_, p_107667_, p_107668_);
        this.f_107663_ = 0.1f * (this.f_107223_.m_188501_() * 0.5f + 0.5f) * 2.0f;
    }

    protected SingleQuadParticle(ClientLevel p_107670_, double p_107671_, double p_107672_, double p_107673_, double p_107674_, double p_107675_, double p_107676_) {
        super(p_107670_, p_107671_, p_107672_, p_107673_, p_107674_, p_107675_, p_107676_);
        this.f_107663_ = 0.1f * (this.f_107223_.m_188501_() * 0.5f + 0.5f) * 2.0f;
    }

    @Override
    public void m_5744_(VertexConsumer p_107678_, Camera p_107679_, float p_107680_) {
        Quaternion $$8;
        Vec3 $$3 = p_107679_.m_90583_();
        float $$4 = (float)(Mth.m_14139_(p_107680_, this.f_107209_, this.f_107212_) - $$3.m_7096_());
        float $$5 = (float)(Mth.m_14139_(p_107680_, this.f_107210_, this.f_107213_) - $$3.m_7098_());
        float $$6 = (float)(Mth.m_14139_(p_107680_, this.f_107211_, this.f_107214_) - $$3.m_7094_());
        if (this.f_107231_ == 0.0f) {
            Quaternion $$7 = p_107679_.m_90591_();
        } else {
            $$8 = new Quaternion(p_107679_.m_90591_());
            float $$9 = Mth.m_14179_(p_107680_, this.f_107204_, this.f_107231_);
            $$8.m_80148_(Vector3f.f_122227_.m_122270_($$9));
        }
        Vector3f $$10 = new Vector3f(-1.0f, -1.0f, 0.0f);
        $$10.m_122251_($$8);
        Vector3f[] $$11 = new Vector3f[]{new Vector3f(-1.0f, -1.0f, 0.0f), new Vector3f(-1.0f, 1.0f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector3f(1.0f, -1.0f, 0.0f)};
        float $$12 = this.m_5902_(p_107680_);
        for (int $$13 = 0; $$13 < 4; ++$$13) {
            Vector3f $$14 = $$11[$$13];
            $$14.m_122251_($$8);
            $$14.m_122261_($$12);
            $$14.m_122272_($$4, $$5, $$6);
        }
        float $$15 = this.m_5970_();
        float $$16 = this.m_5952_();
        float $$17 = this.m_5951_();
        float $$18 = this.m_5950_();
        int $$19 = this.m_6355_(p_107680_);
        p_107678_.m_5483_($$11[0].m_122239_(), $$11[0].m_122260_(), $$11[0].m_122269_()).m_7421_($$16, $$18).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_($$19).m_5752_();
        p_107678_.m_5483_($$11[1].m_122239_(), $$11[1].m_122260_(), $$11[1].m_122269_()).m_7421_($$16, $$17).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_($$19).m_5752_();
        p_107678_.m_5483_($$11[2].m_122239_(), $$11[2].m_122260_(), $$11[2].m_122269_()).m_7421_($$15, $$17).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_($$19).m_5752_();
        p_107678_.m_5483_($$11[3].m_122239_(), $$11[3].m_122260_(), $$11[3].m_122269_()).m_7421_($$15, $$18).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_($$19).m_5752_();
    }

    public float m_5902_(float p_107681_) {
        return this.f_107663_;
    }

    @Override
    public Particle m_6569_(float p_107683_) {
        this.f_107663_ *= p_107683_;
        return super.m_6569_(p_107683_);
    }

    protected abstract float m_5970_();

    protected abstract float m_5952_();

    protected abstract float m_5951_();

    protected abstract float m_5950_();
}

