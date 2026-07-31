/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.RenderLayerParent
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.LivingEntity
 */
package ic2.core.entity.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.items.armor.IEnergyShieldArmor;
import ic2.core.platform.rendering.misc.IC2RenderTypes;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class EnergyShieldLayer<T extends LivingEntity, M extends HumanoidModel<T>>
extends RenderLayer<T, M> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/models/misc/electric_shield_texture.png");
    static final float SCALE = 1.05f;
    static final float TRANSLATION = -0.034f;
    HumanoidModel<T> model;

    public EnergyShieldLayer(RenderLayerParent<T, M> entityRendererIn, HumanoidModel<T> model) {
        super(entityRendererIn);
        this.model = model;
    }

    public void render(PoseStack matrix, MultiBufferSource bufferIn, int packedLightIn, T entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        int time;
        if (!IEnergyShieldArmor.addsEnergyShieldEffect(entityIn)) {
            return;
        }
        boolean always = IEnergyShieldArmor.shouldAlwaysShowEffect(entityIn);
        if (((LivingEntity)entityIn).f_20916_ == 0 && !always) {
            return;
        }
        float f = (float)((LivingEntity)entityIn).f_19797_ * 0.015f;
        int n = time = always ? 0 : Math.abs(((LivingEntity)entityIn).f_20916_);
        float visibility = time == 0 ? 1.0f : (time >= 8 ? 1.0f : (float)time / 7.0f);
        float r = 0.245f * visibility;
        float g = 0.708f * visibility;
        float b = 0.803f * visibility;
        ((HumanoidModel)this.m_117386_()).m_102872_(this.model);
        this.model.m_6839_(entityIn, limbSwing, limbSwingAmount, partialTicks);
        this.model.m_6973_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        packedLightIn = 0x400040;
        for (int i = 0; i < 3; ++i) {
            RenderType type = IC2RenderTypes.getShieldType(TEXTURE, 2.0f, i, f);
            this.model.m_7695_(matrix, bufferIn.m_6299_(type), packedLightIn, OverlayTexture.f_118083_, r, g, b, 1.0f);
            if (!(bufferIn instanceof MultiBufferSource.BufferSource)) continue;
            MultiBufferSource.BufferSource bufferInfo = (MultiBufferSource.BufferSource)bufferIn;
            RenderSystem.m_157429_((float)r, (float)g, (float)b, (float)1.0f);
            bufferInfo.m_109912_(type);
            RenderSystem.m_157429_((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }
}

