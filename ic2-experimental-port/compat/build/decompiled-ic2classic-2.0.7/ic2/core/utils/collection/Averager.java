/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.collection;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;

public class Averager
implements INetworkDataBuffer {
    int[] values = new int[100];
    long total = 0L;
    int inUse;
    int index;
    int subValue;

    public void addValue(int power) {
        this.subValue += power;
    }

    public boolean shouldRun() {
        return this.total != 0L || this.subValue != 0;
    }

    public void finished() {
        ++this.index;
        this.index %= 100;
        if (this.inUse < 100) {
            ++this.inUse;
        } else {
            this.total -= (long)this.values[this.index];
        }
        this.values[this.index] = this.subValue;
        this.total += (long)this.subValue;
        this.subValue = 0;
    }

    public int getAverage() {
        return this.inUse == 0 || this.total == 0L ? 0 : (int)(this.total / (long)this.inUse);
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeByte((byte)this.inUse);
        buffer.writeLong(this.total);
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.inUse = buffer.readByte();
        this.total = buffer.readLong();
    }
}

