/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public interface BlockEntityRenderer<T extends BlockEntity> {
    public void m_6922_(T var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6);

    default public boolean m_5932_(T p_112306_) {
        return false;
    }

    default public int m_142163_() {
        return 64;
    }

    default public boolean m_142756_(T p_173568_, Vec3 p_173569_) {
        return Vec3.m_82512_(((BlockEntity)p_173568_).m_58899_()).m_82509_(p_173569_, this.m_142163_());
    }
}

