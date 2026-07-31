/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.world.entity.animal.axolotl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.EraseMemoryIf;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RandomSwim;
import net.minecraft.world.entity.ai.behavior.RunIf;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.RunSometimes;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.TryFindWater;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.axolotl.PlayDead;
import net.minecraft.world.entity.animal.axolotl.ValidatePlayDead;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class AxolotlAi {
    private static final UniformInt f_149279_ = UniformInt.m_146622_(5, 16);
    private static final float f_149280_ = 0.2f;
    private static final float f_149281_ = 0.15f;
    private static final float f_149282_ = 0.5f;
    private static final float f_149283_ = 0.6f;
    private static final float f_149284_ = 0.6f;

    protected static Brain<?> m_149290_(Brain<Axolotl> p_149291_) {
        AxolotlAi.m_149306_(p_149291_);
        AxolotlAi.m_149308_(p_149291_);
        AxolotlAi.m_149302_(p_149291_);
        AxolotlAi.m_149296_(p_149291_);
        p_149291_.m_21930_((Set<Activity>)ImmutableSet.of((Object)Activity.f_37978_));
        p_149291_.m_21944_(Activity.f_37979_);
        p_149291_.m_21962_();
        return p_149291_;
    }

    private static void m_149296_(Brain<Axolotl> p_149297_) {
        p_149297_.m_21907_(Activity.f_150238_, (ImmutableList<Pair<Integer, Behavior<Axolotl>>>)ImmutableList.of((Object)Pair.of((Object)0, (Object)new PlayDead()), (Object)Pair.of((Object)1, new EraseMemoryIf<Axolotl>(BehaviorUtils::m_217126_, MemoryModuleType.f_148195_))), (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)ImmutableSet.of((Object)Pair.of(MemoryModuleType.f_148195_, (Object)((Object)MemoryStatus.VALUE_PRESENT))), (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.f_148195_));
    }

    private static void m_149302_(Brain<Axolotl> p_149303_) {
        p_149303_.m_21895_(Activity.f_37988_, 0, (ImmutableList<Behavior<Axolotl>>)ImmutableList.of(new StopAttackingIfTargetInvalid<Axolotl>(Axolotl::m_218443_), (Object)new SetWalkTargetFromAttackTargetIfTargetOutOfReach(AxolotlAi::m_149288_), (Object)new MeleeAttack(20), new EraseMemoryIf<Axolotl>(BehaviorUtils::m_217126_, MemoryModuleType.f_26372_)), MemoryModuleType.f_26372_);
    }

    private static void m_149306_(Brain<Axolotl> p_149307_) {
        p_149307_.m_21891_(Activity.f_37978_, 0, (ImmutableList<Behavior<Axolotl>>)ImmutableList.of((Object)new LookAtTargetSink(45, 90), (Object)new MoveToTargetSink(), (Object)new ValidatePlayDead(), (Object)new CountDownCooldownTicks(MemoryModuleType.f_148197_)));
    }

    private static void m_149308_(Brain<Axolotl> p_149309_) {
        p_149309_.m_21900_(Activity.f_37979_, (ImmutableList<Pair<Integer, Behavior<Axolotl>>>)ImmutableList.of((Object)Pair.of((Object)0, new RunSometimes<LivingEntity>(new SetEntityLookTarget(EntityType.f_20532_, 6.0f), UniformInt.m_146622_(30, 60))), (Object)Pair.of((Object)1, (Object)new AnimalMakeLove(EntityType.f_147039_, 0.2f)), (Object)Pair.of((Object)2, new RunOne(ImmutableList.of((Object)Pair.of((Object)new FollowTemptation(AxolotlAi::m_149300_), (Object)1), (Object)Pair.of(new BabyFollowAdult(f_149279_, AxolotlAi::m_149294_), (Object)1)))), (Object)Pair.of((Object)3, new StartAttacking<Axolotl>(AxolotlAi::m_149298_)), (Object)Pair.of((Object)3, (Object)new TryFindWater(6, 0.15f)), (Object)Pair.of((Object)4, new GateBehavior((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), (Set<MemoryModuleType<?>>)ImmutableSet.of(), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.TRY_ALL, ImmutableList.of((Object)Pair.of((Object)new RandomSwim(0.5f), (Object)2), (Object)Pair.of((Object)new RandomStroll(0.15f, false), (Object)2), (Object)Pair.of((Object)new SetWalkTargetFromLookTarget(AxolotlAi::m_182380_, AxolotlAi::m_149300_, 3), (Object)3), (Object)Pair.of(new RunIf<LivingEntity>(Entity::m_20072_, new DoNothing(30, 60)), (Object)5), (Object)Pair.of(new RunIf<LivingEntity>(Entity::m_20096_, new DoNothing(200, 400)), (Object)5))))));
    }

    private static boolean m_182380_(LivingEntity p_182381_) {
        Level $$1 = p_182381_.f_19853_;
        Optional<PositionTracker> $$2 = p_182381_.m_6274_().m_21952_(MemoryModuleType.f_26371_);
        if ($$2.isPresent()) {
            BlockPos $$3 = $$2.get().m_6675_();
            return $$1.m_46801_($$3) == p_182381_.m_20072_();
        }
        return false;
    }

    public static void m_149292_(Axolotl p_149293_) {
        Brain<Axolotl> $$1 = p_149293_.m_6274_();
        Activity $$2 = $$1.m_21968_().orElse(null);
        if ($$2 != Activity.f_150238_) {
            $$1.m_21926_((List<Activity>)ImmutableList.of((Object)Activity.f_150238_, (Object)Activity.f_37988_, (Object)Activity.f_37979_));
            if ($$2 == Activity.f_37988_ && $$1.m_21968_().orElse(null) != Activity.f_37988_) {
                $$1.m_21882_(MemoryModuleType.f_148201_, true, 2400L);
            }
        }
    }

    private static float m_149288_(LivingEntity p_149289_) {
        return p_149289_.m_20072_() ? 0.6f : 0.15f;
    }

    private static float m_149294_(LivingEntity p_149295_) {
        return p_149295_.m_20072_() ? 0.6f : 0.15f;
    }

    private static float m_149300_(LivingEntity p_149301_) {
        return p_149301_.m_20072_() ? 0.5f : 0.15f;
    }

    private static Optional<? extends LivingEntity> m_149298_(Axolotl p_149299_) {
        if (BehaviorUtils.m_217126_(p_149299_)) {
            return Optional.empty();
        }
        return p_149299_.m_6274_().m_21952_(MemoryModuleType.f_148194_);
    }

    public static Ingredient m_149287_() {
        return Ingredient.m_204132_(ItemTags.f_144321_);
    }
}

