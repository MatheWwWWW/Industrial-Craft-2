/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.util.datafix.fixes.References;

public class MobSpawnerEntityIdentifiersFix
extends DataFix {
    public MobSpawnerEntityIdentifiersFix(Schema p_16451_, boolean p_16452_) {
        super(p_16451_, p_16452_);
    }

    private Dynamic<?> m_16456_(Dynamic<?> p_16457_) {
        Optional $$3;
        if (!"MobSpawner".equals(p_16457_.get("id").asString(""))) {
            return p_16457_;
        }
        Optional $$1 = p_16457_.get("EntityId").asString().result();
        if ($$1.isPresent()) {
            Dynamic $$2 = (Dynamic)DataFixUtils.orElse((Optional)p_16457_.get("SpawnData").result(), (Object)p_16457_.emptyMap());
            $$2 = $$2.set("id", $$2.createString(((String)$$1.get()).isEmpty() ? "Pig" : (String)$$1.get()));
            p_16457_ = p_16457_.set("SpawnData", $$2);
            p_16457_ = p_16457_.remove("EntityId");
        }
        if (($$3 = p_16457_.get("SpawnPotentials").asStreamOpt().result()).isPresent()) {
            p_16457_ = p_16457_.set("SpawnPotentials", p_16457_.createList(((Stream)$$3.get()).map(p_16459_ -> {
                Optional $$1 = p_16459_.get("Type").asString().result();
                if ($$1.isPresent()) {
                    Dynamic $$2 = ((Dynamic)DataFixUtils.orElse((Optional)p_16459_.get("Properties").result(), (Object)p_16459_.emptyMap())).set("id", p_16459_.createString((String)$$1.get()));
                    return p_16459_.set("Entity", $$2).remove("Type").remove("Properties");
                }
                return p_16459_;
            })));
        }
        return p_16457_;
    }

    public TypeRewriteRule makeRule() {
        Type $$0 = this.getOutputSchema().getType(References.f_16789_);
        return this.fixTypeEverywhereTyped("MobSpawnerEntityIdentifiersFix", this.getInputSchema().getType(References.f_16789_), $$0, p_16455_ -> {
            Dynamic $$2 = (Dynamic)p_16455_.get(DSL.remainderFinder());
            DataResult $$3 = $$0.readTyped(this.m_16456_($$2 = $$2.set("id", $$2.createString("MobSpawner"))));
            if (!$$3.result().isPresent()) {
                return p_16455_;
            }
            return (Typed)((Pair)$$3.result().get()).getFirst();
        });
    }
}

