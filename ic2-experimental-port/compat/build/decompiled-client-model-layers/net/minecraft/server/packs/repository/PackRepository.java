/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Functions
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.server.packs.repository;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;

public class PackRepository {
    private final Set<RepositorySource> f_10497_;
    private Map<String, Pack> f_10498_ = ImmutableMap.of();
    private List<Pack> f_10499_ = ImmutableList.of();
    private final Pack.PackConstructor f_10500_;

    public PackRepository(Pack.PackConstructor p_10502_, RepositorySource ... p_10503_) {
        this.f_10500_ = p_10502_;
        this.f_10497_ = ImmutableSet.copyOf((Object[])p_10503_);
    }

    public PackRepository(PackType p_143890_, RepositorySource ... p_143891_) {
        this((String p_143894_, Component p_143895_, boolean p_143896_, Supplier<PackResources> p_143897_, PackMetadataSection p_143898_, Pack.Position p_143899_, PackSource p_143900_) -> new Pack(p_143894_, p_143895_, p_143896_, p_143897_, p_143898_, p_143890_, p_143899_, p_143900_), p_143891_);
    }

    public void m_10506_() {
        List $$0 = (List)this.f_10499_.stream().map(Pack::m_10446_).collect(ImmutableList.toImmutableList());
        this.f_10498_ = this.m_10526_();
        this.f_10499_ = this.m_10517_($$0);
    }

    private Map<String, Pack> m_10526_() {
        TreeMap $$0 = Maps.newTreeMap();
        for (RepositorySource $$1 : this.f_10497_) {
            $$1.m_7686_(p_143903_ -> $$0.put(p_143903_.m_10446_(), p_143903_), this.f_10500_);
        }
        return ImmutableMap.copyOf((Map)$$0);
    }

    public void m_10509_(Collection<String> p_10510_) {
        this.f_10499_ = this.m_10517_(p_10510_);
    }

    private List<Pack> m_10517_(Collection<String> p_10518_) {
        List $$1 = this.m_10520_(p_10518_).collect(Collectors.toList());
        for (Pack $$2 : this.f_10498_.values()) {
            if (!$$2.m_10449_() || $$1.contains($$2)) continue;
            $$2.m_10451_().m_10470_($$1, $$2, Functions.identity(), false);
        }
        return ImmutableList.copyOf($$1);
    }

    private Stream<Pack> m_10520_(Collection<String> p_10521_) {
        return p_10521_.stream().map(this.f_10498_::get).filter(Objects::nonNull);
    }

    public Collection<String> m_10514_() {
        return this.f_10498_.keySet();
    }

    public Collection<Pack> m_10519_() {
        return this.f_10498_.values();
    }

    public Collection<String> m_10523_() {
        return (Collection)this.f_10499_.stream().map(Pack::m_10446_).collect(ImmutableSet.toImmutableSet());
    }

    public Collection<Pack> m_10524_() {
        return this.f_10499_;
    }

    @Nullable
    public Pack m_10507_(String p_10508_) {
        return this.f_10498_.get(p_10508_);
    }

    public boolean m_10515_(String p_10516_) {
        return this.f_10498_.containsKey(p_10516_);
    }

    public List<PackResources> m_10525_() {
        return (List)this.f_10499_.stream().map(Pack::m_10445_).collect(ImmutableList.toImmutableList());
    }
}

