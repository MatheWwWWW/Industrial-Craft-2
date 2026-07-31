/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DSL$TypeReference
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.function.Function;
import net.minecraft.util.datafix.fixes.BlockRenameFix;
import net.minecraft.util.datafix.fixes.References;

public abstract class BlockRenameFixWithJigsaw
extends BlockRenameFix {
    private final String f_145148_;

    public BlockRenameFixWithJigsaw(Schema p_145150_, String p_145151_) {
        super(p_145150_, p_145151_);
        this.f_145148_ = p_145151_;
    }

    @Override
    public TypeRewriteRule makeRule() {
        DSL.TypeReference $$0 = References.f_16781_;
        String $$1 = "minecraft:jigsaw";
        OpticFinder $$2 = DSL.namedChoice((String)"minecraft:jigsaw", (Type)this.getInputSchema().getChoiceType($$0, "minecraft:jigsaw"));
        TypeRewriteRule $$3 = this.fixTypeEverywhereTyped(this.f_145148_ + " for jigsaw state", this.getInputSchema().getType($$0), this.getOutputSchema().getType($$0), p_145155_ -> p_145155_.updateTyped($$2, this.getOutputSchema().getChoiceType($$0, "minecraft:jigsaw"), p_145157_ -> p_145157_.update(DSL.remainderFinder(), p_145159_ -> p_145159_.update("final_state", p_145162_ -> (Dynamic)DataFixUtils.orElse(p_145162_.asString().result().map(p_145168_ -> {
            int $$1 = p_145168_.indexOf(91);
            int $$2 = p_145168_.indexOf(123);
            int $$3 = p_145168_.length();
            if ($$1 > 0) {
                $$3 = Math.min($$3, $$1);
            }
            if ($$2 > 0) {
                $$3 = Math.min($$3, $$2);
            }
            String $$4 = p_145168_.substring(0, $$3);
            String $$5 = this.m_7384_($$4);
            return $$5 + p_145168_.substring($$3);
        }).map(arg_0 -> ((Dynamic)p_145159_).createString(arg_0)), (Object)p_145162_)))));
        return TypeRewriteRule.seq((TypeRewriteRule)super.makeRule(), (TypeRewriteRule)$$3);
    }

    public static DataFix m_145163_(Schema p_145164_, String p_145165_, final Function<String, String> p_145166_) {
        return new BlockRenameFixWithJigsaw(p_145164_, p_145165_){

            @Override
            protected String m_7384_(String p_145176_) {
                return (String)p_145166_.apply(p_145176_);
            }
        };
    }
}

