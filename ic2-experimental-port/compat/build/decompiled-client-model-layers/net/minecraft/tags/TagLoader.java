/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.ImmutableSet$Builder
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Multimap
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParser
 *  com.mojang.datafixers.util.Either
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.tags;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.io.BufferedReader;
import java.io.Reader;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagFile;
import org.slf4j.Logger;

public class TagLoader<T> {
    private static final Logger f_13445_ = LogUtils.getLogger();
    private static final String f_144491_ = ".json";
    private static final int f_13447_ = ".json".length();
    final Function<ResourceLocation, Optional<T>> f_13448_;
    private final String f_13449_;

    public TagLoader(Function<ResourceLocation, Optional<T>> p_144493_, String p_144494_) {
        this.f_13448_ = p_144493_;
        this.f_13449_ = p_144494_;
    }

    public Map<ResourceLocation, List<EntryWithSource>> m_144495_(ResourceManager p_144496_) {
        HashMap $$1 = Maps.newHashMap();
        for (Map.Entry<ResourceLocation, List<Resource>> $$2 : p_144496_.m_214160_(this.f_13449_, p_216016_ -> p_216016_.m_135815_().endsWith(f_144491_)).entrySet()) {
            ResourceLocation $$3 = $$2.getKey();
            String $$4 = $$3.m_135815_();
            ResourceLocation $$5 = new ResourceLocation($$3.m_135827_(), $$4.substring(this.f_13449_.length() + 1, $$4.length() - f_13447_));
            for (Resource $$6 : $$2.getValue()) {
                try {
                    BufferedReader $$7 = $$6.m_215508_();
                    try {
                        JsonElement $$8 = JsonParser.parseReader((Reader)$$7);
                        List $$9 = $$1.computeIfAbsent($$5, p_215974_ -> new ArrayList());
                        TagFile $$10 = (TagFile)TagFile.f_215958_.parse(new Dynamic((DynamicOps)JsonOps.INSTANCE, (Object)$$8)).getOrThrow(false, arg_0 -> ((Logger)f_13445_).error(arg_0));
                        if ($$10.f_215960_()) {
                            $$9.clear();
                        }
                        String $$11 = $$6.m_215506_();
                        $$10.f_215959_().forEach(p_215997_ -> $$9.add(new EntryWithSource((TagEntry)p_215997_, $$11)));
                    }
                    finally {
                        if ($$7 == null) continue;
                        ((Reader)$$7).close();
                    }
                }
                catch (Exception $$12) {
                    f_13445_.error("Couldn't read tag list {} from {} in data pack {}", new Object[]{$$5, $$3, $$6.m_215506_(), $$12});
                }
            }
        }
        return $$1;
    }

    private static void m_144523_(Map<ResourceLocation, List<EntryWithSource>> p_144524_, Multimap<ResourceLocation, ResourceLocation> p_144525_, Set<ResourceLocation> p_144526_, ResourceLocation p_144527_, BiConsumer<ResourceLocation, List<EntryWithSource>> p_144528_) {
        if (!p_144526_.add(p_144527_)) {
            return;
        }
        p_144525_.get((Object)p_144527_).forEach(p_216014_ -> TagLoader.m_144523_(p_144524_, p_144525_, p_144526_, p_216014_, p_144528_));
        List<EntryWithSource> $$5 = p_144524_.get(p_144527_);
        if ($$5 != null) {
            p_144528_.accept(p_144527_, $$5);
        }
    }

    private static boolean m_144501_(Multimap<ResourceLocation, ResourceLocation> p_144502_, ResourceLocation p_144503_, ResourceLocation p_144504_) {
        Collection $$3 = p_144502_.get((Object)p_144504_);
        if ($$3.contains(p_144503_)) {
            return true;
        }
        return $$3.stream().anyMatch(p_216032_ -> TagLoader.m_144501_(p_144502_, p_144503_, p_216032_));
    }

    private static void m_144550_(Multimap<ResourceLocation, ResourceLocation> p_144551_, ResourceLocation p_144552_, ResourceLocation p_144553_) {
        if (!TagLoader.m_144501_(p_144551_, p_144552_, p_144553_)) {
            p_144551_.put((Object)p_144552_, (Object)p_144553_);
        }
    }

    private Either<Collection<EntryWithSource>, Collection<T>> m_215978_(TagEntry.Lookup<T> p_215979_, List<EntryWithSource> p_215980_) {
        ImmutableSet.Builder $$2 = ImmutableSet.builder();
        ArrayList<EntryWithSource> $$3 = new ArrayList<EntryWithSource>();
        for (EntryWithSource $$4 : p_215980_) {
            if ($$4.f_216042_().m_215927_(p_215979_, arg_0 -> ((ImmutableSet.Builder)$$2).add(arg_0))) continue;
            $$3.add($$4);
        }
        return $$3.isEmpty() ? Either.right((Object)$$2.build()) : Either.left($$3);
    }

    public Map<ResourceLocation, Collection<T>> m_203898_(Map<ResourceLocation, List<EntryWithSource>> p_203899_) {
        final HashMap $$1 = Maps.newHashMap();
        TagEntry.Lookup $$2 = new TagEntry.Lookup<T>(){

            @Override
            @Nullable
            public T m_213619_(ResourceLocation p_216039_) {
                return TagLoader.this.f_13448_.apply(p_216039_).orElse(null);
            }

            @Override
            @Nullable
            public Collection<T> m_214048_(ResourceLocation p_216041_) {
                return (Collection)$$1.get(p_216041_);
            }
        };
        HashMultimap $$3 = HashMultimap.create();
        p_203899_.forEach((arg_0, arg_1) -> TagLoader.m_216021_((Multimap)$$3, arg_0, arg_1));
        p_203899_.forEach((arg_0, arg_1) -> TagLoader.m_215990_((Multimap)$$3, arg_0, arg_1));
        HashSet $$4 = Sets.newHashSet();
        p_203899_.keySet().forEach(arg_0 -> this.m_216002_(p_203899_, (Multimap)$$3, $$4, $$2, $$1, arg_0));
        return $$1;
    }

    public Map<ResourceLocation, Collection<T>> m_203900_(ResourceManager p_203901_) {
        return this.m_203898_(this.m_144495_(p_203901_));
    }

    private /* synthetic */ void m_216002_(Map p_216003_, Multimap p_216004_, Set p_216005_, TagEntry.Lookup p_216006_, Map p_216007_, ResourceLocation p_216008_) {
        TagLoader.m_144523_(p_216003_, (Multimap<ResourceLocation, ResourceLocation>)p_216004_, p_216005_, p_216008_, (p_215984_, p_215985_) -> this.m_215978_(p_216006_, (List<EntryWithSource>)p_215985_).ifLeft(p_215977_ -> f_13445_.error("Couldn't load tag {} as it is missing following references: {}", p_215984_, (Object)p_215977_.stream().map(Objects::toString).collect(Collectors.joining(", ")))).ifRight(p_216001_ -> p_216007_.put(p_215984_, p_216001_)));
    }

    private static /* synthetic */ void m_215990_(Multimap p_215991_, ResourceLocation p_215992_, List p_215993_) {
        p_215993_.forEach(p_215989_ -> p_215989_.f_216042_.m_215947_(p_216028_ -> TagLoader.m_144550_((Multimap<ResourceLocation, ResourceLocation>)p_215991_, p_215992_, p_216028_)));
    }

    private static /* synthetic */ void m_216021_(Multimap p_216022_, ResourceLocation p_216023_, List p_216024_) {
        p_216024_.forEach(p_216020_ -> p_216020_.f_216042_.m_215938_(p_144563_ -> TagLoader.m_144550_((Multimap<ResourceLocation, ResourceLocation>)p_216022_, p_216023_, p_144563_)));
    }

    public record EntryWithSource(TagEntry f_216042_, String f_216043_) {
        @Override
        public String toString() {
            return this.f_216042_ + " (from " + this.f_216043_ + ")";
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{EntryWithSource.class, "entry;source", "f_216042_", "f_216043_"}, this);
        }

        @Override
        public final boolean equals(Object p_216050_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{EntryWithSource.class, "entry;source", "f_216042_", "f_216043_"}, this, p_216050_);
        }
    }
}

