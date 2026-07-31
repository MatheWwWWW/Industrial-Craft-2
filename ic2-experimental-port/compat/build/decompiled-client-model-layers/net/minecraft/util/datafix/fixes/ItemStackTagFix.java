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
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public abstract class ItemStackTagFix
extends DataFix {
    private final String f_216679_;
    private final Predicate<String> f_216680_;

    public ItemStackTagFix(Schema p_216682_, String p_216683_, Predicate<String> p_216684_) {
        super(p_216682_, false);
        this.f_216679_ = p_216683_;
        this.f_216680_ = p_216684_;
    }

    public final TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16782_);
        OpticFinder $$1 = DSL.fieldFinder((String)"id", (Type)DSL.named((String)References.f_16788_.typeName(), NamespacedSchema.m_17310_()));
        OpticFinder $$2 = $$0.findField("tag");
        return this.fixTypeEverywhereTyped(this.f_216679_, $$0, p_216688_ -> {
            Optional $$3 = p_216688_.getOptional($$1);
            if ($$3.isPresent() && this.f_216680_.test((String)((Pair)$$3.get()).getSecond())) {
                return p_216688_.updateTyped($$2, p_216690_ -> p_216690_.update(DSL.remainderFinder(), this::m_213922_));
            }
            return p_216688_;
        });
    }

    protected abstract <T> Dynamic<T> m_213922_(Dynamic<T> var1);
}

