/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.core.particles;

import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SculkChargeParticleOptions;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.VibrationParticleOption;

public class ParticleTypes {
    public static final SimpleParticleType f_123770_ = ParticleTypes.m_123824_("ambient_entity_effect", false);
    public static final SimpleParticleType f_123792_ = ParticleTypes.m_123824_("angry_villager", false);
    public static final ParticleType<BlockParticleOption> f_123794_ = ParticleTypes.m_235905_("block", false, BlockParticleOption.f_123624_, BlockParticleOption::m_123634_);
    public static final ParticleType<BlockParticleOption> f_194652_ = ParticleTypes.m_235905_("block_marker", false, BlockParticleOption.f_123624_, BlockParticleOption::m_123634_);
    public static final SimpleParticleType f_123795_ = ParticleTypes.m_123824_("bubble", false);
    public static final SimpleParticleType f_123796_ = ParticleTypes.m_123824_("cloud", false);
    public static final SimpleParticleType f_123797_ = ParticleTypes.m_123824_("crit", false);
    public static final SimpleParticleType f_123798_ = ParticleTypes.m_123824_("damage_indicator", true);
    public static final SimpleParticleType f_123799_ = ParticleTypes.m_123824_("dragon_breath", false);
    public static final SimpleParticleType f_123800_ = ParticleTypes.m_123824_("dripping_lava", false);
    public static final SimpleParticleType f_123801_ = ParticleTypes.m_123824_("falling_lava", false);
    public static final SimpleParticleType f_123802_ = ParticleTypes.m_123824_("landing_lava", false);
    public static final SimpleParticleType f_123803_ = ParticleTypes.m_123824_("dripping_water", false);
    public static final SimpleParticleType f_123804_ = ParticleTypes.m_123824_("falling_water", false);
    public static final ParticleType<DustParticleOptions> f_123805_ = ParticleTypes.m_235905_("dust", false, DustParticleOptions.f_123658_, p_123819_ -> DustParticleOptions.f_123657_);
    public static final ParticleType<DustColorTransitionOptions> f_175836_ = ParticleTypes.m_235905_("dust_color_transition", false, DustColorTransitionOptions.f_175754_, p_175841_ -> DustColorTransitionOptions.f_175753_);
    public static final SimpleParticleType f_123806_ = ParticleTypes.m_123824_("effect", false);
    public static final SimpleParticleType f_123807_ = ParticleTypes.m_123824_("elder_guardian", true);
    public static final SimpleParticleType f_123808_ = ParticleTypes.m_123824_("enchanted_hit", false);
    public static final SimpleParticleType f_123809_ = ParticleTypes.m_123824_("enchant", false);
    public static final SimpleParticleType f_123810_ = ParticleTypes.m_123824_("end_rod", false);
    public static final SimpleParticleType f_123811_ = ParticleTypes.m_123824_("entity_effect", false);
    public static final SimpleParticleType f_123812_ = ParticleTypes.m_123824_("explosion_emitter", true);
    public static final SimpleParticleType f_123813_ = ParticleTypes.m_123824_("explosion", true);
    public static final SimpleParticleType f_235902_ = ParticleTypes.m_123824_("sonic_boom", true);
    public static final ParticleType<BlockParticleOption> f_123814_ = ParticleTypes.m_235905_("falling_dust", false, BlockParticleOption.f_123624_, BlockParticleOption::m_123634_);
    public static final SimpleParticleType f_123815_ = ParticleTypes.m_123824_("firework", false);
    public static final SimpleParticleType f_123816_ = ParticleTypes.m_123824_("fishing", false);
    public static final SimpleParticleType f_123744_ = ParticleTypes.m_123824_("flame", false);
    public static final SimpleParticleType f_235898_ = ParticleTypes.m_123824_("sculk_soul", false);
    public static final ParticleType<SculkChargeParticleOptions> f_235899_ = ParticleTypes.m_235905_("sculk_charge", true, SculkChargeParticleOptions.f_235913_, p_175839_ -> SculkChargeParticleOptions.f_235912_);
    public static final SimpleParticleType f_235900_ = ParticleTypes.m_123824_("sculk_charge_pop", true);
    public static final SimpleParticleType f_123745_ = ParticleTypes.m_123824_("soul_fire_flame", false);
    public static final SimpleParticleType f_123746_ = ParticleTypes.m_123824_("soul", false);
    public static final SimpleParticleType f_123747_ = ParticleTypes.m_123824_("flash", false);
    public static final SimpleParticleType f_123748_ = ParticleTypes.m_123824_("happy_villager", false);
    public static final SimpleParticleType f_123749_ = ParticleTypes.m_123824_("composter", false);
    public static final SimpleParticleType f_123750_ = ParticleTypes.m_123824_("heart", false);
    public static final SimpleParticleType f_123751_ = ParticleTypes.m_123824_("instant_effect", false);
    public static final ParticleType<ItemParticleOption> f_123752_ = ParticleTypes.m_235905_("item", false, ItemParticleOption.f_123700_, ItemParticleOption::m_123710_);
    public static final ParticleType<VibrationParticleOption> f_175820_ = ParticleTypes.m_235905_("vibration", true, VibrationParticleOption.f_175843_, p_235911_ -> VibrationParticleOption.f_175842_);
    public static final SimpleParticleType f_123753_ = ParticleTypes.m_123824_("item_slime", false);
    public static final SimpleParticleType f_123754_ = ParticleTypes.m_123824_("item_snowball", false);
    public static final SimpleParticleType f_123755_ = ParticleTypes.m_123824_("large_smoke", false);
    public static final SimpleParticleType f_123756_ = ParticleTypes.m_123824_("lava", false);
    public static final SimpleParticleType f_123757_ = ParticleTypes.m_123824_("mycelium", false);
    public static final SimpleParticleType f_123758_ = ParticleTypes.m_123824_("note", false);
    public static final SimpleParticleType f_123759_ = ParticleTypes.m_123824_("poof", true);
    public static final SimpleParticleType f_123760_ = ParticleTypes.m_123824_("portal", false);
    public static final SimpleParticleType f_123761_ = ParticleTypes.m_123824_("rain", false);
    public static final SimpleParticleType f_123762_ = ParticleTypes.m_123824_("smoke", false);
    public static final SimpleParticleType f_123763_ = ParticleTypes.m_123824_("sneeze", false);
    public static final SimpleParticleType f_123764_ = ParticleTypes.m_123824_("spit", true);
    public static final SimpleParticleType f_123765_ = ParticleTypes.m_123824_("squid_ink", true);
    public static final SimpleParticleType f_123766_ = ParticleTypes.m_123824_("sweep_attack", true);
    public static final SimpleParticleType f_123767_ = ParticleTypes.m_123824_("totem_of_undying", false);
    public static final SimpleParticleType f_123768_ = ParticleTypes.m_123824_("underwater", false);
    public static final SimpleParticleType f_123769_ = ParticleTypes.m_123824_("splash", false);
    public static final SimpleParticleType f_123771_ = ParticleTypes.m_123824_("witch", false);
    public static final SimpleParticleType f_123772_ = ParticleTypes.m_123824_("bubble_pop", false);
    public static final SimpleParticleType f_123773_ = ParticleTypes.m_123824_("current_down", false);
    public static final SimpleParticleType f_123774_ = ParticleTypes.m_123824_("bubble_column_up", false);
    public static final SimpleParticleType f_123775_ = ParticleTypes.m_123824_("nautilus", false);
    public static final SimpleParticleType f_123776_ = ParticleTypes.m_123824_("dolphin", false);
    public static final SimpleParticleType f_123777_ = ParticleTypes.m_123824_("campfire_cosy_smoke", true);
    public static final SimpleParticleType f_123778_ = ParticleTypes.m_123824_("campfire_signal_smoke", true);
    public static final SimpleParticleType f_123779_ = ParticleTypes.m_123824_("dripping_honey", false);
    public static final SimpleParticleType f_123780_ = ParticleTypes.m_123824_("falling_honey", false);
    public static final SimpleParticleType f_123781_ = ParticleTypes.m_123824_("landing_honey", false);
    public static final SimpleParticleType f_123782_ = ParticleTypes.m_123824_("falling_nectar", false);
    public static final SimpleParticleType f_175832_ = ParticleTypes.m_123824_("falling_spore_blossom", false);
    public static final SimpleParticleType f_123783_ = ParticleTypes.m_123824_("ash", false);
    public static final SimpleParticleType f_123784_ = ParticleTypes.m_123824_("crimson_spore", false);
    public static final SimpleParticleType f_123785_ = ParticleTypes.m_123824_("warped_spore", false);
    public static final SimpleParticleType f_175833_ = ParticleTypes.m_123824_("spore_blossom_air", false);
    public static final SimpleParticleType f_123786_ = ParticleTypes.m_123824_("dripping_obsidian_tear", false);
    public static final SimpleParticleType f_123787_ = ParticleTypes.m_123824_("falling_obsidian_tear", false);
    public static final SimpleParticleType f_123788_ = ParticleTypes.m_123824_("landing_obsidian_tear", false);
    public static final SimpleParticleType f_123789_ = ParticleTypes.m_123824_("reverse_portal", false);
    public static final SimpleParticleType f_123790_ = ParticleTypes.m_123824_("white_ash", false);
    public static final SimpleParticleType f_175834_ = ParticleTypes.m_123824_("small_flame", false);
    public static final SimpleParticleType f_175821_ = ParticleTypes.m_123824_("snowflake", false);
    public static final SimpleParticleType f_175822_ = ParticleTypes.m_123824_("dripping_dripstone_lava", false);
    public static final SimpleParticleType f_175823_ = ParticleTypes.m_123824_("falling_dripstone_lava", false);
    public static final SimpleParticleType f_175824_ = ParticleTypes.m_123824_("dripping_dripstone_water", false);
    public static final SimpleParticleType f_175825_ = ParticleTypes.m_123824_("falling_dripstone_water", false);
    public static final SimpleParticleType f_175826_ = ParticleTypes.m_123824_("glow_squid_ink", true);
    public static final SimpleParticleType f_175827_ = ParticleTypes.m_123824_("glow", true);
    public static final SimpleParticleType f_175828_ = ParticleTypes.m_123824_("wax_on", true);
    public static final SimpleParticleType f_175829_ = ParticleTypes.m_123824_("wax_off", true);
    public static final SimpleParticleType f_175830_ = ParticleTypes.m_123824_("electric_spark", true);
    public static final SimpleParticleType f_175831_ = ParticleTypes.m_123824_("scrape", true);
    public static final ParticleType<ShriekParticleOption> f_235901_ = ParticleTypes.m_235905_("shriek", false, ShriekParticleOption.f_235945_, p_235904_ -> ShriekParticleOption.f_235944_);
    public static final Codec<ParticleOptions> f_123791_ = Registry.f_122829_.m_194605_().dispatch("type", ParticleOptions::m_6012_, ParticleType::m_7652_);

    private static SimpleParticleType m_123824_(String p_123825_, boolean p_123826_) {
        return Registry.m_122961_(Registry.f_122829_, p_123825_, new SimpleParticleType(p_123826_));
    }

    private static <T extends ParticleOptions> ParticleType<T> m_235905_(String p_235906_, boolean p_235907_, ParticleOptions.Deserializer<T> p_235908_, final Function<ParticleType<T>, Codec<T>> p_235909_) {
        return Registry.m_122961_(Registry.f_122829_, p_235906_, new ParticleType<T>(p_235907_, p_235908_){

            @Override
            public Codec<T> m_7652_() {
                return (Codec)p_235909_.apply(this);
            }
        });
    }
}

