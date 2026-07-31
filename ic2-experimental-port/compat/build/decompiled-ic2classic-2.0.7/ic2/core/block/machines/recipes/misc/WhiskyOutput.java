/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.machines.recipes.misc;

import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.recipes.IFluidRecipeOutput;
import ic2.core.item.food_and_drink.drinks.Whisky;
import ic2.core.platform.registries.IC2Fluids;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.Collections;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class WhiskyOutput
implements IFluidRecipeOutput {
    Item output;

    public WhiskyOutput(Item output) {
        this.output = output;
    }

    public WhiskyOutput(FriendlyByteBuf buffer) {
        this.output = (Item)buffer.readRegistryIdUnsafe(ForgeRegistries.ITEMS);
    }

    public WhiskyOutput(JsonObject obj) {
        this.output = (Item)ForgeRegistries.ITEMS.getValue(ResourceLocation.m_135820_((String)obj.get("item").getAsString()));
    }

    @Override
    public List<ItemStack> onRecipeProcessed(RandomSource rand, CompoundTag persistentData, CompoundTag recipeFlags) {
        return ObjectLists.singleton((Object)new ItemStack((ItemLike)this.output));
    }

    @Override
    public List<ItemStack> getAllOutputs() {
        return ObjectLists.singleton((Object)new ItemStack((ItemLike)this.output));
    }

    @Override
    public CompoundTag getMetadata() {
        return EMPTY_COMPOUND;
    }

    @Override
    public float getExperience() {
        return 0.0f;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.writeRegistryIdUnsafe(ForgeRegistries.ITEMS, (Object)this.output);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("item", ForgeRegistries.ITEMS.getKey((Object)this.output).toString());
        return obj;
    }

    @Override
    public List<FluidStack> onFluidRecipeProcessed(RandomSource rand, CompoundTag persistentData, CompoundTag recipeFlags, ItemStack input) {
        int year = Whisky.getYear(input);
        return year <= 0 ? Collections.emptyList() : ObjectLists.singleton((Object)new FluidStack(IC2Fluids.ALCOHOL, this.getAmountForYear(year)));
    }

    private int getAmountForYear(int year) {
        switch (year) {
            case 1: {
                return 10000;
            }
            case 2: {
                return 12000;
            }
            case 3: {
                return 15000;
            }
            case 4: {
                return 25000;
            }
            case 5: {
                return 50000;
            }
        }
        return 0;
    }

    @Override
    public List<FluidStack> getAllFluidOutputs() {
        ObjectArrayList allFluids = new ObjectArrayList();
        for (int i = 1; i < 6; ++i) {
            allFluids.add(new FluidStack(IC2Fluids.ALCOHOL, this.getAmountForYear(i)));
        }
        return allFluids;
    }
}

