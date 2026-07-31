/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
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
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.helpers.SanityHelper;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PredictionTabComponent
extends BaseInfoTabComponent {
    public PredictionTabComponent(ReactorPlannerTileEntity tile) {
        super(148, tile, (Component)Component.m_237115_((String)"gui.ic2.reactor_planner.prediction"), new ItemStack((ItemLike)Items.f_42545_), 2);
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
        ReactorPrediction prediction = this.tile.prediction;
        int base = 0;
        if (this.tile.isSteamReactor()) {
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.prediction.steam.expected", Formatters.EU_FORMAT.format(prediction.steamPerTick)), 8, 133, 0x404040);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.prediction.steam.produced", Formatters.EU_FORMAT.format(prediction.totalSteamProduced)), 8, 143, 0x404040);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.prediction.water.consumed", Formatters.EU_FORMAT.format(prediction.totalWaterConsumed)), 8, 153, 0x404040);
            base = 163;
        } else {
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.prediction.eu.expected", Formatters.EU_FORMAT.format(Mth.m_14143_((float)prediction.energyPerTick))), 8, 133, 0x404040);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.prediction.eu.produced", Formatters.EU_FORMAT.format(prediction.totalEnergyProduced)), 8, 143, 0x404040);
            base = 153;
        }
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.prediction.heat_created", Formatters.EU_FORMAT.format(prediction.totalHeatProduced)), 8, base, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.prediction.explosion_strength", Formatters.EU_FORMAT.format(prediction.totalExplosionPower)), 8, base + 10, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.prediction.efficiency", Formatters.EU_READER_FORMAT.format(prediction.efficiency), Formatters.EU_READER_FORMAT.format(prediction.totalEfficiency)), 8, base + 20, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.prediction.breeder", SanityHelper.firstLetterUppercase(Boolean.toString(prediction.breeder))), 8, base + 30, 0x404040);
    }
}

