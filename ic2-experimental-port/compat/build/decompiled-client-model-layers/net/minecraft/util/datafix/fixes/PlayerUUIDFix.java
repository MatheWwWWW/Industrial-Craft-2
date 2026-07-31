/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.AbstractUUIDFix;
import net.minecraft.util.datafix.fixes.EntityUUIDFix;
import net.minecraft.util.datafix.fixes.References;

public class PlayerUUIDFix
extends AbstractUUIDFix {
    public PlayerUUIDFix(Schema p_16684_) {
        super(p_16684_, References.f_16772_);
    }

    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("PlayerUUIDFix", this.getInputSchema().getType(this.f_14569_), p_16686_ -> {
            OpticFinder $$1 = p_16686_.getType().findField("RootVehicle");
            return p_16686_.updateTyped($$1, $$1.type(), p_145597_ -> p_145597_.update(DSL.remainderFinder(), p_145601_ -> PlayerUUIDFix.m_14617_(p_145601_, "Attach", "Attach").orElse((Dynamic<?>)p_145601_))).update(DSL.remainderFinder(), p_145599_ -> EntityUUIDFix.m_15734_(EntityUUIDFix.m_15729_(p_145599_)));
        });
    }
}

