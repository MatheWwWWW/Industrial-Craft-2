/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.common.Tags$Items
 */
package ic2.core.block.machines.recipes;

import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.ItemStackInput;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.recipes.registries.IRecipeFilter;
import ic2.api.recipes.registries.IRecyclerRecipeList;
import ic2.core.block.machines.recipes.MachineFilter;
import ic2.core.block.machines.recipes.MachineRecipeList;
import ic2.core.block.machines.recipes.misc.ScrapOutput;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;

public class RecyclerRecipeList
extends MachineRecipeList
implements IRecyclerRecipeList {
    Map<Item, List<IMachineRecipeList.RecipeEntry>> mappedRecipes = CollectionUtils.createMap();
    MachineFilter filter = new MachineFilter();
    ScrapOutput scrap;
    ScrapOutput scrapMetal;

    public RecyclerRecipeList(Consumer<IMachineRecipeList> reloader) {
        super("recycler", reloader);
    }

    @Override
    public IRecipeFilter getBlackList() {
        return this.filter;
    }

    @Override
    public void reload() {
        this.filter.clear();
        this.mappedRecipes.clear();
        super.reload();
    }

    @Override
    public void writeRecipes(FriendlyByteBuf buffer) {
        super.writeRecipes(buffer);
        this.filter.writeRecipes(buffer);
    }

    @Override
    public void readRecipes(FriendlyByteBuf buffer) {
        super.readRecipes(buffer);
        this.filter.readRecipes(buffer);
    }

    @Override
    public Map<ResourceLocation, JsonObject> writeRecipes() {
        Map<ResourceLocation, JsonObject> map = super.writeRecipes();
        JsonObject obj = this.filter.serialize();
        obj.addProperty("filter", Boolean.valueOf(true));
        map.put(new ResourceLocation("ic2:blacklist"), obj);
        return map;
    }

    @Override
    public void readRecipe(ResourceLocation id, JsonObject data) throws Exception {
        if (GsonHelper.m_13855_((JsonObject)data, (String)"filter", (boolean)false)) {
            this.filter.read(data);
            return;
        }
        super.readRecipe(id, data);
    }

    @Override
    protected void mapRecipes() {
        Object2ObjectMap<Item, Set> mapped = CollectionUtils.createMap();
        for (IMachineRecipeList.RecipeEntry entry : this.recipes.values()) {
            for (ItemStack stack : this.toStackList(entry.getInputs())) {
                mapped.computeIfAbsent(stack.m_41720_(), T -> CollectionUtils.createSet()).add(entry);
            }
        }
        mapped.forEach((K, V) -> this.mappedRecipes.put((Item)K, (List<IMachineRecipeList.RecipeEntry>)new ObjectArrayList((Collection)V)));
    }

    public IMachineRecipeList.RecipeEntry getInternalRecipe(ItemStack stack, boolean hasStackSize) {
        List<IMachineRecipeList.RecipeEntry> list;
        if (!this.mapped) {
            this.mapRecipes();
            this.mapped = true;
        }
        if ((list = this.mappedRecipes.get(stack.m_41720_())) == null) {
            return null;
        }
        for (IMachineRecipeList.RecipeEntry entry : list) {
            if (!entry.getInputs()[0].matches(stack) || hasStackSize && entry.getInputs()[0].getInputSize() > stack.m_41613_()) continue;
            return entry;
        }
        return null;
    }

    @Override
    public IMachineRecipeList.RecipeEntry getRecipe(ItemStack stack, boolean hasStackSize) {
        if (this.filter.contains(stack) || stack.m_41619_()) {
            return null;
        }
        IMachineRecipeList.RecipeEntry entry = this.getInternalRecipe(stack, hasStackSize);
        if (entry == null && this.getInternalRecipe(stack, false) == null) {
            this.loadTemps();
            entry = this.isMetal(stack) ? new IMachineRecipeList.RecipeEntry(new ResourceLocation("scrap_metal"), this.scrapMetal, new ItemStackInput(stack, 1)) : new IMachineRecipeList.RecipeEntry(new ResourceLocation("scrap"), this.scrap, new ItemStackInput(stack, 1));
        }
        return entry;
    }

    protected boolean isMetal(ItemStack item) {
        return item.m_204117_(Tags.Items.INGOTS) && !item.m_204117_(Tags.Items.INGOTS_BRICK) && !item.m_204117_(Tags.Items.INGOTS_NETHER_BRICK);
    }

    protected void loadTemps() {
        if (this.scrap != null && this.scrapMetal != null) {
            return;
        }
        this.scrap = new ScrapOutput(new ItemStack((ItemLike)IC2Items.SCRAP), 4);
        this.scrapMetal = new ScrapOutput(new ItemStack((ItemLike)IC2Items.SCRAP_METAL), 8);
    }
}

