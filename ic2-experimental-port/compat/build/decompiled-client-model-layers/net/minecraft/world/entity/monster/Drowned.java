/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class Drowned
extends Zombie
implements RangedAttackMob {
    public static final float f_149692_ = 0.03f;
    boolean f_32342_;
    protected final WaterBoundPathNavigation f_32340_;
    protected final GroundPathNavigation f_32341_;

    public Drowned(EntityType<? extends Drowned> p_32344_, Level p_32345_) {
        super((EntityType<? extends Zombie>)p_32344_, p_32345_);
        this.f_19793_ = 1.0f;
        this.f_21342_ = new DrownedMoveControl(this);
        this.m_21441_(BlockPathTypes.WATER, 0.0f);
        this.f_32340_ = new WaterBoundPathNavigation(this, p_32345_);
        this.f_32341_ = new GroundPathNavigation(this, p_32345_);
    }

    @Override
    protected void m_6878_() {
        this.f_21345_.m_25352_(1, new DrownedGoToWaterGoal(this, 1.0));
        this.f_21345_.m_25352_(2, new DrownedTridentAttackGoal(this, 1.0, 40, 10.0f));
        this.f_21345_.m_25352_(2, new DrownedAttackGoal(this, 1.0, false));
        this.f_21345_.m_25352_(5, new DrownedGoToBeachGoal(this, 1.0));
        this.f_21345_.m_25352_(6, new DrownedSwimUpGoal(this, 1.0, this.f_19853_.m_5736_()));
        this.f_21345_.m_25352_(7, new RandomStrollGoal(this, 1.0));
        this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, Drowned.class).m_26044_(ZombifiedPiglin.class));
        this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::m_32395_));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<AbstractVillager>((Mob)this, AbstractVillager.class, false));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<IronGolem>((Mob)this, IronGolem.class, true));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<Axolotl>((Mob)this, Axolotl.class, true, false));
        this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal<Turtle>(this, Turtle.class, 10, true, false, Turtle.f_30122_));
    }

    @Override
    public SpawnGroupData m_6518_(ServerLevelAccessor p_32372_, DifficultyInstance p_32373_, MobSpawnType p_32374_, @Nullable SpawnGroupData p_32375_, @Nullable CompoundTag p_32376_) {
        p_32375_ = super.m_6518_(p_32372_, p_32373_, p_32374_, p_32375_, p_32376_);
        if (this.m_6844_(EquipmentSlot.OFFHAND).m_41619_() && p_32372_.m_213780_().m_188501_() < 0.03f) {
            this.m_8061_(EquipmentSlot.OFFHAND, new ItemStack(Items.f_42715_));
            this.m_21508_(EquipmentSlot.OFFHAND);
        }
        return p_32375_;
    }

    public static boolean m_218955_(EntityType<Drowned> p_218956_, ServerLevelAccessor p_218957_, MobSpawnType p_218958_, BlockPos p_218959_, RandomSource p_218960_) {
        boolean $$6;
        if (!p_218957_.m_6425_(p_218959_.m_7495_()).m_205070_(FluidTags.f_13131_)) {
            return false;
        }
        Holder<Biome> $$5 = p_218957_.m_204166_(p_218959_);
        boolean bl = $$6 = p_218957_.m_46791_() != Difficulty.PEACEFUL && Drowned.m_219009_(p_218957_, p_218959_, p_218960_) && (p_218958_ == MobSpawnType.SPAWNER || p_218957_.m_6425_(p_218959_).m_205070_(FluidTags.f_13131_));
        if ($$5.m_203656_(BiomeTags.f_215814_)) {
            return p_218960_.m_188503_(15) == 0 && $$6;
        }
        return p_218960_.m_188503_(40) == 0 && Drowned.m_32366_(p_218957_, p_218959_) && $$6;
    }

    private static boolean m_32366_(LevelAccessor p_32367_, BlockPos p_32368_) {
        return p_32368_.m_123342_() < p_32367_.m_5736_() - 5;
    }

    @Override
    protected boolean m_7586_() {
        return false;
    }

    @Override
    protected SoundEvent m_7515_() {
        if (this.m_20069_()) {
            return SoundEvents.f_11816_;
        }
        return SoundEvents.f_11815_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_32386_) {
        if (this.m_20069_()) {
            return SoundEvents.f_11820_;
        }
        return SoundEvents.f_11819_;
    }

    @Override
    protected SoundEvent m_5592_() {
        if (this.m_20069_()) {
            return SoundEvents.f_11818_;
        }
        return SoundEvents.f_11817_;
    }

    @Override
    protected SoundEvent m_7660_() {
        return SoundEvents.f_11875_;
    }

    @Override
    protected SoundEvent m_5501_() {
        return SoundEvents.f_11876_;
    }

    @Override
    protected ItemStack m_5728_() {
        return ItemStack.f_41583_;
    }

    @Override
    protected void m_213945_(RandomSource p_218953_, DifficultyInstance p_218954_) {
        if ((double)p_218953_.m_188501_() > 0.9) {
            int $$2 = p_218953_.m_188503_(16);
            if ($$2 < 10) {
                this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42713_));
            } else {
                this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42523_));
            }
        }
    }

    @Override
    protected boolean m_7808_(ItemStack p_32364_, ItemStack p_32365_) {
        if (p_32365_.m_150930_(Items.f_42715_)) {
            return false;
        }
        if (p_32365_.m_150930_(Items.f_42713_)) {
            if (p_32364_.m_150930_(Items.f_42713_)) {
                return p_32364_.m_41773_() < p_32365_.m_41773_();
            }
            return false;
        }
        if (p_32364_.m_150930_(Items.f_42713_)) {
            return true;
        }
        return super.m_7808_(p_32364_, p_32365_);
    }

    @Override
    protected boolean m_7593_() {
        return false;
    }

    @Override
    public boolean m_6914_(LevelReader p_32370_) {
        return p_32370_.m_45784_(this);
    }

    public boolean m_32395_(@Nullable LivingEntity p_32396_) {
        if (p_32396_ != null) {
            return !this.f_19853_.m_46461_() || p_32396_.m_20069_();
        }
        return false;
    }

    @Override
    public boolean m_6063_() {
        return !this.m_6069_();
    }

    boolean m_32392_() {
        if (this.f_32342_) {
            return true;
        }
        LivingEntity $$0 = this.m_5448_();
        return $$0 != null && $$0.m_20069_();
    }

    @Override
    public void m_7023_(Vec3 p_32394_) {
        if (this.m_6142_() && this.m_20069_() && this.m_32392_()) {
            this.m_19920_(0.01f, p_32394_);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.9));
        } else {
            super.m_7023_(p_32394_);
        }
    }

    @Override
    public void m_5844_() {
        if (!this.f_19853_.f_46443_) {
            if (this.m_6142_() && this.m_20069_() && this.m_32392_()) {
                this.f_21344_ = this.f_32340_;
                this.m_20282_(true);
            } else {
                this.f_21344_ = this.f_32341_;
                this.m_20282_(false);
            }
        }
    }

    protected boolean m_32391_() {
        double $$2;
        BlockPos $$1;
        Path $$0 = this.m_21573_().m_26570_();
        return $$0 != null && ($$1 = $$0.m_77406_()) != null && ($$2 = this.m_20275_($$1.m_123341_(), $$1.m_123342_(), $$1.m_123343_())) < 4.0;
    }

    @Override
    public void m_6504_(LivingEntity p_32356_, float p_32357_) {
        ThrownTrident $$2 = new ThrownTrident(this.f_19853_, (LivingEntity)this, new ItemStack(Items.f_42713_));
        double $$3 = p_32356_.m_20185_() - this.m_20185_();
        double $$4 = p_32356_.m_20227_(0.3333333333333333) - $$2.m_20186_();
        double $$5 = p_32356_.m_20189_() - this.m_20189_();
        double $$6 = Math.sqrt($$3 * $$3 + $$5 * $$5);
        $$2.m_6686_($$3, $$4 + $$6 * (double)0.2f, $$5, 1.6f, 14 - this.f_19853_.m_46791_().m_19028_() * 4);
        this.m_5496_(SoundEvents.f_11821_, 1.0f, 1.0f / (this.m_217043_().m_188501_() * 0.4f + 0.8f));
        this.f_19853_.m_7967_($$2);
    }

    public void m_32398_(boolean p_32399_) {
        this.f_32342_ = p_32399_;
    }

    static class DrownedMoveControl
    extends MoveControl {
        private final Drowned f_32431_;

        public DrownedMoveControl(Drowned p_32433_) {
            super(p_32433_);
            this.f_32431_ = p_32433_;
        }

        @Override
        public void m_8126_() {
            LivingEntity $$0 = this.f_32431_.m_5448_();
            if (this.f_32431_.m_32392_() && this.f_32431_.m_20069_()) {
                if ($$0 != null && $$0.m_20186_() > this.f_32431_.m_20186_() || this.f_32431_.f_32342_) {
                    this.f_32431_.m_20256_(this.f_32431_.m_20184_().m_82520_(0.0, 0.002, 0.0));
                }
                if (this.f_24981_ != MoveControl.Operation.MOVE_TO || this.f_32431_.m_21573_().m_26571_()) {
                    this.f_32431_.m_7910_(0.0f);
                    return;
                }
                double $$1 = this.f_24975_ - this.f_32431_.m_20185_();
                double $$2 = this.f_24976_ - this.f_32431_.m_20186_();
                double $$3 = this.f_24977_ - this.f_32431_.m_20189_();
                double $$4 = Math.sqrt($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
                $$2 /= $$4;
                float $$5 = (float)(Mth.m_14136_($$3, $$1) * 57.2957763671875) - 90.0f;
                this.f_32431_.m_146922_(this.m_24991_(this.f_32431_.m_146908_(), $$5, 90.0f));
                this.f_32431_.f_20883_ = this.f_32431_.m_146908_();
                float $$6 = (float)(this.f_24978_ * this.f_32431_.m_21133_(Attributes.f_22279_));
                float $$7 = Mth.m_14179_(0.125f, this.f_32431_.m_6113_(), $$6);
                this.f_32431_.m_7910_($$7);
                this.f_32431_.m_20256_(this.f_32431_.m_20184_().m_82520_((double)$$7 * $$1 * 0.005, (double)$$7 * $$2 * 0.1, (double)$$7 * $$3 * 0.005));
            } else {
                if (!this.f_32431_.f_19861_) {
                    this.f_32431_.m_20256_(this.f_32431_.m_20184_().m_82520_(0.0, -0.008, 0.0));
                }
                super.m_8126_();
            }
        }
    }

    static class DrownedGoToWaterGoal
    extends Goal {
        private final PathfinderMob f_32418_;
        private double f_32419_;
        private double f_32420_;
        private double f_32421_;
        private final double f_32422_;
        private final Level f_32423_;

        public DrownedGoToWaterGoal(PathfinderMob p_32425_, double p_32426_) {
            this.f_32418_ = p_32425_;
            this.f_32422_ = p_32426_;
            this.f_32423_ = p_32425_.f_19853_;
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean m_8036_() {
            if (!this.f_32423_.m_46461_()) {
                return false;
            }
            if (this.f_32418_.m_20069_()) {
                return false;
            }
            Vec3 $$0 = this.m_32430_();
            if ($$0 == null) {
                return false;
            }
            this.f_32419_ = $$0.f_82479_;
            this.f_32420_ = $$0.f_82480_;
            this.f_32421_ = $$0.f_82481_;
            return true;
        }

        @Override
        public boolean m_8045_() {
            return !this.f_32418_.m_21573_().m_26571_();
        }

        @Override
        public void m_8056_() {
            this.f_32418_.m_21573_().m_26519_(this.f_32419_, this.f_32420_, this.f_32421_, this.f_32422_);
        }

        @Nullable
        private Vec3 m_32430_() {
            RandomSource $$0 = this.f_32418_.m_217043_();
            BlockPos $$1 = this.f_32418_.m_20183_();
            for (int $$2 = 0; $$2 < 10; ++$$2) {
                BlockPos $$3 = $$1.m_7918_($$0.m_188503_(20) - 10, 2 - $$0.m_188503_(8), $$0.m_188503_(20) - 10);
                if (!this.f_32423_.m_8055_($$3).m_60713_(Blocks.f_49990_)) continue;
                return Vec3.m_82539_($$3);
            }
            return null;
        }
    }

    static class DrownedTridentAttackGoal
    extends RangedAttackGoal {
        private final Drowned f_32448_;

        public DrownedTridentAttackGoal(RangedAttackMob p_32450_, double p_32451_, int p_32452_, float p_32453_) {
            super(p_32450_, p_32451_, p_32452_, p_32453_);
            this.f_32448_ = (Drowned)p_32450_;
        }

        @Override
        public boolean m_8036_() {
            return super.m_8036_() && this.f_32448_.m_21205_().m_150930_(Items.f_42713_);
        }

        @Override
        public void m_8056_() {
            super.m_8056_();
            this.f_32448_.m_21561_(true);
            this.f_32448_.m_6672_(InteractionHand.MAIN_HAND);
        }

        @Override
        public void m_8041_() {
            super.m_8041_();
            this.f_32448_.m_5810_();
            this.f_32448_.m_21561_(false);
        }
    }

    static class DrownedAttackGoal
    extends ZombieAttackGoal {
        private final Drowned f_32400_;

        public DrownedAttackGoal(Drowned p_32402_, double p_32403_, boolean p_32404_) {
            super(p_32402_, p_32403_, p_32404_);
            this.f_32400_ = p_32402_;
        }

        @Override
        public boolean m_8036_() {
            return super.m_8036_() && this.f_32400_.m_32395_(this.f_32400_.m_5448_());
        }

        @Override
        public boolean m_8045_() {
            return super.m_8045_() && this.f_32400_.m_32395_(this.f_32400_.m_5448_());
        }
    }

    static class DrownedGoToBeachGoal
    extends MoveToBlockGoal {
        private final Drowned f_32407_;

        public DrownedGoToBeachGoal(Drowned p_32409_, double p_32410_) {
            super(p_32409_, p_32410_, 8, 2);
            this.f_32407_ = p_32409_;
        }

        @Override
        public boolean m_8036_() {
            return super.m_8036_() && !this.f_32407_.f_19853_.m_46461_() && this.f_32407_.m_20069_() && this.f_32407_.m_20186_() >= (double)(this.f_32407_.f_19853_.m_5736_() - 3);
        }

        @Override
        public boolean m_8045_() {
            return super.m_8045_();
        }

        @Override
        protected boolean m_6465_(LevelReader p_32413_, BlockPos p_32414_) {
            BlockPos $$2 = p_32414_.m_7494_();
            if (!p_32413_.m_46859_($$2) || !p_32413_.m_46859_($$2.m_7494_())) {
                return false;
            }
            return p_32413_.m_8055_(p_32414_).m_60634_(p_32413_, p_32414_, this.f_32407_);
        }

        @Override
        public void m_8056_() {
            this.f_32407_.m_32398_(false);
            this.f_32407_.f_21344_ = this.f_32407_.f_32341_;
            super.m_8056_();
        }

        @Override
        public void m_8041_() {
            super.m_8041_();
        }
    }

    static class DrownedSwimUpGoal
    extends Goal {
        private final Drowned f_32435_;
        private final double f_32436_;
        private final int f_32437_;
        private boolean f_32438_;

        public DrownedSwimUpGoal(Drowned p_32440_, double p_32441_, int p_32442_) {
            this.f_32435_ = p_32440_;
            this.f_32436_ = p_32441_;
            this.f_32437_ = p_32442_;
        }

        @Override
        public boolean m_8036_() {
            return !this.f_32435_.f_19853_.m_46461_() && this.f_32435_.m_20069_() && this.f_32435_.m_20186_() < (double)(this.f_32437_ - 2);
        }

        @Override
        public boolean m_8045_() {
            return this.m_8036_() && !this.f_32438_;
        }

        @Override
        public void m_8037_() {
            if (this.f_32435_.m_20186_() < (double)(this.f_32437_ - 1) && (this.f_32435_.m_21573_().m_26571_() || this.f_32435_.m_32391_())) {
                Vec3 $$0 = DefaultRandomPos.m_148412_(this.f_32435_, 4, 8, new Vec3(this.f_32435_.m_20185_(), this.f_32437_ - 1, this.f_32435_.m_20189_()), 1.5707963705062866);
                if ($$0 == null) {
                    this.f_32438_ = true;
                    return;
                }
                this.f_32435_.m_21573_().m_26519_($$0.f_82479_, $$0.f_82480_, $$0.f_82481_, this.f_32436_);
            }
        }

        @Override
        public void m_8056_() {
            this.f_32435_.m_32398_(true);
            this.f_32438_ = false;
        }

        @Override
        public void m_8041_() {
            this.f_32435_.m_32398_(false);
        }
    }
}

