/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.EntityRenameFix;

public abstract class SimpleEntityRenameFix
extends EntityRenameFix {
    public SimpleEntityRenameFix(String p_16901_, Schema p_16902_, boolean p_16903_) {
        super(p_16901_, p_16902_, p_16903_);
    }

    @Override
    protected Pair<String, Typed<?>> m_6911_(String p_16905_, Typed<?> p_16906_) {
        Pair<String, Dynamic<?>> $$2 = this.m_6942_(p_16905_, (Dynamic)p_16906_.getOrCreate(DSL.remainderFinder()));
        return Pair.of((Object)((String)$$2.getFirst()), (Object)p_16906_.set(DSL.remainderFinder(), (Object)((Dynamic)$$2.getSecond())));
    }

    protected abstract Pair<String, Dynamic<?>> m_6942_(String var1, Dynamic<?> var2);
}

