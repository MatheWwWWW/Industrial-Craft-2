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

public class V135
extends Schema {
    public V135(int p_17404_, Schema p_17405_) {
        super(p_17404_, p_17405_);
    }

    public void registerTypes(Schema p_17411_, Map<String, Supplier<TypeTemplate>> p_17412_, Map<String, Supplier<TypeTemplate>> p_17413_) {
        super.registerTypes(p_17411_, p_17412_, p_17413_);
        p_17411_.registerType(false, References.f_16772_, () -> DSL.optionalFields((String)"RootVehicle", (TypeTemplate)DSL.optionalFields((String)"Entity", (TypeTemplate)References.f_16785_.in(p_17411_)), (String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17411_)), (String)"EnderItems", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17411_))));
        p_17411_.registerType(true, References.f_16785_, () -> DSL.optionalFields((String)"Passengers", (TypeTemplate)DSL.list((TypeTemplate)References.f_16785_.in(p_17411_)), (TypeTemplate)References.f_16786_.in(p_17411_)));
    }
}

