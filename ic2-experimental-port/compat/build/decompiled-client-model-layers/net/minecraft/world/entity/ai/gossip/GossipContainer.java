/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 */
package net.minecraft.world.entity.ai.gossip;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.UUIDUtil;
import net.minecraft.util.RandomSource;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.ai.gossip.GossipType;

public class GossipContainer {
    public static final int f_148158_ = 2;
    private final Map<UUID, EntityGossips> f_26156_ = Maps.newHashMap();

    @VisibleForDebug
    public Map<UUID, Object2IntMap<GossipType>> m_148159_() {
        HashMap $$0 = Maps.newHashMap();
        this.f_26156_.keySet().forEach(p_148167_ -> {
            EntityGossips $$2 = this.f_26156_.get(p_148167_);
            $$0.put(p_148167_, $$2.f_26204_);
        });
        return $$0;
    }

    public void m_26198_() {
        Iterator<EntityGossips> $$0 = this.f_26156_.values().iterator();
        while ($$0.hasNext()) {
            EntityGossips $$1 = $$0.next();
            $$1.m_26208_();
            if (!$$1.m_26225_()) continue;
            $$0.remove();
        }
    }

    private Stream<GossipEntry> m_26203_() {
        return this.f_26156_.entrySet().stream().flatMap(p_26185_ -> ((EntityGossips)p_26185_.getValue()).m_26215_((UUID)p_26185_.getKey()));
    }

    private Collection<GossipEntry> m_217759_(RandomSource p_217760_, int p_217761_) {
        List $$2 = this.m_26203_().collect(Collectors.toList());
        if ($$2.isEmpty()) {
            return Collections.emptyList();
        }
        int[] $$3 = new int[$$2.size()];
        int $$4 = 0;
        for (int $$5 = 0; $$5 < $$2.size(); ++$$5) {
            GossipEntry $$6 = (GossipEntry)$$2.get($$5);
            $$3[$$5] = ($$4 += Math.abs($$6.m_26235_())) - 1;
        }
        Set $$7 = Sets.newIdentityHashSet();
        for (int $$8 = 0; $$8 < p_217761_; ++$$8) {
            int $$9 = p_217760_.m_188503_($$4);
            int $$10 = Arrays.binarySearch($$3, $$9);
            $$7.add((GossipEntry)$$2.get($$10 < 0 ? -$$10 - 1 : $$10));
        }
        return $$7;
    }

    private EntityGossips m_26189_(UUID p_26190_) {
        return this.f_26156_.computeIfAbsent(p_26190_, p_26202_ -> new EntityGossips());
    }

    public void m_217762_(GossipContainer p_217763_, RandomSource p_217764_, int p_217765_) {
        Collection<GossipEntry> $$3 = p_217763_.m_217759_(p_217764_, p_217765_);
        $$3.forEach(p_26200_ -> {
            int $$1 = p_26200_.f_26230_ - p_26200_.f_26229_.f_26277_;
            if ($$1 >= 2) {
                this.m_26189_((UUID)p_26200_.f_26228_).f_26204_.mergeInt((Object)p_26200_.f_26229_, $$1, GossipContainer::m_26158_);
            }
        });
    }

    public int m_26195_(UUID p_26196_, Predicate<GossipType> p_26197_) {
        EntityGossips $$2 = this.f_26156_.get(p_26196_);
        return $$2 != null ? $$2.m_26220_(p_26197_) : 0;
    }

    public long m_148162_(GossipType p_148163_, DoublePredicate p_148164_) {
        return this.f_26156_.values().stream().filter(p_148174_ -> p_148164_.test(p_148174_.f_26204_.getOrDefault((Object)p_148163_, 0) * p_148173_.f_26274_)).count();
    }

    public void m_26191_(UUID p_26192_, GossipType p_26193_, int p_26194_) {
        EntityGossips $$3 = this.m_26189_(p_26192_);
        $$3.f_26204_.mergeInt((Object)p_26193_, p_26194_, (p_186096_, p_186097_) -> this.m_26167_(p_26193_, p_186096_, p_186097_));
        $$3.m_26211_(p_26193_);
        if ($$3.m_26225_()) {
            this.f_26156_.remove(p_26192_);
        }
    }

    public void m_148175_(UUID p_148176_, GossipType p_148177_, int p_148178_) {
        this.m_26191_(p_148176_, p_148177_, -p_148178_);
    }

    public void m_148168_(UUID p_148169_, GossipType p_148170_) {
        EntityGossips $$2 = this.f_26156_.get(p_148169_);
        if ($$2 != null) {
            $$2.m_26226_(p_148170_);
            if ($$2.m_26225_()) {
                this.f_26156_.remove(p_148169_);
            }
        }
    }

    public void m_148160_(GossipType p_148161_) {
        Iterator<EntityGossips> $$1 = this.f_26156_.values().iterator();
        while ($$1.hasNext()) {
            EntityGossips $$2 = $$1.next();
            $$2.m_26226_(p_148161_);
            if (!$$2.m_26225_()) continue;
            $$1.remove();
        }
    }

    public <T> Dynamic<T> m_26179_(DynamicOps<T> p_26180_) {
        return new Dynamic(p_26180_, p_26180_.createList(this.m_26203_().map(p_26183_ -> p_26183_.m_26238_(p_26180_)).map(Dynamic::getValue)));
    }

    public void m_26177_(Dynamic<?> p_26178_) {
        p_26178_.asStream().map(GossipEntry::m_26236_).flatMap(p_26176_ -> p_26176_.result().stream()).forEach(p_26162_ -> this.m_26189_((UUID)p_26162_.f_26228_).f_26204_.put((Object)p_26162_.f_26229_, p_26162_.f_26230_));
    }

    private static int m_26158_(int p_26159_, int p_26160_) {
        return Math.max(p_26159_, p_26160_);
    }

    private int m_26167_(GossipType p_26168_, int p_26169_, int p_26170_) {
        int $$3 = p_26169_ + p_26170_;
        return $$3 > p_26168_.f_26275_ ? Math.max(p_26168_.f_26275_, p_26169_) : $$3;
    }

    static class EntityGossips {
        final Object2IntMap<GossipType> f_26204_ = new Object2IntOpenHashMap();

        EntityGossips() {
        }

        public int m_26220_(Predicate<GossipType> p_26221_) {
            return this.f_26204_.object2IntEntrySet().stream().filter(p_26224_ -> p_26221_.test((GossipType)((Object)((Object)p_26224_.getKey())))).mapToInt(p_26214_ -> p_26214_.getIntValue() * ((GossipType)((Object)((Object)p_26214_.getKey()))).f_26274_).sum();
        }

        public Stream<GossipEntry> m_26215_(UUID p_26216_) {
            return this.f_26204_.object2IntEntrySet().stream().map(p_26219_ -> new GossipEntry(p_26216_, (GossipType)((Object)((Object)p_26219_.getKey())), p_26219_.getIntValue()));
        }

        public void m_26208_() {
            ObjectIterator $$0 = this.f_26204_.object2IntEntrySet().iterator();
            while ($$0.hasNext()) {
                Object2IntMap.Entry $$1 = (Object2IntMap.Entry)$$0.next();
                int $$2 = $$1.getIntValue() - ((GossipType)((Object)$$1.getKey())).f_26276_;
                if ($$2 < 2) {
                    $$0.remove();
                    continue;
                }
                $$1.setValue($$2);
            }
        }

        public boolean m_26225_() {
            return this.f_26204_.isEmpty();
        }

        public void m_26211_(GossipType p_26212_) {
            int $$1 = this.f_26204_.getInt((Object)p_26212_);
            if ($$1 > p_26212_.f_26275_) {
                this.f_26204_.put((Object)p_26212_, p_26212_.f_26275_);
            }
            if ($$1 < 2) {
                this.m_26226_(p_26212_);
            }
        }

        public void m_26226_(GossipType p_26227_) {
            this.f_26204_.removeInt((Object)p_26227_);
        }
    }

    static class GossipEntry {
        public static final String f_148179_ = "Target";
        public static final String f_148180_ = "Type";
        public static final String f_148181_ = "Value";
        public final UUID f_26228_;
        public final GossipType f_26229_;
        public final int f_26230_;

        public GossipEntry(UUID p_26232_, GossipType p_26233_, int p_26234_) {
            this.f_26228_ = p_26232_;
            this.f_26229_ = p_26233_;
            this.f_26230_ = p_26234_;
        }

        public int m_26235_() {
            return this.f_26230_ * this.f_26229_.f_26274_;
        }

        public String toString() {
            return "GossipEntry{target=" + this.f_26228_ + ", type=" + this.f_26229_ + ", value=" + this.f_26230_ + "}";
        }

        public <T> Dynamic<T> m_26238_(DynamicOps<T> p_26239_) {
            return new Dynamic(p_26239_, p_26239_.createMap((Map)ImmutableMap.of((Object)p_26239_.createString(f_148179_), UUIDUtil.f_235867_.encodeStart(p_26239_, (Object)this.f_26228_).result().orElseThrow(RuntimeException::new), (Object)p_26239_.createString(f_148180_), (Object)p_26239_.createString(this.f_26229_.f_26273_), (Object)p_26239_.createString(f_148181_), (Object)p_26239_.createInt(this.f_26230_))));
        }

        public static DataResult<GossipEntry> m_26236_(Dynamic<?> p_26237_) {
            return DataResult.unbox((App)DataResult.instance().group((App)p_26237_.get(f_148179_).read(UUIDUtil.f_235867_), (App)p_26237_.get(f_148180_).asString().map(GossipType::m_26291_), (App)p_26237_.get(f_148181_).asNumber().map(Number::intValue)).apply((Applicative)DataResult.instance(), GossipEntry::new));
        }
    }
}

