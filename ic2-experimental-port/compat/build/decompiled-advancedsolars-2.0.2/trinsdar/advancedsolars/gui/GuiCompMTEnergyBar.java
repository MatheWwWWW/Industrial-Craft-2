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
import trinsdar.advancedsolars.blocks.BlockEntityMolecularTransformer;

public class GuiCompMTEnergyBar
extends GuiWidget {
    BlockEntityMolecularTransformer transformer;
    Vec2i pos = new Vec2i(176, 0);

    public GuiCompMTEnergyBar(BlockEntityMolecularTransformer transformer) {
        super(new Box2i(30, 30, 8, 24));
        this.transformer = transformer;
    }

    protected void addRequests(Set<GuiWidget.ActionRequest> set) {
        set.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        Box2i box = this.getBox();
        if (this.transformer.isActive()) {
            float percentage = (float)this.transformer.energy / (float)this.transformer.maxEnergy;
            this.gui.drawTextureRegion(matrix, (float)(this.gui.getGuiLeft() + box.getX()), (float)(this.gui.getGuiTop() + box.getY()), (float)this.pos.getX(), (float)this.pos.getY(), (float)box.getWidth(), percentage * (float)box.getHeight());
        }
    }
}

