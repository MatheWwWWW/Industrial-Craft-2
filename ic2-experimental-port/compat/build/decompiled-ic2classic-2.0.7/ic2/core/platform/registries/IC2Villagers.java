/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.entity.ai.village.poi.PoiType
 *  net.minecraft.world.entity.npc.VillagerProfession
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.event.village.VillagerTradesEvent
 *  net.minecraftforge.event.village.WandererTradesEvent
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.ForgeRegistries$Keys
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.platform.registries;

import com.google.common.collect.ImmutableSet;
import ic2.api.crops.ICrop;
import ic2.api.items.IDrinkableFluid;
import ic2.core.block.crops.CropRegistry;
import ic2.core.item.food_and_drink.IC2Drink;
import ic2.core.item.food_and_drink.IC2FoodsAndDrinks;
import ic2.core.item.food_and_drink.drinks.Beer;
import ic2.core.item.food_and_drink.drinks.Whisky;
import ic2.core.item.reactor.ReactorUraniumRod;
import ic2.core.item.reactor.base.IUraniumRod;
import ic2.core.platform.recipes.villager.ITradeComp;
import ic2.core.platform.recipes.villager.TradeBuilder;
import ic2.core.platform.registries.ColorMaps;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tags;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.GameData;

public class IC2Villagers {
    private static final int FLAG_TEA = 1;
    private static final int FLAG_COFFEE = 2;
    private static final int FLAG_COCA = 4;
    private static final int FLAG_SPECIALS = 8;
    private static final int FLAG_HOT = 16;
    private static final int FLAG_COLD = 32;
    private static final int FLAG_ANY_TEMP = 48;
    private static Map<VillagerProfession, TradeBuilder> BUILDERS = null;
    public static ResourceKey<PoiType> GREG_TYPE;
    public static ResourceKey<PoiType> ELECTRIC_TYPE;
    public static ResourceKey<PoiType> NUCLEAR_TYPE;
    public static ResourceKey<PoiType> CROP_TYPE;
    public static ResourceKey<PoiType> DEMOLISHON_TYPE;
    public static ResourceKey<PoiType> BREWING_MASTER_TYPE;
    public static VillagerProfession GREG;
    public static VillagerProfession ELECTRICAL_ENGINEER;
    public static VillagerProfession NUCLEAR_ENGINEER;
    public static VillagerProfession CROP_FARMER;
    public static VillagerProfession DEMOLISHON_MAN;
    public static VillagerProfession BREWING_MASTER;

    public static VillagerProfession registerProfession(String name, VillagerProfession profession) {
        ResourceLocation id = GameData.checkPrefix((String)name, (boolean)false);
        ForgeRegistries.VILLAGER_PROFESSIONS.register(id, (Object)profession);
        return profession;
    }

    public static ResourceKey<PoiType> registerPointOfInterest(String name, PoiType poi) {
        ResourceLocation id = GameData.checkPrefix((String)name, (boolean)false);
        ForgeRegistries.POI_TYPES.register(id, (Object)poi);
        IC2Tags.registerTags(new ResourceLocation("tags/point_of_interest_type/acquirable_job_site"), ForgeRegistries.POI_TYPES, poi);
        return ResourceKey.m_135785_((ResourceKey)ForgeRegistries.Keys.POI_TYPES, (ResourceLocation)id);
    }

    public static void loadPois() {
        GREG_TYPE = IC2Villagers.registerPointOfInterest("greg", new PoiType(IC2Villagers.getBlockStates(IC2Blocks.PIXELBLOCK_GREG), 2, 2));
        ELECTRIC_TYPE = IC2Villagers.registerPointOfInterest("electric", new PoiType(IC2Villagers.getBlockStates(IC2Blocks.IRON_FURNACE), 2, 2));
        NUCLEAR_TYPE = IC2Villagers.registerPointOfInterest("nuclear", new PoiType(IC2Villagers.getBlockStates(IC2Blocks.NUCLEAR_REACTOR), 2, 2));
        CROP_TYPE = IC2Villagers.registerPointOfInterest("crop", new PoiType(IC2Villagers.getBlockStates(IC2Blocks.SIMPLE_CROP_LIBRARY), 2, 2));
        DEMOLISHON_TYPE = IC2Villagers.registerPointOfInterest("demo", new PoiType(IC2Villagers.getBlockStates(IC2Blocks.ITNT), 2, 2));
        BREWING_MASTER_TYPE = IC2Villagers.registerPointOfInterest("brewing", new PoiType(IC2Villagers.getBlockStates(IC2Blocks.BARREL), 2, 2));
    }

    public static void loadVillagers() {
        GREG = IC2Villagers.registerProfession("greg", new VillagerProfession("greg", T -> T.m_203565_(GREG_TYPE), T -> T.m_203565_(GREG_TYPE), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.f_12575_));
        ELECTRICAL_ENGINEER = IC2Villagers.registerProfession("electric", new VillagerProfession("electric", T -> T.m_203565_(ELECTRIC_TYPE), T -> T.m_203565_(ELECTRIC_TYPE), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.f_12575_));
        NUCLEAR_ENGINEER = IC2Villagers.registerProfession("nuclear", new VillagerProfession("nuclear", T -> T.m_203565_(NUCLEAR_TYPE), T -> T.m_203565_(NUCLEAR_TYPE), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.f_12575_));
        CROP_FARMER = IC2Villagers.registerProfession("crop", new VillagerProfession("crop", T -> T.m_203565_(CROP_TYPE), T -> T.m_203565_(CROP_TYPE), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.f_12575_));
        DEMOLISHON_MAN = IC2Villagers.registerProfession("demo", new VillagerProfession("demo", T -> T.m_203565_(DEMOLISHON_TYPE), T -> T.m_203565_(DEMOLISHON_TYPE), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.f_12575_));
        BREWING_MASTER = IC2Villagers.registerProfession("brewing", new VillagerProfession("brewing", T -> T.m_203565_(BREWING_MASTER_TYPE), T -> T.m_203565_(BREWING_MASTER_TYPE), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.f_12575_));
    }

    public static void onVillagerEvent(VillagerTradesEvent event) {
        IC2Villagers.loadTrades();
        TradeBuilder builder = BUILDERS.get(event.getType());
        if (builder != null) {
            builder.buildVillager(event);
        }
    }

    public static void onWanderingEvent(WandererTradesEvent event) {
        TradeBuilder builder = new TradeBuilder();
        for (IUraniumRod uranium : ReactorUraniumRod.TYPES) {
            builder.addTrade(0, ITradeComp.emeralds(5, 10, ITradeComp.Target.MAIN), ITradeComp.item(uranium.getBaseIngot(), 2, 5, ITradeComp.Target.OUT));
        }
        builder.buildWanderingTrade(event, true);
    }

    public static void loadTrades() {
        if (BUILDERS != null) {
            return;
        }
        Object2ObjectSortedMap<VillagerProfession, TradeBuilder> trades = CollectionUtils.createLinkedMap();
        trades.put(GREG, IC2Villagers.createGreg());
        trades.put(DEMOLISHON_MAN, IC2Villagers.createDemolishonMan());
        trades.put(ELECTRICAL_ENGINEER, IC2Villagers.createElectricEngineer());
        trades.put(NUCLEAR_ENGINEER, IC2Villagers.createNuclearEngineer());
        trades.put(CROP_FARMER, IC2Villagers.createCrop());
        trades.put(BREWING_MASTER, IC2Villagers.createBrewing());
        trades.put(VillagerProfession.f_35586_, IC2Villagers.createDrinkTrades(18));
        trades.put(VillagerProfession.f_35587_, IC2Villagers.createDrinkTrades(52));
        trades.put(VillagerProfession.f_35588_, IC2Villagers.createDrinkTrades(51));
        trades.put(VillagerProfession.f_35589_, IC2Villagers.createDrinkTrades(33));
        trades.put(VillagerProfession.f_35590_, IC2Villagers.createDrinkTrades(56));
        trades.put(VillagerProfession.f_35591_, IC2Villagers.createDrinkTrades(18));
        trades.put(VillagerProfession.f_35592_, IC2Villagers.createDrinkTrades(17));
        trades.put(VillagerProfession.f_35593_, IC2Villagers.createDrinkTrades(41));
        trades.put(VillagerProfession.f_35594_, IC2Villagers.createDrinkTrades(49));
        trades.put(VillagerProfession.f_35595_, IC2Villagers.createDrinkTrades(36));
        trades.put(VillagerProfession.f_35597_, IC2Villagers.createDrinkTrades(23));
        trades.put(VillagerProfession.f_35598_, IC2Villagers.createDrinkTrades(36));
        trades.put(VillagerProfession.f_35599_, IC2Villagers.createDrinkTrades(33));
        BUILDERS = trades;
    }

    private static TradeBuilder createDrinkTrades(int flags) {
        TradeBuilder builder = new TradeBuilder();
        for (IC2Drink container : IC2FoodsAndDrinks.CONTAINERS) {
            Map<IDrinkableFluid, Item> filled = container.getFilledContainers();
            if ((flags & 1) != 0) {
                if ((flags & 0x10) != 0) {
                    builder.addTrade(0, ITradeComp.item((ItemLike)filled.get(IC2FoodsAndDrinks.TEA), 9, 25, ITradeComp.Target.MAIN), ITradeComp.emeralds(1, 3, ITradeComp.Target.OUT));
                    builder.addTrade(0, ITradeComp.item((ItemLike)filled.get(IC2FoodsAndDrinks.GREEN_TEA), 9, 25, ITradeComp.Target.MAIN), ITradeComp.emeralds(1, 3, ITradeComp.Target.OUT));
                    builder.addTrade(2, ITradeComp.item((ItemLike)filled.get(IC2FoodsAndDrinks.BLACK_TEA), 9, 25, ITradeComp.Target.MAIN), ITradeComp.emeralds(1, 3, ITradeComp.Target.OUT));
                    builder.addTrade(4, ITradeComp.item((ItemLike)filled.get(IC2FoodsAndDrinks.FRUIT_TEA), 9, 25, ITradeComp.Target.MAIN), ITradeComp.emeralds(1, 3, ITradeComp.Target.OUT));
                }
                if ((flags & 0x20) != 0) {
                    builder.addTrade(0, ITradeComp.item((ItemLike)filled.get(IC2FoodsAndDrinks.COLD_TEA), 9, 25, ITradeComp.Target.MAIN), ITradeComp.emeralds(1, ITradeComp.Target.OUT));
                    builder.addTrade(0, ITradeComp.item((ItemLike)filled.get(IC2FoodsAndDrinks.COLD_GREEN_TEA), 9, 25, ITradeComp.Target.MAIN), ITradeComp.emeralds(1, ITradeComp.Target.OUT));
                    builder.addTrade(2, ITradeComp.item((ItemLike)filled.get(IC2FoodsAndDrinks.COLD_BLACK_TEA), 9, 25, ITradeComp.Target.MAIN), ITradeComp.emeralds(1, ITradeComp.Target.OUT));
                    builder.addTrade(4, ITradeComp.item((ItemLike)filled.get(IC2FoodsAndDrinks.COLD_FRUIT_TEA), 9, 25, ITradeComp.Target.MAIN), ITradeComp.emeralds(1, ITradeComp.Target.OUT));
                }
            }
            if ((flags & 0x12) != 0) {
                builder.addTrade(5, ITradeComp.item((ItemLike)filled.get(IC2FoodsAndDrinks.COFFEE), 4, 15, ITradeComp.Target.MAIN), ITradeComp.emeralds(3, 7, ITradeComp.Target.OUT));
                builder.addTrade(5, ITradeComp.item((ItemLike)filled.get(IC2FoodsAndDrinks.DARK_COFFEE), 4, 15, ITradeComp.Target.MAIN), ITradeComp.emeralds(3, 7, ITradeComp.Target.OUT));
            }
            if ((flags & 4) != 0) {
                if ((flags & 0x10) != 0) {
                    builder.addTrade(3, ITradeComp.item((ItemLike)filled.get(IC2FoodsAndDrinks.HOT_COCOA), 16, 64, ITradeComp.Target.MAIN), ITradeComp.emeralds(3, 9, ITradeComp.Target.OUT));
                }
                if ((flags & 0x20) != 0) {
                    builder.addTrade(3, ITradeComp.item((ItemLike)filled.get(IC2FoodsAndDrinks.COCOA), 16, 64, ITradeComp.Target.MAIN), ITradeComp.emeralds(3, 9, ITradeComp.Target.OUT));
                }
            }
            if ((flags & 8) == 0) continue;
            if ((flags & 0x20) != 0) {
                builder.addTrade(5, ITradeComp.item((ItemLike)filled.get(IC2FoodsAndDrinks.ICED_TEA), 16, 64, ITradeComp.Target.MAIN), ITradeComp.emeralds(3, 9, ITradeComp.Target.OUT));
            }
            builder.addTrade(0, ITradeComp.item((ItemLike)filled.get(IC2FoodsAndDrinks.MILK), 8, 20, ITradeComp.Target.MAIN), ITradeComp.emeralds(1, ITradeComp.Target.OUT));
        }
        return builder;
    }

    private static TradeBuilder createBrewing() {
        int i;
        TradeBuilder brewing = new TradeBuilder();
        for (i = 1; i < 5; ++i) {
            int minEmeralds = (int)Math.pow(1.0, i);
            for (int x = 0; x < 6; ++x) {
                for (int y = 0; y < 6; ++y) {
                    brewing.addTrade(i, ITradeComp.item(Beer.createData(new ItemStack((ItemLike)IC2Items.MUG_BEER), i, x, y), 16, ITradeComp.Target.MAIN), ITradeComp.emeralds(minEmeralds, minEmeralds + 10, ITradeComp.Target.OUT));
                    brewing.addTrade(i, ITradeComp.item(Beer.createData(new ItemStack((ItemLike)IC2Items.GLASS_BEER), i, x, y), 16, ITradeComp.Target.MAIN), ITradeComp.emeralds(minEmeralds, minEmeralds + 10, ITradeComp.Target.OUT));
                }
            }
        }
        for (i = 1; i < 6; ++i) {
            brewing.addTrade(5, ITradeComp.item(Whisky.createYear(new ItemStack((ItemLike)IC2Items.GLASS_WHISKY, 16), i), ITradeComp.Target.MAIN), ITradeComp.emeralds(Whisky.YEAR_NUMBERS[i], Whisky.YEAR_NUMBERS[i] + 10, ITradeComp.Target.OUT));
        }
        return brewing;
    }

    private static TradeBuilder createCrop() {
        TradeBuilder crop = new TradeBuilder();
        for (ICrop seed : CropRegistry.INSTANCE.getCrops()) {
            int tier;
            if (seed == CropRegistry.WEED || seed == CropRegistry.SEA_WEED || (tier = seed.getProperties().getTier() / 2) < 0 || tier > 4) continue;
            crop.addTrade(tier, ITradeComp.emeralds(3, tier + 5, ITradeComp.Target.MAIN), ITradeComp.crop(seed, 0, 8, true, ITradeComp.Target.OUT));
            crop.addTrade(tier, ITradeComp.emeralds(5, tier + 8, ITradeComp.Target.MAIN), ITradeComp.crop(seed, 5, 12, false, ITradeComp.Target.OUT));
        }
        crop.addTrade(1, ITradeComp.emeralds(1, 4, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.FERTILIZER, 4, 10, ITradeComp.Target.OUT));
        crop.addTrade(1, ITradeComp.emeralds(1, 4, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.GRIN_POWDER, 4, 10, ITradeComp.Target.OUT));
        crop.addTrade(5, ITradeComp.emeralds(5, 20, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.OVERGROWTH_FERTILIZER, 10, ITradeComp.Target.OUT));
        return crop;
    }

    private static TradeBuilder createNuclearEngineer() {
        TradeBuilder nuclear = new TradeBuilder();
        for (IUraniumRod rod : ReactorUraniumRod.TYPES) {
            if (!rod.isEnrichedUranium()) continue;
            nuclear.addTrade(3, 2, ITradeComp.emeralds(10, 25, ITradeComp.Target.MAIN), ITradeComp.item(rod.getBaseIngot(), ITradeComp.Target.OUT));
            nuclear.addTrade(4, 2, ITradeComp.emeralds(15, 30, ITradeComp.Target.MAIN), ITradeComp.item(rod.getBaseIngot(), 2, ITradeComp.Target.OUT));
        }
        nuclear.addTrade(1, ITradeComp.emeralds(8, 15, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.PLATE_DENSE_COPPER, ITradeComp.Target.OUT));
        nuclear.addTrade(1, ITradeComp.emeralds(1, 3, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.COOLANT_CELL_10K, ITradeComp.Target.OUT));
        nuclear.addTrade(1, ITradeComp.emeralds(1, 3, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.HEAT_EXCHANGER, ITradeComp.Target.OUT));
        nuclear.addTrade(1, ITradeComp.emeralds(1, 3, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.VENT_HEAT, ITradeComp.Target.OUT));
        nuclear.addTrade(1, ITradeComp.emeralds(1, 3, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.VENT_STEAM, ITradeComp.Target.OUT));
        nuclear.addTrade(5, ITradeComp.emeralds(25, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Blocks.REACTOR_PLANNER, ITradeComp.Target.OUT));
        return nuclear;
    }

    private static TradeBuilder createElectricEngineer() {
        TradeBuilder electric = new TradeBuilder();
        electric.addTrade(1, ITradeComp.emeralds(1, 3, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.CIRCUIT, 1, 5, ITradeComp.Target.OUT));
        electric.addTrade(1, ITradeComp.emeralds(5, 10, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Blocks.MACHINE_BLOCK, 2, 5, ITradeComp.Target.OUT));
        electric.addTrade(3, ITradeComp.emeralds(3, 6, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.CIRCUIT, 5, ITradeComp.Target.SUB), ITradeComp.item((ItemLike)IC2Items.ADVANCED_CIRCUIT, 2, 4, ITradeComp.Target.OUT));
        electric.addTrade(4, 2, ITradeComp.emeralds(8, 10, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Blocks.SOLAR_PANEL_COMPRESSED, ITradeComp.Target.OUT));
        electric.addTrade(5, ITradeComp.emeralds(5, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.COMPLEX_CIRCUIT, ITradeComp.Target.OUT));
        return electric;
    }

    private static TradeBuilder createDemolishonMan() {
        TradeBuilder demo = new TradeBuilder();
        demo.addTrade(1, ITradeComp.emeralds(1, ITradeComp.Target.OUT), ITradeComp.item((ItemLike)Items.f_41996_, 2, 5, ITradeComp.Target.MAIN));
        demo.addTrade(1, ITradeComp.emeralds(2, 5, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.SCRAP_METAL, 3, 5, ITradeComp.Target.OUT));
        demo.addTrade(2, ITradeComp.emeralds(2, ITradeComp.Target.OUT), ITradeComp.item((ItemLike)IC2Blocks.ITNT, 2, 5, ITradeComp.Target.MAIN));
        demo.addTrade(3, 2, ITradeComp.emeralds(3, 5, ITradeComp.Target.OUT), ITradeComp.item((ItemLike)IC2Blocks.NUKE, 1, 2, ITradeComp.Target.MAIN));
        demo.addTrade(5, ITradeComp.emeralds(1, 2, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)Items.f_42403_, 8, 16, ITradeComp.Target.OUT));
        return demo;
    }

    private static TradeBuilder createGreg() {
        TradeBuilder greg = new TradeBuilder();
        ItemStack jetpack = new ItemStack((ItemLike)IC2Items.JETPACK_ELECTRIC);
        jetpack.m_41784_().m_128379_("SpecialMode", true);
        greg.addTrade(1, ITradeComp.emeralds(1, ITradeComp.Target.MAIN), ITradeComp.item(jetpack, ITradeComp.Target.OUT));
        ItemStack nuclearJetpack = new ItemStack((ItemLike)IC2Items.JETPACK_NUCLEAR);
        nuclearJetpack.m_41784_().m_128379_("SpecialMode", true);
        greg.addTrade(1, ITradeComp.emeralds(1, ITradeComp.Target.MAIN), ITradeComp.item(nuclearJetpack, ITradeComp.Target.OUT));
        for (Block block : ColorMaps.CFOAM_BLOCKS.getBlocks()) {
            greg.addTrades(new int[]{1, 2}, ITradeComp.emeralds(1, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)block, 12, 24, ITradeComp.Target.OUT));
            greg.addTrade(3, ITradeComp.item((ItemLike)block, 20, 32, ITradeComp.Target.MAIN), ITradeComp.emeralds(1, ITradeComp.Target.OUT));
        }
        greg.addTrade(4, ITradeComp.emeralds(5, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.EU_READER, ITradeComp.Target.OUT));
        greg.addTrade(4, ITradeComp.emeralds(10, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.JETPACK_ELECTRIC, ITradeComp.Target.OUT));
        greg.addTrade(4, ITradeComp.emeralds(10, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.CF_SPRAYER, ITradeComp.Target.OUT));
        greg.addTrade(5, ITradeComp.item((ItemLike)IC2Items.UUMATTER, ITradeComp.Target.MAIN), ITradeComp.emeralds(1, ITradeComp.Target.OUT));
        greg.addTrade(5, ITradeComp.emeralds(16, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.DRILL_ROCK_CUTTER, ITradeComp.Target.OUT));
        greg.addTrade(5, ITradeComp.emeralds(16, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.DRILL_DIAMOND, ITradeComp.Target.OUT));
        greg.addTrade(5, ITradeComp.emeralds(16, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.CHAINSAW, ITradeComp.Target.OUT));
        greg.addTrade(5, ITradeComp.emeralds(16, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.MOWING_TOOL, ITradeComp.Target.OUT));
        greg.addTrade(5, ITradeComp.emeralds(16, ITradeComp.Target.MAIN), ITradeComp.item((ItemLike)IC2Items.ELECTRIC_WRENCH, ITradeComp.Target.OUT));
        return greg;
    }

    public static Set<BlockState> getBlockStates(Block ... blocks) {
        if (blocks.length <= 0) {
            return ImmutableSet.of();
        }
        if (blocks.length == 1) {
            return ImmutableSet.copyOf((Collection)blocks[0].m_49965_().m_61056_());
        }
        ObjectSet set = CollectionUtils.createSet();
        for (Block block : blocks) {
            set.addAll(block.m_49965_().m_61056_());
        }
        return ImmutableSet.copyOf(set);
    }
}

