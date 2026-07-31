/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Objects;
import net.minecraft.util.datafix.fixes.BlockStateData;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class BlockNameFlatteningFix
extends DataFix {
    public BlockNameFlatteningFix(Schema p_14897_, boolean p_14898_) {
        super(p_14897_, p_14898_);
    }

    public TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16787_);
        Type $$1 = this.getOutputSchema().getType(References.f_16787_);
        Type $$2 = DSL.named((String)References.f_16787_.typeName(), (Type)DSL.or((Type)DSL.intType(), NamespacedSchema.m_17310_()));
        Type $$3 = DSL.named((String)References.f_16787_.typeName(), NamespacedSchema.m_17310_());
        if (!Objects.equals($$0, $$2) || !Objects.equals($$1, $$3)) {
            throw new IllegalStateException("Expected and actual types don't match.");
        }
        return this.fixTypeEverywhere("BlockNameFlatteningFix", $$2, $$3, p_14904_ -> p_145141_ -> p_145141_.mapSecond(p_145139_ -> (String)p_145139_.map(BlockStateData::m_14940_, p_145143_ -> BlockStateData.m_14950_(NamespacedSchema.m_17311_(p_145143_)))));
    }
}

