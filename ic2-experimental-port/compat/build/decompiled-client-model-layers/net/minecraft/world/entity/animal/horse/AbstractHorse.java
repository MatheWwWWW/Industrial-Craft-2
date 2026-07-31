/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal.horse;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RunAroundLikeCrazyGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractHorse
extends Animal
implements ContainerListener,
HasCustomInventoryScreen,
PlayerRideableJumping,
Saddleable {
    public static final int f_149486_ = 400;
    public static final int f_149487_ = 499;
    public static final int f_149488_ = 500;
    private static final Predicate<LivingEntity> f_30525_ = p_30636_ -> p_30636_ instanceof AbstractHorse && ((AbstractHorse)p_30636_).m_30623_();
    private static final TargetingConditions f_30526_ = TargetingConditions.m_148353_().m_26883_(16.0).m_148355_().m_26888_(f_30525_);
    private static final Ingredient f_30527_ = Ingredient.m_43929_(Items.f_42405_, Items.f_42501_, Blocks.f_50335_.m_5456_(), Items.f_42410_, Items.f_42677_, Items.f_42436_, Items.f_42437_);
    private static final EntityDataAccessor<Byte> f_30528_ = SynchedEntityData.m_135353_(AbstractHorse.class, EntityDataSerializers.f_135027_);
    private static final EntityDataAccessor<Optional<UUID>> f_30506_ = SynchedEntityData.m_135353_(AbstractHorse.class, EntityDataSerializers.f_135041_);
    private static final int f_149492_ = 2;
    private static final int f_149493_ = 4;
    private static final int f_149494_ = 8;
    private static final int f_149495_ = 16;
    private static final int f_149496_ = 32;
    private static final int f_149497_ = 64;
    public static final int f_149489_ = 0;
    public static final int f_149490_ = 1;
    public static final int f_149491_ = 2;
    private int f_30507_;
    private int f_30508_;
    private int f_30509_;
    public int f_30517_;
    public int f_30518_;
    protected boolean f_30519_;
    protected SimpleContainer f_30520_;
    protected int f_30521_;
    protected float f_30522_;
    private boolean f_30510_;
    private float f_30511_;
    private float f_30512_;
    private float f_30513_;
    private float f_30514_;
    private float f_30515_;
    private float f_30516_;
    protected boolean f_30523_ = true;
    protected int f_30524_;

    protected AbstractHorse(EntityType<? extends AbstractHorse> p_30531_, Level p_30532_) {
        super((EntityType<? extends Animal>)p_30531_, p_30532_);
        this.f_19793_ = 1.0f;
        this.m_30625_();
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(1, new PanicGoal(this, 1.2));
        this.f_21345_.m_25352_(1, new RunAroundLikeCrazyGoal(this, 1.2));
        this.f_21345_.m_25352_(2, new BreedGoal(this, 1.0, AbstractHorse.class));
        this.f_21345_.m_25352_(4, new FollowParentGoal(this, 1.0));
        this.f_21345_.m_25352_(6, new WaterAvoidingRandomStrollGoal(this, 0.7));
        this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
        this.m_7509_();
    }

    protected void m_7509_() {
        this.f_21345_.m_25352_(0, new FloatGoal(this));
        this.f_21345_.m_25352_(3, new TemptGoal(this, 1.25, Ingredient.m_43929_(Items.f_42677_, Items.f_42436_, Items.f_42437_), false));
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_30528_, (byte)0);
        this.f_19804_.m_135372_(f_30506_, Optional.empty());
    }

    protected boolean m_30647_(int p_30648_) {
        return (this.f_19804_.m_135370_(f_30528_) & p_30648_) != 0;
    }

    protected void m_30597_(int p_30598_, boolean p_30599_) {
        byte $$2 = this.f_19804_.m_135370_(f_30528_);
        if (p_30599_) {
            this.f_19804_.m_135381_(f_30528_, (byte)($$2 | p_30598_));
        } else {
            this.f_19804_.m_135381_(f_30528_, (byte)($$2 & ~p_30598_));
        }
    }

    public boolean m_30614_() {
        return this.m_30647_(2);
    }

    @Nullable
    public UUID m_30615_() {
        return this.f_19804_.m_135370_(f_30506_).orElse(null);
    }

    public void m_30586_(@Nullable UUID p_30587_) {
        this.f_19804_.m_135381_(f_30506_, Optional.ofNullable(p_30587_));
    }

    public boolean m_30616_() {
        return this.f_30519_;
    }

    public void m_30651_(boolean p_30652_) {
        this.m_30597_(2, p_30652_);
    }

    public void m_30655_(boolean p_30656_) {
        this.f_30519_ = p_30656_;
    }

    @Override
    protected void m_7880_(float p_30660_) {
        if (p_30660_ > 6.0f && this.m_30617_()) {
            this.m_30661_(false);
        }
    }

    public boolean m_30617_() {
        return this.m_30647_(16);
    }

    public boolean m_30622_() {
        return this.m_30647_(32);
    }

    public boolean m_30623_() {
        return this.m_30647_(8);
    }

    public void m_30657_(boolean p_30658_) {
        this.m_30597_(8, p_30658_);
    }

    @Override
    public boolean m_6741_() {
        return this.m_6084_() && !this.m_6162_() && this.m_30614_();
    }

    @Override
    public void m_5853_(@Nullable SoundSource p_30546_) {
        this.f_30520_.m_6836_(0, new ItemStack(Items.f_42450_));
        if (p_30546_ != null) {
            this.f_19853_.m_6269_(null, this, SoundEvents.f_12034_, p_30546_, 0.5f, 1.0f);
        }
    }

    @Override
    public boolean m_6254_() {
        return this.m_30647_(4);
    }

    public int m_30624_() {
        return this.f_30521_;
    }

    public void m_30649_(int p_30650_) {
        this.f_30521_ = p_30650_;
    }

    public int m_30653_(int p_30654_) {
        int $$1 = Mth.m_14045_(this.m_30624_() + p_30654_, 0, this.m_7555_());
        this.m_30649_($$1);
        return $$1;
    }

    @Override
    public boolean m_6094_() {
        return !this.m_20160_();
    }

    private void m_30610_() {
        SoundEvent $$0;
        this.m_30612_();
        if (!this.m_20067_() && ($$0 = this.m_7872_()) != null) {
            this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), $$0, this.m_5720_(), 1.0f, 1.0f + (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f);
        }
    }

    @Override
    public boolean m_142535_(float p_149499_, float p_149500_, DamageSource p_149501_) {
        int $$3;
        if (p_149499_ > 1.0f) {
            this.m_5496_(SoundEvents.f_11980_, 0.4f, 1.0f);
        }
        if (($$3 = this.m_5639_(p_149499_, p_149500_)) <= 0) {
            return false;
        }
        this.m_6469_(p_149501_, $$3);
        if (this.m_20160_()) {
            for (Entity $$4 : this.m_146897_()) {
                $$4.m_6469_(p_149501_, $$3);
            }
        }
        this.m_21229_();
        return true;
    }

    @Override
    protected int m_5639_(float p_30606_, float p_30607_) {
        return Mth.m_14167_((p_30606_ * 0.5f - 3.0f) * p_30607_);
    }

    protected int m_7506_() {
        return 2;
    }

    protected void m_30625_() {
        SimpleContainer $$0 = this.f_30520_;
        this.f_30520_ = new SimpleContainer(this.m_7506_());
        if ($$0 != null) {
            $$0.m_19181_(this);
            int $$1 = Math.min($$0.m_6643_(), this.f_30520_.m_6643_());
            for (int $$2 = 0; $$2 < $$1; ++$$2) {
                ItemStack $$3 = $$0.m_8020_($$2);
                if ($$3.m_41619_()) continue;
                this.f_30520_.m_6836_($$2, $$3.m_41777_());
            }
        }
        this.f_30520_.m_19164_(this);
        this.m_7493_();
    }

    protected void m_7493_() {
        if (this.f_19853_.f_46443_) {
            return;
        }
        this.m_30597_(4, !this.f_30520_.m_8020_(0).m_41619_());
    }

    @Override
    public void m_5757_(Container p_30548_) {
        boolean $$1 = this.m_6254_();
        this.m_7493_();
        if (this.f_19797_ > 20 && !$$1 && this.m_6254_()) {
            this.m_5496_(SoundEvents.f_12034_, 0.5f, 1.0f);
        }
    }

    public double m_30626_() {
        return this.m_21133_(Attributes.f_22288_);
    }

    @Nullable
    protected SoundEvent m_7872_() {
        return null;
    }

    @Override
    @Nullable
    protected SoundEvent m_5592_() {
        return null;
    }

    @Override
    @Nullable
    protected SoundEvent m_7975_(DamageSource p_30609_) {
        if (this.f_19796_.m_188503_(3) == 0) {
            this.m_30613_();
        }
        return null;
    }

    @Override
    @Nullable
    protected SoundEvent m_7515_() {
        if (this.f_19796_.m_188503_(10) == 0 && !this.m_6107_()) {
            this.m_30613_();
        }
        return null;
    }

    @Nullable
    protected SoundEvent m_7871_() {
        this.m_30613_();
        return null;
    }

    @Override
    protected void m_7355_(BlockPos p_30584_, BlockState p_30585_) {
        if (p_30585_.m_60767_().m_76332_()) {
            return;
        }
        BlockState $$2 = this.f_19853_.m_8055_(p_30584_.m_7494_());
        SoundType $$3 = p_30585_.m_60827_();
        if ($$2.m_60713_(Blocks.f_50125_)) {
            $$3 = $$2.m_60827_();
        }
        if (this.m_20160_() && this.f_30523_) {
            ++this.f_30524_;
            if (this.f_30524_ > 5 && this.f_30524_ % 3 == 0) {
                this.m_5877_($$3);
            } else if (this.f_30524_ <= 5) {
                this.m_5496_(SoundEvents.f_12036_, $$3.m_56773_() * 0.15f, $$3.m_56774_());
            }
        } else if ($$3 == SoundType.f_56736_) {
            this.m_5496_(SoundEvents.f_12036_, $$3.m_56773_() * 0.15f, $$3.m_56774_());
        } else {
            this.m_5496_(SoundEvents.f_12035_, $$3.m_56773_() * 0.15f, $$3.m_56774_());
        }
    }

    protected void m_5877_(SoundType p_30560_) {
        this.m_5496_(SoundEvents.f_11977_, p_30560_.m_56773_() * 0.15f, p_30560_.m_56774_());
    }

    public static AttributeSupplier.Builder m_30627_() {
        return Mob.m_21552_().m_22266_(Attributes.f_22288_).m_22268_(Attributes.f_22276_, 53.0).m_22268_(Attributes.f_22279_, 0.225f);
    }

    @Override
    public int m_5792_() {
        return 6;
    }

    public int m_7555_() {
        return 100;
    }

    @Override
    protected float m_6121_() {
        return 0.8f;
    }

    @Override
    public int m_8100_() {
        return 400;
    }

    @Override
    public void m_213583_(Player p_218808_) {
        if (!this.f_19853_.f_46443_ && (!this.m_20160_() || this.m_20363_(p_218808_)) && this.m_30614_()) {
            p_218808_.m_6658_(this, this.f_30520_);
        }
    }

    public InteractionResult m_30580_(Player p_30581_, ItemStack p_30582_) {
        boolean $$2 = this.m_5994_(p_30581_, p_30582_);
        if (!p_30581_.m_150110_().f_35937_) {
            p_30582_.m_41774_(1);
        }
        if (this.f_19853_.f_46443_) {
            return InteractionResult.CONSUME;
        }
        return $$2 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    protected boolean m_5994_(Player p_30593_, ItemStack p_30594_) {
        boolean $$2 = false;
        float $$3 = 0.0f;
        int $$4 = 0;
        int $$5 = 0;
        if (p_30594_.m_150930_(Items.f_42405_)) {
            $$3 = 2.0f;
            $$4 = 20;
            $$5 = 3;
        } else if (p_30594_.m_150930_(Items.f_42501_)) {
            $$3 = 1.0f;
            $$4 = 30;
            $$5 = 3;
        } else if (p_30594_.m_150930_(Blocks.f_50335_.m_5456_())) {
            $$3 = 20.0f;
            $$4 = 180;
        } else if (p_30594_.m_150930_(Items.f_42410_)) {
            $$3 = 3.0f;
            $$4 = 60;
            $$5 = 3;
        } else if (p_30594_.m_150930_(Items.f_42677_)) {
            $$3 = 4.0f;
            $$4 = 60;
            $$5 = 5;
            if (!this.f_19853_.f_46443_ && this.m_30614_() && this.m_146764_() == 0 && !this.m_27593_()) {
                $$2 = true;
                this.m_27595_(p_30593_);
            }
        } else if (p_30594_.m_150930_(Items.f_42436_) || p_30594_.m_150930_(Items.f_42437_)) {
            $$3 = 10.0f;
            $$4 = 240;
            $$5 = 10;
            if (!this.f_19853_.f_46443_ && this.m_30614_() && this.m_146764_() == 0 && !this.m_27593_()) {
                $$2 = true;
                this.m_27595_(p_30593_);
            }
        }
        if (this.m_21223_() < this.m_21233_() && $$3 > 0.0f) {
            this.m_5634_($$3);
            $$2 = true;
        }
        if (this.m_6162_() && $$4 > 0) {
            this.f_19853_.m_7106_(ParticleTypes.f_123748_, this.m_20208_(1.0), this.m_20187_() + 0.5, this.m_20262_(1.0), 0.0, 0.0, 0.0);
            if (!this.f_19853_.f_46443_) {
                this.m_146758_($$4);
            }
            $$2 = true;
        }
        if ($$5 > 0 && ($$2 || !this.m_30614_()) && this.m_30624_() < this.m_7555_()) {
            $$2 = true;
            if (!this.f_19853_.f_46443_) {
                this.m_30653_($$5);
            }
        }
        if ($$2) {
            this.m_30610_();
            this.m_146850_(GameEvent.f_157806_);
        }
        return $$2;
    }

    protected void m_6835_(Player p_30634_) {
        this.m_30661_(false);
        this.m_30665_(false);
        if (!this.f_19853_.f_46443_) {
            p_30634_.m_146922_(this.m_146908_());
            p_30634_.m_146926_(this.m_146909_());
            p_30634_.m_20329_(this);
        }
    }

    @Override
    protected boolean m_6107_() {
        return super.m_6107_() && this.m_20160_() && this.m_6254_() || this.m_30617_() || this.m_30622_();
    }

    @Override
    public boolean m_6898_(ItemStack p_30644_) {
        return f_30527_.test(p_30644_);
    }

    private void m_30611_() {
        this.f_30517_ = 1;
    }

    @Override
    protected void m_5907_() {
        super.m_5907_();
        if (this.f_30520_ == null) {
            return;
        }
        for (int $$0 = 0; $$0 < this.f_30520_.m_6643_(); ++$$0) {
            ItemStack $$1 = this.f_30520_.m_8020_($$0);
            if ($$1.m_41619_() || EnchantmentHelper.m_44924_($$1)) continue;
            this.m_19983_($$1);
        }
    }

    @Override
    public void m_8107_() {
        if (this.f_19796_.m_188503_(200) == 0) {
            this.m_30611_();
        }
        super.m_8107_();
        if (this.f_19853_.f_46443_ || !this.m_6084_()) {
            return;
        }
        if (this.f_19796_.m_188503_(900) == 0 && this.f_20919_ == 0) {
            this.m_5634_(1.0f);
        }
        if (this.m_7559_()) {
            if (!this.m_30617_() && !this.m_20160_() && this.f_19796_.m_188503_(300) == 0 && this.f_19853_.m_8055_(this.m_20183_().m_7495_()).m_60713_(Blocks.f_50440_)) {
                this.m_30661_(true);
            }
            if (this.m_30617_() && ++this.f_30507_ > 50) {
                this.f_30507_ = 0;
                this.m_30661_(false);
            }
        }
        this.m_7567_();
    }

    protected void m_7567_() {
        AbstractHorse $$0;
        if (this.m_30623_() && this.m_6162_() && !this.m_30617_() && ($$0 = this.f_19853_.m_45963_(AbstractHorse.class, f_30526_, this, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_20191_().m_82400_(16.0))) != null && this.m_20280_($$0) > 4.0) {
            this.f_21344_.m_6570_($$0, 0);
        }
    }

    public boolean m_7559_() {
        return true;
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (this.f_30508_ > 0 && ++this.f_30508_ > 30) {
            this.f_30508_ = 0;
            this.m_30597_(64, false);
        }
        if ((this.m_6109_() || this.m_6142_()) && this.f_30509_ > 0 && ++this.f_30509_ > 20) {
            this.f_30509_ = 0;
            this.m_30665_(false);
        }
        if (this.f_30517_ > 0 && ++this.f_30517_ > 8) {
            this.f_30517_ = 0;
        }
        if (this.f_30518_ > 0) {
            ++this.f_30518_;
            if (this.f_30518_ > 300) {
                this.f_30518_ = 0;
            }
        }
        this.f_30512_ = this.f_30511_;
        if (this.m_30617_()) {
            this.f_30511_ += (1.0f - this.f_30511_) * 0.4f + 0.05f;
            if (this.f_30511_ > 1.0f) {
                this.f_30511_ = 1.0f;
            }
        } else {
            this.f_30511_ += (0.0f - this.f_30511_) * 0.4f - 0.05f;
            if (this.f_30511_ < 0.0f) {
                this.f_30511_ = 0.0f;
            }
        }
        this.f_30514_ = this.f_30513_;
        if (this.m_30622_()) {
            this.f_30512_ = this.f_30511_ = 0.0f;
            this.f_30513_ += (1.0f - this.f_30513_) * 0.4f + 0.05f;
            if (this.f_30513_ > 1.0f) {
                this.f_30513_ = 1.0f;
            }
        } else {
            this.f_30510_ = false;
            this.f_30513_ += (0.8f * this.f_30513_ * this.f_30513_ * this.f_30513_ - this.f_30513_) * 0.6f - 0.05f;
            if (this.f_30513_ < 0.0f) {
                this.f_30513_ = 0.0f;
            }
        }
        this.f_30516_ = this.f_30515_;
        if (this.m_30647_(64)) {
            this.f_30515_ += (1.0f - this.f_30515_) * 0.7f + 0.05f;
            if (this.f_30515_ > 1.0f) {
                this.f_30515_ = 1.0f;
            }
        } else {
            this.f_30515_ += (0.0f - this.f_30515_) * 0.7f - 0.05f;
            if (this.f_30515_ < 0.0f) {
                this.f_30515_ = 0.0f;
            }
        }
    }

    private void m_30612_() {
        if (!this.f_19853_.f_46443_) {
            this.f_30508_ = 1;
            this.m_30597_(64, true);
        }
    }

    public void m_30661_(boolean p_30662_) {
        this.m_30597_(16, p_30662_);
    }

    public void m_30665_(boolean p_30666_) {
        if (p_30666_) {
            this.m_30661_(false);
        }
        this.m_30597_(32, p_30666_);
    }

    private void m_30613_() {
        if (this.m_6109_() || this.m_6142_()) {
            this.f_30509_ = 1;
            this.m_30665_(true);
        }
    }

    public void m_7564_() {
        if (!this.m_30622_()) {
            this.m_30613_();
            SoundEvent $$0 = this.m_7871_();
            if ($$0 != null) {
                this.m_5496_($$0, this.m_6121_(), this.m_6100_());
            }
        }
    }

    public boolean m_30637_(Player p_30638_) {
        this.m_30586_(p_30638_.m_20148_());
        this.m_30651_(true);
        if (p_30638_ instanceof ServerPlayer) {
            CriteriaTriggers.f_10590_.m_68829_((ServerPlayer)p_30638_, this);
        }
        this.f_19853_.m_7605_(this, (byte)7);
        return true;
    }

    @Override
    public void m_7023_(Vec3 p_30633_) {
        if (!this.m_6084_()) {
            return;
        }
        LivingEntity $$1 = this.m_6688_();
        if (!this.m_20160_() || $$1 == null) {
            this.f_20887_ = 0.02f;
            super.m_7023_(p_30633_);
            return;
        }
        this.m_146922_($$1.m_146908_());
        this.f_19859_ = this.m_146908_();
        this.m_146926_($$1.m_146909_() * 0.5f);
        this.m_19915_(this.m_146908_(), this.m_146909_());
        this.f_20885_ = this.f_20883_ = this.m_146908_();
        float $$2 = $$1.f_20900_ * 0.5f;
        float $$3 = $$1.f_20902_;
        if ($$3 <= 0.0f) {
            $$3 *= 0.25f;
            this.f_30524_ = 0;
        }
        if (this.f_19861_ && this.f_30522_ == 0.0f && this.m_30622_() && !this.f_30510_) {
            $$2 = 0.0f;
            $$3 = 0.0f;
        }
        if (this.f_30522_ > 0.0f && !this.m_30616_() && this.f_19861_) {
            double $$4 = this.m_30626_() * (double)this.f_30522_ * (double)this.m_20098_();
            double $$5 = $$4 + this.m_182332_();
            Vec3 $$6 = this.m_20184_();
            this.m_20334_($$6.f_82479_, $$5, $$6.f_82481_);
            this.m_30655_(true);
            this.f_19812_ = true;
            if ($$3 > 0.0f) {
                float $$7 = Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180));
                float $$8 = Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180));
                this.m_20256_(this.m_20184_().m_82520_(-0.4f * $$7 * this.f_30522_, 0.0, 0.4f * $$8 * this.f_30522_));
            }
            this.f_30522_ = 0.0f;
        }
        this.f_20887_ = this.m_6113_() * 0.1f;
        if (this.m_6109_()) {
            this.m_7910_((float)this.m_21133_(Attributes.f_22279_));
            super.m_7023_(new Vec3($$2, p_30633_.f_82480_, $$3));
        } else if ($$1 instanceof Player) {
            this.m_20256_(Vec3.f_82478_);
        }
        if (this.f_19861_) {
            this.f_30522_ = 0.0f;
            this.m_30655_(false);
        }
        this.m_21043_(this, false);
        this.m_146872_();
    }

    protected void m_7486_() {
        this.m_5496_(SoundEvents.f_11979_, 0.4f, 1.0f);
    }

    @Override
    public void m_7380_(CompoundTag p_30589_) {
        super.m_7380_(p_30589_);
        p_30589_.m_128379_("EatingHaystack", this.m_30617_());
        p_30589_.m_128379_("Bred", this.m_30623_());
        p_30589_.m_128405_("Temper", this.m_30624_());
        p_30589_.m_128379_("Tame", this.m_30614_());
        if (this.m_30615_() != null) {
            p_30589_.m_128362_("Owner", this.m_30615_());
        }
        if (!this.f_30520_.m_8020_(0).m_41619_()) {
            p_30589_.m_128365_("SaddleItem", this.f_30520_.m_8020_(0).m_41739_(new CompoundTag()));
        }
    }

    @Override
    public void m_7378_(CompoundTag p_30565_) {
        ItemStack $$4;
        UUID $$3;
        super.m_7378_(p_30565_);
        this.m_30661_(p_30565_.m_128471_("EatingHaystack"));
        this.m_30657_(p_30565_.m_128471_("Bred"));
        this.m_30649_(p_30565_.m_128451_("Temper"));
        this.m_30651_(p_30565_.m_128471_("Tame"));
        if (p_30565_.m_128403_("Owner")) {
            UUID $$1 = p_30565_.m_128342_("Owner");
        } else {
            String $$2 = p_30565_.m_128461_("Owner");
            $$3 = OldUsersConverter.m_11083_(this.m_20194_(), $$2);
        }
        if ($$3 != null) {
            this.m_30586_($$3);
        }
        if (p_30565_.m_128425_("SaddleItem", 10) && ($$4 = ItemStack.m_41712_(p_30565_.m_128469_("SaddleItem"))).m_150930_(Items.f_42450_)) {
            this.f_30520_.m_6836_(0, $$4);
        }
        this.m_7493_();
    }

    @Override
    public boolean m_7848_(Animal p_30553_) {
        return false;
    }

    protected boolean m_30628_() {
        return !this.m_20160_() && !this.m_20159_() && this.m_30614_() && !this.m_6162_() && this.m_21223_() >= this.m_21233_() && this.m_27593_();
    }

    @Override
    @Nullable
    public AgeableMob m_142606_(ServerLevel p_149506_, AgeableMob p_149507_) {
        return null;
    }

    protected void m_149508_(AgeableMob p_149509_, AbstractHorse p_149510_) {
        double $$2 = this.m_21172_(Attributes.f_22276_) + p_149509_.m_21172_(Attributes.f_22276_) + (double)this.m_218805_(this.f_19796_);
        p_149510_.m_21051_(Attributes.f_22276_).m_22100_($$2 / 3.0);
        double $$3 = this.m_21172_(Attributes.f_22288_) + p_149509_.m_21172_(Attributes.f_22288_) + this.m_218809_(this.f_19796_);
        p_149510_.m_21051_(Attributes.f_22288_).m_22100_($$3 / 3.0);
        double $$4 = this.m_21172_(Attributes.f_22279_) + p_149509_.m_21172_(Attributes.f_22279_) + this.m_218811_(this.f_19796_);
        p_149510_.m_21051_(Attributes.f_22279_).m_22100_($$4 / 3.0);
    }

    public float m_30663_(float p_30664_) {
        return Mth.m_14179_(p_30664_, this.f_30512_, this.f_30511_);
    }

    public float m_30667_(float p_30668_) {
        return Mth.m_14179_(p_30668_, this.f_30514_, this.f_30513_);
    }

    public float m_30533_(float p_30534_) {
        return Mth.m_14179_(p_30534_, this.f_30516_, this.f_30515_);
    }

    @Override
    public void m_7888_(int p_30591_) {
        if (!this.m_6254_()) {
            return;
        }
        if (p_30591_ < 0) {
            p_30591_ = 0;
        } else {
            this.f_30510_ = true;
            this.m_30613_();
        }
        this.f_30522_ = p_30591_ >= 90 ? 1.0f : 0.4f + 0.4f * (float)p_30591_ / 90.0f;
    }

    @Override
    public boolean m_7132_() {
        return this.m_6254_();
    }

    @Override
    public void m_7199_(int p_30574_) {
        this.f_30510_ = true;
        this.m_30613_();
        this.m_7486_();
    }

    @Override
    public void m_8012_() {
    }

    protected void m_30669_(boolean p_30670_) {
        SimpleParticleType $$1 = p_30670_ ? ParticleTypes.f_123750_ : ParticleTypes.f_123762_;
        for (int $$2 = 0; $$2 < 7; ++$$2) {
            double $$3 = this.f_19796_.m_188583_() * 0.02;
            double $$4 = this.f_19796_.m_188583_() * 0.02;
            double $$5 = this.f_19796_.m_188583_() * 0.02;
            this.f_19853_.m_7106_($$1, this.m_20208_(1.0), this.m_20187_() + 0.5, this.m_20262_(1.0), $$3, $$4, $$5);
        }
    }

    @Override
    public void m_7822_(byte p_30541_) {
        if (p_30541_ == 7) {
            this.m_30669_(true);
        } else if (p_30541_ == 6) {
            this.m_30669_(false);
        } else {
            super.m_7822_(p_30541_);
        }
    }

    @Override
    public void m_7332_(Entity p_30642_) {
        super.m_7332_(p_30642_);
        if (p_30642_ instanceof Mob) {
            Mob $$1 = (Mob)p_30642_;
            this.f_20883_ = $$1.f_20883_;
        }
        if (this.f_30514_ > 0.0f) {
            float $$2 = Mth.m_14031_(this.f_20883_ * ((float)Math.PI / 180));
            float $$3 = Mth.m_14089_(this.f_20883_ * ((float)Math.PI / 180));
            float $$4 = 0.7f * this.f_30514_;
            float $$5 = 0.15f * this.f_30514_;
            p_30642_.m_6034_(this.m_20185_() + (double)($$4 * $$2), this.m_20186_() + this.m_6048_() + p_30642_.m_6049_() + (double)$$5, this.m_20189_() - (double)($$4 * $$3));
            if (p_30642_ instanceof LivingEntity) {
                ((LivingEntity)p_30642_).f_20883_ = this.f_20883_;
            }
        }
    }

    protected float m_218805_(RandomSource p_218806_) {
        return 15.0f + (float)p_218806_.m_188503_(8) + (float)p_218806_.m_188503_(9);
    }

    protected double m_218809_(RandomSource p_218810_) {
        return (double)0.4f + p_218810_.m_188500_() * 0.2 + p_218810_.m_188500_() * 0.2 + p_218810_.m_188500_() * 0.2;
    }

    protected double m_218811_(RandomSource p_218812_) {
        return ((double)0.45f + p_218812_.m_188500_() * 0.3 + p_218812_.m_188500_() * 0.3 + p_218812_.m_188500_() * 0.3) * 0.25;
    }

    @Override
    public boolean m_6147_() {
        return false;
    }

    @Override
    protected float m_6431_(Pose p_30578_, EntityDimensions p_30579_) {
        return p_30579_.f_20378_ * 0.95f;
    }

    public boolean m_7482_() {
        return false;
    }

    public boolean m_7481_() {
        return !this.m_6844_(EquipmentSlot.CHEST).m_41619_();
    }

    public boolean m_6010_(ItemStack p_30645_) {
        return false;
    }

    private SlotAccess m_149502_(final int p_149503_, final Predicate<ItemStack> p_149504_) {
        return new SlotAccess(){

            @Override
            public ItemStack m_142196_() {
                return AbstractHorse.this.f_30520_.m_8020_(p_149503_);
            }

            @Override
            public boolean m_142104_(ItemStack p_149528_) {
                if (!p_149504_.test(p_149528_)) {
                    return false;
                }
                AbstractHorse.this.f_30520_.m_6836_(p_149503_, p_149528_);
                AbstractHorse.this.m_7493_();
                return true;
            }
        };
    }

    @Override
    public SlotAccess m_141942_(int p_149514_) {
        int $$2;
        int $$1 = p_149514_ - 400;
        if ($$1 >= 0 && $$1 < 2 && $$1 < this.f_30520_.m_6643_()) {
            if ($$1 == 0) {
                return this.m_149502_($$1, p_149518_ -> p_149518_.m_41619_() || p_149518_.m_150930_(Items.f_42450_));
            }
            if ($$1 == 1) {
                if (!this.m_7482_()) {
                    return SlotAccess.f_147290_;
                }
                return this.m_149502_($$1, p_149516_ -> p_149516_.m_41619_() || this.m_6010_((ItemStack)p_149516_));
            }
        }
        if (($$2 = p_149514_ - 500 + 2) >= 2 && $$2 < this.f_30520_.m_6643_()) {
            return SlotAccess.m_147292_(this.f_30520_, $$2);
        }
        return super.m_141942_(p_149514_);
    }

    @Override
    @Nullable
    public LivingEntity m_6688_() {
        Entity entity;
        if (this.m_6254_() && (entity = this.m_146895_()) instanceof LivingEntity) {
            LivingEntity $$0 = (LivingEntity)entity;
            return $$0;
        }
        return null;
    }

    @Nullable
    private Vec3 m_30561_(Vec3 p_30562_, LivingEntity p_30563_) {
        double $$2 = this.m_20185_() + p_30562_.f_82479_;
        double $$3 = this.m_20191_().f_82289_;
        double $$4 = this.m_20189_() + p_30562_.f_82481_;
        BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
        block0: for (Pose $$6 : p_30563_.m_7431_()) {
            $$5.m_122169_($$2, $$3, $$4);
            double $$7 = this.m_20191_().f_82292_ + 0.75;
            do {
                Vec3 $$10;
                AABB $$9;
                double $$8 = this.f_19853_.m_45573_($$5);
                if ((double)$$5.m_123342_() + $$8 > $$7) continue block0;
                if (DismountHelper.m_38439_($$8) && DismountHelper.m_38456_(this.f_19853_, p_30563_, ($$9 = p_30563_.m_21270_($$6)).m_82383_($$10 = new Vec3($$2, (double)$$5.m_123342_() + $$8, $$4)))) {
                    p_30563_.m_20124_($$6);
                    return $$10;
                }
                $$5.m_122173_(Direction.UP);
            } while ((double)$$5.m_123342_() < $$7);
        }
        return null;
    }

    @Override
    public Vec3 m_7688_(LivingEntity p_30576_) {
        Vec3 $$1 = AbstractHorse.m_19903_(this.m_20205_(), p_30576_.m_20205_(), this.m_146908_() + (p_30576_.m_5737_() == HumanoidArm.RIGHT ? 90.0f : -90.0f));
        Vec3 $$2 = this.m_30561_($$1, p_30576_);
        if ($$2 != null) {
            return $$2;
        }
        Vec3 $$3 = AbstractHorse.m_19903_(this.m_20205_(), p_30576_.m_20205_(), this.m_146908_() + (p_30576_.m_5737_() == HumanoidArm.LEFT ? 90.0f : -90.0f));
        Vec3 $$4 = this.m_30561_($$3, p_30576_);
        if ($$4 != null) {
            return $$4;
        }
        return this.m_20182_();
    }

    protected void m_214179_(RandomSource p_218804_) {
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_30555_, DifficultyInstance p_30556_, MobSpawnType p_30557_, @Nullable SpawnGroupData p_30558_, @Nullable CompoundTag p_30559_) {
        if (p_30558_ == null) {
            p_30558_ = new AgeableMob.AgeableMobGroupData(0.2f);
        }
        this.m_214179_(p_30555_.m_213780_());
        return super.m_6518_(p_30555_, p_30556_, p_30557_, p_30558_, p_30559_);
    }

    public boolean m_149511_(Container p_149512_) {
        return this.f_30520_ != p_149512_;
    }

    @Override
    @Nullable
    public /* synthetic */ Entity m_6688_() {
        return this.m_6688_();
    }
}

