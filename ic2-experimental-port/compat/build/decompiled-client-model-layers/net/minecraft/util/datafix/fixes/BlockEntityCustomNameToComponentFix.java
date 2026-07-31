/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.EntityCustomNameToComponentFix;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class BlockEntityCustomNameToComponentFix
extends DataFix {
    public BlockEntityCustomNameToComponentFix(Schema p_14817_, boolean p_14818_) {
        super(p_14817_, p_14818_);
    }

    public TypeRewriteRule makeRule() {
        OpticFinder $$0 = DSL.fieldFinder((String)"id", NamespacedSchema.m_17310_());
        return this.fixTypeEverywhereTyped("BlockEntityCustomNameToComponentFix", this.getInputSchema().getType(References.f_16781_), p_14821_ -> p_14821_.update(DSL.remainderFinder(), p_145133_ -> {
            Optional $$3 = p_14821_.getOptional($$0);
            if ($$3.isPresent() && Objects.equals($$3.get(), "minecraft:command_block")) {
                return p_145133_;
            }
            return EntityCustomNameToComponentFix.m_15407_(p_145133_);
        }));
    }
}

