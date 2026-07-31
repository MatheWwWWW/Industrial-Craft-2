/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.apiimpl.ProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info.electric;

import ic2.api.energy.EnergyNet;
import ic2.core.block.cables.luminator.ConstructionLightTileEntity;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.apiimpl.ProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;

public class ConstructionLightComponent
implements ITileInfoComponent<ConstructionLightTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, ConstructionLightTileEntity tile) {
        ProbeInfo probeInfo = (ProbeInfo)info;
        Panel machine = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = machine.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(tile.getTier()));
        basicInfo.text("ic2.probe.eu.max_in.name", EnergyNet.INSTANCE.getPowerFromTier(tile.getSinkTier()));
        basicInfo.text("ic2.probe.eu.usage.name", 0.1);
        basicInfo.text("ic2.probe.luminator.light.name", tile.isActive() ? 15 : 0);
        basicInfo.vertical().element(ProbePluginHelper.generateEUBar(tile));
        probeInfo.getElements().add(1, machine);
    }
}

