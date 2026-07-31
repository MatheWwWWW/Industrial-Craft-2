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
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public class WeaponSmithChestLootTableFix
extends NamedEntityFix {
    public WeaponSmithChestLootTableFix(Schema p_203111_, boolean p_203112_) {
        super(p_203111_, p_203112_, "WeaponSmithChestLootTableFix", References.f_16781_, "minecraft:chest");
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_203114_) {
        return p_203114_.update(DSL.remainderFinder(), p_203116_ -> {
            String $$1 = p_203116_.get("LootTable").asString("");
            return $$1.equals("minecraft:chests/village_blacksmith") ? p_203116_.set("LootTable", p_203116_.createString("minecraft:chests/village/village_weaponsmith")) : p_203116_;
        });
    }
}

