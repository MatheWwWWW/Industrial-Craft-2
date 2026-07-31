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
import java.util.Map;
import java.util.Objects;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class RenameBiomesFix
extends DataFix {
    private final String f_16834_;
    private final Map<String, String> f_16835_;

    public RenameBiomesFix(Schema p_16837_, boolean p_16838_, String p_16839_, Map<String, String> p_16840_) {
        super(p_16837_, p_16838_);
        this.f_16835_ = p_16840_;
        this.f_16834_ = p_16839_;
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = DSL.named((String)References.f_16794_.typeName(), NamespacedSchema.m_17310_());
        if (!Objects.equals($$0, this.getInputSchema().getType(References.f_16794_))) {
            throw new IllegalStateException("Biome type is not what was expected.");
        }
        return this.fixTypeEverywhere(this.f_16834_, $$0, p_16844_ -> p_145634_ -> p_145634_.mapSecond(p_145636_ -> this.f_16835_.getOrDefault(p_145636_, (String)p_145636_)));
    }
}

