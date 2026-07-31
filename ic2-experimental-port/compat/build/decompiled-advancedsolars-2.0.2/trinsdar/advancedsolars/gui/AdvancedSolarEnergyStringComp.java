/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  ic2.core.inventory.gui.components.GuiWidget
 *  ic2.core.inventory.gui.components.GuiWidget$ActionRequest
 *  ic2.core.utils.math.geometry.Box2i
 *  net.minecraft.network.chat.Component
 */
package trinsdar.advancedsolars.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.network.chat.Component;
import trinsdar.advancedsolars.blocks.BlockEntityAdvancedSolarPanel;
import trinsdar.advancedsolars.util.AdvancedSolarLang;

public class AdvancedSolarEnergyStringComp
extends GuiWidget {
    byte lastMode;
    BlockEntityAdvancedSolarPanel block;
    int white = 0xCDCDCD;

    public AdvancedSolarEnergyStringComp(BlockEntityAdvancedSolarPanel tile) {
        super(Box2i.EMPTY_BOX);
        this.block = tile;
    }

    protected void addRequests(Set<GuiWidget.ActionRequest> set) {
        set.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        if (this.block instanceof BlockEntityAdvancedSolarPanel.BlockEntityUltimateHybridSolarPanel) {
            this.gui.drawString(matrix, (Component)this.block.m_58900_().m_60734_().m_49954_(), 20, 5, 7718655);
        } else if (this.block instanceof BlockEntityAdvancedSolarPanel.BlockEntityHybridSolarPanel) {
            this.gui.drawString(matrix, (Component)this.block.m_58900_().m_60734_().m_49954_(), 41, 5, 7718655);
        } else {
            this.gui.drawString(matrix, (Component)this.block.m_58900_().m_60734_().m_49954_(), 33, 5, 7718655);
        }
        int eu = this.block.getStoredEU();
        int max = this.block.getMaxEU();
        if (eu > max) {
            eu = max;
        }
        this.gui.drawString(matrix, (Component)Component.m_237110_((String)AdvancedSolarLang.storage, (Object[])new Object[]{eu}), 7, 21, this.white);
        this.gui.drawString(matrix, (Component)Component.m_237113_((String)("/" + max)), 7, 31, this.white);
        this.gui.drawString(matrix, (Component)Component.m_237115_((String)AdvancedSolarLang.maxOutput), 7, 41, this.white);
        this.gui.drawString(matrix, (Component)Component.m_237113_((String)(this.block.getMaxOutput() + " EU/t")), 7, 51, this.white);
        this.gui.drawString(matrix, (Component)Component.m_237115_((String)AdvancedSolarLang.generating), 7, 61, this.white);
        this.gui.drawString(matrix, (Component)Component.m_237113_((String)(this.block.getOutput() + " EU/t")), 7, 71, this.white);
    }
}

