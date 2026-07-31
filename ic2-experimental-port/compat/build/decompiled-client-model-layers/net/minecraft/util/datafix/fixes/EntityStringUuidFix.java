/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.util.datafix.fixes.References;

public class EntityStringUuidFix
extends DataFix {
    public EntityStringUuidFix(Schema p_15694_, boolean p_15695_) {
        super(p_15694_, p_15695_);
    }

    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("EntityStringUuidFix", this.getInputSchema().getType(References.f_16786_), p_15697_ -> p_15697_.update(DSL.remainderFinder(), p_145331_ -> {
            Optional $$1 = p_145331_.get("UUID").asString().result();
            if ($$1.isPresent()) {
                UUID $$2 = UUID.fromString((String)$$1.get());
                return p_145331_.remove("UUID").set("UUIDMost", p_145331_.createLong($$2.getMostSignificantBits())).set("UUIDLeast", p_145331_.createLong($$2.getLeastSignificantBits()));
            }
            return p_145331_;
        }));
    }
}

