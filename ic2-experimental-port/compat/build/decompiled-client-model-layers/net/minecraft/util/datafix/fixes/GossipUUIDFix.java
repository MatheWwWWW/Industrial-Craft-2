/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
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
import net.minecraft.util.datafix.fixes.AbstractUUIDFix;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public class GossipUUIDFix
extends NamedEntityFix {
    public GossipUUIDFix(Schema p_15878_, String p_15879_) {
        super(p_15878_, false, "Gossip for for " + p_15879_, References.f_16786_, p_15879_);
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_15881_) {
        return p_15881_.update(DSL.remainderFinder(), p_15883_ -> p_15883_.update("Gossips", p_145376_ -> (Dynamic)DataFixUtils.orElse(p_145376_.asStreamOpt().result().map(p_145374_ -> p_145374_.map(p_145378_ -> AbstractUUIDFix.m_14617_(p_145378_, "Target", "Target").orElse((Dynamic<?>)p_145378_))).map(arg_0 -> ((Dynamic)p_145376_).createList(arg_0)), (Object)p_145376_)));
    }
}

