/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class Panda
extends Animal {
    private static final EntityDataAccessor<Integer> f_29073_ = SynchedEntityData.m_135353_(Panda.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> f_29074_ = SynchedEntityData.m_135353_(Panda.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> f_29075_ = SynchedEntityData.m_135353_(Panda.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Byte> f_29076_ = SynchedEntityData.m_135353_(Panda.class, EntityDataSerializers.f_135027_);
    private static final EntityDataAccessor<Byte> f_29077_ = SynchedEntityData.m_135353_(Panda.class, EntityDataSerializers.f_135027_);
    private static final EntityDataAccessor<Byte> f_29078_ = SynchedEntityData.m_135353_(Panda.class, EntityDataSerializers.f_135027_);
    static final TargetingConditions f_29079_ = TargetingConditions.m_148353_().m_26883_(8.0);
    private static final int f_148960_ = 2;
    private static final int f_148961_ = 4;
    private static final int f_148962_ = 8;
    private static final int f_148963_ = 16;
    private static final int f_148964_ = 5;
    public static final int f_148959_ = 32;
    private static final int f_148965_ = 32;
    boolean f_29080_;
    boolean f_29081_;
    public int f_29072_;
    private Vec3 f_29082_;
    private float f_29083_;
    private float f_29065_;
    private float f_29066_;
    private float f_29067_;
    private float f_29068_;
    private float f_29069_;
    PandaLookAtPlayerGoal f_29070_;
    static final Predicate<ItemEntity> f_29071_ = p_29133_ -> {
        ItemStack $$1 = p_29133_.m_32055_();
        return ($$1.m_150930_(Blocks.f_50571_.m_5456_()) || $$1.m_150930_(Blocks.f_50145_.m_5456_())) && p_29133_.m_6084_() && !p_29133_.m_32063_();
    };

    public Panda(EntityType<? extends Panda> p_29086_, Level p_29087_) {
        super((EntityType<? extends Animal>)p_29086_, p_29087_);
        this.f_21342_ = new PandaMoveControl(this);
        if (!this.m_6162_()) {
            this.m_21553_(true);
        }
    }

    @Override
    public boolean m_7066_(ItemStack p_29146_) {
        EquipmentSlot $$1 = Mob.m_147233_(p_29146_);
        if (!this.m_6844_($$1).m_41619_()) {
            return false;
        }
        return $$1 == EquipmentSlot.MAINHAND && super.m_7066_(p_29146_);
    }

    public int m_29148_() {
        return this.f_19804_.m_135370_(f_29073_);
    }

    public void m_29206_(int p_29207_) {
        this.f_19804_.m_135381_(f_29073_, p_29207_);
    }

    public boolean m_29149_() {
        return this.m_29218_(2);
    }

    public boolean m_29150_() {
        return this.m_29218_(8);
    }

    public void m_29208_(boolean p_29209_) {
        this.m_29134_(8, p_29209_);
    }

    public boolean m_29151_() {
        return this.m_29218_(16);
    }

    public void m_29212_(boolean p_29213_) {
        this.m_29134_(16, p_29213_);
    }

    public boolean m_29152_() {
        return this.f_19804_.m_135370_(f_29075_) > 0;
    }

    public void m_29216_(boolean p_29217_) {
        this.f_19804_.m_135381_(f_29075_, p_29217_ ? 1 : 0);
    }

    private int m_29170_() {
        return this.f_19804_.m_135370_(f_29075_);
    }

    private void m_29214_(int p_29215_) {
        this.f_19804_.m_135381_(f_29075_, p_29215_);
    }

    public void m_29220_(boolean p_29221_) {
        this.m_29134_(2, p_29221_);
        if (!p_29221_) {
            this.m_29210_(0);
        }
    }

    public int m_29153_() {
        return this.f_19804_.m_135370_(f_29074_);
    }

    public void m_29210_(int p_29211_) {
        this.f_19804_.m_135381_(f_29074_, p_29211_);
    }

    public Gene m_29154_() {
        return Gene.m_29248_(this.f_19804_.m_135370_(f_29076_).byteValue());
    }

    public void m_29099_(Gene p_29100_) {
        if (p_29100_.m_29247_() > 6) {
            p_29100_ = Gene.m_218234_(this.f_19796_);
        }
        this.f_19804_.m_135381_(f_29076_, (byte)p_29100_.m_29247_());
    }

    public Gene m_29155_() {
        return Gene.m_29248_(this.f_19804_.m_135370_(f_29077_).byteValue());
    }

    public void m_29116_(Gene p_29117_) {
        if (p_29117_.m_29247_() > 6) {
            p_29117_ = Gene.m_218234_(this.f_19796_);
        }
        this.f_19804_.m_135381_(f_29077_, (byte)p_29117_.m_29247_());
    }

    public boolean m_29156_() {
        return this.m_29218_(4);
    }

    public void m_29222_(boolean p_29223_) {
        this.m_29134_(4, p_29223_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_29073_, 0);
        this.f_19804_.m_135372_(f_29074_, 0);
        this.f_19804_.m_135372_(f_29076_, (byte)0);
        this.f_19804_.m_135372_(f_29077_, (byte)0);
        this.f_19804_.m_135372_(f_29078_, (byte)0);
        this.f_19804_.m_135372_(f_29075_, 0);
    }

    private boolean m_29218_(int p_29219_) {
        return (this.f_19804_.m_135370_(f_29078_) & p_29219_) != 0;
    }

    private void m_29134_(int p_29135_, boolean p_29136_) {
        byte $$2 = this.f_19804_.m_135370_(f_29078_);
        if (p_29136_) {
            this.f_19804_.m_135381_(f_29078_, (byte)($$2 | p_29135_));
        } else {
            this.f_19804_.m_135381_(f_29078_, (byte)($$2 & ~p_29135_));
        }
    }

    @Override
    public void m_7380_(CompoundTag p_29129_) {
        super.m_7380_(p_29129_);
        p_29129_.m_128359_("MainGene", this.m_29154_().m_29257_());
        p_29129_.m_128359_("HiddenGene", this.m_29155_().m_29257_());
    }

    @Override
    public void m_7378_(CompoundTag p_29115_) {
        super.m_7378_(p_29115_);
        this.m_29099_(Gene.m_29253_(p_29115_.m_128461_("MainGene")));
        this.m_29116_(Gene.m_29253_(p_29115_.m_128461_("HiddenGene")));
    }

    @Override
    @Nullable
    public AgeableMob m_142606_(ServerLevel p_148967_, AgeableMob p_148968_) {
        Panda $$2 = EntityType.f_20507_.m_20615_(p_148967_);
        if (p_148968_ instanceof Panda) {
            $$2.m_29103_(this, (Panda)p_148968_);
        }
        $$2.m_29166_();
        return $$2;
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(0, new FloatGoal(this));
        this.f_21345_.m_25352_(2, new PandaPanicGoal(this, 2.0));
        this.f_21345_.m_25352_(2, new PandaBreedGoal(this, 1.0));
        this.f_21345_.m_25352_(3, new PandaAttackGoal(this, (double)1.2f, true));
        this.f_21345_.m_25352_(4, new TemptGoal(this, 1.0, Ingredient.m_43929_(Blocks.f_50571_.m_5456_()), false));
        this.f_21345_.m_25352_(6, new PandaAvoidGoal<Player>(this, Player.class, 8.0f, 2.0, 2.0));
        this.f_21345_.m_25352_(6, new PandaAvoidGoal<Monster>(this, Monster.class, 4.0f, 2.0, 2.0));
        this.f_21345_.m_25352_(7, new PandaSitGoal());
        this.f_21345_.m_25352_(8, new PandaLieOnBackGoal(this));
        this.f_21345_.m_25352_(8, new PandaSneezeGoal(this));
        this.f_29070_ = new PandaLookAtPlayerGoal(this, Player.class, 6.0f);
        this.f_21345_.m_25352_(9, this.f_29070_);
        this.f_21345_.m_25352_(10, new RandomLookAroundGoal(this));
        this.f_21345_.m_25352_(12, new PandaRollGoal(this));
        this.f_21345_.m_25352_(13, new FollowParentGoal(this, 1.25));
        this.f_21345_.m_25352_(14, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.f_21346_.m_25352_(1, new PandaHurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
    }

    public static AttributeSupplier.Builder m_29157_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22279_, 0.15f).m_22268_(Attributes.f_22281_, 6.0);
    }

    public Gene m_29158_() {
        return Gene.m_29260_(this.m_29154_(), this.m_29155_());
    }

    public boolean m_29161_() {
        return this.m_29158_() == Gene.LAZY;
    }

    public boolean m_29162_() {
        return this.m_29158_() == Gene.WORRIED;
    }

    public boolean m_29163_() {
        return this.m_29158_() == Gene.PLAYFUL;
    }

    public boolean m_148973_() {
        return this.m_29158_() == Gene.BROWN;
    }

    public boolean m_29164_() {
        return this.m_29158_() == Gene.WEAK;
    }

    @Override
    public boolean m_5912_() {
        return this.m_29158_() == Gene.AGGRESSIVE;
    }

    @Override
    public boolean m_6573_(Player p_29107_) {
        return false;
    }

    @Override
    public boolean m_7327_(Entity p_29091_) {
        this.m_5496_(SoundEvents.f_12187_, 1.0f, 1.0f);
        if (!this.m_5912_()) {
            this.f_29081_ = true;
        }
        return super.m_7327_(p_29091_);
    }

    @Override
    public void m_8119_() {
        LivingEntity $$0;
        super.m_8119_();
        if (this.m_29162_()) {
            if (this.f_19853_.m_46470_() && !this.m_20069_()) {
                this.m_29208_(true);
                this.m_29216_(false);
            } else if (!this.m_29152_()) {
                this.m_29208_(false);
            }
        }
        if (($$0 = this.m_5448_()) == null) {
            this.f_29080_ = false;
            this.f_29081_ = false;
        }
        if (this.m_29148_() > 0) {
            if ($$0 != null) {
                this.m_21391_($$0, 90.0f, 90.0f);
            }
            if (this.m_29148_() == 29 || this.m_29148_() == 14) {
                this.m_5496_(SoundEvents.f_12183_, 1.0f, 1.0f);
            }
            this.m_29206_(this.m_29148_() - 1);
        }
        if (this.m_29149_()) {
            this.m_29210_(this.m_29153_() + 1);
            if (this.m_29153_() > 20) {
                this.m_29220_(false);
                this.m_29177_();
            } else if (this.m_29153_() == 1) {
                this.m_5496_(SoundEvents.f_12177_, 1.0f, 1.0f);
            }
        }
        if (this.m_29156_()) {
            this.m_29176_();
        } else {
            this.f_29072_ = 0;
        }
        if (this.m_29150_()) {
            this.m_146926_(0.0f);
        }
        this.m_29173_();
        this.m_29171_();
        this.m_29174_();
        this.m_29175_();
    }

    public boolean m_29165_() {
        return this.m_29162_() && this.f_19853_.m_46470_();
    }

    private void m_29171_() {
        if (!this.m_29152_() && this.m_29150_() && !this.m_29165_() && !this.m_6844_(EquipmentSlot.MAINHAND).m_41619_() && this.f_19796_.m_188503_(80) == 1) {
            this.m_29216_(true);
        } else if (this.m_6844_(EquipmentSlot.MAINHAND).m_41619_() || !this.m_29150_()) {
            this.m_29216_(false);
        }
        if (this.m_29152_()) {
            this.m_29172_();
            if (!this.f_19853_.f_46443_ && this.m_29170_() > 80 && this.f_19796_.m_188503_(20) == 1) {
                if (this.m_29170_() > 100 && this.m_29195_(this.m_6844_(EquipmentSlot.MAINHAND))) {
                    if (!this.f_19853_.f_46443_) {
                        this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
                        this.m_146850_(GameEvent.f_157806_);
                    }
                    this.m_29208_(false);
                }
                this.m_29216_(false);
                return;
            }
            this.m_29214_(this.m_29170_() + 1);
        }
    }

    private void m_29172_() {
        if (this.m_29170_() % 5 == 0) {
            this.m_5496_(SoundEvents.f_12181_, 0.5f + 0.5f * (float)this.f_19796_.m_188503_(2), (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.0f);
            for (int $$0 = 0; $$0 < 6; ++$$0) {
                Vec3 $$1 = new Vec3(((double)this.f_19796_.m_188501_() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, ((double)this.f_19796_.m_188501_() - 0.5) * 0.1);
                $$1 = $$1.m_82496_(-this.m_146909_() * ((float)Math.PI / 180));
                $$1 = $$1.m_82524_(-this.m_146908_() * ((float)Math.PI / 180));
                double $$2 = (double)(-this.f_19796_.m_188501_()) * 0.6 - 0.3;
                Vec3 $$3 = new Vec3(((double)this.f_19796_.m_188501_() - 0.5) * 0.8, $$2, 1.0 + ((double)this.f_19796_.m_188501_() - 0.5) * 0.4);
                $$3 = $$3.m_82524_(-this.f_20883_ * ((float)Math.PI / 180));
                $$3 = $$3.m_82520_(this.m_20185_(), this.m_20188_() + 1.0, this.m_20189_());
                this.f_19853_.m_7106_(new ItemParticleOption(ParticleTypes.f_123752_, this.m_6844_(EquipmentSlot.MAINHAND)), $$3.f_82479_, $$3.f_82480_, $$3.f_82481_, $$1.f_82479_, $$1.f_82480_ + 0.05, $$1.f_82481_);
            }
        }
    }

    private void m_29173_() {
        this.f_29065_ = this.f_29083_;
        this.f_29083_ = this.m_29150_() ? Math.min(1.0f, this.f_29083_ + 0.15f) : Math.max(0.0f, this.f_29083_ - 0.19f);
    }

    private void m_29174_() {
        this.f_29067_ = this.f_29066_;
        this.f_29066_ = this.m_29151_() ? Math.min(1.0f, this.f_29066_ + 0.15f) : Math.max(0.0f, this.f_29066_ - 0.19f);
    }

    private void m_29175_() {
        this.f_29069_ = this.f_29068_;
        this.f_29068_ = this.m_29156_() ? Math.min(1.0f, this.f_29068_ + 0.15f) : Math.max(0.0f, this.f_29068_ - 0.19f);
    }

    public float m_29224_(float p_29225_) {
        return Mth.m_14179_(p_29225_, this.f_29065_, this.f_29083_);
    }

    public float m_29226_(float p_29227_) {
        return Mth.m_14179_(p_29227_, this.f_29067_, this.f_29066_);
    }

    public float m_29088_(float p_29089_) {
        return Mth.m_14179_(p_29089_, this.f_29069_, this.f_29068_);
    }

    private void m_29176_() {
        ++this.f_29072_;
        if (this.f_29072_ > 32) {
            this.m_29222_(false);
            return;
        }
        if (!this.f_19853_.f_46443_) {
            Vec3 $$0 = this.m_20184_();
            if (this.f_29072_ == 1) {
                float $$1 = this.m_146908_() * ((float)Math.PI / 180);
                float $$2 = this.m_6162_() ? 0.1f : 0.2f;
                this.f_29082_ = new Vec3($$0.f_82479_ + (double)(-Mth.m_14031_($$1) * $$2), 0.0, $$0.f_82481_ + (double)(Mth.m_14089_($$1) * $$2));
                this.m_20256_(this.f_29082_.m_82520_(0.0, 0.27, 0.0));
            } else if ((float)this.f_29072_ == 7.0f || (float)this.f_29072_ == 15.0f || (float)this.f_29072_ == 23.0f) {
                this.m_20334_(0.0, this.f_19861_ ? 0.27 : $$0.f_82480_, 0.0);
            } else {
                this.m_20334_(this.f_29082_.f_82479_, $$0.f_82480_, this.f_29082_.f_82481_);
            }
        }
    }

    private void m_29177_() {
        Vec3 $$0 = this.m_20184_();
        this.f_19853_.m_7106_(ParticleTypes.f_123763_, this.m_20185_() - (double)(this.m_20205_() + 1.0f) * 0.5 * (double)Mth.m_14031_(this.f_20883_ * ((float)Math.PI / 180)), this.m_20188_() - (double)0.1f, this.m_20189_() + (double)(this.m_20205_() + 1.0f) * 0.5 * (double)Mth.m_14089_(this.f_20883_ * ((float)Math.PI / 180)), $$0.f_82479_, 0.0, $$0.f_82481_);
        this.m_5496_(SoundEvents.f_12178_, 1.0f, 1.0f);
        List<Panda> $$1 = this.f_19853_.m_45976_(Panda.class, this.m_20191_().m_82400_(10.0));
        for (Panda $$2 : $$1) {
            if ($$2.m_6162_() || !$$2.f_19861_ || $$2.m_20069_() || !$$2.m_29167_()) continue;
            $$2.m_6135_();
        }
        if (!this.f_19853_.m_5776_() && this.f_19796_.m_188503_(700) == 0 && this.f_19853_.m_46469_().m_46207_(GameRules.f_46135_)) {
            this.m_19998_(Items.f_42518_);
        }
    }

    @Override
    protected void m_7581_(ItemEntity p_29121_) {
        if (this.m_6844_(EquipmentSlot.MAINHAND).m_41619_() && f_29071_.test(p_29121_)) {
            this.m_21053_(p_29121_);
            ItemStack $$1 = p_29121_.m_32055_();
            this.m_8061_(EquipmentSlot.MAINHAND, $$1);
            this.m_21508_(EquipmentSlot.MAINHAND);
            this.m_7938_(p_29121_, $$1.m_41613_());
            p_29121_.m_146870_();
        }
    }

    @Override
    public boolean m_6469_(DamageSource p_29097_, float p_29098_) {
        if (!this.f_19853_.f_46443_) {
            this.m_29208_(false);
        }
        return super.m_6469_(p_29097_, p_29098_);
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_29109_, DifficultyInstance p_29110_, MobSpawnType p_29111_, @Nullable SpawnGroupData p_29112_, @Nullable CompoundTag p_29113_) {
        RandomSource $$5 = p_29109_.m_213780_();
        this.m_29099_(Gene.m_218234_($$5));
        this.m_29116_(Gene.m_218234_($$5));
        this.m_29166_();
        if (p_29112_ == null) {
            p_29112_ = new AgeableMob.AgeableMobGroupData(0.2f);
        }
        return super.m_6518_(p_29109_, p_29110_, p_29111_, p_29112_, p_29113_);
    }

    public void m_29103_(Panda p_29104_, @Nullable Panda p_29105_) {
        if (p_29105_ == null) {
            if (this.f_19796_.m_188499_()) {
                this.m_29099_(p_29104_.m_29178_());
                this.m_29116_(Gene.m_218234_(this.f_19796_));
            } else {
                this.m_29099_(Gene.m_218234_(this.f_19796_));
                this.m_29116_(p_29104_.m_29178_());
            }
        } else if (this.f_19796_.m_188499_()) {
            this.m_29099_(p_29104_.m_29178_());
            this.m_29116_(p_29105_.m_29178_());
        } else {
            this.m_29099_(p_29105_.m_29178_());
            this.m_29116_(p_29104_.m_29178_());
        }
        if (this.f_19796_.m_188503_(32) == 0) {
            this.m_29099_(Gene.m_218234_(this.f_19796_));
        }
        if (this.f_19796_.m_188503_(32) == 0) {
            this.m_29116_(Gene.m_218234_(this.f_19796_));
        }
    }

    private Gene m_29178_() {
        if (this.f_19796_.m_188499_()) {
            return this.m_29154_();
        }
        return this.m_29155_();
    }

    public void m_29166_() {
        if (this.m_29164_()) {
            this.m_21051_(Attributes.f_22276_).m_22100_(10.0);
        }
        if (this.m_29161_()) {
            this.m_21051_(Attributes.f_22279_).m_22100_(0.07f);
        }
    }

    void m_29179_() {
        if (!this.m_20069_()) {
            this.m_21564_(0.0f);
            this.m_21573_().m_26573_();
            this.m_29208_(true);
        }
    }

    @Override
    public InteractionResult m_6071_(Player p_29123_, InteractionHand p_29124_) {
        ItemStack $$2 = p_29123_.m_21120_(p_29124_);
        if (this.m_29165_()) {
            return InteractionResult.PASS;
        }
        if (this.m_29151_()) {
            this.m_29212_(false);
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        if (this.m_6898_($$2)) {
            if (this.m_5448_() != null) {
                this.f_29080_ = true;
            }
            if (this.m_6162_()) {
                this.m_142075_(p_29123_, p_29124_, $$2);
                this.m_146740_((int)((float)(-this.m_146764_() / 20) * 0.1f), true);
            } else if (!this.f_19853_.f_46443_ && this.m_146764_() == 0 && this.m_5957_()) {
                this.m_142075_(p_29123_, p_29124_, $$2);
                this.m_27595_(p_29123_);
            } else if (!(this.f_19853_.f_46443_ || this.m_29150_() || this.m_20069_())) {
                this.m_29179_();
                this.m_29216_(true);
                ItemStack $$3 = this.m_6844_(EquipmentSlot.MAINHAND);
                if (!$$3.m_41619_() && !p_29123_.m_150110_().f_35937_) {
                    this.m_19983_($$3);
                }
                this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack($$2.m_41720_(), 1));
                this.m_142075_(p_29123_, p_29124_, $$2);
            } else {
                return InteractionResult.PASS;
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    @Nullable
    protected SoundEvent m_7515_() {
        if (this.m_5912_()) {
            return SoundEvents.f_12184_;
        }
        if (this.m_29162_()) {
            return SoundEvents.f_12185_;
        }
        return SoundEvents.f_12179_;
    }

    @Override
    protected void m_7355_(BlockPos p_29126_, BlockState p_29127_) {
        this.m_5496_(SoundEvents.f_12182_, 0.15f, 1.0f);
    }

    @Override
    public boolean m_6898_(ItemStack p_29192_) {
        return p_29192_.m_150930_(Blocks.f_50571_.m_5456_());
    }

    private boolean m_29195_(ItemStack p_29196_) {
        return this.m_6898_(p_29196_) || p_29196_.m_150930_(Blocks.f_50145_.m_5456_());
    }

    @Override
    @Nullable
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12180_;
    }

    @Override
    @Nullable
    protected SoundEvent m_7975_(DamageSource p_29142_) {
        return SoundEvents.f_12186_;
    }

    public boolean m_29167_() {
        return !this.m_29151_() && !this.m_29165_() && !this.m_29152_() && !this.m_29156_() && !this.m_29150_();
    }

    static class PandaMoveControl
    extends MoveControl {
        private final Panda f_29316_;

        public PandaMoveControl(Panda p_29318_) {
            super(p_29318_);
            this.f_29316_ = p_29318_;
        }

        @Override
        public void m_8126_() {
            if (!this.f_29316_.m_29167_()) {
                return;
            }
            super.m_8126_();
        }
    }

    public static final class Gene
    extends Enum<Gene> {
        public static final /* enum */ Gene NORMAL = new Gene(0, "normal", false);
        public static final /* enum */ Gene LAZY = new Gene(1, "lazy", false);
        public static final /* enum */ Gene WORRIED = new Gene(2, "worried", false);
        public static final /* enum */ Gene PLAYFUL = new Gene(3, "playful", false);
        public static final /* enum */ Gene BROWN = new Gene(4, "brown", true);
        public static final /* enum */ Gene WEAK = new Gene(5, "weak", true);
        public static final /* enum */ Gene AGGRESSIVE = new Gene(6, "aggressive", false);
        private static final Gene[] f_29235_;
        private static final int f_148982_ = 6;
        private final int f_29236_;
        private final String f_29237_;
        private final boolean f_29238_;
        private static final /* synthetic */ Gene[] $VALUES;

        public static Gene[] values() {
            return (Gene[])$VALUES.clone();
        }

        public static Gene valueOf(String p_29265_) {
            return Enum.valueOf(Gene.class, p_29265_);
        }

        private Gene(int p_29244_, String p_29245_, boolean p_29246_) {
            this.f_29236_ = p_29244_;
            this.f_29237_ = p_29245_;
            this.f_29238_ = p_29246_;
        }

        public int m_29247_() {
            return this.f_29236_;
        }

        public String m_29257_() {
            return this.f_29237_;
        }

        public boolean m_29263_() {
            return this.f_29238_;
        }

        static Gene m_29260_(Gene p_29261_, Gene p_29262_) {
            if (p_29261_.m_29263_()) {
                if (p_29261_ == p_29262_) {
                    return p_29261_;
                }
                return NORMAL;
            }
            return p_29261_;
        }

        public static Gene m_29248_(int p_29249_) {
            if (p_29249_ < 0 || p_29249_ >= f_29235_.length) {
                p_29249_ = 0;
            }
            return f_29235_[p_29249_];
        }

        public static Gene m_29253_(String p_29254_) {
            for (Gene $$1 : Gene.values()) {
                if (!$$1.f_29237_.equals(p_29254_)) continue;
                return $$1;
            }
            return NORMAL;
        }

        public static Gene m_218234_(RandomSource p_218235_) {
            int $$1 = p_218235_.m_188503_(16);
            if ($$1 == 0) {
                return LAZY;
            }
            if ($$1 == 1) {
                return WORRIED;
            }
            if ($$1 == 2) {
                return PLAYFUL;
            }
            if ($$1 == 4) {
                return AGGRESSIVE;
            }
            if ($$1 < 9) {
                return WEAK;
            }
            if ($$1 < 11) {
                return BROWN;
            }
            return NORMAL;
        }

        private static /* synthetic */ Gene[] m_148983_() {
            return new Gene[]{NORMAL, LAZY, WORRIED, PLAYFUL, BROWN, WEAK, AGGRESSIVE};
        }

        static {
            $VALUES = Gene.m_148983_();
            f_29235_ = (Gene[])Arrays.stream(Gene.values()).sorted(Comparator.comparingInt(Gene::m_29247_)).toArray(Gene[]::new);
        }
    }

    static class PandaPanicGoal
    extends PanicGoal {
        private final Panda f_29320_;

        public PandaPanicGoal(Panda p_29322_, double p_29323_) {
            super(p_29322_, p_29323_);
            this.f_29320_ = p_29322_;
        }

        @Override
        protected boolean m_202729_() {
            return this.f_25684_.m_203117_() || this.f_25684_.m_6060_();
        }

        @Override
        public boolean m_8045_() {
            if (this.f_29320_.m_29150_()) {
                this.f_29320_.m_21573_().m_26573_();
                return false;
            }
            return super.m_8045_();
        }
    }

    static class PandaBreedGoal
    extends BreedGoal {
        private final Panda f_29282_;
        private int f_29283_;

        public PandaBreedGoal(Panda p_186221_, double p_186222_) {
            super(p_186221_, p_186222_);
            this.f_29282_ = p_186221_;
        }

        @Override
        public boolean m_8036_() {
            if (super.m_8036_() && this.f_29282_.m_29148_() == 0) {
                if (!this.m_29289_()) {
                    if (this.f_29283_ <= this.f_29282_.f_19797_) {
                        this.f_29282_.m_29206_(32);
                        this.f_29283_ = this.f_29282_.f_19797_ + 600;
                        if (this.f_29282_.m_6142_()) {
                            Player $$0 = this.f_25114_.m_45946_(f_29079_, this.f_29282_);
                            this.f_29282_.f_29070_.m_29312_($$0);
                        }
                    }
                    return false;
                }
                return true;
            }
            return false;
        }

        private boolean m_29289_() {
            BlockPos $$0 = this.f_29282_.m_20183_();
            BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
            for (int $$2 = 0; $$2 < 3; ++$$2) {
                for (int $$3 = 0; $$3 < 8; ++$$3) {
                    int $$4 = 0;
                    while ($$4 <= $$3) {
                        int $$5;
                        int n = $$5 = $$4 < $$3 && $$4 > -$$3 ? $$3 : 0;
                        while ($$5 <= $$3) {
                            $$1.m_122154_($$0, $$4, $$2, $$5);
                            if (this.f_25114_.m_8055_($$1).m_60713_(Blocks.f_50571_)) {
                                return true;
                            }
                            $$5 = $$5 > 0 ? -$$5 : 1 - $$5;
                        }
                        $$4 = $$4 > 0 ? -$$4 : 1 - $$4;
                    }
                }
            }
            return false;
        }
    }

    static class PandaAttackGoal
    extends MeleeAttackGoal {
        private final Panda f_29267_;

        public PandaAttackGoal(Panda p_29269_, double p_29270_, boolean p_29271_) {
            super(p_29269_, p_29270_, p_29271_);
            this.f_29267_ = p_29269_;
        }

        @Override
        public boolean m_8036_() {
            return this.f_29267_.m_29167_() && super.m_8036_();
        }
    }

    static class PandaAvoidGoal<T extends LivingEntity>
    extends AvoidEntityGoal<T> {
        private final Panda f_29273_;

        public PandaAvoidGoal(Panda p_29275_, Class<T> p_29276_, float p_29277_, double p_29278_, double p_29279_) {
            super(p_29275_, p_29276_, p_29277_, p_29278_, p_29279_, EntitySelector.f_20408_::test);
            this.f_29273_ = p_29275_;
        }

        @Override
        public boolean m_8036_() {
            return this.f_29273_.m_29162_() && this.f_29273_.m_29167_() && super.m_8036_();
        }
    }

    class PandaSitGoal
    extends Goal {
        private int f_29334_;

        public PandaSitGoal() {
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean m_8036_() {
            if (this.f_29334_ > Panda.this.f_19797_ || Panda.this.m_6162_() || Panda.this.m_20069_() || !Panda.this.m_29167_() || Panda.this.m_29148_() > 0) {
                return false;
            }
            List<ItemEntity> $$0 = Panda.this.f_19853_.m_6443_(ItemEntity.class, Panda.this.m_20191_().m_82377_(6.0, 6.0, 6.0), f_29071_);
            return !$$0.isEmpty() || !Panda.this.m_6844_(EquipmentSlot.MAINHAND).m_41619_();
        }

        @Override
        public boolean m_8045_() {
            if (Panda.this.m_20069_() || !Panda.this.m_29161_() && Panda.this.f_19796_.m_188503_(PandaSitGoal.m_186073_(600)) == 1) {
                return false;
            }
            return Panda.this.f_19796_.m_188503_(PandaSitGoal.m_186073_(2000)) != 1;
        }

        @Override
        public void m_8037_() {
            if (!Panda.this.m_29150_() && !Panda.this.m_6844_(EquipmentSlot.MAINHAND).m_41619_()) {
                Panda.this.m_29179_();
            }
        }

        @Override
        public void m_8056_() {
            List<ItemEntity> $$0 = Panda.this.f_19853_.m_6443_(ItemEntity.class, Panda.this.m_20191_().m_82377_(8.0, 8.0, 8.0), f_29071_);
            if (!$$0.isEmpty() && Panda.this.m_6844_(EquipmentSlot.MAINHAND).m_41619_()) {
                Panda.this.m_21573_().m_5624_($$0.get(0), 1.2f);
            } else if (!Panda.this.m_6844_(EquipmentSlot.MAINHAND).m_41619_()) {
                Panda.this.m_29179_();
            }
            this.f_29334_ = 0;
        }

        @Override
        public void m_8041_() {
            ItemStack $$0 = Panda.this.m_6844_(EquipmentSlot.MAINHAND);
            if (!$$0.m_41619_()) {
                Panda.this.m_19983_($$0);
                Panda.this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
                int $$1 = Panda.this.m_29161_() ? Panda.this.f_19796_.m_188503_(50) + 10 : Panda.this.f_19796_.m_188503_(150) + 10;
                this.f_29334_ = Panda.this.f_19797_ + $$1 * 20;
            }
            Panda.this.m_29208_(false);
        }
    }

    static class PandaLieOnBackGoal
    extends Goal {
        private final Panda f_29298_;
        private int f_29299_;

        public PandaLieOnBackGoal(Panda p_29301_) {
            this.f_29298_ = p_29301_;
        }

        @Override
        public boolean m_8036_() {
            return this.f_29299_ < this.f_29298_.f_19797_ && this.f_29298_.m_29161_() && this.f_29298_.m_29167_() && this.f_29298_.f_19796_.m_188503_(PandaLieOnBackGoal.m_186073_(400)) == 1;
        }

        @Override
        public boolean m_8045_() {
            if (this.f_29298_.m_20069_() || !this.f_29298_.m_29161_() && this.f_29298_.f_19796_.m_188503_(PandaLieOnBackGoal.m_186073_(600)) == 1) {
                return false;
            }
            return this.f_29298_.f_19796_.m_188503_(PandaLieOnBackGoal.m_186073_(2000)) != 1;
        }

        @Override
        public void m_8056_() {
            this.f_29298_.m_29212_(true);
            this.f_29299_ = 0;
        }

        @Override
        public void m_8041_() {
            this.f_29298_.m_29212_(false);
            this.f_29299_ = this.f_29298_.f_19797_ + 200;
        }
    }

    static class PandaSneezeGoal
    extends Goal {
        private final Panda f_29342_;

        public PandaSneezeGoal(Panda p_29344_) {
            this.f_29342_ = p_29344_;
        }

        @Override
        public boolean m_8036_() {
            if (!this.f_29342_.m_6162_() || !this.f_29342_.m_29167_()) {
                return false;
            }
            if (this.f_29342_.m_29164_() && this.f_29342_.f_19796_.m_188503_(PandaSneezeGoal.m_186073_(500)) == 1) {
                return true;
            }
            return this.f_29342_.f_19796_.m_188503_(PandaSneezeGoal.m_186073_(6000)) == 1;
        }

        @Override
        public boolean m_8045_() {
            return false;
        }

        @Override
        public void m_8056_() {
            this.f_29342_.m_29220_(true);
        }
    }

    static class PandaLookAtPlayerGoal
    extends LookAtPlayerGoal {
        private final Panda f_29306_;

        public PandaLookAtPlayerGoal(Panda p_29308_, Class<? extends LivingEntity> p_29309_, float p_29310_) {
            super(p_29308_, p_29309_, p_29310_);
            this.f_29306_ = p_29308_;
        }

        public void m_29312_(LivingEntity p_29313_) {
            this.f_25513_ = p_29313_;
        }

        @Override
        public boolean m_8045_() {
            return this.f_25513_ != null && super.m_8045_();
        }

        @Override
        public boolean m_8036_() {
            if (this.f_25512_.m_217043_().m_188501_() >= this.f_25515_) {
                return false;
            }
            if (this.f_25513_ == null) {
                this.f_25513_ = this.f_25516_ == Player.class ? this.f_25512_.f_19853_.m_45949_(this.f_25517_, this.f_25512_, this.f_25512_.m_20185_(), this.f_25512_.m_20188_(), this.f_25512_.m_20189_()) : this.f_25512_.f_19853_.m_45982_(this.f_25512_.f_19853_.m_6443_(this.f_25516_, this.f_25512_.m_20191_().m_82377_(this.f_25514_, 3.0, this.f_25514_), p_148985_ -> true), this.f_25517_, this.f_25512_, this.f_25512_.m_20185_(), this.f_25512_.m_20188_(), this.f_25512_.m_20189_());
            }
            return this.f_29306_.m_29167_() && this.f_25513_ != null;
        }

        @Override
        public void m_8037_() {
            if (this.f_25513_ != null) {
                super.m_8037_();
            }
        }
    }

    static class PandaRollGoal
    extends Goal {
        private final Panda f_29326_;

        public PandaRollGoal(Panda p_29328_) {
            this.f_29326_ = p_29328_;
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }

        @Override
        public boolean m_8036_() {
            int $$4;
            if (!this.f_29326_.m_6162_() && !this.f_29326_.m_29163_() || !this.f_29326_.f_19861_) {
                return false;
            }
            if (!this.f_29326_.m_29167_()) {
                return false;
            }
            float $$0 = this.f_29326_.m_146908_() * ((float)Math.PI / 180);
            float $$1 = -Mth.m_14031_($$0);
            float $$2 = Mth.m_14089_($$0);
            int $$3 = (double)Math.abs($$1) > 0.5 ? Mth.m_14205_($$1) : 0;
            int n = $$4 = (double)Math.abs($$2) > 0.5 ? Mth.m_14205_($$2) : 0;
            if (this.f_29326_.f_19853_.m_8055_(this.f_29326_.m_20183_().m_7918_($$3, -1, $$4)).m_60795_()) {
                return true;
            }
            if (this.f_29326_.m_29163_() && this.f_29326_.f_19796_.m_188503_(PandaRollGoal.m_186073_(60)) == 1) {
                return true;
            }
            return this.f_29326_.f_19796_.m_188503_(PandaRollGoal.m_186073_(500)) == 1;
        }

        @Override
        public boolean m_8045_() {
            return false;
        }

        @Override
        public void m_8056_() {
            this.f_29326_.m_29222_(true);
        }

        @Override
        public boolean m_6767_() {
            return false;
        }
    }

    static class PandaHurtByTargetGoal
    extends HurtByTargetGoal {
        private final Panda f_29290_;

        public PandaHurtByTargetGoal(Panda p_29292_, Class<?> ... p_29293_) {
            super(p_29292_, p_29293_);
            this.f_29290_ = p_29292_;
        }

        @Override
        public boolean m_8045_() {
            if (this.f_29290_.f_29080_ || this.f_29290_.f_29081_) {
                this.f_29290_.m_6710_(null);
                return false;
            }
            return super.m_8045_();
        }

        @Override
        protected void m_5766_(Mob p_29295_, LivingEntity p_29296_) {
            if (p_29295_ instanceof Panda && ((Panda)p_29295_).m_5912_()) {
                p_29295_.m_6710_(p_29296_);
            }
        }
    }
}

