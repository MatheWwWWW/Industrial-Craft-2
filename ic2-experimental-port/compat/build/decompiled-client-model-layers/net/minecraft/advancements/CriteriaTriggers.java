/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.BeeNestDestroyedTrigger;
import net.minecraft.advancements.critereon.BredAnimalsTrigger;
import net.minecraft.advancements.critereon.BrewedPotionTrigger;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.ChanneledLightningTrigger;
import net.minecraft.advancements.critereon.ConstructBeaconTrigger;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.CuredZombieVillagerTrigger;
import net.minecraft.advancements.critereon.DistanceTrigger;
import net.minecraft.advancements.critereon.EffectsChangedTrigger;
import net.minecraft.advancements.critereon.EnchantedItemTrigger;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
import net.minecraft.advancements.critereon.FilledBucketTrigger;
import net.minecraft.advancements.critereon.FishingRodHookedTrigger;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemDurabilityTrigger;
import net.minecraft.advancements.critereon.ItemInteractWithBlockTrigger;
import net.minecraft.advancements.critereon.KilledByCrossbowTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.LevitationTrigger;
import net.minecraft.advancements.critereon.LightningStrikeTrigger;
import net.minecraft.advancements.critereon.LootTableTrigger;
import net.minecraft.advancements.critereon.PickedUpItemTrigger;
import net.minecraft.advancements.critereon.PlacedBlockTrigger;
import net.minecraft.advancements.critereon.PlayerHurtEntityTrigger;
import net.minecraft.advancements.critereon.PlayerInteractTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.advancements.critereon.ShotCrossbowTrigger;
import net.minecraft.advancements.critereon.SlideDownBlockTrigger;
import net.minecraft.advancements.critereon.StartRidingTrigger;
import net.minecraft.advancements.critereon.SummonedEntityTrigger;
import net.minecraft.advancements.critereon.TameAnimalTrigger;
import net.minecraft.advancements.critereon.TargetBlockTrigger;
import net.minecraft.advancements.critereon.TradeTrigger;
import net.minecraft.advancements.critereon.UsedEnderEyeTrigger;
import net.minecraft.advancements.critereon.UsedTotemTrigger;
import net.minecraft.advancements.critereon.UsingItemTrigger;
import net.minecraft.resources.ResourceLocation;

public class CriteriaTriggers {
    private static final Map<ResourceLocation, CriterionTrigger<?>> f_10566_ = Maps.newHashMap();
    public static final ImpossibleTrigger f_10567_ = CriteriaTriggers.m_10595_(new ImpossibleTrigger());
    public static final KilledTrigger f_10568_ = CriteriaTriggers.m_10595_(new KilledTrigger(new ResourceLocation("player_killed_entity")));
    public static final KilledTrigger f_10569_ = CriteriaTriggers.m_10595_(new KilledTrigger(new ResourceLocation("entity_killed_player")));
    public static final EnterBlockTrigger f_10570_ = CriteriaTriggers.m_10595_(new EnterBlockTrigger());
    public static final InventoryChangeTrigger f_10571_ = CriteriaTriggers.m_10595_(new InventoryChangeTrigger());
    public static final RecipeUnlockedTrigger f_10572_ = CriteriaTriggers.m_10595_(new RecipeUnlockedTrigger());
    public static final PlayerHurtEntityTrigger f_10573_ = CriteriaTriggers.m_10595_(new PlayerHurtEntityTrigger());
    public static final EntityHurtPlayerTrigger f_10574_ = CriteriaTriggers.m_10595_(new EntityHurtPlayerTrigger());
    public static final EnchantedItemTrigger f_10575_ = CriteriaTriggers.m_10595_(new EnchantedItemTrigger());
    public static final FilledBucketTrigger f_10576_ = CriteriaTriggers.m_10595_(new FilledBucketTrigger());
    public static final BrewedPotionTrigger f_10577_ = CriteriaTriggers.m_10595_(new BrewedPotionTrigger());
    public static final ConstructBeaconTrigger f_10578_ = CriteriaTriggers.m_10595_(new ConstructBeaconTrigger());
    public static final UsedEnderEyeTrigger f_10579_ = CriteriaTriggers.m_10595_(new UsedEnderEyeTrigger());
    public static final SummonedEntityTrigger f_10580_ = CriteriaTriggers.m_10595_(new SummonedEntityTrigger());
    public static final BredAnimalsTrigger f_10581_ = CriteriaTriggers.m_10595_(new BredAnimalsTrigger());
    public static final PlayerTrigger f_10582_ = CriteriaTriggers.m_10595_(new PlayerTrigger(new ResourceLocation("location")));
    public static final PlayerTrigger f_10583_ = CriteriaTriggers.m_10595_(new PlayerTrigger(new ResourceLocation("slept_in_bed")));
    public static final CuredZombieVillagerTrigger f_10584_ = CriteriaTriggers.m_10595_(new CuredZombieVillagerTrigger());
    public static final TradeTrigger f_10585_ = CriteriaTriggers.m_10595_(new TradeTrigger());
    public static final ItemDurabilityTrigger f_10586_ = CriteriaTriggers.m_10595_(new ItemDurabilityTrigger());
    public static final LevitationTrigger f_10587_ = CriteriaTriggers.m_10595_(new LevitationTrigger());
    public static final ChangeDimensionTrigger f_10588_ = CriteriaTriggers.m_10595_(new ChangeDimensionTrigger());
    public static final PlayerTrigger f_10589_ = CriteriaTriggers.m_10595_(new PlayerTrigger(new ResourceLocation("tick")));
    public static final TameAnimalTrigger f_10590_ = CriteriaTriggers.m_10595_(new TameAnimalTrigger());
    public static final PlacedBlockTrigger f_10591_ = CriteriaTriggers.m_10595_(new PlacedBlockTrigger());
    public static final ConsumeItemTrigger f_10592_ = CriteriaTriggers.m_10595_(new ConsumeItemTrigger());
    public static final EffectsChangedTrigger f_10550_ = CriteriaTriggers.m_10595_(new EffectsChangedTrigger());
    public static final UsedTotemTrigger f_10551_ = CriteriaTriggers.m_10595_(new UsedTotemTrigger());
    public static final DistanceTrigger f_10552_ = CriteriaTriggers.m_10595_(new DistanceTrigger(new ResourceLocation("nether_travel")));
    public static final FishingRodHookedTrigger f_10553_ = CriteriaTriggers.m_10595_(new FishingRodHookedTrigger());
    public static final ChanneledLightningTrigger f_10554_ = CriteriaTriggers.m_10595_(new ChanneledLightningTrigger());
    public static final ShotCrossbowTrigger f_10555_ = CriteriaTriggers.m_10595_(new ShotCrossbowTrigger());
    public static final KilledByCrossbowTrigger f_10556_ = CriteriaTriggers.m_10595_(new KilledByCrossbowTrigger());
    public static final PlayerTrigger f_10557_ = CriteriaTriggers.m_10595_(new PlayerTrigger(new ResourceLocation("hero_of_the_village")));
    public static final PlayerTrigger f_10558_ = CriteriaTriggers.m_10595_(new PlayerTrigger(new ResourceLocation("voluntary_exile")));
    public static final SlideDownBlockTrigger f_10559_ = CriteriaTriggers.m_10595_(new SlideDownBlockTrigger());
    public static final BeeNestDestroyedTrigger f_10560_ = CriteriaTriggers.m_10595_(new BeeNestDestroyedTrigger());
    public static final TargetBlockTrigger f_10561_ = CriteriaTriggers.m_10595_(new TargetBlockTrigger());
    public static final ItemInteractWithBlockTrigger f_10562_ = CriteriaTriggers.m_10595_(new ItemInteractWithBlockTrigger(new ResourceLocation("item_used_on_block")));
    public static final LootTableTrigger f_10563_ = CriteriaTriggers.m_10595_(new LootTableTrigger());
    public static final PickedUpItemTrigger f_215654_ = CriteriaTriggers.m_10595_(new PickedUpItemTrigger(new ResourceLocation("thrown_item_picked_up_by_entity")));
    public static final PickedUpItemTrigger f_215655_ = CriteriaTriggers.m_10595_(new PickedUpItemTrigger(new ResourceLocation("thrown_item_picked_up_by_player")));
    public static final PlayerInteractTrigger f_10565_ = CriteriaTriggers.m_10595_(new PlayerInteractTrigger());
    public static final StartRidingTrigger f_145088_ = CriteriaTriggers.m_10595_(new StartRidingTrigger());
    public static final LightningStrikeTrigger f_145089_ = CriteriaTriggers.m_10595_(new LightningStrikeTrigger());
    public static final UsingItemTrigger f_145090_ = CriteriaTriggers.m_10595_(new UsingItemTrigger());
    public static final DistanceTrigger f_184759_ = CriteriaTriggers.m_10595_(new DistanceTrigger(new ResourceLocation("fall_from_height")));
    public static final DistanceTrigger f_184760_ = CriteriaTriggers.m_10595_(new DistanceTrigger(new ResourceLocation("ride_entity_in_lava")));
    public static final KilledTrigger f_215656_ = CriteriaTriggers.m_10595_(new KilledTrigger(new ResourceLocation("kill_mob_near_sculk_catalyst")));
    public static final ItemInteractWithBlockTrigger f_215657_ = CriteriaTriggers.m_10595_(new ItemInteractWithBlockTrigger(new ResourceLocation("allay_drop_item_on_block")));
    public static final PlayerTrigger f_215658_ = CriteriaTriggers.m_10595_(new PlayerTrigger(new ResourceLocation("avoid_vibration")));

    private static <T extends CriterionTrigger<?>> T m_10595_(T p_10596_) {
        if (f_10566_.containsKey(p_10596_.m_7295_())) {
            throw new IllegalArgumentException("Duplicate criterion id " + p_10596_.m_7295_());
        }
        f_10566_.put(p_10596_.m_7295_(), p_10596_);
        return p_10596_;
    }

    @Nullable
    public static <T extends CriterionTriggerInstance> CriterionTrigger<T> m_10597_(ResourceLocation p_10598_) {
        return f_10566_.get(p_10598_);
    }

    public static Iterable<? extends CriterionTrigger<?>> m_10594_() {
        return f_10566_.values();
    }
}

