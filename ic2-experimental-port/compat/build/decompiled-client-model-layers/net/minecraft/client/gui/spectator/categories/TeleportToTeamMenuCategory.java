/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuCategory;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import net.minecraft.client.gui.spectator.categories.TeleportToPlayerMenuCategory;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.scores.PlayerTeam;

public class TeleportToTeamMenuCategory
implements SpectatorMenuCategory,
SpectatorMenuItem {
    private static final Component f_101875_ = Component.m_237115_("spectatorMenu.team_teleport");
    private static final Component f_101876_ = Component.m_237115_("spectatorMenu.team_teleport.prompt");
    private final List<SpectatorMenuItem> f_101877_ = Lists.newArrayList();

    public TeleportToTeamMenuCategory() {
        Minecraft $$0 = Minecraft.m_91087_();
        for (PlayerTeam $$1 : $$0.f_91073_.m_6188_().m_83491_()) {
            this.f_101877_.add(new TeamSelectionItem($$1));
        }
    }

    @Override
    public List<SpectatorMenuItem> m_5919_() {
        return this.f_101877_;
    }

    @Override
    public Component m_5878_() {
        return f_101876_;
    }

    @Override
    public void m_7608_(SpectatorMenu p_101886_) {
        p_101886_.m_101794_(this);
    }

    @Override
    public Component m_7869_() {
        return f_101875_;
    }

    @Override
    public void m_6252_(PoseStack p_101882_, float p_101883_, int p_101884_) {
        RenderSystem.m_157456_(0, SpectatorGui.f_94760_);
        GuiComponent.m_93133_(p_101882_, 0, 0, 16.0f, 0.0f, 16, 16, 256, 256);
    }

    @Override
    public boolean m_7304_() {
        for (SpectatorMenuItem $$0 : this.f_101877_) {
            if (!$$0.m_7304_()) continue;
            return true;
        }
        return false;
    }

    static class TeamSelectionItem
    implements SpectatorMenuItem {
        private final PlayerTeam f_101891_;
        private final ResourceLocation f_101892_;
        private final List<PlayerInfo> f_101893_;

        public TeamSelectionItem(PlayerTeam p_194115_) {
            this.f_101891_ = p_194115_;
            this.f_101893_ = Lists.newArrayList();
            for (String $$1 : p_194115_.m_6809_()) {
                PlayerInfo $$2 = Minecraft.m_91087_().m_91403_().m_104938_($$1);
                if ($$2 == null) continue;
                this.f_101893_.add($$2);
            }
            if (this.f_101893_.isEmpty()) {
                this.f_101892_ = DefaultPlayerSkin.m_118626_();
            } else {
                String $$3 = this.f_101893_.get(RandomSource.m_216327_().m_188503_(this.f_101893_.size())).m_105312_().getName();
                this.f_101892_ = AbstractClientPlayer.m_108556_($$3);
                AbstractClientPlayer.m_172521_(this.f_101892_, $$3);
            }
        }

        @Override
        public void m_7608_(SpectatorMenu p_101902_) {
            p_101902_.m_101794_(new TeleportToPlayerMenuCategory(this.f_101893_));
        }

        @Override
        public Component m_7869_() {
            return this.f_101891_.m_83364_();
        }

        @Override
        public void m_6252_(PoseStack p_101898_, float p_101899_, int p_101900_) {
            Integer $$3 = this.f_101891_.m_7414_().m_126665_();
            if ($$3 != null) {
                float $$4 = (float)($$3 >> 16 & 0xFF) / 255.0f;
                float $$5 = (float)($$3 >> 8 & 0xFF) / 255.0f;
                float $$6 = (float)($$3 & 0xFF) / 255.0f;
                GuiComponent.m_93172_(p_101898_, 1, 1, 15, 15, Mth.m_14159_($$4 * p_101899_, $$5 * p_101899_, $$6 * p_101899_) | p_101900_ << 24);
            }
            RenderSystem.m_157456_(0, this.f_101892_);
            RenderSystem.m_157429_(p_101899_, p_101899_, p_101899_, (float)p_101900_ / 255.0f);
            PlayerFaceRenderer.m_240071_(p_101898_, 2, 2, 12);
        }

        @Override
        public boolean m_7304_() {
            return !this.f_101893_.isEmpty();
        }
    }
}

