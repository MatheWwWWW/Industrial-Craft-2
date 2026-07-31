/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.AbstractUUIDFix;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class ItemStackUUIDFix
extends AbstractUUIDFix {
    public ItemStackUUIDFix(Schema p_16129_) {
        super(p_16129_, References.f_16782_);
    }

    public TypeRewriteRule makeRule() {
        OpticFinder $$0 = DSL.fieldFinder((String)"id", (Type)DSL.named((String)References.f_16788_.typeName(), NamespacedSchema.m_17310_()));
        return this.fixTypeEverywhereTyped("ItemStackUUIDFix", this.getInputSchema().getType(this.f_14569_), p_16132_ -> {
            OpticFinder $$2 = p_16132_.getType().findField("tag");
            return p_16132_.updateTyped($$2, p_145429_ -> p_145429_.update(DSL.remainderFinder(), p_145433_ -> {
                p_145433_ = this.m_16146_((Dynamic<?>)p_145433_);
                if (p_16132_.getOptional($$0).map(p_145435_ -> "minecraft:player_head".equals(p_145435_.getSecond())).orElse(false).booleanValue()) {
                    p_145433_ = this.m_16148_((Dynamic<?>)p_145433_);
                }
                return p_145433_;
            }));
        });
    }

    private Dynamic<?> m_16146_(Dynamic<?> p_16147_) {
        return p_16147_.update("AttributeModifiers", p_16145_ -> p_16147_.createList(p_16145_.asStream().map(p_145437_ -> ItemStackUUIDFix.m_14617_(p_145437_, "UUID", "UUID").orElse((Dynamic<?>)p_145437_))));
    }

    private Dynamic<?> m_16148_(Dynamic<?> p_16149_) {
        return p_16149_.update("SkullOwner", p_16151_ -> ItemStackUUIDFix.m_14590_(p_16151_, "Id", "Id").orElse((Dynamic<?>)p_16151_));
    }
}

