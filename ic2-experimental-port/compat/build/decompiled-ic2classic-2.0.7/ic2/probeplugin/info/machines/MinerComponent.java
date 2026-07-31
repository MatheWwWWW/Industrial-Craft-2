/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.apiimpl.ProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info.machines;

import ic2.api.energy.EnergyNet;
import ic2.core.block.machines.tiles.hv.RocketMinerTileEntity;
import ic2.core.block.machines.tiles.lv.MinerTileEntity;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.apiimpl.ProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;

public class MinerComponent
implements ITileInfoComponent<MinerTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, MinerTileEntity tile) {
        ProbeInfo probeInfo = (ProbeInfo)info;
        Panel machine = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = machine.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(tile.getTier()));
        basicInfo.text("ic2.probe.eu.max_in.name", tile.getMaxInput());
        basicInfo.text("ic2.probe.eu.usage.name", tile.getEnergyUsage());
        if (tile instanceof RocketMinerTileEntity) {
            RocketMinerTileEntity miner = (RocketMinerTileEntity)tile;
            switch (miner.finished) {
                case 0: {
                    basicInfo.text(miner.isRefueling() ? "ic2.probe.miner.refuel.name" : "ic2.probe.miner.mining.name");
                    break;
                }
                case 1: {
                    basicInfo.text("ic2.probe.miner.retracting.name");
                    break;
                }
                case 3: {
                    basicInfo.text("ic2.probe.miner.power.name");
                }
            }
        } else {
            basicInfo.text(tile.isStuck() ? "ic2.probe.miner.stuck.name" : (tile.isOperating() ? "ic2.probe.miner.mining.name" : "ic2.probe.miner.retracting.name"));
        }
        IExpandedProbeInfo bars = basicInfo.vertical().element(ProbePluginHelper.generateHiddenBar(tile));
        if (!tile.isStuck()) {
            bars.progress((int)tile.getProgress(), (int)tile.getMaxProgress(), IC2Styles.progressBar((int)Math.min(6.0E7f, tile.getProgress()), (int)Math.min(6.0E7f, tile.getMaxProgress())));
        }
        int y = tile.getPipeTip().m_123342_();
        bars.progress(y, tile.getPosition().m_123342_(), IC2Styles.SPEED_BAR.copy().prefix("ic2.probe.miner.progress.name", new Object[]{y}));
        ProbePluginHelper.generateAnySlots(tile, false, machine);
        probeInfo.getElements().add(1, machine);
    }
}

