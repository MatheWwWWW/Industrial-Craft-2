/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.mv.planner;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.components.mv.planner.BaseInfoTabComponent;
import ic2.core.block.machines.logic.planner.newLogic.ReactorPrediction;
import ic2.core.block.machines.tiles.mv.ReactorPlannerTileEntity;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.helpers.Formatters;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StatisticsTabComponent
extends BaseInfoTabComponent {
    public StatisticsTabComponent(ReactorPlannerTileEntity tile) {
        super(118, tile, (Component)Component.m_237115_((String)"gui.ic2.reactor_planner.statistics"), new ItemStack((ItemLike)IC2Items.COAL_BALL), 1);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    protected void drawSideTab(PoseStack matrix, int xOffset, int yOffset, ItemStack item, boolean enabled) {
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        this.gui.drawTextureRegion(matrix, x + xOffset, y + yOffset, enabled ? 122.0f : 156.0f, 212.0f, enabled ? 31.0f : 28.0f, 28.0f);
        this.gui.drawTextureRegion(matrix, x + xOffset + 9, y + yOffset + 7, 90.0f, 228.0f, 14.0f, 13.0f);
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        super.addRequests(requests);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        boolean cooling;
        if (!this.isSelected()) {
            return;
        }
        ReactorPrediction prediction = this.tile.prediction;
        boolean bl = cooling = prediction.coolingPerTick > prediction.heatPerTick;
        if (this.tile.isSteamReactor()) {
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.statistics.expected.heat", Formatters.EU_FORMAT.format(prediction.heatPerTick)), 8, 133, 0x404040);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.statistics.expected.vent", Formatters.EU_FORMAT.format(prediction.coolingPerTick)), 8, 143, 0x404040);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.statistics.expected.reactor", Formatters.EU_FORMAT.format(prediction.reactorCoolingPerTick)), 8, 153, 0x404040);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.statistics.excess." + (cooling ? "cooling" : "heating"), Formatters.EU_FORMAT.format(Math.abs(prediction.coolingPerTick - prediction.heatPerTick))), 8, 163, 0x404040);
        } else {
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.statistics.expected.heat", Formatters.EU_FORMAT.format(prediction.heatPerTick)), 8, 133, 0x404040);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.statistics.expected.pack", Formatters.EU_FORMAT.format(prediction.heatPackHeatPerTick)), 8, 143, 0x404040);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.statistics.expected.vent", Formatters.EU_FORMAT.format(prediction.coolingPerTick)), 8, 153, 0x404040);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.statistics.expected.reactor", Formatters.EU_FORMAT.format(prediction.reactorCoolingPerTick)), 8, 163, 0x404040);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.statistics.excess." + (cooling ? "cooling" : "heating"), Formatters.EU_FORMAT.format(Math.abs(prediction.coolingPerTick - prediction.heatPerTick))), 8, 173, 0x404040);
        }
    }
}

