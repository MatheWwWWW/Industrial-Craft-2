/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.References;

public class StructureReferenceCountFix
extends DataFix {
    public StructureReferenceCountFix(Schema p_16961_, boolean p_16962_) {
        super(p_16961_, p_16962_);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16790_);
        return this.fixTypeEverywhereTyped("Structure Reference Fix", $$0, p_16964_ -> p_16964_.update(DSL.remainderFinder(), StructureReferenceCountFix::m_16965_));
    }

    private static <T> Dynamic<T> m_16965_(Dynamic<T> p_16966_) {
        return p_16966_.update("references", p_16970_ -> p_16970_.createInt(p_16970_.asNumber().map(Number::intValue).result().filter(p_145724_ -> p_145724_ > 0).orElse(1).intValue()));
    }
}

