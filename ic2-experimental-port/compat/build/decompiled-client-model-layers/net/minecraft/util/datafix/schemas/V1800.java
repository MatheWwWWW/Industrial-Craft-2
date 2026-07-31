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

public class V1800
extends NamespacedSchema {
    public V1800(int p_17732_, Schema p_17733_) {
        super(p_17732_, p_17733_);
    }

    protected static void m_17739_(Schema p_17740_, Map<String, Supplier<TypeTemplate>> p_17741_, String p_17742_) {
        p_17740_.register(p_17741_, p_17742_, () -> V100.m_17330_(p_17740_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17744_) {
        Map $$1 = super.registerEntities(p_17744_);
        V1800.m_17739_(p_17744_, $$1, "minecraft:panda");
        p_17744_.register($$1, "minecraft:pillager", p_17738_ -> DSL.optionalFields((String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17744_)), (TypeTemplate)V100.m_17330_(p_17744_)));
        return $$1;
    }
}

