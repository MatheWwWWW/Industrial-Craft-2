/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ComparisonChain
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Ordering
 */
package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.client.gui.spectator.PlayerMenuItem;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuCategory;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.GameType;

public class TeleportToPlayerMenuCategory
implements SpectatorMenuCategory,
SpectatorMenuItem {
    private static final Ordering<PlayerInfo> f_101854_ = Ordering.from((p_101870_, p_101871_) -> ComparisonChain.start().compare((Comparable)p_101870_.m_105312_().getId(), (Comparable)p_101871_.m_105312_().getId()).result());
    private static final Component f_101855_ = Component.m_237115_("spectatorMenu.teleport");
    private static final Component f_101856_ = Component.m_237115_("spectatorMenu.teleport.prompt");
    private final List<SpectatorMenuItem> f_101857_ = Lists.newArrayList();

    public TeleportToPlayerMenuCategory() {
        this(f_101854_.sortedCopy(Minecraft.m_91087_().m_91403_().m_105142_()));
    }

    public TeleportToPlayerMenuCategory(Collection<PlayerInfo> p_101861_) {
        for (PlayerInfo $$1 : f_101854_.sortedCopy(p_101861_)) {
            if ($$1.m_105325_() == GameType.SPECTATOR) continue;
            this.f_101857_.add(new PlayerMenuItem($$1.m_105312_()));
        }
    }

    @Override
    public List<SpectatorMenuItem> m_5919_() {
        return this.f_101857_;
    }

    @Override
    public Component m_5878_() {
        return f_101856_;
    }

    @Override
    public void m_7608_(SpectatorMenu p_101868_) {
        p_101868_.m_101794_(this);
    }

    @Override
    public Component m_7869_() {
        return f_101855_;
    }

    @Override
    public void m_6252_(PoseStack p_101864_, float p_101865_, int p_101866_) {
        RenderSystem.m_157456_(0, SpectatorGui.f_94760_);
        GuiComponent.m_93133_(p_101864_, 0, 0, 0.0f, 0.0f, 16, 16, 256, 256);
    }

    @Override
    public boolean m_7304_() {
        return !this.f_101857_.isEmpty();
    }
}

