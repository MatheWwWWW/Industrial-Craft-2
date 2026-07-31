/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.Util
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.core.Holder
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.BiomeTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.common.Tags$Biomes
 *  net.minecraftforge.eventbus.api.Event
 */
package ic2.core.block.crops;

import ic2.api.crops.BaseSeed;
import ic2.api.crops.CropProperties;
import ic2.api.crops.ICrop;
import ic2.api.crops.ICropRegistry;
import ic2.api.crops.ICropRenderer;
import ic2.api.crops.ICropSeed;
import ic2.api.crops.IFarmland;
import ic2.api.crops.ISubSoil;
import ic2.core.IC2;
import ic2.core.block.crops.crops.BambooCrop;
import ic2.core.block.crops.crops.BaseCrop;
import ic2.core.block.crops.crops.BeetRootCrop;
import ic2.core.block.crops.crops.BerryBushCrop;
import ic2.core.block.crops.crops.BlueWheatCrop;
import ic2.core.block.crops.crops.BoneFlowerCrop;
import ic2.core.block.crops.crops.CactusCrop;
import ic2.core.block.crops.crops.CocoaCrop;
import ic2.core.block.crops.crops.CoffeeCrop;
import ic2.core.block.crops.crops.CoralCrop;
import ic2.core.block.crops.crops.DyeFlowerCrop;
import ic2.core.block.crops.crops.FungusCrop;
import ic2.core.block.crops.crops.GlowBerryCrop;
import ic2.core.block.crops.crops.GlowshroomCrop;
import ic2.core.block.crops.crops.HempCrop;
import ic2.core.block.crops.crops.HopsCrop;
import ic2.core.block.crops.crops.InkbergineCrop;
import ic2.core.block.crops.crops.KelpCrop;
import ic2.core.block.crops.crops.LumiliaCrop;
import ic2.core.block.crops.crops.MelonCrop;
import ic2.core.block.crops.crops.MushroomCrop;
import ic2.core.block.crops.crops.NetherWartCrop;
import ic2.core.block.crops.crops.OreCrop;
import ic2.core.block.crops.crops.PumpkinCrop;
import ic2.core.block.crops.crops.RainbowFlowerCrop;
import ic2.core.block.crops.crops.RedWheatCrop;
import ic2.core.block.crops.crops.SaplingCrop;
import ic2.core.block.crops.crops.SeaGrassCrop;
import ic2.core.block.crops.crops.SeaPickleCrop;
import ic2.core.block.crops.crops.StickReedCrop;
import ic2.core.block.crops.crops.SugarCaneCrop;
import ic2.core.block.crops.crops.TeaCrop;
import ic2.core.block.crops.crops.TerraWartCrop;
import ic2.core.block.crops.crops.VegetableCrop;
import ic2.core.block.crops.crops.VenomiliaCrop;
import ic2.core.block.crops.crops.WeedCrop;
import ic2.core.block.crops.crops.WheatCrop;
import ic2.core.block.crops.renderer.DefaultCropRenderer;
import ic2.core.block.crops.renderer.StickRenderer;
import ic2.core.block.crops.soils.BaseFarmland;
import ic2.core.block.crops.soils.BaseSubSoil;
import ic2.core.block.crops.soils.Farmland;
import ic2.core.block.crops.soils.Mangrowth;
import ic2.core.block.crops.soils.PlanterPotFarmland;
import ic2.core.block.rendering.block.CropEntry;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.QuadBaker;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import net.minecraft.Util;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.Event;

public class CropRegistry
extends ICropRegistry {
    public static final CropRegistry REGISTRY = new CropRegistry();
    Map<ResourceLocation, ICrop> crops = CollectionUtils.createLinkedMap();
    Map<ResourceLocation, ItemStack> displayItems = CollectionUtils.createLinkedMap();
    Map<ResourceLocation, ICropRenderer> customRenders = CollectionUtils.createLinkedMap();
    Object2IntMap<TagKey<Biome>> humidity = new Object2IntOpenHashMap();
    Object2IntMap<TagKey<Biome>> nutrients = new Object2IntOpenHashMap();
    Object2IntMap<TagKey<Biome>> water = new Object2IntOpenHashMap();
    Map<Block, IFarmland> farmlands = CollectionUtils.createMap();
    Map<Block, ISubSoil> soils = CollectionUtils.createMap();
    Map<Item, BaseSeed> seeds = CollectionUtils.createMap();
    Map<ResourceLocation, ItemStack> cropSeeds = CollectionUtils.createMap();
    ICropRenderer stickRenderer = null;
    public static final ICrop GLOWSHROOM = new GlowshroomCrop();
    public static final ICrop NETHERWART = new NetherWartCrop();
    public static final ICrop TERRAWART = new TerraWartCrop();
    public static final ICrop SUGARCANE = new SugarCaneCrop();
    public static final ICrop STICKREED = new StickReedCrop();
    public static final ICrop BAMBOO = new BambooCrop();
    public static final ICrop WHEAT = new WheatCrop();
    public static final ICrop HOPS = new HopsCrop();
    public static final ICrop BLUEWHEAT = new BlueWheatCrop();
    public static final ICrop REDWHEAT = new RedWheatCrop();
    public static final ICrop ALLIUM = new DyeFlowerCrop("allium", DyeColor.MAGENTA, BaseCrop.MOJANG, "Magenta");
    public static final ICrop BLACKTHORN = new DyeFlowerCrop("blackthorn", DyeColor.BLACK, BaseCrop.ALBLAKA, "Black", "Rose");
    public static final ICrop BLUE_ORCHID = new DyeFlowerCrop("blue_orchid", DyeColor.LIGHT_BLUE, BaseCrop.MOJANG, "Blue");
    public static final ICrop CORNFLOWER = new DyeFlowerCrop("cornflower", DyeColor.BLUE, BaseCrop.ALBLAKA, "Blue");
    public static final ICrop DANDELION = new DyeFlowerCrop("dandelion", DyeColor.YELLOW, BaseCrop.NOTCH, "Yellow");
    public static final ICrop ORANGE_TULIP = new DyeFlowerCrop("orange_tulip", DyeColor.ORANGE, BaseCrop.MOJANG, "Orange", "Tulip");
    public static final ICrop OXEYE_DAISY = new DyeFlowerCrop("oxeye_daisy", DyeColor.WHITE, BaseCrop.RAZZOKK, "White", "Rose");
    public static final ICrop PINK_TULIP = new DyeFlowerCrop("pink_tulip", DyeColor.PINK, BaseCrop.MOJANG, "Pink", "Tulip");
    public static final ICrop POPPY = new DyeFlowerCrop("poppy", DyeColor.RED, BaseCrop.NOTCH, "Red", "Rose");
    public static final ICrop PURPLE_TULIP = new DyeFlowerCrop("purple_tulip", DyeColor.PURPLE, BaseCrop.ALBLAKA, "Purple", "Tulip");
    public static final ICrop AZURE_BLUET = new DyeFlowerCrop("azure_bluet", DyeColor.LIGHT_GRAY, BaseCrop.MOJANG, "Gray");
    public static final ICrop LILY_OF_THE_VALLEY = new DyeFlowerCrop("lily_of_the_valley", DyeColor.LIGHT_GRAY, BaseCrop.MOJANG, "Gray");
    public static final ICrop FORGET_ME_NOT = new DyeFlowerCrop("forget_me_not", DyeColor.CYAN, BaseCrop.NANO_HEART, "Cyan");
    public static final ICrop RED_TULIP = new DyeFlowerCrop("red_tulip", DyeColor.RED, BaseCrop.MOJANG, "Red", "Tulip");
    public static final ICrop WHITE_TULIP = new DyeFlowerCrop("white_tulip", DyeColor.LIGHT_GRAY, BaseCrop.MOJANG, "White", "Tulip");
    public static final ICrop VENOMILIA = new VenomiliaCrop();
    public static final ICrop RAINBOW_FLOWER = new RainbowFlowerCrop();
    public static ICrop BRAIN_CORAL;
    public static ICrop BUBBLE_CORAL;
    public static ICrop FIRE_CORAL;
    public static ICrop HORN_CORAL;
    public static ICrop TUBE_CORAL;
    public static final ICrop SEA_PICKLE;
    public static final ICrop KELP;
    public static final ICrop BONE_FLOWER;
    public static final ICrop INKBERGINE;
    public static final ICrop ACACIA_SAPLING;
    public static final ICrop BIRCH_SAPLING;
    public static final ICrop DARK_OAK_SAPLING;
    public static final ICrop OAK_SAPLING;
    public static final ICrop JUNGLE_SAPLING;
    public static final ICrop SPRUCE_SAPLING;
    public static ICrop ADAMANTUM;
    public static ICrop ARGENTUM;
    public static ICrop AURELIA;
    public static ICrop FERRU;
    public static ICrop MALACHITE;
    public static ICrop STANNUM;
    public static ICrop ALUMEN;
    public static final ICrop COFFEE;
    public static final ICrop LUMILIA;
    public static final ICrop CACTUS;
    public static ICrop BEETROOT;
    public static ICrop CARROT;
    public static ICrop POTATO;
    public static final ICrop RED_MUSHROOM;
    public static final ICrop BROWN_MUSHROOM;
    public static final ICrop CRIMSON_FUNGUS;
    public static final ICrop WARPED_FUNGUS;
    public static final ICrop HEMP;
    public static final ICrop TEA;
    public static final ICrop COCOA;
    public static final ICrop MELON;
    public static final ICrop PUMPKIN;
    public static final ICrop SWEET_BERRY_BUSH;
    public static final ICrop GLOW_BERRY;
    private long globalSeed = RandomSource.m_216327_().m_188505_();
    private int tickDelay = 0;

    public CropRegistry() {
        INSTANCE = this;
    }

    public void tickSeed() {
        if (this.tickDelay++ >= 3) {
            this.tickDelay = 0;
            this.globalSeed = RandomSource.m_216327_().m_188505_();
        }
    }

    public void preInit() {
    }

    public void register() {
        this.registerCrop(GLOWSHROOM);
        this.registerCrop(NETHERWART);
        this.registerBaseSeed(Items.f_42588_, new BaseSeed(NETHERWART, 1, 1, 1, 1, 1));
        this.registerCrop(TERRAWART);
        this.registerBaseSeed(IC2Items.TERRA_WART, new BaseSeed(TERRAWART, 1, 1, 1, 1, 1));
        this.registerCrop(SUGARCANE);
        this.registerBaseSeed(Items.f_41909_, new BaseSeed(SUGARCANE, 1, 3, 0, 2, 1));
        this.registerCrop(STICKREED);
        this.registerCrop(BAMBOO);
        this.registerBaseSeed(Items.f_41911_, new BaseSeed(BAMBOO, 1, 1, 1, 1, 1));
        this.registerCrop(WHEAT);
        this.registerBaseSeed(Items.f_42404_, new BaseSeed(WHEAT, 1, 1, 1, 1, 1));
        this.registerCrop(HOPS);
        this.registerCrop(BLUEWHEAT);
        this.registerCrop(REDWHEAT);
        this.registerCrop(ALLIUM);
        this.registerBaseSeed(Items.f_41942_, new BaseSeed(ALLIUM, 4, 1, 1, 1, 4));
        this.registerCrop(BLACKTHORN);
        this.registerCrop(BLUE_ORCHID);
        this.registerBaseSeed(Items.f_41941_, new BaseSeed(BLUE_ORCHID, 4, 1, 1, 1, 4));
        this.registerCrop(CORNFLOWER);
        this.registerBaseSeed(Items.f_41949_, new BaseSeed(CORNFLOWER, 4, 1, 1, 1, 4));
        this.registerCrop(DANDELION);
        this.registerBaseSeed(Items.f_41939_, new BaseSeed(DANDELION, 4, 1, 1, 1, 4));
        this.registerCrop(ORANGE_TULIP);
        this.registerBaseSeed(Items.f_41945_, new BaseSeed(ORANGE_TULIP, 4, 1, 1, 1, 4));
        this.registerCrop(OXEYE_DAISY);
        this.registerBaseSeed(Items.f_41948_, new BaseSeed(OXEYE_DAISY, 4, 1, 1, 1, 4));
        this.registerCrop(PINK_TULIP);
        this.registerBaseSeed(Items.f_41947_, new BaseSeed(PINK_TULIP, 4, 1, 1, 1, 4));
        this.registerCrop(POPPY);
        this.registerBaseSeed(Items.f_41940_, new BaseSeed(POPPY, 4, 1, 1, 1, 4));
        this.registerCrop(RAINBOW_FLOWER);
        this.registerCrop(RED_TULIP);
        this.registerBaseSeed(Items.f_41944_, new BaseSeed(RED_TULIP, 4, 1, 1, 1, 4));
        this.registerCrop(PURPLE_TULIP);
        this.registerCrop(VENOMILIA);
        this.registerCrop(WHITE_TULIP);
        this.registerBaseSeed(Items.f_41946_, new BaseSeed(WHITE_TULIP, 4, 1, 1, 1, 4));
        this.registerCrop(AZURE_BLUET);
        this.registerBaseSeed(Items.f_41943_, new BaseSeed(AZURE_BLUET, 4, 1, 1, 1, 4));
        this.registerCrop(LILY_OF_THE_VALLEY);
        this.registerBaseSeed(Items.f_41950_, new BaseSeed(LILY_OF_THE_VALLEY, 4, 1, 1, 1, 4));
        this.registerCrop(FORGET_ME_NOT);
        BRAIN_CORAL = new CoralCrop("brain", Items.f_42291_, Items.f_42301_, Items.f_42286_, "Pink");
        BUBBLE_CORAL = new CoralCrop("bubble", Items.f_42292_, Items.f_42302_, Items.f_42287_, "Magenta");
        FIRE_CORAL = new CoralCrop("fire", Items.f_42293_, Items.f_42356_, Items.f_42288_, "Red");
        HORN_CORAL = new CoralCrop("horn", Items.f_42294_, Items.f_42357_, Items.f_42289_, "Yellow");
        TUBE_CORAL = new CoralCrop("tube", Items.f_42290_, Items.f_42300_, Items.f_42285_, "Blue");
        this.registerCrop(BRAIN_CORAL);
        this.registerBaseSeed(Items.f_42291_, new BaseSeed(BRAIN_CORAL, 3, 1, 1, 1, 1));
        this.registerCrop(BUBBLE_CORAL);
        this.registerBaseSeed(Items.f_42292_, new BaseSeed(BUBBLE_CORAL, 3, 1, 1, 1, 1));
        this.registerCrop(FIRE_CORAL);
        this.registerBaseSeed(Items.f_42293_, new BaseSeed(FIRE_CORAL, 3, 1, 1, 1, 1));
        this.registerCrop(HORN_CORAL);
        this.registerBaseSeed(Items.f_42294_, new BaseSeed(HORN_CORAL, 3, 1, 1, 1, 1));
        this.registerCrop(TUBE_CORAL);
        this.registerBaseSeed(Items.f_42290_, new BaseSeed(TUBE_CORAL, 3, 1, 1, 1, 1));
        SEA_WEED = new SeaGrassCrop();
        this.registerCrop(SEA_PICKLE);
        this.registerBaseSeed(Items.f_41868_, new BaseSeed(SEA_PICKLE, 4, 1, 1, 1, 4));
        this.registerCrop(KELP);
        this.registerBaseSeed(Items.f_41910_, new BaseSeed(KELP, 1, 1, 1, 1, 1));
        this.registerCrop(SEA_WEED);
        this.registerCrop(BONE_FLOWER);
        this.registerCrop(INKBERGINE);
        this.registerCrop(ACACIA_SAPLING);
        this.registerBaseSeed(Items.f_41827_, new BaseSeed(ACACIA_SAPLING, 1, 1, 1, 1, 1));
        this.registerCrop(BIRCH_SAPLING);
        this.registerBaseSeed(Items.f_42801_, new BaseSeed(BIRCH_SAPLING, 1, 1, 1, 1, 1));
        this.registerCrop(DARK_OAK_SAPLING);
        this.registerBaseSeed(Items.f_41828_, new BaseSeed(DARK_OAK_SAPLING, 1, 1, 1, 1, 1));
        this.registerCrop(OAK_SAPLING);
        this.registerBaseSeed(Items.f_42799_, new BaseSeed(OAK_SAPLING, 1, 1, 1, 1, 1));
        this.registerCrop(JUNGLE_SAPLING);
        this.registerBaseSeed(Items.f_41826_, new BaseSeed(JUNGLE_SAPLING, 1, 1, 1, 1, 1));
        this.registerCrop(SPRUCE_SAPLING);
        this.registerBaseSeed(Items.f_42800_, new BaseSeed(SPRUCE_SAPLING, 1, 1, 1, 1, 1));
        ADAMANTUM = new OreCrop("adamantum", new CropProperties(10, 2, 0, 0, 2, 0), new Block[]{Blocks.f_50089_, Blocks.f_152474_, Blocks.f_50090_}, IC2Items.DUST_DIAMOND, BaseCrop.DAENARA, "Cyan");
        ARGENTUM = new OreCrop("argentum", new CropProperties(10, 2, 0, 0, 2, 0), new Block[]{IC2Blocks.SILVER_ORE, IC2Blocks.DEEPSLATE_SILVER_ORE, IC2Blocks.SILVER_BLOCK}, IC2Items.DUST_SILVER, BaseCrop.SPEIGER, "Silver");
        AURELIA = new OreCrop("aurelia", new CropProperties(10, 2, 0, 0, 2, 0), new Block[]{Blocks.f_49995_, Blocks.f_152467_, Blocks.f_50074_}, IC2Items.DUST_GOLD, BaseCrop.ALBLAKA, "Gold");
        FERRU = new OreCrop("ferru", new CropProperties(6, 2, 0, 0, 1, 0), new Block[]{Blocks.f_49996_, Blocks.f_152468_, Blocks.f_50075_}, IC2Items.DUST_IRON, BaseCrop.ALBLAKA, "Gray");
        MALACHITE = new OreCrop("malachite", new CropProperties(6, 2, 0, 0, 1, 0), this.createCopperArray(), IC2Items.DUST_COPPER, BaseCrop.SPEIGER, "Orange");
        STANNUM = new OreCrop("stannum", new CropProperties(6, 2, 0, 0, 1, 0), new Block[]{IC2Blocks.TIN_ORE, IC2Blocks.DEEPSLATE_TIN_ORE, IC2Blocks.TIN_BLOCK}, IC2Items.DUST_TIN, BaseCrop.SPEIGER, "White");
        ALUMEN = new OreCrop("alumen", new CropProperties(8, 2, 0, 0, 2, 0), new Block[]{IC2Blocks.ALUMINUM_ORE_NETHER, IC2Blocks.ALUMINIUM_BLOCK}, IC2Items.DUST_ALUMINIUM, BaseCrop.DAENARA, "Silver");
        this.registerCrop(ADAMANTUM);
        this.registerCrop(ARGENTUM);
        this.registerCrop(AURELIA);
        this.registerCrop(FERRU);
        this.registerCrop(MALACHITE);
        this.registerCrop(STANNUM);
        this.registerCrop(ALUMEN);
        this.registerCrop(COFFEE);
        this.registerBaseSeed(IC2Items.COFFEE_BEANS, new BaseSeed(COFFEE, 1, 1, 1, 1, 1));
        this.registerCrop(LUMILIA);
        this.registerCrop(CACTUS);
        this.registerBaseSeed(Items.f_41982_, new BaseSeed(CACTUS, 1, 1, 1, 1, 1));
        BEETROOT = new BeetRootCrop();
        CARROT = new VegetableCrop("carrot", "Orange", (ItemLike)Items.f_42619_);
        POTATO = new VegetableCrop("potato", "Yellow", (ItemLike)Items.f_42620_);
        this.registerCrop(BEETROOT);
        this.registerBaseSeed(Items.f_42733_, new BaseSeed(BEETROOT, 1, 1, 1, 1, 1));
        this.registerCrop(CARROT);
        this.registerBaseSeed(Items.f_42619_, new BaseSeed(CARROT, 1, 1, 1, 1, 1));
        this.registerCrop(POTATO);
        this.registerBaseSeed(Items.f_42620_, new BaseSeed(POTATO, 1, 1, 1, 1, 1));
        this.registerCrop(RED_MUSHROOM);
        this.registerBaseSeed(Items.f_41953_, new BaseSeed(RED_MUSHROOM, 3, 1, 1, 1, 4));
        this.registerCrop(BROWN_MUSHROOM);
        this.registerBaseSeed(Items.f_41952_, new BaseSeed(BROWN_MUSHROOM, 3, 1, 1, 1, 4));
        this.registerCrop(CRIMSON_FUNGUS);
        this.registerBaseSeed(Items.f_41954_, new BaseSeed(CRIMSON_FUNGUS, 3, 1, 1, 1, 4));
        this.registerCrop(WARPED_FUNGUS);
        this.registerBaseSeed(Items.f_41955_, new BaseSeed(WARPED_FUNGUS, 3, 1, 1, 1, 4));
        this.registerCrop(HEMP);
        this.registerBaseSeed(IC2Items.HEMP_SEEDS, new BaseSeed(HEMP, 1, 1, 1, 1, 1));
        this.registerCrop(TEA);
        this.registerCrop(COCOA);
        this.registerBaseSeed(Items.f_42533_, new BaseSeed(COCOA, 1, 0, 0, 0, 1));
        this.registerCrop(MELON);
        this.registerBaseSeed(Items.f_42578_, new BaseSeed(MELON, 1, 1, 1, 1, 1));
        this.registerCrop(PUMPKIN);
        this.registerBaseSeed(Items.f_42577_, new BaseSeed(PUMPKIN, 1, 1, 1, 1, 1));
        this.registerCrop(SWEET_BERRY_BUSH);
        this.registerBaseSeed(Items.f_42780_, new BaseSeed(SWEET_BERRY_BUSH, 1, 1, 1, 1, 1));
        this.registerCrop(GLOW_BERRY);
        this.registerBaseSeed(Items.f_151079_, new BaseSeed(GLOW_BERRY, 1, 1, 1, 1, 1));
        WEED = this.registerCrop(new WeedCrop());
        this.registerFarmland(new Farmland(), Blocks.f_50093_);
        this.registerFarmland(new BaseFarmland(0, -2), Blocks.f_50056_);
        this.registerFarmland(new BaseFarmland(5, -2), Blocks.f_50057_);
        this.registerFarmland(new BaseFarmland(0, 1), Blocks.f_50493_);
        this.registerFarmland(new PlanterPotFarmland(), IC2Blocks.PLANTER_POT);
        this.registerFarmland(new BaseFarmland(0, -4), Blocks.f_50546_);
        this.registerFarmland(new BaseFarmland(0, 0), Blocks.f_50599_);
        this.registerFarmland(new BaseFarmland(-3, 6), Blocks.f_50135_);
        this.registerFarmland(new BaseFarmland(-2, 5), Blocks.f_50136_);
        this.registerFarmland(new BaseFarmland(-2, -1), Blocks.f_49992_, Blocks.f_49993_);
        this.registerFarmland(new BaseFarmland(1, 2), Blocks.f_50195_);
        this.registerFarmland(new BaseFarmland(2, 1), Blocks.f_220864_);
        this.registerFarmland(new BaseFarmland(0, 3), Blocks.f_50690_, Blocks.f_50699_);
        this.registerSubSoil(new BaseSubSoil(-1, 3), Blocks.f_50135_);
        this.registerSubSoil(new BaseSubSoil(0, 2), Blocks.f_50136_);
        this.registerSubSoil(new BaseSubSoil(3, -1), Blocks.f_50057_);
        this.registerSubSoil(new BaseSubSoil(-1, 0), Blocks.f_49992_, Blocks.f_49993_);
        this.registerSubSoil(new BaseSubSoil(0, -1), Blocks.f_50546_);
        this.registerSubSoil(new BaseSubSoil(0, 1), Blocks.f_50493_);
        this.registerSubSoil(new BaseSubSoil(2, 0), Blocks.f_50377_);
        this.registerSubSoil(new Mangrowth(), Blocks.f_220833_);
        this.addBiomeNutrientBonus((TagKey<Biome>)BiomeTags.f_207610_, 10);
        this.addBiomeNutrientBonus((TagKey<Biome>)Tags.Biomes.IS_SWAMP, 10);
        this.addBiomeNutrientBonus((TagKey<Biome>)Tags.Biomes.IS_MUSHROOM, 5);
        this.addBiomeNutrientBonus((TagKey<Biome>)BiomeTags.f_207611_, 5);
        this.addBiomeNutrientBonus((TagKey<Biome>)BiomeTags.f_207605_, 2);
        this.addBiomeNutrientBonus((TagKey<Biome>)Tags.Biomes.IS_PLAINS, 0);
        this.addBiomeNutrientBonus((TagKey<Biome>)BiomeTags.f_215816_, -2);
        this.addBiomeNutrientBonus((TagKey<Biome>)BiomeTags.f_207608_, -5);
        this.addBiomeNutrientBonus((TagKey<Biome>)BiomeTags.f_207606_, -5);
        this.addBiomeNutrientBonus((TagKey<Biome>)Tags.Biomes.IS_WASTELAND, -8);
        this.addBiomeNutrientBonus((TagKey<Biome>)BiomeTags.f_215818_, -10);
        this.addBiomeNutrientBonus((TagKey<Biome>)BiomeTags.f_207612_, -10);
        this.addBiomeNutrientBonus((TagKey<Biome>)Tags.Biomes.IS_DEAD, -10);
        this.addBiomeHumidityBonus((TagKey<Biome>)Tags.Biomes.IS_SWAMP, 5);
        this.addBiomeHumidityBonus((TagKey<Biome>)Tags.Biomes.IS_WATER, 10);
        this.addBiomeHumidityBonus((TagKey<Biome>)BiomeTags.f_215816_, -8);
        this.addBiomeHumidityBonus((TagKey<Biome>)Tags.Biomes.IS_WASTELAND, -5);
        this.addBiomeHumidityBonus((TagKey<Biome>)BiomeTags.f_215818_, -7);
        this.addBiomeHumidityBonus((TagKey<Biome>)BiomeTags.f_207612_, -10);
        this.addBiomeWaterQualityBonus((TagKey<Biome>)BiomeTags.f_207603_, 10);
        this.addBiomeWaterQualityBonus((TagKey<Biome>)BiomeTags.f_207605_, 7);
        this.addBiomeWaterQualityBonus((TagKey<Biome>)Tags.Biomes.IS_HOT, 5);
        this.addBiomeWaterQualityBonus((TagKey<Biome>)BiomeTags.f_207604_, 4);
        this.addBiomeWaterQualityBonus((TagKey<Biome>)Tags.Biomes.IS_SWAMP, -3);
        this.addBiomeWaterQualityBonus((TagKey<Biome>)Tags.Biomes.IS_COLD, -6);
        this.addBiomeWaterQualityBonus((TagKey<Biome>)Tags.Biomes.IS_DEAD, -10);
        MinecraftForge.EVENT_BUS.post((Event)new ICropRegistry.CropRegisterEvent());
    }

    private Block[] createCopperArray() {
        return new Block[]{Blocks.f_152505_, Blocks.f_152506_, Blocks.f_152504_, Blocks.f_152503_, Blocks.f_152502_, Blocks.f_152501_, Blocks.f_152510_, Blocks.f_152509_, Blocks.f_152508_, Blocks.f_152507_, Blocks.f_152571_, Blocks.f_152573_, Blocks.f_152572_, Blocks.f_152574_, Blocks.f_152578_, Blocks.f_152576_, Blocks.f_152577_, Blocks.f_152575_};
    }

    @Override
    public void registerCustomCropRenderer(ResourceLocation location, ICropRenderer render) {
        if (location == null || !this.crops.containsKey(location)) {
            IC2.LOGGER.info("Crop not found");
            return;
        }
        if (render == null) {
            this.customRenders.remove(location);
            return;
        }
        this.customRenders.put(location, render);
    }

    @Override
    public void registerCustomStickRenderer(ICropRenderer render) {
        this.stickRenderer = render;
    }

    @Override
    public <T extends ICrop> T registerCrop(T crop) {
        ResourceLocation id = crop.id();
        if (id == null) {
            return null;
        }
        this.crops.put(id, crop);
        ItemStack display = crop.getDisplayItem();
        if (!display.m_41619_()) {
            this.displayItems.put(id, display.m_41777_());
        }
        return crop;
    }

    @Override
    public ICrop getCrop(ResourceLocation location) {
        return this.crops.get(location);
    }

    @Override
    public ICrop getRandomCrop(OptionalLong seed) {
        return (ICrop)Util.m_214621_((List)new ObjectArrayList(this.crops.values()), (RandomSource)RandomSource.m_216335_((long)seed.orElse(this.globalSeed)));
    }

    @Override
    public ICrop getCrop(ItemStack stack) {
        if (stack.m_41720_() instanceof ICropSeed) {
            return ((ICropSeed)stack.m_41720_()).getCrop(stack);
        }
        return null;
    }

    @Override
    public void registerBaseSeed(Item item, BaseSeed seed) {
        if (item == Items.f_41852_ || seed == null) {
            return;
        }
        this.seeds.put(item, seed);
        this.cropSeeds.put(seed.crop.id(), new ItemStack((ItemLike)item, seed.stack_size));
    }

    @Override
    public ItemStack getSeedForCrop(ICrop crop) {
        return this.cropSeeds.getOrDefault(crop.id(), new ItemStack((ItemLike)IC2Items.CROP_STICKS));
    }

    @Override
    public BaseSeed getSeedForStack(ItemStack stack) {
        return this.seeds.get(stack.m_41720_());
    }

    public List<Block> getAllFarmlands() {
        ObjectArrayList blocks = new ObjectArrayList();
        for (Map.Entry<Block, IFarmland> entry : this.farmlands.entrySet()) {
            if (!(entry.getValue() instanceof BaseFarmland)) continue;
            blocks.add(entry.getKey());
        }
        return blocks;
    }

    @Override
    public List<ICrop> getCrops() {
        return new ObjectArrayList(this.crops.values());
    }

    @Override
    public void registerDisplayItem(ResourceLocation cropID, ItemStack stack) {
        if (cropID == null || stack.m_41619_()) {
            return;
        }
        this.displayItems.put(cropID, stack.m_41777_());
    }

    @Override
    public ItemStack getDisplayItem(ResourceLocation cropID) {
        return this.displayItems.getOrDefault(cropID, ItemStack.f_41583_);
    }

    @Override
    public void addBiomeHumidityBonus(TagKey<Biome> type, int bonus) {
        this.humidity.put(type, bonus);
    }

    @Override
    public int getHumidityBonus(Holder<Biome> biome) {
        int result = 0;
        for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable(this.humidity)) {
            if (!biome.m_203656_((TagKey)entry.getKey())) continue;
            result = Math.max(result, entry.getIntValue());
        }
        return result;
    }

    @Override
    public void addBiomeNutrientBonus(TagKey<Biome> type, int bonus) {
        this.nutrients.put(type, bonus);
    }

    @Override
    public int getNutrientBonus(Holder<Biome> biome) {
        int result = 0;
        for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable(this.nutrients)) {
            if (!biome.m_203656_((TagKey)entry.getKey())) continue;
            result = Math.max(result, entry.getIntValue());
        }
        return result;
    }

    @Override
    public void addBiomeWaterQualityBonus(TagKey<Biome> type, int bonus) {
        this.water.put(type, bonus);
    }

    @Override
    public int getWaterQualityBonus(Holder<Biome> biome) {
        int result = 0;
        for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable(this.water)) {
            if (!biome.m_203656_((TagKey)entry.getKey())) continue;
            result = Math.max(result, entry.getIntValue());
        }
        return result;
    }

    @Override
    public void registerFarmland(IFarmland land, Block ... blocks) {
        for (Block block : blocks) {
            this.farmlands.put(block, land);
        }
    }

    @Override
    public IFarmland getFarmland(Block block) {
        return this.farmlands.get(block);
    }

    @Override
    public void registerSubSoil(ISubSoil subSoil, Block ... blocks) {
        for (Block block : blocks) {
            this.soils.put(block, subSoil);
        }
    }

    @Override
    public ISubSoil getSubSoil(Block block) {
        return this.soils.get(block);
    }

    @OnlyIn(value=Dist.CLIENT)
    public Map<CropEntry, List<BakedQuad>> createQuads(boolean fancy) {
        if (this.stickRenderer == null) {
            this.stickRenderer = new StickRenderer();
        }
        Object2ObjectMap<CropEntry, List<Object>> quads = CollectionUtils.createMap();
        List<BakedQuad> stickQuads = null;
        stickQuads = this.stickRenderer.createQuadsForStage(0, fancy, QuadBaker.BAKER);
        quads.put(new CropEntry(null, 0), stickQuads);
        quads.put(new CropEntry(null, 1), this.stickRenderer.createQuadsForStage(1, fancy, QuadBaker.BAKER));
        for (ICrop crop : this.crops.values()) {
            ICropRenderer render = this.customRenders.get(crop.id());
            if (render == null) {
                render = new DefaultCropRenderer(crop);
            }
            int m = crop.getGrowthSteps();
            for (int i = 0; i < m; ++i) {
                ObjectList resultQuads = CollectionUtils.createList();
                resultQuads.addAll(stickQuads);
                List<BakedQuad> list = render.createQuadsForStage(i, fancy, QuadBaker.BAKER);
                if (list != null) {
                    resultQuads.addAll(list);
                }
                quads.put(new CropEntry(crop, i), (List<Object>)resultQuads);
            }
        }
        return quads;
    }

    static {
        SEA_PICKLE = new SeaPickleCrop();
        KELP = new KelpCrop();
        BONE_FLOWER = new BoneFlowerCrop();
        INKBERGINE = new InkbergineCrop();
        ACACIA_SAPLING = new SaplingCrop("acacia", (ItemLike)Items.f_41841_, (ItemLike)Items.f_41827_);
        BIRCH_SAPLING = new SaplingCrop("birch", (ItemLike)Items.f_41839_, (ItemLike)Items.f_42801_);
        DARK_OAK_SAPLING = new SaplingCrop("dark_oak", (ItemLike)Items.f_41842_, (ItemLike)Items.f_41828_);
        OAK_SAPLING = new SaplingCrop("oak", (ItemLike)Items.f_41837_, (ItemLike)Items.f_42799_);
        JUNGLE_SAPLING = new SaplingCrop("jungle", (ItemLike)Items.f_41840_, (ItemLike)Items.f_41826_);
        SPRUCE_SAPLING = new SaplingCrop("spruce", (ItemLike)Items.f_41838_, (ItemLike)Items.f_42800_);
        COFFEE = new CoffeeCrop();
        LUMILIA = new LumiliaCrop();
        CACTUS = new CactusCrop();
        RED_MUSHROOM = new MushroomCrop(true);
        BROWN_MUSHROOM = new MushroomCrop(false);
        CRIMSON_FUNGUS = new FungusCrop(true);
        WARPED_FUNGUS = new FungusCrop(false);
        HEMP = new HempCrop();
        TEA = new TeaCrop();
        COCOA = new CocoaCrop();
        MELON = new MelonCrop();
        PUMPKIN = new PumpkinCrop();
        SWEET_BERRY_BUSH = new BerryBushCrop();
        GLOW_BERRY = new GlowBerryCrop();
    }
}

