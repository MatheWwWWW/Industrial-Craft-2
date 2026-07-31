/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 */
package ic2.core.block.storage.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.base.tiles.impls.BaseChargePadTileEntity;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.network.chat.Component;

public class PadComponent
extends GuiWidget {
    BaseChargePadTileEntity tile;

    public PadComponent(BaseChargePadTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        int max = this.tile.getMaxEU();
        int eu = Math.min(max, this.tile.getStoredEU());
        this.gui.drawString(matrix, (Component)this.translate("misc.ic2.eu", Formatters.EU_FORMAT.format(eu)), 48, 16, 0x404040);
        this.gui.drawString(matrix, (Component)this.string("/").m_7220_((Component)this.translate("misc.ic2.eu", Formatters.EU_FORMAT.format(max))), 48, 25, 0x404040);
        matrix.m_85836_();
        matrix.m_85837_(48.0, 38.0, 0.0);
        matrix.m_85841_(0.5f, 0.5f, 1.0f);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.charge_pad.out", Formatters.EU_FORMAT.format(this.tile.transferLimit)), 0, -8, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.charge_pad.tier", this.tile.tier), 0, 2, 0x404040);
        matrix.m_85849_();
    }

    @Override
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        int upgrades;
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        if (this.tile.data.getBaseTier() == 1) {
            this.gui.drawTextureRegion(matrix, x + 151, y + 28, 188.0f, 0.0f, 18.0f, 18.0f);
            this.gui.drawTextureRegion(matrix, x + 151, y + 57, 188.0f, 0.0f, 18.0f, 18.0f);
        }
        if ((upgrades = this.tile.inventory.getSlotCount() - (this.tile.data.getBaseTier() > 1 ? 3 : 1)) < 3) {
            this.gui.drawTextureRegion(matrix, x + 107, y + 57, 188.0f, 0.0f, 18.0f, 18.0f);
            if (upgrades < 2) {
                this.gui.drawTextureRegion(matrix, x + 87, y + 57, 188.0f, 0.0f, 18.0f, 18.0f);
            }
        }
    }
}

