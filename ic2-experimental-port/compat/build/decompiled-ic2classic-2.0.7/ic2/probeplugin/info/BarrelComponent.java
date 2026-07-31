/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.ElementAlignment
 *  mcjty.theoneprobe.api.IElement
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.api.NumberFormat
 *  mcjty.theoneprobe.apiimpl.ProbeInfo
 *  mcjty.theoneprobe.apiimpl.elements.ElementTank
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info;

import ic2.core.block.misc.tiles.BarrelTileEntity;
import ic2.core.platform.player.PlayerHandler;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.override.styles.ILayoutStyleBuilder;
import ic2.probeplugin.styles.IC2Styles;
import java.util.List;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.apiimpl.ProbeInfo;
import mcjty.theoneprobe.apiimpl.elements.ElementTank;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class BarrelComponent
implements ITileInfoComponent<BarrelTileEntity> {
    @Override
    public boolean hasValidReader(PlayerHandler handler) {
        return handler.hasThermometer();
    }

    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, BarrelTileEntity tile) {
        ProbeInfo probeInfo = (ProbeInfo)info;
        Panel barrel = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = barrel.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text(tile.getBrewType());
        switch (tile.brewType) {
            case 0: {
                break;
            }
            case 1: {
                IExpandedProbeInfo storage = barrel.vertical(ILayoutStyleBuilder.spacing(1).alignment(ElementAlignment.ALIGN_CENTER));
                IExpandedProbeInfo storage_bars = storage.text("ic2.probe.barrel.status.storage.name").vertical(IC2Styles.BARS_STYLE);
                storage_bars.progress(tile.wheatAmount, 64, IC2Styles.WHEAT_BAR.copy().prefix("ic2.probe.barrel.beer.wheat.name", new Object[]{tile.wheatAmount}).width(140));
                storage_bars.progress(tile.hopsAmount, 64, IC2Styles.HOPS_BAR.copy().prefix("ic2.probe.barrel.beer.hops.name", new Object[]{tile.hopsAmount}).width(140));
                ProbePluginHelper.addTanks(tile.getBeerStoredFluid(), storage_bars, true);
                List<IElement> el = storage_bars.getChildren();
                ((ElementTank)el.get(el.size() - 1)).getStyle().width(140);
                IExpandedProbeInfo brew = barrel.vertical(ILayoutStyleBuilder.spacing(1).alignment(ElementAlignment.ALIGN_CENTER));
                IExpandedProbeInfo brew_bars = brew.text("ic2.probe.barrel.status.brew.name").vertical(IC2Styles.BARS_STYLE);
                brew_bars.progress(tile.beerQuality, 5, IC2Styles.PROGRESS_BAR.copy().suffix((Component)Component.m_237119_()).prefix("ic2.probe.barrel.beer.quality." + tile.beerQuality + ".name").width(140));
                brew_bars.progress(tile.getAlcoholLevel(), 6, IC2Styles.HOPS_BAR.copy().prefix("ic2.probe.barrel.beer.alc." + tile.getAlcoholLevel() + ".name").width(140));
                brew_bars.progress(tile.getSolidRatio(), 6, IC2Styles.WHEAT_BAR.copy().prefix("ic2.probe.barrel.beer.solid." + tile.getSolidRatio() + ".name").width(140));
                double max = 24000.0 * Math.pow(3.0, tile.beerQuality == 4 ? 6.0 : (double)tile.beerQuality);
                brew_bars.progress(tile.age, (int)max, IC2Styles.PROGRESS_BAR.copy().prefix(ProbePluginHelper.THERMAL_GEN.format((double)tile.age / max * 100.0) + "% ").width(140));
                break;
            }
            case 2: {
                IExpandedProbeInfo storage = barrel.vertical(ILayoutStyleBuilder.spacing(1).alignment(ElementAlignment.ALIGN_CENTER));
                IExpandedProbeInfo storage_bars = storage.text("ic2.probe.barrel.status.brew.name").vertical(IC2Styles.BARS_STYLE);
                storage_bars.progress(tile.fluidAmount / 1000, 32, IC2Styles.HOPS_BAR.copy().prefix("ic2.probe.barrel.beer.sugar_cane.name", new Object[]{tile.fluidAmount / 1000}));
                int max = tile.timeNeededForRum();
                storage_bars.progress(Math.min(tile.age, max), max, IC2Styles.PROGRESS_BAR.copy().prefix(ProbePluginHelper.THERMAL_GEN.format((double)Math.min(tile.age, max) * 100.0 / (double)max) + "% "));
                break;
            }
            case 5: {
                IExpandedProbeInfo storage = barrel.vertical(ILayoutStyleBuilder.spacing(1).alignment(ElementAlignment.ALIGN_CENTER));
                IExpandedProbeInfo storage_bars = storage.text("ic2.probe.barrel.status.storage.name").vertical(IC2Styles.BARS_STYLE);
                storage_bars.progress(tile.hopsAmount, 16, IC2Styles.HOPS_BAR.copy().prefix("ic2.probe.barrel.whisky.grist.name", new Object[]{tile.hopsAmount}).width(140));
                ProbePluginHelper.addTanks(tile.getBeerStoredFluid(), storage_bars, true);
                List<IElement> el = storage_bars.getChildren();
                ((ElementTank)el.get(el.size() - 1)).getStyle().width(140);
                IExpandedProbeInfo brew = barrel.vertical(ILayoutStyleBuilder.spacing(1).alignment(ElementAlignment.ALIGN_CENTER));
                IExpandedProbeInfo brew_bars = brew.text("ic2.probe.barrel.status.brew.name").vertical(IC2Styles.BARS_STYLE);
                brew_bars.progress(Math.min(tile.beerQuality, 50), 50, IC2Styles.PROGRESS_BAR.copy().suffix((Component)Component.m_237119_()).prefix("ic2.probe.barrel.whisky.years.name", new Object[]{tile.beerQuality}).width(140));
                brew_bars.progress(tile.age, 1728000, IC2Styles.PROGRESS_BAR.copy().prefix(ProbePluginHelper.THERMAL_GEN.format((double)tile.age / ((double)tile.getWhiskBrewTime() / 100.0)) + "% ").width(140));
                break;
            }
            case 10: {
                IExpandedProbeInfo storage = barrel.vertical(ILayoutStyleBuilder.spacing(1).alignment(ElementAlignment.ALIGN_CENTER));
                IExpandedProbeInfo storage_bars = storage.text("ic2.probe.barrel.status.storage.name").vertical(IC2Styles.BARS_STYLE);
                storage_bars.progress(tile.wheatAmount, 20, IC2Styles.EU_BAR.copy().numberFormat(NumberFormat.NONE).prefix("ic2.probe.barrel.beer.redstone.name", new Object[]{tile.wheatAmount}).width(140));
                storage_bars.progress(tile.hopsAmount, 20, IC2Styles.WHEAT_BAR.copy().prefix("ic2.probe.barrel.beer.glowstone.name", new Object[]{tile.hopsAmount}).width(140));
                ProbePluginHelper.addTanks(tile.getBeerStoredFluid(), storage_bars, true);
                List<IElement> el = storage_bars.getChildren();
                ((ElementTank)el.get(el.size() - 1)).getStyle().width(140);
                IExpandedProbeInfo brew = barrel.vertical(ILayoutStyleBuilder.spacing(1).alignment(ElementAlignment.ALIGN_CENTER));
                IExpandedProbeInfo brew_bars = brew.text("ic2.probe.barrel.status.brew.name").text("ic2.probe.barrel.status.output.name", tile.getBrewedPotion()).vertical(IC2Styles.BARS_STYLE);
                brew_bars.progress(tile.beerQuality, 6, IC2Styles.PROGRESS_BAR.copy().suffix((Component)Component.m_237119_()).prefix("ic2.probe.barrel.potion.quality." + tile.beerQuality + ".name").width(140));
                double max = 5000.0 * Math.pow(3.0, tile.beerQuality);
                brew_bars.progress(tile.age, (int)max, IC2Styles.PROGRESS_BAR.copy().prefix(ProbePluginHelper.THERMAL_GEN.format((double)tile.age / max * 100.0) + "% ").width(140));
            }
        }
        probeInfo.getElements().add(1, barrel);
    }
}

