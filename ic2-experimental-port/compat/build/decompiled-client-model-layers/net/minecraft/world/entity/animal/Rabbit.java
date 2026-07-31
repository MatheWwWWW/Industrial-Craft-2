/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal;

import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarrotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class Rabbit
extends Animal {
    public static final double f_149016_ = 0.6;
    public static final double f_149017_ = 0.8;
    public static final double f_149018_ = 1.0;
    public static final double f_149019_ = 2.2;
    public static final double f_149020_ = 1.4;
    private static final EntityDataAccessor<Integer> f_29647_ = SynchedEntityData.m_135353_(Rabbit.class, EntityDataSerializers.f_135028_);
    public static final int f_149021_ = 0;
    public static final int f_149022_ = 1;
    public static final int f_149023_ = 2;
    public static final int f_149024_ = 3;
    public static final int f_149025_ = 4;
    public static final int f_149026_ = 5;
    public static final int f_149027_ = 99;
    private static final ResourceLocation f_29648_ = new ResourceLocation("killer_bunny");
    public static final int f_149028_ = 8;
    public static final int f_149029_ = 8;
    private static final int f_149030_ = 40;
    private int f_29649_;
    private int f_29650_;
    private boolean f_29651_;
    private int f_29652_;
    int f_29653_;

    public Rabbit(EntityType<? extends Rabbit> p_29656_, Level p_29657_) {
        super((EntityType<? extends Animal>)p_29656_, p_29657_);
        this.f_21343_ = new RabbitJumpControl(this);
        this.f_21342_ = new RabbitMoveControl(this);
        this.m_29725_(0.0);
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(1, new FloatGoal(this));
        this.f_21345_.m_25352_(1, new ClimbOnTopOfPowderSnowGoal(this, this.f_19853_));
        this.f_21345_.m_25352_(1, new RabbitPanicGoal(this, 2.2));
        this.f_21345_.m_25352_(2, new BreedGoal(this, 0.8));
        this.f_21345_.m_25352_(3, new TemptGoal(this, 1.0, Ingredient.m_43929_(Items.f_42619_, Items.f_42677_, Blocks.f_50111_), false));
        this.f_21345_.m_25352_(4, new RabbitAvoidEntityGoal<Player>(this, Player.class, 8.0f, 2.2, 2.2));
        this.f_21345_.m_25352_(4, new RabbitAvoidEntityGoal<Wolf>(this, Wolf.class, 10.0f, 2.2, 2.2));
        this.f_21345_.m_25352_(4, new RabbitAvoidEntityGoal<Monster>(this, Monster.class, 4.0f, 2.2, 2.2));
        this.f_21345_.m_25352_(5, new RaidGardenGoal(this));
        this.f_21345_.m_25352_(6, new WaterAvoidingRandomStrollGoal(this, 0.6));
        this.f_21345_.m_25352_(11, new LookAtPlayerGoal(this, Player.class, 10.0f));
    }

    @Override
    protected float m_6118_() {
        if (this.f_19862_ || this.f_21342_.m_24995_() && this.f_21342_.m_25001_() > this.m_20186_() + 0.5) {
            return 0.5f;
        }
        Path $$0 = this.f_21344_.m_26570_();
        if ($$0 != null && !$$0.m_77392_()) {
            Vec3 $$1 = $$0.m_77380_(this);
            if ($$1.f_82480_ > this.m_20186_() + 0.5) {
                return 0.5f;
            }
        }
        if (this.f_21342_.m_24999_() <= 0.6) {
            return 0.2f;
        }
        return 0.3f;
    }

    @Override
    protected void m_6135_() {
        double $$1;
        super.m_6135_();
        double $$0 = this.f_21342_.m_24999_();
        if ($$0 > 0.0 && ($$1 = this.m_20184_().m_165925_()) < 0.01) {
            this.m_19920_(0.1f, new Vec3(0.0, 0.0, 1.0));
        }
        if (!this.f_19853_.f_46443_) {
            this.f_19853_.m_7605_(this, (byte)1);
        }
    }

    public float m_29735_(float p_29736_) {
        if (this.f_29650_ == 0) {
            return 0.0f;
        }
        return ((float)this.f_29649_ + p_29736_) / (float)this.f_29650_;
    }

    public void m_29725_(double p_29726_) {
        this.m_21573_().m_26517_(p_29726_);
        this.f_21342_.m_6849_(this.f_21342_.m_25000_(), this.f_21342_.m_25001_(), this.f_21342_.m_25002_(), p_29726_);
    }

    @Override
    public void m_6862_(boolean p_29732_) {
        super.m_6862_(p_29732_);
        if (p_29732_) {
            this.m_5496_(this.m_29718_(), this.m_6121_(), ((this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.0f) * 0.8f);
        }
    }

    public void m_29716_() {
        this.m_6862_(true);
        this.f_29650_ = 10;
        this.f_29649_ = 0;
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_29647_, 0);
    }

    @Override
    public void m_8024_() {
        if (this.f_29652_ > 0) {
            --this.f_29652_;
        }
        if (this.f_29653_ > 0) {
            this.f_29653_ -= this.f_19796_.m_188503_(3);
            if (this.f_29653_ < 0) {
                this.f_29653_ = 0;
            }
        }
        if (this.f_19861_) {
            RabbitJumpControl $$1;
            LivingEntity $$0;
            if (!this.f_29651_) {
                this.m_6862_(false);
                this.m_29723_();
            }
            if (this.m_29719_() == 99 && this.f_29652_ == 0 && ($$0 = this.m_5448_()) != null && this.m_20280_($$0) < 16.0) {
                this.m_29686_($$0.m_20185_(), $$0.m_20189_());
                this.f_21342_.m_6849_($$0.m_20185_(), $$0.m_20186_(), $$0.m_20189_(), this.f_21342_.m_24999_());
                this.m_29716_();
                this.f_29651_ = true;
            }
            if (!($$1 = (RabbitJumpControl)this.f_21343_).m_29761_()) {
                if (this.f_21342_.m_24995_() && this.f_29652_ == 0) {
                    Path $$2 = this.f_21344_.m_26570_();
                    Vec3 $$3 = new Vec3(this.f_21342_.m_25000_(), this.f_21342_.m_25001_(), this.f_21342_.m_25002_());
                    if ($$2 != null && !$$2.m_77392_()) {
                        $$3 = $$2.m_77380_(this);
                    }
                    this.m_29686_($$3.f_82479_, $$3.f_82481_);
                    this.m_29716_();
                }
            } else if (!$$1.m_29762_()) {
                this.m_29720_();
            }
        }
        this.f_29651_ = this.f_19861_;
    }

    @Override
    public boolean m_5843_() {
        return false;
    }

    private void m_29686_(double p_29687_, double p_29688_) {
        this.m_146922_((float)(Mth.m_14136_(p_29688_ - this.m_20189_(), p_29687_ - this.m_20185_()) * 57.2957763671875) - 90.0f);
    }

    private void m_29720_() {
        ((RabbitJumpControl)this.f_21343_).m_29758_(true);
    }

    private void m_29721_() {
        ((RabbitJumpControl)this.f_21343_).m_29758_(false);
    }

    private void m_29722_() {
        this.f_29652_ = this.f_21342_.m_24999_() < 2.2 ? 10 : 1;
    }

    private void m_29723_() {
        this.m_29722_();
        this.m_29721_();
    }

    @Override
    public void m_8107_() {
        super.m_8107_();
        if (this.f_29649_ != this.f_29650_) {
            ++this.f_29649_;
        } else if (this.f_29650_ != 0) {
            this.f_29649_ = 0;
            this.f_29650_ = 0;
            this.m_6862_(false);
        }
    }

    public static AttributeSupplier.Builder m_29717_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 3.0).m_22268_(Attributes.f_22279_, 0.3f);
    }

    @Override
    public void m_7380_(CompoundTag p_29697_) {
        super.m_7380_(p_29697_);
        p_29697_.m_128405_("RabbitType", this.m_29719_());
        p_29697_.m_128405_("MoreCarrotTicks", this.f_29653_);
    }

    @Override
    public void m_7378_(CompoundTag p_29684_) {
        super.m_7378_(p_29684_);
        this.m_29733_(p_29684_.m_128451_("RabbitType"));
        this.f_29653_ = p_29684_.m_128451_("MoreCarrotTicks");
    }

    protected SoundEvent m_29718_() {
        return SoundEvents.f_12354_;
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12297_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_29715_) {
        return SoundEvents.f_12353_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12352_;
    }

    @Override
    public boolean m_7327_(Entity p_29659_) {
        if (this.m_29719_() == 99) {
            this.m_5496_(SoundEvents.f_12298_, 1.0f, (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.0f);
            return p_29659_.m_6469_(DamageSource.m_19370_(this), 8.0f);
        }
        return p_29659_.m_6469_(DamageSource.m_19370_(this), 3.0f);
    }

    @Override
    public SoundSource m_5720_() {
        return this.m_29719_() == 99 ? SoundSource.HOSTILE : SoundSource.NEUTRAL;
    }

    private static boolean m_149037_(ItemStack p_149038_) {
        return p_149038_.m_150930_(Items.f_42619_) || p_149038_.m_150930_(Items.f_42677_) || p_149038_.m_150930_(Blocks.f_50111_.m_5456_());
    }

    @Override
    public Rabbit m_142606_(ServerLevel p_149035_, AgeableMob p_149036_) {
        Rabbit $$2 = EntityType.f_20517_.m_20615_(p_149035_);
        int $$3 = this.m_29675_(p_149035_);
        if (this.f_19796_.m_188503_(20) != 0) {
            $$3 = p_149036_ instanceof Rabbit && this.f_19796_.m_188499_() ? ((Rabbit)p_149036_).m_29719_() : this.m_29719_();
        }
        $$2.m_29733_($$3);
        return $$2;
    }

    @Override
    public boolean m_6898_(ItemStack p_29729_) {
        return Rabbit.m_149037_(p_29729_);
    }

    public int m_29719_() {
        return this.f_19804_.m_135370_(f_29647_);
    }

    public void m_29733_(int p_29734_) {
        if (p_29734_ == 99) {
            this.m_21051_(Attributes.f_22284_).m_22100_(8.0);
            this.f_21345_.m_25352_(4, new EvilRabbitAttackGoal(this));
            this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
            this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, true));
            this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<Wolf>((Mob)this, Wolf.class, true));
            if (!this.m_8077_()) {
                this.m_6593_(Component.m_237115_(Util.m_137492_("entity", f_29648_)));
            }
        }
        this.f_19804_.m_135381_(f_29647_, p_29734_);
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_29678_, DifficultyInstance p_29679_, MobSpawnType p_29680_, @Nullable SpawnGroupData p_29681_, @Nullable CompoundTag p_29682_) {
        int $$5 = this.m_29675_(p_29678_);
        if (p_29681_ instanceof RabbitGroupData) {
            $$5 = ((RabbitGroupData)p_29681_).f_29749_;
        } else {
            p_29681_ = new RabbitGroupData($$5);
        }
        this.m_29733_($$5);
        return super.m_6518_(p_29678_, p_29679_, p_29680_, p_29681_, p_29682_);
    }

    private int m_29675_(LevelAccessor p_29676_) {
        Holder<Biome> $$1 = p_29676_.m_204166_(this.m_20183_());
        int $$2 = p_29676_.m_213780_().m_188503_(100);
        if ($$1.m_203334_().m_47530_() == Biome.Precipitation.SNOW) {
            return $$2 < 80 ? 1 : 3;
        }
        if ($$1.m_203656_(BiomeTags.f_215810_)) {
            return 4;
        }
        return $$2 < 50 ? 0 : ($$2 < 90 ? 5 : 2);
    }

    public static boolean m_218255_(EntityType<Rabbit> p_218256_, LevelAccessor p_218257_, MobSpawnType p_218258_, BlockPos p_218259_, RandomSource p_218260_) {
        return p_218257_.m_8055_(p_218259_.m_7495_()).m_204336_(BlockTags.f_184234_) && Rabbit.m_186209_(p_218257_, p_218259_);
    }

    boolean m_29724_() {
        return this.f_29653_ == 0;
    }

    @Override
    public void m_7822_(byte p_29663_) {
        if (p_29663_ == 1) {
            this.m_20076_();
            this.f_29650_ = 10;
            this.f_29649_ = 0;
        } else {
            super.m_7822_(p_29663_);
        }
    }

    @Override
    public Vec3 m_7939_() {
        return new Vec3(0.0, 0.6f * this.m_20192_(), this.m_20205_() * 0.4f);
    }

    @Override
    public /* synthetic */ AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return this.m_142606_(serverLevel, ageableMob);
    }

    public static class RabbitJumpControl
    extends JumpControl {
        private final Rabbit f_29753_;
        private boolean f_29754_;

        public RabbitJumpControl(Rabbit p_186229_) {
            super(p_186229_);
            this.f_29753_ = p_186229_;
        }

        public boolean m_29761_() {
            return this.f_24897_;
        }

        public boolean m_29762_() {
            return this.f_29754_;
        }

        public void m_29758_(boolean p_29759_) {
            this.f_29754_ = p_29759_;
        }

        @Override
        public void m_8124_() {
            if (this.f_24897_) {
                this.f_29753_.m_29716_();
                this.f_24897_ = false;
            }
        }
    }

    static class RabbitMoveControl
    extends MoveControl {
        private final Rabbit f_29763_;
        private double f_29764_;

        public RabbitMoveControl(Rabbit p_29766_) {
            super(p_29766_);
            this.f_29763_ = p_29766_;
        }

        @Override
        public void m_8126_() {
            if (this.f_29763_.f_19861_ && !this.f_29763_.f_20899_ && !((RabbitJumpControl)this.f_29763_.f_21343_).m_29761_()) {
                this.f_29763_.m_29725_(0.0);
            } else if (this.m_24995_()) {
                this.f_29763_.m_29725_(this.f_29764_);
            }
            super.m_8126_();
        }

        @Override
        public void m_6849_(double p_29769_, double p_29770_, double p_29771_, double p_29772_) {
            if (this.f_29763_.m_20069_()) {
                p_29772_ = 1.5;
            }
            super.m_6849_(p_29769_, p_29770_, p_29771_, p_29772_);
            if (p_29772_ > 0.0) {
                this.f_29764_ = p_29772_;
            }
        }
    }

    static class RabbitPanicGoal
    extends PanicGoal {
        private final Rabbit f_29773_;

        public RabbitPanicGoal(Rabbit p_29775_, double p_29776_) {
            super(p_29775_, p_29776_);
            this.f_29773_ = p_29775_;
        }

        @Override
        public void m_8037_() {
            super.m_8037_();
            this.f_29773_.m_29725_(this.f_25685_);
        }
    }

    static class RabbitAvoidEntityGoal<T extends LivingEntity>
    extends AvoidEntityGoal<T> {
        private final Rabbit f_29741_;

        public RabbitAvoidEntityGoal(Rabbit p_29743_, Class<T> p_29744_, float p_29745_, double p_29746_, double p_29747_) {
            super(p_29743_, p_29744_, p_29745_, p_29746_, p_29747_);
            this.f_29741_ = p_29743_;
        }

        @Override
        public boolean m_8036_() {
            return this.f_29741_.m_29719_() != 99 && super.m_8036_();
        }
    }

    static class RaidGardenGoal
    extends MoveToBlockGoal {
        private final Rabbit f_29778_;
        private boolean f_29779_;
        private boolean f_29780_;

        public RaidGardenGoal(Rabbit p_29782_) {
            super(p_29782_, 0.7f, 16);
            this.f_29778_ = p_29782_;
        }

        @Override
        public boolean m_8036_() {
            if (this.f_25600_ <= 0) {
                if (!this.f_29778_.f_19853_.m_46469_().m_46207_(GameRules.f_46132_)) {
                    return false;
                }
                this.f_29780_ = false;
                this.f_29779_ = this.f_29778_.m_29724_();
                this.f_29779_ = true;
            }
            return super.m_8036_();
        }

        @Override
        public boolean m_8045_() {
            return this.f_29780_ && super.m_8045_();
        }

        @Override
        public void m_8037_() {
            super.m_8037_();
            this.f_29778_.m_21563_().m_24950_((double)this.f_25602_.m_123341_() + 0.5, this.f_25602_.m_123342_() + 1, (double)this.f_25602_.m_123343_() + 0.5, 10.0f, this.f_29778_.m_8132_());
            if (this.m_25625_()) {
                Level $$0 = this.f_29778_.f_19853_;
                BlockPos $$1 = this.f_25602_.m_7494_();
                BlockState $$2 = $$0.m_8055_($$1);
                Block $$3 = $$2.m_60734_();
                if (this.f_29780_ && $$3 instanceof CarrotBlock) {
                    int $$4 = $$2.m_61143_(CarrotBlock.f_52244_);
                    if ($$4 == 0) {
                        $$0.m_7731_($$1, Blocks.f_50016_.m_49966_(), 2);
                        $$0.m_46953_($$1, true, this.f_29778_);
                    } else {
                        $$0.m_7731_($$1, (BlockState)$$2.m_61124_(CarrotBlock.f_52244_, $$4 - 1), 2);
                        $$0.m_46796_(2001, $$1, Block.m_49956_($$2));
                    }
                    this.f_29778_.f_29653_ = 40;
                }
                this.f_29780_ = false;
                this.f_25600_ = 10;
            }
        }

        @Override
        protected boolean m_6465_(LevelReader p_29785_, BlockPos p_29786_) {
            BlockState $$2 = p_29785_.m_8055_(p_29786_);
            if ($$2.m_60713_(Blocks.f_50093_) && this.f_29779_ && !this.f_29780_ && ($$2 = p_29785_.m_8055_(p_29786_.m_7494_())).m_60734_() instanceof CarrotBlock && ((CarrotBlock)$$2.m_60734_()).m_52307_($$2)) {
                this.f_29780_ = true;
                return true;
            }
            return false;
        }
    }

    static class EvilRabbitAttackGoal
    extends MeleeAttackGoal {
        public EvilRabbitAttackGoal(Rabbit p_29738_) {
            super(p_29738_, 1.4, true);
        }

        @Override
        protected double m_6639_(LivingEntity p_29740_) {
            return 4.0f + p_29740_.m_20205_();
        }
    }

    public static class RabbitGroupData
    extends AgeableMob.AgeableMobGroupData {
        public final int f_29749_;

        public RabbitGroupData(int p_29751_) {
            super(1.0f);
            this.f_29749_ = p_29751_;
        }
    }
}

