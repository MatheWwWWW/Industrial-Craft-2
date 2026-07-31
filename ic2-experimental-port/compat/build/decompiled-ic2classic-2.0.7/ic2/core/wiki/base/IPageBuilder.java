/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.wiki.base;

import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.core.wiki.components.IWikiComponent;
import ic2.core.wiki.recipes.providers.IItemReference;
import ic2.core.wiki.recipes.providers.IRecipeReference;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public interface IPageBuilder {
    public void addComponent(IWikiComponent var1);

    public void setEndOfPage();

    public void setEndOfDoublePage();

    public void disableSearch(boolean var1);

    public int getHeightLeft();

    public boolean hasComponents();

    public void clearPage();

    public void setPageLink(ResourceLocation var1);

    public void markPreview(ItemLike var1);

    public IRecipeReference getRecipes(List<ItemStack> var1, boolean var2);

    public IItemReference getItemList(List<IInput> var1);
}

