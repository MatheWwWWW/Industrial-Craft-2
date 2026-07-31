/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.templates.TypeTemplate
 */
package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.util.datafix.schemas.V100;

public class V1470
extends NamespacedSchema {
    public V1470(int p_17698_, Schema p_17699_) {
        super(p_17698_, p_17699_);
    }

    protected static void m_17705_(Schema p_17706_, Map<String, Supplier<TypeTemplate>> p_17707_, String p_17708_) {
        p_17706_.register(p_17707_, p_17708_, () -> V100.m_17330_(p_17706_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17710_) {
        Map $$1 = super.registerEntities(p_17710_);
        V1470.m_17705_(p_17710_, $$1, "minecraft:turtle");
        V1470.m_17705_(p_17710_, $$1, "minecraft:cod_mob");
        V1470.m_17705_(p_17710_, $$1, "minecraft:tropical_fish");
        V1470.m_17705_(p_17710_, $$1, "minecraft:salmon_mob");
        V1470.m_17705_(p_17710_, $$1, "minecraft:puffer_fish");
        V1470.m_17705_(p_17710_, $$1, "minecraft:phantom");
        V1470.m_17705_(p_17710_, $$1, "minecraft:dolphin");
        V1470.m_17705_(p_17710_, $$1, "minecraft:drowned");
        p_17710_.register($$1, "minecraft:trident", p_17704_ -> DSL.optionalFields((String)"inBlockState", (TypeTemplate)References.f_16783_.in(p_17710_)));
        return $$1;
    }
}

