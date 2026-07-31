/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.world.entity.animal.frog;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.Croak;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.behavior.LongJumpMidJump;
import net.minecraft.world.entity.ai.behavior.LongJumpToPreferredBlock;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RandomSwim;
import net.minecraft.world.entity.ai.behavior.RunIf;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.RunSometimes;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.TryFindLand;
import net.minecraft.world.entity.ai.behavior.TryFindLandNearWater;
import net.minecraft.world.entity.ai.behavior.TryLaySpawnOnWaterNearLand;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.ShootTongue;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

public class FrogAi {
    private static final float f_218560_ = 2.0f;
    private static final float f_218561_ = 1.0f;
    private static final float f_218562_ = 1.0f;
    private static final float f_218563_ = 1.0f;
    private static final float f_218564_ = 0.75f;
    private static final UniformInt f_218565_ = UniformInt.m_146622_(100, 140);
    private static final int f_218566_ = 2;
    private static final int f_218567_ = 4;
    private static final float f_218568_ = 1.5f;
    private static final float f_218569_ = 1.25f;

    protected static void m_218579_(Frog p_218580_, RandomSource p_218581_) {
        p_218580_.m_6274_().m_21879_(MemoryModuleType.f_148199_, f_218565_.m_214085_(p_218581_));
    }

    protected static Brain<?> m_218575_(Brain<Frog> p_218576_) {
        FrogAi.m_218586_(p_218576_);
        FrogAi.m_218590_(p_218576_);
        FrogAi.m_218594_(p_218576_);
        FrogAi.m_218598_(p_218576_);
        FrogAi.m_218606_(p_218576_);
        FrogAi.m_218602_(p_218576_);
        p_218576_.m_21930_((Set<Activity>)ImmutableSet.of((Object)Activity.f_37978_));
        p_218576_.m_21944_(Activity.f_37979_);
        p_218576_.m_21962_();
        return p_218576_;
    }

    private static void m_218586_(Brain<Frog> p_218587_) {
        p_218587_.m_21891_(Activity.f_37978_, 0, (ImmutableList<Behavior<Frog>>)ImmutableList.of((Object)new AnimalPanic(2.0f), (Object)new LookAtTargetSink(45, 90), (Object)new MoveToTargetSink(), (Object)new CountDownCooldownTicks(MemoryModuleType.f_148197_), (Object)new CountDownCooldownTicks(MemoryModuleType.f_148199_)));
    }

    private static void m_218590_(Brain<Frog> p_218591_) {
        p_218591_.m_21903_(Activity.f_37979_, (ImmutableList<Pair<Integer, Behavior<Frog>>>)ImmutableList.of((Object)Pair.of((Object)0, new RunSometimes<LivingEntity>(new SetEntityLookTarget(EntityType.f_20532_, 6.0f), UniformInt.m_146622_(30, 60))), (Object)Pair.of((Object)0, (Object)new AnimalMakeLove(EntityType.f_217012_, 1.0f)), (Object)Pair.of((Object)1, (Object)new FollowTemptation(p_218585_ -> Float.valueOf(1.25f))), (Object)Pair.of((Object)2, new StartAttacking<Frog>(FrogAi::m_218588_, p_218605_ -> p_218605_.m_6274_().m_21952_(MemoryModuleType.f_148194_))), (Object)Pair.of((Object)3, (Object)new TryFindLand(6, 1.0f)), (Object)Pair.of((Object)4, new RunOne((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), ImmutableList.of((Object)Pair.of((Object)new RandomStroll(1.0f), (Object)1), (Object)Pair.of((Object)new SetWalkTargetFromLookTarget(1.0f, 3), (Object)1), (Object)Pair.of((Object)new Croak(), (Object)3), (Object)Pair.of(new RunIf<LivingEntity>(Entity::m_20096_, new DoNothing(5, 20)), (Object)2))))), (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)ImmutableSet.of((Object)Pair.of(MemoryModuleType.f_148200_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), (Object)Pair.of(MemoryModuleType.f_217766_, (Object)((Object)MemoryStatus.VALUE_ABSENT))));
    }

    private static void m_218594_(Brain<Frog> p_218595_) {
        p_218595_.m_21903_(Activity.f_219847_, (ImmutableList<Pair<Integer, Behavior<Frog>>>)ImmutableList.of((Object)Pair.of((Object)0, new RunSometimes<LivingEntity>(new SetEntityLookTarget(EntityType.f_20532_, 6.0f), UniformInt.m_146622_(30, 60))), (Object)Pair.of((Object)1, (Object)new FollowTemptation(p_218574_ -> Float.valueOf(1.25f))), (Object)Pair.of((Object)2, new StartAttacking<Frog>(FrogAi::m_218588_, p_218601_ -> p_218601_.m_6274_().m_21952_(MemoryModuleType.f_148194_))), (Object)Pair.of((Object)3, (Object)new TryFindLand(8, 1.5f)), (Object)Pair.of((Object)5, new GateBehavior((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), (Set<MemoryModuleType<?>>)ImmutableSet.of(), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.TRY_ALL, ImmutableList.of((Object)Pair.of((Object)new RandomSwim(0.75f), (Object)1), (Object)Pair.of((Object)new RandomStroll(1.0f, true), (Object)1), (Object)Pair.of((Object)new SetWalkTargetFromLookTarget(1.0f, 3), (Object)1), (Object)Pair.of(new RunIf<LivingEntity>(Entity::m_20072_, new DoNothing(30, 60)), (Object)5))))), (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)ImmutableSet.of((Object)Pair.of(MemoryModuleType.f_148200_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), (Object)Pair.of(MemoryModuleType.f_217766_, (Object)((Object)MemoryStatus.VALUE_PRESENT))));
    }

    private static void m_218598_(Brain<Frog> p_218599_) {
        p_218599_.m_21903_(Activity.f_219848_, (ImmutableList<Pair<Integer, Behavior<Frog>>>)ImmutableList.of((Object)Pair.of((Object)0, new RunSometimes<LivingEntity>(new SetEntityLookTarget(EntityType.f_20532_, 6.0f), UniformInt.m_146622_(30, 60))), (Object)Pair.of((Object)1, new StartAttacking<Frog>(FrogAi::m_218588_, p_218597_ -> p_218597_.m_6274_().m_21952_(MemoryModuleType.f_148194_))), (Object)Pair.of((Object)2, (Object)new TryFindLandNearWater(8, 1.0f)), (Object)Pair.of((Object)3, (Object)new TryLaySpawnOnWaterNearLand(Blocks.f_220862_, MemoryModuleType.f_217767_)), (Object)Pair.of((Object)4, new RunOne(ImmutableList.of((Object)Pair.of((Object)new RandomStroll(1.0f), (Object)2), (Object)Pair.of((Object)new SetWalkTargetFromLookTarget(1.0f, 3), (Object)1), (Object)Pair.of((Object)new Croak(), (Object)2), (Object)Pair.of(new RunIf<LivingEntity>(Entity::m_20096_, new DoNothing(5, 20)), (Object)1))))), (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)ImmutableSet.of((Object)Pair.of(MemoryModuleType.f_148200_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), (Object)Pair.of(MemoryModuleType.f_217767_, (Object)((Object)MemoryStatus.VALUE_PRESENT))));
    }

    private static void m_218602_(Brain<Frog> p_218603_) {
        p_218603_.m_21903_(Activity.f_150239_, (ImmutableList<Pair<Integer, Behavior<Frog>>>)ImmutableList.of((Object)Pair.of((Object)0, (Object)new LongJumpMidJump(f_218565_, SoundEvents.f_215696_)), (Object)Pair.of((Object)1, new LongJumpToPreferredBlock<Frog>(f_218565_, 2, 4, 1.5f, p_218593_ -> SoundEvents.f_215695_, BlockTags.f_215837_, 0.5f, p_218583_ -> p_218583_.m_60713_(Blocks.f_50196_)))), (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)ImmutableSet.of((Object)Pair.of(MemoryModuleType.f_148196_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), (Object)Pair.of(MemoryModuleType.f_26375_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), (Object)Pair.of(MemoryModuleType.f_148199_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), (Object)Pair.of(MemoryModuleType.f_217766_, (Object)((Object)MemoryStatus.VALUE_ABSENT))));
    }

    private static void m_218606_(Brain<Frog> p_218607_) {
        p_218607_.m_21895_(Activity.f_219846_, 0, (ImmutableList<Behavior<Frog>>)ImmutableList.of(new StopAttackingIfTargetInvalid(), (Object)new ShootTongue(SoundEvents.f_215697_, SoundEvents.f_215692_)), MemoryModuleType.f_26372_);
    }

    private static boolean m_218588_(Frog p_218589_) {
        return !BehaviorUtils.m_217126_(p_218589_);
    }

    public static void m_218577_(Frog p_218578_) {
        p_218578_.m_6274_().m_21926_((List<Activity>)ImmutableList.of((Object)Activity.f_219846_, (Object)Activity.f_219848_, (Object)Activity.f_150239_, (Object)Activity.f_219847_, (Object)Activity.f_37979_));
    }

    public static Ingredient m_218572_() {
        return Frog.f_218455_;
    }
}

