/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.generators.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.ISteamReactor;
import ic2.core.IC2;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ReactorComponent
extends GuiWidget {
    IReactor reactor;
    int size;

    public ReactorComponent(IReactor reactor, int size) {
        super(Box2i.EMPTY_BOX);
        this.reactor = reactor;
        this.size = size;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        int progress = (int)((double)this.reactor.getHeat() / (double)this.reactor.getMaxHeat() * 160.0);
        this.gui.drawTextureRegion(matrix, x + 8, y + 130, 0.0f, 238.0f, progress, 10.0f);
        if (this.size >= 8 && this.reactor instanceof ISteamReactor) {
            this.gui.drawTextureRegion(matrix, x - 18, y, 194.0f, 0.0f, 22.0f, 132.0f);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawCenterString(matrix, (Component)this.translate("gui.ic2.reactor.heat"), 89, 131, 0x404040);
        if (this.reactor instanceof ISteamReactor) {
            this.gui.drawRightString(matrix, (Component)this.translate("gui.ic2.reactor.fluid_output", ItemStack.f_41584_.format(this.reactor.getEnergyOutput() * 3.2)), 169, 146, 0x404040);
        } else {
            this.gui.drawRightString(matrix, (Component)this.translate("gui.ic2.reactor.energy_output", (int)(this.reactor.getEnergyOutput() * (double)IC2.CONFIG.reactorOutput.get())), 169, 146, 0x404040);
        }
    }

    @Override
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (mouseX >= 7 && mouseX <= 169 && mouseY >= 129 && mouseY <= 141 && PlayerHandler.getClientHandler().hasThermometer()) {
            tooltips.accept((Component)this.translate("gui.ic2.reactor.heat_amount", this.reactor.getHeat(), this.reactor.getMaxHeat()));
        }
    }
}

