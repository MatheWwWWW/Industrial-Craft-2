/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 */
package ic2.core.recipe;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.IScrapboxManager;
import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.block.type.ResourceBlock;
import ic2.core.item.type.CraftingItemType;
import ic2.core.item.type.DustResourceType;
import ic2.core.ref.BlockName;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class ScrapboxRecipeManager
implements IScrapboxManager {
    private final List<Drop> drops = new ArrayList<Drop>();

    public static void setup() {
        if (Recipes.scrapboxDrops != null) {
            throw new IllegalStateException("already initialized");
        }
        Recipes.scrapboxDrops = new ScrapboxRecipeManager();
    }

    public static void load() {
        ((ScrapboxRecipeManager)Recipes.scrapboxDrops).addBuiltinDrops();
    }

    private ScrapboxRecipeManager() {
    }

    @Override
    public boolean addRecipe(IRecipeInput input, Collection<ItemStack> output, NBTTagCompound metadata, boolean replace) {
        if (!input.matches(ItemName.crafting.getItemStack(CraftingItemType.scrap_box))) {
            throw new IllegalArgumentException("currently only scrap boxes are supported");
        }
        if (metadata == null || !metadata.func_74764_b("weight")) {
            throw new IllegalArgumentException("no weight metadata");
        }
        if (output.size() != 1) {
            throw new IllegalArgumentException("currently only a single drop stack is supported");
        }
        float weight = metadata.func_74760_g("weight");
        if (weight <= 0.0f || Float.isInfinite(weight) || Float.isNaN(weight)) {
            throw new IllegalArgumentException("invalid weight");
        }
        this.addDrop(output.iterator().next(), weight);
        return true;
    }

    @Override
    public boolean addRecipe(IRecipeInput input, NBTTagCompound metadata, boolean replace, ItemStack ... outputs) {
        return this.addRecipe(input, (Collection<ItemStack>)Arrays.asList(outputs), metadata, replace);
    }

    @Override
    public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> apply(ItemStack input, boolean acceptTest) {
        if (StackUtil.isEmpty(input) || !StackUtil.checkItemEquality(input, ItemName.crafting.getItemStack(CraftingItemType.scrap_box))) {
            return null;
        }
        if (this.drops.isEmpty()) {
            return null;
        }
        float chance = IC2.random.nextFloat() * Drop.topChance;
        int low = 0;
        int high = this.drops.size() - 1;
        while (low < high) {
            int mid = (high + low) / 2;
            if (chance < this.drops.get((int)mid).upperChanceBound) {
                high = mid;
                continue;
            }
            low = mid + 1;
        }
        ItemStack drop = this.drops.get((int)low).item.func_77946_l();
        return new MachineRecipe<IRecipeInput, List<ItemStack>>(Recipes.inputFactory.forStack(ItemName.crafting.getItemStack(CraftingItemType.scrap_box)), Collections.singletonList(drop)).getResult(StackUtil.copyShrunk(input, 1));
    }

    @Override
    public RecipeOutput getOutputFor(ItemStack input, boolean adjustInput) {
        MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> result = this.apply(input, false);
        if (result == null || result.getOutput().isEmpty()) {
            return null;
        }
        return new RecipeOutput(null, new ArrayList<ItemStack>(result.getOutput()));
    }

    @Override
    public Iterable<? extends MachineRecipe<IRecipeInput, Collection<ItemStack>>> getRecipes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isIterable() {
        return false;
    }

    @Override
    public void addDrop(ItemStack drop, float rawChance) {
        this.drops.add(new Drop(drop, rawChance));
    }

    @Override
    public ItemStack getDrop(ItemStack input, boolean adjustInput) {
        MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> result = this.apply(input, false);
        if (result == null || result.getOutput().isEmpty()) {
            return null;
        }
        if (adjustInput) {
            input.func_190920_e(StackUtil.getSize(result.getAdjustedInput()));
        }
        return result.getOutput().iterator().next();
    }

    @Override
    public Map<ItemStack, Float> getDrops() {
        HashMap<ItemStack, Float> ret = new HashMap<ItemStack, Float>(this.drops.size());
        for (Drop drop : this.drops) {
            ret.put(drop.item, Float.valueOf(drop.originalChance / Drop.topChance));
        }
        return ret;
    }

    private void addBuiltinDrops() {
        if (IC2.suddenlyHoes) {
            this.addDrop(Items.field_151017_I, 9001.0f);
        } else {
            this.addDrop(Items.field_151017_I, 5.01f);
        }
        this.addDrop(Blocks.field_150346_d, 5.0f);
        this.addDrop(Items.field_151055_y, 4.0f);
        this.addDrop((Block)Blocks.field_150349_c, 3.0f);
        this.addDrop(Blocks.field_150351_n, 3.0f);
        this.addDrop(Blocks.field_150424_aL, 2.0f);
        this.addDrop(Items.field_151078_bh, 2.0f);
        this.addDrop(Items.field_151034_e, 1.5f);
        this.addDrop(Items.field_151025_P, 1.5f);
        this.addDrop(ItemName.filled_tin_can.getItemStack(), 1.5f);
        this.addDrop(Items.field_151041_m, 1.0f);
        this.addDrop(Items.field_151038_n, 1.0f);
        this.addDrop(Items.field_151039_o, 1.0f);
        this.addDrop(Blocks.field_150425_aM, 1.0f);
        this.addDrop(Items.field_151155_ap, 1.0f);
        this.addDrop(Items.field_151116_aA, 1.0f);
        this.addDrop(Items.field_151008_G, 1.0f);
        this.addDrop(Items.field_151103_aS, 1.0f);
        this.addDrop(Items.field_151157_am, 0.9f);
        this.addDrop(Items.field_151083_be, 0.9f);
        this.addDrop(Blocks.field_150423_aK, 0.9f);
        this.addDrop(Items.field_151077_bg, 0.9f);
        this.addDrop(Items.field_151143_au, 0.01f);
        this.addDrop(Items.field_151137_ax, 0.9f);
        this.addDrop(ItemName.crafting.getItemStack(CraftingItemType.rubber), 0.8f);
        this.addDrop(Items.field_151114_aO, 0.8f);
        this.addDrop(ItemName.dust.getItemStack(DustResourceType.coal), 0.8f);
        this.addDrop(ItemName.dust.getItemStack(DustResourceType.copper), 0.8f);
        this.addDrop(ItemName.dust.getItemStack(DustResourceType.tin), 0.8f);
        this.addDrop(ItemName.single_use_battery.getItemStack(), 0.7f);
        this.addDrop(ItemName.dust.getItemStack(DustResourceType.iron), 0.7f);
        this.addDrop(ItemName.dust.getItemStack(DustResourceType.gold), 0.7f);
        this.addDrop(Items.field_151123_aH, 0.6f);
        this.addDrop(Blocks.field_150366_p, 0.5f);
        this.addDrop((Item)Items.field_151169_ag, 0.01f);
        this.addDrop(Blocks.field_150352_o, 0.5f);
        this.addDrop(Items.field_151105_aU, 0.5f);
        this.addDrop(Items.field_151045_i, 0.1f);
        this.addDrop(Items.field_151166_bC, 0.05f);
        this.addDrop(Items.field_151079_bi, 0.08f);
        this.addDrop(Items.field_151072_bj, 0.04f);
        this.addDrop(Items.field_151110_aK, 0.8f);
        this.addDrop(BlockName.resource.getItemStack(ResourceBlock.copper_ore), 0.7f);
        this.addDrop(BlockName.resource.getItemStack(ResourceBlock.tin_ore), 0.7f);
    }

    private void addDrop(Block block, float rawChance) {
        this.addDrop(new ItemStack(block), rawChance);
    }

    private void addDrop(Item item, float rawChance) {
        this.addDrop(new ItemStack(item), rawChance);
    }

    private static class Drop {
        final ItemStack item;
        final float originalChance;
        final float upperChanceBound;
        static float topChance;

        Drop(ItemStack item, float chance) {
            this.item = item;
            this.originalChance = chance;
            this.upperChanceBound = topChance += chance;
        }
    }
}

