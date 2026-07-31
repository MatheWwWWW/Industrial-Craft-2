/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public class VillagerFollowRangeFix
extends NamedEntityFix {
    private static final double f_145757_ = 16.0;
    private static final double f_145758_ = 48.0;

    public VillagerFollowRangeFix(Schema p_17064_) {
        super(p_17064_, false, "Villager Follow Range Fix", References.f_16786_, "minecraft:villager");
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_17066_) {
        return p_17066_.update(DSL.remainderFinder(), VillagerFollowRangeFix::m_17067_);
    }

    private static Dynamic<?> m_17067_(Dynamic<?> p_17068_) {
        return p_17068_.update("Attributes", p_17071_ -> p_17068_.createList(p_17071_.asStream().map(p_145760_ -> {
            if (!p_145760_.get("Name").asString("").equals("generic.follow_range") || p_145760_.get("Base").asDouble(0.0) != 16.0) {
                return p_145760_;
            }
            return p_145760_.set("Base", p_145760_.createDouble(48.0));
        })));
    }
}

