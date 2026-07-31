/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.References;

public class OptionsAddTextBackgroundFix
extends DataFix {
    public OptionsAddTextBackgroundFix(Schema p_16607_, boolean p_16608_) {
        super(p_16607_, p_16608_);
    }

    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("OptionsAddTextBackgroundFix", this.getInputSchema().getType(References.f_16775_), p_16610_ -> p_16610_.update(DSL.remainderFinder(), p_145567_ -> (Dynamic)DataFixUtils.orElse((Optional)p_145567_.get("chatOpacity").asString().map(p_145570_ -> p_145567_.set("textBackgroundOpacity", p_145567_.createDouble(this.m_16616_((String)p_145570_)))).result(), (Object)p_145567_)));
    }

    private double m_16616_(String p_16617_) {
        try {
            double $$1 = 0.9 * Double.parseDouble(p_16617_) + 0.1;
            return $$1 / 2.0;
        }
        catch (NumberFormatException $$2) {
            return 0.5;
        }
    }
}

