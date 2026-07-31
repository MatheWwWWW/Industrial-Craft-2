/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface EntityGetter {
    public List<Entity> m_6249_(@Nullable Entity var1, AABB var2, Predicate<? super Entity> var3);

    public <T extends Entity> List<T> m_142425_(EntityTypeTest<Entity, T> var1, AABB var2, Predicate<? super T> var3);

    default public <T extends Entity> List<T> m_6443_(Class<T> p_45979_, AABB p_45980_, Predicate<? super T> p_45981_) {
        return this.m_142425_(EntityTypeTest.m_156916_(p_45979_), p_45980_, p_45981_);
    }

    public List<? extends Player> m_6907_();

    default public List<Entity> m_45933_(@Nullable Entity p_45934_, AABB p_45935_) {
        return this.m_6249_(p_45934_, p_45935_, EntitySelector.f_20408_);
    }

    default public boolean m_5450_(@Nullable Entity p_45939_, VoxelShape p_45940_) {
        if (p_45940_.m_83281_()) {
            return true;
        }
        for (Entity $$2 : this.m_45933_(p_45939_, p_45940_.m_83215_())) {
            if ($$2.m_213877_() || !$$2.f_19850_ || p_45939_ != null && $$2.m_20365_(p_45939_) || !Shapes.m_83157_(p_45940_, Shapes.m_83064_($$2.m_20191_()), BooleanOp.f_82689_)) continue;
            return false;
        }
        return true;
    }

    default public <T extends Entity> List<T> m_45976_(Class<T> p_45977_, AABB p_45978_) {
        return this.m_6443_(p_45977_, p_45978_, EntitySelector.f_20408_);
    }

    default public List<VoxelShape> m_183134_(@Nullable Entity p_186451_, AABB p_186452_) {
        if (p_186452_.m_82309_() < 1.0E-7) {
            return List.of();
        }
        Predicate<Entity> $$2 = p_186451_ == null ? EntitySelector.f_185987_ : EntitySelector.f_20408_.and(p_186451_::m_7337_);
        List<Entity> $$3 = this.m_6249_(p_186451_, p_186452_.m_82400_(1.0E-7), $$2);
        if ($$3.isEmpty()) {
            return List.of();
        }
        ImmutableList.Builder $$4 = ImmutableList.builderWithExpectedSize((int)$$3.size());
        for (Entity $$5 : $$3) {
            $$4.add((Object)Shapes.m_83064_($$5.m_20191_()));
        }
        return $$4.build();
    }

    @Nullable
    default public Player m_5788_(double p_45919_, double p_45920_, double p_45921_, double p_45922_, @Nullable Predicate<Entity> p_45923_) {
        double $$5 = -1.0;
        Player $$6 = null;
        for (Player player : this.m_6907_()) {
            if (p_45923_ != null && !p_45923_.test(player)) continue;
            double $$8 = player.m_20275_(p_45919_, p_45920_, p_45921_);
            if (!(p_45922_ < 0.0) && !($$8 < p_45922_ * p_45922_) || $$5 != -1.0 && !($$8 < $$5)) continue;
            $$5 = $$8;
            $$6 = player;
        }
        return $$6;
    }

    @Nullable
    default public Player m_45930_(Entity p_45931_, double p_45932_) {
        return this.m_45924_(p_45931_.m_20185_(), p_45931_.m_20186_(), p_45931_.m_20189_(), p_45932_, false);
    }

    @Nullable
    default public Player m_45924_(double p_45925_, double p_45926_, double p_45927_, double p_45928_, boolean p_45929_) {
        Predicate<Entity> $$5 = p_45929_ ? EntitySelector.f_20406_ : EntitySelector.f_20408_;
        return this.m_5788_(p_45925_, p_45926_, p_45927_, p_45928_, $$5);
    }

    default public boolean m_45914_(double p_45915_, double p_45916_, double p_45917_, double p_45918_) {
        for (Player player : this.m_6907_()) {
            if (!EntitySelector.f_20408_.test(player) || !EntitySelector.f_20403_.test(player)) continue;
            double $$5 = player.m_20275_(p_45915_, p_45916_, p_45917_);
            if (!(p_45918_ < 0.0) && !($$5 < p_45918_ * p_45918_)) continue;
            return true;
        }
        return false;
    }

    @Nullable
    default public Player m_45946_(TargetingConditions p_45947_, LivingEntity p_45948_) {
        return this.m_45982_(this.m_6907_(), p_45947_, p_45948_, p_45948_.m_20185_(), p_45948_.m_20186_(), p_45948_.m_20189_());
    }

    @Nullable
    default public Player m_45949_(TargetingConditions p_45950_, LivingEntity p_45951_, double p_45952_, double p_45953_, double p_45954_) {
        return this.m_45982_(this.m_6907_(), p_45950_, p_45951_, p_45952_, p_45953_, p_45954_);
    }

    @Nullable
    default public Player m_45941_(TargetingConditions p_45942_, double p_45943_, double p_45944_, double p_45945_) {
        return this.m_45982_(this.m_6907_(), p_45942_, null, p_45943_, p_45944_, p_45945_);
    }

    @Nullable
    default public <T extends LivingEntity> T m_45963_(Class<? extends T> p_45964_, TargetingConditions p_45965_, @Nullable LivingEntity p_45966_, double p_45967_, double p_45968_, double p_45969_, AABB p_45970_) {
        return (T)this.m_45982_(this.m_6443_(p_45964_, p_45970_, p_186454_ -> true), p_45965_, p_45966_, p_45967_, p_45968_, p_45969_);
    }

    @Nullable
    default public <T extends LivingEntity> T m_45982_(List<? extends T> p_45983_, TargetingConditions p_45984_, @Nullable LivingEntity p_45985_, double p_45986_, double p_45987_, double p_45988_) {
        double $$6 = -1.0;
        LivingEntity $$7 = null;
        for (LivingEntity $$8 : p_45983_) {
            if (!p_45984_.m_26885_(p_45985_, $$8)) continue;
            double $$9 = $$8.m_20275_(p_45986_, p_45987_, p_45988_);
            if ($$6 != -1.0 && !($$9 < $$6)) continue;
            $$6 = $$9;
            $$7 = $$8;
        }
        return (T)$$7;
    }

    default public List<Player> m_45955_(TargetingConditions p_45956_, LivingEntity p_45957_, AABB p_45958_) {
        ArrayList $$3 = Lists.newArrayList();
        for (Player player : this.m_6907_()) {
            if (!p_45958_.m_82393_(player.m_20185_(), player.m_20186_(), player.m_20189_()) || !p_45956_.m_26885_(p_45957_, player)) continue;
            $$3.add(player);
        }
        return $$3;
    }

    default public <T extends LivingEntity> List<T> m_45971_(Class<T> p_45972_, TargetingConditions p_45973_, LivingEntity p_45974_, AABB p_45975_) {
        List<LivingEntity> $$4 = this.m_6443_(p_45972_, p_45975_, p_186450_ -> true);
        ArrayList $$5 = Lists.newArrayList();
        for (LivingEntity $$6 : $$4) {
            if (!p_45973_.m_26885_(p_45974_, $$6)) continue;
            $$5.add($$6);
        }
        return $$5;
    }

    @Nullable
    default public Player m_46003_(UUID p_46004_) {
        for (int $$1 = 0; $$1 < this.m_6907_().size(); ++$$1) {
            Player $$2 = this.m_6907_().get($$1);
            if (!p_46004_.equals($$2.m_20148_())) continue;
            return $$2;
        }
        return null;
    }
}

