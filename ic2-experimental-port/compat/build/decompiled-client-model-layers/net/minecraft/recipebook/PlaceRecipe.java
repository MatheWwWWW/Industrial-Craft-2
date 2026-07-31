/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.recipebook;

import java.util.Iterator;
import net.minecraft.util.Mth;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;

public interface PlaceRecipe<T> {
    default public void m_135408_(int p_135409_, int p_135410_, int p_135411_, Recipe<?> p_135412_, Iterator<T> p_135413_, int p_135414_) {
        int $$6 = p_135409_;
        int $$7 = p_135410_;
        if (p_135412_ instanceof ShapedRecipe) {
            ShapedRecipe $$8 = (ShapedRecipe)p_135412_;
            $$6 = $$8.m_44220_();
            $$7 = $$8.m_44221_();
        }
        int $$9 = 0;
        block0: for (int $$10 = 0; $$10 < p_135410_; ++$$10) {
            if ($$9 == p_135411_) {
                ++$$9;
            }
            boolean $$11 = (float)$$7 < (float)p_135410_ / 2.0f;
            int $$12 = Mth.m_14143_((float)p_135410_ / 2.0f - (float)$$7 / 2.0f);
            if ($$11 && $$12 > $$10) {
                $$9 += p_135409_;
                ++$$10;
            }
            for (int $$13 = 0; $$13 < p_135409_; ++$$13) {
                boolean $$15;
                if (!p_135413_.hasNext()) {
                    return;
                }
                $$11 = (float)$$6 < (float)p_135409_ / 2.0f;
                $$12 = Mth.m_14143_((float)p_135409_ / 2.0f - (float)$$6 / 2.0f);
                int $$14 = $$6;
                boolean bl = $$15 = $$13 < $$6;
                if ($$11) {
                    $$14 = $$12 + $$6;
                    boolean bl2 = $$15 = $$12 <= $$13 && $$13 < $$12 + $$6;
                }
                if ($$15) {
                    this.m_5817_(p_135413_, $$9, p_135414_, $$10, $$13);
                } else if ($$14 == $$13) {
                    $$9 += p_135409_ - $$13;
                    continue block0;
                }
                ++$$9;
            }
        }
    }

    public void m_5817_(Iterator<T> var1, int var2, int var3, int var4, int var5);
}

