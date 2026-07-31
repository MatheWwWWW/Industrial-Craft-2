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
import net.minecraft.world.inventory.ShulkerBoxMenu;

public class ShulkerBoxScreen
extends AbstractContainerScreen<ShulkerBoxMenu> {
    private static final ResourceLocation f_99237_ = new ResourceLocation("textures/gui/container/shulker_box.png");

    public ShulkerBoxScreen(ShulkerBoxMenu p_99240_, Inventory p_99241_, Component p_99242_) {
        super(p_99240_, p_99241_, p_99242_);
        ++this.f_97727_;
    }

    @Override
    public void m_6305_(PoseStack p_99249_, int p_99250_, int p_99251_, float p_99252_) {
        this.m_7333_(p_99249_);
        super.m_6305_(p_99249_, p_99250_, p_99251_, p_99252_);
        this.m_7025_(p_99249_, p_99250_, p_99251_);
    }

    @Override
    protected void m_7286_(PoseStack p_99244_, float p_99245_, int p_99246_, int p_99247_) {
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, f_99237_);
        int $$4 = (this.f_96543_ - this.f_97726_) / 2;
        int $$5 = (this.f_96544_ - this.f_97727_) / 2;
        this.m_93228_(p_99244_, $$4, $$5, 0, 0, this.f_97726_, this.f_97727_);
    }
}

