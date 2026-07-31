/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.function.Consumer;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class ShriekParticle
extends TextureSheetParticle {
    private static final Vector3f f_233972_ = Util.m_137469_(new Vector3f(0.5f, 0.5f, 0.5f), Vector3f::m_122278_);
    private static final Vector3f f_233973_ = new Vector3f(-1.0f, -1.0f, 0.0f);
    private static final float f_233970_ = 1.0472f;
    private int f_233971_;

    ShriekParticle(ClientLevel p_233976_, double p_233977_, double p_233978_, double p_233979_, int p_233980_) {
        super(p_233976_, p_233977_, p_233978_, p_233979_, 0.0, 0.0, 0.0);
        this.f_107663_ = 0.85f;
        this.f_233971_ = p_233980_;
        this.f_107225_ = 30;
        this.f_107226_ = 0.0f;
        this.f_107215_ = 0.0;
        this.f_107216_ = 0.1;
        this.f_107217_ = 0.0;
    }

    @Override
    public float m_5902_(float p_234003_) {
        return this.f_107663_ * Mth.m_14036_(((float)this.f_107224_ + p_234003_) / (float)this.f_107225_ * 0.75f, 0.0f, 1.0f);
    }

    @Override
    public void m_5744_(VertexConsumer p_233985_, Camera p_233986_, float p_233987_) {
        if (this.f_233971_ > 0) {
            return;
        }
        this.f_107230_ = 1.0f - Mth.m_14036_(((float)this.f_107224_ + p_233987_) / (float)this.f_107225_, 0.0f, 1.0f);
        this.m_233988_(p_233985_, p_233986_, p_233987_, p_234005_ -> {
            p_234005_.m_80148_(Vector3f.f_122225_.m_122270_(0.0f));
            p_234005_.m_80148_(Vector3f.f_122223_.m_122270_(-1.0472f));
        });
        this.m_233988_(p_233985_, p_233986_, p_233987_, p_234000_ -> {
            p_234000_.m_80148_(Vector3f.f_122225_.m_122270_((float)(-Math.PI)));
            p_234000_.m_80148_(Vector3f.f_122223_.m_122270_(1.0472f));
        });
    }

    private void m_233988_(VertexConsumer p_233989_, Camera p_233990_, float p_233991_, Consumer<Quaternion> p_233992_) {
        Vec3 $$4 = p_233990_.m_90583_();
        float $$5 = (float)(Mth.m_14139_(p_233991_, this.f_107209_, this.f_107212_) - $$4.m_7096_());
        float $$6 = (float)(Mth.m_14139_(p_233991_, this.f_107210_, this.f_107213_) - $$4.m_7098_());
        float $$7 = (float)(Mth.m_14139_(p_233991_, this.f_107211_, this.f_107214_) - $$4.m_7094_());
        Quaternion $$8 = new Quaternion(f_233972_, 0.0f, true);
        p_233992_.accept($$8);
        f_233973_.m_122251_($$8);
        Vector3f[] $$9 = new Vector3f[]{new Vector3f(-1.0f, -1.0f, 0.0f), new Vector3f(-1.0f, 1.0f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector3f(1.0f, -1.0f, 0.0f)};
        float $$10 = this.m_5902_(p_233991_);
        for (int $$11 = 0; $$11 < 4; ++$$11) {
            Vector3f $$12 = $$9[$$11];
            $$12.m_122251_($$8);
            $$12.m_122261_($$10);
            $$12.m_122272_($$5, $$6, $$7);
        }
        int $$13 = this.m_6355_(p_233991_);
        this.m_233993_(p_233989_, $$9[0], this.m_5952_(), this.m_5950_(), $$13);
        this.m_233993_(p_233989_, $$9[1], this.m_5952_(), this.m_5951_(), $$13);
        this.m_233993_(p_233989_, $$9[2], this.m_5970_(), this.m_5951_(), $$13);
        this.m_233993_(p_233989_, $$9[3], this.m_5970_(), this.m_5950_(), $$13);
    }

    private void m_233993_(VertexConsumer p_233994_, Vector3f p_233995_, float p_233996_, float p_233997_, int p_233998_) {
        p_233994_.m_5483_(p_233995_.m_122239_(), p_233995_.m_122260_(), p_233995_.m_122269_()).m_7421_(p_233996_, p_233997_).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(p_233998_).m_5752_();
    }

    @Override
    public int m_6355_(float p_233983_) {
        return 240;
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107431_;
    }

    @Override
    public void m_5989_() {
        if (this.f_233971_ > 0) {
            --this.f_233971_;
            return;
        }
        super.m_5989_();
    }

    public static class Provider
    implements ParticleProvider<ShriekParticleOption> {
        private final SpriteSet f_234006_;

        public Provider(SpriteSet p_234008_) {
            this.f_234006_ = p_234008_;
        }

        @Override
        public Particle m_6966_(ShriekParticleOption p_234019_, ClientLevel p_234020_, double p_234021_, double p_234022_, double p_234023_, double p_234024_, double p_234025_, double p_234026_) {
            ShriekParticle $$8 = new ShriekParticle(p_234020_, p_234021_, p_234022_, p_234023_, p_234019_.m_235958_());
            $$8.m_108335_(this.f_234006_);
            $$8.m_107271_(1.0f);
            return $$8;
        }
    }
}

