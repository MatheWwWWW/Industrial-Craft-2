/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.gui.screens.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;

public class GameModeSwitcherScreen
extends Screen {
    static final ResourceLocation f_97541_ = new ResourceLocation("textures/gui/container/gamemode_switcher.png");
    private static final int f_169582_ = 128;
    private static final int f_169583_ = 128;
    private static final int f_169584_ = 26;
    private static final int f_169585_ = 5;
    private static final int f_169586_ = 31;
    private static final int f_169587_ = 5;
    private static final int f_97542_ = GameModeIcon.values().length * 31 - 5;
    private static final Component f_97543_ = Component.m_237110_("debug.gamemodes.select_next", Component.m_237115_("debug.gamemodes.press_f4").m_130940_(ChatFormatting.AQUA));
    private final Optional<GameModeIcon> f_97544_;
    private Optional<GameModeIcon> f_97545_ = Optional.empty();
    private int f_97546_;
    private int f_97547_;
    private boolean f_97548_;
    private final List<GameModeSlot> f_97549_ = Lists.newArrayList();

    public GameModeSwitcherScreen() {
        super(GameNarrator.f_93310_);
        this.f_97544_ = GameModeIcon.m_97612_(this.m_97575_());
    }

    private GameType m_97575_() {
        MultiPlayerGameMode $$0 = Minecraft.m_91087_().f_91072_;
        GameType $$1 = $$0.m_105294_();
        if ($$1 != null) {
            return $$1;
        }
        return $$0.m_105295_() == GameType.CREATIVE ? GameType.SURVIVAL : GameType.CREATIVE;
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        this.f_97545_ = this.f_97544_.isPresent() ? this.f_97544_ : GameModeIcon.m_97612_(this.f_96541_.f_91072_.m_105295_());
        for (int $$0 = 0; $$0 < GameModeIcon.f_97585_.length; ++$$0) {
            GameModeIcon $$1 = GameModeIcon.f_97585_[$$0];
            this.f_97549_.add(new GameModeSlot($$1, this.f_96543_ / 2 - f_97542_ / 2 + $$0 * 31, this.f_96544_ / 2 - 31));
        }
    }

    @Override
    public void m_6305_(PoseStack p_97557_, int p_97558_, int p_97559_, float p_97560_) {
        if (this.m_97577_()) {
            return;
        }
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        p_97557_.m_85836_();
        RenderSystem.m_69478_();
        RenderSystem.m_157456_(0, f_97541_);
        int $$4 = this.f_96543_ / 2 - 62;
        int $$5 = this.f_96544_ / 2 - 31 - 27;
        GameModeSwitcherScreen.m_93133_(p_97557_, $$4, $$5, 0.0f, 0.0f, 125, 75, 128, 128);
        p_97557_.m_85849_();
        super.m_6305_(p_97557_, p_97558_, p_97559_, p_97560_);
        this.f_97545_.ifPresent(p_97563_ -> GameModeSwitcherScreen.m_93215_(p_97557_, this.f_96547_, p_97563_.m_97597_(), this.f_96543_ / 2, this.f_96544_ / 2 - 31 - 20, -1));
        GameModeSwitcherScreen.m_93215_(p_97557_, this.f_96547_, f_97543_, this.f_96543_ / 2, this.f_96544_ / 2 + 5, 0xFFFFFF);
        if (!this.f_97548_) {
            this.f_97546_ = p_97558_;
            this.f_97547_ = p_97559_;
            this.f_97548_ = true;
        }
        boolean $$6 = this.f_97546_ == p_97558_ && this.f_97547_ == p_97559_;
        for (GameModeSlot $$7 : this.f_97549_) {
            $$7.m_6305_(p_97557_, p_97558_, p_97559_, p_97560_);
            this.f_97545_.ifPresent(p_97569_ -> $$7.m_97643_(p_97569_ == p_97568_.f_97623_));
            if ($$6 || !$$7.m_198029_()) continue;
            this.f_97545_ = Optional.of($$7.f_97623_);
        }
    }

    private void m_97576_() {
        GameModeSwitcherScreen.m_97564_(this.f_96541_, this.f_97545_);
    }

    private static void m_97564_(Minecraft p_97565_, Optional<GameModeIcon> p_97566_) {
        if (p_97565_.f_91072_ == null || p_97565_.f_91074_ == null || !p_97566_.isPresent()) {
            return;
        }
        Optional<GameModeIcon> $$2 = GameModeIcon.m_97612_(p_97565_.f_91072_.m_105295_());
        GameModeIcon $$3 = p_97566_.get();
        if ($$2.isPresent() && p_97565_.f_91074_.m_20310_(2) && $$3 != $$2.get()) {
            p_97565_.f_91074_.m_242614_($$3.m_97611_());
        }
    }

    private boolean m_97577_() {
        if (!InputConstants.m_84830_(this.f_96541_.m_91268_().m_85439_(), 292)) {
            this.m_97576_();
            this.f_96541_.m_91152_(null);
            return true;
        }
        return false;
    }

    @Override
    public boolean m_7933_(int p_97553_, int p_97554_, int p_97555_) {
        if (p_97553_ == 293 && this.f_97545_.isPresent()) {
            this.f_97548_ = false;
            this.f_97545_ = this.f_97545_.get().m_97616_();
            return true;
        }
        return super.m_7933_(p_97553_, p_97554_, p_97555_);
    }

    @Override
    public boolean m_7043_() {
        return false;
    }

    static final class GameModeIcon
    extends Enum<GameModeIcon> {
        public static final /* enum */ GameModeIcon CREATIVE = new GameModeIcon(Component.m_237115_("gameMode.creative"), "gamemode creative", new ItemStack(Blocks.f_50440_));
        public static final /* enum */ GameModeIcon SURVIVAL = new GameModeIcon(Component.m_237115_("gameMode.survival"), "gamemode survival", new ItemStack(Items.f_42383_));
        public static final /* enum */ GameModeIcon ADVENTURE = new GameModeIcon(Component.m_237115_("gameMode.adventure"), "gamemode adventure", new ItemStack(Items.f_42676_));
        public static final /* enum */ GameModeIcon SPECTATOR = new GameModeIcon(Component.m_237115_("gameMode.spectator"), "gamemode spectator", new ItemStack(Items.f_42545_));
        protected static final GameModeIcon[] f_97585_;
        private static final int f_169591_ = 16;
        protected static final int f_169590_ = 5;
        final Component f_97586_;
        final String f_97587_;
        final ItemStack f_97588_;
        private static final /* synthetic */ GameModeIcon[] $VALUES;

        public static GameModeIcon[] values() {
            return (GameModeIcon[])$VALUES.clone();
        }

        public static GameModeIcon valueOf(String p_97620_) {
            return Enum.valueOf(GameModeIcon.class, p_97620_);
        }

        private GameModeIcon(Component p_97594_, String p_97595_, ItemStack p_97596_) {
            this.f_97586_ = p_97594_;
            this.f_97587_ = p_97595_;
            this.f_97588_ = p_97596_;
        }

        void m_97607_(ItemRenderer p_97608_, int p_97609_, int p_97610_) {
            p_97608_.m_115203_(this.f_97588_, p_97609_, p_97610_);
        }

        Component m_97597_() {
            return this.f_97586_;
        }

        String m_97611_() {
            return this.f_97587_;
        }

        Optional<GameModeIcon> m_97616_() {
            switch (this) {
                case CREATIVE: {
                    return Optional.of(SURVIVAL);
                }
                case SURVIVAL: {
                    return Optional.of(ADVENTURE);
                }
                case ADVENTURE: {
                    return Optional.of(SPECTATOR);
                }
            }
            return Optional.of(CREATIVE);
        }

        static Optional<GameModeIcon> m_97612_(GameType p_97613_) {
            switch (p_97613_) {
                case SPECTATOR: {
                    return Optional.of(SPECTATOR);
                }
                case SURVIVAL: {
                    return Optional.of(SURVIVAL);
                }
                case CREATIVE: {
                    return Optional.of(CREATIVE);
                }
                case ADVENTURE: {
                    return Optional.of(ADVENTURE);
                }
            }
            return Optional.empty();
        }

        private static /* synthetic */ GameModeIcon[] m_169592_() {
            return new GameModeIcon[]{CREATIVE, SURVIVAL, ADVENTURE, SPECTATOR};
        }

        static {
            $VALUES = GameModeIcon.m_169592_();
            f_97585_ = GameModeIcon.values();
        }
    }

    public class GameModeSlot
    extends AbstractWidget {
        final GameModeIcon f_97623_;
        private boolean f_97624_;

        public GameModeSlot(GameModeIcon p_97627_, int p_97628_, int p_97629_) {
            super(p_97628_, p_97629_, 26, 26, p_97627_.m_97597_());
            this.f_97623_ = p_97627_;
        }

        @Override
        public void m_6303_(PoseStack p_97636_, int p_97637_, int p_97638_, float p_97639_) {
            Minecraft $$4 = Minecraft.m_91087_();
            this.m_97630_(p_97636_, $$4.m_91097_());
            this.f_97623_.m_97607_(GameModeSwitcherScreen.this.f_96542_, this.f_93620_ + 5, this.f_93621_ + 5);
            if (this.f_97624_) {
                this.m_97640_(p_97636_, $$4.m_91097_());
            }
        }

        @Override
        public void m_142291_(NarrationElementOutput p_169594_) {
            this.m_168802_(p_169594_);
        }

        @Override
        public boolean m_198029_() {
            return super.m_198029_() || this.f_97624_;
        }

        public void m_97643_(boolean p_97644_) {
            this.f_97624_ = p_97644_;
        }

        private void m_97630_(PoseStack p_97631_, TextureManager p_97632_) {
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_157456_(0, f_97541_);
            p_97631_.m_85836_();
            p_97631_.m_85837_(this.f_93620_, this.f_93621_, 0.0);
            GameModeSlot.m_93133_(p_97631_, 0, 0, 0.0f, 75.0f, 26, 26, 128, 128);
            p_97631_.m_85849_();
        }

        private void m_97640_(PoseStack p_97641_, TextureManager p_97642_) {
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_157456_(0, f_97541_);
            p_97641_.m_85836_();
            p_97641_.m_85837_(this.f_93620_, this.f_93621_, 0.0);
            GameModeSlot.m_93133_(p_97641_, 0, 0, 26.0f, 75.0f, 26, 26, 128, 128);
            p_97641_.m_85849_();
        }
    }
}

