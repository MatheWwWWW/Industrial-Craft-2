/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;

public class ContainerScreen
extends AbstractContainerScreen<ChestMenu>
implements MenuAccess<ChestMenu> {
    private static final ResourceLocation f_98405_ = new ResourceLocation("textures/gui/container/generic_54.png");
    private final int f_98406_;

    public ContainerScreen(ChestMenu p_98409_, Inventory p_98410_, Component p_98411_) {
        super(p_98409_, p_98410_, p_98411_);
        this.f_96546_ = false;
        int $$3 = 222;
        int $$4 = 114;
        this.f_98406_ = p_98409_.m_39265_();
        this.f_97727_ = 114 + this.f_98406_ * 18;
        this.f_97731_ = this.f_97727_ - 94;
    }

    @Override
    public void m_6305_(PoseStack p_98418_, int p_98419_, int p_98420_, float p_98421_) {
        this.m_7333_(p_98418_);
        super.m_6305_(p_98418_, p_98419_, p_98420_, p_98421_);
        this.m_7025_(p_98418_, p_98419_, p_98420_);
    }

    @Override
    protected void m_7286_(PoseStack p_98413_, float p_98414_, int p_98415_, int p_98416_) {
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, f_98405_);
        int $$4 = (this.f_96543_ - this.f_97726_) / 2;
        int $$5 = (this.f_96544_ - this.f_97727_) / 2;
        this.m_93228_(p_98413_, $$4, $$5, 0, 0, this.f_97726_, this.f_98406_ * 18 + 17);
        this.m_93228_(p_98413_, $$4, $$5 + this.f_98406_ * 18 + 17, 0, 126, this.f_97726_, 96);
    }
}

