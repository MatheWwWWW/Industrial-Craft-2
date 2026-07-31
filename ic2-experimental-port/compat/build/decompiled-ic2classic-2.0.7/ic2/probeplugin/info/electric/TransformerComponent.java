/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.apiimpl.elements.ElementVertical
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info.electric;

import ic2.core.block.base.tiles.impls.BaseTransformerTileEntity;
import ic2.core.utils.helpers.Formatters;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.info.transport.CableProvider;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.apiimpl.elements.ElementVertical;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class TransformerComponent
implements ITileInfoComponent<BaseTransformerTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, BaseTransformerTileEntity tile) {
        ElementVertical machine = new ElementVertical(IC2Styles.OUTER_STYLE);
        IProbeInfo basicInfo = machine.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text("ic2.probe.eu.max_in.name", new Object[]{tile.isActive() ? tile.lowOutput : tile.highOutput});
        basicInfo.text("ic2.probe.eu.output.max.name", new Object[]{tile.isActive() ? tile.highOutput : tile.lowOutput});
        basicInfo.text("ic2.probe.transformer.packets.name", new Object[]{tile.isActive() ? 1 : 4});
        CableProvider.EnergyContainer result = CableProvider.getContainer(tile);
        if (result.getAverageOut() > 0) {
            basicInfo.text((Component)this.translate("tooltip.item.ic2.eu_reader.cable_flow", Formatters.EU_FORMAT.format(result.getAverageOut())).m_130940_(ChatFormatting.AQUA));
            basicInfo.text((Component)this.translate("tooltip.item.ic2.eu_reader.packet_flow", Formatters.EU_FORMAT.format(result.getPacketsOut())).m_130940_(ChatFormatting.AQUA));
        }
        basicInfo.vertical(IC2Styles.BARS_STYLE).element(ProbePluginHelper.generateEUBar(tile));
        info.getElements().add(1, machine);
    }
}

