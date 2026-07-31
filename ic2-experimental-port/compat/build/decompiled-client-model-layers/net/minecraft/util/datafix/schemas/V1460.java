/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.Hook$HookFunction
 *  com.mojang.datafixers.types.templates.TypeTemplate
 */
package net.minecraft.util.datafix.schemas;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Hook;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.util.datafix.schemas.V100;
import net.minecraft.util.datafix.schemas.V1451_6;
import net.minecraft.util.datafix.schemas.V705;

public class V1460
extends NamespacedSchema {
    public V1460(int p_17553_, Schema p_17554_) {
        super(p_17553_, p_17554_);
    }

    protected static void m_17560_(Schema p_17561_, Map<String, Supplier<TypeTemplate>> p_17562_, String p_17563_) {
        p_17561_.register(p_17562_, p_17563_, () -> V100.m_17330_(p_17561_));
    }

    protected static void m_17575_(Schema p_17576_, Map<String, Supplier<TypeTemplate>> p_17577_, String p_17578_) {
        p_17576_.register(p_17577_, p_17578_, () -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17576_))));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17658_) {
        HashMap $$1 = Maps.newHashMap();
        p_17658_.registerSimple((Map)$$1, "minecraft:area_effect_cloud");
        V1460.m_17560_(p_17658_, $$1, "minecraft:armor_stand");
        p_17658_.register((Map)$$1, "minecraft:arrow", p_17683_ -> DSL.optionalFields((String)"inBlockState", (TypeTemplate)References.f_16783_.in(p_17658_)));
        V1460.m_17560_(p_17658_, $$1, "minecraft:bat");
        V1460.m_17560_(p_17658_, $$1, "minecraft:blaze");
        p_17658_.registerSimple((Map)$$1, "minecraft:boat");
        V1460.m_17560_(p_17658_, $$1, "minecraft:cave_spider");
        p_17658_.register((Map)$$1, "minecraft:chest_minecart", p_17680_ -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)References.f_16783_.in(p_17658_), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17658_))));
        V1460.m_17560_(p_17658_, $$1, "minecraft:chicken");
        p_17658_.register((Map)$$1, "minecraft:commandblock_minecart", p_17677_ -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)References.f_16783_.in(p_17658_)));
        V1460.m_17560_(p_17658_, $$1, "minecraft:cow");
        V1460.m_17560_(p_17658_, $$1, "minecraft:creeper");
        p_17658_.register((Map)$$1, "minecraft:donkey", p_17674_ -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17658_)), (String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_17658_), (TypeTemplate)V100.m_17330_(p_17658_)));
        p_17658_.registerSimple((Map)$$1, "minecraft:dragon_fireball");
        p_17658_.registerSimple((Map)$$1, "minecraft:egg");
        V1460.m_17560_(p_17658_, $$1, "minecraft:elder_guardian");
        p_17658_.registerSimple((Map)$$1, "minecraft:ender_crystal");
        V1460.m_17560_(p_17658_, $$1, "minecraft:ender_dragon");
        p_17658_.register((Map)$$1, "minecraft:enderman", p_17671_ -> DSL.optionalFields((String)"carriedBlockState", (TypeTemplate)References.f_16783_.in(p_17658_), (TypeTemplate)V100.m_17330_(p_17658_)));
        V1460.m_17560_(p_17658_, $$1, "minecraft:endermite");
        p_17658_.registerSimple((Map)$$1, "minecraft:ender_pearl");
        p_17658_.registerSimple((Map)$$1, "minecraft:evocation_fangs");
        V1460.m_17560_(p_17658_, $$1, "minecraft:evocation_illager");
        p_17658_.registerSimple((Map)$$1, "minecraft:eye_of_ender_signal");
        p_17658_.register((Map)$$1, "minecraft:falling_block", p_17668_ -> DSL.optionalFields((String)"BlockState", (TypeTemplate)References.f_16783_.in(p_17658_), (String)"TileEntityData", (TypeTemplate)References.f_16781_.in(p_17658_)));
        p_17658_.registerSimple((Map)$$1, "minecraft:fireball");
        p_17658_.register((Map)$$1, "minecraft:fireworks_rocket", p_17665_ -> DSL.optionalFields((String)"FireworksItem", (TypeTemplate)References.f_16782_.in(p_17658_)));
        p_17658_.register((Map)$$1, "minecraft:furnace_minecart", p_17654_ -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)References.f_16783_.in(p_17658_)));
        V1460.m_17560_(p_17658_, $$1, "minecraft:ghast");
        V1460.m_17560_(p_17658_, $$1, "minecraft:giant");
        V1460.m_17560_(p_17658_, $$1, "minecraft:guardian");
        p_17658_.register((Map)$$1, "minecraft:hopper_minecart", p_17651_ -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)References.f_16783_.in(p_17658_), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17658_))));
        p_17658_.register((Map)$$1, "minecraft:horse", p_17648_ -> DSL.optionalFields((String)"ArmorItem", (TypeTemplate)References.f_16782_.in(p_17658_), (String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_17658_), (TypeTemplate)V100.m_17330_(p_17658_)));
        V1460.m_17560_(p_17658_, $$1, "minecraft:husk");
        p_17658_.registerSimple((Map)$$1, "minecraft:illusion_illager");
        p_17658_.register((Map)$$1, "minecraft:item", p_17645_ -> DSL.optionalFields((String)"Item", (TypeTemplate)References.f_16782_.in(p_17658_)));
        p_17658_.register((Map)$$1, "minecraft:item_frame", p_17642_ -> DSL.optionalFields((String)"Item", (TypeTemplate)References.f_16782_.in(p_17658_)));
        p_17658_.registerSimple((Map)$$1, "minecraft:leash_knot");
        p_17658_.register((Map)$$1, "minecraft:llama", p_17639_ -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17658_)), (String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_17658_), (String)"DecorItem", (TypeTemplate)References.f_16782_.in(p_17658_), (TypeTemplate)V100.m_17330_(p_17658_)));
        p_17658_.registerSimple((Map)$$1, "minecraft:llama_spit");
        V1460.m_17560_(p_17658_, $$1, "minecraft:magma_cube");
        p_17658_.register((Map)$$1, "minecraft:minecart", p_17634_ -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)References.f_16783_.in(p_17658_)));
        V1460.m_17560_(p_17658_, $$1, "minecraft:mooshroom");
        p_17658_.register((Map)$$1, "minecraft:mule", p_17629_ -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17658_)), (String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_17658_), (TypeTemplate)V100.m_17330_(p_17658_)));
        V1460.m_17560_(p_17658_, $$1, "minecraft:ocelot");
        p_17658_.registerSimple((Map)$$1, "minecraft:painting");
        p_17658_.registerSimple((Map)$$1, "minecraft:parrot");
        V1460.m_17560_(p_17658_, $$1, "minecraft:pig");
        V1460.m_17560_(p_17658_, $$1, "minecraft:polar_bear");
        p_17658_.register((Map)$$1, "minecraft:potion", p_17624_ -> DSL.optionalFields((String)"Potion", (TypeTemplate)References.f_16782_.in(p_17658_)));
        V1460.m_17560_(p_17658_, $$1, "minecraft:rabbit");
        V1460.m_17560_(p_17658_, $$1, "minecraft:sheep");
        V1460.m_17560_(p_17658_, $$1, "minecraft:shulker");
        p_17658_.registerSimple((Map)$$1, "minecraft:shulker_bullet");
        V1460.m_17560_(p_17658_, $$1, "minecraft:silverfish");
        V1460.m_17560_(p_17658_, $$1, "minecraft:skeleton");
        p_17658_.register((Map)$$1, "minecraft:skeleton_horse", p_17619_ -> DSL.optionalFields((String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_17658_), (TypeTemplate)V100.m_17330_(p_17658_)));
        V1460.m_17560_(p_17658_, $$1, "minecraft:slime");
        p_17658_.registerSimple((Map)$$1, "minecraft:small_fireball");
        p_17658_.registerSimple((Map)$$1, "minecraft:snowball");
        V1460.m_17560_(p_17658_, $$1, "minecraft:snowman");
        p_17658_.register((Map)$$1, "minecraft:spawner_minecart", p_17614_ -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)References.f_16783_.in(p_17658_), (TypeTemplate)References.f_16789_.in(p_17658_)));
        p_17658_.register((Map)$$1, "minecraft:spectral_arrow", p_17609_ -> DSL.optionalFields((String)"inBlockState", (TypeTemplate)References.f_16783_.in(p_17658_)));
        V1460.m_17560_(p_17658_, $$1, "minecraft:spider");
        V1460.m_17560_(p_17658_, $$1, "minecraft:squid");
        V1460.m_17560_(p_17658_, $$1, "minecraft:stray");
        p_17658_.registerSimple((Map)$$1, "minecraft:tnt");
        p_17658_.register((Map)$$1, "minecraft:tnt_minecart", p_17604_ -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)References.f_16783_.in(p_17658_)));
        V1460.m_17560_(p_17658_, $$1, "minecraft:vex");
        p_17658_.register((Map)$$1, "minecraft:villager", p_17598_ -> DSL.optionalFields((String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17658_)), (String)"Offers", (TypeTemplate)DSL.optionalFields((String)"Recipes", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"buy", (TypeTemplate)References.f_16782_.in(p_17658_), (String)"buyB", (TypeTemplate)References.f_16782_.in(p_17658_), (String)"sell", (TypeTemplate)References.f_16782_.in(p_17658_)))), (TypeTemplate)V100.m_17330_(p_17658_)));
        V1460.m_17560_(p_17658_, $$1, "minecraft:villager_golem");
        V1460.m_17560_(p_17658_, $$1, "minecraft:vindication_illager");
        V1460.m_17560_(p_17658_, $$1, "minecraft:witch");
        V1460.m_17560_(p_17658_, $$1, "minecraft:wither");
        V1460.m_17560_(p_17658_, $$1, "minecraft:wither_skeleton");
        p_17658_.registerSimple((Map)$$1, "minecraft:wither_skull");
        V1460.m_17560_(p_17658_, $$1, "minecraft:wolf");
        p_17658_.registerSimple((Map)$$1, "minecraft:xp_bottle");
        p_17658_.registerSimple((Map)$$1, "minecraft:xp_orb");
        V1460.m_17560_(p_17658_, $$1, "minecraft:zombie");
        p_17658_.register((Map)$$1, "minecraft:zombie_horse", p_17592_ -> DSL.optionalFields((String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_17658_), (TypeTemplate)V100.m_17330_(p_17658_)));
        V1460.m_17560_(p_17658_, $$1, "minecraft:zombie_pigman");
        V1460.m_17560_(p_17658_, $$1, "minecraft:zombie_villager");
        return $$1;
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_17656_) {
        HashMap $$1 = Maps.newHashMap();
        V1460.m_17575_(p_17656_, $$1, "minecraft:furnace");
        V1460.m_17575_(p_17656_, $$1, "minecraft:chest");
        V1460.m_17575_(p_17656_, $$1, "minecraft:trapped_chest");
        p_17656_.registerSimple((Map)$$1, "minecraft:ender_chest");
        p_17656_.register((Map)$$1, "minecraft:jukebox", p_17586_ -> DSL.optionalFields((String)"RecordItem", (TypeTemplate)References.f_16782_.in(p_17656_)));
        V1460.m_17575_(p_17656_, $$1, "minecraft:dispenser");
        V1460.m_17575_(p_17656_, $$1, "minecraft:dropper");
        p_17656_.registerSimple((Map)$$1, "minecraft:sign");
        p_17656_.register((Map)$$1, "minecraft:mob_spawner", p_17574_ -> References.f_16789_.in(p_17656_));
        p_17656_.register((Map)$$1, "minecraft:piston", p_17559_ -> DSL.optionalFields((String)"blockState", (TypeTemplate)References.f_16783_.in(p_17656_)));
        V1460.m_17575_(p_17656_, $$1, "minecraft:brewing_stand");
        p_17656_.registerSimple((Map)$$1, "minecraft:enchanting_table");
        p_17656_.registerSimple((Map)$$1, "minecraft:end_portal");
        p_17656_.registerSimple((Map)$$1, "minecraft:beacon");
        p_17656_.registerSimple((Map)$$1, "minecraft:skull");
        p_17656_.registerSimple((Map)$$1, "minecraft:daylight_detector");
        V1460.m_17575_(p_17656_, $$1, "minecraft:hopper");
        p_17656_.registerSimple((Map)$$1, "minecraft:comparator");
        p_17656_.registerSimple((Map)$$1, "minecraft:banner");
        p_17656_.registerSimple((Map)$$1, "minecraft:structure_block");
        p_17656_.registerSimple((Map)$$1, "minecraft:end_gateway");
        p_17656_.registerSimple((Map)$$1, "minecraft:command_block");
        V1460.m_17575_(p_17656_, $$1, "minecraft:shulker_box");
        p_17656_.registerSimple((Map)$$1, "minecraft:bed");
        return $$1;
    }

    public void registerTypes(Schema p_17660_, Map<String, Supplier<TypeTemplate>> p_17661_, Map<String, Supplier<TypeTemplate>> p_17662_) {
        p_17660_.registerType(false, References.f_16771_, DSL::remainder);
        p_17660_.registerType(false, References.f_16793_, () -> DSL.constType(V1460.m_17310_()));
        p_17660_.registerType(false, References.f_16772_, () -> DSL.optionalFields((String)"RootVehicle", (TypeTemplate)DSL.optionalFields((String)"Entity", (TypeTemplate)References.f_16785_.in(p_17660_)), (String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17660_)), (String)"EnderItems", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17660_)), (TypeTemplate)DSL.optionalFields((String)"ShoulderEntityLeft", (TypeTemplate)References.f_16785_.in(p_17660_), (String)"ShoulderEntityRight", (TypeTemplate)References.f_16785_.in(p_17660_), (String)"recipeBook", (TypeTemplate)DSL.optionalFields((String)"recipes", (TypeTemplate)DSL.list((TypeTemplate)References.f_16793_.in(p_17660_)), (String)"toBeDisplayed", (TypeTemplate)DSL.list((TypeTemplate)References.f_16793_.in(p_17660_))))));
        p_17660_.registerType(false, References.f_16773_, () -> DSL.fields((String)"Level", (TypeTemplate)DSL.optionalFields((String)"Entities", (TypeTemplate)DSL.list((TypeTemplate)References.f_16785_.in(p_17660_)), (String)"TileEntities", (TypeTemplate)DSL.list((TypeTemplate)DSL.or((TypeTemplate)References.f_16781_.in(p_17660_), (TypeTemplate)DSL.remainder())), (String)"TileTicks", (TypeTemplate)DSL.list((TypeTemplate)DSL.fields((String)"i", (TypeTemplate)References.f_16787_.in(p_17660_))), (String)"Sections", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"Palette", (TypeTemplate)DSL.list((TypeTemplate)References.f_16783_.in(p_17660_)))))));
        p_17660_.registerType(true, References.f_16781_, () -> DSL.taggedChoiceLazy((String)"id", V1460.m_17310_(), (Map)p_17662_));
        p_17660_.registerType(true, References.f_16785_, () -> DSL.optionalFields((String)"Passengers", (TypeTemplate)DSL.list((TypeTemplate)References.f_16785_.in(p_17660_)), (TypeTemplate)References.f_16786_.in(p_17660_)));
        p_17660_.registerType(true, References.f_16786_, () -> DSL.taggedChoiceLazy((String)"id", V1460.m_17310_(), (Map)p_17661_));
        p_17660_.registerType(true, References.f_16782_, () -> DSL.hook((TypeTemplate)DSL.optionalFields((String)"id", (TypeTemplate)References.f_16788_.in(p_17660_), (String)"tag", (TypeTemplate)DSL.optionalFields((String)"EntityTag", (TypeTemplate)References.f_16785_.in(p_17660_), (String)"BlockEntityTag", (TypeTemplate)References.f_16781_.in(p_17660_), (String)"CanDestroy", (TypeTemplate)DSL.list((TypeTemplate)References.f_16787_.in(p_17660_)), (String)"CanPlaceOn", (TypeTemplate)DSL.list((TypeTemplate)References.f_16787_.in(p_17660_)), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17660_)))), (Hook.HookFunction)V705.f_18072_, (Hook.HookFunction)Hook.HookFunction.IDENTITY));
        p_17660_.registerType(false, References.f_16774_, () -> DSL.compoundList((TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17660_))));
        p_17660_.registerType(false, References.f_16775_, DSL::remainder);
        p_17660_.registerType(false, References.f_16776_, () -> DSL.optionalFields((String)"entities", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"nbt", (TypeTemplate)References.f_16785_.in(p_17660_))), (String)"blocks", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"nbt", (TypeTemplate)References.f_16781_.in(p_17660_))), (String)"palette", (TypeTemplate)DSL.list((TypeTemplate)References.f_16783_.in(p_17660_))));
        p_17660_.registerType(false, References.f_16787_, () -> DSL.constType(V1460.m_17310_()));
        p_17660_.registerType(false, References.f_16788_, () -> DSL.constType(V1460.m_17310_()));
        p_17660_.registerType(false, References.f_16783_, DSL::remainder);
        Supplier<TypeTemplate> $$3 = () -> DSL.compoundList((TypeTemplate)References.f_16788_.in(p_17660_), (TypeTemplate)DSL.constType((Type)DSL.intType()));
        p_17660_.registerType(false, References.f_16777_, () -> DSL.optionalFields((String)"stats", (TypeTemplate)DSL.optionalFields((String)"minecraft:mined", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16787_.in(p_17660_), (TypeTemplate)DSL.constType((Type)DSL.intType())), (String)"minecraft:crafted", (TypeTemplate)((TypeTemplate)$$3.get()), (String)"minecraft:used", (TypeTemplate)((TypeTemplate)$$3.get()), (String)"minecraft:broken", (TypeTemplate)((TypeTemplate)$$3.get()), (String)"minecraft:picked_up", (TypeTemplate)((TypeTemplate)$$3.get()), (TypeTemplate)DSL.optionalFields((String)"minecraft:dropped", (TypeTemplate)((TypeTemplate)$$3.get()), (String)"minecraft:killed", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16784_.in(p_17660_), (TypeTemplate)DSL.constType((Type)DSL.intType())), (String)"minecraft:killed_by", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16784_.in(p_17660_), (TypeTemplate)DSL.constType((Type)DSL.intType())), (String)"minecraft:custom", (TypeTemplate)DSL.compoundList((TypeTemplate)DSL.constType(V1460.m_17310_()), (TypeTemplate)DSL.constType((Type)DSL.intType()))))));
        p_17660_.registerType(false, References.f_16778_, () -> DSL.optionalFields((String)"data", (TypeTemplate)DSL.optionalFields((String)"Features", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16790_.in(p_17660_)), (String)"Objectives", (TypeTemplate)DSL.list((TypeTemplate)References.f_16791_.in(p_17660_)), (String)"Teams", (TypeTemplate)DSL.list((TypeTemplate)References.f_16792_.in(p_17660_)))));
        p_17660_.registerType(false, References.f_16790_, () -> DSL.optionalFields((String)"Children", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"CA", (TypeTemplate)References.f_16783_.in(p_17660_), (String)"CB", (TypeTemplate)References.f_16783_.in(p_17660_), (String)"CC", (TypeTemplate)References.f_16783_.in(p_17660_), (String)"CD", (TypeTemplate)References.f_16783_.in(p_17660_)))));
        Map<String, Supplier<TypeTemplate>> $$4 = V1451_6.m_181077_(p_17660_);
        p_17660_.registerType(false, References.f_16791_, () -> DSL.hook((TypeTemplate)DSL.optionalFields((String)"CriteriaType", (TypeTemplate)DSL.taggedChoiceLazy((String)"type", (Type)DSL.string(), (Map)$$4)), (Hook.HookFunction)V1451_6.f_181074_, (Hook.HookFunction)V1451_6.f_181075_));
        p_17660_.registerType(false, References.f_16792_, DSL::remainder);
        p_17660_.registerType(true, References.f_16789_, () -> DSL.optionalFields((String)"SpawnPotentials", (TypeTemplate)DSL.list((TypeTemplate)DSL.fields((String)"Entity", (TypeTemplate)References.f_16785_.in(p_17660_))), (String)"SpawnData", (TypeTemplate)References.f_16785_.in(p_17660_)));
        p_17660_.registerType(false, References.f_16779_, () -> DSL.optionalFields((String)"minecraft:adventure/adventuring_time", (TypeTemplate)DSL.optionalFields((String)"criteria", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16794_.in(p_17660_), (TypeTemplate)DSL.constType((Type)DSL.string()))), (String)"minecraft:adventure/kill_a_mob", (TypeTemplate)DSL.optionalFields((String)"criteria", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16784_.in(p_17660_), (TypeTemplate)DSL.constType((Type)DSL.string()))), (String)"minecraft:adventure/kill_all_mobs", (TypeTemplate)DSL.optionalFields((String)"criteria", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16784_.in(p_17660_), (TypeTemplate)DSL.constType((Type)DSL.string()))), (String)"minecraft:husbandry/bred_all_animals", (TypeTemplate)DSL.optionalFields((String)"criteria", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16784_.in(p_17660_), (TypeTemplate)DSL.constType((Type)DSL.string())))));
        p_17660_.registerType(false, References.f_16794_, () -> DSL.constType(V1460.m_17310_()));
        p_17660_.registerType(false, References.f_16784_, () -> DSL.constType(V1460.m_17310_()));
        p_17660_.registerType(false, References.f_16780_, DSL::remainder);
        p_17660_.registerType(true, References.f_16795_, DSL::remainder);
        p_17660_.registerType(false, References.f_145628_, () -> DSL.optionalFields((String)"Entities", (TypeTemplate)DSL.list((TypeTemplate)References.f_16785_.in(p_17660_))));
    }
}

