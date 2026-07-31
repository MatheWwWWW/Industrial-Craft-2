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
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.util.datafix.fixes.References;

public class ItemCustomNameToComponentFix
extends DataFix {
    public ItemCustomNameToComponentFix(Schema p_15927_, boolean p_15928_) {
        super(p_15927_, p_15928_);
    }

    private Dynamic<?> m_15934_(Dynamic<?> p_15935_) {
        Optional $$1 = p_15935_.get("display").result();
        if ($$1.isPresent()) {
            Dynamic $$2 = (Dynamic)$$1.get();
            Optional $$3 = $$2.get("Name").asString().result();
            if ($$3.isPresent()) {
                $$2 = $$2.set("Name", $$2.createString(Component.Serializer.m_130703_(Component.m_237113_((String)$$3.get()))));
            } else {
                Optional $$4 = $$2.get("LocName").asString().result();
                if ($$4.isPresent()) {
                    $$2 = $$2.set("Name", $$2.createString(Component.Serializer.m_130703_(Component.m_237115_((String)$$4.get()))));
                    $$2 = $$2.remove("LocName");
                }
            }
            return p_15935_.set("display", $$2);
        }
        return p_15935_;
    }

    public TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16782_);
        OpticFinder $$1 = $$0.findField("tag");
        return this.fixTypeEverywhereTyped("ItemCustomNameToComponentFix", $$0, p_15931_ -> p_15931_.updateTyped($$1, p_145384_ -> p_145384_.update(DSL.remainderFinder(), this::m_15934_)));
    }
}

