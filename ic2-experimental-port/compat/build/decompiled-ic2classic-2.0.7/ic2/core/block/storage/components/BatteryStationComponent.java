/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.ints.Int2IntMap$Entry
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.apache.commons.lang3.time.DurationFormatUtils
 */
package ic2.core.block.storage.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.base.tiles.impls.BaseBatteryStationTileEntity;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.geometry.Box2i;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class BatteryStationComponent
extends GuiWidget {
    BaseBatteryStationTileEntity tile;

    public BatteryStationComponent(BaseBatteryStationTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.battery_station.eu"), 150, 20, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.battery_station.etc"), 150, 30, 0x404040);
        this.gui.drawRightString(matrix, (Component)this.translate("gui.ic2.battery_station.left"), 25, 30, 0x404040);
        float scale = this.tile.getMaxEU() >= 10000000 ? 0.5f : 1.0f;
        float invScale = 1.0f / scale;
        matrix.m_85841_(scale, scale, scale);
        this.gui.drawRightString(matrix, (Component)this.string(Formatters.EU_FORMAT.format(this.tile.getStoredEU())), (int)(80.0f * invScale), (int)(20.0f * invScale), 0x55FF55);
        this.gui.drawCenterString(matrix, (Component)this.string(" / "), (int)(88.0f * invScale), (int)(20.0f * invScale), 0x55FF55);
        this.gui.drawString(matrix, (Component)this.string(Formatters.EU_FORMAT.format(this.tile.getMaxEU())), (int)(95.0f * invScale), (int)(20.0f * invScale), 0x55FF55);
        matrix.m_85841_(invScale, invScale, invScale);
        Int2IntMap.Entry missingTuple = this.tile.getMissingEnergy();
        int missing = missingTuple.getIntKey() + (this.tile.getMaxEU() - this.tile.getStoredEU());
        this.gui.drawString(matrix, (Component)(missing >= 100000000 ? this.translate("gui.ic2.battery_station.alot") : this.string(Formatters.EU_FORMAT.format(missing)).m_7220_((Component)this.translate("gui.ic2.battery_station.eu"))), 35, 30, 0x55FF55);
        if (missing <= 0) {
            this.gui.drawRightString(matrix, (Component)this.translate("gui.ic2.battery_station.done"), 145, 30, 0x55FF55);
        } else {
            int average = Math.min(this.tile.averager.getAverage(), missingTuple.getIntKey() <= 0 ? Integer.MAX_VALUE : missingTuple.getIntValue());
            if (average > 0) {
                this.gui.drawRightString(matrix, (Component)this.string(DurationFormatUtils.formatDuration((long)Math.max(0L, (long)(missing / average) * 50L), (String)"HH:mm:ss")), 145, 30, 0x55FF55);
            } else {
                this.gui.drawRightString(matrix, (Component)this.translate("gui.ic2.battery_station.never"), 145, 30, 0x55FF55);
            }
        }
    }
}

