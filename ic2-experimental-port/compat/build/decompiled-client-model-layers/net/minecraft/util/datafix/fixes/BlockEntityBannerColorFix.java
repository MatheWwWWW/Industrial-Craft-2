/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public class BlockEntityBannerColorFix
extends NamedEntityFix {
    public BlockEntityBannerColorFix(Schema p_14793_, boolean p_14794_) {
        super(p_14793_, p_14794_, "BlockEntityBannerColorFix", References.f_16781_, "minecraft:banner");
    }

    public Dynamic<?> m_14797_(Dynamic<?> p_14798_) {
        p_14798_ = p_14798_.update("Base", p_14808_ -> p_14808_.createInt(15 - p_14808_.asInt(0)));
        p_14798_ = p_14798_.update("Patterns", p_14802_ -> (Dynamic)DataFixUtils.orElse((Optional)p_14802_.asStreamOpt().map(p_145125_ -> p_145125_.map(p_145127_ -> p_145127_.update("Color", p_145129_ -> p_145129_.createInt(15 - p_145129_.asInt(0))))).map(arg_0 -> ((Dynamic)p_14802_).createList(arg_0)).result(), (Object)p_14802_));
        return p_14798_;
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_14796_) {
        return p_14796_.update(DSL.remainderFinder(), this::m_14797_);
    }
}

