package ru.mot.ic2exfidelity.iu;

import ic2.core.IC2;
import ic2.core.block.tileentity.Ic2TileEntityBlock;
import ic2.core.util.Util;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Independent IC2 implementations: no Industrial Upgrade classes are linked. */
public final class NativeIUContent {
    public record Machine(String kind, int tier, double coefficient) {}
    public static final Map<ResourceLocation, Machine> MACHINES = new java.util.concurrent.ConcurrentHashMap<>();
    public static final Map<ResourceLocation, RegistryObject<BlockEntityType<NativeMachine>>> TYPES = new java.util.concurrent.ConcurrentHashMap<>();
    public static final Map<ResourceLocation, RegistryObject<Item>> ITEMS = new java.util.concurrent.ConcurrentHashMap<>();
    private NativeIUContent() {}

    public static void register(String namespace, IEventBus bus) {
        DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, namespace);
        DeferredRegister<Block> blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, namespace);
        DeferredRegister<BlockEntityType<?>> types = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, namespace);
        switch (namespace) {
            case "wateringcan" -> {
                watering(items, "simple_watering_can", 3, 10000);
                watering(items, "adv_watering_can", 5, 20000);
                watering(items, "imp_watering_can", 7, 40000);
            }
            case "powerutils" -> {
                machine(items, blocks, types, namespace, "converter/power_utilities_fe", "fe", 5, 1);
                machine(items, blocks, types, namespace, "converter/power_utilities_qe", "qe", 5, 1);
                for (String name : new String[]{"module_ic", "module_fe", "module_qe"}) material(items, namespace, name);
                RegistryObject<Block> cable = blocks.register("quantum_cable", () -> new Block(properties()));
                ITEMS.put(new ResourceLocation(namespace, "quantum_cable"), items.register("quantum_cable", () -> new BlockItem(cable.get(), itemProperties())));
            }
            case "quantumgenerators" -> {
                String[] names = {"phsp_gen", "nsp_gen", "bsp_gen", "adsp_gen", "grasp_gen", "kvsp_gen"};
                for (int i = 0; i < names.length; i++) {
                    machine(items, blocks, types, namespace, "qg/" + names[i], "generator", 9 + i, 1);
                    material(items, namespace, "core_" + names[i]);
                }
            }
            case "simplyquarries" -> {
                String[] names = {"simply_quarry", "adv_simply_quarry", "imp_simply_quarry", "per_simply_quarry", "pho_simply_quarry"};
                double[] coefficients = {1, 1.25, 1.5, 2, 2.5};
                for (int i = 0; i < names.length; i++) machine(items, blocks, types, namespace, "simplyquarries/" + names[i], "quarry", i + 1, coefficients[i]);
                for (String name : new String[]{"furnace", "speed_i", "speed_ii", "speed_iii", "speed_iv", "speed_v", "lucky_i", "lucky_ii", "lucky_iii", "depth_i", "depth_ii", "depth_iii", "blacklist", "whitelist", "macerator", "combmac"}) {
                    String id = "module_" + name;
                    ITEMS.put(new ResourceLocation(namespace, id), items.register(id, () -> new QuarryModule(itemProperties().m_41487_(1), name)));
                }
            }
            case "diamondvein" -> {
                DeferredRegister<net.minecraft.world.level.levelgen.feature.Feature<?>> features = DeferredRegister.create(ForgeRegistries.FEATURES, namespace);
                features.register("diamond_vein", DiamondVeinFeature::new);
                features.register(bus);
                RegistryObject<Block> deposit = blocks.register("diamond_deposits/deposits_diamond", DiamondDeposit::new);
                ITEMS.put(new ResourceLocation(namespace, "diamond_deposits/deposits_diamond"), items.register("diamond_deposits/deposits_diamond", () -> new BlockItem(deposit.get(), itemProperties())));
            }
            case "reactorplus" -> NativeReactorItems.register(items);
            default -> throw new IllegalArgumentException(namespace);
        }
        blocks.register(bus);
        items.register(bus);
        types.register(bus);
    }

    private static void watering(DeferredRegister<Item> items, String id, int radius, int capacity) {
        ITEMS.put(new ResourceLocation("wateringcan", id), items.register(id, () -> new NativeWateringCan(itemProperties().m_41487_(1), radius, capacity)));
    }
    private static void material(DeferredRegister<Item> items, String namespace, String id) {
        ITEMS.put(new ResourceLocation(namespace, id), items.register(id, () -> new Item(itemProperties())));
    }
    private static void machine(DeferredRegister<Item> items, DeferredRegister<Block> blocks,
            DeferredRegister<BlockEntityType<?>> types, String namespace, String id, String kind, int tier, double coefficient) {
        ResourceLocation key = new ResourceLocation(namespace, id);
        MACHINES.put(key, new Machine(kind, tier, coefficient));
        RegistryObject<Block> block = blocks.register(id, () -> Ic2TileEntityBlock.create(properties(), NativeMachine.class, false, Ic2TileEntityBlock.DefaultDrop.Self, Util.noFacings, false));
        TYPES.put(key, types.register(id, () -> RestoredLegacyContent.createBlockEntityType(block, NativeMachine::new)));
        ITEMS.put(key, items.register(id, () -> new BlockItem(block.get(), itemProperties())));
    }
    public static Item.Properties itemProperties() { return new Item.Properties().m_41491_(IC2.tabIC2); }
    private static BlockBehaviour.Properties properties() { return BlockBehaviour.Properties.m_60939_(Material.f_76279_).m_60913_(3, 5).m_60999_(); }
}
