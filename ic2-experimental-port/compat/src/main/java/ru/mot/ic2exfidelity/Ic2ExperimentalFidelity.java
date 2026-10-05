package ru.mot.ic2exfidelity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;
import ru.mot.ic2exfidelity.friends.LegacyFriendContent;
import ru.mot.ic2exfidelity.friends.LegacyFriendNetwork;
import ru.mot.ic2exfidelity.integration.LegacyCropContent;
import ru.mot.ic2exfidelity.integration.LegacyJetpackContent;
import ru.mot.ic2exfidelity.integration.SmokeFunctionalChecks;

@Mod(Ic2ExperimentalFidelity.MOD_ID)
public final class Ic2ExperimentalFidelity {
    public static final String MOD_ID = "ic2_experimental_fidelity";
    private static final List<String> RESTORED_RECIPE_IDS = List.of(
            "ic2:shaped/teleporter",
            "ic2:shaped/centrifuge",
            "ic2:shaped/metal_former",
            "ic2:shaped/pattern_storage",
            "ic2:shaped/nano_helmet",
            "ic2:shaped/carbon_boat",
            "ic2:shaped/rubber_boat",
            "ic2:shaped/electric_boat",
            "ic2:shaped/copper_ingot_from_block",
            "ic2:shaped/iron_cutting_blade",
            "ic2:shaped/steel_cutting_blade",
            "ic2:shaped/diamond_cutting_blade",
            "ic2:shaped/chainsaw",
            "ic2:shaped/wind_meter",
            "ic2:shaped/meter",
            "ic2:shaped/mining_laser",
            "ic2:shaped/painter",
            "ic2:shaped/tool_box",
            "ic2:shaped/component_heat_exchanger",
            "ic2:shaped/component_heat_vent",
            "ic2:shaped/item_buffer_2",
            "ic2:shaped/rci_lzh",
            "ic2:shaped/rci_rsh",
            "ic2:shaped/crowbar",
            "ic2:shaped/charging_re_battery",
            "ic2:shaped/advanced_charging_re_battery",
            "ic2:shaped/charging_energy_crystal",
            "ic2:shaped/charging_lapotron_crystal",
            "ic2:shaped/advanced_batpack",
            "ic2:shaped/batpack",
            "ic2:shaped/energy_pack",
            "ic2:shapeless/jetpack_attachment",
            "ic2:shaped/electric_hoe",
            "ic2:shaped/cropnalyzer",
            "ic2:shaped/crop_harvester",
            "ic2:shaped/foam_sprayer",
            "ic2:shaped/cf_pack",
            "ic2:shaped/jetpack_electric",
            "ic2:shaped/quantum_chestplate",
            "ic2:shaped/weeding_trowel",
            "ic2:shaped/solar_helmet",
            "ic2:shaped/solar_helmet_alt",
            "ic2:shaped/static_boots",
            "ic2:shaped/static_boots_alt",
            "ic2:shaped/diamond_dust_from_small_dusts",
            "ic2:shaped/emerald_dust_from_small_dusts",
            "ic2:macerator/ender_pearl_to_ender_pearl_dust",
            "ic2:macerator/ender_eye_to_ender_eye_dust",
            "ic2:macerator/emerald_to_emerald_dust",
            "ic2:extractor/bobs_yer_uncle_ranks_berry_to_small_emerald_dust",
            "ic2:shaped/mfsu_upgrade_kit",
            "ic2:shaped/containment_box",
            "ic2:shaped/fluid_cell",
            "ic2:shaped/jetpack",
            "ic2:shaped/reactor_fluid_port",
            "ic2:shaped/geo_generator",
            "ic2:shaped/semifluid_generator",
            "ic2:shaped/fluid_heat_generator",
            "ic2:shaped/steam_kinetic_generator",
            "ic2:shaped/fluid_bottler",
            "ic2:shaped/liquid_heat_exchanger",
            "ic2:shaped/fermenter",
            "ic2:shaped/fluid_regulator",
            "ic2:shaped/condenser",
            "ic2:shaped/solar_distiller",
            "ic2:shaped/fluid_distributor",
            "ic2:shaped/cropmatron",
            "ic2:shaped/electrolyzer",
            "ic2:shaped/pump",
            "ic2:shaped/weighted_fluid_distributor",
            "ic2:shaped/bronze_tank",
            "ic2:shaped/iron_tank",
            "ic2:shaped/steel_tank",
            "ic2:shaped/iridium_tank",
            "ic2:shaped/weed_ex_cell",
            "ic2:shapeless/water_cell",
            "ic2:shapeless/lava_cell",
            "ic2:shapeless/frequency_transmitter",
            "ic2:shapeless/iodine_tablet",
            "ic2:shapeless/rubber_boat_repair",
            "ic2:shapeless/painter_reset",
            "ic2:shapeless/dynamite",
            "ic2:shaped/dynamite_sticky",
            "ic2:shaped/remote",
            "ic2:shaped/remote_alt",
            "ic2:shaped/luminator_flat",
            "ic2:shaped/scanner",
            "ic2:shaped/night_vision_goggles",
            "ic2:shaped/refractory_bricks",
            "ic2:shapeless/coke_kiln",
            "ic2:shapeless/coke_kiln_hatch",
            "ic2:shapeless/coke_kiln_grate",
            "ic2:shaped/barrel",
            "ic2:shapeless/barrel_reset",
            "ic2:shaped/bronze_pipe_tiny",
            "ic2:shaped/bronze_pipe_small",
            "ic2:shaped/bronze_pipe_medium",
            "ic2:shaped/bronze_pipe_large",
            "ic2:shaped/steel_pipe_tiny",
            "ic2:shaped/steel_pipe_small",
            "ic2:shaped/steel_pipe_medium",
            "ic2:shaped/steel_pipe_large",
            "ic2:shapeless/pump_lv",
            "ic2:shapeless/pump_mv",
            "gravisuit:super_conductor_cover",
            "gravisuit:super_conductor",
            "gravisuit:cooling_core",
            "gravisuit:gravitation_engine",
            "gravisuit:magnetron",
            "gravisuit:vajra_core",
            "gravisuit:engine_boost",
            "gravisuit:advanced_electric_jetpack",
            "gravisuit:advanced_nuclear_jetpack_upgrade",
            "gravisuit:advanced_nuclear_jetpack",
            "gravisuit:gravitation_jetpack",
            "gravisuit:nuclear_gravitation_jetpack",
            "gravisuit:nuclear_gravitation_jetpack_upgrade",
            "gravisuit:advanced_lappack",
            "gravisuit:gravitool",
            "gravisuit:vajra",
            "ic2:shaped/nuclear_jetpack",
            "ic2:shaped/compacted_electric_jetpack",
            "ic2:shaped/compacted_nuclear_jetpack",
            "ic2:shaped/compacted_nuclear_jetpack_alt",
            "advanced_solars:sunnarium",
            "advanced_solars:sunnarium_alloy",
            "advanced_solars:iridium_ore_to_iridium_ingot",
            "advanced_solars:iridium_iron_plate",
            "advanced_solars:reinforced_iridium_iron_plate",
            "advanced_solars:irradiant_reinforced_plate",
            "advanced_solars:sunnarium_part",
            "advanced_solars:irradiant_uranium",
            "advanced_solars:enriched_sunnarium",
            "advanced_solars:irradiant_glass_pane",
            "advanced_solars:advanced_solar_panel",
            "ic2:shaped/bronze_cable",
            "ic2:shaped/insulated_bronze_cable_0",
            "ic2:shaped/insulated_bronze_cable_1",
            "ic2:shapeless/insulated_bronze_cable_2",
            "ic2:shaped/2x_insulated_bronze_cable_0",
            "ic2:shaped/2x_insulated_bronze_cable_1",
            "ic2:shapeless/2x_insulated_bronze_cable_2",
            "ic2:shapeless/2x_insulated_bronze_cable_3",
            "ic2:shaped/advanced_solar_helmet",
            "advanced_solars:advanced_solar_helmet",
            "ic2:shaped/iridium_stone",
            "ic2:shaped/plasmafier",
            "ic2:shaped/rare_earth_extractor",
            "ic2:shaped/rare_earth_chunk",
            "ic2:compressor/magnet_creation",
            "ic2:shaped/plasma_core",
            "ic2:shaped/pesd",
            "ic2:shaped/plasma_cable",
            "ic2:shaped/iv_transformer",
            "ic2:shaped/hv_solar_panel",
            "ic2:shaped/mv_solar_panel",
            "advanced_solars:hybrid_solar_panel",
            "advanced_solars:hybrid_solar_helmet",
            "advanced_solars:enriched_sunnarium_alloy",
            "advanced_solars:ultimate_hybrid_solar_panel",
            "advanced_solars:ultimate_hybrid_solar_helmet",
            "advanced_solars:mt_core",
            "advanced_solars:molecular_transformer",
            "gravisuit:ultimate_lappack",
            "gravisuit:relocator",
            "gravisuit:gravitool_upgrade",
            "ic2:shaped/quantum_pack",
            "ic2:shaped/memory_stick",
            "ic2:shaped/precision_wrench",
            "ic2:shaped/advanced_chainsaw",
            "ic2:shaped/advanced_drill",
            "ic2:shaped/portable_teleporter",
            "ic2:shaped/glowtronic_crystal",
            "ic2:shaped/upgrade_base_0",
            "ic2:shaped/complex_circuit",
            "ic2:shaped/advanced_field_upgrade",
            "ic2:shaped/efficiency_upgrade",
            "ic2:shaped/basic_field_upgrade",
            "ic2:shaped/field_upgrade",
            "ic2:shaped/simple_import_upgrade_1",
            "ic2:shapeless/electric_treetap_upgrade",
            "ic2:shapeless/pulsating_quartz_compat",
            "ic2:shapeless/electric_wrench",
            "ic2:shaped/batch_crafter",
            "ic2:canner_enrich/milk");
    private static final List<String> FLUID_CELL_RECIPE_IDS = List.of(
            "ic2:shaped/jetpack",
            "ic2:shaped/reactor_fluid_port",
            "ic2:shaped/geo_generator",
            "ic2:shaped/semifluid_generator",
            "ic2:shaped/fluid_heat_generator",
            "ic2:shaped/steam_kinetic_generator",
            "ic2:shaped/fluid_bottler",
            "ic2:shaped/liquid_heat_exchanger",
            "ic2:shaped/fermenter",
            "ic2:shaped/fluid_regulator",
            "ic2:shaped/condenser",
            "ic2:shaped/solar_distiller",
            "ic2:shaped/fluid_distributor",
            "ic2:shaped/cropmatron",
            "ic2:shaped/electrolyzer",
            "ic2:shaped/pump",
            "ic2:shaped/weighted_fluid_distributor",
            "ic2:shaped/bronze_tank",
            "ic2:shaped/iron_tank",
            "ic2:shaped/steel_tank",
            "ic2:shaped/iridium_tank",
            "ic2:shaped/weed_ex_cell",
            "ic2:shaped/cf_pack",
            "ic2:shaped/foam_sprayer",
            "ic2:shapeless/water_cell",
            "ic2:shapeless/lava_cell");
    private static final List<String> TRANSLATED_LEGACY_RECIPE_IDS = loadLegacyRecipeIds();

    public Ic2ExperimentalFidelity() {
        // 2.8.222 exposed milk as an IC2 canner fluid. Forge's shared milk
        // registry entry avoids a duplicate-fluid conflict on 1.19.2.
        ForgeMod.enableMilkFluid();
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        RestoredLegacyContent.register(modBus);
        LegacyFriendContent.register(modBus);
        LegacyFriendNetwork.install();
        LegacyCropContent.register(modBus);
        LegacyJetpackContent.register(modBus);
        modBus.addListener(RestoredLegacyContent::onCommonSetup);
        modBus.addListener(this::onCropCommonSetup);
        modBus.addListener(this::onJetpackCommonSetup);
        modBus.addListener(this::onLoadComplete);
        MinecraftForge.EVENT_BUS.addListener(this::onAddReloadListeners);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarted);
        MinecraftForge.EVENT_BUS.addListener(RestoredLegacyContent::onFuelBurnTime);
    }

    private void onCropCommonSetup(net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent event) {
        event.enqueueWork(LegacyCropContent::install);
    }

    private void onJetpackCommonSetup(net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent event) {
        event.enqueueWork(LegacyJetpackContent::install);
    }

    private void onLoadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(RestoredLegacyContent::restoreMatterAmplifiers);
        String items = ForgeRegistries.ITEMS.getKeys().stream()
                .filter(id -> id.toString().startsWith("ic2:"))
                .sorted(Comparator.comparing(Object::toString))
                .map(Object::toString)
                .collect(Collectors.joining(","));
        String blocks = ForgeRegistries.BLOCKS.getKeys().stream()
                .filter(id -> id.toString().startsWith("ic2:"))
                .sorted(Comparator.comparing(Object::toString))
                .map(Object::toString)
                .collect(Collectors.joining(","));

        System.out.println("[IC2-FIDELITY-REGISTRY-ITEMS] " + items);
        System.out.println("[IC2-FIDELITY-REGISTRY-BLOCKS] " + blocks);
    }

    private void onAddReloadListeners(AddReloadListenerEvent event) {
        System.out.println("[IC2-FIDELITY-RELOAD] datapack reload listener phase reached");
    }

    private void onServerStarted(ServerStartedEvent event) {
        // TileEntityMatter initializes its manager lazily while the server is
        // being constructed, after mod lifecycle events on some launch paths.
        RestoredLegacyContent.restoreMatterAmplifiers();
        if (System.getProperty("ic2.fidelity.smokeWorld") == null) {
            return;
        }

        Set<String> loaded = event.getServer().m_129894_().m_44073_()
                .map(Object::toString)
                .collect(Collectors.toSet());
        List<String> expectedRecipeIds = Stream.concat(
                RESTORED_RECIPE_IDS.stream(), TRANSLATED_LEGACY_RECIPE_IDS.stream())
                .distinct()
                .toList();
        List<String> missing = expectedRecipeIds.stream()
                .filter(id -> !loaded.contains(id))
                .toList();
        System.out.println("[IC2-FIDELITY-RECIPE-CHECK] loaded="
                + (expectedRecipeIds.size() - missing.size())
                + "/" + expectedRecipeIds.size()
                + " missing=" + String.join(",", missing));
        if (!missing.isEmpty()) {
            throw new IllegalStateException("Restored IC2 recipes failed to load: " + missing);
        }
        verifyFluidCellRecipes(event);
        if (Boolean.getBoolean("ic2.fidelity.nativeIUTest")) {
            ru.mot.ic2exfidelity.iu.NativeIUChecks.run(event.getServer().m_129783_());
            return;
        }
        SmokeFunctionalChecks.run(event.getServer().m_129783_(), event.getServer().m_129894_());
    }

    private static void verifyFluidCellRecipes(ServerStartedEvent event) {
        Item fluidCell = ForgeRegistries.ITEMS.getValue(new ResourceLocation("ic2", "fluid_cell"));
        Item emptyCell = ForgeRegistries.ITEMS.getValue(new ResourceLocation("ic2", "empty_cell"));
        ItemStack fluidStack = new ItemStack(fluidCell);
        ItemStack emptyStack = new ItemStack(emptyCell);
        List<String> invalid = FLUID_CELL_RECIPE_IDS.stream().filter(id -> {
            Recipe<?> recipe = event.getServer().m_129894_().m_44043_(new ResourceLocation(id)).orElse(null);
            if (recipe == null) {
                return true;
            }
            boolean acceptsFluidCell = recipe.m_7527_().stream().anyMatch(ingredient -> ingredient.test(fluidStack));
            boolean acceptsEmptyCell = recipe.m_7527_().stream().anyMatch(ingredient -> ingredient.test(emptyStack));
            return !acceptsFluidCell || acceptsEmptyCell;
        }).toList();
        System.out.println("[IC2-FIDELITY-FLUID-RECIPES] valid="
                + (FLUID_CELL_RECIPE_IDS.size() - invalid.size())
                + "/" + FLUID_CELL_RECIPE_IDS.size()
                + " invalid=" + String.join(",", invalid));
        if (!invalid.isEmpty()) {
            throw new IllegalStateException("Legacy fluid-cell recipe overrides failed: " + invalid);
        }
    }

    private static List<String> loadLegacyRecipeIds() {
        String path = "/META-INF/ic2-fidelity-legacy-recipes.txt";
        try (InputStream stream = Ic2ExperimentalFidelity.class.getResourceAsStream(path)) {
            if (stream == null) {
                throw new IllegalStateException("Missing legacy recipe manifest: " + path);
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                return reader.lines()
                        .map(String::trim)
                        .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                        .toList();
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read legacy recipe manifest: " + path, exception);
        }
    }
}
