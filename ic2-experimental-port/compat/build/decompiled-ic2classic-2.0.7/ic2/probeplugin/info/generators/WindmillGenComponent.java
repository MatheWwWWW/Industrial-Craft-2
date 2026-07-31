/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info.generators;

import ic2.api.energy.EnergyNet;
import ic2.core.block.generators.tiles.WindmillTileEntity;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;

public class WindmillGenComponent
implements ITileInfoComponent<WindmillTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, WindmillTileEntity tile) {
        Panel generator = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = generator.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(tile.getSourceTier()));
        basicInfo.text("ic2.probe.eu.output.current.name", ProbePluginHelper.formatNumber(tile.getEUProduction(), 5));
        basicInfo.text("ic2.probe.eu.output.max.name", tile.getMaxEnergyOutput());
        ProbePluginHelper.generateDefaultSlots(tile, false, generator);
        info.getElements().add(1, generator);
    }
}

