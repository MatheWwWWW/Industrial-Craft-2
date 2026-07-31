/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.Hook$HookFunction
 *  com.mojang.datafixers.types.templates.TypeTemplate
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  org.slf4j.Logger
 */
package net.minecraft.util.datafix.schemas;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Hook;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.slf4j.Logger;

public class V99
extends Schema {
    private static final Logger f_18181_ = LogUtils.getLogger();
    static final Map<String, String> f_18182_ = (Map)DataFixUtils.make((Object)Maps.newHashMap(), p_145919_ -> {
        p_145919_.put("minecraft:furnace", "Furnace");
        p_145919_.put("minecraft:lit_furnace", "Furnace");
        p_145919_.put("minecraft:chest", "Chest");
        p_145919_.put("minecraft:trapped_chest", "Chest");
        p_145919_.put("minecraft:ender_chest", "EnderChest");
        p_145919_.put("minecraft:jukebox", "RecordPlayer");
        p_145919_.put("minecraft:dispenser", "Trap");
        p_145919_.put("minecraft:dropper", "Dropper");
        p_145919_.put("minecraft:sign", "Sign");
        p_145919_.put("minecraft:mob_spawner", "MobSpawner");
        p_145919_.put("minecraft:noteblock", "Music");
        p_145919_.put("minecraft:brewing_stand", "Cauldron");
        p_145919_.put("minecraft:enhanting_table", "EnchantTable");
        p_145919_.put("minecraft:command_block", "CommandBlock");
        p_145919_.put("minecraft:beacon", "Beacon");
        p_145919_.put("minecraft:skull", "Skull");
        p_145919_.put("minecraft:daylight_detector", "DLDetector");
        p_145919_.put("minecraft:hopper", "Hopper");
        p_145919_.put("minecraft:banner", "Banner");
        p_145919_.put("minecraft:flower_pot", "FlowerPot");
        p_145919_.put("minecraft:repeating_command_block", "CommandBlock");
        p_145919_.put("minecraft:chain_command_block", "CommandBlock");
        p_145919_.put("minecraft:standing_sign", "Sign");
        p_145919_.put("minecraft:wall_sign", "Sign");
        p_145919_.put("minecraft:piston_head", "Piston");
        p_145919_.put("minecraft:daylight_detector_inverted", "DLDetector");
        p_145919_.put("minecraft:unpowered_comparator", "Comparator");
        p_145919_.put("minecraft:powered_comparator", "Comparator");
        p_145919_.put("minecraft:wall_banner", "Banner");
        p_145919_.put("minecraft:standing_banner", "Banner");
        p_145919_.put("minecraft:structure_block", "Structure");
        p_145919_.put("minecraft:end_portal", "Airportal");
        p_145919_.put("minecraft:end_gateway", "EndGateway");
        p_145919_.put("minecraft:shield", "Banner");
    });
    protected static final Hook.HookFunction f_18180_ = new Hook.HookFunction(){

        public <T> T apply(DynamicOps<T> p_18312_, T p_18313_) {
            return V99.m_18205_(new Dynamic(p_18312_, p_18313_), f_18182_, "ArmorStand");
        }
    };

    public V99(int p_18185_, Schema p_18186_) {
        super(p_18185_, p_18186_);
    }

    protected static TypeTemplate m_18188_(Schema p_18189_) {
        return DSL.optionalFields((String)"Equipment", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18189_)));
    }

    protected static void m_18193_(Schema p_18194_, Map<String, Supplier<TypeTemplate>> p_18195_, String p_18196_) {
        p_18194_.register(p_18195_, p_18196_, () -> V99.m_18188_(p_18194_));
    }

    protected static void m_18224_(Schema p_18225_, Map<String, Supplier<TypeTemplate>> p_18226_, String p_18227_) {
        p_18225_.register(p_18226_, p_18227_, () -> DSL.optionalFields((String)"inTile", (TypeTemplate)References.f_16787_.in(p_18225_)));
    }

    protected static void m_18236_(Schema p_18237_, Map<String, Supplier<TypeTemplate>> p_18238_, String p_18239_) {
        p_18237_.register(p_18238_, p_18239_, () -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.f_16787_.in(p_18237_)));
    }

    protected static void m_18246_(Schema p_18247_, Map<String, Supplier<TypeTemplate>> p_18248_, String p_18249_) {
        p_18247_.register(p_18248_, p_18249_, () -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18247_))));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_18305_) {
        HashMap $$1 = Maps.newHashMap();
        p_18305_.register((Map)$$1, "Item", p_18301_ -> DSL.optionalFields((String)"Item", (TypeTemplate)References.f_16782_.in(p_18305_)));
        p_18305_.registerSimple((Map)$$1, "XPOrb");
        V99.m_18224_(p_18305_, $$1, "ThrownEgg");
        p_18305_.registerSimple((Map)$$1, "LeashKnot");
        p_18305_.registerSimple((Map)$$1, "Painting");
        p_18305_.register((Map)$$1, "Arrow", p_18298_ -> DSL.optionalFields((String)"inTile", (TypeTemplate)References.f_16787_.in(p_18305_)));
        p_18305_.register((Map)$$1, "TippedArrow", p_18295_ -> DSL.optionalFields((String)"inTile", (TypeTemplate)References.f_16787_.in(p_18305_)));
        p_18305_.register((Map)$$1, "SpectralArrow", p_18292_ -> DSL.optionalFields((String)"inTile", (TypeTemplate)References.f_16787_.in(p_18305_)));
        V99.m_18224_(p_18305_, $$1, "Snowball");
        V99.m_18224_(p_18305_, $$1, "Fireball");
        V99.m_18224_(p_18305_, $$1, "SmallFireball");
        V99.m_18224_(p_18305_, $$1, "ThrownEnderpearl");
        p_18305_.registerSimple((Map)$$1, "EyeOfEnderSignal");
        p_18305_.register((Map)$$1, "ThrownPotion", p_18289_ -> DSL.optionalFields((String)"inTile", (TypeTemplate)References.f_16787_.in(p_18305_), (String)"Potion", (TypeTemplate)References.f_16782_.in(p_18305_)));
        V99.m_18224_(p_18305_, $$1, "ThrownExpBottle");
        p_18305_.register((Map)$$1, "ItemFrame", p_18284_ -> DSL.optionalFields((String)"Item", (TypeTemplate)References.f_16782_.in(p_18305_)));
        V99.m_18224_(p_18305_, $$1, "WitherSkull");
        p_18305_.registerSimple((Map)$$1, "PrimedTnt");
        p_18305_.register((Map)$$1, "FallingSand", p_18279_ -> DSL.optionalFields((String)"Block", (TypeTemplate)References.f_16787_.in(p_18305_), (String)"TileEntityData", (TypeTemplate)References.f_16781_.in(p_18305_)));
        p_18305_.register((Map)$$1, "FireworksRocketEntity", p_18274_ -> DSL.optionalFields((String)"FireworksItem", (TypeTemplate)References.f_16782_.in(p_18305_)));
        p_18305_.registerSimple((Map)$$1, "Boat");
        p_18305_.register((Map)$$1, "Minecart", () -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.f_16787_.in(p_18305_), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18305_))));
        V99.m_18236_(p_18305_, $$1, "MinecartRideable");
        p_18305_.register((Map)$$1, "MinecartChest", p_18269_ -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.f_16787_.in(p_18305_), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18305_))));
        V99.m_18236_(p_18305_, $$1, "MinecartFurnace");
        V99.m_18236_(p_18305_, $$1, "MinecartTNT");
        p_18305_.register((Map)$$1, "MinecartSpawner", () -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.f_16787_.in(p_18305_), (TypeTemplate)References.f_16789_.in(p_18305_)));
        p_18305_.register((Map)$$1, "MinecartHopper", p_18264_ -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.f_16787_.in(p_18305_), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18305_))));
        V99.m_18236_(p_18305_, $$1, "MinecartCommandBlock");
        V99.m_18193_(p_18305_, $$1, "ArmorStand");
        V99.m_18193_(p_18305_, $$1, "Creeper");
        V99.m_18193_(p_18305_, $$1, "Skeleton");
        V99.m_18193_(p_18305_, $$1, "Spider");
        V99.m_18193_(p_18305_, $$1, "Giant");
        V99.m_18193_(p_18305_, $$1, "Zombie");
        V99.m_18193_(p_18305_, $$1, "Slime");
        V99.m_18193_(p_18305_, $$1, "Ghast");
        V99.m_18193_(p_18305_, $$1, "PigZombie");
        p_18305_.register((Map)$$1, "Enderman", p_18259_ -> DSL.optionalFields((String)"carried", (TypeTemplate)References.f_16787_.in(p_18305_), (TypeTemplate)V99.m_18188_(p_18305_)));
        V99.m_18193_(p_18305_, $$1, "CaveSpider");
        V99.m_18193_(p_18305_, $$1, "Silverfish");
        V99.m_18193_(p_18305_, $$1, "Blaze");
        V99.m_18193_(p_18305_, $$1, "LavaSlime");
        V99.m_18193_(p_18305_, $$1, "EnderDragon");
        V99.m_18193_(p_18305_, $$1, "WitherBoss");
        V99.m_18193_(p_18305_, $$1, "Bat");
        V99.m_18193_(p_18305_, $$1, "Witch");
        V99.m_18193_(p_18305_, $$1, "Endermite");
        V99.m_18193_(p_18305_, $$1, "Guardian");
        V99.m_18193_(p_18305_, $$1, "Pig");
        V99.m_18193_(p_18305_, $$1, "Sheep");
        V99.m_18193_(p_18305_, $$1, "Cow");
        V99.m_18193_(p_18305_, $$1, "Chicken");
        V99.m_18193_(p_18305_, $$1, "Squid");
        V99.m_18193_(p_18305_, $$1, "Wolf");
        V99.m_18193_(p_18305_, $$1, "MushroomCow");
        V99.m_18193_(p_18305_, $$1, "SnowMan");
        V99.m_18193_(p_18305_, $$1, "Ozelot");
        V99.m_18193_(p_18305_, $$1, "VillagerGolem");
        p_18305_.register((Map)$$1, "EntityHorse", p_18254_ -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18305_)), (String)"ArmorItem", (TypeTemplate)References.f_16782_.in(p_18305_), (String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_18305_), (TypeTemplate)V99.m_18188_(p_18305_)));
        V99.m_18193_(p_18305_, $$1, "Rabbit");
        p_18305_.register((Map)$$1, "Villager", p_18245_ -> DSL.optionalFields((String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18305_)), (String)"Offers", (TypeTemplate)DSL.optionalFields((String)"Recipes", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"buy", (TypeTemplate)References.f_16782_.in(p_18305_), (String)"buyB", (TypeTemplate)References.f_16782_.in(p_18305_), (String)"sell", (TypeTemplate)References.f_16782_.in(p_18305_)))), (TypeTemplate)V99.m_18188_(p_18305_)));
        p_18305_.registerSimple((Map)$$1, "EnderCrystal");
        p_18305_.registerSimple((Map)$$1, "AreaEffectCloud");
        p_18305_.registerSimple((Map)$$1, "ShulkerBullet");
        V99.m_18193_(p_18305_, $$1, "Shulker");
        return $$1;
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_18303_) {
        HashMap $$1 = Maps.newHashMap();
        V99.m_18246_(p_18303_, $$1, "Furnace");
        V99.m_18246_(p_18303_, $$1, "Chest");
        p_18303_.registerSimple((Map)$$1, "EnderChest");
        p_18303_.register((Map)$$1, "RecordPlayer", p_18235_ -> DSL.optionalFields((String)"RecordItem", (TypeTemplate)References.f_16782_.in(p_18303_)));
        V99.m_18246_(p_18303_, $$1, "Trap");
        V99.m_18246_(p_18303_, $$1, "Dropper");
        p_18303_.registerSimple((Map)$$1, "Sign");
        p_18303_.register((Map)$$1, "MobSpawner", p_18223_ -> References.f_16789_.in(p_18303_));
        p_18303_.registerSimple((Map)$$1, "Music");
        p_18303_.registerSimple((Map)$$1, "Piston");
        V99.m_18246_(p_18303_, $$1, "Cauldron");
        p_18303_.registerSimple((Map)$$1, "EnchantTable");
        p_18303_.registerSimple((Map)$$1, "Airportal");
        p_18303_.registerSimple((Map)$$1, "Control");
        p_18303_.registerSimple((Map)$$1, "Beacon");
        p_18303_.registerSimple((Map)$$1, "Skull");
        p_18303_.registerSimple((Map)$$1, "DLDetector");
        V99.m_18246_(p_18303_, $$1, "Hopper");
        p_18303_.registerSimple((Map)$$1, "Comparator");
        p_18303_.register((Map)$$1, "FlowerPot", p_18192_ -> DSL.optionalFields((String)"Item", (TypeTemplate)DSL.or((TypeTemplate)DSL.constType((Type)DSL.intType()), (TypeTemplate)References.f_16788_.in(p_18303_))));
        p_18303_.registerSimple((Map)$$1, "Banner");
        p_18303_.registerSimple((Map)$$1, "Structure");
        p_18303_.registerSimple((Map)$$1, "EndGateway");
        return $$1;
    }

    public void registerTypes(Schema p_18307_, Map<String, Supplier<TypeTemplate>> p_18308_, Map<String, Supplier<TypeTemplate>> p_18309_) {
        p_18307_.registerType(false, References.f_16771_, DSL::remainder);
        p_18307_.registerType(false, References.f_16772_, () -> DSL.optionalFields((String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18307_)), (String)"EnderItems", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18307_))));
        p_18307_.registerType(false, References.f_16773_, () -> DSL.fields((String)"Level", (TypeTemplate)DSL.optionalFields((String)"Entities", (TypeTemplate)DSL.list((TypeTemplate)References.f_16785_.in(p_18307_)), (String)"TileEntities", (TypeTemplate)DSL.list((TypeTemplate)DSL.or((TypeTemplate)References.f_16781_.in(p_18307_), (TypeTemplate)DSL.remainder())), (String)"TileTicks", (TypeTemplate)DSL.list((TypeTemplate)DSL.fields((String)"i", (TypeTemplate)References.f_16787_.in(p_18307_))))));
        p_18307_.registerType(true, References.f_16781_, () -> DSL.taggedChoiceLazy((String)"id", (Type)DSL.string(), (Map)p_18309_));
        p_18307_.registerType(true, References.f_16785_, () -> DSL.optionalFields((String)"Riding", (TypeTemplate)References.f_16785_.in(p_18307_), (TypeTemplate)References.f_16786_.in(p_18307_)));
        p_18307_.registerType(false, References.f_16784_, () -> DSL.constType(NamespacedSchema.m_17310_()));
        p_18307_.registerType(true, References.f_16786_, () -> DSL.taggedChoiceLazy((String)"id", (Type)DSL.string(), (Map)p_18308_));
        p_18307_.registerType(true, References.f_16782_, () -> DSL.hook((TypeTemplate)DSL.optionalFields((String)"id", (TypeTemplate)DSL.or((TypeTemplate)DSL.constType((Type)DSL.intType()), (TypeTemplate)References.f_16788_.in(p_18307_)), (String)"tag", (TypeTemplate)DSL.optionalFields((String)"EntityTag", (TypeTemplate)References.f_16785_.in(p_18307_), (String)"BlockEntityTag", (TypeTemplate)References.f_16781_.in(p_18307_), (String)"CanDestroy", (TypeTemplate)DSL.list((TypeTemplate)References.f_16787_.in(p_18307_)), (String)"CanPlaceOn", (TypeTemplate)DSL.list((TypeTemplate)References.f_16787_.in(p_18307_)), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18307_)))), (Hook.HookFunction)f_18180_, (Hook.HookFunction)Hook.HookFunction.IDENTITY));
        p_18307_.registerType(false, References.f_16775_, DSL::remainder);
        p_18307_.registerType(false, References.f_16787_, () -> DSL.or((TypeTemplate)DSL.constType((Type)DSL.intType()), (TypeTemplate)DSL.constType(NamespacedSchema.m_17310_())));
        p_18307_.registerType(false, References.f_16788_, () -> DSL.constType(NamespacedSchema.m_17310_()));
        p_18307_.registerType(false, References.f_16777_, DSL::remainder);
        p_18307_.registerType(false, References.f_16778_, () -> DSL.optionalFields((String)"data", (TypeTemplate)DSL.optionalFields((String)"Features", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16790_.in(p_18307_)), (String)"Objectives", (TypeTemplate)DSL.list((TypeTemplate)References.f_16791_.in(p_18307_)), (String)"Teams", (TypeTemplate)DSL.list((TypeTemplate)References.f_16792_.in(p_18307_)))));
        p_18307_.registerType(false, References.f_16790_, DSL::remainder);
        p_18307_.registerType(false, References.f_16791_, DSL::remainder);
        p_18307_.registerType(false, References.f_16792_, DSL::remainder);
        p_18307_.registerType(true, References.f_16789_, DSL::remainder);
        p_18307_.registerType(false, References.f_16780_, DSL::remainder);
        p_18307_.registerType(true, References.f_16795_, DSL::remainder);
        p_18307_.registerType(false, References.f_145628_, () -> DSL.optionalFields((String)"Entities", (TypeTemplate)DSL.list((TypeTemplate)References.f_16785_.in(p_18307_))));
    }

    protected static <T> T m_18205_(Dynamic<T> p_18206_, Map<String, String> p_18207_, String p_18208_) {
        return (T)p_18206_.update("tag", p_145917_ -> p_145917_.update("BlockEntityTag", p_145912_ -> {
            String $$3 = p_18206_.get("id").asString().result().map(NamespacedSchema::m_17311_).orElse("minecraft:air");
            if (!"minecraft:air".equals($$3)) {
                String $$4 = (String)p_18207_.get($$3);
                if ($$4 == null) {
                    f_18181_.warn("Unable to resolve BlockEntity for ItemStack: {}", (Object)$$3);
                } else {
                    return p_145912_.set("id", p_18206_.createString($$4));
                }
            }
            return p_145912_;
        }).update("EntityTag", p_145908_ -> {
            String $$3 = p_18206_.get("id").asString("");
            if ("minecraft:armor_stand".equals(NamespacedSchema.m_17311_($$3))) {
                return p_145908_.set("id", p_18206_.createString(p_18208_));
            }
            return p_145908_;
        })).getValue();
    }
}

