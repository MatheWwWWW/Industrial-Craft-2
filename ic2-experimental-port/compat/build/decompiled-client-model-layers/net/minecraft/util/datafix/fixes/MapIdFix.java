/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Map;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.References;

public class MapIdFix
extends DataFix {
    public MapIdFix(Schema p_16396_, boolean p_16397_) {
        super(p_16396_, p_16397_);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16778_);
        OpticFinder $$1 = $$0.findField("data");
        return this.fixTypeEverywhereTyped("Map id fix", $$0, p_16400_ -> {
            Optional $$2 = p_16400_.getOptionalTyped($$1);
            if ($$2.isPresent()) {
                return p_16400_;
            }
            return p_16400_.update(DSL.remainderFinder(), p_145512_ -> p_145512_.createMap((Map)ImmutableMap.of((Object)p_145512_.createString("data"), (Object)p_145512_)));
        });
    }
}

