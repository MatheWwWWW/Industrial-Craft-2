/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SwellGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class Creeper
extends Monster
implements PowerableMob {
    private static final EntityDataAccessor<Integer> f_32268_ = SynchedEntityData.m_135353_(Creeper.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> f_32274_ = SynchedEntityData.m_135353_(Creeper.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> f_32275_ = SynchedEntityData.m_135353_(Creeper.class, EntityDataSerializers.f_135035_);
    private int f_32269_;
    private int f_32270_;
    private int f_32271_ = 30;
    private int f_32272_ = 3;
    private int f_32273_;

    public Creeper(EntityType<? extends Creeper> p_32278_, Level p_32279_) {
        super((EntityType<? extends Monster>)p_32278_, p_32279_);
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(1, new FloatGoal(this));
        this.f_21345_.m_25352_(2, new SwellGoal(this));
        this.f_21345_.m_25352_(3, new AvoidEntityGoal<Ocelot>(this, Ocelot.class, 6.0f, 1.0, 1.2));
        this.f_21345_.m_25352_(3, new AvoidEntityGoal<Cat>(this, Cat.class, 6.0f, 1.0, 1.2));
        this.f_21345_.m_25352_(4, new MeleeAttackGoal(this, 1.0, false));
        this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
        this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, true));
        this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]));
    }

    public static AttributeSupplier.Builder m_32318_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22279_, 0.25);
    }

    @Override
    public int m_6056_() {
        if (this.m_5448_() == null) {
            return 3;
        }
        return 3 + (int)(this.m_21223_() - 1.0f);
    }

    @Override
    public boolean m_142535_(float p_149687_, float p_149688_, DamageSource p_149689_) {
        boolean $$3 = super.m_142535_(p_149687_, p_149688_, p_149689_);
        this.f_32270_ += (int)(p_149687_ * 1.5f);
        if (this.f_32270_ > this.f_32271_ - 5) {
            this.f_32270_ = this.f_32271_ - 5;
        }
        return $$3;
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_32268_, -1);
        this.f_19804_.m_135372_(f_32274_, false);
        this.f_19804_.m_135372_(f_32275_, false);
    }

    @Override
    public void m_7380_(CompoundTag p_32304_) {
        super.m_7380_(p_32304_);
        if (this.f_19804_.m_135370_(f_32274_).booleanValue()) {
            p_32304_.m_128379_("powered", true);
        }
        p_32304_.m_128376_("Fuse", (short)this.f_32271_);
        p_32304_.m_128344_("ExplosionRadius", (byte)this.f_32272_);
        p_32304_.m_128379_("ignited", this.m_32311_());
    }

    @Override
    public void m_7378_(CompoundTag p_32296_) {
        super.m_7378_(p_32296_);
        this.f_19804_.m_135381_(f_32274_, p_32296_.m_128471_("powered"));
        if (p_32296_.m_128425_("Fuse", 99)) {
            this.f_32271_ = p_32296_.m_128448_("Fuse");
        }
        if (p_32296_.m_128425_("ExplosionRadius", 99)) {
            this.f_32272_ = p_32296_.m_128445_("ExplosionRadius");
        }
        if (p_32296_.m_128471_("ignited")) {
            this.m_32312_();
        }
    }

    @Override
    public void m_8119_() {
        if (this.m_6084_()) {
            int $$0;
            this.f_32269_ = this.f_32270_;
            if (this.m_32311_()) {
                this.m_32283_(1);
            }
            if (($$0 = this.m_32310_()) > 0 && this.f_32270_ == 0) {
                this.m_5496_(SoundEvents.f_11837_, 1.0f, 0.5f);
                this.m_146850_(GameEvent.f_157776_);
            }
            this.f_32270_ += $$0;
            if (this.f_32270_ < 0) {
                this.f_32270_ = 0;
            }
            if (this.f_32270_ >= this.f_32271_) {
                this.f_32270_ = this.f_32271_;
                this.m_32315_();
            }
        }
        super.m_8119_();
    }

    @Override
    public void m_6710_(@Nullable LivingEntity p_149691_) {
        if (p_149691_ instanceof Goat) {
            return;
        }
        super.m_6710_(p_149691_);
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_32309_) {
        return SoundEvents.f_11836_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_11835_;
    }

    @Override
    protected void m_7472_(DamageSource p_32292_, int p_32293_, boolean p_32294_) {
        Creeper $$4;
        super.m_7472_(p_32292_, p_32293_, p_32294_);
        Entity $$3 = p_32292_.m_7639_();
        if ($$3 != this && $$3 instanceof Creeper && ($$4 = (Creeper)$$3).m_32313_()) {
            $$4.m_32314_();
            this.m_19998_(Items.f_42682_);
        }
    }

    @Override
    public boolean m_7327_(Entity p_32281_) {
        return true;
    }

    @Override
    public boolean m_7090_() {
        return this.f_19804_.m_135370_(f_32274_);
    }

    public float m_32320_(float p_32321_) {
        return Mth.m_14179_(p_32321_, this.f_32269_, this.f_32270_) / (float)(this.f_32271_ - 2);
    }

    public int m_32310_() {
        return this.f_19804_.m_135370_(f_32268_);
    }

    public void m_32283_(int p_32284_) {
        this.f_19804_.m_135381_(f_32268_, p_32284_);
    }

    @Override
    public void m_8038_(ServerLevel p_32286_, LightningBolt p_32287_) {
        super.m_8038_(p_32286_, p_32287_);
        this.f_19804_.m_135381_(f_32274_, true);
    }

    @Override
    protected InteractionResult m_6071_(Player p_32301_, InteractionHand p_32302_) {
        ItemStack $$2 = p_32301_.m_21120_(p_32302_);
        if ($$2.m_150930_(Items.f_42409_)) {
            this.f_19853_.m_6263_(p_32301_, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11942_, this.m_5720_(), 1.0f, this.f_19796_.m_188501_() * 0.4f + 0.8f);
            if (!this.f_19853_.f_46443_) {
                this.m_32312_();
                $$2.m_41622_(1, p_32301_, p_32290_ -> p_32290_.m_21190_(p_32302_));
            }
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        return super.m_6071_(p_32301_, p_32302_);
    }

    private void m_32315_() {
        if (!this.f_19853_.f_46443_) {
            Explosion.BlockInteraction $$0 = this.f_19853_.m_46469_().m_46207_(GameRules.f_46132_) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
            float $$1 = this.m_7090_() ? 2.0f : 1.0f;
            this.f_20890_ = true;
            this.f_19853_.m_46511_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), (float)this.f_32272_ * $$1, $$0);
            this.m_146870_();
            this.m_32316_();
        }
    }

    private void m_32316_() {
        Collection<MobEffectInstance> $$0 = this.m_21220_();
        if (!$$0.isEmpty()) {
            AreaEffectCloud $$1 = new AreaEffectCloud(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_());
            $$1.m_19712_(2.5f);
            $$1.m_19732_(-0.5f);
            $$1.m_19740_(10);
            $$1.m_19734_($$1.m_19748_() / 2);
            $$1.m_19738_(-$$1.m_19743_() / (float)$$1.m_19748_());
            for (MobEffectInstance $$2 : $$0) {
                $$1.m_19716_(new MobEffectInstance($$2));
            }
            this.f_19853_.m_7967_($$1);
        }
    }

    public boolean m_32311_() {
        return this.f_19804_.m_135370_(f_32275_);
    }

    public void m_32312_() {
        this.f_19804_.m_135381_(f_32275_, true);
    }

    public boolean m_32313_() {
        return this.m_7090_() && this.f_32273_ < 1;
    }

    public void m_32314_() {
        ++this.f_32273_;
    }
}

