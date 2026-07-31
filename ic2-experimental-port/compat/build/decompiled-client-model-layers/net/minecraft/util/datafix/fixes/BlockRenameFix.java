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
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public abstract class BlockRenameFix
extends DataFix {
    private final String f_14908_;

    public BlockRenameFix(Schema p_14910_, String p_14911_) {
        super(p_14910_, false);
        this.f_14908_ = p_14911_;
    }

    public TypeRewriteRule makeRule() {
        Type $$1;
        Type $$0 = this.getInputSchema().getType(References.f_16787_);
        if (!Objects.equals($$0, $$1 = DSL.named((String)References.f_16787_.typeName(), NamespacedSchema.m_17310_()))) {
            throw new IllegalStateException("block type is not what was expected.");
        }
        TypeRewriteRule $$2 = this.fixTypeEverywhere(this.f_14908_ + " for block", $$1, p_14923_ -> p_145145_ -> p_145145_.mapSecond(this::m_7384_));
        TypeRewriteRule $$3 = this.fixTypeEverywhereTyped(this.f_14908_ + " for block_state", this.getInputSchema().getType(References.f_16783_), p_14913_ -> p_14913_.update(DSL.remainderFinder(), p_145147_ -> {
            Optional $$1 = p_145147_.get("Name").asString().result();
            if ($$1.isPresent()) {
                return p_145147_.set("Name", p_145147_.createString(this.m_7384_((String)$$1.get())));
            }
            return p_145147_;
        }));
        return TypeRewriteRule.seq((TypeRewriteRule)$$2, (TypeRewriteRule)$$3);
    }

    protected abstract String m_7384_(String var1);

    public static DataFix m_14914_(Schema p_14915_, String p_14916_, final Function<String, String> p_14917_) {
        return new BlockRenameFix(p_14915_, p_14916_){

            @Override
            protected String m_7384_(String p_14932_) {
                return (String)p_14917_.apply(p_14932_);
            }
        };
    }
}

