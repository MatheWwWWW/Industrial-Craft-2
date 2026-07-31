/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info.generators;

import ic2.api.energy.EnergyNet;
import ic2.core.block.base.tiles.impls.BaseGeneratorTileEntity;
import ic2.core.block.generators.tiles.FuelGenTileEntity;
import ic2.core.block.generators.tiles.SlagGenTileEntity;
import ic2.core.block.generators.tiles.SolarTurbineTileEntity;
import ic2.core.block.generators.tiles.ThermalGeneratorTileEntity;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;

public class GeneratorComponent
implements ITileInfoComponent<BaseGeneratorTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, BaseGeneratorTileEntity tile) {
        Panel generator = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = generator.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(tile.getSourceTier()));
        basicInfo.text("ic2.probe.eu.output.current.name", ProbePluginHelper.formatNumber(tile.getEUProduction(), 5));
        basicInfo.text("ic2.probe.eu.output.max.name", tile.getMaxEnergyOutput());
        if (tile instanceof SolarTurbineTileEntity) {
            basicInfo.text("ic2.probe.heat.name", ProbePluginHelper.THERMAL_GEN.format((float)((SolarTurbineTileEntity)tile).heat / 240.0f));
        } else if (tile instanceof ThermalGeneratorTileEntity) {
            basicInfo.text("ic2.probe.production.passive.name", ProbePluginHelper.THERMAL_GEN.format(((ThermalGeneratorTileEntity)tile).subProduction.getProduction(2000.0f)));
        }
        IExpandedProbeInfo bars = basicInfo.vertical(IC2Styles.BARS_STYLE);
        if ((tile instanceof SlagGenTileEntity || tile instanceof FuelGenTileEntity) && tile.getFuel() > 0) {
            bars.progress(tile.getFuel(), tile.getMaxFuel(), IC2Styles.FUEL_BAR);
        }
        ProbePluginHelper.addTanks(tile, bars.element(ProbePluginHelper.generateEUBar(tile)), true);
        ProbePluginHelper.generateDefaultSlots(tile, false, generator);
        info.getElements().add(1, generator);
    }
}

