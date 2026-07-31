/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.CaseFormat
 *  com.google.common.hash.Hashing
 *  com.google.common.hash.HashingOutputStream
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.stream.JsonWriter
 *  it.unimi.dsi.fastutil.Pair
 *  net.minecraft.core.Registry
 *  net.minecraft.data.CachedOutput
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.DataProvider
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.block.Block
 */
package ic2.data;

import com.google.common.base.CaseFormat;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import it.unimi.dsi.fastutil.Pair;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;

public class OldToNewLangConverter
implements DataProvider {
    private final DataGenerator generator;
    private final Map<String, String> mapping = OldToNewLangConverter.createMappings();

    public OldToNewLangConverter(DataGenerator dataGenerator) {
        this.generator = dataGenerator;
    }

    public String m_6055_() {
        return "IC2 Localization";
    }

    public boolean isValidGuiLang(String string) {
        return string.contains("gui") && !string.contains(".name") || string.contains("SteamRepressurizer") && !string.contains("config") || string.contains("personalTrader") || string.startsWith("dir.");
    }

    public String convertPainterKey(String string) {
        String string2 = string.split("\\.")[1];
        return "item.ic2." + string2 + "_painter";
    }

    public void m_213708_(CachedOutput cachedOutput) throws IOException {
        Path path = this.generator.m_123916_();
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        Path path2 = path.getParent().resolve("resources").resolve("assets/ic2/lang_ic2");
        Files.walk(path2, 1, new FileVisitOption[0]).forEach(path3 -> {
            if (Files.isDirectory(path3, new LinkOption[0])) {
                return;
            }
            try {
                String string = path3.getFileName().toString().replace(".properties", "");
                Path path4 = path2.resolve(string + ".properties");
                Path path5 = path.resolve("assets/ic2/lang/" + string.toLowerCase(Locale.ROOT) + ".json");
                Properties properties = new Properties();
                properties.load(Files.newBufferedReader(path4));
                ArrayList<Pair> arrayList = new ArrayList<Pair>();
                properties.forEach((BiConsumer<? super Object, ? super Object>)((BiConsumer<Object, Object>)(object, object2) -> {
                    String string = (String)object;
                    String string2 = (String)object2;
                    if (string.contains("energy_o_mat") || string.contains("uu_assembly_bench")) {
                        arrayList.add(Pair.of((Object)("container.ic2." + string.replace("te.", "")), (Object)string2));
                    }
                    if (string.equals("te.mfsu") || string.equals("te.mfe") || string.equals("te.electrolyzer")) {
                        arrayList.add(Pair.of((Object)("block.ic2.classic_" + string.replace("te.", "")), (Object)string2));
                        arrayList.add(Pair.of((Object)("container.ic2.classic_" + string.replace("te.", "")), (Object)string2));
                    }
                    if (this.mapping.containsKey(string)) {
                        arrayList.add(Pair.of((Object)this.mapping.get(string), (Object)string2));
                    } else if (string.startsWith("tooltip.") || string.startsWith("generic.") || string.startsWith("wind_meter.") || this.isValidGuiLang(string)) {
                        arrayList.add(Pair.of((Object)("ic2." + string), (Object)string2));
                    } else if (string.startsWith("achievement.")) {
                        try {
                            String string3 = string.split("[.]", 2)[1];
                            arrayList.add(Pair.of((Object)("advancements.ic2." + string3), (Object)string2));
                        }
                        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
                    } else if (string.startsWith("painter.")) {
                        arrayList.add(Pair.of((Object)this.convertPainterKey(string), (Object)string2));
                    }
                }));
                arrayList.removeIf(pair -> ((String)pair.left()).startsWith("item.") && !Registry.f_122827_.m_7804_(OldToNewLangConverter.extractIdentifier((String)pair.left())));
                arrayList.removeIf(pair -> ((String)pair.left()).startsWith("block.") && !Registry.f_122824_.m_7804_(OldToNewLangConverter.extractIdentifier((String)pair.left())));
                arrayList.addAll(arrayList.stream().filter(pair -> ((String)pair.left()).startsWith("block.") && ((String)pair.left()).split("[.]").length == 3).filter(pair -> {
                    ResourceLocation resourceLocation = OldToNewLangConverter.extractIdentifier((String)pair.left());
                    boolean bl = this.mapping.containsKey("container.ic2." + resourceLocation.m_135815_());
                    return !bl;
                }).map(pair -> Pair.of((Object)("container.ic2." + OldToNewLangConverter.extractIdentifier((String)pair.left()).m_135815_()), (Object)((String)pair.right()))).filter(pair -> {
                    String[] stringArray;
                    if (Registry.f_122863_.m_7804_(OldToNewLangConverter.extractIdentifier((String)pair.left()))) {
                        return true;
                    }
                    for (String string : stringArray = new String[]{"mfe", "mfsu", "rt_generator", "lv_transformer", "mv_transformer", "hv_transformer", "ev_transformer", "tank", "batbox_chargepad", "cesu_chargepad", "mfe_chargepad", "mfsu_chargepad"}) {
                        if (!((String)pair.left()).contains(string)) continue;
                        return true;
                    }
                    return false;
                }).toList());
                arrayList.sort(((Comparator)(pair, pair2) -> OldToNewLangConverter.tryCompareByRawId((Pair<String, String>)pair, (Pair<String, String>)pair2, "block", Registry.f_122824_)).thenComparing((pair, pair2) -> OldToNewLangConverter.tryCompareByRawId((Pair<String, String>)pair, (Pair<String, String>)pair2, "item", Registry.f_122827_)).thenComparing((pair, pair2) -> OldToNewLangConverter.tryCompareByRawId((Pair<String, String>)pair, (Pair<String, String>)pair2, "container", Registry.f_122824_)).thenComparing(pair -> (String)pair.left()));
                JsonObject jsonObject = new JsonObject();
                for (Pair pair3 : arrayList) {
                    jsonObject.addProperty((String)pair3.left(), (String)pair3.right());
                }
                OldToNewLangConverter.writeToPath(cachedOutput, (JsonElement)jsonObject, path5);
            }
            catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
        });
    }

    private static void writeToPath(CachedOutput cachedOutput, JsonElement jsonElement, Path path) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HashingOutputStream hashingOutputStream = new HashingOutputStream(Hashing.sha1(), (OutputStream)byteArrayOutputStream);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter((OutputStream)hashingOutputStream, StandardCharsets.UTF_8);
        JsonWriter jsonWriter = new JsonWriter((Writer)outputStreamWriter);
        jsonWriter.setSerializeNulls(false);
        jsonWriter.setIndent("  ");
        GsonHelper.m_216207_((JsonWriter)jsonWriter, (JsonElement)jsonElement, null);
        jsonWriter.close();
        cachedOutput.m_213871_(path, byteArrayOutputStream.toByteArray(), hashingOutputStream.hash());
    }

    private static Map<String, String> createMappings() {
        String string3;
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        String[] stringArray;
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("itemGroup.IC2", "itemGroup.ic2.general");
        String[] stringArray2 = stringArray = new String[]{"basalt", "copper_ore", "lead_ore", "tin_ore", "uranium_ore", "bronze_block", "copper_block", "lead_block", "silver_block", "steel_block", "tin_block", "uranium_block", "reinforced_stone", "machine", "advanced_machine", "reactor_vessel"};
        int n11 = stringArray2.length;
        for (n10 = 0; n10 < n11; ++n10) {
            String string4 = stringArray2[n10];
            hashMap.put("resource." + string4, "block.ic2." + string4);
        }
        String[] stringArray3 = stringArray2 = new String[]{"ashes", "iridium_ore", "iridium_shard", "matter", "resin", "slag", "iodine", "water_sheet", "lava_sheet"};
        n10 = stringArray3.length;
        for (n9 = 0; n9 < n10; ++n9) {
            String string5 = stringArray3[n9];
            hashMap.put("misc_resource." + string5, "item.ic2." + string5);
        }
        String[] stringArray4 = stringArray3 = new String[]{"stone", "lead", "obsidian", "lapis", "lithium", "sulfur", "silicon_dioxide", "diamond", "energium", "coal", "coal_fuel", "iron", "gold", "copper", "tin", "tin_hydrated", "bronze", "clay", "silver", "netherrack", "alloy", "uranium", "refined_iron", "steel", "hydrated_tin"};
        n9 = stringArray4.length;
        for (n8 = 0; n8 < n9; ++n8) {
            String string6 = stringArray4[n8];
            hashMap.put("crushed." + string6, "item.ic2.crushed_" + string6);
            hashMap.put("purified." + string6, "item.ic2.purified_" + string6);
            hashMap.put("plate." + string6, "item.ic2." + string6 + "_plate");
            hashMap.put("plate.dense_" + string6, "item.ic2.dense_" + string6 + "_plate");
            hashMap.put("casing." + string6, "item.ic2." + string6 + "_casing");
            hashMap.put("ingot." + string6, "item.ic2." + string6 + "_ingot");
            hashMap.put("dust." + string6, "item.ic2." + string6 + "_dust");
            hashMap.put("dust.small_" + string6, "item.ic2.small_" + string6 + "_dust");
            hashMap.put("itemCrushed" + OldToNewLangConverter.toCamelCase(string6) + "Ore", "item.ic2.crushed_" + string6);
            hashMap.put("itemPurifiedCrushed" + OldToNewLangConverter.toCamelCase(string6) + "Ore", "item.ic2.purified_" + string6);
            hashMap.put("itemPlate" + OldToNewLangConverter.toCamelCase(string6), "item.ic2." + string6 + "_plate");
            hashMap.put("itemDensePlate" + OldToNewLangConverter.toCamelCase(string6), "item.ic2.dense_" + string6 + "_plate");
            hashMap.put("itemCasing" + OldToNewLangConverter.toCamelCase(string6), "item.ic2." + string6 + "_casing");
            hashMap.put("itemIngot" + OldToNewLangConverter.toCamelCase(string6), "item.ic2." + string6 + "_ingot");
            hashMap.put("itemDust" + OldToNewLangConverter.toCamelCase(string6), "item.ic2." + string6 + "_dust");
            hashMap.put("itemDust" + string6, "item.ic2." + string6 + "_dust");
            hashMap.put("itemDust" + OldToNewLangConverter.toCamelCase(string6) + "Small", "item.ic2.small_" + string6 + "_dust");
            hashMap.put("blockMetal" + OldToNewLangConverter.toCamelCase(string6), "block.ic2." + string6 + "_block");
            hashMap.put("blockOre" + OldToNewLangConverter.toCamelCase(string6), "block.ic2." + string6 + "_ore");
        }
        hashMap.put("itemPlateAdvIron", "item.ic2.steel_plate");
        hashMap.put("itemDensePlateAdvIron", "item.ic2.dense_steel_plate");
        hashMap.put("itemCasingAdvIron", "item.ic2.steel_casing");
        hashMap.put("itemIngotAdvIron", "item.ic2.steel_ingot");
        hashMap.put("blockMetalAdvIron", "block.ic2.steel_block");
        hashMap.put("itemPlateLapi", "item.ic2.lapis_plate");
        hashMap.put("itemDensePlateLapi", "item.ic2.dense_lapis_plate");
        hashMap.put("itemDustLapi", "item.ic2.lapis_dust");
        String[] stringArray5 = stringArray4 = new String[]{"empty", "lava", "water", "air", "hydrated_coal", "biomass", "coalfuel", "biogas", "biofuel", "electrolyzed_water", "coolant", "hydration", "bio", "weed_ex", "pahoehoe_lava"};
        n8 = stringArray5.length;
        for (n7 = 0; n7 < n8; ++n7) {
            String string7 = stringArray5[n7];
            hashMap.put("cell." + string7, "item.ic2." + string7 + "_cell");
            hashMap.put("itemCell" + OldToNewLangConverter.toCamelCase(string7), "item.ic2." + string7 + "_cell");
            hashMap.put("fluid" + OldToNewLangConverter.toCamelCase(string7), "block.ic2." + string7);
        }
        String[] stringArray6 = stringArray5 = new String[]{"rubber", "circuit", "advanced_circuit", "alloy", "scrap", "scrap_box", "coin", "carbon_fibre", "carbon_mesh", "carbon_plate", "iridium", "coal_ball", "coal_block", "coal_chunk", "industrial_diamond", "fertilizer", "bio_chaff", "heat_conductor", "copper_boiler", "cf_powder", "pellet", "coil", "electric_motor", "power_unit", "small_power_unit", "raw_crystal_memory", "bronze_shaft", "iron_shaft", "steel_shaft", "steam_turbine", "steam_turbine_blade", "wood_rotor_blade", "bronze_rotor_blade", "iron_rotor_blade", "steel_rotor_blade", "carbon_rotor_blade", "empty_fuel_can", "compressed_hydrated_coal", "plant_ball", "compressed_plants", "tin_can", "crystal_memory", "fuel_rod", "wooden_rotor_blade", "jetpack_attachment_plate"};
        n7 = stringArray6.length;
        for (n6 = 0; n6 < n7; ++n6) {
            String string8 = stringArray6[n6];
            hashMap.put("crafting." + string8, "item.ic2." + string8);
            hashMap.put("item" + OldToNewLangConverter.toCamelCase(string8), "item.ic2." + string8);
            hashMap.put("item" + string8.replaceAll("_", ""), "item.ic2." + string8);
        }
        hashMap.put("crystal_memory", "item.ic2.crystal_memory");
        hashMap.put("item.CrystalMemory.tooltip.Empty", "item.ic2.crystal_memory.tooltip.empty");
        hashMap.put("item.CrystalMemory.tooltip.Item", "item.ic2.crystal_memory.tooltip.item");
        hashMap.put("item.CrystalMemory.tooltip.Energy", "item.ic2.crystal_memory.tooltip.energy");
        hashMap.put("item.CrystalMemory.tooltip.UU-Matter", "item.ic2.crystal_memory.tooltip.uu_matter");
        String[] stringArray7 = stringArray6 = new String[]{"fertilizer", "coffee_beans", "coffee_powder", "hops", "grin_powder", "weed"};
        n6 = stringArray7.length;
        for (n5 = 0; n5 < n6; ++n5) {
            String string9 = stringArray7[n5];
            hashMap.put("crop_res." + string9, "item.ic2." + string9);
            hashMap.put("item" + OldToNewLangConverter.toCamelCase(string9), "item.ic2." + string9);
            hashMap.put("item" + string9.replaceAll("_", ""), "item.ic2." + string9);
        }
        String[] stringArray8 = stringArray7 = new String[]{"bronze", "alloy", "hazmat", "rubber", "static", "solar", "nano", "quantum"};
        n5 = stringArray8.length;
        for (n4 = 0; n4 < n5; ++n4) {
            String string10 = stringArray8[n4];
            hashMap.put(string10 + "_helmet", "item.ic2." + string10 + "_helmet");
            hashMap.put(string10 + "_chestplate", "item.ic2." + string10 + "_chestplate");
            hashMap.put(string10 + "_leggings", "item.ic2." + string10 + "_leggings");
            hashMap.put(string10 + "_boots", "item.ic2." + string10 + "_boots");
            hashMap.put("itemArmor" + OldToNewLangConverter.toCamelCase(string10) + "Helmet", "item.ic2." + string10 + "_helmet");
            hashMap.put("itemArmor" + OldToNewLangConverter.toCamelCase(string10) + "Chestplate", "item.ic2." + string10 + "_chestplate");
            hashMap.put("itemArmor" + OldToNewLangConverter.toCamelCase(string10) + "Leggings", "item.ic2." + string10 + "_leggings");
            hashMap.put("itemArmor" + OldToNewLangConverter.toCamelCase(string10) + "Boots", "item.ic2." + string10 + "_boots");
        }
        hashMap.put("cf_pack", "item.ic2.cf_pack");
        hashMap.put("nightvision_goggles", "item.ic2.nightvision_goggles");
        hashMap.put("jetpack", "item.ic2.jetpack");
        hashMap.put("itemArmorJetpack", "item.ic2.jetpack");
        hashMap.put("itemArmorJetpackElectric", "item.ic2.electric_jetpack");
        hashMap.put("itemArmorRubBoots", "item.ic2.rubber_boots");
        hashMap.put("itemArmorCFPack", "item.ic2.cf_pack");
        hashMap.put("itemNightvisionGoggles", "item.ic2.nightvision_goggles");
        hashMap.put("itemSolarHelmet", "item.ic2.solar_helmet");
        String[] stringArray9 = stringArray8 = new String[]{"drill", "diamond_drill", "iridium_drill", "chainsaw", "scanner", "advanced_scanner", "wrench", "wrench_new", "forge_hammer", "electric_hoe", "mining_laser", "bronze_pickaxe", "bronze_axe", "bronze_sword", "bronze_shovel", "bronze_hoe", "cutter", "nano_saber", "treetap", "foam_sprayer", "electric_wrench", "electric_treetap", "frequency_transmitter", "debug_item", "obscurator", "cropnalyzer", "wind_meter", "lathing_tool", "weeding_trowel", "tool_box", "crowbar", "painter", "meter", "terra_wart", "rotor_wood", "rotor_bronze", "rotor_iron", "rotor_steel", "rotor_carbon", "filled_tin_can", "filled_fuel_can"};
        n4 = stringArray9.length;
        for (n3 = 0; n3 < n4; ++n3) {
            String string11 = stringArray9[n3];
            hashMap.put(string11, "item.ic2." + string11);
            hashMap.put("item" + OldToNewLangConverter.toCamelCase(string11), "item.ic2." + string11);
            hashMap.put("itemTool" + OldToNewLangConverter.toCamelCase(string11), "item.ic2." + string11);
        }
        hashMap.put("item.ItemTool.tooltip.UsesLeft", "ic2.tooltip.tool.uses_left");
        String[] stringArray10 = stringArray9 = new String[]{"uranium_fuel_rod", "dual_uranium_fuel_rod", "quad_uranium_fuel_rod", "mox_fuel_rod", "dual_mox_fuel_rod", "quad_mox_fuel_rod", "depleted_uranium", "depleted_dual_uranium", "depleted_quad_uranium", "depleted_mox", "depleted_dual_mox", "depleted_quad_mox", "lithium_fuel_rod", "tritium_cell", "uranium", "mox", "plutonium", "small_plutonium", "uranium_235", "small_uranium_235", "uranium_238", "small_uranium_238", "uranium_pellet", "mox_pellet", "rtg_pellet", "near_depleted_uranium", "depleted_isotope_fuel_rod", "re_enriched_uranium", "heat_storage", "tri_heat_storage", "hex_heat_storage", "plating", "heat_plating", "containment_plating", "heat_exchanger", "reactor_heat_exchanger", "component_heat_exchanger", "advanced_heat_exchanger", "heat_vent", "reactor_heat_vent", "overclocked_heat_vent", "component_heat_vent", "advanced_heat_vent", "neutron_reflector", "iridium_reflector", "thick_neutron_reflector", "rsh_condensator", "lzh_condensator", "heatpack"};
        n3 = stringArray10.length;
        for (n2 = 0; n2 < n3; ++n2) {
            String string12 = stringArray10[n2];
            hashMap.put(string12, "item.ic2." + string12);
            hashMap.put("nuclear." + string12, "item.ic2." + string12);
            hashMap.put("item" + OldToNewLangConverter.toCamelCase(string12), "item.ic2." + string12);
            hashMap.put("reactor" + OldToNewLangConverter.toCamelCase(string12), "item.ic2." + string12);
        }
        hashMap.put("reactoritem.durability", "ic2.reactoritem.durability");
        String[] stringArray11 = stringArray10 = new String[]{"overclocker", "transformer", "energy_storage", "ejector", "advanced_ejector", "pulling", "advanced_pulling", "fluid_ejector", "fluid_pulling", "redstone_inverter", "remote_interface"};
        n2 = stringArray11.length;
        for (n = 0; n < n2; ++n) {
            String string13 = stringArray11[n];
            hashMap.put("upgrade." + string13, "item.ic2." + string13 + "_upgrade");
            hashMap.put(OldToNewLangConverter.toCamelCase(string13) + "Upgrade", "item.ic2." + string13 + "_upgrade");
        }
        String[] stringArray12 = stringArray11 = new String[]{"blank", "cultivation", "irrigation", "desertification", "chilling", "flatification", "mushroom"};
        n = stringArray12.length;
        for (int i = 0; i < n; ++i) {
            String string14 = stringArray12[i];
            hashMap.put("tfbp." + string14, "item.ic2." + string14 + "_tfbp");
            hashMap.put("itemTFBP" + OldToNewLangConverter.toCamelCase(string14), "item.ic2." + string14 + "_tfbp");
        }
        for (String string15 : stringArray12 = new String[]{"single_use_battery", "re_battery", "advanced_re_battery", "energy_crystal", "lapotron_crystal", "charging_re_battery", "advanced_charging_re_battery", "charging_energy_crystal", "charging_lapotron_crystal"}) {
            hashMap.put(string15, "item.ic2." + string15);
        }
        hashMap.put("itemBatSU", "item.ic2.single_use_battery");
        hashMap.put("itemBatRE", "item.ic2.re_battery");
        hashMap.put("itemBatCrystal", "item.ic2.energy_crystal");
        hashMap.put("itemBatLamaCrystal", "item.ic2.lapotron_crystal");
        String[] stringArray13 = new String[]{"empty", "cold_coffee", "dark_coffee", "coffee"};
        for (String object : stringArray13) {
            hashMap.put("mug." + object, "item.ic2." + object + "_mug");
        }
        hashMap.put("itemMugEmpty", "item.ic2.empty_mug");
        hashMap.put("itemMugCoffee_0", "item.ic2.cold_coffee_mug");
        hashMap.put("itemMugCoffee_1", "item.ic2.dark_coffee_mug");
        hashMap.put("itemMugCoffee_2", "item.ic2.coffee_mug");
        hashMap.put("rubber_wood", "block.ic2.rubber_log");
        hashMap.put("leaves.rubber", "block.ic2.rubber_leaves");
        hashMap.put("sapling.rubber", "block.ic2.rubber_sapling");
        hashMap.put("mining_pipe.pipe", "block.ic2.mining_pipe");
        hashMap.put("scaffold.wood", "block.ic2.wooden_scaffold");
        hashMap.put("scaffold.reinforced_wood", "block.ic2.reinforced_wooden_scaffold");
        hashMap.put("scaffold.iron", "block.ic2.iron_scaffold");
        hashMap.put("scaffold.reinforced_iron", "block.ic2.reinforced_iron_scaffold");
        hashMap.put("fence.iron", "block.ic2.iron_fence");
        hashMap.put("sheet.rubber", "block.ic2.rubber_sheet");
        hashMap.put("sheet.resin", "block.ic2.resin_sheet");
        hashMap.put("sheet.wool", "block.ic2.wool_sheet");
        hashMap.put("glass.reinforced", "block.ic2.reinforced_glass");
        hashMap.put("foam.normal", "block.ic2.foam");
        hashMap.put("foam.reinforced", "block.ic2.reinforced_foam");
        for (String string4 : DyeColor.values()) {
            hashMap.put("wall." + string4.m_41065_(), "block.ic2." + string4.m_41065_() + "_wall");
        }
        hashMap.put("reinforced_door", "block.ic2.reinforced_door");
        for (String string5 : new String[]{"itnt", "nuke"}) {
            hashMap.put("te." + string5, "block.ic2." + string5);
        }
        for (String string6 : new String[]{"copper", "gold", "iron", "tin", "detector", "splitter"}) {
            hashMap.put("cable." + string6 + "_cable", "block.ic2." + string6 + "_cable");
            hashMap.put("cable." + string6 + "_cable_0", "block.ic2." + string6 + "_cable");
            hashMap.put("cable." + string6 + "_cable_1", "block.ic2.insulated_" + string6 + "_cable");
            hashMap.put("cable." + string6 + "_cable_2", "block.ic2.double_insulated_" + string6 + "_cable");
            hashMap.put("cable." + string6 + "_cable_3", "block.ic2.triple_insulated_" + string6 + "_cable");
        }
        hashMap.put("cable.glass_cable", "block.ic2.glass_fibre_cable");
        hashMap.put("cable.tooltip.loss ", "ic2.cable.tooltip.loss");
        for (Block block : Registry.f_122824_) {
            String string7;
            ResourceLocation resourceLocation = Registry.f_122824_.m_7981_((Object)block);
            if (!resourceLocation.m_135827_().equals("ic2")) continue;
            string3 = switch (string7 = resourceLocation.m_135815_()) {
                case "uu_scanner" -> "scanner";
                case "classic_cropmatron" -> "cropmatron";
                default -> string7;
            };
            hashMap.put("te." + string3, "block.ic2." + string7);
            hashMap.put("block" + OldToNewLangConverter.toCamelCase(string3), "block.ic2." + string7);
            String string8 = switch (string3) {
                case "advanced_miner" -> "AdvMiner";
                case "electric_furnace" -> "ElecFurnace";
                case "induction_furnace" -> "Induction";
                case "matter_generator" -> "Matter";
                case "ore_washing_plant" -> "OreWashingPlan";
                case "crop_harvester" -> "CropHavester";
                case "bat_box" -> "BatBox";
                case "cesu" -> "CESU";
                case "mfe" -> "MFE";
                case "mfsu" -> "MFSU";
                default -> OldToNewLangConverter.toCamelCase(string3);
            };
            hashMap.put(string8 + ".gui.name", "container.ic2." + string7);
            hashMap.put("block" + string8, "block.ic2." + string7);
        }
        for (String string9 : new String[]{"batbox", "cesu", "mfe", "mfsu"}) {
            hashMap.put("te.chargepad_" + (String)string9, "block.ic2." + (String)string9 + "_chargepad");
            string3 = "batbox".equals(string9) ? "BatBox" : string9.toUpperCase(Locale.ROOT);
            hashMap.put("blockChargepad" + string3, "block.ic2." + (String)string9 + "_chargepad");
        }
        hashMap.put("crop_stick", "block.ic2.crop");
        hashMap.put("blockCrop", "block.ic2.crop");
        hashMap.replaceAll((string, string2) -> OldToNewLangConverter.fix(string2));
        return hashMap;
    }

    private static String fix(String string) {
        return switch (string) {
            case "item.ic2.tin_hydrated_dust" -> "item.ic2.hydrated_tin_dust";
            case "item.ic2.alloy_ingot" -> "item.ic2.mixed_metal_ingot";
            case "item.ic2.depleted_uranium" -> "item.ic2.depleted_uranium_fuel_rod";
            case "item.ic2.depleted_dual_uranium" -> "item.ic2.depleted_dual_uranium_fuel_rod";
            case "item.ic2.depleted_quad_uranium" -> "item.ic2.depleted_quad_uranium_fuel_rod";
            case "item.ic2.depleted_mox" -> "item.ic2.depleted_mox_fuel_rod";
            case "item.ic2.depleted_dual_mox" -> "item.ic2.depleted_dual_mox_fuel_rod";
            case "item.ic2.depleted_quad_mox" -> "item.ic2.depleted_quad_mox_fuel_rod";
            case "item.ic2.iridium_reflector" -> "item.ic2.iridium_neutron_reflector";
            case "item.ic2.heat_storage" -> "item.ic2.reactor_coolant_cell";
            case "item.ic2.tri_heat_storage" -> "item.ic2.triple_reactor_coolant_cell";
            case "item.ic2.hex_heat_storage" -> "item.ic2.sextuple_reactor_coolant_cell";
            case "item.ic2.plating" -> "item.ic2.reactor_plating";
            case "item.ic2.heat_plating" -> "item.ic2.reactor_heat_plating";
            case "item.ic2.containment_plating" -> "item.ic2.containment_reactor_plating";
            case "item.ic2.matter" -> "item.ic2.uu_matter";
            case "item.ic2.wood_rotor_blade" -> "item.ic2.wooden_rotor_blade";
            case "item.ic2.rotor_wood" -> "item.ic2.wooden_rotor";
            case "item.ic2.rotor_bronze" -> "item.ic2.bronze_rotor";
            case "item.ic2.rotor_iron" -> "item.ic2.iron_rotor";
            case "item.ic2.rotor_steel" -> "item.ic2.steel_rotor";
            case "item.ic2.rotor_carbon" -> "item.ic2.carbon_rotor";
            default -> string;
        };
    }

    private static String toCamelCase(String string) {
        return (String)CaseFormat.LOWER_UNDERSCORE.converterTo(CaseFormat.UPPER_CAMEL).convert((Object)string);
    }

    private static <T> int tryCompareByRawId(Pair<String, String> pair, Pair<String, String> pair2, String string, Registry<T> registry) {
        if (((String)pair.left()).startsWith(string + ".ic2")) {
            if (((String)pair2.left()).startsWith(string + ".ic2")) {
                ResourceLocation resourceLocation = OldToNewLangConverter.extractIdentifier((String)pair.left());
                ResourceLocation resourceLocation2 = OldToNewLangConverter.extractIdentifier((String)pair2.left());
                int n = registry.m_6612_(resourceLocation).map(arg_0 -> registry.m_7447_(arg_0)).orElse(Integer.MAX_VALUE);
                int n2 = registry.m_6612_(resourceLocation2).map(arg_0 -> registry.m_7447_(arg_0)).orElse(Integer.MAX_VALUE);
                return Integer.compare(n, n2);
            }
            return -1;
        }
        return ((String)pair2.left()).startsWith(string + ".ic2") ? 1 : 0;
    }

    private static ResourceLocation extractIdentifier(String string) {
        String[] stringArray = string.split("\\.");
        return new ResourceLocation(stringArray[1], stringArray[2]);
    }
}

