/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.util.datafix.fixes.References;

public class ObjectiveDisplayNameFix
extends DataFix {
    public ObjectiveDisplayNameFix(Schema p_16521_, boolean p_16522_) {
        super(p_16521_, p_16522_);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16791_);
        return this.fixTypeEverywhereTyped("ObjectiveDisplayNameFix", $$0, p_181039_ -> p_181039_.update(DSL.remainderFinder(), p_145556_ -> p_145556_.update("DisplayName", p_145559_ -> (Dynamic)DataFixUtils.orElse((Optional)p_145559_.asString().map(p_145561_ -> Component.Serializer.m_130703_(Component.m_237113_(p_145561_))).map(arg_0 -> ((Dynamic)p_145556_).createString(arg_0)).result(), (Object)p_145559_))));
    }
}

