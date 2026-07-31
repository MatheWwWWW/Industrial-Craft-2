/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.TaggedChoice$TaggedChoiceType
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import java.util.Map;
import net.minecraft.util.datafix.fixes.References;

public class BlockEntityIdFix
extends DataFix {
    private static final Map<String, String> f_14827_ = (Map)DataFixUtils.make((Object)Maps.newHashMap(), p_14839_ -> {
        p_14839_.put("Airportal", "minecraft:end_portal");
        p_14839_.put("Banner", "minecraft:banner");
        p_14839_.put("Beacon", "minecraft:beacon");
        p_14839_.put("Cauldron", "minecraft:brewing_stand");
        p_14839_.put("Chest", "minecraft:chest");
        p_14839_.put("Comparator", "minecraft:comparator");
        p_14839_.put("Control", "minecraft:command_block");
        p_14839_.put("DLDetector", "minecraft:daylight_detector");
        p_14839_.put("Dropper", "minecraft:dropper");
        p_14839_.put("EnchantTable", "minecraft:enchanting_table");
        p_14839_.put("EndGateway", "minecraft:end_gateway");
        p_14839_.put("EnderChest", "minecraft:ender_chest");
        p_14839_.put("FlowerPot", "minecraft:flower_pot");
        p_14839_.put("Furnace", "minecraft:furnace");
        p_14839_.put("Hopper", "minecraft:hopper");
        p_14839_.put("MobSpawner", "minecraft:mob_spawner");
        p_14839_.put("Music", "minecraft:noteblock");
        p_14839_.put("Piston", "minecraft:piston");
        p_14839_.put("RecordPlayer", "minecraft:jukebox");
        p_14839_.put("Sign", "minecraft:sign");
        p_14839_.put("Skull", "minecraft:skull");
        p_14839_.put("Structure", "minecraft:structure_block");
        p_14839_.put("Trap", "minecraft:dispenser");
    });

    public BlockEntityIdFix(Schema p_14830_, boolean p_14831_) {
        super(p_14830_, p_14831_);
    }

    public TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16782_);
        Type $$1 = this.getOutputSchema().getType(References.f_16782_);
        TaggedChoice.TaggedChoiceType $$2 = this.getInputSchema().findChoiceType(References.f_16781_);
        TaggedChoice.TaggedChoiceType $$3 = this.getOutputSchema().findChoiceType(References.f_16781_);
        return TypeRewriteRule.seq((TypeRewriteRule)this.convertUnchecked("item stack block entity name hook converter", $$0, $$1), (TypeRewriteRule)this.fixTypeEverywhere("BlockEntityIdFix", (Type)$$2, (Type)$$3, p_14835_ -> p_145135_ -> p_145135_.mapFirst(p_145137_ -> f_14827_.getOrDefault(p_145137_, (String)p_145137_))));
    }
}

