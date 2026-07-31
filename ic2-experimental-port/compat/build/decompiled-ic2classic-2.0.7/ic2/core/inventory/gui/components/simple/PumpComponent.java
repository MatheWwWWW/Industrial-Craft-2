/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.inventory.gui.components.simple;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.tiles.readers.IPumpTile;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PumpComponent
extends GuiWidget {
    IPumpTile tile;
    Vec2i texPos;
    Vec2i barPos;
    Vec2i glassPos;
    String translationKey;

    public PumpComponent(Box2i box, IPumpTile tile, Vec2i texPos, Vec2i barPos, Vec2i glassPos, String translationKey) {
        super(box);
        this.tile = tile;
        this.texPos = texPos;
        this.barPos = barPos;
        this.glassPos = glassPos;
        this.translationKey = translationKey;
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
        Box2i box = this.getBox();
        int maxY = box.getHeight();
        int maxX = box.getWidth();
        float lvl = (float)maxY * Mth.m_14036_((float)((float)this.tile.getPumpProgress() / (float)this.tile.getPumpMaxProgress()), (float)0.0f, (float)1.0f);
        int xPos = x + box.getX();
        int yPos = y + box.getY();
        this.gui.drawTextureRegion(matrix, xPos, (float)yPos + ((float)(maxY - 6) - lvl), this.barPos.getX(), this.barPos.getY(), maxX, 5.0f);
        if (lvl > 0.0f) {
            this.gui.drawTextureRegion(matrix, xPos, (float)yPos + ((float)(maxY - 1) - lvl), this.texPos.getX(), this.texPos.getY(), maxX, lvl);
        }
        this.gui.drawTextureRegion(matrix, xPos, yPos -= 6, this.glassPos.getX(), this.glassPos.getY(), maxX, maxY + 5);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (this.isMouseOver(mouseX, mouseY) && this.translationKey != null && this.hasEUReader()) {
            tooltips.accept((Component)this.translate(this.translationKey, Formatters.EU_FORMAT.format(this.tile.getPumpProgress()), Formatters.EU_FORMAT.format(this.tile.getPumpMaxProgress())));
        }
    }
}

