/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.inventory.gui.components.simple;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.tiles.readers.IProgressMachine;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ProgressComponent
extends GuiWidget {
    IProgressMachine machine;
    Vec2i offset;
    boolean vertical;

    public ProgressComponent(Box2i box, IProgressMachine machine, Vec2i offset, boolean vertical) {
        super(box);
        this.machine = machine;
        this.offset = offset;
        this.vertical = vertical;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        float prog = this.machine.getProgress();
        if (prog >= 0.0f) {
            Box2i box = this.getBox();
            int width = box.getWidth();
            int height = box.getHeight();
            float lvl = (float)(this.vertical ? height : width) * Math.min(1.0f, prog / this.machine.getMaxProgress());
            if (lvl <= 0.0f) {
                return;
            }
            if (this.vertical) {
                this.gui.drawTextureRegion(matrix, this.gui.getGuiLeft() + box.getX(), (float)(this.gui.getGuiTop() + box.getY()) + ((float)height - lvl), this.offset.getX(), (float)this.offset.getY() + ((float)height - lvl), width, lvl);
                return;
            }
            this.gui.drawTextureRegion(matrix, this.gui.getGuiLeft() + box.getX(), this.gui.getGuiTop() + box.getY(), this.offset.getX(), this.offset.getY(), lvl, height);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (this.isMouseOver(mouseX, mouseY) && this.hasEUReader()) {
            tooltips.accept((Component)this.translate("gui.ic2.progress", Formatters.EU_FORMAT.format(this.machine.getProgress()), Formatters.EU_FORMAT.format(this.machine.getMaxProgress())));
        }
    }
}

