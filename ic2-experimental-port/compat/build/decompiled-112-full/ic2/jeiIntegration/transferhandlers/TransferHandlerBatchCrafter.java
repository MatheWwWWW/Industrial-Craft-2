/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.gui.IGuiIngredient
 *  mezz.jei.api.gui.IGuiItemStackGroup
 *  mezz.jei.api.gui.IRecipeLayout
 *  mezz.jei.api.recipe.transfer.IRecipeTransferError
 *  mezz.jei.api.recipe.transfer.IRecipeTransferHandler
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 */
package ic2.jeiIntegration.transferhandlers;

import ic2.core.IC2;
import ic2.core.block.machine.container.ContainerBatchCrafter;
import ic2.core.block.machine.tileentity.TileEntityBatchCrafter;
import ic2.core.util.StackUtil;
import java.util.Map;
import mezz.jei.api.gui.IGuiIngredient;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TransferHandlerBatchCrafter
implements IRecipeTransferHandler<ContainerBatchCrafter> {
    public Class<ContainerBatchCrafter> getContainerClass() {
        return ContainerBatchCrafter.class;
    }

    public IRecipeTransferError transferRecipe(ContainerBatchCrafter container, IRecipeLayout recipeLayout, EntityPlayer player, boolean maxTransfer, boolean doTransfer) {
        if (!doTransfer) {
            return null;
        }
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        Map slotToStackMap = stacks.getGuiIngredients();
        for (int i = 0; i < 9; ++i) {
            IGuiIngredient currentIngredient = (IGuiIngredient)slotToStackMap.get(i + 1);
            ItemStack set = currentIngredient != null ? (ItemStack)currentIngredient.getDisplayedIngredient() : StackUtil.emptyStack;
            ((TileEntityBatchCrafter)container.base).craftingGrid[i] = set;
        }
        IC2.network.get(false).updateTileEntityField((TileEntity)container.base, "craftingGrid");
        IC2.network.get(false).initiateClientTileEntityEvent((TileEntity)container.base, 0);
        return null;
    }
}

