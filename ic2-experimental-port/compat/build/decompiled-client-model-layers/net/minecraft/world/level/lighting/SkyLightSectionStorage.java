/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 */
package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.DataLayerStorageMap;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.minecraft.world.level.lighting.LayerLightSectionStorage;

public class SkyLightSectionStorage
extends LayerLightSectionStorage<SkyDataLayerStorageMap> {
    private static final Direction[] f_75860_ = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
    private final LongSet f_75861_ = new LongOpenHashSet();
    private final LongSet f_75862_ = new LongOpenHashSet();
    private final LongSet f_75863_ = new LongOpenHashSet();
    private final LongSet f_75864_ = new LongOpenHashSet();
    private volatile boolean f_75865_;

    protected SkyLightSectionStorage(LightChunkGetter p_75868_) {
        super(LightLayer.SKY, p_75868_, new SkyDataLayerStorageMap((Long2ObjectOpenHashMap<DataLayer>)new Long2ObjectOpenHashMap(), new Long2IntOpenHashMap(), Integer.MAX_VALUE));
    }

    @Override
    protected int m_6181_(long p_75880_) {
        return this.m_164457_(p_75880_, false);
    }

    protected int m_164457_(long p_164458_, boolean p_164459_) {
        long $$2 = SectionPos.m_123235_(p_164458_);
        int $$3 = SectionPos.m_123225_($$2);
        SkyDataLayerStorageMap $$4 = p_164459_ ? (SkyDataLayerStorageMap)this.f_75732_ : (SkyDataLayerStorageMap)this.f_75731_;
        int $$5 = $$4.f_75901_.get(SectionPos.m_123240_($$2));
        if ($$5 == $$4.f_75900_ || $$3 >= $$5) {
            if (p_164459_ && !this.m_75892_($$2)) {
                return 0;
            }
            return 15;
        }
        DataLayer $$6 = this.m_75761_($$4, $$2);
        if ($$6 == null) {
            p_164458_ = BlockPos.m_122027_(p_164458_);
            while ($$6 == null) {
                if (++$$3 >= $$5) {
                    return 15;
                }
                p_164458_ = BlockPos.m_121910_(p_164458_, 0, 16, 0);
                $$2 = SectionPos.m_123191_($$2, Direction.UP);
                $$6 = this.m_75761_($$4, $$2);
            }
        }
        return $$6.m_62560_(SectionPos.m_123207_(BlockPos.m_121983_(p_164458_)), SectionPos.m_123207_(BlockPos.m_122008_(p_164458_)), SectionPos.m_123207_(BlockPos.m_122015_(p_164458_)));
    }

    @Override
    protected void m_6177_(long p_75885_) {
        long $$2;
        int $$3;
        int $$1 = SectionPos.m_123225_(p_75885_);
        if (((SkyDataLayerStorageMap)this.f_75732_).f_75900_ > $$1) {
            ((SkyDataLayerStorageMap)this.f_75732_).f_75900_ = $$1;
            ((SkyDataLayerStorageMap)this.f_75732_).f_75901_.defaultReturnValue(((SkyDataLayerStorageMap)this.f_75732_).f_75900_);
        }
        if (($$3 = ((SkyDataLayerStorageMap)this.f_75732_).f_75901_.get($$2 = SectionPos.m_123240_(p_75885_))) < $$1 + 1) {
            ((SkyDataLayerStorageMap)this.f_75732_).f_75901_.put($$2, $$1 + 1);
            if (this.f_75864_.contains($$2)) {
                this.m_75896_(p_75885_);
                if ($$3 > ((SkyDataLayerStorageMap)this.f_75732_).f_75900_) {
                    long $$4 = SectionPos.m_123209_(SectionPos.m_123213_(p_75885_), $$3 - 1, SectionPos.m_123230_(p_75885_));
                    this.m_75894_($$4);
                }
                this.m_75881_();
            }
        }
    }

    private void m_75894_(long p_75895_) {
        this.f_75863_.add(p_75895_);
        this.f_75862_.remove(p_75895_);
    }

    private void m_75896_(long p_75897_) {
        this.f_75862_.add(p_75897_);
        this.f_75863_.remove(p_75897_);
    }

    private void m_75881_() {
        this.f_75865_ = !this.f_75862_.isEmpty() || !this.f_75863_.isEmpty();
    }

    @Override
    protected void m_6187_(long p_75887_) {
        long $$1 = SectionPos.m_123240_(p_75887_);
        boolean $$2 = this.f_75864_.contains($$1);
        if ($$2) {
            this.m_75894_(p_75887_);
        }
        int $$3 = SectionPos.m_123225_(p_75887_);
        if (((SkyDataLayerStorageMap)this.f_75732_).f_75901_.get($$1) == $$3 + 1) {
            long $$4 = p_75887_;
            while (!this.m_75791_($$4) && this.m_75870_($$3)) {
                --$$3;
                $$4 = SectionPos.m_123191_($$4, Direction.DOWN);
            }
            if (this.m_75791_($$4)) {
                ((SkyDataLayerStorageMap)this.f_75732_).f_75901_.put($$1, $$3 + 1);
                if ($$2) {
                    this.m_75896_($$4);
                }
            } else {
                ((SkyDataLayerStorageMap)this.f_75732_).f_75901_.remove($$1);
            }
        }
        if ($$2) {
            this.m_75881_();
        }
    }

    @Override
    protected void m_7358_(long p_75877_, boolean p_75878_) {
        this.m_75785_();
        if (p_75878_ && this.f_75864_.add(p_75877_)) {
            int $$2 = ((SkyDataLayerStorageMap)this.f_75732_).f_75901_.get(p_75877_);
            if ($$2 != ((SkyDataLayerStorageMap)this.f_75732_).f_75900_) {
                long $$3 = SectionPos.m_123209_(SectionPos.m_123213_(p_75877_), $$2 - 1, SectionPos.m_123230_(p_75877_));
                this.m_75896_($$3);
                this.m_75881_();
            }
        } else if (!p_75878_) {
            this.f_75864_.remove(p_75877_);
        }
    }

    @Override
    protected boolean m_6808_() {
        return super.m_6808_() || this.f_75865_;
    }

    @Override
    protected DataLayer m_7667_(long p_75883_) {
        DataLayer $$4;
        DataLayer $$1 = (DataLayer)this.f_75735_.get(p_75883_);
        if ($$1 != null) {
            return $$1;
        }
        long $$2 = SectionPos.m_123191_(p_75883_, Direction.UP);
        int $$3 = ((SkyDataLayerStorageMap)this.f_75732_).f_75901_.get(SectionPos.m_123240_(p_75883_));
        if ($$3 == ((SkyDataLayerStorageMap)this.f_75732_).f_75900_ || SectionPos.m_123225_($$2) >= $$3) {
            return new DataLayer();
        }
        while (($$4 = this.m_75758_($$2, true)) == null) {
            $$2 = SectionPos.m_123191_($$2, Direction.UP);
        }
        return SkyLightSectionStorage.m_182512_($$4);
    }

    private static DataLayer m_182512_(DataLayer p_182513_) {
        if (p_182513_.m_62575_()) {
            return new DataLayer();
        }
        byte[] $$1 = p_182513_.m_7877_();
        byte[] $$2 = new byte[2048];
        for (int $$3 = 0; $$3 < 16; ++$$3) {
            System.arraycopy($$1, 0, $$2, $$3 * 128, 128);
        }
        return new DataLayer($$2);
    }

    @Override
    protected void m_6716_(LayerLightEngine<SkyDataLayerStorageMap, ?> p_75873_, boolean p_75874_, boolean p_75875_) {
        LongIterator longIterator;
        super.m_6716_(p_75873_, p_75874_, p_75875_);
        if (!p_75874_) {
            return;
        }
        if (!this.f_75862_.isEmpty()) {
            longIterator = this.f_75862_.iterator();
            while (longIterator.hasNext()) {
                long $$3 = (Long)longIterator.next();
                int $$4 = this.m_6172_($$3);
                if ($$4 == 2 || this.f_75863_.contains($$3) || !this.f_75861_.add($$3)) continue;
                if ($$4 == 1) {
                    this.m_75764_(p_75873_, $$3);
                    if (this.f_75733_.add($$3)) {
                        ((SkyDataLayerStorageMap)this.f_75732_).m_75524_($$3);
                    }
                    Arrays.fill(this.m_75758_($$3, true).m_7877_(), (byte)-1);
                    int $$5 = SectionPos.m_123223_(SectionPos.m_123213_($$3));
                    int $$6 = SectionPos.m_123223_(SectionPos.m_123225_($$3));
                    int $$7 = SectionPos.m_123223_(SectionPos.m_123230_($$3));
                    for (Direction $$8 : f_75860_) {
                        long $$9 = SectionPos.m_123191_($$3, $$8);
                        if (!this.f_75863_.contains($$9) && (this.f_75861_.contains($$9) || this.f_75862_.contains($$9)) || !this.m_75791_($$9)) continue;
                        for (int $$10 = 0; $$10 < 16; ++$$10) {
                            for (int $$11 = 0; $$11 < 16; ++$$11) {
                                long $$19;
                                long $$18;
                                switch ($$8) {
                                    case NORTH: {
                                        long $$12 = BlockPos.m_121882_($$5 + $$10, $$6 + $$11, $$7);
                                        long $$13 = BlockPos.m_121882_($$5 + $$10, $$6 + $$11, $$7 - 1);
                                        break;
                                    }
                                    case SOUTH: {
                                        long $$14 = BlockPos.m_121882_($$5 + $$10, $$6 + $$11, $$7 + 16 - 1);
                                        long $$15 = BlockPos.m_121882_($$5 + $$10, $$6 + $$11, $$7 + 16);
                                        break;
                                    }
                                    case WEST: {
                                        long $$16 = BlockPos.m_121882_($$5, $$6 + $$10, $$7 + $$11);
                                        long $$17 = BlockPos.m_121882_($$5 - 1, $$6 + $$10, $$7 + $$11);
                                        break;
                                    }
                                    default: {
                                        $$18 = BlockPos.m_121882_($$5 + 16 - 1, $$6 + $$10, $$7 + $$11);
                                        $$19 = BlockPos.m_121882_($$5 + 16, $$6 + $$10, $$7 + $$11);
                                    }
                                }
                                p_75873_.m_75576_($$18, $$19, p_75873_.m_6359_($$18, $$19, 0), true);
                            }
                        }
                    }
                    for (int $$20 = 0; $$20 < 16; ++$$20) {
                        for (int $$21 = 0; $$21 < 16; ++$$21) {
                            long $$22 = BlockPos.m_121882_(SectionPos.m_175554_(SectionPos.m_123213_($$3), $$20), SectionPos.m_123223_(SectionPos.m_123225_($$3)), SectionPos.m_175554_(SectionPos.m_123230_($$3), $$21));
                            long $$23 = BlockPos.m_121882_(SectionPos.m_175554_(SectionPos.m_123213_($$3), $$20), SectionPos.m_123223_(SectionPos.m_123225_($$3)) - 1, SectionPos.m_175554_(SectionPos.m_123230_($$3), $$21));
                            p_75873_.m_75576_($$22, $$23, p_75873_.m_6359_($$22, $$23, 0), true);
                        }
                    }
                    continue;
                }
                for (int $$24 = 0; $$24 < 16; ++$$24) {
                    for (int $$25 = 0; $$25 < 16; ++$$25) {
                        long $$26 = BlockPos.m_121882_(SectionPos.m_175554_(SectionPos.m_123213_($$3), $$24), SectionPos.m_175554_(SectionPos.m_123225_($$3), 15), SectionPos.m_175554_(SectionPos.m_123230_($$3), $$25));
                        p_75873_.m_75576_(Long.MAX_VALUE, $$26, 0, true);
                    }
                }
            }
        }
        this.f_75862_.clear();
        if (!this.f_75863_.isEmpty()) {
            longIterator = this.f_75863_.iterator();
            while (longIterator.hasNext()) {
                long $$27 = (Long)longIterator.next();
                if (!this.f_75861_.remove($$27) || !this.m_75791_($$27)) continue;
                for (int $$28 = 0; $$28 < 16; ++$$28) {
                    for (int $$29 = 0; $$29 < 16; ++$$29) {
                        long $$30 = BlockPos.m_121882_(SectionPos.m_175554_(SectionPos.m_123213_($$27), $$28), SectionPos.m_175554_(SectionPos.m_123225_($$27), 15), SectionPos.m_175554_(SectionPos.m_123230_($$27), $$29));
                        p_75873_.m_75576_(Long.MAX_VALUE, $$30, 15, false);
                    }
                }
            }
        }
        this.f_75863_.clear();
        this.f_75865_ = false;
    }

    protected boolean m_75870_(int p_75871_) {
        return p_75871_ >= ((SkyDataLayerStorageMap)this.f_75732_).f_75900_;
    }

    protected boolean m_75890_(long p_75891_) {
        long $$1 = SectionPos.m_123240_(p_75891_);
        int $$2 = ((SkyDataLayerStorageMap)this.f_75732_).f_75901_.get($$1);
        return $$2 == ((SkyDataLayerStorageMap)this.f_75732_).f_75900_ || SectionPos.m_123225_(p_75891_) >= $$2;
    }

    protected boolean m_75892_(long p_75893_) {
        long $$1 = SectionPos.m_123240_(p_75893_);
        return this.f_75864_.contains($$1);
    }

    protected static final class SkyDataLayerStorageMap
    extends DataLayerStorageMap<SkyDataLayerStorageMap> {
        int f_75900_;
        final Long2IntOpenHashMap f_75901_;

        public SkyDataLayerStorageMap(Long2ObjectOpenHashMap<DataLayer> p_75903_, Long2IntOpenHashMap p_75904_, int p_75905_) {
            super(p_75903_);
            this.f_75901_ = p_75904_;
            p_75904_.defaultReturnValue(p_75905_);
            this.f_75900_ = p_75905_;
        }

        @Override
        public SkyDataLayerStorageMap m_5972_() {
            return new SkyDataLayerStorageMap((Long2ObjectOpenHashMap<DataLayer>)this.f_75518_.clone(), this.f_75901_.clone(), this.f_75900_);
        }

        @Override
        public /* synthetic */ DataLayerStorageMap m_5972_() {
            return this.m_5972_();
        }
    }
}

