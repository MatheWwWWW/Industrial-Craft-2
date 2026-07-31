/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.world.entity.animal.goat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Set;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.LongJumpMidJump;
import net.minecraft.world.entity.ai.behavior.LongJumpToRandomPos;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.PrepareRamNearestTarget;
import net.minecraft.world.entity.ai.behavior.RamTarget;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.RunSometimes;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class GoatAi {
    public static final int f_149420_ = 20;
    public static final int f_149421_ = 7;
    private static final UniformInt f_149428_ = UniformInt.m_146622_(5, 16);
    private static final float f_149429_ = 1.0f;
    private static final float f_149430_ = 1.0f;
    private static final float f_149431_ = 1.25f;
    private static final float f_149432_ = 1.25f;
    private static final float f_149433_ = 2.0f;
    private static final float f_149434_ = 1.25f;
    private static final UniformInt f_149435_ = UniformInt.m_146622_(600, 1200);
    public static final int f_149422_ = 5;
    public static final int f_149423_ = 5;
    public static final float f_149424_ = 1.5f;
    private static final UniformInt f_149436_ = UniformInt.m_146622_(600, 6000);
    private static final UniformInt f_149437_ = UniformInt.m_146622_(100, 300);
    private static final TargetingConditions f_149438_ = TargetingConditions.m_148352_().m_26888_(p_149452_ -> !p_149452_.m_6095_().equals(EntityType.f_147035_) && p_149452_.f_19853_.m_6857_().m_61935_(p_149452_.m_20191_()));
    private static final float f_149439_ = 3.0f;
    public static final int f_149425_ = 4;
    public static final float f_149426_ = 2.5f;
    public static final float f_149427_ = 1.0f;

    protected static void m_218764_(Goat p_218765_, RandomSource p_218766_) {
        p_218765_.m_6274_().m_21879_(MemoryModuleType.f_148199_, f_149435_.m_214085_(p_218766_));
        p_218765_.m_6274_().m_21879_(MemoryModuleType.f_148202_, f_149436_.m_214085_(p_218766_));
    }

    protected static Brain<?> m_149447_(Brain<Goat> p_149448_) {
        GoatAi.m_149453_(p_149448_);
        GoatAi.m_149457_(p_149448_);
        GoatAi.m_149461_(p_149448_);
        GoatAi.m_149465_(p_149448_);
        p_149448_.m_21930_((Set<Activity>)ImmutableSet.of((Object)Activity.f_37978_));
        p_149448_.m_21944_(Activity.f_37979_);
        p_149448_.m_21962_();
        return p_149448_;
    }

    private static void m_149453_(Brain<Goat> p_149454_) {
        p_149454_.m_21891_(Activity.f_37978_, 0, (ImmutableList<Behavior<Goat>>)ImmutableList.of((Object)new Swim(0.8f), (Object)new AnimalPanic(2.0f), (Object)new LookAtTargetSink(45, 90), (Object)new MoveToTargetSink(), (Object)new CountDownCooldownTicks(MemoryModuleType.f_148197_), (Object)new CountDownCooldownTicks(MemoryModuleType.f_148199_), (Object)new CountDownCooldownTicks(MemoryModuleType.f_148202_)));
    }

    private static void m_149457_(Brain<Goat> p_149458_) {
        p_149458_.m_21903_(Activity.f_37979_, (ImmutableList<Pair<Integer, Behavior<Goat>>>)ImmutableList.of((Object)Pair.of((Object)0, new RunSometimes<LivingEntity>(new SetEntityLookTarget(EntityType.f_20532_, 6.0f), UniformInt.m_146622_(30, 60))), (Object)Pair.of((Object)0, (Object)new AnimalMakeLove(EntityType.f_147035_, 1.0f)), (Object)Pair.of((Object)1, (Object)new FollowTemptation(p_149446_ -> Float.valueOf(1.25f))), (Object)Pair.of((Object)2, new BabyFollowAdult(f_149428_, 1.25f)), (Object)Pair.of((Object)3, new RunOne(ImmutableList.of((Object)Pair.of((Object)new RandomStroll(1.0f), (Object)2), (Object)Pair.of((Object)new SetWalkTargetFromLookTarget(1.0f, 3), (Object)2), (Object)Pair.of((Object)new DoNothing(30, 60), (Object)1))))), (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)ImmutableSet.of((Object)Pair.of(MemoryModuleType.f_148203_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), (Object)Pair.of(MemoryModuleType.f_148200_, (Object)((Object)MemoryStatus.VALUE_ABSENT))));
    }

    private static void m_149461_(Brain<Goat> p_149462_) {
        p_149462_.m_21903_(Activity.f_150239_, (ImmutableList<Pair<Integer, Behavior<Goat>>>)ImmutableList.of((Object)Pair.of((Object)0, (Object)new LongJumpMidJump(f_149435_, SoundEvents.f_144151_)), (Object)Pair.of((Object)1, new LongJumpToRandomPos<Goat>(f_149435_, 5, 5, 1.5f, p_149476_ -> p_149476_.m_149397_() ? SoundEvents.f_144147_ : SoundEvents.f_144167_))), (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)ImmutableSet.of((Object)Pair.of(MemoryModuleType.f_148196_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), (Object)Pair.of(MemoryModuleType.f_26375_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), (Object)Pair.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), (Object)Pair.of(MemoryModuleType.f_148199_, (Object)((Object)MemoryStatus.VALUE_ABSENT))));
    }

    private static void m_149465_(Brain<Goat> p_149466_) {
        p_149466_.m_21903_(Activity.f_150240_, (ImmutableList<Pair<Integer, Behavior<Goat>>>)ImmutableList.of((Object)Pair.of((Object)0, (Object)new RamTarget(p_149474_ -> p_149474_.m_149397_() ? f_149437_ : f_149436_, f_149438_, 3.0f, p_149470_ -> p_149470_.m_6162_() ? 1.0 : 2.5, p_149468_ -> p_149468_.m_149397_() ? SoundEvents.f_144150_ : SoundEvents.f_144170_, p_218772_ -> p_218772_.m_149397_() ? SoundEvents.f_215700_ : SoundEvents.f_215698_)), (Object)Pair.of((Object)1, new PrepareRamNearestTarget<Goat>(p_218770_ -> p_218770_.m_149397_() ? f_149437_.m_142739_() : f_149436_.m_142739_(), 4, 7, 1.25f, f_149438_, 20, p_218768_ -> p_218768_.m_149397_() ? SoundEvents.f_144149_ : SoundEvents.f_144169_))), (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)ImmutableSet.of((Object)Pair.of(MemoryModuleType.f_148196_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), (Object)Pair.of(MemoryModuleType.f_26375_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), (Object)Pair.of(MemoryModuleType.f_148202_, (Object)((Object)MemoryStatus.VALUE_ABSENT))));
    }

    public static void m_149455_(Goat p_149456_) {
        p_149456_.m_6274_().m_21926_((List<Activity>)ImmutableList.of((Object)Activity.f_150240_, (Object)Activity.f_150239_, (Object)Activity.f_37979_));
    }

    public static Ingredient m_149444_() {
        return Ingredient.m_43929_(Items.f_42405_);
    }
}

