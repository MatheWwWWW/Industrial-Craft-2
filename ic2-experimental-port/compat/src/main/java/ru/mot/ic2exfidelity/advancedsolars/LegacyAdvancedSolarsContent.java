package ru.mot.ic2exfidelity.advancedsolars;

import ic2.core.IC2;
import ic2.core.block.tileentity.Ic2TileEntityBlock;
import ic2.core.util.Util;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Stable Advanced Solars registry ids, materials and generator blocks. */
public final class LegacyAdvancedSolarsContent {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
            ForgeRegistries.BLOCKS, LegacyAdvancedSolarsMod.MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS, LegacyAdvancedSolarsMod.MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
            ForgeRegistries.BLOCK_ENTITY_TYPES, LegacyAdvancedSolarsMod.MOD_ID);

    public static final RegistryObject<Ic2TileEntityBlock> ADVANCED_SOLAR_PANEL = solarBlock(
            "advanced_solar_panel", LegacyAdvancedSolarPanelBlockEntity.Advanced.class);
    public static final RegistryObject<Ic2TileEntityBlock> HYBRID_SOLAR_PANEL = solarBlock(
            "hybrid_solar_panel", LegacyAdvancedSolarPanelBlockEntity.Hybrid.class);
    public static final RegistryObject<Ic2TileEntityBlock> ULTIMATE_HYBRID_SOLAR_PANEL = solarBlock(
            "ultimate_hybrid_solar_panel", LegacyAdvancedSolarPanelBlockEntity.UltimateHybrid.class);
    public static final RegistryObject<Ic2TileEntityBlock> MOLECULAR_TRANSFORMER = solarBlock(
            "molecular_transformer", LegacyMolecularTransformerBlockEntity.class);

    public static final RegistryObject<BlockEntityType<LegacyAdvancedSolarPanelBlockEntity.Advanced>>
            ADVANCED_SOLAR_BLOCK_ENTITY = BLOCK_ENTITIES.register(
                    "advanced_solar_panel", () -> RestoredLegacyContent.createBlockEntityType(
                            ADVANCED_SOLAR_PANEL, LegacyAdvancedSolarPanelBlockEntity.Advanced::new));
    public static final RegistryObject<BlockEntityType<LegacyAdvancedSolarPanelBlockEntity.Hybrid>>
            HYBRID_SOLAR_BLOCK_ENTITY = BLOCK_ENTITIES.register(
                    "hybrid_solar_panel", () -> RestoredLegacyContent.createBlockEntityType(
                            HYBRID_SOLAR_PANEL, LegacyAdvancedSolarPanelBlockEntity.Hybrid::new));
    public static final RegistryObject<BlockEntityType<LegacyAdvancedSolarPanelBlockEntity.UltimateHybrid>>
            ULTIMATE_HYBRID_SOLAR_BLOCK_ENTITY = BLOCK_ENTITIES.register(
                    "ultimate_hybrid_solar_panel", () -> RestoredLegacyContent.createBlockEntityType(
                            ULTIMATE_HYBRID_SOLAR_PANEL,
                            LegacyAdvancedSolarPanelBlockEntity.UltimateHybrid::new));
    public static final RegistryObject<BlockEntityType<LegacyMolecularTransformerBlockEntity>>
            MOLECULAR_TRANSFORMER_BLOCK_ENTITY = BLOCK_ENTITIES.register(
                    "molecular_transformer", () -> RestoredLegacyContent.createBlockEntityType(
                            MOLECULAR_TRANSFORMER,
                            LegacyMolecularTransformerBlockEntity::new));

    public static final RegistryObject<Item> SUNNARIUM = material("sunnarium");
    public static final RegistryObject<Item> SUNNARIUM_ALLOY = material("sunnarium_alloy");
    public static final RegistryObject<Item> IRRADIANT_URANIUM = material("irradiant_uranium");
    public static final RegistryObject<Item> ENRICHED_SUNNARIUM = material("enriched_sunnarium");
    public static final RegistryObject<Item> ENRICHED_SUNNARIUM_ALLOY = material("enriched_sunnarium_alloy");
    public static final RegistryObject<Item> IRRADIANT_GLASS_PANE = material("irradiant_glass_pane");
    public static final RegistryObject<Item> IRIDIUM_IRON_PLATE = material("iridium_iron_plate");
    public static final RegistryObject<Item> REINFORCED_IRIDIUM_IRON_PLATE =
            material("reinforced_iridium_iron_plate");
    public static final RegistryObject<Item> IRRADIANT_REINFORCED_PLATE =
            material("irradiant_reinforced_plate");
    public static final RegistryObject<Item> SUNNARIUM_PART = material("sunnarium_part");
    public static final RegistryObject<Item> IRIDIUM_INGOT = material("iridium_ingot");
    public static final RegistryObject<Item> MT_CORE = material("mt_core");
    public static final RegistryObject<LegacyAdvancedSolarHelmetItem> ADVANCED_SOLAR_HELMET =
            solarHelmet("advanced_solar_helmet", 16, 2, 2, "advanced_solar_helmet_1.png");
    public static final RegistryObject<LegacyAdvancedSolarHelmetItem> HYBRID_SOLAR_HELMET =
            solarHelmet("hybrid_solar_helmet", 128, 16, 3, "hybrid_solar_helmet_1.png");
    public static final RegistryObject<LegacyAdvancedSolarHelmetItem> ULTIMATE_HYBRID_SOLAR_HELMET =
            solarHelmet("ultimate_hybrid_solar_helmet", 1_024, 128, 4,
                    "ultimate_solar_helmet_1.png");

    static {
        blockItem("advanced_solar_panel", ADVANCED_SOLAR_PANEL);
        blockItem("hybrid_solar_panel", HYBRID_SOLAR_PANEL);
        blockItem("ultimate_hybrid_solar_panel", ULTIMATE_HYBRID_SOLAR_PANEL);
        blockItem("molecular_transformer", MOLECULAR_TRANSFORMER);
    }

    private LegacyAdvancedSolarsContent() {
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        BLOCK_ENTITIES.register(modBus);
    }

    private static RegistryObject<Ic2TileEntityBlock> solarBlock(
            String id, Class<? extends ic2.core.block.tileentity.Ic2TileEntity> tileClass) {
        return BLOCKS.register(id, () -> Ic2TileEntityBlock.create(
                BlockBehaviour.Properties.m_60939_(Material.f_76279_)
                        .m_60913_(2.0F, 10.0F)
                        .m_60999_()
                        .m_60918_(SoundType.f_56743_),
                tileClass,
                true,
                Ic2TileEntityBlock.DefaultDrop.Self,
                Util.noFacings,
                false));
    }

    private static RegistryObject<Item> material(String id) {
        return ITEMS.register(id, () -> new Item(new Item.Properties().m_41491_(IC2.tabIC2)));
    }

    private static RegistryObject<LegacyAdvancedSolarHelmetItem> solarHelmet(
            String id, int production, int lowerProduction, int tier, String armorTexture) {
        return ITEMS.register(id, () -> new LegacyAdvancedSolarHelmetItem(
                new Item.Properties().m_41491_(IC2.tabIC2).m_41497_(Rarity.RARE),
                production,
                lowerProduction,
                tier,
                armorTexture));
    }

    private static void blockItem(String id, RegistryObject<? extends Block> block) {
        ITEMS.register(id, () -> new BlockItem(
                block.get(), new Item.Properties().m_41491_(IC2.tabIC2)));
    }
}
