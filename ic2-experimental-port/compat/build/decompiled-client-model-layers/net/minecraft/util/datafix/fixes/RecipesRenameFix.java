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
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class RecipesRenameFix
extends DataFix {
    private final String f_16729_;
    private final Function<String, String> f_16730_;

    public RecipesRenameFix(Schema p_16732_, boolean p_16733_, String p_16734_, Function<String, String> p_16735_) {
        super(p_16732_, p_16733_);
        this.f_16729_ = p_16734_;
        this.f_16730_ = p_16735_;
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = DSL.named((String)References.f_16793_.typeName(), NamespacedSchema.m_17310_());
        if (!Objects.equals($$0, this.getInputSchema().getType(References.f_16793_))) {
            throw new IllegalStateException("Recipe type is not what was expected.");
        }
        return this.fixTypeEverywhere(this.f_16729_, $$0, p_16739_ -> p_145615_ -> p_145615_.mapSecond(this.f_16730_));
    }
}

