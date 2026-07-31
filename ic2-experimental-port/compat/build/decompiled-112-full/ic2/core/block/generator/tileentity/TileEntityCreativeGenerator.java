/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumFacing
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package ic2.core.block.generator.tileentity;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IMultiEnergySource;
import ic2.core.block.TileEntityBlock;
import ic2.core.profile.NotClassic;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

@NotClassic
public class TileEntityCreativeGenerator
extends TileEntityBlock
implements IMultiEnergySource {
    @Override
    public double getOfferedEnergy() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public void drawEnergy(double amount) {
    }

    @Override
    public int getSourceTier() {
        return 1;
    }

    @Override
    public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing side) {
        return true;
    }

    @Override
    public boolean sendMultipleEnergyPackets() {
        return true;
    }

    @Override
    public int getMultipleEnergyPacketAmount() {
        return 10;
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!this.func_145831_w().field_72995_K) {
            MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent(this));
        }
    }

    @Override
    protected void onUnloaded() {
        if (!this.func_145831_w().field_72995_K) {
            MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent(this));
        }
        super.onUnloaded();
    }
}

