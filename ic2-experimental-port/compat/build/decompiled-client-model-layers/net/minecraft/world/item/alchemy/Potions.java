/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.alchemy;

import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

public class Potions {
    public static final Potion f_43598_ = Potions.m_43625_("empty", new Potion(new MobEffectInstance[0]));
    public static final Potion f_43599_ = Potions.m_43625_("water", new Potion(new MobEffectInstance[0]));
    public static final Potion f_43600_ = Potions.m_43625_("mundane", new Potion(new MobEffectInstance[0]));
    public static final Potion f_43601_ = Potions.m_43625_("thick", new Potion(new MobEffectInstance[0]));
    public static final Potion f_43602_ = Potions.m_43625_("awkward", new Potion(new MobEffectInstance[0]));
    public static final Potion f_43603_ = Potions.m_43625_("night_vision", new Potion(new MobEffectInstance(MobEffects.f_19611_, 3600)));
    public static final Potion f_43604_ = Potions.m_43625_("long_night_vision", new Potion("night_vision", new MobEffectInstance(MobEffects.f_19611_, 9600)));
    public static final Potion f_43605_ = Potions.m_43625_("invisibility", new Potion(new MobEffectInstance(MobEffects.f_19609_, 3600)));
    public static final Potion f_43606_ = Potions.m_43625_("long_invisibility", new Potion("invisibility", new MobEffectInstance(MobEffects.f_19609_, 9600)));
    public static final Potion f_43607_ = Potions.m_43625_("leaping", new Potion(new MobEffectInstance(MobEffects.f_19603_, 3600)));
    public static final Potion f_43608_ = Potions.m_43625_("long_leaping", new Potion("leaping", new MobEffectInstance(MobEffects.f_19603_, 9600)));
    public static final Potion f_43609_ = Potions.m_43625_("strong_leaping", new Potion("leaping", new MobEffectInstance(MobEffects.f_19603_, 1800, 1)));
    public static final Potion f_43610_ = Potions.m_43625_("fire_resistance", new Potion(new MobEffectInstance(MobEffects.f_19607_, 3600)));
    public static final Potion f_43611_ = Potions.m_43625_("long_fire_resistance", new Potion("fire_resistance", new MobEffectInstance(MobEffects.f_19607_, 9600)));
    public static final Potion f_43612_ = Potions.m_43625_("swiftness", new Potion(new MobEffectInstance(MobEffects.f_19596_, 3600)));
    public static final Potion f_43613_ = Potions.m_43625_("long_swiftness", new Potion("swiftness", new MobEffectInstance(MobEffects.f_19596_, 9600)));
    public static final Potion f_43614_ = Potions.m_43625_("strong_swiftness", new Potion("swiftness", new MobEffectInstance(MobEffects.f_19596_, 1800, 1)));
    public static final Potion f_43615_ = Potions.m_43625_("slowness", new Potion(new MobEffectInstance(MobEffects.f_19597_, 1800)));
    public static final Potion f_43616_ = Potions.m_43625_("long_slowness", new Potion("slowness", new MobEffectInstance(MobEffects.f_19597_, 4800)));
    public static final Potion f_43617_ = Potions.m_43625_("strong_slowness", new Potion("slowness", new MobEffectInstance(MobEffects.f_19597_, 400, 3)));
    public static final Potion f_43618_ = Potions.m_43625_("turtle_master", new Potion("turtle_master", new MobEffectInstance(MobEffects.f_19597_, 400, 3), new MobEffectInstance(MobEffects.f_19606_, 400, 2)));
    public static final Potion f_43619_ = Potions.m_43625_("long_turtle_master", new Potion("turtle_master", new MobEffectInstance(MobEffects.f_19597_, 800, 3), new MobEffectInstance(MobEffects.f_19606_, 800, 2)));
    public static final Potion f_43620_ = Potions.m_43625_("strong_turtle_master", new Potion("turtle_master", new MobEffectInstance(MobEffects.f_19597_, 400, 5), new MobEffectInstance(MobEffects.f_19606_, 400, 3)));
    public static final Potion f_43621_ = Potions.m_43625_("water_breathing", new Potion(new MobEffectInstance(MobEffects.f_19608_, 3600)));
    public static final Potion f_43622_ = Potions.m_43625_("long_water_breathing", new Potion("water_breathing", new MobEffectInstance(MobEffects.f_19608_, 9600)));
    public static final Potion f_43623_ = Potions.m_43625_("healing", new Potion(new MobEffectInstance(MobEffects.f_19601_, 1)));
    public static final Potion f_43581_ = Potions.m_43625_("strong_healing", new Potion("healing", new MobEffectInstance(MobEffects.f_19601_, 1, 1)));
    public static final Potion f_43582_ = Potions.m_43625_("harming", new Potion(new MobEffectInstance(MobEffects.f_19602_, 1)));
    public static final Potion f_43583_ = Potions.m_43625_("strong_harming", new Potion("harming", new MobEffectInstance(MobEffects.f_19602_, 1, 1)));
    public static final Potion f_43584_ = Potions.m_43625_("poison", new Potion(new MobEffectInstance(MobEffects.f_19614_, 900)));
    public static final Potion f_43585_ = Potions.m_43625_("long_poison", new Potion("poison", new MobEffectInstance(MobEffects.f_19614_, 1800)));
    public static final Potion f_43586_ = Potions.m_43625_("strong_poison", new Potion("poison", new MobEffectInstance(MobEffects.f_19614_, 432, 1)));
    public static final Potion f_43587_ = Potions.m_43625_("regeneration", new Potion(new MobEffectInstance(MobEffects.f_19605_, 900)));
    public static final Potion f_43588_ = Potions.m_43625_("long_regeneration", new Potion("regeneration", new MobEffectInstance(MobEffects.f_19605_, 1800)));
    public static final Potion f_43589_ = Potions.m_43625_("strong_regeneration", new Potion("regeneration", new MobEffectInstance(MobEffects.f_19605_, 450, 1)));
    public static final Potion f_43590_ = Potions.m_43625_("strength", new Potion(new MobEffectInstance(MobEffects.f_19600_, 3600)));
    public static final Potion f_43591_ = Potions.m_43625_("long_strength", new Potion("strength", new MobEffectInstance(MobEffects.f_19600_, 9600)));
    public static final Potion f_43592_ = Potions.m_43625_("strong_strength", new Potion("strength", new MobEffectInstance(MobEffects.f_19600_, 1800, 1)));
    public static final Potion f_43593_ = Potions.m_43625_("weakness", new Potion(new MobEffectInstance(MobEffects.f_19613_, 1800)));
    public static final Potion f_43594_ = Potions.m_43625_("long_weakness", new Potion("weakness", new MobEffectInstance(MobEffects.f_19613_, 4800)));
    public static final Potion f_43595_ = Potions.m_43625_("luck", new Potion("luck", new MobEffectInstance(MobEffects.f_19621_, 6000)));
    public static final Potion f_43596_ = Potions.m_43625_("slow_falling", new Potion(new MobEffectInstance(MobEffects.f_19591_, 1800)));
    public static final Potion f_43597_ = Potions.m_43625_("long_slow_falling", new Potion("slow_falling", new MobEffectInstance(MobEffects.f_19591_, 4800)));

    private static Potion m_43625_(String p_43626_, Potion p_43627_) {
        return Registry.m_122961_(Registry.f_122828_, p_43626_, p_43627_);
    }
}

