/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ambient;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Bat
extends AmbientCreature {
    public static final float f_148698_ = 74.48451f;
    public static final int f_148699_ = Mth.m_14167_(2.4166098f);
    private static final EntityDataAccessor<Byte> f_27407_ = SynchedEntityData.m_135353_(Bat.class, EntityDataSerializers.f_135027_);
    private static final int f_148700_ = 1;
    private static final TargetingConditions f_27408_ = TargetingConditions.m_148353_().m_26883_(4.0);
    @Nullable
    private BlockPos f_27409_;

    public Bat(EntityType<? extends Bat> p_27412_, Level p_27413_) {
        super((EntityType<? extends AmbientCreature>)p_27412_, p_27413_);
        this.m_27456_(true);
    }

    @Override
    public boolean m_142039_() {
        return !this.m_27452_() && this.f_19797_ % f_148699_ == 0;
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_27407_, (byte)0);
    }

    @Override
    protected float m_6121_() {
        return 0.1f;
    }

    @Override
    public float m_6100_() {
        return super.m_6100_() * 0.95f;
    }

    @Override
    @Nullable
    public SoundEvent m_7515_() {
        if (this.m_27452_() && this.f_19796_.m_188503_(4) != 0) {
            return null;
        }
        return SoundEvents.f_11731_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_27451_) {
        return SoundEvents.f_11733_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_11732_;
    }

    @Override
    public boolean m_6094_() {
        return false;
    }

    @Override
    protected void m_7324_(Entity p_27415_) {
    }

    @Override
    protected void m_6138_() {
    }

    public static AttributeSupplier.Builder m_27455_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 6.0);
    }

    public boolean m_27452_() {
        return (this.f_19804_.m_135370_(f_27407_) & 1) != 0;
    }

    public void m_27456_(boolean p_27457_) {
        byte $$1 = this.f_19804_.m_135370_(f_27407_);
        if (p_27457_) {
            this.f_19804_.m_135381_(f_27407_, (byte)($$1 | 1));
        } else {
            this.f_19804_.m_135381_(f_27407_, (byte)($$1 & 0xFFFFFFFE));
        }
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (this.m_27452_()) {
            this.m_20256_(Vec3.f_82478_);
            this.m_20343_(this.m_20185_(), (double)Mth.m_14107_(this.m_20186_()) + 1.0 - (double)this.m_20206_(), this.m_20189_());
        } else {
            this.m_20256_(this.m_20184_().m_82542_(1.0, 0.6, 1.0));
        }
    }

    @Override
    protected void m_8024_() {
        super.m_8024_();
        BlockPos $$0 = this.m_20183_();
        BlockPos $$1 = $$0.m_7494_();
        if (this.m_27452_()) {
            boolean $$2 = this.m_20067_();
            if (this.f_19853_.m_8055_($$1).m_60796_(this.f_19853_, $$0)) {
                if (this.f_19796_.m_188503_(200) == 0) {
                    this.f_20885_ = this.f_19796_.m_188503_(360);
                }
                if (this.f_19853_.m_45946_(f_27408_, this) != null) {
                    this.m_27456_(false);
                    if (!$$2) {
                        this.f_19853_.m_5898_(null, 1025, $$0, 0);
                    }
                }
            } else {
                this.m_27456_(false);
                if (!$$2) {
                    this.f_19853_.m_5898_(null, 1025, $$0, 0);
                }
            }
        } else {
            if (!(this.f_27409_ == null || this.f_19853_.m_46859_(this.f_27409_) && this.f_27409_.m_123342_() > this.f_19853_.m_141937_())) {
                this.f_27409_ = null;
            }
            if (this.f_27409_ == null || this.f_19796_.m_188503_(30) == 0 || this.f_27409_.m_203195_(this.m_20182_(), 2.0)) {
                this.f_27409_ = new BlockPos(this.m_20185_() + (double)this.f_19796_.m_188503_(7) - (double)this.f_19796_.m_188503_(7), this.m_20186_() + (double)this.f_19796_.m_188503_(6) - 2.0, this.m_20189_() + (double)this.f_19796_.m_188503_(7) - (double)this.f_19796_.m_188503_(7));
            }
            double $$3 = (double)this.f_27409_.m_123341_() + 0.5 - this.m_20185_();
            double $$4 = (double)this.f_27409_.m_123342_() + 0.1 - this.m_20186_();
            double $$5 = (double)this.f_27409_.m_123343_() + 0.5 - this.m_20189_();
            Vec3 $$6 = this.m_20184_();
            Vec3 $$7 = $$6.m_82520_((Math.signum($$3) * 0.5 - $$6.f_82479_) * (double)0.1f, (Math.signum($$4) * (double)0.7f - $$6.f_82480_) * (double)0.1f, (Math.signum($$5) * 0.5 - $$6.f_82481_) * (double)0.1f);
            this.m_20256_($$7);
            float $$8 = (float)(Mth.m_14136_($$7.f_82481_, $$7.f_82479_) * 57.2957763671875) - 90.0f;
            float $$9 = Mth.m_14177_($$8 - this.m_146908_());
            this.f_20902_ = 0.5f;
            this.m_146922_(this.m_146908_() + $$9);
            if (this.f_19796_.m_188503_(100) == 0 && this.f_19853_.m_8055_($$1).m_60796_(this.f_19853_, $$1)) {
                this.m_27456_(true);
            }
        }
    }

    @Override
    protected Entity.MovementEmission m_142319_() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    public boolean m_142535_(float p_148702_, float p_148703_, DamageSource p_148704_) {
        return false;
    }

    @Override
    protected void m_7840_(double p_27419_, boolean p_27420_, BlockState p_27421_, BlockPos p_27422_) {
    }

    @Override
    public boolean m_6090_() {
        return true;
    }

    @Override
    public boolean m_6469_(DamageSource p_27424_, float p_27425_) {
        if (this.m_6673_(p_27424_)) {
            return false;
        }
        if (!this.f_19853_.f_46443_ && this.m_27452_()) {
            this.m_27456_(false);
        }
        return super.m_6469_(p_27424_, p_27425_);
    }

    @Override
    public void m_7378_(CompoundTag p_27427_) {
        super.m_7378_(p_27427_);
        this.f_19804_.m_135381_(f_27407_, p_27427_.m_128445_("BatFlags"));
    }

    @Override
    public void m_7380_(CompoundTag p_27443_) {
        super.m_7380_(p_27443_);
        p_27443_.m_128344_("BatFlags", this.f_19804_.m_135370_(f_27407_));
    }

    public static boolean m_218098_(EntityType<Bat> p_218099_, LevelAccessor p_218100_, MobSpawnType p_218101_, BlockPos p_218102_, RandomSource p_218103_) {
        if (p_218102_.m_123342_() >= p_218100_.m_5736_()) {
            return false;
        }
        int $$5 = p_218100_.m_46803_(p_218102_);
        int $$6 = 4;
        if (Bat.m_27453_()) {
            $$6 = 7;
        } else if (p_218103_.m_188499_()) {
            return false;
        }
        if ($$5 > p_218103_.m_188503_($$6)) {
            return false;
        }
        return Bat.m_217057_(p_218099_, p_218100_, p_218101_, p_218102_, p_218103_);
    }

    private static boolean m_27453_() {
        LocalDate $$0 = LocalDate.now();
        int $$1 = $$0.get(ChronoField.DAY_OF_MONTH);
        int $$2 = $$0.get(ChronoField.MONTH_OF_YEAR);
        return $$2 == 10 && $$1 >= 20 || $$2 == 11 && $$1 <= 3;
    }

    @Override
    protected float m_6431_(Pose p_27440_, EntityDimensions p_27441_) {
        return p_27441_.f_20378_ / 2.0f;
    }
}

