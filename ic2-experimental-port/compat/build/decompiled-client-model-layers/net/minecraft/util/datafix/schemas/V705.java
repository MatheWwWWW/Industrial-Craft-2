/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.templates.Hook$HookFunction
 *  com.mojang.datafixers.types.templates.TypeTemplate
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 */
package net.minecraft.util.datafix.schemas;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.Hook;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.util.datafix.schemas.V100;
import net.minecraft.util.datafix.schemas.V704;
import net.minecraft.util.datafix.schemas.V99;

public class V705
extends NamespacedSchema {
    protected static final Hook.HookFunction f_18072_ = new Hook.HookFunction(){

        public <T> T apply(DynamicOps<T> p_18167_, T p_18168_) {
            return V99.m_18205_(new Dynamic(p_18167_, p_18168_), V704.f_18032_, "minecraft:armor_stand");
        }
    };

    public V705(int p_18075_, Schema p_18076_) {
        super(p_18075_, p_18076_);
    }

    protected static void m_18082_(Schema p_18083_, Map<String, Supplier<TypeTemplate>> p_18084_, String p_18085_) {
        p_18083_.register(p_18084_, p_18085_, () -> V100.m_17330_(p_18083_));
    }

    protected static void m_18093_(Schema p_18094_, Map<String, Supplier<TypeTemplate>> p_18095_, String p_18096_) {
        p_18094_.register(p_18095_, p_18096_, () -> DSL.optionalFields((String)"inTile", (TypeTemplate)References.f_16787_.in(p_18094_)));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_18148_) {
        HashMap $$1 = Maps.newHashMap();
        p_18148_.registerSimple((Map)$$1, "minecraft:area_effect_cloud");
        V705.m_18082_(p_18148_, $$1, "minecraft:armor_stand");
        p_18148_.register((Map)$$1, "minecraft:arrow", p_18164_ -> DSL.optionalFields((String)"inTile", (TypeTemplate)References.f_16787_.in(p_18148_)));
        V705.m_18082_(p_18148_, $$1, "minecraft:bat");
        V705.m_18082_(p_18148_, $$1, "minecraft:blaze");
        p_18148_.registerSimple((Map)$$1, "minecraft:boat");
        V705.m_18082_(p_18148_, $$1, "minecraft:cave_spider");
        p_18148_.register((Map)$$1, "minecraft:chest_minecart", p_18161_ -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.f_16787_.in(p_18148_), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18148_))));
        V705.m_18082_(p_18148_, $$1, "minecraft:chicken");
        p_18148_.register((Map)$$1, "minecraft:commandblock_minecart", p_18158_ -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.f_16787_.in(p_18148_)));
        V705.m_18082_(p_18148_, $$1, "minecraft:cow");
        V705.m_18082_(p_18148_, $$1, "minecraft:creeper");
        p_18148_.register((Map)$$1, "minecraft:donkey", p_18155_ -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18148_)), (String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_18148_), (TypeTemplate)V100.m_17330_(p_18148_)));
        p_18148_.registerSimple((Map)$$1, "minecraft:dragon_fireball");
        V705.m_18093_(p_18148_, $$1, "minecraft:egg");
        V705.m_18082_(p_18148_, $$1, "minecraft:elder_guardian");
        p_18148_.registerSimple((Map)$$1, "minecraft:ender_crystal");
        V705.m_18082_(p_18148_, $$1, "minecraft:ender_dragon");
        p_18148_.register((Map)$$1, "minecraft:enderman", p_18146_ -> DSL.optionalFields((String)"carried", (TypeTemplate)References.f_16787_.in(p_18148_), (TypeTemplate)V100.m_17330_(p_18148_)));
        V705.m_18082_(p_18148_, $$1, "minecraft:endermite");
        V705.m_18093_(p_18148_, $$1, "minecraft:ender_pearl");
        p_18148_.registerSimple((Map)$$1, "minecraft:eye_of_ender_signal");
        p_18148_.register((Map)$$1, "minecraft:falling_block", p_18143_ -> DSL.optionalFields((String)"Block", (TypeTemplate)References.f_16787_.in(p_18148_), (String)"TileEntityData", (TypeTemplate)References.f_16781_.in(p_18148_)));
        V705.m_18093_(p_18148_, $$1, "minecraft:fireball");
        p_18148_.register((Map)$$1, "minecraft:fireworks_rocket", p_18140_ -> DSL.optionalFields((String)"FireworksItem", (TypeTemplate)References.f_16782_.in(p_18148_)));
        p_18148_.register((Map)$$1, "minecraft:furnace_minecart", p_18137_ -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.f_16787_.in(p_18148_)));
        V705.m_18082_(p_18148_, $$1, "minecraft:ghast");
        V705.m_18082_(p_18148_, $$1, "minecraft:giant");
        V705.m_18082_(p_18148_, $$1, "minecraft:guardian");
        p_18148_.register((Map)$$1, "minecraft:hopper_minecart", p_18134_ -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.f_16787_.in(p_18148_), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18148_))));
        p_18148_.register((Map)$$1, "minecraft:horse", p_18131_ -> DSL.optionalFields((String)"ArmorItem", (TypeTemplate)References.f_16782_.in(p_18148_), (String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_18148_), (TypeTemplate)V100.m_17330_(p_18148_)));
        V705.m_18082_(p_18148_, $$1, "minecraft:husk");
        p_18148_.register((Map)$$1, "minecraft:item", p_18128_ -> DSL.optionalFields((String)"Item", (TypeTemplate)References.f_16782_.in(p_18148_)));
        p_18148_.register((Map)$$1, "minecraft:item_frame", p_18125_ -> DSL.optionalFields((String)"Item", (TypeTemplate)References.f_16782_.in(p_18148_)));
        p_18148_.registerSimple((Map)$$1, "minecraft:leash_knot");
        V705.m_18082_(p_18148_, $$1, "minecraft:magma_cube");
        p_18148_.register((Map)$$1, "minecraft:minecart", p_18122_ -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.f_16787_.in(p_18148_)));
        V705.m_18082_(p_18148_, $$1, "minecraft:mooshroom");
        p_18148_.register((Map)$$1, "minecraft:mule", p_18119_ -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18148_)), (String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_18148_), (TypeTemplate)V100.m_17330_(p_18148_)));
        V705.m_18082_(p_18148_, $$1, "minecraft:ocelot");
        p_18148_.registerSimple((Map)$$1, "minecraft:painting");
        p_18148_.registerSimple((Map)$$1, "minecraft:parrot");
        V705.m_18082_(p_18148_, $$1, "minecraft:pig");
        V705.m_18082_(p_18148_, $$1, "minecraft:polar_bear");
        p_18148_.register((Map)$$1, "minecraft:potion", p_18116_ -> DSL.optionalFields((String)"Potion", (TypeTemplate)References.f_16782_.in(p_18148_), (String)"inTile", (TypeTemplate)References.f_16787_.in(p_18148_)));
        V705.m_18082_(p_18148_, $$1, "minecraft:rabbit");
        V705.m_18082_(p_18148_, $$1, "minecraft:sheep");
        V705.m_18082_(p_18148_, $$1, "minecraft:shulker");
        p_18148_.registerSimple((Map)$$1, "minecraft:shulker_bullet");
        V705.m_18082_(p_18148_, $$1, "minecraft:silverfish");
        V705.m_18082_(p_18148_, $$1, "minecraft:skeleton");
        p_18148_.register((Map)$$1, "minecraft:skeleton_horse", p_18113_ -> DSL.optionalFields((String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_18148_), (TypeTemplate)V100.m_17330_(p_18148_)));
        V705.m_18082_(p_18148_, $$1, "minecraft:slime");
        V705.m_18093_(p_18148_, $$1, "minecraft:small_fireball");
        V705.m_18093_(p_18148_, $$1, "minecraft:snowball");
        V705.m_18082_(p_18148_, $$1, "minecraft:snowman");
        p_18148_.register((Map)$$1, "minecraft:spawner_minecart", p_18110_ -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.f_16787_.in(p_18148_), (TypeTemplate)References.f_16789_.in(p_18148_)));
        p_18148_.register((Map)$$1, "minecraft:spectral_arrow", p_18107_ -> DSL.optionalFields((String)"inTile", (TypeTemplate)References.f_16787_.in(p_18148_)));
        V705.m_18082_(p_18148_, $$1, "minecraft:spider");
        V705.m_18082_(p_18148_, $$1, "minecraft:squid");
        V705.m_18082_(p_18148_, $$1, "minecraft:stray");
        p_18148_.registerSimple((Map)$$1, "minecraft:tnt");
        p_18148_.register((Map)$$1, "minecraft:tnt_minecart", p_18104_ -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.f_16787_.in(p_18148_)));
        p_18148_.register((Map)$$1, "minecraft:villager", p_18101_ -> DSL.optionalFields((String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18148_)), (String)"Offers", (TypeTemplate)DSL.optionalFields((String)"Recipes", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"buy", (TypeTemplate)References.f_16782_.in(p_18148_), (String)"buyB", (TypeTemplate)References.f_16782_.in(p_18148_), (String)"sell", (TypeTemplate)References.f_16782_.in(p_18148_)))), (TypeTemplate)V100.m_17330_(p_18148_)));
        V705.m_18082_(p_18148_, $$1, "minecraft:villager_golem");
        V705.m_18082_(p_18148_, $$1, "minecraft:witch");
        V705.m_18082_(p_18148_, $$1, "minecraft:wither");
        V705.m_18082_(p_18148_, $$1, "minecraft:wither_skeleton");
        V705.m_18093_(p_18148_, $$1, "minecraft:wither_skull");
        V705.m_18082_(p_18148_, $$1, "minecraft:wolf");
        V705.m_18093_(p_18148_, $$1, "minecraft:xp_bottle");
        p_18148_.registerSimple((Map)$$1, "minecraft:xp_orb");
        V705.m_18082_(p_18148_, $$1, "minecraft:zombie");
        p_18148_.register((Map)$$1, "minecraft:zombie_horse", p_18092_ -> DSL.optionalFields((String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_18148_), (TypeTemplate)V100.m_17330_(p_18148_)));
        V705.m_18082_(p_18148_, $$1, "minecraft:zombie_pigman");
        V705.m_18082_(p_18148_, $$1, "minecraft:zombie_villager");
        p_18148_.registerSimple((Map)$$1, "minecraft:evocation_fangs");
        V705.m_18082_(p_18148_, $$1, "minecraft:evocation_illager");
        p_18148_.registerSimple((Map)$$1, "minecraft:illusion_illager");
        p_18148_.register((Map)$$1, "minecraft:llama", p_18081_ -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18148_)), (String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_18148_), (String)"DecorItem", (TypeTemplate)References.f_16782_.in(p_18148_), (TypeTemplate)V100.m_17330_(p_18148_)));
        p_18148_.registerSimple((Map)$$1, "minecraft:llama_spit");
        V705.m_18082_(p_18148_, $$1, "minecraft:vex");
        V705.m_18082_(p_18148_, $$1, "minecraft:vindication_illager");
        return $$1;
    }

    public void registerTypes(Schema p_18150_, Map<String, Supplier<TypeTemplate>> p_18151_, Map<String, Supplier<TypeTemplate>> p_18152_) {
        super.registerTypes(p_18150_, p_18151_, p_18152_);
        p_18150_.registerType(true, References.f_16786_, () -> DSL.taggedChoiceLazy((String)"id", V705.m_17310_(), (Map)p_18151_));
        p_18150_.registerType(true, References.f_16782_, () -> DSL.hook((TypeTemplate)DSL.optionalFields((String)"id", (TypeTemplate)References.f_16788_.in(p_18150_), (String)"tag", (TypeTemplate)DSL.optionalFields((String)"EntityTag", (TypeTemplate)References.f_16785_.in(p_18150_), (String)"BlockEntityTag", (TypeTemplate)References.f_16781_.in(p_18150_), (String)"CanDestroy", (TypeTemplate)DSL.list((TypeTemplate)References.f_16787_.in(p_18150_)), (String)"CanPlaceOn", (TypeTemplate)DSL.list((TypeTemplate)References.f_16787_.in(p_18150_)), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18150_)))), (Hook.HookFunction)f_18072_, (Hook.HookFunction)Hook.HookFunction.IDENTITY));
    }
}

