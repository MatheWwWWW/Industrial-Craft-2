/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.apiimpl.elements.ElementVertical
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info.electric;

import ic2.api.energy.EnergyNet;
import ic2.core.block.cables.luminator.LuminatorTileEntity;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.apiimpl.elements.ElementVertical;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;

public class LuminatorComponent
implements ITileInfoComponent<LuminatorTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, LuminatorTileEntity tile) {
        ElementVertical machine = new ElementVertical(IC2Styles.OUTER_STYLE);
        IProbeInfo basicInfo = machine.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text("ic2.probe.eu.tier.name", new Object[]{EnergyNet.INSTANCE.getDisplayTier(tile.getTier())});
        basicInfo.text("ic2.probe.eu.max_in.name", new Object[]{EnergyNet.INSTANCE.getPowerFromTier(tile.getSinkTier())});
        basicInfo.text("ic2.probe.eu.usage.name", new Object[]{0.1});
        basicInfo.text("ic2.probe.luminator.light.name", new Object[]{tile.getLightLevel()});
        basicInfo.vertical().element(ProbePluginHelper.generateEUBar(tile));
        info.getElements().add(1, machine);
    }
}

