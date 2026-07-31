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
import net.minecraft.util.datafix.schemas.V100;

public class V700
extends Schema {
    public V700(int p_17985_, Schema p_17986_) {
        super(p_17985_, p_17986_);
    }

    protected static void m_17989_(Schema p_17990_, Map<String, Supplier<TypeTemplate>> p_17991_, String p_17992_) {
        p_17990_.register(p_17991_, p_17992_, () -> V100.m_17330_(p_17990_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17994_) {
        Map $$1 = super.registerEntities(p_17994_);
        V700.m_17989_(p_17994_, $$1, "ElderGuardian");
        return $$1;
    }
}

