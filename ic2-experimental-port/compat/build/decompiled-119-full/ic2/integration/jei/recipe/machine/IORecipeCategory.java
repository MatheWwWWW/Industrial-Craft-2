/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.gui.builder.IRecipeLayoutBuilder
 *  mezz.jei.api.gui.builder.IRecipeSlotBuilder
 *  mezz.jei.api.gui.drawable.IDrawable
 *  mezz.jei.api.recipe.IFocusGroup
 *  mezz.jei.api.recipe.RecipeIngredientRole
 *  mezz.jei.api.recipe.category.IRecipeCategory
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.integration.jei.recipe.machine;

import ic2.core.block.tileentity.Ic2TileEntityBlock;
import ic2.integration.jei.recipe.machine.IORecipeWrapper;
import ic2.integration.jeirei.SlotPosition;
import java.util.List;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public abstract class IORecipeCategory
implements IRecipeCategory<IORecipeWrapper> {
    protected final Ic2TileEntityBlock block;

    public IORecipeCategory(Ic2TileEntityBlock ic2TileEntityBlock) {
        this.block = ic2TileEntityBlock;
    }

    public Component getTitle() {
        return this.getBlockStack().m_41786_();
    }

    protected abstract List<SlotPosition> getInputSlotPos();

    protected abstract List<SlotPosition> getOutputSlotPos();

    protected void addRecipeSlots(IRecipeLayoutBuilder iRecipeLayoutBuilder, IORecipeWrapper iORecipeWrapper, IFocusGroup iFocusGroup, int n, int n2) {
        IRecipeSlotBuilder iRecipeSlotBuilder;
        Object object;
        int n3;
        List<SlotPosition> list = this.getInputSlotPos();
        List<List<ItemStack>> list2 = iORecipeWrapper.getInputs();
        for (n3 = 0; n3 < list.size(); ++n3) {
            object = list.get(n3);
            iRecipeSlotBuilder = iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, ((SlotPosition)object).getX() + n, ((SlotPosition)object).getY() + n2);
            if (n3 >= list2.size()) continue;
            iRecipeSlotBuilder.addItemStacks(list2.get(n3));
        }
        object = this.getOutputSlotPos();
        iRecipeSlotBuilder = List.of(iORecipeWrapper.getOutputs());
        int n4 = 0;
        while (n4 < object.size()) {
            SlotPosition slotPosition = (SlotPosition)object.get(n4);
            IRecipeSlotBuilder iRecipeSlotBuilder2 = iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, slotPosition.getX() + n, slotPosition.getY() + n2);
            if (n4 < iRecipeSlotBuilder.size()) {
                iRecipeSlotBuilder2.addItemStacks((List)iRecipeSlotBuilder.get(n4));
            }
            ++n4;
            ++n3;
        }
    }

    public ItemStack getBlockStack() {
        return new ItemStack((ItemLike)this.block.m_5456_());
    }

    public IDrawable getIcon() {
        return null;
    }
}

