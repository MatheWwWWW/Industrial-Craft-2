/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.Hook$HookFunction
 *  com.mojang.datafixers.types.templates.TypeTemplate
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 */
package net.minecraft.util.datafix.schemas;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Hook;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class V1451_6
extends NamespacedSchema {
    public static final String f_181073_ = "_special";
    protected static final Hook.HookFunction f_181074_ = new Hook.HookFunction(){

        public <T> T apply(DynamicOps<T> p_181096_, T p_181097_) {
            Dynamic $$2 = new Dynamic(p_181096_, p_181097_);
            return (T)((Dynamic)DataFixUtils.orElse($$2.get("CriteriaName").asString().get().left().map(p_181094_ -> {
                int $$1 = p_181094_.indexOf(58);
                if ($$1 < 0) {
                    return Pair.of((Object)V1451_6.f_181073_, (Object)p_181094_);
                }
                try {
                    ResourceLocation $$2 = ResourceLocation.m_135822_(p_181094_.substring(0, $$1), '.');
                    ResourceLocation $$3 = ResourceLocation.m_135822_(p_181094_.substring($$1 + 1), '.');
                    return Pair.of((Object)$$2.toString(), (Object)$$3.toString());
                }
                catch (Exception $$4) {
                    return Pair.of((Object)V1451_6.f_181073_, (Object)p_181094_);
                }
            }).map(p_181092_ -> $$2.set("CriteriaType", $$2.createMap((Map)ImmutableMap.of((Object)$$2.createString("type"), (Object)$$2.createString((String)p_181092_.getFirst()), (Object)$$2.createString("id"), (Object)$$2.createString((String)p_181092_.getSecond()))))), (Object)$$2)).getValue();
        }
    };
    protected static final Hook.HookFunction f_181075_ = new Hook.HookFunction(){

        private String m_181102_(String p_181103_) {
            ResourceLocation $$1 = ResourceLocation.m_135820_(p_181103_);
            return $$1 != null ? $$1.m_135827_() + "." + $$1.m_135815_() : p_181103_;
        }

        public <T> T apply(DynamicOps<T> p_181105_, T p_181106_) {
            Dynamic $$2 = new Dynamic(p_181105_, p_181106_);
            Optional<Dynamic> $$3 = $$2.get("CriteriaType").get().get().left().flatMap(p_181109_ -> {
                Optional $$2 = p_181109_.get("type").asString().get().left();
                Optional $$3 = p_181109_.get("id").asString().get().left();
                if ($$2.isPresent() && $$3.isPresent()) {
                    String $$4 = (String)$$2.get();
                    if ($$4.equals(V1451_6.f_181073_)) {
                        return Optional.of($$2.createString((String)$$3.get()));
                    }
                    return Optional.of(p_181109_.createString(this.m_181102_($$4) + ":" + this.m_181102_((String)$$3.get())));
                }
                return Optional.empty();
            });
            return (T)((Dynamic)DataFixUtils.orElse($$3.map(p_181101_ -> $$2.set("CriteriaName", p_181101_).remove("CriteriaType")), (Object)$$2)).getValue();
        }
    };

    public V1451_6(int p_17532_, Schema p_17533_) {
        super(p_17532_, p_17533_);
    }

    public void registerTypes(Schema p_17540_, Map<String, Supplier<TypeTemplate>> p_17541_, Map<String, Supplier<TypeTemplate>> p_17542_) {
        super.registerTypes(p_17540_, p_17541_, p_17542_);
        Supplier<TypeTemplate> $$3 = () -> DSL.compoundList((TypeTemplate)References.f_16788_.in(p_17540_), (TypeTemplate)DSL.constType((Type)DSL.intType()));
        p_17540_.registerType(false, References.f_16777_, () -> DSL.optionalFields((String)"stats", (TypeTemplate)DSL.optionalFields((String)"minecraft:mined", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16787_.in(p_17540_), (TypeTemplate)DSL.constType((Type)DSL.intType())), (String)"minecraft:crafted", (TypeTemplate)((TypeTemplate)$$3.get()), (String)"minecraft:used", (TypeTemplate)((TypeTemplate)$$3.get()), (String)"minecraft:broken", (TypeTemplate)((TypeTemplate)$$3.get()), (String)"minecraft:picked_up", (TypeTemplate)((TypeTemplate)$$3.get()), (TypeTemplate)DSL.optionalFields((String)"minecraft:dropped", (TypeTemplate)((TypeTemplate)$$3.get()), (String)"minecraft:killed", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16784_.in(p_17540_), (TypeTemplate)DSL.constType((Type)DSL.intType())), (String)"minecraft:killed_by", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16784_.in(p_17540_), (TypeTemplate)DSL.constType((Type)DSL.intType())), (String)"minecraft:custom", (TypeTemplate)DSL.compoundList((TypeTemplate)DSL.constType(V1451_6.m_17310_()), (TypeTemplate)DSL.constType((Type)DSL.intType()))))));
        Map<String, Supplier<TypeTemplate>> $$4 = V1451_6.m_181077_(p_17540_);
        p_17540_.registerType(false, References.f_16791_, () -> DSL.hook((TypeTemplate)DSL.optionalFields((String)"CriteriaType", (TypeTemplate)DSL.taggedChoiceLazy((String)"type", (Type)DSL.string(), (Map)$$4)), (Hook.HookFunction)f_181074_, (Hook.HookFunction)f_181075_));
    }

    protected static Map<String, Supplier<TypeTemplate>> m_181077_(Schema p_181078_) {
        Supplier<TypeTemplate> $$1 = () -> DSL.optionalFields((String)"id", (TypeTemplate)References.f_16788_.in(p_181078_));
        Supplier<TypeTemplate> $$2 = () -> DSL.optionalFields((String)"id", (TypeTemplate)References.f_16787_.in(p_181078_));
        Supplier<TypeTemplate> $$3 = () -> DSL.optionalFields((String)"id", (TypeTemplate)References.f_16784_.in(p_181078_));
        HashMap $$4 = Maps.newHashMap();
        $$4.put("minecraft:mined", $$2);
        $$4.put("minecraft:crafted", $$1);
        $$4.put("minecraft:used", $$1);
        $$4.put("minecraft:broken", $$1);
        $$4.put("minecraft:picked_up", $$1);
        $$4.put("minecraft:dropped", $$1);
        $$4.put("minecraft:killed", $$3);
        $$4.put("minecraft:killed_by", $$3);
        $$4.put("minecraft:custom", () -> DSL.optionalFields((String)"id", (TypeTemplate)DSL.constType(V1451_6.m_17310_())));
        $$4.put(f_181073_, () -> DSL.optionalFields((String)"id", (TypeTemplate)DSL.constType((Type)DSL.string())));
        return $$4;
    }
}

