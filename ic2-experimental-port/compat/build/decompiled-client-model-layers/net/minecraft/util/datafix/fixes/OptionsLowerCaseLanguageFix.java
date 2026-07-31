/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.References;

public class OptionsLowerCaseLanguageFix
extends DataFix {
    public OptionsLowerCaseLanguageFix(Schema p_16659_, boolean p_16660_) {
        super(p_16659_, p_16660_);
    }

    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("OptionsLowerCaseLanguageFix", this.getInputSchema().getType(References.f_16775_), p_16662_ -> p_16662_.update(DSL.remainderFinder(), p_145590_ -> {
            Optional $$1 = p_145590_.get("lang").asString().result();
            if ($$1.isPresent()) {
                return p_145590_.set("lang", p_145590_.createString(((String)$$1.get()).toLowerCase(Locale.ROOT)));
            }
            return p_145590_;
        }));
    }
}

