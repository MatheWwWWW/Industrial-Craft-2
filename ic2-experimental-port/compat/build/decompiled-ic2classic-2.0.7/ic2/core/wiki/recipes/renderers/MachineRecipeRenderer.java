/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.wiki.recipes.renderers;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.wiki.components.IWikiComponent;
import ic2.core.wiki.helper.ProgressBar;
import ic2.core.wiki.recipes.renderers.IRecipeRenderer;
import java.util.Collections;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MachineRecipeRenderer
implements IRecipeRenderer {
    IMachineRecipeList.RecipeEntry entry;
    ItemStack machine;
    ProgressBar bar;

    public MachineRecipeRenderer(IMachineRecipeList.RecipeEntry entry, ItemStack machine, ProgressBar bar) {
        this.entry = entry;
        this.machine = machine;
        this.bar = bar;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderBackground(IC2Screen screen, PoseStack matrix, int x, int y) {
        screen.bindDefaultTexture();
        screen.drawTextureRegion(matrix, x, y, 0.0f, 248.0f, 116.0f, 4.0f);
        screen.drawTextureRegion(matrix, x, y + 60, 0.0f, 252.0f, 116.0f, 4.0f);
        screen.bindTexture(IWikiComponent.TEXTURE);
        screen.drawTextureRegion(matrix, x += 33, y += 20, 158.0f, 0.0f, 20.0f, 20.0f);
        screen.drawTextureRegion(matrix, x + 56, y - 3, 124.0f, 0.0f, 24.0f, 24.0f, 18.0f, 18.0f);
        screen.bindTexture(this.bar.getTexture());
        int timer = screen.getClock() / 2 % this.bar.getWidth();
        screen.drawTextureRegion(matrix, x + 26, y + 2, this.bar.getMinX(false), this.bar.getMinY(false), this.bar.getWidth(), this.bar.getHeight());
        screen.drawTextureRegion(matrix, x + 26, y + 2, this.bar.getMinX(true), this.bar.getMinY(true), this.bar.getWidth() - (this.bar.getWidth() - timer), this.bar.getHeight());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderItems(IC2Screen screen, PoseStack matrix, int x, int y) {
        int time = screen.getClock() / 20;
        Lighting.m_84931_();
        screen.drawItemStack(matrix, (x += 33) - 26, (y += 20) + 2, this.machine);
        this.renderMultiOutput(matrix, this.entry.getInputs()[0].getComponents(), x + 2, y + 2, time, screen.getFont(), screen.getRenderItem());
        this.renderMultiOutput(matrix, this.entry.getOutput().getAllOutputs(), x + 60, y + 1, time, screen.getFont(), screen.getRenderItem());
        Lighting.m_84930_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public List<Component> getHoverInfo(IC2Screen screen, int mouseX, int mouseY, int x, int y) {
        List<ItemStack> list;
        if (screen.isOverArea(mouseX, mouseY, (x += 33) + 2, (y += 20) + 2, 16, 16) && (list = this.entry.getInputs()[0].getComponents()).size() > 0) {
            int time = screen.getClock() / 20;
            return this.getToolTip(list.get(time % list.size()));
        }
        if (screen.isOverArea(mouseX, mouseY, x + 60, y + 1, 16, 16) && (list = this.entry.getOutput().getAllOutputs()).size() > 0) {
            int time = screen.getClock() / 20;
            return this.getToolTip(list.get(time % list.size()));
        }
        return screen.isOverArea(mouseX, mouseY, x - 26, y + 1, 16, 16) ? this.getToolTip(this.machine) : Collections.emptyList();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public ItemStack getHoverStack(IC2Screen screen, int mouseX, int mouseY, int x, int y) {
        List<ItemStack> list;
        if (screen.isOverArea(mouseX, mouseY, (x += 33) + 2, (y += 20) + 2, 16, 16) && (list = this.entry.getInputs()[0].getComponents()).size() > 0) {
            int time = screen.getClock() / 20;
            return list.get(time % list.size()).m_41777_();
        }
        if (screen.isOverArea(mouseX, mouseY, x + 60, y + 1, 16, 16) && (list = this.entry.getOutput().getAllOutputs()).size() > 0) {
            int time = screen.getClock() / 20;
            return list.get(time % list.size()).m_41777_();
        }
        return screen.isOverArea(mouseX, mouseY, x - 26, y + 1, 16, 16) ? this.machine.m_41777_() : ItemStack.f_41583_;
    }
}

