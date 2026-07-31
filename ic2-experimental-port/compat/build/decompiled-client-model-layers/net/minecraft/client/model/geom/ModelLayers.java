/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 */
package net.minecraft.client.model.geom;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModelLayers {
    private static final String f_171234_ = "main";
    private static final Set<ModelLayerLocation> f_171262_ = Sets.newHashSet();
    public static final ModelLayerLocation f_233547_ = ModelLayers.m_171293_("allay");
    public static final ModelLayerLocation f_171155_ = ModelLayers.m_171293_("armor_stand");
    public static final ModelLayerLocation f_171208_ = ModelLayers.m_171298_("armor_stand");
    public static final ModelLayerLocation f_171261_ = ModelLayers.m_171303_("armor_stand");
    public static final ModelLayerLocation f_171263_ = ModelLayers.m_171293_("axolotl");
    public static final ModelLayerLocation f_171264_ = ModelLayers.m_171293_("banner");
    public static final ModelLayerLocation f_171265_ = ModelLayers.m_171293_("bat");
    public static final ModelLayerLocation f_171266_ = ModelLayers.m_171293_("bed_foot");
    public static final ModelLayerLocation f_171267_ = ModelLayers.m_171293_("bed_head");
    public static final ModelLayerLocation f_171268_ = ModelLayers.m_171293_("bee");
    public static final ModelLayerLocation f_171269_ = ModelLayers.m_171293_("bell");
    public static final ModelLayerLocation f_171270_ = ModelLayers.m_171293_("blaze");
    public static final ModelLayerLocation f_171271_ = ModelLayers.m_171293_("book");
    public static final ModelLayerLocation f_171272_ = ModelLayers.m_171293_("cat");
    public static final ModelLayerLocation f_171273_ = ModelLayers.m_171295_("cat", "collar");
    public static final ModelLayerLocation f_171274_ = ModelLayers.m_171293_("cave_spider");
    public static final ModelLayerLocation f_171275_ = ModelLayers.m_171293_("chest");
    public static final ModelLayerLocation f_171276_ = ModelLayers.m_171293_("chest_minecart");
    public static final ModelLayerLocation f_171277_ = ModelLayers.m_171293_("chicken");
    public static final ModelLayerLocation f_171278_ = ModelLayers.m_171293_("cod");
    public static final ModelLayerLocation f_171279_ = ModelLayers.m_171293_("command_block_minecart");
    public static final ModelLayerLocation f_171280_ = ModelLayers.m_171295_("conduit", "cage");
    public static final ModelLayerLocation f_171281_ = ModelLayers.m_171295_("conduit", "eye");
    public static final ModelLayerLocation f_171282_ = ModelLayers.m_171295_("conduit", "shell");
    public static final ModelLayerLocation f_171283_ = ModelLayers.m_171295_("conduit", "wind");
    public static final ModelLayerLocation f_171284_ = ModelLayers.m_171293_("cow");
    public static final ModelLayerLocation f_171285_ = ModelLayers.m_171293_("creeper");
    public static final ModelLayerLocation f_171129_ = ModelLayers.m_171295_("creeper", "armor");
    public static final ModelLayerLocation f_171130_ = ModelLayers.m_171293_("creeper_head");
    public static final ModelLayerLocation f_171131_ = ModelLayers.m_171293_("dolphin");
    public static final ModelLayerLocation f_171132_ = ModelLayers.m_171293_("donkey");
    public static final ModelLayerLocation f_171133_ = ModelLayers.m_171293_("double_chest_left");
    public static final ModelLayerLocation f_171134_ = ModelLayers.m_171293_("double_chest_right");
    public static final ModelLayerLocation f_171135_ = ModelLayers.m_171293_("dragon_skull");
    public static final ModelLayerLocation f_171136_ = ModelLayers.m_171293_("drowned");
    public static final ModelLayerLocation f_171137_ = ModelLayers.m_171298_("drowned");
    public static final ModelLayerLocation f_171138_ = ModelLayers.m_171303_("drowned");
    public static final ModelLayerLocation f_171139_ = ModelLayers.m_171295_("drowned", "outer");
    public static final ModelLayerLocation f_171140_ = ModelLayers.m_171293_("elder_guardian");
    public static final ModelLayerLocation f_171141_ = ModelLayers.m_171293_("elytra");
    public static final ModelLayerLocation f_171142_ = ModelLayers.m_171293_("enderman");
    public static final ModelLayerLocation f_171143_ = ModelLayers.m_171293_("endermite");
    public static final ModelLayerLocation f_171144_ = ModelLayers.m_171293_("ender_dragon");
    public static final ModelLayerLocation f_171145_ = ModelLayers.m_171293_("end_crystal");
    public static final ModelLayerLocation f_171146_ = ModelLayers.m_171293_("evoker");
    public static final ModelLayerLocation f_171147_ = ModelLayers.m_171293_("evoker_fangs");
    public static final ModelLayerLocation f_171148_ = ModelLayers.m_171293_("fox");
    public static final ModelLayerLocation f_233546_ = ModelLayers.m_171293_("frog");
    public static final ModelLayerLocation f_171149_ = ModelLayers.m_171293_("furnace_minecart");
    public static final ModelLayerLocation f_171150_ = ModelLayers.m_171293_("ghast");
    public static final ModelLayerLocation f_171151_ = ModelLayers.m_171293_("giant");
    public static final ModelLayerLocation f_171152_ = ModelLayers.m_171298_("giant");
    public static final ModelLayerLocation f_171153_ = ModelLayers.m_171303_("giant");
    public static final ModelLayerLocation f_171154_ = ModelLayers.m_171293_("glow_squid");
    public static final ModelLayerLocation f_171182_ = ModelLayers.m_171293_("goat");
    public static final ModelLayerLocation f_171183_ = ModelLayers.m_171293_("guardian");
    public static final ModelLayerLocation f_171184_ = ModelLayers.m_171293_("hoglin");
    public static final ModelLayerLocation f_171185_ = ModelLayers.m_171293_("hopper_minecart");
    public static final ModelLayerLocation f_171186_ = ModelLayers.m_171293_("horse");
    public static final ModelLayerLocation f_171187_ = ModelLayers.m_171293_("horse_armor");
    public static final ModelLayerLocation f_171188_ = ModelLayers.m_171293_("husk");
    public static final ModelLayerLocation f_171189_ = ModelLayers.m_171298_("husk");
    public static final ModelLayerLocation f_171190_ = ModelLayers.m_171303_("husk");
    public static final ModelLayerLocation f_171191_ = ModelLayers.m_171293_("illusioner");
    public static final ModelLayerLocation f_171192_ = ModelLayers.m_171293_("iron_golem");
    public static final ModelLayerLocation f_171193_ = ModelLayers.m_171293_("leash_knot");
    public static final ModelLayerLocation f_171194_ = ModelLayers.m_171293_("llama");
    public static final ModelLayerLocation f_171195_ = ModelLayers.m_171295_("llama", "decor");
    public static final ModelLayerLocation f_171196_ = ModelLayers.m_171293_("llama_spit");
    public static final ModelLayerLocation f_171197_ = ModelLayers.m_171293_("magma_cube");
    public static final ModelLayerLocation f_171198_ = ModelLayers.m_171293_("minecart");
    public static final ModelLayerLocation f_171199_ = ModelLayers.m_171293_("mooshroom");
    public static final ModelLayerLocation f_171200_ = ModelLayers.m_171293_("mule");
    public static final ModelLayerLocation f_171201_ = ModelLayers.m_171293_("ocelot");
    public static final ModelLayerLocation f_171202_ = ModelLayers.m_171293_("panda");
    public static final ModelLayerLocation f_171203_ = ModelLayers.m_171293_("parrot");
    public static final ModelLayerLocation f_171204_ = ModelLayers.m_171293_("phantom");
    public static final ModelLayerLocation f_171205_ = ModelLayers.m_171293_("pig");
    public static final ModelLayerLocation f_171206_ = ModelLayers.m_171293_("piglin");
    public static final ModelLayerLocation f_171207_ = ModelLayers.m_171293_("piglin_brute");
    public static final ModelLayerLocation f_171156_ = ModelLayers.m_171298_("piglin_brute");
    public static final ModelLayerLocation f_171157_ = ModelLayers.m_171303_("piglin_brute");
    public static final ModelLayerLocation f_171158_ = ModelLayers.m_171298_("piglin");
    public static final ModelLayerLocation f_171159_ = ModelLayers.m_171303_("piglin");
    public static final ModelLayerLocation f_171160_ = ModelLayers.m_171295_("pig", "saddle");
    public static final ModelLayerLocation f_171161_ = ModelLayers.m_171293_("pillager");
    public static final ModelLayerLocation f_171162_ = ModelLayers.m_171293_("player");
    public static final ModelLayerLocation f_171163_ = ModelLayers.m_171293_("player_head");
    public static final ModelLayerLocation f_171164_ = ModelLayers.m_171298_("player");
    public static final ModelLayerLocation f_171165_ = ModelLayers.m_171303_("player");
    public static final ModelLayerLocation f_171166_ = ModelLayers.m_171293_("player_slim");
    public static final ModelLayerLocation f_171167_ = ModelLayers.m_171298_("player_slim");
    public static final ModelLayerLocation f_171168_ = ModelLayers.m_171303_("player_slim");
    public static final ModelLayerLocation f_171169_ = ModelLayers.m_171293_("spin_attack");
    public static final ModelLayerLocation f_171170_ = ModelLayers.m_171293_("polar_bear");
    public static final ModelLayerLocation f_171171_ = ModelLayers.m_171293_("pufferfish_big");
    public static final ModelLayerLocation f_171172_ = ModelLayers.m_171293_("pufferfish_medium");
    public static final ModelLayerLocation f_171173_ = ModelLayers.m_171293_("pufferfish_small");
    public static final ModelLayerLocation f_171174_ = ModelLayers.m_171293_("rabbit");
    public static final ModelLayerLocation f_171175_ = ModelLayers.m_171293_("ravager");
    public static final ModelLayerLocation f_171176_ = ModelLayers.m_171293_("salmon");
    public static final ModelLayerLocation f_171177_ = ModelLayers.m_171293_("sheep");
    public static final ModelLayerLocation f_171178_ = ModelLayers.m_171295_("sheep", "fur");
    public static final ModelLayerLocation f_171179_ = ModelLayers.m_171293_("shield");
    public static final ModelLayerLocation f_171180_ = ModelLayers.m_171293_("shulker");
    public static final ModelLayerLocation f_171181_ = ModelLayers.m_171293_("shulker_bullet");
    public static final ModelLayerLocation f_171235_ = ModelLayers.m_171293_("silverfish");
    public static final ModelLayerLocation f_171236_ = ModelLayers.m_171293_("skeleton");
    public static final ModelLayerLocation f_171237_ = ModelLayers.m_171293_("skeleton_horse");
    public static final ModelLayerLocation f_171238_ = ModelLayers.m_171298_("skeleton");
    public static final ModelLayerLocation f_171239_ = ModelLayers.m_171303_("skeleton");
    public static final ModelLayerLocation f_171240_ = ModelLayers.m_171293_("skeleton_skull");
    public static final ModelLayerLocation f_171241_ = ModelLayers.m_171293_("slime");
    public static final ModelLayerLocation f_171242_ = ModelLayers.m_171295_("slime", "outer");
    public static final ModelLayerLocation f_171243_ = ModelLayers.m_171293_("snow_golem");
    public static final ModelLayerLocation f_171244_ = ModelLayers.m_171293_("spawner_minecart");
    public static final ModelLayerLocation f_171245_ = ModelLayers.m_171293_("spider");
    public static final ModelLayerLocation f_171246_ = ModelLayers.m_171293_("squid");
    public static final ModelLayerLocation f_171247_ = ModelLayers.m_171293_("stray");
    public static final ModelLayerLocation f_171248_ = ModelLayers.m_171298_("stray");
    public static final ModelLayerLocation f_171249_ = ModelLayers.m_171303_("stray");
    public static final ModelLayerLocation f_171250_ = ModelLayers.m_171295_("stray", "outer");
    public static final ModelLayerLocation f_171251_ = ModelLayers.m_171293_("strider");
    public static final ModelLayerLocation f_171252_ = ModelLayers.m_171295_("strider", "saddle");
    public static final ModelLayerLocation f_233549_ = ModelLayers.m_171293_("tadpole");
    public static final ModelLayerLocation f_171253_ = ModelLayers.m_171293_("tnt_minecart");
    public static final ModelLayerLocation f_171254_ = ModelLayers.m_171293_("trader_llama");
    public static final ModelLayerLocation f_171255_ = ModelLayers.m_171293_("trident");
    public static final ModelLayerLocation f_171256_ = ModelLayers.m_171293_("tropical_fish_large");
    public static final ModelLayerLocation f_171257_ = ModelLayers.m_171295_("tropical_fish_large", "pattern");
    public static final ModelLayerLocation f_171258_ = ModelLayers.m_171293_("tropical_fish_small");
    public static final ModelLayerLocation f_171259_ = ModelLayers.m_171295_("tropical_fish_small", "pattern");
    public static final ModelLayerLocation f_171260_ = ModelLayers.m_171293_("turtle");
    public static final ModelLayerLocation f_171209_ = ModelLayers.m_171293_("vex");
    public static final ModelLayerLocation f_171210_ = ModelLayers.m_171293_("villager");
    public static final ModelLayerLocation f_171211_ = ModelLayers.m_171293_("vindicator");
    public static final ModelLayerLocation f_233548_ = ModelLayers.m_171293_("warden");
    public static final ModelLayerLocation f_171212_ = ModelLayers.m_171293_("wandering_trader");
    public static final ModelLayerLocation f_171213_ = ModelLayers.m_171293_("witch");
    public static final ModelLayerLocation f_171214_ = ModelLayers.m_171293_("wither");
    public static final ModelLayerLocation f_171215_ = ModelLayers.m_171295_("wither", "armor");
    public static final ModelLayerLocation f_171216_ = ModelLayers.m_171293_("wither_skeleton");
    public static final ModelLayerLocation f_171217_ = ModelLayers.m_171298_("wither_skeleton");
    public static final ModelLayerLocation f_171218_ = ModelLayers.m_171303_("wither_skeleton");
    public static final ModelLayerLocation f_171219_ = ModelLayers.m_171293_("wither_skeleton_skull");
    public static final ModelLayerLocation f_171220_ = ModelLayers.m_171293_("wither_skull");
    public static final ModelLayerLocation f_171221_ = ModelLayers.m_171293_("wolf");
    public static final ModelLayerLocation f_171222_ = ModelLayers.m_171293_("zoglin");
    public static final ModelLayerLocation f_171223_ = ModelLayers.m_171293_("zombie");
    public static final ModelLayerLocation f_171224_ = ModelLayers.m_171293_("zombie_head");
    public static final ModelLayerLocation f_171225_ = ModelLayers.m_171293_("zombie_horse");
    public static final ModelLayerLocation f_171226_ = ModelLayers.m_171298_("zombie");
    public static final ModelLayerLocation f_171227_ = ModelLayers.m_171303_("zombie");
    public static final ModelLayerLocation f_171228_ = ModelLayers.m_171293_("zombie_villager");
    public static final ModelLayerLocation f_171229_ = ModelLayers.m_171298_("zombie_villager");
    public static final ModelLayerLocation f_171230_ = ModelLayers.m_171303_("zombie_villager");
    public static final ModelLayerLocation f_171231_ = ModelLayers.m_171293_("zombified_piglin");
    public static final ModelLayerLocation f_171232_ = ModelLayers.m_171298_("zombified_piglin");
    public static final ModelLayerLocation f_171233_ = ModelLayers.m_171303_("zombified_piglin");

    private static ModelLayerLocation m_171293_(String p_171294_) {
        return ModelLayers.m_171295_(p_171294_, f_171234_);
    }

    private static ModelLayerLocation m_171295_(String p_171296_, String p_171297_) {
        ModelLayerLocation $$2 = ModelLayers.m_171300_(p_171296_, p_171297_);
        if (!f_171262_.add($$2)) {
            throw new IllegalStateException("Duplicate registration for " + $$2);
        }
        return $$2;
    }

    private static ModelLayerLocation m_171300_(String p_171301_, String p_171302_) {
        return new ModelLayerLocation(new ResourceLocation("minecraft", p_171301_), p_171302_);
    }

    private static ModelLayerLocation m_171298_(String p_171299_) {
        return ModelLayers.m_171295_(p_171299_, "inner_armor");
    }

    private static ModelLayerLocation m_171303_(String p_171304_) {
        return ModelLayers.m_171295_(p_171304_, "outer_armor");
    }

    public static ModelLayerLocation m_171289_(Boat.Type p_171290_) {
        return ModelLayers.m_171300_("boat/" + p_171290_.m_38429_(), f_171234_);
    }

    public static ModelLayerLocation m_233550_(Boat.Type p_233551_) {
        return ModelLayers.m_171300_("chest_boat/" + p_233551_.m_38429_(), f_171234_);
    }

    public static ModelLayerLocation m_171291_(WoodType p_171292_) {
        return ModelLayers.m_171300_("sign/" + p_171292_.m_61846_(), f_171234_);
    }

    public static Stream<ModelLayerLocation> m_171288_() {
        return f_171262_.stream();
    }
}

