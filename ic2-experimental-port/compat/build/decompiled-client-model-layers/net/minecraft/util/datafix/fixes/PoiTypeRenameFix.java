/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.datafix.fixes.AbstractPoiSectionFix;

public class PoiTypeRenameFix
extends AbstractPoiSectionFix {
    private final Function<String, String> f_216708_;

    public PoiTypeRenameFix(Schema p_216710_, String p_216711_, Function<String, String> p_216712_) {
        super(p_216710_, p_216711_);
        this.f_216708_ = p_216712_;
    }

    @Override
    protected <T> Stream<Dynamic<T>> m_213759_(Stream<Dynamic<T>> p_216716_) {
        return p_216716_.map(p_216714_ -> p_216714_.update("type", p_216718_ -> (Dynamic)DataFixUtils.orElse((Optional)p_216718_.asString().map(this.f_216708_).map(arg_0 -> ((Dynamic)p_216718_).createString(arg_0)).result(), (Object)p_216718_)));
    }
}

