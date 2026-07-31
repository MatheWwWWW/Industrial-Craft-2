/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class EnderMan
extends Monster
implements NeutralMob {
    private static final UUID f_32472_ = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier f_32481_ = new AttributeModifier(f_32472_, "Attacking speed boost", (double)0.15f, AttributeModifier.Operation.ADDITION);
    private static final int f_149694_ = 400;
    private static final int f_149693_ = 600;
    private static final EntityDataAccessor<Optional<BlockState>> f_32482_ = SynchedEntityData.m_135353_(EnderMan.class, EntityDataSerializers.f_135034_);
    private static final EntityDataAccessor<Boolean> f_32473_ = SynchedEntityData.m_135353_(EnderMan.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> f_32474_ = SynchedEntityData.m_135353_(EnderMan.class, EntityDataSerializers.f_135035_);
    private int f_32476_ = Integer.MIN_VALUE;
    private int f_32477_;
    private static final UniformInt f_32478_ = TimeUtil.m_145020_(20, 39);
    private int f_32479_;
    @Nullable
    private UUID f_32480_;

    public EnderMan(EntityType<? extends EnderMan> p_32485_, Level p_32486_) {
        super((EntityType<? extends Monster>)p_32485_, p_32486_);
        this.f_19793_ = 1.0f;
        this.m_21441_(BlockPathTypes.WATER, -1.0f);
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(0, new FloatGoal(this));
        this.f_21345_.m_25352_(1, new EndermanFreezeWhenLookedAt(this));
        this.f_21345_.m_25352_(2, new MeleeAttackGoal(this, 1.0, false));
        this.f_21345_.m_25352_(7, new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0, 0.0f));
        this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
        this.f_21345_.m_25352_(10, new EndermanLeaveBlockGoal(this));
        this.f_21345_.m_25352_(11, new EndermanTakeBlockGoal(this));
        this.f_21346_.m_25352_(1, new EndermanLookForPlayerGoal(this, this::m_21674_));
        this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<Endermite>((Mob)this, Endermite.class, true, false));
        this.f_21346_.m_25352_(4, new ResetUniversalAngerTargetGoal<EnderMan>(this, false));
    }

    public static AttributeSupplier.Builder m_32541_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22276_, 40.0).m_22268_(Attributes.f_22279_, 0.3f).m_22268_(Attributes.f_22281_, 7.0).m_22268_(Attributes.f_22277_, 64.0);
    }

    @Override
    public void m_6710_(@Nullable LivingEntity p_32537_) {
        super.m_6710_(p_32537_);
        AttributeInstance $$1 = this.m_21051_(Attributes.f_22279_);
        if (p_32537_ == null) {
            this.f_32477_ = 0;
            this.f_19804_.m_135381_(f_32473_, false);
            this.f_19804_.m_135381_(f_32474_, false);
            $$1.m_22130_(f_32481_);
        } else {
            this.f_32477_ = this.f_19797_;
            this.f_19804_.m_135381_(f_32473_, true);
            if (!$$1.m_22109_(f_32481_)) {
                $$1.m_22118_(f_32481_);
            }
        }
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_32482_, Optional.empty());
        this.f_19804_.m_135372_(f_32473_, false);
        this.f_19804_.m_135372_(f_32474_, false);
    }

    @Override
    public void m_6825_() {
        this.m_7870_(f_32478_.m_214085_(this.f_19796_));
    }

    @Override
    public void m_7870_(int p_32515_) {
        this.f_32479_ = p_32515_;
    }

    @Override
    public int m_6784_() {
        return this.f_32479_;
    }

    @Override
    public void m_6925_(@Nullable UUID p_32509_) {
        this.f_32480_ = p_32509_;
    }

    @Override
    @Nullable
    public UUID m_6120_() {
        return this.f_32480_;
    }

    public void m_32528_() {
        if (this.f_19797_ >= this.f_32476_ + 400) {
            this.f_32476_ = this.f_19797_;
            if (!this.m_20067_()) {
                this.f_19853_.m_7785_(this.m_20185_(), this.m_20188_(), this.m_20189_(), SoundEvents.f_11851_, this.m_5720_(), 2.5f, 1.0f, false);
            }
        }
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_32513_) {
        if (f_32473_.equals(p_32513_) && this.m_32532_() && this.f_19853_.f_46443_) {
            this.m_32528_();
        }
        super.m_7350_(p_32513_);
    }

    @Override
    public void m_7380_(CompoundTag p_32520_) {
        super.m_7380_(p_32520_);
        BlockState $$1 = this.m_32530_();
        if ($$1 != null) {
            p_32520_.m_128365_("carriedBlockState", NbtUtils.m_129202_($$1));
        }
        this.m_21678_(p_32520_);
    }

    @Override
    public void m_7378_(CompoundTag p_32511_) {
        super.m_7378_(p_32511_);
        BlockState $$1 = null;
        if (p_32511_.m_128425_("carriedBlockState", 10) && ($$1 = NbtUtils.m_129241_(p_32511_.m_128469_("carriedBlockState"))).m_60795_()) {
            $$1 = null;
        }
        this.m_32521_($$1);
        this.m_147285_(this.f_19853_, p_32511_);
    }

    boolean m_32534_(Player p_32535_) {
        ItemStack $$1 = p_32535_.m_150109_().f_35975_.get(3);
        if ($$1.m_150930_(Blocks.f_50143_.m_5456_())) {
            return false;
        }
        Vec3 $$2 = p_32535_.m_20252_(1.0f).m_82541_();
        Vec3 $$3 = new Vec3(this.m_20185_() - p_32535_.m_20185_(), this.m_20188_() - p_32535_.m_20188_(), this.m_20189_() - p_32535_.m_20189_());
        double $$4 = $$3.m_82553_();
        double $$5 = $$2.m_82526_($$3 = $$3.m_82541_());
        if ($$5 > 1.0 - 0.025 / $$4) {
            return p_32535_.m_142582_(this);
        }
        return false;
    }

    @Override
    protected float m_6431_(Pose p_32517_, EntityDimensions p_32518_) {
        return 2.55f;
    }

    @Override
    public void m_8107_() {
        if (this.f_19853_.f_46443_) {
            for (int $$0 = 0; $$0 < 2; ++$$0) {
                this.f_19853_.m_7106_(ParticleTypes.f_123760_, this.m_20208_(0.5), this.m_20187_() - 0.25, this.m_20262_(0.5), (this.f_19796_.m_188500_() - 0.5) * 2.0, -this.f_19796_.m_188500_(), (this.f_19796_.m_188500_() - 0.5) * 2.0);
            }
        }
        this.f_20899_ = false;
        if (!this.f_19853_.f_46443_) {
            this.m_21666_((ServerLevel)this.f_19853_, true);
        }
        super.m_8107_();
    }

    @Override
    public boolean m_6126_() {
        return true;
    }

    @Override
    protected void m_8024_() {
        float $$0;
        if (this.f_19853_.m_46461_() && this.f_19797_ >= this.f_32477_ + 600 && ($$0 = this.m_213856_()) > 0.5f && this.f_19853_.m_45527_(this.m_20183_()) && this.f_19796_.m_188501_() * 30.0f < ($$0 - 0.4f) * 2.0f) {
            this.m_6710_(null);
            this.m_32529_();
        }
        super.m_8024_();
    }

    protected boolean m_32529_() {
        if (this.f_19853_.m_5776_() || !this.m_6084_()) {
            return false;
        }
        double $$0 = this.m_20185_() + (this.f_19796_.m_188500_() - 0.5) * 64.0;
        double $$1 = this.m_20186_() + (double)(this.f_19796_.m_188503_(64) - 32);
        double $$2 = this.m_20189_() + (this.f_19796_.m_188500_() - 0.5) * 64.0;
        return this.m_32543_($$0, $$1, $$2);
    }

    boolean m_32500_(Entity p_32501_) {
        Vec3 $$1 = new Vec3(this.m_20185_() - p_32501_.m_20185_(), this.m_20227_(0.5) - p_32501_.m_20188_(), this.m_20189_() - p_32501_.m_20189_());
        $$1 = $$1.m_82541_();
        double $$2 = 16.0;
        double $$3 = this.m_20185_() + (this.f_19796_.m_188500_() - 0.5) * 8.0 - $$1.f_82479_ * 16.0;
        double $$4 = this.m_20186_() + (double)(this.f_19796_.m_188503_(16) - 8) - $$1.f_82480_ * 16.0;
        double $$5 = this.m_20189_() + (this.f_19796_.m_188500_() - 0.5) * 8.0 - $$1.f_82481_ * 16.0;
        return this.m_32543_($$3, $$4, $$5);
    }

    private boolean m_32543_(double p_32544_, double p_32545_, double p_32546_) {
        BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos(p_32544_, p_32545_, p_32546_);
        while ($$3.m_123342_() > this.f_19853_.m_141937_() && !this.f_19853_.m_8055_($$3).m_60767_().m_76334_()) {
            $$3.m_122173_(Direction.DOWN);
        }
        BlockState $$4 = this.f_19853_.m_8055_($$3);
        boolean $$5 = $$4.m_60767_().m_76334_();
        boolean $$6 = $$4.m_60819_().m_205070_(FluidTags.f_13131_);
        if (!$$5 || $$6) {
            return false;
        }
        Vec3 $$7 = this.m_20182_();
        boolean $$8 = this.m_20984_(p_32544_, p_32545_, p_32546_, true);
        if ($$8) {
            this.f_19853_.m_214171_(GameEvent.f_238175_, $$7, GameEvent.Context.m_223717_(this));
            if (!this.m_20067_()) {
                this.f_19853_.m_6263_(null, this.f_19854_, this.f_19855_, this.f_19856_, SoundEvents.f_11852_, this.m_5720_(), 1.0f, 1.0f);
                this.m_5496_(SoundEvents.f_11852_, 1.0f, 1.0f);
            }
        }
        return $$8;
    }

    @Override
    protected SoundEvent m_7515_() {
        return this.m_32531_() ? SoundEvents.f_11850_ : SoundEvents.f_11899_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_32527_) {
        return SoundEvents.f_11849_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_11900_;
    }

    @Override
    protected void m_7472_(DamageSource p_32497_, int p_32498_, boolean p_32499_) {
        super.m_7472_(p_32497_, p_32498_, p_32499_);
        BlockState $$3 = this.m_32530_();
        if ($$3 != null) {
            this.m_19998_($$3.m_60734_());
        }
    }

    public void m_32521_(@Nullable BlockState p_32522_) {
        this.f_19804_.m_135381_(f_32482_, Optional.ofNullable(p_32522_));
    }

    @Nullable
    public BlockState m_32530_() {
        return this.f_19804_.m_135370_(f_32482_).orElse(null);
    }

    @Override
    public boolean m_6469_(DamageSource p_32494_, float p_32495_) {
        if (this.m_6673_(p_32494_)) {
            return false;
        }
        if (p_32494_ instanceof IndirectEntityDamageSource) {
            boolean $$4;
            Entity $$2 = p_32494_.m_7640_();
            if ($$2 instanceof ThrownPotion) {
                boolean $$3 = this.m_186272_(p_32494_, (ThrownPotion)$$2, p_32495_);
            } else {
                $$4 = false;
            }
            for (int $$5 = 0; $$5 < 64; ++$$5) {
                if (!this.m_32529_()) continue;
                return true;
            }
            return $$4;
        }
        boolean $$6 = super.m_6469_(p_32494_, p_32495_);
        if (!this.f_19853_.m_5776_() && !(p_32494_.m_7639_() instanceof LivingEntity) && this.f_19796_.m_188503_(10) != 0) {
            this.m_32529_();
        }
        return $$6;
    }

    private boolean m_186272_(DamageSource p_186273_, ThrownPotion p_186274_, float p_186275_) {
        boolean $$6;
        ItemStack $$3 = p_186274_.m_7846_();
        Potion $$4 = PotionUtils.m_43579_($$3);
        List<MobEffectInstance> $$5 = PotionUtils.m_43547_($$3);
        boolean bl = $$6 = $$4 == Potions.f_43599_ && $$5.isEmpty();
        if ($$6) {
            return super.m_6469_(p_186273_, p_186275_);
        }
        return false;
    }

    public boolean m_32531_() {
        return this.f_19804_.m_135370_(f_32473_);
    }

    public boolean m_32532_() {
        return this.f_19804_.m_135370_(f_32474_);
    }

    public void m_32533_() {
        this.f_19804_.m_135381_(f_32474_, true);
    }

    @Override
    public boolean m_8023_() {
        return super.m_8023_() || this.m_32530_() != null;
    }

    static class EndermanFreezeWhenLookedAt
    extends Goal {
        private final EnderMan f_32547_;
        @Nullable
        private LivingEntity f_32548_;

        public EndermanFreezeWhenLookedAt(EnderMan p_32550_) {
            this.f_32547_ = p_32550_;
            this.m_7021_(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean m_8036_() {
            this.f_32548_ = this.f_32547_.m_5448_();
            if (!(this.f_32548_ instanceof Player)) {
                return false;
            }
            double $$0 = this.f_32548_.m_20280_(this.f_32547_);
            if ($$0 > 256.0) {
                return false;
            }
            return this.f_32547_.m_32534_((Player)this.f_32548_);
        }

        @Override
        public void m_8056_() {
            this.f_32547_.m_21573_().m_26573_();
        }

        @Override
        public void m_8037_() {
            this.f_32547_.m_21563_().m_24946_(this.f_32548_.m_20185_(), this.f_32548_.m_20188_(), this.f_32548_.m_20189_());
        }
    }

    static class EndermanLeaveBlockGoal
    extends Goal {
        private final EnderMan f_32554_;

        public EndermanLeaveBlockGoal(EnderMan p_32556_) {
            this.f_32554_ = p_32556_;
        }

        @Override
        public boolean m_8036_() {
            if (this.f_32554_.m_32530_() == null) {
                return false;
            }
            if (!this.f_32554_.f_19853_.m_46469_().m_46207_(GameRules.f_46132_)) {
                return false;
            }
            return this.f_32554_.m_217043_().m_188503_(EndermanLeaveBlockGoal.m_186073_(2000)) == 0;
        }

        @Override
        public void m_8037_() {
            RandomSource $$0 = this.f_32554_.m_217043_();
            Level $$1 = this.f_32554_.f_19853_;
            int $$2 = Mth.m_14107_(this.f_32554_.m_20185_() - 1.0 + $$0.m_188500_() * 2.0);
            int $$3 = Mth.m_14107_(this.f_32554_.m_20186_() + $$0.m_188500_() * 2.0);
            int $$4 = Mth.m_14107_(this.f_32554_.m_20189_() - 1.0 + $$0.m_188500_() * 2.0);
            BlockPos $$5 = new BlockPos($$2, $$3, $$4);
            BlockState $$6 = $$1.m_8055_($$5);
            BlockPos $$7 = $$5.m_7495_();
            BlockState $$8 = $$1.m_8055_($$7);
            BlockState $$9 = this.f_32554_.m_32530_();
            if ($$9 == null) {
                return;
            }
            if (this.m_32558_($$1, $$5, $$9 = Block.m_49931_($$9, this.f_32554_.f_19853_, $$5), $$6, $$8, $$7)) {
                $$1.m_7731_($$5, $$9, 3);
                $$1.m_220407_(GameEvent.f_157797_, $$5, GameEvent.Context.m_223719_(this.f_32554_, $$9));
                this.f_32554_.m_32521_(null);
            }
        }

        private boolean m_32558_(Level p_32559_, BlockPos p_32560_, BlockState p_32561_, BlockState p_32562_, BlockState p_32563_, BlockPos p_32564_) {
            return p_32562_.m_60795_() && !p_32563_.m_60795_() && !p_32563_.m_60713_(Blocks.f_50752_) && p_32563_.m_60838_(p_32559_, p_32564_) && p_32561_.m_60710_(p_32559_, p_32560_) && p_32559_.m_45933_(this.f_32554_, AABB.m_82333_(Vec3.m_82528_(p_32560_))).isEmpty();
        }
    }

    static class EndermanTakeBlockGoal
    extends Goal {
        private final EnderMan f_32583_;

        public EndermanTakeBlockGoal(EnderMan p_32585_) {
            this.f_32583_ = p_32585_;
        }

        @Override
        public boolean m_8036_() {
            if (this.f_32583_.m_32530_() != null) {
                return false;
            }
            if (!this.f_32583_.f_19853_.m_46469_().m_46207_(GameRules.f_46132_)) {
                return false;
            }
            return this.f_32583_.m_217043_().m_188503_(EndermanTakeBlockGoal.m_186073_(20)) == 0;
        }

        @Override
        public void m_8037_() {
            RandomSource $$0 = this.f_32583_.m_217043_();
            Level $$1 = this.f_32583_.f_19853_;
            int $$2 = Mth.m_14107_(this.f_32583_.m_20185_() - 2.0 + $$0.m_188500_() * 4.0);
            int $$3 = Mth.m_14107_(this.f_32583_.m_20186_() + $$0.m_188500_() * 3.0);
            int $$4 = Mth.m_14107_(this.f_32583_.m_20189_() - 2.0 + $$0.m_188500_() * 4.0);
            BlockPos $$5 = new BlockPos($$2, $$3, $$4);
            BlockState $$6 = $$1.m_8055_($$5);
            Vec3 $$7 = new Vec3((double)this.f_32583_.m_146903_() + 0.5, (double)$$3 + 0.5, (double)this.f_32583_.m_146907_() + 0.5);
            Vec3 $$8 = new Vec3((double)$$2 + 0.5, (double)$$3 + 0.5, (double)$$4 + 0.5);
            BlockHitResult $$9 = $$1.m_45547_(new ClipContext($$7, $$8, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, this.f_32583_));
            boolean $$10 = $$9.m_82425_().equals($$5);
            if ($$6.m_204336_(BlockTags.f_13046_) && $$10) {
                $$1.m_7471_($$5, false);
                $$1.m_220407_(GameEvent.f_157794_, $$5, GameEvent.Context.m_223719_(this.f_32583_, $$6));
                this.f_32583_.m_32521_($$6.m_60734_().m_49966_());
            }
        }
    }

    static class EndermanLookForPlayerGoal
    extends NearestAttackableTargetGoal<Player> {
        private final EnderMan f_32566_;
        @Nullable
        private Player f_32567_;
        private int f_32568_;
        private int f_32569_;
        private final TargetingConditions f_32570_;
        private final TargetingConditions f_32571_ = TargetingConditions.m_148352_().m_148355_();

        public EndermanLookForPlayerGoal(EnderMan p_32573_, @Nullable Predicate<LivingEntity> p_32574_) {
            super(p_32573_, Player.class, 10, false, false, p_32574_);
            this.f_32566_ = p_32573_;
            this.f_32570_ = TargetingConditions.m_148352_().m_26883_(this.m_7623_()).m_26888_(p_32578_ -> p_32573_.m_32534_((Player)p_32578_));
        }

        @Override
        public boolean m_8036_() {
            this.f_32567_ = this.f_32566_.f_19853_.m_45946_(this.f_32570_, this.f_32566_);
            return this.f_32567_ != null;
        }

        @Override
        public void m_8056_() {
            this.f_32568_ = this.m_183277_(5);
            this.f_32569_ = 0;
            this.f_32566_.m_32533_();
        }

        @Override
        public void m_8041_() {
            this.f_32567_ = null;
            super.m_8041_();
        }

        @Override
        public boolean m_8045_() {
            if (this.f_32567_ != null) {
                if (!this.f_32566_.m_32534_(this.f_32567_)) {
                    return false;
                }
                this.f_32566_.m_21391_(this.f_32567_, 10.0f, 10.0f);
                return true;
            }
            if (this.f_26050_ != null && this.f_32571_.m_26885_(this.f_32566_, this.f_26050_)) {
                return true;
            }
            return super.m_8045_();
        }

        @Override
        public void m_8037_() {
            if (this.f_32566_.m_5448_() == null) {
                super.m_26070_(null);
            }
            if (this.f_32567_ != null) {
                if (--this.f_32568_ <= 0) {
                    this.f_26050_ = this.f_32567_;
                    this.f_32567_ = null;
                    super.m_8056_();
                }
            } else {
                if (this.f_26050_ != null && !this.f_32566_.m_20159_()) {
                    if (this.f_32566_.m_32534_((Player)this.f_26050_)) {
                        if (this.f_26050_.m_20280_(this.f_32566_) < 16.0) {
                            this.f_32566_.m_32529_();
                        }
                        this.f_32569_ = 0;
                    } else if (this.f_26050_.m_20280_(this.f_32566_) > 256.0 && this.f_32569_++ >= this.m_183277_(30) && this.f_32566_.m_32500_(this.f_26050_)) {
                        this.f_32569_ = 0;
                    }
                }
                super.m_8037_();
            }
        }
    }
}

