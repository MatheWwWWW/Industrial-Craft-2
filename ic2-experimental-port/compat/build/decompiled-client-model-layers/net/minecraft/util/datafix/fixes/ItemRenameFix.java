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
import java.util.function.Function;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public abstract class ItemRenameFix
extends DataFix {
    private final String f_15999_;

    public ItemRenameFix(Schema p_16001_, String p_16002_) {
        super(p_16001_, false);
        this.f_15999_ = p_16002_;
    }

    public TypeRewriteRule makeRule() {
        Type $$0 = DSL.named((String)References.f_16788_.typeName(), NamespacedSchema.m_17310_());
        if (!Objects.equals(this.getInputSchema().getType(References.f_16788_), $$0)) {
            throw new IllegalStateException("item name type is not what was expected.");
        }
        return this.fixTypeEverywhere(this.f_15999_, $$0, p_16010_ -> p_145402_ -> p_145402_.mapSecond(this::m_7348_));
    }

    protected abstract String m_7348_(String var1);

    public static DataFix m_16003_(Schema p_16004_, String p_16005_, final Function<String, String> p_16006_) {
        return new ItemRenameFix(p_16004_, p_16005_){

            @Override
            protected String m_7348_(String p_16019_) {
                return (String)p_16006_.apply(p_16019_);
            }
        };
    }
}

