/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.math.Quaternion
 *  com.mojang.math.Vector3f
 *  net.minecraft.client.model.BoatModel
 *  net.minecraft.client.model.geom.ModelLayerLocation
 *  net.minecraft.client.model.geom.ModelLayers
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.vehicle.Boat
 *  net.minecraft.world.entity.vehicle.Boat$Type
 */
package ic2.core.entity.render;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import ic2.api.entity.boat.AbstractBoatEntity;
import ic2.api.entity.boat.BoatType;
import java.util.Map;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;

public class BoatEntityRenderer
extends EntityRenderer<AbstractBoatEntity> {
    private final Map<BoatType, Pair<ResourceLocation, BoatModel>> texturesAndModels;

    public BoatEntityRenderer(EntityRendererProvider.Context context, boolean bl, String string) {
        super(context);
        this.f_114477_ = 0.8f;
        this.texturesAndModels = (Map)BoatType.stream().collect(ImmutableMap.toImmutableMap(boatType -> boatType, boatType -> Pair.of((Object)new ResourceLocation(string, this.getTexture((BoatType)boatType, bl)), (Object)this.createModel(context, bl))));
    }

    private BoatModel createModel(EntityRendererProvider.Context context, boolean bl) {
        ModelLayerLocation modelLayerLocation = bl ? ModelLayers.m_233550_((Boat.Type)Boat.Type.OAK) : ModelLayers.m_171289_((Boat.Type)Boat.Type.OAK);
        return new BoatModel(context.m_174023_(modelLayerLocation), bl);
    }

    public void render(AbstractBoatEntity abstractBoatEntity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        poseStack.m_85836_();
        poseStack.m_85837_(0.0, 0.375, 0.0);
        poseStack.m_85845_(Vector3f.f_122225_.m_122240_(180.0f - f));
        float f3 = (float)abstractBoatEntity.m_38385_() - f2;
        float f4 = abstractBoatEntity.m_38384_() - f2;
        if (f4 < 0.0f) {
            f4 = 0.0f;
        }
        if (f3 > 0.0f) {
            poseStack.m_85845_(Vector3f.f_122223_.m_122240_(Mth.m_14031_((float)f3) * f3 * f4 / 10.0f * (float)abstractBoatEntity.m_38386_()));
        }
        if (!Mth.m_14033_((float)abstractBoatEntity.m_38352_(f2), (float)0.0f)) {
            poseStack.m_85845_(new Quaternion(new Vector3f(1.0f, 0.0f, 1.0f), abstractBoatEntity.m_38352_(f2), true));
        }
        Pair<ResourceLocation, BoatModel> pair = this.texturesAndModels.get(abstractBoatEntity.getOverrideBoatType());
        ResourceLocation resourceLocation = (ResourceLocation)pair.getFirst();
        BoatModel boatModel = (BoatModel)pair.getSecond();
        poseStack.m_85841_(-1.0f, -1.0f, 1.0f);
        poseStack.m_85845_(Vector3f.f_122225_.m_122240_(90.0f));
        boatModel.m_6973_((Boat)abstractBoatEntity, f2, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = multiBufferSource.m_6299_(boatModel.m_103119_(resourceLocation));
        boatModel.m_7695_(poseStack, vertexConsumer, n, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
        if (!abstractBoatEntity.m_5842_()) {
            VertexConsumer vertexConsumer2 = multiBufferSource.m_6299_(RenderType.m_110478_());
            boatModel.m_102282_().m_104301_(poseStack, vertexConsumer2, n, OverlayTexture.f_118083_);
        }
        poseStack.m_85849_();
        super.m_7392_((Entity)abstractBoatEntity, f, f2, poseStack, multiBufferSource, n);
    }

    protected String getTexture(BoatType boatType, boolean bl) {
        if (bl) {
            return "textures/entity/chest_boat/" + boatType.getName() + ".png";
        }
        return "textures/entity/boat/" + boatType.getName() + ".png";
    }

    public ResourceLocation getTexture(AbstractBoatEntity abstractBoatEntity) {
        return (ResourceLocation)this.texturesAndModels.get(abstractBoatEntity.getOverrideBoatType()).getFirst();
    }
}

