/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.ints.Int2IntMap
 *  it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntAVLTreeSet
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntIterator
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntListIterator
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.player;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class StackedContents {
    private static final int f_150116_ = 0;
    public final Int2IntMap f_36451_ = new Int2IntOpenHashMap();

    public void m_36466_(ItemStack p_36467_) {
        if (!(p_36467_.m_41768_() || p_36467_.m_41793_() || p_36467_.m_41788_())) {
            this.m_36491_(p_36467_);
        }
    }

    public void m_36491_(ItemStack p_36492_) {
        this.m_36468_(p_36492_, 64);
    }

    public void m_36468_(ItemStack p_36469_, int p_36470_) {
        if (!p_36469_.m_41619_()) {
            int $$2 = StackedContents.m_36496_(p_36469_);
            int $$3 = Math.min(p_36470_, p_36469_.m_41613_());
            this.m_36484_($$2, $$3);
        }
    }

    public static int m_36496_(ItemStack p_36497_) {
        return Registry.f_122827_.m_7447_(p_36497_.m_41720_());
    }

    boolean m_36482_(int p_36483_) {
        return this.f_36451_.get(p_36483_) > 0;
    }

    int m_36456_(int p_36457_, int p_36458_) {
        int $$2 = this.f_36451_.get(p_36457_);
        if ($$2 >= p_36458_) {
            this.f_36451_.put(p_36457_, $$2 - p_36458_);
            return p_36457_;
        }
        return 0;
    }

    void m_36484_(int p_36485_, int p_36486_) {
        this.f_36451_.put(p_36485_, this.f_36451_.get(p_36485_) + p_36486_);
    }

    public boolean m_36475_(Recipe<?> p_36476_, @Nullable IntList p_36477_) {
        return this.m_36478_(p_36476_, p_36477_, 1);
    }

    public boolean m_36478_(Recipe<?> p_36479_, @Nullable IntList p_36480_, int p_36481_) {
        return new RecipePicker(p_36479_).m_36512_(p_36481_, p_36480_);
    }

    public int m_36493_(Recipe<?> p_36494_, @Nullable IntList p_36495_) {
        return this.m_36471_(p_36494_, Integer.MAX_VALUE, p_36495_);
    }

    public int m_36471_(Recipe<?> p_36472_, int p_36473_, @Nullable IntList p_36474_) {
        return new RecipePicker(p_36472_).m_36525_(p_36473_, p_36474_);
    }

    public static ItemStack m_36454_(int p_36455_) {
        if (p_36455_ == 0) {
            return ItemStack.f_41583_;
        }
        return new ItemStack(Item.m_41445_(p_36455_));
    }

    public void m_36453_() {
        this.f_36451_.clear();
    }

    class RecipePicker {
        private final Recipe<?> f_36499_;
        private final List<Ingredient> f_36500_ = Lists.newArrayList();
        private final int f_36501_;
        private final int[] f_36502_;
        private final int f_36503_;
        private final BitSet f_36504_;
        private final IntList f_36505_ = new IntArrayList();

        public RecipePicker(Recipe<?> p_36508_) {
            this.f_36499_ = p_36508_;
            this.f_36500_.addAll(p_36508_.m_7527_());
            this.f_36500_.removeIf(Ingredient::m_43947_);
            this.f_36501_ = this.f_36500_.size();
            this.f_36502_ = this.m_36509_();
            this.f_36503_ = this.f_36502_.length;
            this.f_36504_ = new BitSet(this.f_36501_ + this.f_36503_ + this.f_36501_ + this.f_36501_ * this.f_36503_);
            for (int $$1 = 0; $$1 < this.f_36500_.size(); ++$$1) {
                IntList $$2 = this.f_36500_.get($$1).m_43931_();
                for (int $$3 = 0; $$3 < this.f_36503_; ++$$3) {
                    if (!$$2.contains(this.f_36502_[$$3])) continue;
                    this.f_36504_.set(this.m_36546_(true, $$3, $$1));
                }
            }
        }

        public boolean m_36512_(int p_36513_, @Nullable IntList p_36514_) {
            boolean $$6;
            if (p_36513_ <= 0) {
                return true;
            }
            int $$2 = 0;
            while (this.m_36510_(p_36513_)) {
                StackedContents.this.m_36456_(this.f_36502_[this.f_36505_.getInt(0)], p_36513_);
                int $$3 = this.f_36505_.size() - 1;
                this.m_36535_(this.f_36505_.getInt($$3));
                for (int $$4 = 0; $$4 < $$3; ++$$4) {
                    this.m_36540_(($$4 & 1) == 0, this.f_36505_.get($$4), this.f_36505_.get($$4 + 1));
                }
                this.f_36505_.clear();
                this.f_36504_.clear(0, this.f_36501_ + this.f_36503_);
                ++$$2;
            }
            boolean $$5 = $$2 == this.f_36501_;
            boolean bl = $$6 = $$5 && p_36514_ != null;
            if ($$6) {
                p_36514_.clear();
            }
            this.f_36504_.clear(0, this.f_36501_ + this.f_36503_ + this.f_36501_);
            int $$7 = 0;
            NonNullList<Ingredient> $$8 = this.f_36499_.m_7527_();
            for (int $$9 = 0; $$9 < $$8.size(); ++$$9) {
                if ($$6 && ((Ingredient)$$8.get($$9)).m_43947_()) {
                    p_36514_.add(0);
                    continue;
                }
                for (int $$10 = 0; $$10 < this.f_36503_; ++$$10) {
                    if (!this.m_36531_(false, $$7, $$10)) continue;
                    this.m_36540_(true, $$10, $$7);
                    StackedContents.this.m_36484_(this.f_36502_[$$10], p_36513_);
                    if (!$$6) continue;
                    p_36514_.add(this.f_36502_[$$10]);
                }
                ++$$7;
            }
            return $$5;
        }

        private int[] m_36509_() {
            IntAVLTreeSet $$0 = new IntAVLTreeSet();
            for (Ingredient $$1 : this.f_36500_) {
                $$0.addAll((IntCollection)$$1.m_43931_());
            }
            IntIterator $$2 = $$0.iterator();
            while ($$2.hasNext()) {
                if (StackedContents.this.m_36482_($$2.nextInt())) continue;
                $$2.remove();
            }
            return $$0.toIntArray();
        }

        private boolean m_36510_(int p_36511_) {
            int $$1 = this.f_36503_;
            for (int $$2 = 0; $$2 < $$1; ++$$2) {
                if (StackedContents.this.f_36451_.get(this.f_36502_[$$2]) < p_36511_) continue;
                this.m_36515_(false, $$2);
                while (!this.f_36505_.isEmpty()) {
                    int $$8;
                    int $$3 = this.f_36505_.size();
                    boolean $$4 = ($$3 & 1) == 1;
                    int $$5 = this.f_36505_.getInt($$3 - 1);
                    if (!$$4 && !this.m_36523_($$5)) break;
                    int $$6 = $$4 ? this.f_36501_ : $$1;
                    for (int $$7 = 0; $$7 < $$6; ++$$7) {
                        if (this.m_36528_($$4, $$7) || !this.m_36518_($$4, $$5, $$7) || !this.m_36531_($$4, $$5, $$7)) continue;
                        this.m_36515_($$4, $$7);
                        break;
                    }
                    if (($$8 = this.f_36505_.size()) != $$3) continue;
                    this.f_36505_.removeInt($$8 - 1);
                }
                if (this.f_36505_.isEmpty()) continue;
                return true;
            }
            return false;
        }

        private boolean m_36523_(int p_36524_) {
            return this.f_36504_.get(this.m_36544_(p_36524_));
        }

        private void m_36535_(int p_36536_) {
            this.f_36504_.set(this.m_36544_(p_36536_));
        }

        private int m_36544_(int p_36545_) {
            return this.f_36501_ + this.f_36503_ + p_36545_;
        }

        private boolean m_36518_(boolean p_36519_, int p_36520_, int p_36521_) {
            return this.f_36504_.get(this.m_36546_(p_36519_, p_36520_, p_36521_));
        }

        private boolean m_36531_(boolean p_36532_, int p_36533_, int p_36534_) {
            return p_36532_ != this.f_36504_.get(1 + this.m_36546_(p_36532_, p_36533_, p_36534_));
        }

        private void m_36540_(boolean p_36541_, int p_36542_, int p_36543_) {
            this.f_36504_.flip(1 + this.m_36546_(p_36541_, p_36542_, p_36543_));
        }

        private int m_36546_(boolean p_36547_, int p_36548_, int p_36549_) {
            int $$3 = p_36547_ ? p_36548_ * this.f_36501_ + p_36549_ : p_36549_ * this.f_36501_ + p_36548_;
            return this.f_36501_ + this.f_36503_ + this.f_36501_ + 2 * $$3;
        }

        private void m_36515_(boolean p_36516_, int p_36517_) {
            this.f_36504_.set(this.m_36537_(p_36516_, p_36517_));
            this.f_36505_.add(p_36517_);
        }

        private boolean m_36528_(boolean p_36529_, int p_36530_) {
            return this.f_36504_.get(this.m_36537_(p_36529_, p_36530_));
        }

        private int m_36537_(boolean p_36538_, int p_36539_) {
            return (p_36538_ ? 0 : this.f_36501_) + p_36539_;
        }

        public int m_36525_(int p_36526_, @Nullable IntList p_36527_) {
            int $$4;
            int $$2 = 0;
            int $$3 = Math.min(p_36526_, this.m_36522_()) + 1;
            while (true) {
                if (this.m_36512_($$4 = ($$2 + $$3) / 2, null)) {
                    if ($$3 - $$2 <= 1) break;
                    $$2 = $$4;
                    continue;
                }
                $$3 = $$4;
            }
            if ($$4 > 0) {
                this.m_36512_($$4, p_36527_);
            }
            return $$4;
        }

        private int m_36522_() {
            int $$0 = Integer.MAX_VALUE;
            for (Ingredient $$1 : this.f_36500_) {
                int $$2 = 0;
                IntListIterator intListIterator = $$1.m_43931_().iterator();
                while (intListIterator.hasNext()) {
                    int $$3 = (Integer)intListIterator.next();
                    $$2 = Math.max($$2, StackedContents.this.f_36451_.get($$3));
                }
                if ($$0 <= 0) continue;
                $$0 = Math.min($$0, $$2);
            }
            return $$0;
        }
    }
}

