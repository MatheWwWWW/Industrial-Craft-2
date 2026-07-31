/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.util.datafix.fixes.AbstractPoiSectionFix;

public class PoiTypeRemoveFix
extends AbstractPoiSectionFix {
    private final Predicate<String> f_216699_;

    public PoiTypeRemoveFix(Schema p_216701_, String p_216702_, Predicate<String> p_216703_) {
        super(p_216701_, p_216702_);
        this.f_216699_ = p_216703_.negate();
    }

    @Override
    protected <T> Stream<Dynamic<T>> m_213759_(Stream<Dynamic<T>> p_216707_) {
        return p_216707_.filter(this::m_216704_);
    }

    private <T> boolean m_216704_(Dynamic<T> p_216705_) {
        return p_216705_.get("type").asString().result().filter(this.f_216699_).isPresent();
    }
}

