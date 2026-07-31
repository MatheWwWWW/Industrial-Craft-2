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
import ic2.core.block.machines.tiles.hv.ElectricEnchanterTileEntity;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.apiimpl.ProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;

public class ElectricEnchanterComponent
implements ITileInfoComponent<ElectricEnchanterTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, ElectricEnchanterTileEntity tile) {
        ProbeInfo probeInfo = (ProbeInfo)info;
        Panel machine = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = machine.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(tile.getTier()));
        basicInfo.text("ic2.probe.eu.max_in.name", tile.getMaxInput());
        basicInfo.text("ic2.probe.eu.usage.name", 500);
        IExpandedProbeInfo bars = basicInfo.vertical().element(ProbePluginHelper.generateHiddenBar(tile));
        if (tile.isActive() || tile.getProgress() > 0.0f) {
            bars.progress((int)tile.getProgress(), (int)tile.getMaxProgress(), IC2Styles.progressBar((int)Math.min(6.0E7f, tile.getProgress()), (int)Math.min(6.0E7f, tile.getMaxProgress())));
        }
        bars.progress(tile.storedExperience, 1000, IC2Styles.XP_BAR);
        ProbePluginHelper.generateAnySlots(tile, false, machine);
        probeInfo.getElements().add(1, machine);
    }
}

