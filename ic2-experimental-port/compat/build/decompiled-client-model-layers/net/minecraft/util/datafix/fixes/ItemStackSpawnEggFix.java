/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class ItemStackSpawnEggFix
extends DataFix {
    private static final Map<String, String> f_16095_ = (Map)DataFixUtils.make((Object)Maps.newHashMap(), p_16107_ -> {
        p_16107_.put("minecraft:bat", "minecraft:bat_spawn_egg");
        p_16107_.put("minecraft:blaze", "minecraft:blaze_spawn_egg");
        p_16107_.put("minecraft:cave_spider", "minecraft:cave_spider_spawn_egg");
        p_16107_.put("minecraft:chicken", "minecraft:chicken_spawn_egg");
        p_16107_.put("minecraft:cow", "minecraft:cow_spawn_egg");
        p_16107_.put("minecraft:creeper", "minecraft:creeper_spawn_egg");
        p_16107_.put("minecraft:donkey", "minecraft:donkey_spawn_egg");
        p_16107_.put("minecraft:elder_guardian", "minecraft:elder_guardian_spawn_egg");
        p_16107_.put("minecraft:enderman", "minecraft:enderman_spawn_egg");
        p_16107_.put("minecraft:endermite", "minecraft:endermite_spawn_egg");
        p_16107_.put("minecraft:evocation_illager", "minecraft:evocation_illager_spawn_egg");
        p_16107_.put("minecraft:ghast", "minecraft:ghast_spawn_egg");
        p_16107_.put("minecraft:guardian", "minecraft:guardian_spawn_egg");
        p_16107_.put("minecraft:horse", "minecraft:horse_spawn_egg");
        p_16107_.put("minecraft:husk", "minecraft:husk_spawn_egg");
        p_16107_.put("minecraft:llama", "minecraft:llama_spawn_egg");
        p_16107_.put("minecraft:magma_cube", "minecraft:magma_cube_spawn_egg");
        p_16107_.put("minecraft:mooshroom", "minecraft:mooshroom_spawn_egg");
        p_16107_.put("minecraft:mule", "minecraft:mule_spawn_egg");
        p_16107_.put("minecraft:ocelot", "minecraft:ocelot_spawn_egg");
        p_16107_.put("minecraft:pufferfish", "minecraft:pufferfish_spawn_egg");
        p_16107_.put("minecraft:parrot", "minecraft:parrot_spawn_egg");
        p_16107_.put("minecraft:pig", "minecraft:pig_spawn_egg");
        p_16107_.put("minecraft:polar_bear", "minecraft:polar_bear_spawn_egg");
        p_16107_.put("minecraft:rabbit", "minecraft:rabbit_spawn_egg");
        p_16107_.put("minecraft:sheep", "minecraft:sheep_spawn_egg");
        p_16107_.put("minecraft:shulker", "minecraft:shulker_spawn_egg");
        p_16107_.put("minecraft:silverfish", "minecraft:silverfish_spawn_egg");
        p_16107_.put("minecraft:skeleton", "minecraft:skeleton_spawn_egg");
        p_16107_.put("minecraft:skeleton_horse", "minecraft:skeleton_horse_spawn_egg");
        p_16107_.put("minecraft:slime", "minecraft:slime_spawn_egg");
        p_16107_.put("minecraft:spider", "minecraft:spider_spawn_egg");
        p_16107_.put("minecraft:squid", "minecraft:squid_spawn_egg");
        p_16107_.put("minecraft:stray", "minecraft:stray_spawn_egg");
        p_16107_.put("minecraft:turtle", "minecraft:turtle_spawn_egg");
        p_16107_.put("minecraft:vex", "minecraft:vex_spawn_egg");
        p_16107_.put("minecraft:villager", "minecraft:villager_spawn_egg");
        p_16107_.put("minecraft:vindication_illager", "minecraft:vindication_illager_spawn_egg");
        p_16107_.put("minecraft:witch", "minecraft:witch_spawn_egg");
        p_16107_.put("minecraft:wither_skeleton", "minecraft:wither_skeleton_spawn_egg");
        p_16107_.put("minecraft:wolf", "minecraft:wolf_spawn_egg");
        p_16107_.put("minecraft:zombie", "minecraft:zombie_spawn_egg");
        p_16107_.put("minecraft:zombie_horse", "minecraft:zombie_horse_spawn_egg");
        p_16107_.put("minecraft:zombie_pigman", "minecraft:zombie_pigman_spawn_egg");
        p_16107_.put("minecraft:zombie_villager", "minecraft:zombie_villager_spawn_egg");
    });

    public ItemStackSpawnEggFix(Schema p_16098_, boolean p_16099_) {
        super(p_16098_, p_16099_);
    }

    public TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16782_);
        OpticFinder $$1 = DSL.fieldFinder((String)"id", (Type)DSL.named((String)References.f_16788_.typeName(), NamespacedSchema.m_17310_()));
        OpticFinder $$2 = DSL.fieldFinder((String)"id", NamespacedSchema.m_17310_());
        OpticFinder $$3 = $$0.findField("tag");
        OpticFinder $$4 = $$3.type().findField("EntityTag");
        return this.fixTypeEverywhereTyped("ItemInstanceSpawnEggFix", $$0, p_16105_ -> {
            Typed $$6;
            Typed $$7;
            Optional $$8;
            Optional $$5 = p_16105_.getOptional($$1);
            if ($$5.isPresent() && Objects.equals(((Pair)$$5.get()).getSecond(), "minecraft:spawn_egg") && ($$8 = ($$7 = ($$6 = p_16105_.getOrCreateTyped($$3)).getOrCreateTyped($$4)).getOptional($$2)).isPresent()) {
                return p_16105_.set($$1, (Object)Pair.of((Object)References.f_16788_.typeName(), (Object)f_16095_.getOrDefault($$8.get(), "minecraft:pig_spawn_egg")));
            }
            return p_16105_;
        });
    }
}

