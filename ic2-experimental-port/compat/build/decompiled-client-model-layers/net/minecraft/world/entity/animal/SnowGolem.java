/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class SnowGolem
extends AbstractGolem
implements Shearable,
RangedAttackMob {
    private static final EntityDataAccessor<Byte> f_29899_ = SynchedEntityData.m_135353_(SnowGolem.class, EntityDataSerializers.f_135027_);
    private static final byte f_149047_ = 16;
    private static final float f_149048_ = 1.7f;

    public SnowGolem(EntityType<? extends SnowGolem> p_29902_, Level p_29903_) {
        super((EntityType<? extends AbstractGolem>)p_29902_, p_29903_);
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(1, new RangedAttackGoal(this, 1.25, 20, 10.0f));
        this.f_21345_.m_25352_(2, new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0, 1.0000001E-5f));
        this.f_21345_.m_25352_(3, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.f_21345_.m_25352_(4, new RandomLookAroundGoal(this));
        this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal<Mob>(this, Mob.class, 10, true, false, p_29932_ -> p_29932_ instanceof Enemy));
    }

    public static AttributeSupplier.Builder m_29934_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 4.0).m_22268_(Attributes.f_22279_, 0.2f);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_29899_, (byte)16);
    }

    @Override
    public void m_7380_(CompoundTag p_29923_) {
        super.m_7380_(p_29923_);
        p_29923_.m_128379_("Pumpkin", this.m_29930_());
    }

    @Override
    public void m_7378_(CompoundTag p_29915_) {
        super.m_7378_(p_29915_);
        if (p_29915_.m_128441_("Pumpkin")) {
            this.m_29936_(p_29915_.m_128471_("Pumpkin"));
        }
    }

    @Override
    public boolean m_6126_() {
        return true;
    }

    @Override
    public void m_8107_() {
        super.m_8107_();
        if (!this.f_19853_.f_46443_) {
            int $$2;
            int $$1;
            int $$0 = Mth.m_14107_(this.m_20185_());
            BlockPos $$3 = new BlockPos($$0, $$1 = Mth.m_14107_(this.m_20186_()), $$2 = Mth.m_14107_(this.m_20189_()));
            Biome $$4 = this.f_19853_.m_204166_($$3).m_203334_();
            if ($$4.m_198910_($$3)) {
                this.m_6469_(DamageSource.f_19307_, 1.0f);
            }
            if (!this.f_19853_.m_46469_().m_46207_(GameRules.f_46132_)) {
                return;
            }
            BlockState $$5 = Blocks.f_50125_.m_49966_();
            for (int $$6 = 0; $$6 < 4; ++$$6) {
                $$0 = Mth.m_14107_(this.m_20185_() + (double)((float)($$6 % 2 * 2 - 1) * 0.25f));
                BlockPos $$7 = new BlockPos($$0, $$1 = Mth.m_14107_(this.m_20186_()), $$2 = Mth.m_14107_(this.m_20189_() + (double)((float)($$6 / 2 % 2 * 2 - 1) * 0.25f)));
                if (!this.f_19853_.m_8055_($$7).m_60795_() || !$$5.m_60710_(this.f_19853_, $$7)) continue;
                this.f_19853_.m_46597_($$7, $$5);
                this.f_19853_.m_220407_(GameEvent.f_157797_, $$7, GameEvent.Context.m_223719_(this, $$5));
            }
        }
    }

    @Override
    public void m_6504_(LivingEntity p_29912_, float p_29913_) {
        Snowball $$2 = new Snowball(this.f_19853_, this);
        double $$3 = p_29912_.m_20188_() - (double)1.1f;
        double $$4 = p_29912_.m_20185_() - this.m_20185_();
        double $$5 = $$3 - $$2.m_20186_();
        double $$6 = p_29912_.m_20189_() - this.m_20189_();
        double $$7 = Math.sqrt($$4 * $$4 + $$6 * $$6) * (double)0.2f;
        $$2.m_6686_($$4, $$5 + $$7, $$6, 1.6f, 12.0f);
        this.m_5496_(SoundEvents.f_12479_, 1.0f, 0.4f / (this.m_217043_().m_188501_() * 0.4f + 0.8f));
        this.f_19853_.m_7967_($$2);
    }

    @Override
    protected float m_6431_(Pose p_29917_, EntityDimensions p_29918_) {
        return 1.7f;
    }

    @Override
    protected InteractionResult m_6071_(Player p_29920_, InteractionHand p_29921_) {
        ItemStack $$2 = p_29920_.m_21120_(p_29921_);
        if ($$2.m_150930_(Items.f_42574_) && this.m_6220_()) {
            this.m_5851_(SoundSource.PLAYERS);
            this.m_146852_(GameEvent.f_157781_, p_29920_);
            if (!this.f_19853_.f_46443_) {
                $$2.m_41622_(1, p_29920_, p_29910_ -> p_29910_.m_21190_(p_29921_));
            }
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void m_5851_(SoundSource p_29907_) {
        this.f_19853_.m_6269_(null, this, SoundEvents.f_12480_, p_29907_, 1.0f, 1.0f);
        if (!this.f_19853_.m_5776_()) {
            this.m_29936_(false);
            this.m_5552_(new ItemStack(Items.f_42047_), 1.7f);
        }
    }

    @Override
    public boolean m_6220_() {
        return this.m_6084_() && this.m_29930_();
    }

    public boolean m_29930_() {
        return (this.f_19804_.m_135370_(f_29899_) & 0x10) != 0;
    }

    public void m_29936_(boolean p_29937_) {
        byte $$1 = this.f_19804_.m_135370_(f_29899_);
        if (p_29937_) {
            this.f_19804_.m_135381_(f_29899_, (byte)($$1 | 0x10));
        } else {
            this.f_19804_.m_135381_(f_29899_, (byte)($$1 & 0xFFFFFFEF));
        }
    }

    @Override
    @Nullable
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12476_;
    }

    @Override
    @Nullable
    protected SoundEvent m_7975_(DamageSource p_29929_) {
        return SoundEvents.f_12478_;
    }

    @Override
    @Nullable
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12477_;
    }

    @Override
    public Vec3 m_7939_() {
        return new Vec3(0.0, 0.75f * this.m_20192_(), this.m_20205_() * 0.4f);
    }
}

