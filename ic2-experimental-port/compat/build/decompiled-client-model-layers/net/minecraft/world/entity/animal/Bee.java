/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal;

import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.AirRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class Bee
extends Animal
implements NeutralMob,
FlyingAnimal {
    public static final float f_148718_ = 120.32113f;
    public static final int f_148719_ = Mth.m_14167_(1.4959966f);
    private static final EntityDataAccessor<Byte> f_27703_ = SynchedEntityData.m_135353_(Bee.class, EntityDataSerializers.f_135027_);
    private static final EntityDataAccessor<Integer> f_27704_ = SynchedEntityData.m_135353_(Bee.class, EntityDataSerializers.f_135028_);
    private static final int f_148729_ = 2;
    private static final int f_148730_ = 4;
    private static final int f_148731_ = 8;
    private static final int f_148732_ = 1200;
    private static final int f_148733_ = 2400;
    private static final int f_148734_ = 3600;
    private static final int f_148735_ = 4;
    private static final int f_148736_ = 10;
    private static final int f_148737_ = 10;
    private static final int f_148738_ = 18;
    private static final int f_148739_ = 32;
    private static final int f_148740_ = 2;
    private static final int f_148741_ = 16;
    private static final int f_148742_ = 20;
    public static final String f_148720_ = "CropsGrownSincePollination";
    public static final String f_148721_ = "CannotEnterHiveTicks";
    public static final String f_148722_ = "TicksSincePollination";
    public static final String f_148723_ = "HasStung";
    public static final String f_148724_ = "HasNectar";
    public static final String f_148727_ = "FlowerPos";
    public static final String f_148728_ = "HivePos";
    private static final UniformInt f_27705_ = TimeUtil.m_145020_(20, 39);
    @Nullable
    private UUID f_27706_;
    private float f_27707_;
    private float f_27708_;
    private int f_27709_;
    int f_27710_;
    private int f_27711_;
    private int f_27712_;
    private static final int f_148725_ = 200;
    int f_27713_;
    private static final int f_148726_ = 200;
    int f_27714_;
    @Nullable
    BlockPos f_27697_;
    @Nullable
    BlockPos f_27698_;
    BeePollinateGoal f_27699_;
    BeeGoToHiveGoal f_27700_;
    private BeeGoToKnownFlowerGoal f_27701_;
    private int f_27702_;

    public Bee(EntityType<? extends Bee> p_27717_, Level p_27718_) {
        super((EntityType<? extends Animal>)p_27717_, p_27718_);
        this.f_27714_ = Mth.m_216271_(this.f_19796_, 20, 60);
        this.f_21342_ = new FlyingMoveControl(this, 20, true);
        this.f_21365_ = new BeeLookControl(this);
        this.m_21441_(BlockPathTypes.DANGER_FIRE, -1.0f);
        this.m_21441_(BlockPathTypes.WATER, -1.0f);
        this.m_21441_(BlockPathTypes.WATER_BORDER, 16.0f);
        this.m_21441_(BlockPathTypes.COCOA, -1.0f);
        this.m_21441_(BlockPathTypes.FENCE, -1.0f);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_27703_, (byte)0);
        this.f_19804_.m_135372_(f_27704_, 0);
    }

    @Override
    public float m_5610_(BlockPos p_27788_, LevelReader p_27789_) {
        if (p_27789_.m_8055_(p_27788_).m_60795_()) {
            return 10.0f;
        }
        return 0.0f;
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(0, new BeeAttackGoal(this, 1.4f, true));
        this.f_21345_.m_25352_(1, new BeeEnterHiveGoal());
        this.f_21345_.m_25352_(2, new BreedGoal(this, 1.0));
        this.f_21345_.m_25352_(3, new TemptGoal(this, 1.25, Ingredient.m_204132_(ItemTags.f_13149_), false));
        this.f_27699_ = new BeePollinateGoal();
        this.f_21345_.m_25352_(4, this.f_27699_);
        this.f_21345_.m_25352_(5, new FollowParentGoal(this, 1.25));
        this.f_21345_.m_25352_(5, new BeeLocateHiveGoal());
        this.f_27700_ = new BeeGoToHiveGoal();
        this.f_21345_.m_25352_(5, this.f_27700_);
        this.f_27701_ = new BeeGoToKnownFlowerGoal();
        this.f_21345_.m_25352_(6, this.f_27701_);
        this.f_21345_.m_25352_(7, new BeeGrowCropGoal());
        this.f_21345_.m_25352_(8, new BeeWanderGoal());
        this.f_21345_.m_25352_(9, new FloatGoal(this));
        this.f_21346_.m_25352_(1, new BeeHurtByOtherGoal(this).m_26044_(new Class[0]));
        this.f_21346_.m_25352_(2, new BeeBecomeAngryTargetGoal(this));
        this.f_21346_.m_25352_(3, new ResetUniversalAngerTargetGoal<Bee>(this, true));
    }

    @Override
    public void m_7380_(CompoundTag p_27823_) {
        super.m_7380_(p_27823_);
        if (this.m_27854_()) {
            p_27823_.m_128365_(f_148728_, NbtUtils.m_129224_(this.m_27855_()));
        }
        if (this.m_27852_()) {
            p_27823_.m_128365_(f_148727_, NbtUtils.m_129224_(this.m_27851_()));
        }
        p_27823_.m_128379_(f_148724_, this.m_27856_());
        p_27823_.m_128379_(f_148723_, this.m_27857_());
        p_27823_.m_128405_(f_148722_, this.f_27710_);
        p_27823_.m_128405_(f_148721_, this.f_27711_);
        p_27823_.m_128405_(f_148720_, this.f_27712_);
        this.m_21678_(p_27823_);
    }

    @Override
    public void m_7378_(CompoundTag p_27793_) {
        this.f_27698_ = null;
        if (p_27793_.m_128441_(f_148728_)) {
            this.f_27698_ = NbtUtils.m_129239_(p_27793_.m_128469_(f_148728_));
        }
        this.f_27697_ = null;
        if (p_27793_.m_128441_(f_148727_)) {
            this.f_27697_ = NbtUtils.m_129239_(p_27793_.m_128469_(f_148727_));
        }
        super.m_7378_(p_27793_);
        this.m_27919_(p_27793_.m_128471_(f_148724_));
        this.m_27925_(p_27793_.m_128471_(f_148723_));
        this.f_27710_ = p_27793_.m_128451_(f_148722_);
        this.f_27711_ = p_27793_.m_128451_(f_148721_);
        this.f_27712_ = p_27793_.m_128451_(f_148720_);
        this.m_147285_(this.f_19853_, p_27793_);
    }

    @Override
    public boolean m_7327_(Entity p_27722_) {
        boolean $$1 = p_27722_.m_6469_(DamageSource.m_19364_(this), (int)this.m_21133_(Attributes.f_22281_));
        if ($$1) {
            this.m_19970_(this, p_27722_);
            if (p_27722_ instanceof LivingEntity) {
                ((LivingEntity)p_27722_).m_21321_(((LivingEntity)p_27722_).m_21235_() + 1);
                int $$2 = 0;
                if (this.f_19853_.m_46791_() == Difficulty.NORMAL) {
                    $$2 = 10;
                } else if (this.f_19853_.m_46791_() == Difficulty.HARD) {
                    $$2 = 18;
                }
                if ($$2 > 0) {
                    ((LivingEntity)p_27722_).m_147207_(new MobEffectInstance(MobEffects.f_19614_, $$2 * 20, 0), this);
                }
            }
            this.m_27925_(true);
            this.m_21662_();
            this.m_5496_(SoundEvents.f_11692_, 1.0f, 1.0f);
        }
        return $$1;
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (this.m_27856_() && this.m_27869_() < 10 && this.f_19796_.m_188501_() < 0.05f) {
            for (int $$0 = 0; $$0 < this.f_19796_.m_188503_(2) + 1; ++$$0) {
                this.m_27779_(this.f_19853_, this.m_20185_() - (double)0.3f, this.m_20185_() + (double)0.3f, this.m_20189_() - (double)0.3f, this.m_20189_() + (double)0.3f, this.m_20227_(0.5), ParticleTypes.f_123782_);
            }
        }
        this.m_27867_();
    }

    private void m_27779_(Level p_27780_, double p_27781_, double p_27782_, double p_27783_, double p_27784_, double p_27785_, ParticleOptions p_27786_) {
        p_27780_.m_7106_(p_27786_, Mth.m_14139_(p_27780_.f_46441_.m_188500_(), p_27781_, p_27782_), p_27785_, Mth.m_14139_(p_27780_.f_46441_.m_188500_(), p_27783_, p_27784_), 0.0, 0.0, 0.0);
    }

    void m_27880_(BlockPos p_27881_) {
        Vec3 $$8;
        Vec3 $$1 = Vec3.m_82539_(p_27881_);
        int $$2 = 0;
        BlockPos $$3 = this.m_20183_();
        int $$4 = (int)$$1.f_82480_ - $$3.m_123342_();
        if ($$4 > 2) {
            $$2 = 4;
        } else if ($$4 < -2) {
            $$2 = -4;
        }
        int $$5 = 6;
        int $$6 = 8;
        int $$7 = $$3.m_123333_(p_27881_);
        if ($$7 < 15) {
            $$5 = $$7 / 2;
            $$6 = $$7 / 2;
        }
        if (($$8 = AirRandomPos.m_148387_(this, $$5, $$6, $$2, $$1, 0.3141592741012573)) == null) {
            return;
        }
        this.f_21344_.m_26529_(0.5f);
        this.f_21344_.m_26519_($$8.f_82479_, $$8.f_82480_, $$8.f_82481_, 1.0);
    }

    @Nullable
    public BlockPos m_27851_() {
        return this.f_27697_;
    }

    public boolean m_27852_() {
        return this.f_27697_ != null;
    }

    public void m_27876_(BlockPos p_27877_) {
        this.f_27697_ = p_27877_;
    }

    @VisibleForDebug
    public int m_148774_() {
        return Math.max(this.f_27700_.f_27980_, this.f_27701_.f_28010_);
    }

    @VisibleForDebug
    public List<BlockPos> m_148775_() {
        return this.f_27700_.f_27981_;
    }

    private boolean m_27865_() {
        return this.f_27710_ > 3600;
    }

    boolean m_27866_() {
        if (this.f_27711_ > 0 || this.f_27699_.m_28086_() || this.m_27857_() || this.m_5448_() != null) {
            return false;
        }
        boolean $$0 = this.m_27865_() || this.f_19853_.m_46471_() || this.f_19853_.m_46462_() || this.m_27856_();
        return $$0 && !this.m_27868_();
    }

    public void m_27915_(int p_27916_) {
        this.f_27711_ = p_27916_;
    }

    public float m_27935_(float p_27936_) {
        return Mth.m_14179_(p_27936_, this.f_27708_, this.f_27707_);
    }

    private void m_27867_() {
        this.f_27708_ = this.f_27707_;
        this.f_27707_ = this.m_27873_() ? Math.min(1.0f, this.f_27707_ + 0.2f) : Math.max(0.0f, this.f_27707_ - 0.24f);
    }

    @Override
    protected void m_8024_() {
        boolean $$0 = this.m_27857_();
        this.f_27702_ = this.m_20072_() ? ++this.f_27702_ : 0;
        if (this.f_27702_ > 20) {
            this.m_6469_(DamageSource.f_19312_, 1.0f);
        }
        if ($$0) {
            ++this.f_27709_;
            if (this.f_27709_ % 5 == 0 && this.f_19796_.m_188503_(Mth.m_14045_(1200 - this.f_27709_, 1, 1200)) == 0) {
                this.m_6469_(DamageSource.f_19318_, this.m_21223_());
            }
        }
        if (!this.m_27856_()) {
            ++this.f_27710_;
        }
        if (!this.f_19853_.f_46443_) {
            this.m_21666_((ServerLevel)this.f_19853_, false);
        }
    }

    public void m_27853_() {
        this.f_27710_ = 0;
    }

    private boolean m_27868_() {
        if (this.f_27698_ == null) {
            return false;
        }
        BlockEntity $$0 = this.f_19853_.m_7702_(this.f_27698_);
        return $$0 instanceof BeehiveBlockEntity && ((BeehiveBlockEntity)$$0).m_58773_();
    }

    @Override
    public int m_6784_() {
        return this.f_19804_.m_135370_(f_27704_);
    }

    @Override
    public void m_7870_(int p_27795_) {
        this.f_19804_.m_135381_(f_27704_, p_27795_);
    }

    @Override
    @Nullable
    public UUID m_6120_() {
        return this.f_27706_;
    }

    @Override
    public void m_6925_(@Nullable UUID p_27791_) {
        this.f_27706_ = p_27791_;
    }

    @Override
    public void m_6825_() {
        this.m_7870_(f_27705_.m_214085_(this.f_19796_));
    }

    private boolean m_27884_(BlockPos p_27885_) {
        BlockEntity $$1 = this.f_19853_.m_7702_(p_27885_);
        if ($$1 instanceof BeehiveBlockEntity) {
            return !((BeehiveBlockEntity)$$1).m_58775_();
        }
        return false;
    }

    @VisibleForDebug
    public boolean m_27854_() {
        return this.f_27698_ != null;
    }

    @Nullable
    @VisibleForDebug
    public BlockPos m_27855_() {
        return this.f_27698_;
    }

    @VisibleForDebug
    public GoalSelector m_148772_() {
        return this.f_21345_;
    }

    @Override
    protected void m_8025_() {
        super.m_8025_();
        DebugPackets.m_133697_(this);
    }

    int m_27869_() {
        return this.f_27712_;
    }

    private void m_27870_() {
        this.f_27712_ = 0;
    }

    void m_27871_() {
        ++this.f_27712_;
    }

    @Override
    public void m_8107_() {
        super.m_8107_();
        if (!this.f_19853_.f_46443_) {
            if (this.f_27711_ > 0) {
                --this.f_27711_;
            }
            if (this.f_27713_ > 0) {
                --this.f_27713_;
            }
            if (this.f_27714_ > 0) {
                --this.f_27714_;
            }
            boolean $$0 = this.m_21660_() && !this.m_27857_() && this.m_5448_() != null && this.m_5448_().m_20280_(this) < 4.0;
            this.m_27929_($$0);
            if (this.f_19797_ % 20 == 0 && !this.m_27872_()) {
                this.f_27698_ = null;
            }
        }
    }

    boolean m_27872_() {
        if (!this.m_27854_()) {
            return false;
        }
        BlockEntity $$0 = this.f_19853_.m_7702_(this.f_27698_);
        return $$0 != null && $$0.m_58903_() == BlockEntityType.f_58912_;
    }

    public boolean m_27856_() {
        return this.m_27921_(8);
    }

    void m_27919_(boolean p_27920_) {
        if (p_27920_) {
            this.m_27853_();
        }
        this.m_27832_(8, p_27920_);
    }

    public boolean m_27857_() {
        return this.m_27921_(4);
    }

    private void m_27925_(boolean p_27926_) {
        this.m_27832_(4, p_27926_);
    }

    private boolean m_27873_() {
        return this.m_27921_(2);
    }

    private void m_27929_(boolean p_27930_) {
        this.m_27832_(2, p_27930_);
    }

    boolean m_27889_(BlockPos p_27890_) {
        return !this.m_27816_(p_27890_, 32);
    }

    private void m_27832_(int p_27833_, boolean p_27834_) {
        if (p_27834_) {
            this.f_19804_.m_135381_(f_27703_, (byte)(this.f_19804_.m_135370_(f_27703_) | p_27833_));
        } else {
            this.f_19804_.m_135381_(f_27703_, (byte)(this.f_19804_.m_135370_(f_27703_) & ~p_27833_));
        }
    }

    private boolean m_27921_(int p_27922_) {
        return (this.f_19804_.m_135370_(f_27703_) & p_27922_) != 0;
    }

    public static AttributeSupplier.Builder m_27858_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0).m_22268_(Attributes.f_22280_, 0.6f).m_22268_(Attributes.f_22279_, 0.3f).m_22268_(Attributes.f_22281_, 2.0).m_22268_(Attributes.f_22277_, 48.0);
    }

    @Override
    protected PathNavigation m_6037_(Level p_27815_) {
        FlyingPathNavigation $$1 = new FlyingPathNavigation(this, p_27815_){

            @Override
            public boolean m_6342_(BlockPos p_27947_) {
                return !this.f_26495_.m_8055_(p_27947_.m_7495_()).m_60795_();
            }

            @Override
            public void m_7638_() {
                if (Bee.this.f_27699_.m_28086_()) {
                    return;
                }
                super.m_7638_();
            }
        };
        $$1.m_26440_(false);
        $$1.m_7008_(false);
        $$1.m_26443_(true);
        return $$1;
    }

    @Override
    public boolean m_6898_(ItemStack p_27895_) {
        return p_27895_.m_204117_(ItemTags.f_13149_);
    }

    boolean m_27896_(BlockPos p_27897_) {
        return this.f_19853_.m_46749_(p_27897_) && this.f_19853_.m_8055_(p_27897_).m_204336_(BlockTags.f_13041_);
    }

    @Override
    protected void m_7355_(BlockPos p_27820_, BlockState p_27821_) {
    }

    @Override
    protected SoundEvent m_7515_() {
        return null;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_27845_) {
        return SoundEvents.f_11741_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_11740_;
    }

    @Override
    protected float m_6121_() {
        return 0.4f;
    }

    @Override
    public Bee m_142606_(ServerLevel p_148760_, AgeableMob p_148761_) {
        return EntityType.f_20550_.m_20615_(p_148760_);
    }

    @Override
    protected float m_6431_(Pose p_27804_, EntityDimensions p_27805_) {
        if (this.m_6162_()) {
            return p_27805_.f_20378_ * 0.5f;
        }
        return p_27805_.f_20378_ * 0.5f;
    }

    @Override
    public boolean m_142535_(float p_148750_, float p_148751_, DamageSource p_148752_) {
        return false;
    }

    @Override
    protected void m_7840_(double p_27754_, boolean p_27755_, BlockState p_27756_, BlockPos p_27757_) {
    }

    @Override
    public boolean m_142039_() {
        return this.m_29443_() && this.f_19797_ % f_148719_ == 0;
    }

    @Override
    public boolean m_29443_() {
        return !this.f_19861_;
    }

    public void m_27864_() {
        this.m_27919_(false);
        this.m_27870_();
    }

    @Override
    public boolean m_6469_(DamageSource p_27762_, float p_27763_) {
        if (this.m_6673_(p_27762_)) {
            return false;
        }
        if (!this.f_19853_.f_46443_) {
            this.f_27699_.m_28087_();
        }
        return super.m_6469_(p_27762_, p_27763_);
    }

    @Override
    public MobType m_6336_() {
        return MobType.f_21642_;
    }

    @Override
    protected void m_203347_(TagKey<Fluid> p_204061_) {
        this.m_20256_(this.m_20184_().m_82520_(0.0, 0.01, 0.0));
    }

    @Override
    public Vec3 m_7939_() {
        return new Vec3(0.0, 0.5f * this.m_20192_(), this.m_20205_() * 0.2f);
    }

    boolean m_27816_(BlockPos p_27817_, int p_27818_) {
        return p_27817_.m_123314_(this.m_20183_(), p_27818_);
    }

    @Override
    public /* synthetic */ AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return this.m_142606_(serverLevel, ageableMob);
    }

    class BeePollinateGoal
    extends BaseBeeGoal {
        private static final int f_148812_ = 400;
        private static final int f_148813_ = 20;
        private static final int f_148814_ = 60;
        private final Predicate<BlockState> f_28063_;
        private static final double f_148815_ = 0.1;
        private static final int f_148816_ = 25;
        private static final float f_148817_ = 0.35f;
        private static final float f_148818_ = 0.6f;
        private static final float f_148819_ = 0.33333334f;
        private int f_28064_;
        private int f_28065_;
        private boolean f_28066_;
        @Nullable
        private Vec3 f_28067_;
        private int f_28068_;
        private static final int f_148820_ = 600;

        BeePollinateGoal() {
            this.f_28063_ = p_28074_ -> {
                if (p_28074_.m_61138_(BlockStateProperties.f_61362_) && p_28074_.m_61143_(BlockStateProperties.f_61362_).booleanValue()) {
                    return false;
                }
                if (p_28074_.m_204336_(BlockTags.f_13041_)) {
                    if (p_28074_.m_60713_(Blocks.f_50355_)) {
                        return p_28074_.m_61143_(DoublePlantBlock.f_52858_) == DoubleBlockHalf.UPPER;
                    }
                    return true;
                }
                return false;
            };
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean m_7989_() {
            if (Bee.this.f_27714_ > 0) {
                return false;
            }
            if (Bee.this.m_27856_()) {
                return false;
            }
            if (Bee.this.f_19853_.m_46471_()) {
                return false;
            }
            Optional<BlockPos> $$0 = this.m_28090_();
            if ($$0.isPresent()) {
                Bee.this.f_27697_ = $$0.get();
                Bee.this.f_21344_.m_26519_((double)Bee.this.f_27697_.m_123341_() + 0.5, (double)Bee.this.f_27697_.m_123342_() + 0.5, (double)Bee.this.f_27697_.m_123343_() + 0.5, 1.2f);
                return true;
            }
            Bee.this.f_27714_ = Mth.m_216271_(Bee.this.f_19796_, 20, 60);
            return false;
        }

        @Override
        public boolean m_8011_() {
            if (!this.f_28066_) {
                return false;
            }
            if (!Bee.this.m_27852_()) {
                return false;
            }
            if (Bee.this.f_19853_.m_46471_()) {
                return false;
            }
            if (this.m_28085_()) {
                return Bee.this.f_19796_.m_188501_() < 0.2f;
            }
            if (Bee.this.f_19797_ % 20 == 0 && !Bee.this.m_27896_(Bee.this.f_27697_)) {
                Bee.this.f_27697_ = null;
                return false;
            }
            return true;
        }

        private boolean m_28085_() {
            return this.f_28064_ > 400;
        }

        boolean m_28086_() {
            return this.f_28066_;
        }

        void m_28087_() {
            this.f_28066_ = false;
        }

        @Override
        public void m_8056_() {
            this.f_28064_ = 0;
            this.f_28068_ = 0;
            this.f_28065_ = 0;
            this.f_28066_ = true;
            Bee.this.m_27853_();
        }

        @Override
        public void m_8041_() {
            if (this.m_28085_()) {
                Bee.this.m_27919_(true);
            }
            this.f_28066_ = false;
            Bee.this.f_21344_.m_26573_();
            Bee.this.f_27714_ = 200;
        }

        @Override
        public boolean m_183429_() {
            return true;
        }

        @Override
        public void m_8037_() {
            ++this.f_28068_;
            if (this.f_28068_ > 600) {
                Bee.this.f_27697_ = null;
                return;
            }
            Vec3 $$0 = Vec3.m_82539_(Bee.this.f_27697_).m_82520_(0.0, 0.6f, 0.0);
            if ($$0.m_82554_(Bee.this.m_20182_()) > 1.0) {
                this.f_28067_ = $$0;
                this.m_28088_();
                return;
            }
            if (this.f_28067_ == null) {
                this.f_28067_ = $$0;
            }
            boolean $$1 = Bee.this.m_20182_().m_82554_(this.f_28067_) <= 0.1;
            boolean $$2 = true;
            if (!$$1 && this.f_28068_ > 600) {
                Bee.this.f_27697_ = null;
                return;
            }
            if ($$1) {
                boolean $$3;
                boolean bl = $$3 = Bee.this.f_19796_.m_188503_(25) == 0;
                if ($$3) {
                    this.f_28067_ = new Vec3($$0.m_7096_() + (double)this.m_28089_(), $$0.m_7098_(), $$0.m_7094_() + (double)this.m_28089_());
                    Bee.this.f_21344_.m_26573_();
                } else {
                    $$2 = false;
                }
                Bee.this.m_21563_().m_24946_($$0.m_7096_(), $$0.m_7098_(), $$0.m_7094_());
            }
            if ($$2) {
                this.m_28088_();
            }
            ++this.f_28064_;
            if (Bee.this.f_19796_.m_188501_() < 0.05f && this.f_28064_ > this.f_28065_ + 60) {
                this.f_28065_ = this.f_28064_;
                Bee.this.m_5496_(SoundEvents.f_11693_, 1.0f, 1.0f);
            }
        }

        private void m_28088_() {
            Bee.this.m_21566_().m_6849_(this.f_28067_.m_7096_(), this.f_28067_.m_7098_(), this.f_28067_.m_7094_(), 0.35f);
        }

        private float m_28089_() {
            return (Bee.this.f_19796_.m_188501_() * 2.0f - 1.0f) * 0.33333334f;
        }

        private Optional<BlockPos> m_28090_() {
            return this.m_28075_(this.f_28063_, 5.0);
        }

        private Optional<BlockPos> m_28075_(Predicate<BlockState> p_28076_, double p_28077_) {
            BlockPos $$2 = Bee.this.m_20183_();
            BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos();
            int $$4 = 0;
            while ((double)$$4 <= p_28077_) {
                int $$5 = 0;
                while ((double)$$5 < p_28077_) {
                    int $$6 = 0;
                    while ($$6 <= $$5) {
                        int $$7;
                        int n = $$7 = $$6 < $$5 && $$6 > -$$5 ? $$5 : 0;
                        while ($$7 <= $$5) {
                            $$3.m_122154_($$2, $$6, $$4 - 1, $$7);
                            if ($$2.m_123314_($$3, p_28077_) && p_28076_.test(Bee.this.f_19853_.m_8055_($$3))) {
                                return Optional.of($$3);
                            }
                            $$7 = $$7 > 0 ? -$$7 : 1 - $$7;
                        }
                        $$6 = $$6 > 0 ? -$$6 : 1 - $$6;
                    }
                    ++$$5;
                }
                $$4 = $$4 > 0 ? -$$4 : 1 - $$4;
            }
            return Optional.empty();
        }
    }

    class BeeLookControl
    extends LookControl {
        BeeLookControl(Mob p_28059_) {
            super(p_28059_);
        }

        @Override
        public void m_8128_() {
            if (Bee.this.m_21660_()) {
                return;
            }
            super.m_8128_();
        }

        @Override
        protected boolean m_8106_() {
            return !Bee.this.f_27699_.m_28086_();
        }
    }

    class BeeAttackGoal
    extends MeleeAttackGoal {
        BeeAttackGoal(PathfinderMob p_27960_, double p_27961_, boolean p_27962_) {
            super(p_27960_, p_27961_, p_27962_);
        }

        @Override
        public boolean m_8036_() {
            return super.m_8036_() && Bee.this.m_21660_() && !Bee.this.m_27857_();
        }

        @Override
        public boolean m_8045_() {
            return super.m_8045_() && Bee.this.m_21660_() && !Bee.this.m_27857_();
        }
    }

    class BeeEnterHiveGoal
    extends BaseBeeGoal {
        BeeEnterHiveGoal() {
        }

        @Override
        public boolean m_7989_() {
            BlockEntity $$0;
            if (Bee.this.m_27854_() && Bee.this.m_27866_() && Bee.this.f_27698_.m_203195_(Bee.this.m_20182_(), 2.0) && ($$0 = Bee.this.f_19853_.m_7702_(Bee.this.f_27698_)) instanceof BeehiveBlockEntity) {
                BeehiveBlockEntity $$1 = (BeehiveBlockEntity)$$0;
                if ($$1.m_58775_()) {
                    Bee.this.f_27698_ = null;
                } else {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean m_8011_() {
            return false;
        }

        @Override
        public void m_8056_() {
            BlockEntity $$0 = Bee.this.f_19853_.m_7702_(Bee.this.f_27698_);
            if ($$0 instanceof BeehiveBlockEntity) {
                BeehiveBlockEntity $$1 = (BeehiveBlockEntity)$$0;
                $$1.m_58741_(Bee.this, Bee.this.m_27856_());
            }
        }
    }

    class BeeLocateHiveGoal
    extends BaseBeeGoal {
        BeeLocateHiveGoal() {
        }

        @Override
        public boolean m_7989_() {
            return Bee.this.f_27713_ == 0 && !Bee.this.m_27854_() && Bee.this.m_27866_();
        }

        @Override
        public boolean m_8011_() {
            return false;
        }

        @Override
        public void m_8056_() {
            Bee.this.f_27713_ = 200;
            List<BlockPos> $$0 = this.m_28055_();
            if ($$0.isEmpty()) {
                return;
            }
            for (BlockPos $$1 : $$0) {
                if (Bee.this.f_27700_.m_27993_($$1)) continue;
                Bee.this.f_27698_ = $$1;
                return;
            }
            Bee.this.f_27700_.m_28006_();
            Bee.this.f_27698_ = $$0.get(0);
        }

        private List<BlockPos> m_28055_() {
            BlockPos $$0 = Bee.this.m_20183_();
            PoiManager $$1 = ((ServerLevel)Bee.this.f_19853_).m_8904_();
            Stream<PoiRecord> $$2 = $$1.m_27181_(p_218130_ -> p_218130_.m_203656_(PoiTypeTags.f_215877_), $$0, 20, PoiManager.Occupancy.ANY);
            return $$2.map(PoiRecord::m_27257_).filter(Bee.this::m_27884_).sorted(Comparator.comparingDouble(p_148811_ -> p_148811_.m_123331_($$0))).collect(Collectors.toList());
        }
    }

    @VisibleForDebug
    public class BeeGoToHiveGoal
    extends BaseBeeGoal {
        public static final int f_148804_ = 600;
        int f_27980_;
        private static final int f_148805_ = 3;
        final List<BlockPos> f_27981_;
        @Nullable
        private Path f_27982_;
        private static final int f_148806_ = 60;
        private int f_27983_;

        BeeGoToHiveGoal() {
            this.f_27980_ = Bee.this.f_19853_.f_46441_.m_188503_(10);
            this.f_27981_ = Lists.newArrayList();
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean m_7989_() {
            return Bee.this.f_27698_ != null && !Bee.this.m_21536_() && Bee.this.m_27866_() && !this.m_28001_(Bee.this.f_27698_) && Bee.this.f_19853_.m_8055_(Bee.this.f_27698_).m_204336_(BlockTags.f_13072_);
        }

        @Override
        public boolean m_8011_() {
            return this.m_7989_();
        }

        @Override
        public void m_8056_() {
            this.f_27980_ = 0;
            this.f_27983_ = 0;
            super.m_8056_();
        }

        @Override
        public void m_8041_() {
            this.f_27980_ = 0;
            this.f_27983_ = 0;
            Bee.this.f_21344_.m_26573_();
            Bee.this.f_21344_.m_26566_();
        }

        @Override
        public void m_8037_() {
            if (Bee.this.f_27698_ == null) {
                return;
            }
            ++this.f_27980_;
            if (this.f_27980_ > this.m_183277_(600)) {
                this.m_28007_();
                return;
            }
            if (Bee.this.f_21344_.m_26572_()) {
                return;
            }
            if (Bee.this.m_27816_(Bee.this.f_27698_, 16)) {
                boolean $$0 = this.m_27990_(Bee.this.f_27698_);
                if (!$$0) {
                    this.m_28007_();
                } else if (this.f_27982_ != null && Bee.this.f_21344_.m_26570_().m_77385_(this.f_27982_)) {
                    ++this.f_27983_;
                    if (this.f_27983_ > 60) {
                        this.m_28008_();
                        this.f_27983_ = 0;
                    }
                } else {
                    this.f_27982_ = Bee.this.f_21344_.m_26570_();
                }
                return;
            }
            if (Bee.this.m_27889_(Bee.this.f_27698_)) {
                this.m_28008_();
                return;
            }
            Bee.this.m_27880_(Bee.this.f_27698_);
        }

        private boolean m_27990_(BlockPos p_27991_) {
            Bee.this.f_21344_.m_26529_(10.0f);
            Bee.this.f_21344_.m_26519_(p_27991_.m_123341_(), p_27991_.m_123342_(), p_27991_.m_123343_(), 1.0);
            return Bee.this.f_21344_.m_26570_() != null && Bee.this.f_21344_.m_26570_().m_77403_();
        }

        boolean m_27993_(BlockPos p_27994_) {
            return this.f_27981_.contains(p_27994_);
        }

        private void m_27998_(BlockPos p_27999_) {
            this.f_27981_.add(p_27999_);
            while (this.f_27981_.size() > 3) {
                this.f_27981_.remove(0);
            }
        }

        void m_28006_() {
            this.f_27981_.clear();
        }

        private void m_28007_() {
            if (Bee.this.f_27698_ != null) {
                this.m_27998_(Bee.this.f_27698_);
            }
            this.m_28008_();
        }

        private void m_28008_() {
            Bee.this.f_27698_ = null;
            Bee.this.f_27713_ = 200;
        }

        private boolean m_28001_(BlockPos p_28002_) {
            if (Bee.this.m_27816_(p_28002_, 2)) {
                return true;
            }
            Path $$1 = Bee.this.f_21344_.m_26570_();
            return $$1 != null && $$1.m_77406_().equals(p_28002_) && $$1.m_77403_() && $$1.m_77392_();
        }
    }

    public class BeeGoToKnownFlowerGoal
    extends BaseBeeGoal {
        private static final int f_148807_ = 600;
        int f_28010_;

        BeeGoToKnownFlowerGoal() {
            this.f_28010_ = Bee.this.f_19853_.f_46441_.m_188503_(10);
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean m_7989_() {
            return Bee.this.f_27697_ != null && !Bee.this.m_21536_() && this.m_28020_() && Bee.this.m_27896_(Bee.this.f_27697_) && !Bee.this.m_27816_(Bee.this.f_27697_, 2);
        }

        @Override
        public boolean m_8011_() {
            return this.m_7989_();
        }

        @Override
        public void m_8056_() {
            this.f_28010_ = 0;
            super.m_8056_();
        }

        @Override
        public void m_8041_() {
            this.f_28010_ = 0;
            Bee.this.f_21344_.m_26573_();
            Bee.this.f_21344_.m_26566_();
        }

        @Override
        public void m_8037_() {
            if (Bee.this.f_27697_ == null) {
                return;
            }
            ++this.f_28010_;
            if (this.f_28010_ > this.m_183277_(600)) {
                Bee.this.f_27697_ = null;
                return;
            }
            if (Bee.this.f_21344_.m_26572_()) {
                return;
            }
            if (Bee.this.m_27889_(Bee.this.f_27697_)) {
                Bee.this.f_27697_ = null;
                return;
            }
            Bee.this.m_27880_(Bee.this.f_27697_);
        }

        private boolean m_28020_() {
            return Bee.this.f_27710_ > 2400;
        }
    }

    class BeeGrowCropGoal
    extends BaseBeeGoal {
        static final int f_148808_ = 30;

        BeeGrowCropGoal() {
        }

        @Override
        public boolean m_7989_() {
            if (Bee.this.m_27869_() >= 10) {
                return false;
            }
            if (Bee.this.f_19796_.m_188501_() < 0.3f) {
                return false;
            }
            return Bee.this.m_27856_() && Bee.this.m_27872_();
        }

        @Override
        public boolean m_8011_() {
            return this.m_7989_();
        }

        @Override
        public void m_8037_() {
            if (Bee.this.f_19796_.m_188503_(this.m_183277_(30)) != 0) {
                return;
            }
            for (int $$0 = 1; $$0 <= 2; ++$$0) {
                BlockPos $$1 = Bee.this.m_20183_().m_6625_($$0);
                BlockState $$2 = Bee.this.f_19853_.m_8055_($$1);
                Block $$3 = $$2.m_60734_();
                boolean $$4 = false;
                IntegerProperty $$5 = null;
                if (!$$2.m_204336_(BlockTags.f_13074_)) continue;
                if ($$3 instanceof CropBlock) {
                    CropBlock $$6 = (CropBlock)$$3;
                    if (!$$6.m_52307_($$2)) {
                        $$4 = true;
                        $$5 = $$6.m_7959_();
                    }
                } else if ($$3 instanceof StemBlock) {
                    int $$7 = $$2.m_61143_(StemBlock.f_57013_);
                    if ($$7 < 7) {
                        $$4 = true;
                        $$5 = StemBlock.f_57013_;
                    }
                } else if ($$2.m_60713_(Blocks.f_50685_)) {
                    int $$8 = $$2.m_61143_(SweetBerryBushBlock.f_57244_);
                    if ($$8 < 3) {
                        $$4 = true;
                        $$5 = SweetBerryBushBlock.f_57244_;
                    }
                } else if ($$2.m_60713_(Blocks.f_152538_) || $$2.m_60713_(Blocks.f_152539_)) {
                    ((BonemealableBlock)((Object)$$2.m_60734_())).m_214148_((ServerLevel)Bee.this.f_19853_, Bee.this.f_19796_, $$1, $$2);
                }
                if (!$$4) continue;
                Bee.this.f_19853_.m_46796_(2005, $$1, 0);
                Bee.this.f_19853_.m_46597_($$1, (BlockState)$$2.m_61124_($$5, $$2.m_61143_($$5) + 1));
                Bee.this.m_27871_();
            }
        }
    }

    class BeeWanderGoal
    extends Goal {
        private static final int f_148821_ = 22;

        BeeWanderGoal() {
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean m_8036_() {
            return Bee.this.f_21344_.m_26571_() && Bee.this.f_19796_.m_188503_(10) == 0;
        }

        @Override
        public boolean m_8045_() {
            return Bee.this.f_21344_.m_26572_();
        }

        @Override
        public void m_8056_() {
            Vec3 $$0 = this.m_28097_();
            if ($$0 != null) {
                Bee.this.f_21344_.m_26536_(Bee.this.f_21344_.m_7864_(new BlockPos($$0), 1), 1.0);
            }
        }

        @Nullable
        private Vec3 m_28097_() {
            Vec3 $$2;
            if (Bee.this.m_27872_() && !Bee.this.m_27816_(Bee.this.f_27698_, 22)) {
                Vec3 $$0 = Vec3.m_82512_(Bee.this.f_27698_);
                Vec3 $$1 = $$0.m_82546_(Bee.this.m_20182_()).m_82541_();
            } else {
                $$2 = Bee.this.m_20252_(0.0f);
            }
            int $$3 = 8;
            Vec3 $$4 = HoverRandomPos.m_148465_(Bee.this, 8, 7, $$2.f_82479_, $$2.f_82481_, 1.5707964f, 3, 1);
            if ($$4 != null) {
                return $$4;
            }
            return AirAndWaterRandomPos.m_148357_(Bee.this, 8, 4, -2, $$2.f_82479_, $$2.f_82481_, 1.5707963705062866);
        }
    }

    class BeeHurtByOtherGoal
    extends HurtByTargetGoal {
        BeeHurtByOtherGoal(Bee p_28033_) {
            super(p_28033_, new Class[0]);
        }

        @Override
        public boolean m_8045_() {
            return Bee.this.m_21660_() && super.m_8045_();
        }

        @Override
        protected void m_5766_(Mob p_28035_, LivingEntity p_28036_) {
            if (p_28035_ instanceof Bee && this.f_26135_.m_142582_(p_28036_)) {
                p_28035_.m_6710_(p_28036_);
            }
        }
    }

    static class BeeBecomeAngryTargetGoal
    extends NearestAttackableTargetGoal<Player> {
        BeeBecomeAngryTargetGoal(Bee p_27966_) {
            super(p_27966_, Player.class, 10, true, false, p_27966_::m_21674_);
        }

        @Override
        public boolean m_8036_() {
            return this.m_27969_() && super.m_8036_();
        }

        @Override
        public boolean m_8045_() {
            boolean $$0 = this.m_27969_();
            if (!$$0 || this.f_26135_.m_5448_() == null) {
                this.f_26137_ = null;
                return false;
            }
            return super.m_8045_();
        }

        private boolean m_27969_() {
            Bee $$0 = (Bee)this.f_26135_;
            return $$0.m_21660_() && !$$0.m_27857_();
        }
    }

    abstract class BaseBeeGoal
    extends Goal {
        BaseBeeGoal() {
        }

        public abstract boolean m_7989_();

        public abstract boolean m_8011_();

        @Override
        public boolean m_8036_() {
            return this.m_7989_() && !Bee.this.m_21660_();
        }

        @Override
        public boolean m_8045_() {
            return this.m_8011_() && !Bee.this.m_21660_();
        }
    }
}

