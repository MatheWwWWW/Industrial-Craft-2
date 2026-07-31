/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;

public class CampfireRenderer
implements BlockEntityRenderer<CampfireBlockEntity> {
    private static final float f_173600_ = 0.375f;
    private final ItemRenderer f_234448_;

    public CampfireRenderer(BlockEntityRendererProvider.Context p_173602_) {
        this.f_234448_ = p_173602_.m_234447_();
    }

    @Override
    public void m_6922_(CampfireBlockEntity p_112344_, float p_112345_, PoseStack p_112346_, MultiBufferSource p_112347_, int p_112348_, int p_112349_) {
        Direction $$6 = p_112344_.m_58900_().m_61143_(CampfireBlock.f_51230_);
        NonNullList<ItemStack> $$7 = p_112344_.m_59065_();
        int $$8 = (int)p_112344_.m_58899_().m_121878_();
        for (int $$9 = 0; $$9 < $$7.size(); ++$$9) {
            ItemStack $$10 = $$7.get($$9);
            if ($$10 == ItemStack.f_41583_) continue;
            p_112346_.m_85836_();
            p_112346_.m_85837_(0.5, 0.44921875, 0.5);
            Direction $$11 = Direction.m_122407_(($$9 + $$6.m_122416_()) % 4);
            float $$12 = -$$11.m_122435_();
            p_112346_.m_85845_(Vector3f.f_122225_.m_122240_($$12));
            p_112346_.m_85845_(Vector3f.f_122223_.m_122240_(90.0f));
            p_112346_.m_85837_(-0.3125, -0.3125, 0.0);
            p_112346_.m_85841_(0.375f, 0.375f, 0.375f);
            this.f_234448_.m_174269_($$10, ItemTransforms.TransformType.FIXED, p_112348_, p_112349_, p_112346_, p_112347_, $$8 + $$9);
            p_112346_.m_85849_();
        }
    }
}

