/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.projectile;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ShulkerBullet
extends Projectile {
    private static final double f_150183_ = 0.15;
    @Nullable
    private Entity f_37312_;
    @Nullable
    private Direction f_37313_;
    private int f_37314_;
    private double f_37315_;
    private double f_37316_;
    private double f_37317_;
    @Nullable
    private UUID f_37311_;

    public ShulkerBullet(EntityType<? extends ShulkerBullet> p_37319_, Level p_37320_) {
        super((EntityType<? extends Projectile>)p_37319_, p_37320_);
        this.f_19794_ = true;
    }

    public ShulkerBullet(Level p_37330_, LivingEntity p_37331_, Entity p_37332_, Direction.Axis p_37333_) {
        this((EntityType<? extends ShulkerBullet>)EntityType.f_20522_, p_37330_);
        this.m_5602_(p_37331_);
        BlockPos $$4 = p_37331_.m_20183_();
        double $$5 = (double)$$4.m_123341_() + 0.5;
        double $$6 = (double)$$4.m_123342_() + 0.5;
        double $$7 = (double)$$4.m_123343_() + 0.5;
        this.m_7678_($$5, $$6, $$7, this.m_146908_(), this.m_146909_());
        this.f_37312_ = p_37332_;
        this.f_37313_ = Direction.UP;
        this.m_37348_(p_37333_);
    }

    @Override
    public SoundSource m_5720_() {
        return SoundSource.HOSTILE;
    }

    @Override
    protected void m_7380_(CompoundTag p_37357_) {
        super.m_7380_(p_37357_);
        if (this.f_37312_ != null) {
            p_37357_.m_128362_("Target", this.f_37312_.m_20148_());
        }
        if (this.f_37313_ != null) {
            p_37357_.m_128405_("Dir", this.f_37313_.m_122411_());
        }
        p_37357_.m_128405_("Steps", this.f_37314_);
        p_37357_.m_128347_("TXD", this.f_37315_);
        p_37357_.m_128347_("TYD", this.f_37316_);
        p_37357_.m_128347_("TZD", this.f_37317_);
    }

    @Override
    protected void m_7378_(CompoundTag p_37353_) {
        super.m_7378_(p_37353_);
        this.f_37314_ = p_37353_.m_128451_("Steps");
        this.f_37315_ = p_37353_.m_128459_("TXD");
        this.f_37316_ = p_37353_.m_128459_("TYD");
        this.f_37317_ = p_37353_.m_128459_("TZD");
        if (p_37353_.m_128425_("Dir", 99)) {
            this.f_37313_ = Direction.m_122376_(p_37353_.m_128451_("Dir"));
        }
        if (p_37353_.m_128403_("Target")) {
            this.f_37311_ = p_37353_.m_128342_("Target");
        }
    }

    @Override
    protected void m_8097_() {
    }

    @Nullable
    private Direction m_150186_() {
        return this.f_37313_;
    }

    private void m_37350_(@Nullable Direction p_37351_) {
        this.f_37313_ = p_37351_;
    }

    private void m_37348_(@Nullable Direction.Axis p_37349_) {
        BlockPos $$3;
        double $$1 = 0.5;
        if (this.f_37312_ == null) {
            BlockPos $$2 = this.m_20183_().m_7495_();
        } else {
            $$1 = (double)this.f_37312_.m_20206_() * 0.5;
            $$3 = new BlockPos(this.f_37312_.m_20185_(), this.f_37312_.m_20186_() + $$1, this.f_37312_.m_20189_());
        }
        double $$4 = (double)$$3.m_123341_() + 0.5;
        double $$5 = (double)$$3.m_123342_() + $$1;
        double $$6 = (double)$$3.m_123343_() + 0.5;
        Direction $$7 = null;
        if (!$$3.m_203195_(this.m_20182_(), 2.0)) {
            BlockPos $$8 = this.m_20183_();
            ArrayList $$9 = Lists.newArrayList();
            if (p_37349_ != Direction.Axis.X) {
                if ($$8.m_123341_() < $$3.m_123341_() && this.f_19853_.m_46859_($$8.m_122029_())) {
                    $$9.add(Direction.EAST);
                } else if ($$8.m_123341_() > $$3.m_123341_() && this.f_19853_.m_46859_($$8.m_122024_())) {
                    $$9.add(Direction.WEST);
                }
            }
            if (p_37349_ != Direction.Axis.Y) {
                if ($$8.m_123342_() < $$3.m_123342_() && this.f_19853_.m_46859_($$8.m_7494_())) {
                    $$9.add(Direction.UP);
                } else if ($$8.m_123342_() > $$3.m_123342_() && this.f_19853_.m_46859_($$8.m_7495_())) {
                    $$9.add(Direction.DOWN);
                }
            }
            if (p_37349_ != Direction.Axis.Z) {
                if ($$8.m_123343_() < $$3.m_123343_() && this.f_19853_.m_46859_($$8.m_122019_())) {
                    $$9.add(Direction.SOUTH);
                } else if ($$8.m_123343_() > $$3.m_123343_() && this.f_19853_.m_46859_($$8.m_122012_())) {
                    $$9.add(Direction.NORTH);
                }
            }
            $$7 = Direction.m_235672_(this.f_19796_);
            if ($$9.isEmpty()) {
                for (int $$10 = 5; !this.f_19853_.m_46859_($$8.m_121945_($$7)) && $$10 > 0; --$$10) {
                    $$7 = Direction.m_235672_(this.f_19796_);
                }
            } else {
                $$7 = (Direction)$$9.get(this.f_19796_.m_188503_($$9.size()));
            }
            $$4 = this.m_20185_() + (double)$$7.m_122429_();
            $$5 = this.m_20186_() + (double)$$7.m_122430_();
            $$6 = this.m_20189_() + (double)$$7.m_122431_();
        }
        this.m_37350_($$7);
        double $$11 = $$4 - this.m_20185_();
        double $$12 = $$5 - this.m_20186_();
        double $$13 = $$6 - this.m_20189_();
        double $$14 = Math.sqrt($$11 * $$11 + $$12 * $$12 + $$13 * $$13);
        if ($$14 == 0.0) {
            this.f_37315_ = 0.0;
            this.f_37316_ = 0.0;
            this.f_37317_ = 0.0;
        } else {
            this.f_37315_ = $$11 / $$14 * 0.15;
            this.f_37316_ = $$12 / $$14 * 0.15;
            this.f_37317_ = $$13 / $$14 * 0.15;
        }
        this.f_19812_ = true;
        this.f_37314_ = 10 + this.f_19796_.m_188503_(5) * 10;
    }

    @Override
    public void m_6043_() {
        if (this.f_19853_.m_46791_() == Difficulty.PEACEFUL) {
            this.m_146870_();
        }
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (!this.f_19853_.f_46443_) {
            if (this.f_37312_ == null && this.f_37311_ != null) {
                this.f_37312_ = ((ServerLevel)this.f_19853_).m_8791_(this.f_37311_);
                if (this.f_37312_ == null) {
                    this.f_37311_ = null;
                }
            }
            if (!(this.f_37312_ == null || !this.f_37312_.m_6084_() || this.f_37312_ instanceof Player && this.f_37312_.m_5833_())) {
                this.f_37315_ = Mth.m_14008_(this.f_37315_ * 1.025, -1.0, 1.0);
                this.f_37316_ = Mth.m_14008_(this.f_37316_ * 1.025, -1.0, 1.0);
                this.f_37317_ = Mth.m_14008_(this.f_37317_ * 1.025, -1.0, 1.0);
                Vec3 $$0 = this.m_20184_();
                this.m_20256_($$0.m_82520_((this.f_37315_ - $$0.f_82479_) * 0.2, (this.f_37316_ - $$0.f_82480_) * 0.2, (this.f_37317_ - $$0.f_82481_) * 0.2));
            } else if (!this.m_20068_()) {
                this.m_20256_(this.m_20184_().m_82520_(0.0, -0.04, 0.0));
            }
            HitResult $$1 = ProjectileUtil.m_37294_(this, this::m_5603_);
            if ($$1.m_6662_() != HitResult.Type.MISS) {
                this.m_6532_($$1);
            }
        }
        this.m_20101_();
        Vec3 $$2 = this.m_20184_();
        this.m_6034_(this.m_20185_() + $$2.f_82479_, this.m_20186_() + $$2.f_82480_, this.m_20189_() + $$2.f_82481_);
        ProjectileUtil.m_37284_(this, 0.5f);
        if (this.f_19853_.f_46443_) {
            this.f_19853_.m_7106_(ParticleTypes.f_123810_, this.m_20185_() - $$2.f_82479_, this.m_20186_() - $$2.f_82480_ + 0.15, this.m_20189_() - $$2.f_82481_, 0.0, 0.0, 0.0);
        } else if (this.f_37312_ != null && !this.f_37312_.m_213877_()) {
            if (this.f_37314_ > 0) {
                --this.f_37314_;
                if (this.f_37314_ == 0) {
                    this.m_37348_(this.f_37313_ == null ? null : this.f_37313_.m_122434_());
                }
            }
            if (this.f_37313_ != null) {
                BlockPos $$3 = this.m_20183_();
                Direction.Axis $$4 = this.f_37313_.m_122434_();
                if (this.f_19853_.m_46575_($$3.m_121945_(this.f_37313_), this)) {
                    this.m_37348_($$4);
                } else {
                    BlockPos $$5 = this.f_37312_.m_20183_();
                    if ($$4 == Direction.Axis.X && $$3.m_123341_() == $$5.m_123341_() || $$4 == Direction.Axis.Z && $$3.m_123343_() == $$5.m_123343_() || $$4 == Direction.Axis.Y && $$3.m_123342_() == $$5.m_123342_()) {
                        this.m_37348_($$4);
                    }
                }
            }
        }
    }

    @Override
    protected boolean m_5603_(Entity p_37341_) {
        return super.m_5603_(p_37341_) && !p_37341_.f_19794_;
    }

    @Override
    public boolean m_6060_() {
        return false;
    }

    @Override
    public boolean m_6783_(double p_37336_) {
        return p_37336_ < 16384.0;
    }

    @Override
    public float m_213856_() {
        return 1.0f;
    }

    @Override
    protected void m_5790_(EntityHitResult p_37345_) {
        super.m_5790_(p_37345_);
        Entity $$1 = p_37345_.m_82443_();
        Entity $$2 = this.m_37282_();
        LivingEntity $$3 = $$2 instanceof LivingEntity ? (LivingEntity)$$2 : null;
        boolean $$4 = $$1.m_6469_(DamageSource.m_19340_(this, $$3).m_19366_(), 4.0f);
        if ($$4) {
            this.m_19970_($$3, $$1);
            if ($$1 instanceof LivingEntity) {
                ((LivingEntity)$$1).m_147207_(new MobEffectInstance(MobEffects.f_19620_, 200), (Entity)MoreObjects.firstNonNull((Object)$$2, (Object)this));
            }
        }
    }

    @Override
    protected void m_8060_(BlockHitResult p_37343_) {
        super.m_8060_(p_37343_);
        ((ServerLevel)this.f_19853_).m_8767_(ParticleTypes.f_123813_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 2, 0.2, 0.2, 0.2, 0.0);
        this.m_5496_(SoundEvents.f_12410_, 1.0f, 1.0f);
    }

    @Override
    protected void m_6532_(HitResult p_37347_) {
        super.m_6532_(p_37347_);
        this.m_146870_();
    }

    @Override
    public boolean m_6087_() {
        return true;
    }

    @Override
    public boolean m_6469_(DamageSource p_37338_, float p_37339_) {
        if (!this.f_19853_.f_46443_) {
            this.m_5496_(SoundEvents.f_12411_, 1.0f, 1.0f);
            ((ServerLevel)this.f_19853_).m_8767_(ParticleTypes.f_123797_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 15, 0.2, 0.2, 0.2, 0.0);
            this.m_146870_();
        }
        return true;
    }

    @Override
    public void m_141965_(ClientboundAddEntityPacket p_150185_) {
        super.m_141965_(p_150185_);
        double $$1 = p_150185_.m_131503_();
        double $$2 = p_150185_.m_131504_();
        double $$3 = p_150185_.m_131505_();
        this.m_20334_($$1, $$2, $$3);
    }
}

