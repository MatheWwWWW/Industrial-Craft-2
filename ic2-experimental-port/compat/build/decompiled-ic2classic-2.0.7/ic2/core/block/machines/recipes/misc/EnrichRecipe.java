/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  it.unimi.dsi.fastutil.objects.ObjectSets
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.machines.recipes.misc;

import ic2.api.recipes.RecipeRegistry;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.inputs.ItemInput;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import java.util.List;
import java.util.Set;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

public class EnrichRecipe {
    ResourceLocation id;
    ObjectSet<Item> input;
    Object2IntMap<IInput> fuels;
    ItemStack output;
    int points;
    int energyCost;
    int color;

    public EnrichRecipe(ResourceLocation id, Item input, Object2IntMap<IInput> fuels, ItemStack output, int points, int color, int energyCost) {
        this(id, (ObjectSet<Item>)ObjectLinkedOpenHashSet.of((Object)input), fuels, output, points, color, energyCost);
    }

    public EnrichRecipe(ResourceLocation id, ObjectSet<Item> input, Object2IntMap<IInput> fuels, ItemStack output, int points, int color, int energyCost) {
        this.id = id;
        this.input = input;
        this.fuels = fuels;
        this.output = output;
        this.points = points;
        this.color = color;
        this.energyCost = energyCost;
    }

    public EnrichRecipe(FriendlyByteBuf buffer) {
        this.id = buffer.m_130281_();
        int inputCount = buffer.m_130242_();
        this.input = ObjectLinkedOpenHashSet.of();
        for (int i = 0; i < inputCount; ++i) {
            this.input.add((Object)((Item)buffer.readRegistryIdUnsafe(ForgeRegistries.ITEMS)));
        }
        this.fuels = new Object2IntLinkedOpenHashMap();
        int size = buffer.m_130242_();
        for (int i = 0; i < size; ++i) {
            int points = buffer.m_130242_();
            IInput input = RecipeRegistry.INGREDIENTS.readInput(buffer);
            if (input == null) continue;
            this.fuels.put((Object)input, points);
        }
        this.output = buffer.m_130267_();
        this.points = buffer.m_130242_();
        this.energyCost = buffer.m_130242_();
        this.color = buffer.readInt();
    }

    public static EnrichRecipe createRecipe(Item input, Object2IntMap<IInput> fuels, Item output, int points, int color, int energyCost, ResourceLocation id) {
        return new EnrichRecipe(id, input, fuels, new ItemStack((ItemLike)output), points, color, energyCost);
    }

    public static EnrichRecipe createRecipe(Item input, Item fuel, int fuelPoints, Item output, int points, int color, int energyCost, ResourceLocation id) {
        return new EnrichRecipe(id, input, (Object2IntMap<IInput>)Object2IntMaps.singleton((Object)new ItemInput(fuel), (int)fuelPoints), new ItemStack((ItemLike)output), points, color, energyCost);
    }

    public static EnrichRecipe createIC2Recipe(Item input, Object2IntMap<IInput> fuels, Item output, int points, int color, int energyCost, String id) {
        return new EnrichRecipe(new ResourceLocation("ic2", id), input, fuels, new ItemStack((ItemLike)output), points, color, energyCost);
    }

    public static EnrichRecipe createIC2Recipe(Item input, Item fuel, int fuelPoints, Item output, int points, int color, int energyCost, String id) {
        return new EnrichRecipe(new ResourceLocation("ic2", id), input, (Object2IntMap<IInput>)Object2IntMaps.singleton((Object)new ItemInput(fuel), (int)fuelPoints), new ItemStack((ItemLike)output), points, color, energyCost);
    }

    public boolean isValidInput(ItemStack inputStack, ItemStack outputStack) {
        return this.input.contains((Object)inputStack.m_41720_()) && (outputStack.m_41619_() || StackUtil.isStackEqual(this.output, outputStack) && StackUtil.getStackSizeLeft(outputStack) >= this.output.m_41613_());
    }

    public List<ItemStack> getFuels() {
        ObjectArrayList list = new ObjectArrayList();
        for (IInput input : this.fuels.keySet()) {
            list.addAll(input.getComponents());
        }
        return list;
    }

    public int getFuelValue(ItemStack stack) {
        for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable(this.fuels)) {
            if (!((IInput)entry.getKey()).matches(stack)) continue;
            return entry.getIntValue();
        }
        return 0;
    }

    public int getRequiredPoints() {
        return this.points;
    }

    public int getEnergyCost() {
        return this.energyCost;
    }

    public Set<Item> getInputs() {
        return ObjectSets.unmodifiable(this.input);
    }

    public void addInput(Item item) {
        this.input.add((Object)item);
    }

    public void addInputs(Set<Item> items) {
        this.input.addAll(items);
    }

    public ItemStack getOutput() {
        return this.output.m_41777_();
    }

    public int getColor() {
        return this.color;
    }

    public Object2IntMap<IInput> getFuelValues() {
        return Object2IntMaps.unmodifiable(this.fuels);
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.m_130085_(this.id);
        buffer.m_130130_(this.input.size());
        for (Item i : this.input) {
            buffer.writeRegistryIdUnsafe(ForgeRegistries.ITEMS, (Object)i);
        }
        buffer.m_130130_(this.fuels.size());
        for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable(this.fuels)) {
            buffer.m_130130_(entry.getIntValue());
            RecipeRegistry.INGREDIENTS.writeInput((IInput)entry.getKey(), buffer);
        }
        buffer.m_130055_(this.output);
        buffer.m_130130_(this.points);
        buffer.m_130130_(this.energyCost);
        buffer.writeInt(this.color);
    }
}

