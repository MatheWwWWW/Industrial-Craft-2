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
 *  org.apache.commons.lang3.StringUtils
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
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.datafix.fixes.BlockEntitySignTextStrictJsonFix;
import net.minecraft.util.datafix.fixes.References;
import org.apache.commons.lang3.StringUtils;

public class ItemWrittenBookPagesStrictJsonFix
extends DataFix {
    public ItemWrittenBookPagesStrictJsonFix(Schema p_16164_, boolean p_16165_) {
        super(p_16164_, p_16165_);
    }

    public Dynamic<?> m_16171_(Dynamic<?> p_16172_) {
        return p_16172_.update("pages", p_16175_ -> (Dynamic)DataFixUtils.orElse((Optional)p_16175_.asStreamOpt().map(p_145441_ -> p_145441_.map(p_145443_ -> {
            if (!p_145443_.asString().result().isPresent()) {
                return p_145443_;
            }
            String $$1 = p_145443_.asString("");
            Component $$2 = null;
            if ("null".equals($$1) || StringUtils.isEmpty((CharSequence)$$1)) {
                $$2 = CommonComponents.f_237098_;
            } else if ($$1.charAt(0) == '\"' && $$1.charAt($$1.length() - 1) == '\"' || $$1.charAt(0) == '{' && $$1.charAt($$1.length() - 1) == '}') {
                try {
                    $$2 = GsonHelper.m_13798_(BlockEntitySignTextStrictJsonFix.f_14861_, $$1, Component.class, true);
                    if ($$2 == null) {
                        $$2 = CommonComponents.f_237098_;
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if ($$2 == null) {
                    try {
                        $$2 = Component.Serializer.m_130701_($$1);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                if ($$2 == null) {
                    try {
                        $$2 = Component.Serializer.m_130714_($$1);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                if ($$2 == null) {
                    $$2 = Component.m_237113_($$1);
                }
            } else {
                $$2 = Component.m_237113_($$1);
            }
            return p_145443_.createString(Component.Serializer.m_130703_($$2));
        })).map(arg_0 -> ((Dynamic)p_16172_).createList(arg_0)).result(), (Object)p_16172_.emptyList()));
    }

    public TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16782_);
        OpticFinder $$1 = $$0.findField("tag");
        return this.fixTypeEverywhereTyped("ItemWrittenBookPagesStrictJsonFix", $$0, p_16168_ -> p_16168_.updateTyped($$1, p_145439_ -> p_145439_.update(DSL.remainderFinder(), this::m_16171_)));
    }
}

