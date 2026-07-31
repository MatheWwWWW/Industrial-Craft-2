/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.util.Mth;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;

public class VibrationSignalParticle
extends TextureSheetParticle {
    private final PositionSource f_234103_;
    private float f_172462_;
    private float f_172460_;

    VibrationSignalParticle(ClientLevel p_234105_, double p_234106_, double p_234107_, double p_234108_, PositionSource p_234109_, int p_234110_) {
        super(p_234105_, p_234106_, p_234107_, p_234108_, 0.0, 0.0, 0.0);
        this.f_107663_ = 0.3f;
        this.f_234103_ = p_234109_;
        this.f_107225_ = p_234110_;
    }

    @Override
    public void m_5744_(VertexConsumer p_172475_, Camera p_172476_, float p_172477_) {
        float $$3 = Mth.m_14031_(((float)this.f_107224_ + p_172477_ - (float)Math.PI * 2) * 0.05f) * 2.0f;
        float $$4 = Mth.m_14179_(p_172477_, this.f_172460_, this.f_172462_);
        float $$5 = 1.0472f;
        this.m_172478_(p_172475_, p_172476_, p_172477_, p_172487_ -> {
            p_172487_.m_80148_(Vector3f.f_122225_.m_122270_($$4));
            p_172487_.m_80148_(Vector3f.f_122223_.m_122270_(-1.0472f));
            p_172487_.m_80148_(Vector3f.f_122225_.m_122270_($$3));
        });
        this.m_172478_(p_172475_, p_172476_, p_172477_, p_172473_ -> {
            p_172473_.m_80148_(Vector3f.f_122225_.m_122270_((float)(-Math.PI) + $$4));
            p_172473_.m_80148_(Vector3f.f_122223_.m_122270_(1.0472f));
            p_172473_.m_80148_(Vector3f.f_122225_.m_122270_($$3));
        });
    }

    private void m_172478_(VertexConsumer p_172479_, Camera p_172480_, float p_172481_, Consumer<Quaternion> p_172482_) {
        Vec3 $$4 = p_172480_.m_90583_();
        float $$5 = (float)(Mth.m_14139_(p_172481_, this.f_107209_, this.f_107212_) - $$4.m_7096_());
        float $$6 = (float)(Mth.m_14139_(p_172481_, this.f_107210_, this.f_107213_) - $$4.m_7098_());
        float $$7 = (float)(Mth.m_14139_(p_172481_, this.f_107211_, this.f_107214_) - $$4.m_7094_());
        Vector3f $$8 = new Vector3f(0.5f, 0.5f, 0.5f);
        $$8.m_122278_();
        Quaternion $$9 = new Quaternion($$8, 0.0f, true);
        p_172482_.accept($$9);
        Vector3f $$10 = new Vector3f(-1.0f, -1.0f, 0.0f);
        $$10.m_122251_($$9);
        Vector3f[] $$11 = new Vector3f[]{new Vector3f(-1.0f, -1.0f, 0.0f), new Vector3f(-1.0f, 1.0f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector3f(1.0f, -1.0f, 0.0f)};
        float $$12 = this.m_5902_(p_172481_);
        for (int $$13 = 0; $$13 < 4; ++$$13) {
            Vector3f $$14 = $$11[$$13];
            $$14.m_122251_($$9);
            $$14.m_122261_($$12);
            $$14.m_122272_($$5, $$6, $$7);
        }
        float $$15 = this.m_5970_();
        float $$16 = this.m_5952_();
        float $$17 = this.m_5951_();
        float $$18 = this.m_5950_();
        int $$19 = this.m_6355_(p_172481_);
        p_172479_.m_5483_($$11[0].m_122239_(), $$11[0].m_122260_(), $$11[0].m_122269_()).m_7421_($$16, $$18).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_($$19).m_5752_();
        p_172479_.m_5483_($$11[1].m_122239_(), $$11[1].m_122260_(), $$11[1].m_122269_()).m_7421_($$16, $$17).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_($$19).m_5752_();
        p_172479_.m_5483_($$11[2].m_122239_(), $$11[2].m_122260_(), $$11[2].m_122269_()).m_7421_($$15, $$17).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_($$19).m_5752_();
        p_172479_.m_5483_($$11[3].m_122239_(), $$11[3].m_122260_(), $$11[3].m_122269_()).m_7421_($$15, $$18).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_($$19).m_5752_();
    }

    @Override
    public int m_6355_(float p_172469_) {
        return 240;
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107431_;
    }

    @Override
    public void m_5989_() {
        this.f_107209_ = this.f_107212_;
        this.f_107210_ = this.f_107213_;
        this.f_107211_ = this.f_107214_;
        if (this.f_107224_++ >= this.f_107225_) {
            this.m_107274_();
            return;
        }
        Optional<Vec3> $$0 = this.f_234103_.m_142502_(this.f_107208_);
        if ($$0.isEmpty()) {
            this.m_107274_();
            return;
        }
        int $$1 = this.f_107225_ - this.f_107224_;
        double $$2 = 1.0 / (double)$$1;
        Vec3 $$3 = $$0.get();
        this.f_107212_ = Mth.m_14139_($$2, this.f_107212_, $$3.m_7096_());
        this.f_107213_ = Mth.m_14139_($$2, this.f_107213_, $$3.m_7098_());
        this.f_107214_ = Mth.m_14139_($$2, this.f_107214_, $$3.m_7094_());
        this.f_172460_ = this.f_172462_;
        this.f_172462_ = (float)Mth.m_14136_(this.f_107212_ - $$3.m_7096_(), this.f_107214_ - $$3.m_7094_());
    }

    public static class Provider
    implements ParticleProvider<VibrationParticleOption> {
        private final SpriteSet f_172488_;

        public Provider(SpriteSet p_172490_) {
            this.f_172488_ = p_172490_;
        }

        @Override
        public Particle m_6966_(VibrationParticleOption p_172501_, ClientLevel p_172502_, double p_172503_, double p_172504_, double p_172505_, double p_172506_, double p_172507_, double p_172508_) {
            VibrationSignalParticle $$8 = new VibrationSignalParticle(p_172502_, p_172503_, p_172504_, p_172505_, p_172501_.m_235983_(), p_172501_.m_235984_());
            $$8.m_108335_(this.f_172488_);
            $$8.m_107271_(1.0f);
            return $$8;
        }
    }
}

