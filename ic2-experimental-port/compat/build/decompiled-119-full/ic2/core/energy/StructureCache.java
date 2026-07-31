/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.ejml.data.DMatrixRMaj
 *  org.ejml.interfaces.linsol.LinearSolverDense
 */
package ic2.core.energy;

import ic2.core.energy.Node;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.ejml.data.DMatrixRMaj;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class StructureCache {
    private static final int maxSize = 32;
    Map<Key, Data> entries = new HashMap<Key, Data>();
    int hits = 0;
    int misses = 0;

    StructureCache() {
    }

    Data get(Set<Integer> set, Set<Integer> set2) {
        Key key = new Key(set, set2);
        Data data = this.entries.get(key);
        if (data == null) {
            data = new Data();
            this.add(key, data);
            ++this.misses;
        } else {
            ++this.hits;
        }
        ++data.queries;
        return data;
    }

    void clear() {
        this.entries.clear();
    }

    int size() {
        return this.entries.size();
    }

    private void add(Key key, Data data) {
        int n = Integer.MAX_VALUE;
        Key key2 = null;
        if (this.entries.size() >= 32) {
            for (Map.Entry<Key, Data> entry : this.entries.entrySet()) {
                if (entry.getValue().queries >= n) continue;
                n = entry.getValue().queries;
                key2 = entry.getKey();
            }
            this.entries.remove(key2);
        }
        this.entries.put(new Key(key), data);
    }

    static class Key {
        final Set<Integer> activeSources;
        final Set<Integer> activeSinks;
        final int hashCode;

        Key(Set<Integer> set, Set<Integer> set2) {
            this.activeSources = set;
            this.activeSinks = set2;
            this.hashCode = this.activeSources.hashCode() * 31 + this.activeSinks.hashCode();
        }

        Key(Key key) {
            this.activeSources = new HashSet<Integer>(key.activeSources);
            this.activeSinks = new HashSet<Integer>(key.activeSinks);
            this.hashCode = key.hashCode;
        }

        public int hashCode() {
            return this.hashCode;
        }

        public boolean equals(Object object) {
            if (!(object instanceof Key)) {
                return false;
            }
            Key key = (Key)object;
            return key.activeSources.equals(this.activeSources) && key.activeSinks.equals(this.activeSinks);
        }
    }

    static class Data {
        boolean isInitialized = false;
        Map<Integer, Node> optimizedNodes;
        List<Node> activeNodes;
        DMatrixRMaj networkMatrix;
        DMatrixRMaj sourceMatrix;
        DMatrixRMaj resultMatrix;
        LinearSolverDense<DMatrixRMaj> solver;
        int queries = 0;

        Data() {
        }
    }
}

