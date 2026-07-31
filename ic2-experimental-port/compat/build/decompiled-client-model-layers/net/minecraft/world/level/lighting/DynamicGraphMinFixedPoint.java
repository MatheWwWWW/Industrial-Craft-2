/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ByteMap
 *  it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongList
 */
package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongList;
import java.util.function.LongPredicate;
import net.minecraft.util.Mth;

public abstract class DynamicGraphMinFixedPoint {
    private static final int f_164422_ = 255;
    private final int f_75537_;
    private final LongLinkedOpenHashSet[] f_75538_;
    private final Long2ByteMap f_75539_;
    private int f_75540_;
    private volatile boolean f_75541_;

    protected DynamicGraphMinFixedPoint(int p_75543_, final int p_75544_, final int p_75545_) {
        if (p_75543_ >= 254) {
            throw new IllegalArgumentException("Level count must be < 254.");
        }
        this.f_75537_ = p_75543_;
        this.f_75538_ = new LongLinkedOpenHashSet[p_75543_];
        for (int $$3 = 0; $$3 < p_75543_; ++$$3) {
            this.f_75538_[$$3] = new LongLinkedOpenHashSet(p_75544_, 0.5f){

                protected void rehash(int p_75611_) {
                    if (p_75611_ > p_75544_) {
                        super.rehash(p_75611_);
                    }
                }
            };
        }
        this.f_75539_ = new Long2ByteOpenHashMap(p_75545_, 0.5f){

            protected void rehash(int p_75620_) {
                if (p_75620_ > p_75545_) {
                    super.rehash(p_75620_);
                }
            }
        };
        this.f_75539_.defaultReturnValue((byte)-1);
        this.f_75540_ = p_75543_;
    }

    private int m_75548_(int p_75549_, int p_75550_) {
        int $$2 = p_75549_;
        if ($$2 > p_75550_) {
            $$2 = p_75550_;
        }
        if ($$2 > this.f_75537_ - 1) {
            $$2 = this.f_75537_ - 1;
        }
        return $$2;
    }

    private void m_75546_(int p_75547_) {
        int $$1 = this.f_75540_;
        this.f_75540_ = p_75547_;
        for (int $$2 = $$1 + 1; $$2 < p_75547_; ++$$2) {
            if (this.f_75538_[$$2].isEmpty()) continue;
            this.f_75540_ = $$2;
            break;
        }
    }

    protected void m_75600_(long p_75601_) {
        int $$1 = this.f_75539_.get(p_75601_) & 0xFF;
        if ($$1 == 255) {
            return;
        }
        int $$2 = this.m_6172_(p_75601_);
        int $$3 = this.m_75548_($$2, $$1);
        this.m_75558_(p_75601_, $$3, this.f_75537_, true);
        this.f_75541_ = this.f_75540_ < this.f_75537_;
    }

    public void m_75581_(LongPredicate p_75582_) {
        LongArrayList $$1 = new LongArrayList();
        this.f_75539_.keySet().forEach(arg_0 -> DynamicGraphMinFixedPoint.m_75583_(p_75582_, (LongList)$$1, arg_0));
        $$1.forEach(this::m_75600_);
    }

    private void m_75558_(long p_75559_, int p_75560_, int p_75561_, boolean p_75562_) {
        if (p_75562_) {
            this.f_75539_.remove(p_75559_);
        }
        this.f_75538_[p_75560_].remove(p_75559_);
        if (this.f_75538_[p_75560_].isEmpty() && this.f_75540_ == p_75560_) {
            this.m_75546_(p_75561_);
        }
    }

    private void m_75554_(long p_75555_, int p_75556_, int p_75557_) {
        this.f_75539_.put(p_75555_, (byte)p_75556_);
        this.f_75538_[p_75557_].add(p_75555_);
        if (this.f_75540_ > p_75557_) {
            this.f_75540_ = p_75557_;
        }
    }

    protected void m_6185_(long p_75602_) {
        this.m_75576_(p_75602_, p_75602_, this.f_75537_ - 1, false);
    }

    protected void m_75576_(long p_75577_, long p_75578_, int p_75579_, boolean p_75580_) {
        this.m_75569_(p_75577_, p_75578_, p_75579_, this.m_6172_(p_75578_), this.f_75539_.get(p_75578_) & 0xFF, p_75580_);
        this.f_75541_ = this.f_75540_ < this.f_75537_;
    }

    private void m_75569_(long p_75570_, long p_75571_, int p_75572_, int p_75573_, int p_75574_, boolean p_75575_) {
        int $$9;
        boolean $$7;
        if (this.m_6163_(p_75571_)) {
            return;
        }
        p_75572_ = Mth.m_14045_(p_75572_, 0, this.f_75537_ - 1);
        p_75573_ = Mth.m_14045_(p_75573_, 0, this.f_75537_ - 1);
        if (p_75574_ == 255) {
            boolean $$6 = true;
            p_75574_ = p_75573_;
        } else {
            $$7 = false;
        }
        if (p_75575_) {
            int $$8 = Math.min(p_75574_, p_75572_);
        } else {
            $$9 = Mth.m_14045_(this.m_6357_(p_75571_, p_75570_, p_75572_), 0, this.f_75537_ - 1);
        }
        int $$10 = this.m_75548_(p_75573_, p_75574_);
        if (p_75573_ != $$9) {
            int $$11 = this.m_75548_(p_75573_, $$9);
            if ($$10 != $$11 && !$$7) {
                this.m_75558_(p_75571_, $$10, $$11, false);
            }
            this.m_75554_(p_75571_, $$9, $$11);
        } else if (!$$7) {
            this.m_75558_(p_75571_, $$10, this.f_75537_, true);
        }
    }

    protected final void m_75593_(long p_75594_, long p_75595_, int p_75596_, boolean p_75597_) {
        int $$4 = this.f_75539_.get(p_75595_) & 0xFF;
        int $$5 = Mth.m_14045_(this.m_6359_(p_75594_, p_75595_, p_75596_), 0, this.f_75537_ - 1);
        if (p_75597_) {
            this.m_75569_(p_75594_, p_75595_, $$5, this.m_6172_(p_75595_), $$4, true);
        } else {
            boolean $$9;
            int $$8;
            if ($$4 == 255) {
                boolean $$6 = true;
                int $$7 = Mth.m_14045_(this.m_6172_(p_75595_), 0, this.f_75537_ - 1);
            } else {
                $$8 = $$4;
                $$9 = false;
            }
            if ($$5 == $$8) {
                this.m_75569_(p_75594_, p_75595_, this.f_75537_ - 1, $$9 ? $$8 : this.m_6172_(p_75595_), $$4, false);
            }
        }
    }

    protected final boolean m_75587_() {
        return this.f_75541_;
    }

    protected final int m_75588_(int p_75589_) {
        if (this.f_75540_ >= this.f_75537_) {
            return p_75589_;
        }
        while (this.f_75540_ < this.f_75537_ && p_75589_ > 0) {
            int $$4;
            --p_75589_;
            LongLinkedOpenHashSet $$1 = this.f_75538_[this.f_75540_];
            long $$2 = $$1.removeFirstLong();
            int $$3 = Mth.m_14045_(this.m_6172_($$2), 0, this.f_75537_ - 1);
            if ($$1.isEmpty()) {
                this.m_75546_(this.f_75537_);
            }
            if (($$4 = this.f_75539_.remove($$2) & 0xFF) < $$3) {
                this.m_7351_($$2, $$4);
                this.m_7900_($$2, $$4, true);
                continue;
            }
            if ($$4 <= $$3) continue;
            this.m_75554_($$2, $$4, this.m_75548_(this.f_75537_ - 1, $$4));
            this.m_7351_($$2, this.f_75537_ - 1);
            this.m_7900_($$2, $$3, false);
        }
        this.f_75541_ = this.f_75540_ < this.f_75537_;
        return p_75589_;
    }

    public int m_75598_() {
        return this.f_75539_.size();
    }

    protected abstract boolean m_6163_(long var1);

    protected abstract int m_6357_(long var1, long var3, int var5);

    protected abstract void m_7900_(long var1, int var3, boolean var4);

    protected abstract int m_6172_(long var1);

    protected abstract void m_7351_(long var1, int var3);

    protected abstract int m_6359_(long var1, long var3, int var5);

    private static /* synthetic */ void m_75583_(LongPredicate p_75584_, LongList p_75585_, long p_75586_) {
        if (p_75584_.test(p_75586_)) {
            p_75585_.add(p_75586_);
        }
    }
}

