/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 */
package ic2.core.block.base.cache;

import ic2.api.util.DirectionList;
import java.util.Iterator;
import net.minecraft.core.Direction;

public interface ICache<T>
extends Iterable<Direction> {
    public void markDirty();

    public void invalidateCache();

    public void setCallback(Runnable var1);

    public void update();

    public T getHandler(Direction var1);

    public DirectionList getPresentSides();

    default public boolean isEmpty() {
        return this.getPresentSides().isEmpty();
    }

    default public boolean contains(Direction dir) {
        return this.getPresentSides().contains(dir);
    }

    @Override
    default public Iterator<Direction> iterator() {
        return this.getPresentSides().iterator();
    }
}

