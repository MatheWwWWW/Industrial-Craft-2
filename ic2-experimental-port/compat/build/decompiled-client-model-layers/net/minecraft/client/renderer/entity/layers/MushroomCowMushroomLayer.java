/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.level.block.state.BlockState;

public class MushroomCowMushroomLayer<T extends MushroomCow>
extends RenderLayer<T, CowModel<T>> {
    private final BlockRenderDispatcher f_234848_;

    public MushroomCowMushroomLayer(RenderLayerParent<T, CowModel<T>> p_234850_, BlockRenderDispatcher p_234851_) {
        super(p_234850_);
        this.f_234848_ = p_234851_;
    }

    @Override
    public void m_6494_(PoseStack p_117256_, MultiBufferSource p_117257_, int p_117258_, T p_117259_, float p_117260_, float p_117261_, float p_117262_, float p_117263_, float p_117264_, float p_117265_) {
        boolean $$11;
        if (((AgeableMob)p_117259_).m_6162_()) {
            return;
        }
        Minecraft $$10 = Minecraft.m_91087_();
        boolean bl = $$11 = $$10.m_91314_((Entity)p_117259_) && ((Entity)p_117259_).m_20145_();
        if (((Entity)p_117259_).m_20145_() && !$$11) {
            return;
        }
        BlockState $$12 = ((MushroomCow)p_117259_).m_28955_().m_28969_();
        int $$13 = LivingEntityRenderer.m_115338_(p_117259_, 0.0f);
        BakedModel $$14 = this.f_234848_.m_110910_($$12);
        p_117256_.m_85836_();
        p_117256_.m_85837_(0.2f, -0.35f, 0.5);
        p_117256_.m_85845_(Vector3f.f_122225_.m_122240_(-48.0f));
        p_117256_.m_85841_(-1.0f, -1.0f, 1.0f);
        p_117256_.m_85837_(-0.5, -0.5, -0.5);
        this.m_234852_(p_117256_, p_117257_, p_117258_, $$11, $$12, $$13, $$14);
        p_117256_.m_85849_();
        p_117256_.m_85836_();
        p_117256_.m_85837_(0.2f, -0.35f, 0.5);
        p_117256_.m_85845_(Vector3f.f_122225_.m_122240_(42.0f));
        p_117256_.m_85837_(0.1f, 0.0, -0.6f);
        p_117256_.m_85845_(Vector3f.f_122225_.m_122240_(-48.0f));
        p_117256_.m_85841_(-1.0f, -1.0f, 1.0f);
        p_117256_.m_85837_(-0.5, -0.5, -0.5);
        this.m_234852_(p_117256_, p_117257_, p_117258_, $$11, $$12, $$13, $$14);
        p_117256_.m_85849_();
        p_117256_.m_85836_();
        ((CowModel)this.m_117386_()).m_102450_().m_104299_(p_117256_);
        p_117256_.m_85837_(0.0, -0.7f, -0.2f);
        p_117256_.m_85845_(Vector3f.f_122225_.m_122240_(-78.0f));
        p_117256_.m_85841_(-1.0f, -1.0f, 1.0f);
        p_117256_.m_85837_(-0.5, -0.5, -0.5);
        this.m_234852_(p_117256_, p_117257_, p_117258_, $$11, $$12, $$13, $$14);
        p_117256_.m_85849_();
    }

    private void m_234852_(PoseStack p_234853_, MultiBufferSource p_234854_, int p_234855_, boolean p_234856_, BlockState p_234857_, int p_234858_, BakedModel p_234859_) {
        if (p_234856_) {
            this.f_234848_.m_110937_().m_111067_(p_234853_.m_85850_(), p_234854_.m_6299_(RenderType.m_110491_(TextureAtlas.f_118259_)), p_234857_, p_234859_, 0.0f, 0.0f, 0.0f, p_234855_, p_234858_);
        } else {
            this.f_234848_.m_110912_(p_234857_, p_234853_, p_234854_, p_234855_, p_234858_);
        }
    }
}

