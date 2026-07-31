/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.inv.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.block.machines.components.mv.planner.NamingTabComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ImprovedTextWidget;
import ic2.core.item.inv.inventory.ReactorCardInventory;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ReactorCardComponent
extends GuiWidget {
    ReactorCardInventory inventory;

    public ReactorCardComponent(ReactorCardInventory inventory) {
        super(Box2i.EMPTY_BOX);
        this.inventory = inventory;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.KEY_INPUT);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        ImprovedTextWidget number = new ImprovedTextWidget(gui.getGuiLeft() + 118, gui.getGuiTop() + 27, 50, 20);
        number.m_94190_(true);
        number.m_94199_(5);
        number.m_94182_(false);
        number.m_94153_(NamingTabComponent.NUMBERS_ONLY);
        number.m_94144_(Integer.toString(this.inventory.maxHeat));
        number.m_94151_(this::updateMaxHeat);
        gui.addRenderableWidget(-2, number);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_card.max_heat"), 116, 17, 0x404040);
    }

    @Override
    public boolean onKeyTyped(int keyCode) {
        return this.gui.getCastedButton(-2, ImprovedTextWidget.class).m_93696_() && keyCode == 69;
    }

    private void updateMaxHeat(String number) {
        try {
            IC2.NETWORKING.get(false).sendClientItemEvent(this.inventory.getInventoryStack(), -1, Integer.parseInt(number));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

