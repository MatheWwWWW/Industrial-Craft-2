/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.References;

public class CauldronRenameFix
extends DataFix {
    public CauldronRenameFix(Schema p_145196_, boolean p_145197_) {
        super(p_145196_, p_145197_);
    }

    private static Dynamic<?> m_145200_(Dynamic<?> p_145201_) {
        Optional $$1 = p_145201_.get("Name").asString().result();
        if ($$1.equals(Optional.of("minecraft:cauldron"))) {
            Dynamic $$2 = p_145201_.get("Properties").orElseEmptyMap();
            if ($$2.get("level").asString("0").equals("0")) {
                return p_145201_.remove("Properties");
            }
            return p_145201_.set("Name", p_145201_.createString("minecraft:water_cauldron"));
        }
        return p_145201_;
    }

    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("cauldron_rename_fix", this.getInputSchema().getType(References.f_16783_), p_145199_ -> p_145199_.update(DSL.remainderFinder(), CauldronRenameFix::m_145200_));
    }
}

