/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.components.spectator;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import net.minecraft.client.gui.spectator.SpectatorMenuListener;
import net.minecraft.client.gui.spectator.categories.SpectatorPage;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SpectatorGui
extends GuiComponent
implements SpectatorMenuListener {
    private static final ResourceLocation f_94761_ = new ResourceLocation("textures/gui/widgets.png");
    public static final ResourceLocation f_94760_ = new ResourceLocation("textures/gui/spectator_widgets.png");
    private static final long f_169074_ = 5000L;
    private static final long f_169075_ = 2000L;
    private final Minecraft f_94762_;
    private long f_94763_;
    @Nullable
    private SpectatorMenu f_94764_;

    public SpectatorGui(Minecraft p_94767_) {
        this.f_94762_ = p_94767_;
    }

    public void m_94771_(int p_94772_) {
        this.f_94763_ = Util.m_137550_();
        if (this.f_94764_ != null) {
            this.f_94764_.m_101797_(p_94772_);
        } else {
            this.f_94764_ = new SpectatorMenu(this);
        }
    }

    private float m_94794_() {
        long $$0 = this.f_94763_ - Util.m_137550_() + 5000L;
        return Mth.m_14036_((float)$$0 / 2000.0f, 0.0f, 1.0f);
    }

    public void m_193837_(PoseStack p_193838_) {
        if (this.f_94764_ == null) {
            return;
        }
        float $$1 = this.m_94794_();
        if ($$1 <= 0.0f) {
            this.f_94764_.m_101800_();
            return;
        }
        int $$2 = this.f_94762_.m_91268_().m_85445_() / 2;
        int $$3 = this.m_93252_();
        this.m_93250_(-90);
        int $$4 = Mth.m_14143_((float)this.f_94762_.m_91268_().m_85446_() - 22.0f * $$1);
        SpectatorPage $$5 = this.f_94764_.m_101802_();
        this.m_94778_(p_193838_, $$1, $$2, $$4, $$5);
        this.m_93250_($$3);
    }

    protected void m_94778_(PoseStack p_94779_, float p_94780_, int p_94781_, int p_94782_, SpectatorPage p_94783_) {
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, p_94780_);
        RenderSystem.m_157456_(0, f_94761_);
        this.m_93228_(p_94779_, p_94781_ - 91, p_94782_, 0, 0, 182, 22);
        if (p_94783_.m_101853_() >= 0) {
            this.m_93228_(p_94779_, p_94781_ - 91 - 1 + p_94783_.m_101853_() * 20, p_94782_ - 1, 0, 22, 24, 22);
        }
        for (int $$5 = 0; $$5 < 9; ++$$5) {
            this.m_94784_(p_94779_, $$5, this.f_94762_.m_91268_().m_85445_() / 2 - 90 + $$5 * 20 + 2, p_94782_ + 3, p_94780_, p_94783_.m_101851_($$5));
        }
        RenderSystem.m_69461_();
    }

    private void m_94784_(PoseStack p_94785_, int p_94786_, int p_94787_, float p_94788_, float p_94789_, SpectatorMenuItem p_94790_) {
        RenderSystem.m_157456_(0, f_94760_);
        if (p_94790_ != SpectatorMenu.f_101771_) {
            int $$6 = (int)(p_94789_ * 255.0f);
            p_94785_.m_85836_();
            p_94785_.m_85837_(p_94787_, p_94788_, 0.0);
            float $$7 = p_94790_.m_7304_() ? 1.0f : 0.25f;
            RenderSystem.m_157429_($$7, $$7, $$7, p_94789_);
            p_94790_.m_6252_(p_94785_, $$7, $$6);
            p_94785_.m_85849_();
            if ($$6 > 3 && p_94790_.m_7304_()) {
                Component $$8 = this.f_94762_.f_91066_.f_92056_[p_94786_].m_90863_();
                this.f_94762_.f_91062_.m_92763_(p_94785_, $$8, p_94787_ + 19 - 2 - this.f_94762_.f_91062_.m_92852_($$8), p_94788_ + 6.0f + 3.0f, 0xFFFFFF + ($$6 << 24));
            }
        }
    }

    public void m_94773_(PoseStack p_94774_) {
        int $$1 = (int)(this.m_94794_() * 255.0f);
        if ($$1 > 3 && this.f_94764_ != null) {
            Component $$3;
            SpectatorMenuItem $$2 = this.f_94764_.m_101796_();
            Component component = $$3 = $$2 == SpectatorMenu.f_101771_ ? this.f_94764_.m_101799_().m_5878_() : $$2.m_7869_();
            if ($$3 != null) {
                int $$4 = (this.f_94762_.m_91268_().m_85445_() - this.f_94762_.f_91062_.m_92852_($$3)) / 2;
                int $$5 = this.f_94762_.m_91268_().m_85446_() - 35;
                RenderSystem.m_69478_();
                RenderSystem.m_69453_();
                this.f_94762_.f_91062_.m_92763_(p_94774_, $$3, $$4, $$5, 0xFFFFFF + ($$1 << 24));
                RenderSystem.m_69461_();
            }
        }
    }

    @Override
    public void m_7613_(SpectatorMenu p_94792_) {
        this.f_94764_ = null;
        this.f_94763_ = 0L;
    }

    public boolean m_94768_() {
        return this.f_94764_ != null;
    }

    public void m_205380_(int p_205381_) {
        int $$1;
        for ($$1 = this.f_94764_.m_101801_() + p_205381_; !($$1 < 0 || $$1 > 8 || this.f_94764_.m_101787_($$1) != SpectatorMenu.f_101771_ && this.f_94764_.m_101787_($$1).m_7304_()); $$1 += p_205381_) {
        }
        if ($$1 >= 0 && $$1 <= 8) {
            this.f_94764_.m_101797_($$1);
            this.f_94763_ = Util.m_137550_();
        }
    }

    public void m_94793_() {
        this.f_94763_ = Util.m_137550_();
        if (this.m_94768_()) {
            int $$0 = this.f_94764_.m_101801_();
            if ($$0 != -1) {
                this.f_94764_.m_101797_($$0);
            }
        } else {
            this.f_94764_ = new SpectatorMenu(this);
        }
    }
}

