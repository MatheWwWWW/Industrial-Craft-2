/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.tutorial;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.Input;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public interface TutorialStepInstance {
    default public void m_7736_() {
    }

    default public void m_7737_() {
    }

    default public void m_6484_(Input p_120623_) {
    }

    default public void m_6420_(double p_120614_, double p_120615_) {
    }

    default public void m_7554_(ClientLevel p_120617_, HitResult p_120618_) {
    }

    default public void m_7464_(ClientLevel p_120619_, BlockPos p_120620_, BlockState p_120621_, float p_120622_) {
    }

    default public void m_7744_() {
    }

    default public void m_6967_(ItemStack p_120616_) {
    }
}

