/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.advancements.Advancement$Builder
 *  net.minecraft.advancements.AdvancementRewards$Builder
 *  net.minecraft.advancements.CriteriaTriggers
 *  net.minecraft.advancements.CriterionTrigger
 *  net.minecraft.advancements.CriterionTriggerInstance
 *  net.minecraft.advancements.DisplayInfo
 *  net.minecraft.advancements.FrameType
 *  net.minecraft.advancements.critereon.InventoryChangeTrigger$TriggerInstance
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.registries;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.core.platform.advancements.IdTrigger;
import ic2.core.platform.advancements.RecipeItemTrigger;
import ic2.core.platform.recipes.misc.AdvRecipeRegistry;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.SimpleRegistry;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class IC2Advancements {
    static final int SHOW_TOAST = 1;
    static final int ANNOUNCE = 2;
    static final int HIDDEN = 4;
    static final int DEFAULT_TOAST = 3;
    public static final SimpleRegistry<Advancement.Builder> ADVANCEMENTS = CollectionUtils.createRegistry();
    public static final IdTrigger ID_TRIGGER = (IdTrigger)CriteriaTriggers.m_10595_((CriterionTrigger)new IdTrigger());
    public static final RecipeItemTrigger RECIPE_ITEM_TRIGGER = (RecipeItemTrigger)CriteriaTriggers.m_10595_((CriterionTrigger)new RecipeItemTrigger());
    static final List<Consumer<SimpleRegistry<Advancement.Builder>>> LISTENERS = ObjectLists.synchronize(CollectionUtils.createList());

    public static void registerIC2Advancement(String name, Advancement.Builder builder) {
        IC2Advancements.registerAdvancement(new ResourceLocation("ic2", name), builder);
    }

    public static void registerAdvancement(String owner, String name, Advancement.Builder builder) {
        IC2Advancements.registerAdvancement(new ResourceLocation(owner, name), builder);
    }

    public static void registerAdvancement(ResourceLocation location, Advancement.Builder builder) {
        ADVANCEMENTS.register(location, builder);
    }

    private static void loadInternal() {
        IC2Advancements.registerIC2Advancement("root", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Items.MINING_LASER, 0, "root", new ResourceLocation("ic2", "textures/gui_sprites/misc/advancement.png"))).m_138386_("hasCrafting", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{Items.f_41960_})));
        IC2Advancements.registerIC2Advancement("acquire_resin", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Items.STICKY_RESIN, 3, "acquire_resin")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Items.STICKY_RESIN})).m_138396_(new ResourceLocation("ic2:root")));
        IC2Advancements.registerIC2Advancement("mine_ore", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.URANIUM_ORE, 3, "mine_ore")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Items.ORE_URANIUM_DROP, Items.f_151051_, IC2Items.RAW_TIN, IC2Items.RAW_SILVER})).m_138396_(new ResourceLocation("ic2:root")));
        IC2Advancements.registerIC2Advancement("acquire_refined_iron", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Items.INGOT_REFINED_IRON, 3, "acquire_refined_iron")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Items.INGOT_REFINED_IRON})).m_138396_(new ResourceLocation("ic2:root")));
        IC2Advancements.registerIC2Advancement("build_cable", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Items.COPPER_CABLE_1X_INSULATED, 3, "build_cable")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Items.COPPER_CABLE_1X_INSULATED})).m_138396_(new ResourceLocation("ic2:acquire_refined_iron")));
        IC2Advancements.registerIC2Advancement("build_reader", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Items.EU_READER, 3, "build_reader")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Items.EU_READER})).m_138396_(new ResourceLocation("ic2:build_cable")));
        IC2Advancements.registerIC2Advancement("build_generator", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.GENERATOR, 3, "build_generator")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.GENERATOR})).m_138396_(new ResourceLocation("ic2:build_cable")));
        IC2Advancements.registerIC2Advancement("build_macerator", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.MACERATOR, 3, "build_macerator")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.MACERATOR})).m_138396_(new ResourceLocation("ic2:build_generator")));
        IC2Advancements.registerIC2Advancement("build_coal_diamond", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Items.INDUSTRIAL_DIAMOND, 3, "build_coal_diamond")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Items.INDUSTRIAL_DIAMOND})).m_138396_(new ResourceLocation("ic2:build_macerator")));
        IC2Advancements.registerIC2Advancement("build_rotary_macerator", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.ROTARY_MACERATOR, 3, "build_rotary_macerator")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.ROTARY_MACERATOR})).m_138396_(new ResourceLocation("ic2:build_macerator")));
        IC2Advancements.registerIC2Advancement("build_canner", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.CANNER, 3, "build_canner")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.CANNER})).m_138396_(new ResourceLocation("ic2:build_generator")));
        IC2Advancements.registerIC2Advancement("build_vacuum_canner", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.VACUUM_CANNER, 3, "build_vacuum_canner")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.VACUUM_CANNER})).m_138396_(new ResourceLocation("ic2:build_canner")));
        IC2Advancements.registerIC2Advancement("build_electric_furnace", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.ELECTRIC_FURNACE, 3, "build_electric_furnace")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.ELECTRIC_FURNACE})).m_138396_(new ResourceLocation("ic2:build_generator")));
        IC2Advancements.registerIC2Advancement("build_induction_furnace", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.INDUCTION_FURNACE, 3, "build_induction_furnace")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.INDUCTION_FURNACE})).m_138396_(new ResourceLocation("ic2:build_electric_furnace")));
        IC2Advancements.registerIC2Advancement("build_compressor", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.COMPRESSOR, 3, "build_compressor")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.COMPRESSOR})).m_138396_(new ResourceLocation("ic2:build_generator")));
        IC2Advancements.registerIC2Advancement("build_singularity_compressor", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.SINGULARITY_COMPRESSOR, 3, "build_singularity_compressor")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.SINGULARITY_COMPRESSOR})).m_138396_(new ResourceLocation("ic2:build_compressor")));
        IC2Advancements.registerIC2Advancement("compress_uranium", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Items.INGOT_URANIUM, 3, "compress_uranium")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Items.INGOT_URANIUM})).m_138396_(new ResourceLocation("ic2:build_compressor")));
        IC2Advancements.registerIC2Advancement("die_from_own_nuke", IC2Advancements.builder().m_138358_(IC2Advancements.challenge((ItemLike)IC2Blocks.NUKE, 3, "die_from_own_nuke")).m_138386_("death_trigger", (CriterionTriggerInstance)new IdTrigger.Instance(new ResourceLocation("ic2:nuke_death"))).m_138396_(new ResourceLocation("ic2:compress_uranium")));
        IC2Advancements.registerIC2Advancement("build_recycler", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.RECYCLER, 3, "build_recycler")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.RECYCLER})).m_138396_(new ResourceLocation("ic2:build_compressor")));
        IC2Advancements.registerIC2Advancement("build_compacting_recycler", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.COMPACTING_RECYCLER, 3, "build_compacting_recycler")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.COMPACTING_RECYCLER})).m_138396_(new ResourceLocation("ic2:build_recycler")));
        IC2Advancements.registerIC2Advancement("build_extractor", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.EXTRACTOR, 3, "build_extractor")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.EXTRACTOR})).m_138396_(new ResourceLocation("ic2:build_generator")));
        IC2Advancements.registerIC2Advancement("build_centrifugal_extractor", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.CENTRIFUGAL_EXTRACTOR, 3, "build_centrifugal_extractor")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.CENTRIFUGAL_EXTRACTOR})).m_138396_(new ResourceLocation("ic2:build_extractor")));
        IC2Advancements.registerIC2Advancement("build_batbox", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.BATBOX, 3, "build_batbox")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.BATBOX})).m_138396_(new ResourceLocation("ic2:build_generator")));
        IC2Advancements.registerIC2Advancement("build_mfe", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.MFE, 3, "build_mfe")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.MFE})).m_138396_(new ResourceLocation("ic2:build_batbox")));
        IC2Advancements.registerIC2Advancement("build_mfsu", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.MFSU, 3, "build_mfsu")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.MFSU})).m_138396_(new ResourceLocation("ic2:build_mfe")));
        IC2Advancements.registerIC2Advancement("build_pesu", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.PESU, 3, "build_pesu")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.PESU})).m_138396_(new ResourceLocation("ic2:build_mfsu")));
        IC2Advancements.registerIC2Advancement("build_drill", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Items.DRILL, 3, "build_drill")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Items.DRILL})).m_138396_(new ResourceLocation("ic2:build_batbox")));
        IC2Advancements.registerIC2Advancement("build_chainsaw", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Items.CHAINSAW, 3, "build_chainsaw")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Items.CHAINSAW})).m_138396_(new ResourceLocation("ic2:build_batbox")));
        IC2Advancements.registerIC2Advancement("build_massfab", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.MASS_FABRICATOR, 3, "build_massfab")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.MASS_FABRICATOR})).m_138396_(new ResourceLocation("ic2:build_batbox")));
        IC2Advancements.registerIC2Advancement("build_diamond_drill", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Items.DRILL_DIAMOND, 3, "build_diamond_drill")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Items.DRILL_DIAMOND})).m_138396_(new ResourceLocation("ic2:build_drill")));
        IC2Advancements.registerIC2Advancement("kill_creeper_chainsaw", IC2Advancements.builder().m_138358_(IC2Advancements.challenge((ItemLike)IC2Items.CHAINSAW, 3, "kill_creeper_chainsaw")).m_138386_("kill_creeper", (CriterionTriggerInstance)new IdTrigger.Instance(new ResourceLocation("ic2:creeper_kill"))).m_138396_(new ResourceLocation("ic2:build_chainsaw")));
        IC2Advancements.registerIC2Advancement("acquire_matter", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Items.UUMATTER, 3, "acquire_matter")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Items.UUMATTER})).m_138396_(new ResourceLocation("ic2:build_massfab")));
        IC2Advancements.registerIC2Advancement("build_quantum_armor", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Items.QUANTUM_SUIT_CHESTPLATE, 3, "build_quantum_armor")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Items.QUANTUM_SUIT_CHESTPLATE})).m_138396_(new ResourceLocation("ic2:acquire_matter")));
        IC2Advancements.registerIC2Advancement("starve_with_quantum_helmet", IC2Advancements.builder().m_138358_(IC2Advancements.challenge((ItemLike)IC2Items.QUANTUM_SUIT_HELMET, 3, "starve_with_quantum_helmet")).m_138386_("starve", (CriterionTriggerInstance)new IdTrigger.Instance(new ResourceLocation("ic2:starve"))).m_138396_(new ResourceLocation("ic2:build_quantum_armor")));
        IC2Advancements.registerIC2Advancement("build_teleporter", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.TELEPORTER, 3, "build_teleporter")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.TELEPORTER})).m_138396_(new ResourceLocation("ic2:build_mfsu")));
        IC2Advancements.registerIC2Advancement("teleport_far_away", IC2Advancements.builder().m_138358_(IC2Advancements.challenge((ItemLike)IC2Blocks.TELEPORTER, 3, "teleport_far_away")).m_138386_("to_far", (CriterionTriggerInstance)new IdTrigger.Instance(new ResourceLocation("ic2:teleport_far"))).m_138396_(new ResourceLocation("ic2:build_teleporter")));
        IC2Advancements.registerIC2Advancement("build_mining_laser", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Items.MINING_LASER, 3, "build_mining_laser")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Items.MINING_LASER})).m_138396_(new ResourceLocation("ic2:build_mfe")));
        IC2Advancements.registerIC2Advancement("kill_dragon_mining_laser", IC2Advancements.builder().m_138358_(IC2Advancements.challenge((ItemLike)Items.f_42683_, 3, "kill_dragon_mining_laser")).m_138386_("target", (CriterionTriggerInstance)new IdTrigger.Instance(IC2Stats.DRAGONS_SHOT)).m_138396_(new ResourceLocation("ic2:build_mining_laser")));
        IC2Advancements.registerIC2Advancement("build_terraformer", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.TERRAFORMER, 3, "build_terraformer")).m_138386_("hasItem", (CriterionTriggerInstance)InventoryChangeTrigger.TriggerInstance.m_43199_((ItemLike[])new ItemLike[]{IC2Blocks.TERRAFORMER})).m_138396_(new ResourceLocation("ic2:build_mfsu")));
        IC2Advancements.registerIC2Advancement("terrform_end_cultivation", IC2Advancements.builder().m_138358_(IC2Advancements.challenge((ItemLike)IC2Items.TFBP_CULTIVATION, 3, "terrform_end_cultivation")).m_138386_("task", (CriterionTriggerInstance)new IdTrigger.Instance(new ResourceLocation("ic2:cultivate_end"))).m_138396_(new ResourceLocation("ic2:build_terraformer")));
        IC2Advancements.registerIC2Advancement("jetpack_kill", IC2Advancements.builder().m_138358_(IC2Advancements.challenge((ItemLike)IC2Items.JETPACK_ELECTRIC, 7, "jetpack_kill")).m_138386_("task", (CriterionTriggerInstance)new IdTrigger.Instance(new ResourceLocation("ic2:jetpack_kill"))).m_138396_(new ResourceLocation("ic2:root")));
        IC2Advancements.registerIC2Advancement("kill_wither_with_nuke", IC2Advancements.builder().m_138358_(IC2Advancements.challenge((ItemLike)Blocks.f_50312_, 3, "kill_wither_with_nuke")).m_138386_("death_trigger", (CriterionTriggerInstance)new IdTrigger.Instance(new ResourceLocation("ic2:nuke_wither"))).m_138396_(new ResourceLocation("ic2:compress_uranium")));
        IC2Advancements.registerIC2Advancement("scaffolded", IC2Advancements.builder().m_138358_(IC2Advancements.task((ItemLike)IC2Blocks.SCAFFOLD_WOOD, 7, "scaffolded")).m_138386_("entry", (CriterionTriggerInstance)new IdTrigger.Instance(new ResourceLocation("ic2:scaffold"))).m_138396_(new ResourceLocation("ic2:root")));
    }

    static Advancement.Builder builder() {
        return Advancement.Builder.m_138353_().m_138356_(new AdvancementRewards.Builder().m_10004_());
    }

    static DisplayInfo task(ItemLike provider, int flags, String name) {
        return new DisplayInfo(new ItemStack(provider), (Component)Component.m_237115_((String)("advancement.ic2." + name)), (Component)Component.m_237115_((String)("advancement.ic2." + name + ".desc")), null, FrameType.TASK, (flags & 1) != 0, (flags & 2) != 0, (flags & 4) != 0);
    }

    static DisplayInfo task(ItemLike provider, int flags, String name, ResourceLocation background) {
        return new DisplayInfo(new ItemStack(provider), (Component)Component.m_237115_((String)("advancement.ic2." + name)), (Component)Component.m_237115_((String)("advancement.ic2." + name + ".desc")), background, FrameType.TASK, (flags & 1) != 0, (flags & 2) != 0, (flags & 4) != 0);
    }

    static DisplayInfo task(ItemStack stack, int flags, String name) {
        return new DisplayInfo(stack, (Component)Component.m_237115_((String)("advancement.ic2." + name)), (Component)Component.m_237115_((String)("advancement.ic2." + name + ".desc")), null, FrameType.TASK, (flags & 1) != 0, (flags & 2) != 0, (flags & 4) != 0);
    }

    static DisplayInfo task(ItemStack stack, int flags, String name, Component desc) {
        return new DisplayInfo(stack, (Component)Component.m_237115_((String)("advancement.ic2." + name)), desc, null, FrameType.TASK, (flags & 1) != 0, (flags & 2) != 0, (flags & 4) != 0);
    }

    static DisplayInfo challenge(ItemLike provider, int flags, String name) {
        return new DisplayInfo(new ItemStack(provider), (Component)Component.m_237115_((String)("advancement.ic2." + name)), (Component)Component.m_237115_((String)("advancement.ic2." + name + ".desc")), null, FrameType.CHALLENGE, (flags & 1) != 0, (flags & 2) != 0, (flags & 4) != 0);
    }

    static DisplayInfo challenge(ItemStack stack, int flags, String name) {
        return new DisplayInfo(stack, (Component)Component.m_237115_((String)("advancement.ic2." + name)), (Component)Component.m_237115_((String)("advancement.ic2." + name + ".desc")), null, FrameType.CHALLENGE, (flags & 1) != 0, (flags & 2) != 0, (flags & 4) != 0);
    }

    static DisplayInfo challenge(ItemStack stack, int flags, String name, Component desc) {
        return new DisplayInfo(stack, (Component)Component.m_237115_((String)("advancement.ic2." + name)), desc, null, FrameType.CHALLENGE, (flags & 1) != 0, (flags & 2) != 0, (flags & 4) != 0);
    }

    static DisplayInfo goal(ItemLike provider, int flags, String name) {
        return new DisplayInfo(new ItemStack(provider), (Component)Component.m_237115_((String)("advancement.ic2." + name)), (Component)Component.m_237115_((String)("advancement.ic2." + name + ".desc")), null, FrameType.GOAL, (flags & 1) != 0, (flags & 2) != 0, (flags & 4) != 0);
    }

    static DisplayInfo goal(ItemStack stack, int flags, String name) {
        return new DisplayInfo(stack, (Component)Component.m_237115_((String)("advancement.ic2." + name)), (Component)Component.m_237115_((String)("advancement.ic2." + name + ".desc")), null, FrameType.GOAL, (flags & 1) != 0, (flags & 2) != 0, (flags & 4) != 0);
    }

    static DisplayInfo goal(ItemStack stack, int flags, String name, Component desc) {
        return new DisplayInfo(stack, (Component)Component.m_237115_((String)("advancement.ic2." + name)), desc, null, FrameType.GOAL, (flags & 1) != 0, (flags & 2) != 0, (flags & 4) != 0);
    }

    private static void loadRecipes() {
        for (Recipe recipe : AdvRecipeRegistry.INSTANCE.getRecipes()) {
            if (recipe == null) continue;
            ResourceLocation location = recipe.m_6423_();
            Advancement.Builder builder = Advancement.Builder.m_138353_();
            builder.m_138396_(new ResourceLocation("minecraft:recipes/root"));
            builder.m_138354_(new AdvancementRewards.Builder().m_10011_(location));
            builder.m_138386_("hasItem", (CriterionTriggerInstance)new RecipeItemTrigger.Instance(location));
            IC2Advancements.registerAdvancement(location.m_135827_(), location.m_135815_() + "_recipe_advancement", builder);
        }
    }

    public static void reload() {
        ADVANCEMENTS.clear();
        IC2Advancements.loadInternal();
        IC2Advancements.loadRecipes();
        int m = LISTENERS.size();
        for (int i = 0; i < m; ++i) {
            LISTENERS.get(i).accept(ADVANCEMENTS);
        }
    }

    public static void registerListener(Consumer<SimpleRegistry<Advancement.Builder>> listener) {
        LISTENERS.add(listener);
    }

    static JsonObject createCriteria(Item item) {
        JsonObject hasItem = new JsonObject();
        hasItem.addProperty("trigger", "minecraft:inventory_changed");
        JsonObject itemObject = new JsonObject();
        JsonArray array = new JsonArray();
        array.add(ForgeRegistries.ITEMS.getKey((Object)item).toString());
        itemObject.add("items", (JsonElement)array);
        hasItem.add("conditions", (JsonElement)itemObject);
        return hasItem;
    }

    static void handle(JsonElement element, Consumer<String> result) {
        IC2Advancements.iterate(element, T -> IC2Advancements.convert(T, result));
    }

    static void convert(JsonObject obj, Consumer<String> result) {
        if (obj.has("tag")) {
            result.accept(obj.get("tag").getAsString());
        } else if (obj.has("item")) {
            result.accept(obj.get("item").getAsString());
        } else if (obj.has("items")) {
            for (JsonElement el : obj.getAsJsonArray("items")) {
                IC2Advancements.convert(el.getAsJsonObject(), result);
            }
        }
    }

    static void iterate(JsonElement element, Consumer<JsonObject> receiver) {
        if (element.isJsonObject()) {
            receiver.accept(element.getAsJsonObject());
        } else if (element.isJsonArray()) {
            for (JsonElement entry : element.getAsJsonArray()) {
                IC2Advancements.iterate(entry, receiver);
            }
        }
    }
}

