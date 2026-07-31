/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

public class PolarBear
extends Animal
implements NeutralMob {
    private static final EntityDataAccessor<Boolean> f_29510_ = SynchedEntityData.m_135353_(PolarBear.class, EntityDataSerializers.f_135035_);
    private static final float f_149003_ = 6.0f;
    private float f_29511_;
    private float f_29512_;
    private int f_29513_;
    private static final UniformInt f_29514_ = TimeUtil.m_145020_(20, 39);
    private int f_29515_;
    @Nullable
    private UUID f_29516_;

    public PolarBear(EntityType<? extends PolarBear> p_29519_, Level p_29520_) {
        super((EntityType<? extends Animal>)p_29519_, p_29520_);
    }

    @Override
    public AgeableMob m_142606_(ServerLevel p_149005_, AgeableMob p_149006_) {
        return EntityType.f_20514_.m_20615_(p_149005_);
    }

    @Override
    public boolean m_6898_(ItemStack p_29565_) {
        return false;
    }

    @Override
    protected void m_8099_() {
        super.m_8099_();
        this.f_21345_.m_25352_(0, new FloatGoal(this));
        this.f_21345_.m_25352_(1, new PolarBearMeleeAttackGoal());
        this.f_21345_.m_25352_(1, new PolarBearPanicGoal());
        this.f_21345_.m_25352_(4, new FollowParentGoal(this, 1.25));
        this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 1.0));
        this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
        this.f_21346_.m_25352_(1, new PolarBearHurtByTargetGoal());
        this.f_21346_.m_25352_(2, new PolarBearAttackPlayersGoal());
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::m_21674_));
        this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal<Fox>(this, Fox.class, 10, true, true, null));
        this.f_21346_.m_25352_(5, new ResetUniversalAngerTargetGoal<PolarBear>(this, false));
    }

    public static AttributeSupplier.Builder m_29560_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 30.0).m_22268_(Attributes.f_22277_, 20.0).m_22268_(Attributes.f_22279_, 0.25).m_22268_(Attributes.f_22281_, 6.0);
    }

    public static boolean m_218249_(EntityType<PolarBear> p_218250_, LevelAccessor p_218251_, MobSpawnType p_218252_, BlockPos p_218253_, RandomSource p_218254_) {
        Holder<Biome> $$5 = p_218251_.m_204166_(p_218253_);
        if ($$5.m_203656_(BiomeTags.f_215813_)) {
            return PolarBear.m_186209_(p_218251_, p_218253_) && p_218251_.m_8055_(p_218253_.m_7495_()).m_204336_(BlockTags.f_215826_);
        }
        return PolarBear.m_218104_(p_218250_, p_218251_, p_218252_, p_218253_, p_218254_);
    }

    @Override
    public void m_7378_(CompoundTag p_29541_) {
        super.m_7378_(p_29541_);
        this.m_147285_(this.f_19853_, p_29541_);
    }

    @Override
    public void m_7380_(CompoundTag p_29548_) {
        super.m_7380_(p_29548_);
        this.m_21678_(p_29548_);
    }

    @Override
    public void m_6825_() {
        this.m_7870_(f_29514_.m_214085_(this.f_19796_));
    }

    @Override
    public void m_7870_(int p_29543_) {
        this.f_29515_ = p_29543_;
    }

    @Override
    public int m_6784_() {
        return this.f_29515_;
    }

    @Override
    public void m_6925_(@Nullable UUID p_29539_) {
        this.f_29516_ = p_29539_;
    }

    @Override
    @Nullable
    public UUID m_6120_() {
        return this.f_29516_;
    }

    @Override
    protected SoundEvent m_7515_() {
        if (this.m_6162_()) {
            return SoundEvents.f_12281_;
        }
        return SoundEvents.f_12280_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_29559_) {
        return SoundEvents.f_12283_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12282_;
    }

    @Override
    protected void m_7355_(BlockPos p_29545_, BlockState p_29546_) {
        this.m_5496_(SoundEvents.f_12284_, 0.15f, 1.0f);
    }

    protected void m_29561_() {
        if (this.f_29513_ <= 0) {
            this.m_5496_(SoundEvents.f_12285_, 1.0f, this.m_6100_());
            this.f_29513_ = 40;
        }
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_29510_, false);
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (this.f_19853_.f_46443_) {
            if (this.f_29512_ != this.f_29511_) {
                this.m_6210_();
            }
            this.f_29511_ = this.f_29512_;
            this.f_29512_ = this.m_29562_() ? Mth.m_14036_(this.f_29512_ + 1.0f, 0.0f, 6.0f) : Mth.m_14036_(this.f_29512_ - 1.0f, 0.0f, 6.0f);
        }
        if (this.f_29513_ > 0) {
            --this.f_29513_;
        }
        if (!this.f_19853_.f_46443_) {
            this.m_21666_((ServerLevel)this.f_19853_, true);
        }
    }

    @Override
    public EntityDimensions m_6972_(Pose p_29531_) {
        if (this.f_29512_ > 0.0f) {
            float $$1 = this.f_29512_ / 6.0f;
            float $$2 = 1.0f + $$1;
            return super.m_6972_(p_29531_).m_20390_(1.0f, $$2);
        }
        return super.m_6972_(p_29531_);
    }

    @Override
    public boolean m_7327_(Entity p_29522_) {
        boolean $$1 = p_29522_.m_6469_(DamageSource.m_19370_(this), (int)this.m_21133_(Attributes.f_22281_));
        if ($$1) {
            this.m_19970_(this, p_29522_);
        }
        return $$1;
    }

    public boolean m_29562_() {
        return this.f_19804_.m_135370_(f_29510_);
    }

    public void m_29567_(boolean p_29568_) {
        this.f_19804_.m_135381_(f_29510_, p_29568_);
    }

    public float m_29569_(float p_29570_) {
        return Mth.m_14179_(p_29570_, this.f_29511_, this.f_29512_) / 6.0f;
    }

    @Override
    protected float m_6108_() {
        return 0.98f;
    }

    @Override
    public SpawnGroupData m_6518_(ServerLevelAccessor p_29533_, DifficultyInstance p_29534_, MobSpawnType p_29535_, @Nullable SpawnGroupData p_29536_, @Nullable CompoundTag p_29537_) {
        if (p_29536_ == null) {
            p_29536_ = new AgeableMob.AgeableMobGroupData(1.0f);
        }
        return super.m_6518_(p_29533_, p_29534_, p_29535_, p_29536_, p_29537_);
    }

    class PolarBearMeleeAttackGoal
    extends MeleeAttackGoal {
        public PolarBearMeleeAttackGoal() {
            super(PolarBear.this, 1.25, true);
        }

        @Override
        protected void m_6739_(LivingEntity p_29589_, double p_29590_) {
            double $$2 = this.m_6639_(p_29589_);
            if (p_29590_ <= $$2 && this.m_25564_()) {
                this.m_25563_();
                this.f_25540_.m_7327_(p_29589_);
                PolarBear.this.m_29567_(false);
            } else if (p_29590_ <= $$2 * 2.0) {
                if (this.m_25564_()) {
                    PolarBear.this.m_29567_(false);
                    this.m_25563_();
                }
                if (this.m_25565_() <= 10) {
                    PolarBear.this.m_29567_(true);
                    PolarBear.this.m_29561_();
                }
            } else {
                this.m_25563_();
                PolarBear.this.m_29567_(false);
            }
        }

        @Override
        public void m_8041_() {
            PolarBear.this.m_29567_(false);
            super.m_8041_();
        }

        @Override
        protected double m_6639_(LivingEntity p_29587_) {
            return 4.0f + p_29587_.m_20205_();
        }
    }

    class PolarBearPanicGoal
    extends PanicGoal {
        public PolarBearPanicGoal() {
            super(PolarBear.this, 2.0);
        }

        @Override
        protected boolean m_202729_() {
            return this.f_25684_.m_21188_() != null && this.f_25684_.m_6162_() || this.f_25684_.m_6060_();
        }
    }

    class PolarBearHurtByTargetGoal
    extends HurtByTargetGoal {
        public PolarBearHurtByTargetGoal() {
            super(PolarBear.this, new Class[0]);
        }

        @Override
        public void m_8056_() {
            super.m_8056_();
            if (PolarBear.this.m_6162_()) {
                this.m_26047_();
                this.m_8041_();
            }
        }

        @Override
        protected void m_5766_(Mob p_29580_, LivingEntity p_29581_) {
            if (p_29580_ instanceof PolarBear && !p_29580_.m_6162_()) {
                super.m_5766_(p_29580_, p_29581_);
            }
        }
    }

    class PolarBearAttackPlayersGoal
    extends NearestAttackableTargetGoal<Player> {
        public PolarBearAttackPlayersGoal() {
            super(PolarBear.this, Player.class, 20, true, true, null);
        }

        @Override
        public boolean m_8036_() {
            if (PolarBear.this.m_6162_()) {
                return false;
            }
            if (super.m_8036_()) {
                List<PolarBear> $$0 = PolarBear.this.f_19853_.m_45976_(PolarBear.class, PolarBear.this.m_20191_().m_82377_(8.0, 4.0, 8.0));
                for (PolarBear $$1 : $$0) {
                    if (!$$1.m_6162_()) continue;
                    return true;
                }
            }
            return false;
        }

        @Override
        protected double m_7623_() {
            return super.m_7623_() * 0.5;
        }
    }
}

