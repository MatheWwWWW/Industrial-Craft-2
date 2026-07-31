/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.stats.StatFormatter
 *  net.minecraft.stats.Stats
 */
package ic2.core.platform.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

public class IC2Stats {
    public static final ResourceLocation DROWNED_WITH_Q_HELMET = IC2Stats.createStat("drown_with_q_helmet", StatFormatter.f_12873_);
    public static final ResourceLocation CABLE_SHOCK_DAMAGE = IC2Stats.createStat("cable_shock_damage", StatFormatter.f_12873_);
    public static final ResourceLocation NUKES_SURVIVED = IC2Stats.createStat("nukes_survived", StatFormatter.f_12873_);
    public static final ResourceLocation NUKES_IGNITED = IC2Stats.createStat("nukes_ignited", StatFormatter.f_12873_);
    public static final ResourceLocation WITHERS_NUKED = IC2Stats.createStat("withers_nuked", StatFormatter.f_12873_);
    public static final ResourceLocation CHAINSAW_KILLS = IC2Stats.createStat("chainsaws_kills", StatFormatter.f_12873_);
    public static final ResourceLocation CROPS_BREED = IC2Stats.createStat("crops_breed", StatFormatter.f_12873_);
    public static final ResourceLocation JETPACK_FLY_TIME = IC2Stats.createStat("jetpack_fly_time", StatFormatter.f_12876_);
    public static final ResourceLocation ROCKET_MODE_USED = IC2Stats.createStat("rocket_mode_used", StatFormatter.f_12873_);
    public static final ResourceLocation BLOCKS_DRILLED = IC2Stats.createStat("blocks_drilled", StatFormatter.f_12873_);
    public static final ResourceLocation BLOCKS_SAWED = IC2Stats.createStat("blocks_sawed", StatFormatter.f_12873_);
    public static final ResourceLocation DISTANCE_TELEPORTED = IC2Stats.createStat("distance_teleported", StatFormatter.f_12875_);
    public static final ResourceLocation FOAM_SPRAYED = IC2Stats.createStat("foam_sprayed", StatFormatter.f_12873_);
    public static final ResourceLocation FOOD_CANS_EATEN = IC2Stats.createStat("food_cans_eaten", StatFormatter.f_12873_);
    public static final ResourceLocation AIR_CELLS_USED = IC2Stats.createStat("air_cells_used", StatFormatter.f_12873_);
    public static final ResourceLocation OVERGROWTH_USED = IC2Stats.createStat("overgrowth_used", StatFormatter.f_12873_);
    public static final ResourceLocation REVIVED_USED = IC2Stats.createStat("revived_used", StatFormatter.f_12873_);
    public static final ResourceLocation ELECTRIC_ENCHANTED = IC2Stats.createStat("electric_enchanted", StatFormatter.f_12873_);
    public static final ResourceLocation TEXTURES_STOLEN = IC2Stats.createStat("textures_stolen", StatFormatter.f_12873_);
    public static final ResourceLocation LUCKY_PERSON = IC2Stats.createStat("lucky_person", StatFormatter.f_12873_);
    public static final ResourceLocation DRAGONS_SHOT = IC2Stats.createStat("dragons_lasered", StatFormatter.f_12873_);
    public static final ResourceLocation PLAYERS_SHOT = IC2Stats.createStat("laser_kills", StatFormatter.f_12873_);
    public static final ResourceLocation CROPS_DISCOVERED = IC2Stats.createStat("crops_discovered", StatFormatter.f_12873_);
    public static final ResourceLocation HIGHEST_CROP_TIER = IC2Stats.createStat("highest_crop_tier", StatFormatter.f_12873_);
    public static final ResourceLocation HIGHEST_CROP_GROWTH = IC2Stats.createStat("highest_crop_growth", StatFormatter.f_12873_);
    public static final ResourceLocation HIGHEST_CROP_GAIN = IC2Stats.createStat("highest_crop_gain", StatFormatter.f_12873_);
    public static final ResourceLocation HIGHEST_CROP_RESISTANCE = IC2Stats.createStat("highest_crop_resistance", StatFormatter.f_12873_);

    public static void init() {
    }

    public static ResourceLocation createStat(String name, StatFormatter format) {
        ResourceLocation location = new ResourceLocation("ic2", name);
        Registry.m_122965_((Registry)Registry.f_122832_, (ResourceLocation)location, (Object)location);
        Stats.f_12988_.m_12899_((Object)location, format);
        return location;
    }
}

