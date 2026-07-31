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

public class V3083
extends NamespacedSchema {
    public V3083(int p_216805_, Schema p_216806_) {
        super(p_216805_, p_216806_);
    }

    protected static void m_216809_(Schema p_216810_, Map<String, Supplier<TypeTemplate>> p_216811_, String p_216812_) {
        p_216810_.register(p_216811_, p_216812_, () -> DSL.optionalFields((String)"ArmorItems", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_216810_)), (String)"HandItems", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_216810_)), (String)"listener", (TypeTemplate)DSL.optionalFields((String)"event", (TypeTemplate)DSL.optionalFields((String)"game_event", (TypeTemplate)References.f_216719_.in(p_216810_)))));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_216814_) {
        Map $$1 = super.registerEntities(p_216814_);
        V3083.m_216809_(p_216814_, $$1, "minecraft:allay");
        return $$1;
    }
}

