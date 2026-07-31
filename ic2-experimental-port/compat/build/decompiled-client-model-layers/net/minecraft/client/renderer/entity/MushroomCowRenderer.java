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
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.MushroomCowMushroomLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.MushroomCow;

public class MushroomCowRenderer
extends MobRenderer<MushroomCow, CowModel<MushroomCow>> {
    private static final Map<MushroomCow.MushroomType, ResourceLocation> f_115507_ = Util.m_137469_(Maps.newHashMap(), p_115516_ -> {
        p_115516_.put(MushroomCow.MushroomType.BROWN, new ResourceLocation("textures/entity/cow/brown_mooshroom.png"));
        p_115516_.put(MushroomCow.MushroomType.RED, new ResourceLocation("textures/entity/cow/red_mooshroom.png"));
    });

    public MushroomCowRenderer(EntityRendererProvider.Context p_174324_) {
        super(p_174324_, new CowModel(p_174324_.m_174023_(ModelLayers.f_171199_)), 0.7f);
        this.m_115326_(new MushroomCowMushroomLayer<MushroomCow>(this, p_174324_.m_234597_()));
    }

    @Override
    public ResourceLocation m_5478_(MushroomCow p_115514_) {
        return f_115507_.get((Object)p_115514_.m_28955_());
    }
}

