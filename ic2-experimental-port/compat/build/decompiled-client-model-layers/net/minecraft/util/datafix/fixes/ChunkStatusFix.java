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
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import net.minecraft.util.datafix.fixes.References;

public class ChunkStatusFix
extends DataFix {
    public ChunkStatusFix(Schema p_15247_, boolean p_15248_) {
        super(p_15247_, p_15248_);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16773_);
        Type $$1 = $$0.findFieldType("Level");
        OpticFinder $$2 = DSL.fieldFinder((String)"Level", (Type)$$1);
        return this.fixTypeEverywhereTyped("ChunkStatusFix", $$0, this.getOutputSchema().getType(References.f_16773_), p_15251_ -> p_15251_.updateTyped($$2, p_145230_ -> {
            Dynamic $$1 = (Dynamic)p_145230_.get(DSL.remainderFinder());
            String $$2 = $$1.get("Status").asString("empty");
            if (Objects.equals($$2, "postprocessed")) {
                $$1 = $$1.set("Status", $$1.createString("fullchunk"));
            }
            return p_145230_.set(DSL.remainderFinder(), (Object)$$1);
        }));
    }
}

