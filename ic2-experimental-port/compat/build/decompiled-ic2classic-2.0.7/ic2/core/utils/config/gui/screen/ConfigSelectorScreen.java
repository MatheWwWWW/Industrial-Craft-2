/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.multiplayer.ServerData
 *  net.minecraft.locale.Language
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.utils.config.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.config.api.ConfigType;
import ic2.core.utils.config.gui.api.BackgroundTexture;
import ic2.core.utils.config.gui.api.IModConfig;
import ic2.core.utils.config.gui.api.IModConfigs;
import ic2.core.utils.config.gui.config.ConfigElement;
import ic2.core.utils.config.gui.config.Element;
import ic2.core.utils.config.gui.config.IIgnoreSearch;
import ic2.core.utils.config.gui.config.ListScreen;
import ic2.core.utils.config.gui.screen.ConfigScreen;
import ic2.core.utils.config.gui.screen.RequestScreen;
import ic2.core.utils.config.gui.screen.SelectFileScreen;
import ic2.core.utils.config.gui.widgets.CarbonIconButton;
import ic2.core.utils.config.gui.widgets.GuiUtils;
import ic2.core.utils.config.gui.widgets.Icon;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class ConfigSelectorScreen
extends ListScreen {
    IModConfigs configs;
    Screen parent;
    Component modName;
    Label toAdd;

    public ConfigSelectorScreen(IModConfigs configs, Screen parent) {
        this(configs, configs.getBackground(), parent);
    }

    public ConfigSelectorScreen(IModConfigs configs, BackgroundTexture customTexture, Screen parent) {
        super((Component)Component.m_237115_((String)"gui.ic2.select_config"), customTexture);
        this.configs = configs;
        this.parent = parent;
        this.modName = Component.m_237113_((String)configs.getModName());
    }

    @Override
    protected void m_7856_() {
        this.toAdd = null;
        super.m_7856_();
        int x = this.f_96543_ / 2;
        int y = this.f_96544_;
        this.m_142416_((GuiEventListener)new ExtendedButton(x - 80, y - 27, 160, 20, (Component)Component.m_237115_((String)"gui.ic2.back"), T -> this.m_7379_()));
    }

    @Override
    public void m_6305_(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.m_6305_(stack, mouseX, mouseY, partialTicks);
        this.f_96547_.m_92889_(stack, this.modName, (float)(this.f_96543_ / 2 - this.f_96547_.m_92852_((FormattedText)this.modName) / 2), 8.0f, -1);
    }

    @Override
    protected void collectElements(Consumer<Element> elements) {
        elements.accept(new Label((Component)Component.m_237113_((String)"Local Configs").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})));
        this.addConfigs(ConfigType.CLIENT, false, elements);
        this.addConfigs(ConfigType.SHARED, false, elements);
        if (this.f_96541_.f_91073_ != null) {
            if (!this.f_96541_.m_91091_()) {
                if (this.isLanServer()) {
                    return;
                }
                if (!this.f_96541_.f_91074_.m_20310_(4)) {
                    return;
                }
                this.toAdd = new Label((Component)Component.m_237113_((String)"Multiplayer Configs").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}));
                this.addConfigs(ConfigType.SHARED, true, elements);
            } else {
                this.toAdd = new Label((Component)Component.m_237113_((String)"World Configs").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}));
            }
        } else {
            this.toAdd = new Label((Component)Component.m_237113_((String)"World Configs").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}));
        }
        this.addConfigs(ConfigType.SERVER, true, elements);
    }

    private void addConfigs(ConfigType type, boolean multiplayer, Consumer<Element> elements) {
        this.configs.getConfigInstances(type).forEach(T -> {
            if (this.toAdd != null) {
                elements.accept(this.toAdd);
                this.toAdd = null;
            }
            elements.accept(new DirectConfig((IModConfig)T, this.modName, this, multiplayer));
        });
    }

    public void m_7379_() {
        this.f_96541_.m_91152_(this.parent);
    }

    private boolean isLanServer() {
        ServerData data = this.f_96541_.m_91089_();
        return data != null && data.m_105389_();
    }

    public static class Label
    extends Element
    implements IIgnoreSearch {
        public Label(Component name) {
            super(name);
        }

        @Override
        public boolean shouldIgnoreSearch() {
            return true;
        }

        @Override
        public void m_6311_(PoseStack poseStack, int x, int top, int left, int width, int height, int mouseX, int mouseY, boolean selected, float partialTicks) {
            float f = ConfigElement.GuiAlign.CENTER.align(left, width, this.font.m_92852_((FormattedText)this.name));
            Objects.requireNonNull(this.font);
            this.renderText(poseStack, this.name, f, ConfigElement.GuiAlign.CENTER.align(top, height, 9), -1);
        }
    }

    public static class DirectConfig
    extends Element {
        List<GuiEventListener> children = new ObjectArrayList();
        Screen parent;
        IModConfig handler;
        Button button;
        CarbonIconButton reset;
        boolean multi;
        boolean multiplayer;
        Component type;
        Component fileName;
        Component baseName;

        public DirectConfig(IModConfig handler, Component baseName, Screen parent, boolean multiplayer) {
            super((Component)Component.m_237113_((String)handler.getFileName()));
            this.handler = handler;
            this.baseName = baseName;
            this.parent = parent;
            this.multiplayer = multiplayer;
        }

        @Override
        public void init() {
            this.multi = this.shouldCreatePick();
            if (this.multi) {
                this.button = new ExtendedButton(0, 0, 82, 20, (Component)Component.m_237115_((String)"gui.ic2.pick_file"), this::onPick);
                this.children.add((GuiEventListener)this.button);
            } else {
                this.button = new ExtendedButton(0, 0, 60, 20, (Component)Component.m_237115_((String)"gui.ic2.modify"), this::onEdit);
                this.reset = new CarbonIconButton(0, 0, 20, 20, Icon.REVERT, (Component)Component.m_237119_(), this::reset).setIconOnly();
                this.reset.f_93623_ = !this.handler.isDefault() && !this.isInWorldConfig();
                this.children.add((GuiEventListener)this.button);
                this.children.add((GuiEventListener)this.reset);
            }
            this.type = Component.m_237115_((String)("gui.ic2.type." + this.handler.getConfigType().name().toLowerCase()));
            this.fileName = Component.m_237113_((String)this.handler.getFileName()).m_130940_(ChatFormatting.GRAY);
        }

        @Override
        public void m_6311_(PoseStack poseStack, int x, int top, int left, int width, int height, int mouseX, int mouseY, boolean selected, float partialTicks) {
            this.button.f_93620_ = left + width - 82;
            this.button.f_93621_ = top + 2;
            this.button.m_6305_(poseStack, mouseX, mouseY, partialTicks);
            if (this.reset != null) {
                this.reset.f_93620_ = left + width - 20;
                this.reset.f_93621_ = top + 2;
                this.reset.m_6305_(poseStack, mouseX, mouseY, partialTicks);
            }
            this.font.m_92877_(poseStack, Language.m_128107_().m_5536_(GuiUtils.ellipsize((FormattedText)this.type, 130, this.font)), (float)(left + 5), (float)(top + 2), -1);
            this.font.m_92877_(poseStack, Language.m_128107_().m_5536_(GuiUtils.ellipsize((FormattedText)this.fileName, 130, this.font)), (float)(left + 5), (float)(top + 11), -1);
            GuiUtils.drawTextureRegion(poseStack, left - 20, top, 22.0f, 22.0f, this.getIcon(), 16.0f, 16.0f);
        }

        @Override
        public List<? extends GuiEventListener> m_6702_() {
            return this.children;
        }

        private boolean shouldCreatePick() {
            return this.handler.isDynamicConfig() && !this.isInWorldConfig();
        }

        private boolean isInWorldConfig() {
            return this.mc.f_91073_ != null && (this.handler.getConfigType() == ConfigType.SERVER || this.handler.getConfigType() == ConfigType.SHARED && this.multiplayer);
        }

        private Icon getIcon() {
            return (this.multi ? Icon.MULTITYPE_ICON : Icon.TYPE_ICON).get((Object)this.handler.getConfigType());
        }

        private void onPick(Button button) {
            this.mc.m_91152_((Screen)new SelectFileScreen(this.baseName, this.owner.getCustomTexture(), this.parent, this.handler));
        }

        private void reset(CarbonIconButton button) {
            this.handler.restoreDefault();
            this.handler.save();
            this.reset.f_93623_ = !this.handler.isDefault();
        }

        private void onEdit(Button button) {
            if (this.isInWorldConfig() && !this.mc.m_91091_()) {
                this.mc.m_91152_((Screen)new RequestScreen(this.owner.getCustomTexture(), this.parent, this.handler));
            } else {
                this.mc.m_91152_((Screen)new ConfigScreen((Component)this.baseName.m_6881_().m_7220_((Component)Component.m_237113_((String)" > ").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})).m_7220_(this.type), this.handler, this.parent, this.owner.getCustomTexture()));
            }
        }
    }
}

