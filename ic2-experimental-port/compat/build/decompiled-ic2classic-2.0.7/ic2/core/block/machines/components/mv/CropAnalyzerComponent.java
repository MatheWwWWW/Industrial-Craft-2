/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.mv;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.item.inv.inventory.CropAnalyzerInventory;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CropAnalyzerComponent
extends GuiWidget {
    CropAnalyzerInventory analyzer;

    public CropAnalyzerComponent(CropAnalyzerInventory analyzer) {
        super(Box2i.EMPTY_BOX);
        this.analyzer = analyzer;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        int level = this.analyzer.getLevel();
        if (level <= -1) {
            return;
        }
        if (level == 0) {
            this.gui.drawString(matrix, (Component)this.translate("info.crop.ic2.data.unknown"), 8, 37, 0xFFFFFF);
            return;
        }
        this.gui.drawString(matrix, this.analyzer.getName(), 8, 37, 0xFFFFFF);
        if (level >= 2) {
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.crop_analyzer.tier", this.analyzer.getTier()), 8, 50, 0xFFFFFF);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.crop_analyzer.discovered"), 8, 73, 0xFFFFFF);
            this.gui.drawString(matrix, this.analyzer.getOwner(), 8, 86, 0xFFFFFF);
        }
        if (level >= 3) {
            this.gui.drawString(matrix, (Component)this.string(this.analyzer.getDesc(0)), 8, 109, 0xFFFFFF);
            this.gui.drawString(matrix, (Component)this.string(this.analyzer.getDesc(1)), 8, 122, 0xFFFFFF);
        }
        if (level >= 4) {
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.crop_analyzer.growth"), 118, 37, 11403055);
            this.gui.drawString(matrix, (Component)this.string(this.analyzer.getStat(0)), 118, 50, 11403055);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.crop_analyzer.gain"), 118, 73, 15649024);
            this.gui.drawString(matrix, (Component)this.string(this.analyzer.getStat(1)), 118, 86, 15649024);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.crop_analyzer.resistance"), 118, 109, 52945);
            this.gui.drawString(matrix, (Component)this.string(this.analyzer.getStat(2)), 118, 122, 52945);
        }
    }
}

