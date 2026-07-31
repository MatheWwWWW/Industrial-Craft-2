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

public class V1801
extends NamespacedSchema {
    public V1801(int p_17746_, Schema p_17747_) {
        super(p_17746_, p_17747_);
    }

    protected static void m_17750_(Schema p_17751_, Map<String, Supplier<TypeTemplate>> p_17752_, String p_17753_) {
        p_17751_.register(p_17752_, p_17753_, () -> V100.m_17330_(p_17751_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17755_) {
        Map $$1 = super.registerEntities(p_17755_);
        V1801.m_17750_(p_17755_, $$1, "minecraft:illager_beast");
        return $$1;
    }
}

