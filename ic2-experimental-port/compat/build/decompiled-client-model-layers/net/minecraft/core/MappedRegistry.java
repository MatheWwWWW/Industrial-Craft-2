/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterators
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.common.collect.Sets$SetView
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Lifecycle
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.Validate
 *  org.slf4j.Logger
 */
package net.minecraft.core;

import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

public class MappedRegistry<T>
extends WritableRegistry<T> {
    private static final Logger f_211050_ = LogUtils.getLogger();
    private final ObjectList<Holder.Reference<T>> f_122672_ = new ObjectArrayList(256);
    private final Object2IntMap<T> f_122673_ = (Object2IntMap)Util.m_137469_(new Object2IntOpenCustomHashMap(Util.m_137583_()), p_194539_ -> p_194539_.defaultReturnValue(-1));
    private final Map<ResourceLocation, Holder.Reference<T>> f_205841_ = new HashMap<ResourceLocation, Holder.Reference<T>>();
    private final Map<ResourceKey<T>, Holder.Reference<T>> f_205842_ = new HashMap<ResourceKey<T>, Holder.Reference<T>>();
    private final Map<T, Holder.Reference<T>> f_205843_ = new IdentityHashMap<T, Holder.Reference<T>>();
    private final Map<T, Lifecycle> f_122676_ = new IdentityHashMap<T, Lifecycle>();
    private Lifecycle f_122677_;
    private volatile Map<TagKey<T>, HolderSet.Named<T>> f_205844_ = new IdentityHashMap<TagKey<T>, HolderSet.Named<T>>();
    private boolean f_205845_;
    @Nullable
    private final Function<T, Holder.Reference<T>> f_205846_;
    @Nullable
    private Map<T, Holder.Reference<T>> f_205847_;
    @Nullable
    private List<Holder.Reference<T>> f_211051_;
    private int f_122678_;

    public MappedRegistry(ResourceKey<? extends Registry<T>> p_205849_, Lifecycle p_205850_, @Nullable Function<T, Holder.Reference<T>> p_205851_) {
        super(p_205849_, p_205850_);
        this.f_122677_ = p_205850_;
        this.f_205846_ = p_205851_;
        if (p_205851_ != null) {
            this.f_205847_ = new IdentityHashMap<T, Holder.Reference<T>>();
        }
    }

    private List<Holder.Reference<T>> m_211053_() {
        if (this.f_211051_ == null) {
            this.f_211051_ = this.f_122672_.stream().filter(Objects::nonNull).toList();
        }
        return this.f_211051_;
    }

    private void m_205921_(ResourceKey<T> p_205922_) {
        if (this.f_205845_) {
            throw new IllegalStateException("Registry is already frozen (trying to add key " + p_205922_ + ")");
        }
    }

    @Override
    public Holder<T> m_203704_(int p_205853_, ResourceKey<T> p_205854_, T p_205855_, Lifecycle p_205856_) {
        return this.m_205857_(p_205853_, p_205854_, p_205855_, p_205856_, true);
    }

    private Holder<T> m_205857_(int p_205858_, ResourceKey<T> p_205859_, T p_205860_, Lifecycle p_205861_, boolean p_205862_) {
        Holder.Reference $$7;
        this.m_205921_(p_205859_);
        Validate.notNull(p_205859_);
        Validate.notNull(p_205860_);
        this.f_122672_.size(Math.max(this.f_122672_.size(), p_205858_ + 1));
        this.f_122673_.put(p_205860_, p_205858_);
        this.f_211051_ = null;
        if (p_205862_ && this.f_205842_.containsKey(p_205859_)) {
            Util.m_143785_("Adding duplicate key '" + p_205859_ + "' to registry");
        }
        if (this.f_205843_.containsKey(p_205860_)) {
            Util.m_143785_("Adding duplicate value '" + p_205860_ + "' to registry");
        }
        this.f_122676_.put(p_205860_, p_205861_);
        this.f_122677_ = this.f_122677_.add(p_205861_);
        if (this.f_122678_ <= p_205858_) {
            this.f_122678_ = p_205858_ + 1;
        }
        if (this.f_205846_ != null) {
            Holder.Reference<T> $$5 = this.f_205846_.apply(p_205860_);
            Holder.Reference<T> $$6 = this.f_205842_.put(p_205859_, $$5);
            if ($$6 != null && $$6 != $$5) {
                throw new IllegalStateException("Invalid holder present for key " + p_205859_);
            }
        } else {
            $$7 = this.f_205842_.computeIfAbsent(p_205859_, p_205927_ -> Holder.Reference.m_205766_(this, p_205927_));
        }
        this.f_205841_.put(p_205859_.m_135782_(), $$7);
        this.f_205843_.put(p_205860_, $$7);
        $$7.m_205775_(p_205859_, p_205860_);
        this.f_122672_.set(p_205858_, (Object)$$7);
        return $$7;
    }

    @Override
    public Holder<T> m_203505_(ResourceKey<T> p_205891_, T p_205892_, Lifecycle p_205893_) {
        return this.m_203704_(this.f_122678_, p_205891_, p_205892_, p_205893_);
    }

    @Override
    public Holder<T> m_203384_(OptionalInt p_205884_, ResourceKey<T> p_205885_, T p_205886_, Lifecycle p_205887_) {
        int $$7;
        Object $$5;
        this.m_205921_(p_205885_);
        Validate.notNull(p_205885_);
        Validate.notNull(p_205886_);
        Holder $$4 = this.f_205842_.get(p_205885_);
        Object v0 = $$5 = $$4 != null && $$4.m_203633_() ? $$4.m_203334_() : null;
        if ($$5 == null) {
            int $$6 = p_205884_.orElse(this.f_122678_);
        } else {
            $$7 = this.f_122673_.getInt($$5);
            if (p_205884_.isPresent() && p_205884_.getAsInt() != $$7) {
                throw new IllegalStateException("ID mismatch");
            }
            this.f_122676_.remove($$5);
            this.f_122673_.removeInt($$5);
            this.f_205843_.remove($$5);
        }
        return this.m_205857_($$7, p_205885_, p_205886_, p_205887_, false);
    }

    @Override
    @Nullable
    public ResourceLocation m_7981_(T p_122746_) {
        Holder.Reference<T> $$1 = this.f_205843_.get(p_122746_);
        return $$1 != null ? $$1.m_205785_().m_135782_() : null;
    }

    @Override
    public Optional<ResourceKey<T>> m_7854_(T p_122755_) {
        return Optional.ofNullable(this.f_205843_.get(p_122755_)).map(Holder.Reference::m_205785_);
    }

    @Override
    public int m_7447_(@Nullable T p_122706_) {
        return this.f_122673_.getInt(p_122706_);
    }

    @Override
    @Nullable
    public T m_6246_(@Nullable ResourceKey<T> p_122714_) {
        return MappedRegistry.m_205865_(this.f_205842_.get(p_122714_));
    }

    @Override
    @Nullable
    public T m_7942_(int p_122684_) {
        if (p_122684_ < 0 || p_122684_ >= this.f_122672_.size()) {
            return null;
        }
        return MappedRegistry.m_205865_((Holder.Reference)this.f_122672_.get(p_122684_));
    }

    @Override
    public Optional<Holder<T>> m_203300_(int p_205907_) {
        if (p_205907_ < 0 || p_205907_ >= this.f_122672_.size()) {
            return Optional.empty();
        }
        return Optional.ofNullable((Holder)this.f_122672_.get(p_205907_));
    }

    @Override
    public Optional<Holder<T>> m_203636_(ResourceKey<T> p_205905_) {
        return Optional.ofNullable((Holder)this.f_205842_.get(p_205905_));
    }

    @Override
    public Holder<T> m_214121_(ResourceKey<T> p_235718_) {
        return this.f_205842_.computeIfAbsent(p_235718_, p_235723_ -> {
            if (this.f_205846_ != null) {
                throw new IllegalStateException("This registry can't create new holders without value");
            }
            this.m_205921_((ResourceKey<T>)p_235723_);
            return Holder.Reference.m_205766_(this, p_235723_);
        });
    }

    @Override
    public DataResult<Holder<T>> m_214185_(ResourceKey<T> p_235720_) {
        Holder.Reference<T> $$1 = this.f_205842_.get(p_235720_);
        if ($$1 == null) {
            if (this.f_205846_ != null) {
                return DataResult.error((String)("This registry can't create new holders without value (requested key: " + p_235720_ + ")"));
            }
            if (this.f_205845_) {
                return DataResult.error((String)("Registry is already frozen (requested key: " + p_235720_ + ")"));
            }
            $$1 = Holder.Reference.m_205766_(this, p_235720_);
            this.f_205842_.put(p_235720_, $$1);
        }
        return DataResult.success($$1);
    }

    @Override
    public int m_13562_() {
        return this.f_205842_.size();
    }

    @Override
    public Lifecycle m_6228_(T p_122764_) {
        return this.f_122676_.get(p_122764_);
    }

    @Override
    public Lifecycle m_7837_() {
        return this.f_122677_;
    }

    @Override
    public Iterator<T> iterator() {
        return Iterators.transform(this.m_211053_().iterator(), Holder::m_203334_);
    }

    @Override
    @Nullable
    public T m_7745_(@Nullable ResourceLocation p_122739_) {
        Holder.Reference<T> $$1 = this.f_205841_.get(p_122739_);
        return MappedRegistry.m_205865_($$1);
    }

    @Nullable
    private static <T> T m_205865_(@Nullable Holder.Reference<T> p_205866_) {
        return p_205866_ != null ? (T)p_205866_.m_203334_() : null;
    }

    @Override
    public Set<ResourceLocation> m_6566_() {
        return Collections.unmodifiableSet(this.f_205841_.keySet());
    }

    @Override
    public Set<ResourceKey<T>> m_214010_() {
        return Collections.unmodifiableSet(this.f_205842_.keySet());
    }

    @Override
    public Set<Map.Entry<ResourceKey<T>, T>> m_6579_() {
        return Collections.unmodifiableSet(Maps.transformValues(this.f_205842_, Holder::m_203334_).entrySet());
    }

    @Override
    public Stream<Holder.Reference<T>> m_203611_() {
        return this.m_211053_().stream();
    }

    @Override
    public boolean m_203658_(TagKey<T> p_205864_) {
        return this.f_205844_.containsKey(p_205864_);
    }

    @Override
    public Stream<Pair<TagKey<T>, HolderSet.Named<T>>> m_203612_() {
        return this.f_205844_.entrySet().stream().map(p_211060_ -> Pair.of((Object)((TagKey)p_211060_.getKey()), (Object)((HolderSet.Named)p_211060_.getValue())));
    }

    @Override
    public HolderSet.Named<T> m_203561_(TagKey<T> p_205895_) {
        HolderSet.Named<T> $$1 = this.f_205844_.get(p_205895_);
        if ($$1 == null) {
            $$1 = this.m_211067_(p_205895_);
            IdentityHashMap<TagKey<T>, HolderSet.Named<T>> $$2 = new IdentityHashMap<TagKey<T>, HolderSet.Named<T>>(this.f_205844_);
            $$2.put(p_205895_, $$1);
            this.f_205844_ = $$2;
        }
        return $$1;
    }

    private HolderSet.Named<T> m_211067_(TagKey<T> p_211068_) {
        return new HolderSet.Named<T>(this, p_211068_);
    }

    @Override
    public Stream<TagKey<T>> m_203613_() {
        return this.f_205844_.keySet().stream();
    }

    @Override
    public boolean m_142427_() {
        return this.f_205842_.isEmpty();
    }

    @Override
    public Optional<Holder<T>> m_213642_(RandomSource p_235716_) {
        return Util.m_214676_(this.m_211053_(), p_235716_).map(Holder::m_205706_);
    }

    @Override
    public boolean m_7804_(ResourceLocation p_122761_) {
        return this.f_205841_.containsKey(p_122761_);
    }

    @Override
    public boolean m_142003_(ResourceKey<T> p_175392_) {
        return this.f_205842_.containsKey(p_175392_);
    }

    @Override
    public Registry<T> m_203521_() {
        this.f_205845_ = true;
        List<ResourceLocation> $$0 = this.f_205842_.entrySet().stream().filter(p_211055_ -> !((Holder.Reference)p_211055_.getValue()).m_203633_()).map(p_211794_ -> ((ResourceKey)p_211794_.getKey()).m_135782_()).sorted().toList();
        if (!$$0.isEmpty()) {
            throw new IllegalStateException("Unbound values in registry " + this.m_123023_() + ": " + $$0);
        }
        if (this.f_205847_ != null) {
            List<Holder.Reference> $$1 = this.f_205847_.values().stream().filter(p_211809_ -> !p_211809_.m_203633_()).toList();
            if (!$$1.isEmpty()) {
                throw new IllegalStateException("Some intrusive holders were not added to registry: " + $$1);
            }
            this.f_205847_ = null;
        }
        return this;
    }

    @Override
    public Holder.Reference<T> m_203693_(T p_205915_) {
        if (this.f_205846_ == null) {
            throw new IllegalStateException("This registry can't create intrusive holders");
        }
        if (this.f_205845_ || this.f_205847_ == null) {
            throw new IllegalStateException("Registry is already frozen");
        }
        return this.f_205847_.computeIfAbsent(p_205915_, p_211813_ -> Holder.Reference.m_205763_(this, p_211813_));
    }

    @Override
    public Optional<HolderSet.Named<T>> m_203431_(TagKey<T> p_205909_) {
        return Optional.ofNullable(this.f_205844_.get(p_205909_));
    }

    @Override
    public void m_203652_(Map<TagKey<T>, List<Holder<T>>> p_205875_) {
        IdentityHashMap<Holder.Reference, List> $$1 = new IdentityHashMap<Holder.Reference, List>();
        this.f_205842_.values().forEach(p_211801_ -> $$1.put((Holder.Reference)p_211801_, new ArrayList()));
        p_205875_.forEach((? super K p_211806_, ? super V p_211807_) -> {
            for (Holder $$3 : p_211807_) {
                if (!$$3.m_203401_(this)) {
                    throw new IllegalStateException("Can't create named set " + p_211806_ + " containing value " + $$3 + " from outside registry " + this);
                }
                if ($$3 instanceof Holder.Reference) {
                    Holder.Reference $$4 = (Holder.Reference)$$3;
                    ((List)$$1.get($$4)).add(p_211806_);
                    continue;
                }
                throw new IllegalStateException("Found direct holder " + $$3 + " value in tag " + p_211806_);
            }
        });
        Sets.SetView $$2 = Sets.difference(this.f_205844_.keySet(), p_205875_.keySet());
        if (!$$2.isEmpty()) {
            f_211050_.warn("Not all defined tags for registry {} are present in data pack: {}", this.m_123023_(), (Object)$$2.stream().map(p_211811_ -> p_211811_.f_203868_().toString()).sorted().collect(Collectors.joining(", ")));
        }
        IdentityHashMap<TagKey<T>, HolderSet.Named<T>> $$3 = new IdentityHashMap<TagKey<T>, HolderSet.Named<T>>(this.f_205844_);
        p_205875_.forEach((? super K p_211797_, ? super V p_211798_) -> $$3.computeIfAbsent((TagKey<T>)p_211797_, this::m_211067_).m_205835_(p_211798_));
        $$1.forEach(Holder.Reference::m_205769_);
        this.f_205844_ = $$3;
    }

    @Override
    public void m_203635_() {
        this.f_205844_.values().forEach(p_211792_ -> p_211792_.m_205835_(List.of()));
        this.f_205842_.values().forEach(p_211803_ -> p_211803_.m_205769_(Set.of()));
    }
}

