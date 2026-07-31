/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.Maps
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture$Type
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.blockentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.dragon.DragonHeadModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.Direction;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SkullBlockRenderer
implements BlockEntityRenderer<SkullBlockEntity> {
    private final Map<SkullBlock.Type, SkullModelBase> f_173658_;
    private static final Map<SkullBlock.Type, ResourceLocation> f_112519_ = Util.m_137469_(Maps.newHashMap(), p_112552_ -> {
        p_112552_.put(SkullBlock.Types.SKELETON, new ResourceLocation("textures/entity/skeleton/skeleton.png"));
        p_112552_.put(SkullBlock.Types.WITHER_SKELETON, new ResourceLocation("textures/entity/skeleton/wither_skeleton.png"));
        p_112552_.put(SkullBlock.Types.ZOMBIE, new ResourceLocation("textures/entity/zombie/zombie.png"));
        p_112552_.put(SkullBlock.Types.CREEPER, new ResourceLocation("textures/entity/creeper/creeper.png"));
        p_112552_.put(SkullBlock.Types.DRAGON, new ResourceLocation("textures/entity/enderdragon/dragon.png"));
        p_112552_.put(SkullBlock.Types.PLAYER, DefaultPlayerSkin.m_118626_());
    });

    public static Map<SkullBlock.Type, SkullModelBase> m_173661_(EntityModelSet p_173662_) {
        ImmutableMap.Builder $$1 = ImmutableMap.builder();
        $$1.put((Object)SkullBlock.Types.SKELETON, (Object)new SkullModel(p_173662_.m_171103_(ModelLayers.f_171240_)));
        $$1.put((Object)SkullBlock.Types.WITHER_SKELETON, (Object)new SkullModel(p_173662_.m_171103_(ModelLayers.f_171219_)));
        $$1.put((Object)SkullBlock.Types.PLAYER, (Object)new SkullModel(p_173662_.m_171103_(ModelLayers.f_171163_)));
        $$1.put((Object)SkullBlock.Types.ZOMBIE, (Object)new SkullModel(p_173662_.m_171103_(ModelLayers.f_171224_)));
        $$1.put((Object)SkullBlock.Types.CREEPER, (Object)new SkullModel(p_173662_.m_171103_(ModelLayers.f_171130_)));
        $$1.put((Object)SkullBlock.Types.DRAGON, (Object)new DragonHeadModel(p_173662_.m_171103_(ModelLayers.f_171135_)));
        return $$1.build();
    }

    public SkullBlockRenderer(BlockEntityRendererProvider.Context p_173660_) {
        this.f_173658_ = SkullBlockRenderer.m_173661_(p_173660_.m_173585_());
    }

    @Override
    public void m_6922_(SkullBlockEntity p_112534_, float p_112535_, PoseStack p_112536_, MultiBufferSource p_112537_, int p_112538_, int p_112539_) {
        float $$6 = p_112534_.m_59762_(p_112535_);
        BlockState $$7 = p_112534_.m_58900_();
        boolean $$8 = $$7.m_60734_() instanceof WallSkullBlock;
        Direction $$9 = $$8 ? $$7.m_61143_(WallSkullBlock.f_58097_) : null;
        float $$10 = 22.5f * (float)($$8 ? (2 + $$9.m_122416_()) * 4 : $$7.m_61143_(SkullBlock.f_56314_));
        SkullBlock.Type $$11 = ((AbstractSkullBlock)$$7.m_60734_()).m_48754_();
        SkullModelBase $$12 = this.f_173658_.get($$11);
        RenderType $$13 = SkullBlockRenderer.m_112523_($$11, p_112534_.m_59779_());
        SkullBlockRenderer.m_173663_($$9, $$10, $$6, p_112536_, p_112537_, p_112538_, $$12, $$13);
    }

    public static void m_173663_(@Nullable Direction p_173664_, float p_173665_, float p_173666_, PoseStack p_173667_, MultiBufferSource p_173668_, int p_173669_, SkullModelBase p_173670_, RenderType p_173671_) {
        p_173667_.m_85836_();
        if (p_173664_ == null) {
            p_173667_.m_85837_(0.5, 0.0, 0.5);
        } else {
            float $$8 = 0.25f;
            p_173667_.m_85837_(0.5f - (float)p_173664_.m_122429_() * 0.25f, 0.25, 0.5f - (float)p_173664_.m_122431_() * 0.25f);
        }
        p_173667_.m_85841_(-1.0f, -1.0f, 1.0f);
        VertexConsumer $$9 = p_173668_.m_6299_(p_173671_);
        p_173670_.m_6251_(p_173666_, p_173665_, 0.0f);
        p_173670_.m_7695_(p_173667_, $$9, p_173669_, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
        p_173667_.m_85849_();
    }

    public static RenderType m_112523_(SkullBlock.Type p_112524_, @Nullable GameProfile p_112525_) {
        ResourceLocation $$2 = f_112519_.get(p_112524_);
        if (p_112524_ != SkullBlock.Types.PLAYER || p_112525_ == null) {
            return RenderType.m_110464_($$2);
        }
        Minecraft $$3 = Minecraft.m_91087_();
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> $$4 = $$3.m_91109_().m_118815_(p_112525_);
        if ($$4.containsKey(MinecraftProfileTexture.Type.SKIN)) {
            return RenderType.m_110473_($$3.m_91109_().m_118825_($$4.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN));
        }
        return RenderType.m_110458_(DefaultPlayerSkin.m_118627_(UUIDUtil.m_235875_(p_112525_)));
    }
}

