/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DSL$TypeReference
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.NamedEntityFix;

public class VariantRenameFix
extends NamedEntityFix {
    private final Map<String, String> f_216740_;

    public VariantRenameFix(Schema p_216742_, String p_216743_, DSL.TypeReference p_216744_, String p_216745_, Map<String, String> p_216746_) {
        super(p_216742_, false, p_216743_, p_216744_, p_216745_);
        this.f_216740_ = p_216746_;
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_216748_) {
        return p_216748_.update(DSL.remainderFinder(), p_216750_ -> p_216750_.update("variant", p_216755_ -> (Dynamic)DataFixUtils.orElse((Optional)p_216755_.asString().map(p_216753_ -> p_216755_.createString(this.f_216740_.getOrDefault(p_216753_, (String)p_216753_))).result(), (Object)p_216755_)));
    }
}

