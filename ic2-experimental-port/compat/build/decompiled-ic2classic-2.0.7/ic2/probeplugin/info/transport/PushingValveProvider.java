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

import ic2.core.block.storage.tiles.tank.PushingValveTileEntity;
import ic2.core.utils.helpers.Formatters;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class PushingValveProvider
implements ITileInfoComponent<PushingValveTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, PushingValveTileEntity tile) {
        Panel electric = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = electric.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text((Component)this.translate("ic2.probe.pump.pressure", 100));
        basicInfo.text((Component)this.translate("ic2.probe.pump.amount", Formatters.EU_FORMAT.format(2000L)));
        info.getElements().add(1, electric);
    }
}

