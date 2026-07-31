/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.entity.inv.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.entity.minecarts.ElectricMinecart;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnergyMinecartComponent
extends GuiWidget {
    ElectricMinecart minecart;

    public EnergyMinecartComponent(ElectricMinecart minecart) {
        super(Box2i.EMPTY_BOX);
        this.minecart = minecart;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        float per;
        float lvl;
        int eu = this.minecart.getStoredEU();
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        int offset = this.minecart.getGuiOffset();
        this.gui.drawTextureRegion(matrix, x + 55 + offset, y + 16, 0.0f, 166.0f, 52.0f, 54.0f);
        if (eu > 0 && (lvl = (per = Math.min(1.0f, (float)eu / (float)this.minecart.getMaxEU())) * 24.0f) > 0.0f) {
            this.gui.drawTextureRegion(matrix, x + 79 + offset, y + 34, 176.0f, 14.0f, lvl, 16.0f);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        int offset = this.minecart.getGuiOffset();
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.storage.power"), 85 + offset, 23, 0x404040);
        int max = this.minecart.getMaxEU();
        int eu = Math.min(this.minecart.getStoredEU(), max);
        this.gui.drawString(matrix, (Component)this.translate("misc.ic2.eu", Formatters.EU_FORMAT.format(eu)), 109 + offset, 35, 0x404040);
        this.gui.drawString(matrix, (Component)this.string("/").m_7220_((Component)this.translate("misc.ic2.eu", Formatters.EU_FORMAT.format(max))), 109 + offset, 45, 0x404040);
    }
}

