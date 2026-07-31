/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package ic2.core.block.wiring;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.core.IC2;
import ic2.core.block.comp.Redstone;
import ic2.core.block.wiring.CableType;
import ic2.core.block.wiring.TileEntityCable;
import ic2.core.block.wiring.TileEntityClassicCableSplitter;
import ic2.core.ref.TeBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

@TeBlock.Delegated(current=TileEntityCableSplitter.class, old=TileEntityClassicCableSplitter.class)
public class TileEntityCableSplitter
extends TileEntityCable {
    public final Redstone redstone = new Redstone(this);

    public static Class<? extends TileEntityCable> delegate() {
        return IC2.version.isClassic() ? TileEntityClassicCableSplitter.class : TileEntityCableSplitter.class;
    }

    public TileEntityCableSplitter() {
        super(CableType.splitter, 0);
        this.addComponent(this.redstone);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (this.redstone.hasRedstoneInput() == this.addedToEnergyNet) {
            if (this.addedToEnergyNet) {
                MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent(this));
                this.addedToEnergyNet = false;
            } else {
                MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent(this));
                this.addedToEnergyNet = true;
            }
        }
        this.setActive(this.addedToEnergyNet);
    }
}

