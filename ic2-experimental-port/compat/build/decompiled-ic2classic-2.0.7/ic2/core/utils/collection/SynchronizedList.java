/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 */
package ic2.core.utils.collection;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class SynchronizedList<K>
extends ObjectArrayList<K>
implements INetworkDataBuffer {
    private static final long serialVersionUID = 1L;
    BiConsumer<K, IOutputBuffer> deserializer;
    Function<IInputBuffer, K> serializer;

    public SynchronizedList(BiConsumer<K, IOutputBuffer> deserializer, Function<IInputBuffer, K> serializer) {
        this.deserializer = deserializer;
        this.serializer = serializer;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeVarInt(this.size);
        for (int i = 0; i < this.size; ++i) {
            this.deserializer.accept(this.get(i), buffer);
        }
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.clear();
        int size = buffer.readVarInt();
        for (int i = 0; i < size; ++i) {
            this.add(this.serializer.apply(buffer));
        }
    }
}

