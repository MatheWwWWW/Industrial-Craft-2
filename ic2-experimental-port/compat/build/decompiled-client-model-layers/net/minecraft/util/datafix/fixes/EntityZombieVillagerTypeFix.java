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
import net.minecraft.util.RandomSource;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public class EntityZombieVillagerTypeFix
extends NamedEntityFix {
    private static final int f_145350_ = 6;
    private static final RandomSource f_15803_ = RandomSource.m_216327_();

    public EntityZombieVillagerTypeFix(Schema p_15806_, boolean p_15807_) {
        super(p_15806_, p_15807_, "EntityZombieVillagerTypeFix", References.f_16786_, "Zombie");
    }

    public Dynamic<?> m_15812_(Dynamic<?> p_15813_) {
        if (p_15813_.get("IsVillager").asBoolean(false)) {
            if (!p_15813_.get("ZombieType").result().isPresent()) {
                int $$1 = this.m_15808_(p_15813_.get("VillagerProfession").asInt(-1));
                if ($$1 == -1) {
                    $$1 = this.m_15808_(f_15803_.m_188503_(6));
                }
                p_15813_ = p_15813_.set("ZombieType", p_15813_.createInt($$1));
            }
            p_15813_ = p_15813_.remove("IsVillager");
        }
        return p_15813_;
    }

    private int m_15808_(int p_15809_) {
        if (p_15809_ < 0 || p_15809_ >= 6) {
            return -1;
        }
        return p_15809_;
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_15811_) {
        return p_15811_.update(DSL.remainderFinder(), this::m_15812_);
    }
}

