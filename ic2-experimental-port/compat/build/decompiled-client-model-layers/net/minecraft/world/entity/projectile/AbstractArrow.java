/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.projectile;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractArrow
extends Projectile {
    private static final double f_150120_ = 2.0;
    private static final EntityDataAccessor<Byte> f_36707_ = SynchedEntityData.m_135353_(AbstractArrow.class, EntityDataSerializers.f_135027_);
    private static final EntityDataAccessor<Byte> f_36708_ = SynchedEntityData.m_135353_(AbstractArrow.class, EntityDataSerializers.f_135027_);
    private static final int f_150117_ = 1;
    private static final int f_150118_ = 2;
    private static final int f_150119_ = 4;
    @Nullable
    private BlockState f_36696_;
    protected boolean f_36703_;
    protected int f_36704_;
    public Pickup f_36705_ = Pickup.DISALLOWED;
    public int f_36706_;
    private int f_36697_;
    private double f_36698_ = 2.0;
    private int f_36699_;
    private SoundEvent f_36700_ = this.m_7239_();
    @Nullable
    private IntOpenHashSet f_36701_;
    @Nullable
    private List<Entity> f_36702_;

    protected AbstractArrow(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super((EntityType<? extends Projectile>)p_36721_, p_36722_);
    }

    protected AbstractArrow(EntityType<? extends AbstractArrow> p_36711_, double p_36712_, double p_36713_, double p_36714_, Level p_36715_) {
        this(p_36711_, p_36715_);
        this.m_6034_(p_36712_, p_36713_, p_36714_);
    }

    protected AbstractArrow(EntityType<? extends AbstractArrow> p_36717_, LivingEntity p_36718_, Level p_36719_) {
        this(p_36717_, p_36718_.m_20185_(), p_36718_.m_20188_() - (double)0.1f, p_36718_.m_20189_(), p_36719_);
        this.m_5602_(p_36718_);
        if (p_36718_ instanceof Player) {
            this.f_36705_ = Pickup.ALLOWED;
        }
    }

    public void m_36740_(SoundEvent p_36741_) {
        this.f_36700_ = p_36741_;
    }

    @Override
    public boolean m_6783_(double p_36726_) {
        double $$1 = this.m_20191_().m_82309_() * 10.0;
        if (Double.isNaN($$1)) {
            $$1 = 1.0;
        }
        return p_36726_ < ($$1 *= 64.0 * AbstractArrow.m_20150_()) * $$1;
    }

    @Override
    protected void m_8097_() {
        this.f_19804_.m_135372_(f_36707_, (byte)0);
        this.f_19804_.m_135372_(f_36708_, (byte)0);
    }

    @Override
    public void m_6686_(double p_36775_, double p_36776_, double p_36777_, float p_36778_, float p_36779_) {
        super.m_6686_(p_36775_, p_36776_, p_36777_, p_36778_, p_36779_);
        this.f_36697_ = 0;
    }

    @Override
    public void m_6453_(double p_36728_, double p_36729_, double p_36730_, float p_36731_, float p_36732_, int p_36733_, boolean p_36734_) {
        this.m_6034_(p_36728_, p_36729_, p_36730_);
        this.m_19915_(p_36731_, p_36732_);
    }

    @Override
    public void m_6001_(double p_36786_, double p_36787_, double p_36788_) {
        super.m_6001_(p_36786_, p_36787_, p_36788_);
        this.f_36697_ = 0;
    }

    @Override
    public void m_8119_() {
        Vec3 $$9;
        VoxelShape $$5;
        BlockPos $$3;
        BlockState $$4;
        super.m_8119_();
        boolean $$0 = this.m_36797_();
        Vec3 $$1 = this.m_20184_();
        if (this.f_19860_ == 0.0f && this.f_19859_ == 0.0f) {
            double $$2 = $$1.m_165924_();
            this.m_146922_((float)(Mth.m_14136_($$1.f_82479_, $$1.f_82481_) * 57.2957763671875));
            this.m_146926_((float)(Mth.m_14136_($$1.f_82480_, $$2) * 57.2957763671875));
            this.f_19859_ = this.m_146908_();
            this.f_19860_ = this.m_146909_();
        }
        if (!(($$4 = this.f_19853_.m_8055_($$3 = this.m_20183_())).m_60795_() || $$0 || ($$5 = $$4.m_60812_(this.f_19853_, $$3)).m_83281_())) {
            Vec3 $$6 = this.m_20182_();
            for (AABB $$7 : $$5.m_83299_()) {
                if (!$$7.m_82338_($$3).m_82390_($$6)) continue;
                this.f_36703_ = true;
                break;
            }
        }
        if (this.f_36706_ > 0) {
            --this.f_36706_;
        }
        if (this.m_20070_() || $$4.m_60713_(Blocks.f_152499_)) {
            this.m_20095_();
        }
        if (this.f_36703_ && !$$0) {
            if (this.f_36696_ != $$4 && this.m_36798_()) {
                this.m_36799_();
            } else if (!this.f_19853_.f_46443_) {
                this.m_6901_();
            }
            ++this.f_36704_;
            return;
        }
        this.f_36704_ = 0;
        Vec3 $$8 = this.m_20182_();
        HitResult $$10 = this.f_19853_.m_45547_(new ClipContext($$8, $$9 = $$8.m_82549_($$1), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if ($$10.m_6662_() != HitResult.Type.MISS) {
            $$9 = $$10.m_82450_();
        }
        while (!this.m_213877_()) {
            EntityHitResult $$11 = this.m_6351_($$8, $$9);
            if ($$11 != null) {
                $$10 = $$11;
            }
            if ($$10 != null && $$10.m_6662_() == HitResult.Type.ENTITY) {
                Entity $$12 = ((EntityHitResult)$$10).m_82443_();
                Entity $$13 = this.m_37282_();
                if ($$12 instanceof Player && $$13 instanceof Player && !((Player)$$13).m_7099_((Player)$$12)) {
                    $$10 = null;
                    $$11 = null;
                }
            }
            if ($$10 != null && !$$0) {
                this.m_6532_($$10);
                this.f_19812_ = true;
            }
            if ($$11 == null || this.m_36796_() <= 0) break;
            $$10 = null;
        }
        $$1 = this.m_20184_();
        double $$14 = $$1.f_82479_;
        double $$15 = $$1.f_82480_;
        double $$16 = $$1.f_82481_;
        if (this.m_36792_()) {
            for (int $$17 = 0; $$17 < 4; ++$$17) {
                this.f_19853_.m_7106_(ParticleTypes.f_123797_, this.m_20185_() + $$14 * (double)$$17 / 4.0, this.m_20186_() + $$15 * (double)$$17 / 4.0, this.m_20189_() + $$16 * (double)$$17 / 4.0, -$$14, -$$15 + 0.2, -$$16);
            }
        }
        double $$18 = this.m_20185_() + $$14;
        double $$19 = this.m_20186_() + $$15;
        double $$20 = this.m_20189_() + $$16;
        double $$21 = $$1.m_165924_();
        if ($$0) {
            this.m_146922_((float)(Mth.m_14136_(-$$14, -$$16) * 57.2957763671875));
        } else {
            this.m_146922_((float)(Mth.m_14136_($$14, $$16) * 57.2957763671875));
        }
        this.m_146926_((float)(Mth.m_14136_($$15, $$21) * 57.2957763671875));
        this.m_146926_(AbstractArrow.m_37273_(this.f_19860_, this.m_146909_()));
        this.m_146922_(AbstractArrow.m_37273_(this.f_19859_, this.m_146908_()));
        float $$22 = 0.99f;
        float $$23 = 0.05f;
        if (this.m_20069_()) {
            for (int $$24 = 0; $$24 < 4; ++$$24) {
                float $$25 = 0.25f;
                this.f_19853_.m_7106_(ParticleTypes.f_123795_, $$18 - $$14 * 0.25, $$19 - $$15 * 0.25, $$20 - $$16 * 0.25, $$14, $$15, $$16);
            }
            $$22 = this.m_6882_();
        }
        this.m_20256_($$1.m_82490_($$22));
        if (!this.m_20068_() && !$$0) {
            Vec3 $$26 = this.m_20184_();
            this.m_20334_($$26.f_82479_, $$26.f_82480_ - (double)0.05f, $$26.f_82481_);
        }
        this.m_6034_($$18, $$19, $$20);
        this.m_20101_();
    }

    private boolean m_36798_() {
        return this.f_36703_ && this.f_19853_.m_45772_(new AABB(this.m_20182_(), this.m_20182_()).m_82400_(0.06));
    }

    private void m_36799_() {
        this.f_36703_ = false;
        Vec3 $$0 = this.m_20184_();
        this.m_20256_($$0.m_82542_(this.f_19796_.m_188501_() * 0.2f, this.f_19796_.m_188501_() * 0.2f, this.f_19796_.m_188501_() * 0.2f));
        this.f_36697_ = 0;
    }

    @Override
    public void m_6478_(MoverType p_36749_, Vec3 p_36750_) {
        super.m_6478_(p_36749_, p_36750_);
        if (p_36749_ != MoverType.SELF && this.m_36798_()) {
            this.m_36799_();
        }
    }

    protected void m_6901_() {
        ++this.f_36697_;
        if (this.f_36697_ >= 1200) {
            this.m_146870_();
        }
    }

    private void m_36723_() {
        if (this.f_36702_ != null) {
            this.f_36702_.clear();
        }
        if (this.f_36701_ != null) {
            this.f_36701_.clear();
        }
    }

    @Override
    protected void m_5790_(EntityHitResult p_36757_) {
        DamageSource $$7;
        Entity $$5;
        super.m_5790_(p_36757_);
        Entity $$1 = p_36757_.m_82443_();
        float $$2 = (float)this.m_20184_().m_82553_();
        int $$3 = Mth.m_14165_(Mth.m_14008_((double)$$2 * this.f_36698_, 0.0, 2.147483647E9));
        if (this.m_36796_() > 0) {
            if (this.f_36701_ == null) {
                this.f_36701_ = new IntOpenHashSet(5);
            }
            if (this.f_36702_ == null) {
                this.f_36702_ = Lists.newArrayListWithCapacity((int)5);
            }
            if (this.f_36701_.size() < this.m_36796_() + 1) {
                this.f_36701_.add($$1.m_19879_());
            } else {
                this.m_146870_();
                return;
            }
        }
        if (this.m_36792_()) {
            long $$4 = this.f_19796_.m_188503_($$3 / 2 + 2);
            $$3 = (int)Math.min($$4 + (long)$$3, Integer.MAX_VALUE);
        }
        if (($$5 = this.m_37282_()) == null) {
            DamageSource $$6 = DamageSource.m_19346_(this, this);
        } else {
            $$7 = DamageSource.m_19346_(this, $$5);
            if ($$5 instanceof LivingEntity) {
                ((LivingEntity)$$5).m_21335_($$1);
            }
        }
        boolean $$8 = $$1.m_6095_() == EntityType.f_20566_;
        int $$9 = $$1.m_20094_();
        if (this.m_6060_() && !$$8) {
            $$1.m_20254_(5);
        }
        if ($$1.m_6469_($$7, $$3)) {
            if ($$8) {
                return;
            }
            if ($$1 instanceof LivingEntity) {
                LivingEntity $$10 = (LivingEntity)$$1;
                if (!this.f_19853_.f_46443_ && this.m_36796_() <= 0) {
                    $$10.m_21317_($$10.m_21234_() + 1);
                }
                if (this.f_36699_ > 0) {
                    double $$11 = Math.max(0.0, 1.0 - $$10.m_21133_(Attributes.f_22278_));
                    Vec3 $$12 = this.m_20184_().m_82542_(1.0, 0.0, 1.0).m_82541_().m_82490_((double)this.f_36699_ * 0.6 * $$11);
                    if ($$12.m_82556_() > 0.0) {
                        $$10.m_5997_($$12.f_82479_, 0.1, $$12.f_82481_);
                    }
                }
                if (!this.f_19853_.f_46443_ && $$5 instanceof LivingEntity) {
                    EnchantmentHelper.m_44823_($$10, $$5);
                    EnchantmentHelper.m_44896_((LivingEntity)$$5, $$10);
                }
                this.m_7761_($$10);
                if ($$5 != null && $$10 != $$5 && $$10 instanceof Player && $$5 instanceof ServerPlayer && !this.m_20067_()) {
                    ((ServerPlayer)$$5).f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132159_, 0.0f));
                }
                if (!$$1.m_6084_() && this.f_36702_ != null) {
                    this.f_36702_.add($$10);
                }
                if (!this.f_19853_.f_46443_ && $$5 instanceof ServerPlayer) {
                    ServerPlayer $$13 = (ServerPlayer)$$5;
                    if (this.f_36702_ != null && this.m_36795_()) {
                        CriteriaTriggers.f_10556_.m_46871_($$13, this.f_36702_);
                    } else if (!$$1.m_6084_() && this.m_36795_()) {
                        CriteriaTriggers.f_10556_.m_46871_($$13, Arrays.asList($$1));
                    }
                }
            }
            this.m_5496_(this.f_36700_, 1.0f, 1.2f / (this.f_19796_.m_188501_() * 0.2f + 0.9f));
            if (this.m_36796_() <= 0) {
                this.m_146870_();
            }
        } else {
            $$1.m_7311_($$9);
            this.m_20256_(this.m_20184_().m_82490_(-0.1));
            this.m_146922_(this.m_146908_() + 180.0f);
            this.f_19859_ += 180.0f;
            if (!this.f_19853_.f_46443_ && this.m_20184_().m_82556_() < 1.0E-7) {
                if (this.f_36705_ == Pickup.ALLOWED) {
                    this.m_5552_(this.m_7941_(), 0.1f);
                }
                this.m_146870_();
            }
        }
    }

    @Override
    protected void m_8060_(BlockHitResult p_36755_) {
        this.f_36696_ = this.f_19853_.m_8055_(p_36755_.m_82425_());
        super.m_8060_(p_36755_);
        Vec3 $$1 = p_36755_.m_82450_().m_82492_(this.m_20185_(), this.m_20186_(), this.m_20189_());
        this.m_20256_($$1);
        Vec3 $$2 = $$1.m_82541_().m_82490_(0.05f);
        this.m_20343_(this.m_20185_() - $$2.f_82479_, this.m_20186_() - $$2.f_82480_, this.m_20189_() - $$2.f_82481_);
        this.m_5496_(this.m_36784_(), 1.0f, 1.2f / (this.f_19796_.m_188501_() * 0.2f + 0.9f));
        this.f_36703_ = true;
        this.f_36706_ = 7;
        this.m_36762_(false);
        this.m_36767_((byte)0);
        this.m_36740_(SoundEvents.f_11685_);
        this.m_36793_(false);
        this.m_36723_();
    }

    protected SoundEvent m_7239_() {
        return SoundEvents.f_11685_;
    }

    protected final SoundEvent m_36784_() {
        return this.f_36700_;
    }

    protected void m_7761_(LivingEntity p_36744_) {
    }

    @Nullable
    protected EntityHitResult m_6351_(Vec3 p_36758_, Vec3 p_36759_) {
        return ProjectileUtil.m_37304_(this.f_19853_, this, p_36758_, p_36759_, this.m_20191_().m_82369_(this.m_20184_()).m_82400_(1.0), this::m_5603_);
    }

    @Override
    protected boolean m_5603_(Entity p_36743_) {
        return super.m_5603_(p_36743_) && (this.f_36701_ == null || !this.f_36701_.contains(p_36743_.m_19879_()));
    }

    @Override
    public void m_7380_(CompoundTag p_36772_) {
        super.m_7380_(p_36772_);
        p_36772_.m_128376_("life", (short)this.f_36697_);
        if (this.f_36696_ != null) {
            p_36772_.m_128365_("inBlockState", NbtUtils.m_129202_(this.f_36696_));
        }
        p_36772_.m_128344_("shake", (byte)this.f_36706_);
        p_36772_.m_128379_("inGround", this.f_36703_);
        p_36772_.m_128344_("pickup", (byte)this.f_36705_.ordinal());
        p_36772_.m_128347_("damage", this.f_36698_);
        p_36772_.m_128379_("crit", this.m_36792_());
        p_36772_.m_128344_("PierceLevel", this.m_36796_());
        p_36772_.m_128359_("SoundEvent", Registry.f_122821_.m_7981_(this.f_36700_).toString());
        p_36772_.m_128379_("ShotFromCrossbow", this.m_36795_());
    }

    @Override
    public void m_7378_(CompoundTag p_36761_) {
        super.m_7378_(p_36761_);
        this.f_36697_ = p_36761_.m_128448_("life");
        if (p_36761_.m_128425_("inBlockState", 10)) {
            this.f_36696_ = NbtUtils.m_129241_(p_36761_.m_128469_("inBlockState"));
        }
        this.f_36706_ = p_36761_.m_128445_("shake") & 0xFF;
        this.f_36703_ = p_36761_.m_128471_("inGround");
        if (p_36761_.m_128425_("damage", 99)) {
            this.f_36698_ = p_36761_.m_128459_("damage");
        }
        this.f_36705_ = Pickup.m_36808_(p_36761_.m_128445_("pickup"));
        this.m_36762_(p_36761_.m_128471_("crit"));
        this.m_36767_(p_36761_.m_128445_("PierceLevel"));
        if (p_36761_.m_128425_("SoundEvent", 8)) {
            this.f_36700_ = Registry.f_122821_.m_6612_(new ResourceLocation(p_36761_.m_128461_("SoundEvent"))).orElse(this.m_7239_());
        }
        this.m_36793_(p_36761_.m_128471_("ShotFromCrossbow"));
    }

    @Override
    public void m_5602_(@Nullable Entity p_36770_) {
        super.m_5602_(p_36770_);
        if (p_36770_ instanceof Player) {
            this.f_36705_ = ((Player)p_36770_).m_150110_().f_35937_ ? Pickup.CREATIVE_ONLY : Pickup.ALLOWED;
        }
    }

    @Override
    public void m_6123_(Player p_36766_) {
        if (this.f_19853_.f_46443_ || !this.f_36703_ && !this.m_36797_() || this.f_36706_ > 0) {
            return;
        }
        if (this.m_142470_(p_36766_)) {
            p_36766_.m_7938_(this, 1);
            this.m_146870_();
        }
    }

    protected boolean m_142470_(Player p_150121_) {
        switch (this.f_36705_) {
            case ALLOWED: {
                return p_150121_.m_150109_().m_36054_(this.m_7941_());
            }
            case CREATIVE_ONLY: {
                return p_150121_.m_150110_().f_35937_;
            }
        }
        return false;
    }

    protected abstract ItemStack m_7941_();

    @Override
    protected Entity.MovementEmission m_142319_() {
        return Entity.MovementEmission.NONE;
    }

    public void m_36781_(double p_36782_) {
        this.f_36698_ = p_36782_;
    }

    public double m_36789_() {
        return this.f_36698_;
    }

    public void m_36735_(int p_36736_) {
        this.f_36699_ = p_36736_;
    }

    public int m_150123_() {
        return this.f_36699_;
    }

    @Override
    public boolean m_6097_() {
        return false;
    }

    @Override
    protected float m_6380_(Pose p_36752_, EntityDimensions p_36753_) {
        return 0.13f;
    }

    public void m_36762_(boolean p_36763_) {
        this.m_36737_(1, p_36763_);
    }

    public void m_36767_(byte p_36768_) {
        this.f_19804_.m_135381_(f_36708_, p_36768_);
    }

    private void m_36737_(int p_36738_, boolean p_36739_) {
        byte $$2 = this.f_19804_.m_135370_(f_36707_);
        if (p_36739_) {
            this.f_19804_.m_135381_(f_36707_, (byte)($$2 | p_36738_));
        } else {
            this.f_19804_.m_135381_(f_36707_, (byte)($$2 & ~p_36738_));
        }
    }

    public boolean m_36792_() {
        byte $$0 = this.f_19804_.m_135370_(f_36707_);
        return ($$0 & 1) != 0;
    }

    public boolean m_36795_() {
        byte $$0 = this.f_19804_.m_135370_(f_36707_);
        return ($$0 & 4) != 0;
    }

    public byte m_36796_() {
        return this.f_19804_.m_135370_(f_36708_);
    }

    public void m_36745_(LivingEntity p_36746_, float p_36747_) {
        int $$2 = EnchantmentHelper.m_44836_(Enchantments.f_44988_, p_36746_);
        int $$3 = EnchantmentHelper.m_44836_(Enchantments.f_44989_, p_36746_);
        this.m_36781_((double)(p_36747_ * 2.0f) + this.f_19796_.m_216328_((double)this.f_19853_.m_46791_().m_19028_() * 0.11, 0.57425));
        if ($$2 > 0) {
            this.m_36781_(this.m_36789_() + (double)$$2 * 0.5 + 0.5);
        }
        if ($$3 > 0) {
            this.m_36735_($$3);
        }
        if (EnchantmentHelper.m_44836_(Enchantments.f_44990_, p_36746_) > 0) {
            this.m_20254_(100);
        }
    }

    protected float m_6882_() {
        return 0.6f;
    }

    public void m_36790_(boolean p_36791_) {
        this.f_19794_ = p_36791_;
        this.m_36737_(2, p_36791_);
    }

    public boolean m_36797_() {
        if (!this.f_19853_.f_46443_) {
            return this.f_19794_;
        }
        return (this.f_19804_.m_135370_(f_36707_) & 2) != 0;
    }

    public void m_36793_(boolean p_36794_) {
        this.m_36737_(4, p_36794_);
    }

    public static final class Pickup
    extends Enum<Pickup> {
        public static final /* enum */ Pickup DISALLOWED = new Pickup();
        public static final /* enum */ Pickup ALLOWED = new Pickup();
        public static final /* enum */ Pickup CREATIVE_ONLY = new Pickup();
        private static final /* synthetic */ Pickup[] $VALUES;

        public static Pickup[] values() {
            return (Pickup[])$VALUES.clone();
        }

        public static Pickup valueOf(String p_36811_) {
            return Enum.valueOf(Pickup.class, p_36811_);
        }

        public static Pickup m_36808_(int p_36809_) {
            if (p_36809_ < 0 || p_36809_ > Pickup.values().length) {
                p_36809_ = 0;
            }
            return Pickup.values()[p_36809_];
        }

        private static /* synthetic */ Pickup[] m_150126_() {
            return new Pickup[]{DISALLOWED, ALLOWED, CREATIVE_ONLY};
        }

        static {
            $VALUES = Pickup.m_150126_();
        }
    }
}

