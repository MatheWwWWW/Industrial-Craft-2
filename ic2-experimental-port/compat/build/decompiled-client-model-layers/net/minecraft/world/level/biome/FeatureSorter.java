/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.Graph;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.apache.commons.lang3.mutable.MutableInt;

public class FeatureSorter {
    public static <T> List<StepFeatureData> m_220603_(List<T> p_220604_, Function<T, List<HolderSet<PlacedFeature>>> p_220605_, boolean p_220606_) {
        Object2IntOpenHashMap $$3 = new Object2IntOpenHashMap();
        MutableInt $$4 = new MutableInt(0);
        record FeatureData(int f_220610_, int f_220611_, PlacedFeature f_220612_) {
            @Override
            public final String toString() {
                return ObjectMethods.bootstrap("toString", new MethodHandle[]{FeatureData.class, "featureIndex;step;feature", "f_220610_", "f_220611_", "f_220612_"}, this);
            }

            @Override
            public final int hashCode() {
                return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{FeatureData.class, "featureIndex;step;feature", "f_220610_", "f_220611_", "f_220612_"}, this);
            }

            @Override
            public final boolean equals(Object p_220621_) {
                return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{FeatureData.class, "featureIndex;step;feature", "f_220610_", "f_220611_", "f_220612_"}, this, p_220621_);
            }
        }
        Comparator<FeatureData> $$5 = Comparator.comparingInt(FeatureData::f_220611_).thenComparingInt(FeatureData::f_220610_);
        TreeMap<FeatureData, Set> $$6 = new TreeMap<FeatureData, Set>($$5);
        int $$7 = 0;
        for (T $$8 : p_220604_) {
            ArrayList $$9 = Lists.newArrayList();
            List<HolderSet<PlacedFeature>> $$10 = p_220605_.apply($$8);
            $$7 = Math.max($$7, $$10.size());
            for (int $$11 = 0; $$11 < $$10.size(); ++$$11) {
                for (Holder $$12 : (HolderSet)$$10.get($$11)) {
                    PlacedFeature $$13 = (PlacedFeature)$$12.m_203334_();
                    $$9.add(new FeatureData($$3.computeIfAbsent((Object)$$13, p_220609_ -> $$4.getAndIncrement()), $$11, $$13));
                }
            }
            for (int $$14 = 0; $$14 < $$9.size(); ++$$14) {
                Set $$15 = $$6.computeIfAbsent((FeatureData)$$9.get($$14), p_220602_ -> new TreeSet($$5));
                if ($$14 >= $$9.size() - 1) continue;
                $$15.add((FeatureData)$$9.get($$14 + 1));
            }
        }
        TreeSet<FeatureData> $$16 = new TreeSet<FeatureData>($$5);
        TreeSet<FeatureData> $$17 = new TreeSet<FeatureData>($$5);
        ArrayList $$18 = Lists.newArrayList();
        for (FeatureData $$19 : $$6.keySet()) {
            if (!$$17.isEmpty()) {
                throw new IllegalStateException("You somehow broke the universe; DFS bork (iteration finished with non-empty in-progress vertex set");
            }
            if ($$16.contains($$19) || !Graph.m_184556_($$6, $$16, $$17, $$18::add, $$19)) continue;
            if (p_220606_) {
                int $$21;
                ArrayList<T> $$20 = new ArrayList<T>(p_220604_);
                do {
                    $$21 = $$20.size();
                    ListIterator $$22 = $$20.listIterator();
                    while ($$22.hasNext()) {
                        Object $$23 = $$22.next();
                        $$22.remove();
                        try {
                            FeatureSorter.m_220603_($$20, p_220605_, false);
                        }
                        catch (IllegalStateException $$24) {
                            continue;
                        }
                        $$22.add($$23);
                    }
                } while ($$21 != $$20.size());
                throw new IllegalStateException("Feature order cycle found, involved sources: " + $$20);
            }
            throw new IllegalStateException("Feature order cycle found");
        }
        Collections.reverse($$18);
        ImmutableList.Builder $$25 = ImmutableList.builder();
        int $$26 = 0;
        while ($$26 < $$7) {
            int $$27 = $$26++;
            List<PlacedFeature> $$28 = $$18.stream().filter(p_220599_ -> p_220599_.f_220611_() == $$27).map(FeatureData::f_220612_).collect(Collectors.toList());
            $$25.add((Object)new StepFeatureData($$28));
        }
        return $$25.build();
    }

    public record StepFeatureData(List<PlacedFeature> f_220624_, ToIntFunction<PlacedFeature> f_220625_) {
        StepFeatureData(List<PlacedFeature> p_220627_) {
            this(p_220627_, Util.m_214634_(p_220627_, p_220633_ -> new Object2IntOpenCustomHashMap(p_220633_, Util.m_137583_())));
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{StepFeatureData.class, "features;indexMapping", "f_220624_", "f_220625_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{StepFeatureData.class, "features;indexMapping", "f_220624_", "f_220625_"}, this);
        }

        @Override
        public final boolean equals(Object p_220636_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{StepFeatureData.class, "features;indexMapping", "f_220624_", "f_220625_"}, this, p_220636_);
        }
    }
}

