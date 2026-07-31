/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Team;

public abstract class TamableAnimal
extends Animal
implements OwnableEntity {
    protected static final EntityDataAccessor<Byte> f_21798_ = SynchedEntityData.m_135353_(TamableAnimal.class, EntityDataSerializers.f_135027_);
    protected static final EntityDataAccessor<Optional<UUID>> f_21799_ = SynchedEntityData.m_135353_(TamableAnimal.class, EntityDataSerializers.f_135041_);
    private boolean f_21800_;

    protected TamableAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super((EntityType<? extends Animal>)p_21803_, p_21804_);
        this.m_5849_();
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_21798_, (byte)0);
        this.f_19804_.m_135372_(f_21799_, Optional.empty());
    }

    @Override
    public void m_7380_(CompoundTag p_21819_) {
        super.m_7380_(p_21819_);
        if (this.m_21805_() != null) {
            p_21819_.m_128362_("Owner", this.m_21805_());
        }
        p_21819_.m_128379_("Sitting", this.f_21800_);
    }

    @Override
    public void m_7378_(CompoundTag p_21815_) {
        UUID $$3;
        super.m_7378_(p_21815_);
        if (p_21815_.m_128403_("Owner")) {
            UUID $$1 = p_21815_.m_128342_("Owner");
        } else {
            String $$2 = p_21815_.m_128461_("Owner");
            $$3 = OldUsersConverter.m_11083_(this.m_20194_(), $$2);
        }
        if ($$3 != null) {
            try {
                this.m_21816_($$3);
                this.m_7105_(true);
            }
            catch (Throwable $$4) {
                this.m_7105_(false);
            }
        }
        this.f_21800_ = p_21815_.m_128471_("Sitting");
        this.m_21837_(this.f_21800_);
    }

    @Override
    public boolean m_6573_(Player p_21813_) {
        return !this.m_21523_();
    }

    protected void m_21834_(boolean p_21835_) {
        SimpleParticleType $$1 = ParticleTypes.f_123750_;
        if (!p_21835_) {
            $$1 = ParticleTypes.f_123762_;
        }
        for (int $$2 = 0; $$2 < 7; ++$$2) {
            double $$3 = this.f_19796_.m_188583_() * 0.02;
            double $$4 = this.f_19796_.m_188583_() * 0.02;
            double $$5 = this.f_19796_.m_188583_() * 0.02;
            this.f_19853_.m_7106_($$1, this.m_20208_(1.0), this.m_20187_() + 0.5, this.m_20262_(1.0), $$3, $$4, $$5);
        }
    }

    @Override
    public void m_7822_(byte p_21807_) {
        if (p_21807_ == 7) {
            this.m_21834_(true);
        } else if (p_21807_ == 6) {
            this.m_21834_(false);
        } else {
            super.m_7822_(p_21807_);
        }
    }

    public boolean m_21824_() {
        return (this.f_19804_.m_135370_(f_21798_) & 4) != 0;
    }

    public void m_7105_(boolean p_21836_) {
        byte $$1 = this.f_19804_.m_135370_(f_21798_);
        if (p_21836_) {
            this.f_19804_.m_135381_(f_21798_, (byte)($$1 | 4));
        } else {
            this.f_19804_.m_135381_(f_21798_, (byte)($$1 & 0xFFFFFFFB));
        }
        this.m_5849_();
    }

    protected void m_5849_() {
    }

    public boolean m_21825_() {
        return (this.f_19804_.m_135370_(f_21798_) & 1) != 0;
    }

    public void m_21837_(boolean p_21838_) {
        byte $$1 = this.f_19804_.m_135370_(f_21798_);
        if (p_21838_) {
            this.f_19804_.m_135381_(f_21798_, (byte)($$1 | 1));
        } else {
            this.f_19804_.m_135381_(f_21798_, (byte)($$1 & 0xFFFFFFFE));
        }
    }

    @Override
    @Nullable
    public UUID m_21805_() {
        return this.f_19804_.m_135370_(f_21799_).orElse(null);
    }

    public void m_21816_(@Nullable UUID p_21817_) {
        this.f_19804_.m_135381_(f_21799_, Optional.ofNullable(p_21817_));
    }

    public void m_21828_(Player p_21829_) {
        this.m_7105_(true);
        this.m_21816_(p_21829_.m_20148_());
        if (p_21829_ instanceof ServerPlayer) {
            CriteriaTriggers.f_10590_.m_68829_((ServerPlayer)p_21829_, this);
        }
    }

    @Override
    @Nullable
    public LivingEntity m_21826_() {
        try {
            UUID $$0 = this.m_21805_();
            if ($$0 == null) {
                return null;
            }
            return this.f_19853_.m_46003_($$0);
        }
        catch (IllegalArgumentException $$1) {
            return null;
        }
    }

    @Override
    public boolean m_6779_(LivingEntity p_21822_) {
        if (this.m_21830_(p_21822_)) {
            return false;
        }
        return super.m_6779_(p_21822_);
    }

    public boolean m_21830_(LivingEntity p_21831_) {
        return p_21831_ == this.m_21826_();
    }

    public boolean m_7757_(LivingEntity p_21810_, LivingEntity p_21811_) {
        return true;
    }

    @Override
    public Team m_5647_() {
        LivingEntity $$0;
        if (this.m_21824_() && ($$0 = this.m_21826_()) != null) {
            return $$0.m_5647_();
        }
        return super.m_5647_();
    }

    @Override
    public boolean m_7307_(Entity p_21833_) {
        if (this.m_21824_()) {
            LivingEntity $$1 = this.m_21826_();
            if (p_21833_ == $$1) {
                return true;
            }
            if ($$1 != null) {
                return $$1.m_7307_(p_21833_);
            }
        }
        return super.m_7307_(p_21833_);
    }

    @Override
    public void m_6667_(DamageSource p_21809_) {
        if (!this.f_19853_.f_46443_ && this.f_19853_.m_46469_().m_46207_(GameRules.f_46142_) && this.m_21826_() instanceof ServerPlayer) {
            this.m_21826_().m_213846_(this.m_21231_().m_19293_());
        }
        super.m_6667_(p_21809_);
    }

    public boolean m_21827_() {
        return this.f_21800_;
    }

    public void m_21839_(boolean p_21840_) {
        this.f_21800_ = p_21840_;
    }

    @Override
    @Nullable
    public /* synthetic */ Entity m_21826_() {
        return this.m_21826_();
    }
}

