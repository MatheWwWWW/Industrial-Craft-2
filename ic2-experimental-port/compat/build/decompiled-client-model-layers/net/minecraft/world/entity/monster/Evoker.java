/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Evoker
extends SpellcasterIllager {
    @Nullable
    private Sheep f_32625_;

    public Evoker(EntityType<? extends Evoker> p_32627_, Level p_32628_) {
        super((EntityType<? extends SpellcasterIllager>)p_32627_, p_32628_);
        this.f_21364_ = 10;
    }

    @Override
    protected void m_8099_() {
        super.m_8099_();
        this.f_21345_.m_25352_(0, new FloatGoal(this));
        this.f_21345_.m_25352_(1, new EvokerCastingSpellGoal());
        this.f_21345_.m_25352_(2, new AvoidEntityGoal<Player>(this, Player.class, 8.0f, 0.6, 1.0));
        this.f_21345_.m_25352_(4, new EvokerSummonSpellGoal());
        this.f_21345_.m_25352_(5, new EvokerAttackSpellGoal());
        this.f_21345_.m_25352_(6, new EvokerWololoSpellGoal());
        this.f_21345_.m_25352_(8, new RandomStrollGoal(this, 0.6));
        this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, Raider.class).m_26044_(new Class[0]));
        this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, true).m_26146_(300));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<AbstractVillager>((Mob)this, AbstractVillager.class, false).m_26146_(300));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<IronGolem>((Mob)this, IronGolem.class, false));
    }

    public static AttributeSupplier.Builder m_32657_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22279_, 0.5).m_22268_(Attributes.f_22277_, 12.0).m_22268_(Attributes.f_22276_, 24.0);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
    }

    @Override
    public void m_7378_(CompoundTag p_32642_) {
        super.m_7378_(p_32642_);
    }

    @Override
    public SoundEvent m_7930_() {
        return SoundEvents.f_11863_;
    }

    @Override
    public void m_7380_(CompoundTag p_32646_) {
        super.m_7380_(p_32646_);
    }

    @Override
    protected void m_8024_() {
        super.m_8024_();
    }

    @Override
    public boolean m_7307_(Entity p_32665_) {
        if (p_32665_ == null) {
            return false;
        }
        if (p_32665_ == this) {
            return true;
        }
        if (super.m_7307_(p_32665_)) {
            return true;
        }
        if (p_32665_ instanceof Vex) {
            return this.m_7307_(((Vex)p_32665_).m_34026_());
        }
        if (p_32665_ instanceof LivingEntity && ((LivingEntity)p_32665_).m_6336_() == MobType.f_21643_) {
            return this.m_5647_() == null && p_32665_.m_5647_() == null;
        }
        return false;
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_11861_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_11864_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_32654_) {
        return SoundEvents.f_11866_;
    }

    void m_32634_(@Nullable Sheep p_32635_) {
        this.f_32625_ = p_32635_;
    }

    @Nullable
    Sheep m_32662_() {
        return this.f_32625_;
    }

    @Override
    protected SoundEvent m_7894_() {
        return SoundEvents.f_11862_;
    }

    @Override
    public void m_7895_(int p_32632_, boolean p_32633_) {
    }

    class EvokerCastingSpellGoal
    extends SpellcasterIllager.SpellcasterCastingSpellGoal {
        EvokerCastingSpellGoal() {
            super(Evoker.this);
        }

        @Override
        public void m_8037_() {
            if (Evoker.this.m_5448_() != null) {
                Evoker.this.m_21563_().m_24960_(Evoker.this.m_5448_(), Evoker.this.m_8085_(), Evoker.this.m_8132_());
            } else if (Evoker.this.m_32662_() != null) {
                Evoker.this.m_21563_().m_24960_(Evoker.this.m_32662_(), Evoker.this.m_8085_(), Evoker.this.m_8132_());
            }
        }
    }

    class EvokerSummonSpellGoal
    extends SpellcasterIllager.SpellcasterUseSpellGoal {
        private final TargetingConditions f_32692_;

        EvokerSummonSpellGoal() {
            super(Evoker.this);
            this.f_32692_ = TargetingConditions.m_148353_().m_26883_(16.0).m_148355_().m_26893_();
        }

        @Override
        public boolean m_8036_() {
            if (!super.m_8036_()) {
                return false;
            }
            int $$0 = Evoker.this.f_19853_.m_45971_(Vex.class, this.f_32692_, Evoker.this, Evoker.this.m_20191_().m_82400_(16.0)).size();
            return Evoker.this.f_19796_.m_188503_(8) + 1 > $$0;
        }

        @Override
        protected int m_8089_() {
            return 100;
        }

        @Override
        protected int m_8067_() {
            return 340;
        }

        @Override
        protected void m_8130_() {
            ServerLevel $$0 = (ServerLevel)Evoker.this.f_19853_;
            for (int $$1 = 0; $$1 < 3; ++$$1) {
                BlockPos $$2 = Evoker.this.m_20183_().m_7918_(-2 + Evoker.this.f_19796_.m_188503_(5), 1, -2 + Evoker.this.f_19796_.m_188503_(5));
                Vex $$3 = EntityType.f_20491_.m_20615_(Evoker.this.f_19853_);
                $$3.m_20035_($$2, 0.0f, 0.0f);
                $$3.m_6518_($$0, Evoker.this.f_19853_.m_6436_($$2), MobSpawnType.MOB_SUMMONED, null, null);
                $$3.m_33994_(Evoker.this);
                $$3.m_34033_($$2);
                $$3.m_33987_(20 * (30 + Evoker.this.f_19796_.m_188503_(90)));
                $$0.m_47205_($$3);
            }
        }

        @Override
        protected SoundEvent m_7030_() {
            return SoundEvents.f_11868_;
        }

        @Override
        protected SpellcasterIllager.IllagerSpell m_7269_() {
            return SpellcasterIllager.IllagerSpell.SUMMON_VEX;
        }
    }

    class EvokerAttackSpellGoal
    extends SpellcasterIllager.SpellcasterUseSpellGoal {
        EvokerAttackSpellGoal() {
            super(Evoker.this);
        }

        @Override
        protected int m_8089_() {
            return 40;
        }

        @Override
        protected int m_8067_() {
            return 100;
        }

        @Override
        protected void m_8130_() {
            LivingEntity $$0 = Evoker.this.m_5448_();
            double $$1 = Math.min($$0.m_20186_(), Evoker.this.m_20186_());
            double $$2 = Math.max($$0.m_20186_(), Evoker.this.m_20186_()) + 1.0;
            float $$3 = (float)Mth.m_14136_($$0.m_20189_() - Evoker.this.m_20189_(), $$0.m_20185_() - Evoker.this.m_20185_());
            if (Evoker.this.m_20280_($$0) < 9.0) {
                for (int $$4 = 0; $$4 < 5; ++$$4) {
                    float $$5 = $$3 + (float)$$4 * (float)Math.PI * 0.4f;
                    this.m_32672_(Evoker.this.m_20185_() + (double)Mth.m_14089_($$5) * 1.5, Evoker.this.m_20189_() + (double)Mth.m_14031_($$5) * 1.5, $$1, $$2, $$5, 0);
                }
                for (int $$6 = 0; $$6 < 8; ++$$6) {
                    float $$7 = $$3 + (float)$$6 * (float)Math.PI * 2.0f / 8.0f + 1.2566371f;
                    this.m_32672_(Evoker.this.m_20185_() + (double)Mth.m_14089_($$7) * 2.5, Evoker.this.m_20189_() + (double)Mth.m_14031_($$7) * 2.5, $$1, $$2, $$7, 3);
                }
            } else {
                for (int $$8 = 0; $$8 < 16; ++$$8) {
                    double $$9 = 1.25 * (double)($$8 + 1);
                    int $$10 = 1 * $$8;
                    this.m_32672_(Evoker.this.m_20185_() + (double)Mth.m_14089_($$3) * $$9, Evoker.this.m_20189_() + (double)Mth.m_14031_($$3) * $$9, $$1, $$2, $$3, $$10);
                }
            }
        }

        private void m_32672_(double p_32673_, double p_32674_, double p_32675_, double p_32676_, float p_32677_, int p_32678_) {
            BlockPos $$6 = new BlockPos(p_32673_, p_32676_, p_32674_);
            boolean $$7 = false;
            double $$8 = 0.0;
            do {
                BlockState $$11;
                VoxelShape $$12;
                BlockPos $$9;
                BlockState $$10;
                if (!($$10 = Evoker.this.f_19853_.m_8055_($$9 = $$6.m_7495_())).m_60783_(Evoker.this.f_19853_, $$9, Direction.UP)) continue;
                if (!Evoker.this.f_19853_.m_46859_($$6) && !($$12 = ($$11 = Evoker.this.f_19853_.m_8055_($$6)).m_60812_(Evoker.this.f_19853_, $$6)).m_83281_()) {
                    $$8 = $$12.m_83297_(Direction.Axis.Y);
                }
                $$7 = true;
                break;
            } while (($$6 = $$6.m_7495_()).m_123342_() >= Mth.m_14107_(p_32675_) - 1);
            if ($$7) {
                Evoker.this.f_19853_.m_7967_(new EvokerFangs(Evoker.this.f_19853_, p_32673_, (double)$$6.m_123342_() + $$8, p_32674_, p_32677_, p_32678_, Evoker.this));
            }
        }

        @Override
        protected SoundEvent m_7030_() {
            return SoundEvents.f_11867_;
        }

        @Override
        protected SpellcasterIllager.IllagerSpell m_7269_() {
            return SpellcasterIllager.IllagerSpell.FANGS;
        }
    }

    public class EvokerWololoSpellGoal
    extends SpellcasterIllager.SpellcasterUseSpellGoal {
        private final TargetingConditions f_32705_;

        public EvokerWololoSpellGoal() {
            super(Evoker.this);
            this.f_32705_ = TargetingConditions.m_148353_().m_26883_(16.0).m_26888_(p_32710_ -> ((Sheep)p_32710_).m_29874_() == DyeColor.BLUE);
        }

        @Override
        public boolean m_8036_() {
            if (Evoker.this.m_5448_() != null) {
                return false;
            }
            if (Evoker.this.m_33736_()) {
                return false;
            }
            if (Evoker.this.f_19797_ < this.f_33775_) {
                return false;
            }
            if (!Evoker.this.f_19853_.m_46469_().m_46207_(GameRules.f_46132_)) {
                return false;
            }
            List<Sheep> $$0 = Evoker.this.f_19853_.m_45971_(Sheep.class, this.f_32705_, Evoker.this, Evoker.this.m_20191_().m_82377_(16.0, 4.0, 16.0));
            if ($$0.isEmpty()) {
                return false;
            }
            Evoker.this.m_32634_($$0.get(Evoker.this.f_19796_.m_188503_($$0.size())));
            return true;
        }

        @Override
        public boolean m_8045_() {
            return Evoker.this.m_32662_() != null && this.f_33774_ > 0;
        }

        @Override
        public void m_8041_() {
            super.m_8041_();
            Evoker.this.m_32634_(null);
        }

        @Override
        protected void m_8130_() {
            Sheep $$0 = Evoker.this.m_32662_();
            if ($$0 != null && $$0.m_6084_()) {
                $$0.m_29855_(DyeColor.RED);
            }
        }

        @Override
        protected int m_8069_() {
            return 40;
        }

        @Override
        protected int m_8089_() {
            return 60;
        }

        @Override
        protected int m_8067_() {
            return 140;
        }

        @Override
        protected SoundEvent m_7030_() {
            return SoundEvents.f_11869_;
        }

        @Override
        protected SpellcasterIllager.IllagerSpell m_7269_() {
            return SpellcasterIllager.IllagerSpell.WOLOLO;
        }
    }
}

