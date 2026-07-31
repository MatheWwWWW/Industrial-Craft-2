/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraftforge.event.level.LevelEvent
 */
package ic2.api.energy.event;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyTile;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.level.LevelEvent;

public class EnergyTileEvent
extends LevelEvent {
    public final IEnergyTile tile;

    public EnergyTileEvent(IEnergyTile iEnergyTile) {
        super((LevelAccessor)EnergyNet.instance.getWorld(iEnergyTile));
        if (this.getLevel() == null) {
            throw new NullPointerException("world is null");
        }
        this.tile = iEnergyTile;
    }
}

