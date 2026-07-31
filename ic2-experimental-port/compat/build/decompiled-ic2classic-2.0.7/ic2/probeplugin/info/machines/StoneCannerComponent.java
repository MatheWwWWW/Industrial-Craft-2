/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info.machines;

import ic2.core.block.machines.tiles.nv.StoneCannerTileEntity;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;

public class StoneCannerComponent
implements ITileInfoComponent<StoneCannerTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, StoneCannerTileEntity tile) {
        Panel machine = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = machine.vertical(IC2Styles.INNER_STYLE);
        IExpandedProbeInfo bars = basicInfo.vertical().progress(tile.getFuel(), tile.getMaxFuel(), IC2Styles.FUEL_BAR);
        if (tile.isActive() || tile.getProgress() > 0.0f) {
            bars.progress((int)tile.getProgress(), (int)tile.getMaxProgress(), IC2Styles.progressBar((int)tile.getProgress(), (int)tile.getMaxProgress()));
        }
        ProbePluginHelper.generateDefaultSlots(tile, false, machine);
        info.getElements().add(1, machine);
    }
}

