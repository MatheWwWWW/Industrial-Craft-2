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

public class V2502
extends NamespacedSchema {
    public V2502(int p_17859_, Schema p_17860_) {
        super(p_17859_, p_17860_);
    }

    protected static void m_17863_(Schema p_17864_, Map<String, Supplier<TypeTemplate>> p_17865_, String p_17866_) {
        p_17864_.register(p_17865_, p_17866_, () -> V100.m_17330_(p_17864_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17868_) {
        Map $$1 = super.registerEntities(p_17868_);
        V2502.m_17863_(p_17868_, $$1, "minecraft:hoglin");
        return $$1;
    }
}

