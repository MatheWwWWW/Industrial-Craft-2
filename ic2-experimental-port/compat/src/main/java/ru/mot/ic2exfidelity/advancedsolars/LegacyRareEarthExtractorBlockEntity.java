package ru.mot.ic2exfidelity.advancedsolars;

import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.network.GrowingBuffer;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

/** IC2 Classic's exact 35-tick rare-earth/aluminium accumulation machine. */
public final class LegacyRareEarthExtractorBlockEntity extends TileEntityElectricMachine
        implements IHasGui, IGuiValueProvider {
    public static final int CAPACITY = 1_120;
    public static final int INPUT_TIER = 1;
    public static final int OPERATION_TICKS = 35;
    public static final int ENERGY_PER_TICK = 1;
    public static final float MATERIAL_TARGET = 1_000.0F;

    private static final String RARE_EARTH = "rare_earth";
    private static final String ALUMINIUM = "aluminium";
    private static final Map<String, Entry> INPUTS = Map.ofEntries(
            rare("stone", 3.125F),
            rare("deepslate", 3.125F),
            rare("granite", 62.5F),
            rare("diorite", 62.5F),
            rare("andesite", 62.5F),
            rare("tuff", 62.5F),
            rare("netherrack", 7.8125F),
            rare("mycelium", 100.0F),
            rare("obsidian", 200.0F),
            rare("soul_sand", 200.0F),
            rare("soul_soil", 200.0F),
            aluminium("clay_ball", 500.0F),
            aluminium("terracotta", 500.0F),
            aluminium("white_terracotta", 500.0F),
            aluminium("orange_terracotta", 500.0F),
            aluminium("magenta_terracotta", 500.0F),
            aluminium("light_blue_terracotta", 500.0F),
            aluminium("yellow_terracotta", 500.0F),
            aluminium("lime_terracotta", 500.0F),
            aluminium("pink_terracotta", 500.0F),
            aluminium("gray_terracotta", 500.0F),
            aluminium("light_gray_terracotta", 500.0F),
            aluminium("cyan_terracotta", 500.0F),
            aluminium("purple_terracotta", 500.0F),
            aluminium("blue_terracotta", 500.0F),
            aluminium("brown_terracotta", 500.0F),
            aluminium("green_terracotta", 500.0F),
            aluminium("red_terracotta", 500.0F),
            aluminium("black_terracotta", 500.0F),
            aluminium("dirt", 100.0F));

    public final InvSlot input;
    public final InvSlotOutput output;
    private String currentMaterial = "";
    private float materialProgress;
    private float progress;

    public LegacyRareEarthExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(LegacyAdvancedSolarsPrerequisites.RARE_EARTH_EXTRACTOR_BLOCK_ENTITY.get(),
                pos, state, CAPACITY, INPUT_TIER);
        input = new InvSlot(this, "input", InvSlot.Access.I, 1, InvSlot.InvSide.ANY) {
            @Override
            public boolean accepts(ItemStack stack) {
                return findEntry(stack) != null;
            }
        };
        output = new InvSlotOutput(this, "output", 1, InvSlot.InvSide.ANY);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        Entry entry = findEntry(input.get());
        if (entry == null || !canAdd(entry) || !energy.useEnergy(ENERGY_PER_TICK)) {
            progress = Math.max(0.0F, input.isEmpty() ? 0.0F : progress - 1.0F);
            setActiveState(false);
            return;
        }

        if (!entry.material().equals(currentMaterial)) {
            currentMaterial = entry.material();
            materialProgress = 0.0F;
        }
        setActiveState(true);
        progress += 1.0F;
        if (progress >= OPERATION_TICKS) {
            progress = 0.0F;
            materialProgress += entry.value();
            consumeOne(input);
            if (materialProgress >= MATERIAL_TARGET) {
                materialProgress -= MATERIAL_TARGET;
                output.add(new ItemStack(outputItem(entry.material())));
            }
            m_6596_();
        }
    }

    private boolean canAdd(Entry entry) {
        if (materialProgress + entry.value() < MATERIAL_TARGET) {
            return true;
        }
        return output.canAdd(new ItemStack(outputItem(entry.material())));
    }

    private static Entry findEntry(ItemStack stack) {
        if (stack == null || stack.m_41619_()) {
            return null;
        }
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.m_41720_());
        return id == null || !"minecraft".equals(id.m_135827_())
                ? null : INPUTS.get(id.m_135815_());
    }

    private static Item outputItem(String material) {
        String id = RARE_EARTH.equals(material) ? "rare_earth_dust" : "dust_aluminium";
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("ic2", id));
        if (item == null) {
            throw new IllegalStateException("Missing IC2 Classic rare-earth output: " + id);
        }
        return item;
    }

    private static void consumeOne(InvSlot slot) {
        ItemStack stack = slot.get();
        stack.m_41774_(1);
        if (stack.m_41619_()) {
            slot.clear();
        } else {
            slot.put(stack);
        }
    }

    private static Map.Entry<String, Entry> rare(String item, float value) {
        return Map.entry(item, new Entry(RARE_EARTH, value));
    }

    private static Map.Entry<String, Entry> aluminium(String item, float value) {
        return Map.entry(item, new Entry(ALUMINIUM, value));
    }

    @Override
    public void m_142466_(CompoundTag tag) {
        super.m_142466_(tag);
        currentMaterial = tag.m_128461_("recipe_id");
        materialProgress = tag.m_128457_("material");
        progress = tag.m_128457_("progress");
    }

    @Override
    public void m_183515_(CompoundTag tag) {
        super.m_183515_(tag);
        tag.m_128359_("recipe_id", currentMaterial);
        tag.m_128350_("material", materialProgress);
        tag.m_128350_("progress", progress);
    }

    @Override
    public double getGuiValue(String name) {
        return switch (name) {
            case "progress" -> progress / OPERATION_TICKS;
            case "material" -> materialProgress / MATERIAL_TARGET;
            default -> throw new IllegalArgumentException("Unknown rare-earth GUI value: " + name);
        };
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int syncId, Player player) {
        return DynamicContainer.create(syncId, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(
            int syncId, Inventory inventory, GrowingBuffer buffer) {
        return DynamicContainer.create(syncId, inventory, this);
    }

    public float getProgressTicks() {
        return progress;
    }

    public float getMaterialProgress() {
        return materialProgress;
    }

    public String getCurrentMaterial() {
        return currentMaterial;
    }

    public void forceAddEnergyForTest(double amount) {
        energy.addEnergy(amount);
    }

    private record Entry(String material, float value) {
    }
}
