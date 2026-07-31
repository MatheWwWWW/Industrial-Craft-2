/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.world.level.block.state.pattern;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class BlockPatternBuilder {
    private static final Joiner f_61236_ = Joiner.on((String)",");
    private final List<String[]> f_61237_ = Lists.newArrayList();
    private final Map<Character, Predicate<BlockInWorld>> f_61238_ = Maps.newHashMap();
    private int f_61239_;
    private int f_61240_;

    private BlockPatternBuilder() {
        this.f_61238_.put(Character.valueOf(' '), p_187549_ -> true);
    }

    public BlockPatternBuilder m_61247_(String ... p_61248_) {
        if (ArrayUtils.isEmpty((Object[])p_61248_) || StringUtils.isEmpty((CharSequence)p_61248_[0])) {
            throw new IllegalArgumentException("Empty pattern for aisle");
        }
        if (this.f_61237_.isEmpty()) {
            this.f_61239_ = p_61248_.length;
            this.f_61240_ = p_61248_[0].length();
        }
        if (p_61248_.length != this.f_61239_) {
            throw new IllegalArgumentException("Expected aisle with height of " + this.f_61239_ + ", but was given one with a height of " + p_61248_.length + ")");
        }
        for (String $$1 : p_61248_) {
            if ($$1.length() != this.f_61240_) {
                throw new IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + this.f_61240_ + ", found one with " + $$1.length() + ")");
            }
            for (char $$2 : $$1.toCharArray()) {
                if (this.f_61238_.containsKey(Character.valueOf($$2))) continue;
                this.f_61238_.put(Character.valueOf($$2), null);
            }
        }
        this.f_61237_.add(p_61248_);
        return this;
    }

    public static BlockPatternBuilder m_61243_() {
        return new BlockPatternBuilder();
    }

    public BlockPatternBuilder m_61244_(char p_61245_, Predicate<BlockInWorld> p_61246_) {
        this.f_61238_.put(Character.valueOf(p_61245_), p_61246_);
        return this;
    }

    public BlockPattern m_61249_() {
        return new BlockPattern(this.m_61250_());
    }

    private Predicate<BlockInWorld>[][][] m_61250_() {
        this.m_61251_();
        Predicate[][][] $$0 = (Predicate[][][])Array.newInstance(Predicate.class, this.f_61237_.size(), this.f_61239_, this.f_61240_);
        for (int $$1 = 0; $$1 < this.f_61237_.size(); ++$$1) {
            for (int $$2 = 0; $$2 < this.f_61239_; ++$$2) {
                for (int $$3 = 0; $$3 < this.f_61240_; ++$$3) {
                    $$0[$$1][$$2][$$3] = this.f_61238_.get(Character.valueOf(this.f_61237_.get($$1)[$$2].charAt($$3)));
                }
            }
        }
        return $$0;
    }

    private void m_61251_() {
        ArrayList $$0 = Lists.newArrayList();
        for (Map.Entry<Character, Predicate<BlockInWorld>> $$1 : this.f_61238_.entrySet()) {
            if ($$1.getValue() != null) continue;
            $$0.add($$1.getKey());
        }
        if (!$$0.isEmpty()) {
            throw new IllegalStateException("Predicates for character(s) " + f_61236_.join((Iterable)$$0) + " are missing");
        }
    }
}

