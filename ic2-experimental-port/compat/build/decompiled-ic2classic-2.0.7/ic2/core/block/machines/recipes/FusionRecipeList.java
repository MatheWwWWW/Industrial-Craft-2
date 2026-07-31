/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.item.Item
 *  net.minecraftforge.common.crafting.CraftingHelper
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.machines.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.registries.IFusionRecipeList;
import ic2.core.block.machines.recipes.IRecipeList;
import ic2.core.platform.recipes.misc.BaseRegistry;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class FusionRecipeList
extends BaseRegistry<IFusionRecipeList>
implements IFusionRecipeList,
IRecipeList {
    Map<Item, IFusionRecipeList.FusionFuel> fuels = CollectionUtils.createLinkedMap();

    public FusionRecipeList(Consumer<IFusionRecipeList> listener) {
        this.registerListener(listener);
    }

    @Override
    protected void reloadInternals() {
        this.fuels.clear();
    }

    @Override
    public void writeRecipes(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.fuels.size());
        for (IFusionRecipeList.FusionFuel fuel : this.fuels.values()) {
            buffer.writeRegistryIdUnsafe(ForgeRegistries.ITEMS, (Object)fuel.fuel());
            buffer.m_130130_(fuel.fuelValue());
            buffer.writeFloat(fuel.productionRate());
            buffer.writeBoolean(fuel.consumed());
        }
    }

    @Override
    public void readRecipes(FriendlyByteBuf buffer) {
        Object2ObjectSortedMap<Item, IFusionRecipeList.FusionFuel> newFuels = CollectionUtils.createLinkedMap();
        int m = buffer.m_130242_();
        for (int i = 0; i < m; ++i) {
            Item item = (Item)buffer.readRegistryIdUnsafe(ForgeRegistries.ITEMS);
            newFuels.put(item, new IFusionRecipeList.FusionFuel(item, buffer.m_130242_(), buffer.readFloat(), buffer.readBoolean()));
        }
        this.fuels = newFuels;
    }

    @Override
    public String getFolder() {
        return "fusion";
    }

    @Override
    public Map<ResourceLocation, JsonObject> writeRecipes() {
        JsonArray array = new JsonArray();
        for (IFusionRecipeList.FusionFuel fuel : this.fuels.values()) {
            JsonObject obj = new JsonObject();
            obj.addProperty("input", ForgeRegistries.ITEMS.getKey((Object)fuel.fuel()).toString());
            obj.addProperty("consumed", Boolean.valueOf(fuel.consumed()));
            obj.addProperty("fuelValue", (Number)fuel.fuelValue());
            obj.addProperty("productionRate", (Number)Float.valueOf(fuel.productionRate()));
            array.add((JsonElement)obj);
        }
        return CollectionUtils.createLinkedMap(new ResourceLocation("ic2:fuels"), IRecipeList.mapArray("fuels", array));
    }

    @Override
    public void readRecipe(ResourceLocation id, JsonObject data) throws Exception {
        IRecipeList.iterateObject(data.get("fuels"), T -> {
            Item item = CraftingHelper.getItem((String)GsonHelper.m_13906_((JsonObject)T, (String)"input"), (boolean)true);
            if (GsonHelper.m_13855_((JsonObject)T, (String)"remove", (boolean)false)) {
                this.removeFuel(item);
                return;
            }
            int fuelValue = GsonHelper.m_13927_((JsonObject)T, (String)"fuelValue");
            if (fuelValue <= 0) {
                throw new RuntimeException("Fuel Value isn't allowed to be 0 or less");
            }
            float productionRate = GsonHelper.m_13915_((JsonObject)T, (String)"productionRate");
            if (productionRate <= 0.0f) {
                throw new RuntimeException("Fuel ProductionRate isn't allowed to be 0 or less");
            }
            this.addFuel(item, fuelValue, productionRate, GsonHelper.m_13855_((JsonObject)T, (String)"consumed", (boolean)false));
        });
    }

    @Override
    public void addFuel(Item fuel, int fuelValue, float productionRate, boolean consumed) {
        this.fuels.put(fuel, new IFusionRecipeList.FusionFuel(fuel, fuelValue, productionRate, consumed));
    }

    @Override
    public IFusionRecipeList.FusionFuel getFuel(Item item) {
        return this.fuels.get(item);
    }

    @Override
    public void removeFuel(Item item) {
        this.fuels.remove(item);
    }

    @Override
    public List<IFusionRecipeList.FusionFuel> getFuels() {
        return new ObjectArrayList(this.fuels.values());
    }
}

