/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.block.machines.logic.crafter;

import ic2.core.block.machines.logic.crafter.CraftRecipe;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.INBTSavable;
import ic2.core.inventory.filter.IFilter;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.math.MathUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CraftingList
implements IHasInventory,
INBTSavable {
    int slots;
    CraftRecipe[] recipes;
    NonNullList<ItemStack> displayItems;
    IntSet savedSlots = new IntOpenHashSet();
    IntList nonEmpty = new IntArrayList();

    public CraftingList(int slots) {
        if (slots > 64) {
            throw new ArrayIndexOutOfBoundsException("Crafting Lists can't have more then 64 Recipes stored");
        }
        this.slots = slots;
        this.recipes = new CraftRecipe[slots];
        this.displayItems = NonNullList.m_122780_((int)slots, (Object)ItemStack.f_41583_);
    }

    public CraftingList setSlotsSaved(int from, int to) {
        this.savedSlots.addAll((IntCollection)IntArrayList.wrap((int[])MathUtils.fromTo(from, to)));
        return this;
    }

    public CraftingList setSlotsSaved(int ... slots) {
        this.savedSlots.addAll((IntCollection)IntArrayList.wrap((int[])slots));
        return this;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        ListTag list = new ListTag();
        int m = this.nonEmpty.size();
        for (int i = 0; i < m; ++i) {
            int index = this.nonEmpty.getInt(i);
            if (this.recipes[index] == null || this.savedSlots.size() > 0 && !this.savedSlots.contains(index)) continue;
            CompoundTag data = this.recipes[index].writeRecipe(new CompoundTag());
            data.m_128405_("list_entry", index);
            list.add((Object)data);
        }
        NBTUtils.put(nbt, "recipes", list);
        return nbt;
    }

    @Override
    public void load(CompoundTag nbt) {
        this.nonEmpty.clear();
        for (CompoundTag data : NBTListWrapper.wrap(nbt.m_128437_("recipes", 10), CompoundTag.class)) {
            int index = data.m_128451_("list_entry");
            CraftRecipe recipe = new CraftRecipe();
            recipe.readRecipe(data);
            this.recipes[index] = recipe;
            this.nonEmpty.add(index);
        }
    }

    public void validateRecipes(Level world) {
        int m = this.nonEmpty.size();
        for (int i = 0; i < m; ++i) {
            int index = this.nonEmpty.getInt(i);
            if (this.recipes[index] == null) {
                this.nonEmpty.rem(i--);
                --m;
                this.displayItems.set(index, (Object)ItemStack.f_41583_);
                continue;
            }
            if (this.recipes[index].validate(world)) {
                this.displayItems.set(index, (Object)this.recipes[index].getDisplayItem());
                continue;
            }
            --m;
            this.nonEmpty.rem(i--);
            this.displayItems.set(index, (Object)ItemStack.f_41583_);
        }
    }

    public void saveRecipe(int slot, CraftRecipe recipe) {
        if (slot < 0 || slot >= this.slots) {
            return;
        }
        this.recipes[slot] = recipe;
        this.displayItems.set(slot, (Object)recipe.getDisplayItem());
        if (!this.nonEmpty.contains(slot)) {
            this.nonEmpty.add(slot);
        }
    }

    public void removeRecipe(int slot) {
        if (slot < 0 || slot >= this.slots) {
            return;
        }
        this.recipes[slot] = null;
        this.displayItems.set(slot, (Object)ItemStack.f_41583_);
        this.nonEmpty.rem(slot);
    }

    public boolean isEmpty() {
        return this.nonEmpty.isEmpty();
    }

    public CraftRecipe getRecipe(int slot) {
        return this.recipes[slot];
    }

    public CraftRecipe getRecipeFrom(IFilter filter) {
        int m = this.nonEmpty.size();
        for (int i = 0; i < m; ++i) {
            int slot = this.nonEmpty.getInt(i);
            if (!filter.matches((ItemStack)this.displayItems.get(slot))) continue;
            return this.recipes[slot];
        }
        return null;
    }

    @Override
    public int getSlotCount() {
        return this.slots;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return (ItemStack)this.displayItems.get(slot);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        this.displayItems.set(slot, (Object)stack);
    }

    @Override
    public int getMaxStackSize(int slot) {
        return 64;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack) {
        return false;
    }

    public CraftRecipe getCopy(int index) {
        return this.recipes[index] != null ? this.recipes[index].copy() : null;
    }
}

