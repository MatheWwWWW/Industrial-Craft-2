/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.apiimpl.ProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info.machines;

import ic2.api.energy.EnergyNet;
import ic2.core.block.machines.tiles.lv.WoodGassifierTileEntity;
import ic2.core.utils.helpers.Formatters;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.apiimpl.ProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ElectricWoodGassifierComponent
implements ITileInfoComponent<WoodGassifierTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, WoodGassifierTileEntity tile) {
        ProbeInfo probeInfo = (ProbeInfo)info;
        Panel machine = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = machine.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(tile.getTier()));
        basicInfo.text("ic2.probe.eu.max_in.name", tile.getMaxInput());
        basicInfo.text("ic2.probe.eu.usage.name", 1);
        basicInfo.text((Component)this.translate("ic2.probe.pump.pressure", 25));
        basicInfo.text((Component)this.translate("ic2.probe.pump.amount", Formatters.EU_FORMAT.format(1800L)));
        IExpandedProbeInfo bars = basicInfo.vertical().element(ProbePluginHelper.generateHiddenBar(tile));
        if (tile.isActive() || tile.getProgress() > 0.0f) {
            bars.progress((int)tile.getProgress(), (int)tile.getMaxProgress(), IC2Styles.progressBar((int)tile.getProgress(), (int)tile.getMaxProgress()));
        }
        ProbePluginHelper.addTanks(tile, bars, true);
        ProbePluginHelper.generateDefaultSlots(tile, false, machine);
        probeInfo.getElements().add(1, machine);
    }
}

