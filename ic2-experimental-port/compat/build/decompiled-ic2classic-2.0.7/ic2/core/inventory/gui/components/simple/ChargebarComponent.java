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
import ic2.api.tiles.readers.IEUStorage;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChargebarComponent
extends GuiWidget {
    IEUStorage provider;
    Vec2i offset;
    boolean vertical;
    int stepSize = 0;
    int initialOffset = 0;

    public ChargebarComponent(Box2i box, IEUStorage storage, Vec2i sourcePosition, boolean vertical) {
        super(box);
        this.provider = storage;
        this.offset = sourcePosition;
        this.vertical = vertical;
    }

    public ChargebarComponent setStepSize(int value, int initialOffset) {
        this.stepSize = value;
        this.initialOffset = initialOffset;
        return this;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        int eu = this.provider.getStoredEU();
        if (eu > 0) {
            Box2i box = this.getBox();
            int width = box.getWidth();
            int height = box.getHeight();
            int max = this.vertical ? height : width;
            float lvl = (float)max * Math.min(1.0f, (float)eu / (float)this.provider.getMaxEU());
            if (lvl <= 0.0f) {
                return;
            }
            if (this.stepSize > 0) {
                boolean isMax = lvl >= (float)max;
                float f = lvl = isMax ? (float)max : (float)((int)lvl / this.stepSize * this.stepSize);
                if (lvl <= 0.0f) {
                    return;
                }
                if (!isMax) {
                    lvl += (float)this.initialOffset;
                }
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
            tooltips.accept((Component)this.translate("gui.ic2.charge", Formatters.EU_FORMAT.format(this.provider.getStoredEU()), Formatters.EU_FORMAT.format(this.provider.getMaxEU())));
        }
    }
}

