/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.stats;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Stats {
    public static final StatType<Block> f_12949_ = Stats.m_13010_("mined", Registry.f_122824_);
    public static final StatType<Item> f_12981_ = Stats.m_13010_("crafted", Registry.f_122827_);
    public static final StatType<Item> f_12982_ = Stats.m_13010_("used", Registry.f_122827_);
    public static final StatType<Item> f_12983_ = Stats.m_13010_("broken", Registry.f_122827_);
    public static final StatType<Item> f_12984_ = Stats.m_13010_("picked_up", Registry.f_122827_);
    public static final StatType<Item> f_12985_ = Stats.m_13010_("dropped", Registry.f_122827_);
    public static final StatType<EntityType<?>> f_12986_ = Stats.m_13010_("killed", Registry.f_122826_);
    public static final StatType<EntityType<?>> f_12987_ = Stats.m_13010_("killed_by", Registry.f_122826_);
    public static final StatType<ResourceLocation> f_12988_ = Stats.m_13010_("custom", Registry.f_122832_);
    public static final ResourceLocation f_12989_ = Stats.m_13007_("leave_game", StatFormatter.f_12873_);
    public static final ResourceLocation f_144255_ = Stats.m_13007_("play_time", StatFormatter.f_12876_);
    public static final ResourceLocation f_144256_ = Stats.m_13007_("total_world_time", StatFormatter.f_12876_);
    public static final ResourceLocation f_12991_ = Stats.m_13007_("time_since_death", StatFormatter.f_12876_);
    public static final ResourceLocation f_12992_ = Stats.m_13007_("time_since_rest", StatFormatter.f_12876_);
    public static final ResourceLocation f_12993_ = Stats.m_13007_("sneak_time", StatFormatter.f_12876_);
    public static final ResourceLocation f_12994_ = Stats.m_13007_("walk_one_cm", StatFormatter.f_12875_);
    public static final ResourceLocation f_12995_ = Stats.m_13007_("crouch_one_cm", StatFormatter.f_12875_);
    public static final ResourceLocation f_12996_ = Stats.m_13007_("sprint_one_cm", StatFormatter.f_12875_);
    public static final ResourceLocation f_12997_ = Stats.m_13007_("walk_on_water_one_cm", StatFormatter.f_12875_);
    public static final ResourceLocation f_12998_ = Stats.m_13007_("fall_one_cm", StatFormatter.f_12875_);
    public static final ResourceLocation f_12999_ = Stats.m_13007_("climb_one_cm", StatFormatter.f_12875_);
    public static final ResourceLocation f_13000_ = Stats.m_13007_("fly_one_cm", StatFormatter.f_12875_);
    public static final ResourceLocation f_13001_ = Stats.m_13007_("walk_under_water_one_cm", StatFormatter.f_12875_);
    public static final ResourceLocation f_13002_ = Stats.m_13007_("minecart_one_cm", StatFormatter.f_12875_);
    public static final ResourceLocation f_13003_ = Stats.m_13007_("boat_one_cm", StatFormatter.f_12875_);
    public static final ResourceLocation f_13004_ = Stats.m_13007_("pig_one_cm", StatFormatter.f_12875_);
    public static final ResourceLocation f_13005_ = Stats.m_13007_("horse_one_cm", StatFormatter.f_12875_);
    public static final ResourceLocation f_12923_ = Stats.m_13007_("aviate_one_cm", StatFormatter.f_12875_);
    public static final ResourceLocation f_12924_ = Stats.m_13007_("swim_one_cm", StatFormatter.f_12875_);
    public static final ResourceLocation f_12925_ = Stats.m_13007_("strider_one_cm", StatFormatter.f_12875_);
    public static final ResourceLocation f_12926_ = Stats.m_13007_("jump", StatFormatter.f_12873_);
    public static final ResourceLocation f_12927_ = Stats.m_13007_("drop", StatFormatter.f_12873_);
    public static final ResourceLocation f_12928_ = Stats.m_13007_("damage_dealt", StatFormatter.f_12874_);
    public static final ResourceLocation f_12929_ = Stats.m_13007_("damage_dealt_absorbed", StatFormatter.f_12874_);
    public static final ResourceLocation f_12930_ = Stats.m_13007_("damage_dealt_resisted", StatFormatter.f_12874_);
    public static final ResourceLocation f_12931_ = Stats.m_13007_("damage_taken", StatFormatter.f_12874_);
    public static final ResourceLocation f_12932_ = Stats.m_13007_("damage_blocked_by_shield", StatFormatter.f_12874_);
    public static final ResourceLocation f_12933_ = Stats.m_13007_("damage_absorbed", StatFormatter.f_12874_);
    public static final ResourceLocation f_12934_ = Stats.m_13007_("damage_resisted", StatFormatter.f_12874_);
    public static final ResourceLocation f_12935_ = Stats.m_13007_("deaths", StatFormatter.f_12873_);
    public static final ResourceLocation f_12936_ = Stats.m_13007_("mob_kills", StatFormatter.f_12873_);
    public static final ResourceLocation f_12937_ = Stats.m_13007_("animals_bred", StatFormatter.f_12873_);
    public static final ResourceLocation f_12938_ = Stats.m_13007_("player_kills", StatFormatter.f_12873_);
    public static final ResourceLocation f_12939_ = Stats.m_13007_("fish_caught", StatFormatter.f_12873_);
    public static final ResourceLocation f_12940_ = Stats.m_13007_("talked_to_villager", StatFormatter.f_12873_);
    public static final ResourceLocation f_12941_ = Stats.m_13007_("traded_with_villager", StatFormatter.f_12873_);
    public static final ResourceLocation f_12942_ = Stats.m_13007_("eat_cake_slice", StatFormatter.f_12873_);
    public static final ResourceLocation f_12943_ = Stats.m_13007_("fill_cauldron", StatFormatter.f_12873_);
    public static final ResourceLocation f_12944_ = Stats.m_13007_("use_cauldron", StatFormatter.f_12873_);
    public static final ResourceLocation f_12945_ = Stats.m_13007_("clean_armor", StatFormatter.f_12873_);
    public static final ResourceLocation f_12946_ = Stats.m_13007_("clean_banner", StatFormatter.f_12873_);
    public static final ResourceLocation f_12947_ = Stats.m_13007_("clean_shulker_box", StatFormatter.f_12873_);
    public static final ResourceLocation f_12948_ = Stats.m_13007_("interact_with_brewingstand", StatFormatter.f_12873_);
    public static final ResourceLocation f_12955_ = Stats.m_13007_("interact_with_beacon", StatFormatter.f_12873_);
    public static final ResourceLocation f_12956_ = Stats.m_13007_("inspect_dropper", StatFormatter.f_12873_);
    public static final ResourceLocation f_12957_ = Stats.m_13007_("inspect_hopper", StatFormatter.f_12873_);
    public static final ResourceLocation f_12958_ = Stats.m_13007_("inspect_dispenser", StatFormatter.f_12873_);
    public static final ResourceLocation f_12959_ = Stats.m_13007_("play_noteblock", StatFormatter.f_12873_);
    public static final ResourceLocation f_12960_ = Stats.m_13007_("tune_noteblock", StatFormatter.f_12873_);
    public static final ResourceLocation f_12961_ = Stats.m_13007_("pot_flower", StatFormatter.f_12873_);
    public static final ResourceLocation f_12962_ = Stats.m_13007_("trigger_trapped_chest", StatFormatter.f_12873_);
    public static final ResourceLocation f_12963_ = Stats.m_13007_("open_enderchest", StatFormatter.f_12873_);
    public static final ResourceLocation f_12964_ = Stats.m_13007_("enchant_item", StatFormatter.f_12873_);
    public static final ResourceLocation f_12965_ = Stats.m_13007_("play_record", StatFormatter.f_12873_);
    public static final ResourceLocation f_12966_ = Stats.m_13007_("interact_with_furnace", StatFormatter.f_12873_);
    public static final ResourceLocation f_12967_ = Stats.m_13007_("interact_with_crafting_table", StatFormatter.f_12873_);
    public static final ResourceLocation f_12968_ = Stats.m_13007_("open_chest", StatFormatter.f_12873_);
    public static final ResourceLocation f_12969_ = Stats.m_13007_("sleep_in_bed", StatFormatter.f_12873_);
    public static final ResourceLocation f_12970_ = Stats.m_13007_("open_shulker_box", StatFormatter.f_12873_);
    public static final ResourceLocation f_12971_ = Stats.m_13007_("open_barrel", StatFormatter.f_12873_);
    public static final ResourceLocation f_12972_ = Stats.m_13007_("interact_with_blast_furnace", StatFormatter.f_12873_);
    public static final ResourceLocation f_12973_ = Stats.m_13007_("interact_with_smoker", StatFormatter.f_12873_);
    public static final ResourceLocation f_12974_ = Stats.m_13007_("interact_with_lectern", StatFormatter.f_12873_);
    public static final ResourceLocation f_12975_ = Stats.m_13007_("interact_with_campfire", StatFormatter.f_12873_);
    public static final ResourceLocation f_12976_ = Stats.m_13007_("interact_with_cartography_table", StatFormatter.f_12873_);
    public static final ResourceLocation f_12977_ = Stats.m_13007_("interact_with_loom", StatFormatter.f_12873_);
    public static final ResourceLocation f_12978_ = Stats.m_13007_("interact_with_stonecutter", StatFormatter.f_12873_);
    public static final ResourceLocation f_12979_ = Stats.m_13007_("bell_ring", StatFormatter.f_12873_);
    public static final ResourceLocation f_12980_ = Stats.m_13007_("raid_trigger", StatFormatter.f_12873_);
    public static final ResourceLocation f_12950_ = Stats.m_13007_("raid_win", StatFormatter.f_12873_);
    public static final ResourceLocation f_12951_ = Stats.m_13007_("interact_with_anvil", StatFormatter.f_12873_);
    public static final ResourceLocation f_12952_ = Stats.m_13007_("interact_with_grindstone", StatFormatter.f_12873_);
    public static final ResourceLocation f_12953_ = Stats.m_13007_("target_hit", StatFormatter.f_12873_);
    public static final ResourceLocation f_12954_ = Stats.m_13007_("interact_with_smithing_table", StatFormatter.f_12873_);

    private static ResourceLocation m_13007_(String p_13008_, StatFormatter p_13009_) {
        ResourceLocation $$2 = new ResourceLocation(p_13008_);
        Registry.m_122961_(Registry.f_122832_, p_13008_, $$2);
        f_12988_.m_12899_($$2, p_13009_);
        return $$2;
    }

    private static <T> StatType<T> m_13010_(String p_13011_, Registry<T> p_13012_) {
        return Registry.m_122961_(Registry.f_122867_, p_13011_, new StatType<T>(p_13012_));
    }
}

