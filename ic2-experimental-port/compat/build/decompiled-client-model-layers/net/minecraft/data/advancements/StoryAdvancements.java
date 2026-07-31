/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.advancements;

import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.CuredZombieVillagerTrigger;
import net.minecraft.advancements.critereon.DamagePredicate;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.EnchantedItemTrigger;
import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;

public class StoryAdvancements
implements Consumer<Consumer<Advancement>> {
    @Override
    public void accept(Consumer<Advancement> p_124025_) {
        Advancement $$1 = Advancement.Builder.m_138353_().m_138371_(Blocks.f_50440_, Component.m_237115_("advancements.story.root.title"), Component.m_237115_("advancements.story.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), FrameType.TASK, false, false, false).m_138386_("crafting_table", InventoryChangeTrigger.TriggerInstance.m_43199_(Blocks.f_50091_)).m_138389_(p_124025_, "story/root");
        Advancement $$2 = Advancement.Builder.m_138353_().m_138398_($$1).m_138371_(Items.f_42422_, Component.m_237115_("advancements.story.mine_stone.title"), Component.m_237115_("advancements.story.mine_stone.description"), null, FrameType.TASK, true, true, false).m_138386_("get_stone", InventoryChangeTrigger.TriggerInstance.m_43197_(ItemPredicate.Builder.m_45068_().m_204145_(ItemTags.f_13165_).m_45077_())).m_138389_(p_124025_, "story/mine_stone");
        Advancement $$3 = Advancement.Builder.m_138353_().m_138398_($$2).m_138371_(Items.f_42427_, Component.m_237115_("advancements.story.upgrade_tools.title"), Component.m_237115_("advancements.story.upgrade_tools.description"), null, FrameType.TASK, true, true, false).m_138386_("stone_pickaxe", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42427_)).m_138389_(p_124025_, "story/upgrade_tools");
        Advancement $$4 = Advancement.Builder.m_138353_().m_138398_($$3).m_138371_(Items.f_42416_, Component.m_237115_("advancements.story.smelt_iron.title"), Component.m_237115_("advancements.story.smelt_iron.description"), null, FrameType.TASK, true, true, false).m_138386_("iron", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42416_)).m_138389_(p_124025_, "story/smelt_iron");
        Advancement $$5 = Advancement.Builder.m_138353_().m_138398_($$4).m_138371_(Items.f_42385_, Component.m_237115_("advancements.story.iron_tools.title"), Component.m_237115_("advancements.story.iron_tools.description"), null, FrameType.TASK, true, true, false).m_138386_("iron_pickaxe", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42385_)).m_138389_(p_124025_, "story/iron_tools");
        Advancement $$6 = Advancement.Builder.m_138353_().m_138398_($$5).m_138371_(Items.f_42415_, Component.m_237115_("advancements.story.mine_diamond.title"), Component.m_237115_("advancements.story.mine_diamond.description"), null, FrameType.TASK, true, true, false).m_138386_("diamond", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42415_)).m_138389_(p_124025_, "story/mine_diamond");
        Advancement $$7 = Advancement.Builder.m_138353_().m_138398_($$4).m_138371_(Items.f_42448_, Component.m_237115_("advancements.story.lava_bucket.title"), Component.m_237115_("advancements.story.lava_bucket.description"), null, FrameType.TASK, true, true, false).m_138386_("lava_bucket", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42448_)).m_138389_(p_124025_, "story/lava_bucket");
        Advancement $$8 = Advancement.Builder.m_138353_().m_138398_($$4).m_138371_(Items.f_42469_, Component.m_237115_("advancements.story.obtain_armor.title"), Component.m_237115_("advancements.story.obtain_armor.description"), null, FrameType.TASK, true, true, false).m_138360_(RequirementsStrategy.f_15979_).m_138386_("iron_helmet", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42468_)).m_138386_("iron_chestplate", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42469_)).m_138386_("iron_leggings", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42470_)).m_138386_("iron_boots", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42471_)).m_138389_(p_124025_, "story/obtain_armor");
        Advancement.Builder.m_138353_().m_138398_($$6).m_138371_(Items.f_42690_, Component.m_237115_("advancements.story.enchant_item.title"), Component.m_237115_("advancements.story.enchant_item.description"), null, FrameType.TASK, true, true, false).m_138386_("enchanted_item", EnchantedItemTrigger.TriggerInstance.m_27696_()).m_138389_(p_124025_, "story/enchant_item");
        Advancement $$9 = Advancement.Builder.m_138353_().m_138398_($$7).m_138371_(Blocks.f_50080_, Component.m_237115_("advancements.story.form_obsidian.title"), Component.m_237115_("advancements.story.form_obsidian.description"), null, FrameType.TASK, true, true, false).m_138386_("obsidian", InventoryChangeTrigger.TriggerInstance.m_43199_(Blocks.f_50080_)).m_138389_(p_124025_, "story/form_obsidian");
        Advancement.Builder.m_138353_().m_138398_($$8).m_138371_(Items.f_42740_, Component.m_237115_("advancements.story.deflect_arrow.title"), Component.m_237115_("advancements.story.deflect_arrow.description"), null, FrameType.TASK, true, true, false).m_138386_("deflected_projectile", EntityHurtPlayerTrigger.TriggerInstance.m_35206_(DamagePredicate.Builder.m_24931_().m_24932_(DamageSourcePredicate.Builder.m_25471_().m_25474_(true)).m_24934_(true))).m_138389_(p_124025_, "story/deflect_arrow");
        Advancement.Builder.m_138353_().m_138398_($$6).m_138371_(Items.f_42473_, Component.m_237115_("advancements.story.shiny_gear.title"), Component.m_237115_("advancements.story.shiny_gear.description"), null, FrameType.TASK, true, true, false).m_138360_(RequirementsStrategy.f_15979_).m_138386_("diamond_helmet", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42472_)).m_138386_("diamond_chestplate", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42473_)).m_138386_("diamond_leggings", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42474_)).m_138386_("diamond_boots", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42475_)).m_138389_(p_124025_, "story/shiny_gear");
        Advancement $$10 = Advancement.Builder.m_138353_().m_138398_($$9).m_138371_(Items.f_42409_, Component.m_237115_("advancements.story.enter_the_nether.title"), Component.m_237115_("advancements.story.enter_the_nether.description"), null, FrameType.TASK, true, true, false).m_138386_("entered_nether", ChangeDimensionTrigger.TriggerInstance.m_19782_(Level.f_46429_)).m_138389_(p_124025_, "story/enter_the_nether");
        Advancement.Builder.m_138353_().m_138398_($$10).m_138371_(Items.f_42436_, Component.m_237115_("advancements.story.cure_zombie_villager.title"), Component.m_237115_("advancements.story.cure_zombie_villager.description"), null, FrameType.GOAL, true, true, false).m_138386_("cured_zombie", CuredZombieVillagerTrigger.TriggerInstance.m_24302_()).m_138389_(p_124025_, "story/cure_zombie_villager");
        Advancement $$11 = Advancement.Builder.m_138353_().m_138398_($$10).m_138371_(Items.f_42545_, Component.m_237115_("advancements.story.follow_ender_eye.title"), Component.m_237115_("advancements.story.follow_ender_eye.description"), null, FrameType.TASK, true, true, false).m_138386_("in_stronghold", PlayerTrigger.TriggerInstance.m_222635_(LocationPredicate.m_220589_(BuiltinStructures.f_209855_))).m_138389_(p_124025_, "story/follow_ender_eye");
        Advancement.Builder.m_138353_().m_138398_($$11).m_138371_(Blocks.f_50259_, Component.m_237115_("advancements.story.enter_the_end.title"), Component.m_237115_("advancements.story.enter_the_end.description"), null, FrameType.TASK, true, true, false).m_138386_("entered_end", ChangeDimensionTrigger.TriggerInstance.m_19782_(Level.f_46430_)).m_138389_(p_124025_, "story/enter_the_end");
    }

    @Override
    public /* synthetic */ void accept(Object object) {
        this.accept((Consumer)object);
    }
}

