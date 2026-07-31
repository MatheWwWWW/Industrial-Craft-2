/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.core.NonNullList
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.crafting.IShapedRecipe
 */
package ic2.core.wiki.recipes.renderers;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.platform.rendering.RenderUtils;
import ic2.core.wiki.components.IWikiComponent;
import ic2.core.wiki.recipes.renderers.IRecipeRenderer;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.crafting.IShapedRecipe;

public class CraftingRecipeRenderer
implements IRecipeRenderer {
    Recipe<?> recipe;

    public CraftingRecipeRenderer(Recipe<?> recipe) {
        this.recipe = recipe;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderBackground(IC2Screen screen, PoseStack matrix, int x, int y) {
        screen.bindDefaultTexture();
        screen.drawTextureRegion(matrix, x, y, 0.0f, 248.0f, 116.0f, 4.0f);
        screen.drawTextureRegion(matrix, x, y + 60, 0.0f, 252.0f, 116.0f, 4.0f);
        screen.bindTexture(IWikiComponent.TEXTURE);
        x += 7;
        y += 7;
        for (int i = 0; i < 9; ++i) {
            screen.drawTextureRegion(matrix, x - 1 + i / 3 * 17, y - 1 + i % 3 * 17, 124.0f, 0.0f, 18.0f, 18.0f, 18.0f, 18.0f);
        }
        screen.drawTextureRegion(matrix, x + 56, y + 19, 124.0f, 18.0f, 16.0f, 12.0f, 16.0f, 12.0f);
        screen.drawTextureRegion(matrix, x + 77, y + 13, 124.0f, 0.0f, 24.0f, 24.0f, 18.0f, 18.0f);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderItems(IC2Screen screen, PoseStack matrix, int x, int y) {
        x += 7;
        y += 7;
        int time = screen.getClock() / 20;
        NonNullList list = this.recipe.m_7527_();
        Lighting.m_84931_();
        ItemRenderer item = screen.getRenderItem();
        IShapedRecipe shaped = this.recipe instanceof IShapedRecipe ? (IShapedRecipe)this.recipe : null;
        int xMax = shaped == null ? 3 : shaped.getRecipeWidth();
        int yMax = shaped == null ? 3 : shaped.getRecipeHeight();
        for (int xPos = 0; xPos < xMax; ++xPos) {
            for (int yPos = 0; yPos < yMax; ++yPos) {
                int length;
                Ingredient entry;
                int index = yPos * xMax + xPos;
                if (list.size() <= index || (entry = (Ingredient)list.get(index)) == Ingredient.f_43901_ || (length = entry.m_43908_().length) <= 0) continue;
                int posX = x + xPos * 17;
                int posY = y + yPos * 17;
                this.renderMultiOutput(matrix, entry.m_43908_(), posX, posY, time, screen.getFont(), item);
            }
        }
        ItemStack stack = this.recipe.m_8043_();
        RenderUtils.renderGuiItem(item, matrix, stack, x + 81, y + 17);
        RenderUtils.renderGuiItemDecorations(matrix, screen.getFont(), stack, x + 81, y + 17);
        Lighting.m_84930_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public List<Component> getHoverInfo(IC2Screen screen, int mouseX, int mouseY, int x, int y) {
        if (screen.isOverArea(mouseX, mouseY, (x += 7) + 81, (y += 7) + 17, 16, 16)) {
            return this.getToolTip(this.recipe.m_8043_());
        }
        NonNullList list = this.recipe.m_7527_();
        int time = screen.getClock() / 20;
        IShapedRecipe shaped = this.recipe instanceof IShapedRecipe ? (IShapedRecipe)this.recipe : null;
        int xMax = shaped == null ? 3 : shaped.getRecipeWidth();
        int yMax = shaped == null ? 3 : shaped.getRecipeHeight();
        for (int xPos = 0; xPos < xMax; ++xPos) {
            for (int yPos = 0; yPos < yMax; ++yPos) {
                int posY;
                int posX;
                Ingredient entry;
                int index = yPos * xMax + xPos;
                if (list.size() <= index || (entry = (Ingredient)list.get(index)) == Ingredient.f_43901_ || !screen.isOverArea(mouseX, mouseY, x + (posX = xPos * 18), y + (posY = yPos * 18), 16, 16)) continue;
                ItemStack[] array = entry.m_43908_();
                return this.getToolTip(array[time % array.length]);
            }
        }
        return Collections.emptyList();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public ItemStack getHoverStack(IC2Screen screen, int mouseX, int mouseY, int x, int y) {
        if (screen.isOverArea(mouseX, mouseY, (x += 7) + 81, (y += 7) + 17, 16, 16)) {
            return this.recipe.m_8043_().m_41777_();
        }
        NonNullList list = this.recipe.m_7527_();
        int time = screen.getClock() / 20;
        IShapedRecipe shaped = this.recipe instanceof IShapedRecipe ? (IShapedRecipe)this.recipe : null;
        int xMax = shaped == null ? 3 : shaped.getRecipeWidth();
        int yMax = shaped == null ? 3 : shaped.getRecipeHeight();
        for (int xPos = 0; xPos < xMax; ++xPos) {
            for (int yPos = 0; yPos < yMax; ++yPos) {
                int posY;
                int posX;
                Ingredient entry;
                int index = yPos * xMax + xPos;
                if (list.size() <= index || (entry = (Ingredient)list.get(index)) == Ingredient.f_43901_ || !screen.isOverArea(mouseX, mouseY, x + (posX = xPos * 18), y + (posY = yPos * 18), 16, 16)) continue;
                ItemStack[] array = entry.m_43908_();
                return array[time % array.length].m_41777_();
            }
        }
        return ItemStack.f_41583_;
    }
}

