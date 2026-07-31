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
import trinsdar.advancedsolars.blocks.BlockEntityMolecularTransformer;

public class MolecularTransformerStringComp
extends GuiWidget {
    byte lastMode;
    BlockEntityMolecularTransformer block;
    int white = 0xCDCDCD;

    public MolecularTransformerStringComp(BlockEntityMolecularTransformer tile) {
        super(Box2i.EMPTY_BOX);
        this.block = tile;
    }

    protected void addRequests(Set<GuiWidget.ActionRequest> set) {
        set.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        int max;
        int eu = this.block.getStoredEU();
        if (eu > (max = this.block.getMaxEU())) {
            eu = max;
        }
        if (this.block.input.m_41619_() || this.block.output.m_41619_()) {
            return;
        }
        this.gui.drawString(matrix, (Component)Component.m_237113_((String)"Input: ").m_7220_(this.block.input.m_41611_()), 56, 11, this.white);
        this.gui.drawString(matrix, (Component)Component.m_237113_((String)"Output: ").m_7220_(this.block.output.m_41611_()), 56, 21, this.white);
        this.gui.drawString(matrix, (Component)Component.m_237113_((String)("Energy: " + max)), 56, 31, this.white);
        this.gui.drawString(matrix, (Component)Component.m_237113_((String)("EU in: " + this.block.energyInPerTick)), 56, 41, this.white);
        this.gui.drawString(matrix, (Component)Component.m_237113_((String)("Progress: " + Math.round((float)eu / (float)max * 100.0f) + "%")), 56, 51, this.white);
    }
}

