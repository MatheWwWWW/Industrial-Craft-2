/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 */
package ic2.core.energy;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.IEnergyNet;
import ic2.api.energy.PacketStats;
import ic2.api.energy.TransferStats;
import ic2.api.energy.tile.IEnergyTile;
import ic2.core.energy.EnergyNetLocal;
import ic2.core.utils.collection.CollectionUtils;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class EnergyNetGlobal
implements IEnergyNet {
    static final double CONSTANT = 1.0 / Math.log(4.0);
    static final Map<Level, EnergyNetLocal> WORLDS = CollectionUtils.createMap();
    public static EnergyNetGlobal INSTANCE = new EnergyNetGlobal();

    @Override
    public IEnergyTile getTile(Level world, BlockPos pos) {
        EnergyNetLocal local = this.getOrCreate(world);
        if (local == null) {
            return null;
        }
        IEnergyTile[] tiles = local.get(pos);
        return tiles == null ? null : tiles[0];
    }

    @Override
    public IEnergyTile getSubTile(Level world, BlockPos pos) {
        EnergyNetLocal local = this.getOrCreate(world);
        if (local == null) {
            return null;
        }
        IEnergyTile[] tiles = local.get(pos);
        return tiles == null ? null : tiles[1];
    }

    @Override
    public IEnergyNet.GridTile getTiles(Level world, BlockPos pos) {
        EnergyNetLocal local = this.getOrCreate(world);
        if (local == null) {
            return null;
        }
        IEnergyTile[] tiles = local.get(pos);
        return tiles == null ? null : new IEnergyNet.GridTile(tiles[0], tiles[1]);
    }

    @Override
    public void addTile(IEnergyTile tile) {
        EnergyNetLocal local = this.getOrCreate(tile.getWorldObj());
        if (local != null) {
            local.addTile(tile);
        }
    }

    @Override
    public void removeTile(IEnergyTile tile) {
        EnergyNetLocal local = this.getOrCreate(tile.getWorldObj());
        if (local != null) {
            local.removeTile(tile);
        }
    }

    @Override
    public void updateTile(IEnergyTile tile) {
        EnergyNetLocal local = this.getOrCreate(tile.getWorldObj());
        if (local != null) {
            local.updateTile(tile);
        }
    }

    @Override
    public int getPowerFromTier(int tier) {
        return 8 << Math.min(tier, 13) * 2;
    }

    @Override
    public int getTierFromPower(int power) {
        return power <= 8 ? 0 : (int)Math.ceil(Math.log((double)power * 0.125) * CONSTANT);
    }

    @Override
    public String getDisplayTier(int tier) {
        switch (tier) {
            case 1: {
                return "LV";
            }
            case 2: {
                return "MV";
            }
            case 3: {
                return "HV";
            }
            case 4: {
                return "EV";
            }
            case 5: {
                return "IV";
            }
            case 6: {
                return "LuV";
            }
        }
        return String.valueOf(tier);
    }

    @Override
    public TransferStats getStats(IEnergyTile tile) {
        EnergyNetLocal local = this.getOrCreate(tile.getWorldObj());
        return local == null ? new TransferStats(0L, 0L, 0L, 0L) : local.getStats(tile);
    }

    @Override
    public List<PacketStats> getPacketStats(IEnergyTile tile) {
        EnergyNetLocal local = this.getOrCreate(tile.getWorldObj());
        return local == null ? Collections.emptyList() : local.getPackets(tile);
    }

    EnergyNetLocal getOrCreate(Level world) {
        if (world == null) {
            return null;
        }
        EnergyNetLocal local = WORLDS.get(world);
        if (local == null) {
            local = new EnergyNetLocal(world);
            WORLDS.put(world, local);
        }
        return local;
    }

    public void init() {
        EnergyNet.INSTANCE = this;
    }

    public static void onWorldTickStart(Level world) {
        EnergyNetLocal local = WORLDS.get(world);
        if (local != null) {
            local.onTickStart();
        }
    }

    public static void onWorldTickStop(Level world) {
        EnergyNetLocal local = WORLDS.get(world);
        if (local != null) {
            local.onTickStop();
        }
    }

    public static void onWorldUnload(Level world) {
        WORLDS.remove(world);
    }

    public static void onServerStopped() {
        WORLDS.clear();
    }
}

