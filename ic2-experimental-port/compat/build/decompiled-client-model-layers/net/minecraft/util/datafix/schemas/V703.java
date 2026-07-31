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
import net.minecraft.util.datafix.schemas.V100;

public class V703
extends Schema {
    public V703(int p_18018_, Schema p_18019_) {
        super(p_18018_, p_18019_);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_18031_) {
        Map $$1 = super.registerEntities(p_18031_);
        $$1.remove("EntityHorse");
        p_18031_.register($$1, "Horse", () -> DSL.optionalFields((String)"ArmorItem", (TypeTemplate)References.f_16782_.in(p_18031_), (String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_18031_), (TypeTemplate)V100.m_17330_(p_18031_)));
        p_18031_.register($$1, "Donkey", () -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18031_)), (String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_18031_), (TypeTemplate)V100.m_17330_(p_18031_)));
        p_18031_.register($$1, "Mule", () -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18031_)), (String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_18031_), (TypeTemplate)V100.m_17330_(p_18031_)));
        p_18031_.register($$1, "ZombieHorse", () -> DSL.optionalFields((String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_18031_), (TypeTemplate)V100.m_17330_(p_18031_)));
        p_18031_.register($$1, "SkeletonHorse", () -> DSL.optionalFields((String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_18031_), (TypeTemplate)V100.m_17330_(p_18031_)));
        return $$1;
    }
}

