package ru.mot.ic2exfidelity.integration;

import ic2.api.crops.CropCard;
import ic2.api.crops.CropProperties;
import ic2.api.crops.Crops;
import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import ic2.core.IC2;
import ic2.core.block.tileentity.Ic2TileEntityBlock;
import ic2.core.crop.Ic2CropType;
import ic2.core.crop.TileEntityCrop;
import ic2.core.ref.Ic2BlockEntities;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/** Restores the data-driven crop cards removed between IC2 2.8.222 and ex119. */
public final class LegacyCropContent {
    private static final String IC2_ID = "ic2";
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, IC2_ID);
    private static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, IC2_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, IC2_ID);

    public static final RegistryObject<Item> ENDER_PEARL_DUST = item("ender_pearl_dust");
    public static final RegistryObject<Item> ENDER_EYE_DUST = item("ender_eye_dust");
    public static final RegistryObject<Item> MILK_DUST = item("milk_dust");
    public static final RegistryObject<Item> EMERALD_DUST = item("emerald_dust");
    public static final RegistryObject<Item> SMALL_EMERALD_DUST = item("small_emerald_dust");
    public static final RegistryObject<Item> SMALL_DIAMOND_DUST = item("small_diamond_dust");
    public static final RegistryObject<Item> MILK_WART = item("milk_wart");
    public static final RegistryObject<Item> OIL_BERRY = item("oil_berry");
    public static final RegistryObject<Item> BOBS_YER_UNCLE_RANKS_BERRY =
            item("bobs_yer_uncle_ranks_berry");

    private static final List<CropSpec> SPECS = List.of(
            crop("blazereed", "Mr. Brain", 6, 0, 4, 1, 0, 0,
                    List.of("Fire", "Blaze", "Reed", "Sulfur"), 4,
                    () -> stacks(stack("minecraft:blaze_powder")),
                    () -> stacks(stack("minecraft:blaze_rod"), stack("ic2:sulfur_dust")),
                    0, 1, null),
            crop("bobs_yer_uncle_ranks_berries", "GenerikB", 11, 4, 0, 8, 2, 9,
                    List.of("Shiny", "Vine", "Emerald", "Berylium", "Crystal"), 4,
                    () -> stacks(stack(BOBS_YER_UNCLE_RANKS_BERRY)),
                    () -> stacks(stack("minecraft:emerald")), 0, 1, null),
            crop("corium", "Gregorius Techneticies", 6, 0, 2, 3, 1, 0,
                    List.of("Cow", "Silk", "Vine"), 4,
                    () -> stacks(stack("minecraft:leather")), LegacyCropContent::noStacks,
                    0, 1, null),
            crop("corpse_plant", "Mr. Kenny", 5, 0, 2, 1, 0, 3,
                    List.of("Toxic", "Undead", "Vine", "Edible", "Rotten"), 4,
                    () -> stacks(stack("minecraft:rotten_flesh")),
                    () -> stacks(stack("minecraft:bone"), stack("minecraft:bone_meal"),
                            stack("minecraft:bone_meal")), 0, 1, null),
            crop("creeper_weed", "General Spaz", 7, 3, 0, 5, 1, 3,
                    List.of("Creeper", "Vine", "Explosive", "Fire", "Sulfur", "Saltpeter", "Coal"), 4,
                    () -> stacks(stack("minecraft:gunpowder")), LegacyCropContent::noStacks,
                    0, 1, null),
            crop("diareed", "Diareed", 12, 5, 0, 10, 2, 10,
                    List.of("Fire", "Shiny", "Reed", "Coal", "Diamond", "Crystal"), 4,
                    () -> stacks(stack(SMALL_DIAMOND_DUST)),
                    () -> stacks(stack("minecraft:diamond")), 0, 1, null),
            crop("egg_plant", "Link", 6, 0, 4, 1, 0, 0,
                    List.of("Chicken", "Egg", "Edible", "Feather", "Flower", "Addictive"), 3,
                    () -> stacks(stack("minecraft:egg")),
                    () -> stacks(stack("minecraft:chicken"), stack("minecraft:feather"),
                            stack("minecraft:feather"), stack("minecraft:feather")),
                    900, 2, null),
            crop("ender_blossom", "RichardG", 10, 5, 0, 2, 1, 6,
                    List.of("Ender", "Flower", "Shiny"), 4,
                    () -> stacks(stack(ENDER_PEARL_DUST)),
                    () -> stacks(stack("minecraft:ender_pearl"), stack("minecraft:ender_pearl"),
                            stack("minecraft:ender_eye")), 0, 1, null),
            crop("meat_rose", "VintageBeef", 7, 0, 4, 1, 3, 0,
                    List.of("Edible", "Flower", "Cow", "Chicken", "Pig", "Sheep"), 4,
                    () -> stacks(stack("minecraft:pink_dye")),
                    () -> stacks(stack("minecraft:beef"), stack("minecraft:porkchop"),
                            stack("minecraft:chicken"), stack("minecraft:mutton")),
                    1500, 1, null),
            crop("milk_wart", "Mr. Brain", 6, 0, 3, 0, 1, 0,
                    List.of("Edible", "Milk", "Cow"), 3,
                    () -> stacks(stack(MILK_WART)), LegacyCropContent::noStacks,
                    900, 1, MILK_WART),
            crop("oil_berries", "Spacetoad", 9, 6, 1, 2, 1, 12,
                    List.of("Fire", "Dark", "Reed", "Rotten", "Coal", "Oil"), 3,
                    () -> stacks(stack(OIL_BERRY)), LegacyCropContent::noStacks,
                    0, 1, null),
            crop("slime_plant", "Neowulf", 6, 3, 0, 0, 0, 2,
                    List.of("Slime", "Bouncy", "Sticky", "Bush"), 4,
                    () -> stacks(stack("minecraft:slime_ball")), LegacyCropContent::noStacks,
                    0, 3, null),
            crop("spidernip", "Mr. Kenny", 4, 2, 1, 4, 1, 3,
                    List.of("Toxic", "Silk", "Spider", "Flower", "Ingredient", "Addictive"), 4,
                    () -> stacks(stack("minecraft:string")),
                    () -> stacks(stack("minecraft:spider_eye"), stack("minecraft:cobweb")),
                    600, 1, null),
            crop("tearstalks", "Neowulf", 8, 1, 2, 0, 0, 0,
                    List.of("Healing", "Nether", "Ingredient", "Reed", "Ghast"), 4,
                    () -> stacks(stack("minecraft:ghast_tear")), LegacyCropContent::noStacks,
                    0, 1, null),
            crop("withereed", "CovertJaguar", 8, 2, 0, 4, 1, 3,
                    List.of("Fire", "Undead", "Reed", "Coal", "Rotten", "Wither"), 4,
                    () -> stacks(stack("ic2:coal_dust")),
                    () -> stacks(stack("minecraft:coal"), stack("minecraft:coal")),
                    0, 1, null));

    private static final Map<String, RegistryObject<Ic2TileEntityBlock>> CROP_BLOCKS =
            new LinkedHashMap<>();
    private static final Map<String, RegistryObject<BlockEntityType<?>>> CROP_BLOCK_ENTITIES =
            new LinkedHashMap<>();
    private static final Map<String, LegacyGenericCropCard> CROP_CARDS = new LinkedHashMap<>();
    private static boolean installed;

    static {
        for (CropSpec spec : SPECS) {
            String registryName = spec.id() + "_crop";
            RegistryObject<Ic2TileEntityBlock> block = BLOCKS.register(
                    registryName,
                    () -> Ic2TileEntityBlock.create(
                            cropProperties(),
                            TileEntityCrop.class,
                            false,
                            Ic2TileEntityBlock.DefaultDrop.Self,
                            Set.of(),
                            false,
                            proxyType(spec.maxSize())));
            CROP_BLOCKS.put(spec.id(), block);
            RegistryObject<BlockEntityType<?>> blockEntity = BLOCK_ENTITIES.register(
                    registryName,
                    () -> RestoredLegacyContent.createBlockEntityType(block, TileEntityCrop::new));
            CROP_BLOCK_ENTITIES.put(spec.id(), blockEntity);
        }
    }

    private LegacyCropContent() {
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        BLOCK_ENTITIES.register(modBus);
    }

    public static void install() {
        if (installed) {
            return;
        }
        if (Crops.instance == null) {
            throw new IllegalStateException("IC2 crop registry is not initialized");
        }
        patchBlockEntityLookup();
        for (CropSpec spec : SPECS) {
            LegacyCropType type = new LegacyCropType(
                    spec.id(), CROP_BLOCKS.get(spec.id()), spec.maxSize() - 1);
            LegacyGenericCropCard card = new LegacyGenericCropCard(type, spec);
            Crops.instance.registerCrop(card);
            CROP_CARDS.put(spec.id(), card);
            if (spec.baseSeed() != null) {
                Crops.instance.registerBaseSeed(
                        new ItemStack(spec.baseSeed().get()), card, 0, 1, 1, 1);
            }
        }
        installed = true;
        System.out.println("[IC2-FIDELITY-CROPS] restored=" + CROP_CARDS.size());
    }

    public static Map<String, LegacyGenericCropCard> cropCards() {
        return Map.copyOf(CROP_CARDS);
    }

    public static Map<String, RegistryObject<Ic2TileEntityBlock>> cropBlocks() {
        return Map.copyOf(CROP_BLOCKS);
    }

    @SuppressWarnings("unchecked")
    private static void patchBlockEntityLookup() {
        try {
            Field field = Ic2BlockEntities.class.getDeclaredField("blockEntityTypeMap");
            field.setAccessible(true);
            Map<String, BlockEntityType<?>> map =
                    (Map<String, BlockEntityType<?>>) field.get(null);
            if (map == null) {
                throw new IllegalStateException("IC2 block-entity lookup map is not initialized");
            }
            for (CropSpec spec : SPECS) {
                ResourceLocation id = new ResourceLocation(IC2_ID, spec.id() + "_crop");
                map.put(id.toString(), CROP_BLOCK_ENTITIES.get(spec.id()).get());
            }
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to register restored crop block entities", exception);
        }
    }

    private static RegistryObject<Item> item(String id) {
        return ITEMS.register(id, () -> new Item(new Item.Properties().m_41491_(IC2.tabIC2)));
    }

    private static BlockBehaviour.Properties cropProperties() {
        return BlockBehaviour.Properties.m_60939_(Material.f_76300_)
                .m_60913_(0.8F, 0.2F)
                .m_60918_(SoundType.f_56758_)
                .m_60910_();
    }

    private static Ic2CropType proxyType(int maxSize) {
        return switch (maxSize) {
            case 3 -> Ic2CropType.redMushroom;
            case 4 -> Ic2CropType.dandelion;
            default -> throw new IllegalArgumentException("Unsupported legacy crop size: " + maxSize);
        };
    }

    private static CropSpec crop(
            String id,
            String discoveredBy,
            int tier,
            int chemistry,
            int consumable,
            int defensive,
            int colorful,
            int weed,
            List<String> attributes,
            int maxSize,
            Supplier<ItemStack[]> drops,
            Supplier<ItemStack[]> specialDrops,
            int growthSpeed,
            int afterHarvestSize,
            RegistryObject<Item> baseSeed) {
        return new CropSpec(
                id,
                discoveredBy,
                new CropProperties(tier, chemistry, consumable, defensive, colorful, weed),
                attributes.toArray(String[]::new),
                maxSize,
                drops,
                specialDrops,
                growthSpeed,
                afterHarvestSize,
                baseSeed);
    }

    private static ItemStack stack(String id) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
        if (item == null) {
            throw new IllegalStateException("Missing restored crop drop: " + id);
        }
        return new ItemStack(item);
    }

    private static ItemStack stack(RegistryObject<Item> item) {
        return new ItemStack(item.get());
    }

    private static ItemStack[] stacks(ItemStack... stacks) {
        return stacks;
    }

    private static ItemStack[] noStacks() {
        return new ItemStack[0];
    }

    private record CropSpec(
            String id,
            String discoveredBy,
            CropProperties properties,
            String[] attributes,
            int maxSize,
            Supplier<ItemStack[]> drops,
            Supplier<ItemStack[]> specialDrops,
            int growthSpeed,
            int afterHarvestSize,
            RegistryObject<Item> baseSeed) {
    }

    private record LegacyCropType(
            String name,
            RegistryObject<Ic2TileEntityBlock> cropBlock,
            int maxAge) implements ICropType {
        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getOwner() {
            return IC2_ID;
        }

        @Override
        public Block getCropBlock() {
            return cropBlock.get();
        }

        @Override
        public int getMaxAge() {
            return maxAge;
        }
    }

    public static final class LegacyGenericCropCard extends CropCard {
        private final CropSpec spec;
        private final ItemStack[] drops;
        private final ItemStack[] specialDrops;

        private LegacyGenericCropCard(LegacyCropType type, CropSpec spec) {
            super(type);
            this.spec = spec;
            this.drops = copyNonEmpty(spec.drops().get());
            this.specialDrops = copyNonEmpty(spec.specialDrops().get());
        }

        @Override
        public Block getCropBlock() {
            return cropType.getCropBlock();
        }

        @Override
        public String getDiscoveredBy() {
            return spec.discoveredBy();
        }

        @Override
        public int getRootsLength(ICropTile cropTile) {
            return 5;
        }

        @Override
        public CropProperties getProperties() {
            return spec.properties();
        }

        @Override
        public String[] getAttributes() {
            return spec.attributes().clone();
        }

        @Override
        public int getGrowthDuration(ICropTile cropTile) {
            int factor = spec.growthSpeed() < 200 ? 200 : spec.growthSpeed();
            return spec.properties().getTier() * factor;
        }

        @Override
        public boolean canCross(ICropTile cropTile) {
            return cropTile.getCurrentAge() >= getMaxAge() - 1;
        }

        @Override
        public boolean canGrow(ICropTile cropTile) {
            return cropTile.getCurrentAge() < getMaxAge();
        }

        @Override
        public boolean canBeHarvested(ICropTile cropTile) {
            return cropTile.getCurrentAge() >= getMaxAge();
        }

        @Override
        public int getOptimalHarvestAge(ICropTile cropTile) {
            return getMaxAge();
        }

        @Override
        public int getAgeAfterHarvest(ICropTile cropTile) {
            return spec.afterHarvestSize() - 1;
        }

        @Override
        public ItemStack[] getGains(ICropTile cropTile) {
            ItemStack[] gains = copyNonEmpty(drops);
            if (specialDrops.length > 0) {
                int roulette = IC2.random.m_188503_(specialDrops.length * 2 + 2);
                if (roulette < specialDrops.length) {
                    ItemStack[] expanded = new ItemStack[gains.length + 1];
                    System.arraycopy(gains, 0, expanded, 0, gains.length);
                    expanded[gains.length] = specialDrops[roulette].m_41777_();
                    gains = expanded;
                }
            }
            return gains;
        }

        @Override
        public List<ResourceLocation> getTexturesLocation() {
            List<ResourceLocation> textures = new ArrayList<>(getMaxAge() + 1);
            for (int age = 0; age <= getMaxAge(); age++) {
                textures.add(new ResourceLocation(
                        IC2_ID, "blocks/crop/" + getId() + "_" + (age + 1)));
            }
            return textures;
        }

        public int legacyMaxSize() {
            return spec.maxSize();
        }

        public int legacyGrowthSpeed() {
            return spec.growthSpeed();
        }

        public int legacyAfterHarvestSize() {
            return spec.afterHarvestSize();
        }

        public int specialDropCount() {
            return specialDrops.length;
        }

        private static ItemStack[] copyNonEmpty(ItemStack[] source) {
            List<ItemStack> result = new ArrayList<>(source.length);
            for (ItemStack stack : source) {
                if (stack != null && !stack.m_41619_()) {
                    result.add(stack.m_41777_());
                }
            }
            return result.toArray(ItemStack[]::new);
        }
    }
}
