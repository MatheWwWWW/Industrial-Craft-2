/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.apiimpl.ProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info.generators;

import ic2.api.energy.EnergyNet;
import ic2.core.block.generators.tiles.WaveGenTileEntity;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.apiimpl.ProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;

public class WaveGenComponent
implements ITileInfoComponent<WaveGenTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, WaveGenTileEntity tile) {
        ProbeInfo probeInfo = (ProbeInfo)info;
        Panel generator = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = generator.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(tile.getSourceTier()));
        basicInfo.text("ic2.probe.eu.output.current.name", ProbePluginHelper.formatNumber(tile.getEUProduction(), 5));
        basicInfo.text("ic2.probe.eu.output.max.name", tile.getMaxEnergyOutput());
        probeInfo.getElements().add(1, generator);
    }
}

