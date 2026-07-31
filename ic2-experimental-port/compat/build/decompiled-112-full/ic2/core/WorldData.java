/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.World
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraftforge.fml.common.FMLCommonHandler
 */
package ic2.core;

import ic2.core.IWorldTickCallback;
import ic2.core.WindSim;
import ic2.core.block.personal.TradingMarket;
import ic2.core.energy.grid.EnergyNetLocal;
import ic2.core.network.TeUpdateDataServer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class WorldData {
    private static ConcurrentMap<Integer, WorldData> idxClient = FMLCommonHandler.instance().getSide().isClient() ? new ConcurrentHashMap() : null;
    private static ConcurrentMap<Integer, WorldData> idxServer = new ConcurrentHashMap<Integer, WorldData>();
    final Queue<IWorldTickCallback> singleUpdates = new ConcurrentLinkedQueue<IWorldTickCallback>();
    final Set<IWorldTickCallback> continuousUpdates = new HashSet<IWorldTickCallback>();
    boolean continuousUpdatesInUse = false;
    final List<IWorldTickCallback> continuousUpdatesToAdd = new ArrayList<IWorldTickCallback>();
    final List<IWorldTickCallback> continuousUpdatesToRemove = new ArrayList<IWorldTickCallback>();
    public final EnergyNetLocal energyNet;
    public final Map<TileEntity, TeUpdateDataServer> tesToUpdate = new IdentityHashMap<TileEntity, TeUpdateDataServer>();
    public final TradingMarket tradeMarket;
    public final WindSim windSim;
    public final Map<Chunk, NBTTagCompound> worldGenData = new IdentityHashMap<Chunk, NBTTagCompound>();
    public final Set<Chunk> chunksToDecorate = Collections.newSetFromMap(new IdentityHashMap());
    public final Set<Chunk> pendingUnloadChunks = Collections.newSetFromMap(new IdentityHashMap());

    private WorldData(World world) {
        if (!world.field_72995_K) {
            this.energyNet = EnergyNetLocal.create(world);
            this.tradeMarket = new TradingMarket(world);
            this.windSim = new WindSim(world);
        } else {
            this.energyNet = null;
            this.tradeMarket = null;
            this.windSim = null;
        }
    }

    public static WorldData get(World world) {
        return WorldData.get(world, true);
    }

    public static WorldData get(World world, boolean load) {
        if (world == null) {
            throw new IllegalArgumentException("world is null");
        }
        ConcurrentMap<Integer, WorldData> index = WorldData.getIndex(!world.field_72995_K);
        WorldData ret = (WorldData)index.get(world.field_73011_w.getDimension());
        if (ret != null || !load) {
            return ret;
        }
        ret = new WorldData(world);
        WorldData prev = index.putIfAbsent(world.field_73011_w.getDimension(), ret);
        if (prev != null) {
            ret = prev;
        }
        return ret;
    }

    public static void onWorldUnload(World world) {
        WorldData.getIndex(!world.field_72995_K).remove(world.field_73011_w.getDimension());
    }

    private static ConcurrentMap<Integer, WorldData> getIndex(boolean simulating) {
        return simulating ? idxServer : idxClient;
    }
}

