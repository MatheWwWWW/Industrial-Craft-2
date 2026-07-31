/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntIterator
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.apiimpl.ProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info.machines;

import ic2.api.energy.EnergyNet;
import ic2.core.block.base.tiles.impls.machine.multi.BaseAdvMultiMachineTileEntity;
import ic2.core.block.base.tiles.impls.machine.multi.BaseColossalMachineTileEntity;
import ic2.core.block.base.tiles.impls.machine.multi.BaseMultiMachineTileEntity;
import ic2.core.block.base.tiles.impls.machine.multi.BasicMultiMachineTileEntity;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntIterator;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.apiimpl.ProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class BaseMultiBlockMachineComponent
implements ITileInfoComponent<BaseMultiMachineTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, BaseMultiMachineTileEntity tile) {
        BaseAdvMultiMachineTileEntity adv;
        ProbeInfo probeInfo = (ProbeInfo)info;
        Panel machine = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = machine.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(tile.getTier()));
        basicInfo.text("ic2.probe.eu.max_in.name", tile.getMaxInput());
        basicInfo.text("ic2.probe.eu.usage.name", tile.getEnergyPerTick());
        IExpandedProbeInfo bars = basicInfo.vertical().element(ProbePluginHelper.generateHiddenBar(tile));
        if (tile instanceof BaseAdvMultiMachineTileEntity && (adv = (BaseAdvMultiMachineTileEntity)tile).getSpeed() > 0) {
            bars.progress(adv.getSpeed(), adv.getMaxSpeed(), IC2Styles.SPEED_BAR.copy().prefix((Component)adv.getSpeedName().m_6881_().m_130946_(": " + ProbePluginHelper.THERMAL_GEN.format((double)adv.getSpeed() / (double)adv.getMaxSpeed() * 100.0) + "%")));
        }
        if (tile instanceof BasicMultiMachineTileEntity) {
            BasicMultiMachineTileEntity machineTile = (BasicMultiMachineTileEntity)tile;
            if (tile.isMachineWorking() || machineTile.getProgress() > 0.0f) {
                bars.progress((int)machineTile.getProgress(), (int)machineTile.getMaxProgress(), IC2Styles.progressBar((int)Math.min(6.0E7f, machineTile.getProgress() / tile.progressPerTick), (int)Math.min(6.0E7f, machineTile.getMaxProgress() / tile.progressPerTick)));
            }
        }
        if (tile instanceof BaseColossalMachineTileEntity) {
            BaseColossalMachineTileEntity colossal = (BaseColossalMachineTileEntity)tile;
            IntArrayList maxProgressFilter = new IntArrayList();
            IntArrayList progressFilter = new IntArrayList();
            IntIterator iter = colossal.getActiveSlots();
            while (iter.hasNext()) {
                int index;
                int slot = iter.nextInt();
                int progress = (int)colossal.getProgress(slot);
                int maxProgress = (int)colossal.getMaxProgress(slot);
                int maxIndex = maxProgressFilter.indexOf(maxProgress);
                if (maxIndex == (index = progressFilter.indexOf(progress)) && index != -1) continue;
                maxProgressFilter.add(maxProgress);
                progressFilter.add(progress);
            }
            for (int i = 0; i < progressFilter.size(); ++i) {
                bars.progress(progressFilter.getInt(i), maxProgressFilter.getInt(i), IC2Styles.progressBar((int)Math.min(6.0E7f, (float)progressFilter.getInt(i) / tile.progressPerTick), (int)Math.min(6.0E7f, (float)maxProgressFilter.getInt(i) / tile.progressPerTick)));
            }
        }
        ProbePluginHelper.addTanks(tile, bars, true);
        ProbePluginHelper.generateDefaultSlots(tile, false, machine);
        probeInfo.getElements().add(1, machine);
    }
}

