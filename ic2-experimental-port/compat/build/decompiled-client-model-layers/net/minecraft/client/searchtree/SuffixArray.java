/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.Arrays
 *  it.unimi.dsi.fastutil.Swapper
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntComparator
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  org.slf4j.Logger
 */
package net.minecraft.client.searchtree;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.Swapper;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import org.slf4j.Logger;

public class SuffixArray<T> {
    private static final boolean f_119957_ = Boolean.parseBoolean(System.getProperty("SuffixArray.printComparisons", "false"));
    private static final boolean f_119958_ = Boolean.parseBoolean(System.getProperty("SuffixArray.printArray", "false"));
    private static final Logger f_119959_ = LogUtils.getLogger();
    private static final int f_174963_ = -1;
    private static final int f_174964_ = -2;
    protected final List<T> f_119956_ = Lists.newArrayList();
    private final IntList f_119960_ = new IntArrayList();
    private final IntList f_119961_ = new IntArrayList();
    private IntList f_119962_ = new IntArrayList();
    private IntList f_119963_ = new IntArrayList();
    private int f_119964_;

    public void m_119970_(T p_119971_, String p_119972_) {
        this.f_119964_ = Math.max(this.f_119964_, p_119972_.length());
        int $$2 = this.f_119956_.size();
        this.f_119956_.add(p_119971_);
        this.f_119961_.add(this.f_119960_.size());
        for (int $$3 = 0; $$3 < p_119972_.length(); ++$$3) {
            this.f_119962_.add($$2);
            this.f_119963_.add($$3);
            this.f_119960_.add((int)p_119972_.charAt($$3));
        }
        this.f_119962_.add($$2);
        this.f_119963_.add(p_119972_.length());
        this.f_119960_.add(-1);
    }

    public void m_119967_() {
        int $$0 = this.f_119960_.size();
        int[] $$1 = new int[$$0];
        int[] $$2 = new int[$$0];
        int[] $$3 = new int[$$0];
        int[] $$4 = new int[$$0];
        IntComparator $$5 = (p_194458_, p_194459_) -> {
            if ($$2[p_194458_] == $$2[p_194459_]) {
                return Integer.compare($$3[p_194458_], $$3[p_194459_]);
            }
            return Integer.compare($$2[p_194458_], $$2[p_194459_]);
        };
        Swapper $$6 = (p_194464_, p_194465_) -> {
            if (p_194464_ != p_194465_) {
                int $$5 = $$2[p_194464_];
                p_194461_[p_194464_] = $$2[p_194465_];
                p_194461_[p_194465_] = $$5;
                $$5 = $$3[p_194464_];
                p_194462_[p_194464_] = $$3[p_194465_];
                p_194462_[p_194465_] = $$5;
                $$5 = $$4[p_194464_];
                p_194463_[p_194464_] = $$4[p_194465_];
                p_194463_[p_194465_] = $$5;
            }
        };
        for (int $$7 = 0; $$7 < $$0; ++$$7) {
            $$1[$$7] = this.f_119960_.getInt($$7);
        }
        int $$8 = 1;
        int $$9 = Math.min($$0, this.f_119964_);
        while ($$8 * 2 < $$9) {
            for (int $$10 = 0; $$10 < $$0; ++$$10) {
                $$2[$$10] = $$1[$$10];
                $$3[$$10] = $$10 + $$8 < $$0 ? $$1[$$10 + $$8] : -2;
                $$4[$$10] = $$10;
            }
            it.unimi.dsi.fastutil.Arrays.quickSort((int)0, (int)$$0, (IntComparator)$$5, (Swapper)$$6);
            for (int $$11 = 0; $$11 < $$0; ++$$11) {
                $$1[$$4[$$11]] = $$11 > 0 && $$2[$$11] == $$2[$$11 - 1] && $$3[$$11] == $$3[$$11 - 1] ? $$1[$$4[$$11 - 1]] : $$11;
            }
            $$8 *= 2;
        }
        IntList $$12 = this.f_119962_;
        IntList $$13 = this.f_119963_;
        this.f_119962_ = new IntArrayList($$12.size());
        this.f_119963_ = new IntArrayList($$13.size());
        for (int $$14 = 0; $$14 < $$0; ++$$14) {
            int $$15 = $$4[$$14];
            this.f_119962_.add($$12.getInt($$15));
            this.f_119963_.add($$13.getInt($$15));
        }
        if (f_119958_) {
            this.m_119984_();
        }
    }

    private void m_119984_() {
        for (int $$0 = 0; $$0 < this.f_119962_.size(); ++$$0) {
            f_119959_.debug("{} {}", (Object)$$0, (Object)this.m_119968_($$0));
        }
        f_119959_.debug("");
    }

    private String m_119968_(int p_119969_) {
        int $$1 = this.f_119963_.getInt(p_119969_);
        int $$2 = this.f_119961_.getInt(this.f_119962_.getInt(p_119969_));
        StringBuilder $$3 = new StringBuilder();
        int $$4 = 0;
        while ($$2 + $$4 < this.f_119960_.size()) {
            int $$5;
            if ($$4 == $$1) {
                $$3.append('^');
            }
            if (($$5 = this.f_119960_.getInt($$2 + $$4)) == -1) break;
            $$3.append((char)$$5);
            ++$$4;
        }
        return $$3.toString();
    }

    private int m_119975_(String p_119976_, int p_119977_) {
        int $$2 = this.f_119961_.getInt(this.f_119962_.getInt(p_119977_));
        int $$3 = this.f_119963_.getInt(p_119977_);
        for (int $$4 = 0; $$4 < p_119976_.length(); ++$$4) {
            char $$7;
            int $$5 = this.f_119960_.getInt($$2 + $$3 + $$4);
            if ($$5 == -1) {
                return 1;
            }
            char $$6 = p_119976_.charAt($$4);
            if ($$6 < ($$7 = (char)$$5)) {
                return -1;
            }
            if ($$6 <= $$7) continue;
            return 1;
        }
        return 0;
    }

    public List<T> m_119973_(String p_119974_) {
        int $$1 = this.f_119962_.size();
        int $$2 = 0;
        int $$3 = $$1;
        while ($$2 < $$3) {
            int $$4 = $$2 + ($$3 - $$2) / 2;
            int $$5 = this.m_119975_(p_119974_, $$4);
            if (f_119957_) {
                f_119959_.debug("comparing lower \"{}\" with {} \"{}\": {}", new Object[]{p_119974_, $$4, this.m_119968_($$4), $$5});
            }
            if ($$5 > 0) {
                $$2 = $$4 + 1;
                continue;
            }
            $$3 = $$4;
        }
        if ($$2 < 0 || $$2 >= $$1) {
            return Collections.emptyList();
        }
        int $$6 = $$2;
        $$3 = $$1;
        while ($$2 < $$3) {
            int $$7 = $$2 + ($$3 - $$2) / 2;
            int $$8 = this.m_119975_(p_119974_, $$7);
            if (f_119957_) {
                f_119959_.debug("comparing upper \"{}\" with {} \"{}\": {}", new Object[]{p_119974_, $$7, this.m_119968_($$7), $$8});
            }
            if ($$8 >= 0) {
                $$2 = $$7 + 1;
                continue;
            }
            $$3 = $$7;
        }
        int $$9 = $$2;
        IntOpenHashSet $$10 = new IntOpenHashSet();
        for (int $$11 = $$6; $$11 < $$9; ++$$11) {
            $$10.add(this.f_119962_.getInt($$11));
        }
        int[] $$12 = $$10.toIntArray();
        Arrays.sort($$12);
        LinkedHashSet $$13 = Sets.newLinkedHashSet();
        for (int $$14 : $$12) {
            $$13.add(this.f_119956_.get($$14));
        }
        return Lists.newArrayList((Iterable)$$13);
    }
}

