/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 */
package ic2.core.block.transport.item.logic;

import ic2.core.block.transport.item.tubes.TeleportTubeTileEntity;
import ic2.core.platform.player.friends.Action;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TeleportManager {
    public static final TeleportManager INSTANCE = new TeleportManager();
    Map<String, List<TeleportTubeTileEntity>> tiles = CollectionUtils.createMap();
    Map<TeleportTubeTileEntity, String> tubes = CollectionUtils.createMap();

    public void add(TeleportTubeTileEntity tile) {
        String freq = tile.getFrequency();
        if (freq == null) {
            return;
        }
        this.tubes.put(tile, freq);
        List<TeleportTubeTileEntity> list = this.tiles.get(freq);
        if (list == null) {
            list = CollectionUtils.createList();
            this.tiles.put(freq, list);
        }
        list.add(tile);
    }

    public void remove(TeleportTubeTileEntity tile) {
        List<TeleportTubeTileEntity> list;
        String oldId = this.tubes.remove(tile);
        if (oldId != null && (list = this.tiles.get(oldId)) != null && list.remove(tile) && list.isEmpty()) {
            this.tiles.remove(oldId);
        }
    }

    public void update(TeleportTubeTileEntity tile) {
        String id = tile.getFrequency();
        if (id == null || !this.tubes.containsKey(tile)) {
            return;
        }
        String oldId = this.tubes.put(tile, id);
        List<TeleportTubeTileEntity> list = this.tiles.get(oldId);
        if (list != null && list.remove(tile) && list.isEmpty()) {
            this.tiles.remove(oldId);
        }
        if ((list = this.tiles.get(id)) == null) {
            list = CollectionUtils.createList();
            this.tiles.put(id, list);
        }
        list.add(tile);
    }

    public TeleportTubeTileEntity getRandomTarget(TeleportTubeTileEntity tile) {
        List<TeleportTubeTileEntity> targets = this.getAllPossibleTarget(tile);
        return targets.isEmpty() ? null : targets.get(tile.m_58904_().f_46441_.m_188503_(targets.size()));
    }

    public List<TeleportTubeTileEntity> getAllPossibleTarget(TeleportTubeTileEntity tile) {
        if (tile.getFrequency() == null || !tile.canSend()) {
            return Collections.emptyList();
        }
        List<TeleportTubeTileEntity> targets = this.tiles.get(tile.getFrequency());
        if (targets == null || targets.isEmpty()) {
            return Collections.emptyList();
        }
        ObjectList result = CollectionUtils.createList();
        boolean isPublic = tile.isPublic();
        int m = targets.size();
        for (int i = 0; i < m; ++i) {
            TeleportTubeTileEntity target = targets.get(i);
            if (target == tile || !target.canReceive() || isPublic != target.isPublic() || !target.canDoAction(tile.getOwner(), Action.TELEPORT_NETWORK, false)) continue;
            result.add((TeleportTubeTileEntity)target);
        }
        return result;
    }
}

