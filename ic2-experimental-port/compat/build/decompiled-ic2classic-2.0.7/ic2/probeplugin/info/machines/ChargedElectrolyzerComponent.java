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

import ic2.core.block.machines.tiles.mv.ChargedElectrolyzerTileEntity;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.apiimpl.ProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;

public class ChargedElectrolyzerComponent
implements ITileInfoComponent<ChargedElectrolyzerTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, ChargedElectrolyzerTileEntity tile) {
        ProbeInfo probeInfo = (ProbeInfo)info;
        Panel generator = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = generator.vertical(IC2Styles.INNER_STYLE);
        boolean discharging = tile.canPower();
        boolean charging = tile.shouldDrain();
        basicInfo.text("ic2.probe.electrolyzer.transferrate.name", tile.getTransferrate());
        basicInfo.text("ic2.probe.electrolyzer." + (discharging ? (charging ? "transfer" : "discharging") : (charging ? "charging" : "nothing")) + ".name");
        IExpandedProbeInfo bars = basicInfo.vertical(IC2Styles.BARS_STYLE);
        if (tile.energy > 0) {
            bars.progress(tile.energy, tile.maxEnergy, IC2Styles.progressBar(tile.energy, tile.maxEnergy, "", " EU").width(120));
        }
        ProbePluginHelper.generateDefaultSlots(tile, false, generator);
        probeInfo.getElements().add(1, generator);
    }
}

