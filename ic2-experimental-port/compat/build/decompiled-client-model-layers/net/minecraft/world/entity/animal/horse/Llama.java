/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal.horse;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LlamaFollowCaravanGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.RunAroundLikeCrazyGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Llama
extends AbstractChestedHorse
implements RangedAttackMob {
    private static final int f_149535_ = 5;
    private static final int f_149536_ = 4;
    private static final Ingredient f_30744_ = Ingredient.m_43929_(Items.f_42405_, Blocks.f_50335_.m_5456_());
    private static final EntityDataAccessor<Integer> f_30745_ = SynchedEntityData.m_135353_(Llama.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> f_30746_ = SynchedEntityData.m_135353_(Llama.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> f_30747_ = SynchedEntityData.m_135353_(Llama.class, EntityDataSerializers.f_135028_);
    boolean f_30741_;
    @Nullable
    private Llama f_30742_;
    @Nullable
    private Llama f_30743_;

    public Llama(EntityType<? extends Llama> p_30750_, Level p_30751_) {
        super((EntityType<? extends AbstractChestedHorse>)p_30750_, p_30751_);
    }

    public boolean m_7565_() {
        return false;
    }

    private void m_30840_(int p_30841_) {
        this.f_19804_.m_135381_(f_30745_, Math.max(1, Math.min(5, p_30841_)));
    }

    private void m_218817_(RandomSource p_218818_) {
        int $$1 = p_218818_.m_188501_() < 0.04f ? 5 : 3;
        this.m_30840_(1 + p_218818_.m_188503_($$1));
    }

    public int m_30823_() {
        return this.f_19804_.m_135370_(f_30745_);
    }

    @Override
    public void m_7380_(CompoundTag p_30793_) {
        super.m_7380_(p_30793_);
        p_30793_.m_128405_("Variant", this.m_30825_());
        p_30793_.m_128405_("Strength", this.m_30823_());
        if (!this.f_30520_.m_8020_(1).m_41619_()) {
            p_30793_.m_128365_("DecorItem", this.f_30520_.m_8020_(1).m_41739_(new CompoundTag()));
        }
    }

    @Override
    public void m_7378_(CompoundTag p_30780_) {
        this.m_30840_(p_30780_.m_128451_("Strength"));
        super.m_7378_(p_30780_);
        this.m_30838_(p_30780_.m_128451_("Variant"));
        if (p_30780_.m_128425_("DecorItem", 10)) {
            this.f_30520_.m_6836_(1, ItemStack.m_41712_(p_30780_.m_128469_("DecorItem")));
        }
        this.m_7493_();
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(0, new FloatGoal(this));
        this.f_21345_.m_25352_(1, new RunAroundLikeCrazyGoal(this, 1.2));
        this.f_21345_.m_25352_(2, new LlamaFollowCaravanGoal(this, 2.1f));
        this.f_21345_.m_25352_(3, new RangedAttackGoal(this, 1.25, 40, 20.0f));
        this.f_21345_.m_25352_(3, new PanicGoal(this, 1.2));
        this.f_21345_.m_25352_(4, new BreedGoal(this, 1.0));
        this.f_21345_.m_25352_(5, new TemptGoal(this, 1.25, Ingredient.m_43929_(Items.f_42129_), false));
        this.f_21345_.m_25352_(6, new FollowParentGoal(this, 1.0));
        this.f_21345_.m_25352_(7, new WaterAvoidingRandomStrollGoal(this, 0.7));
        this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.f_21345_.m_25352_(9, new RandomLookAroundGoal(this));
        this.f_21346_.m_25352_(1, new LlamaHurtByTargetGoal(this));
        this.f_21346_.m_25352_(2, new LlamaAttackWolfGoal(this));
    }

    public static AttributeSupplier.Builder m_30824_() {
        return Llama.m_30501_().m_22268_(Attributes.f_22277_, 40.0);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_30745_, 0);
        this.f_19804_.m_135372_(f_30746_, -1);
        this.f_19804_.m_135372_(f_30747_, 0);
    }

    public int m_30825_() {
        return Mth.m_14045_(this.f_19804_.m_135370_(f_30747_), 0, 3);
    }

    public void m_30838_(int p_30839_) {
        this.f_19804_.m_135381_(f_30747_, p_30839_);
    }

    @Override
    protected int m_7506_() {
        if (this.m_30502_()) {
            return 2 + 3 * this.m_7488_();
        }
        return super.m_7506_();
    }

    @Override
    public void m_7332_(Entity p_30830_) {
        if (!this.m_20363_(p_30830_)) {
            return;
        }
        float $$1 = Mth.m_14089_(this.f_20883_ * ((float)Math.PI / 180));
        float $$2 = Mth.m_14031_(this.f_20883_ * ((float)Math.PI / 180));
        float $$3 = 0.3f;
        p_30830_.m_6034_(this.m_20185_() + (double)(0.3f * $$2), this.m_20186_() + this.m_6048_() + p_30830_.m_6049_(), this.m_20189_() - (double)(0.3f * $$1));
    }

    @Override
    public double m_6048_() {
        return (double)this.m_20206_() * 0.6;
    }

    @Override
    @Nullable
    public LivingEntity m_6688_() {
        return null;
    }

    @Override
    public boolean m_6898_(ItemStack p_30832_) {
        return f_30744_.test(p_30832_);
    }

    @Override
    protected boolean m_5994_(Player p_30796_, ItemStack p_30797_) {
        SoundEvent $$6;
        int $$2 = 0;
        int $$3 = 0;
        float $$4 = 0.0f;
        boolean $$5 = false;
        if (p_30797_.m_150930_(Items.f_42405_)) {
            $$2 = 10;
            $$3 = 3;
            $$4 = 2.0f;
        } else if (p_30797_.m_150930_(Blocks.f_50335_.m_5456_())) {
            $$2 = 90;
            $$3 = 6;
            $$4 = 10.0f;
            if (this.m_30614_() && this.m_146764_() == 0 && this.m_5957_()) {
                $$5 = true;
                this.m_27595_(p_30796_);
            }
        }
        if (this.m_21223_() < this.m_21233_() && $$4 > 0.0f) {
            this.m_5634_($$4);
            $$5 = true;
        }
        if (this.m_6162_() && $$2 > 0) {
            this.f_19853_.m_7106_(ParticleTypes.f_123748_, this.m_20208_(1.0), this.m_20187_() + 0.5, this.m_20262_(1.0), 0.0, 0.0, 0.0);
            if (!this.f_19853_.f_46443_) {
                this.m_146758_($$2);
            }
            $$5 = true;
        }
        if ($$3 > 0 && ($$5 || !this.m_30614_()) && this.m_30624_() < this.m_7555_()) {
            $$5 = true;
            if (!this.f_19853_.f_46443_) {
                this.m_30653_($$3);
            }
        }
        if ($$5 && !this.m_20067_() && ($$6 = this.m_7872_()) != null) {
            this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_7872_(), this.m_5720_(), 1.0f, 1.0f + (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f);
        }
        return $$5;
    }

    @Override
    protected boolean m_6107_() {
        return this.m_21224_() || this.m_30617_();
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_30774_, DifficultyInstance p_30775_, MobSpawnType p_30776_, @Nullable SpawnGroupData p_30777_, @Nullable CompoundTag p_30778_) {
        int $$7;
        RandomSource $$5 = p_30774_.m_213780_();
        this.m_218817_($$5);
        if (p_30777_ instanceof LlamaGroupData) {
            int $$6 = ((LlamaGroupData)p_30777_).f_30847_;
        } else {
            $$7 = $$5.m_188503_(4);
            p_30777_ = new LlamaGroupData($$7);
        }
        this.m_30838_($$7);
        return super.m_6518_(p_30774_, p_30775_, p_30776_, p_30777_, p_30778_);
    }

    @Override
    protected SoundEvent m_7871_() {
        return SoundEvents.f_12093_;
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12092_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_30803_) {
        return SoundEvents.f_12097_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12095_;
    }

    @Override
    @Nullable
    protected SoundEvent m_7872_() {
        return SoundEvents.f_12096_;
    }

    @Override
    protected void m_7355_(BlockPos p_30790_, BlockState p_30791_) {
        this.m_5496_(SoundEvents.f_12099_, 0.15f, 1.0f);
    }

    @Override
    protected void m_7609_() {
        this.m_5496_(SoundEvents.f_12094_, 1.0f, (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.0f);
    }

    @Override
    public void m_7564_() {
        SoundEvent $$0 = this.m_7871_();
        if ($$0 != null) {
            this.m_5496_($$0, this.m_6121_(), this.m_6100_());
        }
    }

    @Override
    public int m_7488_() {
        return this.m_30823_();
    }

    @Override
    public boolean m_7482_() {
        return true;
    }

    @Override
    public boolean m_7481_() {
        return !this.f_30520_.m_8020_(1).m_41619_();
    }

    @Override
    public boolean m_6010_(ItemStack p_30834_) {
        return p_30834_.m_204117_(ItemTags.f_215867_);
    }

    @Override
    public boolean m_6741_() {
        return false;
    }

    @Override
    public void m_5757_(Container p_30760_) {
        DyeColor $$1 = this.m_30826_();
        super.m_5757_(p_30760_);
        DyeColor $$2 = this.m_30826_();
        if (this.f_19797_ > 20 && $$2 != null && $$2 != $$1) {
            this.m_5496_(SoundEvents.f_12100_, 0.5f, 1.0f);
        }
    }

    @Override
    protected void m_7493_() {
        if (this.f_19853_.f_46443_) {
            return;
        }
        super.m_7493_();
        this.m_30771_(Llama.m_30835_(this.f_30520_.m_8020_(1)));
    }

    private void m_30771_(@Nullable DyeColor p_30772_) {
        this.f_19804_.m_135381_(f_30746_, p_30772_ == null ? -1 : p_30772_.m_41060_());
    }

    @Nullable
    private static DyeColor m_30835_(ItemStack p_30836_) {
        Block $$1 = Block.m_49814_(p_30836_.m_41720_());
        if ($$1 instanceof WoolCarpetBlock) {
            return ((WoolCarpetBlock)$$1).m_58309_();
        }
        return null;
    }

    @Nullable
    public DyeColor m_30826_() {
        int $$0 = this.f_19804_.m_135370_(f_30746_);
        return $$0 == -1 ? null : DyeColor.m_41053_($$0);
    }

    @Override
    public int m_7555_() {
        return 30;
    }

    @Override
    public boolean m_7848_(Animal p_30765_) {
        return p_30765_ != this && p_30765_ instanceof Llama && this.m_30628_() && ((Llama)p_30765_).m_30628_();
    }

    @Override
    public Llama m_142606_(ServerLevel p_149545_, AgeableMob p_149546_) {
        Llama $$2 = this.m_7127_();
        this.m_149508_(p_149546_, $$2);
        Llama $$3 = (Llama)p_149546_;
        int $$4 = this.f_19796_.m_188503_(Math.max(this.m_30823_(), $$3.m_30823_())) + 1;
        if (this.f_19796_.m_188501_() < 0.03f) {
            ++$$4;
        }
        $$2.m_30840_($$4);
        $$2.m_30838_(this.f_19796_.m_188499_() ? this.m_30825_() : $$3.m_30825_());
        return $$2;
    }

    protected Llama m_7127_() {
        return EntityType.f_20466_.m_20615_(this.f_19853_);
    }

    private void m_30827_(LivingEntity p_30828_) {
        LlamaSpit $$1 = new LlamaSpit(this.f_19853_, this);
        double $$2 = p_30828_.m_20185_() - this.m_20185_();
        double $$3 = p_30828_.m_20227_(0.3333333333333333) - $$1.m_20186_();
        double $$4 = p_30828_.m_20189_() - this.m_20189_();
        double $$5 = Math.sqrt($$2 * $$2 + $$4 * $$4) * (double)0.2f;
        $$1.m_6686_($$2, $$3 + $$5, $$4, 1.5f, 10.0f);
        if (!this.m_20067_()) {
            this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12098_, this.m_5720_(), 1.0f, 1.0f + (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f);
        }
        this.f_19853_.m_7967_($$1);
        this.f_30741_ = true;
    }

    void m_30752_(boolean p_30753_) {
        this.f_30741_ = p_30753_;
    }

    @Override
    public boolean m_142535_(float p_149538_, float p_149539_, DamageSource p_149540_) {
        int $$3 = this.m_5639_(p_149538_, p_149539_);
        if ($$3 <= 0) {
            return false;
        }
        if (p_149538_ >= 6.0f) {
            this.m_6469_(p_149540_, $$3);
            if (this.m_20160_()) {
                for (Entity $$4 : this.m_146897_()) {
                    $$4.m_6469_(p_149540_, $$3);
                }
            }
        }
        this.m_21229_();
        return true;
    }

    public void m_30809_() {
        if (this.f_30742_ != null) {
            this.f_30742_.f_30743_ = null;
        }
        this.f_30742_ = null;
    }

    public void m_30766_(Llama p_30767_) {
        this.f_30742_ = p_30767_;
        this.f_30742_.f_30743_ = this;
    }

    public boolean m_30810_() {
        return this.f_30743_ != null;
    }

    public boolean m_30811_() {
        return this.f_30742_ != null;
    }

    @Nullable
    public Llama m_30812_() {
        return this.f_30742_;
    }

    @Override
    protected double m_5823_() {
        return 2.0;
    }

    @Override
    protected void m_7567_() {
        if (!this.m_30811_() && this.m_6162_()) {
            super.m_7567_();
        }
    }

    @Override
    public boolean m_7559_() {
        return false;
    }

    @Override
    public void m_6504_(LivingEntity p_30762_, float p_30763_) {
        this.m_30827_(p_30762_);
    }

    @Override
    public Vec3 m_7939_() {
        return new Vec3(0.0, 0.75 * (double)this.m_20192_(), (double)this.m_20205_() * 0.5);
    }

    @Override
    public /* synthetic */ AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return this.m_142606_(serverLevel, ageableMob);
    }

    @Override
    @Nullable
    public /* synthetic */ Entity m_6688_() {
        return this.m_6688_();
    }

    static class LlamaHurtByTargetGoal
    extends HurtByTargetGoal {
        public LlamaHurtByTargetGoal(Llama p_30854_) {
            super(p_30854_, new Class[0]);
        }

        @Override
        public boolean m_8045_() {
            if (this.f_26135_ instanceof Llama) {
                Llama $$0 = (Llama)this.f_26135_;
                if ($$0.f_30741_) {
                    $$0.m_30752_(false);
                    return false;
                }
            }
            return super.m_8045_();
        }
    }

    static class LlamaAttackWolfGoal
    extends NearestAttackableTargetGoal<Wolf> {
        public LlamaAttackWolfGoal(Llama p_30843_) {
            super(p_30843_, Wolf.class, 16, false, true, p_30845_ -> !((Wolf)p_30845_).m_21824_());
        }

        @Override
        protected double m_7623_() {
            return super.m_7623_() * 0.25;
        }
    }

    static class LlamaGroupData
    extends AgeableMob.AgeableMobGroupData {
        public final int f_30847_;

        LlamaGroupData(int p_30849_) {
            super(true);
            this.f_30847_ = p_30849_;
        }
    }
}

