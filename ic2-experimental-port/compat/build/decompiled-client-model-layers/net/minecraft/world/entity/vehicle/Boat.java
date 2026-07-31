/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.vehicle;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Boat
extends Entity {
    private static final EntityDataAccessor<Integer> f_38282_ = SynchedEntityData.m_135353_(Boat.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> f_38283_ = SynchedEntityData.m_135353_(Boat.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Float> f_38284_ = SynchedEntityData.m_135353_(Boat.class, EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Integer> f_38285_ = SynchedEntityData.m_135353_(Boat.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> f_38286_ = SynchedEntityData.m_135353_(Boat.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> f_38287_ = SynchedEntityData.m_135353_(Boat.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Integer> f_38262_ = SynchedEntityData.m_135353_(Boat.class, EntityDataSerializers.f_135028_);
    public static final int f_150268_ = 0;
    public static final int f_150269_ = 1;
    private static final int f_150266_ = 60;
    private static final float f_150267_ = 0.3926991f;
    public static final double f_150270_ = 0.7853981852531433;
    public static final int f_150271_ = 60;
    private final float[] f_38263_ = new float[2];
    private float f_38264_;
    private float f_38265_;
    private float f_38266_;
    private int f_38267_;
    private double f_38268_;
    private double f_38269_;
    private double f_38270_;
    private double f_38271_;
    private double f_38272_;
    private boolean f_38273_;
    private boolean f_38274_;
    private boolean f_38275_;
    private boolean f_38276_;
    private double f_38277_;
    private float f_38278_;
    private Status f_38279_;
    private Status f_38280_;
    private double f_38281_;
    private boolean f_38257_;
    private boolean f_38258_;
    private float f_38259_;
    private float f_38260_;
    private float f_38261_;

    public Boat(EntityType<? extends Boat> p_38290_, Level p_38291_) {
        super(p_38290_, p_38291_);
        this.f_19850_ = true;
    }

    public Boat(Level p_38293_, double p_38294_, double p_38295_, double p_38296_) {
        this((EntityType<? extends Boat>)EntityType.f_20552_, p_38293_);
        this.m_6034_(p_38294_, p_38295_, p_38296_);
        this.f_19854_ = p_38294_;
        this.f_19855_ = p_38295_;
        this.f_19856_ = p_38296_;
    }

    @Override
    protected float m_6380_(Pose p_38327_, EntityDimensions p_38328_) {
        return p_38328_.f_20378_;
    }

    @Override
    protected Entity.MovementEmission m_142319_() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    protected void m_8097_() {
        this.f_19804_.m_135372_(f_38282_, 0);
        this.f_19804_.m_135372_(f_38283_, 1);
        this.f_19804_.m_135372_(f_38284_, Float.valueOf(0.0f));
        this.f_19804_.m_135372_(f_38285_, Type.OAK.ordinal());
        this.f_19804_.m_135372_(f_38286_, false);
        this.f_19804_.m_135372_(f_38287_, false);
        this.f_19804_.m_135372_(f_38262_, 0);
    }

    @Override
    public boolean m_7337_(Entity p_38376_) {
        return Boat.m_38323_(this, p_38376_);
    }

    public static boolean m_38323_(Entity p_38324_, Entity p_38325_) {
        return (p_38325_.m_5829_() || p_38325_.m_6094_()) && !p_38324_.m_20365_(p_38325_);
    }

    @Override
    public boolean m_5829_() {
        return true;
    }

    @Override
    public boolean m_6094_() {
        return true;
    }

    @Override
    protected Vec3 m_7643_(Direction.Axis p_38335_, BlockUtil.FoundRectangle p_38336_) {
        return LivingEntity.m_21289_(super.m_7643_(p_38335_, p_38336_));
    }

    @Override
    public double m_6048_() {
        return -0.1;
    }

    @Override
    public boolean m_6469_(DamageSource p_38319_, float p_38320_) {
        boolean $$2;
        if (this.m_6673_(p_38319_)) {
            return false;
        }
        if (this.f_19853_.f_46443_ || this.m_213877_()) {
            return true;
        }
        this.m_38362_(-this.m_38386_());
        this.m_38354_(10);
        this.m_38311_(this.m_38384_() + p_38320_ * 10.0f);
        this.m_5834_();
        this.m_146852_(GameEvent.f_223706_, p_38319_.m_7639_());
        boolean bl = $$2 = p_38319_.m_7639_() instanceof Player && ((Player)p_38319_.m_7639_()).m_150110_().f_35937_;
        if ($$2 || this.m_38384_() > 40.0f) {
            if (!$$2 && this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
                this.m_213560_(p_38319_);
            }
            this.m_146870_();
        }
        return true;
    }

    protected void m_213560_(DamageSource p_219862_) {
        this.m_19998_(this.m_38369_());
    }

    @Override
    public void m_6845_(boolean p_38381_) {
        if (!this.f_19853_.f_46443_) {
            this.f_38257_ = true;
            this.f_38258_ = p_38381_;
            if (this.m_38397_() == 0) {
                this.m_38366_(60);
            }
        }
        this.f_19853_.m_7106_(ParticleTypes.f_123769_, this.m_20185_() + (double)this.f_19796_.m_188501_(), this.m_20186_() + 0.7, this.m_20189_() + (double)this.f_19796_.m_188501_(), 0.0, 0.0, 0.0);
        if (this.f_19796_.m_188503_(20) == 0) {
            this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_5509_(), this.m_5720_(), 1.0f, 0.8f + 0.4f * this.f_19796_.m_188501_(), false);
            this.m_146852_(GameEvent.f_157784_, this.m_6688_());
        }
    }

    @Override
    public void m_7334_(Entity p_38373_) {
        if (p_38373_ instanceof Boat) {
            if (p_38373_.m_20191_().f_82289_ < this.m_20191_().f_82292_) {
                super.m_7334_(p_38373_);
            }
        } else if (p_38373_.m_20191_().f_82289_ <= this.m_20191_().f_82289_) {
            super.m_7334_(p_38373_);
        }
    }

    public Item m_38369_() {
        switch (this.m_38387_()) {
            default: {
                return Items.f_42453_;
            }
            case SPRUCE: {
                return Items.f_42742_;
            }
            case BIRCH: {
                return Items.f_42743_;
            }
            case JUNGLE: {
                return Items.f_42744_;
            }
            case ACACIA: {
                return Items.f_42745_;
            }
            case DARK_OAK: {
                return Items.f_42746_;
            }
            case MANGROVE: 
        }
        return Items.f_220204_;
    }

    @Override
    public void m_6053_() {
        this.m_38362_(-this.m_38386_());
        this.m_38354_(10);
        this.m_38311_(this.m_38384_() * 11.0f);
    }

    @Override
    public boolean m_6087_() {
        return !this.m_213877_();
    }

    @Override
    public void m_6453_(double p_38299_, double p_38300_, double p_38301_, float p_38302_, float p_38303_, int p_38304_, boolean p_38305_) {
        this.f_38268_ = p_38299_;
        this.f_38269_ = p_38300_;
        this.f_38270_ = p_38301_;
        this.f_38271_ = p_38302_;
        this.f_38272_ = p_38303_;
        this.f_38267_ = 10;
    }

    @Override
    public Direction m_6374_() {
        return this.m_6350_().m_122427_();
    }

    @Override
    public void m_8119_() {
        this.f_38280_ = this.f_38279_;
        this.f_38279_ = this.m_38392_();
        this.f_38265_ = this.f_38279_ == Status.UNDER_WATER || this.f_38279_ == Status.UNDER_FLOWING_WATER ? (this.f_38265_ += 1.0f) : 0.0f;
        if (!this.f_19853_.f_46443_ && this.f_38265_ >= 60.0f) {
            this.m_20153_();
        }
        if (this.m_38385_() > 0) {
            this.m_38354_(this.m_38385_() - 1);
        }
        if (this.m_38384_() > 0.0f) {
            this.m_38311_(this.m_38384_() - 1.0f);
        }
        super.m_8119_();
        this.m_38391_();
        if (this.m_6109_()) {
            if (!(this.m_146895_() instanceof Player)) {
                this.m_38339_(false, false);
            }
            this.m_38395_();
            if (this.f_19853_.f_46443_) {
                this.m_38396_();
                this.f_19853_.m_5503_(new ServerboundPaddleBoatPacket(this.m_38313_(0), this.m_38313_(1)));
            }
            this.m_6478_(MoverType.SELF, this.m_20184_());
        } else {
            this.m_20256_(Vec3.f_82478_);
        }
        this.m_38388_();
        for (int $$0 = 0; $$0 <= 1; ++$$0) {
            if (this.m_38313_($$0)) {
                SoundEvent $$1;
                if (!this.m_20067_() && (double)(this.f_38263_[$$0] % ((float)Math.PI * 2)) <= 0.7853981852531433 && (double)((this.f_38263_[$$0] + 0.3926991f) % ((float)Math.PI * 2)) >= 0.7853981852531433 && ($$1 = this.m_38370_()) != null) {
                    Vec3 $$2 = this.m_20252_(1.0f);
                    double $$3 = $$0 == 1 ? -$$2.f_82481_ : $$2.f_82481_;
                    double $$4 = $$0 == 1 ? $$2.f_82479_ : -$$2.f_82479_;
                    this.f_19853_.m_6263_(null, this.m_20185_() + $$3, this.m_20186_(), this.m_20189_() + $$4, $$1, this.m_5720_(), 1.0f, 0.8f + 0.4f * this.f_19796_.m_188501_());
                }
                int n = $$0;
                this.f_38263_[n] = this.f_38263_[n] + 0.3926991f;
                continue;
            }
            this.f_38263_[$$0] = 0.0f;
        }
        this.m_20101_();
        List<Entity> $$5 = this.f_19853_.m_6249_(this, this.m_20191_().m_82377_(0.2f, -0.01f, 0.2f), EntitySelector.m_20421_(this));
        if (!$$5.isEmpty()) {
            boolean $$6 = !this.f_19853_.f_46443_ && !(this.m_6688_() instanceof Player);
            for (int $$7 = 0; $$7 < $$5.size(); ++$$7) {
                Entity $$8 = $$5.get($$7);
                if ($$8.m_20363_(this)) continue;
                if ($$6 && this.m_20197_().size() < this.m_213801_() && !$$8.m_20159_() && $$8.m_20205_() < this.m_20205_() && $$8 instanceof LivingEntity && !($$8 instanceof WaterAnimal) && !($$8 instanceof Player)) {
                    $$8.m_20329_(this);
                    continue;
                }
                this.m_7334_($$8);
            }
        }
    }

    private void m_38388_() {
        if (this.f_19853_.f_46443_) {
            int $$0 = this.m_38397_();
            this.f_38259_ = $$0 > 0 ? (this.f_38259_ += 0.05f) : (this.f_38259_ -= 0.1f);
            this.f_38259_ = Mth.m_14036_(this.f_38259_, 0.0f, 1.0f);
            this.f_38261_ = this.f_38260_;
            this.f_38260_ = 10.0f * (float)Math.sin(0.5f * (float)this.f_19853_.m_46467_()) * this.f_38259_;
        } else {
            int $$1;
            if (!this.f_38257_) {
                this.m_38366_(0);
            }
            if (($$1 = this.m_38397_()) > 0) {
                this.m_38366_(--$$1);
                int $$2 = 60 - $$1 - 1;
                if ($$2 > 0 && $$1 == 0) {
                    this.m_38366_(0);
                    Vec3 $$3 = this.m_20184_();
                    if (this.f_38258_) {
                        this.m_20256_($$3.m_82520_(0.0, -0.7, 0.0));
                        this.m_20153_();
                    } else {
                        this.m_20334_($$3.f_82479_, this.m_146862_(p_150274_ -> p_150274_ instanceof Player) ? 2.7 : 0.6, $$3.f_82481_);
                    }
                }
                this.f_38257_ = false;
            }
        }
    }

    @Nullable
    protected SoundEvent m_38370_() {
        switch (this.m_38392_()) {
            case IN_WATER: 
            case UNDER_WATER: 
            case UNDER_FLOWING_WATER: {
                return SoundEvents.f_11707_;
            }
            case ON_LAND: {
                return SoundEvents.f_11706_;
            }
        }
        return null;
    }

    private void m_38391_() {
        if (this.m_6109_()) {
            this.f_38267_ = 0;
            this.m_217006_(this.m_20185_(), this.m_20186_(), this.m_20189_());
        }
        if (this.f_38267_ <= 0) {
            return;
        }
        double $$0 = this.m_20185_() + (this.f_38268_ - this.m_20185_()) / (double)this.f_38267_;
        double $$1 = this.m_20186_() + (this.f_38269_ - this.m_20186_()) / (double)this.f_38267_;
        double $$2 = this.m_20189_() + (this.f_38270_ - this.m_20189_()) / (double)this.f_38267_;
        double $$3 = Mth.m_14175_(this.f_38271_ - (double)this.m_146908_());
        this.m_146922_(this.m_146908_() + (float)$$3 / (float)this.f_38267_);
        this.m_146926_(this.m_146909_() + (float)(this.f_38272_ - (double)this.m_146909_()) / (float)this.f_38267_);
        --this.f_38267_;
        this.m_6034_($$0, $$1, $$2);
        this.m_19915_(this.m_146908_(), this.m_146909_());
    }

    public void m_38339_(boolean p_38340_, boolean p_38341_) {
        this.f_19804_.m_135381_(f_38286_, p_38340_);
        this.f_19804_.m_135381_(f_38287_, p_38341_);
    }

    public float m_38315_(int p_38316_, float p_38317_) {
        if (this.m_38313_(p_38316_)) {
            return Mth.m_144920_(this.f_38263_[p_38316_] - 0.3926991f, this.f_38263_[p_38316_], p_38317_);
        }
        return 0.0f;
    }

    private Status m_38392_() {
        Status $$0 = this.m_38394_();
        if ($$0 != null) {
            this.f_38277_ = this.m_20191_().f_82292_;
            return $$0;
        }
        if (this.m_38393_()) {
            return Status.IN_WATER;
        }
        float $$1 = this.m_38377_();
        if ($$1 > 0.0f) {
            this.f_38278_ = $$1;
            return Status.ON_LAND;
        }
        return Status.IN_AIR;
    }

    public float m_38371_() {
        AABB $$0 = this.m_20191_();
        int $$1 = Mth.m_14107_($$0.f_82288_);
        int $$2 = Mth.m_14165_($$0.f_82291_);
        int $$3 = Mth.m_14107_($$0.f_82292_);
        int $$4 = Mth.m_14165_($$0.f_82292_ - this.f_38281_);
        int $$5 = Mth.m_14107_($$0.f_82290_);
        int $$6 = Mth.m_14165_($$0.f_82293_);
        BlockPos.MutableBlockPos $$7 = new BlockPos.MutableBlockPos();
        block0: for (int $$8 = $$3; $$8 < $$4; ++$$8) {
            float $$9 = 0.0f;
            for (int $$10 = $$1; $$10 < $$2; ++$$10) {
                for (int $$11 = $$5; $$11 < $$6; ++$$11) {
                    $$7.m_122178_($$10, $$8, $$11);
                    FluidState $$12 = this.f_19853_.m_6425_($$7);
                    if ($$12.m_205070_(FluidTags.f_13131_)) {
                        $$9 = Math.max($$9, $$12.m_76155_(this.f_19853_, $$7));
                    }
                    if ($$9 >= 1.0f) continue block0;
                }
            }
            if (!($$9 < 1.0f)) continue;
            return (float)$$7.m_123342_() + $$9;
        }
        return $$4 + 1;
    }

    public float m_38377_() {
        AABB $$0 = this.m_20191_();
        AABB $$1 = new AABB($$0.f_82288_, $$0.f_82289_ - 0.001, $$0.f_82290_, $$0.f_82291_, $$0.f_82289_, $$0.f_82293_);
        int $$2 = Mth.m_14107_($$1.f_82288_) - 1;
        int $$3 = Mth.m_14165_($$1.f_82291_) + 1;
        int $$4 = Mth.m_14107_($$1.f_82289_) - 1;
        int $$5 = Mth.m_14165_($$1.f_82292_) + 1;
        int $$6 = Mth.m_14107_($$1.f_82290_) - 1;
        int $$7 = Mth.m_14165_($$1.f_82293_) + 1;
        VoxelShape $$8 = Shapes.m_83064_($$1);
        float $$9 = 0.0f;
        int $$10 = 0;
        BlockPos.MutableBlockPos $$11 = new BlockPos.MutableBlockPos();
        for (int $$12 = $$2; $$12 < $$3; ++$$12) {
            for (int $$13 = $$6; $$13 < $$7; ++$$13) {
                int $$14 = ($$12 == $$2 || $$12 == $$3 - 1 ? 1 : 0) + ($$13 == $$6 || $$13 == $$7 - 1 ? 1 : 0);
                if ($$14 == 2) continue;
                for (int $$15 = $$4; $$15 < $$5; ++$$15) {
                    if ($$14 > 0 && ($$15 == $$4 || $$15 == $$5 - 1)) continue;
                    $$11.m_122178_($$12, $$15, $$13);
                    BlockState $$16 = this.f_19853_.m_8055_($$11);
                    if ($$16.m_60734_() instanceof WaterlilyBlock || !Shapes.m_83157_($$16.m_60812_(this.f_19853_, $$11).m_83216_($$12, $$15, $$13), $$8, BooleanOp.f_82689_)) continue;
                    $$9 += $$16.m_60734_().m_49958_();
                    ++$$10;
                }
            }
        }
        return $$9 / (float)$$10;
    }

    private boolean m_38393_() {
        AABB $$0 = this.m_20191_();
        int $$1 = Mth.m_14107_($$0.f_82288_);
        int $$2 = Mth.m_14165_($$0.f_82291_);
        int $$3 = Mth.m_14107_($$0.f_82289_);
        int $$4 = Mth.m_14165_($$0.f_82289_ + 0.001);
        int $$5 = Mth.m_14107_($$0.f_82290_);
        int $$6 = Mth.m_14165_($$0.f_82293_);
        boolean $$7 = false;
        this.f_38277_ = -1.7976931348623157E308;
        BlockPos.MutableBlockPos $$8 = new BlockPos.MutableBlockPos();
        for (int $$9 = $$1; $$9 < $$2; ++$$9) {
            for (int $$10 = $$3; $$10 < $$4; ++$$10) {
                for (int $$11 = $$5; $$11 < $$6; ++$$11) {
                    $$8.m_122178_($$9, $$10, $$11);
                    FluidState $$12 = this.f_19853_.m_6425_($$8);
                    if (!$$12.m_205070_(FluidTags.f_13131_)) continue;
                    float $$13 = (float)$$10 + $$12.m_76155_(this.f_19853_, $$8);
                    this.f_38277_ = Math.max((double)$$13, this.f_38277_);
                    $$7 |= $$0.f_82289_ < (double)$$13;
                }
            }
        }
        return $$7;
    }

    @Nullable
    private Status m_38394_() {
        AABB $$0 = this.m_20191_();
        double $$1 = $$0.f_82292_ + 0.001;
        int $$2 = Mth.m_14107_($$0.f_82288_);
        int $$3 = Mth.m_14165_($$0.f_82291_);
        int $$4 = Mth.m_14107_($$0.f_82292_);
        int $$5 = Mth.m_14165_($$1);
        int $$6 = Mth.m_14107_($$0.f_82290_);
        int $$7 = Mth.m_14165_($$0.f_82293_);
        boolean $$8 = false;
        BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
        for (int $$10 = $$2; $$10 < $$3; ++$$10) {
            for (int $$11 = $$4; $$11 < $$5; ++$$11) {
                for (int $$12 = $$6; $$12 < $$7; ++$$12) {
                    $$9.m_122178_($$10, $$11, $$12);
                    FluidState $$13 = this.f_19853_.m_6425_($$9);
                    if (!$$13.m_205070_(FluidTags.f_13131_) || !($$1 < (double)((float)$$9.m_123342_() + $$13.m_76155_(this.f_19853_, $$9)))) continue;
                    if ($$13.m_76170_()) {
                        $$8 = true;
                        continue;
                    }
                    return Status.UNDER_FLOWING_WATER;
                }
            }
        }
        return $$8 ? Status.UNDER_WATER : null;
    }

    private void m_38395_() {
        double $$0 = -0.04f;
        double $$1 = this.m_20068_() ? 0.0 : (double)-0.04f;
        double $$2 = 0.0;
        this.f_38264_ = 0.05f;
        if (this.f_38280_ == Status.IN_AIR && this.f_38279_ != Status.IN_AIR && this.f_38279_ != Status.ON_LAND) {
            this.f_38277_ = this.m_20227_(1.0);
            this.m_6034_(this.m_20185_(), (double)(this.m_38371_() - this.m_20206_()) + 0.101, this.m_20189_());
            this.m_20256_(this.m_20184_().m_82542_(1.0, 0.0, 1.0));
            this.f_38281_ = 0.0;
            this.f_38279_ = Status.IN_WATER;
        } else {
            if (this.f_38279_ == Status.IN_WATER) {
                $$2 = (this.f_38277_ - this.m_20186_()) / (double)this.m_20206_();
                this.f_38264_ = 0.9f;
            } else if (this.f_38279_ == Status.UNDER_FLOWING_WATER) {
                $$1 = -7.0E-4;
                this.f_38264_ = 0.9f;
            } else if (this.f_38279_ == Status.UNDER_WATER) {
                $$2 = 0.01f;
                this.f_38264_ = 0.45f;
            } else if (this.f_38279_ == Status.IN_AIR) {
                this.f_38264_ = 0.9f;
            } else if (this.f_38279_ == Status.ON_LAND) {
                this.f_38264_ = this.f_38278_;
                if (this.m_6688_() instanceof Player) {
                    this.f_38278_ /= 2.0f;
                }
            }
            Vec3 $$3 = this.m_20184_();
            this.m_20334_($$3.f_82479_ * (double)this.f_38264_, $$3.f_82480_ + $$1, $$3.f_82481_ * (double)this.f_38264_);
            this.f_38266_ *= this.f_38264_;
            if ($$2 > 0.0) {
                Vec3 $$4 = this.m_20184_();
                this.m_20334_($$4.f_82479_, ($$4.f_82480_ + $$2 * 0.06153846016296973) * 0.75, $$4.f_82481_);
            }
        }
    }

    private void m_38396_() {
        if (!this.m_20160_()) {
            return;
        }
        float $$0 = 0.0f;
        if (this.f_38273_) {
            this.f_38266_ -= 1.0f;
        }
        if (this.f_38274_) {
            this.f_38266_ += 1.0f;
        }
        if (this.f_38274_ != this.f_38273_ && !this.f_38275_ && !this.f_38276_) {
            $$0 += 0.005f;
        }
        this.m_146922_(this.m_146908_() + this.f_38266_);
        if (this.f_38275_) {
            $$0 += 0.04f;
        }
        if (this.f_38276_) {
            $$0 -= 0.005f;
        }
        this.m_20256_(this.m_20184_().m_82520_(Mth.m_14031_(-this.m_146908_() * ((float)Math.PI / 180)) * $$0, 0.0, Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180)) * $$0));
        this.m_38339_(this.f_38274_ && !this.f_38273_ || this.f_38275_, this.f_38273_ && !this.f_38274_ || this.f_38275_);
    }

    protected float m_213802_() {
        return 0.0f;
    }

    @Override
    public void m_7332_(Entity p_38379_) {
        if (!this.m_20363_(p_38379_)) {
            return;
        }
        float $$1 = this.m_213802_();
        float $$2 = (float)((this.m_213877_() ? (double)0.01f : this.m_6048_()) + p_38379_.m_6049_());
        if (this.m_20197_().size() > 1) {
            int $$3 = this.m_20197_().indexOf(p_38379_);
            $$1 = $$3 == 0 ? 0.2f : -0.6f;
            if (p_38379_ instanceof Animal) {
                $$1 += 0.2f;
            }
        }
        Vec3 $$4 = new Vec3($$1, 0.0, 0.0).m_82524_(-this.m_146908_() * ((float)Math.PI / 180) - 1.5707964f);
        p_38379_.m_6034_(this.m_20185_() + $$4.f_82479_, this.m_20186_() + (double)$$2, this.m_20189_() + $$4.f_82481_);
        p_38379_.m_146922_(p_38379_.m_146908_() + this.f_38266_);
        p_38379_.m_5616_(p_38379_.m_6080_() + this.f_38266_);
        this.m_38321_(p_38379_);
        if (p_38379_ instanceof Animal && this.m_20197_().size() == this.m_213801_()) {
            int $$5 = p_38379_.m_19879_() % 2 == 0 ? 90 : 270;
            p_38379_.m_5618_(((Animal)p_38379_).f_20883_ + (float)$$5);
            p_38379_.m_5616_(p_38379_.m_6080_() + (float)$$5);
        }
    }

    @Override
    public Vec3 m_7688_(LivingEntity p_38357_) {
        double $$3;
        Vec3 $$1 = Boat.m_19903_(this.m_20205_() * Mth.f_13994_, p_38357_.m_20205_(), p_38357_.m_146908_());
        double $$2 = this.m_20185_() + $$1.f_82479_;
        BlockPos $$4 = new BlockPos($$2, this.m_20191_().f_82292_, $$3 = this.m_20189_() + $$1.f_82481_);
        BlockPos $$5 = $$4.m_7495_();
        if (!this.f_19853_.m_46801_($$5)) {
            double $$8;
            ArrayList $$6 = Lists.newArrayList();
            double $$7 = this.f_19853_.m_45573_($$4);
            if (DismountHelper.m_38439_($$7)) {
                $$6.add(new Vec3($$2, (double)$$4.m_123342_() + $$7, $$3));
            }
            if (DismountHelper.m_38439_($$8 = this.f_19853_.m_45573_($$5))) {
                $$6.add(new Vec3($$2, (double)$$5.m_123342_() + $$8, $$3));
            }
            for (Pose $$9 : p_38357_.m_7431_()) {
                for (Vec3 $$10 : $$6) {
                    if (!DismountHelper.m_150279_(this.f_19853_, $$10, p_38357_, $$9)) continue;
                    p_38357_.m_20124_($$9);
                    return $$10;
                }
            }
        }
        return super.m_7688_(p_38357_);
    }

    protected void m_38321_(Entity p_38322_) {
        p_38322_.m_5618_(this.m_146908_());
        float $$1 = Mth.m_14177_(p_38322_.m_146908_() - this.m_146908_());
        float $$2 = Mth.m_14036_($$1, -105.0f, 105.0f);
        p_38322_.f_19859_ += $$2 - $$1;
        p_38322_.m_146922_(p_38322_.m_146908_() + $$2 - $$1);
        p_38322_.m_5616_(p_38322_.m_146908_());
    }

    @Override
    public void m_7340_(Entity p_38383_) {
        this.m_38321_(p_38383_);
    }

    @Override
    protected void m_7380_(CompoundTag p_38359_) {
        p_38359_.m_128359_("Type", this.m_38387_().m_38429_());
    }

    @Override
    protected void m_7378_(CompoundTag p_38338_) {
        if (p_38338_.m_128425_("Type", 8)) {
            this.m_38332_(Type.m_38432_(p_38338_.m_128461_("Type")));
        }
    }

    @Override
    public InteractionResult m_6096_(Player p_38330_, InteractionHand p_38331_) {
        if (p_38330_.m_36341_()) {
            return InteractionResult.PASS;
        }
        if (this.f_38265_ < 60.0f) {
            if (!this.f_19853_.f_46443_) {
                return p_38330_.m_20329_(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void m_7840_(double p_38307_, boolean p_38308_, BlockState p_38309_, BlockPos p_38310_) {
        this.f_38281_ = this.m_20184_().f_82480_;
        if (this.m_20159_()) {
            return;
        }
        if (p_38308_) {
            if (this.f_19789_ > 3.0f) {
                if (this.f_38279_ != Status.ON_LAND) {
                    this.m_183634_();
                    return;
                }
                this.m_142535_(this.f_19789_, 1.0f, DamageSource.f_19315_);
                if (!this.f_19853_.f_46443_ && !this.m_213877_()) {
                    this.m_6074_();
                    if (this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
                        for (int $$4 = 0; $$4 < 3; ++$$4) {
                            this.m_19998_(this.m_38387_().m_38434_());
                        }
                        for (int $$5 = 0; $$5 < 2; ++$$5) {
                            this.m_19998_(Items.f_42398_);
                        }
                    }
                }
            }
            this.m_183634_();
        } else if (!this.f_19853_.m_6425_(this.m_20183_().m_7495_()).m_205070_(FluidTags.f_13131_) && p_38307_ < 0.0) {
            this.f_19789_ -= (float)p_38307_;
        }
    }

    public boolean m_38313_(int p_38314_) {
        return this.f_19804_.m_135370_(p_38314_ == 0 ? f_38286_ : f_38287_) != false && this.m_6688_() != null;
    }

    public void m_38311_(float p_38312_) {
        this.f_19804_.m_135381_(f_38284_, Float.valueOf(p_38312_));
    }

    public float m_38384_() {
        return this.f_19804_.m_135370_(f_38284_).floatValue();
    }

    public void m_38354_(int p_38355_) {
        this.f_19804_.m_135381_(f_38282_, p_38355_);
    }

    public int m_38385_() {
        return this.f_19804_.m_135370_(f_38282_);
    }

    private void m_38366_(int p_38367_) {
        this.f_19804_.m_135381_(f_38262_, p_38367_);
    }

    private int m_38397_() {
        return this.f_19804_.m_135370_(f_38262_);
    }

    public float m_38352_(float p_38353_) {
        return Mth.m_14179_(p_38353_, this.f_38261_, this.f_38260_);
    }

    public void m_38362_(int p_38363_) {
        this.f_19804_.m_135381_(f_38283_, p_38363_);
    }

    public int m_38386_() {
        return this.f_19804_.m_135370_(f_38283_);
    }

    public void m_38332_(Type p_38333_) {
        this.f_19804_.m_135381_(f_38285_, p_38333_.ordinal());
    }

    public Type m_38387_() {
        return Type.m_38430_(this.f_19804_.m_135370_(f_38285_));
    }

    @Override
    protected boolean m_7310_(Entity p_38390_) {
        return this.m_20197_().size() < this.m_213801_() && !this.m_204029_(FluidTags.f_13131_);
    }

    protected int m_213801_() {
        return 2;
    }

    @Override
    @Nullable
    public Entity m_6688_() {
        return this.m_146895_();
    }

    public void m_38342_(boolean p_38343_, boolean p_38344_, boolean p_38345_, boolean p_38346_) {
        this.f_38273_ = p_38343_;
        this.f_38274_ = p_38344_;
        this.f_38275_ = p_38345_;
        this.f_38276_ = p_38346_;
    }

    @Override
    public Packet<?> m_5654_() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public boolean m_5842_() {
        return this.f_38279_ == Status.UNDER_WATER || this.f_38279_ == Status.UNDER_FLOWING_WATER;
    }

    @Override
    public ItemStack m_142340_() {
        return new ItemStack(this.m_38369_());
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type OAK = new Type(Blocks.f_50705_, "oak");
        public static final /* enum */ Type SPRUCE = new Type(Blocks.f_50741_, "spruce");
        public static final /* enum */ Type BIRCH = new Type(Blocks.f_50742_, "birch");
        public static final /* enum */ Type JUNGLE = new Type(Blocks.f_50743_, "jungle");
        public static final /* enum */ Type ACACIA = new Type(Blocks.f_50744_, "acacia");
        public static final /* enum */ Type DARK_OAK = new Type(Blocks.f_50745_, "dark_oak");
        public static final /* enum */ Type MANGROVE = new Type(Blocks.f_220865_, "mangrove");
        private final String f_38420_;
        private final Block f_38421_;
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_38437_) {
            return Enum.valueOf(Type.class, p_38437_);
        }

        private Type(Block p_38427_, String p_38428_) {
            this.f_38420_ = p_38428_;
            this.f_38421_ = p_38427_;
        }

        public String m_38429_() {
            return this.f_38420_;
        }

        public Block m_38434_() {
            return this.f_38421_;
        }

        public String toString() {
            return this.f_38420_;
        }

        public static Type m_38430_(int p_38431_) {
            Type[] $$1 = Type.values();
            if (p_38431_ < 0 || p_38431_ >= $$1.length) {
                p_38431_ = 0;
            }
            return $$1[p_38431_];
        }

        public static Type m_38432_(String p_38433_) {
            Type[] $$1 = Type.values();
            for (int $$2 = 0; $$2 < $$1.length; ++$$2) {
                if (!$$1[$$2].m_38429_().equals(p_38433_)) continue;
                return $$1[$$2];
            }
            return $$1[0];
        }

        private static /* synthetic */ Type[] m_150277_() {
            return new Type[]{OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK, MANGROVE};
        }

        static {
            $VALUES = Type.m_150277_();
        }
    }

    public static final class Status
    extends Enum<Status> {
        public static final /* enum */ Status IN_WATER = new Status();
        public static final /* enum */ Status UNDER_WATER = new Status();
        public static final /* enum */ Status UNDER_FLOWING_WATER = new Status();
        public static final /* enum */ Status ON_LAND = new Status();
        public static final /* enum */ Status IN_AIR = new Status();
        private static final /* synthetic */ Status[] $VALUES;

        public static Status[] values() {
            return (Status[])$VALUES.clone();
        }

        public static Status valueOf(String p_38412_) {
            return Enum.valueOf(Status.class, p_38412_);
        }

        private static /* synthetic */ Status[] m_150276_() {
            return new Status[]{IN_WATER, UNDER_WATER, UNDER_FLOWING_WATER, ON_LAND, IN_AIR};
        }

        static {
            $VALUES = Status.m_150276_();
        }
    }
}

