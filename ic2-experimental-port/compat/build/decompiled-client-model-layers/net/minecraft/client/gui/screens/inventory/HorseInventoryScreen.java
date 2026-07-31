/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.HorseInventoryMenu;

public class HorseInventoryScreen
extends AbstractContainerScreen<HorseInventoryMenu> {
    private static final ResourceLocation f_98811_ = new ResourceLocation("textures/gui/container/horse.png");
    private final AbstractHorse f_98812_;
    private float f_98813_;
    private float f_98814_;

    public HorseInventoryScreen(HorseInventoryMenu p_98817_, Inventory p_98818_, AbstractHorse p_98819_) {
        super(p_98817_, p_98818_, p_98819_.m_5446_());
        this.f_98812_ = p_98819_;
        this.f_96546_ = false;
    }

    @Override
    protected void m_7286_(PoseStack p_98821_, float p_98822_, int p_98823_, int p_98824_) {
        AbstractChestedHorse $$6;
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, f_98811_);
        int $$4 = (this.f_96543_ - this.f_97726_) / 2;
        int $$5 = (this.f_96544_ - this.f_97727_) / 2;
        this.m_93228_(p_98821_, $$4, $$5, 0, 0, this.f_97726_, this.f_97727_);
        if (this.f_98812_ instanceof AbstractChestedHorse && ($$6 = (AbstractChestedHorse)this.f_98812_).m_30502_()) {
            this.m_93228_(p_98821_, $$4 + 79, $$5 + 17, 0, this.f_97727_, $$6.m_7488_() * 18, 54);
        }
        if (this.f_98812_.m_6741_()) {
            this.m_93228_(p_98821_, $$4 + 7, $$5 + 35 - 18, 18, this.f_97727_ + 54, 18, 18);
        }
        if (this.f_98812_.m_7482_()) {
            if (this.f_98812_ instanceof Llama) {
                this.m_93228_(p_98821_, $$4 + 7, $$5 + 35, 36, this.f_97727_ + 54, 18, 18);
            } else {
                this.m_93228_(p_98821_, $$4 + 7, $$5 + 35, 0, this.f_97727_ + 54, 18, 18);
            }
        }
        InventoryScreen.m_98850_($$4 + 51, $$5 + 60, 17, (float)($$4 + 51) - this.f_98813_, (float)($$5 + 75 - 50) - this.f_98814_, this.f_98812_);
    }

    @Override
    public void m_6305_(PoseStack p_98826_, int p_98827_, int p_98828_, float p_98829_) {
        this.m_7333_(p_98826_);
        this.f_98813_ = p_98827_;
        this.f_98814_ = p_98828_;
        super.m_6305_(p_98826_, p_98827_, p_98828_, p_98829_);
        this.m_7025_(p_98826_, p_98827_, p_98828_);
    }
}

