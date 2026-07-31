/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.References;

public class IglooMetadataRemovalFix
extends DataFix {
    public IglooMetadataRemovalFix(Schema p_15902_, boolean p_15903_) {
        super(p_15902_, p_15903_);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16790_);
        Type $$1 = this.getOutputSchema().getType(References.f_16790_);
        return this.writeFixAndRead("IglooMetadataRemovalFix", $$0, $$1, IglooMetadataRemovalFix::m_15904_);
    }

    private static <T> Dynamic<T> m_15904_(Dynamic<T> p_15905_) {
        boolean $$1 = p_15905_.get("Children").asStreamOpt().map(p_15911_ -> p_15911_.allMatch(IglooMetadataRemovalFix::m_15912_)).result().orElse(false);
        if ($$1) {
            return p_15905_.set("id", p_15905_.createString("Igloo")).remove("Children");
        }
        return p_15905_.update("Children", IglooMetadataRemovalFix::m_15908_);
    }

    private static <T> Dynamic<T> m_15908_(Dynamic<T> p_15909_) {
        return p_15909_.asStreamOpt().map(p_15907_ -> p_15907_.filter(p_145382_ -> !IglooMetadataRemovalFix.m_15912_(p_145382_))).map(arg_0 -> p_15909_.createList(arg_0)).result().orElse(p_15909_);
    }

    private static boolean m_15912_(Dynamic<?> p_15913_) {
        return p_15913_.get("id").asString("").equals("Iglu");
    }
}

