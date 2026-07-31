/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DSL$TypeReference
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

public class AddFlagIfNotPresentFix
extends DataFix {
    private final String f_184805_;
    private final boolean f_184806_;
    private final String f_184807_;
    private final DSL.TypeReference f_184808_;

    public AddFlagIfNotPresentFix(Schema p_184810_, DSL.TypeReference p_184811_, String p_184812_, boolean p_184813_) {
        super(p_184810_, true);
        this.f_184806_ = p_184813_;
        this.f_184807_ = p_184812_;
        this.f_184805_ = "AddFlagIfNotPresentFix_" + this.f_184807_ + "=" + this.f_184806_ + " for " + p_184810_.getVersionKey();
        this.f_184808_ = p_184811_;
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(this.f_184808_);
        return this.fixTypeEverywhereTyped(this.f_184805_, $$0, p_184815_ -> p_184815_.update(DSL.remainderFinder(), p_184817_ -> p_184817_.set(this.f_184807_, (Dynamic)DataFixUtils.orElseGet((Optional)p_184817_.get(this.f_184807_).result(), () -> p_184817_.createBoolean(this.f_184806_)))));
    }
}

