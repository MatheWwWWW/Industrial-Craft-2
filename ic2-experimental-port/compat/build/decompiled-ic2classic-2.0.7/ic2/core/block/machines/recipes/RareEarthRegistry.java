/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.common.crafting.CraftingHelper
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.machines.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.registries.IRareEarthRegistry;
import ic2.core.IC2;
import ic2.core.block.machines.recipes.IRecipeList;
import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class RareEarthRegistry
implements IRareEarthRegistry,
IRecipeList {
    List<Consumer<IRareEarthRegistry>> listeners = ObjectLists.synchronize(CollectionUtils.createList());
    Object2ObjectMap<ResourceLocation, ItemStack> outputs = CollectionUtils.createLinkedMap();
    Object2ObjectMap<ItemStack, IRareEarthRegistry.RareEntry> inputMap = CollectionUtils.createLinkedMap(ItemStackStrategy.INSTANCE);

    public RareEarthRegistry(Consumer<IRareEarthRegistry> listener) {
        this.registerListener(listener);
    }

    @Override
    public void registerListener(Consumer<IRareEarthRegistry> listener) {
        if (listener == null) {
            return;
        }
        this.listeners.add(listener);
    }

    public void reload() {
        this.outputs.clear();
        this.inputMap.clear();
        for (Consumer<IRareEarthRegistry> entry : this.listeners) {
            entry.accept(this);
        }
    }

    @Override
    public void writeRecipes(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.outputs.size());
        for (Object entry : Object2ObjectMaps.fastIterable(this.outputs)) {
            buffer.m_130085_((ResourceLocation)entry.getKey());
            buffer.m_130055_((ItemStack)entry.getValue());
        }
        buffer.m_130130_(this.inputMap.size());
        for (Object entry : this.inputMap.values()) {
            buffer.m_130085_(((IRareEarthRegistry.RareEntry)entry).getID());
            buffer.m_130055_(((IRareEarthRegistry.RareEntry)entry).getInput());
            buffer.writeFloat(((IRareEarthRegistry.RareEntry)entry).getValue());
            buffer.m_130055_(((IRareEarthRegistry.RareEntry)entry).getOutput());
        }
    }

    @Override
    public void readRecipes(FriendlyByteBuf buffer) {
        ResourceLocation location;
        int i;
        Object2ObjectSortedMap outputs = CollectionUtils.createLinkedMap();
        Object2ObjectSortedMap inputs = CollectionUtils.createLinkedMap(ItemStackStrategy.INSTANCE);
        int count = buffer.m_130242_();
        for (i = 0; i < count; ++i) {
            location = buffer.m_130281_();
            ItemStack output = buffer.m_130267_();
            outputs.put((Object)location, (Object)output);
        }
        count = buffer.m_130242_();
        for (i = 0; i < count; ++i) {
            location = buffer.m_130281_();
            ItemStack input = buffer.m_130267_();
            float value = buffer.readFloat();
            ItemStack output = buffer.m_130267_();
            inputs.put((Object)input, (Object)new IRareEarthRegistry.RareEntry(location, input, value, output));
        }
        this.outputs = outputs;
        this.inputMap = inputs;
    }

    @Override
    public String getFolder() {
        return "rare_earth";
    }

    @Override
    public Map<ResourceLocation, JsonObject> writeRecipes() {
        Object2ObjectSortedMap<ResourceLocation, JsonObject> map = CollectionUtils.createLinkedMap();
        Object2ObjectSortedMap mapped = CollectionUtils.createLinkedMap();
        for (IRareEarthRegistry.RareEntry entry : this.inputMap.values()) {
            ((List)mapped.computeIfAbsent((Object)entry.getID(), T -> CollectionUtils.createList())).add(entry);
        }
        for (IRareEarthRegistry.RareEntry entry : mapped.object2ObjectEntrySet()) {
            JsonObject obj = new JsonObject();
            obj.add("output", (JsonElement)IInput.writeItemStack((ItemStack)this.outputs.get(entry.getKey())));
            JsonArray array = new JsonArray();
            for (IRareEarthRegistry.RareEntry rare : (List)entry.getValue()) {
                JsonObject data = new JsonObject();
                data.addProperty("item", ForgeRegistries.ITEMS.getKey((Object)rare.getInput().m_41720_()).toString());
                data.addProperty("value", (Number)Float.valueOf(rare.getValue()));
                array.add((JsonElement)data);
            }
            obj.add("inputs", (JsonElement)array);
            map.put((ResourceLocation)entry.getKey(), obj);
        }
        return map;
    }

    @Override
    public void readRecipe(ResourceLocation id, JsonObject data) throws Exception {
        if (IRecipeList.isRemover(data)) {
            IRecipeList.getEntriesToRemove(data, this::remove);
            return;
        }
        ItemStack output = CraftingHelper.getItemStack((JsonObject)GsonHelper.m_13930_((JsonObject)data, (String)"output"), (boolean)true);
        if (output.m_41619_()) {
            throw new RuntimeException("Output is Empty");
        }
        this.registerOutput(id, output);
        IRecipeList.iterateObject(data.get("inputs"), T -> {
            Item item = CraftingHelper.getItem((String)GsonHelper.m_13906_((JsonObject)T, (String)"item"), (boolean)true);
            float value = GsonHelper.m_13915_((JsonObject)T, (String)"value");
            if (value <= 0.0f) {
                throw new RuntimeException("Value needs to be bigger then 0");
            }
            this.registerInput(id, value, new ItemStack((ItemLike)item));
        });
    }

    @Override
    public void registerOutput(ResourceLocation id, ItemStack output) {
        if (output.m_41619_() || id == null) {
            return;
        }
        this.outputs.put((Object)id, (Object)output.m_41777_());
    }

    @Override
    public void registerInput(ResourceLocation id, float value, ItemStack ... inputs) {
        ItemStack output = (ItemStack)this.outputs.get((Object)id);
        if (output == null) {
            IC2.LOGGER.info("No Output found for [" + id + "] ignoring recipes");
            return;
        }
        for (ItemStack stack : inputs) {
            if (stack.m_41619_()) continue;
            this.inputMap.put((Object)stack, (Object)new IRareEarthRegistry.RareEntry(id, stack.m_41777_(), value, output.m_41777_()));
        }
    }

    @Override
    public IRareEarthRegistry.RareEntry getOutputFor(ItemStack input) {
        return (IRareEarthRegistry.RareEntry)this.inputMap.get((Object)input);
    }

    @Override
    public ItemStack getOutputFor(ResourceLocation id) {
        return (ItemStack)this.outputs.getOrDefault((Object)id, (Object)ItemStack.f_41583_);
    }

    @Override
    public IRareEarthRegistry.RareEntry removeInput(ItemStack input) {
        return (IRareEarthRegistry.RareEntry)this.inputMap.remove((Object)input);
    }

    @Override
    public List<IRareEarthRegistry.RareEntry> getAllRecipes() {
        return new ObjectArrayList(this.inputMap.values());
    }

    @Override
    public void removeOutput(ItemStack output) {
        ObjectIterator iter = Object2ObjectMaps.fastIterator(this.outputs);
        while (iter.hasNext()) {
            Object2ObjectMap.Entry entry = (Object2ObjectMap.Entry)iter.next();
            if (!StackUtil.isStackEqual(output, (ItemStack)entry.getValue())) continue;
            this.removeInputs((ResourceLocation)entry.getKey());
            iter.remove();
        }
    }

    @Override
    public void remove(ResourceLocation id) {
        this.outputs.remove((Object)id);
        this.removeInputs(id);
    }

    private void removeInputs(ResourceLocation location) {
        ObjectIterator iter = this.inputMap.values().iterator();
        while (iter.hasNext()) {
            if (!((IRareEarthRegistry.RareEntry)iter.next()).matches(location)) continue;
            iter.remove();
        }
    }
}

