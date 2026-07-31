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
 *  com.mojang.datafixers.types.templates.CompoundList$CompoundListType
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
import com.mojang.datafixers.types.templates.CompoundList;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class NewVillageFix
extends DataFix {
    public NewVillageFix(Schema p_16476_, boolean p_16477_) {
        super(p_16476_, p_16477_);
    }

    protected TypeRewriteRule makeRule() {
        CompoundList.CompoundListType $$0 = DSL.compoundList((Type)DSL.string(), (Type)this.getInputSchema().getType(References.f_16790_));
        OpticFinder $$1 = $$0.finder();
        return this.m_16498_($$0);
    }

    private <SF> TypeRewriteRule m_16498_(CompoundList.CompoundListType<String, SF> p_16499_) {
        Type $$1 = this.getInputSchema().getType(References.f_16773_);
        Type $$2 = this.getInputSchema().getType(References.f_16790_);
        OpticFinder $$3 = $$1.findField("Level");
        OpticFinder $$4 = $$3.type().findField("Structures");
        OpticFinder $$5 = $$4.type().findField("Starts");
        OpticFinder $$6 = p_16499_.finder();
        return TypeRewriteRule.seq((TypeRewriteRule)this.fixTypeEverywhereTyped("NewVillageFix", $$1, p_16483_ -> p_16483_.updateTyped($$3, p_145526_ -> p_145526_.updateTyped($$4, p_145530_ -> p_145530_.updateTyped($$5, p_145533_ -> p_145533_.update($$6, p_145544_ -> p_145544_.stream().filter(p_145546_ -> !Objects.equals(p_145546_.getFirst(), "Village")).map(p_145535_ -> p_145535_.mapFirst(p_145542_ -> p_145542_.equals("New_Village") ? "Village" : p_145542_)).collect(Collectors.toList()))).update(DSL.remainderFinder(), p_145550_ -> p_145550_.update("References", p_145552_ -> {
            Optional $$1 = p_145552_.get("New_Village").result();
            return ((Dynamic)DataFixUtils.orElse($$1.map(p_145540_ -> p_145552_.remove("New_Village").set("Village", p_145540_)), (Object)p_145552_)).remove("Village");
        }))))), (TypeRewriteRule)this.fixTypeEverywhereTyped("NewVillageStartFix", $$2, p_16497_ -> p_16497_.update(DSL.remainderFinder(), p_145537_ -> p_145537_.update("id", p_145548_ -> Objects.equals(NamespacedSchema.m_17311_(p_145548_.asString("")), "minecraft:new_village") ? p_145548_.createString("minecraft:village") : p_145548_))));
    }
}

