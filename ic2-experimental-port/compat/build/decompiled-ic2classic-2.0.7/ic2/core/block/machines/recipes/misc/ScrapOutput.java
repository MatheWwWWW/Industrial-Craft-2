/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.common.crafting.CraftingHelper
 */
package ic2.core.block.machines.recipes.misc;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.inputs.ItemInput;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import ic2.api.recipes.ingridients.recipes.SimpleRecipeOutput;
import ic2.api.recipes.misc.RecipeMods;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.platform.registries.IC2Items;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.Collections;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ScrapOutput
implements IRecipeOutput {
    ItemStack output;
    int chance;
    CompoundTag nbt;

    public ScrapOutput(JsonObject obj) {
        this.output = CraftingHelper.getItemStack((JsonObject)obj, (boolean)true);
        this.chance = obj.get("chance").getAsInt();
        this.nbt = obj.has("nbt") ? IInput.readNBT(obj.get("nbt").getAsString()) : null;
    }

    public ScrapOutput(FriendlyByteBuf buffer) {
        this.output = buffer.m_130267_();
        this.nbt = buffer.m_130260_();
        this.chance = buffer.readInt();
    }

    public ScrapOutput(ItemStack drop, int chance) {
        this.output = drop.m_41777_();
        this.chance = chance;
    }

    public static IMachineRecipeList.RecipeEntry createEntry(int time) {
        return new IMachineRecipeList.RecipeEntry(new ResourceLocation("ic2", "box"), new SimpleRecipeOutput((List<ItemStack>)ObjectLists.singleton((Object)new ItemStack((ItemLike)IC2Items.SCRAPBOX)), RecipeMods.RECIPE_TIME.create(time), 0.0f), new ItemInput(IC2Items.SCRAP, 9));
    }

    @Override
    public List<ItemStack> onRecipeProcessed(RandomSource rand, CompoundTag persistentData, CompoundTag recipeFlags) {
        return rand.m_188503_(this.chance) == 0 ? ObjectLists.singleton((Object)this.output.m_41777_()) : Collections.emptyList();
    }

    @Override
    public List<ItemStack> onRecipeProcessed(RandomSource rand, CompoundTag persistentData, CompoundTag recipeFlags, IRecipeOutput.IRecipeOverride overrides) {
        return rand.m_188501_() < overrides.getChance(1.0f / (float)this.chance) ? ObjectLists.singleton((Object)this.output.m_41777_()) : Collections.emptyList();
    }

    @Override
    public List<ItemStack> getAllOutputs() {
        return ObjectLists.singleton((Object)this.output.m_41777_());
    }

    @Override
    public CompoundTag getMetadata() {
        return this.nbt == null ? EMPTY_COMPOUND : this.nbt;
    }

    @Override
    public float getExperience() {
        return 0.0f;
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.add("output", (JsonElement)IInput.writeItemStack(this.output));
        obj.addProperty("chance", (Number)this.chance);
        if (this.nbt != null && this.nbt != EMPTY_COMPOUND) {
            obj.addProperty("nbt", this.nbt.toString());
        }
        return obj;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.m_130055_(this.output);
        buffer.m_130079_(this.nbt);
        buffer.writeInt(this.chance);
    }
}

