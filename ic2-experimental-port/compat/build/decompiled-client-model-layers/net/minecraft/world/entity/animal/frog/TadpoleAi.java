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
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomSwim;
import net.minecraft.world.entity.ai.behavior.RunIf;
import net.minecraft.world.entity.ai.behavior.RunSometimes;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.entity.schedule.Activity;

public class TadpoleAi {
    private static final float f_218735_ = 2.0f;
    private static final float f_218736_ = 0.5f;
    private static final float f_218737_ = 1.25f;

    protected static Brain<?> m_218741_(Brain<Tadpole> p_218742_) {
        TadpoleAi.m_218745_(p_218742_);
        TadpoleAi.m_218747_(p_218742_);
        p_218742_.m_21930_((Set<Activity>)ImmutableSet.of((Object)Activity.f_37978_));
        p_218742_.m_21944_(Activity.f_37979_);
        p_218742_.m_21962_();
        return p_218742_;
    }

    private static void m_218745_(Brain<Tadpole> p_218746_) {
        p_218746_.m_21891_(Activity.f_37978_, 0, (ImmutableList<Behavior<Tadpole>>)ImmutableList.of((Object)new AnimalPanic(2.0f), (Object)new LookAtTargetSink(45, 90), (Object)new MoveToTargetSink(), (Object)new CountDownCooldownTicks(MemoryModuleType.f_148197_)));
    }

    private static void m_218747_(Brain<Tadpole> p_218748_) {
        p_218748_.m_21900_(Activity.f_37979_, (ImmutableList<Pair<Integer, Behavior<Tadpole>>>)ImmutableList.of((Object)Pair.of((Object)0, new RunSometimes<LivingEntity>(new SetEntityLookTarget(EntityType.f_20532_, 6.0f), UniformInt.m_146622_(30, 60))), (Object)Pair.of((Object)1, (Object)new FollowTemptation(p_218740_ -> Float.valueOf(1.25f))), (Object)Pair.of((Object)2, new GateBehavior((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), (Set<MemoryModuleType<?>>)ImmutableSet.of(), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.TRY_ALL, ImmutableList.of((Object)Pair.of((Object)new RandomSwim(0.5f), (Object)2), (Object)Pair.of((Object)new SetWalkTargetFromLookTarget(0.5f, 3), (Object)3), (Object)Pair.of(new RunIf<LivingEntity>(Entity::m_20072_, new DoNothing(30, 60)), (Object)5))))));
    }

    public static void m_218743_(Tadpole p_218744_) {
        p_218744_.m_6274_().m_21926_((List<Activity>)ImmutableList.of((Object)Activity.f_37979_));
    }
}

