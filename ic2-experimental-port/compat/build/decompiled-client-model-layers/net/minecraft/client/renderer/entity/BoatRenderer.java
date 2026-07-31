/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;

public class BoatRenderer
extends EntityRenderer<Boat> {
    private final Map<Boat.Type, Pair<ResourceLocation, BoatModel>> f_173934_;

    public BoatRenderer(EntityRendererProvider.Context p_234563_, boolean p_234564_) {
        super(p_234563_);
        this.f_114477_ = 0.8f;
        this.f_173934_ = (Map)Stream.of(Boat.Type.values()).collect(ImmutableMap.toImmutableMap(p_173938_ -> p_173938_, p_234575_ -> Pair.of((Object)new ResourceLocation(BoatRenderer.m_234565_(p_234575_, p_234564_)), (Object)this.m_234568_(p_234563_, (Boat.Type)((Object)p_234575_), p_234564_))));
    }

    private BoatModel m_234568_(EntityRendererProvider.Context p_234569_, Boat.Type p_234570_, boolean p_234571_) {
        ModelLayerLocation $$3 = p_234571_ ? ModelLayers.m_233550_(p_234570_) : ModelLayers.m_171289_(p_234570_);
        return new BoatModel(p_234569_.m_174023_($$3), p_234571_);
    }

    private static String m_234565_(Boat.Type p_234566_, boolean p_234567_) {
        if (p_234567_) {
            return "textures/entity/chest_boat/" + p_234566_.m_38429_() + ".png";
        }
        return "textures/entity/boat/" + p_234566_.m_38429_() + ".png";
    }

    @Override
    public void m_7392_(Boat p_113929_, float p_113930_, float p_113931_, PoseStack p_113932_, MultiBufferSource p_113933_, int p_113934_) {
        float $$8;
        p_113932_.m_85836_();
        p_113932_.m_85837_(0.0, 0.375, 0.0);
        p_113932_.m_85845_(Vector3f.f_122225_.m_122240_(180.0f - p_113930_));
        float $$6 = (float)p_113929_.m_38385_() - p_113931_;
        float $$7 = p_113929_.m_38384_() - p_113931_;
        if ($$7 < 0.0f) {
            $$7 = 0.0f;
        }
        if ($$6 > 0.0f) {
            p_113932_.m_85845_(Vector3f.f_122223_.m_122240_(Mth.m_14031_($$6) * $$6 * $$7 / 10.0f * (float)p_113929_.m_38386_()));
        }
        if (!Mth.m_14033_($$8 = p_113929_.m_38352_(p_113931_), 0.0f)) {
            p_113932_.m_85845_(new Quaternion(new Vector3f(1.0f, 0.0f, 1.0f), p_113929_.m_38352_(p_113931_), true));
        }
        Pair<ResourceLocation, BoatModel> $$9 = this.f_173934_.get((Object)p_113929_.m_38387_());
        ResourceLocation $$10 = (ResourceLocation)$$9.getFirst();
        BoatModel $$11 = (BoatModel)$$9.getSecond();
        p_113932_.m_85841_(-1.0f, -1.0f, 1.0f);
        p_113932_.m_85845_(Vector3f.f_122225_.m_122240_(90.0f));
        $$11.m_6973_(p_113929_, p_113931_, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer $$12 = p_113933_.m_6299_($$11.m_103119_($$10));
        $$11.m_7695_(p_113932_, $$12, p_113934_, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
        if (!p_113929_.m_5842_()) {
            VertexConsumer $$13 = p_113933_.m_6299_(RenderType.m_110478_());
            $$11.m_102282_().m_104301_(p_113932_, $$13, p_113934_, OverlayTexture.f_118083_);
        }
        p_113932_.m_85849_();
        super.m_7392_(p_113929_, p_113930_, p_113931_, p_113932_, p_113933_, p_113934_);
    }

    @Override
    public ResourceLocation m_5478_(Boat p_113927_) {
        return (ResourceLocation)this.f_173934_.get((Object)p_113927_.m_38387_()).getFirst();
    }
}

