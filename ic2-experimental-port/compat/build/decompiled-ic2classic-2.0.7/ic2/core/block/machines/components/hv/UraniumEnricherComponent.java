/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.hv;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.recipes.misc.EnrichRecipe;
import ic2.core.block.machines.tiles.hv.UraniumEnchricherTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.helpers.SanityHelper;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UraniumEnricherComponent
extends GuiWidget {
    UraniumEnchricherTileEntity tile;

    public UraniumEnricherComponent(Box2i box, UraniumEnchricherTileEntity tile) {
        super(box);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        EnrichRecipe recipe = this.tile.getStoredRecipe();
        if (recipe != null) {
            float pixel = (float)this.getBox().getHeight() * ((float)this.tile.getPoints() / (float)this.tile.getMaxPoints());
            this.gui.drawColoredRegion(matrix, this.box.getX() + this.gui.getGuiLeft() + 1, (float)(this.box.getY() + this.gui.getGuiTop() + this.box.getHeight()) - pixel, this.box.getWidth() - 1, pixel, recipe.getColor());
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        EnrichRecipe recipe;
        if (this.getBox().isInBox(mouseX, mouseY) && (recipe = this.tile.getStoredRecipe()) != null) {
            tooltips.accept((Component)this.string(SanityHelper.toPascalCase(recipe.getId().m_135815_())));
            tooltips.accept((Component)this.string(this.tile.getPoints() + " / 1000"));
        }
    }
}

