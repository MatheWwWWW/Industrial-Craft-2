/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.gui.spectator;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.client.gui.spectator.RootSpectatorMenuCategory;
import net.minecraft.client.gui.spectator.SpectatorMenuCategory;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import net.minecraft.client.gui.spectator.SpectatorMenuListener;
import net.minecraft.client.gui.spectator.categories.SpectatorPage;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class SpectatorMenu {
    private static final SpectatorMenuItem f_101772_ = new CloseSpectatorItem();
    private static final SpectatorMenuItem f_101773_ = new ScrollMenuItem(-1, true);
    private static final SpectatorMenuItem f_101774_ = new ScrollMenuItem(1, true);
    private static final SpectatorMenuItem f_101775_ = new ScrollMenuItem(1, false);
    private static final int f_170328_ = 8;
    static final Component f_101776_ = Component.m_237115_("spectatorMenu.close");
    static final Component f_101777_ = Component.m_237115_("spectatorMenu.previous_page");
    static final Component f_101778_ = Component.m_237115_("spectatorMenu.next_page");
    public static final SpectatorMenuItem f_101771_ = new SpectatorMenuItem(){

        @Override
        public void m_7608_(SpectatorMenu p_101812_) {
        }

        @Override
        public Component m_7869_() {
            return CommonComponents.f_237098_;
        }

        @Override
        public void m_6252_(PoseStack p_101808_, float p_101809_, int p_101810_) {
        }

        @Override
        public boolean m_7304_() {
            return false;
        }
    };
    private final SpectatorMenuListener f_101779_;
    private SpectatorMenuCategory f_101780_ = new RootSpectatorMenuCategory();
    private int f_101781_ = -1;
    int f_101782_;

    public SpectatorMenu(SpectatorMenuListener p_101785_) {
        this.f_101779_ = p_101785_;
    }

    public SpectatorMenuItem m_101787_(int p_101788_) {
        int $$1 = p_101788_ + this.f_101782_ * 6;
        if (this.f_101782_ > 0 && p_101788_ == 0) {
            return f_101773_;
        }
        if (p_101788_ == 7) {
            if ($$1 < this.f_101780_.m_5919_().size()) {
                return f_101774_;
            }
            return f_101775_;
        }
        if (p_101788_ == 8) {
            return f_101772_;
        }
        if ($$1 < 0 || $$1 >= this.f_101780_.m_5919_().size()) {
            return f_101771_;
        }
        return (SpectatorMenuItem)MoreObjects.firstNonNull((Object)this.f_101780_.m_5919_().get($$1), (Object)f_101771_);
    }

    public List<SpectatorMenuItem> m_101786_() {
        ArrayList $$0 = Lists.newArrayList();
        for (int $$1 = 0; $$1 <= 8; ++$$1) {
            $$0.add(this.m_101787_($$1));
        }
        return $$0;
    }

    public SpectatorMenuItem m_101796_() {
        return this.m_101787_(this.f_101781_);
    }

    public SpectatorMenuCategory m_101799_() {
        return this.f_101780_;
    }

    public void m_101797_(int p_101798_) {
        SpectatorMenuItem $$1 = this.m_101787_(p_101798_);
        if ($$1 != f_101771_) {
            if (this.f_101781_ == p_101798_ && $$1.m_7304_()) {
                $$1.m_7608_(this);
            } else {
                this.f_101781_ = p_101798_;
            }
        }
    }

    public void m_101800_() {
        this.f_101779_.m_7613_(this);
    }

    public int m_101801_() {
        return this.f_101781_;
    }

    public void m_101794_(SpectatorMenuCategory p_101795_) {
        this.f_101780_ = p_101795_;
        this.f_101781_ = -1;
        this.f_101782_ = 0;
    }

    public SpectatorPage m_101802_() {
        return new SpectatorPage(this.m_101786_(), this.f_101781_);
    }

    static class CloseSpectatorItem
    implements SpectatorMenuItem {
        CloseSpectatorItem() {
        }

        @Override
        public void m_7608_(SpectatorMenu p_101823_) {
            p_101823_.m_101800_();
        }

        @Override
        public Component m_7869_() {
            return f_101776_;
        }

        @Override
        public void m_6252_(PoseStack p_101819_, float p_101820_, int p_101821_) {
            RenderSystem.m_157456_(0, SpectatorGui.f_94760_);
            GuiComponent.m_93133_(p_101819_, 0, 0, 128.0f, 0.0f, 16, 16, 256, 256);
        }

        @Override
        public boolean m_7304_() {
            return true;
        }
    }

    static class ScrollMenuItem
    implements SpectatorMenuItem {
        private final int f_101826_;
        private final boolean f_101827_;

        public ScrollMenuItem(int p_101829_, boolean p_101830_) {
            this.f_101826_ = p_101829_;
            this.f_101827_ = p_101830_;
        }

        @Override
        public void m_7608_(SpectatorMenu p_101836_) {
            p_101836_.f_101782_ += this.f_101826_;
        }

        @Override
        public Component m_7869_() {
            return this.f_101826_ < 0 ? f_101777_ : f_101778_;
        }

        @Override
        public void m_6252_(PoseStack p_101832_, float p_101833_, int p_101834_) {
            RenderSystem.m_157456_(0, SpectatorGui.f_94760_);
            if (this.f_101826_ < 0) {
                GuiComponent.m_93133_(p_101832_, 0, 0, 144.0f, 0.0f, 16, 16, 256, 256);
            } else {
                GuiComponent.m_93133_(p_101832_, 0, 0, 160.0f, 0.0f, 16, 16, 256, 256);
            }
        }

        @Override
        public boolean m_7304_() {
            return this.f_101827_;
        }
    }
}

