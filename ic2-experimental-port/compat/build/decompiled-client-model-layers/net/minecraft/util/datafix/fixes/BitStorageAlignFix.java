/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.List$ListType
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.stream.LongStream;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.fixes.References;

public class BitStorageAlignFix
extends DataFix {
    private static final int f_145092_ = 6;
    private static final int f_145093_ = 16;
    private static final int f_145094_ = 16;
    private static final int f_145095_ = 4096;
    private static final int f_145096_ = 9;
    private static final int f_145097_ = 256;

    public BitStorageAlignFix(Schema p_14736_) {
        super(p_14736_, false);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16773_);
        Type $$1 = $$0.findFieldType("Level");
        OpticFinder $$2 = DSL.fieldFinder((String)"Level", (Type)$$1);
        OpticFinder $$3 = $$2.type().findField("Sections");
        Type $$4 = ((List.ListType)$$3.type()).getElement();
        OpticFinder $$5 = DSL.typeFinder((Type)$$4);
        Type $$6 = DSL.named((String)References.f_16783_.typeName(), (Type)DSL.remainderType());
        OpticFinder $$7 = DSL.fieldFinder((String)"Palette", (Type)DSL.list((Type)$$6));
        return this.fixTypeEverywhereTyped("BitStorageAlignFix", $$0, this.getOutputSchema().getType(References.f_16773_), p_14749_ -> p_14749_.updateTyped($$2, p_145120_ -> this.m_14762_(BitStorageAlignFix.m_14750_($$3, $$5, $$7, p_145120_))));
    }

    private Typed<?> m_14762_(Typed<?> p_14763_) {
        return p_14763_.update(DSL.remainderFinder(), p_14765_ -> p_14765_.update("Heightmaps", p_145113_ -> p_145113_.updateMapValues(p_145110_ -> p_145110_.mapSecond(p_145123_ -> BitStorageAlignFix.m_14776_(p_14765_, p_145123_, 256, 9)))));
    }

    private static Typed<?> m_14750_(OpticFinder<?> p_14751_, OpticFinder<?> p_14752_, OpticFinder<List<Pair<String, Dynamic<?>>>> p_14753_, Typed<?> p_14754_) {
        return p_14754_.updateTyped(p_14751_, p_14758_ -> p_14758_.updateTyped(p_14752_, p_145103_ -> {
            int $$2 = p_145103_.getOptional(p_14753_).map(p_145115_ -> Math.max(4, DataFixUtils.ceillog2((int)p_145115_.size()))).orElse(0);
            if ($$2 == 0 || Mth.m_14152_($$2)) {
                return p_145103_;
            }
            return p_145103_.update(DSL.remainderFinder(), p_145100_ -> p_145100_.update("BlockStates", p_145107_ -> BitStorageAlignFix.m_14776_(p_145100_, p_145107_, 4096, $$2)));
        }));
    }

    private static Dynamic<?> m_14776_(Dynamic<?> p_14777_, Dynamic<?> p_14778_, int p_14779_, int p_14780_) {
        long[] $$4 = p_14778_.asLongStream().toArray();
        long[] $$5 = BitStorageAlignFix.m_14737_(p_14779_, p_14780_, $$4);
        return p_14777_.createLongList(LongStream.of($$5));
    }

    public static long[] m_14737_(int p_14738_, int p_14739_, long[] p_14740_) {
        int $$3 = p_14740_.length;
        if ($$3 == 0) {
            return p_14740_;
        }
        long $$4 = (1L << p_14739_) - 1L;
        int $$5 = 64 / p_14739_;
        int $$6 = (p_14738_ + $$5 - 1) / $$5;
        long[] $$7 = new long[$$6];
        int $$8 = 0;
        int $$9 = 0;
        long $$10 = 0L;
        int $$11 = 0;
        long $$12 = p_14740_[0];
        long $$13 = $$3 > 1 ? p_14740_[1] : 0L;
        for (int $$14 = 0; $$14 < p_14738_; ++$$14) {
            long $$21;
            int $$15 = $$14 * p_14739_;
            int $$16 = $$15 >> 6;
            int $$17 = ($$14 + 1) * p_14739_ - 1 >> 6;
            int $$18 = $$15 ^ $$16 << 6;
            if ($$16 != $$11) {
                $$12 = $$13;
                $$13 = $$16 + 1 < $$3 ? p_14740_[$$16 + 1] : 0L;
                $$11 = $$16;
            }
            if ($$16 == $$17) {
                long $$19 = $$12 >>> $$18 & $$4;
            } else {
                int $$20 = 64 - $$18;
                $$21 = ($$12 >>> $$18 | $$13 << $$20) & $$4;
            }
            int $$22 = $$9 + p_14739_;
            if ($$22 >= 64) {
                $$7[$$8++] = $$10;
                $$10 = $$21;
                $$9 = p_14739_;
                continue;
            }
            $$10 |= $$21 << $$9;
            $$9 = $$22;
        }
        if ($$10 != 0L) {
            $$7[$$8] = $$10;
        }
        return $$7;
    }
}

