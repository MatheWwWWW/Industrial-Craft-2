/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.api.NumberFormat
 *  mcjty.theoneprobe.apiimpl.ProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  org.apache.commons.lang3.time.DurationFormatUtils
 */
package ic2.probeplugin.info.machines;

import ic2.api.energy.EnergyNet;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.base.tiles.impls.BaseChargingBenchTileEntity;
import ic2.core.block.cables.mointor.MonitorTileEntity;
import ic2.core.block.machines.tiles.ev.CrafterTileEntity;
import ic2.core.block.machines.tiles.mv.ChunkloaderTileEntity;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.apiimpl.ProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class ElectricBlockComponent
implements ITileInfoComponent<BaseElectricTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, BaseElectricTileEntity tile) {
        BaseChargingBenchTileEntity data;
        int missing;
        ProbeInfo probeInfo = (ProbeInfo)info;
        Panel electric = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = electric.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(tile.getTier()));
        basicInfo.text("ic2.probe.eu.max_in.name", tile.getMaxInput());
        if (tile instanceof ChunkloaderTileEntity) {
            ChunkloaderTileEntity chunk = (ChunkloaderTileEntity)tile;
            basicInfo.text("ic2.probe.eu.usage.name", ChunkloaderTileEntity.POWER_COST[chunk.radius] * (chunk.doesChunkProcessing ? 2 : 1));
        } else if (tile instanceof MonitorTileEntity) {
            MonitorTileEntity monitor = (MonitorTileEntity)tile;
            basicInfo.text("ic2.probe.eu.usage.name", monitor.isActive() ? Math.max(monitor.width, monitor.height) : 0);
        } else if (tile instanceof CrafterTileEntity) {
            CrafterTileEntity crafter = (CrafterTileEntity)tile;
            basicInfo.text("ic2.probe.crafter.usage", 25);
            basicInfo.text("ic2.probe.crafter.delay", crafter.getSpeed());
            basicInfo.text("ic2.probe.crafter.crafts", crafter.getCrafts());
        }
        IExpandedProbeInfo bars = basicInfo.vertical().element(ProbePluginHelper.generateHiddenBar(tile));
        if (tile instanceof BaseChargingBenchTileEntity && (missing = (data = (BaseChargingBenchTileEntity)tile).getMissingEnergy().getIntKey()) > 0) {
            int slots = 0;
            for (int i = 0; i < 16; ++i) {
                if (data.getStackInSlot(i).m_41619_()) continue;
                ++slots;
            }
            int average = Math.min(data.averager.getAverage(), missing);
            bars.progress(slots - data.getChargingSlots(), slots, IC2Styles.PROGRESS_BAR.copy().numberFormat(NumberFormat.NONE).prefix("ic2.probe.chargingBench.eta.name", new Object[]{DurationFormatUtils.formatDuration((long)(average <= 0 ? 0L : (long)(missing / average * 50)), (String)"HH:mm:ss")}).suffix((Component)Component.m_237119_()));
        }
        ProbePluginHelper.addTanks(tile, bars, true);
        ProbePluginHelper.generateAnySlots(tile, false, electric);
        if (tile instanceof MonitorTileEntity && !((MonitorTileEntity)tile).isMaster()) {
            return;
        }
        probeInfo.getElements().add(1, electric);
    }
}

