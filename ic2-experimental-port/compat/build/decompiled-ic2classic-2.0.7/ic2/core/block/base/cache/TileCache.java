/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.block.base.cache;

import ic2.api.util.DirectionList;
import ic2.api.util.ILocation;
import ic2.core.block.base.cache.BaseCache;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class TileCache<T extends BlockEntity>
extends BaseCache<T> {
    Class<T> filter;
    boolean testing = false;

    public TileCache(ILocation position, DirectionList validChecks, Class<T> filter) {
        super(position, validChecks);
        this.filter = filter;
    }

    public void validateExisting() {
        this.testing = true;
        boolean update = false;
        for (Direction dir : this.present) {
            BlockEntity entry = (BlockEntity)this.get(dir);
            if (entry == null || !entry.m_58901_()) continue;
            this.clearCache(dir);
            update = true;
        }
        if (update) {
            this.update();
        }
        this.testing = false;
    }

    @Override
    public void markDirty() {
        if (this.testing) {
            return;
        }
        super.markDirty();
    }

    @Override
    public void update() {
        this.testing = true;
        Level world = this.position.getWorldObj();
        BlockPos worldPos = this.position.getPosition();
        for (Direction dir : this.validSides) {
            BlockEntity tile = DirectionList.getNeighborTile(world, worldPos, dir);
            if (!this.filter.isInstance(tile)) {
                this.clearCache(dir);
                continue;
            }
            this.addCache(dir, tile);
        }
        this.testing = true;
    }

    @Override
    public T getHandler(Direction dir) {
        BlockEntity entry = (BlockEntity)this.get(dir);
        if (entry != null && entry.m_58901_()) {
            this.clearCache(dir);
            return null;
        }
        return (T)entry;
    }
}

