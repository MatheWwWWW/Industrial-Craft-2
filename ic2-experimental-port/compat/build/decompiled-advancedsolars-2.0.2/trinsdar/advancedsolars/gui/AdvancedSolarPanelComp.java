/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  ic2.core.inventory.gui.components.GuiWidget
 *  ic2.core.inventory.gui.components.GuiWidget$ActionRequest
 *  ic2.core.utils.math.geometry.Box2i
 *  ic2.core.utils.math.geometry.Vec2i
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package trinsdar.advancedsolars.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import java.util.Set;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import trinsdar.advancedsolars.blocks.BlockEntityAdvancedSolarPanel;

public class AdvancedSolarPanelComp
extends GuiWidget {
    BlockEntityAdvancedSolarPanel block;
    Vec2i dayTexPos;
    Vec2i nightTexPos;

    public AdvancedSolarPanelComp(BlockEntityAdvancedSolarPanel tile, Box2i box, Vec2i dayPos, Vec2i nightPos) {
        super(box);
        this.block = tile;
        this.dayTexPos = dayPos;
        this.nightTexPos = nightPos;
    }

    protected void addRequests(Set<GuiWidget.ActionRequest> set) {
        set.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        Box2i box = this.getBox();
        if (this.block.isActive()) {
            Vec2i pos = this.block.isDay() ? this.dayTexPos : this.nightTexPos;
            this.gui.drawTextureRegion(matrix, (float)(this.gui.getGuiLeft() + box.getX()), (float)(this.gui.getGuiTop() + box.getY()), (float)pos.getX(), (float)pos.getY(), (float)box.getWidth(), (float)box.getHeight());
        }
    }
}

