/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.collection;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.utils.collection.CollectionUtils;
import java.util.Collection;
import java.util.function.Supplier;

public class SerializableCollection<T extends INetworkDataBuffer>
implements INetworkDataBuffer {
    Collection<T> collection;
    Supplier<T> creator;

    public SerializableCollection(Collection<T> collection, Supplier<T> creator) {
        this.collection = collection;
        this.creator = creator;
    }

    public static <T extends INetworkDataBuffer> SerializableCollection<T> createList(Supplier<T> creator) {
        return SerializableCollection.create(CollectionUtils.createList(), creator);
    }

    public static <T extends INetworkDataBuffer> SerializableCollection<T> createSet(Supplier<T> creator) {
        return SerializableCollection.create(CollectionUtils.createSet(), creator);
    }

    public static <T extends INetworkDataBuffer> SerializableCollection<T> createLinkedSet(Supplier<T> creator) {
        return SerializableCollection.create(CollectionUtils.createLinkedSet(), creator);
    }

    public static <T extends INetworkDataBuffer> SerializableCollection<T> createClientList(boolean client, Supplier<T> creator) {
        return SerializableCollection.create(client ? CollectionUtils.createList() : CollectionUtils.createLinkedSet(), creator);
    }

    public static <T extends INetworkDataBuffer> SerializableCollection<T> create(Collection<T> collection, Supplier<T> creator) {
        return new SerializableCollection<T>(collection, creator);
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeInt(this.collection.size());
        for (INetworkDataBuffer entry : this.collection) {
            entry.write(buffer);
        }
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.collection.clear();
        int size = buffer.readInt();
        for (int i = 0; i < size; ++i) {
            INetworkDataBuffer entry = (INetworkDataBuffer)this.creator.get();
            entry.read(buffer);
            this.collection.add(entry);
        }
    }

    public boolean isEmpty() {
        return this.collection.isEmpty();
    }

    public int size() {
        return this.collection.size();
    }

    public void clear() {
        this.collection.clear();
    }

    public boolean contains(T entry) {
        return this.collection.contains(entry);
    }

    public boolean add(T entry) {
        return this.collection.add(entry);
    }

    public boolean remove(T entry) {
        return this.collection.remove(entry);
    }

    public Collection<T> get() {
        return this.collection;
    }

    public <K extends Collection<T>> K getCasted() {
        return (K)this.collection;
    }

    public <K extends Collection<T>> K getCasted(Class<K> clz) {
        return (K)this.collection;
    }
}

