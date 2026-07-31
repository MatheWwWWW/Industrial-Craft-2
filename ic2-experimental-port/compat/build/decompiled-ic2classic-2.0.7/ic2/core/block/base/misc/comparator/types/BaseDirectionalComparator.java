/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 */
package ic2.core.block.base.misc.comparator.types;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.util.DirectionList;
import ic2.core.block.base.misc.comparator.BaseComparator;
import ic2.core.block.base.misc.comparator.ComparatorListener;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public abstract class BaseDirectionalComparator
extends BaseComparator {
    private int[] values = new int[7];

    public BaseDirectionalComparator(String id, Component name) {
        super(id, name);
    }

    protected abstract int createValue(Direction var1);

    @Override
    protected int createValue() {
        return 0;
    }

    @Override
    public int getValue(Direction dir) {
        return this.values[dir == null ? 6 : dir.m_122411_()];
    }

    @Override
    public boolean updateValue(long time, boolean ignore) {
        if (this.lastTime >= time) {
            return false;
        }
        this.lastTime = time;
        if (!ignore && this.listeners.isEmpty()) {
            return false;
        }
        boolean result = this.updateValue(null);
        for (Direction dir : DirectionList.ALL) {
            result |= this.updateValue(dir);
        }
        if (result) {
            int m = this.listeners.size();
            for (int i = 0; i < m; ++i) {
                ((ComparatorListener)this.listeners.get(i)).onComparatorChanged();
            }
            return true;
        }
        return false;
    }

    protected boolean updateValue(Direction dir) {
        int newValue = Mth.m_14045_((int)this.createValue(dir), (int)0, (int)15);
        if (newValue != this.values[dir == null ? 6 : dir.m_122411_()]) {
            this.values[dir == null ? 6 : dir.m_122411_()] = newValue;
            return true;
        }
        return false;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        for (int i = 0; i < 7; ++i) {
            buffer.writeByte((byte)this.values[i]);
        }
    }

    @Override
    public void read(IInputBuffer buffer) {
        for (int i = 0; i < 7; ++i) {
            this.values[i] = buffer.readByte();
        }
    }
}

