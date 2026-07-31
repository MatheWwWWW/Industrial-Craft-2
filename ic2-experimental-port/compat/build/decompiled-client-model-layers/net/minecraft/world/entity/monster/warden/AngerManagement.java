/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Streams
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster.warden;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Streams;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.AngerLevel;
import net.minecraft.world.entity.player.Player;

public class AngerManagement {
    @VisibleForTesting
    protected static final int f_219241_ = 2;
    @VisibleForTesting
    protected static final int f_219242_ = 150;
    private static final int f_219246_ = 1;
    private int f_219247_ = Mth.m_216287_(RandomSource.m_216327_(), 0, 2);
    int f_219248_;
    private static final Codec<Pair<UUID, Integer>> f_219249_ = RecordCodecBuilder.create(p_219274_ -> p_219274_.group((App)ExtraCodecs.f_216157_.fieldOf("uuid").forGetter(Pair::getFirst), (App)ExtraCodecs.f_144628_.fieldOf("anger").forGetter(Pair::getSecond)).apply((Applicative)p_219274_, Pair::of));
    private final Predicate<Entity> f_219250_;
    @VisibleForTesting
    protected final ArrayList<Entity> f_219243_;
    private final Sorter f_219251_;
    @VisibleForTesting
    protected final Object2IntMap<Entity> f_219244_;
    @VisibleForTesting
    protected final Object2IntMap<UUID> f_219245_;

    public static Codec<AngerManagement> m_219277_(Predicate<Entity> p_219278_) {
        return RecordCodecBuilder.create(p_219281_ -> p_219281_.group((App)f_219249_.listOf().fieldOf("suspects").orElse(Collections.emptyList()).forGetter(AngerManagement::m_219285_)).apply((Applicative)p_219281_, p_219284_ -> new AngerManagement(p_219278_, (List<Pair<UUID, Integer>>)p_219284_)));
    }

    public AngerManagement(Predicate<Entity> p_219254_, List<Pair<UUID, Integer>> p_219255_) {
        this.f_219250_ = p_219254_;
        this.f_219243_ = new ArrayList();
        this.f_219251_ = new Sorter(this);
        this.f_219244_ = new Object2IntOpenHashMap();
        this.f_219245_ = new Object2IntOpenHashMap(p_219255_.size());
        p_219255_.forEach(p_219272_ -> this.f_219245_.put((Object)((UUID)p_219272_.getFirst()), (Integer)p_219272_.getSecond()));
    }

    private List<Pair<UUID, Integer>> m_219285_() {
        return Streams.concat((Stream[])new Stream[]{this.f_219243_.stream().map(p_219295_ -> Pair.of((Object)p_219295_.m_20148_(), (Object)this.f_219244_.getInt(p_219295_))), this.f_219245_.object2IntEntrySet().stream().map(p_219276_ -> Pair.of((Object)((UUID)p_219276_.getKey()), (Object)p_219276_.getIntValue()))}).collect(Collectors.toList());
    }

    public void m_219263_(ServerLevel p_219264_, Predicate<Entity> p_219265_) {
        --this.f_219247_;
        if (this.f_219247_ <= 0) {
            this.m_219261_(p_219264_);
            this.f_219247_ = 2;
        }
        ObjectIterator $$2 = this.f_219245_.object2IntEntrySet().iterator();
        while ($$2.hasNext()) {
            Object2IntMap.Entry $$3 = (Object2IntMap.Entry)$$2.next();
            int $$4 = $$3.getIntValue();
            if ($$4 <= 1) {
                $$2.remove();
                continue;
            }
            $$3.setValue($$4 - 1);
        }
        ObjectIterator $$5 = this.f_219244_.object2IntEntrySet().iterator();
        while ($$5.hasNext()) {
            Object2IntMap.Entry $$6 = (Object2IntMap.Entry)$$5.next();
            int $$7 = $$6.getIntValue();
            Entity $$8 = (Entity)$$6.getKey();
            Entity.RemovalReason $$9 = $$8.m_146911_();
            if ($$7 <= 1 || !p_219265_.test($$8) || $$9 != null) {
                this.f_219243_.remove($$8);
                $$5.remove();
                if ($$7 <= 1 || $$9 == null) continue;
                switch ($$9) {
                    case CHANGED_DIMENSION: 
                    case UNLOADED_TO_CHUNK: 
                    case UNLOADED_WITH_PLAYER: {
                        this.f_219245_.put((Object)$$8.m_20148_(), $$7 - 1);
                    }
                }
                continue;
            }
            $$6.setValue($$7 - 1);
        }
        this.m_219288_();
    }

    private void m_219288_() {
        this.f_219248_ = 0;
        this.f_219243_.sort(this.f_219251_);
        if (this.f_219243_.size() == 1) {
            this.f_219248_ = this.f_219244_.getInt((Object)this.f_219243_.get(0));
        }
    }

    private void m_219261_(ServerLevel p_219262_) {
        ObjectIterator $$1 = this.f_219245_.object2IntEntrySet().iterator();
        while ($$1.hasNext()) {
            Object2IntMap.Entry $$2 = (Object2IntMap.Entry)$$1.next();
            int $$3 = $$2.getIntValue();
            Entity $$4 = p_219262_.m_8791_((UUID)$$2.getKey());
            if ($$4 == null) continue;
            this.f_219244_.put((Object)$$4, $$3);
            this.f_219243_.add($$4);
            $$1.remove();
        }
    }

    public int m_219268_(Entity p_219269_, int p_219270_) {
        boolean $$2 = !this.f_219244_.containsKey((Object)p_219269_);
        int $$3 = this.f_219244_.computeInt((Object)p_219269_, (p_219259_, p_219260_) -> Math.min(150, (p_219260_ == null ? 0 : p_219260_) + p_219270_));
        if ($$2) {
            int $$4 = this.f_219245_.removeInt((Object)p_219269_.m_20148_());
            this.f_219244_.put((Object)p_219269_, $$3 += $$4);
            this.f_219243_.add(p_219269_);
        }
        this.m_219288_();
        return $$3;
    }

    public void m_219266_(Entity p_219267_) {
        this.f_219244_.removeInt((Object)p_219267_);
        this.f_219243_.remove(p_219267_);
        this.m_219288_();
    }

    @Nullable
    private Entity m_219291_() {
        return this.f_219243_.stream().filter(this.f_219250_).findFirst().orElse(null);
    }

    public int m_219286_(@Nullable Entity p_219287_) {
        return p_219287_ == null ? this.f_219248_ : this.f_219244_.getInt((Object)p_219287_);
    }

    public Optional<LivingEntity> m_219256_() {
        return Optional.ofNullable(this.m_219291_()).filter(p_219293_ -> p_219293_ instanceof LivingEntity).map(p_219290_ -> (LivingEntity)p_219290_);
    }

    @VisibleForTesting
    protected record Sorter(AngerManagement f_219298_) implements Comparator<Entity>
    {
        @Override
        public int compare(Entity p_219303_, Entity p_219304_) {
            boolean $$5;
            if (p_219303_.equals(p_219304_)) {
                return 0;
            }
            int $$2 = this.f_219298_.f_219244_.getOrDefault((Object)p_219303_, 0);
            int $$3 = this.f_219298_.f_219244_.getOrDefault((Object)p_219304_, 0);
            this.f_219298_.f_219248_ = Math.max(this.f_219298_.f_219248_, Math.max($$2, $$3));
            boolean $$4 = AngerLevel.m_219227_($$2).m_219236_();
            if ($$4 != ($$5 = AngerLevel.m_219227_($$3).m_219236_())) {
                return $$4 ? -1 : 1;
            }
            boolean $$6 = p_219303_ instanceof Player;
            boolean $$7 = p_219304_ instanceof Player;
            if ($$6 != $$7) {
                return $$6 ? -1 : 1;
            }
            return Integer.compare($$3, $$2);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Sorter.class, "angerManagement", "f_219298_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Sorter.class, "angerManagement", "f_219298_"}, this);
        }

        @Override
        public final boolean equals(Object p_219309_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Sorter.class, "angerManagement", "f_219298_"}, this, p_219309_);
        }

        @Override
        public /* synthetic */ int compare(Object object, Object object2) {
            return this.compare((Entity)object, (Entity)object2);
        }
    }
}

