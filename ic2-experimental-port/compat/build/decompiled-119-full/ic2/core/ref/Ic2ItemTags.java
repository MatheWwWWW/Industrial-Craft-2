/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 */
package ic2.core.ref;

import ic2.core.IC2;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class Ic2ItemTags {
    public static final TagKey<Item> BRONZE_INGOTS = Ic2ItemTags.createResource("bronze", "ingots");
    public static final TagKey<Item> LEAD_INGOTS = Ic2ItemTags.createResource("lead", "ingots");
    public static final TagKey<Item> PLUTONIUM_INGOTS = Ic2ItemTags.createResource("plutonium", "ingots");
    public static final TagKey<Item> SILVER_INGOTS = Ic2ItemTags.createResource("silver", "ingots");
    public static final TagKey<Item> STEEL_INGOTS = Ic2ItemTags.createResource("steel", "ingots");
    public static final TagKey<Item> TIN_INGOTS = Ic2ItemTags.createResource("tin", "ingots");
    public static final TagKey<Item> URANIUM_INGOTS = Ic2ItemTags.createResource("uranium", "ingots");
    public static final TagKey<Item> IRIDIUM_NUGGETS = Ic2ItemTags.createResource("iridium", "nuggets");
    public static final TagKey<Item> COAL_DUSTS = Ic2ItemTags.createResource("coal", "dusts");
    public static final TagKey<Item> COPPER_DUSTS = Ic2ItemTags.createResource("copper", "dusts");
    public static final TagKey<Item> DIAMOND_DUSTS = Ic2ItemTags.createResource("diamond", "dusts");
    public static final TagKey<Item> GOLD_DUSTS = Ic2ItemTags.createResource("gold", "dusts");
    public static final TagKey<Item> IRON_DUSTS = Ic2ItemTags.createResource("iron", "dusts");
    public static final TagKey<Item> LAPIS_DUSTS = Ic2ItemTags.createResource("lapis", "dusts");
    public static final TagKey<Item> LEAD_DUSTS = Ic2ItemTags.createResource("lead", "dusts");
    public static final TagKey<Item> OBSIDIAN_DUSTS = Ic2ItemTags.createResource("obsidian", "dusts");
    public static final TagKey<Item> SILVER_DUSTS = Ic2ItemTags.createResource("silver", "dusts");
    public static final TagKey<Item> STONE_DUSTS = Ic2ItemTags.createResource("stone", "dusts");
    public static final TagKey<Item> SULFUR_DUSTS = Ic2ItemTags.createResource("sulfur", "dusts");
    public static final TagKey<Item> TIN_DUSTS = Ic2ItemTags.createResource("tin", "dusts");
    public static final TagKey<Item> IRON_PLATES = Ic2ItemTags.createResource("iron", "plates");
    public static final TagKey<Item> GOLD_PLATES = Ic2ItemTags.createResource("gold", "plates");
    public static final TagKey<Item> LEAD_PLATES = Ic2ItemTags.createResource("lead", "plates");
    public static final TagKey<Item> BRONZE_PLATES = Ic2ItemTags.createResource("bronze", "plates");
    public static final TagKey<Item> TIN_PLATES = Ic2ItemTags.createResource("tin", "plates");
    public static final TagKey<Item> COPPER_PLATES = Ic2ItemTags.createResource("copper", "plates");
    public static final TagKey<Item> LAPIS_PLATES = Ic2ItemTags.createResource("lapis", "plates");
    public static final TagKey<Item> OBSIDIAN_PLATES = Ic2ItemTags.createResource("obsidian", "plates");
    public static final TagKey<Item> STEEL_PLATES = Ic2ItemTags.createResource("steel", "plates");
    public static final TagKey<Item> ORES = Ic2ItemTags.create("c:ores", "forge:ores");
    public static final TagKey<Item> LEAD_ORES = Ic2ItemTags.createResource("lead", "ores");
    public static final TagKey<Item> LEAD_RAW_ORES = Ic2ItemTags.createResource("lead", "raw_ores");
    public static final TagKey<Item> SILVER_ORES = Ic2ItemTags.createResource("silver", "ores");
    public static final TagKey<Item> TIN_RAW_ORES = Ic2ItemTags.createResource("tin", "raw_ores");
    public static final TagKey<Item> TIN_ORES = Ic2ItemTags.createResource("tin", "ores");
    public static final TagKey<Item> URANIUM_ORES = Ic2ItemTags.createResource("uranium", "ores");
    public static final TagKey<Item> URANIUM_RAW_ORES = Ic2ItemTags.createResource("uranium", "raw_ores");
    public static final TagKey<Item> BRONZE_BLOCKS = Ic2ItemTags.createResource("bronze", "blocks");
    public static final TagKey<Item> STEEL_BLOCKS = Ic2ItemTags.createResource("steel", "blocks");
    public static final TagKey<Item> DIAMONDS = Ic2ItemTags.create("c:diamonds", "forge:gems/diamond");
    public static final TagKey<Item> WOODEN_CHESTS = Ic2ItemTags.create("c:wooden_chests", "forge:chests/wooden");
    public static final TagKey<Item> FORGE_HAMMERS = Ic2ItemTags.create("forge_hammers");
    public static final TagKey<Item> WIRE_CUTTERS = Ic2ItemTags.create("wire_cutters");

    public static void init() {
    }

    private static TagKey<Item> create(String string) {
        return TagKey.m_203882_((ResourceKey)Registry.f_122904_, (ResourceLocation)IC2.getIdentifier(string));
    }

    private static TagKey<Item> create(String string, String string2) {
        ResourceLocation resourceLocation = new ResourceLocation(IC2.envProxy.isFabricEnv() ? string : string2);
        return TagKey.m_203882_((ResourceKey)Registry.f_122904_, (ResourceLocation)resourceLocation);
    }

    private static TagKey<Item> createResource(String string, String string2) {
        return Ic2ItemTags.create("c:%s_%s".formatted(string, string2), "forge:%s/%s".formatted(string2, string));
    }
}

