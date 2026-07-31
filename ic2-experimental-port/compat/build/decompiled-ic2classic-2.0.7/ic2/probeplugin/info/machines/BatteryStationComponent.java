/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.api.NumberFormat
 *  mcjty.theoneprobe.apiimpl.ProbeInfo
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  org.apache.commons.lang3.time.DurationFormatUtils
 */
package ic2.probeplugin.info.machines;

import ic2.api.energy.EnergyNet;
import ic2.core.block.base.tiles.impls.BaseBatteryStationTileEntity;
import ic2.core.utils.helpers.Formatters;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.info.transport.CableProvider;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.apiimpl.ProbeInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class BatteryStationComponent
implements ITileInfoComponent<BaseBatteryStationTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, BaseBatteryStationTileEntity tile) {
        ProbeInfo probeInfo = (ProbeInfo)info;
        Panel generator = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = generator.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(tile.getSourceTier()));
        basicInfo.text("ic2.probe.eu.output.max.name", tile.getMaxEnergyOutput());
        CableProvider.EnergyContainer result = CableProvider.getContainer(tile);
        if (result.getAverageOut() > 0) {
            basicInfo.text((Component)this.translate("tooltip.item.ic2.eu_reader.cable_flow_out", Formatters.EU_FORMAT.format(result.getAverageOut())).m_130940_(ChatFormatting.AQUA));
            basicInfo.text((Component)this.translate("tooltip.item.ic2.eu_reader.packet_flow_out", Formatters.EU_FORMAT.format(result.getPacketsOut())).m_130940_(ChatFormatting.AQUA));
        }
        IExpandedProbeInfo bars = basicInfo.vertical(IC2Styles.BARS_STYLE).element(ProbePluginHelper.generateEUBar(tile, 125));
        int missing = tile.getMissingEnergy().getIntKey();
        if (missing > 0) {
            int slots = 0;
            for (int i = 0; i < 16; ++i) {
                if (tile.getStackInSlot(i).m_41619_()) continue;
                ++slots;
            }
            int average = Math.min(tile.averager.getAverage(), missing);
            bars.progress(slots - tile.getChargingSlots(), slots, IC2Styles.PROGRESS_BAR.copy().numberFormat(NumberFormat.NONE).prefix("ic2.probe.discharging.eta.name", new Object[]{DurationFormatUtils.formatDuration((long)(average <= 0 ? 0L : (long)(missing / average * 50)), (String)"HH:mm:ss")}).suffix((Component)Component.m_237119_()).width(125));
        }
        ProbePluginHelper.generateDefaultSlots(tile, false, generator);
        probeInfo.getElements().add(1, generator);
    }
}

