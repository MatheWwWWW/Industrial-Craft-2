/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.item.crafting.StonecutterRecipe;

public class StonecutterScreen
extends AbstractContainerScreen<StonecutterMenu> {
    private static final ResourceLocation f_99303_ = new ResourceLocation("textures/gui/container/stonecutter.png");
    private static final int f_169826_ = 12;
    private static final int f_169827_ = 15;
    private static final int f_169828_ = 4;
    private static final int f_169829_ = 3;
    private static final int f_169830_ = 16;
    private static final int f_169831_ = 18;
    private static final int f_169832_ = 54;
    private static final int f_169833_ = 52;
    private static final int f_169834_ = 14;
    private float f_99304_;
    private boolean f_99305_;
    private int f_99306_;
    private boolean f_99307_;

    public StonecutterScreen(StonecutterMenu p_99310_, Inventory p_99311_, Component p_99312_) {
        super(p_99310_, p_99311_, p_99312_);
        p_99310_.m_40323_(this::m_99354_);
        --this.f_97729_;
    }

    @Override
    public void m_6305_(PoseStack p_99337_, int p_99338_, int p_99339_, float p_99340_) {
        super.m_6305_(p_99337_, p_99338_, p_99339_, p_99340_);
        this.m_7025_(p_99337_, p_99338_, p_99339_);
    }

    @Override
    protected void m_7286_(PoseStack p_99328_, float p_99329_, int p_99330_, int p_99331_) {
        this.m_7333_(p_99328_);
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, f_99303_);
        int $$4 = this.f_97735_;
        int $$5 = this.f_97736_;
        this.m_93228_(p_99328_, $$4, $$5, 0, 0, this.f_97726_, this.f_97727_);
        int $$6 = (int)(41.0f * this.f_99304_);
        this.m_93228_(p_99328_, $$4 + 119, $$5 + 15 + $$6, 176 + (this.m_99353_() ? 0 : 12), 0, 12, 15);
        int $$7 = this.f_97735_ + 52;
        int $$8 = this.f_97736_ + 14;
        int $$9 = this.f_99306_ + 12;
        this.m_99341_(p_99328_, p_99330_, p_99331_, $$7, $$8, $$9);
        this.m_99348_($$7, $$8, $$9);
    }

    @Override
    protected void m_7025_(PoseStack p_99333_, int p_99334_, int p_99335_) {
        super.m_7025_(p_99333_, p_99334_, p_99335_);
        if (this.f_99307_) {
            int $$3 = this.f_97735_ + 52;
            int $$4 = this.f_97736_ + 14;
            int $$5 = this.f_99306_ + 12;
            List<StonecutterRecipe> $$6 = ((StonecutterMenu)this.f_97732_).m_40339_();
            for (int $$7 = this.f_99306_; $$7 < $$5 && $$7 < ((StonecutterMenu)this.f_97732_).m_40340_(); ++$$7) {
                int $$8 = $$7 - this.f_99306_;
                int $$9 = $$3 + $$8 % 4 * 16;
                int $$10 = $$4 + $$8 / 4 * 18 + 2;
                if (p_99334_ < $$9 || p_99334_ >= $$9 + 16 || p_99335_ < $$10 || p_99335_ >= $$10 + 18) continue;
                this.m_6057_(p_99333_, $$6.get($$7).m_8043_(), p_99334_, p_99335_);
            }
        }
    }

    private void m_99341_(PoseStack p_99342_, int p_99343_, int p_99344_, int p_99345_, int p_99346_, int p_99347_) {
        for (int $$6 = this.f_99306_; $$6 < p_99347_ && $$6 < ((StonecutterMenu)this.f_97732_).m_40340_(); ++$$6) {
            int $$7 = $$6 - this.f_99306_;
            int $$8 = p_99345_ + $$7 % 4 * 16;
            int $$9 = $$7 / 4;
            int $$10 = p_99346_ + $$9 * 18 + 2;
            int $$11 = this.f_97727_;
            if ($$6 == ((StonecutterMenu)this.f_97732_).m_40338_()) {
                $$11 += 18;
            } else if (p_99343_ >= $$8 && p_99344_ >= $$10 && p_99343_ < $$8 + 16 && p_99344_ < $$10 + 18) {
                $$11 += 36;
            }
            this.m_93228_(p_99342_, $$8, $$10 - 1, 0, $$11, 16, 18);
        }
    }

    private void m_99348_(int p_99349_, int p_99350_, int p_99351_) {
        List<StonecutterRecipe> $$3 = ((StonecutterMenu)this.f_97732_).m_40339_();
        for (int $$4 = this.f_99306_; $$4 < p_99351_ && $$4 < ((StonecutterMenu)this.f_97732_).m_40340_(); ++$$4) {
            int $$5 = $$4 - this.f_99306_;
            int $$6 = p_99349_ + $$5 % 4 * 16;
            int $$7 = $$5 / 4;
            int $$8 = p_99350_ + $$7 * 18 + 2;
            this.f_96541_.m_91291_().m_115203_($$3.get($$4).m_8043_(), $$6, $$8);
        }
    }

    @Override
    public boolean m_6375_(double p_99318_, double p_99319_, int p_99320_) {
        this.f_99305_ = false;
        if (this.f_99307_) {
            int $$3 = this.f_97735_ + 52;
            int $$4 = this.f_97736_ + 14;
            int $$5 = this.f_99306_ + 12;
            for (int $$6 = this.f_99306_; $$6 < $$5; ++$$6) {
                int $$7 = $$6 - this.f_99306_;
                double $$8 = p_99318_ - (double)($$3 + $$7 % 4 * 16);
                double $$9 = p_99319_ - (double)($$4 + $$7 / 4 * 18);
                if (!($$8 >= 0.0) || !($$9 >= 0.0) || !($$8 < 16.0) || !($$9 < 18.0) || !((StonecutterMenu)this.f_97732_).m_6366_(this.f_96541_.f_91074_, $$6)) continue;
                Minecraft.m_91087_().m_91106_().m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_12495_, 1.0f));
                this.f_96541_.f_91072_.m_105208_(((StonecutterMenu)this.f_97732_).f_38840_, $$6);
                return true;
            }
            $$3 = this.f_97735_ + 119;
            $$4 = this.f_97736_ + 9;
            if (p_99318_ >= (double)$$3 && p_99318_ < (double)($$3 + 12) && p_99319_ >= (double)$$4 && p_99319_ < (double)($$4 + 54)) {
                this.f_99305_ = true;
            }
        }
        return super.m_6375_(p_99318_, p_99319_, p_99320_);
    }

    @Override
    public boolean m_7979_(double p_99322_, double p_99323_, int p_99324_, double p_99325_, double p_99326_) {
        if (this.f_99305_ && this.m_99353_()) {
            int $$5 = this.f_97736_ + 14;
            int $$6 = $$5 + 54;
            this.f_99304_ = ((float)p_99323_ - (float)$$5 - 7.5f) / ((float)($$6 - $$5) - 15.0f);
            this.f_99304_ = Mth.m_14036_(this.f_99304_, 0.0f, 1.0f);
            this.f_99306_ = (int)((double)(this.f_99304_ * (float)this.m_99352_()) + 0.5) * 4;
            return true;
        }
        return super.m_7979_(p_99322_, p_99323_, p_99324_, p_99325_, p_99326_);
    }

    @Override
    public boolean m_6050_(double p_99314_, double p_99315_, double p_99316_) {
        if (this.m_99353_()) {
            int $$3 = this.m_99352_();
            float $$4 = (float)p_99316_ / (float)$$3;
            this.f_99304_ = Mth.m_14036_(this.f_99304_ - $$4, 0.0f, 1.0f);
            this.f_99306_ = (int)((double)(this.f_99304_ * (float)$$3) + 0.5) * 4;
        }
        return true;
    }

    private boolean m_99353_() {
        return this.f_99307_ && ((StonecutterMenu)this.f_97732_).m_40340_() > 12;
    }

    protected int m_99352_() {
        return (((StonecutterMenu)this.f_97732_).m_40340_() + 4 - 1) / 4 - 3;
    }

    private void m_99354_() {
        this.f_99307_ = ((StonecutterMenu)this.f_97732_).m_40341_();
        if (!this.f_99307_) {
            this.f_99304_ = 0.0f;
            this.f_99306_ = 0;
        }
    }
}

