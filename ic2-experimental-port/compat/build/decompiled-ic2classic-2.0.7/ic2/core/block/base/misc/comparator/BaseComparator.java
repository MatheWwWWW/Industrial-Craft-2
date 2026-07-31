/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 */
package ic2.core.block.base.misc.comparator;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.block.base.misc.comparator.ComparatorListener;
import ic2.core.block.base.misc.comparator.ComparatorManager;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import java.util.function.BooleanSupplier;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public abstract class BaseComparator
implements INetworkDataBuffer {
    protected List<ComparatorListener> listeners = CollectionUtils.createList();
    private ComparatorManager manager;
    protected long lastTime;
    private int value;
    final String id;
    final Component name;
    BooleanSupplier visible = null;

    public BaseComparator(String id, Component name) {
        this.id = id;
        this.name = name;
    }

    void setManager(ComparatorManager manager) {
        this.manager = manager;
    }

    public BaseComparator setVisibleHandler(BooleanSupplier visible) {
        this.visible = visible;
        return this;
    }

    void addListener(ComparatorListener listener) {
        this.listeners.add(listener);
        if (this.listeners.size() == 1) {
            this.manager.updating.add(this);
        }
    }

    boolean removeListener(ComparatorListener listener) {
        if (this.listeners.remove(listener)) {
            if (this.listeners.isEmpty()) {
                this.manager.updating.remove(this);
            }
            return true;
        }
        return false;
    }

    public boolean visible() {
        return this.visible == null || this.visible.getAsBoolean();
    }

    public final String getId() {
        return this.id;
    }

    public final Component getName() {
        return this.name;
    }

    protected abstract int createValue();

    public int getValue(Direction dir) {
        return this.value;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeByte((byte)this.value);
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.value = buffer.readByte();
    }

    public boolean updateValue(long time, boolean ignore) {
        if (this.lastTime >= time) {
            return false;
        }
        this.lastTime = time;
        if (!ignore && this.listeners.isEmpty()) {
            return false;
        }
        int newValue = Mth.m_14045_((int)this.createValue(), (int)0, (int)15);
        if (newValue != this.value) {
            this.value = newValue;
            int m = this.listeners.size();
            for (int i = 0; i < m; ++i) {
                this.listeners.get(i).onComparatorChanged();
            }
            return true;
        }
        return false;
    }

    public static final int value(float min, float max, int value) {
        return (int)(min / max * (float)value);
    }
}

