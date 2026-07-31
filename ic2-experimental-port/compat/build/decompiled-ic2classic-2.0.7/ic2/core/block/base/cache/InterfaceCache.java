/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ObjectArrays
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.block.base.cache;

import com.google.common.collect.ObjectArrays;
import ic2.api.util.DirectionList;
import ic2.api.util.ILocation;
import ic2.core.block.base.cache.BaseCache;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class InterfaceCache<T>
extends BaseCache<T> {
    Class<T> filter;
    boolean testing = false;

    public InterfaceCache(ILocation position, DirectionList validChecks, Class<T> filter) {
        super(position, validChecks);
        this.filter = filter;
        this.cache = ObjectArrays.newArray(filter, (int)6);
    }

    public void validateExisting() {
        this.testing = true;
        boolean update = false;
        for (Direction dir : this.present) {
            BlockEntity entity;
            Object entry = this.get(dir);
            if (!(entry instanceof BlockEntity) || !(entity = (BlockEntity)entry).m_58901_()) continue;
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
        for (Direction dir : this.present.invert().remove(this.validSides.invert())) {
            BlockPos pos = worldPos.m_121945_(dir);
            if (!world.m_46749_(pos)) {
                this.clearCache(dir);
                continue;
            }
            BlockEntity tile = world.m_7702_(pos);
            if (!this.filter.isInstance(tile)) {
                this.clearCache(dir);
                continue;
            }
            this.addCache(dir, tile);
        }
        this.testing = false;
    }

    @Override
    public T getHandler(Direction dir) {
        BlockEntity entity;
        Object entry = this.get(dir);
        if (entry instanceof BlockEntity && (entity = (BlockEntity)entry).m_58901_()) {
            this.clearCache(dir);
            return null;
        }
        return entry;
    }
}

