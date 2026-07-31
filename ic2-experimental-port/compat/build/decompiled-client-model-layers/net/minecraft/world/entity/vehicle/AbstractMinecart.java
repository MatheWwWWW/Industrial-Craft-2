/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.google.common.collect.UnmodifiableIterator
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.vehicle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
import net.minecraft.world.entity.vehicle.MinecartFurnace;
import net.minecraft.world.entity.vehicle.MinecartHopper;
import net.minecraft.world.entity.vehicle.MinecartSpawner;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractMinecart
extends Entity {
    private static final EntityDataAccessor<Integer> f_38079_ = SynchedEntityData.m_135353_(AbstractMinecart.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> f_38080_ = SynchedEntityData.m_135353_(AbstractMinecart.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Float> f_38081_ = SynchedEntityData.m_135353_(AbstractMinecart.class, EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Integer> f_38082_ = SynchedEntityData.m_135353_(AbstractMinecart.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> f_38083_ = SynchedEntityData.m_135353_(AbstractMinecart.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> f_38084_ = SynchedEntityData.m_135353_(AbstractMinecart.class, EntityDataSerializers.f_135035_);
    private static final ImmutableMap<Pose, ImmutableList<Integer>> f_38067_ = ImmutableMap.of((Object)((Object)Pose.STANDING), (Object)ImmutableList.of((Object)0, (Object)1, (Object)-1), (Object)((Object)Pose.CROUCHING), (Object)ImmutableList.of((Object)0, (Object)1, (Object)-1), (Object)((Object)Pose.SWIMMING), (Object)ImmutableList.of((Object)0, (Object)1));
    protected static final float f_150249_ = 0.95f;
    private boolean f_38068_;
    private static final Map<RailShape, Pair<Vec3i, Vec3i>> f_38069_ = Util.m_137469_(Maps.newEnumMap(RailShape.class), p_38135_ -> {
        Vec3i $$1 = Direction.WEST.m_122436_();
        Vec3i $$2 = Direction.EAST.m_122436_();
        Vec3i $$3 = Direction.NORTH.m_122436_();
        Vec3i $$4 = Direction.SOUTH.m_122436_();
        Vec3i $$5 = $$1.m_7495_();
        Vec3i $$6 = $$2.m_7495_();
        Vec3i $$7 = $$3.m_7495_();
        Vec3i $$8 = $$4.m_7495_();
        p_38135_.put(RailShape.NORTH_SOUTH, Pair.of((Object)$$3, (Object)$$4));
        p_38135_.put(RailShape.EAST_WEST, Pair.of((Object)$$1, (Object)$$2));
        p_38135_.put(RailShape.ASCENDING_EAST, Pair.of((Object)$$5, (Object)$$2));
        p_38135_.put(RailShape.ASCENDING_WEST, Pair.of((Object)$$1, (Object)$$6));
        p_38135_.put(RailShape.ASCENDING_NORTH, Pair.of((Object)$$3, (Object)$$8));
        p_38135_.put(RailShape.ASCENDING_SOUTH, Pair.of((Object)$$7, (Object)$$4));
        p_38135_.put(RailShape.SOUTH_EAST, Pair.of((Object)$$4, (Object)$$2));
        p_38135_.put(RailShape.SOUTH_WEST, Pair.of((Object)$$4, (Object)$$1));
        p_38135_.put(RailShape.NORTH_WEST, Pair.of((Object)$$3, (Object)$$1));
        p_38135_.put(RailShape.NORTH_EAST, Pair.of((Object)$$3, (Object)$$2));
    });
    private int f_38070_;
    private double f_38071_;
    private double f_38072_;
    private double f_38073_;
    private double f_38074_;
    private double f_38075_;
    private double f_38076_;
    private double f_38077_;
    private double f_38078_;

    protected AbstractMinecart(EntityType<?> p_38087_, Level p_38088_) {
        super(p_38087_, p_38088_);
        this.f_19850_ = true;
    }

    protected AbstractMinecart(EntityType<?> p_38090_, Level p_38091_, double p_38092_, double p_38093_, double p_38094_) {
        this(p_38090_, p_38091_);
        this.m_6034_(p_38092_, p_38093_, p_38094_);
        this.f_19854_ = p_38092_;
        this.f_19855_ = p_38093_;
        this.f_19856_ = p_38094_;
    }

    public static AbstractMinecart m_38119_(Level p_38120_, double p_38121_, double p_38122_, double p_38123_, Type p_38124_) {
        if (p_38124_ == Type.CHEST) {
            return new MinecartChest(p_38120_, p_38121_, p_38122_, p_38123_);
        }
        if (p_38124_ == Type.FURNACE) {
            return new MinecartFurnace(p_38120_, p_38121_, p_38122_, p_38123_);
        }
        if (p_38124_ == Type.TNT) {
            return new MinecartTNT(p_38120_, p_38121_, p_38122_, p_38123_);
        }
        if (p_38124_ == Type.SPAWNER) {
            return new MinecartSpawner(p_38120_, p_38121_, p_38122_, p_38123_);
        }
        if (p_38124_ == Type.HOPPER) {
            return new MinecartHopper(p_38120_, p_38121_, p_38122_, p_38123_);
        }
        if (p_38124_ == Type.COMMAND_BLOCK) {
            return new MinecartCommandBlock(p_38120_, p_38121_, p_38122_, p_38123_);
        }
        return new Minecart(p_38120_, p_38121_, p_38122_, p_38123_);
    }

    @Override
    protected Entity.MovementEmission m_142319_() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    protected void m_8097_() {
        this.f_19804_.m_135372_(f_38079_, 0);
        this.f_19804_.m_135372_(f_38080_, 1);
        this.f_19804_.m_135372_(f_38081_, Float.valueOf(0.0f));
        this.f_19804_.m_135372_(f_38082_, Block.m_49956_(Blocks.f_50016_.m_49966_()));
        this.f_19804_.m_135372_(f_38083_, 6);
        this.f_19804_.m_135372_(f_38084_, false);
    }

    @Override
    public boolean m_7337_(Entity p_38168_) {
        return Boat.m_38323_(this, p_38168_);
    }

    @Override
    public boolean m_6094_() {
        return true;
    }

    @Override
    protected Vec3 m_7643_(Direction.Axis p_38132_, BlockUtil.FoundRectangle p_38133_) {
        return LivingEntity.m_21289_(super.m_7643_(p_38132_, p_38133_));
    }

    @Override
    public double m_6048_() {
        return 0.0;
    }

    @Override
    public Vec3 m_7688_(LivingEntity p_38145_) {
        Direction $$1 = this.m_6374_();
        if ($$1.m_122434_() == Direction.Axis.Y) {
            return super.m_7688_(p_38145_);
        }
        int[][] $$2 = DismountHelper.m_38467_($$1);
        BlockPos $$3 = this.m_20183_();
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        ImmutableList<Pose> $$5 = p_38145_.m_7431_();
        for (Pose $$6 : $$5) {
            EntityDimensions $$7 = p_38145_.m_6972_($$6);
            float $$8 = Math.min($$7.f_20377_, 1.0f) / 2.0f;
            UnmodifiableIterator unmodifiableIterator = ((ImmutableList)f_38067_.get((Object)$$6)).iterator();
            while (unmodifiableIterator.hasNext()) {
                int $$9 = (Integer)unmodifiableIterator.next();
                for (int[] $$10 : $$2) {
                    Vec3 $$13;
                    AABB $$12;
                    $$4.m_122178_($$3.m_123341_() + $$10[0], $$3.m_123342_() + $$9, $$3.m_123343_() + $$10[1]);
                    double $$11 = this.f_19853_.m_45564_(DismountHelper.m_38446_(this.f_19853_, $$4), () -> DismountHelper.m_38446_(this.f_19853_, (BlockPos)$$4.m_7495_()));
                    if (!DismountHelper.m_38439_($$11) || !DismountHelper.m_38456_(this.f_19853_, p_38145_, ($$12 = new AABB(-$$8, 0.0, -$$8, $$8, $$7.f_20378_, $$8)).m_82383_($$13 = Vec3.m_82514_($$4, $$11)))) continue;
                    p_38145_.m_20124_($$6);
                    return $$13;
                }
            }
        }
        double $$14 = this.m_20191_().f_82292_;
        $$4.m_122169_($$3.m_123341_(), $$14, $$3.m_123343_());
        for (Pose $$15 : $$5) {
            double $$16 = p_38145_.m_6972_((Pose)$$15).f_20378_;
            int $$17 = Mth.m_14165_($$14 - (double)$$4.m_123342_() + $$16);
            double $$18 = DismountHelper.m_38463_($$4, $$17, p_38149_ -> this.f_19853_.m_8055_((BlockPos)p_38149_).m_60812_(this.f_19853_, (BlockPos)p_38149_));
            if (!($$14 + $$16 <= $$18)) continue;
            p_38145_.m_20124_($$15);
            break;
        }
        return super.m_7688_(p_38145_);
    }

    @Override
    public boolean m_6469_(DamageSource p_38117_, float p_38118_) {
        boolean $$2;
        if (this.f_19853_.f_46443_ || this.m_213877_()) {
            return true;
        }
        if (this.m_6673_(p_38117_)) {
            return false;
        }
        this.m_38160_(-this.m_38177_());
        this.m_38154_(10);
        this.m_5834_();
        this.m_38109_(this.m_38169_() + p_38118_ * 10.0f);
        this.m_146852_(GameEvent.f_223706_, p_38117_.m_7639_());
        boolean bl = $$2 = p_38117_.m_7639_() instanceof Player && ((Player)p_38117_.m_7639_()).m_150110_().f_35937_;
        if ($$2 || this.m_38169_() > 40.0f) {
            this.m_20153_();
            if (!$$2 || this.m_8077_()) {
                this.m_7617_(p_38117_);
            } else {
                this.m_146870_();
            }
        }
        return true;
    }

    @Override
    protected float m_6041_() {
        BlockState $$0 = this.f_19853_.m_8055_(this.m_20183_());
        if ($$0.m_204336_(BlockTags.f_13034_)) {
            return 1.0f;
        }
        return super.m_6041_();
    }

    public void m_7617_(DamageSource p_38115_) {
        this.m_6074_();
        if (this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
            ItemStack $$1 = new ItemStack(this.m_213728_());
            if (this.m_8077_()) {
                $$1.m_41714_(this.m_7770_());
            }
            this.m_19983_($$1);
        }
    }

    abstract Item m_213728_();

    @Override
    public void m_6053_() {
        this.m_38160_(-this.m_38177_());
        this.m_38154_(10);
        this.m_38109_(this.m_38169_() + this.m_38169_() * 10.0f);
    }

    @Override
    public boolean m_6087_() {
        return !this.m_213877_();
    }

    private static Pair<Vec3i, Vec3i> m_38125_(RailShape p_38126_) {
        return f_38069_.get(p_38126_);
    }

    @Override
    public Direction m_6374_() {
        return this.f_38068_ ? this.m_6350_().m_122424_().m_122427_() : this.m_6350_().m_122427_();
    }

    @Override
    public void m_8119_() {
        double $$12;
        BlockPos $$8;
        BlockState $$9;
        int $$7;
        int $$6;
        int $$5;
        if (this.m_38176_() > 0) {
            this.m_38154_(this.m_38176_() - 1);
        }
        if (this.m_38169_() > 0.0f) {
            this.m_38109_(this.m_38169_() - 1.0f);
        }
        this.m_146871_();
        this.m_20157_();
        if (this.f_19853_.f_46443_) {
            if (this.f_38070_ > 0) {
                double $$0 = this.m_20185_() + (this.f_38071_ - this.m_20185_()) / (double)this.f_38070_;
                double $$1 = this.m_20186_() + (this.f_38072_ - this.m_20186_()) / (double)this.f_38070_;
                double $$2 = this.m_20189_() + (this.f_38073_ - this.m_20189_()) / (double)this.f_38070_;
                double $$3 = Mth.m_14175_(this.f_38074_ - (double)this.m_146908_());
                this.m_146922_(this.m_146908_() + (float)$$3 / (float)this.f_38070_);
                this.m_146926_(this.m_146909_() + (float)(this.f_38075_ - (double)this.m_146909_()) / (float)this.f_38070_);
                --this.f_38070_;
                this.m_6034_($$0, $$1, $$2);
                this.m_19915_(this.m_146908_(), this.m_146909_());
            } else {
                this.m_20090_();
                this.m_19915_(this.m_146908_(), this.m_146909_());
            }
            return;
        }
        if (!this.m_20068_()) {
            double $$4 = this.m_20069_() ? -0.005 : -0.04;
            this.m_20256_(this.m_20184_().m_82520_(0.0, $$4, 0.0));
        }
        if (this.f_19853_.m_8055_(new BlockPos($$5 = Mth.m_14107_(this.m_20185_()), ($$6 = Mth.m_14107_(this.m_20186_())) - 1, $$7 = Mth.m_14107_(this.m_20189_()))).m_204336_(BlockTags.f_13034_)) {
            --$$6;
        }
        if (BaseRailBlock.m_49416_($$9 = this.f_19853_.m_8055_($$8 = new BlockPos($$5, $$6, $$7)))) {
            this.m_6401_($$8, $$9);
            if ($$9.m_60713_(Blocks.f_50285_)) {
                this.m_6025_($$5, $$6, $$7, $$9.m_61143_(PoweredRailBlock.f_55215_));
            }
        } else {
            this.m_38163_();
        }
        this.m_20101_();
        this.m_146926_(0.0f);
        double $$10 = this.f_19854_ - this.m_20185_();
        double $$11 = this.f_19856_ - this.m_20189_();
        if ($$10 * $$10 + $$11 * $$11 > 0.001) {
            this.m_146922_((float)(Mth.m_14136_($$11, $$10) * 180.0 / Math.PI));
            if (this.f_38068_) {
                this.m_146922_(this.m_146908_() + 180.0f);
            }
        }
        if (($$12 = (double)Mth.m_14177_(this.m_146908_() - this.f_19859_)) < -170.0 || $$12 >= 170.0) {
            this.m_146922_(this.m_146908_() + 180.0f);
            this.f_38068_ = !this.f_38068_;
        }
        this.m_19915_(this.m_146908_(), this.m_146909_());
        if (this.m_6064_() == Type.RIDEABLE && this.m_20184_().m_165925_() > 0.01) {
            List<Entity> $$13 = this.f_19853_.m_6249_(this, this.m_20191_().m_82377_(0.2f, 0.0, 0.2f), EntitySelector.m_20421_(this));
            if (!$$13.isEmpty()) {
                for (int $$14 = 0; $$14 < $$13.size(); ++$$14) {
                    Entity $$15 = $$13.get($$14);
                    if ($$15 instanceof Player || $$15 instanceof IronGolem || $$15 instanceof AbstractMinecart || this.m_20160_() || $$15.m_20159_()) {
                        $$15.m_7334_(this);
                        continue;
                    }
                    $$15.m_20329_(this);
                }
            }
        } else {
            for (Entity $$16 : this.f_19853_.m_45933_(this, this.m_20191_().m_82377_(0.2f, 0.0, 0.2f))) {
                if (this.m_20363_($$16) || !$$16.m_6094_() || !($$16 instanceof AbstractMinecart)) continue;
                $$16.m_7334_(this);
            }
        }
        this.m_20073_();
        if (this.m_20077_()) {
            this.m_20093_();
            this.f_19789_ *= 0.5f;
        }
        this.f_19803_ = false;
    }

    protected double m_7097_() {
        return (this.m_20069_() ? 4.0 : 8.0) / 20.0;
    }

    public void m_6025_(int p_38111_, int p_38112_, int p_38113_, boolean p_38114_) {
    }

    protected void m_38163_() {
        double $$0 = this.m_7097_();
        Vec3 $$1 = this.m_20184_();
        this.m_20334_(Mth.m_14008_($$1.f_82479_, -$$0, $$0), $$1.f_82480_, Mth.m_14008_($$1.f_82481_, -$$0, $$0));
        if (this.f_19861_) {
            this.m_20256_(this.m_20184_().m_82490_(0.5));
        }
        this.m_6478_(MoverType.SELF, this.m_20184_());
        if (!this.f_19861_) {
            this.m_20256_(this.m_20184_().m_82490_(0.95));
        }
    }

    protected void m_6401_(BlockPos p_38156_, BlockState p_38157_) {
        double $$32;
        this.m_183634_();
        double $$2 = this.m_20185_();
        double $$3 = this.m_20186_();
        double $$4 = this.m_20189_();
        Vec3 $$5 = this.m_38179_($$2, $$3, $$4);
        $$3 = p_38156_.m_123342_();
        boolean $$6 = false;
        boolean $$7 = false;
        if (p_38157_.m_60713_(Blocks.f_50030_)) {
            $$6 = p_38157_.m_61143_(PoweredRailBlock.f_55215_);
            $$7 = !$$6;
        }
        double $$8 = 0.0078125;
        if (this.m_20069_()) {
            $$8 *= 0.2;
        }
        Vec3 $$9 = this.m_20184_();
        RailShape $$10 = p_38157_.m_61143_(((BaseRailBlock)p_38157_.m_60734_()).m_7978_());
        switch ($$10) {
            case ASCENDING_EAST: {
                this.m_20256_($$9.m_82520_(-$$8, 0.0, 0.0));
                $$3 += 1.0;
                break;
            }
            case ASCENDING_WEST: {
                this.m_20256_($$9.m_82520_($$8, 0.0, 0.0));
                $$3 += 1.0;
                break;
            }
            case ASCENDING_NORTH: {
                this.m_20256_($$9.m_82520_(0.0, 0.0, $$8));
                $$3 += 1.0;
                break;
            }
            case ASCENDING_SOUTH: {
                this.m_20256_($$9.m_82520_(0.0, 0.0, -$$8));
                $$3 += 1.0;
            }
        }
        $$9 = this.m_20184_();
        Pair<Vec3i, Vec3i> $$11 = AbstractMinecart.m_38125_($$10);
        Vec3i $$12 = (Vec3i)$$11.getFirst();
        Vec3i $$13 = (Vec3i)$$11.getSecond();
        double $$14 = $$13.m_123341_() - $$12.m_123341_();
        double $$15 = $$13.m_123343_() - $$12.m_123343_();
        double $$16 = Math.sqrt($$14 * $$14 + $$15 * $$15);
        double $$17 = $$9.f_82479_ * $$14 + $$9.f_82481_ * $$15;
        if ($$17 < 0.0) {
            $$14 = -$$14;
            $$15 = -$$15;
        }
        double $$18 = Math.min(2.0, $$9.m_165924_());
        $$9 = new Vec3($$18 * $$14 / $$16, $$9.f_82480_, $$18 * $$15 / $$16);
        this.m_20256_($$9);
        Entity $$19 = this.m_146895_();
        if ($$19 instanceof Player) {
            Vec3 $$20 = $$19.m_20184_();
            double $$21 = $$20.m_165925_();
            double $$22 = this.m_20184_().m_165925_();
            if ($$21 > 1.0E-4 && $$22 < 0.01) {
                this.m_20256_(this.m_20184_().m_82520_($$20.f_82479_ * 0.1, 0.0, $$20.f_82481_ * 0.1));
                $$7 = false;
            }
        }
        if ($$7) {
            double $$23 = this.m_20184_().m_165924_();
            if ($$23 < 0.03) {
                this.m_20256_(Vec3.f_82478_);
            } else {
                this.m_20256_(this.m_20184_().m_82542_(0.5, 0.0, 0.5));
            }
        }
        double $$24 = (double)p_38156_.m_123341_() + 0.5 + (double)$$12.m_123341_() * 0.5;
        double $$25 = (double)p_38156_.m_123343_() + 0.5 + (double)$$12.m_123343_() * 0.5;
        double $$26 = (double)p_38156_.m_123341_() + 0.5 + (double)$$13.m_123341_() * 0.5;
        double $$27 = (double)p_38156_.m_123343_() + 0.5 + (double)$$13.m_123343_() * 0.5;
        $$14 = $$26 - $$24;
        $$15 = $$27 - $$25;
        if ($$14 == 0.0) {
            double $$28 = $$4 - (double)p_38156_.m_123343_();
        } else if ($$15 == 0.0) {
            double $$29 = $$2 - (double)p_38156_.m_123341_();
        } else {
            double $$30 = $$2 - $$24;
            double $$31 = $$4 - $$25;
            $$32 = ($$30 * $$14 + $$31 * $$15) * 2.0;
        }
        $$2 = $$24 + $$14 * $$32;
        $$4 = $$25 + $$15 * $$32;
        this.m_6034_($$2, $$3, $$4);
        double $$33 = this.m_20160_() ? 0.75 : 1.0;
        double $$34 = this.m_7097_();
        $$9 = this.m_20184_();
        this.m_6478_(MoverType.SELF, new Vec3(Mth.m_14008_($$33 * $$9.f_82479_, -$$34, $$34), 0.0, Mth.m_14008_($$33 * $$9.f_82481_, -$$34, $$34)));
        if ($$12.m_123342_() != 0 && Mth.m_14107_(this.m_20185_()) - p_38156_.m_123341_() == $$12.m_123341_() && Mth.m_14107_(this.m_20189_()) - p_38156_.m_123343_() == $$12.m_123343_()) {
            this.m_6034_(this.m_20185_(), this.m_20186_() + (double)$$12.m_123342_(), this.m_20189_());
        } else if ($$13.m_123342_() != 0 && Mth.m_14107_(this.m_20185_()) - p_38156_.m_123341_() == $$13.m_123341_() && Mth.m_14107_(this.m_20189_()) - p_38156_.m_123343_() == $$13.m_123343_()) {
            this.m_6034_(this.m_20185_(), this.m_20186_() + (double)$$13.m_123342_(), this.m_20189_());
        }
        this.m_7114_();
        Vec3 $$35 = this.m_38179_(this.m_20185_(), this.m_20186_(), this.m_20189_());
        if ($$35 != null && $$5 != null) {
            double $$36 = ($$5.f_82480_ - $$35.f_82480_) * 0.05;
            Vec3 $$37 = this.m_20184_();
            double $$38 = $$37.m_165924_();
            if ($$38 > 0.0) {
                this.m_20256_($$37.m_82542_(($$38 + $$36) / $$38, 1.0, ($$38 + $$36) / $$38));
            }
            this.m_6034_(this.m_20185_(), $$35.f_82480_, this.m_20189_());
        }
        int $$39 = Mth.m_14107_(this.m_20185_());
        int $$40 = Mth.m_14107_(this.m_20189_());
        if ($$39 != p_38156_.m_123341_() || $$40 != p_38156_.m_123343_()) {
            Vec3 $$41 = this.m_20184_();
            double $$42 = $$41.m_165924_();
            this.m_20334_($$42 * (double)($$39 - p_38156_.m_123341_()), $$41.f_82480_, $$42 * (double)($$40 - p_38156_.m_123343_()));
        }
        if ($$6) {
            Vec3 $$43 = this.m_20184_();
            double $$44 = $$43.m_165924_();
            if ($$44 > 0.01) {
                double $$45 = 0.06;
                this.m_20256_($$43.m_82520_($$43.f_82479_ / $$44 * 0.06, 0.0, $$43.f_82481_ / $$44 * 0.06));
            } else {
                Vec3 $$46 = this.m_20184_();
                double $$47 = $$46.f_82479_;
                double $$48 = $$46.f_82481_;
                if ($$10 == RailShape.EAST_WEST) {
                    if (this.m_38129_(p_38156_.m_122024_())) {
                        $$47 = 0.02;
                    } else if (this.m_38129_(p_38156_.m_122029_())) {
                        $$47 = -0.02;
                    }
                } else if ($$10 == RailShape.NORTH_SOUTH) {
                    if (this.m_38129_(p_38156_.m_122012_())) {
                        $$48 = 0.02;
                    } else if (this.m_38129_(p_38156_.m_122019_())) {
                        $$48 = -0.02;
                    }
                } else {
                    return;
                }
                this.m_20334_($$47, $$46.f_82480_, $$48);
            }
        }
    }

    private boolean m_38129_(BlockPos p_38130_) {
        return this.f_19853_.m_8055_(p_38130_).m_60796_(this.f_19853_, p_38130_);
    }

    protected void m_7114_() {
        double $$0 = this.m_20160_() ? 0.997 : 0.96;
        Vec3 $$1 = this.m_20184_();
        $$1 = $$1.m_82542_($$0, 0.0, $$0);
        if (this.m_20069_()) {
            $$1 = $$1.m_82490_(0.95f);
        }
        this.m_20256_($$1);
    }

    @Nullable
    public Vec3 m_38096_(double p_38097_, double p_38098_, double p_38099_, double p_38100_) {
        BlockState $$7;
        int $$6;
        int $$5;
        int $$4 = Mth.m_14107_(p_38097_);
        if (this.f_19853_.m_8055_(new BlockPos($$4, ($$5 = Mth.m_14107_(p_38098_)) - 1, $$6 = Mth.m_14107_(p_38099_))).m_204336_(BlockTags.f_13034_)) {
            --$$5;
        }
        if (BaseRailBlock.m_49416_($$7 = this.f_19853_.m_8055_(new BlockPos($$4, $$5, $$6)))) {
            RailShape $$8 = $$7.m_61143_(((BaseRailBlock)$$7.m_60734_()).m_7978_());
            p_38098_ = $$5;
            if ($$8.m_61745_()) {
                p_38098_ = $$5 + 1;
            }
            Pair<Vec3i, Vec3i> $$9 = AbstractMinecart.m_38125_($$8);
            Vec3i $$10 = (Vec3i)$$9.getFirst();
            Vec3i $$11 = (Vec3i)$$9.getSecond();
            double $$12 = $$11.m_123341_() - $$10.m_123341_();
            double $$13 = $$11.m_123343_() - $$10.m_123343_();
            double $$14 = Math.sqrt($$12 * $$12 + $$13 * $$13);
            if ($$10.m_123342_() != 0 && Mth.m_14107_(p_38097_ += ($$12 /= $$14) * p_38100_) - $$4 == $$10.m_123341_() && Mth.m_14107_(p_38099_ += ($$13 /= $$14) * p_38100_) - $$6 == $$10.m_123343_()) {
                p_38098_ += (double)$$10.m_123342_();
            } else if ($$11.m_123342_() != 0 && Mth.m_14107_(p_38097_) - $$4 == $$11.m_123341_() && Mth.m_14107_(p_38099_) - $$6 == $$11.m_123343_()) {
                p_38098_ += (double)$$11.m_123342_();
            }
            return this.m_38179_(p_38097_, p_38098_, p_38099_);
        }
        return null;
    }

    @Nullable
    public Vec3 m_38179_(double p_38180_, double p_38181_, double p_38182_) {
        BlockState $$6;
        int $$5;
        int $$4;
        int $$3 = Mth.m_14107_(p_38180_);
        if (this.f_19853_.m_8055_(new BlockPos($$3, ($$4 = Mth.m_14107_(p_38181_)) - 1, $$5 = Mth.m_14107_(p_38182_))).m_204336_(BlockTags.f_13034_)) {
            --$$4;
        }
        if (BaseRailBlock.m_49416_($$6 = this.f_19853_.m_8055_(new BlockPos($$3, $$4, $$5)))) {
            double $$24;
            RailShape $$7 = $$6.m_61143_(((BaseRailBlock)$$6.m_60734_()).m_7978_());
            Pair<Vec3i, Vec3i> $$8 = AbstractMinecart.m_38125_($$7);
            Vec3i $$9 = (Vec3i)$$8.getFirst();
            Vec3i $$10 = (Vec3i)$$8.getSecond();
            double $$11 = (double)$$3 + 0.5 + (double)$$9.m_123341_() * 0.5;
            double $$12 = (double)$$4 + 0.0625 + (double)$$9.m_123342_() * 0.5;
            double $$13 = (double)$$5 + 0.5 + (double)$$9.m_123343_() * 0.5;
            double $$14 = (double)$$3 + 0.5 + (double)$$10.m_123341_() * 0.5;
            double $$15 = (double)$$4 + 0.0625 + (double)$$10.m_123342_() * 0.5;
            double $$16 = (double)$$5 + 0.5 + (double)$$10.m_123343_() * 0.5;
            double $$17 = $$14 - $$11;
            double $$18 = ($$15 - $$12) * 2.0;
            double $$19 = $$16 - $$13;
            if ($$17 == 0.0) {
                double $$20 = p_38182_ - (double)$$5;
            } else if ($$19 == 0.0) {
                double $$21 = p_38180_ - (double)$$3;
            } else {
                double $$22 = p_38180_ - $$11;
                double $$23 = p_38182_ - $$13;
                $$24 = ($$22 * $$17 + $$23 * $$19) * 2.0;
            }
            p_38180_ = $$11 + $$17 * $$24;
            p_38181_ = $$12 + $$18 * $$24;
            p_38182_ = $$13 + $$19 * $$24;
            if ($$18 < 0.0) {
                p_38181_ += 1.0;
            } else if ($$18 > 0.0) {
                p_38181_ += 0.5;
            }
            return new Vec3(p_38180_, p_38181_, p_38182_);
        }
        return null;
    }

    @Override
    public AABB m_6921_() {
        AABB $$0 = this.m_20191_();
        if (this.m_38184_()) {
            return $$0.m_82400_((double)Math.abs(this.m_38183_()) / 16.0);
        }
        return $$0;
    }

    @Override
    protected void m_7378_(CompoundTag p_38137_) {
        if (p_38137_.m_128471_("CustomDisplayTile")) {
            this.m_38146_(NbtUtils.m_129241_(p_38137_.m_128469_("DisplayState")));
            this.m_38174_(p_38137_.m_128451_("DisplayOffset"));
        }
    }

    @Override
    protected void m_7380_(CompoundTag p_38151_) {
        if (this.m_38184_()) {
            p_38151_.m_128379_("CustomDisplayTile", true);
            p_38151_.m_128365_("DisplayState", NbtUtils.m_129202_(this.m_38178_()));
            p_38151_.m_128405_("DisplayOffset", this.m_38183_());
        }
    }

    @Override
    public void m_7334_(Entity p_38165_) {
        double $$2;
        if (this.f_19853_.f_46443_) {
            return;
        }
        if (p_38165_.f_19794_ || this.f_19794_) {
            return;
        }
        if (this.m_20363_(p_38165_)) {
            return;
        }
        double $$1 = p_38165_.m_20185_() - this.m_20185_();
        double $$3 = $$1 * $$1 + ($$2 = p_38165_.m_20189_() - this.m_20189_()) * $$2;
        if ($$3 >= (double)1.0E-4f) {
            $$3 = Math.sqrt($$3);
            $$1 /= $$3;
            $$2 /= $$3;
            double $$4 = 1.0 / $$3;
            if ($$4 > 1.0) {
                $$4 = 1.0;
            }
            $$1 *= $$4;
            $$2 *= $$4;
            $$1 *= (double)0.1f;
            $$2 *= (double)0.1f;
            $$1 *= 0.5;
            $$2 *= 0.5;
            if (p_38165_ instanceof AbstractMinecart) {
                Vec3 $$8;
                double $$6;
                double $$5 = p_38165_.m_20185_() - this.m_20185_();
                Vec3 $$7 = new Vec3($$5, 0.0, $$6 = p_38165_.m_20189_() - this.m_20189_()).m_82541_();
                double $$9 = Math.abs($$7.m_82526_($$8 = new Vec3(Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180)), 0.0, Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180))).m_82541_()));
                if ($$9 < (double)0.8f) {
                    return;
                }
                Vec3 $$10 = this.m_20184_();
                Vec3 $$11 = p_38165_.m_20184_();
                if (((AbstractMinecart)p_38165_).m_6064_() == Type.FURNACE && this.m_6064_() != Type.FURNACE) {
                    this.m_20256_($$10.m_82542_(0.2, 1.0, 0.2));
                    this.m_5997_($$11.f_82479_ - $$1, 0.0, $$11.f_82481_ - $$2);
                    p_38165_.m_20256_($$11.m_82542_(0.95, 1.0, 0.95));
                } else if (((AbstractMinecart)p_38165_).m_6064_() != Type.FURNACE && this.m_6064_() == Type.FURNACE) {
                    p_38165_.m_20256_($$11.m_82542_(0.2, 1.0, 0.2));
                    p_38165_.m_5997_($$10.f_82479_ + $$1, 0.0, $$10.f_82481_ + $$2);
                    this.m_20256_($$10.m_82542_(0.95, 1.0, 0.95));
                } else {
                    double $$12 = ($$11.f_82479_ + $$10.f_82479_) / 2.0;
                    double $$13 = ($$11.f_82481_ + $$10.f_82481_) / 2.0;
                    this.m_20256_($$10.m_82542_(0.2, 1.0, 0.2));
                    this.m_5997_($$12 - $$1, 0.0, $$13 - $$2);
                    p_38165_.m_20256_($$11.m_82542_(0.2, 1.0, 0.2));
                    p_38165_.m_5997_($$12 + $$1, 0.0, $$13 + $$2);
                }
            } else {
                this.m_5997_(-$$1, 0.0, -$$2);
                p_38165_.m_5997_($$1 / 4.0, 0.0, $$2 / 4.0);
            }
        }
    }

    @Override
    public void m_6453_(double p_38102_, double p_38103_, double p_38104_, float p_38105_, float p_38106_, int p_38107_, boolean p_38108_) {
        this.f_38071_ = p_38102_;
        this.f_38072_ = p_38103_;
        this.f_38073_ = p_38104_;
        this.f_38074_ = p_38105_;
        this.f_38075_ = p_38106_;
        this.f_38070_ = p_38107_ + 2;
        this.m_20334_(this.f_38076_, this.f_38077_, this.f_38078_);
    }

    @Override
    public void m_6001_(double p_38171_, double p_38172_, double p_38173_) {
        this.f_38076_ = p_38171_;
        this.f_38077_ = p_38172_;
        this.f_38078_ = p_38173_;
        this.m_20334_(this.f_38076_, this.f_38077_, this.f_38078_);
    }

    public void m_38109_(float p_38110_) {
        this.f_19804_.m_135381_(f_38081_, Float.valueOf(p_38110_));
    }

    public float m_38169_() {
        return this.f_19804_.m_135370_(f_38081_).floatValue();
    }

    public void m_38154_(int p_38155_) {
        this.f_19804_.m_135381_(f_38079_, p_38155_);
    }

    public int m_38176_() {
        return this.f_19804_.m_135370_(f_38079_);
    }

    public void m_38160_(int p_38161_) {
        this.f_19804_.m_135381_(f_38080_, p_38161_);
    }

    public int m_38177_() {
        return this.f_19804_.m_135370_(f_38080_);
    }

    public abstract Type m_6064_();

    public BlockState m_38178_() {
        if (!this.m_38184_()) {
            return this.m_6390_();
        }
        return Block.m_49803_(this.m_20088_().m_135370_(f_38082_));
    }

    public BlockState m_6390_() {
        return Blocks.f_50016_.m_49966_();
    }

    public int m_38183_() {
        if (!this.m_38184_()) {
            return this.m_7144_();
        }
        return this.m_20088_().m_135370_(f_38083_);
    }

    public int m_7144_() {
        return 6;
    }

    public void m_38146_(BlockState p_38147_) {
        this.m_20088_().m_135381_(f_38082_, Block.m_49956_(p_38147_));
        this.m_38138_(true);
    }

    public void m_38174_(int p_38175_) {
        this.m_20088_().m_135381_(f_38083_, p_38175_);
        this.m_38138_(true);
    }

    public boolean m_38184_() {
        return this.m_20088_().m_135370_(f_38084_);
    }

    public void m_38138_(boolean p_38139_) {
        this.m_20088_().m_135381_(f_38084_, p_38139_);
    }

    @Override
    public Packet<?> m_5654_() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public ItemStack m_142340_() {
        Item $$5;
        switch (this.m_6064_()) {
            case FURNACE: {
                Item $$0 = Items.f_42520_;
                break;
            }
            case CHEST: {
                Item $$1 = Items.f_42519_;
                break;
            }
            case TNT: {
                Item $$2 = Items.f_42693_;
                break;
            }
            case HOPPER: {
                Item $$3 = Items.f_42694_;
                break;
            }
            case COMMAND_BLOCK: {
                Item $$4 = Items.f_42657_;
                break;
            }
            default: {
                $$5 = Items.f_42449_;
            }
        }
        return new ItemStack($$5);
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type RIDEABLE = new Type();
        public static final /* enum */ Type CHEST = new Type();
        public static final /* enum */ Type FURNACE = new Type();
        public static final /* enum */ Type TNT = new Type();
        public static final /* enum */ Type SPAWNER = new Type();
        public static final /* enum */ Type HOPPER = new Type();
        public static final /* enum */ Type COMMAND_BLOCK = new Type();
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_38200_) {
            return Enum.valueOf(Type.class, p_38200_);
        }

        private static /* synthetic */ Type[] m_150253_() {
            return new Type[]{RIDEABLE, CHEST, FURNACE, TNT, SPAWNER, HOPPER, COMMAND_BLOCK};
        }

        static {
            $VALUES = Type.m_150253_();
        }
    }
}

