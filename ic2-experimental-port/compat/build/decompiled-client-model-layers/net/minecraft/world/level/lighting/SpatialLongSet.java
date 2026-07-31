/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.HashCommon
 *  it.unimi.dsi.fastutil.longs.Long2LongLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet
 */
package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.Long2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import java.util.NoSuchElementException;
import net.minecraft.util.Mth;

public class SpatialLongSet
extends LongLinkedOpenHashSet {
    private final InternalMap f_164460_;

    public SpatialLongSet(int p_164462_, float p_164463_) {
        super(p_164462_, p_164463_);
        this.f_164460_ = new InternalMap(p_164462_ / 64, p_164463_);
    }

    public boolean add(long p_164465_) {
        return this.f_164460_.m_164499_(p_164465_);
    }

    public boolean rem(long p_164468_) {
        return this.f_164460_.m_164501_(p_164468_);
    }

    public long removeFirstLong() {
        return this.f_164460_.m_164485_();
    }

    public int size() {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        return this.f_164460_.isEmpty();
    }

    protected static class InternalMap
    extends Long2LongLinkedOpenHashMap {
        private static final int f_164471_ = Mth.m_14173_(60000000);
        private static final int f_164472_ = Mth.m_14173_(60000000);
        private static final int f_164473_;
        private static final int f_164474_ = 0;
        private static final int f_164475_;
        private static final int f_164476_;
        private static final long f_164477_;
        private int f_164478_ = -1;
        private long f_164479_;
        private final int f_164480_;

        public InternalMap(int p_164483_, float p_164484_) {
            super(p_164483_, p_164484_);
            this.f_164480_ = p_164483_;
        }

        static long m_164489_(long p_164490_) {
            return p_164490_ & (f_164477_ ^ 0xFFFFFFFFFFFFFFFFL);
        }

        static int m_164497_(long p_164498_) {
            int $$1 = (int)(p_164498_ >>> f_164476_ & 3L);
            int $$2 = (int)(p_164498_ >>> 0 & 3L);
            int $$3 = (int)(p_164498_ >>> f_164475_ & 3L);
            return $$1 << 4 | $$3 << 2 | $$2;
        }

        static long m_164491_(long p_164492_, int p_164493_) {
            p_164492_ |= (long)(p_164493_ >>> 4 & 3) << f_164476_;
            p_164492_ |= (long)(p_164493_ >>> 2 & 3) << f_164475_;
            return p_164492_ |= (long)(p_164493_ >>> 0 & 3) << 0;
        }

        public boolean m_164499_(long p_164500_) {
            int $$6;
            long $$1 = InternalMap.m_164489_(p_164500_);
            int $$2 = InternalMap.m_164497_(p_164500_);
            long $$3 = 1L << $$2;
            if ($$1 == 0L) {
                if (this.containsNullKey) {
                    return this.m_164486_(this.n, $$3);
                }
                this.containsNullKey = true;
                int $$4 = this.n;
            } else {
                if (this.f_164478_ != -1 && $$1 == this.f_164479_) {
                    return this.m_164486_(this.f_164478_, $$3);
                }
                long[] $$5 = this.key;
                $$6 = (int)HashCommon.mix((long)$$1) & this.mask;
                long $$7 = $$5[$$6];
                while ($$7 != 0L) {
                    if ($$7 == $$1) {
                        this.f_164478_ = $$6;
                        this.f_164479_ = $$1;
                        return this.m_164486_($$6, $$3);
                    }
                    $$6 = $$6 + 1 & this.mask;
                    $$7 = $$5[$$6];
                }
            }
            this.key[$$6] = $$1;
            this.value[$$6] = $$3;
            if (this.size == 0) {
                this.first = this.last = $$6;
                this.link[$$6] = -1L;
            } else {
                int n = this.last;
                this.link[n] = this.link[n] ^ (this.link[this.last] ^ (long)$$6 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
                this.link[$$6] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
                this.last = $$6;
            }
            if (this.size++ >= this.maxFill) {
                this.rehash(HashCommon.arraySize((int)(this.size + 1), (float)this.f));
            }
            return false;
        }

        private boolean m_164486_(int p_164487_, long p_164488_) {
            boolean $$2 = (this.value[p_164487_] & p_164488_) != 0L;
            int n = p_164487_;
            this.value[n] = this.value[n] | p_164488_;
            return $$2;
        }

        public boolean m_164501_(long p_164502_) {
            long $$1 = InternalMap.m_164489_(p_164502_);
            int $$2 = InternalMap.m_164497_(p_164502_);
            long $$3 = 1L << $$2;
            if ($$1 == 0L) {
                if (this.containsNullKey) {
                    return this.m_164503_($$3);
                }
                return false;
            }
            if (this.f_164478_ != -1 && $$1 == this.f_164479_) {
                return this.m_164494_(this.f_164478_, $$3);
            }
            long[] $$4 = this.key;
            int $$5 = (int)HashCommon.mix((long)$$1) & this.mask;
            long $$6 = $$4[$$5];
            while ($$6 != 0L) {
                if ($$1 == $$6) {
                    this.f_164478_ = $$5;
                    this.f_164479_ = $$1;
                    return this.m_164494_($$5, $$3);
                }
                $$5 = $$5 + 1 & this.mask;
                $$6 = $$4[$$5];
            }
            return false;
        }

        private boolean m_164503_(long p_164504_) {
            if ((this.value[this.n] & p_164504_) == 0L) {
                return false;
            }
            int n = this.n;
            this.value[n] = this.value[n] & (p_164504_ ^ 0xFFFFFFFFFFFFFFFFL);
            if (this.value[this.n] != 0L) {
                return true;
            }
            this.containsNullKey = false;
            --this.size;
            this.fixPointers(this.n);
            if (this.size < this.maxFill / 4 && this.n > 16) {
                this.rehash(this.n / 2);
            }
            return true;
        }

        private boolean m_164494_(int p_164495_, long p_164496_) {
            if ((this.value[p_164495_] & p_164496_) == 0L) {
                return false;
            }
            int n = p_164495_;
            this.value[n] = this.value[n] & (p_164496_ ^ 0xFFFFFFFFFFFFFFFFL);
            if (this.value[p_164495_] != 0L) {
                return true;
            }
            this.f_164478_ = -1;
            --this.size;
            this.fixPointers(p_164495_);
            this.shiftKeys(p_164495_);
            if (this.size < this.maxFill / 4 && this.n > 16) {
                this.rehash(this.n / 2);
            }
            return true;
        }

        public long m_164485_() {
            if (this.size == 0) {
                throw new NoSuchElementException();
            }
            int $$0 = this.first;
            long $$1 = this.key[$$0];
            int $$2 = Long.numberOfTrailingZeros(this.value[$$0]);
            int n = $$0;
            this.value[n] = this.value[n] & (1L << $$2 ^ 0xFFFFFFFFFFFFFFFFL);
            if (this.value[$$0] == 0L) {
                this.removeFirstLong();
                this.f_164478_ = -1;
            }
            return InternalMap.m_164491_($$1, $$2);
        }

        protected void rehash(int p_164506_) {
            if (p_164506_ > this.f_164480_) {
                super.rehash(p_164506_);
            }
        }

        static {
            f_164475_ = f_164473_ = 64 - f_164471_ - f_164472_;
            f_164476_ = f_164473_ + f_164472_;
            f_164477_ = 3L << f_164476_ | 3L | 3L << f_164475_;
        }
    }
}

