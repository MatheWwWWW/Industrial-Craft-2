/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.world.entity.monster.hoglin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.ai.behavior.BecomePassiveIfMemoryPresent;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.EraseMemoryIf;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunIf;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.RunSometimes;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.schedule.Activity;

public class HoglinAi {
    public static final int f_149902_ = 8;
    public static final int f_149903_ = 4;
    private static final UniformInt f_34568_ = TimeUtil.m_145020_(5, 20);
    private static final int f_149904_ = 200;
    private static final int f_149905_ = 8;
    private static final int f_149906_ = 15;
    private static final int f_149907_ = 40;
    private static final int f_149908_ = 15;
    private static final int f_149909_ = 200;
    private static final UniformInt f_34569_ = UniformInt.m_146622_(5, 16);
    private static final float f_149910_ = 1.0f;
    private static final float f_149911_ = 1.3f;
    private static final float f_149912_ = 0.6f;
    private static final float f_149913_ = 0.4f;
    private static final float f_149914_ = 0.6f;

    protected static Brain<?> m_34575_(Brain<Hoglin> p_34576_) {
        HoglinAi.m_34591_(p_34576_);
        HoglinAi.m_34601_(p_34576_);
        HoglinAi.m_34608_(p_34576_);
        HoglinAi.m_34615_(p_34576_);
        p_34576_.m_21930_((Set<Activity>)ImmutableSet.of((Object)Activity.f_37978_));
        p_34576_.m_21944_(Activity.f_37979_);
        p_34576_.m_21962_();
        return p_34576_;
    }

    private static void m_34591_(Brain<Hoglin> p_34592_) {
        p_34592_.m_21891_(Activity.f_37978_, 0, (ImmutableList<Behavior<Hoglin>>)ImmutableList.of((Object)new LookAtTargetSink(45, 90), (Object)new MoveToTargetSink()));
    }

    private static void m_34601_(Brain<Hoglin> p_34602_) {
        p_34602_.m_21891_(Activity.f_37979_, 10, (ImmutableList<Behavior<Hoglin>>)ImmutableList.of((Object)new BecomePassiveIfMemoryPresent(MemoryModuleType.f_26356_, 200), (Object)new AnimalMakeLove(EntityType.f_20456_, 0.6f), SetWalkTargetAwayFrom.m_24012_(MemoryModuleType.f_26356_, 1.0f, 8, true), new StartAttacking<Hoglin>(HoglinAi::m_34610_), new RunIf<PathfinderMob>(Hoglin::m_34552_, SetWalkTargetAwayFrom.m_24019_(MemoryModuleType.f_26350_, 0.4f, 8, false)), new RunSometimes<LivingEntity>(new SetEntityLookTarget(8.0f), UniformInt.m_146622_(30, 60)), new BabyFollowAdult(f_34569_, 0.6f), HoglinAi.m_34571_()));
    }

    private static void m_34608_(Brain<Hoglin> p_34609_) {
        p_34609_.m_21895_(Activity.f_37988_, 10, (ImmutableList<Behavior<Hoglin>>)ImmutableList.of((Object)new BecomePassiveIfMemoryPresent(MemoryModuleType.f_26356_, 200), (Object)new AnimalMakeLove(EntityType.f_20456_, 0.6f), (Object)new SetWalkTargetFromAttackTargetIfTargetOutOfReach(1.0f), new RunIf<Mob>(Hoglin::m_34552_, new MeleeAttack(40)), new RunIf<Mob>(AgeableMob::m_6162_, new MeleeAttack(15)), new StopAttackingIfTargetInvalid(), new EraseMemoryIf<Hoglin>(HoglinAi::m_34637_, MemoryModuleType.f_26372_)), MemoryModuleType.f_26372_);
    }

    private static void m_34615_(Brain<Hoglin> p_34616_) {
        p_34616_.m_21895_(Activity.f_37991_, 10, (ImmutableList<Behavior<Hoglin>>)ImmutableList.of(SetWalkTargetAwayFrom.m_24019_(MemoryModuleType.f_26383_, 1.3f, 15, false), HoglinAi.m_34571_(), new RunSometimes<LivingEntity>(new SetEntityLookTarget(8.0f), UniformInt.m_146622_(30, 60)), new EraseMemoryIf<Hoglin>(HoglinAi::m_34617_, MemoryModuleType.f_26383_)), MemoryModuleType.f_26383_);
    }

    private static RunOne<Hoglin> m_34571_() {
        return new RunOne<Hoglin>((List<Pair<Behavior<Hoglin>, Integer>>)ImmutableList.of((Object)Pair.of((Object)new RandomStroll(0.4f), (Object)2), (Object)Pair.of((Object)new SetWalkTargetFromLookTarget(0.4f, 3), (Object)2), (Object)Pair.of((Object)new DoNothing(30, 60), (Object)1)));
    }

    protected static void m_34577_(Hoglin p_34578_) {
        Brain<Hoglin> $$1 = p_34578_.m_6274_();
        Activity $$2 = $$1.m_21968_().orElse(null);
        $$1.m_21926_((List<Activity>)ImmutableList.of((Object)Activity.f_37988_, (Object)Activity.f_37991_, (Object)Activity.f_37979_));
        Activity $$3 = $$1.m_21968_().orElse(null);
        if ($$2 != $$3) {
            HoglinAi.m_34593_(p_34578_).ifPresent(p_34578_::m_219179_);
        }
        p_34578_.m_21561_($$1.m_21874_(MemoryModuleType.f_26372_));
    }

    protected static void m_34579_(Hoglin p_34580_, LivingEntity p_34581_) {
        if (p_34580_.m_6162_()) {
            return;
        }
        if (p_34581_.m_6095_() == EntityType.f_20511_ && HoglinAi.m_34622_(p_34580_)) {
            HoglinAi.m_34619_(p_34580_, p_34581_);
            HoglinAi.m_34605_(p_34580_, p_34581_);
            return;
        }
        HoglinAi.m_34634_(p_34580_, p_34581_);
    }

    private static void m_34605_(Hoglin p_34606_, LivingEntity p_34607_) {
        HoglinAi.m_34627_(p_34606_).forEach(p_34590_ -> HoglinAi.m_34612_(p_34590_, p_34607_));
    }

    private static void m_34612_(Hoglin p_34613_, LivingEntity p_34614_) {
        LivingEntity $$2 = p_34614_;
        Brain<Hoglin> $$3 = p_34613_.m_6274_();
        $$2 = BehaviorUtils.m_22625_(p_34613_, $$3.m_21952_(MemoryModuleType.f_26383_), $$2);
        $$2 = BehaviorUtils.m_22625_(p_34613_, $$3.m_21952_(MemoryModuleType.f_26372_), $$2);
        HoglinAi.m_34619_(p_34613_, $$2);
    }

    private static void m_34619_(Hoglin p_34620_, LivingEntity p_34621_) {
        p_34620_.m_6274_().m_21936_(MemoryModuleType.f_26372_);
        p_34620_.m_6274_().m_21936_(MemoryModuleType.f_26370_);
        p_34620_.m_6274_().m_21882_(MemoryModuleType.f_26383_, p_34621_, f_34568_.m_214085_(p_34620_.f_19853_.f_46441_));
    }

    private static Optional<? extends LivingEntity> m_34610_(Hoglin p_34611_) {
        if (HoglinAi.m_34603_(p_34611_) || HoglinAi.m_34637_(p_34611_)) {
            return Optional.empty();
        }
        return p_34611_.m_6274_().m_21952_(MemoryModuleType.f_148206_);
    }

    static boolean m_34585_(Hoglin p_34586_, BlockPos p_34587_) {
        Optional<BlockPos> $$2 = p_34586_.m_6274_().m_21952_(MemoryModuleType.f_26356_);
        return $$2.isPresent() && $$2.get().m_123314_(p_34587_, 8.0);
    }

    private static boolean m_34617_(Hoglin p_34618_) {
        return p_34618_.m_34552_() && !HoglinAi.m_34622_(p_34618_);
    }

    private static boolean m_34622_(Hoglin p_34623_) {
        int $$2;
        if (p_34623_.m_6162_()) {
            return false;
        }
        int $$1 = p_34623_.m_6274_().m_21952_(MemoryModuleType.f_26352_).orElse(0);
        return $$1 > ($$2 = p_34623_.m_6274_().m_21952_(MemoryModuleType.f_26353_).orElse(0) + 1);
    }

    protected static void m_34595_(Hoglin p_34596_, LivingEntity p_34597_) {
        Brain<Hoglin> $$2 = p_34596_.m_6274_();
        $$2.m_21936_(MemoryModuleType.f_26357_);
        $$2.m_21936_(MemoryModuleType.f_26375_);
        if (p_34596_.m_6162_()) {
            HoglinAi.m_34612_(p_34596_, p_34597_);
            return;
        }
        HoglinAi.m_34624_(p_34596_, p_34597_);
    }

    private static void m_34624_(Hoglin p_34625_, LivingEntity p_34626_) {
        if (p_34625_.m_6274_().m_21954_(Activity.f_37991_) && p_34626_.m_6095_() == EntityType.f_20511_) {
            return;
        }
        if (p_34626_.m_6095_() == EntityType.f_20456_) {
            return;
        }
        if (BehaviorUtils.m_22598_(p_34625_, p_34626_, 4.0)) {
            return;
        }
        if (!Sensor.m_148312_(p_34625_, p_34626_)) {
            return;
        }
        HoglinAi.m_34629_(p_34625_, p_34626_);
        HoglinAi.m_34634_(p_34625_, p_34626_);
    }

    private static void m_34629_(Hoglin p_34630_, LivingEntity p_34631_) {
        Brain<Hoglin> $$2 = p_34630_.m_6274_();
        $$2.m_21936_(MemoryModuleType.f_26326_);
        $$2.m_21936_(MemoryModuleType.f_26375_);
        $$2.m_21882_(MemoryModuleType.f_26372_, p_34631_, 200L);
    }

    private static void m_34634_(Hoglin p_34635_, LivingEntity p_34636_) {
        HoglinAi.m_34627_(p_34635_).forEach(p_34574_ -> HoglinAi.m_34639_(p_34574_, p_34636_));
    }

    private static void m_34639_(Hoglin p_34640_, LivingEntity p_34641_) {
        if (HoglinAi.m_34603_(p_34640_)) {
            return;
        }
        Optional<LivingEntity> $$2 = p_34640_.m_6274_().m_21952_(MemoryModuleType.f_26372_);
        LivingEntity $$3 = BehaviorUtils.m_22625_(p_34640_, $$2, p_34641_);
        HoglinAi.m_34629_(p_34640_, $$3);
    }

    public static Optional<SoundEvent> m_34593_(Hoglin p_34594_) {
        return p_34594_.m_6274_().m_21968_().map(p_34600_ -> HoglinAi.m_34582_(p_34594_, p_34600_));
    }

    private static SoundEvent m_34582_(Hoglin p_34583_, Activity p_34584_) {
        if (p_34584_ == Activity.f_37991_ || p_34583_.m_34554_()) {
            return SoundEvents.f_11962_;
        }
        if (p_34584_ == Activity.f_37988_) {
            return SoundEvents.f_11957_;
        }
        if (HoglinAi.m_34632_(p_34583_)) {
            return SoundEvents.f_11962_;
        }
        return SoundEvents.f_11956_;
    }

    private static List<Hoglin> m_34627_(Hoglin p_34628_) {
        return p_34628_.m_6274_().m_21952_(MemoryModuleType.f_26348_).orElse((List<Hoglin>)ImmutableList.of());
    }

    private static boolean m_34632_(Hoglin p_34633_) {
        return p_34633_.m_6274_().m_21874_(MemoryModuleType.f_26356_);
    }

    private static boolean m_34637_(Hoglin p_34638_) {
        return p_34638_.m_6274_().m_21874_(MemoryModuleType.f_26375_);
    }

    protected static boolean m_34603_(Hoglin p_34604_) {
        return p_34604_.m_6274_().m_21874_(MemoryModuleType.f_26357_);
    }
}

