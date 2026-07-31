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
import net.minecraft.world.inventory.DispenserMenu;

public class DispenserScreen
extends AbstractContainerScreen<DispenserMenu> {
    private static final ResourceLocation f_98682_ = new ResourceLocation("textures/gui/container/dispenser.png");

    public DispenserScreen(DispenserMenu p_98685_, Inventory p_98686_, Component p_98687_) {
        super(p_98685_, p_98686_, p_98687_);
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        this.f_97728_ = (this.f_97726_ - this.f_96547_.m_92852_(this.f_96539_)) / 2;
    }

    @Override
    public void m_6305_(PoseStack p_98694_, int p_98695_, int p_98696_, float p_98697_) {
        this.m_7333_(p_98694_);
        super.m_6305_(p_98694_, p_98695_, p_98696_, p_98697_);
        this.m_7025_(p_98694_, p_98695_, p_98696_);
    }

    @Override
    protected void m_7286_(PoseStack p_98689_, float p_98690_, int p_98691_, int p_98692_) {
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, f_98682_);
        int $$4 = (this.f_96543_ - this.f_97726_) / 2;
        int $$5 = (this.f_96544_ - this.f_97727_) / 2;
        this.m_93228_(p_98689_, $$4, $$5, 0, 0, this.f_97726_, this.f_97727_);
    }
}

