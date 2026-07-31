/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 */
package net.minecraft.client.renderer.texture;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.renderer.texture.StitcherException;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;

public class Stitcher {
    private static final Comparator<Holder> f_118161_ = Comparator.comparing(p_118201_ -> -p_118201_.f_118204_).thenComparing(p_118199_ -> -p_118199_.f_118203_).thenComparing(p_118197_ -> p_118197_.f_118202_.m_118431_());
    private final int f_118162_;
    private final Set<Holder> f_118163_ = Sets.newHashSetWithExpectedSize((int)256);
    private final List<Region> f_118164_ = Lists.newArrayListWithCapacity((int)256);
    private int f_118165_;
    private int f_118166_;
    private final int f_118167_;
    private final int f_118168_;

    public Stitcher(int p_118171_, int p_118172_, int p_118173_) {
        this.f_118162_ = p_118173_;
        this.f_118167_ = p_118171_;
        this.f_118168_ = p_118172_;
    }

    public int m_118174_() {
        return this.f_118165_;
    }

    public int m_118187_() {
        return this.f_118166_;
    }

    public void m_118185_(TextureAtlasSprite.Info p_118186_) {
        Holder $$1 = new Holder(p_118186_, this.f_118162_);
        this.f_118163_.add($$1);
    }

    public void m_118193_() {
        ArrayList $$0 = Lists.newArrayList(this.f_118163_);
        $$0.sort(f_118161_);
        for (Holder $$1 : $$0) {
            if (this.m_118178_($$1)) continue;
            throw new StitcherException($$1.f_118202_, (Collection)$$0.stream().map(p_118195_ -> p_118195_.f_118202_).collect(ImmutableList.toImmutableList()));
        }
        this.f_118165_ = Mth.m_14125_(this.f_118165_);
        this.f_118166_ = Mth.m_14125_(this.f_118166_);
    }

    public void m_118180_(SpriteLoader p_118181_) {
        for (Region $$1 : this.f_118164_) {
            $$1.m_118223_(p_118184_ -> {
                Holder $$2 = p_118184_.m_118220_();
                TextureAtlasSprite.Info $$3 = $$2.f_118202_;
                p_118181_.m_118228_($$3, this.f_118165_, this.f_118166_, p_118184_.m_118225_(), p_118184_.m_118226_());
            });
        }
    }

    static int m_118188_(int p_118189_, int p_118190_) {
        return (p_118189_ >> p_118190_) + ((p_118189_ & (1 << p_118190_) - 1) == 0 ? 0 : 1) << p_118190_;
    }

    private boolean m_118178_(Holder p_118179_) {
        for (Region $$1 : this.f_118164_) {
            if (!$$1.m_118221_(p_118179_)) continue;
            return true;
        }
        return this.m_118191_(p_118179_);
    }

    private boolean m_118191_(Holder p_118192_) {
        Region $$12;
        boolean $$10;
        boolean $$8;
        boolean $$6;
        int $$1 = Mth.m_14125_(this.f_118165_);
        int $$2 = Mth.m_14125_(this.f_118166_);
        int $$3 = Mth.m_14125_(this.f_118165_ + p_118192_.f_118203_);
        int $$4 = Mth.m_14125_(this.f_118166_ + p_118192_.f_118204_);
        boolean $$5 = $$3 <= this.f_118167_;
        boolean bl = $$6 = $$4 <= this.f_118168_;
        if (!$$5 && !$$6) {
            return false;
        }
        boolean $$7 = $$5 && $$1 != $$3;
        boolean bl2 = $$8 = $$6 && $$2 != $$4;
        if ($$7 ^ $$8) {
            boolean $$9 = $$7;
        } else {
            boolean bl3 = $$10 = $$5 && $$1 <= $$2;
        }
        if ($$10) {
            if (this.f_118166_ == 0) {
                this.f_118166_ = p_118192_.f_118204_;
            }
            Region $$11 = new Region(this.f_118165_, 0, p_118192_.f_118203_, this.f_118166_);
            this.f_118165_ += p_118192_.f_118203_;
        } else {
            $$12 = new Region(0, this.f_118166_, this.f_118165_, p_118192_.f_118204_);
            this.f_118166_ += p_118192_.f_118204_;
        }
        $$12.m_118221_(p_118192_);
        this.f_118164_.add($$12);
        return true;
    }

    static class Holder {
        public final TextureAtlasSprite.Info f_118202_;
        public final int f_118203_;
        public final int f_118204_;

        public Holder(TextureAtlasSprite.Info p_118206_, int p_118207_) {
            this.f_118202_ = p_118206_;
            this.f_118203_ = Stitcher.m_118188_(p_118206_.m_118434_(), p_118207_);
            this.f_118204_ = Stitcher.m_118188_(p_118206_.m_118437_(), p_118207_);
        }

        public String toString() {
            return "Holder{width=" + this.f_118203_ + ", height=" + this.f_118204_ + "}";
        }
    }

    public static class Region {
        private final int f_118209_;
        private final int f_118210_;
        private final int f_118211_;
        private final int f_118212_;
        private List<Region> f_118213_;
        private Holder f_118214_;

        public Region(int p_118216_, int p_118217_, int p_118218_, int p_118219_) {
            this.f_118209_ = p_118216_;
            this.f_118210_ = p_118217_;
            this.f_118211_ = p_118218_;
            this.f_118212_ = p_118219_;
        }

        public Holder m_118220_() {
            return this.f_118214_;
        }

        public int m_118225_() {
            return this.f_118209_;
        }

        public int m_118226_() {
            return this.f_118210_;
        }

        public boolean m_118221_(Holder p_118222_) {
            if (this.f_118214_ != null) {
                return false;
            }
            int $$1 = p_118222_.f_118203_;
            int $$2 = p_118222_.f_118204_;
            if ($$1 > this.f_118211_ || $$2 > this.f_118212_) {
                return false;
            }
            if ($$1 == this.f_118211_ && $$2 == this.f_118212_) {
                this.f_118214_ = p_118222_;
                return true;
            }
            if (this.f_118213_ == null) {
                this.f_118213_ = Lists.newArrayListWithCapacity((int)1);
                this.f_118213_.add(new Region(this.f_118209_, this.f_118210_, $$1, $$2));
                int $$3 = this.f_118211_ - $$1;
                int $$4 = this.f_118212_ - $$2;
                if ($$4 > 0 && $$3 > 0) {
                    int $$6;
                    int $$5 = Math.max(this.f_118212_, $$3);
                    if ($$5 >= ($$6 = Math.max(this.f_118211_, $$4))) {
                        this.f_118213_.add(new Region(this.f_118209_, this.f_118210_ + $$2, $$1, $$4));
                        this.f_118213_.add(new Region(this.f_118209_ + $$1, this.f_118210_, $$3, this.f_118212_));
                    } else {
                        this.f_118213_.add(new Region(this.f_118209_ + $$1, this.f_118210_, $$3, $$2));
                        this.f_118213_.add(new Region(this.f_118209_, this.f_118210_ + $$2, this.f_118211_, $$4));
                    }
                } else if ($$3 == 0) {
                    this.f_118213_.add(new Region(this.f_118209_, this.f_118210_ + $$2, $$1, $$4));
                } else if ($$4 == 0) {
                    this.f_118213_.add(new Region(this.f_118209_ + $$1, this.f_118210_, $$3, $$2));
                }
            }
            for (Region $$7 : this.f_118213_) {
                if (!$$7.m_118221_(p_118222_)) continue;
                return true;
            }
            return false;
        }

        public void m_118223_(Consumer<Region> p_118224_) {
            if (this.f_118214_ != null) {
                p_118224_.accept(this);
            } else if (this.f_118213_ != null) {
                for (Region $$1 : this.f_118213_) {
                    $$1.m_118223_(p_118224_);
                }
            }
        }

        public String toString() {
            return "Slot{originX=" + this.f_118209_ + ", originY=" + this.f_118210_ + ", width=" + this.f_118211_ + ", height=" + this.f_118212_ + ", texture=" + this.f_118214_ + ", subSlots=" + this.f_118213_ + "}";
        }
    }

    public static interface SpriteLoader {
        public void m_118228_(TextureAtlasSprite.Info var1, int var2, int var3, int var4, int var5);
    }
}

