/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.apache.commons.lang3.time.DurationFormatUtils
 */
package ic2.core.block.personal.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.personal.tile.FluidOMatTileEntity;
import ic2.core.block.personal.trade.Trade;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.geometry.Box2i;
import java.time.Duration;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class FluidOMatClosedComponent
extends GuiWidget {
    public static Box2i INFO = new Box2i(85, 72, 8, 14);
    FluidOMatTileEntity tile;

    public FluidOMatClosedComponent(FluidOMatTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.m_142416_(new ToolTipButton(x + 61, y + 87, 54, 12, (Component)this.translate("gui.ic2.trade.buy"), T -> this.tile.sendToServer(7, 0)));
        gui.hideSlot(0, 1, 2, 3);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (INFO.isInBox(mouseX, mouseY)) {
            Object trade = this.tile.getTrade();
            MutableComponent text = this.translate("gui.ic2.trade.input_infos").m_130946_("\n");
            if (((Trade)trade).moneyNeeded > 0) {
                text.m_7220_((Component)this.translate("gui.ic2.trade.check.money_request", ((Trade)trade).moneyNeeded)).m_130946_("\n");
            }
            text.m_7220_((Component)this.translate((this.tile.inputFilter & 0x10) != 0 ? "gui.ic2.trade.check.nbt_needed" : "gui.ic2.trade.check.nbt_ignored")).m_130946_("\n");
            if ((this.tile.inputFilter & 0x80) != 0) {
                text.m_7220_((Component)this.translate("gui.ic2.trade.check.fluid")).m_130946_("\n");
            }
            tooltips.accept((Component)text);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        Object trade = this.tile.getTrade();
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.trade.stock", Formatters.EU_FORMAT.format(((Trade)trade).getStock(this.tile.stock, null))), 70, 100, 0x404040);
        long timeLeft = ((Trade)trade).lastTrade + (long)((Trade)trade).tradeDelay - this.tile.m_58904_().m_46467_();
        if (timeLeft > 0L && ((Trade)trade).tradeDelay > 0) {
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.trade.restock").m_130946_(DurationFormatUtils.formatDuration((long)Duration.ofSeconds(timeLeft / 20L + 1L).toMillis(), (String)"HH:mm:ss")), 70, 90, 0x404040);
        }
    }
}

