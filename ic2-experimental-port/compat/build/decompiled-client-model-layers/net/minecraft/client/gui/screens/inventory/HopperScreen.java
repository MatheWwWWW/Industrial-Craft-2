/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.HopperMenu;

public class HopperScreen
extends AbstractContainerScreen<HopperMenu> {
    private static final ResourceLocation f_98795_ = new ResourceLocation("textures/gui/container/hopper.png");

    public HopperScreen(HopperMenu p_98798_, Inventory p_98799_, Component p_98800_) {
        super(p_98798_, p_98799_, p_98800_);
        this.f_96546_ = false;
        this.f_97727_ = 133;
        this.f_97731_ = this.f_97727_ - 94;
    }

    @Override
    public void m_6305_(PoseStack p_98807_, int p_98808_, int p_98809_, float p_98810_) {
        this.m_7333_(p_98807_);
        super.m_6305_(p_98807_, p_98808_, p_98809_, p_98810_);
        this.m_7025_(p_98807_, p_98808_, p_98809_);
    }

    @Override
    protected void m_7286_(PoseStack p_98802_, float p_98803_, int p_98804_, int p_98805_) {
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, f_98795_);
        int $$4 = (this.f_96543_ - this.f_97726_) / 2;
        int $$5 = (this.f_96544_ - this.f_97727_) / 2;
        this.m_93228_(p_98802_, $$4, $$5, 0, 0, this.f_97726_, this.f_97727_);
    }
}

