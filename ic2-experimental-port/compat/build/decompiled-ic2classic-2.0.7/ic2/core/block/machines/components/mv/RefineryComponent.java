/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.mv;

import ic2.core.block.machines.tiles.mv.RefineryTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RefineryComponent
extends GuiWidget {
    RefineryTileEntity tile;

    public RefineryComponent(RefineryTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.m_142416_(new ToolTipButton(x + 18, y + 78, 12, 12, (Component)this.string("V"), T -> this.sendInfo(0))).setToolTip("gui.ic2.refinery.void");
        gui.m_142416_(new ToolTipButton(x + 38, y + 78, 12, 12, (Component)this.string("V"), T -> this.sendInfo(1))).setToolTip("gui.ic2.refinery.void");
        gui.m_142416_(new ToolTipButton(x + 133, y + 78, 12, 12, (Component)this.string("F"), T -> this.sendInfo(2))).setToolTip("gui.ic2.refinery.fill");
    }

    private void sendInfo(int index) {
        this.tile.sendToServer(index, 0);
    }
}

