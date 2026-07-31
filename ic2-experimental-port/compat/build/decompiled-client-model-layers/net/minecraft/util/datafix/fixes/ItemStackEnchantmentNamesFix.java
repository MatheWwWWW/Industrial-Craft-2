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
 *  com.mojang.serialization.Dynamic
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.References;

public class ItemStackEnchantmentNamesFix
extends DataFix {
    private static final Int2ObjectMap<String> f_16062_ = (Int2ObjectMap)DataFixUtils.make((Object)new Int2ObjectOpenHashMap(), p_16075_ -> {
        p_16075_.put(0, (Object)"minecraft:protection");
        p_16075_.put(1, (Object)"minecraft:fire_protection");
        p_16075_.put(2, (Object)"minecraft:feather_falling");
        p_16075_.put(3, (Object)"minecraft:blast_protection");
        p_16075_.put(4, (Object)"minecraft:projectile_protection");
        p_16075_.put(5, (Object)"minecraft:respiration");
        p_16075_.put(6, (Object)"minecraft:aqua_affinity");
        p_16075_.put(7, (Object)"minecraft:thorns");
        p_16075_.put(8, (Object)"minecraft:depth_strider");
        p_16075_.put(9, (Object)"minecraft:frost_walker");
        p_16075_.put(10, (Object)"minecraft:binding_curse");
        p_16075_.put(16, (Object)"minecraft:sharpness");
        p_16075_.put(17, (Object)"minecraft:smite");
        p_16075_.put(18, (Object)"minecraft:bane_of_arthropods");
        p_16075_.put(19, (Object)"minecraft:knockback");
        p_16075_.put(20, (Object)"minecraft:fire_aspect");
        p_16075_.put(21, (Object)"minecraft:looting");
        p_16075_.put(22, (Object)"minecraft:sweeping");
        p_16075_.put(32, (Object)"minecraft:efficiency");
        p_16075_.put(33, (Object)"minecraft:silk_touch");
        p_16075_.put(34, (Object)"minecraft:unbreaking");
        p_16075_.put(35, (Object)"minecraft:fortune");
        p_16075_.put(48, (Object)"minecraft:power");
        p_16075_.put(49, (Object)"minecraft:punch");
        p_16075_.put(50, (Object)"minecraft:flame");
        p_16075_.put(51, (Object)"minecraft:infinity");
        p_16075_.put(61, (Object)"minecraft:luck_of_the_sea");
        p_16075_.put(62, (Object)"minecraft:lure");
        p_16075_.put(65, (Object)"minecraft:loyalty");
        p_16075_.put(66, (Object)"minecraft:impaling");
        p_16075_.put(67, (Object)"minecraft:riptide");
        p_16075_.put(68, (Object)"minecraft:channeling");
        p_16075_.put(70, (Object)"minecraft:mending");
        p_16075_.put(71, (Object)"minecraft:vanishing_curse");
    });

    public ItemStackEnchantmentNamesFix(Schema p_16065_, boolean p_16066_) {
        super(p_16065_, p_16066_);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16782_);
        OpticFinder $$1 = $$0.findField("tag");
        return this.fixTypeEverywhereTyped("ItemStackEnchantmentFix", $$0, p_16069_ -> p_16069_.updateTyped($$1, p_145419_ -> p_145419_.update(DSL.remainderFinder(), this::m_16072_)));
    }

    private Dynamic<?> m_16072_(Dynamic<?> p_16073_) {
        Optional $$1 = p_16073_.get("ench").asStreamOpt().map(p_16081_ -> p_16081_.map(p_145425_ -> p_145425_.set("id", p_145425_.createString((String)f_16062_.getOrDefault(p_145425_.get("id").asInt(0), (Object)"null"))))).map(arg_0 -> p_16073_.createList(arg_0)).result();
        if ($$1.isPresent()) {
            p_16073_ = p_16073_.remove("ench").set("Enchantments", (Dynamic)$$1.get());
        }
        return p_16073_.update("StoredEnchantments", p_16079_ -> (Dynamic)DataFixUtils.orElse((Optional)p_16079_.asStreamOpt().map(p_145421_ -> p_145421_.map(p_145423_ -> p_145423_.set("id", p_145423_.createString((String)f_16062_.getOrDefault(p_145423_.get("id").asInt(0), (Object)"null"))))).map(arg_0 -> ((Dynamic)p_16079_).createList(arg_0)).result(), (Object)p_16079_));
    }
}

