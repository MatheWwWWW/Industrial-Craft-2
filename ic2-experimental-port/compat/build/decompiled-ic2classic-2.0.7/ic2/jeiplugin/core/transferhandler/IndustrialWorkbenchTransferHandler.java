/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.jeiplugin.core.transferhandler;

import ic2.core.IC2;
import ic2.core.block.machines.containers.nv.IndustrialWorkbenchContainer;
import ic2.core.networking.buffers.data.NBTBuffer;
import java.util.List;
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
import net.minecraft.world.level.block.entity.BlockEntity;

public class IndustrialWorkbenchTransferHandler
implements IRecipeTransferHandler<IndustrialWorkbenchContainer, CraftingRecipe> {
    public Class<IndustrialWorkbenchContainer> getContainerClass() {
        return IndustrialWorkbenchContainer.class;
    }

    public Optional<MenuType<IndustrialWorkbenchContainer>> getMenuType() {
        return Optional.empty();
    }

    public RecipeType<CraftingRecipe> getRecipeType() {
        return RecipeTypes.CRAFTING;
    }

    public IRecipeTransferError transferRecipe(IndustrialWorkbenchContainer container, CraftingRecipe recipe, IRecipeSlotsView recipeLayout, Player player, boolean maxTransfer, boolean doTransfer) {
        if (doTransfer) {
            ListTag list = new ListTag();
            List slotViews = recipeLayout.getSlotViews(RecipeIngredientRole.INPUT);
            for (int i = 0; i < 9; ++i) {
                IRecipeSlotView view = (IRecipeSlotView)slotViews.get(i);
                CompoundTag nbt = new CompoundTag();
                nbt.m_128405_("SlotID", i);
                if (view != null && view.getRole() == RecipeIngredientRole.INPUT && !view.isEmpty()) {
                    view.getDisplayedIngredient((IIngredientType)VanillaTypes.ITEM_STACK).orElse(ItemStack.f_41583_).m_41739_(nbt);
                }
                list.add((Object)nbt);
            }
            IC2.NETWORKING.get(false).sendClientTileDataBufferEvent((BlockEntity)container.getHolder(), "jei", new NBTBuffer("RecipeData", (Tag)list));
        }
        return null;
    }
}

