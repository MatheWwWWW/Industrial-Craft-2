/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.api.NumberFormat
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info;

import ic2.api.energy.EnergyNet;
import ic2.core.block.base.tiles.BaseMultiBlockTileEntity;
import ic2.core.block.generators.tiles.SteamTunnelTileEntity;
import ic2.core.block.machines.tiles.ev.ElectricFisherTileEntity;
import ic2.core.block.storage.tiles.tank.TankTileEntity;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.NumberFormat;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;

public class MultiBlockComponent
implements ITileInfoComponent<BaseMultiBlockTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, BaseMultiBlockTileEntity tile) {
        IExpandedProbeInfo basicInfo;
        if (!tile.isValid || tile.isDynamic()) {
            long time = tile.clockTime(512);
            info.progress(time, 512L, info.defaultProgressStyle().width(120).showText(true).numberFormat(NumberFormat.NONE).prefix("Next Reform: " + (512L - time)).suffix(" Ticks"));
        }
        if (tile instanceof TankTileEntity) {
            Panel tank = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
            IExpandedProbeInfo basicInfo2 = tank.vertical(IC2Styles.INNER_STYLE);
            IExpandedProbeInfo bars = basicInfo2.vertical();
            ProbePluginHelper.addTanks(tile, bars, true);
            ProbePluginHelper.generateDefaultSlots(tile, false, tank);
            info.getElements().add(1, tank);
        }
        if (tile instanceof ElectricFisherTileEntity) {
            ElectricFisherTileEntity fisher = (ElectricFisherTileEntity)tile;
            Panel machine = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
            basicInfo = machine.vertical(IC2Styles.INNER_STYLE);
            basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(fisher.getSinkTier()));
            basicInfo.text("ic2.probe.eu.max_in.name", fisher.getMaxInput());
            basicInfo.text("ic2.probe.eu.usage.name", 150);
            IExpandedProbeInfo bars = basicInfo.vertical().element(ProbePluginHelper.generateHiddenBar(tile));
            bars.progress((int)fisher.getProgress(), (int)fisher.getMaxProgress(), IC2Styles.progressBar((int)fisher.getProgress(), (int)fisher.getMaxProgress()));
            info.getElements().add(1, machine);
        }
        if (tile instanceof SteamTunnelTileEntity) {
            SteamTunnelTileEntity tunnel = (SteamTunnelTileEntity)tile;
            Panel generator = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
            basicInfo = generator.vertical(IC2Styles.INNER_STYLE);
            basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(tunnel.getSourceTier()));
            basicInfo.text("ic2.probe.eu.output.current.name", ProbePluginHelper.formatNumber(tunnel.getEUProduction(), 5));
            basicInfo.text("ic2.probe.eu.output.max.name", tunnel.getMaxEnergyOutput());
            ProbePluginHelper.addTanks(tile, generator.vertical(IC2Styles.BARS_STYLE), true);
            info.getElements().add(1, generator);
        }
    }
}

