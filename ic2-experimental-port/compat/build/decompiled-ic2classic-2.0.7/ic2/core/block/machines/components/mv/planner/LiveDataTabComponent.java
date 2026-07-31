/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.mv.planner;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.components.mv.planner.BaseInfoTabComponent;
import ic2.core.block.machines.logic.planner.newLogic.ReactorLogic;
import ic2.core.block.machines.tiles.mv.ReactorPlannerTileEntity;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.helpers.Formatters;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LiveDataTabComponent
extends BaseInfoTabComponent {
    public LiveDataTabComponent(ReactorPlannerTileEntity tile) {
        super(88, tile, (Component)Component.m_237115_((String)"gui.ic2.reactor_planner.active_info"), new ItemStack((ItemLike)Items.f_41962_), 0);
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        super.addRequests(requests);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        if (!this.isSelected()) {
            return;
        }
        ReactorLogic logic = this.tile.simulation;
        int base = 0;
        if (this.tile.isSteamReactor()) {
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.production.steam", Formatters.EU_FORMAT.format(logic.steamProduction)), 8, 130, 0x404040);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.produced.steam", Formatters.EU_FORMAT.format(logic.totalSteamProduced)), 8, 140, 0x404040);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.consume.water", Formatters.EU_FORMAT.format(logic.totalWaterConsumed)), 8, 150, 0x404040);
            base = 160;
        } else {
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.production.eu", Formatters.EU_FORMAT.format(logic.getEnergyOutput())), 8, 133, 0x404040);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.produced.eu", Formatters.EU_FORMAT.format(logic.totalProducedPower)), 8, 143, 0x404040);
            base = 153;
        }
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.core_heat", Formatters.EU_FORMAT.format(logic.heat)), 8, base, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.melting_heat", Formatters.EU_FORMAT.format(logic.maxHeat)), 8, base + 10, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.heat_effect", Formatters.EU_FORMAT.format(logic.heatEffectModifier * 100.0f)), 8, base + 20, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.time", Formatters.EU_FORMAT.format(logic.tick)), 8, base + 30, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.time_left", Formatters.EU_FORMAT.format(logic.ticksLeft), Formatters.EU_FORMAT.format(logic.totalTicks)), 8, base + 40, 0x404040);
    }
}

