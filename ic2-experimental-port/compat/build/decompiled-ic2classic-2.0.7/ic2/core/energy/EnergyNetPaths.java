/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectSets
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 */
package ic2.core.energy;

import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.core.energy.EnergyNetLocal;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EnergyNetPaths {
    Map<IEnergySource, Set<EnergyNetLocal.EnergyPath>> sourcePaths = CollectionUtils.createMap();
    Map<IEnergySink, Set<EnergyNetLocal.EnergyPath>> sinkPaths = CollectionUtils.createMap();
    Set<EnergyNetLocal.EnergyPath> allPaths = CollectionUtils.createLinkedSet();

    public void addSourcePaths(IEnergySource source, List<EnergyNetLocal.EnergyPath> paths) {
        Set<EnergyNetLocal.EnergyPath> set = this.sourcePaths.get(source);
        if (set == null) {
            set = CollectionUtils.createLinkedSet();
            this.sourcePaths.put(source, set);
        }
        set.addAll(paths);
        for (EnergyNetLocal.EnergyPath path : paths) {
            Set<EnergyNetLocal.EnergyPath> sinkSet = this.sinkPaths.get(path.target);
            if (sinkSet == null) {
                sinkSet = CollectionUtils.createLinkedSet();
                this.sinkPaths.put(path.target, sinkSet);
            }
            sinkSet.add(path);
            this.allPaths.add(path);
        }
    }

    public void addSinkPaths(IEnergySink sink, List<EnergyNetLocal.EnergyPath> paths) {
        Set<EnergyNetLocal.EnergyPath> set = this.sinkPaths.get(sink);
        if (set == null) {
            set = CollectionUtils.createLinkedSet();
            this.sinkPaths.put(sink, set);
        }
        set.addAll(paths);
        for (EnergyNetLocal.EnergyPath path : paths) {
            Set<EnergyNetLocal.EnergyPath> sinkSet = this.sourcePaths.get(path.source);
            if (sinkSet == null) {
                sinkSet = CollectionUtils.createLinkedSet();
                this.sourcePaths.put(path.source, sinkSet);
            }
            sinkSet.add(path);
            this.allPaths.add(path);
        }
    }

    void addPaths(Collection<EnergyNetLocal.EnergyPath> paths) {
        for (EnergyNetLocal.EnergyPath path : paths) {
            Set<EnergyNetLocal.EnergyPath> set = this.sourcePaths.get(path.source);
            if (set == null) {
                set = CollectionUtils.createLinkedSet();
                this.sourcePaths.put(path.source, set);
            }
            set.add(path);
            set = this.sinkPaths.get(path.target);
            if (set == null) {
                set = CollectionUtils.createLinkedSet();
                this.sinkPaths.put(path.target, set);
            }
            set.add(path);
            this.allPaths.add(path);
        }
    }

    public void removeSource(IEnergySource source) {
        Set<EnergyNetLocal.EnergyPath> paths = this.sourcePaths.remove(source);
        if (paths != null) {
            for (EnergyNetLocal.EnergyPath path : paths) {
                this.sinkPaths.get(path.target).remove(path);
                this.allPaths.remove(path);
            }
        }
    }

    public void removeSink(IEnergySink sink) {
        Set<EnergyNetLocal.EnergyPath> paths = this.sinkPaths.remove(sink);
        if (paths != null) {
            for (EnergyNetLocal.EnergyPath path : paths) {
                this.sourcePaths.get(path.source).remove(path);
                this.allPaths.remove(path);
            }
        }
    }

    public void removePaths(Collection<EnergyNetLocal.EnergyPath> paths) {
        for (EnergyNetLocal.EnergyPath path : paths) {
            Set<EnergyNetLocal.EnergyPath> sink;
            Set<EnergyNetLocal.EnergyPath> source = this.sourcePaths.get(path.source);
            if (source != null) {
                source.remove(path);
            }
            if ((sink = this.sinkPaths.get(path.target)) != null) {
                sink.remove(path);
            }
            this.allPaths.remove(path);
        }
    }

    public Set<EnergyNetLocal.EnergyPath> get(IEnergySource source) {
        return this.sourcePaths.getOrDefault(source, (Set<EnergyNetLocal.EnergyPath>)ObjectSets.emptySet());
    }

    public Set<EnergyNetLocal.EnergyPath> getReverse(IEnergySink sink) {
        return this.sinkPaths.getOrDefault(sink, (Set<EnergyNetLocal.EnergyPath>)ObjectSets.emptySet());
    }

    public Set<EnergyNetLocal.EnergyPath> getAllPaths() {
        return this.allPaths;
    }

    public void clear() {
        this.sinkPaths.clear();
        this.sourcePaths.clear();
        this.allPaths.clear();
    }

    public EnergyNetSubPaths createSubMap() {
        return new EnergyNetSubPaths(this);
    }

    public static class EnergyNetSubPaths {
        EnergyNetPaths paths;
        Map<IEnergySource, Set<EnergyNetLocal.EnergyPath>> subSourcePaths = CollectionUtils.createMap();
        Map<IEnergySink, Set<EnergyNetLocal.EnergyPath>> subSinkPaths = CollectionUtils.createMap();
        Set<EnergyNetLocal.EnergyPath> allSubPaths = CollectionUtils.createLinkedSet();

        EnergyNetSubPaths(EnergyNetPaths paths) {
            this.paths = paths;
        }

        public void addSourcePaths(IEnergySource source, List<EnergyNetLocal.EnergyPath> paths) {
            Set<EnergyNetLocal.EnergyPath> set = this.subSourcePaths.get(source);
            if (set == null) {
                set = CollectionUtils.createLinkedSet();
                this.subSourcePaths.put(source, set);
            }
            set.addAll(paths);
            for (EnergyNetLocal.EnergyPath path : paths) {
                Set<EnergyNetLocal.EnergyPath> sinkSet = this.subSinkPaths.get(path.target);
                if (sinkSet == null) {
                    sinkSet = CollectionUtils.createLinkedSet();
                    this.subSinkPaths.put(path.target, sinkSet);
                }
                sinkSet.add(path);
                this.allSubPaths.add(path);
            }
            this.paths.addSourcePaths(source, paths);
        }

        public void addSinkPaths(IEnergySink sink, List<EnergyNetLocal.EnergyPath> paths) {
            Set<EnergyNetLocal.EnergyPath> set = this.subSinkPaths.get(sink);
            if (set == null) {
                set = CollectionUtils.createLinkedSet();
                this.subSinkPaths.put(sink, set);
            }
            set.addAll(paths);
            for (EnergyNetLocal.EnergyPath path : paths) {
                Set<EnergyNetLocal.EnergyPath> sinkSet = this.subSourcePaths.get(path.source);
                if (sinkSet == null) {
                    sinkSet = CollectionUtils.createLinkedSet();
                    this.subSourcePaths.put(path.source, sinkSet);
                }
                sinkSet.add(path);
                this.allSubPaths.add(path);
            }
            this.paths.addSinkPaths(sink, paths);
        }

        public void removeSource(IEnergySource source) {
            Set<EnergyNetLocal.EnergyPath> paths = this.subSourcePaths.remove(source);
            if (paths != null) {
                for (EnergyNetLocal.EnergyPath path : paths) {
                    this.subSinkPaths.get(path.target).remove(path);
                    this.allSubPaths.remove(path);
                }
                this.paths.removePaths(paths);
            }
        }

        public void removeSink(IEnergySink sink) {
            Set<EnergyNetLocal.EnergyPath> paths = this.subSinkPaths.remove(sink);
            if (paths != null) {
                for (EnergyNetLocal.EnergyPath path : paths) {
                    this.subSourcePaths.get(path.source).remove(path);
                    this.allSubPaths.remove(path);
                }
                this.paths.removePaths(paths);
            }
        }

        public boolean removeConductor(IEnergyConductor conductor) {
            List<EnergyNetLocal.EnergyPath> paths = this.getPathsForConductor(conductor);
            this.removePaths(paths);
            return !paths.isEmpty();
        }

        public void removePaths(Collection<EnergyNetLocal.EnergyPath> paths) {
            for (EnergyNetLocal.EnergyPath path : paths) {
                Set<EnergyNetLocal.EnergyPath> sink;
                Set<EnergyNetLocal.EnergyPath> source = this.subSourcePaths.get(path.source);
                if (source != null) {
                    source.remove(path);
                }
                if ((sink = this.subSinkPaths.get(path.target)) != null) {
                    sink.remove(path);
                }
                this.allSubPaths.remove(path);
            }
            this.paths.removePaths(paths);
        }

        public Set<EnergyNetLocal.EnergyPath> get(IEnergySource source) {
            return this.subSourcePaths.getOrDefault(source, (Set<EnergyNetLocal.EnergyPath>)ObjectSets.emptySet());
        }

        public Set<EnergyNetLocal.EnergyPath> getReverse(IEnergySink sink) {
            return this.subSinkPaths.getOrDefault(sink, (Set<EnergyNetLocal.EnergyPath>)ObjectSets.emptySet());
        }

        public Set<EnergyNetLocal.EnergyPath> getAllPaths() {
            return this.allSubPaths;
        }

        public Set<EnergyNetLocal.EnergyPath> getSubPaths(Set<IEnergySource> sources, Set<IEnergySink> sinks) {
            ObjectSortedSet paths = CollectionUtils.createLinkedSet();
            for (EnergyNetLocal.EnergyPath path : this.allSubPaths) {
                if (!sources.contains(path.source) || !sinks.contains(path.target)) continue;
                paths.add((EnergyNetLocal.EnergyPath)path);
            }
            return paths;
        }

        public void transferPaths(Collection<EnergyNetLocal.EnergyPath> paths) {
            for (EnergyNetLocal.EnergyPath path : paths) {
                Set<EnergyNetLocal.EnergyPath> set = this.subSourcePaths.get(path.source);
                if (set == null) {
                    set = CollectionUtils.createLinkedSet();
                    this.subSourcePaths.put(path.source, set);
                }
                set.add(path);
                set = this.subSinkPaths.get(path.target);
                if (set == null) {
                    set = CollectionUtils.createLinkedSet();
                    this.subSinkPaths.put(path.target, set);
                }
                set.add(path);
                this.allSubPaths.add(path);
            }
        }

        public void addSubSet() {
            this.paths.addPaths(this.allSubPaths);
        }

        public List<EnergyNetLocal.EnergyPath> getPathsForConductor(IEnergyConductor entry) {
            ArrayList<EnergyNetLocal.EnergyPath> paths = new ArrayList<EnergyNetLocal.EnergyPath>();
            for (EnergyNetLocal.EnergyPath path : this.allSubPaths) {
                if (!path.cableCheck.contains(entry)) continue;
                paths.add(path);
            }
            return paths;
        }

        public void reset() {
            this.paths.removePaths(this.allSubPaths);
            this.subSinkPaths.clear();
            this.subSourcePaths.clear();
            this.allSubPaths.clear();
        }

        public void clear() {
            this.subSinkPaths.clear();
            this.subSourcePaths.clear();
            this.allSubPaths.clear();
        }
    }
}

