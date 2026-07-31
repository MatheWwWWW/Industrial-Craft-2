/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.TaggedChoice$TaggedChoiceType
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Objects;
import net.minecraft.util.datafix.fixes.References;

public class EntityMinecartIdentifiersFix
extends DataFix {
    private static final List<String> f_15476_ = Lists.newArrayList((Object[])new String[]{"MinecartRideable", "MinecartChest", "MinecartFurnace"});

    public EntityMinecartIdentifiersFix(Schema p_15479_, boolean p_15480_) {
        super(p_15479_, p_15480_);
    }

    public TypeRewriteRule makeRule() {
        TaggedChoice.TaggedChoiceType $$0 = this.getInputSchema().findChoiceType(References.f_16786_);
        TaggedChoice.TaggedChoiceType $$1 = this.getOutputSchema().findChoiceType(References.f_16786_);
        return this.fixTypeEverywhere("EntityMinecartIdentifiersFix", (Type)$$0, (Type)$$1, p_15485_ -> p_145290_ -> {
            if (Objects.equals(p_145290_.getFirst(), "Minecart")) {
                String $$8;
                Typed $$4 = (Typed)$$0.point(p_15485_, (Object)"Minecart", p_145290_.getSecond()).orElseThrow(IllegalStateException::new);
                Dynamic $$5 = (Dynamic)$$4.getOrCreate(DSL.remainderFinder());
                int $$6 = $$5.get("Type").asInt(0);
                if ($$6 > 0 && $$6 < f_15476_.size()) {
                    String $$7 = f_15476_.get($$6);
                } else {
                    $$8 = "MinecartRideable";
                }
                return Pair.of((Object)$$8, (Object)((DataResult)$$4.write().map(p_145294_ -> ((Type)$$1.types().get($$8)).read(p_145294_)).result().orElseThrow(() -> new IllegalStateException("Could not read the new minecart."))));
            }
            return p_145290_;
        });
    }
}

