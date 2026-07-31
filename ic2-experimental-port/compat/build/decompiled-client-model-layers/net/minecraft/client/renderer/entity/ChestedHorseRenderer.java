/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.model.ChestedHorseModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;

public class ChestedHorseRenderer<T extends AbstractChestedHorse>
extends AbstractHorseRenderer<T, ChestedHorseModel<T>> {
    private static final Map<EntityType<?>, ResourceLocation> f_113979_ = Maps.newHashMap((Map)ImmutableMap.of(EntityType.f_20560_, (Object)new ResourceLocation("textures/entity/horse/donkey.png"), EntityType.f_20503_, (Object)new ResourceLocation("textures/entity/horse/mule.png")));

    public ChestedHorseRenderer(EntityRendererProvider.Context p_173948_, float p_173949_, ModelLayerLocation p_173950_) {
        super(p_173948_, new ChestedHorseModel(p_173948_.m_174023_(p_173950_)), p_173949_);
    }

    @Override
    public ResourceLocation m_5478_(T p_113987_) {
        return f_113979_.get(((Entity)p_113987_).m_6095_());
    }
}

