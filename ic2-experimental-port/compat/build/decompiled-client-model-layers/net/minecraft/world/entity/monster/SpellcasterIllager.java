/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.level.Level;

public abstract class SpellcasterIllager
extends AbstractIllager {
    private static final EntityDataAccessor<Byte> f_33720_ = SynchedEntityData.m_135353_(SpellcasterIllager.class, EntityDataSerializers.f_135027_);
    protected int f_33719_;
    private IllagerSpell f_33721_ = IllagerSpell.NONE;

    protected SpellcasterIllager(EntityType<? extends SpellcasterIllager> p_33724_, Level p_33725_) {
        super((EntityType<? extends AbstractIllager>)p_33724_, p_33725_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_33720_, (byte)0);
    }

    @Override
    public void m_7378_(CompoundTag p_33732_) {
        super.m_7378_(p_33732_);
        this.f_33719_ = p_33732_.m_128451_("SpellTicks");
    }

    @Override
    public void m_7380_(CompoundTag p_33734_) {
        super.m_7380_(p_33734_);
        p_33734_.m_128405_("SpellTicks", this.f_33719_);
    }

    @Override
    public AbstractIllager.IllagerArmPose m_6768_() {
        if (this.m_33736_()) {
            return AbstractIllager.IllagerArmPose.SPELLCASTING;
        }
        if (this.m_37888_()) {
            return AbstractIllager.IllagerArmPose.CELEBRATING;
        }
        return AbstractIllager.IllagerArmPose.CROSSED;
    }

    public boolean m_33736_() {
        if (this.f_19853_.f_46443_) {
            return this.f_19804_.m_135370_(f_33720_) > 0;
        }
        return this.f_33719_ > 0;
    }

    public void m_33727_(IllagerSpell p_33728_) {
        this.f_33721_ = p_33728_;
        this.f_19804_.m_135381_(f_33720_, (byte)p_33728_.f_33747_);
    }

    protected IllagerSpell m_33737_() {
        if (!this.f_19853_.f_46443_) {
            return this.f_33721_;
        }
        return IllagerSpell.m_33758_(this.f_19804_.m_135370_(f_33720_).byteValue());
    }

    @Override
    protected void m_8024_() {
        super.m_8024_();
        if (this.f_33719_ > 0) {
            --this.f_33719_;
        }
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (this.f_19853_.f_46443_ && this.m_33736_()) {
            IllagerSpell $$0 = this.m_33737_();
            double $$1 = $$0.f_33748_[0];
            double $$2 = $$0.f_33748_[1];
            double $$3 = $$0.f_33748_[2];
            float $$4 = this.f_20883_ * ((float)Math.PI / 180) + Mth.m_14089_((float)this.f_19797_ * 0.6662f) * 0.25f;
            float $$5 = Mth.m_14089_($$4);
            float $$6 = Mth.m_14031_($$4);
            this.f_19853_.m_7106_(ParticleTypes.f_123811_, this.m_20185_() + (double)$$5 * 0.6, this.m_20186_() + 1.8, this.m_20189_() + (double)$$6 * 0.6, $$1, $$2, $$3);
            this.f_19853_.m_7106_(ParticleTypes.f_123811_, this.m_20185_() - (double)$$5 * 0.6, this.m_20186_() + 1.8, this.m_20189_() - (double)$$6 * 0.6, $$1, $$2, $$3);
        }
    }

    protected int m_33738_() {
        return this.f_33719_;
    }

    protected abstract SoundEvent m_7894_();

    protected static final class IllagerSpell
    extends Enum<IllagerSpell> {
        public static final /* enum */ IllagerSpell NONE = new IllagerSpell(0, 0.0, 0.0, 0.0);
        public static final /* enum */ IllagerSpell SUMMON_VEX = new IllagerSpell(1, 0.7, 0.7, 0.8);
        public static final /* enum */ IllagerSpell FANGS = new IllagerSpell(2, 0.4, 0.3, 0.35);
        public static final /* enum */ IllagerSpell WOLOLO = new IllagerSpell(3, 0.7, 0.5, 0.2);
        public static final /* enum */ IllagerSpell DISAPPEAR = new IllagerSpell(4, 0.3, 0.3, 0.8);
        public static final /* enum */ IllagerSpell BLINDNESS = new IllagerSpell(5, 0.1, 0.1, 0.2);
        final int f_33747_;
        final double[] f_33748_;
        private static final /* synthetic */ IllagerSpell[] $VALUES;

        public static IllagerSpell[] values() {
            return (IllagerSpell[])$VALUES.clone();
        }

        public static IllagerSpell valueOf(String p_33765_) {
            return Enum.valueOf(IllagerSpell.class, p_33765_);
        }

        private IllagerSpell(int p_33754_, double p_33755_, double p_33756_, double p_33757_) {
            this.f_33747_ = p_33754_;
            this.f_33748_ = new double[]{p_33755_, p_33756_, p_33757_};
        }

        public static IllagerSpell m_33758_(int p_33759_) {
            for (IllagerSpell $$1 : IllagerSpell.values()) {
                if (p_33759_ != $$1.f_33747_) continue;
                return $$1;
            }
            return NONE;
        }

        private static /* synthetic */ IllagerSpell[] m_149852_() {
            return new IllagerSpell[]{NONE, SUMMON_VEX, FANGS, WOLOLO, DISAPPEAR, BLINDNESS};
        }

        static {
            $VALUES = IllagerSpell.m_149852_();
        }
    }

    protected abstract class SpellcasterUseSpellGoal
    extends Goal {
        protected int f_33774_;
        protected int f_33775_;

        protected SpellcasterUseSpellGoal() {
        }

        @Override
        public boolean m_8036_() {
            LivingEntity $$0 = SpellcasterIllager.this.m_5448_();
            if ($$0 == null || !$$0.m_6084_()) {
                return false;
            }
            if (SpellcasterIllager.this.m_33736_()) {
                return false;
            }
            return SpellcasterIllager.this.f_19797_ >= this.f_33775_;
        }

        @Override
        public boolean m_8045_() {
            LivingEntity $$0 = SpellcasterIllager.this.m_5448_();
            return $$0 != null && $$0.m_6084_() && this.f_33774_ > 0;
        }

        @Override
        public void m_8056_() {
            this.f_33774_ = this.m_183277_(this.m_8069_());
            SpellcasterIllager.this.f_33719_ = this.m_8089_();
            this.f_33775_ = SpellcasterIllager.this.f_19797_ + this.m_8067_();
            SoundEvent $$0 = this.m_7030_();
            if ($$0 != null) {
                SpellcasterIllager.this.m_5496_($$0, 1.0f, 1.0f);
            }
            SpellcasterIllager.this.m_33727_(this.m_7269_());
        }

        @Override
        public void m_8037_() {
            --this.f_33774_;
            if (this.f_33774_ == 0) {
                this.m_8130_();
                SpellcasterIllager.this.m_5496_(SpellcasterIllager.this.m_7894_(), 1.0f, 1.0f);
            }
        }

        protected abstract void m_8130_();

        protected int m_8069_() {
            return 20;
        }

        protected abstract int m_8089_();

        protected abstract int m_8067_();

        @Nullable
        protected abstract SoundEvent m_7030_();

        protected abstract IllagerSpell m_7269_();
    }

    protected class SpellcasterCastingSpellGoal
    extends Goal {
        public SpellcasterCastingSpellGoal() {
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean m_8036_() {
            return SpellcasterIllager.this.m_33738_() > 0;
        }

        @Override
        public void m_8056_() {
            super.m_8056_();
            SpellcasterIllager.this.f_21344_.m_26573_();
        }

        @Override
        public void m_8041_() {
            super.m_8041_();
            SpellcasterIllager.this.m_33727_(IllagerSpell.NONE);
        }

        @Override
        public void m_8037_() {
            if (SpellcasterIllager.this.m_5448_() != null) {
                SpellcasterIllager.this.m_21563_().m_24960_(SpellcasterIllager.this.m_5448_(), SpellcasterIllager.this.m_8085_(), SpellcasterIllager.this.m_8132_());
            }
        }
    }
}

