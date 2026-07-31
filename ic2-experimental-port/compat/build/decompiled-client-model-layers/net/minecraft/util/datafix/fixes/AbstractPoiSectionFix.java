/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
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
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.util.datafix.fixes.References;

public abstract class AbstractPoiSectionFix
extends DataFix {
    private final String f_216534_;

    public AbstractPoiSectionFix(Schema p_216536_, String p_216537_) {
        super(p_216536_, false);
        this.f_216534_ = p_216537_;
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = DSL.named((String)References.f_16780_.typeName(), (Type)DSL.remainderType());
        if (!Objects.equals($$0, this.getInputSchema().getType(References.f_16780_))) {
            throw new IllegalStateException("Poi type is not what was expected.");
        }
        return this.fixTypeEverywhere(this.f_216534_, $$0, p_216546_ -> p_216549_ -> p_216549_.mapSecond(this::m_216540_));
    }

    private <T> Dynamic<T> m_216540_(Dynamic<T> p_216541_) {
        return p_216541_.update("Sections", p_216555_ -> p_216555_.updateMapValues(p_216539_ -> p_216539_.mapSecond(this::m_216550_)));
    }

    private Dynamic<?> m_216550_(Dynamic<?> p_216551_) {
        return p_216551_.update("Records", this::m_216552_);
    }

    private <T> Dynamic<T> m_216552_(Dynamic<T> p_216553_) {
        return (Dynamic)DataFixUtils.orElse(p_216553_.asStreamOpt().result().map(p_216544_ -> p_216553_.createList(this.m_213759_((Stream)p_216544_))), p_216553_);
    }

    protected abstract <T> Stream<Dynamic<T>> m_213759_(Stream<Dynamic<T>> var1);
}

