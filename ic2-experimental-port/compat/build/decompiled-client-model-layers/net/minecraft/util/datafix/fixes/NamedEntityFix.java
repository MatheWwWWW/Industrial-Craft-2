/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DSL$TypeReference
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;

public abstract class NamedEntityFix
extends DataFix {
    private final String f_16461_;
    private final String f_16462_;
    private final DSL.TypeReference f_16463_;

    public NamedEntityFix(Schema p_16465_, boolean p_16466_, String p_16467_, DSL.TypeReference p_16468_, String p_16469_) {
        super(p_16465_, p_16466_);
        this.f_16461_ = p_16467_;
        this.f_16463_ = p_16468_;
        this.f_16462_ = p_16469_;
    }

    public TypeRewriteRule makeRule() {
        OpticFinder $$0 = DSL.namedChoice((String)this.f_16462_, (Type)this.getInputSchema().getChoiceType(this.f_16463_, this.f_16462_));
        return this.fixTypeEverywhereTyped(this.f_16461_, this.getInputSchema().getType(this.f_16463_), this.getOutputSchema().getType(this.f_16463_), p_16472_ -> p_16472_.updateTyped($$0, this.getOutputSchema().getChoiceType(this.f_16463_, this.f_16462_), this::m_7504_));
    }

    protected abstract Typed<?> m_7504_(Typed<?> var1);
}

