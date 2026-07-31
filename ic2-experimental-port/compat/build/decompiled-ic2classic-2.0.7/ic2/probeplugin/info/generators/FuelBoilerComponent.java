/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info.generators;

import ic2.core.block.generators.tiles.FuelBoilerTileEntity;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;

public class FuelBoilerComponent
implements ITileInfoComponent<FuelBoilerTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, FuelBoilerTileEntity tile) {
        Panel generator = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = generator.vertical(IC2Styles.INNER_STYLE);
        IExpandedProbeInfo bars = basicInfo.vertical(IC2Styles.BARS_STYLE);
        bars.progress(tile.getFuel(), tile.getMaxFuel(), IC2Styles.FUEL_BAR);
        bars.progress(tile.heat, 24000, IC2Styles.reactorBar(tile.heat / 30, 800));
        ProbePluginHelper.addTanks(tile, bars, true);
        ProbePluginHelper.generateDefaultSlots(tile, false, generator);
        info.getElements().add(1, generator);
    }
}

