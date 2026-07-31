/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.TaggedChoice$TaggedChoiceType
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.DynamicOps
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import java.util.Locale;
import net.minecraft.util.datafix.fixes.References;

public abstract class EntityRenameFix
extends DataFix {
    protected final String f_15616_;

    public EntityRenameFix(String p_15618_, Schema p_15619_, boolean p_15620_) {
        super(p_15619_, p_15620_);
        this.f_15616_ = p_15618_;
    }

    public TypeRewriteRule makeRule() {
        TaggedChoice.TaggedChoiceType $$0 = this.getInputSchema().findChoiceType(References.f_16786_);
        TaggedChoice.TaggedChoiceType $$1 = this.getOutputSchema().findChoiceType(References.f_16786_);
        return this.fixTypeEverywhere(this.f_15616_, (Type)$$0, (Type)$$1, p_15624_ -> p_145311_ -> {
            String $$4 = (String)p_145311_.getFirst();
            Type $$5 = (Type)$$0.types().get($$4);
            Pair<String, Typed<?>> $$6 = this.m_6911_($$4, this.m_15630_(p_145311_.getSecond(), (DynamicOps<?>)p_15624_, (Type)$$5));
            Type $$7 = (Type)$$1.types().get($$6.getFirst());
            if (!$$7.equals((Object)((Typed)$$6.getSecond()).getType(), true, true)) {
                throw new IllegalStateException(String.format(Locale.ROOT, "Dynamic type check failed: %s not equal to %s", $$7, ((Typed)$$6.getSecond()).getType()));
            }
            return Pair.of((Object)((String)$$6.getFirst()), (Object)((Typed)$$6.getSecond()).getValue());
        });
    }

    private <A> Typed<A> m_15630_(Object p_15631_, DynamicOps<?> p_15632_, Type<A> p_15633_) {
        return new Typed(p_15633_, p_15632_, p_15631_);
    }

    protected abstract Pair<String, Typed<?>> m_6911_(String var1, Typed<?> var2);
}

