/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.projectile;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownTrident
extends AbstractArrow {
    private static final EntityDataAccessor<Byte> f_37558_ = SynchedEntityData.m_135353_(ThrownTrident.class, EntityDataSerializers.f_135027_);
    private static final EntityDataAccessor<Boolean> f_37554_ = SynchedEntityData.m_135353_(ThrownTrident.class, EntityDataSerializers.f_135035_);
    private ItemStack f_37555_ = new ItemStack(Items.f_42713_);
    private boolean f_37556_;
    public int f_37557_;

    public ThrownTrident(EntityType<? extends ThrownTrident> p_37561_, Level p_37562_) {
        super((EntityType<? extends AbstractArrow>)p_37561_, p_37562_);
    }

    public ThrownTrident(Level p_37569_, LivingEntity p_37570_, ItemStack p_37571_) {
        super(EntityType.f_20487_, p_37570_, p_37569_);
        this.f_37555_ = p_37571_.m_41777_();
        this.f_19804_.m_135381_(f_37558_, (byte)EnchantmentHelper.m_44928_(p_37571_));
        this.f_19804_.m_135381_(f_37554_, p_37571_.m_41790_());
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_37558_, (byte)0);
        this.f_19804_.m_135372_(f_37554_, false);
    }

    @Override
    public void m_8119_() {
        if (this.f_36704_ > 4) {
            this.f_37556_ = true;
        }
        Entity $$0 = this.m_37282_();
        byte $$1 = this.f_19804_.m_135370_(f_37558_);
        if ($$1 > 0 && (this.f_37556_ || this.m_36797_()) && $$0 != null) {
            if (!this.m_37594_()) {
                if (!this.f_19853_.f_46443_ && this.f_36705_ == AbstractArrow.Pickup.ALLOWED) {
                    this.m_5552_(this.m_7941_(), 0.1f);
                }
                this.m_146870_();
            } else {
                this.m_36790_(true);
                Vec3 $$2 = $$0.m_146892_().m_82546_(this.m_20182_());
                this.m_20343_(this.m_20185_(), this.m_20186_() + $$2.f_82480_ * 0.015 * (double)$$1, this.m_20189_());
                if (this.f_19853_.f_46443_) {
                    this.f_19791_ = this.m_20186_();
                }
                double $$3 = 0.05 * (double)$$1;
                this.m_20256_(this.m_20184_().m_82490_(0.95).m_82549_($$2.m_82541_().m_82490_($$3)));
                if (this.f_37557_ == 0) {
                    this.m_5496_(SoundEvents.f_12516_, 10.0f, 1.0f);
                }
                ++this.f_37557_;
            }
        }
        super.m_8119_();
    }

    private boolean m_37594_() {
        Entity $$0 = this.m_37282_();
        if ($$0 == null || !$$0.m_6084_()) {
            return false;
        }
        return !($$0 instanceof ServerPlayer) || !$$0.m_5833_();
    }

    @Override
    protected ItemStack m_7941_() {
        return this.f_37555_.m_41777_();
    }

    public boolean m_37593_() {
        return this.f_19804_.m_135370_(f_37554_);
    }

    @Override
    @Nullable
    protected EntityHitResult m_6351_(Vec3 p_37575_, Vec3 p_37576_) {
        if (this.f_37556_) {
            return null;
        }
        return super.m_6351_(p_37575_, p_37576_);
    }

    @Override
    protected void m_5790_(EntityHitResult p_37573_) {
        BlockPos $$9;
        Entity $$4;
        Entity $$1 = p_37573_.m_82443_();
        float $$2 = 8.0f;
        if ($$1 instanceof LivingEntity) {
            LivingEntity $$3 = (LivingEntity)$$1;
            $$2 += EnchantmentHelper.m_44833_(this.f_37555_, $$3.m_6336_());
        }
        DamageSource $$5 = DamageSource.m_19337_(this, ($$4 = this.m_37282_()) == null ? this : $$4);
        this.f_37556_ = true;
        SoundEvent $$6 = SoundEvents.f_12514_;
        if ($$1.m_6469_($$5, $$2)) {
            if ($$1.m_6095_() == EntityType.f_20566_) {
                return;
            }
            if ($$1 instanceof LivingEntity) {
                LivingEntity $$7 = (LivingEntity)$$1;
                if ($$4 instanceof LivingEntity) {
                    EnchantmentHelper.m_44823_($$7, $$4);
                    EnchantmentHelper.m_44896_((LivingEntity)$$4, $$7);
                }
                this.m_7761_($$7);
            }
        }
        this.m_20256_(this.m_20184_().m_82542_(-0.01, -0.1, -0.01));
        float $$8 = 1.0f;
        if (this.f_19853_ instanceof ServerLevel && this.f_19853_.m_46470_() && this.m_150194_() && this.f_19853_.m_45527_($$9 = $$1.m_20183_())) {
            LightningBolt $$10 = EntityType.f_20465_.m_20615_(this.f_19853_);
            $$10.m_20219_(Vec3.m_82539_($$9));
            $$10.m_20879_($$4 instanceof ServerPlayer ? (ServerPlayer)$$4 : null);
            this.f_19853_.m_7967_($$10);
            $$6 = SoundEvents.f_12521_;
            $$8 = 5.0f;
        }
        this.m_5496_($$6, $$8, 1.0f);
    }

    public boolean m_150194_() {
        return EnchantmentHelper.m_44936_(this.f_37555_);
    }

    @Override
    protected boolean m_142470_(Player p_150196_) {
        return super.m_142470_(p_150196_) || this.m_36797_() && this.m_150171_(p_150196_) && p_150196_.m_150109_().m_36054_(this.m_7941_());
    }

    @Override
    protected SoundEvent m_7239_() {
        return SoundEvents.f_12515_;
    }

    @Override
    public void m_6123_(Player p_37580_) {
        if (this.m_150171_(p_37580_) || this.m_37282_() == null) {
            super.m_6123_(p_37580_);
        }
    }

    @Override
    public void m_7378_(CompoundTag p_37578_) {
        super.m_7378_(p_37578_);
        if (p_37578_.m_128425_("Trident", 10)) {
            this.f_37555_ = ItemStack.m_41712_(p_37578_.m_128469_("Trident"));
        }
        this.f_37556_ = p_37578_.m_128471_("DealtDamage");
        this.f_19804_.m_135381_(f_37558_, (byte)EnchantmentHelper.m_44928_(this.f_37555_));
    }

    @Override
    public void m_7380_(CompoundTag p_37582_) {
        super.m_7380_(p_37582_);
        p_37582_.m_128365_("Trident", this.f_37555_.m_41739_(new CompoundTag()));
        p_37582_.m_128379_("DealtDamage", this.f_37556_);
    }

    @Override
    public void m_6901_() {
        byte $$0 = this.f_19804_.m_135370_(f_37558_);
        if (this.f_36705_ != AbstractArrow.Pickup.ALLOWED || $$0 <= 0) {
            super.m_6901_();
        }
    }

    @Override
    protected float m_6882_() {
        return 0.99f;
    }

    @Override
    public boolean m_6000_(double p_37588_, double p_37589_, double p_37590_) {
        return true;
    }
}

