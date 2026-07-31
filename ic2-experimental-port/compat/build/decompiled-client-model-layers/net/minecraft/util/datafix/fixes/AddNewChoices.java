/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL$TypeReference
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.TaggedChoice$TaggedChoiceType
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import java.util.Locale;

public class AddNewChoices
extends DataFix {
    private final String f_14625_;
    private final DSL.TypeReference f_14626_;

    public AddNewChoices(Schema p_14628_, String p_14629_, DSL.TypeReference p_14630_) {
        super(p_14628_, true);
        this.f_14625_ = p_14629_;
        this.f_14626_ = p_14630_;
    }

    public TypeRewriteRule makeRule() {
        TaggedChoice.TaggedChoiceType $$0 = this.getInputSchema().findChoiceType(this.f_14626_);
        TaggedChoice.TaggedChoiceType $$1 = this.getOutputSchema().findChoiceType(this.f_14626_);
        return this.m_14637_(this.f_14625_, $$0, $$1);
    }

    protected final <K> TypeRewriteRule m_14637_(String p_14638_, TaggedChoice.TaggedChoiceType<K> p_14639_, TaggedChoice.TaggedChoiceType<?> p_14640_) {
        if (p_14639_.getKeyType() != p_14640_.getKeyType()) {
            throw new IllegalStateException("Could not inject: key type is not the same");
        }
        TaggedChoice.TaggedChoiceType<?> $$3 = p_14640_;
        return this.fixTypeEverywhere(p_14638_, (Type)p_14639_, (Type)$$3, p_14636_ -> p_145061_ -> {
            if (!$$3.hasType(p_145061_.getFirst())) {
                throw new IllegalArgumentException(String.format(Locale.ROOT, "Unknown type %s in %s ", p_145061_.getFirst(), this.f_14626_));
            }
            return p_145061_;
        });
    }
}

