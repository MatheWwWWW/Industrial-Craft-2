/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.item.Items
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.platform.registries;

import ic2.api.recipes.registries.IPotionBrewRegistry;
import ic2.core.entity.potion.RadiationEffect;
import ic2.core.entity.potion.SimpleEffect;
import ic2.core.platform.registries.IC2Items;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.GameData;

public class IC2Potions {
    public static MobEffect RADIATION;
    public static MobEffect SHAKY;

    public static <T extends MobEffect> T registerEffect(String name, T effect) {
        ForgeRegistries.MOB_EFFECTS.register(GameData.checkPrefix((String)name, (boolean)false), effect);
        return effect;
    }

    public static void init() {
        RADIATION = IC2Potions.registerEffect("radiation", new RadiationEffect());
        SHAKY = IC2Potions.registerEffect("shaky", new SimpleEffect(MobEffectCategory.NEUTRAL, 5578058));
    }

    public static void registerBrewing(IPotionBrewRegistry registry) {
        registry.registerPotionContainer(Items.f_42590_, new IPotionBrewRegistry.PotionContainer(Items.f_42589_, 250, 1.0f));
        registry.registerPotionContainer(IC2Items.EMPTY_SPLASH_POTION, new IPotionBrewRegistry.PotionContainer(Items.f_42736_, 250, 1.0f));
        registry.registerPotionContainer(IC2Items.EMPTY_LINGERING_POTION, new IPotionBrewRegistry.PotionContainer(Items.f_42739_, 250, 1.0f));
        registry.registerPotionContainer(Items.f_42412_, new IPotionBrewRegistry.PotionContainer(Items.f_42738_, 25, 0.125f));
        registry.registerName(MobEffects.f_19611_, "night_vision");
        registry.registerName(MobEffects.f_19607_, "fire_resistance");
        registry.registerName(MobEffects.f_19603_, "leaping");
        registry.registerName(MobEffects.f_19613_, "weakness");
        registry.registerName(MobEffects.f_19596_, "swiftness");
        registry.registerName(MobEffects.f_19608_, "water_breathing");
        registry.registerName(MobEffects.f_19601_, "healing");
        registry.registerName(MobEffects.f_19614_, "poison");
        registry.registerName(MobEffects.f_19600_, "strength");
        registry.registerName(MobEffects.f_19591_, "slow_falling");
        registry.registerName(MobEffects.f_19609_, "invisibility");
        registry.registerName(MobEffects.f_19597_, "slowness");
        registry.registerName(MobEffects.f_19602_, "harming");
        registry.registerBrew(Items.f_42677_, MobEffects.f_19611_);
        registry.registerBrew(Items.f_42542_, MobEffects.f_19607_);
        registry.registerBrew(Items.f_42648_, MobEffects.f_19603_);
        registry.registerBrew(Items.f_42592_, MobEffects.f_19613_);
        registry.registerBrew(Items.f_42501_, MobEffects.f_19596_);
        registry.registerBrew(Items.f_42529_, MobEffects.f_19608_);
        registry.registerBrew(Items.f_42546_, MobEffects.f_19601_);
        registry.registerBrew(Items.f_42591_, MobEffects.f_19614_);
        registry.registerBrew(Items.f_42593_, MobEffects.f_19600_);
        registry.registerBrew(Items.f_42714_, MobEffects.f_19591_);
        registry.registerBrew(Items.f_42592_, MobEffects.f_19611_, MobEffects.f_19609_);
        registry.registerBrew(Items.f_42592_, MobEffects.f_19603_, MobEffects.f_19597_);
        registry.registerBrew(Items.f_42592_, MobEffects.f_19596_, MobEffects.f_19597_);
        registry.registerBrew(Items.f_42592_, MobEffects.f_19601_, MobEffects.f_19602_);
        registry.registerBrew(Items.f_42592_, MobEffects.f_19614_, MobEffects.f_19602_);
    }
}

