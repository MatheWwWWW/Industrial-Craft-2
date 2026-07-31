/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;
import net.minecraft.util.datafix.fixes.References;

public class ChunkBiomeFix
extends DataFix {
    public ChunkBiomeFix(Schema p_15014_, boolean p_15015_) {
        super(p_15014_, p_15015_);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16773_);
        OpticFinder $$1 = $$0.findField("Level");
        return this.fixTypeEverywhereTyped("Leaves fix", $$0, p_15018_ -> p_15018_.updateTyped($$1, p_145204_ -> p_145204_.update(DSL.remainderFinder(), p_145206_ -> {
            Optional $$1 = p_145206_.get("Biomes").asIntStreamOpt().result();
            if ($$1.isEmpty()) {
                return p_145206_;
            }
            int[] $$2 = ((IntStream)$$1.get()).toArray();
            if ($$2.length != 256) {
                return p_145206_;
            }
            int[] $$3 = new int[1024];
            for (int $$4 = 0; $$4 < 4; ++$$4) {
                for (int $$5 = 0; $$5 < 4; ++$$5) {
                    int $$6 = ($$5 << 2) + 2;
                    int $$7 = ($$4 << 2) + 2;
                    int $$8 = $$7 << 4 | $$6;
                    $$3[$$4 << 2 | $$5] = $$2[$$8];
                }
            }
            for (int $$9 = 1; $$9 < 64; ++$$9) {
                System.arraycopy($$3, 0, $$3, $$9 * 16, 16);
            }
            return p_145206_.set("Biomes", p_145206_.createIntList(Arrays.stream($$3)));
        })));
    }
}

