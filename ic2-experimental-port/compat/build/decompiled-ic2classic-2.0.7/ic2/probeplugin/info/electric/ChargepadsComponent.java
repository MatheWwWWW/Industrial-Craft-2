/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.apiimpl.ProbeInfo
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info.electric;

import ic2.api.energy.EnergyNet;
import ic2.core.block.base.tiles.impls.BaseChargePadTileEntity;
import ic2.core.utils.helpers.Formatters;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.info.transport.CableProvider;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.apiimpl.ProbeInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ChargepadsComponent
implements ITileInfoComponent<BaseChargePadTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, BaseChargePadTileEntity tile) {
        ProbeInfo probeInfo = (ProbeInfo)info;
        Panel main = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = main.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(tile.getSinkTier()));
        basicInfo.text("ic2.probe.eu.max_in.name", tile.maxInput);
        basicInfo.text("ic2.probe.chargepad.transferrate.name", tile.transferLimit);
        basicInfo.text("ic2.probe.chargepad.radius.name", Float.valueOf(tile.range + 1.0f));
        CableProvider.EnergyContainer result = CableProvider.getContainer(tile);
        if (result.getAverageIn() > 0) {
            basicInfo.text((Component)this.translate("tooltip.item.ic2.eu_reader.cable_flow_in", Formatters.EU_FORMAT.format(result.getAverageIn())).m_130940_(ChatFormatting.AQUA));
            basicInfo.text((Component)this.translate("tooltip.item.ic2.eu_reader.packet_flow_in", Formatters.EU_FORMAT.format(result.getPacketsIn())).m_130940_(ChatFormatting.AQUA));
        }
        basicInfo.vertical(IC2Styles.BARS_STYLE).element(ProbePluginHelper.generateEUBar(tile));
        probeInfo.getElements().add(1, main);
    }
}

