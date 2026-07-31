/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.behavior.AcquirePoi;
import net.minecraft.world.entity.ai.behavior.AssignProfessionFromJobSite;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.CelebrateVillagersSurvivedRaid;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.behavior.GiveGiftToHero;
import net.minecraft.world.entity.ai.behavior.GoOutsideToCelebrate;
import net.minecraft.world.entity.ai.behavior.GoToClosestVillage;
import net.minecraft.world.entity.ai.behavior.GoToPotentialJobSite;
import net.minecraft.world.entity.ai.behavior.GoToWantedItem;
import net.minecraft.world.entity.ai.behavior.HarvestFarmland;
import net.minecraft.world.entity.ai.behavior.InsideBrownianWalk;
import net.minecraft.world.entity.ai.behavior.InteractWith;
import net.minecraft.world.entity.ai.behavior.InteractWithDoor;
import net.minecraft.world.entity.ai.behavior.JumpOnBed;
import net.minecraft.world.entity.ai.behavior.LocateHidingPlace;
import net.minecraft.world.entity.ai.behavior.LocateHidingPlaceDuringRaid;
import net.minecraft.world.entity.ai.behavior.LookAndFollowTradingPlayerSink;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.PlayTagWithOtherKids;
import net.minecraft.world.entity.ai.behavior.PoiCompetitorScan;
import net.minecraft.world.entity.ai.behavior.ReactToBell;
import net.minecraft.world.entity.ai.behavior.ResetProfession;
import net.minecraft.world.entity.ai.behavior.ResetRaidStatus;
import net.minecraft.world.entity.ai.behavior.RingBell;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetClosestHomeAsWalkTarget;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetHiddenState;
import net.minecraft.world.entity.ai.behavior.SetLookAndInteract;
import net.minecraft.world.entity.ai.behavior.SetRaidStatus;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromBlockMemory;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.ShowTradesToPlayer;
import net.minecraft.world.entity.ai.behavior.SleepInBed;
import net.minecraft.world.entity.ai.behavior.SocializeAtBell;
import net.minecraft.world.entity.ai.behavior.StrollAroundPoi;
import net.minecraft.world.entity.ai.behavior.StrollToPoi;
import net.minecraft.world.entity.ai.behavior.StrollToPoiList;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.behavior.TradeWithVillager;
import net.minecraft.world.entity.ai.behavior.UpdateActivityFromSchedule;
import net.minecraft.world.entity.ai.behavior.UseBonemeal;
import net.minecraft.world.entity.ai.behavior.ValidateNearbyPoi;
import net.minecraft.world.entity.ai.behavior.VictoryStroll;
import net.minecraft.world.entity.ai.behavior.VillageBoundRandomStroll;
import net.minecraft.world.entity.ai.behavior.VillagerCalmDown;
import net.minecraft.world.entity.ai.behavior.VillagerMakeLove;
import net.minecraft.world.entity.ai.behavior.VillagerPanicTrigger;
import net.minecraft.world.entity.ai.behavior.WakeUp;
import net.minecraft.world.entity.ai.behavior.WorkAtComposter;
import net.minecraft.world.entity.ai.behavior.WorkAtPoi;
import net.minecraft.world.entity.ai.behavior.YieldJobSite;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;

public class VillagerGoalPackages {
    private static final float f_148040_ = 0.4f;

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24585_(VillagerProfession p_24586_, float p_24587_) {
        return ImmutableList.of((Object)Pair.of((Object)0, (Object)new Swim(0.8f)), (Object)Pair.of((Object)0, (Object)new InteractWithDoor()), (Object)Pair.of((Object)0, (Object)new LookAtTargetSink(45, 90)), (Object)Pair.of((Object)0, (Object)new VillagerPanicTrigger()), (Object)Pair.of((Object)0, (Object)new WakeUp()), (Object)Pair.of((Object)0, (Object)new ReactToBell()), (Object)Pair.of((Object)0, (Object)new SetRaidStatus()), (Object)Pair.of((Object)0, (Object)new ValidateNearbyPoi(p_24586_.f_219628_(), MemoryModuleType.f_26360_)), (Object)Pair.of((Object)0, (Object)new ValidateNearbyPoi(p_24586_.f_219629_(), MemoryModuleType.f_26361_)), (Object)Pair.of((Object)1, (Object)new MoveToTargetSink()), (Object)Pair.of((Object)2, (Object)new PoiCompetitorScan(p_24586_)), (Object)Pair.of((Object)3, (Object)new LookAndFollowTradingPlayerSink(p_24587_)), (Object[])new Pair[]{Pair.of((Object)5, new GoToWantedItem(p_24587_, false, 4)), Pair.of((Object)6, (Object)new AcquirePoi(p_24586_.f_219629_(), MemoryModuleType.f_26360_, MemoryModuleType.f_26361_, true, Optional.empty())), Pair.of((Object)7, (Object)new GoToPotentialJobSite(p_24587_)), Pair.of((Object)8, (Object)new YieldJobSite(p_24587_)), Pair.of((Object)10, (Object)new AcquirePoi(p_217499_ -> p_217499_.m_203565_(PoiTypes.f_218060_), MemoryModuleType.f_26359_, false, Optional.of((byte)14))), Pair.of((Object)10, (Object)new AcquirePoi(p_217497_ -> p_217497_.m_203565_(PoiTypes.f_218061_), MemoryModuleType.f_26362_, true, Optional.of((byte)14))), Pair.of((Object)10, (Object)new AssignProfessionFromJobSite()), Pair.of((Object)10, (Object)new ResetProfession())});
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24589_(VillagerProfession p_24590_, float p_24591_) {
        WorkAtPoi $$3;
        if (p_24590_ == VillagerProfession.f_35590_) {
            WorkAtComposter $$2 = new WorkAtComposter();
        } else {
            $$3 = new WorkAtPoi();
        }
        return ImmutableList.of(VillagerGoalPackages.m_24588_(), (Object)Pair.of((Object)5, new RunOne(ImmutableList.of((Object)Pair.of((Object)$$3, (Object)7), (Object)Pair.of((Object)new StrollAroundPoi(MemoryModuleType.f_26360_, 0.4f, 4), (Object)2), (Object)Pair.of((Object)new StrollToPoi(MemoryModuleType.f_26360_, 0.4f, 1, 10), (Object)5), (Object)Pair.of((Object)new StrollToPoiList(MemoryModuleType.f_26363_, p_24591_, 1, 6, MemoryModuleType.f_26360_), (Object)5), (Object)Pair.of((Object)new HarvestFarmland(), (Object)(p_24590_ == VillagerProfession.f_35590_ ? 2 : 5)), (Object)Pair.of((Object)new UseBonemeal(), (Object)(p_24590_ == VillagerProfession.f_35590_ ? 4 : 7))))), (Object)Pair.of((Object)10, (Object)new ShowTradesToPlayer(400, 1600)), (Object)Pair.of((Object)10, (Object)new SetLookAndInteract(EntityType.f_20532_, 4)), (Object)Pair.of((Object)2, (Object)new SetWalkTargetFromBlockMemory(MemoryModuleType.f_26360_, p_24591_, 9, 100, 1200)), (Object)Pair.of((Object)3, (Object)new GiveGiftToHero(100)), (Object)Pair.of((Object)99, (Object)new UpdateActivityFromSchedule()));
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24583_(float p_24584_) {
        return ImmutableList.of((Object)Pair.of((Object)0, (Object)new MoveToTargetSink(80, 120)), VillagerGoalPackages.m_24582_(), (Object)Pair.of((Object)5, (Object)new PlayTagWithOtherKids()), (Object)Pair.of((Object)5, new RunOne((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26366_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), ImmutableList.of((Object)Pair.of(InteractWith.m_23260_(EntityType.f_20492_, 8, MemoryModuleType.f_26374_, p_24584_, 2), (Object)2), (Object)Pair.of(InteractWith.m_23260_(EntityType.f_20553_, 8, MemoryModuleType.f_26374_, p_24584_, 2), (Object)1), (Object)Pair.of((Object)new VillageBoundRandomStroll(p_24584_), (Object)1), (Object)Pair.of((Object)new SetWalkTargetFromLookTarget(p_24584_, 2), (Object)1), (Object)Pair.of((Object)new JumpOnBed(p_24584_), (Object)2), (Object)Pair.of((Object)new DoNothing(20, 40), (Object)2)))), (Object)Pair.of((Object)99, (Object)new UpdateActivityFromSchedule()));
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24592_(VillagerProfession p_24593_, float p_24594_) {
        return ImmutableList.of((Object)Pair.of((Object)2, (Object)new SetWalkTargetFromBlockMemory(MemoryModuleType.f_26359_, p_24594_, 1, 150, 1200)), (Object)Pair.of((Object)3, (Object)new ValidateNearbyPoi(p_217495_ -> p_217495_.m_203565_(PoiTypes.f_218060_), MemoryModuleType.f_26359_)), (Object)Pair.of((Object)3, (Object)new SleepInBed()), (Object)Pair.of((Object)5, new RunOne((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26359_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), ImmutableList.of((Object)Pair.of((Object)new SetClosestHomeAsWalkTarget(p_24594_), (Object)1), (Object)Pair.of((Object)new InsideBrownianWalk(p_24594_), (Object)4), (Object)Pair.of((Object)new GoToClosestVillage(p_24594_, 4), (Object)2), (Object)Pair.of((Object)new DoNothing(20, 40), (Object)2)))), VillagerGoalPackages.m_24588_(), (Object)Pair.of((Object)99, (Object)new UpdateActivityFromSchedule()));
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24595_(VillagerProfession p_24596_, float p_24597_) {
        return ImmutableList.of((Object)Pair.of((Object)2, new RunOne(ImmutableList.of((Object)Pair.of((Object)new StrollAroundPoi(MemoryModuleType.f_26362_, 0.4f, 40), (Object)2), (Object)Pair.of((Object)new SocializeAtBell(), (Object)2)))), (Object)Pair.of((Object)10, (Object)new ShowTradesToPlayer(400, 1600)), (Object)Pair.of((Object)10, (Object)new SetLookAndInteract(EntityType.f_20532_, 4)), (Object)Pair.of((Object)2, (Object)new SetWalkTargetFromBlockMemory(MemoryModuleType.f_26362_, p_24597_, 6, 100, 200)), (Object)Pair.of((Object)3, (Object)new GiveGiftToHero(100)), (Object)Pair.of((Object)3, (Object)new ValidateNearbyPoi(p_217493_ -> p_217493_.m_203565_(PoiTypes.f_218061_), MemoryModuleType.f_26362_)), (Object)Pair.of((Object)3, new GateBehavior((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(), (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.f_26374_), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE, ImmutableList.of((Object)Pair.of((Object)new TradeWithVillager(), (Object)1)))), VillagerGoalPackages.m_24582_(), (Object)Pair.of((Object)99, (Object)new UpdateActivityFromSchedule()));
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24598_(VillagerProfession p_24599_, float p_24600_) {
        return ImmutableList.of((Object)Pair.of((Object)2, new RunOne(ImmutableList.of((Object)Pair.of(InteractWith.m_23260_(EntityType.f_20492_, 8, MemoryModuleType.f_26374_, p_24600_, 2), (Object)2), (Object)Pair.of(new InteractWith<Villager, AgeableMob>(EntityType.f_20492_, 8, AgeableMob::m_35506_, AgeableMob::m_35506_, MemoryModuleType.f_26375_, p_24600_, 2), (Object)1), (Object)Pair.of(InteractWith.m_23260_(EntityType.f_20553_, 8, MemoryModuleType.f_26374_, p_24600_, 2), (Object)1), (Object)Pair.of((Object)new VillageBoundRandomStroll(p_24600_), (Object)1), (Object)Pair.of((Object)new SetWalkTargetFromLookTarget(p_24600_, 2), (Object)1), (Object)Pair.of((Object)new JumpOnBed(p_24600_), (Object)1), (Object)Pair.of((Object)new DoNothing(30, 60), (Object)1)))), (Object)Pair.of((Object)3, (Object)new GiveGiftToHero(100)), (Object)Pair.of((Object)3, (Object)new SetLookAndInteract(EntityType.f_20532_, 4)), (Object)Pair.of((Object)3, (Object)new ShowTradesToPlayer(400, 1600)), (Object)Pair.of((Object)3, new GateBehavior((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(), (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.f_26374_), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE, ImmutableList.of((Object)Pair.of((Object)new TradeWithVillager(), (Object)1)))), (Object)Pair.of((Object)3, new GateBehavior((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(), (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.f_26375_), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE, ImmutableList.of((Object)Pair.of((Object)new VillagerMakeLove(), (Object)1)))), VillagerGoalPackages.m_24582_(), (Object)Pair.of((Object)99, (Object)new UpdateActivityFromSchedule()));
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24601_(VillagerProfession p_24602_, float p_24603_) {
        float $$2 = p_24603_ * 1.5f;
        return ImmutableList.of((Object)Pair.of((Object)0, (Object)new VillagerCalmDown()), (Object)Pair.of((Object)1, SetWalkTargetAwayFrom.m_24019_(MemoryModuleType.f_26323_, $$2, 6, false)), (Object)Pair.of((Object)1, SetWalkTargetAwayFrom.m_24019_(MemoryModuleType.f_26382_, $$2, 6, false)), (Object)Pair.of((Object)3, (Object)new VillageBoundRandomStroll($$2, 2, 2)), VillagerGoalPackages.m_24588_());
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24604_(VillagerProfession p_24605_, float p_24606_) {
        return ImmutableList.of((Object)Pair.of((Object)0, (Object)new RingBell()), (Object)Pair.of((Object)0, new RunOne(ImmutableList.of((Object)Pair.of((Object)new SetWalkTargetFromBlockMemory(MemoryModuleType.f_26362_, p_24606_ * 1.5f, 2, 150, 200), (Object)6), (Object)Pair.of((Object)new VillageBoundRandomStroll(p_24606_ * 1.5f), (Object)2)))), VillagerGoalPackages.m_24588_(), (Object)Pair.of((Object)99, (Object)new ResetRaidStatus()));
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24607_(VillagerProfession p_24608_, float p_24609_) {
        return ImmutableList.of((Object)Pair.of((Object)0, new RunOne(ImmutableList.of((Object)Pair.of((Object)new GoOutsideToCelebrate(p_24609_), (Object)5), (Object)Pair.of((Object)new VictoryStroll(p_24609_ * 1.1f), (Object)2)))), (Object)Pair.of((Object)0, (Object)new CelebrateVillagersSurvivedRaid(600, 600)), (Object)Pair.of((Object)2, (Object)new LocateHidingPlaceDuringRaid(24, p_24609_ * 1.4f)), VillagerGoalPackages.m_24588_(), (Object)Pair.of((Object)99, (Object)new ResetRaidStatus()));
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> m_24610_(VillagerProfession p_24611_, float p_24612_) {
        int $$2 = 2;
        return ImmutableList.of((Object)Pair.of((Object)0, (Object)new SetHiddenState(15, 3)), (Object)Pair.of((Object)1, (Object)new LocateHidingPlace(32, p_24612_ * 1.25f, 2)), VillagerGoalPackages.m_24588_());
    }

    private static Pair<Integer, Behavior<LivingEntity>> m_24582_() {
        return Pair.of((Object)5, new RunOne(ImmutableList.of((Object)Pair.of((Object)new SetEntityLookTarget(EntityType.f_20553_, 8.0f), (Object)8), (Object)Pair.of((Object)new SetEntityLookTarget(EntityType.f_20492_, 8.0f), (Object)2), (Object)Pair.of((Object)new SetEntityLookTarget(EntityType.f_20532_, 8.0f), (Object)2), (Object)Pair.of((Object)new SetEntityLookTarget(MobCategory.CREATURE, 8.0f), (Object)1), (Object)Pair.of((Object)new SetEntityLookTarget(MobCategory.WATER_CREATURE, 8.0f), (Object)1), (Object)Pair.of((Object)new SetEntityLookTarget(MobCategory.AXOLOTLS, 8.0f), (Object)1), (Object)Pair.of((Object)new SetEntityLookTarget(MobCategory.UNDERGROUND_WATER_CREATURE, 8.0f), (Object)1), (Object)Pair.of((Object)new SetEntityLookTarget(MobCategory.WATER_AMBIENT, 8.0f), (Object)1), (Object)Pair.of((Object)new SetEntityLookTarget(MobCategory.MONSTER, 8.0f), (Object)1), (Object)Pair.of((Object)new DoNothing(30, 60), (Object)2))));
    }

    private static Pair<Integer, Behavior<LivingEntity>> m_24588_() {
        return Pair.of((Object)5, new RunOne(ImmutableList.of((Object)Pair.of((Object)new SetEntityLookTarget(EntityType.f_20492_, 8.0f), (Object)2), (Object)Pair.of((Object)new SetEntityLookTarget(EntityType.f_20532_, 8.0f), (Object)2), (Object)Pair.of((Object)new DoNothing(30, 60), (Object)8))));
    }
}

