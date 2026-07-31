/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.apiimpl.ProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.probeplugin.info.generators;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.ISteamReactor;
import ic2.core.block.generators.tiles.ElectricNuclearReactorTileEntity;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.utils.helpers.Formatters;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.apiimpl.ProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class NuclearReactorComponent
implements ITileInfoComponent<IReactor> {
    public static final NuclearReactorComponent INSTANCE = new NuclearReactorComponent();

    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, IReactor tile) {
        ProbeInfo probeInfo = (ProbeInfo)info;
        Panel generator = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = generator.vertical(IC2Styles.INNER_STYLE);
        if (tile instanceof ElectricNuclearReactorTileEntity) {
            ElectricNuclearReactorTileEntity electric = (ElectricNuclearReactorTileEntity)tile;
            basicInfo.text("ic2.probe.eu.output.current.name", ProbePluginHelper.formatNumber(electric.getProvidedEnergy(), 5));
            basicInfo.text("ic2.probe.reactor.breeding.name", tile.getHeat() / 3000 + 1);
        } else if (tile instanceof ISteamReactor) {
            basicInfo.text("ic2.probe.steam.output.name", ProbePluginHelper.THERMAL_GEN.format(tile.getEnergyOutput() * (double)3.2f));
            basicInfo.text("ic2.probe.water.consumption.name", ProbePluginHelper.THERMAL_GEN.format(tile.getEnergyOutput() / 50.0));
            basicInfo.text((Component)this.translate("ic2.probe.pump.pressure", 100));
            basicInfo.text((Component)this.translate("ic2.probe.pump.amount", Formatters.EU_FORMAT.format(20000L)));
        }
        IExpandedProbeInfo bars = basicInfo.vertical(IC2Styles.BARS_STYLE);
        if (PlayerHandler.getHandler(player).hasThermometer()) {
            bars.progress(tile.getHeat(), tile.getMaxHeat(), IC2Styles.reactorBar(tile.getHeat(), tile.getMaxHeat()));
        }
        ProbePluginHelper.addTanks((BlockEntity)tile, bars, true);
        ProbePluginHelper.generateDefaultSlots((BlockEntity)tile, false, generator);
        probeInfo.getElements().add(1, generator);
    }
}

