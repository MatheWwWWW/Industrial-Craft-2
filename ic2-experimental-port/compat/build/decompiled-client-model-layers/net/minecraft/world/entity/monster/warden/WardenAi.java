/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.world.entity.monster.warden;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.GoToTargetLocation;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.behavior.warden.Digging;
import net.minecraft.world.entity.ai.behavior.warden.Emerging;
import net.minecraft.world.entity.ai.behavior.warden.ForceUnmount;
import net.minecraft.world.entity.ai.behavior.warden.Roar;
import net.minecraft.world.entity.ai.behavior.warden.SetRoarTarget;
import net.minecraft.world.entity.ai.behavior.warden.SetWardenLookTarget;
import net.minecraft.world.entity.ai.behavior.warden.Sniffing;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.entity.ai.behavior.warden.TryToSniff;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.schedule.Activity;

public class WardenAi {
    private static final float f_219493_ = 0.5f;
    private static final float f_219494_ = 0.7f;
    private static final float f_219495_ = 1.2f;
    private static final int f_219496_ = 18;
    private static final int f_219497_ = Mth.m_14167_(100.0f);
    public static final int f_219490_ = Mth.m_14167_(133.59999f);
    public static final int f_219491_ = Mth.m_14167_(84.0f);
    private static final int f_219498_ = Mth.m_14167_(83.2f);
    public static final int f_219492_ = 1200;
    private static final int f_219499_ = 100;
    private static final List<SensorType<? extends Sensor<? super Warden>>> f_219500_ = List.of(SensorType.f_26812_, SensorType.f_217825_);
    private static final List<MemoryModuleType<?>> f_219501_ = List.of(MemoryModuleType.f_148204_, MemoryModuleType.f_148205_, MemoryModuleType.f_26368_, MemoryModuleType.f_148206_, MemoryModuleType.f_26333_, MemoryModuleType.f_26371_, MemoryModuleType.f_26370_, MemoryModuleType.f_26326_, MemoryModuleType.f_26377_, MemoryModuleType.f_26372_, MemoryModuleType.f_26373_, MemoryModuleType.f_148194_, MemoryModuleType.f_217782_, MemoryModuleType.f_217783_, MemoryModuleType.f_217784_, MemoryModuleType.f_217785_, MemoryModuleType.f_217786_, MemoryModuleType.f_217769_, MemoryModuleType.f_217770_, MemoryModuleType.f_217771_, MemoryModuleType.f_217772_, MemoryModuleType.f_217773_, MemoryModuleType.f_217774_, MemoryModuleType.f_217775_, MemoryModuleType.f_217776_, MemoryModuleType.f_217777_);
    private static final Behavior<Warden> f_219502_ = new Behavior<Warden>((Map)ImmutableMap.of(MemoryModuleType.f_217770_, (Object)((Object)MemoryStatus.REGISTERED))){

        @Override
        protected void m_6735_(ServerLevel p_219554_, Warden p_219555_, long p_219556_) {
            WardenAi.m_219505_(p_219555_);
        }
    };

    public static void m_219512_(Warden p_219513_) {
        p_219513_.m_6274_().m_21926_((List<Activity>)ImmutableList.of((Object)Activity.f_219852_, (Object)Activity.f_219853_, (Object)Activity.f_219851_, (Object)Activity.f_37988_, (Object)Activity.f_219850_, (Object)Activity.f_219849_, (Object)Activity.f_37979_));
    }

    protected static Brain<?> m_219520_(Warden p_219521_, Dynamic<?> p_219522_) {
        Brain.Provider $$2 = Brain.m_21923_(f_219501_, f_219500_);
        Brain<Warden> $$3 = $$2.m_22073_(p_219522_);
        WardenAi.m_219510_($$3);
        WardenAi.m_219526_($$3);
        WardenAi.m_219531_($$3);
        WardenAi.m_219536_($$3);
        WardenAi.m_219545_($$3);
        WardenAi.m_219517_(p_219521_, $$3);
        WardenAi.m_219541_($$3);
        WardenAi.m_219543_($$3);
        $$3.m_21930_((Set<Activity>)ImmutableSet.of((Object)Activity.f_37978_));
        $$3.m_21944_(Activity.f_37979_);
        $$3.m_21962_();
        return $$3;
    }

    private static void m_219510_(Brain<Warden> p_219511_) {
        p_219511_.m_21891_(Activity.f_37978_, 0, (ImmutableList<Behavior<Warden>>)ImmutableList.of((Object)new Swim(0.8f), (Object)new SetWardenLookTarget(), (Object)new LookAtTargetSink(45, 90), (Object)new MoveToTargetSink()));
    }

    private static void m_219526_(Brain<Warden> p_219527_) {
        p_219527_.m_21895_(Activity.f_219852_, 5, (ImmutableList<Behavior<Warden>>)ImmutableList.of(new Emerging(f_219490_)), MemoryModuleType.f_217786_);
    }

    private static void m_219531_(Brain<Warden> p_219532_) {
        p_219532_.m_21903_(Activity.f_219853_, (ImmutableList<Pair<Integer, Behavior<Warden>>>)ImmutableList.of((Object)Pair.of((Object)0, (Object)new ForceUnmount()), (Object)Pair.of((Object)1, new Digging(f_219497_))), (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)ImmutableSet.of((Object)Pair.of(MemoryModuleType.f_217782_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), (Object)Pair.of(MemoryModuleType.f_217770_, (Object)((Object)MemoryStatus.VALUE_ABSENT))));
    }

    private static void m_219536_(Brain<Warden> p_219537_) {
        p_219537_.m_21891_(Activity.f_37979_, 10, (ImmutableList<Behavior<Warden>>)ImmutableList.of(new SetRoarTarget<Warden>(Warden::m_219448_), (Object)new TryToSniff(), new RunOne((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_217785_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), ImmutableList.of((Object)Pair.of((Object)new RandomStroll(0.5f), (Object)2), (Object)Pair.of((Object)new DoNothing(30, 60), (Object)1)))));
    }

    private static void m_219541_(Brain<Warden> p_219542_) {
        p_219542_.m_21895_(Activity.f_219850_, 5, (ImmutableList<Behavior<Warden>>)ImmutableList.of(new SetRoarTarget<Warden>(Warden::m_219448_), new GoToTargetLocation(MemoryModuleType.f_217783_, 2, 0.7f)), MemoryModuleType.f_217783_);
    }

    private static void m_219543_(Brain<Warden> p_219544_) {
        p_219544_.m_21895_(Activity.f_219849_, 5, (ImmutableList<Behavior<Warden>>)ImmutableList.of(new SetRoarTarget<Warden>(Warden::m_219448_), new Sniffing(f_219498_)), MemoryModuleType.f_217785_);
    }

    private static void m_219545_(Brain<Warden> p_219546_) {
        p_219546_.m_21895_(Activity.f_219851_, 10, (ImmutableList<Behavior<Warden>>)ImmutableList.of((Object)new Roar()), MemoryModuleType.f_217782_);
    }

    private static void m_219517_(Warden p_219518_, Brain<Warden> p_219519_) {
        p_219519_.m_21895_(Activity.f_37988_, 10, (ImmutableList<Behavior<Warden>>)ImmutableList.of(f_219502_, new StopAttackingIfTargetInvalid<Warden>(p_219540_ -> !p_219518_.m_219446_().m_219236_() || !p_219518_.m_219385_((Entity)p_219540_), WardenAi::m_219528_, false), (Object)new SetEntityLookTarget(p_219535_ -> WardenAi.m_219514_(p_219518_, p_219535_), (float)p_219518_.m_21133_(Attributes.f_22277_)), (Object)new SetWalkTargetFromAttackTargetIfTargetOutOfReach(1.2f), (Object)new SonicBoom(), (Object)new MeleeAttack(18)), MemoryModuleType.f_26372_);
    }

    private static boolean m_219514_(Warden p_219515_, LivingEntity p_219516_) {
        return p_219515_.m_6274_().m_21952_(MemoryModuleType.f_26372_).filter(p_219509_ -> p_219509_ == p_219516_).isPresent();
    }

    private static void m_219528_(Warden p_219529_, LivingEntity p_219530_) {
        if (!p_219529_.m_219385_(p_219530_)) {
            p_219529_.m_219428_(p_219530_);
        }
        WardenAi.m_219505_(p_219529_);
    }

    public static void m_219505_(LivingEntity p_219506_) {
        if (p_219506_.m_6274_().m_21874_(MemoryModuleType.f_217770_)) {
            p_219506_.m_6274_().m_21882_(MemoryModuleType.f_217770_, Unit.INSTANCE, 1200L);
        }
    }

    public static void m_219523_(Warden p_219524_, BlockPos p_219525_) {
        if (!p_219524_.f_19853_.m_6857_().m_61937_(p_219525_) || p_219524_.m_219448_().isPresent() || p_219524_.m_6274_().m_21952_(MemoryModuleType.f_26372_).isPresent()) {
            return;
        }
        WardenAi.m_219505_(p_219524_);
        p_219524_.m_6274_().m_21882_(MemoryModuleType.f_217772_, Unit.INSTANCE, 100L);
        p_219524_.m_6274_().m_21882_(MemoryModuleType.f_26371_, new BlockPosTracker(p_219525_), 100L);
        p_219524_.m_6274_().m_21882_(MemoryModuleType.f_217783_, p_219525_, 100L);
        p_219524_.m_6274_().m_21936_(MemoryModuleType.f_26370_);
    }
}

