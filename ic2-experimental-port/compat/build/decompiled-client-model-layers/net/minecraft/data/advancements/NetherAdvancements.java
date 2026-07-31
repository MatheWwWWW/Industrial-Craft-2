/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.data.advancements;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.BrewedPotionTrigger;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.ConstructBeaconTrigger;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.DistanceTrigger;
import net.minecraft.advancements.critereon.EffectsChangedTrigger;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemDurabilityTrigger;
import net.minecraft.advancements.critereon.ItemInteractWithBlockTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.LootTableTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.advancements.critereon.PickedUpItemTrigger;
import net.minecraft.advancements.critereon.PlayerInteractTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.advancements.critereon.SummonedEntityTrigger;
import net.minecraft.data.advancements.AdventureAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

public class NetherAdvancements
implements Consumer<Consumer<Advancement>> {
    private static final List<ResourceKey<Biome>> f_124015_ = ImmutableList.of(Biomes.f_48209_, Biomes.f_48199_, Biomes.f_48201_, Biomes.f_48200_, Biomes.f_48175_);
    private static final EntityPredicate.Composite f_124016_ = EntityPredicate.Composite.m_36690_(LootItemEntityPropertyCondition.m_81864_(LootContext.EntityTarget.THIS, EntityPredicate.Builder.m_36633_().m_36640_(EntityEquipmentPredicate.Builder.m_32204_().m_32205_(ItemPredicate.Builder.m_45068_().m_151445_(Items.f_42476_).m_45077_()).m_32207_())).m_81807_().m_6409_(), LootItemEntityPropertyCondition.m_81864_(LootContext.EntityTarget.THIS, EntityPredicate.Builder.m_36633_().m_36640_(EntityEquipmentPredicate.Builder.m_32204_().m_32208_(ItemPredicate.Builder.m_45068_().m_151445_(Items.f_42477_).m_45077_()).m_32207_())).m_81807_().m_6409_(), LootItemEntityPropertyCondition.m_81864_(LootContext.EntityTarget.THIS, EntityPredicate.Builder.m_36633_().m_36640_(EntityEquipmentPredicate.Builder.m_32204_().m_32210_(ItemPredicate.Builder.m_45068_().m_151445_(Items.f_42478_).m_45077_()).m_32207_())).m_81807_().m_6409_(), LootItemEntityPropertyCondition.m_81864_(LootContext.EntityTarget.THIS, EntityPredicate.Builder.m_36633_().m_36640_(EntityEquipmentPredicate.Builder.m_32204_().m_32212_(ItemPredicate.Builder.m_45068_().m_151445_(Items.f_42479_).m_45077_()).m_32207_())).m_81807_().m_6409_());

    @Override
    public void accept(Consumer<Advancement> p_124020_) {
        Advancement $$1 = Advancement.Builder.m_138353_().m_138371_(Blocks.f_50452_, Component.m_237115_("advancements.nether.root.title"), Component.m_237115_("advancements.nether.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/nether.png"), FrameType.TASK, false, false, false).m_138386_("entered_nether", ChangeDimensionTrigger.TriggerInstance.m_19782_(Level.f_46429_)).m_138389_(p_124020_, "nether/root");
        Advancement $$2 = Advancement.Builder.m_138353_().m_138398_($$1).m_138371_(Items.f_42613_, Component.m_237115_("advancements.nether.return_to_sender.title"), Component.m_237115_("advancements.nether.return_to_sender.description"), null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(50)).m_138386_("killed_ghast", KilledTrigger.TriggerInstance.m_48136_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20453_), DamageSourcePredicate.Builder.m_25471_().m_25474_(true).m_25472_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20463_)))).m_138389_(p_124020_, "nether/return_to_sender");
        Advancement $$3 = Advancement.Builder.m_138353_().m_138398_($$1).m_138371_(Blocks.f_50197_, Component.m_237115_("advancements.nether.find_fortress.title"), Component.m_237115_("advancements.nether.find_fortress.description"), null, FrameType.TASK, true, true, false).m_138386_("fortress", PlayerTrigger.TriggerInstance.m_222635_(LocationPredicate.m_220589_(BuiltinStructures.f_209859_))).m_138389_(p_124020_, "nether/find_fortress");
        Advancement.Builder.m_138353_().m_138398_($$1).m_138371_(Items.f_42676_, Component.m_237115_("advancements.nether.fast_travel.title"), Component.m_237115_("advancements.nether.fast_travel.description"), null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(100)).m_138386_("travelled", DistanceTrigger.TriggerInstance.m_186192_(DistancePredicate.m_148836_(MinMaxBounds.Doubles.m_154804_(7000.0)))).m_138389_(p_124020_, "nether/fast_travel");
        Advancement.Builder.m_138353_().m_138398_($$2).m_138371_(Items.f_42586_, Component.m_237115_("advancements.nether.uneasy_alliance.title"), Component.m_237115_("advancements.nether.uneasy_alliance.description"), null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(100)).m_138386_("killed_ghast", KilledTrigger.TriggerInstance.m_48134_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20453_).m_36650_(LocationPredicate.m_52638_(Level.f_46428_)))).m_138389_(p_124020_, "nether/uneasy_alliance");
        Advancement $$4 = Advancement.Builder.m_138353_().m_138398_($$3).m_138371_(Blocks.f_50312_, Component.m_237115_("advancements.nether.get_wither_skull.title"), Component.m_237115_("advancements.nether.get_wither_skull.description"), null, FrameType.TASK, true, true, false).m_138386_("wither_skull", InventoryChangeTrigger.TriggerInstance.m_43199_(Blocks.f_50312_)).m_138389_(p_124020_, "nether/get_wither_skull");
        Advancement $$5 = Advancement.Builder.m_138353_().m_138398_($$4).m_138371_(Items.f_42686_, Component.m_237115_("advancements.nether.summon_wither.title"), Component.m_237115_("advancements.nether.summon_wither.description"), null, FrameType.TASK, true, true, false).m_138386_("summoned", SummonedEntityTrigger.TriggerInstance.m_68275_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20496_))).m_138389_(p_124020_, "nether/summon_wither");
        Advancement $$6 = Advancement.Builder.m_138353_().m_138398_($$3).m_138371_(Items.f_42585_, Component.m_237115_("advancements.nether.obtain_blaze_rod.title"), Component.m_237115_("advancements.nether.obtain_blaze_rod.description"), null, FrameType.TASK, true, true, false).m_138386_("blaze_rod", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42585_)).m_138389_(p_124020_, "nether/obtain_blaze_rod");
        Advancement $$7 = Advancement.Builder.m_138353_().m_138398_($$5).m_138371_(Blocks.f_50273_, Component.m_237115_("advancements.nether.create_beacon.title"), Component.m_237115_("advancements.nether.create_beacon.description"), null, FrameType.TASK, true, true, false).m_138386_("beacon", ConstructBeaconTrigger.TriggerInstance.m_22765_(MinMaxBounds.Ints.m_55386_(1))).m_138389_(p_124020_, "nether/create_beacon");
        Advancement.Builder.m_138353_().m_138398_($$7).m_138371_(Blocks.f_50273_, Component.m_237115_("advancements.nether.create_full_beacon.title"), Component.m_237115_("advancements.nether.create_full_beacon.description"), null, FrameType.GOAL, true, true, false).m_138386_("beacon", ConstructBeaconTrigger.TriggerInstance.m_22765_(MinMaxBounds.Ints.m_55371_(4))).m_138389_(p_124020_, "nether/create_full_beacon");
        Advancement $$8 = Advancement.Builder.m_138353_().m_138398_($$6).m_138371_(Items.f_42589_, Component.m_237115_("advancements.nether.brew_potion.title"), Component.m_237115_("advancements.nether.brew_potion.description"), null, FrameType.TASK, true, true, false).m_138386_("potion", BrewedPotionTrigger.TriggerInstance.m_19145_()).m_138389_(p_124020_, "nether/brew_potion");
        Advancement $$9 = Advancement.Builder.m_138353_().m_138398_($$8).m_138371_(Items.f_42455_, Component.m_237115_("advancements.nether.all_potions.title"), Component.m_237115_("advancements.nether.all_potions.description"), null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(100)).m_138386_("all_effects", EffectsChangedTrigger.TriggerInstance.m_26780_(MobEffectsPredicate.m_56552_().m_56553_(MobEffects.f_19596_).m_56553_(MobEffects.f_19597_).m_56553_(MobEffects.f_19600_).m_56553_(MobEffects.f_19603_).m_56553_(MobEffects.f_19605_).m_56553_(MobEffects.f_19607_).m_56553_(MobEffects.f_19608_).m_56553_(MobEffects.f_19609_).m_56553_(MobEffects.f_19611_).m_56553_(MobEffects.f_19613_).m_56553_(MobEffects.f_19614_).m_56553_(MobEffects.f_19591_).m_56553_(MobEffects.f_19606_))).m_138389_(p_124020_, "nether/all_potions");
        Advancement.Builder.m_138353_().m_138398_($$9).m_138371_(Items.f_42446_, Component.m_237115_("advancements.nether.all_effects.title"), Component.m_237115_("advancements.nether.all_effects.description"), null, FrameType.CHALLENGE, true, true, true).m_138354_(AdvancementRewards.Builder.m_10005_(1000)).m_138386_("all_effects", EffectsChangedTrigger.TriggerInstance.m_26780_(MobEffectsPredicate.m_56552_().m_56553_(MobEffects.f_19596_).m_56553_(MobEffects.f_19597_).m_56553_(MobEffects.f_19600_).m_56553_(MobEffects.f_19603_).m_56553_(MobEffects.f_19605_).m_56553_(MobEffects.f_19607_).m_56553_(MobEffects.f_19608_).m_56553_(MobEffects.f_19609_).m_56553_(MobEffects.f_19611_).m_56553_(MobEffects.f_19613_).m_56553_(MobEffects.f_19614_).m_56553_(MobEffects.f_19615_).m_56553_(MobEffects.f_19598_).m_56553_(MobEffects.f_19599_).m_56553_(MobEffects.f_19620_).m_56553_(MobEffects.f_19619_).m_56553_(MobEffects.f_19617_).m_56553_(MobEffects.f_19612_).m_56553_(MobEffects.f_19604_).m_56553_(MobEffects.f_19606_).m_56553_(MobEffects.f_19591_).m_56553_(MobEffects.f_19592_).m_56553_(MobEffects.f_19593_).m_56553_(MobEffects.f_19610_).m_56553_(MobEffects.f_19594_).m_56553_(MobEffects.f_19595_).m_56553_(MobEffects.f_216964_))).m_138389_(p_124020_, "nether/all_effects");
        Advancement $$10 = Advancement.Builder.m_138353_().m_138398_($$1).m_138371_(Items.f_42792_, Component.m_237115_("advancements.nether.obtain_ancient_debris.title"), Component.m_237115_("advancements.nether.obtain_ancient_debris.description"), null, FrameType.TASK, true, true, false).m_138386_("ancient_debris", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42792_)).m_138389_(p_124020_, "nether/obtain_ancient_debris");
        Advancement.Builder.m_138353_().m_138398_($$10).m_138371_(Items.f_42481_, Component.m_237115_("advancements.nether.netherite_armor.title"), Component.m_237115_("advancements.nether.netherite_armor.description"), null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(100)).m_138386_("netherite_armor", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42480_, Items.f_42481_, Items.f_42482_, Items.f_42483_)).m_138389_(p_124020_, "nether/netherite_armor");
        Advancement.Builder.m_138353_().m_138398_($$10).m_138371_(Items.f_42790_, Component.m_237115_("advancements.nether.use_lodestone.title"), Component.m_237115_("advancements.nether.use_lodestone.description"), null, FrameType.TASK, true, true, false).m_138386_("use_lodestone", ItemInteractWithBlockTrigger.TriggerInstance.m_220065_(LocationPredicate.Builder.m_52651_().m_52652_(BlockPredicate.Builder.m_17924_().m_146726_(Blocks.f_50729_).m_17931_()), ItemPredicate.Builder.m_45068_().m_151445_(Items.f_42522_))).m_138389_(p_124020_, "nether/use_lodestone");
        Advancement $$11 = Advancement.Builder.m_138353_().m_138398_($$1).m_138371_(Items.f_42754_, Component.m_237115_("advancements.nether.obtain_crying_obsidian.title"), Component.m_237115_("advancements.nether.obtain_crying_obsidian.description"), null, FrameType.TASK, true, true, false).m_138386_("crying_obsidian", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42754_)).m_138389_(p_124020_, "nether/obtain_crying_obsidian");
        Advancement.Builder.m_138353_().m_138398_($$11).m_138371_(Items.f_42767_, Component.m_237115_("advancements.nether.charge_respawn_anchor.title"), Component.m_237115_("advancements.nether.charge_respawn_anchor.description"), null, FrameType.TASK, true, true, false).m_138386_("charge_respawn_anchor", ItemInteractWithBlockTrigger.TriggerInstance.m_220065_(LocationPredicate.Builder.m_52651_().m_52652_(BlockPredicate.Builder.m_17924_().m_146726_(Blocks.f_50724_).m_17929_(StatePropertiesPredicate.Builder.m_67693_().m_67694_(RespawnAnchorBlock.f_55833_, 4).m_67706_()).m_17931_()), ItemPredicate.Builder.m_45068_().m_151445_(Blocks.f_50141_))).m_138389_(p_124020_, "nether/charge_respawn_anchor");
        Advancement $$12 = Advancement.Builder.m_138353_().m_138398_($$1).m_138371_(Items.f_42685_, Component.m_237115_("advancements.nether.ride_strider.title"), Component.m_237115_("advancements.nether.ride_strider.description"), null, FrameType.TASK, true, true, false).m_138386_("used_warped_fungus_on_a_stick", ItemDurabilityTrigger.TriggerInstance.m_43694_(EntityPredicate.Composite.m_36673_(EntityPredicate.Builder.m_36633_().m_36644_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20482_).m_36662_()).m_36662_()), ItemPredicate.Builder.m_45068_().m_151445_(Items.f_42685_).m_45077_(), MinMaxBounds.Ints.f_55364_)).m_138389_(p_124020_, "nether/ride_strider");
        Advancement.Builder.m_138353_().m_138398_($$12).m_138371_(Items.f_42685_, Component.m_237115_("advancements.nether.ride_strider_in_overworld_lava.title"), Component.m_237115_("advancements.nether.ride_strider_in_overworld_lava.description"), null, FrameType.TASK, true, true, false).m_138386_("ride_entity_distance", DistanceTrigger.TriggerInstance.m_186194_(EntityPredicate.Builder.m_36633_().m_36650_(LocationPredicate.m_52638_(Level.f_46428_)).m_36644_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20482_).m_36662_()), DistancePredicate.m_148836_(MinMaxBounds.Doubles.m_154804_(50.0)))).m_138389_(p_124020_, "nether/ride_strider_in_overworld_lava");
        AdventureAdvancements.m_123986_(Advancement.Builder.m_138353_(), f_124015_).m_138398_($$12).m_138371_(Items.f_42483_, Component.m_237115_("advancements.nether.explore_nether.title"), Component.m_237115_("advancements.nether.explore_nether.description"), null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(500)).m_138389_(p_124020_, "nether/explore_nether");
        Advancement $$13 = Advancement.Builder.m_138353_().m_138398_($$1).m_138371_(Items.f_42763_, Component.m_237115_("advancements.nether.find_bastion.title"), Component.m_237115_("advancements.nether.find_bastion.description"), null, FrameType.TASK, true, true, false).m_138386_("bastion", PlayerTrigger.TriggerInstance.m_222635_(LocationPredicate.m_220589_(BuiltinStructures.f_209863_))).m_138389_(p_124020_, "nether/find_bastion");
        Advancement.Builder.m_138353_().m_138398_($$13).m_138371_(Blocks.f_50087_, Component.m_237115_("advancements.nether.loot_bastion.title"), Component.m_237115_("advancements.nether.loot_bastion.description"), null, FrameType.TASK, true, true, false).m_138360_(RequirementsStrategy.f_15979_).m_138386_("loot_bastion_other", LootTableTrigger.TriggerInstance.m_54618_(new ResourceLocation("minecraft:chests/bastion_other"))).m_138386_("loot_bastion_treasure", LootTableTrigger.TriggerInstance.m_54618_(new ResourceLocation("minecraft:chests/bastion_treasure"))).m_138386_("loot_bastion_hoglin_stable", LootTableTrigger.TriggerInstance.m_54618_(new ResourceLocation("minecraft:chests/bastion_hoglin_stable"))).m_138386_("loot_bastion_bridge", LootTableTrigger.TriggerInstance.m_54618_(new ResourceLocation("minecraft:chests/bastion_bridge"))).m_138389_(p_124020_, "nether/loot_bastion");
        Advancement.Builder.m_138353_().m_138398_($$1).m_138360_(RequirementsStrategy.f_15979_).m_138371_(Items.f_42417_, Component.m_237115_("advancements.nether.distract_piglin.title"), Component.m_237115_("advancements.nether.distract_piglin.description"), null, FrameType.TASK, true, true, false).m_138386_("distract_piglin", PickedUpItemTrigger.TriggerInstance.m_221326_(f_124016_, ItemPredicate.Builder.m_45068_().m_204145_(ItemTags.f_13151_).m_45077_(), EntityPredicate.Composite.m_36673_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20511_).m_36642_(EntityFlagsPredicate.Builder.m_33713_().m_33717_(false).m_33716_()).m_36662_()))).m_138386_("distract_piglin_directly", PlayerInteractTrigger.TriggerInstance.m_61517_(f_124016_, ItemPredicate.Builder.m_45068_().m_151445_(PiglinAi.f_34794_), EntityPredicate.Composite.m_36673_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20511_).m_36642_(EntityFlagsPredicate.Builder.m_33713_().m_33717_(false).m_33716_()).m_36662_()))).m_138389_(p_124020_, "nether/distract_piglin");
    }

    @Override
    public /* synthetic */ void accept(Object object) {
        this.accept((Consumer)object);
    }
}

