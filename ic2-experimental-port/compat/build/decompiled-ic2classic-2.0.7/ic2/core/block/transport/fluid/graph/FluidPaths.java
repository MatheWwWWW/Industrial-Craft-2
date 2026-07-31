/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectSets
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 */
package ic2.core.block.transport.fluid.graph;

import ic2.core.block.transport.fluid.graph.FluidGrid;
import ic2.core.block.transport.fluid.graph.IFluidPipe;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FluidPaths {
    Map<FluidGrid.DirSource, Set<FluidGrid.FluidPath>> pumpPaths = CollectionUtils.createMap();
    Map<IFluidPipe.FluidOutput, Set<FluidGrid.FluidPath>> receivePaths = CollectionUtils.createMap();
    Set<FluidGrid.FluidPath> allPaths = CollectionUtils.createLinkedSet();

    public void addPaths(FluidGrid.DirSource source, Set<FluidGrid.FluidPath> paths) {
        this.allPaths.addAll(paths);
        this.pumpPaths.put(source, (Set<FluidGrid.FluidPath>)new ObjectLinkedOpenHashSet(paths));
        for (FluidGrid.FluidPath path : paths) {
            Set<FluidGrid.FluidPath> targets = this.receivePaths.get(path.output);
            if (targets == null) {
                targets = CollectionUtils.createLinkedSet();
                this.receivePaths.put(path.output, targets);
            }
            targets.add(path);
        }
    }

    public void addPaths(Set<FluidGrid.FluidPath> paths) {
        for (FluidGrid.FluidPath path : paths) {
            Set<FluidGrid.FluidPath> targets = this.receivePaths.get(path.output);
            if (targets == null) {
                targets = CollectionUtils.createLinkedSet();
                this.receivePaths.put(path.output, targets);
            }
            targets.add(path);
        }
    }

    public void removeSource(FluidGrid.DirSource source) {
        Set<FluidGrid.FluidPath> paths = this.pumpPaths.remove(source);
        if (paths != null) {
            for (FluidGrid.FluidPath path : paths) {
                this.receivePaths.get(path.output).remove(path);
                this.allPaths.remove(path);
            }
        }
    }

    public void removeSink(IFluidPipe.FluidOutput sink) {
        Set<FluidGrid.FluidPath> paths = this.receivePaths.remove(sink);
        if (paths != null) {
            for (FluidGrid.FluidPath path : paths) {
                this.pumpPaths.get(path.source).remove(path);
                this.allPaths.remove(path);
            }
        }
    }

    public void removePaths(Collection<FluidGrid.FluidPath> paths) {
        for (FluidGrid.FluidPath path : paths) {
            Set<FluidGrid.FluidPath> sink;
            Set<FluidGrid.FluidPath> source = this.pumpPaths.get(path.source);
            if (source != null) {
                source.remove(path);
            }
            if ((sink = this.receivePaths.get(path.output)) != null) {
                sink.remove(path);
            }
            this.allPaths.remove(path);
        }
    }

    public Set<FluidGrid.FluidPath> getPaths(FluidGrid.DirSource source) {
        return this.pumpPaths.getOrDefault(source, (Set<FluidGrid.FluidPath>)ObjectSets.emptySet());
    }

    public void clear() {
        this.allPaths.clear();
        this.receivePaths.clear();
        this.pumpPaths.clear();
    }

    public FluidSubPaths createPaths() {
        return new FluidSubPaths(this);
    }

    public static class FluidSubPaths {
        FluidPaths owner;
        Map<FluidGrid.DirSource, Set<FluidGrid.FluidPath>> pumpPaths = CollectionUtils.createMap();
        Map<IFluidPipe.FluidOutput, Set<FluidGrid.FluidPath>> receivePaths = CollectionUtils.createMap();
        Set<FluidGrid.FluidPath> allPaths = CollectionUtils.createLinkedSet();

        public FluidSubPaths(FluidPaths owner) {
            this.owner = owner;
        }

        public void addPaths(FluidGrid.DirSource source, Set<FluidGrid.FluidPath> paths) {
            this.allPaths.addAll(paths);
            this.pumpPaths.put(source, (Set<FluidGrid.FluidPath>)new ObjectLinkedOpenHashSet(paths));
            for (FluidGrid.FluidPath path : paths) {
                Set<FluidGrid.FluidPath> targets = this.receivePaths.get(path.output);
                if (targets == null) {
                    targets = CollectionUtils.createLinkedSet();
                    this.receivePaths.put(path.output, targets);
                }
                targets.add(path);
            }
            this.owner.addPaths(source, paths);
        }

        public void removeSource(FluidGrid.DirSource source) {
            Set<FluidGrid.FluidPath> paths = this.pumpPaths.remove(source);
            if (paths != null) {
                for (FluidGrid.FluidPath path : paths) {
                    this.receivePaths.get(path.output).remove(path);
                    this.allPaths.remove(path);
                }
                this.owner.removePaths(paths);
            }
        }

        public void removeSink(IFluidPipe.FluidOutput sink) {
            Set<FluidGrid.FluidPath> paths = this.receivePaths.remove(sink);
            if (paths != null) {
                for (FluidGrid.FluidPath path : paths) {
                    this.pumpPaths.get(path.source).remove(path);
                    this.allPaths.remove(path);
                }
                this.owner.removePaths(paths);
            }
        }

        public void removePaths(Collection<FluidGrid.FluidPath> paths) {
            for (FluidGrid.FluidPath path : paths) {
                Set<FluidGrid.FluidPath> sink;
                Set<FluidGrid.FluidPath> source = this.pumpPaths.get(path.source);
                if (source != null) {
                    source.remove(path);
                }
                if ((sink = this.receivePaths.get(path.output)) != null) {
                    sink.remove(path);
                }
                this.allPaths.remove(path);
            }
            this.owner.removePaths(paths);
        }

        public void addSubSet() {
            this.owner.addPaths(this.allPaths);
        }

        public Set<FluidGrid.FluidPath> getSubPaths(Set<FluidGrid.DirSource> inputs, Set<IFluidPipe.FluidOutput> outputs) {
            ObjectSortedSet paths = CollectionUtils.createLinkedSet();
            for (FluidGrid.FluidPath path : this.allPaths) {
                if (!inputs.contains(path.source) || !outputs.contains(path.output)) continue;
                paths.add((FluidGrid.FluidPath)path);
            }
            return paths;
        }

        public void transferPaths(Collection<FluidGrid.FluidPath> paths) {
            for (FluidGrid.FluidPath path : paths) {
                Set<FluidGrid.FluidPath> set = this.pumpPaths.get(path.source);
                if (set == null) {
                    set = CollectionUtils.createLinkedSet();
                    this.pumpPaths.put(path.source, set);
                }
                set.add(path);
                set = this.receivePaths.get(path.output);
                if (set == null) {
                    set = CollectionUtils.createLinkedSet();
                    this.receivePaths.put(path.output, set);
                }
                set.add(path);
                this.allPaths.add(path);
            }
        }

        public Set<FluidGrid.FluidPath> getPaths(FluidGrid.DirSource source) {
            return this.pumpPaths.getOrDefault(source, Collections.emptySet());
        }

        public Set<FluidGrid.FluidPath> getPaths(IFluidPipe.FluidOutput output) {
            return this.receivePaths.getOrDefault(output, Collections.emptySet());
        }

        public List<FluidGrid.FluidPath> getPathsForPipe(IFluidPipe entry) {
            ObjectList paths = CollectionUtils.createList();
            for (FluidGrid.FluidPath path : this.allPaths) {
                if (!path.paths.contains(entry)) continue;
                paths.add((FluidGrid.FluidPath)path);
            }
            return paths;
        }

        public Set<FluidGrid.FluidPath> getPaths() {
            return this.allPaths;
        }

        public void reset() {
            this.owner.removePaths(this.allPaths);
            this.pumpPaths.clear();
            this.receivePaths.clear();
            this.allPaths.clear();
        }

        public void clear() {
            this.pumpPaths.clear();
            this.receivePaths.clear();
            this.allPaths.clear();
        }
    }
}

