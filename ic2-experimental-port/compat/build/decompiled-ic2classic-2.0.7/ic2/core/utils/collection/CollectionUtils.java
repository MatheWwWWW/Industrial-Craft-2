/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.Hash$Strategy
 *  it.unimi.dsi.fastutil.PriorityQueue
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenCustomHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectArrays
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenCustomHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 */
package ic2.core.utils.collection;

import ic2.core.utils.collection.FunctionRegistry;
import ic2.core.utils.collection.SimpleRegistry;
import ic2.core.utils.collection.SupplierRegistry;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenCustomHashSet;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class CollectionUtils {
    public static <K> List<K>[] createLists(int length) {
        List[] lists = new List[length];
        for (int i = 0; i < length; ++i) {
            lists[i] = new ObjectArrayList();
        }
        return lists;
    }

    public static IntList[] createIntLists(int length) {
        IntList[] lists = new IntList[length];
        for (int i = 0; i < length; ++i) {
            lists[i] = new IntArrayList();
        }
        return lists;
    }

    public static <T> ObjectSet<T>[] createLinkedSets(int length) {
        ObjectSet[] sets = new ObjectSet[length];
        for (int i = 0; i < length; ++i) {
            sets[i] = CollectionUtils.createLinkedSet();
        }
        return sets;
    }

    public static <K, V> Object2ObjectMap<K, V>[] createMaps(int size, boolean linked) {
        Object2ObjectMap[] maps = new Object2ObjectMap[size];
        for (int i = 0; i < size; ++i) {
            maps[i] = linked ? new Object2ObjectLinkedOpenHashMap() : new Object2ObjectOpenHashMap();
        }
        return maps;
    }

    public static <T extends IIndexEnum> T[] createSortedArray(T[] values) {
        ObjectArrays.quickSort((Object[])values, (Comparator)EnumSorter.INSTANCE);
        return values;
    }

    public static <K, V extends Supplier<K>> SupplierRegistry<K, V> createSupplierRegistry() {
        return new SupplierRegistry();
    }

    public static <K, P, V extends Function<P, K>> FunctionRegistry<K, P, V> createFunctionRegistry() {
        return new FunctionRegistry();
    }

    public static <K, P, J, V extends Function<P, K>, M extends Function<J, K>> FunctionRegistry.BiFunctionRegistry<K, P, J, V, M> createBiFunctionRegistry() {
        return new FunctionRegistry.BiFunctionRegistry();
    }

    public static <V> SimpleRegistry<V> createRegistry() {
        return new SimpleRegistry();
    }

    public static <K, V> Object2ObjectMap<K, V> createMap() {
        return new Object2ObjectOpenHashMap();
    }

    public static <K, V> Object2ObjectSortedMap<K, V> createLinkedMap() {
        return new Object2ObjectLinkedOpenHashMap();
    }

    public static <K, V> Object2ObjectSortedMap<K, V> createLinkedMap(K key, V value) {
        Object2ObjectLinkedOpenHashMap map = new Object2ObjectLinkedOpenHashMap();
        map.put(key, value);
        return map;
    }

    public static <K, V> Object2ObjectMap<K, V> createMap(Hash.Strategy<K> sorter) {
        return new Object2ObjectOpenCustomHashMap(sorter);
    }

    public static <K, V> Object2ObjectSortedMap<K, V> createLinkedMap(Hash.Strategy<K> sorter) {
        return new Object2ObjectLinkedOpenCustomHashMap(sorter);
    }

    public static <K> ObjectSet<K> createSet() {
        return new ObjectOpenHashSet();
    }

    public static <K> ObjectSet<K> createSet(Hash.Strategy<K> sorter) {
        return new ObjectOpenCustomHashSet(sorter);
    }

    public static <K> ObjectSortedSet<K> createLinkedSet() {
        return new ObjectLinkedOpenHashSet();
    }

    public static <K> ObjectSortedSet<K> createLinkedSet(Hash.Strategy<K> sorter) {
        return new ObjectLinkedOpenCustomHashSet(sorter);
    }

    public static <K> ObjectList<K> createList() {
        return new ObjectArrayList();
    }

    @SafeVarargs
    public static <K> ObjectList<K> asList(K ... values) {
        return ObjectArrayList.wrap((Object[])values);
    }

    public static <K, V> TreeMap<K, V> createTreeMap() {
        return new TreeMap();
    }

    public static <T> PriorityQueue<T> createInsertionQueue() {
        return new ObjectArrayFIFOQueue();
    }

    public static <T> List<T> merge(List<T> first, List<T> second) {
        ObjectArrayList list = new ObjectArrayList(first);
        list.addAll(second);
        return list;
    }

    public static class EnumSorter
    implements Comparator<IIndexEnum> {
        public static final EnumSorter INSTANCE = new EnumSorter();

        @Override
        public int compare(IIndexEnum o1, IIndexEnum o2) {
            return Integer.compare(o1.getIndex(), o2.getIndex());
        }
    }

    public static interface IIndexEnum {
        public int getIndex();
    }
}

