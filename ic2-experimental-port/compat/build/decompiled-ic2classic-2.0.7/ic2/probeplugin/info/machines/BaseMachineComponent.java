/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.probeplugin.info.machines;

import ic2.api.energy.EnergyNet;
import ic2.core.block.base.tiles.impls.machine.single.BaseAdvMachineTileEntity;
import ic2.core.block.base.tiles.impls.machine.single.BaseMachineTileEntity;
import ic2.core.block.machines.tiles.lv.RareEarthExtractorTileEntity;
import ic2.core.block.machines.tiles.mv.RareEarthCentrifugeTileEntity;
import ic2.core.block.machines.tiles.mv.RefineryTileEntity;
import ic2.core.block.machines.tiles.mv.SlowGrinderTileEntity;
import ic2.core.block.machines.tiles.mv.VacuumCannerTileEntity;
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
import net.minecraft.world.item.ItemStack;

public class BaseMachineComponent
implements ITileInfoComponent<BaseMachineTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, BaseMachineTileEntity tile) {
        VacuumCannerTileEntity vac;
        BaseAdvMachineTileEntity adv;
        Panel machine = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = machine.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(tile.getTier()));
        basicInfo.text("ic2.probe.eu.max_in.name", tile.getMaxInput());
        basicInfo.text("ic2.probe.eu.usage.name", tile.getEnergyPerTick());
        if (tile instanceof SlowGrinderTileEntity) {
            basicInfo.text("ic2.probe.scrap.chance.name", Formatters.XP_FORMAT.format(((SlowGrinderTileEntity)tile).getChance(0.25f) * 100.0f));
        }
        IExpandedProbeInfo bars = basicInfo.vertical().element(ProbePluginHelper.generateHiddenBar(tile));
        if (tile instanceof BaseAdvMachineTileEntity && (adv = (BaseAdvMachineTileEntity)tile).getSpeed() > 0) {
            bars.progress(adv.getSpeed(), adv.getMaxSpeed(), IC2Styles.SPEED_BAR.copy().prefix((Component)adv.getSpeedName().m_6881_().m_130946_(": " + ProbePluginHelper.THERMAL_GEN.format((double)adv.getSpeed() / (double)adv.getMaxSpeed() * 100.0) + "%")));
        }
        if (tile instanceof VacuumCannerTileEntity && (vac = (VacuumCannerTileEntity)tile).getSpeed() > 0) {
            bars.progress(vac.getSpeed(), vac.getMaxSpeed(), IC2Styles.SPEED_BAR.copy().prefix((Component)vac.getSpeedName().m_6881_().m_130946_(": " + ProbePluginHelper.THERMAL_GEN.format((double)vac.getSpeed() / (double)vac.getMaxSpeed() * 100.0) + "%")));
        }
        if (tile instanceof RareEarthExtractorTileEntity) {
            extractor = (RareEarthExtractorTileEntity)tile;
            ItemStack output = ((RareEarthExtractorTileEntity)extractor).getCurrentOutput();
            if (!output.m_41619_() && ((RareEarthExtractorTileEntity)extractor).materialProgress > 0.0f) {
                bars.progress((int)((RareEarthExtractorTileEntity)extractor).materialProgress, 1000, IC2Styles.MATERIAL_BAR.copy().prefix("ic2.probe.progress.material.name", new Object[]{Formatters.EU_READER_FORMAT.format(((RareEarthExtractorTileEntity)extractor).materialProgress)}));
            }
        } else if (tile instanceof RareEarthCentrifugeTileEntity) {
            extractor = (RareEarthCentrifugeTileEntity)tile;
            ItemStack output = ((RareEarthCentrifugeTileEntity)extractor).getCurrentOutput();
            if (!output.m_41619_() && ((RareEarthCentrifugeTileEntity)extractor).materialProgress > 0.0f) {
                bars.progress((int)((RareEarthCentrifugeTileEntity)extractor).materialProgress, 1000, IC2Styles.MATERIAL_BAR.copy().prefix("ic2.probe.progress.material.name", new Object[]{Formatters.EU_READER_FORMAT.format(((RareEarthCentrifugeTileEntity)extractor).materialProgress)}));
            }
            if (((RareEarthCentrifugeTileEntity)extractor).getSpeed() > 0) {
                bars.progress(((RareEarthCentrifugeTileEntity)extractor).getSpeed(), ((RareEarthCentrifugeTileEntity)extractor).getMaxSpeed(), IC2Styles.SPEED_BAR.copy().prefix((Component)((RareEarthCentrifugeTileEntity)extractor).getSpeedName().m_6881_().m_130946_(": " + ProbePluginHelper.THERMAL_GEN.format((double)((RareEarthCentrifugeTileEntity)extractor).getSpeed() / (double)((RareEarthCentrifugeTileEntity)extractor).getMaxSpeed() * 100.0) + "%")));
            }
        }
        if (tile instanceof RefineryTileEntity) {
            ProbePluginHelper.addTanks(tile, bars, true);
        }
        if (tile.isMachineWorking() || tile.getProgress() > 0.0f) {
            bars.progress((int)tile.getProgress(), (int)tile.getMaxProgress(), IC2Styles.progressBar((int)Math.min(6.0E7f, tile.getProgress() / tile.progressPerTick), (int)Math.min(6.0E7f, tile.getMaxProgress() / tile.progressPerTick)));
        }
        ProbePluginHelper.generateDefaultSlots(tile, false, machine);
        info.getElements().add(1, machine);
    }
}

