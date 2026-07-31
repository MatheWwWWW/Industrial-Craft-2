/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.inv.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.item.inv.inventory.TFBPInventory;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TFBPComponent
extends GuiWidget {
    TFBPInventory inventory;

    public TFBPComponent(TFBPInventory inventory) {
        super(Box2i.EMPTY_BOX);
        this.inventory = inventory;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.addRenderableWidget(0, new ToolTipButton(x + 49, y + 35, 12, 12, (Component)this.string("-"), T -> this.changeRadius(-1))).setToolTip((Component)this.translate("tooltip.ic2.press_key_description", this.translate("tooltip.ic2.shift.name"), "10x"));
        gui.addRenderableWidget(1, new ToolTipButton(x + 115, y + 35, 12, 12, (Component)this.string("+"), T -> this.changeRadius(1))).setToolTip((Component)this.translate("tooltip.ic2.press_key_description", this.translate("tooltip.ic2.shift.name"), "10x"));
    }

    private void changeRadius(int offset) {
        int newRadius = Mth.m_14045_((int)(this.inventory.radius + (offset *= Screen.m_96638_() ? 10 : 1)), (int)0, (int)100);
        IC2.NETWORKING.get(false).sendClientItemEvent(this.inventory.getInventoryStack(), 0, newRadius);
        this.inventory.radius = newRadius;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawCenterString(matrix, (Component)this.string("gui.ic2.tfbp.radius"), 88, 22, 0x404040);
        this.gui.drawCenterString(matrix, (Component)this.string(Integer.toString(this.inventory.radius)), 88, 37, 0x404040);
    }
}

