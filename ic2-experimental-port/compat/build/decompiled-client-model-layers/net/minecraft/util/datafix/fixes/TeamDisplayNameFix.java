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
import java.util.Objects;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.util.datafix.fixes.References;

public class TeamDisplayNameFix
extends DataFix {
    public TeamDisplayNameFix(Schema p_17001_, boolean p_17002_) {
        super(p_17001_, p_17002_);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = DSL.named((String)References.f_16792_.typeName(), (Type)DSL.remainderType());
        if (!Objects.equals($$0, this.getInputSchema().getType(References.f_16792_))) {
            throw new IllegalStateException("Team type is not what was expected.");
        }
        return this.fixTypeEverywhere("TeamDisplayNameFix", $$0, p_17011_ -> p_145726_ -> p_145726_.mapSecond(p_145728_ -> p_145728_.update("DisplayName", p_145731_ -> (Dynamic)DataFixUtils.orElse((Optional)p_145731_.asString().map(p_145733_ -> Component.Serializer.m_130703_(Component.m_237113_(p_145733_))).map(arg_0 -> ((Dynamic)p_145728_).createString(arg_0)).result(), (Object)p_145731_))));
    }
}

