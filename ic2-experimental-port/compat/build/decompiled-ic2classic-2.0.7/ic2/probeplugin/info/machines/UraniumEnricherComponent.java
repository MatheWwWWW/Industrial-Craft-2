/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.apiimpl.ProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info.machines;

import ic2.api.energy.EnergyNet;
import ic2.core.block.machines.recipes.misc.EnrichRecipe;
import ic2.core.block.machines.tiles.hv.UraniumEnchricherTileEntity;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.apiimpl.ProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;

public class UraniumEnricherComponent
implements ITileInfoComponent<UraniumEnchricherTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, UraniumEnchricherTileEntity tile) {
        ProbeInfo probeInfo = (ProbeInfo)info;
        Panel machine = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = machine.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(tile.getTier()));
        basicInfo.text("ic2.probe.eu.max_in.name", tile.getMaxInput());
        EnrichRecipe recipe = tile.getStoredRecipe();
        basicInfo.text("ic2.probe.eu.usage.name", recipe != null ? recipe.getEnergyCost() + 100 : 100);
        IExpandedProbeInfo bars = basicInfo.vertical().element(ProbePluginHelper.generateEUBar(tile, 140));
        if (tile.isActive() || tile.mainProgress > 0 || tile.secondaryProgress > 0) {
            if (tile.isActive() || tile.mainProgress > 0) {
                bars.progress(tile.mainProgress, 1000, IC2Styles.progressBar(tile.mainProgress, 1000).width(140));
            }
            if (tile.isActive() || tile.secondaryProgress > 0) {
                bars.progress(tile.secondaryProgress, 100, IC2Styles.secondaryProgressBar(tile.secondaryProgress, 100, "t").width(140));
            }
        }
        bars.progress(tile.storedPoints, 1000, IC2Styles.enricher(recipe == null ? -1 : recipe.getColor(), recipe == null ? "Empty" : recipe.getId().m_135815_(), tile.storedPoints).width(140));
        ProbePluginHelper.generateDefaultSlots(tile, false, machine);
        probeInfo.getElements().add(1, machine);
    }
}

