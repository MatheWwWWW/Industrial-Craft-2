/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.models.model;

import java.util.Optional;
import java.util.stream.IntStream;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

public class ModelTemplates {
    public static final ModelTemplate f_125647_ = ModelTemplates.m_125723_("cube", TextureSlot.f_125869_, TextureSlot.f_125876_, TextureSlot.f_125877_, TextureSlot.f_125878_, TextureSlot.f_125879_, TextureSlot.f_125880_, TextureSlot.f_125881_);
    public static final ModelTemplate f_125691_ = ModelTemplates.m_125723_("cube_directional", TextureSlot.f_125869_, TextureSlot.f_125876_, TextureSlot.f_125877_, TextureSlot.f_125878_, TextureSlot.f_125879_, TextureSlot.f_125880_, TextureSlot.f_125881_);
    public static final ModelTemplate f_125692_ = ModelTemplates.m_125723_("cube_all", TextureSlot.f_125867_);
    public static final ModelTemplate f_125693_ = ModelTemplates.m_125719_("cube_mirrored_all", "_mirrored", TextureSlot.f_125867_);
    public static final ModelTemplate f_236341_ = ModelTemplates.m_125719_("cube_north_west_mirrored_all", "_north_west_mirrored", TextureSlot.f_125867_);
    public static final ModelTemplate f_125694_ = ModelTemplates.m_125723_("cube_column", TextureSlot.f_125870_, TextureSlot.f_125875_);
    public static final ModelTemplate f_125695_ = ModelTemplates.m_125719_("cube_column_horizontal", "_horizontal", TextureSlot.f_125870_, TextureSlot.f_125875_);
    public static final ModelTemplate f_176473_ = ModelTemplates.m_125719_("cube_column_mirrored", "_mirrored", TextureSlot.f_125870_, TextureSlot.f_125875_);
    public static final ModelTemplate f_125696_ = ModelTemplates.m_125723_("cube_top", TextureSlot.f_125872_, TextureSlot.f_125875_);
    public static final ModelTemplate f_125697_ = ModelTemplates.m_125723_("cube_bottom_top", TextureSlot.f_125872_, TextureSlot.f_125871_, TextureSlot.f_125875_);
    public static final ModelTemplate f_125698_ = ModelTemplates.m_125723_("orientable", TextureSlot.f_125872_, TextureSlot.f_125873_, TextureSlot.f_125875_);
    public static final ModelTemplate f_125699_ = ModelTemplates.m_125723_("orientable_with_bottom", TextureSlot.f_125872_, TextureSlot.f_125871_, TextureSlot.f_125875_, TextureSlot.f_125873_);
    public static final ModelTemplate f_125700_ = ModelTemplates.m_125719_("orientable_vertical", "_vertical", TextureSlot.f_125873_, TextureSlot.f_125875_);
    public static final ModelTemplate f_125701_ = ModelTemplates.m_125723_("button", TextureSlot.f_125868_);
    public static final ModelTemplate f_125702_ = ModelTemplates.m_125719_("button_pressed", "_pressed", TextureSlot.f_125868_);
    public static final ModelTemplate f_125703_ = ModelTemplates.m_125719_("button_inventory", "_inventory", TextureSlot.f_125868_);
    public static final ModelTemplate f_236342_ = ModelTemplates.m_125719_("door_bottom_left", "_bottom_left", TextureSlot.f_125872_, TextureSlot.f_125871_);
    public static final ModelTemplate f_236343_ = ModelTemplates.m_125719_("door_bottom_left_open", "_bottom_left_open", TextureSlot.f_125872_, TextureSlot.f_125871_);
    public static final ModelTemplate f_236344_ = ModelTemplates.m_125719_("door_bottom_right", "_bottom_right", TextureSlot.f_125872_, TextureSlot.f_125871_);
    public static final ModelTemplate f_236345_ = ModelTemplates.m_125719_("door_bottom_right_open", "_bottom_right_open", TextureSlot.f_125872_, TextureSlot.f_125871_);
    public static final ModelTemplate f_236346_ = ModelTemplates.m_125719_("door_top_left", "_top_left", TextureSlot.f_125872_, TextureSlot.f_125871_);
    public static final ModelTemplate f_236347_ = ModelTemplates.m_125719_("door_top_left_open", "_top_left_open", TextureSlot.f_125872_, TextureSlot.f_125871_);
    public static final ModelTemplate f_236348_ = ModelTemplates.m_125719_("door_top_right", "_top_right", TextureSlot.f_125872_, TextureSlot.f_125871_);
    public static final ModelTemplate f_236349_ = ModelTemplates.m_125719_("door_top_right_open", "_top_right_open", TextureSlot.f_125872_, TextureSlot.f_125871_);
    public static final ModelTemplate f_125708_ = ModelTemplates.m_125719_("fence_post", "_post", TextureSlot.f_125868_);
    public static final ModelTemplate f_125709_ = ModelTemplates.m_125719_("fence_side", "_side", TextureSlot.f_125868_);
    public static final ModelTemplate f_125710_ = ModelTemplates.m_125719_("fence_inventory", "_inventory", TextureSlot.f_125868_);
    public static final ModelTemplate f_125711_ = ModelTemplates.m_125719_("template_wall_post", "_post", TextureSlot.f_125884_);
    public static final ModelTemplate f_125712_ = ModelTemplates.m_125719_("template_wall_side", "_side", TextureSlot.f_125884_);
    public static final ModelTemplate f_125713_ = ModelTemplates.m_125719_("template_wall_side_tall", "_side_tall", TextureSlot.f_125884_);
    public static final ModelTemplate f_125714_ = ModelTemplates.m_125719_("wall_inventory", "_inventory", TextureSlot.f_125884_);
    public static final ModelTemplate f_125715_ = ModelTemplates.m_125723_("template_fence_gate", TextureSlot.f_125868_);
    public static final ModelTemplate f_125621_ = ModelTemplates.m_125719_("template_fence_gate_open", "_open", TextureSlot.f_125868_);
    public static final ModelTemplate f_125622_ = ModelTemplates.m_125719_("template_fence_gate_wall", "_wall", TextureSlot.f_125868_);
    public static final ModelTemplate f_125623_ = ModelTemplates.m_125719_("template_fence_gate_wall_open", "_wall_open", TextureSlot.f_125868_);
    public static final ModelTemplate f_125624_ = ModelTemplates.m_125723_("pressure_plate_up", TextureSlot.f_125868_);
    public static final ModelTemplate f_125625_ = ModelTemplates.m_125719_("pressure_plate_down", "_down", TextureSlot.f_125868_);
    public static final ModelTemplate f_125626_ = ModelTemplates.m_125726_(TextureSlot.f_125869_);
    public static final ModelTemplate f_125627_ = ModelTemplates.m_125723_("slab", TextureSlot.f_125871_, TextureSlot.f_125872_, TextureSlot.f_125875_);
    public static final ModelTemplate f_125628_ = ModelTemplates.m_125719_("slab_top", "_top", TextureSlot.f_125871_, TextureSlot.f_125872_, TextureSlot.f_125875_);
    public static final ModelTemplate f_125629_ = ModelTemplates.m_125723_("leaves", TextureSlot.f_125867_);
    public static final ModelTemplate f_125630_ = ModelTemplates.m_125723_("stairs", TextureSlot.f_125871_, TextureSlot.f_125872_, TextureSlot.f_125875_);
    public static final ModelTemplate f_125631_ = ModelTemplates.m_125719_("inner_stairs", "_inner", TextureSlot.f_125871_, TextureSlot.f_125872_, TextureSlot.f_125875_);
    public static final ModelTemplate f_125632_ = ModelTemplates.m_125719_("outer_stairs", "_outer", TextureSlot.f_125871_, TextureSlot.f_125872_, TextureSlot.f_125875_);
    public static final ModelTemplate f_125633_ = ModelTemplates.m_125719_("template_trapdoor_top", "_top", TextureSlot.f_125868_);
    public static final ModelTemplate f_125634_ = ModelTemplates.m_125719_("template_trapdoor_bottom", "_bottom", TextureSlot.f_125868_);
    public static final ModelTemplate f_125635_ = ModelTemplates.m_125719_("template_trapdoor_open", "_open", TextureSlot.f_125868_);
    public static final ModelTemplate f_125636_ = ModelTemplates.m_125719_("template_orientable_trapdoor_top", "_top", TextureSlot.f_125868_);
    public static final ModelTemplate f_125637_ = ModelTemplates.m_125719_("template_orientable_trapdoor_bottom", "_bottom", TextureSlot.f_125868_);
    public static final ModelTemplate f_125638_ = ModelTemplates.m_125719_("template_orientable_trapdoor_open", "_open", TextureSlot.f_125868_);
    public static final ModelTemplate f_176462_ = ModelTemplates.m_125723_("pointed_dripstone", TextureSlot.f_125882_);
    public static final ModelTemplate f_125639_ = ModelTemplates.m_125723_("cross", TextureSlot.f_125882_);
    public static final ModelTemplate f_125640_ = ModelTemplates.m_125723_("tinted_cross", TextureSlot.f_125882_);
    public static final ModelTemplate f_125641_ = ModelTemplates.m_125723_("flower_pot_cross", TextureSlot.f_125883_);
    public static final ModelTemplate f_125642_ = ModelTemplates.m_125723_("tinted_flower_pot_cross", TextureSlot.f_125883_);
    public static final ModelTemplate f_125643_ = ModelTemplates.m_125723_("rail_flat", TextureSlot.f_125885_);
    public static final ModelTemplate f_125644_ = ModelTemplates.m_125719_("rail_curved", "_corner", TextureSlot.f_125885_);
    public static final ModelTemplate f_125645_ = ModelTemplates.m_125719_("template_rail_raised_ne", "_raised_ne", TextureSlot.f_125885_);
    public static final ModelTemplate f_125646_ = ModelTemplates.m_125719_("template_rail_raised_sw", "_raised_sw", TextureSlot.f_125885_);
    public static final ModelTemplate f_125665_ = ModelTemplates.m_125723_("carpet", TextureSlot.f_125886_);
    public static final ModelTemplate f_125666_ = ModelTemplates.m_125723_("coral_fan", TextureSlot.f_125890_);
    public static final ModelTemplate f_125667_ = ModelTemplates.m_125723_("coral_wall_fan", TextureSlot.f_125890_);
    public static final ModelTemplate f_125668_ = ModelTemplates.m_125723_("template_glazed_terracotta", TextureSlot.f_125887_);
    public static final ModelTemplate f_125669_ = ModelTemplates.m_125723_("template_chorus_flower", TextureSlot.f_125868_);
    public static final ModelTemplate f_125670_ = ModelTemplates.m_125723_("template_daylight_detector", TextureSlot.f_125872_, TextureSlot.f_125875_);
    public static final ModelTemplate f_125671_ = ModelTemplates.m_125719_("template_glass_pane_noside", "_noside", TextureSlot.f_125888_);
    public static final ModelTemplate f_125672_ = ModelTemplates.m_125719_("template_glass_pane_noside_alt", "_noside_alt", TextureSlot.f_125888_);
    public static final ModelTemplate f_125673_ = ModelTemplates.m_125719_("template_glass_pane_post", "_post", TextureSlot.f_125888_, TextureSlot.f_125889_);
    public static final ModelTemplate f_125674_ = ModelTemplates.m_125719_("template_glass_pane_side", "_side", TextureSlot.f_125888_, TextureSlot.f_125889_);
    public static final ModelTemplate f_125675_ = ModelTemplates.m_125719_("template_glass_pane_side_alt", "_side_alt", TextureSlot.f_125888_, TextureSlot.f_125889_);
    public static final ModelTemplate f_125676_ = ModelTemplates.m_125723_("template_command_block", TextureSlot.f_125873_, TextureSlot.f_125874_, TextureSlot.f_125875_);
    public static final ModelTemplate f_125677_ = ModelTemplates.m_125723_("template_anvil", TextureSlot.f_125872_);
    public static final ModelTemplate[] f_125678_ = (ModelTemplate[])IntStream.range(0, 8).mapToObj(p_125729_ -> ModelTemplates.m_125719_("stem_growth" + p_125729_, "_stage" + p_125729_, TextureSlot.f_125891_)).toArray(ModelTemplate[]::new);
    public static final ModelTemplate f_125679_ = ModelTemplates.m_125723_("stem_fruit", TextureSlot.f_125891_, TextureSlot.f_125892_);
    public static final ModelTemplate f_125680_ = ModelTemplates.m_125723_("crop", TextureSlot.f_125856_);
    public static final ModelTemplate f_125681_ = ModelTemplates.m_125723_("template_farmland", TextureSlot.f_125857_, TextureSlot.f_125872_);
    public static final ModelTemplate f_125682_ = ModelTemplates.m_125723_("template_fire_floor", TextureSlot.f_125858_);
    public static final ModelTemplate f_125683_ = ModelTemplates.m_125723_("template_fire_side", TextureSlot.f_125858_);
    public static final ModelTemplate f_125684_ = ModelTemplates.m_125723_("template_fire_side_alt", TextureSlot.f_125858_);
    public static final ModelTemplate f_125685_ = ModelTemplates.m_125723_("template_fire_up", TextureSlot.f_125858_);
    public static final ModelTemplate f_125686_ = ModelTemplates.m_125723_("template_fire_up_alt", TextureSlot.f_125858_);
    public static final ModelTemplate f_125687_ = ModelTemplates.m_125723_("template_campfire", TextureSlot.f_125858_, TextureSlot.f_125864_);
    public static final ModelTemplate f_125688_ = ModelTemplates.m_125723_("template_lantern", TextureSlot.f_125859_);
    public static final ModelTemplate f_125689_ = ModelTemplates.m_125719_("template_hanging_lantern", "_hanging", TextureSlot.f_125859_);
    public static final ModelTemplate f_125690_ = ModelTemplates.m_125723_("template_torch", TextureSlot.f_125862_);
    public static final ModelTemplate f_125648_ = ModelTemplates.m_125723_("template_torch_wall", TextureSlot.f_125862_);
    public static final ModelTemplate f_125649_ = ModelTemplates.m_125723_("template_piston", TextureSlot.f_125860_, TextureSlot.f_125871_, TextureSlot.f_125875_);
    public static final ModelTemplate f_125650_ = ModelTemplates.m_125723_("template_piston_head", TextureSlot.f_125860_, TextureSlot.f_125875_, TextureSlot.f_125861_);
    public static final ModelTemplate f_125651_ = ModelTemplates.m_125723_("template_piston_head_short", TextureSlot.f_125860_, TextureSlot.f_125875_, TextureSlot.f_125861_);
    public static final ModelTemplate f_125652_ = ModelTemplates.m_125723_("template_seagrass", TextureSlot.f_125868_);
    public static final ModelTemplate f_125653_ = ModelTemplates.m_125723_("template_turtle_egg", TextureSlot.f_125867_);
    public static final ModelTemplate f_125654_ = ModelTemplates.m_125723_("template_two_turtle_eggs", TextureSlot.f_125867_);
    public static final ModelTemplate f_125655_ = ModelTemplates.m_125723_("template_three_turtle_eggs", TextureSlot.f_125867_);
    public static final ModelTemplate f_125656_ = ModelTemplates.m_125723_("template_four_turtle_eggs", TextureSlot.f_125867_);
    public static final ModelTemplate f_125657_ = ModelTemplates.m_125723_("template_single_face", TextureSlot.f_125868_);
    public static final ModelTemplate f_176463_ = ModelTemplates.m_125723_("template_cauldron_level1", TextureSlot.f_176492_, TextureSlot.f_176491_, TextureSlot.f_125869_, TextureSlot.f_125872_, TextureSlot.f_125871_, TextureSlot.f_125875_);
    public static final ModelTemplate f_176464_ = ModelTemplates.m_125723_("template_cauldron_level2", TextureSlot.f_176492_, TextureSlot.f_176491_, TextureSlot.f_125869_, TextureSlot.f_125872_, TextureSlot.f_125871_, TextureSlot.f_125875_);
    public static final ModelTemplate f_176465_ = ModelTemplates.m_125723_("template_cauldron_full", TextureSlot.f_176492_, TextureSlot.f_176491_, TextureSlot.f_125869_, TextureSlot.f_125872_, TextureSlot.f_125871_, TextureSlot.f_125875_);
    public static final ModelTemplate f_176466_ = ModelTemplates.m_125723_("template_azalea", TextureSlot.f_125872_, TextureSlot.f_125875_);
    public static final ModelTemplate f_176467_ = ModelTemplates.m_125723_("template_potted_azalea_bush", TextureSlot.f_125872_, TextureSlot.f_125875_);
    public static final ModelTemplate f_125658_ = ModelTemplates.m_125730_("generated", TextureSlot.f_125863_);
    public static final ModelTemplate f_125659_ = ModelTemplates.m_125730_("handheld", TextureSlot.f_125863_);
    public static final ModelTemplate f_125660_ = ModelTemplates.m_125730_("handheld_rod", TextureSlot.f_125863_);
    public static final ModelTemplate f_125661_ = ModelTemplates.m_125730_("template_shulker_box", TextureSlot.f_125869_);
    public static final ModelTemplate f_125662_ = ModelTemplates.m_125730_("template_bed", TextureSlot.f_125869_);
    public static final ModelTemplate f_125663_ = ModelTemplates.m_125730_("template_banner", new TextureSlot[0]);
    public static final ModelTemplate f_125664_ = ModelTemplates.m_125730_("template_skull", new TextureSlot[0]);
    public static final ModelTemplate f_176468_ = ModelTemplates.m_125723_("template_candle", TextureSlot.f_125867_, TextureSlot.f_125869_);
    public static final ModelTemplate f_176469_ = ModelTemplates.m_125723_("template_two_candles", TextureSlot.f_125867_, TextureSlot.f_125869_);
    public static final ModelTemplate f_176470_ = ModelTemplates.m_125723_("template_three_candles", TextureSlot.f_125867_, TextureSlot.f_125869_);
    public static final ModelTemplate f_176471_ = ModelTemplates.m_125723_("template_four_candles", TextureSlot.f_125867_, TextureSlot.f_125869_);
    public static final ModelTemplate f_176472_ = ModelTemplates.m_125723_("template_cake_with_candle", TextureSlot.f_176490_, TextureSlot.f_125871_, TextureSlot.f_125875_, TextureSlot.f_125872_, TextureSlot.f_125869_);
    public static final ModelTemplate f_236340_ = ModelTemplates.m_125723_("template_sculk_shrieker", TextureSlot.f_125871_, TextureSlot.f_125875_, TextureSlot.f_125872_, TextureSlot.f_125869_, TextureSlot.f_236352_);

    private static ModelTemplate m_125726_(TextureSlot ... p_125727_) {
        return new ModelTemplate(Optional.empty(), Optional.empty(), p_125727_);
    }

    private static ModelTemplate m_125723_(String p_125724_, TextureSlot ... p_125725_) {
        return new ModelTemplate(Optional.of(new ResourceLocation("minecraft", "block/" + p_125724_)), Optional.empty(), p_125725_);
    }

    private static ModelTemplate m_125730_(String p_125731_, TextureSlot ... p_125732_) {
        return new ModelTemplate(Optional.of(new ResourceLocation("minecraft", "item/" + p_125731_)), Optional.empty(), p_125732_);
    }

    private static ModelTemplate m_125719_(String p_125720_, String p_125721_, TextureSlot ... p_125722_) {
        return new ModelTemplate(Optional.of(new ResourceLocation("minecraft", "block/" + p_125720_)), Optional.of(p_125721_), p_125722_);
    }
}

