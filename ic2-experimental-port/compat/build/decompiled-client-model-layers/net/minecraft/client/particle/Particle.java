/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class Particle {
    private static final AABB f_107206_ = new AABB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    private static final double f_197408_ = Mth.m_144952_(100.0);
    protected final ClientLevel f_107208_;
    protected double f_107209_;
    protected double f_107210_;
    protected double f_107211_;
    protected double f_107212_;
    protected double f_107213_;
    protected double f_107214_;
    protected double f_107215_;
    protected double f_107216_;
    protected double f_107217_;
    private AABB f_107207_ = f_107206_;
    protected boolean f_107218_;
    protected boolean f_107219_ = true;
    private boolean f_107205_;
    protected boolean f_107220_;
    protected float f_107221_ = 0.6f;
    protected float f_107222_ = 1.8f;
    protected final RandomSource f_107223_ = RandomSource.m_216327_();
    protected int f_107224_;
    protected int f_107225_;
    protected float f_107226_;
    protected float f_107227_ = 1.0f;
    protected float f_107228_ = 1.0f;
    protected float f_107229_ = 1.0f;
    protected float f_107230_ = 1.0f;
    protected float f_107231_;
    protected float f_107204_;
    protected float f_172258_ = 0.98f;
    protected boolean f_172259_ = false;

    protected Particle(ClientLevel p_107234_, double p_107235_, double p_107236_, double p_107237_) {
        this.f_107208_ = p_107234_;
        this.m_107250_(0.2f, 0.2f);
        this.m_107264_(p_107235_, p_107236_, p_107237_);
        this.f_107209_ = p_107235_;
        this.f_107210_ = p_107236_;
        this.f_107211_ = p_107237_;
        this.f_107225_ = (int)(4.0f / (this.f_107223_.m_188501_() * 0.9f + 0.1f));
    }

    public Particle(ClientLevel p_107239_, double p_107240_, double p_107241_, double p_107242_, double p_107243_, double p_107244_, double p_107245_) {
        this(p_107239_, p_107240_, p_107241_, p_107242_);
        this.f_107215_ = p_107243_ + (Math.random() * 2.0 - 1.0) * (double)0.4f;
        this.f_107216_ = p_107244_ + (Math.random() * 2.0 - 1.0) * (double)0.4f;
        this.f_107217_ = p_107245_ + (Math.random() * 2.0 - 1.0) * (double)0.4f;
        double $$7 = (Math.random() + Math.random() + 1.0) * (double)0.15f;
        double $$8 = Math.sqrt(this.f_107215_ * this.f_107215_ + this.f_107216_ * this.f_107216_ + this.f_107217_ * this.f_107217_);
        this.f_107215_ = this.f_107215_ / $$8 * $$7 * (double)0.4f;
        this.f_107216_ = this.f_107216_ / $$8 * $$7 * (double)0.4f + (double)0.1f;
        this.f_107217_ = this.f_107217_ / $$8 * $$7 * (double)0.4f;
    }

    public Particle m_107268_(float p_107269_) {
        this.f_107215_ *= (double)p_107269_;
        this.f_107216_ = (this.f_107216_ - (double)0.1f) * (double)p_107269_ + (double)0.1f;
        this.f_107217_ *= (double)p_107269_;
        return this;
    }

    public void m_172260_(double p_172261_, double p_172262_, double p_172263_) {
        this.f_107215_ = p_172261_;
        this.f_107216_ = p_172262_;
        this.f_107217_ = p_172263_;
    }

    public Particle m_6569_(float p_107270_) {
        this.m_107250_(0.2f * p_107270_, 0.2f * p_107270_);
        return this;
    }

    public void m_107253_(float p_107254_, float p_107255_, float p_107256_) {
        this.f_107227_ = p_107254_;
        this.f_107228_ = p_107255_;
        this.f_107229_ = p_107256_;
    }

    protected void m_107271_(float p_107272_) {
        this.f_107230_ = p_107272_;
    }

    public void m_107257_(int p_107258_) {
        this.f_107225_ = p_107258_;
    }

    public int m_107273_() {
        return this.f_107225_;
    }

    public void m_5989_() {
        this.f_107209_ = this.f_107212_;
        this.f_107210_ = this.f_107213_;
        this.f_107211_ = this.f_107214_;
        if (this.f_107224_++ >= this.f_107225_) {
            this.m_107274_();
            return;
        }
        this.f_107216_ -= 0.04 * (double)this.f_107226_;
        this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
        if (this.f_172259_ && this.f_107213_ == this.f_107210_) {
            this.f_107215_ *= 1.1;
            this.f_107217_ *= 1.1;
        }
        this.f_107215_ *= (double)this.f_172258_;
        this.f_107216_ *= (double)this.f_172258_;
        this.f_107217_ *= (double)this.f_172258_;
        if (this.f_107218_) {
            this.f_107215_ *= (double)0.7f;
            this.f_107217_ *= (double)0.7f;
        }
    }

    public abstract void m_5744_(VertexConsumer var1, Camera var2, float var3);

    public abstract ParticleRenderType m_7556_();

    public String toString() {
        return this.getClass().getSimpleName() + ", Pos (" + this.f_107212_ + "," + this.f_107213_ + "," + this.f_107214_ + "), RGBA (" + this.f_107227_ + "," + this.f_107228_ + "," + this.f_107229_ + "," + this.f_107230_ + "), Age " + this.f_107224_;
    }

    public void m_107274_() {
        this.f_107220_ = true;
    }

    protected void m_107250_(float p_107251_, float p_107252_) {
        if (p_107251_ != this.f_107221_ || p_107252_ != this.f_107222_) {
            this.f_107221_ = p_107251_;
            this.f_107222_ = p_107252_;
            AABB $$2 = this.m_107277_();
            double $$3 = ($$2.f_82288_ + $$2.f_82291_ - (double)p_107251_) / 2.0;
            double $$4 = ($$2.f_82290_ + $$2.f_82293_ - (double)p_107251_) / 2.0;
            this.m_107259_(new AABB($$3, $$2.f_82289_, $$4, $$3 + (double)this.f_107221_, $$2.f_82289_ + (double)this.f_107222_, $$4 + (double)this.f_107221_));
        }
    }

    public void m_107264_(double p_107265_, double p_107266_, double p_107267_) {
        this.f_107212_ = p_107265_;
        this.f_107213_ = p_107266_;
        this.f_107214_ = p_107267_;
        float $$3 = this.f_107221_ / 2.0f;
        float $$4 = this.f_107222_;
        this.m_107259_(new AABB(p_107265_ - (double)$$3, p_107266_, p_107267_ - (double)$$3, p_107265_ + (double)$$3, p_107266_ + (double)$$4, p_107267_ + (double)$$3));
    }

    public void m_6257_(double p_107246_, double p_107247_, double p_107248_) {
        if (this.f_107205_) {
            return;
        }
        double $$3 = p_107246_;
        double $$4 = p_107247_;
        double $$5 = p_107248_;
        if (this.f_107219_ && (p_107246_ != 0.0 || p_107247_ != 0.0 || p_107248_ != 0.0) && p_107246_ * p_107246_ + p_107247_ * p_107247_ + p_107248_ * p_107248_ < f_197408_) {
            Vec3 $$6 = Entity.m_198894_(null, new Vec3(p_107246_, p_107247_, p_107248_), this.m_107277_(), this.f_107208_, List.of());
            p_107246_ = $$6.f_82479_;
            p_107247_ = $$6.f_82480_;
            p_107248_ = $$6.f_82481_;
        }
        if (p_107246_ != 0.0 || p_107247_ != 0.0 || p_107248_ != 0.0) {
            this.m_107259_(this.m_107277_().m_82386_(p_107246_, p_107247_, p_107248_));
            this.m_107275_();
        }
        if (Math.abs($$4) >= (double)1.0E-5f && Math.abs(p_107247_) < (double)1.0E-5f) {
            this.f_107205_ = true;
        }
        boolean bl = this.f_107218_ = $$4 != p_107247_ && $$4 < 0.0;
        if ($$3 != p_107246_) {
            this.f_107215_ = 0.0;
        }
        if ($$5 != p_107248_) {
            this.f_107217_ = 0.0;
        }
    }

    protected void m_107275_() {
        AABB $$0 = this.m_107277_();
        this.f_107212_ = ($$0.f_82288_ + $$0.f_82291_) / 2.0;
        this.f_107213_ = $$0.f_82289_;
        this.f_107214_ = ($$0.f_82290_ + $$0.f_82293_) / 2.0;
    }

    protected int m_6355_(float p_107249_) {
        BlockPos $$1 = new BlockPos(this.f_107212_, this.f_107213_, this.f_107214_);
        if (this.f_107208_.m_46805_($$1)) {
            return LevelRenderer.m_109541_(this.f_107208_, $$1);
        }
        return 0;
    }

    public boolean m_107276_() {
        return !this.f_107220_;
    }

    public AABB m_107277_() {
        return this.f_107207_;
    }

    public void m_107259_(AABB p_107260_) {
        this.f_107207_ = p_107260_;
    }

    public Optional<ParticleGroup> m_142654_() {
        return Optional.empty();
    }
}

