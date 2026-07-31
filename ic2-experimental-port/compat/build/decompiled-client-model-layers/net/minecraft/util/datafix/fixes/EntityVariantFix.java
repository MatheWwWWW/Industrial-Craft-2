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
 *  com.mojang.serialization.DynamicOps
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import net.minecraft.util.datafix.fixes.NamedEntityFix;

public class EntityVariantFix
extends NamedEntityFix {
    private final String f_216620_;
    private final IntFunction<String> f_216621_;

    public EntityVariantFix(Schema p_216623_, String p_216624_, DSL.TypeReference p_216625_, String p_216626_, String p_216627_, IntFunction<String> p_216628_) {
        super(p_216623_, false, p_216624_, p_216625_, p_216626_);
        this.f_216620_ = p_216627_;
        this.f_216621_ = p_216628_;
    }

    private static <T> Dynamic<T> m_216636_(Dynamic<T> p_216637_, String p_216638_, String p_216639_, Function<Dynamic<T>, Dynamic<T>> p_216640_) {
        return p_216637_.map(p_216646_ -> {
            DynamicOps $$5 = p_216637_.getOps();
            Function<Object, Object> $$6 = p_216656_ -> ((Dynamic)p_216640_.apply(new Dynamic($$5, p_216656_))).getValue();
            return $$5.get(p_216646_, p_216638_).map(p_216652_ -> $$5.set(p_216646_, p_216639_, $$6.apply(p_216652_))).result().orElse(p_216646_);
        });
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_216630_) {
        return p_216630_.update(DSL.remainderFinder(), p_216632_ -> EntityVariantFix.m_216636_(p_216632_, this.f_216620_, "variant", p_216658_ -> (Dynamic)DataFixUtils.orElse((Optional)p_216658_.asNumber().map(p_216635_ -> p_216658_.createString(this.f_216621_.apply(p_216635_.intValue()))).result(), (Object)p_216658_)));
    }
}

