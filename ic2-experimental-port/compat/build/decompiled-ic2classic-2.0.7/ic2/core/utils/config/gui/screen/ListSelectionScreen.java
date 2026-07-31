/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.ConfirmScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.utils.config.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.gui.api.BackgroundTexture;
import ic2.core.utils.config.gui.api.ICompoundNode;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.api.INode;
import ic2.core.utils.config.gui.api.ISuggestionRenderer;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.gui.config.Element;
import ic2.core.utils.config.gui.config.ElementList;
import ic2.core.utils.config.gui.config.ListScreen;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public abstract class ListSelectionScreen
extends ListScreen {
    IConfigNode node;
    INode value;
    Screen parent;
    Button apply;
    Runnable abortListener;
    Runnable successListener;
    boolean dontWarn;

    public ListSelectionScreen(Screen parent, IConfigNode node, INode value, BackgroundTexture customTexture) {
        super(node.getName(), customTexture);
        this.parent = parent;
        this.node = node;
        this.value = value;
        this.value.createTemp();
    }

    public static ListSelectionScreen ofValue(Screen parent, IConfigNode node, IValueNode value, BackgroundTexture customTexture) {
        return new Value(parent, node, value, customTexture);
    }

    public static ListSelectionScreen ofCompound(Screen parent, IConfigNode node, ICompoundNode value, BackgroundTexture customTexture) {
        return new Compound(parent, node, value, customTexture);
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        this.visibleList.m_93471_(true);
        this.loadDefault();
        this.visibleList.setCallback(T -> this.setValue(((SelectionElement)((Object)T)).getSuggestion().getValue()));
        int x = this.f_96543_ / 2 - 100;
        int y = this.f_96544_;
        this.apply = (Button)this.m_142416_((GuiEventListener)new ExtendedButton(x + 10, y - 27, 85, 20, (Component)Component.m_237115_((String)"gui.ic2.pick"), this::save));
        this.m_142416_((GuiEventListener)new ExtendedButton(x + 105, y - 27, 85, 20, (Component)Component.m_237115_((String)"gui.ic2.cancel"), this::cancel));
    }

    public ListSelectionScreen withListener(Runnable success, Runnable abort) {
        this.successListener = success;
        this.abortListener = abort;
        return this;
    }

    public ListSelectionScreen disableAbortWarning() {
        this.dontWarn = true;
        return this;
    }

    protected abstract void loadDefault();

    protected abstract void setValue(String var1);

    protected void findDefault(String defaultValue) {
        for (Element element : this.allEntries) {
            if (!((SelectionElement)element).getSuggestion().getValue().equals(defaultValue)) continue;
            this.visibleList.setSelected(element);
            break;
        }
        this.visibleList.scrollToSelected(true);
    }

    @Override
    protected List<Element> sortElements(List<Element> list) {
        list.sort(Comparator.comparing(Element::getName, String.CASE_INSENSITIVE_ORDER));
        return list;
    }

    @Override
    public void m_6305_(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.apply.f_93623_ = this.value.isChanged();
        super.m_6305_(stack, mouseX, mouseY, partialTicks);
        this.f_96547_.m_92889_(stack, this.f_96539_, (float)(this.f_96543_ / 2 - this.f_96547_.m_92852_((FormattedText)this.f_96539_) / 2), 8.0f, -1);
    }

    @Override
    protected void collectElements(Consumer<Element> elements) {
        for (ConfigEntry.Suggestion entry : this.node.getValidValues()) {
            elements.accept(new SelectionElement(entry, this.visibleList));
        }
    }

    public void m_7379_() {
        this.abort();
        this.f_96541_.m_91152_(this.parent);
    }

    private void save(Button button) {
        this.value.apply();
        if (this.successListener != null) {
            this.successListener.run();
        } else {
            this.f_96541_.m_91152_(this.parent);
        }
    }

    private void cancel(Button button) {
        if (this.value.isChanged() && !this.dontWarn) {
            this.f_96541_.m_91152_((Screen)new ConfirmScreen(T -> {
                if (T) {
                    this.abort();
                }
                this.f_96541_.m_91152_((Screen)(T ? this.parent : this));
            }, (Component)Component.m_237115_((String)"gui.ic2.warn.changed"), (Component)Component.m_237115_((String)"gui.ic2.warn.changed.desc").m_130940_(ChatFormatting.GRAY)));
            return;
        }
        this.abort();
        this.f_96541_.m_91152_(this.parent);
    }

    private void abort() {
        this.value.setPrevious();
        if (this.abortListener != null) {
            this.abortListener.run();
        }
    }

    public static class Value
    extends ListSelectionScreen {
        public Value(Screen parent, IConfigNode node, IValueNode value, BackgroundTexture customTexture) {
            super(parent, node, value, customTexture);
        }

        @Override
        protected void loadDefault() {
            this.findDefault(((IValueNode)this.value).get());
        }

        @Override
        protected void setValue(String value) {
            ((IValueNode)this.value).set(value);
        }
    }

    public static class Compound
    extends ListSelectionScreen {
        public Compound(Screen parent, IConfigNode node, ICompoundNode value, BackgroundTexture customTexture) {
            super(parent, node, value, customTexture);
        }

        @Override
        protected void loadDefault() {
            this.findDefault(((ICompoundNode)this.value).get());
        }

        @Override
        protected void setValue(String value) {
            ((ICompoundNode)this.value).set(value);
        }
    }

    private class SelectionElement
    extends Element {
        ConfigEntry.Suggestion suggestion;
        ElementList myList;
        int lastClick;
        ISuggestionRenderer renderer;
        boolean loaded;

        public SelectionElement(ConfigEntry.Suggestion suggestion, ElementList list) {
            super((Component)Component.m_237115_((String)suggestion.getName()));
            this.lastClick = -1;
            this.loaded = false;
            this.suggestion = suggestion;
            this.myList = list;
        }

        @Override
        public void m_6311_(PoseStack poseStack, int x, int top, int left, int width, int height, int mouseX, int mouseY, boolean selected, float partialTicks) {
            Component comp;
            ISuggestionRenderer renderer = this.getRenderer();
            if (renderer != null && (comp = renderer.renderSuggestion(poseStack, this.suggestion.getValue(), left, top)) != null && mouseX >= left && mouseX <= left + 20 && mouseY >= top && mouseY <= top + 20) {
                this.owner.addTooltips(comp);
            }
            this.renderText(poseStack, (Component)Component.m_237119_().m_130940_(this.myList.m_93511_() == this ? ChatFormatting.YELLOW : ChatFormatting.WHITE).m_7220_(this.name), left + (renderer != null ? 20 : 0), top + 6, -1);
        }

        private ISuggestionRenderer getRenderer() {
            if (this.loaded) {
                return this.renderer;
            }
            this.loaded = true;
            if (this.suggestion.getExtra() != null) {
                this.renderer = ISuggestionRenderer.Registry.getRendererForType(this.suggestion.getExtra());
            }
            return this.renderer;
        }

        public ConfigEntry.Suggestion getSuggestion() {
            return this.suggestion;
        }

        public boolean m_6375_(double p_94737_, double p_94738_, int p_94739_) {
            if (this.myList.m_93511_() == this) {
                if (this.lastClick >= 0 && this.myList.getLastTick() - this.lastClick <= 5) {
                    ListSelectionScreen.this.save(null);
                    return true;
                }
                this.lastClick = this.myList.getLastTick();
            } else {
                this.lastClick = this.myList.getLastTick();
            }
            this.myList.setSelected(this);
            return true;
        }
    }
}

