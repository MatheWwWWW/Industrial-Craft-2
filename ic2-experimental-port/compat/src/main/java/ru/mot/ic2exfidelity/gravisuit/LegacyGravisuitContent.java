package ru.mot.ic2exfidelity.gravisuit;

import ic2.core.IC2;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.BiFunction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Stable registry names and default 2.2 power values from Gravisuit Classic. */
public final class LegacyGravisuitContent {
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LegacyGravisuitMod.MOD_ID);
    private static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, LegacyGravisuitMod.MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, LegacyGravisuitMod.MOD_ID);
    private static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LegacyGravisuitMod.MOD_ID);

    public static final RegistryObject<Block> PLASMA_PORTAL = BLOCKS.register(
            "plasma_portal", () -> new LegacyPlasmaPortalBlock(
                    BlockBehaviour.Properties.m_60939_(Material.f_76298_)
                            .m_60966_().m_60910_().m_60955_().m_222994_()));
    public static final RegistryObject<BlockEntityType<LegacyPlasmaPortalBlockEntity>>
            PLASMA_PORTAL_BLOCK_ENTITY = BLOCK_ENTITIES.register(
                    "plasma_portal", () -> RestoredLegacyContent.createBlockEntityType(
                            PLASMA_PORTAL, LegacyPlasmaPortalBlockEntity::new));
    public static final RegistryObject<EntityType<LegacyPlasmaBallEntity>> PLASMA_BALL =
            ENTITIES.register("plasma_ball", LegacyGravisuitContent::createPlasmaBallType);

    public static final RegistryObject<Item> SUPER_CONDUCTOR_COVER = component("super_conductor_cover");
    public static final RegistryObject<Item> SUPER_CONDUCTOR = component("super_conductor");
    public static final RegistryObject<Item> COOLING_CORE = component("cooling_core");
    public static final RegistryObject<Item> GRAVITATION_ENGINE = component("gravitation_engine");
    public static final RegistryObject<Item> MAGNETRON = component("magnetron");
    public static final RegistryObject<Item> VAJRA_CORE = component("vajra_core");
    public static final RegistryObject<Item> ENGINE_BOOST = component("engine_boost");

    public static final RegistryObject<Item> ADVANCED_LAPPACK = ITEMS.register(
            "advanced_lappack",
            () -> new LegacyGravisuitEnergyPack(
                    properties().m_41497_(Rarity.UNCOMMON),
                    600_000.0,
                    500.0,
                    2,
                    "gravisuit:textures/models/advanced_lappack_1.png"));
    public static final RegistryObject<Item> ULTIMATE_LAPPACK = ITEMS.register(
            "ultimate_lappack",
            () -> new LegacyGravisuitEnergyPack(
                    properties().m_41497_(Rarity.EPIC),
                    10_000_000.0,
                    4_000.0,
                    3,
                    "gravisuit:textures/models/ultimate_lappack_1.png"));

    public static final RegistryObject<Item> ADVANCED_ELECTRIC_JETPACK = ITEMS.register(
            "advanced_electric_jetpack",
            () -> LegacyGravisuitJetpack.advancedElectric(properties()));
    public static final RegistryObject<Item> ADVANCED_NUCLEAR_JETPACK = ITEMS.register(
            "advanced_nuclear_jetpack",
            () -> LegacyGravisuitJetpack.advancedNuclear(properties()));
    public static final RegistryObject<Item> GRAVITATION_JETPACK = ITEMS.register(
            "gravitation_jetpack",
            () -> LegacyGravisuitJetpack.gravitation(properties()));
    public static final RegistryObject<Item> NUCLEAR_GRAVITATION_JETPACK = ITEMS.register(
            "nuclear_gravitation_jetpack",
            () -> LegacyGravisuitJetpack.nuclearGravitation(properties()));

    public static final RegistryObject<Item> GRAVITOOL = ITEMS.register(
            "gravitool",
            () -> new LegacyGravitool(properties()));
    public static final RegistryObject<Item> VAJRA = ITEMS.register(
            "vajra",
            () -> new LegacyVajra(properties()));
    public static final RegistryObject<Item> RELOCATOR = ITEMS.register(
            "relocator",
            () -> new LegacyRelocatorItem(properties()));

    private LegacyGravisuitContent() {
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        BLOCK_ENTITIES.register(modBus);
        ENTITIES.register(modBus);
        ITEMS.register(modBus);
    }

    private static RegistryObject<Item> component(String id) {
        return ITEMS.register(id, () -> new Item(new Item.Properties().m_41491_(IC2.tabIC2)));
    }

    private static Item.Properties properties() {
        return new Item.Properties().m_41487_(1).m_41491_(IC2.tabIC2);
    }

    private static EntityType<LegacyPlasmaBallEntity> createPlasmaBallType() {
        EntityType.Builder<LegacyPlasmaBallEntity> builder = EntityType.Builder
                .<LegacyPlasmaBallEntity>m_20704_(
                        (type, level) -> new LegacyPlasmaBallEntity(type, level),
                        MobCategory.MISC)
                .m_20699_(1.0F, 1.0F)
                .m_20702_(4).m_20717_(20);
        configureForgeSpawnBehavior(builder);
        return builder.m_20712_("gravisuit:plasma_ball");
    }

    /** Forge supplies these builder methods through its runtime binpatches. */
    private static void configureForgeSpawnBehavior(
            EntityType.Builder<LegacyPlasmaBallEntity> builder) {
        try {
            Method velocity = builder.getClass().getMethod(
                    "setShouldReceiveVelocityUpdates", boolean.class);
            velocity.invoke(builder, true);
            Method clientFactory = builder.getClass().getMethod(
                    "setCustomClientFactory", BiFunction.class);
            BiFunction<Object, net.minecraft.world.level.Level,
                    LegacyPlasmaBallEntity> factory = (message, level) ->
                            new LegacyPlasmaBallEntity(PLASMA_BALL.get(), level);
            clientFactory.invoke(builder, factory);
        } catch (NoSuchMethodException | IllegalAccessException exception) {
            throw new IllegalStateException(
                    "Forge entity builder extensions are unavailable", exception);
        } catch (InvocationTargetException exception) {
            throw new IllegalStateException(
                    "Unable to configure plasma-ball network spawning",
                    exception.getCause());
        }
    }
}
