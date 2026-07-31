/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.apache.commons.lang3.time.DurationFormatUtils
 */
package ic2.core.block.machines.components.luv;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.tiles.luv.FusionReactorTileEntity;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class FusionReactorComponent
extends GuiWidget {
    FusionReactorTileEntity tile;

    public FusionReactorComponent(FusionReactorTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        float total = (float)this.tile.heat / 48000.0f * 50.0f;
        if (total > 0.0f) {
            this.gui.drawGradientRegionVertical(matrix, this.gui.getGuiLeft() + 140, (float)(this.gui.getGuiTop() + 19) + (50.0f - total), 6.0f, total, ColorUtils.mixColors(ColorUtils.BLUE, ColorUtils.RED, Math.max(0.0f, (total - 25.0f) / 25.0f)), ColorUtils.BLUE);
        }
        if ((total = this.tile.getMaterial() * 50.0f) > 0.0f) {
            this.gui.drawTextureRegion(matrix, this.gui.getGuiLeft() + 30, (float)(this.gui.getGuiTop() + 18) + (50.0f - total), 176.0f, 156.0f - total, 6.0f, total);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        long ticks = this.tile.getTicksTillHeatup();
        if (ticks > 0L) {
            this.gui.drawString(matrix, (Component)this.string("Heatup:"), 39, 62, 0x404040);
            this.gui.drawRightString(matrix, (Component)this.string(DurationFormatUtils.formatDuration((long)Math.abs(ticks * 50L), (String)"HH:mm:ss")), 79, 72, 0x404040);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (mouseX >= 140 && mouseX <= 146 && mouseY >= 19 && mouseY <= 69) {
            tooltips.accept((Component)this.string("Heat: " + this.tile.heat + " / 48000"));
        }
        if (mouseX >= 30 && mouseX <= 36 && mouseY >= 18 && mouseY <= 68) {
            tooltips.accept((Component)this.string("Material: " + this.tile.material + " / " + this.tile.maxMaterial));
        }
    }
}

