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

public class EntityPaintingFieldsRenameFix
extends NamedEntityFix {
    public EntityPaintingFieldsRenameFix(Schema p_216606_) {
        super(p_216606_, false, "EntityPaintingFieldsRenameFix", References.f_16786_, "minecraft:painting");
    }

    public Dynamic<?> m_216609_(Dynamic<?> p_216610_) {
        return this.m_216611_(this.m_216611_(p_216610_, "Motive", "variant"), "Facing", "facing");
    }

    private Dynamic<?> m_216611_(Dynamic<?> p_216612_, String p_216613_, String p_216614_) {
        Optional $$3 = p_216612_.get(p_216613_).result();
        Optional<Dynamic> $$4 = $$3.map(p_216619_ -> p_216612_.remove(p_216613_).set(p_216614_, p_216619_));
        return (Dynamic)DataFixUtils.orElse($$4, p_216612_);
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_216608_) {
        return p_216608_.update(DSL.remainderFinder(), this::m_216609_);
    }
}

