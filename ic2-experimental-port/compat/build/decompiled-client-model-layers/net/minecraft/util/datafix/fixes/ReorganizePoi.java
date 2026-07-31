/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.References;

public class ReorganizePoi
extends DataFix {
    public ReorganizePoi(Schema p_16853_, boolean p_16854_) {
        super(p_16853_, p_16854_);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = DSL.named((String)References.f_16780_.typeName(), (Type)DSL.remainderType());
        if (!Objects.equals($$0, this.getInputSchema().getType(References.f_16780_))) {
            throw new IllegalStateException("Poi type is not what was expected.");
        }
        return this.fixTypeEverywhere("POI reorganization", $$0, p_16860_ -> p_145640_ -> p_145640_.mapSecond(ReorganizePoi::m_16857_));
    }

    private static <T> Dynamic<T> m_16857_(Dynamic<T> p_16858_) {
        HashMap $$1 = Maps.newHashMap();
        for (int $$2 = 0; $$2 < 16; ++$$2) {
            String $$3 = String.valueOf($$2);
            Optional $$4 = p_16858_.get($$3).result();
            if (!$$4.isPresent()) continue;
            Dynamic $$5 = (Dynamic)$$4.get();
            Dynamic $$6 = p_16858_.createMap((Map)ImmutableMap.of((Object)p_16858_.createString("Records"), (Object)$$5));
            $$1.put(p_16858_.createInt($$2), $$6);
            p_16858_ = p_16858_.remove($$3);
        }
        return p_16858_.set("Sections", p_16858_.createMap((Map)$$1));
    }
}

