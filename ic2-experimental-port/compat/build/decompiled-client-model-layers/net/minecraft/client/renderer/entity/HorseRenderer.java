/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.client.renderer.entity.layers.HorseMarkingLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Variant;

public final class HorseRenderer
extends AbstractHorseRenderer<Horse, HorseModel<Horse>> {
    private static final Map<Variant, ResourceLocation> f_114865_ = Util.m_137469_(Maps.newEnumMap(Variant.class), p_114874_ -> {
        p_114874_.put(Variant.WHITE, new ResourceLocation("textures/entity/horse/horse_white.png"));
        p_114874_.put(Variant.CREAMY, new ResourceLocation("textures/entity/horse/horse_creamy.png"));
        p_114874_.put(Variant.CHESTNUT, new ResourceLocation("textures/entity/horse/horse_chestnut.png"));
        p_114874_.put(Variant.BROWN, new ResourceLocation("textures/entity/horse/horse_brown.png"));
        p_114874_.put(Variant.BLACK, new ResourceLocation("textures/entity/horse/horse_black.png"));
        p_114874_.put(Variant.GRAY, new ResourceLocation("textures/entity/horse/horse_gray.png"));
        p_114874_.put(Variant.DARKBROWN, new ResourceLocation("textures/entity/horse/horse_darkbrown.png"));
    });

    public HorseRenderer(EntityRendererProvider.Context p_174167_) {
        super(p_174167_, new HorseModel(p_174167_.m_174023_(ModelLayers.f_171186_)), 1.1f);
        this.m_115326_(new HorseMarkingLayer(this));
        this.m_115326_(new HorseArmorLayer(this, p_174167_.m_174027_()));
    }

    @Override
    public ResourceLocation m_5478_(Horse p_114872_) {
        return f_114865_.get((Object)p_114872_.m_30723_());
    }
}

