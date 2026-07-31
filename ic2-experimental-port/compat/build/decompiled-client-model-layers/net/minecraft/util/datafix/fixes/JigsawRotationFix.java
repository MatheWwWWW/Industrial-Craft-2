/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.References;

public class JigsawRotationFix
extends DataFix {
    private static final Map<String, String> f_145444_ = ImmutableMap.builder().put((Object)"down", (Object)"down_south").put((Object)"up", (Object)"up_north").put((Object)"north", (Object)"north_up").put((Object)"south", (Object)"south_up").put((Object)"west", (Object)"west_up").put((Object)"east", (Object)"east_up").build();

    public JigsawRotationFix(Schema p_16191_, boolean p_16192_) {
        super(p_16191_, p_16192_);
    }

    private static Dynamic<?> m_16195_(Dynamic<?> p_16196_) {
        Optional $$1 = p_16196_.get("Name").asString().result();
        if ($$1.equals(Optional.of("minecraft:jigsaw"))) {
            return p_16196_.update("Properties", p_16198_ -> {
                String $$1 = p_16198_.get("facing").asString("north");
                return p_16198_.remove("facing").set("orientation", p_16198_.createString(f_145444_.getOrDefault($$1, $$1)));
            });
        }
        return p_16196_;
    }

    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("jigsaw_rotation_fix", this.getInputSchema().getType(References.f_16783_), p_16194_ -> p_16194_.update(DSL.remainderFinder(), JigsawRotationFix::m_16195_));
    }
}

