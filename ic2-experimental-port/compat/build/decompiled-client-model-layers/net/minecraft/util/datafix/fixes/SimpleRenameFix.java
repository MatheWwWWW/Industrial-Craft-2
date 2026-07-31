/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DSL$TypeReference
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
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class SimpleRenameFix
extends DataFix {
    private final String f_216721_;
    private final Map<String, String> f_216722_;
    private final DSL.TypeReference f_216723_;

    public SimpleRenameFix(Schema p_216730_, DSL.TypeReference p_216731_, Map<String, String> p_216732_) {
        this(p_216730_, p_216731_, p_216731_.typeName() + "-renames at version: " + p_216730_.getVersionKey(), p_216732_);
    }

    public SimpleRenameFix(Schema p_216725_, DSL.TypeReference p_216726_, String p_216727_, Map<String, String> p_216728_) {
        super(p_216725_, false);
        this.f_216722_ = p_216728_;
        this.f_216721_ = p_216727_;
        this.f_216723_ = p_216726_;
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = DSL.named((String)this.f_216723_.typeName(), NamespacedSchema.m_17310_());
        if (!Objects.equals($$0, this.getInputSchema().getType(this.f_216723_))) {
            throw new IllegalStateException("\"" + this.f_216723_.typeName() + "\" type is not what was expected.");
        }
        return this.fixTypeEverywhere(this.f_216721_, $$0, p_216736_ -> p_216734_ -> p_216734_.mapSecond(p_216738_ -> this.f_216722_.getOrDefault(p_216738_, (String)p_216738_)));
    }
}

