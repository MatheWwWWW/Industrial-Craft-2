/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class EntityCustomNameToComponentFix
extends DataFix {
    public EntityCustomNameToComponentFix(Schema p_15398_, boolean p_15399_) {
        super(p_15398_, p_15399_);
    }

    public TypeRewriteRule makeRule() {
        OpticFinder $$0 = DSL.fieldFinder((String)"id", NamespacedSchema.m_17310_());
        return this.fixTypeEverywhereTyped("EntityCustomNameToComponentFix", this.getInputSchema().getType(References.f_16786_), p_15402_ -> p_15402_.update(DSL.remainderFinder(), p_145277_ -> {
            Optional $$3 = p_15402_.getOptional($$0);
            if ($$3.isPresent() && Objects.equals($$3.get(), "minecraft:commandblock_minecart")) {
                return p_145277_;
            }
            return EntityCustomNameToComponentFix.m_15407_(p_145277_);
        }));
    }

    public static Dynamic<?> m_15407_(Dynamic<?> p_15408_) {
        String $$1 = p_15408_.get("CustomName").asString("");
        if ($$1.isEmpty()) {
            return p_15408_.remove("CustomName");
        }
        return p_15408_.set("CustomName", p_15408_.createString(Component.Serializer.m_130703_(Component.m_237113_($$1))));
    }
}

