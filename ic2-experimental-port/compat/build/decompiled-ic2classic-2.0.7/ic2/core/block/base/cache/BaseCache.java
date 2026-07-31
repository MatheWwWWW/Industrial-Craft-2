/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 */
package ic2.core.block.base.cache;

import ic2.api.util.DirectionList;
import ic2.api.util.ILocation;
import ic2.core.block.base.cache.ICache;
import java.util.Arrays;
import net.minecraft.core.Direction;

public abstract class BaseCache<T>
implements ICache<T> {
    protected ILocation position;
    protected DirectionList validSides;
    Runnable callback;
    T[] cache = new Object[6];
    DirectionList present = DirectionList.EMPTY;

    public BaseCache(ILocation position, DirectionList validSides) {
        this.position = position;
        this.validSides = validSides;
    }

    @Override
    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    @Override
    public void markDirty() {
        if (this.callback != null) {
            this.callback.run();
        }
    }

    @Override
    public void invalidateCache() {
        this.present = DirectionList.EMPTY;
        Arrays.fill(this.cache, null);
    }

    protected T get(Direction dir) {
        return this.cache[dir.m_122411_()];
    }

    @Override
    public T getHandler(Direction dir) {
        return this.cache[dir.m_122411_()];
    }

    @Override
    public DirectionList getPresentSides() {
        return this.present;
    }

    protected void addCache(Direction dir, T handler) {
        this.cache[dir.m_122411_()] = handler;
        if (handler != null) {
            this.present = this.present.add(dir);
        }
    }

    protected void clearCache(Direction dir) {
        this.cache[dir.m_122411_()] = null;
        this.present = this.present.remove(dir);
        this.markDirty();
    }
}

