/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.world.entity.animal.allay;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.FlyingRandomStroll;
import net.minecraft.world.entity.ai.behavior.GoAndGiveItemsToTarget;
import net.minecraft.world.entity.ai.behavior.GoToWantedItem;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.RunSometimes;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StayCloseToTarget;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class AllayAi {
    private static final float f_218396_ = 1.0f;
    private static final float f_218397_ = 2.25f;
    private static final float f_218398_ = 1.75f;
    private static final float f_218399_ = 2.5f;
    private static final int f_218400_ = 4;
    private static final int f_218401_ = 16;
    private static final int f_218402_ = 6;
    private static final int f_218403_ = 30;
    private static final int f_218404_ = 60;
    private static final int f_218405_ = 600;
    private static final int f_218406_ = 32;

    protected static Brain<?> m_218419_(Brain<Allay> p_218420_) {
        AllayAi.m_218425_(p_218420_);
        AllayAi.m_218431_(p_218420_);
        p_218420_.m_21930_((Set<Activity>)ImmutableSet.of((Object)Activity.f_37978_));
        p_218420_.m_21944_(Activity.f_37979_);
        p_218420_.m_21962_();
        return p_218420_;
    }

    private static void m_218425_(Brain<Allay> p_218426_) {
        p_218426_.m_21891_(Activity.f_37978_, 0, (ImmutableList<Behavior<Allay>>)ImmutableList.of((Object)new Swim(0.8f), (Object)new AnimalPanic(2.5f), (Object)new LookAtTargetSink(45, 90), (Object)new MoveToTargetSink(), (Object)new CountDownCooldownTicks(MemoryModuleType.f_217780_), (Object)new CountDownCooldownTicks(MemoryModuleType.f_217781_)));
    }

    private static void m_218431_(Brain<Allay> p_218432_) {
        p_218432_.m_21903_(Activity.f_37979_, (ImmutableList<Pair<Integer, Behavior<Allay>>>)ImmutableList.of((Object)Pair.of((Object)0, new GoToWantedItem<Allay>(p_218428_ -> true, 1.75f, true, 32)), (Object)Pair.of((Object)1, new GoAndGiveItemsToTarget(AllayAi::m_218423_, 2.25f)), (Object)Pair.of((Object)2, new StayCloseToTarget(AllayAi::m_218423_, 4, 16, 2.25f)), (Object)Pair.of((Object)3, new RunSometimes<LivingEntity>(new SetEntityLookTarget(p_218434_ -> true, 6.0f), UniformInt.m_146622_(30, 60))), (Object)Pair.of((Object)4, new RunOne(ImmutableList.of((Object)Pair.of((Object)new FlyingRandomStroll(1.0f), (Object)2), (Object)Pair.of((Object)new SetWalkTargetFromLookTarget(1.0f, 3), (Object)2), (Object)Pair.of((Object)new DoNothing(30, 60), (Object)1))))), (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)ImmutableSet.of());
    }

    public static void m_218421_(Allay p_218422_) {
        p_218422_.m_6274_().m_21926_((List<Activity>)ImmutableList.of((Object)Activity.f_37979_));
    }

    public static void m_218416_(LivingEntity p_218417_, BlockPos p_218418_) {
        Brain<?> $$2 = p_218417_.m_6274_();
        GlobalPos $$3 = GlobalPos.m_122643_(p_218417_.m_9236_().m_46472_(), p_218418_);
        Optional<GlobalPos> $$4 = $$2.m_21952_(MemoryModuleType.f_217779_);
        if ($$4.isEmpty()) {
            $$2.m_21879_(MemoryModuleType.f_217779_, $$3);
            $$2.m_21879_(MemoryModuleType.f_217780_, 600);
        } else if ($$4.get().equals($$3)) {
            $$2.m_21879_(MemoryModuleType.f_217780_, 600);
        }
    }

    private static Optional<PositionTracker> m_218423_(LivingEntity p_218424_) {
        Brain<?> $$1 = p_218424_.m_6274_();
        Optional<GlobalPos> $$2 = $$1.m_21952_(MemoryModuleType.f_217779_);
        if ($$2.isPresent()) {
            GlobalPos $$3 = $$2.get();
            if (AllayAi.m_218412_(p_218424_, $$1, $$3)) {
                return Optional.of(new BlockPosTracker($$3.m_122646_().m_7494_()));
            }
            $$1.m_21936_(MemoryModuleType.f_217779_);
        }
        return AllayAi.m_218429_(p_218424_);
    }

    private static boolean m_218412_(LivingEntity p_218413_, Brain<?> p_218414_, GlobalPos p_218415_) {
        Optional<Integer> $$3 = p_218414_.m_21952_(MemoryModuleType.f_217780_);
        Level $$4 = p_218413_.m_9236_();
        return $$4.m_46472_() == p_218415_.m_122640_() && $$4.m_8055_(p_218415_.m_122646_()).m_60713_(Blocks.f_50065_) && $$3.isPresent();
    }

    private static Optional<PositionTracker> m_218429_(LivingEntity p_218430_) {
        return AllayAi.m_218410_(p_218430_).map(p_218409_ -> new EntityTracker((Entity)p_218409_, true));
    }

    public static Optional<ServerPlayer> m_218410_(LivingEntity p_218411_) {
        Level $$1 = p_218411_.m_9236_();
        if (!$$1.m_5776_() && $$1 instanceof ServerLevel) {
            ServerLevel $$2 = (ServerLevel)$$1;
            Optional<UUID> $$3 = p_218411_.m_6274_().m_21952_(MemoryModuleType.f_217778_);
            if ($$3.isPresent()) {
                Entity $$4 = $$2.m_8791_($$3.get());
                if ($$4 instanceof ServerPlayer) {
                    ServerPlayer $$5 = (ServerPlayer)$$4;
                    if (($$5.f_8941_.m_9294_() || $$5.f_8941_.m_9295_()) && $$5.m_19950_(p_218411_, 64.0)) {
                        return Optional.of($$5);
                    }
                }
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}

