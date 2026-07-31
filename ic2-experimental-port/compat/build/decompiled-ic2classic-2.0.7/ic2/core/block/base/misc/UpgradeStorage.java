/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.base.misc;

import ic2.api.items.IUpgradeItem;
import ic2.api.recipes.ingridients.queue.IStackOutput;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.tiles.IMachine;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class UpgradeStorage {
    EnumMap<IUpgradeItem.Functions, IntList> function_slots = new EnumMap(IUpgradeItem.Functions.class);

    public void clear() {
        this.function_slots.clear();
    }

    public void validate(int slot, EnumSet<IUpgradeItem.Functions> functions) {
        if (functions == null) {
            return;
        }
        for (IUpgradeItem.Functions entry : functions) {
            IntList list = this.function_slots.get((Object)entry);
            if (list == null) {
                list = new IntArrayList();
                this.function_slots.put(entry, list);
            }
            list.add(slot);
        }
    }

    public boolean has(IUpgradeItem.Functions function) {
        return this.function_slots.get((Object)function) != null;
    }

    public boolean match(IUpgradeItem.Functions main, IUpgradeItem.Functions other) {
        return Objects.equals(this.function_slots.get((Object)main), this.function_slots.get((Object)other));
    }

    public void onTick(NonNullList<ItemStack> items, IMachine machine) {
        IntList list = this.function_slots.get((Object)IUpgradeItem.Functions.TICK);
        if (list == null) {
            return;
        }
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = (ItemStack)items.get(list.getInt(i));
            if (!(stack.m_41720_() instanceof IUpgradeItem)) continue;
            ((IUpgradeItem)stack.m_41720_()).onTick(stack, machine);
        }
    }

    public void onRecipeFinished(NonNullList<ItemStack> items, IMachine machine) {
        IntList list = this.function_slots.get((Object)IUpgradeItem.Functions.RECIPE);
        if (list == null) {
            return;
        }
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = (ItemStack)items.get(list.getInt(i));
            if (!(stack.m_41720_() instanceof IUpgradeItem)) continue;
            ((IUpgradeItem)stack.m_41720_()).onMachineProcessed(stack, machine);
        }
    }

    public void onRecipeFinishedPre(NonNullList<ItemStack> items, IMachine machine, IRecipeOutput output, CompoundTag recipeData) {
        IntList list = this.function_slots.get((Object)IUpgradeItem.Functions.RECIPE);
        if (list == null) {
            return;
        }
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = (ItemStack)items.get(list.getInt(i));
            if (!(stack.m_41720_() instanceof IUpgradeItem)) continue;
            ((IUpgradeItem)stack.m_41720_()).onMachineFinishedRecipePre(stack, machine, output, recipeData);
        }
    }

    public void onRecipeFinishedPost(NonNullList<ItemStack> items, IMachine machine, IMachineRecipeList.RecipeEntry entry, List<IStackOutput> drops) {
        IntList list = this.function_slots.get((Object)IUpgradeItem.Functions.RECIPE);
        if (list == null) {
            return;
        }
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = (ItemStack)items.get(list.getInt(i));
            if (!(stack.m_41720_() instanceof IUpgradeItem)) continue;
            ((IUpgradeItem)stack.m_41720_()).onMachineFinishedRecipePost(stack, machine, entry, drops);
        }
    }
}

