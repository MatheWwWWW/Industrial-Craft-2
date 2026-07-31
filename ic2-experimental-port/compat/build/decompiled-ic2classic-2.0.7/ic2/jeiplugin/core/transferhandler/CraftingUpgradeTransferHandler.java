/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  mezz.jei.api.constants.RecipeTypes
 *  mezz.jei.api.constants.VanillaTypes
 *  mezz.jei.api.gui.ingredient.IRecipeSlotView
 *  mezz.jei.api.gui.ingredient.IRecipeSlotsView
 *  mezz.jei.api.ingredients.IIngredientType
 *  mezz.jei.api.recipe.RecipeIngredientRole
 *  mezz.jei.api.recipe.RecipeType
 *  mezz.jei.api.recipe.transfer.IRecipeTransferError
 *  mezz.jei.api.recipe.transfer.IRecipeTransferHandler
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.CraftingRecipe
 *  net.minecraft.world.level.ItemLike
 */
package ic2.jeiplugin.core.transferhandler;

import ic2.core.IC2;
import ic2.core.item.inv.container.CraftingUpgradeContainer;
import ic2.core.networking.buffers.data.NBTBuffer;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.level.ItemLike;

public class CraftingUpgradeTransferHandler
implements IRecipeTransferHandler<CraftingUpgradeContainer, CraftingRecipe> {
    public Class<CraftingUpgradeContainer> getContainerClass() {
        return CraftingUpgradeContainer.class;
    }

    public IRecipeTransferError transferRecipe(CraftingUpgradeContainer container, CraftingRecipe recipe, IRecipeSlotsView recipeLayout, Player player, boolean maxTransfer, boolean doTransfer) {
        if (doTransfer) {
            List slotViews = recipeLayout.getSlotViews(RecipeIngredientRole.INPUT);
            Object2ObjectSortedMap slots = CollectionUtils.createLinkedMap();
            for (int i = 0; i < 9; ++i) {
                IRecipeSlotView view = (IRecipeSlotView)slotViews.get(i);
                if (view == null || view.isEmpty()) continue;
                this.getSlot((Map<ItemStack, IntList>)slots, view.getDisplayedIngredient((IIngredientType)VanillaTypes.ITEM_STACK).orElse(ItemStack.f_41583_)).add(i);
            }
            ListTag list = new ListTag();
            for (Map.Entry entry : slots.entrySet()) {
                CompoundTag data = ((ItemStack)entry.getKey()).m_41739_(new CompoundTag());
                data.m_128385_("slots", ((IntList)entry.getValue()).toIntArray());
                list.add((Object)data);
            }
            IC2.NETWORKING.get(false).sendClientItemBuffer(new ItemStack((ItemLike)IC2Items.CRAFTING_UPGRADE), "jei", new NBTBuffer("RecipeItems", (Tag)list));
        }
        return null;
    }

    public IntList getSlot(Map<ItemStack, IntList> slots, ItemStack stack) {
        for (Map.Entry<ItemStack, IntList> entry : slots.entrySet()) {
            if (!StackUtil.isStackEqual(entry.getKey(), stack) || entry.getKey().m_41613_() != stack.m_41613_()) continue;
            return entry.getValue();
        }
        IntArrayList set = new IntArrayList();
        slots.put(stack, (IntList)set);
        return set;
    }

    public Optional<MenuType<CraftingUpgradeContainer>> getMenuType() {
        return Optional.empty();
    }

    public RecipeType<CraftingRecipe> getRecipeType() {
        return RecipeTypes.CRAFTING;
    }
}

