/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.projectile;

import java.util.List;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FireworkRocketEntity
extends Projectile
implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> f_37019_ = SynchedEntityData.m_135353_(FireworkRocketEntity.class, EntityDataSerializers.f_135033_);
    private static final EntityDataAccessor<OptionalInt> f_37020_ = SynchedEntityData.m_135353_(FireworkRocketEntity.class, EntityDataSerializers.f_135044_);
    private static final EntityDataAccessor<Boolean> f_37021_ = SynchedEntityData.m_135353_(FireworkRocketEntity.class, EntityDataSerializers.f_135035_);
    private int f_37022_;
    private int f_37023_;
    @Nullable
    private LivingEntity f_37024_;

    public FireworkRocketEntity(EntityType<? extends FireworkRocketEntity> p_37027_, Level p_37028_) {
        super((EntityType<? extends Projectile>)p_37027_, p_37028_);
    }

    public FireworkRocketEntity(Level p_37030_, double p_37031_, double p_37032_, double p_37033_, ItemStack p_37034_) {
        super((EntityType<? extends Projectile>)EntityType.f_20451_, p_37030_);
        this.f_37022_ = 0;
        this.m_6034_(p_37031_, p_37032_, p_37033_);
        int $$5 = 1;
        if (!p_37034_.m_41619_() && p_37034_.m_41782_()) {
            this.f_19804_.m_135381_(f_37019_, p_37034_.m_41777_());
            $$5 += p_37034_.m_41698_("Fireworks").m_128445_("Flight");
        }
        this.m_20334_(this.f_19796_.m_216328_(0.0, 0.002297), 0.05, this.f_19796_.m_216328_(0.0, 0.002297));
        this.f_37023_ = 10 * $$5 + this.f_19796_.m_188503_(6) + this.f_19796_.m_188503_(7);
    }

    public FireworkRocketEntity(Level p_37036_, @Nullable Entity p_37037_, double p_37038_, double p_37039_, double p_37040_, ItemStack p_37041_) {
        this(p_37036_, p_37038_, p_37039_, p_37040_, p_37041_);
        this.m_5602_(p_37037_);
    }

    public FireworkRocketEntity(Level p_37058_, ItemStack p_37059_, LivingEntity p_37060_) {
        this(p_37058_, p_37060_, p_37060_.m_20185_(), p_37060_.m_20186_(), p_37060_.m_20189_(), p_37059_);
        this.f_19804_.m_135381_(f_37020_, OptionalInt.of(p_37060_.m_19879_()));
        this.f_37024_ = p_37060_;
    }

    public FireworkRocketEntity(Level p_37043_, ItemStack p_37044_, double p_37045_, double p_37046_, double p_37047_, boolean p_37048_) {
        this(p_37043_, p_37045_, p_37046_, p_37047_, p_37044_);
        this.f_19804_.m_135381_(f_37021_, p_37048_);
    }

    public FireworkRocketEntity(Level p_37050_, ItemStack p_37051_, Entity p_37052_, double p_37053_, double p_37054_, double p_37055_, boolean p_37056_) {
        this(p_37050_, p_37051_, p_37053_, p_37054_, p_37055_, p_37056_);
        this.m_5602_(p_37052_);
    }

    @Override
    protected void m_8097_() {
        this.f_19804_.m_135372_(f_37019_, ItemStack.f_41583_);
        this.f_19804_.m_135372_(f_37020_, OptionalInt.empty());
        this.f_19804_.m_135372_(f_37021_, false);
    }

    @Override
    public boolean m_6783_(double p_37065_) {
        return p_37065_ < 4096.0 && !this.m_37088_();
    }

    @Override
    public boolean m_6000_(double p_37083_, double p_37084_, double p_37085_) {
        return super.m_6000_(p_37083_, p_37084_, p_37085_) && !this.m_37088_();
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (this.m_37088_()) {
            if (this.f_37024_ == null) {
                this.f_19804_.m_135370_(f_37020_).ifPresent(p_37067_ -> {
                    Entity $$1 = this.f_19853_.m_6815_(p_37067_);
                    if ($$1 instanceof LivingEntity) {
                        this.f_37024_ = (LivingEntity)$$1;
                    }
                });
            }
            if (this.f_37024_ != null) {
                Vec3 $$5;
                if (this.f_37024_.m_21255_()) {
                    Vec3 $$0 = this.f_37024_.m_20154_();
                    double $$1 = 1.5;
                    double $$2 = 0.1;
                    Vec3 $$3 = this.f_37024_.m_20184_();
                    this.f_37024_.m_20256_($$3.m_82520_($$0.f_82479_ * 0.1 + ($$0.f_82479_ * 1.5 - $$3.f_82479_) * 0.5, $$0.f_82480_ * 0.1 + ($$0.f_82480_ * 1.5 - $$3.f_82480_) * 0.5, $$0.f_82481_ * 0.1 + ($$0.f_82481_ * 1.5 - $$3.f_82481_) * 0.5));
                    Vec3 $$4 = this.f_37024_.m_204034_(Items.f_42688_);
                } else {
                    $$5 = Vec3.f_82478_;
                }
                this.m_6034_(this.f_37024_.m_20185_() + $$5.f_82479_, this.f_37024_.m_20186_() + $$5.f_82480_, this.f_37024_.m_20189_() + $$5.f_82481_);
                this.m_20256_(this.f_37024_.m_20184_());
            }
        } else {
            if (!this.m_37079_()) {
                double $$6 = this.f_19862_ ? 1.0 : 1.15;
                this.m_20256_(this.m_20184_().m_82542_($$6, 1.0, $$6).m_82520_(0.0, 0.04, 0.0));
            }
            Vec3 $$7 = this.m_20184_();
            this.m_6478_(MoverType.SELF, $$7);
            this.m_20256_($$7);
        }
        HitResult $$8 = ProjectileUtil.m_37294_(this, this::m_5603_);
        if (!this.f_19794_) {
            this.m_6532_($$8);
            this.f_19812_ = true;
        }
        this.m_37283_();
        if (this.f_37022_ == 0 && !this.m_20067_()) {
            this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11932_, SoundSource.AMBIENT, 3.0f, 1.0f);
        }
        ++this.f_37022_;
        if (this.f_19853_.f_46443_ && this.f_37022_ % 2 < 2) {
            this.f_19853_.m_7106_(ParticleTypes.f_123815_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.f_19796_.m_188583_() * 0.05, -this.m_20184_().f_82480_ * 0.5, this.f_19796_.m_188583_() * 0.05);
        }
        if (!this.f_19853_.f_46443_ && this.f_37022_ > this.f_37023_) {
            this.m_37080_();
        }
    }

    private void m_37080_() {
        this.f_19853_.m_7605_(this, (byte)17);
        this.m_146852_(GameEvent.f_157812_, this.m_37282_());
        this.m_37087_();
        this.m_146870_();
    }

    @Override
    protected void m_5790_(EntityHitResult p_37071_) {
        super.m_5790_(p_37071_);
        if (this.f_19853_.f_46443_) {
            return;
        }
        this.m_37080_();
    }

    @Override
    protected void m_8060_(BlockHitResult p_37069_) {
        BlockPos $$1 = new BlockPos(p_37069_.m_82425_());
        this.f_19853_.m_8055_($$1).m_60682_(this.f_19853_, $$1, this);
        if (!this.f_19853_.m_5776_() && this.m_37086_()) {
            this.m_37080_();
        }
        super.m_8060_(p_37069_);
    }

    private boolean m_37086_() {
        ItemStack $$0 = this.f_19804_.m_135370_(f_37019_);
        CompoundTag $$1 = $$0.m_41619_() ? null : $$0.m_41737_("Fireworks");
        ListTag $$2 = $$1 != null ? $$1.m_128437_("Explosions", 10) : null;
        return $$2 != null && !$$2.isEmpty();
    }

    private void m_37087_() {
        ListTag $$3;
        float $$0 = 0.0f;
        ItemStack $$1 = this.f_19804_.m_135370_(f_37019_);
        CompoundTag $$2 = $$1.m_41619_() ? null : $$1.m_41737_("Fireworks");
        ListTag listTag = $$3 = $$2 != null ? $$2.m_128437_("Explosions", 10) : null;
        if ($$3 != null && !$$3.isEmpty()) {
            $$0 = 5.0f + (float)($$3.size() * 2);
        }
        if ($$0 > 0.0f) {
            if (this.f_37024_ != null) {
                this.f_37024_.m_6469_(DamageSource.m_19352_(this, this.m_37282_()), 5.0f + (float)($$3.size() * 2));
            }
            double $$4 = 5.0;
            Vec3 $$5 = this.m_20182_();
            List<LivingEntity> $$6 = this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(5.0));
            for (LivingEntity $$7 : $$6) {
                if ($$7 == this.f_37024_ || this.m_20280_($$7) > 25.0) continue;
                boolean $$8 = false;
                for (int $$9 = 0; $$9 < 2; ++$$9) {
                    Vec3 $$10 = new Vec3($$7.m_20185_(), $$7.m_20227_(0.5 * (double)$$9), $$7.m_20189_());
                    BlockHitResult $$11 = this.f_19853_.m_45547_(new ClipContext($$5, $$10, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
                    if (((HitResult)$$11).m_6662_() != HitResult.Type.MISS) continue;
                    $$8 = true;
                    break;
                }
                if (!$$8) continue;
                float $$12 = $$0 * (float)Math.sqrt((5.0 - (double)this.m_20270_($$7)) / 5.0);
                $$7.m_6469_(DamageSource.m_19352_(this, this.m_37282_()), $$12);
            }
        }
    }

    private boolean m_37088_() {
        return this.f_19804_.m_135370_(f_37020_).isPresent();
    }

    public boolean m_37079_() {
        return this.f_19804_.m_135370_(f_37021_);
    }

    @Override
    public void m_7822_(byte p_37063_) {
        if (p_37063_ == 17 && this.f_19853_.f_46443_) {
            if (!this.m_37086_()) {
                for (int $$1 = 0; $$1 < this.f_19796_.m_188503_(3) + 2; ++$$1) {
                    this.f_19853_.m_7106_(ParticleTypes.f_123759_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.f_19796_.m_188583_() * 0.05, 0.005, this.f_19796_.m_188583_() * 0.05);
                }
            } else {
                ItemStack $$2 = this.f_19804_.m_135370_(f_37019_);
                CompoundTag $$3 = $$2.m_41619_() ? null : $$2.m_41737_("Fireworks");
                Vec3 $$4 = this.m_20184_();
                this.f_19853_.m_7228_(this.m_20185_(), this.m_20186_(), this.m_20189_(), $$4.f_82479_, $$4.f_82480_, $$4.f_82481_, $$3);
            }
        }
        super.m_7822_(p_37063_);
    }

    @Override
    public void m_7380_(CompoundTag p_37075_) {
        super.m_7380_(p_37075_);
        p_37075_.m_128405_("Life", this.f_37022_);
        p_37075_.m_128405_("LifeTime", this.f_37023_);
        ItemStack $$1 = this.f_19804_.m_135370_(f_37019_);
        if (!$$1.m_41619_()) {
            p_37075_.m_128365_("FireworksItem", $$1.m_41739_(new CompoundTag()));
        }
        p_37075_.m_128379_("ShotAtAngle", this.f_19804_.m_135370_(f_37021_));
    }

    @Override
    public void m_7378_(CompoundTag p_37073_) {
        super.m_7378_(p_37073_);
        this.f_37022_ = p_37073_.m_128451_("Life");
        this.f_37023_ = p_37073_.m_128451_("LifeTime");
        ItemStack $$1 = ItemStack.m_41712_(p_37073_.m_128469_("FireworksItem"));
        if (!$$1.m_41619_()) {
            this.f_19804_.m_135381_(f_37019_, $$1);
        }
        if (p_37073_.m_128441_("ShotAtAngle")) {
            this.f_19804_.m_135381_(f_37021_, p_37073_.m_128471_("ShotAtAngle"));
        }
    }

    @Override
    public ItemStack m_7846_() {
        ItemStack $$0 = this.f_19804_.m_135370_(f_37019_);
        return $$0.m_41619_() ? new ItemStack(Items.f_42688_) : $$0;
    }

    @Override
    public boolean m_6097_() {
        return false;
    }
}

