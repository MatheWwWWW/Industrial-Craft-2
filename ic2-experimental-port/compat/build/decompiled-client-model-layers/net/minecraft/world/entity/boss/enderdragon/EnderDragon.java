/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.boss.enderdragon;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhaseManager;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.pathfinder.BinaryHeap;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class EnderDragon
extends Mob
implements Enemy {
    private static final Logger f_31087_ = LogUtils.getLogger();
    public static final EntityDataAccessor<Integer> f_31067_ = SynchedEntityData.m_135353_(EnderDragon.class, EntityDataSerializers.f_135028_);
    private static final TargetingConditions f_31088_ = TargetingConditions.m_148352_().m_26883_(64.0);
    private static final int f_149566_ = 200;
    private static final int f_149567_ = 400;
    private static final float f_149568_ = 0.25f;
    private static final String f_149569_ = "DragonDeathTime";
    private static final String f_149570_ = "DragonPhase";
    public final double[][] f_31092_ = new double[64][3];
    public int f_31093_ = -1;
    private final EnderDragonPart[] f_31089_;
    public final EnderDragonPart f_31080_;
    private final EnderDragonPart f_31090_;
    private final EnderDragonPart f_31091_;
    private final EnderDragonPart f_31068_;
    private final EnderDragonPart f_31069_;
    private final EnderDragonPart f_31070_;
    private final EnderDragonPart f_31071_;
    private final EnderDragonPart f_31072_;
    public float f_31081_;
    public float f_31082_;
    public boolean f_31083_;
    public int f_31084_;
    public float f_31085_;
    @Nullable
    public EndCrystal f_31086_;
    @Nullable
    private final EndDragonFight f_31073_;
    private final EnderDragonPhaseManager f_31074_;
    private int f_31075_ = 100;
    private float f_31076_;
    private final Node[] f_31077_ = new Node[24];
    private final int[] f_31078_ = new int[24];
    private final BinaryHeap f_31079_ = new BinaryHeap();

    public EnderDragon(EntityType<? extends EnderDragon> p_31096_, Level p_31097_) {
        super((EntityType<? extends Mob>)EntityType.f_20565_, p_31097_);
        this.f_31080_ = new EnderDragonPart(this, "head", 1.0f, 1.0f);
        this.f_31090_ = new EnderDragonPart(this, "neck", 3.0f, 3.0f);
        this.f_31091_ = new EnderDragonPart(this, "body", 5.0f, 3.0f);
        this.f_31068_ = new EnderDragonPart(this, "tail", 2.0f, 2.0f);
        this.f_31069_ = new EnderDragonPart(this, "tail", 2.0f, 2.0f);
        this.f_31070_ = new EnderDragonPart(this, "tail", 2.0f, 2.0f);
        this.f_31071_ = new EnderDragonPart(this, "wing", 4.0f, 2.0f);
        this.f_31072_ = new EnderDragonPart(this, "wing", 4.0f, 2.0f);
        this.f_31089_ = new EnderDragonPart[]{this.f_31080_, this.f_31090_, this.f_31091_, this.f_31068_, this.f_31069_, this.f_31070_, this.f_31071_, this.f_31072_};
        this.m_21153_(this.m_21233_());
        this.f_19794_ = true;
        this.f_19811_ = true;
        this.f_31073_ = p_31097_ instanceof ServerLevel ? ((ServerLevel)p_31097_).m_8586_() : null;
        this.f_31074_ = new EnderDragonPhaseManager(this);
    }

    public static AttributeSupplier.Builder m_31167_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 200.0);
    }

    @Override
    public boolean m_142039_() {
        float $$0 = Mth.m_14089_(this.f_31082_ * ((float)Math.PI * 2));
        float $$1 = Mth.m_14089_(this.f_31081_ * ((float)Math.PI * 2));
        return $$1 <= -0.3f && $$0 >= -0.3f;
    }

    @Override
    public void m_142043_() {
        if (this.f_19853_.f_46443_ && !this.m_20067_()) {
            this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11893_, this.m_5720_(), 5.0f, 0.8f + this.f_19796_.m_188501_() * 0.3f, false);
        }
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.m_20088_().m_135372_(f_31067_, EnderDragonPhase.f_31387_.m_31405_());
    }

    public double[] m_31101_(int p_31102_, float p_31103_) {
        if (this.m_21224_()) {
            p_31103_ = 0.0f;
        }
        p_31103_ = 1.0f - p_31103_;
        int $$2 = this.f_31093_ - p_31102_ & 0x3F;
        int $$3 = this.f_31093_ - p_31102_ - 1 & 0x3F;
        double[] $$4 = new double[3];
        double $$5 = this.f_31092_[$$2][0];
        double $$6 = Mth.m_14175_(this.f_31092_[$$3][0] - $$5);
        $$4[0] = $$5 + $$6 * (double)p_31103_;
        $$5 = this.f_31092_[$$2][1];
        $$6 = this.f_31092_[$$3][1] - $$5;
        $$4[1] = $$5 + $$6 * (double)p_31103_;
        $$4[2] = Mth.m_14139_(p_31103_, this.f_31092_[$$2][2], this.f_31092_[$$3][2]);
        return $$4;
    }

    @Override
    public void m_8107_() {
        this.m_146874_();
        if (this.f_19853_.f_46443_) {
            this.m_21153_(this.m_21223_());
            if (!this.m_20067_() && !this.f_31074_.m_31415_().m_7080_() && --this.f_31075_ < 0) {
                this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11894_, this.m_5720_(), 2.5f, 0.8f + this.f_19796_.m_188501_() * 0.3f, false);
                this.f_31075_ = 200 + this.f_19796_.m_188503_(200);
            }
        }
        this.f_31081_ = this.f_31082_;
        if (this.m_21224_()) {
            float $$0 = (this.f_19796_.m_188501_() - 0.5f) * 8.0f;
            float $$1 = (this.f_19796_.m_188501_() - 0.5f) * 4.0f;
            float $$2 = (this.f_19796_.m_188501_() - 0.5f) * 8.0f;
            this.f_19853_.m_7106_(ParticleTypes.f_123813_, this.m_20185_() + (double)$$0, this.m_20186_() + 2.0 + (double)$$1, this.m_20189_() + (double)$$2, 0.0, 0.0, 0.0);
            return;
        }
        this.m_31160_();
        Vec3 $$3 = this.m_20184_();
        float $$4 = 0.2f / ((float)$$3.m_165924_() * 10.0f + 1.0f);
        this.f_31082_ = this.f_31074_.m_31415_().m_7080_() ? (this.f_31082_ += 0.1f) : (this.f_31083_ ? (this.f_31082_ += $$4 * 0.5f) : (this.f_31082_ += ($$4 *= (float)Math.pow(2.0, $$3.f_82480_))));
        this.m_146922_(Mth.m_14177_(this.m_146908_()));
        if (this.m_21525_()) {
            this.f_31082_ = 0.5f;
            return;
        }
        if (this.f_31093_ < 0) {
            for (int $$5 = 0; $$5 < this.f_31092_.length; ++$$5) {
                this.f_31092_[$$5][0] = this.m_146908_();
                this.f_31092_[$$5][1] = this.m_20186_();
            }
        }
        if (++this.f_31093_ == this.f_31092_.length) {
            this.f_31093_ = 0;
        }
        this.f_31092_[this.f_31093_][0] = this.m_146908_();
        this.f_31092_[this.f_31093_][1] = this.m_20186_();
        if (this.f_19853_.f_46443_) {
            if (this.f_20903_ > 0) {
                double $$6 = this.m_20185_() + (this.f_20904_ - this.m_20185_()) / (double)this.f_20903_;
                double $$7 = this.m_20186_() + (this.f_20905_ - this.m_20186_()) / (double)this.f_20903_;
                double $$8 = this.m_20189_() + (this.f_20906_ - this.m_20189_()) / (double)this.f_20903_;
                double $$9 = Mth.m_14175_(this.f_20907_ - (double)this.m_146908_());
                this.m_146922_(this.m_146908_() + (float)$$9 / (float)this.f_20903_);
                this.m_146926_(this.m_146909_() + (float)(this.f_20908_ - (double)this.m_146909_()) / (float)this.f_20903_);
                --this.f_20903_;
                this.m_6034_($$6, $$7, $$8);
                this.m_19915_(this.m_146908_(), this.m_146909_());
            }
            this.f_31074_.m_31415_().m_6991_();
        } else {
            Vec3 $$11;
            DragonPhaseInstance $$10 = this.f_31074_.m_31415_();
            $$10.m_6989_();
            if (this.f_31074_.m_31415_() != $$10) {
                $$10 = this.f_31074_.m_31415_();
                $$10.m_6989_();
            }
            if (($$11 = $$10.m_5535_()) != null) {
                double $$12 = $$11.f_82479_ - this.m_20185_();
                double $$13 = $$11.f_82480_ - this.m_20186_();
                double $$14 = $$11.f_82481_ - this.m_20189_();
                double $$15 = $$12 * $$12 + $$13 * $$13 + $$14 * $$14;
                float $$16 = $$10.m_7072_();
                double $$17 = Math.sqrt($$12 * $$12 + $$14 * $$14);
                if ($$17 > 0.0) {
                    $$13 = Mth.m_14008_($$13 / $$17, -$$16, $$16);
                }
                this.m_20256_(this.m_20184_().m_82520_(0.0, $$13 * 0.01, 0.0));
                this.m_146922_(Mth.m_14177_(this.m_146908_()));
                Vec3 $$18 = $$11.m_82492_(this.m_20185_(), this.m_20186_(), this.m_20189_()).m_82541_();
                Vec3 $$19 = new Vec3(Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180)), this.m_20184_().f_82480_, -Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180))).m_82541_();
                float $$20 = Math.max(((float)$$19.m_82526_($$18) + 0.5f) / 1.5f, 0.0f);
                if (Math.abs($$12) > (double)1.0E-5f || Math.abs($$14) > (double)1.0E-5f) {
                    float $$21 = Mth.m_14036_(Mth.m_14177_(180.0f - (float)Mth.m_14136_($$12, $$14) * 57.295776f - this.m_146908_()), -50.0f, 50.0f);
                    this.f_31085_ *= 0.8f;
                    this.f_31085_ += $$21 * $$10.m_7089_();
                    this.m_146922_(this.m_146908_() + this.f_31085_ * 0.1f);
                }
                float $$22 = (float)(2.0 / ($$15 + 1.0));
                float $$23 = 0.06f;
                this.m_19920_(0.06f * ($$20 * $$22 + (1.0f - $$22)), new Vec3(0.0, 0.0, -1.0));
                if (this.f_31083_) {
                    this.m_6478_(MoverType.SELF, this.m_20184_().m_82490_(0.8f));
                } else {
                    this.m_6478_(MoverType.SELF, this.m_20184_());
                }
                Vec3 $$24 = this.m_20184_().m_82541_();
                double $$25 = 0.8 + 0.15 * ($$24.m_82526_($$19) + 1.0) / 2.0;
                this.m_20256_(this.m_20184_().m_82542_($$25, 0.91f, $$25));
            }
        }
        this.f_20883_ = this.m_146908_();
        Vec3[] $$26 = new Vec3[this.f_31089_.length];
        for (int $$27 = 0; $$27 < this.f_31089_.length; ++$$27) {
            $$26[$$27] = new Vec3(this.f_31089_[$$27].m_20185_(), this.f_31089_[$$27].m_20186_(), this.f_31089_[$$27].m_20189_());
        }
        float $$28 = (float)(this.m_31101_(5, 1.0f)[1] - this.m_31101_(10, 1.0f)[1]) * 10.0f * ((float)Math.PI / 180);
        float $$29 = Mth.m_14089_($$28);
        float $$30 = Mth.m_14031_($$28);
        float $$31 = this.m_146908_() * ((float)Math.PI / 180);
        float $$32 = Mth.m_14031_($$31);
        float $$33 = Mth.m_14089_($$31);
        this.m_31115_(this.f_31091_, $$32 * 0.5f, 0.0, -$$33 * 0.5f);
        this.m_31115_(this.f_31071_, $$33 * 4.5f, 2.0, $$32 * 4.5f);
        this.m_31115_(this.f_31072_, $$33 * -4.5f, 2.0, $$32 * -4.5f);
        if (!this.f_19853_.f_46443_ && this.f_20916_ == 0) {
            this.m_31131_(this.f_19853_.m_6249_(this, this.f_31071_.m_20191_().m_82377_(4.0, 2.0, 4.0).m_82386_(0.0, -2.0, 0.0), EntitySelector.f_20406_));
            this.m_31131_(this.f_19853_.m_6249_(this, this.f_31072_.m_20191_().m_82377_(4.0, 2.0, 4.0).m_82386_(0.0, -2.0, 0.0), EntitySelector.f_20406_));
            this.m_31141_(this.f_19853_.m_6249_(this, this.f_31080_.m_20191_().m_82400_(1.0), EntitySelector.f_20406_));
            this.m_31141_(this.f_19853_.m_6249_(this, this.f_31090_.m_20191_().m_82400_(1.0), EntitySelector.f_20406_));
        }
        float $$34 = Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180) - this.f_31085_ * 0.01f);
        float $$35 = Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180) - this.f_31085_ * 0.01f);
        float $$36 = this.m_31159_();
        this.m_31115_(this.f_31080_, $$34 * 6.5f * $$29, $$36 + $$30 * 6.5f, -$$35 * 6.5f * $$29);
        this.m_31115_(this.f_31090_, $$34 * 5.5f * $$29, $$36 + $$30 * 5.5f, -$$35 * 5.5f * $$29);
        double[] $$37 = this.m_31101_(5, 1.0f);
        for (int $$38 = 0; $$38 < 3; ++$$38) {
            EnderDragonPart $$39 = null;
            if ($$38 == 0) {
                $$39 = this.f_31068_;
            }
            if ($$38 == 1) {
                $$39 = this.f_31069_;
            }
            if ($$38 == 2) {
                $$39 = this.f_31070_;
            }
            double[] $$40 = this.m_31101_(12 + $$38 * 2, 1.0f);
            float $$41 = this.m_146908_() * ((float)Math.PI / 180) + this.m_31164_($$40[0] - $$37[0]) * ((float)Math.PI / 180);
            float $$42 = Mth.m_14031_($$41);
            float $$43 = Mth.m_14089_($$41);
            float $$44 = 1.5f;
            float $$45 = (float)($$38 + 1) * 2.0f;
            this.m_31115_($$39, -($$32 * 1.5f + $$42 * $$45) * $$29, $$40[1] - $$37[1] - (double)(($$45 + 1.5f) * $$30) + 1.5, ($$33 * 1.5f + $$43 * $$45) * $$29);
        }
        if (!this.f_19853_.f_46443_) {
            this.f_31083_ = this.m_31139_(this.f_31080_.m_20191_()) | this.m_31139_(this.f_31090_.m_20191_()) | this.m_31139_(this.f_31091_.m_20191_());
            if (this.f_31073_ != null) {
                this.f_31073_.m_64096_(this);
            }
        }
        for (int $$46 = 0; $$46 < this.f_31089_.length; ++$$46) {
            this.f_31089_[$$46].f_19854_ = $$26[$$46].f_82479_;
            this.f_31089_[$$46].f_19855_ = $$26[$$46].f_82480_;
            this.f_31089_[$$46].f_19856_ = $$26[$$46].f_82481_;
            this.f_31089_[$$46].f_19790_ = $$26[$$46].f_82479_;
            this.f_31089_[$$46].f_19791_ = $$26[$$46].f_82480_;
            this.f_31089_[$$46].f_19792_ = $$26[$$46].f_82481_;
        }
    }

    private void m_31115_(EnderDragonPart p_31116_, double p_31117_, double p_31118_, double p_31119_) {
        p_31116_.m_6034_(this.m_20185_() + p_31117_, this.m_20186_() + p_31118_, this.m_20189_() + p_31119_);
    }

    private float m_31159_() {
        if (this.f_31074_.m_31415_().m_7080_()) {
            return -1.0f;
        }
        double[] $$0 = this.m_31101_(5, 1.0f);
        double[] $$1 = this.m_31101_(0, 1.0f);
        return (float)($$0[1] - $$1[1]);
    }

    private void m_31160_() {
        if (this.f_31086_ != null) {
            if (this.f_31086_.m_213877_()) {
                this.f_31086_ = null;
            } else if (this.f_19797_ % 10 == 0 && this.m_21223_() < this.m_21233_()) {
                this.m_21153_(this.m_21223_() + 1.0f);
            }
        }
        if (this.f_19796_.m_188503_(10) == 0) {
            List<EndCrystal> $$0 = this.f_19853_.m_45976_(EndCrystal.class, this.m_20191_().m_82400_(32.0));
            EndCrystal $$1 = null;
            double $$2 = Double.MAX_VALUE;
            for (EndCrystal $$3 : $$0) {
                double $$4 = $$3.m_20280_(this);
                if (!($$4 < $$2)) continue;
                $$2 = $$4;
                $$1 = $$3;
            }
            this.f_31086_ = $$1;
        }
    }

    private void m_31131_(List<Entity> p_31132_) {
        double $$1 = (this.f_31091_.m_20191_().f_82288_ + this.f_31091_.m_20191_().f_82291_) / 2.0;
        double $$2 = (this.f_31091_.m_20191_().f_82290_ + this.f_31091_.m_20191_().f_82293_) / 2.0;
        for (Entity $$3 : p_31132_) {
            if (!($$3 instanceof LivingEntity)) continue;
            double $$4 = $$3.m_20185_() - $$1;
            double $$5 = $$3.m_20189_() - $$2;
            double $$6 = Math.max($$4 * $$4 + $$5 * $$5, 0.1);
            $$3.m_5997_($$4 / $$6 * 4.0, 0.2f, $$5 / $$6 * 4.0);
            if (this.f_31074_.m_31415_().m_7080_() || ((LivingEntity)$$3).m_21213_() >= $$3.f_19797_ - 2) continue;
            $$3.m_6469_(DamageSource.m_19370_(this), 5.0f);
            this.m_19970_(this, $$3);
        }
    }

    private void m_31141_(List<Entity> p_31142_) {
        for (Entity $$1 : p_31142_) {
            if (!($$1 instanceof LivingEntity)) continue;
            $$1.m_6469_(DamageSource.m_19370_(this), 10.0f);
            this.m_19970_(this, $$1);
        }
    }

    private float m_31164_(double p_31165_) {
        return (float)Mth.m_14175_(p_31165_);
    }

    private boolean m_31139_(AABB p_31140_) {
        int $$1 = Mth.m_14107_(p_31140_.f_82288_);
        int $$2 = Mth.m_14107_(p_31140_.f_82289_);
        int $$3 = Mth.m_14107_(p_31140_.f_82290_);
        int $$4 = Mth.m_14107_(p_31140_.f_82291_);
        int $$5 = Mth.m_14107_(p_31140_.f_82292_);
        int $$6 = Mth.m_14107_(p_31140_.f_82293_);
        boolean $$7 = false;
        boolean $$8 = false;
        for (int $$9 = $$1; $$9 <= $$4; ++$$9) {
            for (int $$10 = $$2; $$10 <= $$5; ++$$10) {
                for (int $$11 = $$3; $$11 <= $$6; ++$$11) {
                    BlockPos $$12 = new BlockPos($$9, $$10, $$11);
                    BlockState $$13 = this.f_19853_.m_8055_($$12);
                    if ($$13.m_60795_() || $$13.m_204336_(BlockTags.f_215822_)) continue;
                    if (!this.f_19853_.m_46469_().m_46207_(GameRules.f_46132_) || $$13.m_204336_(BlockTags.f_13069_)) {
                        $$7 = true;
                        continue;
                    }
                    $$8 = this.f_19853_.m_7471_($$12, false) || $$8;
                }
            }
        }
        if ($$8) {
            BlockPos $$14 = new BlockPos($$1 + this.f_19796_.m_188503_($$4 - $$1 + 1), $$2 + this.f_19796_.m_188503_($$5 - $$2 + 1), $$3 + this.f_19796_.m_188503_($$6 - $$3 + 1));
            this.f_19853_.m_46796_(2008, $$14, 0);
        }
        return $$7;
    }

    public boolean m_31120_(EnderDragonPart p_31121_, DamageSource p_31122_, float p_31123_) {
        if (this.f_31074_.m_31415_().m_7309_() == EnderDragonPhase.f_31386_) {
            return false;
        }
        p_31123_ = this.f_31074_.m_31415_().m_7584_(p_31122_, p_31123_);
        if (p_31121_ != this.f_31080_) {
            p_31123_ = p_31123_ / 4.0f + Math.min(p_31123_, 1.0f);
        }
        if (p_31123_ < 0.01f) {
            return false;
        }
        if (p_31122_.m_7639_() instanceof Player || p_31122_.m_19372_()) {
            float $$3 = this.m_21223_();
            this.m_31161_(p_31122_, p_31123_);
            if (this.m_21224_() && !this.f_31074_.m_31415_().m_7080_()) {
                this.m_21153_(1.0f);
                this.f_31074_.m_31416_(EnderDragonPhase.f_31386_);
            }
            if (this.f_31074_.m_31415_().m_7080_()) {
                this.f_31076_ = this.f_31076_ + $$3 - this.m_21223_();
                if (this.f_31076_ > 0.25f * this.m_21233_()) {
                    this.f_31076_ = 0.0f;
                    this.f_31074_.m_31416_(EnderDragonPhase.f_31381_);
                }
            }
        }
        return true;
    }

    @Override
    public boolean m_6469_(DamageSource p_31113_, float p_31114_) {
        if (!this.f_19853_.f_46443_) {
            return this.m_31120_(this.f_31091_, p_31113_, p_31114_);
        }
        return false;
    }

    protected boolean m_31161_(DamageSource p_31162_, float p_31163_) {
        return super.m_6469_(p_31162_, p_31163_);
    }

    @Override
    public void m_6074_() {
        this.m_142687_(Entity.RemovalReason.KILLED);
        this.m_146850_(GameEvent.f_223707_);
        if (this.f_31073_ != null) {
            this.f_31073_.m_64096_(this);
            this.f_31073_.m_64085_(this);
        }
    }

    @Override
    protected void m_6153_() {
        if (this.f_31073_ != null) {
            this.f_31073_.m_64096_(this);
        }
        ++this.f_31084_;
        if (this.f_31084_ >= 180 && this.f_31084_ <= 200) {
            float $$0 = (this.f_19796_.m_188501_() - 0.5f) * 8.0f;
            float $$1 = (this.f_19796_.m_188501_() - 0.5f) * 4.0f;
            float $$2 = (this.f_19796_.m_188501_() - 0.5f) * 8.0f;
            this.f_19853_.m_7106_(ParticleTypes.f_123812_, this.m_20185_() + (double)$$0, this.m_20186_() + 2.0 + (double)$$1, this.m_20189_() + (double)$$2, 0.0, 0.0, 0.0);
        }
        boolean $$3 = this.f_19853_.m_46469_().m_46207_(GameRules.f_46135_);
        int $$4 = 500;
        if (this.f_31073_ != null && !this.f_31073_.m_64099_()) {
            $$4 = 12000;
        }
        if (this.f_19853_ instanceof ServerLevel) {
            if (this.f_31084_ > 150 && this.f_31084_ % 5 == 0 && $$3) {
                ExperienceOrb.m_147082_((ServerLevel)this.f_19853_, this.m_20182_(), Mth.m_14143_((float)$$4 * 0.08f));
            }
            if (this.f_31084_ == 1 && !this.m_20067_()) {
                this.f_19853_.m_6798_(1028, this.m_20183_(), 0);
            }
        }
        this.m_6478_(MoverType.SELF, new Vec3(0.0, 0.1f, 0.0));
        this.m_146922_(this.m_146908_() + 20.0f);
        this.f_20883_ = this.m_146908_();
        if (this.f_31084_ == 200 && this.f_19853_ instanceof ServerLevel) {
            if ($$3) {
                ExperienceOrb.m_147082_((ServerLevel)this.f_19853_, this.m_20182_(), Mth.m_14143_((float)$$4 * 0.2f));
            }
            if (this.f_31073_ != null) {
                this.f_31073_.m_64085_(this);
            }
            this.m_142687_(Entity.RemovalReason.KILLED);
            this.m_146850_(GameEvent.f_223707_);
        }
    }

    public int m_31155_() {
        if (this.f_31077_[0] == null) {
            for (int $$0 = 0; $$0 < 24; ++$$0) {
                int $$8;
                int $$7;
                int $$1 = 5;
                int $$2 = $$0;
                if ($$0 < 12) {
                    int $$3 = Mth.m_14143_(60.0f * Mth.m_14089_(2.0f * ((float)(-Math.PI) + 0.2617994f * (float)$$2)));
                    int $$4 = Mth.m_14143_(60.0f * Mth.m_14031_(2.0f * ((float)(-Math.PI) + 0.2617994f * (float)$$2)));
                } else if ($$0 < 20) {
                    int $$5 = Mth.m_14143_(40.0f * Mth.m_14089_(2.0f * ((float)(-Math.PI) + 0.3926991f * (float)($$2 -= 12))));
                    int $$6 = Mth.m_14143_(40.0f * Mth.m_14031_(2.0f * ((float)(-Math.PI) + 0.3926991f * (float)$$2)));
                    $$1 += 10;
                } else {
                    $$7 = Mth.m_14143_(20.0f * Mth.m_14089_(2.0f * ((float)(-Math.PI) + 0.7853982f * (float)($$2 -= 20))));
                    $$8 = Mth.m_14143_(20.0f * Mth.m_14031_(2.0f * ((float)(-Math.PI) + 0.7853982f * (float)$$2)));
                }
                int $$9 = Math.max(this.f_19853_.m_5736_() + 10, this.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos($$7, 0, $$8)).m_123342_() + $$1);
                this.f_31077_[$$0] = new Node($$7, $$9, $$8);
            }
            this.f_31078_[0] = 6146;
            this.f_31078_[1] = 8197;
            this.f_31078_[2] = 8202;
            this.f_31078_[3] = 16404;
            this.f_31078_[4] = 32808;
            this.f_31078_[5] = 32848;
            this.f_31078_[6] = 65696;
            this.f_31078_[7] = 131392;
            this.f_31078_[8] = 131712;
            this.f_31078_[9] = 263424;
            this.f_31078_[10] = 526848;
            this.f_31078_[11] = 525313;
            this.f_31078_[12] = 1581057;
            this.f_31078_[13] = 3166214;
            this.f_31078_[14] = 2138120;
            this.f_31078_[15] = 6373424;
            this.f_31078_[16] = 4358208;
            this.f_31078_[17] = 12910976;
            this.f_31078_[18] = 9044480;
            this.f_31078_[19] = 9706496;
            this.f_31078_[20] = 15216640;
            this.f_31078_[21] = 0xD0E000;
            this.f_31078_[22] = 11763712;
            this.f_31078_[23] = 0x7E0000;
        }
        return this.m_31170_(this.m_20185_(), this.m_20186_(), this.m_20189_());
    }

    public int m_31170_(double p_31171_, double p_31172_, double p_31173_) {
        float $$3 = 10000.0f;
        int $$4 = 0;
        Node $$5 = new Node(Mth.m_14107_(p_31171_), Mth.m_14107_(p_31172_), Mth.m_14107_(p_31173_));
        int $$6 = 0;
        if (this.f_31073_ == null || this.f_31073_.m_64098_() == 0) {
            $$6 = 12;
        }
        for (int $$7 = $$6; $$7 < 24; ++$$7) {
            float $$8;
            if (this.f_31077_[$$7] == null || !(($$8 = this.f_31077_[$$7].m_77299_($$5)) < $$3)) continue;
            $$3 = $$8;
            $$4 = $$7;
        }
        return $$4;
    }

    @Nullable
    public Path m_31104_(int p_31105_, int p_31106_, @Nullable Node p_31107_) {
        for (int $$3 = 0; $$3 < 24; ++$$3) {
            Node $$4 = this.f_31077_[$$3];
            $$4.f_77279_ = false;
            $$4.f_77277_ = 0.0f;
            $$4.f_77275_ = 0.0f;
            $$4.f_77276_ = 0.0f;
            $$4.f_77278_ = null;
            $$4.f_77274_ = -1;
        }
        Node $$5 = this.f_31077_[p_31105_];
        Node $$6 = this.f_31077_[p_31106_];
        $$5.f_77275_ = 0.0f;
        $$5.f_77277_ = $$5.f_77276_ = $$5.m_77293_($$6);
        this.f_31079_.m_77081_();
        this.f_31079_.m_77084_($$5);
        Node $$7 = $$5;
        int $$8 = 0;
        if (this.f_31073_ == null || this.f_31073_.m_64098_() == 0) {
            $$8 = 12;
        }
        while (!this.f_31079_.m_77092_()) {
            Node $$9 = this.f_31079_.m_77091_();
            if ($$9.equals($$6)) {
                if (p_31107_ != null) {
                    p_31107_.f_77278_ = $$6;
                    $$6 = p_31107_;
                }
                return this.m_31128_($$5, $$6);
            }
            if ($$9.m_77293_($$6) < $$7.m_77293_($$6)) {
                $$7 = $$9;
            }
            $$9.f_77279_ = true;
            int $$10 = 0;
            for (int $$11 = 0; $$11 < 24; ++$$11) {
                if (this.f_31077_[$$11] != $$9) continue;
                $$10 = $$11;
                break;
            }
            for (int $$12 = $$8; $$12 < 24; ++$$12) {
                if ((this.f_31078_[$$10] & 1 << $$12) <= 0) continue;
                Node $$13 = this.f_31077_[$$12];
                if ($$13.f_77279_) continue;
                float $$14 = $$9.f_77275_ + $$9.m_77293_($$13);
                if ($$13.m_77303_() && !($$14 < $$13.f_77275_)) continue;
                $$13.f_77278_ = $$9;
                $$13.f_77275_ = $$14;
                $$13.f_77276_ = $$13.m_77293_($$6);
                if ($$13.m_77303_()) {
                    this.f_31079_.m_77086_($$13, $$13.f_77275_ + $$13.f_77276_);
                    continue;
                }
                $$13.f_77277_ = $$13.f_77275_ + $$13.f_77276_;
                this.f_31079_.m_77084_($$13);
            }
        }
        if ($$7 == $$5) {
            return null;
        }
        f_31087_.debug("Failed to find path from {} to {}", (Object)p_31105_, (Object)p_31106_);
        if (p_31107_ != null) {
            p_31107_.f_77278_ = $$7;
            $$7 = p_31107_;
        }
        return this.m_31128_($$5, $$7);
    }

    private Path m_31128_(Node p_31129_, Node p_31130_) {
        ArrayList $$2 = Lists.newArrayList();
        Node $$3 = p_31130_;
        $$2.add(0, $$3);
        while ($$3.f_77278_ != null) {
            $$3 = $$3.f_77278_;
            $$2.add(0, $$3);
        }
        return new Path($$2, new BlockPos(p_31130_.f_77271_, p_31130_.f_77272_, p_31130_.f_77273_), true);
    }

    @Override
    public void m_7380_(CompoundTag p_31144_) {
        super.m_7380_(p_31144_);
        p_31144_.m_128405_(f_149570_, this.f_31074_.m_31415_().m_7309_().m_31405_());
        p_31144_.m_128405_(f_149569_, this.f_31084_);
    }

    @Override
    public void m_7378_(CompoundTag p_31134_) {
        super.m_7378_(p_31134_);
        if (p_31134_.m_128441_(f_149570_)) {
            this.f_31074_.m_31416_(EnderDragonPhase.m_31398_(p_31134_.m_128451_(f_149570_)));
        }
        if (p_31134_.m_128441_(f_149569_)) {
            this.f_31084_ = p_31134_.m_128451_(f_149569_);
        }
    }

    @Override
    public void m_6043_() {
    }

    public EnderDragonPart[] m_31156_() {
        return this.f_31089_;
    }

    @Override
    public boolean m_6087_() {
        return false;
    }

    @Override
    public SoundSource m_5720_() {
        return SoundSource.HOSTILE;
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_11890_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_31154_) {
        return SoundEvents.f_11895_;
    }

    @Override
    protected float m_6121_() {
        return 5.0f;
    }

    public float m_31108_(int p_31109_, double[] p_31110_, double[] p_31111_) {
        double $$10;
        DragonPhaseInstance $$3 = this.f_31074_.m_31415_();
        EnderDragonPhase<? extends DragonPhaseInstance> $$4 = $$3.m_7309_();
        if ($$4 == EnderDragonPhase.f_31380_ || $$4 == EnderDragonPhase.f_31381_) {
            BlockPos $$5 = this.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.f_65714_);
            double $$6 = Math.max(Math.sqrt($$5.m_203193_(this.m_20182_())) / 4.0, 1.0);
            double $$7 = (double)p_31109_ / $$6;
        } else if ($$3.m_7080_()) {
            double $$8 = p_31109_;
        } else if (p_31109_ == 6) {
            double $$9 = 0.0;
        } else {
            $$10 = p_31111_[1] - p_31110_[1];
        }
        return (float)$$10;
    }

    public Vec3 m_31174_(float p_31175_) {
        Vec3 $$12;
        DragonPhaseInstance $$1 = this.f_31074_.m_31415_();
        EnderDragonPhase<? extends DragonPhaseInstance> $$2 = $$1.m_7309_();
        if ($$2 == EnderDragonPhase.f_31380_ || $$2 == EnderDragonPhase.f_31381_) {
            BlockPos $$3 = this.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.f_65714_);
            float $$4 = Math.max((float)Math.sqrt($$3.m_203193_(this.m_20182_())) / 4.0f, 1.0f);
            float $$5 = 6.0f / $$4;
            float $$6 = this.m_146909_();
            float $$7 = 1.5f;
            this.m_146926_(-$$5 * 1.5f * 5.0f);
            Vec3 $$8 = this.m_20252_(p_31175_);
            this.m_146926_($$6);
        } else if ($$1.m_7080_()) {
            float $$9 = this.m_146909_();
            float $$10 = 1.5f;
            this.m_146926_(-45.0f);
            Vec3 $$11 = this.m_20252_(p_31175_);
            this.m_146926_($$9);
        } else {
            $$12 = this.m_20252_(p_31175_);
        }
        return $$12;
    }

    public void m_31124_(EndCrystal p_31125_, BlockPos p_31126_, DamageSource p_31127_) {
        Player $$4;
        if (p_31127_.m_7639_() instanceof Player) {
            Player $$3 = (Player)p_31127_.m_7639_();
        } else {
            $$4 = this.f_19853_.m_45941_(f_31088_, p_31126_.m_123341_(), p_31126_.m_123342_(), p_31126_.m_123343_());
        }
        if (p_31125_ == this.f_31086_) {
            this.m_31120_(this.f_31080_, DamageSource.m_19373_($$4), 10.0f);
        }
        this.f_31074_.m_31415_().m_8059_(p_31125_, p_31126_, p_31127_, $$4);
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_31136_) {
        if (f_31067_.equals(p_31136_) && this.f_19853_.f_46443_) {
            this.f_31074_.m_31416_(EnderDragonPhase.m_31398_(this.m_20088_().m_135370_(f_31067_)));
        }
        super.m_7350_(p_31136_);
    }

    public EnderDragonPhaseManager m_31157_() {
        return this.f_31074_;
    }

    @Nullable
    public EndDragonFight m_31158_() {
        return this.f_31073_;
    }

    @Override
    public boolean m_147207_(MobEffectInstance p_182394_, @Nullable Entity p_182395_) {
        return false;
    }

    @Override
    protected boolean m_7341_(Entity p_31169_) {
        return false;
    }

    @Override
    public boolean m_6072_() {
        return false;
    }

    @Override
    public void m_141965_(ClientboundAddEntityPacket p_218825_) {
        super.m_141965_(p_218825_);
        EnderDragonPart[] $$1 = this.m_31156_();
        for (int $$2 = 0; $$2 < $$1.length; ++$$2) {
            $$1[$$2].m_20234_($$2 + p_218825_.m_131496_());
        }
    }

    @Override
    public boolean m_6779_(LivingEntity p_149576_) {
        return p_149576_.m_142066_();
    }
}

