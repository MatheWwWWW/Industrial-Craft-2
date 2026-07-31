/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.IBlockAccess
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$Phase
 *  net.minecraftforge.fml.common.gameevent.TickEvent$WorldTickEvent
 */
package ic2.core.energy.grid;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.info.ILocatable;
import ic2.core.IC2;
import ic2.core.energy.grid.EnergyNetGlobal;
import ic2.core.energy.grid.EnergyNetLocal;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventHandler {
    private static boolean initialized;

    public static void init() {
        if (initialized) {
            throw new IllegalStateException("already initialized");
        }
        initialized = true;
        MinecraftForge.EVENT_BUS.register((Object)new EventHandler());
    }

    private EventHandler() {
    }

    @SubscribeEvent
    public void onEnergyTileLoad(EnergyTileLoadEvent event) {
        if (event.getWorld().field_72995_K) {
            IC2.log.warn(LogCategory.EnergyNet, "EnergyTileLoadEvent: posted for %s client-side, aborting", Util.toString(event.tile, (IBlockAccess)event.getWorld(), EnergyNet.instance.getPos(event.tile)));
            return;
        }
        if (event.tile instanceof TileEntity) {
            EnergyNet.instance.addTile((TileEntity)((IEnergyTile)((TileEntity)event.tile)));
        } else if (event.tile instanceof ILocatable) {
            EnergyNet.instance.addTile((ILocatable)((Object)((IEnergyTile)((Object)((ILocatable)((Object)event.tile))))));
        } else {
            throw new IllegalArgumentException("invalid tile type: " + event.tile);
        }
    }

    @SubscribeEvent
    public void onEnergyTileUnload(EnergyTileUnloadEvent event) {
        if (event.getWorld().field_72995_K) {
            IC2.log.warn(LogCategory.EnergyNet, "EnergyTileUnloadEvent: posted for %s client-side, aborting", Util.toString(event.tile, (IBlockAccess)event.getWorld(), EnergyNet.instance.getPos(event.tile)));
            return;
        }
        EnergyNet.instance.removeTile(event.tile);
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        EnergyNetLocal enet = EnergyNetGlobal.getLocal(event.world);
        if (event.phase == TickEvent.Phase.START) {
            enet.onTickStart();
        } else {
            enet.onTickEnd();
        }
    }
}

