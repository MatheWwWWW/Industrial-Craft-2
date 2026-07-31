/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Set;
import net.minecraft.util.datafix.fixes.References;

public class WallPropertyFix
extends DataFix {
    private static final Set<String> f_17151_ = ImmutableSet.of((Object)"minecraft:andesite_wall", (Object)"minecraft:brick_wall", (Object)"minecraft:cobblestone_wall", (Object)"minecraft:diorite_wall", (Object)"minecraft:end_stone_brick_wall", (Object)"minecraft:granite_wall", (Object[])new String[]{"minecraft:mossy_cobblestone_wall", "minecraft:mossy_stone_brick_wall", "minecraft:nether_brick_wall", "minecraft:prismarine_wall", "minecraft:red_nether_brick_wall", "minecraft:red_sandstone_wall", "minecraft:sandstone_wall", "minecraft:stone_brick_wall"});

    public WallPropertyFix(Schema p_17154_, boolean p_17155_) {
        super(p_17154_, p_17155_);
    }

    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("WallPropertyFix", this.getInputSchema().getType(References.f_16783_), p_17157_ -> p_17157_.update(DSL.remainderFinder(), WallPropertyFix::m_17158_));
    }

    private static String m_17163_(String p_17164_) {
        return "true".equals(p_17164_) ? "low" : "none";
    }

    private static <T> Dynamic<T> m_17160_(Dynamic<T> p_17161_, String p_17162_) {
        return p_17161_.update(p_17162_, p_17168_ -> (Dynamic)DataFixUtils.orElse(p_17168_.asString().result().map(WallPropertyFix::m_17163_).map(arg_0 -> ((Dynamic)p_17168_).createString(arg_0)), (Object)p_17168_));
    }

    private static <T> Dynamic<T> m_17158_(Dynamic<T> p_17159_) {
        boolean $$1 = p_17159_.get("Name").asString().result().filter(f_17151_::contains).isPresent();
        if (!$$1) {
            return p_17159_;
        }
        return p_17159_.update("Properties", p_17166_ -> {
            Dynamic $$1 = WallPropertyFix.m_17160_(p_17166_, "east");
            $$1 = WallPropertyFix.m_17160_($$1, "west");
            $$1 = WallPropertyFix.m_17160_($$1, "north");
            return WallPropertyFix.m_17160_($$1, "south");
        });
    }
}

