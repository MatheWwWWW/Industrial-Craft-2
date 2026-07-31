/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.apiimpl.ProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.probeplugin.info.machines;

import ic2.api.energy.EnergyNet;
import ic2.core.block.machines.tiles.lv.PumpTileEntity;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class PumpComponent
implements ITileInfoComponent<PumpTileEntity> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, PumpTileEntity tile) {
        ProbeInfo probeInfo = (ProbeInfo)info;
        Panel machine = new Panel(IC2Styles.OUTER_STYLE, Panel.Type.VERTICAL);
        IExpandedProbeInfo basicInfo = machine.vertical(IC2Styles.INNER_STYLE);
        basicInfo.text("ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(tile.getTier()));
        basicInfo.text("ic2.probe.eu.max_in.name", tile.getMaxInput());
        basicInfo.text("ic2.probe.eu.usage.name", tile.getPumpCost());
        basicInfo.text((Component)this.translate("ic2.probe.pump.pressure", 100));
        basicInfo.text((Component)this.translate("ic2.probe.pump.amount", Formatters.EU_FORMAT.format(800L)));
        IExpandedProbeInfo bars = basicInfo.vertical().element(ProbePluginHelper.generateHiddenBar(tile));
        if (tile.isActive() || tile.getPumpProgress() > 0) {
            bars.progress(tile.getPumpProgress(), tile.getPumpMaxProgress(), IC2Styles.progressBar(Math.min(60000000, tile.getPumpProgress()), Math.min(60000000, tile.getPumpMaxProgress())));
        }
        ProbePluginHelper.addTanks((IFluidHandler)tile.tank, bars, true);
        FluidStack stack = tile.getFluidBlockBelow(false);
        if (!stack.isEmpty()) {
            machine.text("ic2.probe.pump.fluid.name", stack.getDisplayName());
        }
        ProbePluginHelper.generateDefaultSlots(tile, false, machine);
        probeInfo.getElements().add(1, machine);
    }
}

