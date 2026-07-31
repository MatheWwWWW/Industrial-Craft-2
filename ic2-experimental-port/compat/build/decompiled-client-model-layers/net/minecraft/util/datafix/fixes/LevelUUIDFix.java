/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  org.slf4j.Logger
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.AbstractUUIDFix;
import net.minecraft.util.datafix.fixes.References;
import org.slf4j.Logger;

public class LevelUUIDFix
extends AbstractUUIDFix {
    private static final Logger f_201928_ = LogUtils.getLogger();

    public LevelUUIDFix(Schema p_16360_) {
        super(p_16360_, References.f_16771_);
    }

    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("LevelUUIDFix", this.getInputSchema().getType(this.f_14569_), p_16362_ -> p_16362_.updateTyped(DSL.remainderFinder(), p_145496_ -> p_145496_.update(DSL.remainderFinder(), p_145510_ -> {
            p_145510_ = this.m_16376_((Dynamic<?>)p_145510_);
            p_145510_ = this.m_16374_((Dynamic<?>)p_145510_);
            p_145510_ = this.m_16372_((Dynamic<?>)p_145510_);
            return p_145510_;
        })));
    }

    private Dynamic<?> m_16372_(Dynamic<?> p_16373_) {
        return LevelUUIDFix.m_14590_(p_16373_, "WanderingTraderId", "WanderingTraderId").orElse(p_16373_);
    }

    private Dynamic<?> m_16374_(Dynamic<?> p_16375_) {
        return p_16375_.update("DimensionData", p_16387_ -> p_16387_.updateMapValues(p_145498_ -> p_145498_.mapSecond(p_145506_ -> p_145506_.update("DragonFight", p_145508_ -> LevelUUIDFix.m_14617_(p_145508_, "DragonUUID", "Dragon").orElse((Dynamic<?>)p_145508_)))));
    }

    private Dynamic<?> m_16376_(Dynamic<?> p_16377_) {
        return p_16377_.update("CustomBossEvents", p_16379_ -> p_16379_.updateMapValues(p_145491_ -> p_145491_.mapSecond(p_145500_ -> p_145500_.update("Players", p_145494_ -> p_145500_.createList(p_145494_.asStream().map(p_145502_ -> LevelUUIDFix.m_14578_(p_145502_).orElseGet(() -> {
            f_201928_.warn("CustomBossEvents contains invalid UUIDs.");
            return p_145502_;
        })))))));
    }
}

