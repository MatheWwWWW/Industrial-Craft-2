/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import com.mojang.math.Vector3f;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Shulker
extends AbstractGolem
implements Enemy {
    private static final UUID f_33394_ = UUID.fromString("7E0292F2-9434-48D5-A29F-9583AF7DF27F");
    private static final AttributeModifier f_33395_ = new AttributeModifier(f_33394_, "Covered armor bonus", 20.0, AttributeModifier.Operation.ADDITION);
    protected static final EntityDataAccessor<Direction> f_33392_ = SynchedEntityData.m_135353_(Shulker.class, EntityDataSerializers.f_135040_);
    protected static final EntityDataAccessor<Byte> f_33401_ = SynchedEntityData.m_135353_(Shulker.class, EntityDataSerializers.f_135027_);
    protected static final EntityDataAccessor<Byte> f_33393_ = SynchedEntityData.m_135353_(Shulker.class, EntityDataSerializers.f_135027_);
    private static final int f_149756_ = 6;
    private static final byte f_149757_ = 16;
    private static final byte f_149758_ = 16;
    private static final int f_149759_ = 8;
    private static final int f_149760_ = 8;
    private static final int f_149761_ = 5;
    private static final float f_149762_ = 0.05f;
    static final Vector3f f_149763_ = Util.m_137537_(() -> {
        Vec3i $$0 = Direction.SOUTH.m_122436_();
        return new Vector3f($$0.m_123341_(), $$0.m_123342_(), $$0.m_123343_());
    });
    private float f_33396_;
    private float f_33397_;
    @Nullable
    private BlockPos f_149764_;
    private int f_33399_;
    private static final float f_149765_ = 1.0f;

    public Shulker(EntityType<? extends Shulker> p_33404_, Level p_33405_) {
        super((EntityType<? extends AbstractGolem>)p_33404_, p_33405_);
        this.f_21364_ = 5;
        this.f_21365_ = new ShulkerLookControl(this);
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(1, new LookAtPlayerGoal(this, Player.class, 8.0f, 0.02f, true));
        this.f_21345_.m_25352_(4, new ShulkerAttackGoal());
        this.f_21345_.m_25352_(7, new ShulkerPeekGoal());
        this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
        this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, this.getClass()).m_26044_(new Class[0]));
        this.f_21346_.m_25352_(2, new ShulkerNearestAttackGoal(this));
        this.f_21346_.m_25352_(3, new ShulkerDefenseAttackGoal(this));
    }

    @Override
    protected Entity.MovementEmission m_142319_() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    public SoundSource m_5720_() {
        return SoundSource.HOSTILE;
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12407_;
    }

    @Override
    public void m_8032_() {
        if (!this.m_33468_()) {
            super.m_8032_();
        }
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12413_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_33457_) {
        if (this.m_33468_()) {
            return SoundEvents.f_12415_;
        }
        return SoundEvents.f_12414_;
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_33392_, Direction.DOWN);
        this.f_19804_.m_135372_(f_33401_, (byte)0);
        this.f_19804_.m_135372_(f_33393_, (byte)16);
    }

    public static AttributeSupplier.Builder m_33477_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 30.0);
    }

    @Override
    protected BodyRotationControl m_7560_() {
        return new ShulkerBodyRotationControl(this);
    }

    @Override
    public void m_7378_(CompoundTag p_33432_) {
        super.m_7378_(p_33432_);
        this.m_149788_(Direction.m_122376_(p_33432_.m_128445_("AttachFace")));
        this.f_19804_.m_135381_(f_33401_, p_33432_.m_128445_("Peek"));
        if (p_33432_.m_128425_("Color", 99)) {
            this.f_19804_.m_135381_(f_33393_, p_33432_.m_128445_("Color"));
        }
    }

    @Override
    public void m_7380_(CompoundTag p_33443_) {
        super.m_7380_(p_33443_);
        p_33443_.m_128344_("AttachFace", (byte)this.m_33461_().m_122411_());
        p_33443_.m_128344_("Peek", this.f_19804_.m_135370_(f_33401_));
        p_33443_.m_128344_("Color", this.f_19804_.m_135370_(f_33393_));
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (!(this.f_19853_.f_46443_ || this.m_20159_() || this.m_149785_(this.m_20183_(), this.m_33461_()))) {
            this.m_149807_();
        }
        if (this.m_149808_()) {
            this.m_149809_();
        }
        if (this.f_19853_.f_46443_) {
            if (this.f_33399_ > 0) {
                --this.f_33399_;
            } else {
                this.f_149764_ = null;
            }
        }
    }

    private void m_149807_() {
        Direction $$0 = this.m_149810_(this.m_20183_());
        if ($$0 != null) {
            this.m_149788_($$0);
        } else {
            this.m_33460_();
        }
    }

    @Override
    protected AABB m_142242_() {
        float $$0 = Shulker.m_149768_(this.f_33397_);
        Direction $$1 = this.m_33461_().m_122424_();
        float $$2 = this.m_6095_().m_20678_() / 2.0f;
        return Shulker.m_149790_($$1, $$0).m_82386_(this.m_20185_() - (double)$$2, this.m_20186_(), this.m_20189_() - (double)$$2);
    }

    private static float m_149768_(float p_149769_) {
        return 0.5f - Mth.m_14031_((0.5f + p_149769_) * (float)Math.PI) * 0.5f;
    }

    private boolean m_149808_() {
        this.f_33396_ = this.f_33397_;
        float $$0 = (float)this.m_33463_() * 0.01f;
        if (this.f_33397_ == $$0) {
            return false;
        }
        this.f_33397_ = this.f_33397_ > $$0 ? Mth.m_14036_(this.f_33397_ - 0.05f, $$0, 1.0f) : Mth.m_14036_(this.f_33397_ + 0.05f, 0.0f, $$0);
        return true;
    }

    private void m_149809_() {
        this.m_20090_();
        float $$0 = Shulker.m_149768_(this.f_33397_);
        float $$1 = Shulker.m_149768_(this.f_33396_);
        Direction $$2 = this.m_33461_().m_122424_();
        float $$3 = $$0 - $$1;
        if ($$3 <= 0.0f) {
            return;
        }
        List<Entity> $$4 = this.f_19853_.m_6249_(this, Shulker.m_149793_($$2, $$1, $$0).m_82386_(this.m_20185_() - 0.5, this.m_20186_(), this.m_20189_() - 0.5), EntitySelector.f_20408_.and(p_149771_ -> !p_149771_.m_20365_(this)));
        for (Entity $$5 : $$4) {
            if ($$5 instanceof Shulker || $$5.f_19794_) continue;
            $$5.m_6478_(MoverType.SHULKER, new Vec3($$3 * (float)$$2.m_122429_(), $$3 * (float)$$2.m_122430_(), $$3 * (float)$$2.m_122431_()));
        }
    }

    public static AABB m_149790_(Direction p_149791_, float p_149792_) {
        return Shulker.m_149793_(p_149791_, -1.0f, p_149792_);
    }

    public static AABB m_149793_(Direction p_149794_, float p_149795_, float p_149796_) {
        double $$3 = Math.max(p_149795_, p_149796_);
        double $$4 = Math.min(p_149795_, p_149796_);
        return new AABB(BlockPos.f_121853_).m_82363_((double)p_149794_.m_122429_() * $$3, (double)p_149794_.m_122430_() * $$3, (double)p_149794_.m_122431_() * $$3).m_82310_((double)(-p_149794_.m_122429_()) * (1.0 + $$4), (double)(-p_149794_.m_122430_()) * (1.0 + $$4), (double)(-p_149794_.m_122431_()) * (1.0 + $$4));
    }

    @Override
    public double m_6049_() {
        EntityType<?> $$0 = this.m_20202_().m_6095_();
        if ($$0 == EntityType.f_20552_ || $$0 == EntityType.f_20469_) {
            return 0.1875 - this.m_20202_().m_6048_();
        }
        return super.m_6049_();
    }

    @Override
    public boolean m_7998_(Entity p_149773_, boolean p_149774_) {
        if (this.f_19853_.m_5776_()) {
            this.f_149764_ = null;
            this.f_33399_ = 0;
        }
        this.m_149788_(Direction.DOWN);
        return super.m_7998_(p_149773_, p_149774_);
    }

    @Override
    public void m_8127_() {
        super.m_8127_();
        if (this.f_19853_.f_46443_) {
            this.f_149764_ = this.m_20183_();
        }
        this.f_20884_ = 0.0f;
        this.f_20883_ = 0.0f;
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_149780_, DifficultyInstance p_149781_, MobSpawnType p_149782_, @Nullable SpawnGroupData p_149783_, @Nullable CompoundTag p_149784_) {
        this.m_146922_(0.0f);
        this.f_20885_ = this.m_146908_();
        this.m_146867_();
        return super.m_6518_(p_149780_, p_149781_, p_149782_, p_149783_, p_149784_);
    }

    @Override
    public void m_6478_(MoverType p_33424_, Vec3 p_33425_) {
        if (p_33424_ == MoverType.SHULKER_BOX) {
            this.m_33460_();
        } else {
            super.m_6478_(p_33424_, p_33425_);
        }
    }

    @Override
    public Vec3 m_20184_() {
        return Vec3.f_82478_;
    }

    @Override
    public void m_20256_(Vec3 p_149804_) {
    }

    @Override
    public void m_6034_(double p_33449_, double p_33450_, double p_33451_) {
        BlockPos $$3 = this.m_20183_();
        if (this.m_20159_()) {
            super.m_6034_(p_33449_, p_33450_, p_33451_);
        } else {
            super.m_6034_((double)Mth.m_14107_(p_33449_) + 0.5, Mth.m_14107_(p_33450_ + 0.5), (double)Mth.m_14107_(p_33451_) + 0.5);
        }
        if (this.f_19797_ == 0) {
            return;
        }
        BlockPos $$4 = this.m_20183_();
        if (!$$4.equals($$3)) {
            this.f_19804_.m_135381_(f_33401_, (byte)0);
            this.f_19812_ = true;
            if (this.f_19853_.f_46443_ && !this.m_20159_() && !$$4.equals(this.f_149764_)) {
                this.f_149764_ = $$3;
                this.f_33399_ = 6;
                this.f_19790_ = this.m_20185_();
                this.f_19791_ = this.m_20186_();
                this.f_19792_ = this.m_20189_();
            }
        }
    }

    @Nullable
    protected Direction m_149810_(BlockPos p_149811_) {
        for (Direction $$1 : Direction.values()) {
            if (!this.m_149785_(p_149811_, $$1)) continue;
            return $$1;
        }
        return null;
    }

    boolean m_149785_(BlockPos p_149786_, Direction p_149787_) {
        if (this.m_149812_(p_149786_)) {
            return false;
        }
        Direction $$2 = p_149787_.m_122424_();
        if (!this.f_19853_.m_46578_(p_149786_.m_121945_(p_149787_), this, $$2)) {
            return false;
        }
        AABB $$3 = Shulker.m_149790_($$2, 1.0f).m_82338_(p_149786_).m_82406_(1.0E-6);
        return this.f_19853_.m_45756_(this, $$3);
    }

    private boolean m_149812_(BlockPos p_149813_) {
        BlockState $$1 = this.f_19853_.m_8055_(p_149813_);
        if ($$1.m_60795_()) {
            return false;
        }
        boolean $$2 = $$1.m_60713_(Blocks.f_50110_) && p_149813_.equals(this.m_20183_());
        return !$$2;
    }

    protected boolean m_33460_() {
        if (this.m_21525_() || !this.m_6084_()) {
            return false;
        }
        BlockPos $$0 = this.m_20183_();
        for (int $$1 = 0; $$1 < 5; ++$$1) {
            Direction $$3;
            BlockPos $$2 = $$0.m_7918_(Mth.m_216287_(this.f_19796_, -8, 8), Mth.m_216287_(this.f_19796_, -8, 8), Mth.m_216287_(this.f_19796_, -8, 8));
            if ($$2.m_123342_() <= this.f_19853_.m_141937_() || !this.f_19853_.m_46859_($$2) || !this.f_19853_.m_6857_().m_61937_($$2) || !this.f_19853_.m_45756_(this, new AABB($$2).m_82406_(1.0E-6)) || ($$3 = this.m_149810_($$2)) == null) continue;
            this.m_19877_();
            this.m_149788_($$3);
            this.m_5496_(SoundEvents.f_12418_, 1.0f, 1.0f);
            this.m_6034_((double)$$2.m_123341_() + 0.5, $$2.m_123342_(), (double)$$2.m_123343_() + 0.5);
            this.f_19853_.m_220407_(GameEvent.f_238175_, $$0, GameEvent.Context.m_223717_(this));
            this.f_19804_.m_135381_(f_33401_, (byte)0);
            this.m_6710_(null);
            return true;
        }
        return false;
    }

    @Override
    public void m_6453_(double p_33411_, double p_33412_, double p_33413_, float p_33414_, float p_33415_, int p_33416_, boolean p_33417_) {
        this.f_20903_ = 0;
        this.m_6034_(p_33411_, p_33412_, p_33413_);
        this.m_19915_(p_33414_, p_33415_);
    }

    @Override
    public boolean m_6469_(DamageSource p_33421_, float p_33422_) {
        Entity $$2;
        if (this.m_33468_() && ($$2 = p_33421_.m_7640_()) instanceof AbstractArrow) {
            return false;
        }
        if (super.m_6469_(p_33421_, p_33422_)) {
            Entity $$3;
            if ((double)this.m_21223_() < (double)this.m_21233_() * 0.5 && this.f_19796_.m_188503_(4) == 0) {
                this.m_33460_();
            } else if (p_33421_.m_19360_() && ($$3 = p_33421_.m_7640_()) != null && $$3.m_6095_() == EntityType.f_20522_) {
                this.m_149805_();
            }
            return true;
        }
        return false;
    }

    private boolean m_33468_() {
        return this.m_33463_() == 0;
    }

    private void m_149805_() {
        Vec3 $$0 = this.m_20182_();
        AABB $$1 = this.m_20191_();
        if (this.m_33468_() || !this.m_33460_()) {
            return;
        }
        int $$2 = this.f_19853_.m_142425_(EntityType.f_20521_, $$1.m_82400_(8.0), Entity::m_6084_).size();
        float $$3 = (float)($$2 - 1) / 5.0f;
        if (this.f_19853_.f_46441_.m_188501_() < $$3) {
            return;
        }
        Shulker $$4 = EntityType.f_20521_.m_20615_(this.f_19853_);
        DyeColor $$5 = this.m_33467_();
        if ($$5 != null) {
            $$4.m_149777_($$5);
        }
        $$4.m_20219_($$0);
        this.f_19853_.m_7967_($$4);
    }

    @Override
    public boolean m_5829_() {
        return this.m_6084_();
    }

    public Direction m_33461_() {
        return this.f_19804_.m_135370_(f_33392_);
    }

    private void m_149788_(Direction p_149789_) {
        this.f_19804_.m_135381_(f_33392_, p_149789_);
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_33434_) {
        if (f_33392_.equals(p_33434_)) {
            this.m_20011_(this.m_142242_());
        }
        super.m_7350_(p_33434_);
    }

    private int m_33463_() {
        return this.f_19804_.m_135370_(f_33401_).byteValue();
    }

    void m_33418_(int p_33419_) {
        if (!this.f_19853_.f_46443_) {
            this.m_21051_(Attributes.f_22284_).m_22130_(f_33395_);
            if (p_33419_ == 0) {
                this.m_21051_(Attributes.f_22284_).m_22125_(f_33395_);
                this.m_5496_(SoundEvents.f_12412_, 1.0f, 1.0f);
                this.m_146850_(GameEvent.f_157802_);
            } else {
                this.m_5496_(SoundEvents.f_12416_, 1.0f, 1.0f);
                this.m_146850_(GameEvent.f_157803_);
            }
        }
        this.f_19804_.m_135381_(f_33401_, (byte)p_33419_);
    }

    public float m_33480_(float p_33481_) {
        return Mth.m_14179_(p_33481_, this.f_33396_, this.f_33397_);
    }

    @Override
    protected float m_6431_(Pose p_33438_, EntityDimensions p_33439_) {
        return 0.5f;
    }

    @Override
    public void m_141965_(ClientboundAddEntityPacket p_219067_) {
        super.m_141965_(p_219067_);
        this.f_20883_ = 0.0f;
        this.f_20884_ = 0.0f;
    }

    @Override
    public int m_8132_() {
        return 180;
    }

    @Override
    public int m_8085_() {
        return 180;
    }

    @Override
    public void m_7334_(Entity p_33474_) {
    }

    @Override
    public float m_6143_() {
        return 0.0f;
    }

    public Optional<Vec3> m_149766_(float p_149767_) {
        if (this.f_149764_ == null || this.f_33399_ <= 0) {
            return Optional.empty();
        }
        double $$1 = (double)((float)this.f_33399_ - p_149767_) / 6.0;
        $$1 *= $$1;
        BlockPos $$2 = this.m_20183_();
        double $$3 = (double)($$2.m_123341_() - this.f_149764_.m_123341_()) * $$1;
        double $$4 = (double)($$2.m_123342_() - this.f_149764_.m_123342_()) * $$1;
        double $$5 = (double)($$2.m_123343_() - this.f_149764_.m_123343_()) * $$1;
        return Optional.of(new Vec3(-$$3, -$$4, -$$5));
    }

    private void m_149777_(DyeColor p_149778_) {
        this.f_19804_.m_135381_(f_33393_, (byte)p_149778_.m_41060_());
    }

    @Nullable
    public DyeColor m_33467_() {
        byte $$0 = this.f_19804_.m_135370_(f_33393_);
        if ($$0 == 16 || $$0 > 15) {
            return null;
        }
        return DyeColor.m_41053_($$0);
    }

    class ShulkerLookControl
    extends LookControl {
        public ShulkerLookControl(Mob p_149820_) {
            super(p_149820_);
        }

        @Override
        protected void m_142586_() {
        }

        @Override
        protected Optional<Float> m_180896_() {
            Direction $$0 = Shulker.this.m_33461_().m_122424_();
            Vector3f $$1 = f_149763_.m_122281_();
            $$1.m_122251_($$0.m_122406_());
            Vec3i $$2 = $$0.m_122436_();
            Vector3f $$3 = new Vector3f($$2.m_123341_(), $$2.m_123342_(), $$2.m_123343_());
            $$3.m_122279_($$1);
            double $$4 = this.f_24941_ - this.f_24937_.m_20185_();
            double $$5 = this.f_24942_ - this.f_24937_.m_20188_();
            double $$6 = this.f_24943_ - this.f_24937_.m_20189_();
            Vector3f $$7 = new Vector3f((float)$$4, (float)$$5, (float)$$6);
            float $$8 = $$3.m_122276_($$7);
            float $$9 = $$1.m_122276_($$7);
            return Math.abs($$8) > 1.0E-5f || Math.abs($$9) > 1.0E-5f ? Optional.of(Float.valueOf((float)(Mth.m_14136_(-$$8, $$9) * 57.2957763671875))) : Optional.empty();
        }

        @Override
        protected Optional<Float> m_180897_() {
            return Optional.of(Float.valueOf(0.0f));
        }
    }

    class ShulkerAttackGoal
    extends Goal {
        private int f_33483_;

        public ShulkerAttackGoal() {
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean m_8036_() {
            LivingEntity $$0 = Shulker.this.m_5448_();
            if ($$0 == null || !$$0.m_6084_()) {
                return false;
            }
            return Shulker.this.f_19853_.m_46791_() != Difficulty.PEACEFUL;
        }

        @Override
        public void m_8056_() {
            this.f_33483_ = 20;
            Shulker.this.m_33418_(100);
        }

        @Override
        public void m_8041_() {
            Shulker.this.m_33418_(0);
        }

        @Override
        public boolean m_183429_() {
            return true;
        }

        @Override
        public void m_8037_() {
            if (Shulker.this.f_19853_.m_46791_() == Difficulty.PEACEFUL) {
                return;
            }
            --this.f_33483_;
            LivingEntity $$0 = Shulker.this.m_5448_();
            if ($$0 == null) {
                return;
            }
            Shulker.this.m_21563_().m_24960_($$0, 180.0f, 180.0f);
            double $$1 = Shulker.this.m_20280_($$0);
            if ($$1 < 400.0) {
                if (this.f_33483_ <= 0) {
                    this.f_33483_ = 20 + Shulker.this.f_19796_.m_188503_(10) * 20 / 2;
                    Shulker.this.f_19853_.m_7967_(new ShulkerBullet(Shulker.this.f_19853_, Shulker.this, $$0, Shulker.this.m_33461_().m_122434_()));
                    Shulker.this.m_5496_(SoundEvents.f_12417_, 2.0f, (Shulker.this.f_19796_.m_188501_() - Shulker.this.f_19796_.m_188501_()) * 0.2f + 1.0f);
                }
            } else {
                Shulker.this.m_6710_(null);
            }
            super.m_8037_();
        }
    }

    class ShulkerPeekGoal
    extends Goal {
        private int f_33510_;

        ShulkerPeekGoal() {
        }

        @Override
        public boolean m_8036_() {
            return Shulker.this.m_5448_() == null && Shulker.this.f_19796_.m_188503_(ShulkerPeekGoal.m_186073_(40)) == 0 && Shulker.this.m_149785_(Shulker.this.m_20183_(), Shulker.this.m_33461_());
        }

        @Override
        public boolean m_8045_() {
            return Shulker.this.m_5448_() == null && this.f_33510_ > 0;
        }

        @Override
        public void m_8056_() {
            this.f_33510_ = this.m_183277_(20 * (1 + Shulker.this.f_19796_.m_188503_(3)));
            Shulker.this.m_33418_(30);
        }

        @Override
        public void m_8041_() {
            if (Shulker.this.m_5448_() == null) {
                Shulker.this.m_33418_(0);
            }
        }

        @Override
        public void m_8037_() {
            --this.f_33510_;
        }
    }

    class ShulkerNearestAttackGoal
    extends NearestAttackableTargetGoal<Player> {
        public ShulkerNearestAttackGoal(Shulker p_33505_) {
            super((Mob)p_33505_, Player.class, true);
        }

        @Override
        public boolean m_8036_() {
            if (Shulker.this.f_19853_.m_46791_() == Difficulty.PEACEFUL) {
                return false;
            }
            return super.m_8036_();
        }

        @Override
        protected AABB m_7255_(double p_33508_) {
            Direction $$1 = ((Shulker)this.f_26135_).m_33461_();
            if ($$1.m_122434_() == Direction.Axis.X) {
                return this.f_26135_.m_20191_().m_82377_(4.0, p_33508_, p_33508_);
            }
            if ($$1.m_122434_() == Direction.Axis.Z) {
                return this.f_26135_.m_20191_().m_82377_(p_33508_, p_33508_, 4.0);
            }
            return this.f_26135_.m_20191_().m_82377_(p_33508_, 4.0, p_33508_);
        }
    }

    static class ShulkerDefenseAttackGoal
    extends NearestAttackableTargetGoal<LivingEntity> {
        public ShulkerDefenseAttackGoal(Shulker p_33496_) {
            super(p_33496_, LivingEntity.class, 10, true, false, p_33501_ -> p_33501_ instanceof Enemy);
        }

        @Override
        public boolean m_8036_() {
            if (this.f_26135_.m_5647_() == null) {
                return false;
            }
            return super.m_8036_();
        }

        @Override
        protected AABB m_7255_(double p_33499_) {
            Direction $$1 = ((Shulker)this.f_26135_).m_33461_();
            if ($$1.m_122434_() == Direction.Axis.X) {
                return this.f_26135_.m_20191_().m_82377_(4.0, p_33499_, p_33499_);
            }
            if ($$1.m_122434_() == Direction.Axis.Z) {
                return this.f_26135_.m_20191_().m_82377_(p_33499_, p_33499_, 4.0);
            }
            return this.f_26135_.m_20191_().m_82377_(p_33499_, 4.0, p_33499_);
        }
    }

    static class ShulkerBodyRotationControl
    extends BodyRotationControl {
        public ShulkerBodyRotationControl(Mob p_149816_) {
            super(p_149816_);
        }

        @Override
        public void m_8121_() {
        }
    }
}

