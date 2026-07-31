/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.wiki.recipes.renderers;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.platform.rendering.RenderUtils;
import ic2.core.wiki.components.IWikiComponent;
import ic2.core.wiki.recipes.renderers.IRecipeRenderer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SmeltingRecipeRenderer
implements IRecipeRenderer {
    Recipe<?> recipe;

    public SmeltingRecipeRenderer(Recipe<?> recipe) {
        this.recipe = recipe;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderBackground(IC2Screen screen, PoseStack matrix, int x, int y) {
        int timer = screen.getClock() / 2 % 22;
        screen.bindDefaultTexture();
        screen.drawTextureRegion(matrix, x, y, 0.0f, 248.0f, 116.0f, 4.0f);
        screen.drawTextureRegion(matrix, x, y + 60, 0.0f, 252.0f, 116.0f, 4.0f);
        screen.bindTexture(IWikiComponent.TEXTURE);
        screen.drawTextureRegion(matrix, x += 33, y += 20, 158.0f, 0.0f, 20.0f, 20.0f);
        screen.drawTextureRegion(matrix, x + 26, y + 2, 212.0f, 0.0f, 22.0f, 15.0f);
        screen.drawTextureRegion(matrix, x + 26, y + 2, 234.0f, 0.0f, 22 - (22 - timer), 15.0f);
        screen.drawTextureRegion(matrix, x + 56, y - 3, 124.0f, 0.0f, 24.0f, 24.0f, 18.0f, 18.0f);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderItems(IC2Screen screen, PoseStack matrix, int x, int y) {
        int time = screen.getClock() / 20;
        Lighting.m_84931_();
        ItemRenderer item = screen.getRenderItem();
        RenderUtils.renderGuiItem(item, matrix, this.recipe.m_8042_(), (x += 33) - 26, (y += 20) + 2);
        this.renderMultiOutput(matrix, (List<ItemStack>)ObjectArrayList.wrap((Object[])((Ingredient)this.recipe.m_7527_().get(0)).m_43908_()), x + 2, y + 2, time, screen.getFont(), item);
        RenderUtils.renderGuiItem(item, matrix, this.recipe.m_8043_(), x + 60, y + 1);
        RenderUtils.renderGuiItemDecorations(matrix, screen.getFont(), this.recipe.m_8043_(), x + 60, y + 1);
        Lighting.m_84930_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public List<Component> getHoverInfo(IC2Screen screen, int mouseX, int mouseY, int x, int y) {
        if (screen.isOverArea(mouseX, mouseY, (x += 33) + 2, (y += 20) + 2, 16, 16)) {
            int time = screen.getClock() / 20;
            ItemStack[] array = ((Ingredient)this.recipe.m_7527_().get(0)).m_43908_();
            if (array.length > 0) {
                return this.getToolTip(array[time % array.length]);
            }
        }
        if (screen.isOverArea(mouseX, mouseY, x + 60, y + 1, 16, 16)) {
            return this.getToolTip(this.recipe.m_8043_());
        }
        return screen.isOverArea(mouseX, mouseY, x - 26, y + 1, 16, 16) ? this.getToolTip(this.recipe.m_8042_()) : Collections.emptyList();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public ItemStack getHoverStack(IC2Screen screen, int mouseX, int mouseY, int x, int y) {
        if (screen.isOverArea(mouseX, mouseY, (x += 33) + 2, (y += 20) + 2, 16, 16)) {
            int time = screen.getClock() / 20;
            ItemStack[] array = ((Ingredient)this.recipe.m_7527_().get(0)).m_43908_();
            if (array.length > 0) {
                return array[time % array.length].m_41777_();
            }
        }
        if (screen.isOverArea(mouseX, mouseY, x + 60, y + 1, 16, 16)) {
            return this.recipe.m_8043_().m_41777_();
        }
        return screen.isOverArea(mouseX, mouseY, x - 26, y + 1, 16, 16) ? this.recipe.m_8042_().m_41777_() : ItemStack.f_41583_;
    }
}

