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
import ic2.core.block.generators.tiles.FuelBoilerTileEntity;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FuelBoilerComponent
extends GuiWidget {
    FuelBoilerTileEntity tile;

    public FuelBoilerComponent(FuelBoilerTileEntity tile) {
        super(Box2i.EMPTY_BOX);
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
        float total = (float)this.tile.heat / 480.0f;
        if (total > 0.0f) {
            this.gui.drawGradientRegionVertical(matrix, this.gui.getGuiLeft() + 140, (float)(this.gui.getGuiTop() + 19) + (50.0f - total), 6.0f, total, ColorUtils.mixColors(ColorUtils.BLUE, ColorUtils.RED, Math.max(0.0f, (total - 25.0f) / 25.0f)), ColorUtils.BLUE);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (mouseX >= 140 && mouseX <= 145 && mouseY >= 19 && mouseY <= 69) {
            tooltips.accept((Component)this.translate("gui.ic2.fuel_boiler.heat", this.tile.heat / 30, 800));
        }
    }
}

