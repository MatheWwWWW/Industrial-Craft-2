/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.templates.TypeTemplate
 */
package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.util.datafix.schemas.V100;

public class V2522
extends NamespacedSchema {
    public V2522(int p_17933_, Schema p_17934_) {
        super(p_17933_, p_17934_);
    }

    protected static void m_17937_(Schema p_17938_, Map<String, Supplier<TypeTemplate>> p_17939_, String p_17940_) {
        p_17938_.register(p_17939_, p_17940_, () -> V100.m_17330_(p_17938_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17942_) {
        Map $$1 = super.registerEntities(p_17942_);
        V2522.m_17937_(p_17942_, $$1, "minecraft:zoglin");
        return $$1;
    }
}

