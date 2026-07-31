/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;

public class SpawnerRenderer
implements BlockEntityRenderer<SpawnerBlockEntity> {
    private final EntityRenderDispatcher f_234449_;

    public SpawnerRenderer(BlockEntityRendererProvider.Context p_173673_) {
        this.f_234449_ = p_173673_.m_234446_();
    }

    @Override
    public void m_6922_(SpawnerBlockEntity p_112563_, float p_112564_, PoseStack p_112565_, MultiBufferSource p_112566_, int p_112567_, int p_112568_) {
        p_112565_.m_85836_();
        p_112565_.m_85837_(0.5, 0.0, 0.5);
        BaseSpawner $$6 = p_112563_.m_59801_();
        Entity $$7 = $$6.m_151314_(p_112563_.m_58904_());
        if ($$7 != null) {
            float $$8 = 0.53125f;
            float $$9 = Math.max($$7.m_20205_(), $$7.m_20206_());
            if ((double)$$9 > 1.0) {
                $$8 /= $$9;
            }
            p_112565_.m_85837_(0.0, 0.4f, 0.0);
            p_112565_.m_85845_(Vector3f.f_122225_.m_122240_((float)Mth.m_14139_(p_112564_, $$6.m_45474_(), $$6.m_45473_()) * 10.0f));
            p_112565_.m_85837_(0.0, -0.2f, 0.0);
            p_112565_.m_85845_(Vector3f.f_122223_.m_122240_(-30.0f));
            p_112565_.m_85841_($$8, $$8, $$8);
            this.f_234449_.m_114384_($$7, 0.0, 0.0, 0.0, 0.0f, p_112564_, p_112565_, p_112566_, p_112567_);
        }
        p_112565_.m_85849_();
    }
}

