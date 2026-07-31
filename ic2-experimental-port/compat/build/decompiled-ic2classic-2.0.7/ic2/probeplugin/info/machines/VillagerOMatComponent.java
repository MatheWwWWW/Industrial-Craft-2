/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info.machines;

import ic2.api.energy.EnergyNet;
import ic2.core.block.machines.tiles.hv.VillagerOMatTileEntity;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class VillagerOMatComponent
implements ITileInfoComponent<VillagerOMatTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, VillagerOMatTileEntity tile) {
        Panel machine = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = machine.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(tile.getTier()));
        basicInfo.text("ic2.probe.eu.max_in.name", tile.getMaxInput());
        basicInfo.text("ic2.probe.villager_o_mat.usage", tile.trades.getActiveTrades() * 6000);
        IExpandedProbeInfo bars = basicInfo.vertical().element(ProbePluginHelper.generateHiddenBar(tile));
        bars.progress(1200 - (int)tile.clockTime(1200), 1200, IC2Styles.PROGRESS_BAR.copy().prefix((Component)this.translate("ic2.probe.villager_o_mat.next", tile.clockTime(1200))));
        info.getElements().add(1, machine);
    }
}

