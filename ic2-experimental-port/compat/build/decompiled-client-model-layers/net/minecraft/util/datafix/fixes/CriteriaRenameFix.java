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
import java.util.function.UnaryOperator;
import net.minecraft.util.datafix.fixes.References;

public class CriteriaRenameFix
extends DataFix {
    private final String f_216581_;
    private final String f_216582_;
    private final UnaryOperator<String> f_216583_;

    public CriteriaRenameFix(Schema p_216585_, String p_216586_, String p_216587_, UnaryOperator<String> p_216588_) {
        super(p_216585_, false);
        this.f_216581_ = p_216586_;
        this.f_216582_ = p_216587_;
        this.f_216583_ = p_216588_;
    }

    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped(this.f_216581_, this.getInputSchema().getType(References.f_16779_), p_216590_ -> p_216590_.update(DSL.remainderFinder(), this::m_216593_));
    }

    private Dynamic<?> m_216593_(Dynamic<?> p_216594_) {
        return p_216594_.update(this.f_216582_, p_216599_ -> p_216599_.update("criteria", p_216601_ -> p_216601_.updateMapValues(p_216592_ -> p_216592_.mapFirst(p_216603_ -> (Dynamic)DataFixUtils.orElse((Optional)p_216603_.asString().map(p_216597_ -> p_216603_.createString((String)this.f_216583_.apply((String)p_216597_))).result(), (Object)p_216603_)))));
    }
}

