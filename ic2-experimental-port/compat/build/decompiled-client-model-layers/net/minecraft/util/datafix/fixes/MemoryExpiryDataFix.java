/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public class MemoryExpiryDataFix
extends NamedEntityFix {
    public MemoryExpiryDataFix(Schema p_16405_, String p_16406_) {
        super(p_16405_, false, "Memory expiry data fix (" + p_16406_ + ")", References.f_16786_, p_16406_);
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_16408_) {
        return p_16408_.update(DSL.remainderFinder(), this::m_16411_);
    }

    public Dynamic<?> m_16411_(Dynamic<?> p_16412_) {
        return p_16412_.update("Brain", this::m_16413_);
    }

    private Dynamic<?> m_16413_(Dynamic<?> p_16414_) {
        return p_16414_.update("memories", this::m_16415_);
    }

    private Dynamic<?> m_16415_(Dynamic<?> p_16416_) {
        return p_16416_.updateMapValues(this::m_16409_);
    }

    private Pair<Dynamic<?>, Dynamic<?>> m_16409_(Pair<Dynamic<?>, Dynamic<?>> p_16410_) {
        return p_16410_.mapSecond(this::m_16417_);
    }

    private Dynamic<?> m_16417_(Dynamic<?> p_16418_) {
        return p_16418_.createMap((Map)ImmutableMap.of((Object)p_16418_.createString("value"), p_16418_));
    }
}

