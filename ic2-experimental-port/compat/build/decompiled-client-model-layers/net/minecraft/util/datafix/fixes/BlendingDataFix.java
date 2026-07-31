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
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.SectionPos;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class BlendingDataFix
extends DataFix {
    private final String f_216557_;
    private static final Set<String> f_216558_ = Set.of("minecraft:empty", "minecraft:structure_starts", "minecraft:structure_references", "minecraft:biomes");

    public BlendingDataFix(Schema p_216561_) {
        super(p_216561_, false);
        this.f_216557_ = "Blending Data Fix v" + p_216561_.getVersionKey();
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getOutputSchema().getType(References.f_16773_);
        return this.fixTypeEverywhereTyped(this.f_216557_, $$0, p_216563_ -> p_216563_.update(DSL.remainderFinder(), p_240248_ -> BlendingDataFix.m_240278_(p_240248_, p_240248_.get("__context"))));
    }

    private static Dynamic<?> m_240278_(Dynamic<?> p_240279_, OptionalDynamic<?> p_240280_) {
        p_240279_ = p_240279_.remove("blending_data");
        boolean $$2 = "minecraft:overworld".equals(p_240280_.get("dimension").asString().result().orElse(""));
        Optional $$3 = p_240279_.get("Status").result();
        if ($$2 && $$3.isPresent()) {
            Dynamic $$6;
            String $$7;
            String $$4 = NamespacedSchema.m_17311_(((Dynamic)$$3.get()).asString("empty"));
            Optional $$5 = p_240279_.get("below_zero_retrogen").result();
            if (!f_216558_.contains($$4)) {
                p_240279_ = BlendingDataFix.m_216566_(p_240279_, 384, -64);
            } else if ($$5.isPresent() && !f_216558_.contains($$7 = NamespacedSchema.m_17311_(($$6 = (Dynamic)$$5.get()).get("target_status").asString("empty")))) {
                p_240279_ = BlendingDataFix.m_216566_(p_240279_, 256, 0);
            }
        }
        return p_240279_;
    }

    private static Dynamic<?> m_216566_(Dynamic<?> p_216567_, int p_216568_, int p_216569_) {
        return p_216567_.set("blending_data", p_216567_.createMap(Map.of(p_216567_.createString("min_section"), p_216567_.createInt(SectionPos.m_123171_(p_216569_)), p_216567_.createString("max_section"), p_216567_.createInt(SectionPos.m_123171_(p_216569_ + p_216568_)))));
    }
}

