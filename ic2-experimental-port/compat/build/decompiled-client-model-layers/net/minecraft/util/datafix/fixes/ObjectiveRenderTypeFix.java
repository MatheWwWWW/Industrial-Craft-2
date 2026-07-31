/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class ObjectiveRenderTypeFix
extends DataFix {
    public ObjectiveRenderTypeFix(Schema p_16536_, boolean p_16537_) {
        super(p_16536_, p_16537_);
    }

    private static ObjectiveCriteria.RenderType m_16544_(String p_16545_) {
        return p_16545_.equals("health") ? ObjectiveCriteria.RenderType.HEARTS : ObjectiveCriteria.RenderType.INTEGER;
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16791_);
        return this.fixTypeEverywhereTyped("ObjectiveRenderTypeFix", $$0, p_181041_ -> p_181041_.update(DSL.remainderFinder(), p_145565_ -> {
            Optional $$1 = p_145565_.get("RenderType").asString().result();
            if (!$$1.isPresent()) {
                String $$2 = p_145565_.get("CriteriaName").asString("");
                ObjectiveCriteria.RenderType $$3 = ObjectiveRenderTypeFix.m_16544_($$2);
                return p_145565_.set("RenderType", p_145565_.createString($$3.m_83633_()));
            }
            return p_145565_;
        }));
    }
}

