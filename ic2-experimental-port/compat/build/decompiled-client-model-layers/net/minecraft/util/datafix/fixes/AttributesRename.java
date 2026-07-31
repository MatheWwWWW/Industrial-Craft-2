/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import net.minecraft.util.datafix.fixes.References;

public class AttributesRename
extends DataFix {
    private static final Map<String, String> f_14668_ = ImmutableMap.builder().put((Object)"generic.maxHealth", (Object)"generic.max_health").put((Object)"Max Health", (Object)"generic.max_health").put((Object)"zombie.spawnReinforcements", (Object)"zombie.spawn_reinforcements").put((Object)"Spawn Reinforcements Chance", (Object)"zombie.spawn_reinforcements").put((Object)"horse.jumpStrength", (Object)"horse.jump_strength").put((Object)"Jump Strength", (Object)"horse.jump_strength").put((Object)"generic.followRange", (Object)"generic.follow_range").put((Object)"Follow Range", (Object)"generic.follow_range").put((Object)"generic.knockbackResistance", (Object)"generic.knockback_resistance").put((Object)"Knockback Resistance", (Object)"generic.knockback_resistance").put((Object)"generic.movementSpeed", (Object)"generic.movement_speed").put((Object)"Movement Speed", (Object)"generic.movement_speed").put((Object)"generic.flyingSpeed", (Object)"generic.flying_speed").put((Object)"Flying Speed", (Object)"generic.flying_speed").put((Object)"generic.attackDamage", (Object)"generic.attack_damage").put((Object)"generic.attackKnockback", (Object)"generic.attack_knockback").put((Object)"generic.attackSpeed", (Object)"generic.attack_speed").put((Object)"generic.armorToughness", (Object)"generic.armor_toughness").build();

    public AttributesRename(Schema p_14671_) {
        super(p_14671_, false);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16782_);
        OpticFinder $$1 = $$0.findField("tag");
        return TypeRewriteRule.seq((TypeRewriteRule)this.fixTypeEverywhereTyped("Rename ItemStack Attributes", $$0, p_14674_ -> p_14674_.updateTyped($$1, AttributesRename::m_14675_)), (TypeRewriteRule[])new TypeRewriteRule[]{this.fixTypeEverywhereTyped("Rename Entity Attributes", this.getInputSchema().getType(References.f_16786_), AttributesRename::m_14683_), this.fixTypeEverywhereTyped("Rename Player Attributes", this.getInputSchema().getType(References.f_16772_), AttributesRename::m_14683_)});
    }

    private static Dynamic<?> m_14677_(Dynamic<?> p_14678_) {
        return (Dynamic)DataFixUtils.orElse(p_14678_.asString().result().map(p_14680_ -> f_14668_.getOrDefault(p_14680_, (String)p_14680_)).map(arg_0 -> p_14678_.createString(arg_0)), p_14678_);
    }

    private static Typed<?> m_14675_(Typed<?> p_14676_) {
        return p_14676_.update(DSL.remainderFinder(), p_14694_ -> p_14694_.update("AttributeModifiers", p_145080_ -> (Dynamic)DataFixUtils.orElse(p_145080_.asStreamOpt().result().map(p_145074_ -> p_145074_.map(p_145082_ -> p_145082_.update("AttributeName", AttributesRename::m_14677_))).map(arg_0 -> ((Dynamic)p_145080_).createList(arg_0)), (Object)p_145080_)));
    }

    private static Typed<?> m_14683_(Typed<?> p_14684_) {
        return p_14684_.update(DSL.remainderFinder(), p_14686_ -> p_14686_.update("Attributes", p_145076_ -> (Dynamic)DataFixUtils.orElse(p_145076_.asStreamOpt().result().map(p_145072_ -> p_145072_.map(p_145078_ -> p_145078_.update("Name", AttributesRename::m_14677_))).map(arg_0 -> ((Dynamic)p_145076_).createList(arg_0)), (Object)p_145076_)));
    }
}

