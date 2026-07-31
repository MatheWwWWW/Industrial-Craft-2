/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectFunction
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongAVLTreeSet
 *  it.unimi.dsi.fastutil.longs.LongBidirectionalIterator
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.longs.LongSortedSet
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.entity;

import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongAVLTreeSet;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.SectionPos;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntitySection;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.entity.Visibility;
import net.minecraft.world.phys.AABB;

public class EntitySectionStorage<T extends EntityAccess> {
    private final Class<T> f_156850_;
    private final Long2ObjectFunction<Visibility> f_156851_;
    private final Long2ObjectMap<EntitySection<T>> f_156852_ = new Long2ObjectOpenHashMap();
    private final LongSortedSet f_156853_ = new LongAVLTreeSet();

    public EntitySectionStorage(Class<T> p_156855_, Long2ObjectFunction<Visibility> p_156856_) {
        this.f_156850_ = p_156855_;
        this.f_156851_ = p_156856_;
    }

    public void m_188362_(AABB p_188363_, Consumer<EntitySection<T>> p_188364_) {
        int $$2 = 2;
        int $$3 = SectionPos.m_175552_(p_188363_.f_82288_ - 2.0);
        int $$4 = SectionPos.m_175552_(p_188363_.f_82289_ - 4.0);
        int $$5 = SectionPos.m_175552_(p_188363_.f_82290_ - 2.0);
        int $$6 = SectionPos.m_175552_(p_188363_.f_82291_ + 2.0);
        int $$7 = SectionPos.m_175552_(p_188363_.f_82292_ + 0.0);
        int $$8 = SectionPos.m_175552_(p_188363_.f_82293_ + 2.0);
        for (int $$9 = $$3; $$9 <= $$6; ++$$9) {
            long $$10 = SectionPos.m_123209_($$9, 0, 0);
            long $$11 = SectionPos.m_123209_($$9, -1, -1);
            LongBidirectionalIterator $$12 = this.f_156853_.subSet($$10, $$11 + 1L).iterator();
            while ($$12.hasNext()) {
                EntitySection $$16;
                long $$13 = $$12.nextLong();
                int $$14 = SectionPos.m_123225_($$13);
                int $$15 = SectionPos.m_123230_($$13);
                if ($$14 < $$4 || $$14 > $$7 || $$15 < $$5 || $$15 > $$8 || ($$16 = (EntitySection)this.f_156852_.get($$13)) == null || $$16.m_156833_() || !$$16.m_156848_().m_157694_()) continue;
                p_188364_.accept($$16);
            }
        }
    }

    public LongStream m_156861_(long p_156862_) {
        int $$2;
        int $$1 = ChunkPos.m_45592_(p_156862_);
        LongSortedSet $$3 = this.m_156858_($$1, $$2 = ChunkPos.m_45602_(p_156862_));
        if ($$3.isEmpty()) {
            return LongStream.empty();
        }
        LongBidirectionalIterator $$4 = $$3.iterator();
        return StreamSupport.longStream(Spliterators.spliteratorUnknownSize((PrimitiveIterator.OfLong)$$4, 1301), false);
    }

    private LongSortedSet m_156858_(int p_156859_, int p_156860_) {
        long $$2 = SectionPos.m_123209_(p_156859_, 0, p_156860_);
        long $$3 = SectionPos.m_123209_(p_156859_, -1, p_156860_);
        return this.f_156853_.subSet($$2, $$3 + 1L);
    }

    public Stream<EntitySection<T>> m_156888_(long p_156889_) {
        return this.m_156861_(p_156889_).mapToObj(arg_0 -> this.f_156852_.get(arg_0)).filter(Objects::nonNull);
    }

    private static long m_156899_(long p_156900_) {
        return ChunkPos.m_45589_(SectionPos.m_123213_(p_156900_), SectionPos.m_123230_(p_156900_));
    }

    public EntitySection<T> m_156893_(long p_156894_) {
        return (EntitySection)this.f_156852_.computeIfAbsent(p_156894_, this::m_156901_);
    }

    @Nullable
    public EntitySection<T> m_156895_(long p_156896_) {
        return (EntitySection)this.f_156852_.get(p_156896_);
    }

    private EntitySection<T> m_156901_(long p_156902_) {
        long $$1 = EntitySectionStorage.m_156899_(p_156902_);
        Visibility $$2 = (Visibility)((Object)this.f_156851_.get($$1));
        this.f_156853_.add(p_156902_);
        return new EntitySection<T>(this.f_156850_, $$2);
    }

    public LongSet m_156857_() {
        LongOpenHashSet $$0 = new LongOpenHashSet();
        this.f_156852_.keySet().forEach(arg_0 -> EntitySectionStorage.m_156884_((LongSet)$$0, arg_0));
        return $$0;
    }

    public void m_156890_(AABB p_156891_, Consumer<T> p_156892_) {
        this.m_188362_(p_156891_, p_188368_ -> p_188368_.m_188352_(p_156891_, p_156892_));
    }

    public <U extends T> void m_156863_(EntityTypeTest<T, U> p_156864_, AABB p_156865_, Consumer<U> p_156866_) {
        this.m_188362_(p_156865_, p_188361_ -> p_188361_.m_188348_(p_156864_, p_156865_, p_156866_));
    }

    public void m_156897_(long p_156898_) {
        this.f_156852_.remove(p_156898_);
        this.f_156853_.remove(p_156898_);
    }

    @VisibleForDebug
    public int m_156887_() {
        return this.f_156853_.size();
    }

    private static /* synthetic */ void m_156884_(LongSet p_156885_, long p_156886_) {
        p_156885_.add(EntitySectionStorage.m_156899_(p_156886_));
    }
}

