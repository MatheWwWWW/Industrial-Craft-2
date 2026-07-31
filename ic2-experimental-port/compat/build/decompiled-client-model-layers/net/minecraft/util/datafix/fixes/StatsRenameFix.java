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
 *  com.mojang.datafixers.types.templates.TaggedChoice$TaggedChoiceType
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import java.util.Map;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class StatsRenameFix
extends DataFix {
    private final String f_145702_;
    private final Map<String, String> f_145703_;

    public StatsRenameFix(Schema p_145705_, String p_145706_, Map<String, String> p_145707_) {
        super(p_145705_, false);
        this.f_145702_ = p_145706_;
        this.f_145703_ = p_145707_;
    }

    protected TypeRewriteRule makeRule() {
        return TypeRewriteRule.seq((TypeRewriteRule)this.m_181057_(), (TypeRewriteRule)this.m_181042_());
    }

    private TypeRewriteRule m_181042_() {
        Type $$0 = this.getOutputSchema().getType(References.f_16791_);
        Type $$1 = this.getInputSchema().getType(References.f_16791_);
        OpticFinder $$2 = $$1.findField("CriteriaType");
        TaggedChoice.TaggedChoiceType $$3 = (TaggedChoice.TaggedChoiceType)$$2.type().findChoiceType("type", -1).orElseThrow(() -> new IllegalStateException("Can't find choice type for criteria"));
        Type $$4 = (Type)$$3.types().get("minecraft:custom");
        if ($$4 == null) {
            throw new IllegalStateException("Failed to find custom criterion type variant");
        }
        OpticFinder $$5 = DSL.namedChoice((String)"minecraft:custom", (Type)$$4);
        OpticFinder $$6 = DSL.fieldFinder((String)"id", NamespacedSchema.m_17310_());
        return this.fixTypeEverywhereTyped(this.f_145702_, $$1, $$0, p_181062_ -> p_181062_.updateTyped($$2, p_181066_ -> p_181066_.updateTyped($$5, p_181069_ -> p_181069_.update($$6, p_181071_ -> this.f_145703_.getOrDefault(p_181071_, (String)p_181071_)))));
    }

    private TypeRewriteRule m_181057_() {
        Type $$0 = this.getOutputSchema().getType(References.f_16777_);
        Type $$1 = this.getInputSchema().getType(References.f_16777_);
        OpticFinder $$2 = $$1.findField("stats");
        OpticFinder $$3 = $$2.type().findField("minecraft:custom");
        OpticFinder $$4 = NamespacedSchema.m_17310_().finder();
        return this.fixTypeEverywhereTyped(this.f_145702_, $$1, $$0, p_145712_ -> p_145712_.updateTyped($$2, p_145716_ -> p_145716_.updateTyped($$3, p_145719_ -> p_145719_.update($$4, p_145721_ -> this.f_145703_.getOrDefault(p_145721_, (String)p_145721_)))));
    }
}

