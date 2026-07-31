/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.advancements;

import java.util.List;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.ChanneledLightningTrigger;
import net.minecraft.advancements.critereon.DamagePredicate;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.DistanceTrigger;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemInteractWithBlockTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.KilledByCrossbowTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.LighthingBoltPredicate;
import net.minecraft.advancements.critereon.LightningStrikeTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.PlayerHurtEntityTrigger;
import net.minecraft.advancements.critereon.PlayerPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.advancements.critereon.ShotCrossbowTrigger;
import net.minecraft.advancements.critereon.SlideDownBlockTrigger;
import net.minecraft.advancements.critereon.SummonedEntityTrigger;
import net.minecraft.advancements.critereon.TargetBlockTrigger;
import net.minecraft.advancements.critereon.TradeTrigger;
import net.minecraft.advancements.critereon.UsedTotemTrigger;
import net.minecraft.advancements.critereon.UsingItemTrigger;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.block.Blocks;

public class AdventureAdvancements
implements Consumer<Consumer<Advancement>> {
    private static final int f_194669_ = 384;
    private static final int f_194670_ = 320;
    private static final int f_194671_ = -64;
    private static final int f_194672_ = 5;
    private static final EntityType<?>[] f_123979_ = new EntityType[]{EntityType.f_20551_, EntityType.f_20554_, EntityType.f_20558_, EntityType.f_20562_, EntityType.f_20563_, EntityType.f_20565_, EntityType.f_20566_, EntityType.f_20567_, EntityType.f_20568_, EntityType.f_20453_, EntityType.f_20455_, EntityType.f_20456_, EntityType.f_20458_, EntityType.f_20468_, EntityType.f_20509_, EntityType.f_20511_, EntityType.f_20512_, EntityType.f_20513_, EntityType.f_20518_, EntityType.f_20521_, EntityType.f_20523_, EntityType.f_20524_, EntityType.f_20526_, EntityType.f_20479_, EntityType.f_20481_, EntityType.f_20491_, EntityType.f_20493_, EntityType.f_20495_, EntityType.f_20497_, EntityType.f_20496_, EntityType.f_20500_, EntityType.f_20530_, EntityType.f_20501_, EntityType.f_20531_};

    private static LightningStrikeTrigger.TriggerInstance m_176029_(MinMaxBounds.Ints p_176030_, EntityPredicate p_176031_) {
        return LightningStrikeTrigger.TriggerInstance.m_153413_(EntityPredicate.Builder.m_36633_().m_36638_(DistancePredicate.m_148840_(MinMaxBounds.Doubles.m_154808_(30.0))).m_218800_(LighthingBoltPredicate.m_153250_(p_176030_)).m_36662_(), p_176031_);
    }

    private static UsingItemTrigger.TriggerInstance m_176026_(EntityType<?> p_176027_, Item p_176028_) {
        return UsingItemTrigger.TriggerInstance.m_163883_(EntityPredicate.Builder.m_36633_().m_218800_(PlayerPredicate.Builder.m_156767_().m_156771_(EntityPredicate.Builder.m_36633_().m_36636_(p_176027_).m_36662_()).m_62313_()), ItemPredicate.Builder.m_45068_().m_151445_(p_176028_));
    }

    @Override
    public void accept(Consumer<Advancement> p_123983_) {
        Advancement $$1 = Advancement.Builder.m_138353_().m_138371_(Items.f_42676_, Component.m_237115_("advancements.adventure.root.title"), Component.m_237115_("advancements.adventure.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png"), FrameType.TASK, false, false, false).m_138360_(RequirementsStrategy.f_15979_).m_138386_("killed_something", KilledTrigger.TriggerInstance.m_48141_()).m_138386_("killed_by_something", KilledTrigger.TriggerInstance.m_48142_()).m_138389_(p_123983_, "adventure/root");
        Advancement $$2 = Advancement.Builder.m_138353_().m_138398_($$1).m_138371_(Blocks.f_50028_, Component.m_237115_("advancements.adventure.sleep_in_bed.title"), Component.m_237115_("advancements.adventure.sleep_in_bed.description"), null, FrameType.TASK, true, true, false).m_138386_("slept_in_bed", PlayerTrigger.TriggerInstance.m_222640_()).m_138389_(p_123983_, "adventure/sleep_in_bed");
        AdventureAdvancements.m_123986_(Advancement.Builder.m_138353_(), MultiNoiseBiomeSource.Preset.f_187087_.m_220662_().toList()).m_138398_($$2).m_138371_(Items.f_42475_, Component.m_237115_("advancements.adventure.adventuring_time.title"), Component.m_237115_("advancements.adventure.adventuring_time.description"), null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(500)).m_138389_(p_123983_, "adventure/adventuring_time");
        Advancement $$3 = Advancement.Builder.m_138353_().m_138398_($$1).m_138371_(Items.f_42616_, Component.m_237115_("advancements.adventure.trade.title"), Component.m_237115_("advancements.adventure.trade.description"), null, FrameType.TASK, true, true, false).m_138386_("traded", TradeTrigger.TriggerInstance.m_70987_()).m_138389_(p_123983_, "adventure/trade");
        Advancement.Builder.m_138353_().m_138398_($$3).m_138371_(Items.f_42616_, Component.m_237115_("advancements.adventure.trade_at_world_height.title"), Component.m_237115_("advancements.adventure.trade_at_world_height.description"), null, FrameType.TASK, true, true, false).m_138386_("trade_at_world_height", TradeTrigger.TriggerInstance.m_191436_(EntityPredicate.Builder.m_36633_().m_36650_(LocationPredicate.m_187442_(MinMaxBounds.Doubles.m_154804_(319.0))))).m_138389_(p_123983_, "adventure/trade_at_world_height");
        Advancement $$4 = this.m_123984_(Advancement.Builder.m_138353_()).m_138398_($$1).m_138371_(Items.f_42383_, Component.m_237115_("advancements.adventure.kill_a_mob.title"), Component.m_237115_("advancements.adventure.kill_a_mob.description"), null, FrameType.TASK, true, true, false).m_138360_(RequirementsStrategy.f_15979_).m_138389_(p_123983_, "adventure/kill_a_mob");
        this.m_123984_(Advancement.Builder.m_138353_()).m_138398_($$4).m_138371_(Items.f_42388_, Component.m_237115_("advancements.adventure.kill_all_mobs.title"), Component.m_237115_("advancements.adventure.kill_all_mobs.description"), null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(100)).m_138389_(p_123983_, "adventure/kill_all_mobs");
        Advancement $$5 = Advancement.Builder.m_138353_().m_138398_($$4).m_138371_(Items.f_42411_, Component.m_237115_("advancements.adventure.shoot_arrow.title"), Component.m_237115_("advancements.adventure.shoot_arrow.description"), null, FrameType.TASK, true, true, false).m_138386_("shot_arrow", PlayerHurtEntityTrigger.TriggerInstance.m_60149_(DamagePredicate.Builder.m_24931_().m_24932_(DamageSourcePredicate.Builder.m_25471_().m_25474_(true).m_25472_(EntityPredicate.Builder.m_36633_().m_204077_(EntityTypeTags.f_13123_))))).m_138389_(p_123983_, "adventure/shoot_arrow");
        Advancement $$6 = Advancement.Builder.m_138353_().m_138398_($$4).m_138371_(Items.f_42713_, Component.m_237115_("advancements.adventure.throw_trident.title"), Component.m_237115_("advancements.adventure.throw_trident.description"), null, FrameType.TASK, true, true, false).m_138386_("shot_trident", PlayerHurtEntityTrigger.TriggerInstance.m_60149_(DamagePredicate.Builder.m_24931_().m_24932_(DamageSourcePredicate.Builder.m_25471_().m_25474_(true).m_25472_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20487_))))).m_138389_(p_123983_, "adventure/throw_trident");
        Advancement.Builder.m_138353_().m_138398_($$6).m_138371_(Items.f_42713_, Component.m_237115_("advancements.adventure.very_very_frightening.title"), Component.m_237115_("advancements.adventure.very_very_frightening.description"), null, FrameType.TASK, true, true, false).m_138386_("struck_villager", ChanneledLightningTrigger.TriggerInstance.m_21746_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20492_).m_36662_())).m_138389_(p_123983_, "adventure/very_very_frightening");
        Advancement.Builder.m_138353_().m_138398_($$3).m_138371_(Blocks.f_50143_, Component.m_237115_("advancements.adventure.summon_iron_golem.title"), Component.m_237115_("advancements.adventure.summon_iron_golem.description"), null, FrameType.GOAL, true, true, false).m_138386_("summoned_golem", SummonedEntityTrigger.TriggerInstance.m_68275_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20460_))).m_138389_(p_123983_, "adventure/summon_iron_golem");
        Advancement.Builder.m_138353_().m_138398_($$5).m_138371_(Items.f_42412_, Component.m_237115_("advancements.adventure.sniper_duel.title"), Component.m_237115_("advancements.adventure.sniper_duel.description"), null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(50)).m_138386_("killed_skeleton", KilledTrigger.TriggerInstance.m_48136_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20524_).m_36638_(DistancePredicate.m_148836_(MinMaxBounds.Doubles.m_154804_(50.0))), DamageSourcePredicate.Builder.m_25471_().m_25474_(true))).m_138389_(p_123983_, "adventure/sniper_duel");
        Advancement.Builder.m_138353_().m_138398_($$4).m_138371_(Items.f_42747_, Component.m_237115_("advancements.adventure.totem_of_undying.title"), Component.m_237115_("advancements.adventure.totem_of_undying.description"), null, FrameType.GOAL, true, true, false).m_138386_("used_totem", UsedTotemTrigger.TriggerInstance.m_74452_(Items.f_42747_)).m_138389_(p_123983_, "adventure/totem_of_undying");
        Advancement $$7 = Advancement.Builder.m_138353_().m_138398_($$1).m_138371_(Items.f_42717_, Component.m_237115_("advancements.adventure.ol_betsy.title"), Component.m_237115_("advancements.adventure.ol_betsy.description"), null, FrameType.TASK, true, true, false).m_138386_("shot_crossbow", ShotCrossbowTrigger.TriggerInstance.m_65483_(Items.f_42717_)).m_138389_(p_123983_, "adventure/ol_betsy");
        Advancement.Builder.m_138353_().m_138398_($$7).m_138371_(Items.f_42717_, Component.m_237115_("advancements.adventure.whos_the_pillager_now.title"), Component.m_237115_("advancements.adventure.whos_the_pillager_now.description"), null, FrameType.TASK, true, true, false).m_138386_("kill_pillager", KilledByCrossbowTrigger.TriggerInstance.m_46900_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20513_))).m_138389_(p_123983_, "adventure/whos_the_pillager_now");
        Advancement.Builder.m_138353_().m_138398_($$7).m_138371_(Items.f_42717_, Component.m_237115_("advancements.adventure.two_birds_one_arrow.title"), Component.m_237115_("advancements.adventure.two_birds_one_arrow.description"), null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(65)).m_138386_("two_birds", KilledByCrossbowTrigger.TriggerInstance.m_46900_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20509_), EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20509_))).m_138389_(p_123983_, "adventure/two_birds_one_arrow");
        Advancement.Builder.m_138353_().m_138398_($$7).m_138371_(Items.f_42717_, Component.m_237115_("advancements.adventure.arbalistic.title"), Component.m_237115_("advancements.adventure.arbalistic.description"), null, FrameType.CHALLENGE, true, true, true).m_138354_(AdvancementRewards.Builder.m_10005_(85)).m_138386_("arbalistic", KilledByCrossbowTrigger.TriggerInstance.m_46893_(MinMaxBounds.Ints.m_55371_(5))).m_138389_(p_123983_, "adventure/arbalistic");
        Advancement $$8 = Advancement.Builder.m_138353_().m_138398_($$1).m_138362_(Raid.m_37779_(), Component.m_237115_("advancements.adventure.voluntary_exile.title"), Component.m_237115_("advancements.adventure.voluntary_exile.description"), null, FrameType.TASK, true, true, true).m_138386_("voluntary_exile", KilledTrigger.TriggerInstance.m_48134_(EntityPredicate.Builder.m_36633_().m_204077_(EntityTypeTags.f_13121_).m_36640_(EntityEquipmentPredicate.f_32177_))).m_138389_(p_123983_, "adventure/voluntary_exile");
        Advancement.Builder.m_138353_().m_138398_($$8).m_138362_(Raid.m_37779_(), Component.m_237115_("advancements.adventure.hero_of_the_village.title"), Component.m_237115_("advancements.adventure.hero_of_the_village.description"), null, FrameType.CHALLENGE, true, true, true).m_138354_(AdvancementRewards.Builder.m_10005_(100)).m_138386_("hero_of_the_village", PlayerTrigger.TriggerInstance.m_222641_()).m_138389_(p_123983_, "adventure/hero_of_the_village");
        Advancement.Builder.m_138353_().m_138398_($$1).m_138371_(Blocks.f_50719_.m_5456_(), Component.m_237115_("advancements.adventure.honey_block_slide.title"), Component.m_237115_("advancements.adventure.honey_block_slide.description"), null, FrameType.TASK, true, true, false).m_138386_("honey_block_slide", SlideDownBlockTrigger.TriggerInstance.m_67006_(Blocks.f_50719_)).m_138389_(p_123983_, "adventure/honey_block_slide");
        Advancement.Builder.m_138353_().m_138398_($$5).m_138371_(Blocks.f_50716_.m_5456_(), Component.m_237115_("advancements.adventure.bullseye.title"), Component.m_237115_("advancements.adventure.bullseye.description"), null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(50)).m_138386_("bullseye", TargetBlockTrigger.TriggerInstance.m_70236_(MinMaxBounds.Ints.m_55371_(15), EntityPredicate.Composite.m_36673_(EntityPredicate.Builder.m_36633_().m_36638_(DistancePredicate.m_148836_(MinMaxBounds.Doubles.m_154804_(30.0))).m_36662_()))).m_138389_(p_123983_, "adventure/bullseye");
        Advancement.Builder.m_138353_().m_138398_($$2).m_138371_(Items.f_42463_, Component.m_237115_("advancements.adventure.walk_on_powder_snow_with_leather_boots.title"), Component.m_237115_("advancements.adventure.walk_on_powder_snow_with_leather_boots.description"), null, FrameType.TASK, true, true, false).m_138386_("walk_on_powder_snow_with_leather_boots", PlayerTrigger.TriggerInstance.m_222637_(Blocks.f_152499_, Items.f_42463_)).m_138389_(p_123983_, "adventure/walk_on_powder_snow_with_leather_boots");
        Advancement.Builder.m_138353_().m_138398_($$1).m_138371_(Items.f_151041_, Component.m_237115_("advancements.adventure.lightning_rod_with_villager_no_fire.title"), Component.m_237115_("advancements.adventure.lightning_rod_with_villager_no_fire.description"), null, FrameType.TASK, true, true, false).m_138386_("lightning_rod_with_villager_no_fire", AdventureAdvancements.m_176029_(MinMaxBounds.Ints.m_55371_(0), EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20492_).m_36662_())).m_138389_(p_123983_, "adventure/lightning_rod_with_villager_no_fire");
        Advancement $$9 = Advancement.Builder.m_138353_().m_138398_($$1).m_138371_(Items.f_151059_, Component.m_237115_("advancements.adventure.spyglass_at_parrot.title"), Component.m_237115_("advancements.adventure.spyglass_at_parrot.description"), null, FrameType.TASK, true, true, false).m_138386_("spyglass_at_parrot", AdventureAdvancements.m_176026_(EntityType.f_20508_, Items.f_151059_)).m_138389_(p_123983_, "adventure/spyglass_at_parrot");
        Advancement $$10 = Advancement.Builder.m_138353_().m_138398_($$9).m_138371_(Items.f_151059_, Component.m_237115_("advancements.adventure.spyglass_at_ghast.title"), Component.m_237115_("advancements.adventure.spyglass_at_ghast.description"), null, FrameType.TASK, true, true, false).m_138386_("spyglass_at_ghast", AdventureAdvancements.m_176026_(EntityType.f_20453_, Items.f_151059_)).m_138389_(p_123983_, "adventure/spyglass_at_ghast");
        Advancement.Builder.m_138353_().m_138398_($$2).m_138371_(Items.f_41984_, Component.m_237115_("advancements.adventure.play_jukebox_in_meadows.title"), Component.m_237115_("advancements.adventure.play_jukebox_in_meadows.description"), null, FrameType.TASK, true, true, false).m_138386_("play_jukebox_in_meadows", ItemInteractWithBlockTrigger.TriggerInstance.m_220065_(LocationPredicate.Builder.m_52651_().m_52656_(Biomes.f_186754_).m_52652_(BlockPredicate.Builder.m_17924_().m_146726_(Blocks.f_50131_).m_17931_()), ItemPredicate.Builder.m_45068_().m_204145_(ItemTags.f_13158_))).m_138389_(p_123983_, "adventure/play_jukebox_in_meadows");
        Advancement.Builder.m_138353_().m_138398_($$10).m_138371_(Items.f_151059_, Component.m_237115_("advancements.adventure.spyglass_at_dragon.title"), Component.m_237115_("advancements.adventure.spyglass_at_dragon.description"), null, FrameType.TASK, true, true, false).m_138386_("spyglass_at_dragon", AdventureAdvancements.m_176026_(EntityType.f_20565_, Items.f_151059_)).m_138389_(p_123983_, "adventure/spyglass_at_dragon");
        Advancement.Builder.m_138353_().m_138398_($$1).m_138371_(Items.f_42447_, Component.m_237115_("advancements.adventure.fall_from_world_height.title"), Component.m_237115_("advancements.adventure.fall_from_world_height.description"), null, FrameType.TASK, true, true, false).m_138386_("fall_from_world_height", DistanceTrigger.TriggerInstance.m_186197_(EntityPredicate.Builder.m_36633_().m_36650_(LocationPredicate.m_187442_(MinMaxBounds.Doubles.m_154808_(-59.0))), DistancePredicate.m_148838_(MinMaxBounds.Doubles.m_154804_(379.0)), LocationPredicate.m_187442_(MinMaxBounds.Doubles.m_154804_(319.0)))).m_138389_(p_123983_, "adventure/fall_from_world_height");
        Advancement.Builder.m_138353_().m_138398_($$4).m_138371_(Blocks.f_220857_, Component.m_237115_("advancements.adventure.kill_mob_near_sculk_catalyst.title"), Component.m_237115_("advancements.adventure.kill_mob_near_sculk_catalyst.description"), null, FrameType.CHALLENGE, true, true, false).m_138386_("kill_mob_near_sculk_catalyst", KilledTrigger.TriggerInstance.m_220237_()).m_138389_(p_123983_, "adventure/kill_mob_near_sculk_catalyst");
        Advancement.Builder.m_138353_().m_138398_($$1).m_138371_(Blocks.f_152500_, Component.m_237115_("advancements.adventure.avoid_vibration.title"), Component.m_237115_("advancements.adventure.avoid_vibration.description"), null, FrameType.TASK, true, true, false).m_138386_("avoid_vibration", PlayerTrigger.TriggerInstance.m_222642_()).m_138389_(p_123983_, "adventure/avoid_vibration");
    }

    private Advancement.Builder m_123984_(Advancement.Builder p_123985_) {
        for (EntityType<?> $$1 : f_123979_) {
            p_123985_.m_138386_(Registry.f_122826_.m_7981_($$1).toString(), KilledTrigger.TriggerInstance.m_48134_(EntityPredicate.Builder.m_36633_().m_36636_($$1)));
        }
        return p_123985_;
    }

    protected static Advancement.Builder m_123986_(Advancement.Builder p_123987_, List<ResourceKey<Biome>> p_123988_) {
        for (ResourceKey<Biome> $$2 : p_123988_) {
            p_123987_.m_138386_($$2.m_135782_().toString(), PlayerTrigger.TriggerInstance.m_222635_(LocationPredicate.m_52634_($$2)));
        }
        return p_123987_;
    }

    @Override
    public /* synthetic */ void accept(Object object) {
        this.accept((Consumer)object);
    }
}

