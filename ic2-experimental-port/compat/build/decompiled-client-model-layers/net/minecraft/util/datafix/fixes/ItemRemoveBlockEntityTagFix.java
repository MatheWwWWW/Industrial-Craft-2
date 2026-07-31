/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
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
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class ItemRemoveBlockEntityTagFix
extends DataFix {
    private final Set<String> f_242500_;

    public ItemRemoveBlockEntityTagFix(Schema p_242892_, boolean p_242905_, Set<String> p_242937_) {
        super(p_242892_, p_242905_);
        this.f_242500_ = p_242937_;
    }

    public TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16782_);
        OpticFinder $$1 = DSL.fieldFinder((String)"id", (Type)DSL.named((String)References.f_16788_.typeName(), NamespacedSchema.m_17310_()));
        OpticFinder $$2 = $$0.findField("tag");
        OpticFinder $$3 = $$2.type().findField("BlockEntityTag");
        return this.fixTypeEverywhereTyped("ItemRemoveBlockEntityTagFix", $$0, p_242866_ -> {
            Typed $$6;
            Optional $$7;
            Optional $$5;
            Optional $$4 = p_242866_.getOptional($$1);
            if ($$4.isPresent() && this.f_242500_.contains(((Pair)$$4.get()).getSecond()) && ($$5 = p_242866_.getOptionalTyped($$2)).isPresent() && ($$7 = ($$6 = (Typed)$$5.get()).getOptionalTyped($$3)).isPresent()) {
                Optional $$8 = $$6.write().result();
                Dynamic $$9 = $$8.isPresent() ? (Dynamic)$$8.get() : (Dynamic)$$6.get(DSL.remainderFinder());
                Dynamic $$10 = $$9.remove("BlockEntityTag");
                Optional $$11 = $$2.type().readTyped($$10).result();
                if ($$11.isEmpty()) {
                    return p_242866_;
                }
                return p_242866_.set($$2, (Typed)((Pair)$$11.get()).getFirst());
            }
            return p_242866_;
        });
    }
}

