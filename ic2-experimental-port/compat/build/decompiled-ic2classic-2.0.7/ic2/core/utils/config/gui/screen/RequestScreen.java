/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 */
package ic2.core.utils.config.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.config.gui.api.BackgroundTexture;
import ic2.core.utils.config.gui.api.IModConfig;
import ic2.core.utils.config.gui.api.IRequestScreen;
import ic2.core.utils.config.gui.config.Element;
import ic2.core.utils.config.gui.config.ListScreen;
import ic2.core.utils.config.gui.screen.ConfigScreen;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;

public class RequestScreen
extends ListScreen
implements IRequestScreen {
    static final Component REQUEST = Component.m_237115_((String)"gui.ic2.requesting_config");
    static final Component[] ANIMATION = new Component[]{Component.m_237113_((String)"Ooooo").m_130940_(ChatFormatting.GRAY), Component.m_237113_((String)"oOooo").m_130940_(ChatFormatting.GRAY), Component.m_237113_((String)"ooOoo").m_130940_(ChatFormatting.GRAY), Component.m_237113_((String)"oooOo").m_130940_(ChatFormatting.GRAY), Component.m_237113_((String)"ooooO").m_130940_(ChatFormatting.GRAY)};
    Screen parent;
    IModConfig config;
    UUID requestId;
    Predicate<FriendlyByteBuf> result;
    int tick = 0;

    public RequestScreen(BackgroundTexture customTexture, Screen parent, IModConfig config) {
        super((Component)Component.m_237113_((String)"Request Screen"), customTexture);
        this.parent = parent;
        this.requestId = UUID.randomUUID();
        this.config = config.loadFromNetworking(this.requestId, T -> {
            this.result = T;
        });
    }

    @Override
    protected void collectElements(Consumer<Element> elements) {
    }

    @Override
    public void receiveConfigData(UUID requestId, FriendlyByteBuf buf) {
        if (!this.requestId.equals(requestId)) {
            return;
        }
        if (this.result == null) {
            return;
        }
        if (this.result.test(buf)) {
            this.f_96541_.m_91152_((Screen)new ConfigScreen((Component)Component.m_237113_((String)this.config.getConfigName()), this.config, this.parent, this.getCustomTexture()));
            return;
        }
        this.f_96541_.m_91152_(this.parent);
    }

    @Override
    public void m_86600_() {
        super.m_86600_();
        ++this.tick;
        if (this.tick > 400) {
            this.f_96541_.m_91152_(this.parent);
        }
    }

    @Override
    public void m_6305_(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.m_6305_(stack, mouseX, mouseY, partialTicks);
        this.f_96547_.m_92889_(stack, REQUEST, (float)(this.f_96543_ / 2 - this.f_96547_.m_92852_((FormattedText)REQUEST) / 2), (float)(this.f_96544_ / 2 - 12), -1);
        int index = this.tick / 5 % 8;
        if (index >= 5) {
            index = 8 - index;
        }
        this.f_96547_.m_92889_(stack, ANIMATION[index], (float)(this.f_96543_ / 2 - this.f_96547_.m_92852_((FormattedText)ANIMATION[index]) / 2), (float)(this.f_96544_ / 2), -1);
        int timeout = (401 - this.tick) / 20;
        if (timeout <= 18) {
            MutableComponent draw = Component.m_237110_((String)"gui.ic2.timeout", (Object[])new Object[]{timeout}).m_130940_(ChatFormatting.RED);
            this.f_96547_.m_92889_(stack, (Component)draw, (float)(this.f_96543_ / 2 - this.f_96547_.m_92852_((FormattedText)draw) / 2), (float)(this.f_96544_ / 2 + 12), -1);
        }
    }
}

