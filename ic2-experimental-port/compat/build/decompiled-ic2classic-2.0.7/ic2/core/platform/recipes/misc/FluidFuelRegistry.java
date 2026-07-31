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
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.recipes.misc;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.registries.IFluidFuelRegistry;
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
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidFuelRegistry
extends BaseRegistry<IFluidFuelRegistry>
implements IFluidFuelRegistry,
IRecipeList {
    Map<Fluid, IFluidFuelRegistry.FuelEntry> fuels = CollectionUtils.createLinkedMap();

    public FluidFuelRegistry(Consumer<IFluidFuelRegistry> listener) {
        this.registerListener(listener);
    }

    @Override
    protected void reloadInternals() {
        this.fuels.clear();
    }

    @Override
    public void writeRecipes(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.fuels.size());
        for (IFluidFuelRegistry.FuelEntry entry : this.fuels.values()) {
            entry.write(buffer);
        }
    }

    @Override
    public void readRecipes(FriendlyByteBuf buffer) {
        Object2ObjectSortedMap<Fluid, IFluidFuelRegistry.FuelEntry> newMap = CollectionUtils.createLinkedMap();
        int size = buffer.m_130242_();
        for (int i = 0; i < size; ++i) {
            IFluidFuelRegistry.FuelEntry entry = new IFluidFuelRegistry.FuelEntry(buffer);
            if (entry.getFluid() == Fluids.f_76191_) continue;
            newMap.put(entry.getFluid(), entry);
        }
        this.fuels = newMap;
    }

    @Override
    public String getFolder() {
        return "liquid_fuel";
    }

    @Override
    public Map<ResourceLocation, JsonObject> writeRecipes() {
        JsonArray array = new JsonArray();
        for (IFluidFuelRegistry.FuelEntry entry : this.fuels.values()) {
            JsonObject obj = new JsonObject();
            obj.addProperty("fluid", ForgeRegistries.FLUIDS.getKey((Object)entry.getFluid()).toString());
            obj.addProperty("burn_time", (Number)entry.getTicksPerBucket());
            obj.addProperty("production", (Number)entry.getEuPerTick());
            array.add((JsonElement)obj);
        }
        return CollectionUtils.createLinkedMap(new ResourceLocation("ic2:fuels"), IRecipeList.mapArray("fuels", array));
    }

    @Override
    public void readRecipe(ResourceLocation id, JsonObject data) throws Exception {
        IRecipeList.iterateObject(data.get("fuels"), T -> {
            Fluid fluid = (Fluid)ForgeRegistries.FLUIDS.getValue(new ResourceLocation(GsonHelper.m_13906_((JsonObject)T, (String)"fluid")));
            if (GsonHelper.m_13855_((JsonObject)T, (String)"remove", (boolean)false)) {
                this.removeFuel(fluid);
                return;
            }
            int burntime = GsonHelper.m_13927_((JsonObject)T, (String)"burn_time");
            if (burntime <= 0) {
                throw new RuntimeException("Fuel Burntime isn't allowed to be 0 or less");
            }
            int production = GsonHelper.m_13927_((JsonObject)T, (String)"production");
            if (production <= 0) {
                throw new RuntimeException("Fuel Production isn't allowed to be 0 or less");
            }
            this.addFuel(fluid, burntime, production);
        });
    }

    @Override
    public void addFuel(Fluid fluid, int ticksPerBucket, int euPerTick) {
        if (ticksPerBucket < 1 || euPerTick < 1 || fluid == Fluids.f_76191_) {
            return;
        }
        this.fuels.put(fluid, new IFluidFuelRegistry.FuelEntry(fluid, ticksPerBucket, euPerTick));
    }

    @Override
    public void removeFuel(Fluid fluid) {
        this.fuels.remove(fluid);
    }

    @Override
    public List<IFluidFuelRegistry.FuelEntry> getFuels() {
        return new ObjectArrayList(this.fuels.values());
    }

    @Override
    public IFluidFuelRegistry.FuelEntry getFuel(Fluid fluid) {
        return this.fuels.get(fluid);
    }
}

