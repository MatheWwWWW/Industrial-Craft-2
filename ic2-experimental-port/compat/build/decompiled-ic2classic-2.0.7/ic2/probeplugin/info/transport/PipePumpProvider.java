/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info.transport;

import ic2.api.energy.EnergyNet;
import ic2.core.block.transport.fluid.tiles.ElectricPipePumpTileEntity;
import ic2.core.block.transport.fluid.tiles.SimplePipePumpTileEntity;
import ic2.core.utils.helpers.Formatters;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class PipePumpProvider
implements ITileInfoComponent<SimplePipePumpTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, SimplePipePumpTileEntity tile) {
        Panel electric = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = electric.vertical(IC2Styles.INNER_STYLE);
        if (tile instanceof ElectricPipePumpTileEntity) {
            ElectricPipePumpTileEntity pump = (ElectricPipePumpTileEntity)tile;
            basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(pump.getTier()));
            basicInfo.text("ic2.probe.eu.max_in.name", EnergyNet.INSTANCE.getPowerFromTier(pump.getTier()));
            electric.vertical().element(ProbePluginHelper.generateHiddenBar(tile));
        }
        basicInfo.text((Component)this.translate("ic2.probe.pump.pressure", tile.getPressure()));
        basicInfo.text((Component)this.translate("ic2.probe.pump.amount", Formatters.EU_FORMAT.format(tile.getDrainAmount() / 20)));
        info.getElements().add(1, electric);
    }
}

