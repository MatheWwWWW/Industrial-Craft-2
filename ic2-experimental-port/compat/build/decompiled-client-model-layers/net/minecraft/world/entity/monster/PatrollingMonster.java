/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public abstract class PatrollingMonster
extends Monster {
    @Nullable
    private BlockPos f_33042_;
    private boolean f_33043_;
    private boolean f_33044_;

    protected PatrollingMonster(EntityType<? extends PatrollingMonster> p_33046_, Level p_33047_) {
        super((EntityType<? extends Monster>)p_33046_, p_33047_);
    }

    @Override
    protected void m_8099_() {
        super.m_8099_();
        this.f_21345_.m_25352_(4, new LongDistancePatrolGoal<PatrollingMonster>(this, 0.7, 0.595));
    }

    @Override
    public void m_7380_(CompoundTag p_33063_) {
        super.m_7380_(p_33063_);
        if (this.f_33042_ != null) {
            p_33063_.m_128365_("PatrolTarget", NbtUtils.m_129224_(this.f_33042_));
        }
        p_33063_.m_128379_("PatrolLeader", this.f_33043_);
        p_33063_.m_128379_("Patrolling", this.f_33044_);
    }

    @Override
    public void m_7378_(CompoundTag p_33055_) {
        super.m_7378_(p_33055_);
        if (p_33055_.m_128441_("PatrolTarget")) {
            this.f_33042_ = NbtUtils.m_129239_(p_33055_.m_128469_("PatrolTarget"));
        }
        this.f_33043_ = p_33055_.m_128471_("PatrolLeader");
        this.f_33044_ = p_33055_.m_128471_("Patrolling");
    }

    @Override
    public double m_6049_() {
        return -0.45;
    }

    public boolean m_7490_() {
        return true;
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_33049_, DifficultyInstance p_33050_, MobSpawnType p_33051_, @Nullable SpawnGroupData p_33052_, @Nullable CompoundTag p_33053_) {
        if (p_33051_ != MobSpawnType.PATROL && p_33051_ != MobSpawnType.EVENT && p_33051_ != MobSpawnType.STRUCTURE && p_33049_.m_213780_().m_188501_() < 0.06f && this.m_7490_()) {
            this.f_33043_ = true;
        }
        if (this.m_33067_()) {
            this.m_8061_(EquipmentSlot.HEAD, Raid.m_37779_());
            this.m_21409_(EquipmentSlot.HEAD, 2.0f);
        }
        if (p_33051_ == MobSpawnType.PATROL) {
            this.f_33044_ = true;
        }
        return super.m_6518_(p_33049_, p_33050_, p_33051_, p_33052_, p_33053_);
    }

    public static boolean m_219025_(EntityType<? extends PatrollingMonster> p_219026_, LevelAccessor p_219027_, MobSpawnType p_219028_, BlockPos p_219029_, RandomSource p_219030_) {
        if (p_219027_.m_45517_(LightLayer.BLOCK, p_219029_) > 8) {
            return false;
        }
        return PatrollingMonster.m_219019_(p_219026_, p_219027_, p_219028_, p_219029_, p_219030_);
    }

    @Override
    public boolean m_6785_(double p_33073_) {
        return !this.f_33044_ || p_33073_ > 16384.0;
    }

    public void m_33070_(BlockPos p_33071_) {
        this.f_33042_ = p_33071_;
        this.f_33044_ = true;
    }

    public BlockPos m_33065_() {
        return this.f_33042_;
    }

    public boolean m_33066_() {
        return this.f_33042_ != null;
    }

    public void m_33075_(boolean p_33076_) {
        this.f_33043_ = p_33076_;
        this.f_33044_ = true;
    }

    public boolean m_33067_() {
        return this.f_33043_;
    }

    public boolean m_7492_() {
        return true;
    }

    public void m_33068_() {
        this.f_33042_ = this.m_20183_().m_7918_(-500 + this.f_19796_.m_188503_(1000), 0, -500 + this.f_19796_.m_188503_(1000));
        this.f_33044_ = true;
    }

    protected boolean m_33069_() {
        return this.f_33044_;
    }

    protected void m_33077_(boolean p_33078_) {
        this.f_33044_ = p_33078_;
    }

    public static class LongDistancePatrolGoal<T extends PatrollingMonster>
    extends Goal {
        private static final int f_149720_ = 200;
        private final T f_33079_;
        private final double f_33080_;
        private final double f_33081_;
        private long f_33082_;

        public LongDistancePatrolGoal(T p_33084_, double p_33085_, double p_33086_) {
            this.f_33079_ = p_33084_;
            this.f_33080_ = p_33085_;
            this.f_33081_ = p_33086_;
            this.f_33082_ = -1L;
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean m_8036_() {
            boolean $$0 = ((PatrollingMonster)this.f_33079_).f_19853_.m_46467_() < this.f_33082_;
            return ((PatrollingMonster)this.f_33079_).m_33069_() && ((Mob)this.f_33079_).m_5448_() == null && !((Entity)this.f_33079_).m_20160_() && ((PatrollingMonster)this.f_33079_).m_33066_() && !$$0;
        }

        @Override
        public void m_8056_() {
        }

        @Override
        public void m_8041_() {
        }

        @Override
        public void m_8037_() {
            boolean $$0 = ((PatrollingMonster)this.f_33079_).m_33067_();
            PathNavigation $$1 = ((Mob)this.f_33079_).m_21573_();
            if ($$1.m_26571_()) {
                List<PatrollingMonster> $$2 = this.m_33093_();
                if (((PatrollingMonster)this.f_33079_).m_33069_() && $$2.isEmpty()) {
                    ((PatrollingMonster)this.f_33079_).m_33077_(false);
                } else if (!$$0 || !((PatrollingMonster)this.f_33079_).m_33065_().m_203195_(((Entity)this.f_33079_).m_20182_(), 10.0)) {
                    Vec3 $$3 = Vec3.m_82539_(((PatrollingMonster)this.f_33079_).m_33065_());
                    Vec3 $$4 = ((Entity)this.f_33079_).m_20182_();
                    Vec3 $$5 = $$4.m_82546_($$3);
                    $$3 = $$5.m_82524_(90.0f).m_82490_(0.4).m_82549_($$3);
                    Vec3 $$6 = $$3.m_82546_($$4).m_82541_().m_82490_(10.0).m_82549_($$4);
                    BlockPos $$7 = new BlockPos($$6);
                    if (!$$1.m_26519_(($$7 = ((PatrollingMonster)this.f_33079_).f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, $$7)).m_123341_(), $$7.m_123342_(), $$7.m_123343_(), $$0 ? this.f_33081_ : this.f_33080_)) {
                        this.m_33094_();
                        this.f_33082_ = ((PatrollingMonster)this.f_33079_).f_19853_.m_46467_() + 200L;
                    } else if ($$0) {
                        for (PatrollingMonster $$8 : $$2) {
                            $$8.m_33070_($$7);
                        }
                    }
                } else {
                    ((PatrollingMonster)this.f_33079_).m_33068_();
                }
            }
        }

        private List<PatrollingMonster> m_33093_() {
            return ((PatrollingMonster)this.f_33079_).f_19853_.m_6443_(PatrollingMonster.class, ((Entity)this.f_33079_).m_20191_().m_82400_(16.0), p_33089_ -> p_33089_.m_7492_() && !p_33089_.m_7306_((Entity)this.f_33079_));
        }

        private boolean m_33094_() {
            RandomSource $$0 = ((LivingEntity)this.f_33079_).m_217043_();
            BlockPos $$1 = ((PatrollingMonster)this.f_33079_).f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ((Entity)this.f_33079_).m_20183_().m_7918_(-8 + $$0.m_188503_(16), 0, -8 + $$0.m_188503_(16)));
            return ((Mob)this.f_33079_).m_21573_().m_26519_($$1.m_123341_(), $$1.m_123342_(), $$1.m_123343_(), this.f_33080_);
        }
    }
}

