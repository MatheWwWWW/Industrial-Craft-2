/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SnowGolemHeadLayer
extends RenderLayer<SnowGolem, SnowGolemModel<SnowGolem>> {
    private final BlockRenderDispatcher f_234868_;
    private final ItemRenderer f_234869_;

    public SnowGolemHeadLayer(RenderLayerParent<SnowGolem, SnowGolemModel<SnowGolem>> p_234871_, BlockRenderDispatcher p_234872_, ItemRenderer p_234873_) {
        super(p_234871_);
        this.f_234868_ = p_234872_;
        this.f_234869_ = p_234873_;
    }

    @Override
    public void m_6494_(PoseStack p_117494_, MultiBufferSource p_117495_, int p_117496_, SnowGolem p_117497_, float p_117498_, float p_117499_, float p_117500_, float p_117501_, float p_117502_, float p_117503_) {
        boolean $$10;
        if (!p_117497_.m_29930_()) {
            return;
        }
        boolean bl = $$10 = Minecraft.m_91087_().m_91314_(p_117497_) && p_117497_.m_20145_();
        if (p_117497_.m_20145_() && !$$10) {
            return;
        }
        p_117494_.m_85836_();
        ((SnowGolemModel)this.m_117386_()).m_103851_().m_104299_(p_117494_);
        float $$11 = 0.625f;
        p_117494_.m_85837_(0.0, -0.34375, 0.0);
        p_117494_.m_85845_(Vector3f.f_122225_.m_122240_(180.0f));
        p_117494_.m_85841_(0.625f, -0.625f, -0.625f);
        ItemStack $$12 = new ItemStack(Blocks.f_50143_);
        if ($$10) {
            BlockState $$13 = Blocks.f_50143_.m_49966_();
            BakedModel $$14 = this.f_234868_.m_110910_($$13);
            int $$15 = LivingEntityRenderer.m_115338_(p_117497_, 0.0f);
            p_117494_.m_85837_(-0.5, -0.5, -0.5);
            this.f_234868_.m_110937_().m_111067_(p_117494_.m_85850_(), p_117495_.m_6299_(RenderType.m_110491_(TextureAtlas.f_118259_)), $$13, $$14, 0.0f, 0.0f, 0.0f, p_117496_, $$15);
        } else {
            this.f_234869_.m_174242_(p_117497_, $$12, ItemTransforms.TransformType.HEAD, false, p_117494_, p_117495_, p_117497_.f_19853_, p_117496_, LivingEntityRenderer.m_115338_(p_117497_, 0.0f), p_117497_.m_19879_());
        }
        p_117494_.m_85849_();
    }
}

