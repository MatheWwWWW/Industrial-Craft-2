/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.References;

public class StructureSettingsFlattenFix
extends DataFix {
    public StructureSettingsFlattenFix(Schema p_204000_) {
        super(p_204000_, false);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16795_);
        OpticFinder $$1 = $$0.findField("dimensions");
        return this.fixTypeEverywhereTyped("StructureSettingsFlatten", $$0, p_204003_ -> p_204003_.updateTyped($$1, p_204016_ -> {
            Dynamic $$2 = (Dynamic)p_204016_.write().result().orElseThrow();
            Dynamic $$3 = $$2.updateMapValues(StructureSettingsFlattenFix::m_204004_);
            return (Typed)((Pair)$$1.type().readTyped($$3).result().orElseThrow()).getFirst();
        }));
    }

    private static Pair<Dynamic<?>, Dynamic<?>> m_204004_(Pair<Dynamic<?>, Dynamic<?>> p_204005_) {
        Dynamic $$1 = (Dynamic)p_204005_.getSecond();
        return Pair.of((Object)((Dynamic)p_204005_.getFirst()), (Object)$$1.update("generator", p_204018_ -> p_204018_.update("settings", p_204020_ -> p_204020_.update("structures", StructureSettingsFlattenFix::m_204006_))));
    }

    private static Dynamic<?> m_204006_(Dynamic<?> p_204007_) {
        Dynamic $$1 = p_204007_.get("structures").orElseEmptyMap().updateMapValues(p_204010_ -> p_204010_.mapSecond(p_204013_ -> p_204013_.set("type", p_204007_.createString("minecraft:random_spread"))));
        return (Dynamic)DataFixUtils.orElse(p_204007_.get("stronghold").result().map(p_207675_ -> $$1.set("minecraft:stronghold", p_207675_.set("type", p_204007_.createString("minecraft:concentric_rings")))), (Object)$$1);
    }
}

