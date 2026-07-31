/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class ReceivingLevelScreen
extends Screen {
    private static final Component f_96526_ = Component.m_237115_("multiplayer.downloadingTerrain");
    private static final long f_202370_ = 2000L;
    private boolean f_202371_ = false;
    private boolean f_202372_ = false;
    private final long f_202373_ = System.currentTimeMillis();

    public ReceivingLevelScreen() {
        super(GameNarrator.f_93310_);
    }

    @Override
    public boolean m_6913_() {
        return false;
    }

    @Override
    public void m_6305_(PoseStack p_96530_, int p_96531_, int p_96532_, float p_96533_) {
        this.m_96626_(0);
        ReceivingLevelScreen.m_93215_(p_96530_, this.f_96547_, f_96526_, this.f_96543_ / 2, this.f_96544_ / 2 - 50, 0xFFFFFF);
        super.m_6305_(p_96530_, p_96531_, p_96532_, p_96533_);
    }

    @Override
    public void m_86600_() {
        boolean $$2;
        boolean $$0;
        boolean bl = $$0 = this.f_202372_ || System.currentTimeMillis() > this.f_202373_ + 2000L;
        if (!$$0 || this.f_96541_ == null || this.f_96541_.f_91074_ == null) {
            return;
        }
        BlockPos $$1 = this.f_96541_.f_91074_.m_20183_();
        boolean bl2 = $$2 = this.f_96541_.f_91073_ != null && this.f_96541_.f_91073_.m_151562_($$1.m_123342_());
        if ($$2 || this.f_96541_.f_91060_.m_202430_($$1)) {
            this.m_7379_();
        }
        if (this.f_202371_) {
            this.f_202372_ = true;
        }
    }

    public void m_202375_() {
        this.f_202371_ = true;
    }

    @Override
    public boolean m_7043_() {
        return false;
    }
}

