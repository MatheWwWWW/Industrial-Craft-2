/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 */
package net.minecraft.client.gui.spectator.categories;

import com.google.common.base.MoreObjects;
import java.util.List;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;

public class SpectatorPage {
    public static final int f_170329_ = -1;
    private final List<SpectatorMenuItem> f_101845_;
    private final int f_101846_;

    public SpectatorPage(List<SpectatorMenuItem> p_170331_, int p_170332_) {
        this.f_101845_ = p_170331_;
        this.f_101846_ = p_170332_;
    }

    public SpectatorMenuItem m_101851_(int p_101852_) {
        if (p_101852_ < 0 || p_101852_ >= this.f_101845_.size()) {
            return SpectatorMenu.f_101771_;
        }
        return (SpectatorMenuItem)MoreObjects.firstNonNull((Object)this.f_101845_.get(p_101852_), (Object)SpectatorMenu.f_101771_);
    }

    public int m_101853_() {
        return this.f_101846_;
    }
}

