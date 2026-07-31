/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.fixes.VillagerRebuildLevelAndXpFix;

public class ZombieVillagerRebuildXpFix
extends NamedEntityFix {
    public ZombieVillagerRebuildXpFix(Schema p_17298_, boolean p_17299_) {
        super(p_17298_, p_17299_, "Zombie Villager XP rebuild", References.f_16786_, "minecraft:zombie_villager");
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_17301_) {
        return p_17301_.update(DSL.remainderFinder(), p_17303_ -> {
            Optional $$1 = p_17303_.get("Xp").asNumber().result();
            if (!$$1.isPresent()) {
                int $$2 = p_17303_.get("VillagerData").get("level").asInt(1);
                return p_17303_.set("Xp", p_17303_.createInt(VillagerRebuildLevelAndXpFix.m_17079_($$2)));
            }
            return p_17303_;
        });
    }
}

