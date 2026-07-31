/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.stream.Collectors;
import net.minecraft.util.datafix.fixes.References;

public class OptionsKeyTranslationFix
extends DataFix {
    public OptionsKeyTranslationFix(Schema p_16645_, boolean p_16646_) {
        super(p_16645_, p_16646_);
    }

    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("OptionsKeyTranslationFix", this.getInputSchema().getType(References.f_16775_), p_16648_ -> p_16648_.update(DSL.remainderFinder(), p_145582_ -> p_145582_.getMapValues().map(p_145588_ -> p_145582_.createMap(p_145588_.entrySet().stream().map(p_145585_ -> {
            String $$2;
            if (((Dynamic)p_145585_.getKey()).asString("").startsWith("key_") && !($$2 = ((Dynamic)p_145585_.getValue()).asString("")).startsWith("key.mouse") && !$$2.startsWith("scancode.")) {
                return Pair.of((Object)((Dynamic)p_145585_.getKey()), (Object)p_145582_.createString("key.keyboard." + $$2.substring("key.".length())));
            }
            return Pair.of((Object)((Dynamic)p_145585_.getKey()), (Object)((Dynamic)p_145585_.getValue()));
        }).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)))).result().orElse(p_145582_)));
    }
}

