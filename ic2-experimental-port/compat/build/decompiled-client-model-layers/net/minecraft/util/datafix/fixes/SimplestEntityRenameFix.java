/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.TaggedChoice$TaggedChoiceType
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import java.util.Locale;
import java.util.Objects;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public abstract class SimplestEntityRenameFix
extends DataFix {
    private final String f_16909_;

    public SimplestEntityRenameFix(String p_16911_, Schema p_16912_, boolean p_16913_) {
        super(p_16912_, p_16913_);
        this.f_16909_ = p_16911_;
    }

    public TypeRewriteRule makeRule() {
        TaggedChoice.TaggedChoiceType $$0 = this.getInputSchema().findChoiceType(References.f_16786_);
        TaggedChoice.TaggedChoiceType $$1 = this.getOutputSchema().findChoiceType(References.f_16786_);
        Type $$2 = DSL.named((String)References.f_16784_.typeName(), NamespacedSchema.m_17310_());
        if (!Objects.equals(this.getOutputSchema().getType(References.f_16784_), $$2)) {
            throw new IllegalStateException("Entity name type is not what was expected.");
        }
        return TypeRewriteRule.seq((TypeRewriteRule)this.fixTypeEverywhere(this.f_16909_, (Type)$$0, (Type)$$1, p_16921_ -> p_145688_ -> p_145688_.mapFirst(p_145692_ -> {
            String $$3 = this.m_7476_((String)p_145692_);
            Type $$4 = (Type)$$0.types().get(p_145692_);
            Type $$5 = (Type)$$1.types().get($$3);
            if (!$$5.equals((Object)$$4, true, true)) {
                throw new IllegalStateException(String.format(Locale.ROOT, "Dynamic type check failed: %s not equal to %s", $$5, $$4));
            }
            return $$3;
        })), (TypeRewriteRule)this.fixTypeEverywhere(this.f_16909_ + " for entity name", $$2, p_16929_ -> p_145694_ -> p_145694_.mapSecond(this::m_7476_)));
    }

    protected abstract String m_7476_(String var1);
}

