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

public class V1929
extends NamespacedSchema {
    public V1929(int p_17811_, Schema p_17812_) {
        super(p_17811_, p_17812_);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17820_) {
        Map $$1 = super.registerEntities(p_17820_);
        p_17820_.register($$1, "minecraft:wandering_trader", p_17818_ -> DSL.optionalFields((String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17820_)), (String)"Offers", (TypeTemplate)DSL.optionalFields((String)"Recipes", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"buy", (TypeTemplate)References.f_16782_.in(p_17820_), (String)"buyB", (TypeTemplate)References.f_16782_.in(p_17820_), (String)"sell", (TypeTemplate)References.f_16782_.in(p_17820_)))), (TypeTemplate)V100.m_17330_(p_17820_)));
        p_17820_.register($$1, "minecraft:trader_llama", p_17815_ -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17820_)), (String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_17820_), (String)"DecorItem", (TypeTemplate)References.f_16782_.in(p_17820_), (TypeTemplate)V100.m_17330_(p_17820_)));
        return $$1;
    }
}

