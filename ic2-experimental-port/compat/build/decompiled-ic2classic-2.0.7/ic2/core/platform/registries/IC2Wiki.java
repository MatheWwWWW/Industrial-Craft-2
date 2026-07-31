/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.AbstractObject2IntMap$BasicEntry
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.ChatFormatting
 *  net.minecraft.locale.Language
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraftforge.eventbus.api.Event
 *  net.minecraftforge.fml.loading.FMLEnvironment
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.registries;

import ic2.api.crops.ICrop;
import ic2.core.IC2;
import ic2.core.block.crops.CropRegistry;
import ic2.core.block.transport.item.TubeAction;
import ic2.core.platform.events.impl.WikiEvent;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2MultiBlocks;
import ic2.core.platform.registries.IC2Tags;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.wiki.base.IChapterBuilder;
import ic2.core.wiki.base.book.BuildWiki;
import ic2.core.wiki.base.managers.WikiBuilder;
import ic2.core.wiki.components.builders.CategoryObj;
import ic2.core.wiki.components.builders.CraftObj;
import ic2.core.wiki.components.builders.CropObj;
import ic2.core.wiki.components.builders.DoublePageEndObj;
import ic2.core.wiki.components.builders.HeaderObj;
import ic2.core.wiki.components.builders.IWikiObj;
import ic2.core.wiki.components.builders.ItemListObj;
import ic2.core.wiki.components.builders.LinkObj;
import ic2.core.wiki.components.builders.MultiBlockObj;
import ic2.core.wiki.components.builders.NoSearchObj;
import ic2.core.wiki.components.builders.PaddingObj;
import ic2.core.wiki.components.builders.PageEndObj;
import ic2.core.wiki.components.builders.PreviewObj;
import ic2.core.wiki.components.builders.TextObj;
import it.unimi.dsi.fastutil.objects.AbstractObject2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;

public class IC2Wiki {
    public static BuildWiki IC2_WIKI;

    public static void init() {
        IC2.PLATFORM.addLangFile(new ResourceLocation("ic2", "wiki"));
        if (FMLEnvironment.dist.isDedicatedServer()) {
            return;
        }
        WikiBuilder builder = new WikiBuilder(T -> {
            IC2_WIKI = T;
        }, () -> new ResourceLocation("ic2", "main"), "ic2");
        builder.setMainPage(() -> new ResourceLocation("ic2", "table_of_contents"));
        builder.addBookMarkLocation("ic2c/beta_wiki.dat");
        builder.setControlsPage(() -> new AbstractObject2IntMap.BasicEntry((Object)new ResourceLocation("ic2:main"), 9));
        IC2.PLATFORM.registerWiki(builder);
        builder.addPageAdder(IC2Wiki::addSubChapters);
        builder.addPageAdder(IC2Wiki::addCropPages);
        builder.addPageAdder(IC2Wiki::addTutorials);
        builder.addChapterBuilder(IC2Wiki::addMainChapters);
        builder.addChapterBuilder(IC2Wiki::addBlocksChapter);
        builder.addChapterBuilder(IC2Wiki::addItemsChapter);
        builder.addChapterBuilder(IC2Wiki::addFluidsChapter);
        builder.addChapterBuilder(IC2Wiki::addCropsChapter);
        IC2.EVENT_BUS.post((Event)new WikiEvent.WikiSetupEvent(builder));
    }

    public static void addSubChapters(WikiBuilder builder) {
        builder.addBasicChapter(new ItemLike[]{IC2Blocks.LIQUID_FUEL_GENERATOR, IC2Blocks.OCEAN_GENERATOR, IC2Blocks.ROCKET_MINER, IC2Blocks.INDUSTRIAL_WORKBENCH, IC2Blocks.VILLAGER_O_MAT});
        builder.addBasicChapter(new ItemLike[]{IC2Blocks.CROP_MATRON, IC2Blocks.CROP_HARVESTER, IC2Items.CROP_ANALYZER, IC2Blocks.SIMPLE_CROP_LIBRARY, IC2Blocks.CROP_LIBRARY, IC2Blocks.UU_CROP_LIBRARY});
        builder.addChapter((ItemLike)IC2Blocks.FUEL_BOILER, new HeaderObj("wiki.ic2.subpage.fuel_boiler.head"), new MultiBlockObj(IC2MultiBlocks.FUEL_BOILER.get()), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.fuel_boiler.head"), new TextObj("wiki.ic2.subpage.fuel_boiler.body"));
        builder.addChapter((ItemLike)IC2Blocks.PRESSURE_ALLOY_FURNACE, new HeaderObj("wiki.ic2.subpage.pressure_alloy_furnace.head"), new MultiBlockObj(IC2MultiBlocks.PRESSURE_ALLOY_FURNACE.get()), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.pressure_alloy_furnace.head"), new TextObj("wiki.ic2.subpage.pressure_alloy_furnace.body"));
        builder.addChapter((ItemLike)IC2Blocks.NUCLEAR_REACTOR, new HeaderObj("wiki.ic2.subpage.reactor.head"), new MultiBlockObj(IC2MultiBlocks.REACTOR.get()), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.reactor.head"), new TextObj("wiki.ic2.subpage.reactor.body"));
        builder.addChapter((ItemLike)IC2Blocks.STEAM_REACTOR, new HeaderObj("wiki.ic2.subpage.steam_reactor.head"), new MultiBlockObj(IC2MultiBlocks.STEAM_REACTOR.get()), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.steam_reactor.head"), new TextObj("wiki.ic2.subpage.steam_reactor.body"));
        builder.addChapter((ItemLike)IC2Blocks.THERMAL_GENERATOR, new HeaderObj("wiki.ic2.subpage.thermal_gen.head"), new TextObj("wiki.ic2.subpage.thermal_gen.body"));
        builder.addChapter((ItemLike)IC2Blocks.TANK, new HeaderObj("wiki.ic2.subpage.tank.head"), new MultiBlockObj(IC2MultiBlocks.TANK.get()), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.tank.head"), new TextObj("wiki.ic2.subpage.tank.body"), new ItemListObj(5).addBlockTag(IC2Tags.TANK_STRUCTURE));
        builder.addChapter((ItemLike)IC2Blocks.STEAM_TUNNEL, new HeaderObj("wiki.ic2.subpage.steam_tunnel.head"), new MultiBlockObj(IC2MultiBlocks.STEAM_TUNNEL.get()), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.steam_tunnel.head"), new TextObj("wiki.ic2.subpage.steam_tunnel.body"));
        builder.addChapter((ItemLike)IC2Blocks.ELECTROLYZER, new HeaderObj("wiki.ic2.subpage.electrolyzer.head"), new MultiBlockObj(IC2MultiBlocks.ELECTROLYZER.apply(IC2Blocks.ELECTROLYZER)).setNoPlace(), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.electrolyzer.head"), new TextObj("wiki.ic2.subpage.electrolyzer.body"));
        builder.addChapter((ItemLike)IC2Blocks.CHARGED_ELECTROLYZER, new HeaderObj("wiki.ic2.subpage.electrolyzer.head"), new MultiBlockObj(IC2MultiBlocks.ELECTROLYZER.apply(IC2Blocks.CHARGED_ELECTROLYZER)).setNoPlace(), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.electrolyzer.head"), new TextObj("wiki.ic2.subpage.electrolyzer.body"));
        builder.addChapter((ItemLike)IC2Blocks.CHUNKLOADER, new HeaderObj("wiki.ic2.subpage.chunkloader.head"), new TextObj("wiki.ic2.subpage.chunkloader.body"), new PageEndObj(), new TextObj("wiki.ic2.subpage.chunkloader.body2"));
        builder.addChapter((ItemLike)IC2Blocks.PUMP, new HeaderObj("wiki.ic2.subpage.pump.head"), new TextObj("wiki.ic2.subpage.pump.body"), new MultiBlockObj(IC2MultiBlocks.PUMP.apply(IC2Blocks.PUMP)).setNoPlace(), new TextObj("wiki.ic2.subpage.pump.compressor"));
        builder.addChapter((ItemLike)IC2Blocks.OVERCLOCKED_PUMP, new HeaderObj("wiki.ic2.subpage.pump.head"), new TextObj("wiki.ic2.subpage.pump.body"), new MultiBlockObj(IC2MultiBlocks.PUMP.apply(IC2Blocks.OVERCLOCKED_PUMP)).setNoPlace(), new TextObj("wiki.ic2.subpage.pump.compressor"));
        builder.addChapter((ItemLike)IC2Blocks.HYPERCLOCKED_PUMP, new HeaderObj("wiki.ic2.subpage.pump.head"), new TextObj("wiki.ic2.subpage.pump.body"), new MultiBlockObj(IC2MultiBlocks.PUMP.apply(IC2Blocks.HYPERCLOCKED_PUMP)).setNoPlace(), new TextObj("wiki.ic2.subpage.pump.compressor"));
        builder.addChapter((ItemLike)IC2Blocks.MINER, new HeaderObj("wiki.ic2.subpage.miner.head"), new MultiBlockObj(IC2MultiBlocks.MINER.get()).setNoPlace(), new TextObj("wiki.ic2.subpage.miner.body"));
        builder.addChapter((ItemLike)IC2Blocks.TESLA_COIL, new HeaderObj("wiki.ic2.subpage.tesla_coil.head"), new TextObj("wiki.ic2.subpage.tesla_coil.body"));
        builder.addChapter((ItemLike)IC2Blocks.ELECTRIC_ENCHANTER, new HeaderObj("wiki.ic2.subpage.electric_enchanter.head"), new MultiBlockObj(IC2MultiBlocks.ELECTRIC_ENCHANTER.get()).setNoPlace(), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.electric_enchanter.head"), new TextObj("wiki.ic2.subpage.electric_enchanter.body"));
        builder.addChapter((ItemLike)IC2Blocks.TELEPORTER, new HeaderObj("wiki.ic2.subpage.teleporter.head"), new TextObj("wiki.ic2.subpage.teleporter.body"), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.teleporter.head"), new TextObj("wiki.ic2.subpage.teleporter.body2"));
        builder.addChapter((ItemLike)IC2Blocks.FISHER, new HeaderObj("wiki.ic2.subpage.fisher.head"), new MultiBlockObj(IC2MultiBlocks.FISHER.get()), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.fisher.head"), new TextObj("wiki.ic2.subpage.fisher.body"));
        builder.addChapter((ItemLike)IC2Blocks.FUSION_REACTOR, new HeaderObj("wiki.ic2.subpage.fusion_reactor.head"), new MultiBlockObj(IC2MultiBlocks.FUSION_REACTOR.get()), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.fusion_reactor.head"), new TextObj("wiki.ic2.subpage.fusion_reactor.body"));
        builder.addChapter((ItemLike)IC2Blocks.COLOSSAL_FURNACE, new HeaderObj("wiki.ic2.subpage.colossal_machine.furnace"), new MultiBlockObj(IC2MultiBlocks.COLOSSAL_FURNACE.get()), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.colossal_machine.furnace"), new TextObj("wiki.ic2.subpage.colossal_machine.body"));
        builder.addChapter((ItemLike)IC2Blocks.COLOSSAL_MACERATOR, new HeaderObj("wiki.ic2.subpage.colossal_machine.macerator"), new MultiBlockObj(IC2MultiBlocks.COLOSSAL_MACERATOR.get()), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.colossal_machine.macerator"), new TextObj("wiki.ic2.subpage.colossal_machine.body"));
        builder.addChapter((ItemLike)IC2Blocks.COLOSSAL_EXTRACTOR, new HeaderObj("wiki.ic2.subpage.colossal_machine.extractor"), new MultiBlockObj(IC2MultiBlocks.COLOSSAL_EXTRACTOR.get()), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.colossal_machine.extractor"), new TextObj("wiki.ic2.subpage.colossal_machine.body"));
        builder.addChapter((ItemLike)IC2Blocks.COLOSSAL_COMPRESSOR, new HeaderObj("wiki.ic2.subpage.colossal_machine.compressor"), new MultiBlockObj(IC2MultiBlocks.COLOSSAL_COMPRESSOR.get()), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.colossal_machine.compressor"), new TextObj("wiki.ic2.subpage.colossal_machine.body"));
        builder.addChapter((ItemLike)IC2Blocks.COLOSSAL_RECYCLER, new HeaderObj("wiki.ic2.subpage.colossal_machine.recycler"), new MultiBlockObj(IC2MultiBlocks.COLOSSAL_RECYCLER.get()), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.colossal_machine.recycler"), new TextObj("wiki.ic2.subpage.colossal_machine.body"));
        ObjectList objects = CollectionUtils.createList();
        objects.add((HeaderObj)new HeaderObj("wiki.ic2.subpage.tube_tool.head"));
        objects.add((TextObj)new TextObj("wiki.ic2.subpage.tube_tool.body"));
        for (TubeAction action : TubeAction.getAll()) {
            objects.add((PageEndObj)new PageEndObj());
            objects.add((HeaderObj)new HeaderObj((Component)action.getName().m_130940_(ChatFormatting.BOLD)));
            objects.add((TextObj)new TextObj((Component)action.getDesc()));
        }
        builder.addChapter((ItemLike)IC2Items.TUBE_TOOL, (IWikiObj[])objects.toArray(IWikiObj[]::new));
    }

    public static void addMainChapters(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "main");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.main.head"), new TextObj("wiki.ic2.page.main.intro"), new CraftObj(new ItemLike[]{IC2Items.WIKI_ITEM, IC2Items.UNPRINTED_WIKI}), new TextObj("wiki.ic2.page.main.body"), new PageEndObj(), new HeaderObj("wiki.ic2.page.main.wiki_head"), new TextObj("wiki.ic2.page.main.wiki").withHeader("wiki.ic2.page.main.wiki_head"));
        builder.finishSubChapter();
        WikiEvent.AddonSetupEvent bus = new WikiEvent.AddonSetupEvent();
        IC2.EVENT_BUS.post((Event)bus);
        ObjectArrayList links = new ObjectArrayList();
        links.add(new CategoryObj.Link((ItemLike)Items.f_42517_, "ic2:tutorials").with("wiki.ic2.category.tutorials"));
        links.add(new CategoryObj.Link((ItemLike)IC2Items.COIN, "ic2:credits").with("wiki.ic2.category.credits"));
        if (bus.areAddonsEnabled()) {
            links.add(new CategoryObj.Link((ItemLike)IC2Items.QUANTUM_OVERCLOCKER_UPGRADE, "ic2:addons").with("wiki.ic2.category.addons"));
        }
        builder.addSubChapter("ic2", "table_of_contents");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.table.head"), new CategoryObj("wiki.ic2.category.general", (CategoryObj.Link[])links.toArray(CategoryObj.Link[]::new)), new PaddingObj(10), new CategoryObj("wiki.ic2.category.blocks", new CategoryObj.Link((ItemLike)IC2Blocks.GENERATOR, "ic2:blocks.generators").with("wiki.ic2.category.generators"), new CategoryObj.Link((ItemLike)IC2Blocks.MACERATOR, "ic2:blocks.machines").with("wiki.ic2.category.machines"), new CategoryObj.Link((ItemLike)IC2Items.COPPER_CABLE_1X_INSULATED, "ic2:blocks.cables").with("wiki.ic2.category.cables"), new CategoryObj.Link((ItemLike)IC2Blocks.BATBOX, "ic2:blocks.electric_blocks").with("wiki.ic2.category.electric_blocks"), new CategoryObj.Link((ItemLike)IC2Blocks.PERSONAL_CHEST, "ic2:blocks.personal_blocks").with("wiki.ic2.category.personal_blocks"), new CategoryObj.Link((ItemLike)IC2Blocks.TANK, "ic2:blocks.fluid_blocks").with("wiki.ic2.category.fluid_blocks"), new CategoryObj.Link((ItemLike)IC2Blocks.SIMPLE_TUBE, "ic2:blocks.tubes").with("wiki.ic2.category.tubes"), new CategoryObj.Link((ItemLike)IC2Blocks.ITNT, "ic2:blocks.explosives").with("wiki.ic2.category.explosives"), new CategoryObj.Link((ItemLike)IC2Blocks.TREETAP_AND_BUCKET, "ic2:blocks.misc").with("wiki.ic2.category.block_misc"), new CategoryObj.Link((ItemLike)IC2Items.CF_SPRAYER, "ic2:blocks.cfoam").with("wiki.ic2.category.cfoam")), new PageEndObj(), new HeaderObj("wiki.ic2.page.table.head"), new CategoryObj("wiki.ic2.category.items", new CategoryObj.Link((ItemLike)IC2Items.PICKAXE_BRONZE, "ic2:items.tools").with("wiki.ic2.category.tools"), new CategoryObj.Link((ItemLike)IC2Items.DRILL_DIAMOND, "ic2:items.electric_tools").with("wiki.ic2.category.electric_tools"), new CategoryObj.Link((ItemLike)IC2Items.NANOSUIT_CHESTPLATE, "ic2:items.armors").with("wiki.ic2.category.armors"), new CategoryObj.Link((ItemLike)IC2Items.RE_BATTERY, "ic2:items.batteries").with("wiki.ic2.category.batteries"), new CategoryObj.Link((ItemLike)IC2Items.SCANNER_OD, "ic2:items.scanners").with("wiki.ic2.category.scanners"), new CategoryObj.Link((ItemLike)IC2Items.OVERCLOCKER_UPGRADE, "ic2:items.upgrades").with("wiki.ic2.category.upgrades"), new CategoryObj.Link((ItemLike)IC2Items.URANIUM_ROD_SINGLE, "ic2:items.reactor.components").with("wiki.ic2.category.reactor"), new CategoryObj.Link((ItemLike)IC2Items.MONITOR_CARD, "ic2:items.info.cards").with("wiki.ic2.category.monitor_cards"), new CategoryObj.Link((ItemLike)IC2Items.MEMORY_STICK, "ic2:items.misc").with("wiki.ic2.category.item_misc")), new PaddingObj(10), new CategoryObj("wiki.ic2.category.misc", new CategoryObj.Link((ItemLike)Items.f_42447_, "ic2:fluids").with("wiki.ic2.category.fluids"), new CategoryObj.Link((ItemLike)IC2Items.CROP_SEED, "ic2:crops").with("wiki.ic2.category.crops"), new CategoryObj.Link((ItemLike)Items.f_42616_, "ic2:subpage.villager").with("wiki.ic2.category.villager")));
        IC2.EVENT_BUS.post((Event)new WikiEvent.TableOfContentsSetupEvent(builder));
        builder.finishSubChapter();
        builder.addSubChapter("ic2", "tutorials");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.tutorials.head"), new TextObj("wiki.ic2.page.tutorials.desc").setCutOff(), new PageEndObj(), new HeaderObj("wiki.ic2.page.tutorials.head.path"), new CategoryObj("wiki.ic2.category.progression", new CategoryObj.Link((ItemLike)Items.f_41960_, "ic2:subpage.first_day").with("wiki.ic2.category.first_day"), new CategoryObj.Link((ItemLike)IC2Blocks.MACERATOR, "ic2:subpage.early_game").with("wiki.ic2.category.early_game"), new CategoryObj.Link((ItemLike)IC2Blocks.ALLOY_SMELTER, "ic2:subpage.second_tier").with("wiki.ic2.category.second_tier"), new CategoryObj.Link((ItemLike)IC2Blocks.ELECTROLYZER, "ic2:subpage.third_tier").with("wiki.ic2.category.third_tier"), new CategoryObj.Link((ItemLike)IC2Blocks.MASS_FABRICATOR, "ic2:subpage.fourth_tier").with("wiki.ic2.category.fourth_tier")), new PaddingObj(5), new CategoryObj("wiki.ic2.category.essentials", new CategoryObj.Link((ItemLike)IC2Items.EU_READER, "ic2:subpage.electricity").with("wiki.ic2.category.electricity"), new CategoryObj.Link((ItemLike)IC2Items.WRENCH, "ic2:subpage.wrench").with("wiki.ic2.category.wrenches"), new CategoryObj.Link((ItemLike)IC2Items.RE_BATTERY, "ic2:subpage.item_energy").with("wiki.ic2.category.item_energy"), new CategoryObj.Link((ItemLike)Items.f_42487_, "ic2:subpage.gui").with("wiki.ic2.category.gui")), new PaddingObj(5), new CategoryObj("wiki.ic2.category.misc_tutorials", new CategoryObj.Link((ItemLike)IC2Blocks.SIMPLE_TUBE, "ic2:subpage.logistic_grid").with("wiki.ic2.category.logistic"), new CategoryObj.Link((ItemLike)IC2Blocks.BARREL, "ic2:subpage.brewing").with("wiki.ic2.category.brewing"), new CategoryObj.Link((ItemLike)IC2Blocks.NUCLEAR_REACTOR, "ic2:subpage.nuclear_reactor").with("wiki.ic2.category.reactors"), new CategoryObj.Link((ItemLike)IC2Items.CROP_SEED, "ic2:subpage.crop").with("wiki.ic2.category.crops")));
        builder.finishSubChapter();
        builder.addSubChapter("ic2", "credits");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.credits.head"), new TextObj(IC2Wiki.createCredits()).withHeader("wiki.ic2.page.credits.head"), new NoSearchObj(false));
        builder.finishSubChapter();
        builder.finishBuildChapter(true);
    }

    public static void addTutorials(WikiBuilder builder) {
        builder.addChapter("ic2", "subpage.first_day", new HeaderObj("wiki.ic2.subpage.first_day.head"), new TextObj("wiki.ic2.subpage.first_day.body"), new CraftObj(new ItemLike[]{Blocks.f_49997_, Blocks.f_49996_, Blocks.f_152505_, IC2Blocks.TIN_ORE, IC2Items.STICKY_RESIN, IC2Items.TREETAP, IC2Blocks.TREETAP_AND_BUCKET}), new TextObj("wiki.ic2.subpage.first_day.list"));
        builder.addChapter("ic2", "subpage.early_game", new HeaderObj("wiki.ic2.subpage.early_game.head"), new TextObj("wiki.ic2.subpage.early_game.body"), new CraftObj(new ItemLike[]{IC2Blocks.GENERATOR, IC2Blocks.EXTRACTOR, IC2Blocks.MACERATOR, IC2Blocks.STONE_MACERATOR, IC2Blocks.ELECTRIC_FURNACE, IC2Blocks.IRON_FURNACE, IC2Items.COPPER_CABLE_1X_INSULATED, IC2Items.EU_READER, IC2Items.DRILL, IC2Items.CHAINSAW, IC2Items.MOWING_TOOL, IC2Items.DUST_BRONZE, IC2Items.WRENCH, IC2Items.TOOL_BOX}), new TextObj("wiki.ic2.subpage.early_game.list"));
        builder.addChapter("ic2", "subpage.second_tier", new HeaderObj("wiki.ic2.subpage.second_tier.head"), new TextObj("wiki.ic2.subpage.second_tier.body"), new CraftObj(new ItemLike[]{IC2Blocks.RARE_EARTH_EXTRACTOR, IC2Blocks.ALLOY_SMELTER, IC2Blocks.COMPRESSOR, IC2Items.INGOT_ADVANCED_ALLOY, IC2Items.MINING_LASER, IC2Items.DRILL_ROCK_CUTTER}), new TextObj("wiki.ic2.subpage.second_tier.list"));
        builder.addChapter("ic2", "subpage.third_tier", new HeaderObj("wiki.ic2.subpage.third_tier.head"), new TextObj("wiki.ic2.subpage.third_tier.body"), new CraftObj(new ItemLike[]{IC2Blocks.ELECTROLYZER, IC2Blocks.CHARGED_ELECTROLYZER, IC2Blocks.MFE, IC2Blocks.MFSU, IC2Items.DEAD_MAGNET, IC2Items.PULSATING_QUARTZ}), new TextObj("wiki.ic2.subpage.third_tier.list"));
        builder.addChapter("ic2", "subpage.fourth_tier", new HeaderObj("wiki.ic2.subpage.fourth_tier.head"), new TextObj("wiki.ic2.subpage.fourth_tier.body"), new CraftObj(new ItemLike[]{IC2Blocks.MASS_FABRICATOR, IC2Items.ORE_IRIDIUM, IC2Blocks.SLOW_GRINDER, IC2Items.SCRAP, IC2Items.SCRAPBOX, IC2Items.SCRAP_METAL}));
        builder.addChapter("ic2", "subpage.logistic_grid", new HeaderObj("wiki.ic2.subpage.tubes.head"), new TextObj("wiki.ic2.subpage.tubes.body"));
        builder.addChapter("ic2", "subpage.electricity", new HeaderObj("wiki.ic2.subpage.electricity.head"), new TextObj("wiki.ic2.subpage.electricity.body"));
        builder.addChapter("ic2", "subpage.brewing", new HeaderObj("wiki.ic2.subpage.brewing.head"), new TextObj("wiki.ic2.subpage.brewing.body"));
        builder.addChapter("ic2", "subpage.wrench", new HeaderObj("wiki.ic2.subpage.wrench.head"), new TextObj("wiki.ic2.subpage.wrench.body"));
        builder.addChapter("ic2", "subpage.item_energy", new HeaderObj("wiki.ic2.subpage.item_energy.head"), new TextObj("wiki.ic2.subpage.item_energy.body"));
        builder.addChapter("ic2", "subpage.gui", new HeaderObj("wiki.ic2.subpage.gui.head"), new TextObj("wiki.ic2.subpage.gui.body"));
        builder.addChapter("ic2", "subpage.crop", new HeaderObj("wiki.ic2.subpage.crop.head"), new MultiBlockObj(IC2MultiBlocks.CROP_BLOCK.apply(0)).setNoPlace(), new PageEndObj(), new HeaderObj("wiki.ic2.subpage.crop.head"), new TextObj("wiki.ic2.subpage.crop.body"), new MultiBlockObj(IC2MultiBlocks.CROP_BLOCK.apply(1)).setNoPlace(), new TextObj("wiki.ic2.subpage.crop.breeding"), new MultiBlockObj(IC2MultiBlocks.CROP_BLOCK.apply(2)).setNoPlace(), new TextObj("wiki.ic2.subpage.crop.subsoil"));
        builder.addChapter("ic2", "subpage.villager", new HeaderObj("wiki.ic2.subpage.villager.head"), new TextObj("wiki.ic2.subpage.villager.body"), new CraftObj(new ItemLike[]{IC2Blocks.IRON_FURNACE, IC2Blocks.NUCLEAR_REACTOR, IC2Blocks.SIMPLE_CROP_LIBRARY, IC2Blocks.ITNT, IC2Blocks.BARREL, IC2Blocks.PIXELBLOCK_GREG}), new TextObj("wiki.ic2.subpage.villager.recipes"));
        ObjectArrayList list = new ObjectArrayList();
        IC2.EVENT_BUS.post((Event)new WikiEvent.AddonPageEvent(arg_0 -> IC2Wiki.lambda$addTutorials$6((List)list, arg_0)));
        if (list.size() > 0) {
            list.addAll(0, Arrays.asList(new HeaderObj("wiki.ic2.subpage.addons.head"), new TextObj("wiki.ic2.subpage.addons.body"), new PageEndObj()));
            builder.addChapter(new ResourceLocation("ic2:addons"), (IWikiObj[])list.toArray(IWikiObj[]::new));
        }
    }

    public static void addBlocksChapter(IChapterBuilder builder) {
        IC2Wiki.addGeneratorsChapter(builder);
        IC2Wiki.addMachinesChapter(builder);
        IC2Wiki.addCablesChapter(builder);
        IC2Wiki.addSpecialElectricBlocksChapter(builder);
        IC2Wiki.addPersonalBlocksChapter(builder);
        IC2Wiki.addFluidBlocksChapter(builder);
        IC2Wiki.addTubesChapter(builder);
        IC2Wiki.addExplosives(builder);
        IC2Wiki.addDecorativeChapter(builder);
    }

    public static void addGeneratorsChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "blocks.generators");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.generator"), new TextObj("wiki.ic2.page.blocks.generator.desc"), new DoublePageEndObj(), new PaddingObj(9).setOptional(), new TextObj("wiki.ic2.page.blocks.generator.category"));
        builder.finishSubChapter();
        builder.addSubChapter("ic2", "blocks.generators.lv");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.generator.lv"), new TextObj("wiki.ic2.page.blocks.generator.lv.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Blocks.GENERATOR, IC2Blocks.GEOTHERMAL_GENERATOR, IC2Blocks.SOLAR_PANEL, IC2Blocks.WATER_MILL, IC2Blocks.WIND_TURBINE, IC2Blocks.SOLAR_TURBINE});
        builder.finishSubChapter();
        builder.addSubChapter("ic2", "blocks.generators.mv");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.generator.mv"), new TextObj("wiki.ic2.page.blocks.generator.mv.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Blocks.SLAG_GENERATOR, IC2Blocks.WAVE_GENERATOR, IC2Blocks.THERMAL_GENERATOR, IC2Blocks.STEAM_TURBINE, IC2Blocks.LIQUID_FUEL_GENERATOR, IC2Blocks.NUCLEAR_REACTOR});
        builder.finishSubChapter();
        builder.addSubChapter("ic2", "blocks.generators.hv");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.generator.hv"), new TextObj("wiki.ic2.page.blocks.generator.hv.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Blocks.STEAM_TUNNEL, IC2Blocks.STEAM_REACTOR, IC2Blocks.FUEL_BOILER, IC2Blocks.OCEAN_GENERATOR, IC2Blocks.WINDMILL});
        builder.finishSubChapter();
        builder.addSubChapter("ic2", "blocks.generators.compact");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.generator.compact"), new TextObj("wiki.ic2.page.blocks.generator.compact.desc"), new DoublePageEndObj());
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.compact_solar_panel", new ItemLike[]{IC2Blocks.SOLAR_PANEL_LV, IC2Blocks.SOLAR_PANEL_MV, IC2Blocks.SOLAR_PANEL_HV}));
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Blocks.SOLAR_PANEL_COMPRESSED, IC2Blocks.SOLAR_PANEL_LV_COMPRESSED, IC2Blocks.SOLAR_PANEL_MV_COMPRESSED, IC2Blocks.SOLAR_PANEL_HV_COMPRESSED}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.compact_watermill", new ItemLike[]{IC2Blocks.WATER_MILL_LV, IC2Blocks.WATER_MILL_MV, IC2Blocks.WATER_MILL_HV}));
        builder.finishSubChapter();
        builder.finishBuildChapter(true);
    }

    public static void addMachinesChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "blocks.machines");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.machines"), new TextObj("wiki.ic2.page.blocks.machines.desc"), new DoublePageEndObj(), new PaddingObj(9).setOptional(), new TextObj("wiki.ic2.page.blocks.machines.category"));
        builder.finishSubChapter();
        builder.addSubChapter("ic2", "blocks.machines.nv");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.machines.nv"), new TextObj("wiki.ic2.page.blocks.machines.nv.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Blocks.IRON_FURNACE, IC2Blocks.STONE_MACERATOR, IC2Blocks.STONE_CANNER, IC2Blocks.WOOD_GASSIFIER, IC2Blocks.ARMOR_CONFIGURATOR, IC2Blocks.SOUND_BEACON});
        builder.finishSubChapter();
        builder.addSubChapter("ic2", "blocks.machines.worktable");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.machines.worktable"), new TextObj("wiki.ic2.page.blocks.machines.worktable.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Blocks.INDUSTRIAL_WORKBENCH, IC2Blocks.STORAGE_EXPANSION, IC2Blocks.BUFFER_STORAGE_EXPANSION, IC2Blocks.MEMORY_EXPANSION, IC2Blocks.FLUID_EXPANSION, IC2Blocks.UU_EXPANSION});
        builder.finishSubChapter();
        builder.addSubChapter("ic2", "blocks.machines.lv");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.machines.lv"), new TextObj("wiki.ic2.page.blocks.machines.lv.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Blocks.ELECTRIC_FURNACE, IC2Blocks.ELECTRIC_BLAST_FURNACE, IC2Blocks.ELECTRIC_SMOKER, IC2Blocks.MACERATOR, IC2Blocks.EXTRACTOR, IC2Blocks.COMPRESSOR, IC2Blocks.RECYCLER, IC2Blocks.SAWMILL, IC2Blocks.RARE_EARTH_EXTRACTOR, IC2Blocks.PUMP, IC2Blocks.MINER, IC2Blocks.CANNER, IC2Blocks.MAGNETIZER, IC2Blocks.ELECTRIC_WOOD_GASSIFIER, IC2Blocks.MACHINE_BUFFER, IC2Blocks.MACHINE_TANK, IC2Blocks.ELECTROLYZER, IC2Blocks.ALLOY_SMELTER});
        builder.finishSubChapter();
        builder.addSubChapter("ic2", "blocks.machines.mv");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.machines.mv"), new TextObj("wiki.ic2.page.blocks.machines.mv.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Blocks.INDUCTION_FURNACE, IC2Blocks.INDUCTION_BLAST_FURNACE, IC2Blocks.INDUCTION_SMOKER, IC2Blocks.ROTARY_MACERATOR, IC2Blocks.SINGULARITY_COMPRESSOR, IC2Blocks.CENTRIFUGAL_EXTRACTOR, IC2Blocks.COMPACTING_RECYCLER, IC2Blocks.CHARGED_ELECTROLYZER, IC2Blocks.CENTRIFUGAL_RARE_EARTH_EXTRACTOR, IC2Blocks.CHUNKLOADER, IC2Blocks.OVERCLOCKED_PUMP, IC2Blocks.TESLA_COIL, IC2Blocks.CROP_MATRON, IC2Blocks.CROP_HARVESTER, IC2Blocks.CROP_ANALYZER, IC2Blocks.SLOW_GRINDER, IC2Blocks.VACUUM_CANNER, IC2Blocks.RANGED_PUMP, IC2Blocks.BASE_TELEPORTER, IC2Blocks.SIMPLE_CROP_LIBRARY, IC2Blocks.REACTOR_PLANNER, IC2Blocks.REFINERY});
        builder.finishSubChapter();
        builder.addSubChapter("ic2", "blocks.machines.hv");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.machines.hv"), new TextObj("wiki.ic2.page.blocks.machines.hv.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Blocks.MASS_FABRICATOR, IC2Blocks.ELECTRIC_ENCHANTER, IC2Blocks.TELEPORTER, IC2Blocks.URANIUM_ENRICHER, IC2Blocks.ORE_SCANNER, IC2Blocks.CROP_LIBRARY, IC2Blocks.ROCKET_MINER, IC2Blocks.PRESSURE_ALLOY_FURNACE, IC2Blocks.TERRAFORMER, IC2Blocks.VILLAGER_O_MAT});
        builder.finishSubChapter();
        builder.addSubChapter("ic2", "blocks.machines.ev");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.machines.ev"), new TextObj("wiki.ic2.page.blocks.machines.ev.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Blocks.PLASMAFIER, IC2Blocks.UU_CROP_LIBRARY, IC2Blocks.COLOSSAL_MACERATOR, IC2Blocks.COLOSSAL_FURNACE, IC2Blocks.COLOSSAL_EXTRACTOR, IC2Blocks.COLOSSAL_COMPRESSOR, IC2Blocks.COLOSSAL_RECYCLER, IC2Blocks.FISHER});
        builder.finishSubChapter();
        builder.addSubChapter("ic2", "blocks.machines.luv");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.machines.luv"), new TextObj("wiki.ic2.page.blocks.machines.luv.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Blocks.TELEPORTER_HUB, IC2Blocks.FUSION_REACTOR});
        builder.finishSubChapter();
        builder.finishBuildChapter(true);
    }

    public static void addCablesChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "blocks.cables");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.cables"), new TextObj("wiki.ic2.page.blocks.cables.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Items.TIN_CABLE});
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.COPPER_CABLE, IC2Items.COPPER_CABLE_1X_INSULATED}));
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.GOLD_CABLE, IC2Items.GOLD_CABLE_1X_INSULATED, IC2Items.GOLD_CABLE_2X_INSULATED}));
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.BRONZE_CABLE, IC2Items.BRONZE_CABLE_1X_INSULATED, IC2Items.BRONZE_CABLE_2X_INSULATED}));
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.IRON_CABLE, IC2Items.IRON_CABLE_1X_INSULATED, IC2Items.IRON_CABLE_2X_INSULATED, IC2Items.IRON_CABLE_4X_INSULATED}));
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.ALUMINIUM_CABLE, IC2Items.ALUMINIUM_CABLE_1X_INSULATED, IC2Items.ALUMINIUM_CABLE_2X_INSULATED, IC2Items.ALUMINIUM_CABLE_4X_INSULATED, IC2Items.ALUMINIUM_CABLE_8X_INSULATED}));
        builder.addSubPages(new ItemLike[]{IC2Items.GLASSFIBER_CABLE, IC2Items.SUPER_CABLE, IC2Items.PLASMA_CABLE, IC2Items.DETECTOR_CABLE, IC2Items.SPLITTER_CABLE, IC2Items.COMPARING_CABLE, IC2Blocks.REDIRECTOR_MASTER, IC2Blocks.REDIRECTOR_SLAVE});
        builder.finishBuildChapter(true);
    }

    public static void addSpecialElectricBlocksChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "blocks.electric_blocks");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.electric_blocks"), new TextObj("wiki.ic2.page.blocks.electric_blocks.desc"), new DoublePageEndObj());
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.energy_storage", new ItemLike[]{IC2Blocks.BATBOX, IC2Blocks.MFE, IC2Blocks.MFSU, IC2Blocks.ESU, IC2Blocks.ISU, IC2Blocks.PESU}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.transformer", new ItemLike[]{IC2Blocks.TRANSFORMER_LV, IC2Blocks.TRANSFORMER_MV, IC2Blocks.TRANSFORMER_HV, IC2Blocks.TRANSFORMER_EV, IC2Blocks.TRANSFORMER_IV}));
        builder.addSubPages(new ItemLike[]{IC2Blocks.TRANSFORMER_ADJUSTABLE});
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.charging_bench", new ItemLike[]{IC2Blocks.CHARGING_BENCH_LV, IC2Blocks.CHARGING_BENCH_MV, IC2Blocks.CHARGING_BENCH_HV, IC2Blocks.CHARGING_BENCH_EV, IC2Blocks.CHARGING_BENCH_IV}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.battery_station", new ItemLike[]{IC2Blocks.BATTERY_STATION_LV, IC2Blocks.BATTERY_STATION_MV, IC2Blocks.BATTERY_STATION_HV, IC2Blocks.BATTERY_STATION_EV, IC2Blocks.BATTERY_STATION_IV}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.chargepad", new ItemLike[]{IC2Blocks.LV_CHARGEPAD, IC2Blocks.MV_CHARGEPAD, IC2Blocks.HV_CHARGEPAD, IC2Blocks.FISSION_CHARGEPAD}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.flux_generator", new ItemLike[]{IC2Blocks.FLUX_GENERATOR_LV, IC2Blocks.FLUX_GENERATOR_MV, IC2Blocks.FLUX_GENERATOR_HV}));
        builder.addSubPages(new ItemLike[]{IC2Blocks.LUMINATOR, IC2Blocks.LUMINATOR_ADJUSTABLE, IC2Blocks.CONSTRUCTION_LIGHT, IC2Blocks.MONITOR});
        builder.finishBuildChapter(true);
    }

    public static void addPersonalBlocksChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "blocks.personal_blocks");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.personal_blocks"), new TextObj("wiki.ic2.page.blocks.personal_blocks.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Blocks.PERSONAL_CHEST, IC2Blocks.PERSONAL_TANK});
        builder.addSubPages(new ItemLike[]{IC2Blocks.TRADE_O_MAT, IC2Blocks.FLUID_O_MAT, IC2Blocks.ENERGY_O_MAT});
        builder.addSubPages(new ItemLike[]{IC2Blocks.IRIDIUM_STONE});
        builder.finishBuildChapter(true);
    }

    public static void addFluidBlocksChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "blocks.fluid_blocks");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.fluid_blocks"), new TextObj("wiki.ic2.page.blocks.fluid_blocks.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Blocks.PIPE, IC2Blocks.DETECTOR_PIPE, IC2Blocks.SPLITTER_PIPE, IC2Blocks.HIGH_CAPACITY_PIPE, IC2Blocks.HIGH_PRESSURE_PIPE});
        builder.addSubPages(new ItemLike[]{IC2Blocks.TANK, IC2Blocks.VALVE, IC2Blocks.VALVE_PUSHING, IC2Blocks.TANK_ANIMATED, IC2Blocks.VALVE_TELEPORTING});
        builder.addSubPages(new ItemLike[]{IC2Blocks.ELECTRIC_PIPE_PUMP});
        builder.finishBuildChapter(true);
    }

    public static void addTubesChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "blocks.tubes");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.tubes"), new TextObj("wiki.ic2.page.blocks.tubes.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Blocks.SIMPLE_TUBE, IC2Blocks.SPEED_TUBE, IC2Blocks.TRANSPORT_TUBE, IC2Blocks.VOID_TUBE, IC2Blocks.HOVER_TUBE, IC2Blocks.DIRECTIONAL_TUBE, IC2Blocks.FILTER_TUBE, IC2Blocks.INSERTION_TUBE, IC2Blocks.EXTRACTOR_TUBE, IC2Blocks.FILTERED_EXTRACTION_TUBE, IC2Blocks.STICKY_TUBE, IC2Blocks.STACKING_TUBE, IC2Blocks.PICKUP_TUBE, IC2Blocks.REDSTONE_TUBE, IC2Blocks.SWITCH_TUBE, IC2Blocks.ROUND_ROBIN_TUBE, IC2Blocks.TELEPORT_TUBE, IC2Blocks.DROPPING_TUBE, IC2Blocks.DYEING_TUBE, IC2Blocks.COLOR_FILTER_TUBE, IC2Blocks.REQUEST_TUBE, IC2Blocks.PROVIDER_TUBE, IC2Blocks.LIMITER_TUBE, IC2Blocks.FLUID_TUBE});
        builder.finishBuildChapter(true);
    }

    public static void addExplosives(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "blocks.explosives");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.explosives"), new TextObj("wiki.ic2.page.blocks.explosives.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Blocks.ITNT});
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.nuke", true, null, new ItemLike[]{IC2Blocks.NUKE}));
        builder.addSubPages(new ItemLike[]{IC2Blocks.DYNAMITE, IC2Items.STICKY_DYNAMITE, IC2Items.DYNAMITE_REMOTE});
        builder.finishBuildChapter(true);
    }

    public static void addDecorativeChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "blocks.misc");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.misc"), new TextObj("wiki.ic2.page.blocks.misc.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Blocks.TREETAP_AND_BUCKET});
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.reinforced.stone", new ItemLike[]{IC2Blocks.REINFORCED_STONE, IC2Blocks.REINFORCED_BRICK, IC2Blocks.REINFORCED_CRACKED_STONE}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.reinforced.glass", new ItemLike[]{IC2Blocks.REINFORCED_GLASS, IC2Blocks.REINFORCED_CLEAR_GLASS}));
        builder.addSubPages(new ItemLike[]{IC2Blocks.REINFORCED_DOOR});
        builder.finishBuildChapter(true);
        builder.startBuildChapter("ic2", "blocks.cfoam");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.blocks.cfoam"), new TextObj("wiki.ic2.page.blocks.cfoam.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Blocks.CFOAM_WET});
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.cfoam", new ItemLike[]{IC2Blocks.CFOAM_BLOCK_BLACK, IC2Blocks.CFOAM_BLOCK_BLUE, IC2Blocks.CFOAM_BLOCK_BROWN, IC2Blocks.CFOAM_BLOCK_CYAN, IC2Blocks.CFOAM_BLOCK_GRAY, IC2Blocks.CFOAM_BLOCK_GREEN, IC2Blocks.CFOAM_BLOCK_LIGHT_BLUE, IC2Blocks.CFOAM_BLOCK_LIGHT_GRAY, IC2Blocks.CFOAM_BLOCK_LIME, IC2Blocks.CFOAM_BLOCK_MAGENTA, IC2Blocks.CFOAM_BLOCK_ORANGE, IC2Blocks.CFOAM_BLOCK_PINK, IC2Blocks.CFOAM_BLOCK_PURPLE, IC2Blocks.CFOAM_BLOCK_RED, IC2Blocks.CFOAM_BLOCK_WHITE, IC2Blocks.CFOAM_BLOCK_YELLOW}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.cfoam.wool", new ItemLike[]{IC2Blocks.CFOAM_WOOL_BLACK, IC2Blocks.CFOAM_WOOL_BLUE, IC2Blocks.CFOAM_WOOL_BROWN, IC2Blocks.CFOAM_WOOL_CYAN, IC2Blocks.CFOAM_WOOL_GRAY, IC2Blocks.CFOAM_WOOL_GREEN, IC2Blocks.CFOAM_WOOL_LIGHT_BLUE, IC2Blocks.CFOAM_WOOL_LIGHT_GRAY, IC2Blocks.CFOAM_WOOL_LIME, IC2Blocks.CFOAM_WOOL_MAGENTA, IC2Blocks.CFOAM_WOOL_ORANGE, IC2Blocks.CFOAM_WOOL_PINK, IC2Blocks.CFOAM_WOOL_PURPLE, IC2Blocks.CFOAM_WOOL_RED, IC2Blocks.CFOAM_WOOL_WHITE, IC2Blocks.CFOAM_WOOL_YELLOW}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.cfoam.carpet", new ItemLike[]{IC2Blocks.CFOAM_WOOL_CARPET_BLACK, IC2Blocks.CFOAM_WOOL_CARPET_BLUE, IC2Blocks.CFOAM_WOOL_CARPET_BROWN, IC2Blocks.CFOAM_WOOL_CARPET_CYAN, IC2Blocks.CFOAM_WOOL_CARPET_GRAY, IC2Blocks.CFOAM_WOOL_CARPET_GREEN, IC2Blocks.CFOAM_WOOL_CARPET_LIGHT_BLUE, IC2Blocks.CFOAM_WOOL_CARPET_LIGHT_GRAY, IC2Blocks.CFOAM_WOOL_CARPET_LIME, IC2Blocks.CFOAM_WOOL_CARPET_MAGENTA, IC2Blocks.CFOAM_WOOL_CARPET_ORANGE, IC2Blocks.CFOAM_WOOL_CARPET_PINK, IC2Blocks.CFOAM_WOOL_CARPET_PURPLE, IC2Blocks.CFOAM_WOOL_CARPET_RED, IC2Blocks.CFOAM_WOOL_CARPET_WHITE, IC2Blocks.CFOAM_WOOL_CARPET_YELLOW}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.cfoam.stairs", new ItemLike[]{IC2Blocks.CFOAM_STAIRS_BLACK, IC2Blocks.CFOAM_STAIRS_BLUE, IC2Blocks.CFOAM_STAIRS_BROWN, IC2Blocks.CFOAM_STAIRS_CYAN, IC2Blocks.CFOAM_STAIRS_GRAY, IC2Blocks.CFOAM_STAIRS_GREEN, IC2Blocks.CFOAM_STAIRS_LIGHT_BLUE, IC2Blocks.CFOAM_STAIRS_LIGHT_GRAY, IC2Blocks.CFOAM_STAIRS_LIME, IC2Blocks.CFOAM_STAIRS_MAGENTA, IC2Blocks.CFOAM_STAIRS_ORANGE, IC2Blocks.CFOAM_STAIRS_PINK, IC2Blocks.CFOAM_STAIRS_PURPLE, IC2Blocks.CFOAM_STAIRS_RED, IC2Blocks.CFOAM_STAIRS_WHITE, IC2Blocks.CFOAM_STAIRS_YELLOW}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.cfoam.slabs", new ItemLike[]{IC2Blocks.CFOAM_SLAB_BLACK, IC2Blocks.CFOAM_SLAB_BLUE, IC2Blocks.CFOAM_SLAB_BROWN, IC2Blocks.CFOAM_SLAB_CYAN, IC2Blocks.CFOAM_SLAB_GRAY, IC2Blocks.CFOAM_SLAB_GREEN, IC2Blocks.CFOAM_SLAB_LIGHT_BLUE, IC2Blocks.CFOAM_SLAB_LIGHT_GRAY, IC2Blocks.CFOAM_SLAB_LIME, IC2Blocks.CFOAM_SLAB_MAGENTA, IC2Blocks.CFOAM_SLAB_ORANGE, IC2Blocks.CFOAM_SLAB_PINK, IC2Blocks.CFOAM_SLAB_PURPLE, IC2Blocks.CFOAM_SLAB_RED, IC2Blocks.CFOAM_SLAB_WHITE, IC2Blocks.CFOAM_SLAB_YELLOW}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.cfoam.tiles", new ItemLike[]{IC2Blocks.CFOAM_TILE_BLACK, IC2Blocks.CFOAM_TILE_BLUE, IC2Blocks.CFOAM_TILE_BROWN, IC2Blocks.CFOAM_TILE_CYAN, IC2Blocks.CFOAM_TILE_GRAY, IC2Blocks.CFOAM_TILE_GREEN, IC2Blocks.CFOAM_TILE_LIGHT_BLUE, IC2Blocks.CFOAM_TILE_LIGHT_GRAY, IC2Blocks.CFOAM_TILE_LIME, IC2Blocks.CFOAM_TILE_MAGENTA, IC2Blocks.CFOAM_TILE_ORANGE, IC2Blocks.CFOAM_TILE_PINK, IC2Blocks.CFOAM_TILE_PURPLE, IC2Blocks.CFOAM_TILE_RED, IC2Blocks.CFOAM_TILE_WHITE, IC2Blocks.CFOAM_TILE_YELLOW}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.cfoam.panel", new ItemLike[]{IC2Blocks.CFOAM_PANEL_BLACK, IC2Blocks.CFOAM_PANEL_BLUE, IC2Blocks.CFOAM_PANEL_BROWN, IC2Blocks.CFOAM_PANEL_CYAN, IC2Blocks.CFOAM_PANEL_GRAY, IC2Blocks.CFOAM_PANEL_GREEN, IC2Blocks.CFOAM_PANEL_LIGHT_BLUE, IC2Blocks.CFOAM_PANEL_LIGHT_GRAY, IC2Blocks.CFOAM_PANEL_LIME, IC2Blocks.CFOAM_PANEL_MAGENTA, IC2Blocks.CFOAM_PANEL_ORANGE, IC2Blocks.CFOAM_PANEL_PINK, IC2Blocks.CFOAM_PANEL_PURPLE, IC2Blocks.CFOAM_PANEL_RED, IC2Blocks.CFOAM_PANEL_WHITE, IC2Blocks.CFOAM_PANEL_YELLOW}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.cfoam.wall", new ItemLike[]{IC2Blocks.CFOAM_WALL_BLACK, IC2Blocks.CFOAM_WALL_BLUE, IC2Blocks.CFOAM_WALL_BROWN, IC2Blocks.CFOAM_WALL_CYAN, IC2Blocks.CFOAM_WALL_GRAY, IC2Blocks.CFOAM_WALL_GREEN, IC2Blocks.CFOAM_WALL_LIGHT_BLUE, IC2Blocks.CFOAM_WALL_LIGHT_GRAY, IC2Blocks.CFOAM_WALL_LIME, IC2Blocks.CFOAM_WALL_MAGENTA, IC2Blocks.CFOAM_WALL_ORANGE, IC2Blocks.CFOAM_WALL_PINK, IC2Blocks.CFOAM_WALL_PURPLE, IC2Blocks.CFOAM_WALL_RED, IC2Blocks.CFOAM_WALL_WHITE, IC2Blocks.CFOAM_WALL_YELLOW}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.pixelfoam", new ItemLike[]{IC2Blocks.PIXELBLOCK_BOLT, IC2Blocks.PIXELBLOCK_CROWN, IC2Blocks.PIXELBLOCK_FIRE, IC2Blocks.PIXELBLOCK_GREG, IC2Blocks.PIXELBLOCK_HALF_LIFE, IC2Blocks.PIXELBLOCK_HEART, IC2Blocks.PIXELBLOCK_IC2, IC2Blocks.PIXELBLOCK_MUFFIN_BUTTON, IC2Blocks.PIXELBLOCK_NUCLEAR, IC2Blocks.PIXELBLOCK_PORTAL, IC2Blocks.PIXELBLOCK_ROSE, IC2Blocks.PIXELBLOCK_TRIFORCE, IC2Blocks.PIXELBLOCK_SPACE_BEAN}));
        builder.finishBuildChapter(true);
    }

    public static void addItemsChapter(IChapterBuilder builder) {
        IC2Wiki.addToolsChapter(builder);
        IC2Wiki.addElectricToolsChapter(builder);
        IC2Wiki.addArmorChapter(builder);
        IC2Wiki.addBatteryChapter(builder);
        IC2Wiki.addScannerChapter(builder);
        IC2Wiki.addUpgradeChapter(builder);
        IC2Wiki.addReactorComponentChapter(builder);
        IC2Wiki.addInfoCardChapter(builder);
        IC2Wiki.addMiscChapter(builder);
    }

    public static void addToolsChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "items.tools");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.items.tools"), new TextObj("wiki.ic2.page.items.tools.desc"), new DoublePageEndObj());
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.tools.bronze", new ItemLike[]{IC2Items.AXE_BRONZE, IC2Items.HOE_BRONZE, IC2Items.PICKAXE_BRONZE, IC2Items.SHOVEL_BRONZE, IC2Items.SWORD_BRONZE}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.tools.aluminium", new ItemLike[]{IC2Items.AXE_ALUMINIUM, IC2Items.HOE_ALUMINIUM, IC2Items.PICKAXE_ALUMINIUM, IC2Items.SHOVEL_ALUMINIUM, IC2Items.SWORD_ALUMINIUM}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.shields", new ItemLike[]{IC2Items.SHIELD_BRONZE, IC2Items.SHIELD_ALUMINUM, IC2Items.SHIELD_PLATED, IC2Items.SHIELD_NANO}));
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.PAINTER, IC2Items.PAINTER_BLACK, IC2Items.PAINTER_BLUE, IC2Items.PAINTER_BROWN, IC2Items.PAINTER_CYAN, IC2Items.PAINTER_GRAY, IC2Items.PAINTER_GREEN, IC2Items.PAINTER_LIGHT_BLUE, IC2Items.PAINTER_LIGHT_GRAY, IC2Items.PAINTER_LIME, IC2Items.PAINTER_MAGENTA, IC2Items.PAINTER_ORANGE, IC2Items.PAINTER_PINK, IC2Items.PAINTER_PURPLE, IC2Items.PAINTER_RED, IC2Items.PAINTER_WHITE, IC2Items.PAINTER_YELLOW}));
        builder.addSubPages(new ItemLike[]{IC2Items.TREETAP, IC2Items.WRENCH, IC2Items.CUTTER, IC2Items.CF_SPRAYER});
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.TOOL_BOX, IC2Items.TOOL_BOX_CARBON, IC2Items.TOOL_BOX_IRIDIUM}));
        builder.addSubPages(new ItemLike[]{IC2Items.EU_READER, IC2Items.THERMOMETER, IC2Items.CROP_ANALYZER, IC2Items.TUBE_TOOL});
        builder.finishBuildChapter(true);
    }

    public static void addElectricToolsChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "items.electric_tools");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.items.electric_tools"), new TextObj("wiki.ic2.page.items.electric_tools.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Items.DRILL, IC2Items.DRILL_DIAMOND, IC2Items.DRILL_ROCK_CUTTER, IC2Items.DRILL_ADVANCED});
        builder.addSubPages(new ItemLike[]{IC2Items.CHAINSAW, IC2Items.CHAINSAW_ADVANCED, IC2Items.ELECTRIC_HOE, IC2Items.ADVANCED_HOE});
        builder.addSubPages(new ItemLike[]{IC2Items.ELECTRIC_WRENCH, IC2Items.PRECISION_WRENCH, IC2Items.MOWING_TOOL});
        builder.addSubPages(new ItemLike[]{IC2Items.ELECTRIC_SPRAYER, IC2Items.ELECTRIC_TREETAP, IC2Items.ADVANCED_TREETAP});
        builder.addSubPages(new ItemLike[]{IC2Items.OBSCURATOR, IC2Items.PORTABLE_TELEPORTER});
        builder.addSubPages(new ItemLike[]{IC2Items.NANO_SABER, IC2Items.MINING_LASER});
        builder.finishBuildChapter(true);
    }

    public static void addArmorChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "items.armors");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.items.armors"), new TextObj("wiki.ic2.page.items.armors.desc"), new DoublePageEndObj());
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.armor.bronze", new ItemLike[]{IC2Items.BRONZE_HELMET, IC2Items.BRONZE_CHEST, IC2Items.BRONZE_LEGGINGS, IC2Items.BRONZE_BOOTS}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.armor.composite", new ItemLike[]{IC2Items.COMPOSITE_HELMET, IC2Items.COMPOSITE_CHEST, IC2Items.COMPOSITE_LEGGINGS, IC2Items.COMPOSITE_BOOTS}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.armor.hazmat", new ItemLike[]{IC2Items.HAZMAT_HELMET, IC2Items.HAZMAT_CHEST, IC2Items.HAZMAT_LEGGINGS, IC2Items.HAZMAT_BOOTS}));
        builder.addSubPages(new ItemLike[]{IC2Items.SOLAR_HELMET, IC2Items.SOLAR_HELMET_ADVANCED, IC2Items.STATIC_BOOTS});
        builder.addSubPages(new ItemLike[]{IC2Items.NIGHT_VISION_HELMET, IC2Items.CF_PACK});
        builder.addSubPages(new ItemLike[]{IC2Items.JETPACK_FUEL});
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.armor.jetpack.electric", new ItemLike[]{IC2Items.JETPACK_ELECTRIC, IC2Items.JETPACK_ELECTRIC_COMPACT}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.armor.jetpack.nuclear", new ItemLike[]{IC2Items.JETPACK_NUCLEAR, IC2Items.JETPACK_NUCLEAR_COMPACT}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.armor.nano", IC2Items.NANOSUIT_HELMET.createDefaultArmor(), IC2Items.NANOSUIT_CHESTPLATE.createDefaultArmor(), IC2Items.NANOSUIT_LEGGINGS.createDefaultArmor(), IC2Items.NANOSUIT_BOOTS.createDefaultArmor()));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.armor.quantum", IC2Items.QUANTUM_SUIT_HELMET.createDefaultArmor(), IC2Items.QUANTUM_SUIT_CHESTPLATE.createDefaultArmor(), IC2Items.QUANTUM_SUIT_LEGGINGS.createDefaultArmor(), IC2Items.QUANTUM_SUIT_BOOTS.createDefaultArmor()));
        builder.addSubPages(new ItemLike[]{IC2Items.BASE_MODULE, IC2Items.AIR_REFILL_MODULE, IC2Items.AUTO_FEED_MODULE, IC2Items.CHARGER_MODULE, IC2Items.FOOD_STORAGE_MODULE, IC2Items.JUMP_BOOST_MODULE, IC2Items.FLASH_LIGHT_MODULE, IC2Items.PARACHUTE_MODULE, IC2Items.PROTECTION_MODULE, IC2Items.SPEED_MODULE, IC2Items.STEP_ASSIST_MODULE, IC2Items.WATER_WALKER_MODULE, IC2Items.TANK_MODULE, IC2Items.EXTINGUISHING_MODULE, IC2Items.LAVA_PROTECT_MODULE, IC2Items.SONAR_MODULE, IC2Items.ENERGY_HUD_MODULE, IC2Items.GOLDEN_MODULE});
        builder.finishBuildChapter(true);
    }

    public static void addBatteryChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "items.batteries");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.items.batteries"), new TextObj("wiki.ic2.page.items.batteries.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Items.SU_BATTERY});
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.battery", new ItemLike[]{IC2Items.RE_BATTERY, IC2Items.ENERGY_CRYSTAL, IC2Items.LAPATRON_CRYSTAL, IC2Items.GLOWTRONIC_CRYSTAL, IC2Items.UESC, IC2Items.PESD}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.accumulator", new ItemLike[]{IC2Items.QUANTUM_ACCUMULATOR, IC2Items.QUANTUM_ACCUMULATOR_BIG}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.battery.pack", new ItemLike[]{IC2Items.BAT_PACK, IC2Items.LAP_PACK, IC2Items.QUANTUM_PACK}));
        builder.addSubPages(new ItemLike[]{IC2Items.BATTERY_BELT});
        builder.finishBuildChapter(true);
    }

    public static void addScannerChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "items.scanners");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.items.scanners"), new TextObj("wiki.ic2.page.items.scanners.desc"), new DoublePageEndObj());
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.scanner.od", new ItemLike[]{IC2Items.SCANNER_OD, IC2Items.SCANNER_OD_RANGED, IC2Items.SCANNER_OD_BIG_RANGED}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.scanner.ov", new ItemLike[]{IC2Items.SCANNER_OV, IC2Items.SCANNER_OV_RANGED, IC2Items.SCANNER_OV_BIG_RANGED}));
        builder.addSubPages(new ItemLike[]{IC2Items.SCANNER_QUARRY, IC2Items.SCANNER_FILTERED});
        builder.addSubPages(new ItemLike[]{IC2Items.SCANNER_FLUID, IC2Items.SCANNER_FLUID_FILTERED});
        builder.finishBuildChapter(true);
    }

    public static void addUpgradeChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "items.upgrades");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.items.upgrades"), new TextObj("wiki.ic2.page.items.upgrades.desc"), new PageEndObj(), new PaddingObj(9).setOptional(), new TextObj("wiki.ic2.page.items.upgrades.category"), new DoublePageEndObj());
        builder.addSubChapter("ic2", "items.upgrades.machine");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.items.upgrades.machine"), new TextObj("wiki.ic2.page.items.upgrades.machine.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Items.OVERCLOCKER_UPGRADE, IC2Items.QUANTUM_OVERCLOCKER_UPGRADE, IC2Items.TRANSFORMER_UPGRADE, IC2Items.ENERGY_STORAGE_UPGRADE, IC2Items.EFFICIENCY_UPGRADE, IC2Items.ENERGY_STORAGE_MULTIPLIER_UPGRADE, IC2Items.REDSTONE_INVERTER_UPGRADE, IC2Items.REDSTONE_SENSITIVITY_UPGRADE, IC2Items.EXPORT_UPGRADE_SIMPLE, IC2Items.IMPORT_UPGRADE_SIMPLE, IC2Items.IMPORT_UPGRADE, IC2Items.EXPORT_UPGRADE, IC2Items.FLUID_EXPORT_UPGRADE_SIMPLE, IC2Items.FLUID_IMPORT_UPGRADE_SIMPLE, IC2Items.FLUID_IMPORT_UPGRADE, IC2Items.FLUID_EXPORT_UPGRADE, IC2Items.LOUDNESS_UPGRADE, IC2Items.MUFFLER_UPGRADE, IC2Items.MUTE_UPGRADE, IC2Items.SLOWNESS_UPGRADE, IC2Items.EXP_COLLECTOR_UPGRADE, IC2Items.COBBLEGEN_UPGRADE, IC2Items.CRAFTING_UPGRADE, IC2Items.SAWBLADE_EFFICIENT_UPGRADE, IC2Items.SAWBLADE_DURABLE_UPGRADE, IC2Items.UPGRADE_CONTAINER});
        builder.finishSubChapter();
        builder.addSubChapter("ic2", "items.upgrades.chargepad");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.items.upgrades.chargepad"), new TextObj("wiki.ic2.page.items.upgrades.chargepad.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Items.DAMAGE_PAD_UPGRADE, IC2Items.DRAIN_PAD_UPGRADE, IC2Items.PROXIMITY_PAD_UPGRADE, IC2Items.WIDE_BAND_PAD_UPGRADE, IC2Items.ARMOR_PRIORITY_PAD_UPGRADE});
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.upgrade_field_upgrade", new ItemLike[]{IC2Items.BASIC_FIELD_PAD_UPGRADE, IC2Items.FIELD_PAD_UPGRADE, IC2Items.ADVANCED_FIELD_PAD_UPGRADE}));
        builder.finishSubChapter();
        builder.addSubChapter("ic2", "items.upgrades.inventory");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.items.upgrades.inventory"), new TextObj("wiki.ic2.page.items.upgrades.inventory.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Items.ITEM_LIMITER});
        builder.finishSubChapter();
        builder.addSubChapter("ic2", "items.upgrades.kits");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.items.upgrades.kits"), new TextObj("wiki.ic2.page.items.upgrades.kits.desc"), new DoublePageEndObj());
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.upgrade.kit.storage", new ItemLike[]{IC2Items.MFE_UPGRADE_KIT, IC2Items.MFSU_UPGRADE_KIT, IC2Items.ESU_UPGRADE_KIT, IC2Items.ISU_UPGRADE_KIT, IC2Items.PESU_UPGRADE_KIT}));
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.upgrade.kit.pad", new ItemLike[]{IC2Items.CRYSTAL_UPGRADE_KIT, IC2Items.LAPOTRONIC_UPGRADE_KIT, IC2Items.FISSION_UPGRADE_KIT}));
        builder.finishSubChapter();
        builder.finishBuildChapter(true);
    }

    public static void addReactorComponentChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "items.reactor.components");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.items.reactor.components"), new TextObj("wiki.ic2.page.items.reactor.components.desc"), new DoublePageEndObj());
        builder.addPreviewBookMark("ic2", "heat_vent");
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.VENT_HEAT, IC2Items.VENT_HEAT_CORE, IC2Items.VENT_HEAT_OVERCLOCKED, IC2Items.VENT_HEAT_ADVANCED}));
        builder.addPreviewBookMark("ic2", "steam_vent");
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.VENT_STEAM, IC2Items.VENT_STEAM_CORE, IC2Items.VENT_STEAM_OVERCLOCKED, IC2Items.VENT_STEAM_ADVANCED}));
        builder.addPreviewBookMark("ic2", "electric_vent");
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.VENT_ELECTRIC, IC2Items.VENT_ELECTRIC_CORE, IC2Items.VENT_ELECTRIC_OVERCLOCKED, IC2Items.VENT_ELECTRIC_ADVANCED}));
        builder.addPreviewBookMark("ic2", "heat_exchanger");
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.HEAT_EXCHANGER, IC2Items.HEAT_EXCHANGER_CORE, IC2Items.HEAT_EXCHANGER_SPREAD, IC2Items.HEAT_EXCHANGER_ADVANCED}));
        builder.addPreviewBookMark("ic2", "heat_storage");
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.heat_storage", new ItemLike[]{IC2Items.COOLANT_CELL_10K, IC2Items.COOLANT_CELL_30K, IC2Items.COOLANT_CELL_60K}));
        builder.addPreviewBookMark("ic2", "condensator");
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.CONDENSATOR, IC2Items.CONDENSATOR_LAP}));
        builder.addSubPages(new ItemLike[]{IC2Items.HEAT_PACK});
        builder.addPreviewBookMark("ic2", "plating");
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.PLATING, IC2Items.PLATING_HEAT_CAPACITY, IC2Items.PLATING_CONTAINMENT}));
        builder.addSubPages(new ItemLike[]{IC2Items.HEAT_SPREADER});
        builder.addPreviewBookMark("ic2", "reflector");
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.REFLECTOR, IC2Items.REFLECTOR_THICK, IC2Items.REFLECTOR_IRIDIUM}));
        builder.addSubPages(new ItemLike[]{IC2Items.REACTOR_FUEL_CELL});
        builder.addPreviewBookMark("ic2", "uranium_rods");
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.URANIUM_ROD_SINGLE, IC2Items.URANIUM_ROD_DUAL, IC2Items.URANIUM_ROD_QUAD}));
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.URANIUM_ROD_REDSTONE_SINGLE, IC2Items.URANIUM_ROD_REDSTONE_DUAL, IC2Items.URANIUM_ROD_REDSTONE_QUAD}));
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.URANIUM_ROD_BLAZE_SINGLE, IC2Items.URANIUM_ROD_BLAZE_DUAL, IC2Items.URANIUM_ROD_BLAZE_QUAD}));
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.URANIUM_ROD_ENDER_PEARL_SINGLE, IC2Items.URANIUM_ROD_ENDER_PEARL_DUAL, IC2Items.URANIUM_ROD_ENDER_PEARL_QUAD}));
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.URANIUM_ROD_NETHER_STAR_SINGLE, IC2Items.URANIUM_ROD_NETHER_STAR_DUAL, IC2Items.URANIUM_ROD_NETHER_STAR_QUAD}));
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.URANIUM_ROD_CHARCOAL_SINGLE, IC2Items.URANIUM_ROD_CHARCOAL_DUAL, IC2Items.URANIUM_ROD_CHARCOAL_QUAD}));
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.URANIUM_ROD_NEAR_DEPLETED, IC2Items.URANIUM_ROD_NEAR_DEPLETED_REDSTONE, IC2Items.URANIUM_ROD_NEAR_DEPLETED_BLAZE, IC2Items.URANIUM_ROD_NEAR_DEPLETED_ENDER_PEARL, IC2Items.URANIUM_ROD_NEAR_DEPLETED_NETHER_STAR, IC2Items.URANIUM_ROD_NEAR_DEPLETED_CHARCOAL}));
        builder.addPreviewBookMark("ic2", "isotopic_rod");
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.URANIUM_ROD_ISOTOPIC, IC2Items.URANIUM_ROD_ISOTOPIC_REDSTONE, IC2Items.URANIUM_ROD_ISOTOPIC_BLAZE, IC2Items.URANIUM_ROD_ISOTOPIC_ENDER_PEARL, IC2Items.URANIUM_ROD_ISOTOPIC_NETHER_STAR, IC2Items.URANIUM_ROD_ISOTOPIC_CHARCOAL}));
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.URANIUM_ROD_RE_ENRICHED, IC2Items.URANIUM_ROD_RE_ENRICHED_REDSTONE, IC2Items.URANIUM_ROD_RE_ENRICHED_BLAZE, IC2Items.URANIUM_ROD_RE_ENRICHED_ENDER_PEARL, IC2Items.URANIUM_ROD_RE_ENRICHED_NETHER_STAR, IC2Items.URANIUM_ROD_RE_ENRICHED_CHARCOAL}));
        builder.finishBuildChapter(true);
    }

    public static void addInfoCardChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "items.info.cards");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.items.info.cards"), new TextObj("wiki.ic2.page.items.info.cards.desc"), new DoublePageEndObj());
        builder.addSubPages(new ItemLike[]{IC2Items.MONITOR_CARD, IC2Items.ACTIVITY_CARD, IC2Items.ENERGY_CARD, IC2Items.LAG_CARD, IC2Items.PROGRESS_CARD, IC2Items.REACTOR_CARD, IC2Items.TEXT_CARD, IC2Items.TIME_CARD, IC2Items.DETECTOR_CARD, IC2Items.INV_CARD});
        builder.finishBuildChapter(true);
    }

    public static void addMiscChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "items.misc");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.items.misc"), new TextObj("wiki.ic2.page.items.misc.desc"), new DoublePageEndObj());
        builder.addSimplePage(IC2Wiki.createForItemList("wiki.ic2.preview.rotor", new ItemLike[]{IC2Items.WOODEN_ROTOR, IC2Items.WOOL_ROTOR, IC2Items.IRON_ROTOR, IC2Items.CARBON_ROTOR, IC2Items.MIXED_ALLOY_ROTOR, IC2Items.IRIDIUM_ROTOR}));
        builder.addSubPages(new ItemLike[]{IC2Items.SCRAPBOX});
        builder.addSubPages(new ItemLike[]{IC2Items.TFBP, IC2Items.TFBP_MUSHROOM, IC2Items.TFBP_CULTIVATION, IC2Items.TFBP_CHILLING, IC2Items.TFBP_IRRIGATION, IC2Items.TFBP_DESERTIFICATION, IC2Items.TFBP_FLATIFICATOR});
        builder.addSubPages(new ItemLike[]{IC2Items.TFBP_BIOME, IC2Items.TFBP_BIOME_CULTIVATION, IC2Items.TFBP_BIOME_DESERTIFICATION, IC2Items.TFBP_BIOME_FORESTIFICATION, IC2Items.TFBP_BIOME_IRRIGATION, IC2Items.TFBP_BIOME_MUSHROOM, IC2Items.TFBP_BIOME_UNDERGROWTH, IC2Items.TFBP_BIOME_CHILLING});
        builder.addSimplePage(IC2Wiki.createForItemList(new ItemLike[]{IC2Items.COIN, IC2Items.COIN_8X, IC2Items.COIN_64X, IC2Items.COIN_512X, IC2Items.COIN_4096X, IC2Items.COIN_32768X}));
        builder.addSubPages(new ItemLike[]{IC2Items.FREQUENCY_TRANSMITTER, IC2Items.MINER_REMOTE, IC2Items.MEMORY_STICK, IC2Items.FUEL_CAN});
        builder.finishBuildChapter(true);
    }

    public static void addFluidsChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "fluids");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.fluids"), new TextObj("wiki.ic2.page.fluids.desc"), new PageEndObj());
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.fluids.wood_gas"), new TextObj("wiki.ic2.page.fluids.wood_gas.desc").setCutOff());
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.fluids.biofuel"), new TextObj("wiki.ic2.page.fluids.biofuel.desc").setCutOff());
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.fluids.alcohol"), new TextObj("wiki.ic2.page.fluids.alcohol.desc").setCutOff());
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.fluids.steam"), new TextObj("wiki.ic2.page.fluids.steam.desc").setCutOff());
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.fluids.blazing_lava"), new TextObj("wiki.ic2.page.fluids.blazing_lava.desc").setCutOff());
        builder.finishBuildChapter(true);
    }

    public static void addCropPages(WikiBuilder builder) {
        for (ICrop crop : CropRegistry.INSTANCE.getCrops()) {
            if (crop == CropRegistry.WEED) continue;
            builder.addPage(crop.id(), new HeaderObj(crop.getName()), new CropObj(crop), new TextObj("wiki." + crop.id().m_135827_() + ".preview." + crop.id().m_135815_() + ".desc").setCutOff(), new PageEndObj());
        }
        builder.addBasicChapter(new ItemLike[]{IC2Items.OVERGROWTH_FERTILIZER});
    }

    public static void addCropsChapter(IChapterBuilder builder) {
        builder.startBuildChapter("ic2", "crops");
        builder.addSimplePage(new HeaderObj("wiki.ic2.page.crops"), new TextObj("wiki.ic2.page.crops.desc"));
        builder.addSubPages(new ItemLike[]{IC2Items.FERTILIZER, IC2Items.HYDRATION_CELL, IC2Items.WEEDEX, IC2Items.OVERGROWTH_FERTILIZER, IC2Items.RESTORING_FERTILIZER});
        for (ICrop crop : CropRegistry.INSTANCE.getCrops()) {
            if (crop == CropRegistry.WEED) continue;
            builder.addSubPages(crop.id());
        }
        builder.finishBuildChapter(true);
    }

    private static List<IWikiObj> createForItemList(String name, ItemLike ... provider) {
        return IC2Wiki.createForItemList(name, false, null, provider);
    }

    private static List<IWikiObj> createForItemList(String name, boolean hidden, String link, ItemLike ... provider) {
        ResourceLocation loc;
        ObjectList result = CollectionUtils.createList();
        result.add((PreviewObj)new PreviewObj(provider));
        result.add((HeaderObj)new HeaderObj(name));
        if (!hidden) {
            result.add((CraftObj)new CraftObj(provider));
        } else {
            result.add((CraftObj)new CraftObj(provider).disableHidden());
        }
        result.add((TextObj)new TextObj(name + ".desc").setCutOff());
        if (link != null && (loc = ResourceLocation.m_135820_((String)link)) != null) {
            result.add((LinkObj)new LinkObj(loc));
        }
        return result;
    }

    private static List<IWikiObj> createForItemList(String name, ItemStack ... provider) {
        return IC2Wiki.createForItemList(name, null, provider);
    }

    private static List<IWikiObj> createForItemList(String name, String link, ItemStack ... provider) {
        ResourceLocation loc;
        ObjectList result = CollectionUtils.createList();
        result.add((PreviewObj)new PreviewObj(provider));
        result.add((HeaderObj)new HeaderObj(name));
        result.add((CraftObj)new CraftObj(provider));
        result.add((TextObj)new TextObj(name + ".desc").setCutOff());
        if (link != null && (loc = ResourceLocation.m_135820_((String)link)) != null) {
            result.add((LinkObj)new LinkObj(loc));
        }
        return result;
    }

    private static List<IWikiObj> createForItemList(ItemLike ... provider) {
        ObjectList result = CollectionUtils.createList();
        result.add((PreviewObj)new PreviewObj(provider));
        result.add((HeaderObj)new HeaderObj((Component)provider[0].m_5456_().m_7968_().m_41786_().m_6881_().m_130948_(Style.f_131099_.m_131136_(Boolean.valueOf(true)))));
        result.add((CraftObj)new CraftObj(provider));
        ResourceLocation location = ForgeRegistries.ITEMS.getKey((Object)provider[0].m_5456_());
        result.add((TextObj)new TextObj("wiki." + location.m_135827_() + ".preview." + location.m_135815_() + ".desc").setCutOff());
        result.add((LinkObj)new LinkObj(location.m_135827_(), "subpage." + location.m_135815_()));
        return result;
    }

    private static Component createCredits() {
        MutableComponent comp = Component.m_237119_();
        comp.m_130946_("The Credits Section of the Wiki.\n\nThis is to display the work that people contributed to the Project\nWithout them this would have never started or finished\n\u00a7<newPage>\n\u00a7<header;Daenara;true>\nFor doing a lot of grind work and improving UI design,\ncreating a most of the new crops and the foundation design of OneProbe support,\nadding a lot of the new decorative Blocks,\nproviding a better design for the drink system and adding new variants,\nadding new paintings and keeping everything spellchecked,\nand for getting this port started in the first place!\n\u00a7<newPage>\n\u00a7<header;Meduris;true>\nFor adding Jetpacks, Shields, Armors & Huds, Nuclear Items, Custom Configs.\nProviding tools that saved us hundreds of hours, and ASM code that we needed for special features.\nAnd for taking care of a lot of problems during testing\n\u00a7<newPage>\n\u00a7<header;Razzokk;true>\nFor Creating a lot of beautiful textures and models,\nwhich includes:\nAll Tubes, Pipes, Monitor, Electric Enchanter, Wiki, New Tools and a lot more.\nAnd for helping port from 1.14-1.16.\n\u00a7<newPage>\n\u00a7<header;Gween;true>\nFor Creating a ton of beautiful Textures.\nWhich includes:\nMost of the new crops if not all, Raw ores, most of the new machines,\nMost of the Armor Modules,\nand a lot more.\n\u00a7<newPage>\n\u00a7<header;Xaikii;true>\nFor Helping in a Pinch for doublechecking the Wiki and fixing documentation.\nProviding some Documentation and design input.\n\u00a7<newPage>\n\u00a7<header;Jason;true>\nFor almost singlehandedly redoing IC2Classics art style and redoing a lot of textures.\nHe is a madman!\n\u00a7<newPage>\n\u00a7<header;LuxOperator;true>\nFor Helping with Textures and helping to drive IC2Classic into a better looking style!\n\u00a7<newPage>\n\u00a7<header;Greg;true>\nFor Providing solutions to problems we would never have thought of and providing design ideas.\n\u00a7<newPage>\n\u00a7<header;Lupas Wolf;true>\nFor helping with the documentation and providing valueable information what info is required for new players!\nOn top of testing the entire mod from a beginner perspective.\n\u00a7<newPage>\n\u00a7<header;Dovahkiin0_0;true>\nFor Helping me with the designing of the Fusion Reactor,\ndoing the final Spellcheck on the wiki,\nand in general testing the mod to its final limitations!\n\u00a7<newPage>\n\u00a7<header;PrincessOfEvil;true>\nFor finding exploits without end, providing some reactor component art and in general helping out with extensive testing.\n\u00a7<newPage>\n\u00a7<header;Iridium Crafter;true>\nFor helping to test and for almost driving me insane.\n\u00a7<newPage>\n\u00a7<header;Daywalker;true>\nFor helping test the mod and finding some really obscure bugs, and also producing bugs that didn't actually exist!\n\u00a7<newPage>\n\u00a7<header;Instinx;true>\nFor Providing the test server that made it possible to ensure a really stable release!\n\u00a7<newPage>\n\u00a7<header;Minesasha;true>\nFor Providing input/support/testing in the Early versions of IC2Classic");
        String s = Language.m_128107_().m_6834_("ic2.wiki.translated");
        if (!s.isBlank() && !s.equals("ic2.wiki.translated")) {
            comp.m_130946_("\u00a7<newPage>\n\u00a7<header;wiki.ic2.translated_by;true>\n" + s);
        }
        return comp;
    }

    private static /* synthetic */ void lambda$addTutorials$6(List list, IWikiObj[] T) {
        list.addAll(ObjectArrayList.wrap((Object[])T));
        list.add(new PageEndObj());
    }
}

