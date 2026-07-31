/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.attributes;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class Attributes {
    public static final Attribute f_22276_ = Attributes.m_22290_("generic.max_health", new RangedAttribute("attribute.name.generic.max_health", 20.0, 1.0, 1024.0).m_22084_(true));
    public static final Attribute f_22277_ = Attributes.m_22290_("generic.follow_range", new RangedAttribute("attribute.name.generic.follow_range", 32.0, 0.0, 2048.0));
    public static final Attribute f_22278_ = Attributes.m_22290_("generic.knockback_resistance", new RangedAttribute("attribute.name.generic.knockback_resistance", 0.0, 0.0, 1.0));
    public static final Attribute f_22279_ = Attributes.m_22290_("generic.movement_speed", new RangedAttribute("attribute.name.generic.movement_speed", 0.7f, 0.0, 1024.0).m_22084_(true));
    public static final Attribute f_22280_ = Attributes.m_22290_("generic.flying_speed", new RangedAttribute("attribute.name.generic.flying_speed", 0.4f, 0.0, 1024.0).m_22084_(true));
    public static final Attribute f_22281_ = Attributes.m_22290_("generic.attack_damage", new RangedAttribute("attribute.name.generic.attack_damage", 2.0, 0.0, 2048.0));
    public static final Attribute f_22282_ = Attributes.m_22290_("generic.attack_knockback", new RangedAttribute("attribute.name.generic.attack_knockback", 0.0, 0.0, 5.0));
    public static final Attribute f_22283_ = Attributes.m_22290_("generic.attack_speed", new RangedAttribute("attribute.name.generic.attack_speed", 4.0, 0.0, 1024.0).m_22084_(true));
    public static final Attribute f_22284_ = Attributes.m_22290_("generic.armor", new RangedAttribute("attribute.name.generic.armor", 0.0, 0.0, 30.0).m_22084_(true));
    public static final Attribute f_22285_ = Attributes.m_22290_("generic.armor_toughness", new RangedAttribute("attribute.name.generic.armor_toughness", 0.0, 0.0, 20.0).m_22084_(true));
    public static final Attribute f_22286_ = Attributes.m_22290_("generic.luck", new RangedAttribute("attribute.name.generic.luck", 0.0, -1024.0, 1024.0).m_22084_(true));
    public static final Attribute f_22287_ = Attributes.m_22290_("zombie.spawn_reinforcements", new RangedAttribute("attribute.name.zombie.spawn_reinforcements", 0.0, 0.0, 1.0));
    public static final Attribute f_22288_ = Attributes.m_22290_("horse.jump_strength", new RangedAttribute("attribute.name.horse.jump_strength", 0.7, 0.0, 2.0).m_22084_(true));

    private static Attribute m_22290_(String p_22291_, Attribute p_22292_) {
        return Registry.m_122961_(Registry.f_122866_, p_22291_, p_22292_);
    }
}

