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
 *  com.mojang.serialization.OptionalDynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import net.minecraft.util.datafix.fixes.References;

public class BlendingDataRemoveFromNetherEndFix
extends DataFix {
    public BlendingDataRemoveFromNetherEndFix(Schema p_240321_) {
        super(p_240321_, false);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getOutputSchema().getType(References.f_16773_);
        return this.fixTypeEverywhereTyped("BlendingDataRemoveFromNetherEndFix", $$0, p_240286_ -> p_240286_.update(DSL.remainderFinder(), p_240254_ -> BlendingDataRemoveFromNetherEndFix.m_240317_(p_240254_, p_240254_.get("__context"))));
    }

    private static Dynamic<?> m_240317_(Dynamic<?> p_240318_, OptionalDynamic<?> p_240319_) {
        boolean $$2 = "minecraft:overworld".equals(p_240319_.get("dimension").asString().result().orElse(""));
        return $$2 ? p_240318_ : p_240318_.remove("blending_data");
    }
}

