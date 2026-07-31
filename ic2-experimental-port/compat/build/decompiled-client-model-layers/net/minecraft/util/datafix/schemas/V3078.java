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

public class V3078
extends NamespacedSchema {
    public V3078(int p_216769_, Schema p_216770_) {
        super(p_216769_, p_216770_);
    }

    protected static void m_216773_(Schema p_216774_, Map<String, Supplier<TypeTemplate>> p_216775_, String p_216776_) {
        p_216774_.register(p_216775_, p_216776_, () -> V100.m_17330_(p_216774_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_216782_) {
        Map $$1 = super.registerEntities(p_216782_);
        V3078.m_216773_(p_216782_, $$1, "minecraft:frog");
        V3078.m_216773_(p_216782_, $$1, "minecraft:tadpole");
        return $$1;
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_216780_) {
        Map $$1 = super.registerBlockEntities(p_216780_);
        p_216780_.register($$1, "minecraft:sculk_shrieker", () -> DSL.optionalFields((String)"listener", (TypeTemplate)DSL.optionalFields((String)"event", (TypeTemplate)DSL.optionalFields((String)"game_event", (TypeTemplate)References.f_216719_.in(p_216780_)))));
        return $$1;
    }
}

