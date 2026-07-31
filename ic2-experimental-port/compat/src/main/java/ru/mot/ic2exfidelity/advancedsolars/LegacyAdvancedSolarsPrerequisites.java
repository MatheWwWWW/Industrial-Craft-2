package ru.mot.ic2exfidelity.advancedsolars;

import ic2.core.IC2;
import ic2.core.block.tileentity.Ic2TileEntityBlock;
import ic2.core.block.wiring.CableBlock;
import ic2.core.block.wiring.CableType;
import ic2.core.block.wiring.FoamCableBlock;
import ic2.core.item.ItemBattery;
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

/** IC2 Classic generator ids used by Advanced Solars' unchanged recipes. */
public final class LegacyAdvancedSolarsPrerequisites {
    private static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, "ic2");
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, "ic2");
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "ic2");

    public static final RegistryObject<Ic2TileEntityBlock> MV_SOLAR_PANEL = solarBlock(
            "solar_panel_mv", LegacyClassicSolarPanelBlockEntity.MV.class);
    public static final RegistryObject<Ic2TileEntityBlock> HV_SOLAR_PANEL = solarBlock(
            "solar_panel_hv", LegacyClassicSolarPanelBlockEntity.HV.class);
    public static final RegistryObject<FoamCableBlock> BRONZE_FOAM_CABLE = foamCable(
            "bronze_foam_cable", CableType.bronze, 0);
    public static final RegistryObject<FoamCableBlock> BRONZE_INSULATED_FOAM_CABLE = foamCable(
            "bronze_insulated_foam_cable", CableType.bronze, 1);
    public static final RegistryObject<FoamCableBlock> BRONZE_DOUBLE_INSULATED_FOAM_CABLE =
            foamCable("bronze_double_insulated_foam_cable", CableType.bronze, 2);
    public static final RegistryObject<FoamCableBlock> PLASMA_FOAM_CABLE = foamCable(
            "plasma_foam_cable", CableType.plasma, 0);
    public static final RegistryObject<CableBlock> BRONZE_CABLE = cable(
            "bronze_cable", CableType.bronze, 0, BRONZE_FOAM_CABLE);
    public static final RegistryObject<CableBlock> BRONZE_INSULATED_CABLE_BLOCK = cable(
            "bronze_cable_insulated", CableType.bronze, 1, BRONZE_INSULATED_FOAM_CABLE);
    public static final RegistryObject<CableBlock> BRONZE_DOUBLE_INSULATED_CABLE_BLOCK = cable(
            "bronze_cable_double_insulated", CableType.bronze, 2,
            BRONZE_DOUBLE_INSULATED_FOAM_CABLE);
    public static final RegistryObject<CableBlock> PLASMA_CABLE = BLOCKS.register(
            "plasma_cable", () -> CableBlock.create(cableProperties(0),
                    CableType.plasma, 0, PLASMA_FOAM_CABLE.get()));
    public static final RegistryObject<Ic2TileEntityBlock> IV_TRANSFORMER = BLOCKS.register(
            "transformer_iv", () -> Ic2TileEntityBlock.create(
                    BlockBehaviour.Properties.m_60939_(Material.f_76279_)
                            .m_60913_(2.0F, 10.0F)
                            .m_60999_()
                            .m_60918_(SoundType.f_56743_),
                    LegacyIVTransformerBlockEntity.class,
                    true,
                    Ic2TileEntityBlock.DefaultDrop.Self,
                    Util.allFacings,
                    true));
    public static final RegistryObject<LegacyIridiumStoneBlock> IRIDIUM_STONE = BLOCKS.register(
            "iridium_stone", () -> new LegacyIridiumStoneBlock(
                    BlockBehaviour.Properties.m_60939_(Material.f_76279_)
                            .m_60913_(-1.0F, 3_600_000.0F)
                            .m_60918_(SoundType.f_56743_)));
    public static final RegistryObject<Ic2TileEntityBlock> PLASMAFIER = BLOCKS.register(
            "plasmafier", () -> Ic2TileEntityBlock.create(
                    BlockBehaviour.Properties.m_60939_(Material.f_76279_)
                            .m_60913_(2.0F, 10.0F)
                            .m_60999_()
                            .m_60918_(SoundType.f_56743_),
                    LegacyPlasmafierBlockEntity.class,
                    true,
                    Ic2TileEntityBlock.DefaultDrop.Self,
                    Util.horizontalFacings,
                    true));
    public static final RegistryObject<Ic2TileEntityBlock> RARE_EARTH_EXTRACTOR = BLOCKS.register(
            "rare_earth_extractor", () -> Ic2TileEntityBlock.create(
                    BlockBehaviour.Properties.m_60939_(Material.f_76279_)
                            .m_60913_(2.0F, 10.0F)
                            .m_60999_()
                            .m_60918_(SoundType.f_56743_),
                    LegacyRareEarthExtractorBlockEntity.class,
                    true,
                    Ic2TileEntityBlock.DefaultDrop.Machine,
                    Util.horizontalFacings,
                    true));

    public static final RegistryObject<BlockEntityType<LegacyClassicSolarPanelBlockEntity.MV>>
            MV_SOLAR_PANEL_BLOCK_ENTITY = BLOCK_ENTITIES.register(
                    "solar_panel_mv", () -> RestoredLegacyContent.createBlockEntityType(
                            MV_SOLAR_PANEL, LegacyClassicSolarPanelBlockEntity.MV::new));
    public static final RegistryObject<BlockEntityType<LegacyClassicSolarPanelBlockEntity.HV>>
            HV_SOLAR_PANEL_BLOCK_ENTITY = BLOCK_ENTITIES.register(
                    "solar_panel_hv", () -> RestoredLegacyContent.createBlockEntityType(
                            HV_SOLAR_PANEL, LegacyClassicSolarPanelBlockEntity.HV::new));
    public static final RegistryObject<BlockEntityType<LegacyIVTransformerBlockEntity>>
            IV_TRANSFORMER_BLOCK_ENTITY = BLOCK_ENTITIES.register(
                    "transformer_iv", () -> RestoredLegacyContent.createBlockEntityType(
                            IV_TRANSFORMER, LegacyIVTransformerBlockEntity::new));
    public static final RegistryObject<BlockEntityType<LegacyIridiumStoneBlockEntity>>
            IRIDIUM_STONE_BLOCK_ENTITY = BLOCK_ENTITIES.register(
                    "iridium_stone", () -> RestoredLegacyContent.createBlockEntityType(
                            IRIDIUM_STONE, LegacyIridiumStoneBlockEntity::new));
    public static final RegistryObject<BlockEntityType<LegacyPlasmafierBlockEntity>>
            PLASMAFIER_BLOCK_ENTITY = BLOCK_ENTITIES.register(
                    "plasmafier", () -> RestoredLegacyContent.createBlockEntityType(
                            PLASMAFIER, LegacyPlasmafierBlockEntity::new));
    public static final RegistryObject<BlockEntityType<LegacyRareEarthExtractorBlockEntity>>
            RARE_EARTH_EXTRACTOR_BLOCK_ENTITY = BLOCK_ENTITIES.register(
                    "rare_earth_extractor", () -> RestoredLegacyContent.createBlockEntityType(
                            RARE_EARTH_EXTRACTOR, LegacyRareEarthExtractorBlockEntity::new));

    public static final RegistryObject<Item> PLASMA_CELL = ITEMS.register(
            "cell_plasma", () -> new Item(new Item.Properties().m_41491_(IC2.tabIC2)));
    public static final RegistryObject<Item> PLASMA_CORE = ITEMS.register(
            "plasma_core", () -> new Item(new Item.Properties().m_41491_(IC2.tabIC2)));
    public static final RegistryObject<Item> RARE_EARTH_DUST = ITEMS.register(
            "rare_earth_dust", () -> new Item(new Item.Properties().m_41491_(IC2.tabIC2)));
    public static final RegistryObject<Item> ALUMINIUM_DUST = ITEMS.register(
            "dust_aluminium", () -> new Item(new Item.Properties().m_41491_(IC2.tabIC2)));
    public static final RegistryObject<Item> RARE_EARTH_CHUNK = ITEMS.register(
            "rare_earth_chunk", () -> new Item(new Item.Properties().m_41491_(IC2.tabIC2)));
    public static final RegistryObject<Item> DEAD_MAGNET = ITEMS.register(
            "dead_magnet", () -> new Item(new Item.Properties().m_41491_(IC2.tabIC2)));
    public static final RegistryObject<Item> MAGNET = ITEMS.register(
            "magnet", () -> new Item(new Item.Properties().m_41491_(IC2.tabIC2)));
    public static final RegistryObject<ItemBattery> PESD = ITEMS.register(
            "pesd", () -> new ItemBattery(
                    new Item.Properties().m_41491_(IC2.tabIC2).m_41497_(Rarity.RARE),
                    50_000_000.0,
                    25_000.0,
                    4));

    static {
        blockItem("solar_panel_mv", MV_SOLAR_PANEL);
        blockItem("solar_panel_hv", HV_SOLAR_PANEL);
        blockItem("transformer_iv", IV_TRANSFORMER);
        blockItem("iridium_stone", IRIDIUM_STONE);
        blockItem("plasmafier", PLASMAFIER);
        blockItem("rare_earth_extractor", RARE_EARTH_EXTRACTOR);
        cableItem("bronze_cable_item", BRONZE_CABLE);
        cableItem("bronze_insulated_cable_item", BRONZE_INSULATED_CABLE_BLOCK);
        cableItem("bronze_double_insulated_cable_item",
                BRONZE_DOUBLE_INSULATED_CABLE_BLOCK);
        cableItem("plasma_cable_item", PLASMA_CABLE);
    }

    public static final RegistryObject<LegacyClassicAdvancedSolarHelmetItem>
            ADVANCED_SOLAR_HELMET = ITEMS.register(
                    "advanced_solar_helmet",
                    () -> new LegacyClassicAdvancedSolarHelmetItem(
                            new Item.Properties()
                                    .m_41491_(IC2.tabIC2)
                                    .m_41497_(Rarity.UNCOMMON)));

    private LegacyAdvancedSolarsPrerequisites() {
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
                Util.horizontalFacings,
                false));
    }

    private static RegistryObject<CableBlock> cable(
            String id, CableType type, int insulation,
            RegistryObject<FoamCableBlock> foamCable) {
        return BLOCKS.register(id, () -> CableBlock.create(
                cableProperties(insulation),
                type,
                insulation,
                foamCable.get()));
    }

    private static RegistryObject<FoamCableBlock> foamCable(
            String id, CableType type, int insulation) {
        return BLOCKS.register(id, () -> FoamCableBlock.create(
                cableProperties(insulation), type, insulation));
    }

    private static BlockBehaviour.Properties cableProperties(int insulation) {
        return BlockBehaviour.Properties.m_60939_(
                        insulation == 0 ? Material.f_76279_ : Material.f_76272_)
                .m_60913_(0.5F, 5.0F)
                .m_60918_(insulation == 0 ? SoundType.f_56743_ : SoundType.f_56745_);
    }

    private static void blockItem(String id, RegistryObject<? extends Block> block) {
        ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties()
                .m_41491_(IC2.tabIC2)
                .m_41497_(Rarity.UNCOMMON)));
    }

    private static void cableItem(String id, RegistryObject<? extends Block> block) {
        ITEMS.register(id, () -> new BlockItem(
                block.get(),
                new Item.Properties().m_41491_(IC2.tabIC2)));
    }
}
