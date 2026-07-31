/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.OptionalDynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Codec;
import com.mojang.serialization.OptionalDynamic;
import java.util.List;
import net.minecraft.util.datafix.fixes.References;

public class EntityRedundantChanceTagsFix
extends DataFix {
    private static final Codec<List<Float>> f_15598_ = Codec.FLOAT.listOf();

    public EntityRedundantChanceTagsFix(Schema p_15601_, boolean p_15602_) {
        super(p_15601_, p_15602_);
    }

    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("EntityRedundantChanceTagsFix", this.getInputSchema().getType(References.f_16786_), p_15607_ -> p_15607_.update(DSL.remainderFinder(), p_145304_ -> {
            if (EntityRedundantChanceTagsFix.m_15610_(p_145304_.get("HandDropChances"), 2)) {
                p_145304_ = p_145304_.remove("HandDropChances");
            }
            if (EntityRedundantChanceTagsFix.m_15610_(p_145304_.get("ArmorDropChances"), 4)) {
                p_145304_ = p_145304_.remove("ArmorDropChances");
            }
            return p_145304_;
        }));
    }

    private static boolean m_15610_(OptionalDynamic<?> p_15611_, int p_15612_) {
        return p_15611_.flatMap(arg_0 -> f_15598_.parse(arg_0)).map(p_15605_ -> p_15605_.size() == p_15612_ && p_15605_.stream().allMatch(p_145306_ -> p_145306_.floatValue() == 0.0f)).result().orElse(false);
    }
}

