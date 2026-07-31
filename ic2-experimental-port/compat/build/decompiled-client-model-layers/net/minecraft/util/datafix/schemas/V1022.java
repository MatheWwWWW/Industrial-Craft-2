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

public class V1022
extends Schema {
    public V1022(int p_17365_, Schema p_17366_) {
        super(p_17365_, p_17366_);
    }

    public void registerTypes(Schema p_17373_, Map<String, Supplier<TypeTemplate>> p_17374_, Map<String, Supplier<TypeTemplate>> p_17375_) {
        super.registerTypes(p_17373_, p_17374_, p_17375_);
        p_17373_.registerType(false, References.f_16793_, () -> DSL.constType(NamespacedSchema.m_17310_()));
        p_17373_.registerType(false, References.f_16772_, () -> DSL.optionalFields((String)"RootVehicle", (TypeTemplate)DSL.optionalFields((String)"Entity", (TypeTemplate)References.f_16785_.in(p_17373_)), (String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17373_)), (String)"EnderItems", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17373_)), (TypeTemplate)DSL.optionalFields((String)"ShoulderEntityLeft", (TypeTemplate)References.f_16785_.in(p_17373_), (String)"ShoulderEntityRight", (TypeTemplate)References.f_16785_.in(p_17373_), (String)"recipeBook", (TypeTemplate)DSL.optionalFields((String)"recipes", (TypeTemplate)DSL.list((TypeTemplate)References.f_16793_.in(p_17373_)), (String)"toBeDisplayed", (TypeTemplate)DSL.list((TypeTemplate)References.f_16793_.in(p_17373_))))));
        p_17373_.registerType(false, References.f_16774_, () -> DSL.compoundList((TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17373_))));
    }
}

