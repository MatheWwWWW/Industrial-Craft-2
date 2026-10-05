package ru.mot.ic2hadroncollider;

import ic2.core.IC2;
import ic2.core.block.tileentity.Ic2TileEntityBlock;
import ic2.core.ref.IC2Material;
import ic2.core.util.Util;
import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class HadronColliderContent {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
            ForgeRegistries.BLOCKS, HadronColliderMod.MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS, HadronColliderMod.MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
            ForgeRegistries.BLOCK_ENTITY_TYPES, HadronColliderMod.MOD_ID);

    public static final RegistryObject<Ic2TileEntityBlock> HADRON_COLLIDER = BLOCKS.register(
            "hadron_collider", () -> Ic2TileEntityBlock.create(
                    BlockBehaviour.Properties.m_60939_(IC2Material.MACHINE)
                            .m_60913_(2.0F, 10.0F)
                            .m_60999_()
                            .m_60918_(SoundType.f_56743_),
                    HadronColliderBlockEntity.class,
                    true,
                    // Same rule as IC2's Matter Fabricator: a wrench keeps the
                    // machine, a pickaxe only returns the Advanced Machine Casing.
                    Ic2TileEntityBlock.DefaultDrop.AdvMachine,
                    Util.horizontalFacings,
                    true));

    public static final RegistryObject<BlockEntityType<HadronColliderBlockEntity>> HADRON_COLLIDER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("hadron_collider", () -> createBlockEntityType(HADRON_COLLIDER));

    public static final RegistryObject<Item> HADRON_COLLIDER_ITEM = ITEMS.register(
            "hadron_collider", () -> new HadronColliderItem(
                    HADRON_COLLIDER.get(),
                    new Item.Properties().m_41491_(IC2.tabIC2).m_41497_(Rarity.EPIC)));
    public static final RegistryObject<Item> ACCELERATOR_MAGNET = ITEMS.register(
            "accelerator_magnet", () -> new Item(
                    new Item.Properties().m_41491_(IC2.tabIC2).m_41497_(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> COLLISION_CHAMBER = ITEMS.register(
            "collision_chamber", () -> new Item(
                    new Item.Properties().m_41491_(IC2.tabIC2).m_41497_(Rarity.RARE)));

    private HadronColliderContent() {
    }

    static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        BLOCK_ENTITIES.register(modBus);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static BlockEntityType<HadronColliderBlockEntity> createBlockEntityType(
            RegistryObject<? extends Block> block) {
        try {
            // BlockEntityType.BlockEntitySupplier is package-private in the SRG
            // compile jar but public at runtime; build it through its runtime
            // signature, as the IC2 Experimental Fidelity companion does.
            Class<?> factoryType = Class.forName(
                    "net.minecraft.world.level.block.entity.BlockEntityType$BlockEntitySupplier");
            Object factory = Proxy.newProxyInstance(
                    HadronColliderContent.class.getClassLoader(),
                    new Class<?>[] {factoryType},
                    (proxy, method, args) -> switch (method.getName()) {
                        case "m_155267_" -> new HadronColliderBlockEntity(
                                (BlockPos) args[0], (BlockState) args[1]);
                        case "hashCode" -> System.identityHashCode(proxy);
                        case "equals" -> proxy == args[0];
                        case "toString" -> "Hadron Collider block-entity factory";
                        default -> throw new UnsupportedOperationException(method.toString());
                    });
            Constructor<BlockEntityType> constructor = BlockEntityType.class.getConstructor(
                    factoryType, Set.class, com.mojang.datafixers.types.Type.class);
            return constructor.newInstance(factory, Set.of(block.get()), null);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to create the Hadron Collider block-entity type", exception);
        }
    }
}
