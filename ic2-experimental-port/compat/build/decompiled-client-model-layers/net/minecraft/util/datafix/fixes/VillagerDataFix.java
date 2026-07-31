/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public class VillagerDataFix
extends NamedEntityFix {
    public VillagerDataFix(Schema p_17056_, String p_17057_) {
        super(p_17056_, false, "Villager profession data fix (" + p_17057_ + ")", References.f_16786_, p_17057_);
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_17062_) {
        Dynamic $$1 = (Dynamic)p_17062_.get(DSL.remainderFinder());
        return p_17062_.set(DSL.remainderFinder(), (Object)$$1.remove("Profession").remove("Career").remove("CareerLevel").set("VillagerData", $$1.createMap((Map)ImmutableMap.of((Object)$$1.createString("type"), (Object)$$1.createString("minecraft:plains"), (Object)$$1.createString("profession"), (Object)$$1.createString(VillagerDataFix.m_17058_($$1.get("Profession").asInt(0), $$1.get("Career").asInt(0))), (Object)$$1.createString("level"), (Object)((Dynamic)DataFixUtils.orElse((Optional)$$1.get("CareerLevel").result(), (Object)$$1.createInt(1)))))));
    }

    private static String m_17058_(int p_17059_, int p_17060_) {
        if (p_17059_ == 0) {
            if (p_17060_ == 2) {
                return "minecraft:fisherman";
            }
            if (p_17060_ == 3) {
                return "minecraft:shepherd";
            }
            if (p_17060_ == 4) {
                return "minecraft:fletcher";
            }
            return "minecraft:farmer";
        }
        if (p_17059_ == 1) {
            if (p_17060_ == 2) {
                return "minecraft:cartographer";
            }
            return "minecraft:librarian";
        }
        if (p_17059_ == 2) {
            return "minecraft:cleric";
        }
        if (p_17059_ == 3) {
            if (p_17060_ == 2) {
                return "minecraft:weaponsmith";
            }
            if (p_17060_ == 3) {
                return "minecraft:toolsmith";
            }
            return "minecraft:armorer";
        }
        if (p_17059_ == 4) {
            if (p_17060_ == 2) {
                return "minecraft:leatherworker";
            }
            return "minecraft:butcher";
        }
        if (p_17059_ == 5) {
            return "minecraft:nitwit";
        }
        return "minecraft:none";
    }
}

