/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.TypeTemplate
 */
package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class V1125
extends NamespacedSchema {
    public V1125(int p_17391_, Schema p_17392_) {
        super(p_17391_, p_17392_);
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_17398_) {
        Map $$1 = super.registerBlockEntities(p_17398_);
        p_17398_.registerSimple($$1, "minecraft:bed");
        return $$1;
    }

    public void registerTypes(Schema p_17400_, Map<String, Supplier<TypeTemplate>> p_17401_, Map<String, Supplier<TypeTemplate>> p_17402_) {
        super.registerTypes(p_17400_, p_17401_, p_17402_);
        p_17400_.registerType(false, References.f_16779_, () -> DSL.optionalFields((String)"minecraft:adventure/adventuring_time", (TypeTemplate)DSL.optionalFields((String)"criteria", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16794_.in(p_17400_), (TypeTemplate)DSL.constType((Type)DSL.string()))), (String)"minecraft:adventure/kill_a_mob", (TypeTemplate)DSL.optionalFields((String)"criteria", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16784_.in(p_17400_), (TypeTemplate)DSL.constType((Type)DSL.string()))), (String)"minecraft:adventure/kill_all_mobs", (TypeTemplate)DSL.optionalFields((String)"criteria", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16784_.in(p_17400_), (TypeTemplate)DSL.constType((Type)DSL.string()))), (String)"minecraft:husbandry/bred_all_animals", (TypeTemplate)DSL.optionalFields((String)"criteria", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16784_.in(p_17400_), (TypeTemplate)DSL.constType((Type)DSL.string())))));
        p_17400_.registerType(false, References.f_16794_, () -> DSL.constType(V1125.m_17310_()));
        p_17400_.registerType(false, References.f_16784_, () -> DSL.constType(V1125.m_17310_()));
    }
}

