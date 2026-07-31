/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 */
package ic2.core.recipe;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.IScrapboxManager;
import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.ref.Ic2Items;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

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

    public boolean addRecipe(IRecipeInput iRecipeInput, Collection<ItemStack> collection, CompoundTag compoundTag, boolean bl) {
        if (!iRecipeInput.matches(new ItemStack((ItemLike)Ic2Items.SCRAP_BOX))) {
            throw new IllegalArgumentException("currently only scrap boxes are supported");
        }
        if (compoundTag == null || !compoundTag.m_128441_("weight")) {
            throw new IllegalArgumentException("no weight metadata");
        }
        if (collection.size() != 1) {
            throw new IllegalArgumentException("currently only a single drop stack is supported");
        }
        float f = compoundTag.m_128457_("weight");
        if (f <= 0.0f || Float.isInfinite(f) || Float.isNaN(f)) {
            throw new IllegalArgumentException("invalid weight");
        }
        this.addDrop(collection.iterator().next(), f);
        return true;
    }

    public boolean addRecipe(IRecipeInput iRecipeInput, CompoundTag compoundTag, boolean bl, ItemStack ... itemStackArray) {
        return this.addRecipe(iRecipeInput, Arrays.asList(itemStackArray), compoundTag, bl);
    }

    @Override
    public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> apply(ItemStack itemStack, boolean bl) {
        if (StackUtil.isEmpty(itemStack) || itemStack.m_41720_() != Ic2Items.SCRAP_BOX) {
            return null;
        }
        if (this.drops.isEmpty()) {
            return null;
        }
        float f = IC2.random.m_188501_() * Drop.topChance;
        int n = 0;
        int n2 = this.drops.size() - 1;
        while (n < n2) {
            int n3 = n2 + n >>> 1;
            if (f < this.drops.get((int)n3).upperChanceBound) {
                n2 = n3;
                continue;
            }
            n = n3 + 1;
        }
        ItemStack itemStack2 = this.drops.get((int)n).item.m_41777_();
        return new MachineRecipe<IRecipeInput, List<ItemStack>>(Recipes.inputFactory.forItem((ItemLike)Ic2Items.SCRAP_BOX), Collections.singletonList(itemStack2)).getResult(StackUtil.copyShrunk(itemStack, 1));
    }

    @Override
    public RecipeOutput getOutputFor(ItemStack itemStack, boolean bl) {
        MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> machineRecipeResult = this.apply(itemStack, false);
        if (machineRecipeResult == null || machineRecipeResult.getOutput().isEmpty()) {
            return null;
        }
        return new RecipeOutput(null, new ArrayList<ItemStack>(machineRecipeResult.getOutput()));
    }

    @Override
    public Iterable<? extends MachineRecipe<IRecipeInput, Collection<ItemStack>>> getRecipes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isIterable() {
        return false;
    }

    public void addDrop(ItemStack itemStack, float f) {
        this.drops.add(new Drop(itemStack, f));
    }

    @Override
    public ItemStack getDrop(ItemStack itemStack, boolean bl) {
        MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> machineRecipeResult = this.apply(itemStack, false);
        if (machineRecipeResult == null || machineRecipeResult.getOutput().isEmpty()) {
            return null;
        }
        if (bl) {
            itemStack.m_41764_(StackUtil.getSize(machineRecipeResult.getAdjustedInput()));
        }
        return machineRecipeResult.getOutput().iterator().next();
    }

    @Override
    public Map<ItemStack, Float> getDrops() {
        HashMap<ItemStack, Float> hashMap = new HashMap<ItemStack, Float>(this.drops.size());
        for (Drop drop : this.drops) {
            hashMap.put(drop.item, Float.valueOf(drop.originalChance / Drop.topChance));
        }
        return hashMap;
    }

    private void addBuiltinDrops() {
        if (IC2.suddenlyHoes) {
            this.addDrop(Items.f_42424_, 9001.0f);
        } else {
            this.addDrop(Items.f_42424_, 5.01f);
        }
        this.addDrop(Blocks.f_50493_, 5.0f);
        this.addDrop(Items.f_42398_, 4.0f);
        this.addDrop(Blocks.f_50034_, 3.0f);
        this.addDrop(Blocks.f_49994_, 3.0f);
        this.addDrop(Blocks.f_50134_, 2.0f);
        this.addDrop(Items.f_42583_, 2.0f);
        this.addDrop(Items.f_42410_, 1.5f);
        this.addDrop(Items.f_42406_, 1.5f);
        this.addDrop(Ic2Items.FILLED_TIN_CAN, 1.5f);
        this.addDrop(Items.f_42420_, 1.0f);
        this.addDrop(Items.f_42421_, 1.0f);
        this.addDrop(Items.f_42422_, 1.0f);
        this.addDrop(Blocks.f_50135_, 1.0f);
        this.addDrop(Items.f_42438_, 1.0f);
        this.addDrop(Items.f_42454_, 1.0f);
        this.addDrop(Items.f_42402_, 1.0f);
        this.addDrop(Items.f_42500_, 1.0f);
        this.addDrop(Items.f_42486_, 0.9f);
        this.addDrop(Items.f_42580_, 0.9f);
        this.addDrop(Blocks.f_50133_, 0.9f);
        this.addDrop(Items.f_42582_, 0.9f);
        this.addDrop(Items.f_42449_, 0.01f);
        this.addDrop(Items.f_42451_, 0.9f);
        this.addDrop(Ic2Items.RUBBER, 0.8f);
        this.addDrop(Items.f_42525_, 0.8f);
        this.addDrop(Ic2Items.COAL_DUST, 0.8f);
        this.addDrop(Ic2Items.COPPER_DUST, 0.8f);
        this.addDrop(Ic2Items.TIN_DUST, 0.8f);
        this.addDrop(Ic2Items.SINGLE_USE_BATTERY, 0.7f);
        this.addDrop(Ic2Items.IRON_DUST, 0.7f);
        this.addDrop(Ic2Items.GOLD_DUST, 0.7f);
        this.addDrop(Items.f_42518_, 0.6f);
        this.addDrop(Blocks.f_49996_, 0.5f);
        this.addDrop(Items.f_42476_, 0.01f);
        this.addDrop(Blocks.f_49995_, 0.5f);
        this.addDrop(Items.f_42502_, 0.5f);
        this.addDrop(Items.f_42415_, 0.1f);
        this.addDrop(Items.f_42616_, 0.05f);
        this.addDrop(Items.f_42584_, 0.08f);
        this.addDrop(Items.f_42585_, 0.04f);
        this.addDrop(Items.f_42521_, 0.8f);
        this.addDrop(Blocks.f_152505_, 0.7f);
        this.addDrop(Ic2Items.TIN_ORE, 0.7f);
    }

    private void addDrop(Block block, float f) {
        this.addDrop(new ItemStack((ItemLike)block), f);
    }

    private void addDrop(Item item, float f) {
        this.addDrop(new ItemStack((ItemLike)item), f);
    }

    private static class Drop {
        final ItemStack item;
        final float originalChance;
        final float upperChanceBound;
        static float topChance;

        Drop(ItemStack itemStack, float f) {
            this.item = itemStack;
            this.originalChance = f;
            this.upperChanceBound = topChance += f;
        }
    }
}

