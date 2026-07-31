/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.entity.decoration.PaintingVariant
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.common.Tags$Blocks
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.IForgeRegistry
 */
package ic2.core.platform.registries;

import ic2.api.blocks.BlockRegistries;
import ic2.api.blocks.PainterHelper;
import ic2.core.platform.recipes.helpers.DefaultOreSearcher;
import ic2.core.platform.registries.ColorMaps;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Fluids;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Paintings;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.SimpleRegistry;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class IC2Tags {
    public static final SimpleRegistry<Set<String>> TAGS_TO_LOAD = CollectionUtils.createRegistry();
    public static TagKey<Item> TOOLBOX = IC2Tags.createItemTag("ic2", "toolbox");
    public static TagKey<Item> LOGS = ItemTags.f_13182_;
    public static TagKey<Item> RUBBER = IC2Tags.createItemTag("ic2", "rubber");
    public static TagKey<Item> ORE_COPPER = IC2Tags.createForgeItemTag("ores/copper");
    public static TagKey<Item> ORE_TIN = IC2Tags.createForgeItemTag("ores/tin");
    public static TagKey<Item> ORE_SILVER = IC2Tags.createForgeItemTag("ores/silver");
    public static TagKey<Item> ORE_URANIUM = IC2Tags.createForgeItemTag("ores/uranium");
    public static TagKey<Item> ORE_ALUMINIUM = IC2Tags.createForgeItemTag("ores/aluminium");
    public static TagKey<Item> RAW_TIN = IC2Tags.createForgeItemTag("raw_materials/tin");
    public static TagKey<Item> RAW_SILVER = IC2Tags.createForgeItemTag("raw_materials/silver");
    public static TagKey<Item> RAW_URANIUM = IC2Tags.createForgeItemTag("raw_materials/uranium");
    public static TagKey<Item> RAW_ALUMINIUM = IC2Tags.createForgeItemTag("raw_materials/aluminium");
    public static TagKey<Item> DUST_COPPER = IC2Tags.createForgeItemTag("dusts/copper");
    public static TagKey<Item> DUST_TIN = IC2Tags.createForgeItemTag("dusts/tin");
    public static TagKey<Item> DUST_SILVER = IC2Tags.createForgeItemTag("dusts/silver");
    public static TagKey<Item> DUST_BRONZE = IC2Tags.createForgeItemTag("dusts/bronze");
    public static TagKey<Item> DUST_ALUMINIUM = IC2Tags.createForgeItemTag("dusts/aluminium");
    public static TagKey<Item> DUST_IRON = IC2Tags.createForgeItemTag("dusts/iron");
    public static TagKey<Item> DUST_GOLD = IC2Tags.createForgeItemTag("dusts/gold");
    public static TagKey<Item> DUST_COAL = IC2Tags.createForgeItemTag("dusts/coal");
    public static TagKey<Item> DUST_CHARCOAL = IC2Tags.createForgeItemTag("dusts/charcoal");
    public static TagKey<Item> INGOT_COPPER = IC2Tags.createForgeItemTag("ingots/copper");
    public static TagKey<Item> INGOT_TIN = IC2Tags.createForgeItemTag("ingots/tin");
    public static TagKey<Item> INGOT_SILVER = IC2Tags.createForgeItemTag("ingots/silver");
    public static TagKey<Item> INGOT_BRONZE = IC2Tags.createForgeItemTag("ingots/bronze");
    public static TagKey<Item> INGOT_ALUMINIUM = IC2Tags.createForgeItemTag("ingots/aluminium");
    public static TagKey<Item> INGOT_REFINED_IRON = IC2Tags.createForgeItemTag("ingots/refined_iron");
    public static TagKey<Item> INGOT_URANIUM = IC2Tags.createForgeItemTag("ingots/uranium");
    public static TagKey<Item> STORAGE_COPPER = IC2Tags.createForgeItemTag("storage_blocks/copper");
    public static TagKey<Item> STORAGE_TIN = IC2Tags.createForgeItemTag("storage_blocks/tin");
    public static TagKey<Item> STORAGE_SILVER = IC2Tags.createForgeItemTag("storage_blocks/silver");
    public static TagKey<Item> STORAGE_BRONZE = IC2Tags.createForgeItemTag("storage_blocks/bronze");
    public static TagKey<Item> STORAGE_ALUMINIUM = IC2Tags.createForgeItemTag("storage_blocks/aluminium");
    public static TagKey<Item> STORAGE_REFINED_IRON = IC2Tags.createForgeItemTag("storage_blocks/refined_iron");
    public static TagKey<Item> STORAGE_RAW_TIN = IC2Tags.createForgeItemTag("storage_blocks/raw_tin");
    public static TagKey<Item> STORAGE_RAW_SILVER = IC2Tags.createForgeItemTag("storage_blocks/raw_silver");
    public static TagKey<Item> STORAGE_RAW_ALUMINIUM = IC2Tags.createForgeItemTag("storage_blocks/raw_aluminium");
    public static TagKey<Item> STORAGE_RAW_URANIUM = IC2Tags.createForgeItemTag("storage_blocks/raw_uranium");
    public static TagKey<Item> CFOAM_BLOCK = IC2Tags.createItemTag("ic2", "cfoam/block");
    public static TagKey<Item> CFOAM_WOOL = IC2Tags.createItemTag("ic2", "cfoam/wool");
    public static TagKey<Item> CFOAM_WOOL_CARPET = IC2Tags.createItemTag("ic2", "cfoam/carpet");
    public static TagKey<Item> CFOAM_STAIRS = IC2Tags.createItemTag("ic2", "cfoam/stairs");
    public static TagKey<Item> CFOAM_SLABS = IC2Tags.createItemTag("ic2", "cfoam/slabs");
    public static TagKey<Item> CFOAM_SURFACE = IC2Tags.createItemTag("ic2", "cfoam/surface");
    public static TagKey<Item> CFOAM_PANEL = IC2Tags.createItemTag("ic2", "cfoam/panel");
    public static TagKey<Item> CFOAM_WALLS = IC2Tags.createItemTag("ic2", "cfoam/walls");
    public static TagKey<Block> ROCK_CUTTER_BLOCKS = IC2Tags.createBlockTag("ic2", "rock_cutter");
    public static TagKey<Block> MINING_LASER_UNBREAKABLE = IC2Tags.createBlockTag("ic2", "mining_laser_unbreakable");
    public static TagKey<Block> TANK_STRUCTURE = IC2Tags.createBlockTag("ic2", "tank_structure_blocks");
    public static TagKey<Block> TERRAFORMER_FLOWERS = IC2Tags.createBlockTag("ic2", "flowers");
    public static TagKey<Block> TERRAFORMER_GROUND = IC2Tags.createBlockTag("ic2", "ground");
    public static TagKey<Block> TERRAFORMER_FLAT = IC2Tags.createBlockTag("ic2", "flat");
    public static TagKey<Block> CAKES = IC2Tags.createBlockTag("cakes");
    public static TagKey<Item> DUST_COAL_AND_CHARCOAL = IC2Tags.createItemTag("ic2", "coal_and_charcoal");
    public static TagKey<Fluid> STEAM = IC2Tags.createFluidTag("forge", "steam");
    public static TagKey<Item> WIKI_SPAWN = IC2Tags.createItemTag("ic2", "spawn_wiki");

    public static void initTags() {
        BlockRegistries.registerListener(new DefaultOreSearcher());
        IC2Tags.registerMinecraftTag("logs", IC2Blocks.RUBBERWOOD_LOG, IC2Blocks.RUBBER_LOG_STRIPPED, IC2Blocks.RUBBER_LOG_BARKED_STRIPPED, IC2Blocks.RUBBERWOOD_LOG_BARKED);
        IC2Tags.registerMinecraftTag("planks", IC2Blocks.RUBBERWOOD_PLANKS);
        IC2Tags.registerMinecraftTag("leaves", IC2Blocks.RUBBER_LEAVES);
        IC2Tags.registerMinecraftTag("saplings", IC2Blocks.RUBBER_SAPLING);
        IC2Tags.registerMinecraftTag("wooden_slabs", IC2Blocks.RUBBERWOOD_SLAB);
        IC2Tags.registerMinecraftTag("wooden_stairs", IC2Blocks.RUBBERWOOD_STAIRS);
        IC2Tags.registerMinecraftTag("wooden_doors", IC2Blocks.RUBBERWOOD_DOOR);
        IC2Tags.registerMinecraftTag("wooden_trapdoors", IC2Blocks.RUBBERWOOD_TRAPDOOR);
        IC2Tags.registerMinecraftTag("wooden_fences", IC2Blocks.RUBBERWOOD_FENCE);
        IC2Tags.registerMinecraftTag("wooden_pressure_plates", IC2Blocks.RUBBERWOOD_PRESSURE_PLATE);
        IC2Tags.registerMinecraftTag("wooden_buttons", IC2Blocks.RUBBERWOOD_BUTTON);
        IC2Tags.registerMinecraftTag("fences", IC2Blocks.IRON_FENCE);
        IC2Tags.registerMinecraftTag("boats", IC2Items.RUBBER_BOAT, IC2Items.RUBBERWOOD_BOAT, IC2Items.CARBON_BOAT, IC2Items.RUBBER_BOAT_BROKEN);
        IC2Tags.registerMinecraftTag("signs", IC2Items.RUBBERWOOD_SIGN);
        IC2Tags.registerTags(new ResourceLocation("tags/blocks/candle_cakes"), ForgeRegistries.BLOCKS, IC2Blocks.CAKE_CANDLE_CHOCOLATE, IC2Blocks.CAKE_CANDLE_COFFEE, IC2Blocks.CAKE_CANDLE_HONEY, IC2Blocks.CAKE_CANDLE_LEMON);
        IC2Tags.registerMinecraftTag("cakes", Blocks.f_50145_, IC2Blocks.CAKE_CHOCOLATE, IC2Blocks.CAKE_COFFEE, IC2Blocks.CAKE_HONEY, IC2Blocks.CAKE_LEMON);
        IC2Tags.registerBlockTag("minecraft", "cakes", BlockTags.f_144268_);
        IC2Tags.registerSimpleTag("forge", "steam", IC2Fluids.STEAM);
        IC2Tags.registerTag("forge", "seeds", "hemp", IC2Items.HEMP_SEEDS);
        IC2Tags.registerSimpleTag("ic2", "rubber", IC2Items.RUBBER);
        for (DyeColor color : DyeColor.values()) {
            IC2Tags.registerTag("ic2", "cfoam", "block", ColorMaps.CFOAM_BLOCKS.getBlock(color));
            IC2Tags.registerTag("ic2", "cfoam", "wool", ColorMaps.CFOAM_WOOL.getBlock(color));
            IC2Tags.registerTag("ic2", "cfoam", "carpet", ColorMaps.CFOAM_CARPET.getBlock(color));
            IC2Tags.registerTag("ic2", "cfoam", "stairs", ColorMaps.CFOAM_STAIRS.getBlock(color));
            IC2Tags.registerTag("ic2", "cfoam", "slabs", ColorMaps.CFOAM_SLABS.getBlock(color));
            IC2Tags.registerTag("ic2", "cfoam", "surface", ColorMaps.CFOAM_SURFACE.getBlock(color));
            IC2Tags.registerTag("ic2", "cfoam", "panel", ColorMaps.CFOAM_PANELS.getBlock(color));
            IC2Tags.registerTag("ic2", "cfoam", "walls", ColorMaps.CFOAM_WALLS.getBlock(color));
            IC2Tags.registerMinecraftTag("walls", ColorMaps.CFOAM_WALLS.getBlock(color));
            IC2Tags.registerMinecraftTag("stairs", ColorMaps.CFOAM_STAIRS.getBlock(color));
            IC2Tags.registerMinecraftTag("slabs", ColorMaps.CFOAM_SLABS.getBlock(color));
            IC2Tags.registerMinecraftTag("carpet", ColorMaps.CFOAM_CARPET.getBlock(color));
        }
        IC2Tags.registerMinecraftTag("walls", IC2Blocks.TEXTURED_WALL);
        IC2Tags.registerTag("forge", "gems", "diamond", IC2Items.INDUSTRIAL_DIAMOND);
        IC2Tags.registerTag("forge", "ores", "tin", IC2Blocks.TIN_ORE, IC2Blocks.DEEPSLATE_TIN_ORE);
        IC2Tags.registerTag("forge", "ores", "silver", IC2Blocks.SILVER_ORE, IC2Blocks.SILVER_ORE_NETHER, IC2Blocks.DEEPSLATE_SILVER_ORE);
        IC2Tags.registerTag("forge", "ores", "uranium", IC2Blocks.URANIUM_ORE, IC2Blocks.DEEPSLATE_URANIUM_ORE);
        IC2Tags.registerTag("forge", "ores", "aluminium", IC2Blocks.ALUMINUM_ORE_NETHER);
        IC2Tags.registerTag("forge", "raw_materials", "tin", IC2Items.RAW_TIN);
        IC2Tags.registerTag("forge", "raw_materials", "silver", IC2Items.RAW_SILVER);
        IC2Tags.registerTag("forge", "raw_materials", "aluminium", IC2Items.RAW_ALUMINIUM);
        IC2Tags.registerTag("forge", "raw_materials", "uranium", IC2Items.ORE_URANIUM_DROP);
        IC2Tags.registerTag("forge", "dusts", "copper", IC2Items.DUST_COPPER);
        IC2Tags.registerTag("forge", "dusts", "tin", IC2Items.DUST_TIN);
        IC2Tags.registerTag("forge", "dusts", "silver", IC2Items.DUST_SILVER);
        IC2Tags.registerTag("forge", "dusts", "bronze", IC2Items.DUST_BRONZE);
        IC2Tags.registerTag("forge", "dusts", "aluminium", IC2Items.DUST_ALUMINIUM);
        IC2Tags.registerTag("forge", "dusts", "iron", IC2Items.DUST_IRON);
        IC2Tags.registerTag("forge", "dusts", "gold", IC2Items.DUST_GOLD);
        IC2Tags.registerTag("forge", "dusts", "coal", IC2Items.DUST_COAL);
        IC2Tags.registerTag("forge", "dusts", "charcoal", IC2Items.DUST_CHARCOAL);
        IC2Tags.registerTag("forge", "ingots", "tin", IC2Items.INGOT_TIN);
        IC2Tags.registerTag("forge", "ingots", "silver", IC2Items.INGOT_SILVER);
        IC2Tags.registerTag("forge", "ingots", "bronze", IC2Items.INGOT_BRONZE);
        IC2Tags.registerTag("forge", "ingots", "aluminium", IC2Items.INGOT_ALUMINIUM);
        IC2Tags.registerTag("forge", "ingots", "refined_iron", IC2Items.INGOT_REFINED_IRON);
        IC2Tags.registerTag("forge", "ingots", "uranium", IC2Items.INGOT_URANIUM);
        IC2Tags.registerTag("forge", "storage_blocks", "tin", IC2Blocks.TIN_BLOCK);
        IC2Tags.registerTag("forge", "storage_blocks", "silver", IC2Blocks.SILVER_BLOCK);
        IC2Tags.registerTag("forge", "storage_blocks", "bronze", IC2Blocks.BRONZE_BLOCK);
        IC2Tags.registerTag("forge", "storage_blocks", "aluminium", IC2Blocks.ALUMINIUM_BLOCK);
        IC2Tags.registerTag("forge", "storage_blocks", "refined_iron", IC2Blocks.REFINED_IRON_BLOCK);
        IC2Tags.registerTag("forge", "storage_blocks", "raw_tin", IC2Blocks.RAW_TIN_BLOCK);
        IC2Tags.registerTag("forge", "storage_blocks", "raw_silver", IC2Blocks.RAW_SILVER_BLOCK);
        IC2Tags.registerTag("forge", "storage_blocks", "raw_aluminium", IC2Blocks.RAW_ALUMINIUM_BLOCK);
        IC2Tags.registerTag("forge", "storage_blocks", "raw_uranium", IC2Blocks.URANIUM_DROP_BLOCK);
        IC2Tags.registerSimpleTag("forge", "gaseous", IC2Fluids.WOOD_GAS, IC2Fluids.STEAM);
        IC2Tags.registerSimpleTag("ic2", "rock_cutter", Blocks.f_50069_, Blocks.f_50228_, Blocks.f_50334_, Blocks.f_50122_, Blocks.f_50080_, Blocks.f_50723_, Blocks.f_50730_, Blocks.f_50137_, Blocks.f_152550_);
        IC2Tags.registerSimpleTag("ic2", "mining_laser_unbreakable", Blocks.f_50076_, Blocks.f_50080_, Blocks.f_49991_, Blocks.f_49990_, Blocks.f_50752_);
        IC2Tags.registerBlockTag("ic2", "tank_structure_blocks", Tags.Blocks.STORAGE_BLOCKS, Tags.Blocks.GLASS, Tags.Blocks.STONE, Tags.Blocks.COBBLESTONE, BlockTags.f_13091_, CFOAM_BLOCK, Tags.Blocks.END_STONES, Tags.Blocks.SANDSTONE);
        ObjectSortedSet blocks = CollectionUtils.createLinkedSet();
        blocks.addAll(PainterHelper.CONCRETE.getBlocks());
        blocks.addAll(PainterHelper.GLAZED_TERRACOTTA.getBlocks());
        blocks.addAll(PainterHelper.TERRACOTTA.getBlocks());
        blocks.add((Object)Blocks.f_50352_);
        blocks.add((Object)Blocks.f_50076_);
        blocks.add((Object)Blocks.f_50080_);
        blocks.addAll(CollectionUtils.asList(IC2Blocks.REINFORCED_BRICK, IC2Blocks.REINFORCED_CLEAR_GLASS, IC2Blocks.REINFORCED_CRACKED_STONE, IC2Blocks.REINFORCED_GLASS, IC2Blocks.REINFORCED_STONE));
        IC2Tags.registerSimpleTag("ic2", "tank_structure_blocks", (Block[])blocks.toArray((Object[])new Block[blocks.size()]));
        IC2Tags.registerSimpleTag("forge", "glass", IC2Blocks.REINFORCED_CLEAR_GLASS, IC2Blocks.REINFORCED_GLASS);
        IC2Tags.registerBlockTag("ic2", "flowers", BlockTags.f_13073_, BlockTags.f_13041_);
        IC2Tags.registerBlockTag("ic2", "ground", BlockTags.f_144274_);
        IC2Tags.registerSimpleTag("ic2", "ground", Blocks.f_50093_, Blocks.f_50352_);
        IC2Tags.registerSimpleTag("ic2", "ground", PainterHelper.TERRACOTTA.getBlockArray());
        IC2Tags.registerBlockTag("ic2", "flat", BlockTags.f_13041_, BlockTags.f_13073_, BlockTags.f_144274_, BlockTags.f_13106_, BlockTags.f_13104_, BlockTags.f_13029_, Tags.Blocks.GRAVEL);
        IC2Tags.registerSimpleTag("ic2", "flat", Blocks.f_50186_, Blocks.f_50133_, Blocks.f_50180_, Blocks.f_50182_, Blocks.f_50181_);
        IC2Tags.registerItemTag("ic2", "coal_and_charcoal", DUST_COAL, DUST_CHARCOAL);
        for (PaintingVariant varriant : IC2Paintings.PAINTINGS) {
            IC2Tags.registerTags(new ResourceLocation("minecraft", "tags/painting_variant/placeable"), ForgeRegistries.PAINTING_VARIANTS, varriant);
        }
    }

    public static TagKey<Item> createItemTag(String name) {
        return IC2Tags.createItemTag("minecraft", name);
    }

    public static TagKey<Item> createForgeItemTag(String name) {
        return IC2Tags.createItemTag("forge", name);
    }

    public static TagKey<Item> createItemTag(String owner, String name) {
        return ItemTags.create((ResourceLocation)new ResourceLocation(owner, name));
    }

    public static TagKey<Block> createBlockTag(String name) {
        return IC2Tags.createBlockTag("minecraft", name);
    }

    public static TagKey<Block> createForgeBlockTag(String name) {
        return IC2Tags.createBlockTag("forge", name);
    }

    public static TagKey<Block> createBlockTag(String owner, String name) {
        return BlockTags.create((ResourceLocation)new ResourceLocation(owner, name));
    }

    public static TagKey<Fluid> createFluidTag(String name) {
        return IC2Tags.createFluidTag("minecraft", name);
    }

    public static TagKey<Fluid> createForgeFluidTag(String name) {
        return IC2Tags.createFluidTag("forge", name);
    }

    public static TagKey<Fluid> createFluidTag(String owner, String name) {
        return FluidTags.create((ResourceLocation)new ResourceLocation(owner, name));
    }

    public static void registerTag(String owner, String category, String name, Item ... items) {
        IC2Tags.registerTags(new ResourceLocation(owner, "tags/items/" + category + "/" + name), ForgeRegistries.ITEMS, items);
        IC2Tags.registerTags(new ResourceLocation(owner, "tags/items/" + category), ForgeRegistries.ITEMS, items);
    }

    public static void registerTag(String owner, String category, String name, Block ... blocks) {
        IC2Tags.registerTags(new ResourceLocation(owner, "tags/blocks/" + category + "/" + name), ForgeRegistries.BLOCKS, blocks);
        IC2Tags.registerTags(new ResourceLocation(owner, "tags/blocks/" + category), ForgeRegistries.BLOCKS, blocks);
        IC2Tags.registerTags(new ResourceLocation(owner, "tags/items/" + category + "/" + name), ForgeRegistries.BLOCKS, blocks);
        IC2Tags.registerTags(new ResourceLocation(owner, "tags/items/" + category), ForgeRegistries.BLOCKS, blocks);
    }

    public static void registerTag(String owner, String category, String name, Fluid ... fluids) {
        IC2Tags.registerTags(new ResourceLocation(owner, "tags/fluids/" + category + "/" + name), ForgeRegistries.FLUIDS, fluids);
        IC2Tags.registerTags(new ResourceLocation(owner, "tags/fluids/" + category), ForgeRegistries.FLUIDS, fluids);
    }

    public static void registerSimpleTag(String owner, String name, Item ... items) {
        IC2Tags.registerTags(new ResourceLocation(owner, "tags/items/" + name), ForgeRegistries.ITEMS, items);
    }

    public static void registerSimpleTag(String owner, String name, Block ... blocks) {
        IC2Tags.registerTags(new ResourceLocation(owner, "tags/blocks/" + name), ForgeRegistries.BLOCKS, blocks);
        IC2Tags.registerTags(new ResourceLocation(owner, "tags/items/" + name), ForgeRegistries.BLOCKS, blocks);
    }

    public static void registerSimpleTag(String owner, String name, Fluid ... fluids) {
        IC2Tags.registerTags(new ResourceLocation(owner, "tags/fluids/" + name), ForgeRegistries.FLUIDS, fluids);
    }

    public static void registerMinecraftTag(String name, Item ... items) {
        IC2Tags.registerTags(new ResourceLocation("tags/items/" + name), ForgeRegistries.ITEMS, items);
    }

    public static void registerMinecraftTag(String name, Block ... blocks) {
        IC2Tags.registerTags(new ResourceLocation("tags/blocks/" + name), ForgeRegistries.BLOCKS, blocks);
        IC2Tags.registerTags(new ResourceLocation("tags/items/" + name), ForgeRegistries.BLOCKS, blocks);
    }

    public static void registerMinecraftTag(String name, Fluid ... fluids) {
        IC2Tags.registerTags(new ResourceLocation("tags/fluids/" + name), ForgeRegistries.FLUIDS, fluids);
    }

    public static void registerItemTag(String key, String name, TagKey<?> ... tags) {
        IC2Tags.registerTag(key, "tags/items/" + name, tags);
    }

    public static void registerBlockTag(String key, String name, TagKey<?> ... tags) {
        IC2Tags.registerTag(key, "tags/blocks/" + name, tags);
    }

    public static void registerTag(String key, String value, TagKey<?> ... tags) {
        IC2Tags.registerTag(new ResourceLocation(key, value), tags);
    }

    public static void registerTag(ResourceLocation name, TagKey<?> ... tags) {
        Set<String> set = TAGS_TO_LOAD.getValue(name);
        if (set == null) {
            set = CollectionUtils.createSet();
            TAGS_TO_LOAD.register(name, set);
        }
        int m = tags.length;
        for (int i = 0; i < m; ++i) {
            set.add("#" + tags[i].f_203868_().toString());
        }
    }

    @SafeVarargs
    public static <T> void registerTags(ResourceLocation key, IForgeRegistry<T> registry, T ... entries) {
        Set<String> set = TAGS_TO_LOAD.getValue(key);
        if (set == null) {
            set = CollectionUtils.createSet();
            TAGS_TO_LOAD.register(key, set);
        }
        for (T entry : entries) {
            set.add(registry.getKey(entry).toString());
        }
    }
}

