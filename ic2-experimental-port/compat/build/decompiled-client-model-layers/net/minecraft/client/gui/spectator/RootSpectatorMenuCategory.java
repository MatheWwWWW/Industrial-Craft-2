/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.gui.spectator;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.gui.spectator.SpectatorMenuCategory;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import net.minecraft.client.gui.spectator.categories.TeleportToPlayerMenuCategory;
import net.minecraft.client.gui.spectator.categories.TeleportToTeamMenuCategory;
import net.minecraft.network.chat.Component;

public class RootSpectatorMenuCategory
implements SpectatorMenuCategory {
    private static final Component f_101765_ = Component.m_237115_("spectatorMenu.root.prompt");
    private final List<SpectatorMenuItem> f_101766_ = Lists.newArrayList();

    public RootSpectatorMenuCategory() {
        this.f_101766_.add(new TeleportToPlayerMenuCategory());
        this.f_101766_.add(new TeleportToTeamMenuCategory());
    }

    @Override
    public List<SpectatorMenuItem> m_5919_() {
        return this.f_101766_;
    }

    @Override
    public Component m_5878_() {
        return f_101765_;
    }
}

