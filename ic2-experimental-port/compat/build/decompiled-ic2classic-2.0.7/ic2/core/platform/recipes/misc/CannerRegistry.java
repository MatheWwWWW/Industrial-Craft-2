/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.common.crafting.CraftingHelper
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.recipes.misc;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.items.IRepairable;
import ic2.api.recipes.RecipeRegistry;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.registries.ICannerRecipeRegistry;
import ic2.core.block.machines.recipes.IRecipeList;
import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.platform.recipes.misc.BaseRegistry;
import ic2.core.platform.recipes.misc.IngredientRegistry;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class CannerRegistry
extends BaseRegistry<ICannerRecipeRegistry>
implements ICannerRecipeRegistry,
IRecipeList {
    Map<Item, List<Tuple<IInput, Integer>>> repairRegistry = CollectionUtils.createLinkedMap();
    Map<ItemStack, ICannerRecipeRegistry.FuelValue> fuelPoints = CollectionUtils.createLinkedMap(ItemStackStrategy.INSTANCE);
    Map<ItemStack, List<Tuple<IInput, ItemStack>>> cannable = CollectionUtils.createLinkedMap(ItemStackStrategy.INSTANCE);

    public CannerRegistry(Consumer<ICannerRecipeRegistry> listener) {
        this.registerListener(listener);
    }

    @Override
    protected void reloadInternals() {
        this.repairRegistry.clear();
        this.fuelPoints.clear();
        this.cannable.clear();
    }

    @Override
    public String getFolder() {
        return "canner";
    }

    @Override
    public void readRecipe(ResourceLocation id, JsonObject data) throws Exception {
        if (data.has("recipes")) {
            for (JsonElement elem : GsonHelper.m_13933_((JsonObject)data, (String)"recipes")) {
                JsonObject obj = elem.getAsJsonObject();
                this.readRecipe(id, obj);
            }
            return;
        }
        String type = GsonHelper.m_13906_((JsonObject)data, (String)"type");
        if (type.equals("fuel")) {
            ItemStack stack = CraftingHelper.getItemStack((JsonObject)GsonHelper.m_13930_((JsonObject)data, (String)"input"), (boolean)true);
            if (GsonHelper.m_13855_((JsonObject)data, (String)"remove", (boolean)false)) {
                this.removeValue(stack);
                return;
            }
            if (GsonHelper.m_13855_((JsonObject)data, (String)"multiplier", (boolean)false)) {
                this.registerFuelMultiplier(stack, GsonHelper.m_13915_((JsonObject)data, (String)"value"));
            } else {
                this.registerFuelValue(stack, GsonHelper.m_13927_((JsonObject)data, (String)"value"));
            }
        } else if (type.equals("canning")) {
            ItemStack container = CraftingHelper.getItemStack((JsonObject)GsonHelper.m_13930_((JsonObject)data, (String)"container"), (boolean)true);
            IInput input = IngredientRegistry.INSTANCE.readInput(GsonHelper.m_13930_((JsonObject)data, (String)"input"));
            if (GsonHelper.m_13855_((JsonObject)data, (String)"remove", (boolean)false)) {
                this.removeFillable(container, input.getComponents().get(0));
                return;
            }
            this.registerFillable(container, input, CraftingHelper.getItemStack((JsonObject)GsonHelper.m_13930_((JsonObject)data, (String)"output"), (boolean)true));
        }
    }

    @Override
    public Map<ResourceLocation, JsonObject> writeRecipes() {
        JsonArray result = new JsonArray();
        for (Map.Entry<ItemStack, ICannerRecipeRegistry.FuelValue> entry : this.fuelPoints.entrySet()) {
            ICannerRecipeRegistry.FuelValue value = entry.getValue();
            JsonObject obj = new JsonObject();
            obj.addProperty("type", "fuel");
            obj.add("input", (JsonElement)IInput.writeItemStack(entry.getKey()));
            if (value.isMultiplier()) {
                obj.addProperty("multiplier", Boolean.valueOf(true));
                obj.addProperty("value", (Number)Float.valueOf(value.getFuelMultiplier()));
            } else {
                obj.addProperty("multiplier", Boolean.valueOf(false));
                obj.addProperty("value", (Number)value.getFuelValue());
            }
            result.add((JsonElement)obj);
        }
        Object2ObjectSortedMap<ResourceLocation, JsonObject> map = CollectionUtils.createLinkedMap(new ResourceLocation("ic2:fuels"), IRecipeList.mapArray("recipes", result));
        result = new JsonArray();
        for (Map.Entry<ItemStack, List<Tuple<IInput, ItemStack>>> entry : this.cannable.entrySet()) {
            JsonObject container = IInput.writeItemStack(entry.getKey());
            for (Tuple<IInput, ItemStack> subEntry : entry.getValue()) {
                JsonObject obj = new JsonObject();
                obj.addProperty("type", "canning");
                obj.add("input", (JsonElement)IngredientRegistry.INSTANCE.serializeInput((IInput)subEntry.m_14418_()));
                obj.add("container", (JsonElement)container);
                obj.add("output", (JsonElement)IInput.writeItemStack((ItemStack)subEntry.m_14419_()));
                result.add((JsonElement)obj);
            }
        }
        map.put(new ResourceLocation("ic2:canning"), IRecipeList.mapArray("recipes", result));
        return map;
    }

    @Override
    public void writeRecipes(FriendlyByteBuf buffer) {
        List list;
        buffer.m_130130_(this.repairRegistry.size());
        for (Map.Entry<Item, List<Tuple<IInput, Integer>>> entry : this.repairRegistry.entrySet()) {
            buffer.writeRegistryIdUnsafe(ForgeRegistries.ITEMS, (Object)entry.getKey());
            list = entry.getValue();
            buffer.m_130130_(list.size());
            for (Tuple<IInput, Integer> sub : list) {
                RecipeRegistry.INGREDIENTS.writeInput((IInput)sub.m_14418_(), buffer);
                buffer.writeInt(((Integer)sub.m_14419_()).intValue());
            }
        }
        buffer.m_130130_(this.fuelPoints.size());
        for (ICannerRecipeRegistry.FuelValue fuelValue : this.fuelPoints.values()) {
            fuelValue.write(buffer);
        }
        buffer.m_130130_(this.cannable.size());
        for (Map.Entry entry : this.cannable.entrySet()) {
            buffer.m_130055_((ItemStack)entry.getKey());
            list = (List)entry.getValue();
            buffer.m_130130_(list.size());
            for (Tuple subEntry : list) {
                RecipeRegistry.INGREDIENTS.writeInput((IInput)subEntry.m_14418_(), buffer);
                buffer.m_130055_((ItemStack)subEntry.m_14419_());
            }
        }
    }

    @Override
    public void readRecipes(FriendlyByteBuf buffer) {
        Object2ObjectSortedMap replaceMap = CollectionUtils.createLinkedMap();
        int read = buffer.m_130242_();
        for (int i = 0; i < read; ++i) {
            Item item = (Item)buffer.readRegistryIdUnsafe(ForgeRegistries.ITEMS);
            int size = buffer.m_130242_();
            ObjectList list = CollectionUtils.createList();
            for (int j = 0; j < size; ++j) {
                IInput input = RecipeRegistry.INGREDIENTS.readInput(buffer);
                list.add((Tuple)new Tuple((Object)input, (Object)buffer.readInt()));
            }
            replaceMap.put(item, list);
        }
        this.repairRegistry = replaceMap;
        read = buffer.m_130242_();
        Object2ObjectSortedMap<ItemStack, ICannerRecipeRegistry.FuelValue> replaceFuel = CollectionUtils.createLinkedMap(ItemStackStrategy.INSTANCE);
        for (int i = 0; i < read; ++i) {
            ICannerRecipeRegistry.FuelValue value = new ICannerRecipeRegistry.FuelValue(buffer);
            replaceFuel.put(value.getStack(), value);
        }
        this.fuelPoints = replaceFuel;
        Object2ObjectSortedMap canReplace = CollectionUtils.createLinkedMap(ItemStackStrategy.INSTANCE);
        read = buffer.m_130242_();
        for (int i = 0; i < read; ++i) {
            ItemStack container = buffer.m_130267_();
            ObjectList list = CollectionUtils.createList();
            int size = buffer.m_130242_();
            for (int j = 0; j < size; ++j) {
                IInput input = RecipeRegistry.INGREDIENTS.readInput(buffer);
                list.add((Tuple)new Tuple((Object)input, (Object)buffer.m_130267_()));
            }
            canReplace.put(container, list);
        }
        this.cannable = canReplace;
    }

    @Override
    public void registerRepairable(IRepairable repair, IInput input, int repairAmount) {
        List<Tuple<IInput, Integer>> list = this.repairRegistry.get(repair.m_5456_());
        if (list == null) {
            list = CollectionUtils.createList();
            this.repairRegistry.put(repair.m_5456_(), list);
        }
        list.add((Tuple<IInput, Integer>)new Tuple((Object)input, (Object)repairAmount));
    }

    @Override
    public Tuple<IInput, Integer> getRepairableItem(ItemStack input, ItemStack repair, boolean compareStack) {
        for (Tuple entry : this.repairRegistry.getOrDefault(input.m_41720_(), Collections.emptyList())) {
            if (!((IInput)entry.m_14418_()).matches(repair)) continue;
            return entry;
        }
        return null;
    }

    @Override
    public Map<Item, List<Tuple<IInput, Integer>>> getRepairItems() {
        return new Object2ObjectLinkedOpenHashMap(this.repairRegistry);
    }

    @Override
    public void removeRepairable(IRepairable repair) {
        this.repairRegistry.remove(repair.m_5456_());
    }

    @Override
    public void registerFuelValue(ItemStack stack, int amount) {
        this.fuelPoints.put(stack, new ICannerRecipeRegistry.FuelValue(stack.m_41777_(), amount));
    }

    @Override
    public void registerFuelMultiplier(ItemStack stack, float multiplier) {
        this.fuelPoints.put(stack, new ICannerRecipeRegistry.FuelValue(stack.m_41777_(), multiplier));
    }

    @Override
    public ICannerRecipeRegistry.FuelValue getValueForStack(ItemStack stack) {
        return this.fuelPoints.get(stack);
    }

    @Override
    public List<ICannerRecipeRegistry.FuelValue> getFuels() {
        return new ObjectArrayList(this.fuelPoints.values());
    }

    @Override
    public void removeValue(ItemStack stack) {
        this.fuelPoints.remove(stack);
    }

    @Override
    public void registerFillable(ItemStack container, IInput fillable, ItemStack output) {
        List<Tuple<IInput, ItemStack>> list = this.cannable.get(container);
        if (list == null) {
            list = CollectionUtils.createList();
            this.cannable.put(container, list);
        }
        list.add((Tuple<IInput, ItemStack>)new Tuple((Object)fillable, (Object)output.m_41777_()));
    }

    @Override
    public Tuple<IInput, ItemStack> getFillOutput(ItemStack container, ItemStack toFill, boolean compareStack) {
        for (Tuple<IInput, ItemStack> entry : this.cannable.get(container)) {
            if (!((IInput)entry.m_14418_()).matches(toFill) || compareStack && ((IInput)entry.m_14418_()).getInputSize() > toFill.m_41613_()) continue;
            return entry;
        }
        return null;
    }

    @Override
    public Map<ItemStack, List<Tuple<IInput, ItemStack>>> getFillables() {
        Object2ObjectSortedMap<ItemStack, List<Tuple<IInput, ItemStack>>> maps = CollectionUtils.createLinkedMap(ItemStackStrategy.INSTANCE);
        for (Map.Entry<ItemStack, List<Tuple<IInput, ItemStack>>> entry : this.cannable.entrySet()) {
            maps.put(entry.getKey().m_41777_(), entry.getValue());
        }
        return maps;
    }

    @Override
    public boolean hasContainer(ItemStack container) {
        return this.cannable.get(container) != null;
    }

    @Override
    public void removeFillable(ItemStack container) {
        this.cannable.remove(container);
    }

    @Override
    public void removeFillable(ItemStack container, ItemStack fillable) {
        List<Tuple<IInput, ItemStack>> list = this.cannable.get(container);
        if (list == null) {
            return;
        }
        Iterator<Tuple<IInput, ItemStack>> iter = list.iterator();
        while (iter.hasNext()) {
            if (!((IInput)iter.next().m_14418_()).matches(fillable)) continue;
            iter.remove();
        }
        if (list.isEmpty()) {
            this.cannable.remove(container);
        }
    }
}

