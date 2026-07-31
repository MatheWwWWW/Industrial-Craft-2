/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMaps
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.SectionTracker;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.DataLayerStorageMap;
import net.minecraft.world.level.lighting.LayerLightEngine;

public abstract class LayerLightSectionStorage<M extends DataLayerStorageMap<M>>
extends SectionTracker {
    protected static final int f_164440_ = 0;
    protected static final int f_164441_ = 1;
    protected static final int f_164442_ = 2;
    protected static final DataLayer f_75727_ = new DataLayer();
    private static final Direction[] f_75737_ = Direction.values();
    private final LightLayer f_75738_;
    private final LightChunkGetter f_75739_;
    protected final LongSet f_75728_ = new LongOpenHashSet();
    protected final LongSet f_75729_ = new LongOpenHashSet();
    protected final LongSet f_75730_ = new LongOpenHashSet();
    protected volatile M f_75731_;
    protected final M f_75732_;
    protected final LongSet f_75733_ = new LongOpenHashSet();
    protected final LongSet f_75734_ = new LongOpenHashSet();
    protected final Long2ObjectMap<DataLayer> f_75735_ = Long2ObjectMaps.synchronize((Long2ObjectMap)new Long2ObjectOpenHashMap());
    private final LongSet f_75740_ = new LongOpenHashSet();
    private final LongSet f_75741_ = new LongOpenHashSet();
    private final LongSet f_75742_ = new LongOpenHashSet();
    protected volatile boolean f_75736_;

    protected LayerLightSectionStorage(LightLayer p_75745_, LightChunkGetter p_75746_, M p_75747_) {
        super(3, 16, 256);
        this.f_75738_ = p_75745_;
        this.f_75739_ = p_75746_;
        this.f_75732_ = p_75747_;
        this.f_75731_ = ((DataLayerStorageMap)p_75747_).m_5972_();
        ((DataLayerStorageMap)this.f_75731_).m_75534_();
    }

    protected boolean m_75791_(long p_75792_) {
        return this.m_75758_(p_75792_, true) != null;
    }

    @Nullable
    protected DataLayer m_75758_(long p_75759_, boolean p_75760_) {
        return this.m_75761_(p_75760_ ? this.f_75732_ : this.f_75731_, p_75759_);
    }

    @Nullable
    protected DataLayer m_75761_(M p_75762_, long p_75763_) {
        return ((DataLayerStorageMap)p_75762_).m_75532_(p_75763_);
    }

    @Nullable
    public DataLayer m_75793_(long p_75794_) {
        DataLayer $$1 = (DataLayer)this.f_75735_.get(p_75794_);
        if ($$1 != null) {
            return $$1;
        }
        return this.m_75758_(p_75794_, false);
    }

    protected abstract int m_6181_(long var1);

    protected int m_75795_(long p_75796_) {
        long $$1 = SectionPos.m_123235_(p_75796_);
        DataLayer $$2 = this.m_75758_($$1, true);
        return $$2.m_62560_(SectionPos.m_123207_(BlockPos.m_121983_(p_75796_)), SectionPos.m_123207_(BlockPos.m_122008_(p_75796_)), SectionPos.m_123207_(BlockPos.m_122015_(p_75796_)));
    }

    protected void m_75772_(long p_75773_, int p_75774_) {
        long $$2 = SectionPos.m_123235_(p_75773_);
        if (this.f_75733_.add($$2)) {
            ((DataLayerStorageMap)this.f_75732_).m_75524_($$2);
        }
        DataLayer $$3 = this.m_75758_($$2, true);
        $$3.m_62564_(SectionPos.m_123207_(BlockPos.m_121983_(p_75773_)), SectionPos.m_123207_(BlockPos.m_122008_(p_75773_)), SectionPos.m_123207_(BlockPos.m_122015_(p_75773_)), p_75774_);
        SectionPos.m_194639_(p_75773_, arg_0 -> ((LongSet)this.f_75734_).add(arg_0));
    }

    @Override
    protected int m_6172_(long p_75781_) {
        if (p_75781_ == Long.MAX_VALUE) {
            return 2;
        }
        if (this.f_75728_.contains(p_75781_)) {
            return 0;
        }
        if (!this.f_75742_.contains(p_75781_) && ((DataLayerStorageMap)this.f_75732_).m_75529_(p_75781_)) {
            return 1;
        }
        return 2;
    }

    @Override
    protected int m_7409_(long p_75771_) {
        if (this.f_75729_.contains(p_75771_)) {
            return 2;
        }
        if (this.f_75728_.contains(p_75771_) || this.f_75730_.contains(p_75771_)) {
            return 0;
        }
        return 2;
    }

    @Override
    protected void m_7351_(long p_75749_, int p_75750_) {
        int $$2 = this.m_6172_(p_75749_);
        if ($$2 != 0 && p_75750_ == 0) {
            this.f_75728_.add(p_75749_);
            this.f_75730_.remove(p_75749_);
        }
        if ($$2 == 0 && p_75750_ != 0) {
            this.f_75728_.remove(p_75749_);
            this.f_75729_.remove(p_75749_);
        }
        if ($$2 >= 2 && p_75750_ != 2) {
            if (this.f_75742_.contains(p_75749_)) {
                this.f_75742_.remove(p_75749_);
            } else {
                ((DataLayerStorageMap)this.f_75732_).m_75526_(p_75749_, this.m_7667_(p_75749_));
                this.f_75733_.add(p_75749_);
                this.m_6177_(p_75749_);
                int $$3 = SectionPos.m_123213_(p_75749_);
                int $$4 = SectionPos.m_123225_(p_75749_);
                int $$5 = SectionPos.m_123230_(p_75749_);
                for (int $$6 = -1; $$6 <= 1; ++$$6) {
                    for (int $$7 = -1; $$7 <= 1; ++$$7) {
                        for (int $$8 = -1; $$8 <= 1; ++$$8) {
                            this.f_75734_.add(SectionPos.m_123209_($$3 + $$7, $$4 + $$8, $$5 + $$6));
                        }
                    }
                }
            }
        }
        if ($$2 != 2 && p_75750_ >= 2) {
            this.f_75742_.add(p_75749_);
        }
        this.f_75736_ = !this.f_75742_.isEmpty();
    }

    protected DataLayer m_7667_(long p_75797_) {
        DataLayer $$1 = (DataLayer)this.f_75735_.get(p_75797_);
        if ($$1 != null) {
            return $$1;
        }
        return new DataLayer();
    }

    protected void m_75764_(LayerLightEngine<?, ?> p_75765_, long p_75766_) {
        if (p_75765_.m_75598_() == 0) {
            return;
        }
        if (p_75765_.m_75598_() < 8192) {
            p_75765_.m_75581_(p_75753_ -> SectionPos.m_123235_(p_75753_) == p_75766_);
            return;
        }
        int $$2 = SectionPos.m_123223_(SectionPos.m_123213_(p_75766_));
        int $$3 = SectionPos.m_123223_(SectionPos.m_123225_(p_75766_));
        int $$4 = SectionPos.m_123223_(SectionPos.m_123230_(p_75766_));
        for (int $$5 = 0; $$5 < 16; ++$$5) {
            for (int $$6 = 0; $$6 < 16; ++$$6) {
                for (int $$7 = 0; $$7 < 16; ++$$7) {
                    long $$8 = BlockPos.m_121882_($$2 + $$5, $$3 + $$6, $$4 + $$7);
                    p_75765_.m_75600_($$8);
                }
            }
        }
    }

    protected boolean m_6808_() {
        return this.f_75736_;
    }

    protected void m_6716_(LayerLightEngine<M, ?> p_75767_, boolean p_75768_, boolean p_75769_) {
        if (!this.m_6808_() && this.f_75735_.isEmpty()) {
            return;
        }
        LongIterator longIterator = this.f_75742_.iterator();
        while (longIterator.hasNext()) {
            long $$3 = (Long)longIterator.next();
            this.m_75764_(p_75767_, $$3);
            DataLayer $$4 = (DataLayer)this.f_75735_.remove($$3);
            DataLayer $$5 = ((DataLayerStorageMap)this.f_75732_).m_75535_($$3);
            if (!this.f_75741_.contains(SectionPos.m_123240_($$3))) continue;
            if ($$4 != null) {
                this.f_75735_.put($$3, (Object)$$4);
                continue;
            }
            if ($$5 == null) continue;
            this.f_75735_.put($$3, (Object)$$5);
        }
        ((DataLayerStorageMap)this.f_75732_).m_75531_();
        longIterator = this.f_75742_.iterator();
        while (longIterator.hasNext()) {
            long $$6 = (Long)longIterator.next();
            this.m_6187_($$6);
        }
        this.f_75742_.clear();
        this.f_75736_ = false;
        for (Long2ObjectMap.Entry $$7 : this.f_75735_.long2ObjectEntrySet()) {
            long $$8 = $$7.getLongKey();
            if (!this.m_75791_($$8)) continue;
            DataLayer $$9 = (DataLayer)$$7.getValue();
            if (((DataLayerStorageMap)this.f_75732_).m_75532_($$8) == $$9) continue;
            this.m_75764_(p_75767_, $$8);
            ((DataLayerStorageMap)this.f_75732_).m_75526_($$8, $$9);
            this.f_75733_.add($$8);
        }
        ((DataLayerStorageMap)this.f_75732_).m_75531_();
        if (!p_75769_) {
            longIterator = this.f_75735_.keySet().iterator();
            while (longIterator.hasNext()) {
                long $$10 = (Long)longIterator.next();
                this.m_75777_(p_75767_, $$10);
            }
        } else {
            longIterator = this.f_75740_.iterator();
            while (longIterator.hasNext()) {
                long $$11 = (Long)longIterator.next();
                this.m_75777_(p_75767_, $$11);
            }
        }
        this.f_75740_.clear();
        ObjectIterator $$12 = this.f_75735_.long2ObjectEntrySet().iterator();
        while ($$12.hasNext()) {
            Long2ObjectMap.Entry $$13 = (Long2ObjectMap.Entry)$$12.next();
            long $$14 = $$13.getLongKey();
            if (!this.m_75791_($$14)) continue;
            $$12.remove();
        }
    }

    private void m_75777_(LayerLightEngine<M, ?> p_75778_, long p_75779_) {
        if (!this.m_75791_(p_75779_)) {
            return;
        }
        int $$2 = SectionPos.m_123223_(SectionPos.m_123213_(p_75779_));
        int $$3 = SectionPos.m_123223_(SectionPos.m_123225_(p_75779_));
        int $$4 = SectionPos.m_123223_(SectionPos.m_123230_(p_75779_));
        for (Direction $$5 : f_75737_) {
            long $$6 = SectionPos.m_123191_(p_75779_, $$5);
            if (this.f_75735_.containsKey($$6) || !this.m_75791_($$6)) continue;
            for (int $$7 = 0; $$7 < 16; ++$$7) {
                for (int $$8 = 0; $$8 < 16; ++$$8) {
                    long $$20;
                    long $$19;
                    switch ($$5) {
                        case DOWN: {
                            long $$9 = BlockPos.m_121882_($$2 + $$8, $$3, $$4 + $$7);
                            long $$10 = BlockPos.m_121882_($$2 + $$8, $$3 - 1, $$4 + $$7);
                            break;
                        }
                        case UP: {
                            long $$11 = BlockPos.m_121882_($$2 + $$8, $$3 + 16 - 1, $$4 + $$7);
                            long $$12 = BlockPos.m_121882_($$2 + $$8, $$3 + 16, $$4 + $$7);
                            break;
                        }
                        case NORTH: {
                            long $$13 = BlockPos.m_121882_($$2 + $$7, $$3 + $$8, $$4);
                            long $$14 = BlockPos.m_121882_($$2 + $$7, $$3 + $$8, $$4 - 1);
                            break;
                        }
                        case SOUTH: {
                            long $$15 = BlockPos.m_121882_($$2 + $$7, $$3 + $$8, $$4 + 16 - 1);
                            long $$16 = BlockPos.m_121882_($$2 + $$7, $$3 + $$8, $$4 + 16);
                            break;
                        }
                        case WEST: {
                            long $$17 = BlockPos.m_121882_($$2, $$3 + $$7, $$4 + $$8);
                            long $$18 = BlockPos.m_121882_($$2 - 1, $$3 + $$7, $$4 + $$8);
                            break;
                        }
                        default: {
                            $$19 = BlockPos.m_121882_($$2 + 16 - 1, $$3 + $$7, $$4 + $$8);
                            $$20 = BlockPos.m_121882_($$2 + 16, $$3 + $$7, $$4 + $$8);
                        }
                    }
                    p_75778_.m_75576_($$19, $$20, p_75778_.m_6359_($$19, $$20, p_75778_.m_6172_($$19)), false);
                    p_75778_.m_75576_($$20, $$19, p_75778_.m_6359_($$20, $$19, p_75778_.m_6172_($$20)), false);
                }
            }
        }
    }

    protected void m_6177_(long p_75798_) {
    }

    protected void m_6187_(long p_75799_) {
    }

    protected void m_7358_(long p_75775_, boolean p_75776_) {
    }

    public void m_75782_(long p_75783_, boolean p_75784_) {
        if (p_75784_) {
            this.f_75741_.add(p_75783_);
        } else {
            this.f_75741_.remove(p_75783_);
        }
    }

    protected void m_75754_(long p_75755_, @Nullable DataLayer p_75756_, boolean p_75757_) {
        if (p_75756_ != null) {
            this.f_75735_.put(p_75755_, (Object)p_75756_);
            if (!p_75757_) {
                this.f_75740_.add(p_75755_);
            }
        } else {
            this.f_75735_.remove(p_75755_);
        }
    }

    protected void m_75787_(long p_75788_, boolean p_75789_) {
        boolean $$2 = this.f_75728_.contains(p_75788_);
        if (!$$2 && !p_75789_) {
            this.f_75730_.add(p_75788_);
            this.m_75576_(Long.MAX_VALUE, p_75788_, 0, true);
        }
        if ($$2 && p_75789_) {
            this.f_75729_.add(p_75788_);
            this.m_75576_(Long.MAX_VALUE, p_75788_, 2, false);
        }
    }

    protected void m_75785_() {
        if (this.m_75587_()) {
            this.m_75588_(Integer.MAX_VALUE);
        }
    }

    protected void m_75790_() {
        if (!this.f_75733_.isEmpty()) {
            Object $$0 = ((DataLayerStorageMap)this.f_75732_).m_5972_();
            ((DataLayerStorageMap)$$0).m_75534_();
            this.f_75731_ = $$0;
            this.f_75733_.clear();
        }
        if (!this.f_75734_.isEmpty()) {
            LongIterator $$1 = this.f_75734_.iterator();
            while ($$1.hasNext()) {
                long $$2 = $$1.nextLong();
                this.f_75739_.m_6506_(this.f_75738_, SectionPos.m_123184_($$2));
            }
            this.f_75734_.clear();
        }
    }
}

