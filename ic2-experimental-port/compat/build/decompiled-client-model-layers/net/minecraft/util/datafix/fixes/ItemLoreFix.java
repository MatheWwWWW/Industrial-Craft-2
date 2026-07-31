/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
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
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.network.chat.Component;
import net.minecraft.util.datafix.fixes.References;

public class ItemLoreFix
extends DataFix {
    public ItemLoreFix(Schema p_15958_, boolean p_15959_) {
        super(p_15958_, p_15959_);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16782_);
        OpticFinder $$1 = $$0.findField("tag");
        return this.fixTypeEverywhereTyped("Item Lore componentize", $$0, p_15962_ -> p_15962_.updateTyped($$1, p_145392_ -> p_145392_.update(DSL.remainderFinder(), p_145394_ -> p_145394_.update("display", p_145396_ -> p_145396_.update("Lore", p_145398_ -> (Dynamic)DataFixUtils.orElse((Optional)p_145398_.asStreamOpt().map(ItemLoreFix::m_15969_).map(arg_0 -> ((Dynamic)p_145398_).createList(arg_0)).result(), (Object)p_145398_))))));
    }

    private static <T> Stream<Dynamic<T>> m_15969_(Stream<Dynamic<T>> p_15970_) {
        return p_15970_.map(p_15966_ -> (Dynamic)DataFixUtils.orElse((Optional)p_15966_.asString().map(ItemLoreFix::m_15967_).map(arg_0 -> ((Dynamic)p_15966_).createString(arg_0)).result(), (Object)p_15966_));
    }

    private static String m_15967_(String p_15968_) {
        return Component.Serializer.m_130703_(Component.m_237113_(p_15968_));
    }
}

