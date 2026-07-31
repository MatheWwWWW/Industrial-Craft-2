/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.block.transport.item.logistic;

import ic2.api.tiles.tubes.IRequestTube;
import ic2.api.tiles.tubes.ITube;
import ic2.core.block.transport.item.logistic.TubeGrid;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import java.util.Map;
import java.util.UUID;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TubeNet {
    public static final TubeNet INSTANCE = new TubeNet();
    Map<Level, TubeGrid> grids = CollectionUtils.createMap();

    public void addTube(ITube tube) {
        TubeGrid grid = this.getOrCreate(tube.getWorldObj());
        if (grid != null) {
            grid.addTube(tube);
        }
    }

    public void removeTube(ITube tube) {
        TubeGrid grid = this.getOrCreate(tube.getWorldObj());
        if (grid != null) {
            grid.removeTube(tube);
        }
    }

    public void updateTube(ITube tube) {
        TubeGrid grid = this.getOrCreate(tube.getWorldObj());
        if (grid != null) {
            grid.updateTube(tube);
        }
    }

    public void clearRequests(IRequestTube tube) {
        TubeGrid grid = this.getOrCreate(tube.getWorldObj());
        if (grid != null) {
            grid.clearRequests(tube);
        }
    }

    public Object2IntMap<Item> getPotentialItems(IRequestTube tube) {
        TubeGrid grid = this.getOrCreate(tube.getWorldObj());
        if (grid != null) {
            return grid.getPotentialItems(tube);
        }
        return Object2IntMaps.emptyMap();
    }

    public void onRequestLost(ITube tube, ItemStack stack, UUID requestId) {
        TubeGrid grid = this.getOrCreate(tube.getWorldObj());
        if (grid != null) {
            grid.onRequestLost(tube, stack, requestId);
        }
    }

    public void printLag(ITube tube, Player player) {
        TubeGrid grid = this.getOrCreate(tube.getWorldObj());
        if (grid != null) {
            grid.printLag(tube, player);
        }
    }

    TubeGrid getOrCreate(Level world) {
        if (world == null) {
            return null;
        }
        TubeGrid local = this.grids.get(world);
        if (local == null) {
            local = new TubeGrid(world);
            this.grids.put(world, local);
        }
        return local;
    }

    public static void onTick(Level world) {
        TubeGrid grid = TubeNet.INSTANCE.grids.get(world);
        if (grid != null) {
            grid.onTick();
        }
    }

    public static void onWorldUnload(Level world) {
        TubeNet.INSTANCE.grids.remove(world);
    }

    public static void onServerStopped() {
        TubeNet.INSTANCE.grids.clear();
    }
}

