/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import net.minecraft.util.datafix.fixes.References;

public class ForcePoiRebuild
extends DataFix {
    public ForcePoiRebuild(Schema p_15821_, boolean p_15822_) {
        super(p_15821_, p_15822_);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = DSL.named((String)References.f_16780_.typeName(), (Type)DSL.remainderType());
        if (!Objects.equals($$0, this.getInputSchema().getType(References.f_16780_))) {
            throw new IllegalStateException("Poi type is not what was expected.");
        }
        return this.fixTypeEverywhere("POI rebuild", $$0, p_15828_ -> p_145354_ -> p_145354_.mapSecond(ForcePoiRebuild::m_15825_));
    }

    private static <T> Dynamic<T> m_15825_(Dynamic<T> p_15826_) {
        return p_15826_.update("Sections", p_15832_ -> p_15832_.updateMapValues(p_145352_ -> p_145352_.mapSecond(p_145356_ -> p_145356_.remove("Valid"))));
    }
}

