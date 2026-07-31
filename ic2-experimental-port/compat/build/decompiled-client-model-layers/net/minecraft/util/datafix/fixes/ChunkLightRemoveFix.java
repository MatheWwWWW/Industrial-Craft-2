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
import net.minecraft.util.datafix.fixes.References;

public class ChunkLightRemoveFix
extends DataFix {
    public ChunkLightRemoveFix(Schema p_15025_, boolean p_15026_) {
        super(p_15025_, p_15026_);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16773_);
        Type $$1 = $$0.findFieldType("Level");
        OpticFinder $$2 = DSL.fieldFinder((String)"Level", (Type)$$1);
        return this.fixTypeEverywhereTyped("ChunkLightRemoveFix", $$0, this.getOutputSchema().getType(References.f_16773_), p_15029_ -> p_15029_.updateTyped($$2, p_145208_ -> p_145208_.update(DSL.remainderFinder(), p_145210_ -> p_145210_.remove("isLightOn"))));
    }
}

