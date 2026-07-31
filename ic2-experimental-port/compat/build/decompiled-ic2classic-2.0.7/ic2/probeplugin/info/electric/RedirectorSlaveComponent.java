/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.probeplugin.info.electric;

import ic2.api.util.DirectionList;
import ic2.core.block.storage.tiles.RedirectorMasterTileEntity;
import ic2.core.block.storage.tiles.RedirectorSlaveTileEntity;
import ic2.core.utils.helpers.Formatters;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.info.transport.CableProvider;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RedirectorSlaveComponent
implements ITileInfoComponent<RedirectorSlaveTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, RedirectorSlaveTileEntity tile) {
        CableProvider.EnergyContainer result;
        Panel generator = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = generator.vertical(IC2Styles.INNER_STYLE);
        BlockEntity master = DirectionList.getNeighborTile(tile, tile.getFacing());
        if (master instanceof RedirectorMasterTileEntity) {
            basicInfo.text("ic2.probe.redirector.slave.info", ((RedirectorMasterTileEntity)master).shares[tile.getFacing().m_122424_().m_122411_()]);
        }
        if ((result = CableProvider.getContainer(tile)).getAverageOut() > 0) {
            basicInfo.padding(0, 5);
            basicInfo.text((Component)this.translate("tooltip.item.ic2.eu_reader.cable_flow", Formatters.EU_FORMAT.format(result.getAverageOut())).m_130940_(ChatFormatting.AQUA));
            basicInfo.text((Component)this.translate("tooltip.item.ic2.eu_reader.packet_flow", Formatters.EU_FORMAT.format(result.getPacketsOut())).m_130940_(ChatFormatting.AQUA));
        }
        info.getElements().add(1, generator);
    }
}

