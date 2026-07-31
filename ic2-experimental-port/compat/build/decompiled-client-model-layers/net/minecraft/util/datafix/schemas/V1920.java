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

public class V1920
extends NamespacedSchema {
    public V1920(int p_17787_, Schema p_17788_) {
        super(p_17787_, p_17788_);
    }

    protected static void m_17791_(Schema p_17792_, Map<String, Supplier<TypeTemplate>> p_17793_, String p_17794_) {
        p_17792_.register(p_17793_, p_17794_, () -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17792_))));
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_17796_) {
        Map $$1 = super.registerBlockEntities(p_17796_);
        V1920.m_17791_(p_17796_, $$1, "minecraft:campfire");
        return $$1;
    }
}

