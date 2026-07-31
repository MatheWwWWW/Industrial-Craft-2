/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.generators.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.generators.tiles.WindTurbineTileEntity;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WindTurbineComponent
extends GuiWidget {
    WindTurbineTileEntity tile;

    public WindTurbineComponent(WindTurbineTileEntity tile) {
        super(new Box2i(80, 45, 14, 14));
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        float l = Math.min(14.0f, this.tile.subproduction.getProduction(1600.0f) * 14.0f);
        if (l > 0.0f) {
            this.gui.drawTextureRegion(matrix, x + 80, (float)(y + 45 + 14) - l, 176.0f, 14.0f - l, 14.0f, l);
        }
        if ((l = Math.min(14.0f, this.tile.subproduction.getProduction(1600.0f, 1200.0f) * 14.0f)) > 0.0f) {
            this.gui.drawTextureRegion(matrix, x + 80, (float)(y + 45 + 14) - l, 176.0f, 28.0f - l, 14.0f, l);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (this.isMouseOver(mouseX, mouseY)) {
            tooltips.accept((Component)this.translate("gui.ic2.production", Formatters.EU_FORMAT.format(this.tile.subproduction.getProduction(100.0f))));
        }
    }
}

