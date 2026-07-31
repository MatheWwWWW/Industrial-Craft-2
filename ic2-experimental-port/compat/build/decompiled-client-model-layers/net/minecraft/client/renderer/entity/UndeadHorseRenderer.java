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
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

public class UndeadHorseRenderer
extends AbstractHorseRenderer<AbstractHorse, HorseModel<AbstractHorse>> {
    private static final Map<EntityType<?>, ResourceLocation> f_116267_ = Maps.newHashMap((Map)ImmutableMap.of(EntityType.f_20502_, (Object)new ResourceLocation("textures/entity/horse/horse_zombie.png"), EntityType.f_20525_, (Object)new ResourceLocation("textures/entity/horse/horse_skeleton.png")));

    public UndeadHorseRenderer(EntityRendererProvider.Context p_174432_, ModelLayerLocation p_174433_) {
        super(p_174432_, new HorseModel(p_174432_.m_174023_(p_174433_)), 1.0f);
    }

    @Override
    public ResourceLocation m_5478_(AbstractHorse p_116274_) {
        return f_116267_.get(p_116274_.m_6095_());
    }
}

