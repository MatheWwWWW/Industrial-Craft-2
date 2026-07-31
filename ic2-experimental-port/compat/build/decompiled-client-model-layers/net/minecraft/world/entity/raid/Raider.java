/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.raid;

import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PathfindToRaidGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raids;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public abstract class Raider
extends PatrollingMonster {
    protected static final EntityDataAccessor<Boolean> f_37835_ = SynchedEntityData.m_135353_(Raider.class, EntityDataSerializers.f_135035_);
    static final Predicate<ItemEntity> f_37831_ = p_37872_ -> !p_37872_.m_32063_() && p_37872_.m_6084_() && ItemStack.m_41728_(p_37872_.m_32055_(), Raid.m_37779_());
    @Nullable
    protected Raid f_37836_;
    private int f_37832_;
    private boolean f_37833_;
    private int f_37834_;

    protected Raider(EntityType<? extends Raider> p_37839_, Level p_37840_) {
        super((EntityType<? extends PatrollingMonster>)p_37839_, p_37840_);
    }

    @Override
    protected void m_8099_() {
        super.m_8099_();
        this.f_21345_.m_25352_(1, new ObtainRaidLeaderBannerGoal(this, this));
        this.f_21345_.m_25352_(3, new PathfindToRaidGoal<Raider>(this));
        this.f_21345_.m_25352_(4, new RaiderMoveThroughVillageGoal(this, 1.05f, 1));
        this.f_21345_.m_25352_(5, new RaiderCelebration(this));
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_37835_, false);
    }

    public abstract void m_7895_(int var1, boolean var2);

    public boolean m_37882_() {
        return this.f_37833_;
    }

    public void m_37897_(boolean p_37898_) {
        this.f_37833_ = p_37898_;
    }

    @Override
    public void m_8107_() {
        if (this.f_19853_ instanceof ServerLevel && this.m_6084_()) {
            Raid $$0 = this.m_37885_();
            if (this.m_37882_()) {
                if ($$0 == null) {
                    Raid $$1;
                    if (this.f_19853_.m_46467_() % 20L == 0L && ($$1 = ((ServerLevel)this.f_19853_).m_8832_(this.m_20183_())) != null && Raids.m_37965_(this, $$1)) {
                        $$1.m_37713_($$1.m_37771_(), this, null, true);
                    }
                } else {
                    LivingEntity $$2 = this.m_5448_();
                    if ($$2 != null && ($$2.m_6095_() == EntityType.f_20532_ || $$2.m_6095_() == EntityType.f_20460_)) {
                        this.f_20891_ = 0;
                    }
                }
            }
        }
        super.m_8107_();
    }

    @Override
    protected void m_7562_() {
        this.f_20891_ += 2;
    }

    @Override
    public void m_6667_(DamageSource p_37847_) {
        if (this.f_19853_ instanceof ServerLevel) {
            Entity $$1 = p_37847_.m_7639_();
            Raid $$2 = this.m_37885_();
            if ($$2 != null) {
                if (this.m_33067_()) {
                    $$2.m_37758_(this.m_37887_());
                }
                if ($$1 != null && $$1.m_6095_() == EntityType.f_20532_) {
                    $$2.m_37726_($$1);
                }
                $$2.m_37740_(this, false);
            }
            if (this.m_33067_() && $$2 == null && ((ServerLevel)this.f_19853_).m_8832_(this.m_20183_()) == null) {
                ItemStack $$3 = this.m_6844_(EquipmentSlot.HEAD);
                Player $$4 = null;
                Entity $$5 = $$1;
                if ($$5 instanceof Player) {
                    $$4 = (Player)$$5;
                } else if ($$5 instanceof Wolf) {
                    Wolf $$6 = (Wolf)$$5;
                    LivingEntity $$7 = $$6.m_21826_();
                    if ($$6.m_21824_() && $$7 instanceof Player) {
                        $$4 = (Player)$$7;
                    }
                }
                if (!$$3.m_41619_() && ItemStack.m_41728_($$3, Raid.m_37779_()) && $$4 != null) {
                    MobEffectInstance $$8 = $$4.m_21124_(MobEffects.f_19594_);
                    int $$9 = 1;
                    if ($$8 != null) {
                        $$9 += $$8.m_19564_();
                        $$4.m_6234_(MobEffects.f_19594_);
                    } else {
                        --$$9;
                    }
                    $$9 = Mth.m_14045_($$9, 0, 4);
                    MobEffectInstance $$10 = new MobEffectInstance(MobEffects.f_19594_, 120000, $$9, false, false, true);
                    if (!this.f_19853_.m_46469_().m_46207_(GameRules.f_46154_)) {
                        $$4.m_7292_($$10);
                    }
                }
            }
        }
        super.m_6667_(p_37847_);
    }

    @Override
    public boolean m_7492_() {
        return !this.m_37886_();
    }

    public void m_37851_(@Nullable Raid p_37852_) {
        this.f_37836_ = p_37852_;
    }

    @Nullable
    public Raid m_37885_() {
        return this.f_37836_;
    }

    public boolean m_37886_() {
        return this.m_37885_() != null && this.m_37885_().m_37782_();
    }

    public void m_37842_(int p_37843_) {
        this.f_37832_ = p_37843_;
    }

    public int m_37887_() {
        return this.f_37832_;
    }

    public boolean m_37888_() {
        return this.f_19804_.m_135370_(f_37835_);
    }

    public void m_37899_(boolean p_37900_) {
        this.f_19804_.m_135381_(f_37835_, p_37900_);
    }

    @Override
    public void m_7380_(CompoundTag p_37870_) {
        super.m_7380_(p_37870_);
        p_37870_.m_128405_("Wave", this.f_37832_);
        p_37870_.m_128379_("CanJoinRaid", this.f_37833_);
        if (this.f_37836_ != null) {
            p_37870_.m_128405_("RaidId", this.f_37836_.m_37781_());
        }
    }

    @Override
    public void m_7378_(CompoundTag p_37862_) {
        super.m_7378_(p_37862_);
        this.f_37832_ = p_37862_.m_128451_("Wave");
        this.f_37833_ = p_37862_.m_128471_("CanJoinRaid");
        if (p_37862_.m_128425_("RaidId", 3)) {
            if (this.f_19853_ instanceof ServerLevel) {
                this.f_37836_ = ((ServerLevel)this.f_19853_).m_8905_().m_37958_(p_37862_.m_128451_("RaidId"));
            }
            if (this.f_37836_ != null) {
                this.f_37836_.m_37718_(this.f_37832_, this, false);
                if (this.m_33067_()) {
                    this.f_37836_.m_37710_(this.f_37832_, this);
                }
            }
        }
    }

    @Override
    protected void m_7581_(ItemEntity p_37866_) {
        boolean $$2;
        ItemStack $$1 = p_37866_.m_32055_();
        boolean bl = $$2 = this.m_37886_() && this.m_37885_().m_37750_(this.m_37887_()) != null;
        if (this.m_37886_() && !$$2 && ItemStack.m_41728_($$1, Raid.m_37779_())) {
            EquipmentSlot $$3 = EquipmentSlot.HEAD;
            ItemStack $$4 = this.m_6844_($$3);
            double $$5 = this.m_21519_($$3);
            if (!$$4.m_41619_() && (double)Math.max(this.f_19796_.m_188501_() - 0.1f, 0.0f) < $$5) {
                this.m_19983_($$4);
            }
            this.m_21053_(p_37866_);
            this.m_8061_($$3, $$1);
            this.m_7938_(p_37866_, $$1.m_41613_());
            p_37866_.m_146870_();
            this.m_37885_().m_37710_(this.m_37887_(), this);
            this.m_33075_(true);
        } else {
            super.m_7581_(p_37866_);
        }
    }

    @Override
    public boolean m_6785_(double p_37894_) {
        if (this.m_37885_() == null) {
            return super.m_6785_(p_37894_);
        }
        return false;
    }

    @Override
    public boolean m_8023_() {
        return super.m_8023_() || this.m_37885_() != null;
    }

    public int m_37889_() {
        return this.f_37834_;
    }

    public void m_37863_(int p_37864_) {
        this.f_37834_ = p_37864_;
    }

    @Override
    public boolean m_6469_(DamageSource p_37849_, float p_37850_) {
        if (this.m_37886_()) {
            this.m_37885_().m_37776_();
        }
        return super.m_6469_(p_37849_, p_37850_);
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_37856_, DifficultyInstance p_37857_, MobSpawnType p_37858_, @Nullable SpawnGroupData p_37859_, @Nullable CompoundTag p_37860_) {
        this.m_37897_(this.m_6095_() != EntityType.f_20495_ || p_37858_ != MobSpawnType.NATURAL);
        return super.m_6518_(p_37856_, p_37857_, p_37858_, p_37859_, p_37860_);
    }

    public abstract SoundEvent m_7930_();

    public static class ObtainRaidLeaderBannerGoal<T extends Raider>
    extends Goal {
        private final T f_37914_;
        final /* synthetic */ Raider f_37913_;

        public ObtainRaidLeaderBannerGoal(T p_37917_) {
            this.f_37913_ = p_37916_;
            this.f_37914_ = p_37917_;
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean m_8036_() {
            List<ItemEntity> $$2;
            Raid $$0 = ((Raider)this.f_37914_).m_37885_();
            if (!((Raider)this.f_37914_).m_37886_() || ((Raider)this.f_37914_).m_37885_().m_37706_() || !((PatrollingMonster)this.f_37914_).m_7490_() || ItemStack.m_41728_(((Mob)this.f_37914_).m_6844_(EquipmentSlot.HEAD), Raid.m_37779_())) {
                return false;
            }
            Raider $$1 = $$0.m_37750_(((Raider)this.f_37914_).m_37887_());
            if (!($$1 != null && $$1.m_6084_() || ($$2 = ((Raider)this.f_37914_).f_19853_.m_6443_(ItemEntity.class, ((Entity)this.f_37914_).m_20191_().m_82377_(16.0, 8.0, 16.0), f_37831_)).isEmpty())) {
                return ((Mob)this.f_37914_).m_21573_().m_5624_($$2.get(0), 1.15f);
            }
            return false;
        }

        @Override
        public void m_8037_() {
            List<ItemEntity> $$0;
            if (((Mob)this.f_37914_).m_21573_().m_26567_().m_203195_(((Entity)this.f_37914_).m_20182_(), 1.414) && !($$0 = ((Raider)this.f_37914_).f_19853_.m_6443_(ItemEntity.class, ((Entity)this.f_37914_).m_20191_().m_82377_(4.0, 4.0, 4.0), f_37831_)).isEmpty()) {
                ((Raider)this.f_37914_).m_7581_($$0.get(0));
            }
        }
    }

    static class RaiderMoveThroughVillageGoal
    extends Goal {
        private final Raider f_37929_;
        private final double f_37930_;
        private BlockPos f_37931_;
        private final List<BlockPos> f_37932_ = Lists.newArrayList();
        private final int f_37933_;
        private boolean f_37934_;

        public RaiderMoveThroughVillageGoal(Raider p_37936_, double p_37937_, int p_37938_) {
            this.f_37929_ = p_37936_;
            this.f_37930_ = p_37937_;
            this.f_37933_ = p_37938_;
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean m_8036_() {
            this.m_37950_();
            return this.m_37948_() && this.m_37949_() && this.f_37929_.m_5448_() == null;
        }

        private boolean m_37948_() {
            return this.f_37929_.m_37886_() && !this.f_37929_.m_37885_().m_37706_();
        }

        private boolean m_37949_() {
            ServerLevel $$0 = (ServerLevel)this.f_37929_.f_19853_;
            BlockPos $$1 = this.f_37929_.m_20183_();
            Optional<BlockPos> $$2 = $$0.m_8904_().m_217951_(p_219843_ -> p_219843_.m_203565_(PoiTypes.f_218060_), this::m_37942_, PoiManager.Occupancy.ANY, $$1, 48, this.f_37929_.f_19796_);
            if (!$$2.isPresent()) {
                return false;
            }
            this.f_37931_ = $$2.get().m_7949_();
            return true;
        }

        @Override
        public boolean m_8045_() {
            if (this.f_37929_.m_21573_().m_26571_()) {
                return false;
            }
            return this.f_37929_.m_5448_() == null && !this.f_37931_.m_203195_(this.f_37929_.m_20182_(), this.f_37929_.m_20205_() + (float)this.f_37933_) && !this.f_37934_;
        }

        @Override
        public void m_8041_() {
            if (this.f_37931_.m_203195_(this.f_37929_.m_20182_(), this.f_37933_)) {
                this.f_37932_.add(this.f_37931_);
            }
        }

        @Override
        public void m_8056_() {
            super.m_8056_();
            this.f_37929_.m_21310_(0);
            this.f_37929_.m_21573_().m_26519_(this.f_37931_.m_123341_(), this.f_37931_.m_123342_(), this.f_37931_.m_123343_(), this.f_37930_);
            this.f_37934_ = false;
        }

        @Override
        public void m_8037_() {
            if (this.f_37929_.m_21573_().m_26571_()) {
                Vec3 $$0 = Vec3.m_82539_(this.f_37931_);
                Vec3 $$1 = DefaultRandomPos.m_148412_(this.f_37929_, 16, 7, $$0, 0.3141592741012573);
                if ($$1 == null) {
                    $$1 = DefaultRandomPos.m_148412_(this.f_37929_, 8, 7, $$0, 1.5707963705062866);
                }
                if ($$1 == null) {
                    this.f_37934_ = true;
                    return;
                }
                this.f_37929_.m_21573_().m_26519_($$1.f_82479_, $$1.f_82480_, $$1.f_82481_, this.f_37930_);
            }
        }

        private boolean m_37942_(BlockPos p_37943_) {
            for (BlockPos $$1 : this.f_37932_) {
                if (!Objects.equals(p_37943_, $$1)) continue;
                return false;
            }
            return true;
        }

        private void m_37950_() {
            if (this.f_37932_.size() > 2) {
                this.f_37932_.remove(0);
            }
        }
    }

    public class RaiderCelebration
    extends Goal {
        private final Raider f_37921_;

        RaiderCelebration(Raider p_37924_) {
            this.f_37921_ = p_37924_;
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean m_8036_() {
            Raid $$0 = this.f_37921_.m_37885_();
            return this.f_37921_.m_6084_() && this.f_37921_.m_5448_() == null && $$0 != null && $$0.m_37768_();
        }

        @Override
        public void m_8056_() {
            this.f_37921_.m_37899_(true);
            super.m_8056_();
        }

        @Override
        public void m_8041_() {
            this.f_37921_.m_37899_(false);
            super.m_8041_();
        }

        @Override
        public void m_8037_() {
            if (!this.f_37921_.m_20067_() && this.f_37921_.f_19796_.m_188503_(this.m_183277_(100)) == 0) {
                Raider.this.m_5496_(Raider.this.m_7930_(), Raider.this.m_6121_(), Raider.this.m_6100_());
            }
            if (!this.f_37921_.m_20159_() && this.f_37921_.f_19796_.m_188503_(this.m_183277_(50)) == 0) {
                this.f_37921_.m_21569_().m_24901_();
            }
            super.m_8037_();
        }
    }

    protected class HoldGroundAttackGoal
    extends Goal {
        private final Raider f_37903_;
        private final float f_37904_;
        public final TargetingConditions f_37901_ = TargetingConditions.m_148353_().m_26883_(8.0).m_148355_().m_26893_();

        public HoldGroundAttackGoal(AbstractIllager p_37907_, float p_37908_) {
            this.f_37903_ = p_37907_;
            this.f_37904_ = p_37908_ * p_37908_;
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean m_8036_() {
            LivingEntity $$0 = this.f_37903_.m_21188_();
            return this.f_37903_.m_37885_() == null && this.f_37903_.m_33069_() && this.f_37903_.m_5448_() != null && !this.f_37903_.m_5912_() && ($$0 == null || $$0.m_6095_() != EntityType.f_20532_);
        }

        @Override
        public void m_8056_() {
            super.m_8056_();
            this.f_37903_.m_21573_().m_26573_();
            List<Raider> $$0 = this.f_37903_.f_19853_.m_45971_(Raider.class, this.f_37901_, this.f_37903_, this.f_37903_.m_20191_().m_82377_(8.0, 8.0, 8.0));
            for (Raider $$1 : $$0) {
                $$1.m_6710_(this.f_37903_.m_5448_());
            }
        }

        @Override
        public void m_8041_() {
            super.m_8041_();
            LivingEntity $$0 = this.f_37903_.m_5448_();
            if ($$0 != null) {
                List<Raider> $$1 = this.f_37903_.f_19853_.m_45971_(Raider.class, this.f_37901_, this.f_37903_, this.f_37903_.m_20191_().m_82377_(8.0, 8.0, 8.0));
                for (Raider $$2 : $$1) {
                    $$2.m_6710_($$0);
                    $$2.m_21561_(true);
                }
                this.f_37903_.m_21561_(true);
            }
        }

        @Override
        public boolean m_183429_() {
            return true;
        }

        @Override
        public void m_8037_() {
            LivingEntity $$0 = this.f_37903_.m_5448_();
            if ($$0 == null) {
                return;
            }
            if (this.f_37903_.m_20280_($$0) > (double)this.f_37904_) {
                this.f_37903_.m_21563_().m_24960_($$0, 30.0f, 30.0f);
                if (this.f_37903_.f_19796_.m_188503_(50) == 0) {
                    this.f_37903_.m_8032_();
                }
            } else {
                this.f_37903_.m_21561_(true);
            }
            super.m_8037_();
        }
    }
}

