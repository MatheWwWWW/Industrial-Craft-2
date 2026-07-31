/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.templates.TypeTemplate
 */
package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V100
extends Schema {
    public V100(int p_17328_, Schema p_17329_) {
        super(p_17328_, p_17329_);
    }

    protected static TypeTemplate m_17330_(Schema p_17331_) {
        return DSL.optionalFields((String)"ArmorItems", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17331_)), (String)"HandItems", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17331_)));
    }

    protected static void m_17335_(Schema p_17336_, Map<String, Supplier<TypeTemplate>> p_17337_, String p_17338_) {
        p_17336_.register(p_17337_, p_17338_, () -> V100.m_17330_(p_17336_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17350_) {
        Map $$1 = super.registerEntities(p_17350_);
        V100.m_17335_(p_17350_, $$1, "ArmorStand");
        V100.m_17335_(p_17350_, $$1, "Creeper");
        V100.m_17335_(p_17350_, $$1, "Skeleton");
        V100.m_17335_(p_17350_, $$1, "Spider");
        V100.m_17335_(p_17350_, $$1, "Giant");
        V100.m_17335_(p_17350_, $$1, "Zombie");
        V100.m_17335_(p_17350_, $$1, "Slime");
        V100.m_17335_(p_17350_, $$1, "Ghast");
        V100.m_17335_(p_17350_, $$1, "PigZombie");
        p_17350_.register($$1, "Enderman", p_17348_ -> DSL.optionalFields((String)"carried", (TypeTemplate)References.f_16787_.in(p_17350_), (TypeTemplate)V100.m_17330_(p_17350_)));
        V100.m_17335_(p_17350_, $$1, "CaveSpider");
        V100.m_17335_(p_17350_, $$1, "Silverfish");
        V100.m_17335_(p_17350_, $$1, "Blaze");
        V100.m_17335_(p_17350_, $$1, "LavaSlime");
        V100.m_17335_(p_17350_, $$1, "EnderDragon");
        V100.m_17335_(p_17350_, $$1, "WitherBoss");
        V100.m_17335_(p_17350_, $$1, "Bat");
        V100.m_17335_(p_17350_, $$1, "Witch");
        V100.m_17335_(p_17350_, $$1, "Endermite");
        V100.m_17335_(p_17350_, $$1, "Guardian");
        V100.m_17335_(p_17350_, $$1, "Pig");
        V100.m_17335_(p_17350_, $$1, "Sheep");
        V100.m_17335_(p_17350_, $$1, "Cow");
        V100.m_17335_(p_17350_, $$1, "Chicken");
        V100.m_17335_(p_17350_, $$1, "Squid");
        V100.m_17335_(p_17350_, $$1, "Wolf");
        V100.m_17335_(p_17350_, $$1, "MushroomCow");
        V100.m_17335_(p_17350_, $$1, "SnowMan");
        V100.m_17335_(p_17350_, $$1, "Ozelot");
        V100.m_17335_(p_17350_, $$1, "VillagerGolem");
        p_17350_.register($$1, "EntityHorse", p_17343_ -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17350_)), (String)"ArmorItem", (TypeTemplate)References.f_16782_.in(p_17350_), (String)"SaddleItem", (TypeTemplate)References.f_16782_.in(p_17350_), (TypeTemplate)V100.m_17330_(p_17350_)));
        V100.m_17335_(p_17350_, $$1, "Rabbit");
        p_17350_.register($$1, "Villager", p_17334_ -> DSL.optionalFields((String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17350_)), (String)"Offers", (TypeTemplate)DSL.optionalFields((String)"Recipes", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"buy", (TypeTemplate)References.f_16782_.in(p_17350_), (String)"buyB", (TypeTemplate)References.f_16782_.in(p_17350_), (String)"sell", (TypeTemplate)References.f_16782_.in(p_17350_)))), (TypeTemplate)V100.m_17330_(p_17350_)));
        V100.m_17335_(p_17350_, $$1, "Shulker");
        p_17350_.registerSimple($$1, "AreaEffectCloud");
        p_17350_.registerSimple($$1, "ShulkerBullet");
        return $$1;
    }

    public void registerTypes(Schema p_17352_, Map<String, Supplier<TypeTemplate>> p_17353_, Map<String, Supplier<TypeTemplate>> p_17354_) {
        super.registerTypes(p_17352_, p_17353_, p_17354_);
        p_17352_.registerType(false, References.f_16776_, () -> DSL.optionalFields((String)"entities", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"nbt", (TypeTemplate)References.f_16785_.in(p_17352_))), (String)"blocks", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"nbt", (TypeTemplate)References.f_16781_.in(p_17352_))), (String)"palette", (TypeTemplate)DSL.list((TypeTemplate)References.f_16783_.in(p_17352_))));
        p_17352_.registerType(false, References.f_16783_, DSL::remainder);
    }
}

