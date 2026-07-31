/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DSL$TypeReference
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.Hook$HookFunction
 *  com.mojang.datafixers.types.templates.TypeTemplate
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
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.util.datafix.schemas.V99;

public class V704
extends Schema {
    protected static final Map<String, String> f_18032_ = (Map)DataFixUtils.make(() -> {
        HashMap $$0 = Maps.newHashMap();
        $$0.put("minecraft:furnace", "minecraft:furnace");
        $$0.put("minecraft:lit_furnace", "minecraft:furnace");
        $$0.put("minecraft:chest", "minecraft:chest");
        $$0.put("minecraft:trapped_chest", "minecraft:chest");
        $$0.put("minecraft:ender_chest", "minecraft:ender_chest");
        $$0.put("minecraft:jukebox", "minecraft:jukebox");
        $$0.put("minecraft:dispenser", "minecraft:dispenser");
        $$0.put("minecraft:dropper", "minecraft:dropper");
        $$0.put("minecraft:sign", "minecraft:sign");
        $$0.put("minecraft:mob_spawner", "minecraft:mob_spawner");
        $$0.put("minecraft:spawner", "minecraft:mob_spawner");
        $$0.put("minecraft:noteblock", "minecraft:noteblock");
        $$0.put("minecraft:brewing_stand", "minecraft:brewing_stand");
        $$0.put("minecraft:enhanting_table", "minecraft:enchanting_table");
        $$0.put("minecraft:command_block", "minecraft:command_block");
        $$0.put("minecraft:beacon", "minecraft:beacon");
        $$0.put("minecraft:skull", "minecraft:skull");
        $$0.put("minecraft:daylight_detector", "minecraft:daylight_detector");
        $$0.put("minecraft:hopper", "minecraft:hopper");
        $$0.put("minecraft:banner", "minecraft:banner");
        $$0.put("minecraft:flower_pot", "minecraft:flower_pot");
        $$0.put("minecraft:repeating_command_block", "minecraft:command_block");
        $$0.put("minecraft:chain_command_block", "minecraft:command_block");
        $$0.put("minecraft:shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:white_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:orange_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:magenta_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:light_blue_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:yellow_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:lime_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:pink_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:gray_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:silver_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:cyan_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:purple_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:blue_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:brown_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:green_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:red_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:black_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:bed", "minecraft:bed");
        $$0.put("minecraft:light_gray_shulker_box", "minecraft:shulker_box");
        $$0.put("minecraft:banner", "minecraft:banner");
        $$0.put("minecraft:white_banner", "minecraft:banner");
        $$0.put("minecraft:orange_banner", "minecraft:banner");
        $$0.put("minecraft:magenta_banner", "minecraft:banner");
        $$0.put("minecraft:light_blue_banner", "minecraft:banner");
        $$0.put("minecraft:yellow_banner", "minecraft:banner");
        $$0.put("minecraft:lime_banner", "minecraft:banner");
        $$0.put("minecraft:pink_banner", "minecraft:banner");
        $$0.put("minecraft:gray_banner", "minecraft:banner");
        $$0.put("minecraft:silver_banner", "minecraft:banner");
        $$0.put("minecraft:light_gray_banner", "minecraft:banner");
        $$0.put("minecraft:cyan_banner", "minecraft:banner");
        $$0.put("minecraft:purple_banner", "minecraft:banner");
        $$0.put("minecraft:blue_banner", "minecraft:banner");
        $$0.put("minecraft:brown_banner", "minecraft:banner");
        $$0.put("minecraft:green_banner", "minecraft:banner");
        $$0.put("minecraft:red_banner", "minecraft:banner");
        $$0.put("minecraft:black_banner", "minecraft:banner");
        $$0.put("minecraft:standing_sign", "minecraft:sign");
        $$0.put("minecraft:wall_sign", "minecraft:sign");
        $$0.put("minecraft:piston_head", "minecraft:piston");
        $$0.put("minecraft:daylight_detector_inverted", "minecraft:daylight_detector");
        $$0.put("minecraft:unpowered_comparator", "minecraft:comparator");
        $$0.put("minecraft:powered_comparator", "minecraft:comparator");
        $$0.put("minecraft:wall_banner", "minecraft:banner");
        $$0.put("minecraft:standing_banner", "minecraft:banner");
        $$0.put("minecraft:structure_block", "minecraft:structure_block");
        $$0.put("minecraft:end_portal", "minecraft:end_portal");
        $$0.put("minecraft:end_gateway", "minecraft:end_gateway");
        $$0.put("minecraft:sign", "minecraft:sign");
        $$0.put("minecraft:shield", "minecraft:banner");
        $$0.put("minecraft:white_bed", "minecraft:bed");
        $$0.put("minecraft:orange_bed", "minecraft:bed");
        $$0.put("minecraft:magenta_bed", "minecraft:bed");
        $$0.put("minecraft:light_blue_bed", "minecraft:bed");
        $$0.put("minecraft:yellow_bed", "minecraft:bed");
        $$0.put("minecraft:lime_bed", "minecraft:bed");
        $$0.put("minecraft:pink_bed", "minecraft:bed");
        $$0.put("minecraft:gray_bed", "minecraft:bed");
        $$0.put("minecraft:silver_bed", "minecraft:bed");
        $$0.put("minecraft:light_gray_bed", "minecraft:bed");
        $$0.put("minecraft:cyan_bed", "minecraft:bed");
        $$0.put("minecraft:purple_bed", "minecraft:bed");
        $$0.put("minecraft:blue_bed", "minecraft:bed");
        $$0.put("minecraft:brown_bed", "minecraft:bed");
        $$0.put("minecraft:green_bed", "minecraft:bed");
        $$0.put("minecraft:red_bed", "minecraft:bed");
        $$0.put("minecraft:black_bed", "minecraft:bed");
        $$0.put("minecraft:oak_sign", "minecraft:sign");
        $$0.put("minecraft:spruce_sign", "minecraft:sign");
        $$0.put("minecraft:birch_sign", "minecraft:sign");
        $$0.put("minecraft:jungle_sign", "minecraft:sign");
        $$0.put("minecraft:acacia_sign", "minecraft:sign");
        $$0.put("minecraft:dark_oak_sign", "minecraft:sign");
        $$0.put("minecraft:crimson_sign", "minecraft:sign");
        $$0.put("minecraft:warped_sign", "minecraft:sign");
        $$0.put("minecraft:skeleton_skull", "minecraft:skull");
        $$0.put("minecraft:wither_skeleton_skull", "minecraft:skull");
        $$0.put("minecraft:zombie_head", "minecraft:skull");
        $$0.put("minecraft:player_head", "minecraft:skull");
        $$0.put("minecraft:creeper_head", "minecraft:skull");
        $$0.put("minecraft:dragon_head", "minecraft:skull");
        $$0.put("minecraft:barrel", "minecraft:barrel");
        $$0.put("minecraft:conduit", "minecraft:conduit");
        $$0.put("minecraft:smoker", "minecraft:smoker");
        $$0.put("minecraft:blast_furnace", "minecraft:blast_furnace");
        $$0.put("minecraft:lectern", "minecraft:lectern");
        $$0.put("minecraft:bell", "minecraft:bell");
        $$0.put("minecraft:jigsaw", "minecraft:jigsaw");
        $$0.put("minecraft:campfire", "minecraft:campfire");
        $$0.put("minecraft:bee_nest", "minecraft:beehive");
        $$0.put("minecraft:beehive", "minecraft:beehive");
        $$0.put("minecraft:sculk_sensor", "minecraft:sculk_sensor");
        return ImmutableMap.copyOf((Map)$$0);
    });
    protected static final Hook.HookFunction f_18033_ = new Hook.HookFunction(){

        public <T> T apply(DynamicOps<T> p_18070_, T p_18071_) {
            return V99.m_18205_(new Dynamic(p_18070_, p_18071_), f_18032_, "ArmorStand");
        }
    };

    public V704(int p_18036_, Schema p_18037_) {
        super(p_18036_, p_18037_);
    }

    protected static void m_18043_(Schema p_18044_, Map<String, Supplier<TypeTemplate>> p_18045_, String p_18046_) {
        p_18044_.register(p_18045_, p_18046_, () -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18044_))));
    }

    public Type<?> getChoiceType(DSL.TypeReference p_18060_, String p_18061_) {
        if (Objects.equals(p_18060_.typeName(), References.f_16781_.typeName())) {
            return super.getChoiceType(p_18060_, NamespacedSchema.m_17311_(p_18061_));
        }
        return super.getChoiceType(p_18060_, p_18061_);
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_18063_) {
        HashMap $$1 = Maps.newHashMap();
        V704.m_18043_(p_18063_, $$1, "minecraft:furnace");
        V704.m_18043_(p_18063_, $$1, "minecraft:chest");
        p_18063_.registerSimple((Map)$$1, "minecraft:ender_chest");
        p_18063_.register((Map)$$1, "minecraft:jukebox", p_18058_ -> DSL.optionalFields((String)"RecordItem", (TypeTemplate)References.f_16782_.in(p_18063_)));
        V704.m_18043_(p_18063_, $$1, "minecraft:dispenser");
        V704.m_18043_(p_18063_, $$1, "minecraft:dropper");
        p_18063_.registerSimple((Map)$$1, "minecraft:sign");
        p_18063_.register((Map)$$1, "minecraft:mob_spawner", p_18055_ -> References.f_16789_.in(p_18063_));
        p_18063_.registerSimple((Map)$$1, "minecraft:noteblock");
        p_18063_.registerSimple((Map)$$1, "minecraft:piston");
        V704.m_18043_(p_18063_, $$1, "minecraft:brewing_stand");
        p_18063_.registerSimple((Map)$$1, "minecraft:enchanting_table");
        p_18063_.registerSimple((Map)$$1, "minecraft:end_portal");
        p_18063_.registerSimple((Map)$$1, "minecraft:beacon");
        p_18063_.registerSimple((Map)$$1, "minecraft:skull");
        p_18063_.registerSimple((Map)$$1, "minecraft:daylight_detector");
        V704.m_18043_(p_18063_, $$1, "minecraft:hopper");
        p_18063_.registerSimple((Map)$$1, "minecraft:comparator");
        p_18063_.register((Map)$$1, "minecraft:flower_pot", p_18042_ -> DSL.optionalFields((String)"Item", (TypeTemplate)DSL.or((TypeTemplate)DSL.constType((Type)DSL.intType()), (TypeTemplate)References.f_16788_.in(p_18063_))));
        p_18063_.registerSimple((Map)$$1, "minecraft:banner");
        p_18063_.registerSimple((Map)$$1, "minecraft:structure_block");
        p_18063_.registerSimple((Map)$$1, "minecraft:end_gateway");
        p_18063_.registerSimple((Map)$$1, "minecraft:command_block");
        return $$1;
    }

    public void registerTypes(Schema p_18065_, Map<String, Supplier<TypeTemplate>> p_18066_, Map<String, Supplier<TypeTemplate>> p_18067_) {
        super.registerTypes(p_18065_, p_18066_, p_18067_);
        p_18065_.registerType(false, References.f_16781_, () -> DSL.taggedChoiceLazy((String)"id", NamespacedSchema.m_17310_(), (Map)p_18067_));
        p_18065_.registerType(true, References.f_16782_, () -> DSL.hook((TypeTemplate)DSL.optionalFields((String)"id", (TypeTemplate)References.f_16788_.in(p_18065_), (String)"tag", (TypeTemplate)DSL.optionalFields((String)"EntityTag", (TypeTemplate)References.f_16785_.in(p_18065_), (String)"BlockEntityTag", (TypeTemplate)References.f_16781_.in(p_18065_), (String)"CanDestroy", (TypeTemplate)DSL.list((TypeTemplate)References.f_16787_.in(p_18065_)), (String)"CanPlaceOn", (TypeTemplate)DSL.list((TypeTemplate)References.f_16787_.in(p_18065_)), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18065_)))), (Hook.HookFunction)f_18033_, (Hook.HookFunction)Hook.HookFunction.IDENTITY));
    }
}

