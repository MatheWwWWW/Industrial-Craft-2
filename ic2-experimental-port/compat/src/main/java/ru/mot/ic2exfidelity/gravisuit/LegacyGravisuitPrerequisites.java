package ru.mot.ic2exfidelity.gravisuit;

import ic2.core.IC2;
import ic2.core.item.ItemBattery;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/** IC2 Classic jetpacks required by GraviSuite's unchanged recipe graph. */
public final class LegacyGravisuitPrerequisites {
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, "ic2");

    public static final RegistryObject<Item> COMPACTED_ELECTRIC_JETPACK = ITEMS.register(
            "compacted_electric_jetpack",
            () -> LegacyGravisuitJetpack.compactedElectric(properties()));
    public static final RegistryObject<Item> NUCLEAR_JETPACK = ITEMS.register(
            "nuclear_jetpack",
            () -> LegacyGravisuitJetpack.nuclear(properties()));
    public static final RegistryObject<Item> COMPACTED_NUCLEAR_JETPACK = ITEMS.register(
            "compacted_nuclear_jetpack",
            () -> LegacyGravisuitJetpack.compactedNuclear(properties()));
    public static final RegistryObject<Item> GLOWTRONIC_CRYSTAL = ITEMS.register(
            "glowtronic_crystal",
            () -> new ItemBattery(properties(), 7_500_000.0, 2_500.0, 3));
    public static final RegistryObject<Item> QUANTUM_PACK = ITEMS.register(
            "quantum_pack",
            () -> new LegacyGravisuitEnergyPack(
                    properties().m_41497_(Rarity.RARE),
                    1_200_000.0, 1_000.0, 3,
                    "ic2:textures/models/armor/quantumpack_1.png"));
    public static final RegistryObject<Item> MEMORY_STICK = ITEMS.register(
            "memory_stick", () -> new LegacyMemoryStick(properties()));
    public static final RegistryObject<Item> PORTABLE_TELEPORTER = ITEMS.register(
            "portable_teleporter", () -> new LegacyPortableTeleporter(properties()));
    public static final RegistryObject<Item> PRECISION_WRENCH = ITEMS.register(
            "precision_wrench", () -> new LegacyPrecisionWrench(properties()));
    public static final RegistryObject<Item> ADVANCED_DRILL = ITEMS.register(
            "advanced_drill", () -> new LegacyAdvancedDrill(properties()));
    public static final RegistryObject<Item> ADVANCED_CHAINSAW = ITEMS.register(
            "advanced_chainsaw", () -> new LegacyAdvancedChainsaw(properties()));

    /* Crafting-only Classic components retained under their original ids. */
    public static final RegistryObject<Item> UPGRADE_BASE = component("upgrade_base");
    public static final RegistryObject<Item> PULSATING_QUARTZ = component("pulsating_quartz");
    public static final RegistryObject<Item> COMPLEX_CIRCUIT = component("complex_circuit");
    public static final RegistryObject<Item> EFFICIENCY_UPGRADE = component("efficiency_upgrade");
    public static final RegistryObject<Item> BASIC_FIELD_PAD_UPGRADE =
            component("basic_field_expansion_pad_upgrade");
    public static final RegistryObject<Item> FIELD_PAD_UPGRADE =
            component("field_expansion_pad_upgrade");
    public static final RegistryObject<Item> ADVANCED_FIELD_PAD_UPGRADE =
            component("advanced_field_expansion_pad_upgrade");
    public static final RegistryObject<Item> SIMPLE_IMPORT = component("simple_import");

    private LegacyGravisuitPrerequisites() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }

    private static Item.Properties properties() {
        return new Item.Properties().m_41487_(1).m_41491_(IC2.tabIC2);
    }

    private static RegistryObject<Item> component(String id) {
        return ITEMS.register(id, () -> new Item(new Item.Properties().m_41491_(IC2.tabIC2)));
    }
}
